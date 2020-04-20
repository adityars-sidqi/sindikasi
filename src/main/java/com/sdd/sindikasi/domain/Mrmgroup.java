package com.sdd.sindikasi.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

/**
 * The persistent class for the mrmgroup database table.
 * 
 */
@Entity
@NamedQuery(name = "Mrmgroup.findAll", query = "SELECT m FROM Mrmgroup m")
public class Mrmgroup implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer mrmgrouppk;
	private Date lastupdated;
	private String rmgroupcode;
	private String rmgroupname;
	private String updatedby;

	public Mrmgroup() {
	}

	@Id
	@SequenceGenerator(name = "MRMGROUP_MRMGROUPPK_GENERATOR", sequenceName = "MRMGROUP_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MRMGROUP_MRMGROUPPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getMrmgrouppk() {
		return this.mrmgrouppk;
	}

	public void setMrmgrouppk(Integer mrmgrouppk) {
		this.mrmgrouppk = mrmgrouppk;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastupdated() {
		return this.lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	@Column(length = 10)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getRmgroupcode() {
		return this.rmgroupcode;
	}

	public void setRmgroupcode(String rmgroupcode) {
		this.rmgroupcode = rmgroupcode;
	}

	@Column(length = 40)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getRmgroupname() {
		return this.rmgroupname;
	}

	public void setRmgroupname(String rmgroupname) {
		this.rmgroupname = rmgroupname;
	}

	@Column(length = 30)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

}