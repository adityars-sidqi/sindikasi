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
 * The persistent class for the tportopart database table.
 * 
 */
@Entity
@NamedQuery(name = "Tportopart.findAll", query = "SELECT t FROM Tportopart t")
public class Tportopart implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tportopartpk;
	private String currency;
	private BigDecimal feeamount;
	private String isself;
	private BigDecimal kiidc;
	private BigDecimal kipokok;
	private BigDecimal kmk;
	private Date lastupdated;
	private BigDecimal ncl;
	private BigDecimal oskiidc;
	private BigDecimal oskipokok;
	private BigDecimal oskmk;
	private BigDecimal osncl;
	private String participantname;
	private String picemail;
	private String pichp;
	private String picname;
	private BigDecimal portionamount;
	private BigDecimal portionpercent;
	private String updatedby;
	private Tporto tporto;

	public Tportopart() {
	}

	@Id
	@SequenceGenerator(name = "TPORTOPART_TPORTOPARTPK_GENERATOR", sequenceName = "TPORTOPART_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TPORTOPART_TPORTOPARTPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTportopartpk() {
		return this.tportopartpk;
	}

	public void setTportopartpk(Integer tportopartpk) {
		this.tportopartpk = tportopartpk;
	}

	@Column(length = 5)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getCurrency() {
		return currency;
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

	public BigDecimal getOskiidc() {
		return oskiidc;
	}

	public void setOskiidc(BigDecimal oskiidc) {
		this.oskiidc = oskiidc;
	}

	public BigDecimal getOskipokok() {
		return oskipokok;
	}

	public void setOskipokok(BigDecimal oskipokok) {
		this.oskipokok = oskipokok;
	}

	public BigDecimal getOskmk() {
		return oskmk;
	}

	public void setOskmk(BigDecimal oskmk) {
		this.oskmk = oskmk;
	}

	public BigDecimal getOsncl() {
		return osncl;
	}

	public void setOsncl(BigDecimal osncl) {
		this.osncl = osncl;
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

	// bi-directional many-to-one association to Tporto
	@ManyToOne
	@JoinColumn(name = "tportofk")
	@ForeignKey(name = "TPORTOPART_FK1")
	public Tporto getTporto() {
		return this.tporto;
	}

	public void setTporto(Tporto tporto) {
		this.tporto = tporto;
	}

}