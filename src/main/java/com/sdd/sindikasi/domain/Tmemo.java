package com.sdd.sindikasi.domain;

import java.io.Serializable;
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
 * The persistent class for the tmemo database table.
 * 
 */
@Entity
@NamedQuery(name="Tmemo.findAll", query="SELECT t FROM Tmemo t")
public class Tmemo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tmemopk;
	private String createdby;
	private Date createdtime;
	private String memo;
	private Tpipeline tpipeline;

	public Tmemo() {
	}


	@Id
	@SequenceGenerator(name="TMEMO_TMEMOPK_GENERATOR", sequenceName="TMEMO_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TMEMO_TMEMOPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTmemopk() {
		return this.tmemopk;
	}

	public void setTmemopk(Integer tmemopk) {
		this.tmemopk = tmemopk;
	}

	@Column(length = 15)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatedtime() {
		return this.createdtime;
	}

	public void setCreatedtime(Date createdtime) {
		this.createdtime = createdtime;
	}

	@Column(length = 200)
	@Type(type = "com.sdd.utils.usertype.TrimUserType")
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}


	//bi-directional many-to-one association to Tpipeline
	@ManyToOne
	@JoinColumn(name="tpipelinefk")
	@ForeignKey(name = "TMEMO_FK1")
	public Tpipeline getTpipeline() {
		return this.tpipeline;
	}

	public void setTpipeline(Tpipeline tpipeline) {
		this.tpipeline = tpipeline;
	}

}