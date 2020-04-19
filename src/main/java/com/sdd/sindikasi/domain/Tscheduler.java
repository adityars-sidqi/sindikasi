package com.sdd.sindikasi.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

/**
 * The persistent class for the tscheduler database table.
 * 
 */
@Entity
@NamedQuery(name = "Tscheduler.findAll", query = "SELECT t FROM Tscheduler t")
public class Tscheduler implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tschedulerpk;
	private String jobclass;
	private Date lastupdated;
	private Integer repeatinterval;
	private String schedulerdesc;
	private String schedulergroup;
	private String schedulername;
	private String schedulerrepeattype;
	private String schedulerstatus;
	private String updatedby;

	public Tscheduler() {
	}

	@Id
	@SequenceGenerator(name = "TSCHEDULER_TSCHEDULERPK_GENERATOR")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TSCHEDULER_TSCHEDULERPK_GENERATOR")
	public Integer getTschedulerpk() {
		return this.tschedulerpk;
	}

	public void setTschedulerpk(Integer tschedulerpk) {
		this.tschedulerpk = tschedulerpk;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUserType")
	public String getJobclass() {
		return this.jobclass;
	}

	public void setJobclass(String jobclass) {
		this.jobclass = jobclass;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastupdated() {
		return this.lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	public Integer getRepeatinterval() {
		return this.repeatinterval;
	}

	public void setRepeatinterval(Integer repeatinterval) {
		this.repeatinterval = repeatinterval;
	}

	@Column(length = 200)
	@Type(type = "com.sdd.utils.usertype.TrimUserType")
	public String getSchedulerdesc() {
		return this.schedulerdesc;
	}

	public void setSchedulerdesc(String schedulerdesc) {
		this.schedulerdesc = schedulerdesc;
	}

	@Column(length = 30)
	@Type(type = "com.sdd.utils.usertype.TrimUserType")
	public String getSchedulergroup() {
		return this.schedulergroup;
	}

	public void setSchedulergroup(String schedulergroup) {
		this.schedulergroup = schedulergroup;
	}

	@Column(length = 30)
	@Type(type = "com.sdd.utils.usertype.TrimUserType")
	public String getSchedulername() {
		return this.schedulername;
	}

	public void setSchedulername(String schedulername) {
		this.schedulername = schedulername;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUserType")
	public String getSchedulerrepeattype() {
		return this.schedulerrepeattype;
	}

	public void setSchedulerrepeattype(String schedulerrepeattype) {
		this.schedulerrepeattype = schedulerrepeattype;
	}

	@Column(length = 3)
	@Type(type = "com.sdd.utils.usertype.TrimUserType")
	public String getSchedulerstatus() {
		return this.schedulerstatus;
	}

	public void setSchedulerstatus(String schedulerstatus) {
		this.schedulerstatus = schedulerstatus;
	}

	@Column(length = 15)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

}