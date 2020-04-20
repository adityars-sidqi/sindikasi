package com.sdd.sindikasi.viewmodel;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;

import com.sdd.sindikasi.domain.Muser;

public class IndexVm {
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;

	@Wire
	private Div divWelcome;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);

		Executions.createComponents("/view/dashboard/dashboardpipeline.zul", divWelcome, null);
		Executions.createComponents("/view/dashboard/dashboardportofolio.zul", divWelcome, null);
	}
}
