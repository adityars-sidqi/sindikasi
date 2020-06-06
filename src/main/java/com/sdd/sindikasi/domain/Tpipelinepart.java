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
 * The persistent class for the tpipelinepart database table.
 * 
 */
@Entity
@NamedQuery(name = "Tpipelinepart.findAll", query = "SELECT t FROM Tpipelinepart t")
public class Tpipelinepart implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tpipelinepartpk;
	private String currency;
	private String isself;
	private BigDecimal kiidc;
	private BigDecimal kipokok;
	private BigDecimal kmk;
	private Date lastupdated;
	private BigDecimal ncl;
	private String participantname;
	private String picemail;
	private String pichp;
	private String picname;
	private BigDecimal portionamount;
	private BigDecimal portionpercent;
	private String updatedby;
	private Tpipeline tpipeline;

	public Tpipelinepart() {
	}

	@Id
	@SequenceGenerator(name = "TPIPELINEPART_TPIPELINEPARTPK_GENERATOR", sequenceName = "TPIPELINEPART_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TPIPELINEPART_TPIPELINEPARTPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTpipelinepartpk() {
		return this.tpipelinepartpk;
	}

	public void setTpipelinepartpk(Integer tpipelinepartpk) {
		this.tpipelinepartpk = tpipelinepartpk;
	}

	@Column(length = 5)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(length = 1)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getIsself() {
		return isself;
	}

	public void setIsself(String isself) {
		this.isself = isself;
	}

	public BigDecimal getKiidc() {
		return kiidc;
	}

	public void setKiidc(BigDecimal kiidc) {
		this.kiidc = kiidc;
	}

	public BigDecimal getKipokok() {
		return kipokok;
	}

	public void setKipokok(BigDecimal kipokok) {
		this.kipokok = kipokok;
	}

	public BigDecimal getKmk() {
		return kmk;
	}

	public void setKmk(BigDecimal kmk) {
		this.kmk = kmk;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastupdated() {
		return this.lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	public BigDecimal getNcl() {
		return ncl;
	}

	public void setNcl(BigDecimal ncl) {
		this.ncl = ncl;
	}

	@Column(length = 100)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getParticipantname() {
		return this.participantname;
	}

	public void setParticipantname(String participantname) {
		this.participantname = participantname;
	}

	@Column(length = 100)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getPicemail() {
		return picemail;
	}

	public void setPicemail(String picemail) {
		this.picemail = picemail;
	}

	@Column(length = 20)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getPichp() {
		return pichp;
	}

	public void setPichp(String pichp) {
		this.pichp = pichp;
	}

	@Column(length = 30)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getPicname() {
		return picname;
	}

	public void setPicname(String picname) {
		this.picname = picname;
	}

	public BigDecimal getPortionamount() {
		return this.portionamount;
	}

	public void setPortionamount(BigDecimal portionamount) {
		this.portionamount = portionamount;
	}

	public BigDecimal getPortionpercent() {
		return this.portionpercent;
	}

	public void setPortionpercent(BigDecimal portionpercent) {
		this.portionpercent = portionpercent;
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
	@ManyToOne
	@JoinColumn(name = "tpipelinefk")
	@ForeignKey(name = "TPIPELINEPART_FK1")
	public Tpipeline getTpipeline() {
		return this.tpipeline;
	}

	public void setTpipeline(Tpipeline tpipeline) {
		this.tpipeline = tpipeline;
	}

}