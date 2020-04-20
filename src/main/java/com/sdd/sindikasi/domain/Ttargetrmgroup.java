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
 * The persistent class for the ttargetrmgroup database table.
 * 
 */
@Entity
@NamedQuery(name = "Ttargetrmgroup.findAll", query = "SELECT t FROM Ttargetrmgroup t")
public class Ttargetrmgroup implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer ttargetrmgrouppk;
	private Date lastupdated;
	private BigDecimal targetamount;
	private String targetyear;
	private String updatedby;
	private Mrmgroup mrmgroup;

	public Ttargetrmgroup() {
	}

	@Id
	@SequenceGenerator(name = "TTARGETRMGROUP_TTARGETRMGROUPPK_GENERATOR", sequenceName = "TTARGETRMGROUP_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TTARGETRMGROUP_TTARGETRMGROUPPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTtargetrmgrouppk() {
		return this.ttargetrmgrouppk;
	}

	public void setTtargetrmgrouppk(Integer ttargetrmgrouppk) {
		this.ttargetrmgrouppk = ttargetrmgrouppk;
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

	// bi-directional many-to-one association to Mrmgroup
	@ManyToOne
	@JoinColumn(name = "mrmgroupfk")
	@ForeignKey(name = "TTARGETRMGROUP_FK1")
	public Mrmgroup getMrmgroup() {
		return this.mrmgroup;
	}

	public void setMrmgroup(Mrmgroup mrmgroup) {
		this.mrmgroup = mrmgroup;
	}

}