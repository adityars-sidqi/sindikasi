package com.sdd.sindikasi.viewmodel;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WebApps;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.A;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

import com.sdd.sindikasi.domain.Tpipeline;
import com.sdd.sindikasi.domain.Tportopayschedule;
import com.sdd.sindikasi.domain.Muser;
import com.sdd.sindikasi.model.TpipelineListModel;
import com.sdd.sindikasi.model.TportopayscheduleListModel;
import com.sdd.sindikasi.utils.AppData;
import com.sdd.utils.SysUtils;

public class PayscheduleVm {
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;

	private SimpleDateFormat dateLocalFormatter = new SimpleDateFormat("dd-MM-yyyy");
	private SimpleDateFormat datetimeLocalFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private SimpleDateFormat monthFormatter = new SimpleDateFormat("MMM");
	private DecimalFormat decimalLocalFormatter = new DecimalFormat("#,###");
	
	private TportopayscheduleListModel model;

	private int pageStartNumber;
	private int pageTotalSize;
	private boolean needsPageUpdate;
	private String filter;
	private String orderby;
	
	private Integer totalselected;
	private Map<Integer, Tportopayschedule> mapData;

	@Wire
	private Paging paging;
	@Wire
	private Grid grid;
	@Wire
	private Checkbox chkAll;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("arg") final String arg) {
		Selectors.wireComponents(view, this, false);

		oUser = (Muser) zkSession.getAttribute("oUser");

		doReset();

		paging.addEventListener("onPaging", new EventListener<Event>() {

			public void onEvent(Event event) throws Exception {
				PagingEvent pe = (PagingEvent) event;
				pageStartNumber = pe.getActivePage();
				refreshModel(pageStartNumber);
			}
		});

		grid.setRowRenderer(new RowRenderer<Tportopayschedule>() {

			public void render(Row row, Tportopayschedule data, int index) throws Exception {
				row.getChildren().add(new Label(
						String.valueOf((SysUtils.PAGESIZE * pageStartNumber) + index + 1)));
				Checkbox check = new Checkbox();
				check.setAttribute("obj", data);
				check.addEventListener(Events.ON_CHECK, new EventListener<Event>() {

					public void onEvent(Event event) throws Exception {
						Checkbox checked = (Checkbox) event
								.getTarget();
						Tportopayschedule obj = (Tportopayschedule) checked.getAttribute("obj");
						if (checked.isChecked()) {
							mapData.put(obj.getTportopayschedulepk(), obj);
						} else {
							mapData.remove(obj.getTportopayschedulepk());
						}
						totalselected = mapData.size();
						BindUtils.postNotifyChange(null, null, PayscheduleVm.this, "totalselected");	
					}
				});
				
				if (mapData.get(data.getTportopayschedulepk()) != null)
					check.setChecked(true);
				row.getChildren().add(check);
				
				A a = new A(data.getTporto().getProject());
				a.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					public void onEvent(Event event) throws Exception {
						
					}
				});				
				row.getChildren().add(a);
				row.getChildren().add(new Label(data.getTporto().getDebitur()));
				row.getChildren().add(new Label(data.getTporto().getMsector().getSectorname()));
				row.getChildren().add(new Label(data.getTporto().getCurrency()));
				row.getChildren().add(new Label(decimalLocalFormatter.format(data.getTporto().getProjectamount())));
				row.getChildren().add(new Label(decimalLocalFormatter.format(data.getTporto().getSelfportion())));
				row.getChildren().add(new Label(decimalLocalFormatter.format(data.getTporto().getFeepercent())));				
				row.getChildren().add(new Label(AppData.getPaytypeLabel(data.getPaytype())));
				row.getChildren().add(new Label(AppData.getMonthPaySchedule(data.getMonthschedule())));
			}
		});
		
		
	}
	
	@Command
	@NotifyChange("totalselected")
	public void doCheckedall(@BindingParam("checked") Boolean checked) {
		try {
			List<Row> components = grid.getRows().getChildren();
			for (Row comp : components) {
				Checkbox chk = (Checkbox) comp.getChildren().get(1);
				Tportopayschedule obj = (Tportopayschedule) chk.getAttribute("obj");
				if (checked) {
					if (!chk.isChecked()) {
						chk.setChecked(true);
						mapData.put(obj.getTportopayschedulepk(), obj);						
					}								
				} else {
					if (chk.isChecked()) {
						chk.setChecked(false);
						mapData.remove(obj.getTportopayschedulepk());
					}					
				}
			}
			totalselected = mapData.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@NotifyChange("pageTotalSize")
	public void refreshModel(int activePage) {
		orderby = "tporto.debitur, tporto.project, paytype";
		paging.setPageSize(SysUtils.PAGESIZE);
		model = new TportopayscheduleListModel(activePage, SysUtils.PAGESIZE, filter, orderby);
		if (needsPageUpdate) {
			pageTotalSize = model.getTotalSize(filter);
			needsPageUpdate = false;
		}
		paging.setTotalSize(pageTotalSize);
		grid.setModel(model);
	}

	@Command
	@NotifyChange("*")
	public void doSearch() {
		filter = "";
		
		needsPageUpdate = true;
		paging.setActivePage(0);
		pageStartNumber = 0;
		refreshModel(pageStartNumber);
	}

	@NotifyChange("*")
	public void doReset() {
		mapData = new HashMap<Integer, Tportopayschedule>();
		doSearch();
	}
	
	@Command
	public void doInvoice() {
		if (mapData.isEmpty()) {
			Messagebox.show("Data is empty. Please choose data.", WebApps.getCurrent().getAppName(), Messagebox.OK, Messagebox.INFORMATION);
		} else {
			List<Tportopayschedule> objList = new ArrayList<Tportopayschedule>();
			for (Entry<Integer, Tportopayschedule> entry: mapData.entrySet()) {
				Tportopayschedule data = entry.getValue();
				objList.add(data);
			}
			Collections.sort(objList, Tportopayschedule.payScheduleComparator1);
			Collections.sort(objList, Tportopayschedule.payScheduleComparator2);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("objList", objList);
			
			Window win = (Window) Executions.createComponents("/view/invoiceform.zul", null, map);
			win.setClosable(true);
			win.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

				public void onEvent(Event event) throws Exception {

				}
			});
			win.doModal();
		}		
	}

	public int getPageTotalSize() {
		return pageTotalSize;
	}

	public void setPageTotalSize(int pageTotalSize) {
		this.pageTotalSize = pageTotalSize;
	}

	public Integer getTotalselected() {
		return totalselected;
	}

	public void setTotalselected(Integer totalselected) {
		this.totalselected = totalselected;
	}

}
