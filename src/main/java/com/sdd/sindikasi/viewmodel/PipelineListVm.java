package com.sdd.sindikasi.viewmodel;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WebApps;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

import com.sdd.sindikasi.domain.Tpipeline;
import com.sdd.sindikasi.domain.Vpipelineperiod;
import com.sdd.sindikasi.domain.Vpipelinesector;
import com.sdd.sindikasi.domain.Vpipelineunit;
import com.sdd.sindikasi.dao.MdebiturDAO;
import com.sdd.sindikasi.dao.MsectorDAO;
import com.sdd.sindikasi.dao.MunitDAO;
import com.sdd.sindikasi.dao.TpipelineDAO;
import com.sdd.sindikasi.domain.Mdebitur;
import com.sdd.sindikasi.domain.Msector;
import com.sdd.sindikasi.domain.Munit;
import com.sdd.sindikasi.model.TpipelineListModel;
import com.sdd.sindikasi.utils.AppData;
import com.sdd.sindikasi.utils.AppUtils;
import com.sdd.utils.SysUtils;

public class PipelineListVm {

	private SimpleDateFormat dateLocalFormatter = new SimpleDateFormat("dd-MM-yyyy");
	private DecimalFormat decimalLocalFormatter = new DecimalFormat("#,###");

	private Tpipeline objForm;

	private TpipelineListModel model;

	private int pageStartNumber;
	private int pageTotalSize;
	private boolean needsPageUpdate;
	private String filter;
	private String orderby;

	private String regid;
	private String debitur;
	private Msector msector;
	private Munit munit;

	private String arg;
	private Vpipelineunit vpipelineunit;
	private Vpipelinesector vpipelinesector;
	private Vpipelineperiod vpipelineperiod;

