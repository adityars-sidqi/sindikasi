package com.sdd.sindikasi.viewmodel;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Paging;
import org.zkoss.zul.event.PagingEvent;

import com.sdd.sindikasi.dao.TportoDAO;
import com.sdd.sindikasi.domain.Tporto;
import com.sdd.sindikasi.domain.Muser;
import com.sdd.sindikasi.domain.Tporto;
import com.sdd.sindikasi.model.TportoListModel;
import com.sdd.sindikasi.utils.AppData;
import com.sdd.sindikasi.model.TportoListModel;
import com.sdd.utils.SysUtils;

public class PortofolioListVm {
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;

	private SimpleDateFormat dateLocalFormatter = new SimpleDateFormat("dd-MM-yyyy");
	private SimpleDateFormat datetimeLocalFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private SimpleDateFormat monthFormatter = new SimpleDateFormat("MMM");
	private DecimalFormat decimalLocalFormatter = new DecimalFormat("#,###");

	private Tporto objForm;

	private TportoListModel model;

	private int pageStartNumber;
	private int pageTotalSize;
	private boolean needsPageUpdate;
	private String filter;
	private String orderby;

	private String regid;

	@Wire
	private Paging paging;
	@Wire
	private Listbox listbox;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
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

		if (listbox != null) {
			listbox.setItemRenderer(new ListitemRenderer<Tporto>() {

				public void render(Listitem item, final Tporto data, int index) throws Exception {
					Listcell cell = new Listcell(String.valueOf((SysUtils.PAGESIZE * pageStartNumber) + index + 1));
					item.appendChild(cell);
					cell = new Listcell(data.getRegid());
					item.appendChild(cell);
					cell = new Listcell(datetimeLocalFormatter.format(data.getRegtime()));
					item.appendChild(cell);
					cell = new Listcell(data.getDebitur());
					item.appendChild(cell);
					cell = new Listcell(data.getMsector().getSectorname());
					item.appendChild(cell);
					cell = new Listcell(data.getProject());
					item.appendChild(cell);
					cell = new Listcell(data.getMunit().getUnitname());
					item.appendChild(cell);
					cell = new Listcell(data.getCurrency());
					item.appendChild(cell);
					cell = new Listcell(decimalLocalFormatter.format(data.getProjectamount()));
					item.appendChild(cell);
					cell = new Listcell(decimalLocalFormatter.format(data.getSelfportion()));
					item.appendChild(cell);
					cell = new Listcell(decimalLocalFormatter.format(data.getFeepercent()));
					item.appendChild(cell);
					cell = new Listcell(monthFormatter.format(data.getTargetpk()));
					item.appendChild(cell);
				}
			});
		}

		listbox.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

			public void onEvent(Event event) throws Exception {
				if (listbox.getSelectedIndex() != -1) {

				}
			}
		});
	}

	@NotifyChange("pageTotalSize")
	public void refreshModel(int activePage) {
		orderby = "regid";
		paging.setPageSize(SysUtils.PAGESIZE);
		model = new TportoListModel(activePage, SysUtils.PAGESIZE, filter, orderby);
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
		if (regid != null && regid.trim().length() > 0) {
			if (filter.length() > 0)
				filter += " and ";
			filter += "regid like '%" + regid.trim().toUpperCase() + "%'";
		}

		needsPageUpdate = true;
		paging.setActivePage(0);
		pageStartNumber = 0;
		refreshModel(pageStartNumber);
	}

	@NotifyChange("*")
	public void doReset() {

		doSearch();

		objForm = new Tporto();

	}

	public Tporto getObjForm() {
		return objForm;
	}

	public void setObjForm(Tporto objForm) {
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

}
