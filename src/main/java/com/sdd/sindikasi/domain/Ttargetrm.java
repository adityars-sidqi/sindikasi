package com.sdd.sindikasi.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Type;

/**
 * The persistent class for the ttargetrm database table.
 * 
 */
@Entity
@NamedQuery(name="Ttargetrm.findAll", query="SELECT t FROM Ttargetrm t")
public class Ttargetrm implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer ttargetrmpk;
	private Date lastupdated;
	private BigDecimal targetamount;
	private String targetyear;
	private String updatedby;
	private Mrm mrm;
	private Mrmgroup mrmgroup;

	public Ttargetrm() {
	}


	@Id
	@SequenceGenerator(name="TTARGETRM_TTARGETRMPK_GENERATOR", sequenceName="TTARGETRM_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TTARGETRM_TTARGETRMPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTtargetrmpk() {
		return this.ttargetrmpk;
	}

	public void setTtargetrmpk(Integer ttargetrmpk) {
		this.ttargetrmpk = ttargetrmpk;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastupdated() {
		return this.lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}


	public BigDecimal getTargetamount() {
		return this.targetamount;
	}

	public void setTargetamount(BigDecimal targetamount) {
		this.targetamount = targetamount;
	}

	@Column(length = 4)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getTargetyear() {
		return this.targetyear;
	}

	public void setTargetyear(String targetyear) {
		this.targetyear = targetyear;
	}

	@Column(length = 30)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}


	//bi-directional many-to-one association to Mrm
	@ManyToOne
	@JoinColumn(name="mrmfk")
	@ForeignKey(name = "TTARGETRM_FK2")
	public Mrm getMrm() {
		return this.mrm;
	}

	public void setMrm(Mrm mrm) {
		this.mrm = mrm;
	}


	//bi-directional many-to-one association to Mrmgroup
	@ManyToOne
	@JoinColumn(name="mrmgroupfk")
	@ForeignKey(name = "TTARGETRM_FK1")
	public Mrmgroup getMrmgroup() {
		return this.mrmgroup;
	}

	public void setMrmgroup(Mrmgroup mrmgroup) {
		this.mrmgroup = mrmgroup;
	}

}