	@Wire
	private Caption caption;
	@Wire
	private Combobox cbSector;
	@Wire
	private Combobox cbUnit;
	@Wire
	private Checkbox chkAgentFac;
	@Wire
	private Checkbox chkAgentCol;
	@Wire
	private Checkbox chkAgentEsc;
	@Wire
	private Paging paging;
	@Wire
	private Listbox listbox;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("arg") final String arg, @ExecutionArgParam("vpipelineunit") Vpipelineunit vpipelineunit, 
			@ExecutionArgParam("vpipelinesector") Vpipelinesector vpipelinesector, @ExecutionArgParam("vpipelineperiod") Vpipelineperiod vpipelineperiod) {
		Selectors.wireComponents(view, this, false);

		if (arg != null)
			this.arg = arg;
		this.vpipelineunit = vpipelineunit;
		this.vpipelinesector = vpipelinesector;
		this.vpipelineperiod = vpipelineperiod;
		
		if (vpipelineunit != null || vpipelinesector != null || vpipelineperiod != null)
			caption.setVisible(true);

		doReset();

		paging.addEventListener("onPaging", new EventListener<Event>() {

			public void onEvent(Event event) throws Exception {
				PagingEvent pe = (PagingEvent) event;
				pageStartNumber = pe.getActivePage();
				refreshModel(pageStartNumber);
			}
		});

		if (listbox != null) {
			listbox.setItemRenderer(new ListitemRenderer<Tpipeline>() {

				public void render(Listitem item, final Tpipeline data, int index) throws Exception {
					Listcell cell = new Listcell(String.valueOf((SysUtils.PAGESIZE * pageStartNumber) + index + 1));
					item.appendChild(cell);					
					cell = new Listcell(data.getMdebitur().getDebitur());
					item.appendChild(cell);					
					cell = new Listcell(data.getProject());
					item.appendChild(cell);
					cell = new Listcell(data.getMsector().getSectorname());
					item.appendChild(cell);
					cell = new Listcell(data.getMunit().getUnitname());
					item.appendChild(cell);
					cell = new Listcell(data.getProjectcurrency() + " " + decimalLocalFormatter.format(AppData.getAmountAfterDivide(data.getProjectamount())));
					item.appendChild(cell);
					cell = new Listcell(data.getCreditfacilitycurr() + " " + decimalLocalFormatter.format(AppData.getAmountAfterDivide(data.getCreditfacility())));
					item.appendChild(cell);
					cell = new Listcell(data.getSelfportioncurrency() + " " + decimalLocalFormatter.format(AppData.getAmountAfterDivide(data.getSelfportion())));
					item.appendChild(cell);					
					cell = new Listcell(AppData.getAgentType(data.getIsagentfac(), data.getIsagentcol(), data.getIsagentesc()) );
					item.appendChild(cell);					
					cell = new Listcell(data.getFeepercent() + " %");
					item.appendChild(cell);
					cell = new Listcell(data.getFeeamount() != null ? data.getFeecurrency() + " " + decimalLocalFormatter.format(AppData.getAmountAfterDivide(data.getFeeamount())) : "");
					item.appendChild(cell);
					cell = new Listcell(data.getYearperiod() + " " +  data.getQperiod());
					item.appendChild(cell);
					cell = new Listcell(AppData.getStatusPipelineLabel(data.getStatus()));
					item.appendChild(cell);
				}
			});
		}

		listbox.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

			public void onEvent(Event event) throws Exception {
				if (listbox.getSelectedIndex() != -1) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("obj", objForm);
					map.put("pageStartNumber", pageStartNumber);
					if (arg != null) {
						map.put("arg", arg);
					}

					Window win = (Window) Executions.createComponents("/view/pipeline.zul", null, map).getFirstChild();
					win.setClosable(true);
					win.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

						@SuppressWarnings("unchecked")
						public void onEvent(Event event) throws Exception {

							if (event.getData() != null) {
								Map<String, Object> map = (Map<String, Object>) event.getData();
								if (map.get("isSaved") != null) {
									needsPageUpdate = true;
									pageStartNumber = (Integer) map.get("pageStartNumber");
									refreshModel(pageStartNumber);
								}
							}
						}
					});
					win.doModal();
				}
			}
		});
	}

	@NotifyChange("pageTotalSize")
	public void refreshModel(int activePage) {
		orderby = "tpipelinepk desc";
		paging.setPageSize(SysUtils.PAGESIZE);
		model = new TpipelineListModel(activePage, SysUtils.PAGESIZE, filter, orderby);
		if (needsPageUpdate) {
			pageTotalSize = model.getTotalSize(filter);
			needsPageUpdate = false;
		}
		paging.setTotalSize(pageTotalSize);
		listbox.setModel(model);
	}
	
	@Command
	@NotifyChange("*")
	public void doSearch() {
		filter = "";
		if (vpipelineunit != null) {
			filter = "munitfk = " + vpipelineunit.getMunitpk();
		} else if (vpipelinesector != null) {
			filter = "msectorfk = " + vpipelinesector.getMsectorpk();
		} else if (vpipelineperiod != null) {
			filter = "munitfk = " + vpipelineperiod.getMunitpk() + " and yearperiod = " + vpipelineperiod.getYearperiod() + " and qperiod = '" + vpipelineperiod.getQperiod() + "'";
		} else {
			if (arg != null && arg.equals("APPROVAL"))
				filter += "status = '" + AppUtils.STATUS_PROGRESS + "'";
			if (regid != null && regid.trim().length() > 0) {
				if (filter.length() > 0)
					filter += " and ";
				filter += "regid like '%" + regid.trim().toUpperCase() + "%'";
			}
			
			if (debitur != null && debitur.trim().length() > 0) {
				if (filter.length() > 0)
					filter += " and ";
				filter += "debitur like '%" + debitur.trim().toUpperCase() + "%'";
			}
			if (msector != null) {
				if (filter.length() > 0)
					filter += " and ";
				filter += "msectorfk = " + msector.getMsectorpk();
			}
			if (munit != null) {
				if (filter.length() > 0)
					filter += " and ";
				filter += "munitfk = " + munit.getMunitpk();
			}			
			if (chkAgentFac.isChecked()) {
				if (filter.length() > 0)
					filter += " and ";
				filter += "tpipeline.isagentfac = 'Y'";
			}
			if (chkAgentCol.isChecked()) {
				if (filter.length() > 0)
					filter += " and ";
				filter += "tpipeline.isagentcol = 'Y'";
			}
			if (chkAgentEsc.isChecked()) {
				if (filter.length() > 0)
					filter += " and ";
				filter += "tpipeline.isagentesc = 'Y'";
			}
		}		

		needsPageUpdate = true;
		paging.setActivePage(0);
		pageStartNumber = 0;
		refreshModel(pageStartNumber);
	}

	@NotifyChange("*")
	@Command
	public void doReset() {		
		debitur = null;
		msector = null;
		munit = null;
		cbSector.setValue(null);
		cbUnit.setValue(null);
		chkAgentFac.setChecked(false);
		chkAgentCol.setChecked(false);
		chkAgentEsc.setChecked(false);
		doSearch();
	}
	
	@Command
	public void doExport() {
		try {
			List<Tpipeline> objList = new TpipelineDAO().listNativeByFilter(filter, orderby);
			if (objList.size() > 0) {
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet("List Data Pipeline");
				
				int rownum = 0;
				int cellnum = 0;
				Integer no = 0;

				org.apache.poi.ss.usermodel.Row row = sheet.createRow(rownum++);
				Cell cell = row.createCell(0);
				cell.setCellValue("List Data Pipeline");
				
				row = sheet.createRow(rownum++);
				
				Map<Integer, Object[]> datamap = new TreeMap<Integer, Object[]>();
				datamap.put(1,
						new Object[] { "No", "Debitur", "Project", "Sector", "Institusi", "Unit", "TFP Currency", "TFP", "Credit Facility Curr", "Credit Facility", 
								"Self Portion Currencry", "Self Portion", "Agent Code", "Fee Percent", "Fee Currency", "Fee Amount", "Period", "Target PK", "Credit Plan", "Status" });
				no = 2;
				for (Tpipeline data : objList) {

					datamap.put(no,
							new Object[] { no - 1, data.getMdebitur().getDebitur(), data.getProject(), data.getMsector().getSectorname(), data.getMunit().getIsbumn().equals("Y") ? "BUMN" : "Non BUMN", 
									data.getMunit().getUnitcode(), data.getProjectcurrency(), data.getProjectamount().doubleValue(), 
									data.getCreditfacilitycurr(), data.getCreditfacility().doubleValue(), 
									data.getSelfportioncurrency(), data.getSelfportion().doubleValue(),
									AppData.getAgentType(data.getIsagentfac(), data.getIsagentcol(), data.getIsagentesc()),
									data.getFeepercent() != null ? data.getFeepercent().doubleValue() : "", data.getFeecurrency(), 
									data.getFeeamount() != null ? data.getFeeamount().doubleValue() : 0,
									data.getYearperiod() + " " + data.getQperiod(), dateLocalFormatter.format(data.getTargetpk()), data.getCreditplan(), 
									AppData.getStatusPipelineLabel(data.getStatus()) });
					no++;
				}
				Set<Integer> keyset = datamap.keySet();
				for (Integer key : keyset) {
					row = sheet.createRow(rownum++);
					Object[] objArr = datamap.get(key);
					cellnum = 0;
					for (Object obj : objArr) {
						cell = row.createCell(cellnum++);
						if (obj instanceof String)
							cell.setCellValue((String) obj);
						else if (obj instanceof Integer)
							cell.setCellValue((Integer) obj);
						else if (obj instanceof Double)
							cell.setCellValue((Double) obj);
					}
				}
								
				String path = Executions.getCurrent().getDesktop().getWebApp()
						.getRealPath(AppUtils.FILES_ROOT_PATH + AppUtils.REPORT_PATH);
				String filename = "Pipeline_" + new SimpleDateFormat("yyMMddHHmm").format(new Date()) + ".xlsx";
				FileOutputStream out = new FileOutputStream(new File(path + "/" + filename));
				workbook.write(out);
				out.close();

				Filedownload.save(new File(path + "/" + filename),
						"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			} else {
				Messagebox.show("No Data", WebApps.getCurrent().getAppName() , Messagebox.OK,
						Messagebox.INFORMATION);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ListModelList<Mdebitur> getMdebiturModel() {
		ListModelList<Mdebitur> lm = null;
		try {
			lm = new ListModelList<Mdebitur>(new MdebiturDAO().listByFilter("0=0", "debitur"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lm;
	}

	public ListModelList<Msector> getMsectorModel() {
		ListModelList<Msector> lm = null;
		try {
			lm = new ListModelList<Msector>(new MsectorDAO().listByFilter("0=0", "msectorpk"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lm;
	}
	
	public ListModelList<Munit> getMunitModel() {
		ListModelList<Munit> lm = null;
		try {
			lm = new ListModelList<Munit>(new MunitDAO().listByFilter("0=0", "unitcode"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lm;
	}

	public Tpipeline getObjForm() {
		return objForm;
	}

	public void setObjForm(Tpipeline objForm) {
		this.objForm = objForm;
	}

	public String getRegid() {
		return regid;
	}

	public void setRegid(String regid) {
		this.regid = regid;
	}

	public int getPageTotalSize() {
		return pageTotalSize;
	}

	public void setPageTotalSize(int pageTotalSize) {
		this.pageTotalSize = pageTotalSize;
	}

	public String getDebitur() {
		return debitur;
	}

	public void setDebitur(String debitur) {
		this.debitur = debitur;
	}

	public Msector getMsector() {
		return msector;
	}

	public void setMsector(Msector msector) {
		this.msector = msector;
	}

	public Munit getMunit() {
		return munit;
	}

	public void setMunit(Munit munit) {
		this.munit = munit;
	}

}
