package com.sdd.utils.db;

import org.hibernate.envers.RevisionListener;
import org.zkoss.zk.ui.Sessions;

import com.sdd.ecollection.domain.AuditLog;
import com.sdd.ecollection.domain.Muser;

public class AuditLogListeners implements RevisionListener {

	public void newRevision(Object revisionEntity) {
		AuditLog obj = (AuditLog) revisionEntity;
		Muser oUser = (Muser) Sessions.getCurrent().getAttribute("oUser");
		obj.setUserid(oUser.getUserid());
	}

}
