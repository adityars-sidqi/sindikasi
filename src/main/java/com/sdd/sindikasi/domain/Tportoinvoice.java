package com.sdd.sindikasi.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Type;

/**
 * The persistent class for the tportoinvoice database table.
 * 
 */
@Entity
@NamedQuery(name = "Tportoinvoice.findAll", query = "SELECT t FROM Tportoinvoice t")
public class Tportoinvoice implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tportoinvoicepk;
	private String currency;
	private BigDecimal invoiceamount;
	private Date invoicedate;
	private String invoiceno;
	private Date lastupdated;
	private String paytype;
	private String updatedby;
	private Tporto tporto;

	public Tportoinvoice() {
	}

	@Id
	@SequenceGenerator(name = "TPORTOINVOICE_TPORTOINVOICEPK_GENERATOR", sequenceName = "TPORTOINVOICE_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TPORTOINVOICE_TPORTOINVOICEPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTportoinvoicepk() {
		return this.tportoinvoicepk;
	}

	public void setTportoinvoicepk(Integer tportoinvoicepk) {
		this.tportoinvoicepk = tportoinvoicepk;
	}

	@Column(length = 5)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getInvoiceamount() {
		return this.invoiceamount;
	}

	public void setInvoiceamount(BigDecimal invoiceamount) {
		this.invoiceamount = invoiceamount;
	}

	@Temporal(TemporalType.DATE)
	public Date getInvoicedate() {
		return this.invoicedate;
	}

	public void setInvoicedate(Date invoicedate) {
		this.invoicedate = invoicedate;
	}

	@Column(length = 20)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getInvoiceno() {
		return this.invoiceno;
	}

	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastupdated() {
		return this.lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
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

	// bi-directional many-to-one association to Tporto
	@ManyToOne
	@JoinColumn(name = "tportofk")
	@ForeignKey(name = "TPORTOINVOICE_FK1")
	public Tporto getTporto() {
		return this.tporto;
	}

	public void setTporto(Tporto tporto) {
		this.tporto = tporto;
	}

}