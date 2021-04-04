package com.sdd.sindikasi.viewmodel;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Tabs;

public class IndexVm {
	
	@Wire
	private Div divPage;
	@Wire
	private Tabs tabs;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		doTab("period");
	}
	
	@Command
	public void doTab(@BindingParam("tab") String tab) {
		divPage.getChildren().clear();
		String page = ""; 
		if (tab.equals("period")) {
			page = "/view/report/pipelinesumperiod.zul";    		
    	} else if (tab.equals("unit")) {
    		page = "/view/report/pipelinesumunit.zul";
    	} else if (tab.equals("sector")) {
    		page = "/view/report/pipelinesumsector.zul";
    	}
    	Executions.createComponents(page, divPage, null);
    }

}
