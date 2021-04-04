package com.sdd.sindikasi.viewmodel;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.event.PagingEvent;

import com.sdd.sindikasi.domain.Tpipeline;
import com.sdd.sindikasi.domain.Tpipelinepart;
import com.sdd.sindikasi.domain.Vpipelineperiod;
import com.sdd.sindikasi.domain.Vpipelinesector;
import com.sdd.sindikasi.domain.Vpipelineunit;
import com.sdd.sindikasi.dao.MdebiturDAO;
import com.sdd.sindikasi.dao.MsectorDAO;
import com.sdd.sindikasi.dao.MunitDAO;
import com.sdd.sindikasi.dao.TmemoDAO;
import com.sdd.sindikasi.dao.TpipelineDAO;
import com.sdd.sindikasi.dao.TpipelinepartDAO;
import com.sdd.sindikasi.domain.Mdebitur;
import com.sdd.sindikasi.domain.Msector;
import com.sdd.sindikasi.domain.Munit;
import com.sdd.sindikasi.domain.Tmemo;
import com.sdd.sindikasi.model.TpipelineListModel;
import com.sdd.sindikasi.utils.AppUtils;
import com.sdd.utils.SysUtils;

public class PipelineActivityVm {

	private DecimalFormat decimalLocalFormatter = new DecimalFormat("#,###");

	private Tpipeline objForm;

	private TpipelineListModel model;
	private List<Tpipelinepart> listPart;
	private Map<Integer, List<Tpipelinepart>> mapListPart;
	private List<Tmemo> listMemo;
	private Map<Integer, List<Tmemo>> mapListMemo;

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
	
	private SimpleDateFormat dateLocalFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	@Wire
	private Caption caption;
	@Wire
	private Combobox cbSector;
	@Wire
	private Combobox cbUnit;
	@Wire
	private Paging paging;
	@Wire
	private Grid grid;

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
		
