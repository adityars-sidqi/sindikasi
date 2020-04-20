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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;



/**
 * The persistent class for the MDECLINECODE database table.
 * 
 */
@Entity
@Table(name="MDECLINECODE")
@NamedQuery(name="Mdeclinecode.findAll", query="SELECT m FROM Mdeclinecode m")
public class Mdeclinecode implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer mdeclinecodepk;
	private String declinecode;
	private String declinedesc;
	private Date lastupdated;
	private String updatedby;

	public Mdeclinecode() {
	}


	@Id
	@SequenceGenerator(name="MDECLINECODE_MDECLINECODEPK_GENERATOR", sequenceName="MDECLINECODE_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MDECLINECODE_MDECLINECODEPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getMdeclinecodepk() {
		return this.mdeclinecodepk;
	}

	public void setMdeclinecodepk(Integer mdeclinecodepk) {
		this.mdeclinecodepk = mdeclinecodepk;
	}


	@Column(length = 3)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getDeclinecode() {
		return this.declinecode;
	}

	public void setDeclinecode(String declinecode) {
		this.declinecode = declinecode;
	}


	@Column(length = 70)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getDeclinedesc() {
		return this.declinedesc;
	}

	public void setDeclinedesc(String declinedesc) {
		this.declinedesc = declinedesc;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastupdated() {
		return this.lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
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