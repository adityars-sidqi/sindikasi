package com.sdd.sindikasi.viewmodel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WebApps;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Window;

import com.sdd.sindikasi.dao.McurrencyDAO;
import com.sdd.sindikasi.dao.MdebiturDAO;
import com.sdd.sindikasi.dao.MdeclinecodeDAO;
import com.sdd.sindikasi.dao.MrmDAO;
import com.sdd.sindikasi.dao.MrmgroupDAO;
import com.sdd.sindikasi.dao.MsectorDAO;
import com.sdd.sindikasi.dao.MunitDAO;
import com.sdd.sindikasi.dao.TcounterengineDAO;
import com.sdd.sindikasi.dao.TmemoDAO;
import com.sdd.sindikasi.dao.TpipelineDAO;
import com.sdd.sindikasi.dao.TpipelinedocDAO;
import com.sdd.sindikasi.dao.TpipelinepartDAO;
import com.sdd.sindikasi.dao.TportoDAO;
import com.sdd.sindikasi.dao.TportopartDAO;
import com.sdd.sindikasi.domain.Mdebitur;
import com.sdd.sindikasi.domain.Mdeclinecode;
import com.sdd.sindikasi.domain.Mrm;
import com.sdd.sindikasi.domain.Mrmgroup;
import com.sdd.sindikasi.domain.Msector;
import com.sdd.sindikasi.domain.Munit;
import com.sdd.sindikasi.domain.Muser;
import com.sdd.sindikasi.domain.Tmemo;
import com.sdd.sindikasi.domain.Tpipeline;
import com.sdd.sindikasi.domain.Tpipelinedoc;
import com.sdd.sindikasi.domain.Tpipelinepart;
import com.sdd.sindikasi.domain.Tporto;
import com.sdd.sindikasi.domain.Tportopart;
import com.sdd.sindikasi.utils.AppData;
import com.sdd.sindikasi.utils.AppUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class PipelineFormVm {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;

	private SimpleDateFormat datetimelocalFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	private Session session;
	private Transaction transaction;

	private boolean isInsert;
	private boolean isSaved;

	private int pageStartNumber;

	private Tpipeline objForm;
	private Tpipelinepart objPart;

	private TpipelineDAO oDao = new TpipelineDAO();
	private TpipelinepartDAO tpipelinepartDao = new TpipelinepartDAO();
	private TpipelinedocDAO tpipelinedocDao = new TpipelinedocDAO();
	private TmemoDAO tmemoDao = new TmemoDAO();

	private TportoDAO tportoDao = new TportoDAO();
	private TportopartDAO tportopartDao = new TportopartDAO();

	private List<Tpipelinepart> listPipelinepart;
	private List<Tpipelinepart> listDelPart;
	private List<Tmemo> listNewMemo;
	private List<Tmemo> listDelMemo;
	private List<Tpipelinedoc> listDelDoc;
	private List<Media> listMedia;

	private String memo;

	private BigDecimal selfportion;
	private BigDecimal selfportionamount = new BigDecimal(0);
	private BigDecimal feeamount = new BigDecimal(0);

	private int nopart;
	private int nodoc;
	private int nomemo;

	private Mrmgroup objRmgroup;

	@Wire
	private Caption caption;
	@Wire
	private Row rowReg;
	@Wire
	private Combobox cbDebitur;
	@Wire
	private Combobox cbSector;
	@Wire
	private Combobox cbRm;
	@Wire
	private Combobox cbUnit;
	@Wire
	private Combobox cbCurrency;
	@Wire
	private Grid gridAgentself;
	@Wire
	private Checkbox chkAllagentself;
	@Wire
	private Grid gridFormParticipant;
	@Wire
	private Checkbox chkAgentFac;
	@Wire
	private Checkbox chkAgentCol;
	@Wire
	private Checkbox chkAgentEsc;
	@Wire
	private Checkbox chkAgentFacPart;
	@Wire
	private Checkbox chkAgentColPart;
	@Wire
	private Checkbox chkAgentEscPart;
	@Wire
	private Grid gridParticipant;
	@Wire
	private Grid gridFormMemo;
	@Wire
	private Grid gridMemo;
	@Wire
	private Grid gridDoc;
	@Wire
	private Combobox cbStatus;
	@Wire
	private Groupbox gbChangestatus;
	@Wire
	private Row rowApprove;
	@Wire
	private Row rowDecline;
	@Wire
	private Button btnSubmit;
	@Wire
	private Window win;
	@Wire
	private Div divFiles;

	@Wire
	private Grid gridCreditFacility;
	@Wire
	private Combobox cbKipokokcurr, cbKiidccurr, cbKmkcurr, cbTermloancurr, cbCorploancurr, cbNclcurr;
	@Wire
	private Decimalbox dbKipokok, dbKiidc, dbKmk, dbTermloan, dbCorploan, dbNcl;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("obj") Tpipeline obj,
			@ExecutionArgParam("pageStartNumber") Integer pageStartNumber, @ExecutionArgParam("arg") String arg) {
		Selectors.wireComponents(view, this, false);

		oUser = (Muser) zkSession.getAttribute("oUser");

		doReset();

		if (obj != null) {
			try {
				isInsert = false;
				caption.setVisible(true);
				objForm = obj;
				if (pageStartNumber != null)
					this.pageStartNumber = pageStartNumber;

				rowReg.setVisible(true);
				if (arg != null && arg.equals("APPROVAL")) {
					gbChangestatus.setVisible(true);
					rowApprove.setVisible(false);
					rowDecline.setVisible(false);
				}

				cbDebitur.setValue(objForm.getMdebitur().getDebitur());
				cbUnit.setValue(objForm.getMunit().getUnitname());
				cbSector.setValue(objForm.getMsector().getSectorname());
				if (objForm.getIsagentfac().equals("Y"))
					chkAgentFac.setChecked(true);
				else
					chkAgentFac.setChecked(false);
				if (objForm.getIsagentcol().equals("Y"))
					chkAgentCol.setChecked(true);
				else
					chkAgentCol.setChecked(false);
				if (objForm.getIsagentesc().equals("Y"))
					chkAgentEsc.setChecked(true);
				else
					chkAgentEsc.setChecked(false);

				doFeeCal(objForm.getFeepercent(), objForm.getSelfportion());

				for (Tpipelinepart tpipelinepart : tpipelinepartDao
						.listByFilter("tpipeline.tpipelinepk = " + objForm.getTpipelinepk(), "participantname")) {
					if (tpipelinepart.getIsself().equals("Y"))
						objPart = tpipelinepart;
					else
						doAddGridPipelinepart(tpipelinepart, objForm);
				}

				for (Tmemo tmemo : tmemoDao.listByFilter("tpipeline.tpipelinepk = " + objForm.getTpipelinepk(),
						"createdtime")) {
					doAddGridMemo(tmemo, objForm);
				}

				for (Tpipelinedoc tdoc : tpipelinedocDao
						.listByFilter("tpipeline.tpipelinepk = " + objForm.getTpipelinepk(), "createdtime")) {
					doAddGridDoc(tdoc);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Command
	@NotifyChange("feeamount")
	public void doFeeCal(@BindingParam("percent") BigDecimal percent,
			@BindingParam("selfamount") BigDecimal selfamount) {
		try {
			if (percent != null && selfamount != null)
				feeamount = percent.divide(new BigDecimal(100)).multiply(selfamount);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	public void doUpload(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx) {
		try {
			UploadEvent event = (UploadEvent) ctx.getTriggerEvent();
			for (final Media media : event.getMedias()) {
				listMedia.add(media);
				final Hlayout hlayout = new Hlayout();
				hlayout.appendChild(new Label(media.getName()));
				hlayout.appendChild(new Separator("vertical"));
				A aDel = new A("Delete");
				aDel.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					public void onEvent(Event event) throws Exception {
						divFiles.removeChild(hlayout);
						listMedia.remove(media);
					}

				});
				hlayout.appendChild(aDel);
				divFiles.appendChild(hlayout);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange("objPart")
	public void doSavePart() {
		try {
			if (chkAgentFacPart.isChecked())
				objPart.setIsagentfac("Y");
			else
				objPart.setIsagentfac("N");
			if (chkAgentColPart.isChecked())
				objPart.setIsagentcol("Y");
			else
				objPart.setIsagentcol("N");
			if (chkAgentEscPart.isChecked())
				objPart.setIsagentesc("Y");
			else
				objPart.setIsagentesc("N");
			listPipelinepart.add(objPart);
			doAddGridPipelinepart(objPart, null);
			doResetPart();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void doAddGridPipelinepart(final Tpipelinepart obj, final Tpipeline tpipeline) {
		final Row row = new Row();
		row.appendChild(new Label(String.valueOf(++nopart)));
		row.appendChild(new Label(obj.getParticipantname()));
		row.appendChild(new Label(AppData.getAgentType(obj.getIsagentfac(), obj.getIsagentcol(), obj.getIsagentesc())));
		/*
		 * row.appendChild(new Label(obj.getCurrency() + " " +
		 * decimalLocalFormatter.format(obj.getPortionamount()))); row.appendChild(new
		 * Label(obj.getPicname())); row.appendChild(new Label(obj.getPichp()));
		 * row.appendChild(new Label(obj.getPicemail()));
		 */
		Button btnDel = new Button("Delete");
		btnDel.setZclass("btn btn-sm btn-danger");
		btnDel.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

			public void onEvent(Event event) throws Exception {
				Messagebox.show(Labels.getLabel("common.delete.confirm"), "Confirm Dialog",
						Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener<Event>() {

							public void onEvent(Event event) throws Exception {
								if (event.getName().equals("onOK")) {
									listPipelinepart.remove(obj);
									if (tpipeline != null)
										listDelPart.add(obj);
									gridParticipant.getRows().removeChild(row);
								}
							}
						});

			}

		});
		row.appendChild(btnDel);

		gridParticipant.getRows().appendChild(row);
	}

	@Command
	@NotifyChange("memo")
	public void doSaveMemo() {
		if (memo != null && memo.trim().length() > 0) {
			try {

				final Tmemo obj = new Tmemo();
				obj.setMemo(memo);
				obj.setCreatedtime(new Date());
				obj.setCreatedby(oUser.getUserid());
				listNewMemo.add(obj);

				doAddGridMemo(obj, null);

				memo = "";

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Messagebox.show("You must entry the memo", WebApps.getCurrent().getAppName(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	private void doAddGridDoc(final Tpipelinedoc obj) {
		final Row row = new Row();
		row.appendChild(new Label(String.valueOf(++nodoc)));
		A a = new A(obj.getFilename());
		a.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

			public void onEvent(Event event) throws Exception {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("docpath", AppUtils.FILES_ROOT_PATH + AppUtils.DOC_PATH + obj.getFilename());

				Window win = (Window) Executions.createComponents("/view/docviewer.zul", null, map);
				win.setClosable(true);
				win.doModal();
			}
		});
		row.appendChild(a);
		row.appendChild(new Label(datetimelocalFormatter.format(obj.getCreatedtime())));
		row.appendChild(new Label(obj.getCreatedby()));

		Button btnDel = new Button("Delete");
		btnDel.setZclass("btn btn-sm btn-danger");
		btnDel.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

			public void onEvent(Event event) throws Exception {
				Messagebox.show(Labels.getLabel("common.delete.confirm"), "Confirm Dialog",
						Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener<Event>() {

							public void onEvent(Event event) throws Exception {
								if (event.getName().equals("onOK")) {
									listDelDoc.add(obj);
									gridDoc.getRows().removeChild(row);
								}
							}
						});

			}

		});
		row.appendChild(btnDel);

		gridDoc.getRows().appendChild(row);
	}

	private void doAddGridMemo(final Tmemo obj, final Tpipeline tpipeline) {
		final Row row = new Row();
		row.appendChild(new Label(String.valueOf(++nomemo)));
		row.appendChild(new Label(obj.getMemo()));
		row.appendChild(new Label(datetimelocalFormatter.format(obj.getCreatedtime())));
		row.appendChild(new Label(obj.getCreatedby()));

		Button btnDel = new Button("Delete");
		btnDel.setZclass("btn btn-sm btn-danger");
		btnDel.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

			public void onEvent(Event event) throws Exception {
				Messagebox.show(Labels.getLabel("common.delete.confirm"), "Confirm Dialog",
						Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener<Event>() {

							public void onEvent(Event event) throws Exception {
								if (event.getName().equals("onOK")) {
									listNewMemo.remove(obj);
									if (tpipeline != null)
										listDelMemo.add(obj);
									gridMemo.getRows().removeChild(row);
								}
							}
						});

			}

		});
		row.appendChild(btnDel);

		gridMemo.getRows().appendChild(row);
	}

	@Command
	@NotifyChange("*")
	public void doSave() {
		try {
			isSaved = true;

			session = StoreHibernateUtil.openSession();
			transaction = session.beginTransaction();

			objForm.setFeecurrency(objForm.getSelfportioncurrency());
			objForm.setFeeamount(feeamount);
			if (chkAgentFac.isChecked())
				objForm.setIsagentfac("Y");
			else
				objForm.setIsagentfac("N");
			if (chkAgentCol.isChecked())
				objForm.setIsagentcol("Y");
			else
				objForm.setIsagentcol("N");
			if (chkAgentEsc.isChecked())
				objForm.setIsagentesc("Y");
			else
				objForm.setIsagentesc("N");

			objForm.setYearperiod(Integer.parseInt(new SimpleDateFormat("yyyy").format(objForm.getTargetpk())));
			objForm.setMonthperiod(Integer.parseInt(new SimpleDateFormat("MM").format(objForm.getTargetpk())));
			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
			if (objForm.getTargetpk().compareTo(dateFormatter.parse(objForm.getYearperiod() + "-09-30")) > 0) {
				objForm.setQperiod("Q4");
			} else if (objForm.getTargetpk().compareTo(dateFormatter.parse(objForm.getYearperiod() + "-06-30")) > 0) {
				objForm.setQperiod("Q3");
			} else if (objForm.getTargetpk().compareTo(dateFormatter.parse(objForm.getYearperiod() + "-03-31")) > 0) {
				objForm.setQperiod("Q2");
			} else {
				objForm.setQperiod("Q1");
			}

			if (isInsert) {
				String regid = new TcounterengineDAO().getLastCounter("REGID");
				objForm.setRegid(regid);
				objForm.setRegtime(new Date());
				objForm.setStatus(AppUtils.STATUS_PROGRESS);
			} else {
				if (objForm.getStatus().equals(AppUtils.STATUS_APPROVE)) {
					Tporto tporto = new Tporto();
					tporto.setRegid(objForm.getRegid());
					tporto.setRegtime(objForm.getRegtime());
					tporto.setMsector(objForm.getMsector());
					tporto.setMunit(objForm.getMunit());
					tporto.setDebitur(objForm.getMdebitur().getDebitur());
					tporto.setOfficephone(objForm.getMdebitur().getOfficephone());
					tporto.setOfficefax(objForm.getMdebitur().getOfficefax());
					tporto.setOfficeemail(objForm.getMdebitur().getOfficeemail());
					tporto.setOfficeaddress(objForm.getMdebitur().getOfficeaddress());
					tporto.setDirname(objForm.getMdebitur().getDirname());
					tporto.setDirphone(objForm.getMdebitur().getDirphone());
					tporto.setDiremail(objForm.getMdebitur().getDiremail());
					tporto.setProject(objForm.getProject());
					tporto.setRmgroupcode(objForm.getMrm().getMrmgroup().getRmgroupcode());
					tporto.setRmid(objForm.getMrm().getRmid());
					tporto.setRmname(objForm.getMrm().getRmname());
					tporto.setRmcredit(objForm.getRmcredit());
					tporto.setProjectamount(objForm.getProjectamount());
					tporto.setCreditfacility(objForm.getCreditfacility());
					tporto.setSelfportion(selfportion);
					tporto.setFeepercent(objForm.getFeepercent());
					tporto.setFeeamount(objForm.getFeeamount());
					tporto.setTargetpk(objForm.getTargetpk());
					tporto.setPortodate(new Date());

					tporto.setLastupdated(new Date());
					tporto.setUpdatedby(oUser.getUserid());

					tportoDao.save(session, tporto);

					for (Tpipelinepart obj : tpipelinepartDao
							.listByFilter("tpipeline.tpipelinepk = " + objForm.getTpipelinepk(), "participantname")) {
						Tportopart tportopart = new Tportopart();
						tportopart.setTporto(tporto);
						tportopart.setParticipantname(obj.getParticipantname());
						tportopart.setPortionpercent(obj.getPortionpercent());
						tportopart.setPortionamount(obj.getPortionamount());
						tportopart.setLastupdated(new Date());
						tportopart.setUpdatedby(oUser.getUserid());

						tportopartDao.save(session, tportopart);
					}
				}
			}

			objForm.setLastupdated(new Date());
			objForm.setUpdatedby(oUser.getUserid());

			oDao.save(session, objForm);

			for (Tpipelinepart obj : listDelPart) {
				tpipelinepartDao.delete(session, obj);
			}

			for (Tpipelinepart obj : listPipelinepart) {
				obj.setTpipeline(objForm);
				obj.setIsself("N");
				obj.setLastupdated(new Date());
				obj.setUpdatedby(oUser.getUserid());

				tpipelinepartDao.save(session, obj);
			}

			for (Tmemo obj : listDelMemo) {
				tmemoDao.delete(session, obj);
			}

			for (Tmemo obj : listNewMemo) {
				obj.setTpipeline(objForm);

				tmemoDao.save(session, obj);
			}

			for (Tpipelinedoc obj : listDelDoc) {
				tpipelinedocDao.delete(session, obj);
			}

			String path = Executions.getCurrent().getDesktop().getWebApp()
					.getRealPath(AppUtils.FILES_ROOT_PATH + AppUtils.DOC_PATH);
			for (Media media : listMedia) {
				if (media.isBinary()) {
					Files.copy(new File(path + "/" + media.getName()), media.getStreamData());
				} else {
					BufferedWriter writer = new BufferedWriter(new FileWriter(path + "/" + media.getName()));
					Files.copy(writer, media.getReaderData());
					writer.close();
				}

				Tpipelinedoc doc = new Tpipelinedoc();
				doc.setTpipeline(objForm);
				doc.setFilename(media.getName());
				doc.setCreatedby(oUser.getUserid());
				doc.setCreatedtime(new Date());
				tpipelinedocDao.save(session, doc);
			}

			transaction.commit();

			if (isInsert) {
				Clients.showNotification(Labels.getLabel("common.add.success"), "info", null, "middle_center", 3000);
				doReset();
			} else {
				Clients.showNotification(Labels.getLabel("common.update.success"), "info", null, "middle_center", 3000);
				doClose();
			}
		} catch (HibernateException e) {
			transaction.rollback();
			if (isInsert)
				objForm.setTpipelinepk(null);
			Messagebox.show("Error : " + e.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		} catch (Exception e) {
			if (isInsert)
				objForm.setTpipelinepk(null);
			Messagebox.show("Error : " + e.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	@Command
	public void doLoadDeclinecode(@BindingParam("status") String status) {
		if (status.equals(AppUtils.STATUS_APPROVE)) {
			rowApprove.setVisible(true);
			rowDecline.setVisible(false);
		} else {
			rowApprove.setVisible(false);
			rowDecline.setVisible(true);
		}
	}

	@Command
	public void doLoadRmByRmgroup(@BindingParam("obj") Mrmgroup obj) {
		if (obj != null) {
			cbRm.getChildren().clear();
			try {
				for(Mrm objRm : new MrmDAO().listByFilter("mrmgroup.mrmgrouppk = " + obj.getMrmgrouppk(), "rmname")) {
					Comboitem item = new Comboitem(objRm.getRmname());
					item.setValue(objRm);
					cbRm.appendChild(item);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Command
	public void doCheckedall(@BindingParam("checked") Boolean checked) {
		if (gridCreditFacility.getRows() != null && gridCreditFacility.getRows().getChildren() != null) {
			List<Row> components = gridCreditFacility.getRows().getChildren();
			for (Row comp : components) {
				Checkbox chk = (Checkbox) comp.getChildren().get(0);
				if (checked) {
					if (!chk.isChecked()) {
						chk.setChecked(true);
						doCheckCreditFacility(true, "all");
					}
				} else {
					if (chk.isChecked()) {
						chk.setChecked(false);
						doCheckCreditFacility(false, "all");
					}
				}
			}
		}
	}

	@Command
	public void doCheckCreditFacility(@BindingParam("checked") Boolean isChecked,
			@BindingParam("facility") String facility) {
		if (isChecked && (facility.equals("kipokok") || facility.equals("all"))) {
			cbKipokokcurr.setDisabled(false);
			dbKipokok.setReadonly(false);
		} else {
			cbKipokokcurr.setSelectedItem(null);
			cbKipokokcurr.setDisabled(true);
			dbKipokok.setReadonly(true);
		}
		if (isChecked && (facility.equals("kiidc") || facility.equals("all"))) {
			cbKiidccurr.setDisabled(false);
			dbKiidc.setReadonly(false);
		} else {
			cbKiidccurr.setSelectedItem(null);
			cbKiidccurr.setDisabled(true);
			dbKiidc.setReadonly(true);
		}
		if (isChecked && (facility.equals("kmk") || facility.equals("all"))) {
			cbKmkcurr.setDisabled(false);
			dbKmk.setReadonly(false);
		} else {
			cbKmkcurr.setSelectedItem(null);
			cbKmkcurr.setDisabled(true);
			dbKmk.setReadonly(true);
		}
		if (isChecked && (facility.equals("termloan") || facility.equals("all"))) {
			cbTermloancurr.setDisabled(false);
			dbTermloan.setReadonly(false);
		} else {
			cbTermloancurr.setSelectedItem(null);
			cbTermloancurr.setDisabled(true);
			dbTermloan.setReadonly(true);
		}
		if (isChecked && (facility.equals("corploan") || facility.equals("all"))) {
			cbCorploancurr.setDisabled(false);
			dbCorploan.setReadonly(false);
		} else {
			cbCorploancurr.setSelectedItem(null);
			cbCorploancurr.setDisabled(true);
			dbCorploan.setReadonly(true);
		}
		if (isChecked && (facility.equals("ncl") || facility.equals("all"))) {
			cbNclcurr.setDisabled(false);
			dbNcl.setReadonly(false);
		} else {
			cbNclcurr.setSelectedItem(null);
			cbNclcurr.setDisabled(true);
			dbNcl.setReadonly(true);
		}
	}

	@Command()
	@NotifyChange("*")
	public void doClose() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageStartNumber", pageStartNumber);
		map.put("isSaved", isSaved);
		Event closeEvent = new Event("onClose", win, map);
		Events.postEvent(closeEvent);
	}

	@NotifyChange("*")
	public void doReset() {
		try {
			nopart = 0;
			nodoc = 0;
			nomemo = 0;

			objForm = new Tpipeline();
			objForm.setProjectcurrency("IDR");
			objForm.setCreditfacilitycurr("IDR");
			objForm.setSelfportioncurrency("IDR");

			isInsert = true;
			isSaved = false;
			listPipelinepart = new ArrayList<Tpipelinepart>();
			listDelPart = new ArrayList<Tpipelinepart>();
			listNewMemo = new ArrayList<Tmemo>();
			listDelMemo = new ArrayList<Tmemo>();
			listDelDoc = new ArrayList<Tpipelinedoc>();
			listMedia = new ArrayList<Media>();

			cbDebitur.setValue(null);
			cbSector.setValue(null);
			cbStatus.setValue(null);
			cbUnit.setValue(null);

			rowReg.setVisible(false);

			gbChangestatus.setVisible(false);
			cbStatus.getChildren().clear();
			for (int i = 0; i < AppUtils.STATUS_PIPELINE.length; i++) {
				Comboitem item = new Comboitem(AppData.getStatusPipelineLabel(AppUtils.STATUS_PIPELINE[i]));
				item.setValue(AppUtils.STATUS_PIPELINE[i]);
				cbStatus.appendChild(item);
			}

			doResetPart();
			
			doCheckCreditFacility(false, "all");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@NotifyChange("objPart")
	public void doResetPart() {
		objPart = new Tpipelinepart();
	}

	public Validator getValidator() {
		return new AbstractValidator() {

			public void validate(ValidationContext ctx) {

				Mdebitur mdebitur = (Mdebitur) ctx.getProperties("mdebitur")[0].getValue();
				Msector msector = (Msector) ctx.getProperties("msector")[0].getValue();
				String project = (String) ctx.getProperties("project")[0].getValue();
				Munit munit = (Munit) ctx.getProperties("munit")[0].getValue();
				BigDecimal projectamount = (BigDecimal) ctx.getProperties("projectamount")[0].getValue();
				BigDecimal creditfacility = (BigDecimal) ctx.getProperties("creditfacility")[0].getValue();
				BigDecimal selfportion = (BigDecimal) ctx.getProperties("selfportion")[0].getValue();
				Date targetpk = (Date) ctx.getProperties("targetpk")[0].getValue();

				if (mdebitur == null)
					this.addInvalidMessage(ctx, "mdebitur", Labels.getLabel("common.validator.empty"));
				if (msector == null)
					this.addInvalidMessage(ctx, "msector", Labels.getLabel("common.validator.empty"));
				if (project == null || "".equals(project.trim()))
					this.addInvalidMessage(ctx, "project", Labels.getLabel("common.validator.empty"));
				if (munit == null)
					this.addInvalidMessage(ctx, "munit", Labels.getLabel("common.validator.empty"));
				if (projectamount == null || projectamount.compareTo(BigDecimal.ZERO) <= 0)
					this.addInvalidMessage(ctx, "projectamount", Labels.getLabel("common.validator.empty"));
				if (creditfacility == null || creditfacility.compareTo(BigDecimal.ZERO) <= 0)
					this.addInvalidMessage(ctx, "creditfacility", Labels.getLabel("common.validator.empty"));
				if (selfportion == null || selfportion.compareTo(BigDecimal.ZERO) <= 0)
					this.addInvalidMessage(ctx, "selfportion", Labels.getLabel("common.validator.empty"));
				if (targetpk == null)
					this.addInvalidMessage(ctx, "targetpk", Labels.getLabel("common.validator.empty"));

			}
		};
	}

	public Validator getValidatorPart() {
		return new AbstractValidator() {

			public void validate(ValidationContext ctx) {
				String participantname = (String) ctx.getProperties("participantname")[0].getValue();

				if (participantname == null || "".equals(participantname.trim()))
					this.addInvalidMessage(ctx, "participantname", Labels.getLabel("common.validator.empty"));
			}
		};
	}

	public ListModelList<Mdebitur> getMdebitur() {
		ListModelList<Mdebitur> lm = null;
		try {
			lm = new ListModelList<Mdebitur>(new MdebiturDAO().listByFilter("0=0", "debitur"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lm;
	}

	public ListModelList<Msector> getMsector() {
		ListModelList<Msector> lm = null;
		try {
			lm = new ListModelList<Msector>(new MsectorDAO().listByFilter("0=0", "sectorname"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lm;
	}

	public ListModelList<Mrm> getMrm() {
		ListModelList<Mrm> lm = null;
		try {
			lm = new ListModelList<Mrm>(new MrmDAO().listByFilter("0=0", "rmname"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lm;
	}

	public ListModelList<Munit> getMunit() {
		ListModelList<Munit> lm = null;
		try {
			lm = new ListModelList<Munit>(new MunitDAO().listByFilter("0=0", "unitcode"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lm;
	}

	@SuppressWarnings("unchecked")
	public ListModelList<String> getMcurreny() {
		ListModelList<String> lm = null;
		try {
			lm = new ListModelList<String>(new McurrencyDAO().listStr("currencycode"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lm;
	}

	public ListModelList<Mdeclinecode> getMdeclinecode() {
		ListModelList<Mdeclinecode> lm = null;
		try {
			lm = new ListModelList<Mdeclinecode>(new MdeclinecodeDAO().listByFilter("0=0", "declinedesc"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lm;
	}

	public ListModelList<Mrmgroup> getMrmgroupmodel() {
		ListModelList<Mrmgroup> lm = null;
		try {
			lm = new ListModelList<Mrmgroup>(new MrmgroupDAO().listByFilter("0=0", "rmgroupcode"));
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

	public Tpipelinepart getObjPart() {
		return objPart;
	}

	public void setObjPart(Tpipelinepart objPart) {
		this.objPart = objPart;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public BigDecimal getSelfportionamount() {
		return selfportionamount;
	}

	public void setSelfportionamount(BigDecimal selfportionamount) {
		this.selfportionamount = selfportionamount;
	}

	public BigDecimal getSelfportion() {
		return selfportion;
	}

	public void setSelfportion(BigDecimal selfportion) {
		this.selfportion = selfportion;
	}

	public BigDecimal getFeeamount() {
		return feeamount;
	}

	public void setFeeamount(BigDecimal feeamount) {
		this.feeamount = feeamount;
	}

	public Mrmgroup getObjRmgroup() {
		return objRmgroup;
	}

	public void setObjRmgroup(Mrmgroup objRmgroup) {
		this.objRmgroup = objRmgroup;
	}

}
