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
 * The persistent class for the tportopayment database table.
 * 
 */
@Entity
@NamedQuery(name = "Tportopayment.findAll", query = "SELECT t FROM Tportopayment t")
public class Tportopayment implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tportopayschedulepk;
	private String currency;
	private Date lastupdated;
	private BigDecimal payamount;
	private Date paydate;
	private String paytype;
	private String updatedby;
	private Tportoinvoice tportoinvoice;

	public Tportopayment() {
	}

	@Id
	@SequenceGenerator(name = "TPORTOPAYMENT_TPORTOPAYSCHEDULEPK_GENERATOR", sequenceName = "TPORTOPAYMENT_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TPORTOPAYMENT_TPORTOPAYSCHEDULEPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTportopayschedulepk() {
		return this.tportopayschedulepk;
	}

	public void setTportopayschedulepk(Integer tportopayschedulepk) {
		this.tportopayschedulepk = tportopayschedulepk;
	}

	@Column(length = 5)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastupdated() {
		return this.lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	public BigDecimal getPayamount() {
		return this.payamount;
	}

	public void setPayamount(BigDecimal payamount) {
		this.payamount = payamount;
	}

	@Temporal(TemporalType.DATE)
	public Date getPaydate() {
		return this.paydate;
	}

	public void setPaydate(Date paydate) {
		this.paydate = paydate;
	}

	@Column(length = 1)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getPaytype() {
		return this.paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	@Column(length = 30)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	// bi-directional many-to-one association to Tportoinvoice
	@ManyToOne
	@JoinColumn(name = "tportoinvoicefk")
	@ForeignKey(name = "TPORTOPAYMENT_FK1")
	public Tportoinvoice getTportoinvoice() {
		return this.tportoinvoice;
	}

	public void setTportoinvoice(Tportoinvoice tportoinvoice) {
		this.tportoinvoice = tportoinvoice;
	}

}