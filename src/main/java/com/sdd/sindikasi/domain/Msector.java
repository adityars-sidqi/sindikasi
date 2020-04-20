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
 * The persistent class for the msector database table.
 * 
 */
@Entity
@NamedQuery(name = "Msector.findAll", query = "SELECT m FROM Msector m")
public class Msector implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer msectorpk;
	private Date lastupdated;
	private String sectorname;
	private String updatedby;

	public Msector() {
	}

	@Id
	@SequenceGenerator(name = "MSECTOR_MSECTORPK_GENERATOR", sequenceName = "MSECTOR_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MSECTOR_MSECTORPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getMsectorpk() {
		return this.msectorpk;
	}

	public void setMsectorpk(Integer msectorpk) {
		this.msectorpk = msectorpk;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastupdated() {
		return this.lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	@Column(length = 50)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getSectorname() {
		return this.sectorname;
	}

	public void setSectorname(String sectorname) {
		this.sectorname = sectorname;
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