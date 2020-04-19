package com.sdd.sindikasi.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

/**
 * The persistent class for the mrmgroup database table.
 * 
 */
@Entity
@NamedQuery(name = "Mrmgroup.findAll", query = "SELECT m FROM Mrmgroup m")
public class Mrmgroup implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer mrmgrouppk;
	private Date lastupdated;
	private String rmgroupcode;
	private String rmgroupname;
	private String updatedby;
	private List<Mrm> mrms;
	private List<Ttargetrm> ttargetrms;
	private List<Ttargetrmgroup> ttargetrmgroups;

	public Mrmgroup() {
	}

	@Id
	@SequenceGenerator(name = "MRMGROUP_MRMGROUPPK_GENERATOR", sequenceName = "MRMGROUP_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MRMGROUP_MRMGROUPPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getMrmgrouppk() {
		return this.mrmgrouppk;
	}

	public void setMrmgrouppk(Integer mrmgrouppk) {
		this.mrmgrouppk = mrmgrouppk;
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
	public String getRmgroupcode() {
		return this.rmgroupcode;
	}

	public void setRmgroupcode(String rmgroupcode) {
		this.rmgroupcode = rmgroupcode;
	}

	@Column(length = 40)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getRmgroupname() {
		return this.rmgroupname;
	}

	public void setRmgroupname(String rmgroupname) {
		this.rmgroupname = rmgroupname;
	}

	@Column(length = 30)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	// bi-directional many-to-one association to Mrm
	@OneToMany(mappedBy = "mrmgroup")
	public List<Mrm> getMrms() {
		return this.mrms;
	}

	public void setMrms(List<Mrm> mrms) {
		this.mrms = mrms;
	}

	public Mrm addMrm(Mrm mrm) {
		getMrms().add(mrm);
		mrm.setMrmgroup(this);

		return mrm;
	}

	public Mrm removeMrm(Mrm mrm) {
		getMrms().remove(mrm);
		mrm.setMrmgroup(null);

		return mrm;
	}

	// bi-directional many-to-one association to Ttargetrm
	@OneToMany(mappedBy = "mrmgroup")
	public List<Ttargetrm> getTtargetrms() {
		return this.ttargetrms;
	}

	public void setTtargetrms(List<Ttargetrm> ttargetrms) {
		this.ttargetrms = ttargetrms;
	}

	public Ttargetrm addTtargetrm(Ttargetrm ttargetrm) {
		getTtargetrms().add(ttargetrm);
		ttargetrm.setMrmgroup(this);

		return ttargetrm;
	}

	public Ttargetrm removeTtargetrm(Ttargetrm ttargetrm) {
		getTtargetrms().remove(ttargetrm);
		ttargetrm.setMrmgroup(null);

		return ttargetrm;
	}

	// bi-directional many-to-one association to Ttargetrmgroup
	@OneToMany(mappedBy = "mrmgroup")
	public List<Ttargetrmgroup> getTtargetrmgroups() {
		return this.ttargetrmgroups;
	}

	public void setTtargetrmgroups(List<Ttargetrmgroup> ttargetrmgroups) {
		this.ttargetrmgroups = ttargetrmgroups;
	}

	public Ttargetrmgroup addTtargetrmgroup(Ttargetrmgroup ttargetrmgroup) {
		getTtargetrmgroups().add(ttargetrmgroup);
		ttargetrmgroup.setMrmgroup(this);

		return ttargetrmgroup;
	}

	public Ttargetrmgroup removeTtargetrmgroup(Ttargetrmgroup ttargetrmgroup) {
		getTtargetrmgroups().remove(ttargetrmgroup);
		ttargetrmgroup.setMrmgroup(null);

		return ttargetrmgroup;
	}

}