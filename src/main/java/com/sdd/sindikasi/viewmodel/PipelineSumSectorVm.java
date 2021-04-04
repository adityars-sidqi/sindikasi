package com.sdd.sindikasi.viewmodel;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
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
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

import com.sdd.sindikasi.dao.TpipelineDAO;
import com.sdd.sindikasi.domain.Vpipelinesector;
import com.sdd.sindikasi.utils.AppData;
import com.sdd.sindikasi.utils.AppUtils;

public class PipelineSumSectorVm {
	
	private Vpipelinesector objForm;
	private List<Vpipelinesector> objList;
	private Integer tdebitur;
	private BigDecimal tpc;
	private BigDecimal tcredit;
	private BigDecimal tsp;
	private BigDecimal tfee;
	private DecimalFormat decimalLocalFormatter = new DecimalFormat("#,###");
	
	@Wire
	private Listbox listbox;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		
		listbox.setItemRenderer(new ListitemRenderer<Vpipelinesector>() {

			public void render(Listitem item, final Vpipelinesector data, int index) throws Exception {
				Listcell cell = new Listcell(String.valueOf(index + 1));
				item.appendChild(cell);					
				cell = new Listcell(data.getSectorname());
				item.appendChild(cell);
				cell = new Listcell(data.getTdebitur() != null ? decimalLocalFormatter.format(data.getTdebitur()) : "0");
				item.appendChild(cell);
				cell = new Listcell(data.getTpc() != null ? decimalLocalFormatter.format(AppData.getAmountAfterDivide(data.getTpc())) : "0");
				item.appendChild(cell);
				cell = new Listcell(data.getTcredit() != null ? decimalLocalFormatter.format(AppData.getAmountAfterDivide(data.getTcredit())) : "0");
				item.appendChild(cell);
				cell = new Listcell(data.getTsp() != null ? decimalLocalFormatter.format(AppData.getAmountAfterDivide(data.getTsp())) : "0");
				item.appendChild(cell);
				cell = new Listcell(data.getTfee() != null ? decimalLocalFormatter.format(AppData.getAmountAfterDivide(data.getTfee())) : "0");
				item.appendChild(cell);
				
				tdebitur += data.getTdebitur() != null ? data.getTdebitur() : 0;
				tpc = tpc.add(data.getTpc() != null ? AppData.getAmountAfterDivide(data.getTpc()) : new BigDecimal(0));
				tcredit = tcredit.add(data.getTcredit() != null ? AppData.getAmountAfterDivide(data.getTcredit()) : new BigDecimal(0));
				tsp = tsp.add(data.getTsp() != null ? AppData.getAmountAfterDivide(data.getTsp()) : new BigDecimal(0));
				tfee = tfee.add(data.getTfee() != null ? AppData.getAmountAfterDivide(data.getTfee()) : new BigDecimal(0));
				
				BindUtils.postNotifyChange(null, null, PipelineSumSectorVm.this, "*");
			}
		});
		
		listbox.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

			public void onEvent(Event event) throws Exception {
				if (listbox.getSelectedIndex() != -1) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("vpipelinesector", objForm);
					
					Window win = (Window) Executions.createComponents("/view/pipelinelist.zul", null, map);
					win.setClosable(true);		
					win.setWidth("97%");
					win.doModal();
				}
			}
		});
		
		doReset();
	}
	
	public void doRefreshModel() {		
		try {
			objList = new TpipelineDAO().getSumBySector("0=0");
			listbox.setModel(new ListModelList<Vpipelinesector>(objList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void doReset() {
		tdebitur = 0;
		tpc = new BigDecimal(0);
		tcredit = new BigDecimal(0);
		tsp = new BigDecimal(0);
		tfee = new BigDecimal(0);
		doRefreshModel();
	}
	
	@Command
	public void doExport() {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Summary Data Pipeline By Sector");
			
			int rownum = 0;
			int cellnum = 0;
			Integer no = 0;

			org.apache.poi.ss.usermodel.Row row = sheet.createRow(rownum++);
			Cell cell = row.createCell(0);
			cell.setCellValue("Summary Data Pipeline By Sector");
			
			row = sheet.createRow(rownum++);

			Map<Integer, Object[]> datamap = new TreeMap<Integer, Object[]>();
			datamap.put(1,
					new Object[] { "No", "Sector", "Total Debitur", "TFP", "Total Credit", "Total Self Portion", "Total Fee" });
			no = 2;
			for (Vpipelinesector data : objList) {

				datamap.put(no,
						new Object[] { no - 1, data.getSectorname(),
								data.getTdebitur(), data.getTpc() != null ? AppData.getAmountAfterDivide(data.getTpc()).doubleValue() : 0, 
								data.getTcredit() != null ? AppData.getAmountAfterDivide(data.getTcredit()).doubleValue() : 0, 
								data.getTsp() != null ? AppData.getAmountAfterDivide(data.getTsp()).doubleValue() : 0,
								data.getTfee() != null ? AppData.getAmountAfterDivide(data.getTfee()).doubleValue() : 0 });
				no++;
			}
			datamap.put(no,
					new Object[] { "", "TOTAL", 
							tdebitur, tpc != null ? tpc.doubleValue() : 0, 
							tcredit != null ? tcredit.doubleValue() : 0, 
							tsp != null ? AppData.getAmountAfterDivide(tsp).doubleValue() : 0,
							tfee != null ? AppData.getAmountAfterDivide(tfee).doubleValue() : 0 });
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
			
			row = sheet.createRow(rownum++);
			cell = row.createCell(0);
			cell.setCellValue("* Dalam Rupiah Dan Satuan Juta.");

			String path = Executions.getCurrent().getDesktop().getWebApp()
					.getRealPath(AppUtils.FILES_ROOT_PATH + AppUtils.REPORT_PATH);
			String filename = "PipelineBySector_" + new SimpleDateFormat("yyMMddHHmm").format(new Date()) + ".xlsx";
			FileOutputStream out = new FileOutputStream(new File(path + "/" + filename));
			workbook.write(out);
			out.close();

			Filedownload.save(new File(path + "/" + filename),
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Vpipelinesector getObjForm() {
		return objForm;
	}

	public void setObjForm(Vpipelinesector objForm) {
		this.objForm = objForm;
	}

	public Integer getTdebitur() {
		return tdebitur;
	}

	public void setTdebitur(Integer tdebitur) {
		this.tdebitur = tdebitur;
	}

	public BigDecimal getTpc() {
		return tpc;
	}

	public void setTpc(BigDecimal tpc) {
		this.tpc = tpc;
	}

	public BigDecimal getTsp() {
		return tsp;
	}

	public void setTsp(BigDecimal tsp) {
		this.tsp = tsp;
	}

	public BigDecimal getTfee() {
		return tfee;
	}

	public void setTfee(BigDecimal tfee) {
		this.tfee = tfee;
	}

	public BigDecimal getTcredit() {
		return tcredit;
	}

	public void setTcredit(BigDecimal tcredit) {
		this.tcredit = tcredit;
	}
	
}
