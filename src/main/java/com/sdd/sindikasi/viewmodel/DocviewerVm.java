package com.sdd.sindikasi.viewmodel;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Iframe;

public class DocviewerVm {

	@Wire
	private Iframe iframe;

	@NotifyChange("*")
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("docpath") String docpath) {
		Selectors.wireComponents(view, this, false);
		try {
			System.out.println(docpath);
			iframe.setSrc(docpath);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
