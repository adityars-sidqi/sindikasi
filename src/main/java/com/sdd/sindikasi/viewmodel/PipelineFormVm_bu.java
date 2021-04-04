package com.sdd.sindikasi.viewmodel;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
import org.zkoss.zul.Caption;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import com.sdd.sindikasi.dao.MagenttypeDAO;
import com.sdd.sindikasi.dao.McurrencyDAO;
import com.sdd.sindikasi.dao.MdebiturDAO;
import com.sdd.sindikasi.dao.MdeclinecodeDAO;
import com.sdd.sindikasi.dao.MrmDAO;
import com.sdd.sindikasi.dao.MsectorDAO;
import com.sdd.sindikasi.dao.MunitDAO;
import com.sdd.sindikasi.dao.TcounterengineDAO;
import com.sdd.sindikasi.dao.TmemoDAO;
import com.sdd.sindikasi.dao.TpipelineDAO;
import com.sdd.sindikasi.dao.TpipelinepartDAO;
import com.sdd.sindikasi.dao.TpipelinepartagentDAO;
import com.sdd.sindikasi.dao.TportoDAO;
import com.sdd.sindikasi.dao.TportopartDAO;
import com.sdd.sindikasi.domain.Magenttype;
import com.sdd.sindikasi.domain.Mdebitur;
import com.sdd.sindikasi.domain.Mdeclinecode;
import com.sdd.sindikasi.domain.Mrm;
import com.sdd.sindikasi.domain.Msector;
import com.sdd.sindikasi.domain.Munit;
import com.sdd.sindikasi.domain.Muser;
import com.sdd.sindikasi.domain.Tmemo;
import com.sdd.sindikasi.domain.Tpipeline;
import com.sdd.sindikasi.domain.Tpipelinepart;
import com.sdd.sindikasi.domain.Tpipelinepartagent;
import com.sdd.sindikasi.domain.Tporto;
import com.sdd.sindikasi.domain.Tportopart;
import com.sdd.sindikasi.utils.AppData;
import com.sdd.sindikasi.utils.AppUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class PipelineFormVm_bu {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;

	private SimpleDateFormat datetimelocalFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private DecimalFormat decimalLocalFormatter = new DecimalFormat("#,###");

	private Session session;
	private Transaction transaction;

	private boolean isInsert;
	private boolean isSaved;

	private int pageStartNumber;

	private Tpipeline objForm;
	private Tpipelinepart objPipelinepart;

	private MagenttypeDAO magenttypeDao = new MagenttypeDAO();

	private TpipelineDAO oDao = new TpipelineDAO();
	private TpipelinepartDAO tpipelinepartDao = new TpipelinepartDAO();
	private TpipelinepartagentDAO tpipelinepartagentDao = new TpipelinepartagentDAO();
	private TmemoDAO tmemoDao = new TmemoDAO();

	private TportoDAO tportoDao = new TportoDAO();
	private TportopartDAO tportopartDao = new TportopartDAO();

	private List<Tpipelinepart> listPipelinepart;
	private List<Tmemo> listMemo;

	private List<Magenttype> listMagenttypeself = new ArrayList<Magenttype>();
	private Map<Integer, Magenttype> mapMagenttypeself = new HashMap<Integer, Magenttype>();

	private List<Magenttype> listMagenttype = new ArrayList<Magenttype>();
	private Map<Integer, Magenttype> mapMagenttype = new HashMap<Integer, Magenttype>();

	private List<Tpipelinepartagent> listPipelinepartagent = new ArrayList<Tpipelinepartagent>();

	private String participantname;
	private String currencypart;
	private BigDecimal portionamount = new BigDecimal(0);
	private BigDecimal kipokok;
	private BigDecimal kiidc;
	private BigDecimal kmk;
	private BigDecimal ncl;
	private BigDecimal feeamount;
	private String picname;
	private String pichp;
	private String picemail;

	private String memo;

	private BigDecimal selfportion;
	private BigDecimal selfportionamount = new BigDecimal(0);

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
	private Combobox cbCurrencypart;
	@Wire
	private Grid gridAgent;
	@Wire
	private Checkbox chkAllagent;
	@Wire
	private Grid gridParticipant;
	@Wire
	private Grid gridFormMemo;
	@Wire
	private Grid gridMemo;
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

				for (Tpipelinepart tpipelinepart : tpipelinepartDao
						.listByFilter("tpipeline.tpipelinepk = " + objForm.getTpipelinepk(), "participantname")) {
					if (tpipelinepart.getIsself().equals("Y"))
						objPipelinepart = tpipelinepart;
					else
						doAddGridPipelinepart(tpipelinepart, objForm);
				}

				//doCalculate(objPipelinepart.getPortionamount());

				for (Tpipelinepartagent tpipelinepartagent : tpipelinepartagentDao.listByFilter(
						"tpipelinepart.tpipelinepartpk = " + objPipelinepart.getTpipelinepartpk(),
						"tpipelinepartagent")) {
					mapMagenttypeself.put(tpipelinepartagent.getMagenttype().getMagenttypepk(),
							tpipelinepartagent.getMagenttype());
				}

				for (Tmemo tmemo : tmemoDao.listByFilter("tpipeline.tpipelinepk = " + objForm.getTpipelinepk(),
						"createdtime")) {
					doAddGridMemo(tmemo, objForm);
				}

				doLoadAgentself();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Command
	@NotifyChange("*")
	public void doSave() {
		try {

			isSaved = true;

			session = StoreHibernateUtil.openSession();
			transaction = session.beginTransaction();

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

			objForm.setSelfportion(selfportionamount);
			objForm.setFeepercent(
					objForm.getFeeamount().divide(objForm.getProjectamount()).multiply(new BigDecimal(100)));
			objForm.setLastupdated(new Date());
			objForm.setUpdatedby(oUser.getUserid());

			oDao.save(session, objForm);

			objPipelinepart.setTpipeline(objForm);
			objPipelinepart.setIsself("Y");
			objPipelinepart.setFeeamount(objForm.getFeeamount());
			objPipelinepart.setParticipantname("BNI");
			objPipelinepart.setPortionamount(objForm.getSelfportion());
			BigDecimal portionpercent = objPipelinepart.getPortionamount().divide(objForm.getProjectamount())
					.multiply(new BigDecimal(100));
			objPipelinepart.setPortionpercent(portionpercent);
			objPipelinepart.setLastupdated(new Date());
			objPipelinepart.setUpdatedby(oUser.getUserid());
			tpipelinepartDao.save(session, objPipelinepart);

			for (Magenttype obj : listMagenttypeself) {
				Tpipelinepartagent objPipelinepartagentself = new Tpipelinepartagent();
				objPipelinepartagentself.setMagenttype(obj);
				objPipelinepartagentself.setTpipelinepart(objPipelinepart);
				objPipelinepartagentself.setLastupdated(new Date());
				objPipelinepartagentself.setUpdatedby(oUser.getUserid());

				tpipelinepartagentDao.save(session, objPipelinepartagentself);
			}

			for (Tpipelinepart obj : listPipelinepart) {
				obj.setTpipeline(objForm);
				obj.setIsself("N");
				portionpercent = obj.getPortionamount().divide(objForm.getProjectamount())
						.multiply(new BigDecimal(100));
				obj.setPortionpercent(portionpercent);
				obj.setLastupdated(new Date());
				obj.setUpdatedby(oUser.getUserid());

				tpipelinepartDao.save(session, obj);

			}

			for (Tpipelinepartagent obj : listPipelinepartagent) {
				obj.setLastupdated(new Date());
				obj.setUpdatedby(oUser.getUserid());

				tpipelinepartagentDao.save(session, obj);
			}

			for (Tmemo obj : listMemo) {
				obj.setTpipeline(objForm);

				tmemoDao.save(session, obj);
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
	@NotifyChange("gridAgent")
	public void doLoadAgentself() {

		try {
			chkAllagentself.setChecked(false);
			gridAgentself.getRows().getChildren().clear();
			for (Magenttype obj : magenttypeDao.listByFilter("0=0", "agentcode")) {
				doAddGridAgentself(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void doAddGridAgentself(final Magenttype obj) {
		final Row row = new Row();

		final Checkbox chk = new Checkbox();
		chk.setAttribute("obj", obj);
		chk.addEventListener(Events.ON_CHECK, new EventListener<Event>() {

			public void onEvent(Event event) throws Exception {
				if (chk.isChecked()) {
					listMagenttypeself.add((Magenttype) chk.getAttribute("obj"));
				} else {
					listMagenttypeself.remove(chk.getAttribute("obj"));
				}
			}
		});
		if (mapMagenttypeself.get(obj.getMagenttypepk()) != null) {
			chk.setChecked(true);
			listMagenttypeself.add(obj);
		}
		row.appendChild(chk);

		row.appendChild(new Label(obj.getAgentcode()));

		gridAgentself.getRows().appendChild(row);
	}

	@Command
	@NotifyChange("gridAgent")
	public void doLoadAgent() {

		try {
			chkAllagent.setChecked(false);
			gridAgent.getRows().getChildren().clear();
			for (Magenttype obj : magenttypeDao.listByFilter("0=0", "agentcode")) {
				doAddGridAgent(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void doAddGridAgent(final Magenttype obj) {
		final Row row = new Row();

		final Checkbox chk = new Checkbox();
		chk.setAttribute("obj", obj);
		chk.addEventListener(Events.ON_CHECK, new EventListener<Event>() {

			public void onEvent(Event event) throws Exception {
				if (chk.isChecked()) {
					listMagenttype.add((Magenttype) chk.getAttribute("obj"));
				} else {
					listMagenttype.remove(chk.getAttribute("obj"));
				}
			}
		});
		if (mapMagenttype.get(obj.getMagenttypepk()) != null) {
			chk.setChecked(true);
			listMagenttype.add(obj);
		}
		row.appendChild(chk);

		row.appendChild(new Label(obj.getAgentcode()));

		gridAgent.getRows().appendChild(row);
	}

	@Command
	@NotifyChange("gridAgent")
	public void doCheckedallAgent(@BindingParam("checked") boolean checked) {
		try {
			List<Row> components = gridAgent.getRows().getChildren();
			for (Row comp : components) {
				Checkbox chk = (Checkbox) comp.getChildren().get(0);
				if (checked) {
					chk.setChecked(true);
					listMagenttype.remove(chk.getAttribute("obj"));
					listMagenttype.add((Magenttype) chk.getAttribute("obj"));
				} else {
					chk.setChecked(false);
					listMagenttype.remove(chk.getAttribute("obj"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange({ "participantname", "currencypart", "kipokok", "kiidc", "kmk", "ncl", "feeamount", "picemail",
			"pichp", "picname" })
	public void doSavePipelinepart() {
		if (participantname.trim().length() > 0 && currencypart.trim().length() > 0
				&& kipokok.compareTo(BigDecimal.ZERO) > 0 && kiidc.compareTo(BigDecimal.ZERO) > 0
				&& kmk.compareTo(BigDecimal.ZERO) > 0 && ncl.compareTo(BigDecimal.ZERO) > 0
				&& feeamount.compareTo(BigDecimal.ZERO) > 0 && picemail.trim().length() > 0 && pichp.trim().length() > 0
				&& picname.trim().length() > 0)
			try {

				final Tpipelinepart obj = new Tpipelinepart();
				obj.setParticipantname(participantname);
				obj.setCurrency(currencypart);

				portionamount = kipokok.add(kiidc).add(kmk).add(ncl);
				obj.setPortionamount(portionamount);
				obj.setKipokok(kipokok);
				obj.setKiidc(kiidc);
				obj.setKmk(kmk);
				obj.setNcl(ncl);
				obj.setFeeamount(feeamount);
				obj.setPicname(picname);
				obj.setPichp(pichp);
				obj.setPicemail(picemail);

				listPipelinepart.add(obj);

				for (Magenttype objAgenttype : listMagenttype) {
					final Tpipelinepartagent objTpipelinepartagent = new Tpipelinepartagent();
					objTpipelinepartagent.setTpipelinepart(obj);
					objTpipelinepartagent.setMagenttype(objAgenttype);

					listPipelinepartagent.add(objTpipelinepartagent);
				}

//				mapPipelinepartagent.put(obj, listPipelinepartagent);

				doAddGridPipelinepart(obj, null);

				participantname = "";
				currencypart = "";
				portionamount = null;
				kipokok = null;
				kiidc = null;
				kmk = null;
				ncl = null;
				feeamount = null;
				picemail = "";
				pichp = "";
				picname = "";
				cbCurrencypart.setValue("");
//				listPipelinepartagent = new ArrayList<Tpipelinepartagent>();
				listMagenttype = new ArrayList<Magenttype>();

			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	private void doAddGridPipelinepart(final Tpipelinepart obj, Tpipeline tpipeline) {
		final Row row = new Row();
		row.appendChild(new Label(obj.getParticipantname()));
//		row.appendChild(new Label(decimalLocalFormatter.format(obj.getPortionpercent())));
		row.appendChild(new Label(decimalLocalFormatter.format(obj.getPortionamount())));

		if (tpipeline != null)
			row.appendChild(new Label(""));
		else {
			Button btnDel = new Button("Delete");
			btnDel.setZclass("btn btn-sm btn-danger");
			btnDel.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

				public void onEvent(Event event) throws Exception {
					listPipelinepart.remove(obj);
					gridParticipant.getRows().removeChild(row);
				}

			});
			row.appendChild(btnDel);
		}

		gridParticipant.getRows().appendChild(row);
	}

	@Command
	@NotifyChange("memo")
	public void doSaveMemo() {
		if (memo != null && memo.trim().length() > 0)
			try {

				final Tmemo obj = new Tmemo();
				obj.setMemo(memo);
				obj.setCreatedtime(new Date());
				obj.setCreatedby(oUser.getUserid());
				listMemo.add(obj);

				doAddGridMemo(obj, null);

				memo = "";

			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	private void doAddGridMemo(final Tmemo obj, Tpipeline tpipeline) {
		final Row row = new Row();
		row.appendChild(new Label(obj.getMemo()));
		row.appendChild(new Label(obj.getCreatedby()));
		row.appendChild(new Label(datetimelocalFormatter.format(obj.getCreatedtime())));

		if (tpipeline != null)
			row.appendChild(new Label(""));
		else {
			Button btnDel = new Button("Delete");
			btnDel.setZclass("btn btn-sm btn-danger");
			btnDel.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

				public void onEvent(Event event) throws Exception {
					listMemo.remove(obj);
					gridMemo.getRows().removeChild(row);
				}

			});
			row.appendChild(btnDel);
		}

		gridMemo.getRows().appendChild(row);
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

	/*@Command
	@NotifyChange("selfportionamount")
	public void doCalculate(@BindingParam("amount") BigDecimal amount) {
		selfportionamount = selfportionamount.add(amount);
	}

	@Command
	@NotifyChange("portionamount")
	public void doCalculatePart(@BindingParam("amount") BigDecimal amount) {
		portionamount = portionamount.add(amount);
	}*/

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

			objForm = new Tpipeline();
			objPipelinepart = new Tpipelinepart();

			isInsert = true;
			isSaved = false;
			listPipelinepart = new ArrayList<Tpipelinepart>();
			listMemo = new ArrayList<Tmemo>();

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

			doLoadAgentself();
			doLoadAgent();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Validator getValidator() {
		return new AbstractValidator() {

			public void validate(ValidationContext ctx) {

				Mdebitur mdebitur = (Mdebitur) ctx.getProperties("mdebitur")[0].getValue();
				Msector msector = (Msector) ctx.getProperties("msector")[0].getValue();
				String project = (String) ctx.getProperties("project")[0].getValue();
				Mrm mrm = (Mrm) ctx.getProperties("mrm")[0].getValue();
				Munit munit = (Munit) ctx.getProperties("munit")[0].getValue();
				String currency = (String) ctx.getProperties("currency")[0].getValue();
				BigDecimal projectamount = (BigDecimal) ctx.getProperties("projectamount")[0].getValue();
				BigDecimal feeamount = (BigDecimal) ctx.getProperties("feeamount")[0].getValue();
				Date targetpk = (Date) ctx.getProperties("targetpk")[0].getValue();
				String notes = (String) ctx.getProperties("notes")[0].getValue();

				if (mdebitur == null)
					this.addInvalidMessage(ctx, "mdebitur", Labels.getLabel("common.validator.empty"));
				if (msector == null)
					this.addInvalidMessage(ctx, "msector", Labels.getLabel("common.validator.empty"));
				if (project == null || "".equals(project.trim()))
					this.addInvalidMessage(ctx, "project", Labels.getLabel("common.validator.empty"));
				if (mrm == null)
					this.addInvalidMessage(ctx, "mrm", Labels.getLabel("common.validator.empty"));

				if (munit == null)
					this.addInvalidMessage(ctx, "munit", Labels.getLabel("common.validator.empty"));
				if (currency == null || "".equals(currency.trim()))
					this.addInvalidMessage(ctx, "currency", Labels.getLabel("common.validator.empty"));
				if (projectamount == null || projectamount.compareTo(BigDecimal.ZERO) <= 0)
					this.addInvalidMessage(ctx, "projectamount", Labels.getLabel("common.validator.empty"));
				if (feeamount == null || feeamount.compareTo(BigDecimal.ZERO) <= 0)
					this.addInvalidMessage(ctx, "feeamount", Labels.getLabel("common.validator.empty"));
				if (targetpk == null)
					this.addInvalidMessage(ctx, "targetpk", Labels.getLabel("common.validator.empty"));
				if (notes == null || "".equals(notes.trim()))
					this.addInvalidMessage(ctx, "notes", Labels.getLabel("common.validator.empty"));
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

	public Tpipeline getObjForm() {
		return objForm;
	}

	public void setObjForm(Tpipeline objForm) {
		this.objForm = objForm;
	}

	public Tpipelinepart getObjPipelinepart() {
		return objPipelinepart;
	}

	public void setObjPipelinepart(Tpipelinepart objPipelinepart) {
		this.objPipelinepart = objPipelinepart;
	}

	public String getParticipantname() {
		return participantname;
	}

	public void setParticipantname(String participantname) {
		this.participantname = participantname;
	}

	public String getCurrencypart() {
		return currencypart;
	}

	public void setCurrencypart(String currencypart) {
		this.currencypart = currencypart;
	}

	public BigDecimal getPortionamount() {
		return portionamount;
	}

	public void setPortionamount(BigDecimal portionamount) {
		this.portionamount = portionamount;
	}

	public BigDecimal getKipokok() {
		return kipokok;
	}

	public void setKipokok(BigDecimal kipokok) {
		this.kipokok = kipokok;
	}

	public BigDecimal getKiidc() {
		return kiidc;
	}

	public void setKiidc(BigDecimal kiidc) {
		this.kiidc = kiidc;
	}

	public BigDecimal getKmk() {
		return kmk;
	}

	public void setKmk(BigDecimal kmk) {
		this.kmk = kmk;
	}

	public BigDecimal getNcl() {
		return ncl;
	}

	public void setNcl(BigDecimal ncl) {
		this.ncl = ncl;
	}

	public BigDecimal getFeeamount() {
		return feeamount;
	}

	public void setFeeamount(BigDecimal feeamount) {
		this.feeamount = feeamount;
	}

	public String getPicname() {
		return picname;
	}

	public void setPicname(String picname) {
		this.picname = picname;
	}

	public String getPichp() {
		return pichp;
	}

	public void setPichp(String pichp) {
		this.pichp = pichp;
	}

	public String getPicemail() {
		return picemail;
	}

	public void setPicemail(String picemail) {
		this.picemail = picemail;
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

}