		grid.setRowRenderer(new RowRenderer<Tpipeline>() {

			public void render(Row row, Tpipeline data, int index) throws Exception {
				row.getChildren().add(new Label(String.valueOf((SysUtils.PAGESIZE * pageStartNumber) + index + 1)));
				
				Div divData = new Div();
				Label lbl = new Label(data.getMdebitur().getDebitur());
				lbl.setStyle("font-size: 14px; font-weight: bold");
				divData.appendChild(lbl);
				
				Div divRow = new Div();
				divRow.setClass("row");
								
				divRow = new Div();
				divRow.setClass("row");
				Div divCol1 = new Div();
				divCol1.setClass("col");
				Div divCol2 = new Div();
				divCol2.setClass("col");
				divCol1.appendChild(new Label("Project"));				
				divCol2.appendChild(new Label(data.getProject()));
				divRow.appendChild(divCol1);
				divRow.appendChild(divCol2);
				divData.appendChild(divRow);
				
				divRow = new Div();
				divRow.setClass("row");
				divCol1 = new Div();
				divCol1.setClass("col");
				divCol2 = new Div();
				divCol2.setClass("col");
				divCol1.appendChild(new Label("Sector"));
				divCol2.appendChild(new Label(data.getMsector().getSectorname()));
				divRow.appendChild(divCol1);
				divRow.appendChild(divCol2);
				divData.appendChild(divRow);
				
				divRow = new Div();
				divRow.setClass("row");
				divCol1 = new Div();
				divCol1.setClass("col");
				divCol2 = new Div();
				divCol2.setClass("col");
				divCol1.appendChild(new Label("Project Amount"));
				divCol2.appendChild(new Label(data.getProjectcurrency() + " " + decimalLocalFormatter.format(data.getProjectamount())));
				divRow.appendChild(divCol1);
				divRow.appendChild(divCol2);
				divData.appendChild(divRow);
				
				divRow = new Div();
				divRow.setClass("row");
				divCol1 = new Div();
				divCol1.setClass("col");
				divCol2 = new Div();
				divCol2.setClass("col");
				divCol1.appendChild(new Label("Credit Facility"));
				divCol2.appendChild(new Label(data.getCreditfacilitycurr() + " " + decimalLocalFormatter.format(data.getCreditfacility())));
				divRow.appendChild(divCol1);
				divRow.appendChild(divCol2);
				divData.appendChild(divRow);
				
				divRow = new Div();
				divRow.setClass("row");
				divCol1 = new Div();
				divCol1.setClass("col");
				divCol2 = new Div();
				divCol2.setClass("col");
				divCol1.appendChild(new Label("Self Portion"));
				divCol2.appendChild(new Label(data.getSelfportioncurrency() + " " + decimalLocalFormatter.format(data.getSelfportion())));
				divRow.appendChild(divCol1);
				divRow.appendChild(divCol2);
				divData.appendChild(divRow);
				
				divRow = new Div();
				divRow.setClass("row");
				divCol1 = new Div();
				divCol1.setClass("col");
				divCol2 = new Div();
				divCol2.setClass("col");
				divCol1.appendChild(new Label("Credit Plan"));
				divCol2.appendChild(new Label(data.getCreditplan() + " Month"));
				divRow.appendChild(divCol1);
				divRow.appendChild(divCol2);
				divData.appendChild(divRow);
				
				String agentfac = "";
				if (data.getIsagentfac().equals("Y"))
					agentfac = "BNI";
				String agentcol = "";
				if (data.getIsagentcol().equals("Y"))
					agentcol = "BNI";
				String agentesc = "";
				if (data.getIsagentesc().equals("Y"))
					agentesc = "BNI";
				
				List<Tpipelinepart> parts = mapListPart.get(data.getTpipelinepk());
				if (parts != null) {
					for (Tpipelinepart part: parts) {
						if (part.getIsagentfac().equals("Y")) {
							if (agentfac.length() > 0)
								agentfac += ", ";
							agentfac += part.getParticipantname();
						}
						if (part.getIsagentcol().equals("Y")) {
							if (agentcol.length() > 0)
								agentcol += ", ";
							agentcol += part.getParticipantname();
						}
						if (part.getIsagentesc().equals("Y")) {
							if (agentesc.length() > 0)
								agentesc += ", ";
							agentesc += part.getParticipantname();
						}
					}
				}
				
				
				divRow = new Div();
				divRow.setClass("row");
				divCol1 = new Div();
				divCol1.setClass("col");
				divCol2 = new Div();
				divCol2.setClass("col");
				divCol1.appendChild(new Label("Agent Facility"));
				divCol2.appendChild(new Label(agentfac));
				divRow.appendChild(divCol1);
				divRow.appendChild(divCol2);
				divData.appendChild(divRow);
								
				
				divRow = new Div();
				divRow.setClass("row");
				divCol1 = new Div();
				divCol1.setClass("col");
				divCol2 = new Div();
				divCol2.setClass("col");
				divCol1.appendChild(new Label("Agent Collateral"));
				divCol2.appendChild(new Label(agentcol));
				divRow.appendChild(divCol1);
				divRow.appendChild(divCol2);
				divData.appendChild(divRow);
				
				
				divRow = new Div();
				divRow.setClass("row");
				divCol1 = new Div();
				divCol1.setClass("col");
				divCol2 = new Div();
				divCol2.setClass("col");
				divCol1.appendChild(new Label("Agent Escrow"));
				divCol2.appendChild(new Label(agentesc));
				divRow.appendChild(divCol1);
				divRow.appendChild(divCol2);
				divData.appendChild(divRow);
				
				divRow = new Div();
				divRow.setClass("row");
				divCol1 = new Div();
				divCol1.setClass("col");
				divCol2 = new Div();
				divCol2.setClass("col");
				divCol1.appendChild(new Label("Fee"));
				divCol2.appendChild(new Label(data.getFeepercent() + "%"));
				divRow.appendChild(divCol1);
				divRow.appendChild(divCol2);
				divData.appendChild(divRow);
				
				row.getChildren().add(divData);
				
				List<Tmemo> memos = mapListMemo.get(data.getTpipelinepk());
				if (memos != null) {
					Div divMemo = new Div();
					for (Tmemo memo: memos) {
						Div divRowMemo = new Div();
						Div divColMemo = new Div();
						
						divRowMemo.setClass("row");		
						divColMemo.setClass("col");
						divColMemo.appendChild(new Label(dateLocalFormatter.format(memo.getCreatedtime()) + " " + memo.getMemo()));
						divRowMemo.appendChild(divColMemo);
						divMemo.appendChild(divRowMemo);
					}
					row.getChildren().add(divMemo);
				} else {
					row.getChildren().add(new Label());
				}
				
				row.getChildren().add(new Label(data.getQperiod() + " " + data.getYearperiod()));
			}
		});
		
	}

	@NotifyChange("pageTotalSize")
	public void refreshModel(int activePage) {
		orderby = "yearperiod, qperiod";
		paging.setPageSize(SysUtils.PAGESIZE);
		model = new TpipelineListModel(activePage, SysUtils.PAGESIZE, filter, orderby);
		if (needsPageUpdate) {
			pageTotalSize = model.getTotalSize(filter);
			needsPageUpdate = false;
		}
		paging.setTotalSize(pageTotalSize);
		grid.setModel(model);
	}
	
	private void doGetParts() {
		try {
			List<Tpipelinepart> objList;
			mapListPart = new HashMap<Integer, List<Tpipelinepart>>();
			listPart = new TpipelinepartDAO().listNativeByFilter(filter, "tpipelinepartpk");
			for (Tpipelinepart obj: listPart) {
				objList = mapListPart.get(obj.getTpipeline().getTpipelinepk());
				if (objList == null) {
					objList = new ArrayList<Tpipelinepart>();
				}
				objList.add(obj);				
				mapListPart.put(obj.getTpipeline().getTpipelinepk(), objList);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void doGetMemos() {
		try {
			List<Tmemo> objList;
			mapListMemo = new HashMap<Integer, List<Tmemo>>();
			listMemo = new TmemoDAO().listNativeByFilter(filter, "tmemopk");
			for (Tmemo obj: listMemo) {
				objList = mapListMemo.get(obj.getTpipeline().getTpipelinepk());
				if (objList == null) {
					objList = new ArrayList<Tmemo>();
				}
				objList.add(obj);				
				mapListMemo.put(obj.getTpipeline().getTpipelinepk(), objList);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Command
	@NotifyChange("*")
	public void doSearch() {
		filter = "";
		if (vpipelineunit != null) {
			filter = "munitfk = " + vpipelineunit.getMunitpk();
		} else if (vpipelinesector != null) {
			filter = "msectorfk = " + vpipelinesector.getMsectorpk();
		} else if (vpipelineunit != null) {
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
		}	
		
		doGetParts();
		doGetMemos();
		
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
		doSearch();
	}
	
	@Command
	public void doExport() {
		try {
			List<Tpipeline> objList = new TpipelineDAO().listNativeByFilter(filter, orderby);
			if (objList.size() > 0) {
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet("Pipeline Activity");
				
				int rownum = 0;
				int cellnum = 0;
				Integer no = 0;

				org.apache.poi.ss.usermodel.Row row = sheet.createRow(rownum++);
				Cell cell = row.createCell(0);
				cell.setCellValue("Pipeline Activity");
				
				row = sheet.createRow(rownum++);
				cell = row.createCell(0);
				cell.setCellValue("No");
				cell = row.createCell(1);
				cell.setCellValue("Informasi Awal");
				cell = row.createCell(2);
				cell.setCellValue("Update");
				cell = row.createCell(3);
				cell.setCellValue("Target");
				
				
								
				String path = Executions.getCurrent().getDesktop().getWebApp()
						.getRealPath(AppUtils.FILES_ROOT_PATH + AppUtils.REPORT_PATH);
				String filename = "PipelineActivity_" + new SimpleDateFormat("yyMMddHHmm").format(new Date()) + ".xlsx";
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
