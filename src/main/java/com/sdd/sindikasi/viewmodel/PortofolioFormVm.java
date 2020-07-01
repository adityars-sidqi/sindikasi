package com.sdd.sindikasi.viewmodel;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;

import com.sdd.sindikasi.domain.Muser;
import com.sdd.sindikasi.domain.Tpipeline;
import com.sdd.sindikasi.domain.Tporto;

public class PortofolioFormVm {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;

	private Session session;
	private Transaction transaction;

	private Tporto objForm;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);

		oUser = (Muser) zkSession.getAttribute("oUser");

		doReset();
	}

	@NotifyChange("*")
	public void doReset() {
		objForm = new Tporto();
	}

	public Tporto getObjForm() {
		return objForm;
	}

	public void setObjForm(Tporto objForm) {
		this.objForm = objForm;
	}

}
