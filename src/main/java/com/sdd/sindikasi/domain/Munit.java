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
 * The persistent class for the munit database table.
 * 
 */
@Entity
@NamedQuery(name = "Munit.findAll", query = "SELECT m FROM Munit m")
public class Munit implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer munitpk;
	private Date lastupdated;
	private String unitcode;
	private String unitcostcenter;
	private String unitname;
	private String updatedby;
	private List<Tpipeline> tpipelines;

	public Munit() {
	}

	@Id
	@SequenceGenerator(name = "MUNIT_MUNITPK_GENERATOR", sequenceName = "MUNIT_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MUNIT_MUNITPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getMunitpk() {
		return this.munitpk;
	}

	public void setMunitpk(Integer munitpk) {
		this.munitpk = munitpk;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastupdated() {
		return this.lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	@Column(length = 5)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getUnitcode() {
		return this.unitcode;
	}

	public void setUnitcode(String unitcode) {
		this.unitcode = unitcode;
	}

	@Column(length = 10)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getUnitcostcenter() {
		return this.unitcostcenter;
	}

	public void setUnitcostcenter(String unitcostcenter) {
		this.unitcostcenter = unitcostcenter;
	}

	@Column(length = 50)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getUnitname() {
		return this.unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

	@Column(length = 30)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	// bi-directional many-to-one association to Tpipeline
	@OneToMany(mappedBy = "munit")
	public List<Tpipeline> getTpipelines() {
		return this.tpipelines;
	}

	public void setTpipelines(List<Tpipeline> tpipelines) {
		this.tpipelines = tpipelines;
	}

	public Tpipeline addTpipeline(Tpipeline tpipeline) {
		getTpipelines().add(tpipeline);
		tpipeline.setMunit(this);

		return tpipeline;
	}

	public Tpipeline removeTpipeline(Tpipeline tpipeline) {
		getTpipelines().remove(tpipeline);
		tpipeline.setMunit(null);

		return tpipeline;
	}

}