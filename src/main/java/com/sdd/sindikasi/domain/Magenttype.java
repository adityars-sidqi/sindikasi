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
 * The persistent class for the magenttype database table.
 * 
 */
@Entity
@NamedQuery(name = "Magenttype.findAll", query = "SELECT m FROM Magenttype m")
public class Magenttype implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer magenttypepk;
	private String agentcode;
	private String description;
	private Date lastupdated;
	private String updatedby;

	public Magenttype() {
	}

	@Id
	@SequenceGenerator(name = "MAGENTTYPE_MAGENTTYPEPK_GENERATOR", sequenceName = "MAGENTTYPE_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MAGENTTYPE_MAGENTTYPEPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getMagenttypepk() {
		return this.magenttypepk;
	}

	public void setMagenttypepk(Integer magenttypepk) {
		this.magenttypepk = magenttypepk;
	}

	@Column(length = 5)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getAgentcode() {
		return this.agentcode;
	}

	public void setAgentcode(String agentcode) {
		this.agentcode = agentcode;
	}

	@Column(length = 100)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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