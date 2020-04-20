package com.sdd.sindikasi.viewmodel;

import com.sdd.sindikasi.dao.MsectorDAO;
import com.sdd.sindikasi.dao.MunitDAO;
import com.sdd.sindikasi.dao.TportoDAO;
import com.sdd.sindikasi.domain.Msector;
import com.sdd.sindikasi.domain.Munit;
import com.sdd.sindikasi.domain.Muser;
import com.sdd.sindikasi.domain.Tporto;
import com.sdd.sindikasi.utils.AppUtils;
import com.sdd.utils.SysUtils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zkoss.bind.annotation.*;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.event.PagingEvent;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ReportPortoVm {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;

	private SimpleDateFormat dateLocalFormatter = new SimpleDateFormat("dd-MM-yyyy");
	private SimpleDateFormat datetimeLocalFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private SimpleDateFormat monthFormatter = new SimpleDateFormat("MMM");
	private DecimalFormat decimalLocalFormatter = new DecimalFormat("#,###");

	private TportoDAO oDao = new TportoDAO();

	private String filter;
	private String orderby;

	private Msector msector;
	private Munit munit;
	private Integer month;

	@Wire
	private Combobox cbMonth;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);

		oUser = (Muser) zkSession.getAttribute("oUser");

		doReset();
		String[] months = new DateFormatSymbols().getMonths();
		for (int i = 0; i < months.length; i++) {
			Comboitem item = new Comboitem();
			item.setLabel(months[i]);
			item.setValue(i + 1);
			cbMonth.appendChild(item);
		}
	}

	@Command
	@NotifyChange("*")
	public void doExport() {
		try {
			
			filter = "";
			
			if (msector != null) {
				if (filter.length() > 0)
					filter += " and ";
				filter += "msector.msectorpk = " + msector.getMsectorpk();
			}

			if (munit != null) {
				if (filter.length() > 0)
					filter += " and ";
				filter += "munit.munitpk = " + munit.getMunitpk();
			}

			if (month != null) {
				if (filter.length() > 0)
					filter += " and ";
				filter += "date_part('month',regtime) = " + month;
			}

			orderby = "regid";

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("List Report Portofolio");
			List<Tporto> objList = oDao.listByFilter(filter, orderby);

			int rownum = 0;
			int cellnum = 0;
			Integer no = 0;

			org.apache.poi.ss.usermodel.Row row = sheet.createRow(rownum++);
			Cell cell = row.createCell(0);
			cell.setCellValue("List Report Portofolio");

			row = sheet.createRow(rownum++);

			Map<Integer, Object[]> datamap = new TreeMap<Integer, Object[]>();
			datamap.put(1,
					new Object[] { "No", Labels.getLabel("porto.regid"), Labels.getLabel("porto.regtime"),
							Labels.getLabel("porto.debitur"), Labels.getLabel("porto.officephone"),
							Labels.getLabel("porto.officeemail"), Labels.getLabel("porto.dirname"),
							Labels.getLabel("porto.dirphone"), Labels.getLabel("porto.project"),
							Labels.getLabel("porto.rmgroupcode"), Labels.getLabel("porto.rmid"),
							Labels.getLabel("porto.rmname"), Labels.getLabel("porto.rmcredit"),
							Labels.getLabel("porto.currency"), Labels.getLabel("porto.projectamount"),
							Labels.getLabel("porto.creditfacility"), Labels.getLabel("porto.selfportion"),
							Labels.getLabel("porto.feepercent"), Labels.getLabel("porto.feeamount"),
							Labels.getLabel("porto.targetpk"), Labels.getLabel("porto.portodate") });
			no = 2;
			for (Tporto data : objList) {

				datamap.put(no,
						new Object[] { no - 1, data.getRegid(), datetimeLocalFormatter.format(data.getRegtime()),
								data.getDebitur(), data.getOfficephone(), data.getOfficeemail(), data.getDirname(),
								data.getDirphone(), data.getProject(), data.getRmgroupcode(), data.getRmid(),
								data.getRmname(), data.getRmcredit(), data.getCurrency(),
								decimalLocalFormatter.format(data.getProjectamount()),
								decimalLocalFormatter.format(data.getCreditfacility()),
								decimalLocalFormatter.format(data.getSelfportion()),
								decimalLocalFormatter.format(data.getFeepercent()),
								decimalLocalFormatter.format(data.getFeeamount()),
								dateLocalFormatter.format(data.getTargetpk()),
								dateLocalFormatter.format(data.getPortodate()) });
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
			String filename = "REPORTPORTOFOLIO_" + new SimpleDateFormat("yyMMddHHmm").format(new Date()) + ".xlsx";
			FileOutputStream out = new FileOutputStream(new File(path + "/" + filename));
			workbook.write(out);
			out.close();

			Filedownload.save(new File(path + "/" + filename),
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange("*")
	public void doReset() {
		msector = null;
		munit = null;
		cbMonth.setValue(null);
	}

	public ListModelList<Msector> getMsectors() {
		ListModelList<Msector> lm = null;
		try {
			lm = new ListModelList<Msector>(new MsectorDAO().listByFilter("0=0", "sectorname"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lm;
	}

	public ListModelList<Munit> getMunits() {
		ListModelList<Munit> lm = null;
		try {
			lm = new ListModelList<Munit>(new MunitDAO().listByFilter("0=0", "unitcode"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lm;
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

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

}
