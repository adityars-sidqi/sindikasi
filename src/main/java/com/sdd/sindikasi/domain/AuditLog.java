package com.sdd.sindikasi.domain;

import javax.persistence.Entity;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import com.sdd.utils.db.AuditLogListeners;

@Entity(name = "REVINFO")
@RevisionEntity(AuditLogListeners.class)
public class AuditLog extends DefaultRevisionEntity {

	private String userid;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

}
