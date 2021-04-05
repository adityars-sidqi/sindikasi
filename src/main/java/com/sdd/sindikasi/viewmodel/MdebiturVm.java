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

import com.sdd.sindikasi.dao.MdebiturDAO;
import com.sdd.sindikasi.domain.Mdebitur;
import com.sdd.sindikasi.domain.Muser;
import com.sdd.sindikasi.model.MdebiturListModel;
import com.sdd.utils.SysUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class MdebiturVm {
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();

	private SimpleDateFormat datetimeLocalFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	private Session session;
	private Transaction transaction;

	private Muser oUser;

	private MdebiturListModel model;
	private MdebiturDAO oDao = new MdebiturDAO();

	private int pageStartNumber;
	private int pageTotalSize;
	private boolean needsPageUpdate;
	private String filter;
	private String orderby;
	private boolean isInsert;

	private Mdebitur objForm;

	private String debitur;
	private String dirname;

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
			listbox.setItemRenderer(new ListitemRenderer<Mdebitur>() {

				public void render(Listitem item, final Mdebitur data, int index) throws Exception {
					Listcell cell = new Listcell(String.valueOf((SysUtils.PAGESIZE * pageStartNumber) + index + 1));
					item.appendChild(cell);
					cell = new Listcell(data.getDebitur());
					item.appendChild(cell);
					cell = new Listcell(data.getOfficephone());
					item.appendChild(cell);
					cell = new Listcell(data.getOfficefax());
					item.appendChild(cell);
					cell = new Listcell(data.getOfficeemail());
					item.appendChild(cell);
					cell = new Listcell(data.getOfficeaddress());
					item.appendChild(cell);
					cell = new Listcell(data.getDirname());
					item.appendChild(cell);
					cell = new Listcell(data.getDirphone());
					item.appendChild(cell);
					cell = new Listcell(data.getDiremail());
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
		orderby = "debitur";
		paging.setPageSize(SysUtils.PAGESIZE);
		model = new MdebiturListModel(activePage, SysUtils.PAGESIZE, filter, orderby);
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
		if (debitur != null && debitur.trim().length() > 0) {
			if (filter.length() > 0)
				filter += " and ";
			filter += "debitur like '%" + debitur.trim().toUpperCase() + "%'";
		}
		if (dirname != null && dirname.trim().length() > 0) {
			if (filter.length() > 0)
				filter += " and ";
			filter += "dirname like '%" + dirname.trim().toUpperCase() + "%'";
		}
		needsPageUpdate = true;
		paging.setActivePage(0);
		pageStartNumber = 0;
		refreshModel(pageStartNumber);
	}

	@Command
	@NotifyChange("*")
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
				objForm.setMdebiturpk(null);
			Messagebox.show("Error : " + e.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		} catch (Exception e) {
			if (isInsert)
				objForm.setMdebiturpk(null);
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

									doReset();
									BindUtils.postNotifyChange(null, null, MdebiturVm.this, "objForm");
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
		objForm = new Mdebitur();

		btnCancel.setDisabled(true);
		btnDelete.setDisabled(true);
		btnSave.setLabel(Labels.getLabel("common.save"));
	}

	public Validator getValidator() {
		return new AbstractValidator() {

			public void validate(ValidationContext ctx) {

				String debitur = (String) ctx.getProperties("debitur")[0].getValue();
				String officephone = (String) ctx.getProperties("officephone")[0].getValue();
				String officefax = (String) ctx.getProperties("officefax")[0].getValue();
				String officeemail = (String) ctx.getProperties("officeemail")[0].getValue();
				String officeaddress = (String) ctx.getProperties("officeaddress")[0].getValue();
				String dirname = (String) ctx.getProperties("dirname")[0].getValue();
				String dirphone = (String) ctx.getProperties("dirphone")[0].getValue();
				String diremail = (String) ctx.getProperties("diremail")[0].getValue();
				String picname = (String) ctx.getProperties("picname")[0].getValue();
				String picposition = (String) ctx.getProperties("picposition")[0].getValue();
				String picphone = (String) ctx.getProperties("picphone")[0].getValue();
				String picemail = (String) ctx.getProperties("picemail")[0].getValue();

				if (debitur == null || "".equals(debitur.trim()))
					this.addInvalidMessage(ctx, "debitur", Labels.getLabel("common.validator.empty"));
				if (officephone == null || "".equals(officephone.trim()))
					this.addInvalidMessage(ctx, "officephone", Labels.getLabel("common.validator.empty"));
				if (officefax == null || "".equals(officefax.trim()))
					this.addInvalidMessage(ctx, "officefax", Labels.getLabel("common.validator.empty"));
				if (officeemail == null || "".equals(officeemail.trim()))
					this.addInvalidMessage(ctx, "officeemail", Labels.getLabel("common.validator.empty"));
				if (officeaddress == null || "".equals(officeaddress.trim()))
					this.addInvalidMessage(ctx, "officeaddress", Labels.getLabel("common.validator.empty"));
				if (dirname == null || "".equals(dirname.trim()))
					this.addInvalidMessage(ctx, "dirname", Labels.getLabel("common.validator.empty"));
				if (dirphone == null || "".equals(dirphone.trim()))
					this.addInvalidMessage(ctx, "dirphone", Labels.getLabel("common.validator.empty"));
				if (diremail == null || "".equals(diremail.trim()))
					this.addInvalidMessage(ctx, "diremail", Labels.getLabel("common.validator.empty"));
				if (picname == null || "".equals(picname.trim()))
					this.addInvalidMessage(ctx, "picname", Labels.getLabel("common.validator.empty"));
				if (picposition == null || "".equals(picposition.trim()))
					this.addInvalidMessage(ctx, "picposition", Labels.getLabel("common.validator.empty"));
				if (picphone == null || "".equals(picphone.trim()))
					this.addInvalidMessage(ctx, "picphone", Labels.getLabel("common.validator.empty"));
				if (picemail == null || "".equals(picemail.trim()))
					this.addInvalidMessage(ctx, "picemail", Labels.getLabel("common.validator.empty"));

			}
		};
	}

	public Mdebitur getObjForm() {
		return objForm;
	}

	public void setObjForm(Mdebitur objForm) {
		this.objForm = objForm;
	}

	public String getDebitur() {
		return debitur;
	}

	public void setDebitur(String debitur) {
		this.debitur = debitur;
	}

	public String getDirname() {
		return dirname;
	}

	public void setDirname(String dirname) {
		this.dirname = dirname;
	}

	public int getPageTotalSize() {
		return pageTotalSize;
	}

	public void setPageTotalSize(int pageTotalSize) {
		this.pageTotalSize = pageTotalSize;
	}
}
