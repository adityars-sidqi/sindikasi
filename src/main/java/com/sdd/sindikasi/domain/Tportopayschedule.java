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
 * The persistent class for the tportopayschedule database table.
 * 
 */
@Entity
@NamedQuery(name="Tportopayschedule.findAll", query="SELECT t FROM Tportopayschedule t")
public class Tportopayschedule implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tportopayschedulepk;
	private Date lastupdated;
	private String monthschedule;
	private String paytype;
	private String updatedby;
	private Tporto tporto;

	public Tportopayschedule() {
	}


	@Id
	@SequenceGenerator(name="TPORTOPAYSCHEDULE_TPORTOPAYSCHEDULEPK_GENERATOR", sequenceName="TPORTOPAYSCHEDULE_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TPORTOPAYSCHEDULE_TPORTOPAYSCHEDULEPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTportopayschedulepk() {
		return this.tportopayschedulepk;
	}

	public void setTportopayschedulepk(Integer tportopayschedulepk) {
		this.tportopayschedulepk = tportopayschedulepk;
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
	public String getMonthschedule() {
		return this.monthschedule;
	}

	public void setMonthschedule(String monthschedule) {
		this.monthschedule = monthschedule;
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


	//bi-directional many-to-one association to Tporto
	@ManyToOne
	@JoinColumn(name="tportofk")
	@ForeignKey(name = "TPORTOPAYSCHEDULE_FK1")
	public Tporto getTporto() {
		return this.tporto;
	}

	public void setTporto(Tporto tporto) {
		this.tporto = tporto;
	}

}