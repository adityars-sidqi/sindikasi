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
 * The persistent class for the tportopartagent database table.
 * 
 */
@Entity
@NamedQuery(name = "Tportopartagent.findAll", query = "SELECT t FROM Tportopartagent t")
public class Tportopartagent implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tportopartagentpk;
	private Date lastupdated;
	private String updatedby;
	private Magenttype magenttype;
	private Tportopart tportopart;

	public Tportopartagent() {
	}

	@Id
	@SequenceGenerator(name = "TPORTOPARTAGENT_TPORTOPARTAGENTPK_GENERATOR", sequenceName = "TPORTOPARTAGENT_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TPORTOPARTAGENT_TPORTOPARTAGENTPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTportopartagentpk() {
		return this.tportopartagentpk;
	}

	public void setTportopartagentpk(Integer tportopartagentpk) {
		this.tportopartagentpk = tportopartagentpk;
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
	@ForeignKey(name = "TPORTOPARTAGENT_FK2")
	public Magenttype getMagenttype() {
		return this.magenttype;
	}

	public void setMagenttype(Magenttype magenttype) {
		this.magenttype = magenttype;
	}

	// bi-directional many-to-one association to Tportopart
	@ManyToOne
	@JoinColumn(name = "tportopartfk")
	@ForeignKey(name = "TPORTOPARTAGENT_FK1")
	public Tportopart getTportopart() {
		return this.tportopart;
	}

	public void setTportopart(Tportopart tportopart) {
		this.tportopart = tportopart;
	}

}