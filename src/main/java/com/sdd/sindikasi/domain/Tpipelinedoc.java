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

import org.hibernate.annotations.Type;

/**
 * The persistent class for the tpipelinedoc database table.
 * 
 */
@Entity
@NamedQuery(name="Tpipelinedoc.findAll", query="SELECT t FROM Tpipelinedoc t")
public class Tpipelinedoc implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tpipelinedocpk;
	private String createdby;
	private Date createdtime;
	private String filename;
	private Tpipeline tpipeline;

	public Tpipelinedoc() {
	}


	@Id
	@SequenceGenerator(name="Tpipelinedoc_TpipelinedocPK_GENERATOR", sequenceName="Tpipelinedoc_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="Tpipelinedoc_TpipelinedocPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTpipelinedocpk() {
		return this.tpipelinedocpk;
	}

	public void setTpipelinedocpk(Integer tpipelinedocpk) {
		this.tpipelinedocpk = tpipelinedocpk;
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
	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}


	//bi-directional many-to-one association to Tpipeline
	@ManyToOne
	@JoinColumn(name="tpipelinefk")
	public Tpipeline getTpipeline() {
		return this.tpipeline;
	}


	public void setTpipeline(Tpipeline tpipeline) {
		this.tpipeline = tpipeline;
	}

}