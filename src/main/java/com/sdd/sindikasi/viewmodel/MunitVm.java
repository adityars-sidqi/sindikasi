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
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.event.PagingEvent;

import com.sdd.sindikasi.dao.MunitDAO;
import com.sdd.sindikasi.domain.Munit;
import com.sdd.sindikasi.domain.Muser;
import com.sdd.sindikasi.model.MunitListModel;
import com.sdd.utils.SysUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class MunitVm {
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();

	private SimpleDateFormat datetimeLocalFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	private Session session;
	private Transaction transaction;

	private Muser oUser;

	private MunitListModel model;
	private MunitDAO oDao = new MunitDAO();

	private int pageStartNumber;
	private int pageTotalSize;
	private boolean needsPageUpdate;
	private String filter;
	private String orderby;
	private boolean isInsert;

	private Munit objForm;

	private String unitcode;
	private String unitname;

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
			listbox.setItemRenderer(new ListitemRenderer<Munit>() {

				public void render(Listitem item, final Munit data, int index) throws Exception {
					Listcell cell = new Listcell(String.valueOf((SysUtils.PAGESIZE * pageStartNumber) + index + 1));
					item.appendChild(cell);
					cell = new Listcell(data.getUnitcode());
					item.appendChild(cell);
					cell = new Listcell(data.getUnitname());
					item.appendChild(cell);
					cell = new Listcell(data.getUnitcostcenter());
					item.appendChild(cell);
					cell = new Listcell(data.getIsbumn().equals("Y") ? "BUMN" : "Non BUMN");
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
				}
			}
		});
	}

	@NotifyChange("pageTotalSize")
	public void refreshModel(int activePage) {
		orderby = "unitcode";
		paging.setPageSize(SysUtils.PAGESIZE);
		model = new MunitListModel(activePage, SysUtils.PAGESIZE, filter, orderby);
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
		if (unitcode != null && unitcode.trim().length() > 0) {
			if (filter.length() > 0)
				filter += " and ";
			filter += "debitur like '%" + unitcode.trim().toUpperCase() + "%'";
		}
		if (unitname != null && unitname.trim().length() > 0) {
			if (filter.length() > 0)
				filter += " and ";
			filter += "unitname like '%" + unitname.trim().toUpperCase() + "%'";
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
				objForm.setMunitpk(null);
			Messagebox.show("Error : " + e.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		} catch (Exception e) {
			if (isInsert)
				objForm.setMunitpk(null);
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

									BindUtils.postNotifyChange(null, null, MunitVm.this, "objForm");
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
		objForm = new Munit();
		objForm.setIsbumn("N");

		btnCancel.setDisabled(true);
		btnDelete.setDisabled(true);
		btnSave.setLabel(Labels.getLabel("common.save"));
	}

	public Validator getValidator() {
		return new AbstractValidator() {

			public void validate(ValidationContext ctx) {

				String unitcode = (String) ctx.getProperties("unitcode")[0].getValue();
				String unitname = (String) ctx.getProperties("unitname")[0].getValue();
				String unitcostcenter = (String) ctx.getProperties("unitcostcenter")[0].getValue();

				if (unitcode == null || "".equals(unitcode.trim()))
					this.addInvalidMessage(ctx, "unitcode", Labels.getLabel("common.validator.empty"));
				if (unitname == null || "".equals(unitname.trim()))
					this.addInvalidMessage(ctx, "unitname", Labels.getLabel("common.validator.empty"));
				if (unitcostcenter == null || "".equals(unitcostcenter.trim()))
					this.addInvalidMessage(ctx, "unitcostcenter", Labels.getLabel("common.validator.empty"));
			}
		};
	}

	public Munit getObjForm() {
		return objForm;
	}

	public void setObjForm(Munit objForm) {
		this.objForm = objForm;
	}

	public String getUnitcode() {
		return unitcode;
	}

	public void setUnitcode(String unitcode) {
		this.unitcode = unitcode;
	}

	public String getUnitname() {
		return unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

	public int getPageTotalSize() {
		return pageTotalSize;
	}

	public void setPageTotalSize(int pageTotalSize) {
		this.pageTotalSize = pageTotalSize;
	}
}
