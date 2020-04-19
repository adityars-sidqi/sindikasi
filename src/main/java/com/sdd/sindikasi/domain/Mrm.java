package com.sdd.sindikasi.domain;

import java.io.Serializable;
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

import org.hibernate.annotations.Type;

/**
 * The persistent class for the mrm database table.
 * 
 */
@Entity
@NamedQuery(name="Mrm.findAll", query="SELECT m FROM Mrm m")
public class Mrm implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer mrmpk;
	private Date lastupdated;
	private String rmid;
	private String rmname;
	private String updatedby;
	private Mrmgroup mrmgroup;
	private List<Tpipeline> tpipelines;
	private List<Ttargetrm> ttargetrms;

	public Mrm() {
	}


	@Id
	@SequenceGenerator(name="MRM_MRMPK_GENERATOR", sequenceName="MRM_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MRM_MRMPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getMrmpk() {
		return this.mrmpk;
	}

	public void setMrmpk(Integer mrmpk) {
		this.mrmpk = mrmpk;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastupdated() {
		return this.lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	@Column(length = 10)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getRmid() {
		return this.rmid;
	}

	public void setRmid(String rmid) {
		this.rmid = rmid;
	}

	@Column(length = 40)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getRmname() {
		return this.rmname;
	}

	public void setRmname(String rmname) {
		this.rmname = rmname;
	}

	@Column(length = 30)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}


	//bi-directional many-to-one association to Mrmgroup
	@ManyToOne
	@JoinColumn(name="mrmgroupfk")
	public Mrmgroup getMrmgroup() {
		return this.mrmgroup;
	}

	public void setMrmgroup(Mrmgroup mrmgroup) {
		this.mrmgroup = mrmgroup;
	}


	//bi-directional many-to-one association to Tpipeline
	@OneToMany(mappedBy="mrm")
	public List<Tpipeline> getTpipelines() {
		return this.tpipelines;
	}

	public void setTpipelines(List<Tpipeline> tpipelines) {
		this.tpipelines = tpipelines;
	}

	public Tpipeline addTpipeline(Tpipeline tpipeline) {
		getTpipelines().add(tpipeline);
		tpipeline.setMrm(this);

		return tpipeline;
	}

	public Tpipeline removeTpipeline(Tpipeline tpipeline) {
		getTpipelines().remove(tpipeline);
		tpipeline.setMrm(null);

		return tpipeline;
	}


	//bi-directional many-to-one association to Ttargetrm
	@OneToMany(mappedBy="mrm")
	public List<Ttargetrm> getTtargetrms() {
		return this.ttargetrms;
	}

	public void setTtargetrms(List<Ttargetrm> ttargetrms) {
		this.ttargetrms = ttargetrms;
	}

	public Ttargetrm addTtargetrm(Ttargetrm ttargetrm) {
		getTtargetrms().add(ttargetrm);
		ttargetrm.setMrm(this);

		return ttargetrm;
	}

	public Ttargetrm removeTtargetrm(Ttargetrm ttargetrm) {
		getTtargetrms().remove(ttargetrm);
		ttargetrm.setMrm(null);

		return ttargetrm;
	}

}