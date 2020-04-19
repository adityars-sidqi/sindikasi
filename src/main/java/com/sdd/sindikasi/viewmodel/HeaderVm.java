package com.sdd.sindikasi.viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Window;

public class HeaderVm {
	
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
		
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);						
	}
	
	@Command
	public void doChangePassword() {													
		Window win = (Window) Executions.createComponents("/changepass.zul", null, null);
		win.setClosable(true);							
		win.doModal();		
	}
	
	@Command
	public void doLogout() {													
		if (zkSession.getAttribute("oUser") != null) {				
			zkSession.removeAttribute("oUser");			
		}	
		Executions.sendRedirect("/logout.zul");
	}
}
