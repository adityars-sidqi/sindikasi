package com.sdd.sindikasi.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
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
 * The persistent class for the tpipelinepartagent database table.
 * 
 */
@Entity
@NamedQuery(name = "Tpipelinepartagent.findAll", query = "SELECT t FROM Tpipelinepartagent t")
public class Tpipelinepartagent implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tpipelinepartagentpk;
	private String currency;
	private BigDecimal feeamount;
	private Date lastupdated;
	private String updatedby;
	private Magenttype magenttype;
	private Tpipelinepart tpipelinepart;

	public Tpipelinepartagent() {
	}

	@Id
	@SequenceGenerator(name = "TPIPELINEPARTAGENT_TPIPELINEPARTAGENTPK_GENERATOR", sequenceName = "TPIPELINEPARTAGENT_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TPIPELINEPARTAGENT_TPIPELINEPARTAGENTPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTpipelinepartagentpk() {
		return this.tpipelinepartagentpk;
	}

	public void setTpipelinepartagentpk(Integer tpipelinepartagentpk) {
		this.tpipelinepartagentpk = tpipelinepartagentpk;
	}

	@Column(length = 5)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getFeeamount() {
		return this.feeamount;
	}

	public void setFeeamount(BigDecimal feeamount) {
		this.feeamount = feeamount;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastupdated() {
		return this.lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	@Column(length = 30)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	// bi-directional many-to-one association to Magenttype
	@ManyToOne
	@JoinColumn(name = "magenttypefk")
	@ForeignKey(name = "TPIPELINEPARTAGENT_FK2")
	public Magenttype getMagenttype() {
		return this.magenttype;
	}

	public void setMagenttype(Magenttype magenttype) {
		this.magenttype = magenttype;
	}

	// bi-directional many-to-one association to Tpipelinepart
	@ManyToOne
	@JoinColumn(name = "tpipelinepartfk")
	@ForeignKey(name = "TPIPELINEPARTAGENT_FK1")
	public Tpipelinepart getTpipelinepart() {
		return this.tpipelinepart;
	}

	public void setTpipelinepart(Tpipelinepart tpipelinepart) {
		this.tpipelinepart = tpipelinepart;
	}

}