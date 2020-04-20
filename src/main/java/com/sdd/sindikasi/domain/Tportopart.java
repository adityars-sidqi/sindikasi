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
 * The persistent class for the tportopart database table.
 * 
 */
@Entity
@NamedQuery(name="Tportopart.findAll", query="SELECT t FROM Tportopart t")
public class Tportopart implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tportopartpk;
	private Date lastupdated;
	private String participantname;
	private BigDecimal portionamount;
	private BigDecimal portionpercent;
	private String updatedby;
	private Tporto tporto;

	public Tportopart() {
	}


	@Id
	@SequenceGenerator(name="TPORTOPART_TPORTOPARTPK_GENERATOR", sequenceName="TPORTOPART_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TPORTOPART_TPORTOPARTPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTportopartpk() {
		return this.tportopartpk;
	}

	public void setTportopartpk(Integer tportopartpk) {
		this.tportopartpk = tportopartpk;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastupdated() {
		return this.lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	@Column(length = 100)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getParticipantname() {
		return this.participantname;
	}

	public void setParticipantname(String participantname) {
		this.participantname = participantname;
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


	//bi-directional many-to-one association to Tporto
	@ManyToOne
	@JoinColumn(name="tportofk")
	@ForeignKey(name = "TPORTOPART_FK1")
	public Tporto getTporto() {
		return this.tporto;
	}

	public void setTporto(Tporto tporto) {
		this.tporto = tporto;
	}

}