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
 * The persistent class for the tportopartdebt database table.
 * 
 */
@Entity
@NamedQuery(name = "Tportopartdebt.findAll", query = "SELECT t FROM Tportopartdebt t")
public class Tportopartdebt implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tportopartdebtpk;
	private String currency;
	private BigDecimal debtamount;
	private Date debtdate;
	private BigDecimal kiidc;
	private BigDecimal kipokok;
	private BigDecimal kmk;
	private Date lastupdated;
	private BigDecimal ncl;
	private String updatedby;
	private Tportopart tportopart;

	public Tportopartdebt() {
	}

	@Id
	@SequenceGenerator(name = "TPORTOPARTDEBT_TPORTOPARTDEBTPK_GENERATOR", sequenceName = "TPORTOPARTDEBT_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TPORTOPARTDEBT_TPORTOPARTDEBTPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTportopartdebtpk() {
		return this.tportopartdebtpk;
	}

	public void setTportopartdebtpk(Integer tportopartdebtpk) {
		this.tportopartdebtpk = tportopartdebtpk;
	}

	@Column(length = 5)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getDebtamount() {
		return this.debtamount;
	}

	public void setDebtamount(BigDecimal debtamount) {
		this.debtamount = debtamount;
	}

	@Temporal(TemporalType.DATE)
	public Date getDebtdate() {
		return this.debtdate;
	}

	public void setDebtdate(Date debtdate) {
		this.debtdate = debtdate;
	}

	public BigDecimal getKiidc() {
		return this.kiidc;
	}

	public void setKiidc(BigDecimal kiidc) {
		this.kiidc = kiidc;
	}

	public BigDecimal getKipokok() {
		return this.kipokok;
	}

	public void setKipokok(BigDecimal kipokok) {
		this.kipokok = kipokok;
	}

	public BigDecimal getKmk() {
		return this.kmk;
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
		return this.ncl;
	}

	public void setNcl(BigDecimal ncl) {
		this.ncl = ncl;
	}

	@Column(length = 30)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	// bi-directional many-to-one association to Tportopart
	@ManyToOne
	@JoinColumn(name = "tportopartfk")
	@ForeignKey(name = "TPORTOPARTDEBT_FK1")
	public Tportopart getTportopart() {
		return this.tportopart;
	}

	public void setTportopart(Tportopart tportopart) {
		this.tportopart = tportopart;
	}

}