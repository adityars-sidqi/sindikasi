package com.sdd.sindikasi.viewmodel;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.event.PagingEvent;

import com.sdd.sindikasi.dao.MrmDAO;
import com.sdd.sindikasi.dao.MrmgroupDAO;
import com.sdd.sindikasi.dao.MusergroupDAO;
import com.sdd.sindikasi.domain.Mrm;
import com.sdd.sindikasi.domain.Mrmgroup;
import com.sdd.sindikasi.domain.Muser;
import com.sdd.sindikasi.domain.Musergroup;
import com.sdd.sindikasi.model.MrmListModel;
import com.sdd.sindikasi.utils.AppData;
import com.sdd.utils.SysUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class MrmVm {
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();

	private SimpleDateFormat datetimeLocalFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	private Session session;
	private Transaction transaction;

	private Muser oUser;

	private MrmListModel model;
	private MrmDAO oDao = new MrmDAO();

	private int pageStartNumber;
	private int pageTotalSize;
	private boolean needsPageUpdate;
	private String filter;
	private String orderby;
	private boolean isInsert;

	private Mrm objForm;

	private String rmid;

	@Wire
	private Combobox cbRmgroup;

	@Wire
	private Button btnSave;
	@Wire
	private Button btnCancel;
	@Wire
	private Button btnDelete;
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
			listbox.setItemRenderer(new ListitemRenderer<Mrm>() {

				public void render(Listitem item, final Mrm data, int index) throws Exception {
					Listcell cell = new Listcell(String.valueOf((SysUtils.PAGESIZE * pageStartNumber) + index + 1));
					item.appendChild(cell);
					cell = new Listcell(data.getRmid());
					item.appendChild(cell);
					cell = new Listcell(data.getRmname());
					item.appendChild(cell);
					cell = new Listcell(data.getMrmgroup().getRmgroupname());
					item.appendChild(cell);
					cell = new Listcell(data.getUpdatedby());
					item.appendChild(cell);
					cell = new Listcell(datetimeLocalFormatter.format(data.getLastupdated()));
					item.appendChild(cell);
				}
			});
		}

		listbox.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

			public void onEvent(Event event) throws Exception {
				if (listbox.getSelectedIndex() != -1) {

					isInsert = false;
					btnSave.setLabel(Labels.getLabel("common.update"));
					btnCancel.setDisabled(false);
					btnDelete.setDisabled(false);

					cbRmgroup.setValue(objForm.getMrmgroup().getRmgroupname());
				}
			}
		});
	}

	@NotifyChange("pageTotalSize")
	public void refreshModel(int activePage) {
		orderby = "rmid";
		paging.setPageSize(SysUtils.PAGESIZE);
		model = new MrmListModel(activePage, SysUtils.PAGESIZE, filter, orderby);
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
		if (rmid != null && rmid.trim().length() > 0) {
			if (filter.length() > 0)
				filter += " and ";
			filter += "rmid like '%" + rmid.trim().toUpperCase() + "%'";
		}
		needsPageUpdate = true;
		paging.setActivePage(0);
		pageStartNumber = 0;
		refreshModel(pageStartNumber);
	}

	@Command
	@NotifyChange("objForm")
	public void doCancel() {
		doReset();
	}

	@Command
	@NotifyChange("*")
	public void doSave() {
		try {

			session = StoreHibernateUtil.openSession();
			transaction = session.beginTransaction();

			objForm.setUpdatedby(oUser.getUserid());
			objForm.setLastupdated(new Date());

			oDao.save(session, objForm);
			transaction.commit();
			session.close();
			if (isInsert) {
				needsPageUpdate = true;
				Clients.showNotification(Labels.getLabel("common.add.success"), "info", null, "middle_center", 3000);
			} else
				Clients.showNotification(Labels.getLabel("common.update.success"), "info", null, "middle_center", 3000);
			doReset();
		} catch (HibernateException e) {
			transaction.rollback();
			if (isInsert)
				objForm.setMrmpk(null);
			Messagebox.show("Error : " + e.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		} catch (Exception e) {
			if (isInsert)
				objForm.setMrmpk(null);
			Messagebox.show("Error : " + e.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Command
	@NotifyChange("*")
	public void doDelete() {
		try {
			Messagebox.show(Labels.getLabel("common.delete.confirm"), "Confirm Dialog",
					Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {

						public void onEvent(Event event) throws Exception {
							if (event.getName().equals("onOK")) {
								try {
									session = StoreHibernateUtil.openSession();
									transaction = session.beginTransaction();
									oDao.delete(session, objForm);
									transaction.commit();
									session.close();

									Clients.showNotification(Labels.getLabel("common.delete.success"), "info", null,
											"middle_center", 3000);

									BindUtils.postNotifyChange(null, null, MrmVm.this, "objForm");
									doReset();
								} catch (HibernateException e) {
									transaction.rollback();
									Messagebox.show("Error : " + e.getMessage(), "Error", Messagebox.OK,
											Messagebox.ERROR);
									e.printStackTrace();
								} catch (Exception e) {
									Messagebox.show("Error : " + e.getMessage(), "Error", Messagebox.OK,
											Messagebox.ERROR);
									e.printStackTrace();
								}
							}
						}

					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@NotifyChange("*")
	public void doReset() {

		doSearch();

		isInsert = true;
		objForm = new Mrm();

		btnCancel.setDisabled(true);
		btnDelete.setDisabled(true);
		btnSave.setLabel(Labels.getLabel("common.save"));
	}

	public Validator getValidator() {
		return new AbstractValidator() {

			public void validate(ValidationContext ctx) {

				String rmid = (String) ctx.getProperties("rmid")[0].getValue();
				String rmname = (String) ctx.getProperties("rmname")[0].getValue();
				Mrmgroup mrmgroup = (Mrmgroup) ctx.getProperties("mrmgroup")[0].getValue();

				if (rmid == null || "".equals(rmid.trim()))
					this.addInvalidMessage(ctx, "rmid", Labels.getLabel("common.validator.empty"));
				if (rmname == null || "".equals(rmname.trim()))
					this.addInvalidMessage(ctx, "rmname", Labels.getLabel("common.validator.empty"));
				if (mrmgroup == null)
					this.addInvalidMessage(ctx, "mrmgroup", Labels.getLabel("common.validator.empty"));
			}
		};
	}

	public ListModelList<Mrmgroup> getMrmgroup() {
		ListModelList<Mrmgroup> lm = null;
		try {
			lm = new ListModelList<Mrmgroup>(new MrmgroupDAO().listByFilter("0=0", "rmgroupcode"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lm;
	}

	public Mrm getObjForm() {
		return objForm;
	}

	public void setObjForm(Mrm objForm) {
		this.objForm = objForm;
	}

	public String getRmid() {
		return rmid;
	}

	public void setRmid(String rmid) {
		this.rmid = rmid;
	}

	public int getPageTotalSize() {
		return pageTotalSize;
	}

	public void setPageTotalSize(int pageTotalSize) {
		this.pageTotalSize = pageTotalSize;
	}
}
