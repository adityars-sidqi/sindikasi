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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

/**
 * The persistent class for the mcurrency database table.
 * 
 */
@Entity
@NamedQuery(name = "Mcurrency.findAll", query = "SELECT m FROM Mcurrency m")
public class Mcurrency implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer mcurrencypk;
	private String country;
	private String currencycode;
	private Date lastupdated;
	private String updatedby;

	public Mcurrency() {
	}

	@Id
	@SequenceGenerator(name = "MCURRENCY_MCURRENCYPK_GENERATOR", sequenceName = "MCURRENCY_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MCURRENCY_MCURRENCYPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getMcurrencypk() {
		return this.mcurrencypk;
	}

	public void setMcurrencypk(Integer mcurrencypk) {
		this.mcurrencypk = mcurrencypk;
	}

	@Column(length = 40)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(length = 5)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getCurrencycode() {
		return this.currencycode;
	}

	public void setCurrencycode(String currencycode) {
		this.currencycode = currencycode;
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

}