package com.sdd.utils.scheduler;

import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.util.WebAppCleanup;
import org.zkoss.zk.ui.util.WebAppInit;

import com.sdd.ecollection.handler.ScheduleManager;

public class SchedulerService implements WebAppInit, WebAppCleanup {

	public void cleanup(WebApp wapp) throws Exception {

	}

	public void init(WebApp wapp) throws Exception {
		// new ScheduleManager().initializer(wapp.getRealPath("/"));
		new ScheduleManager().initializer(wapp.getRealPath(""));
	}
}
