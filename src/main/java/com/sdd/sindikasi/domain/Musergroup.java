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
 * The persistent class for the musergroup database table.
 * 
 */
@Entity
@NamedQuery(name="Musergroup.findAll", query="SELECT m FROM Musergroup m")
public class Musergroup implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer musergrouppk;
	private Date lastupdated;
	private String updatedby;
	private String usergroupcode;
	private String usergroupdesc;
	private String usergroupname;

	public Musergroup() {
	}


	@Id
	@SequenceGenerator(name="MUSERGROUP_MUSERGROUPPK_GENERATOR", sequenceName="MUSERGROUP_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MUSERGROUP_MUSERGROUPPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getMusergrouppk() {
		return this.musergrouppk;
	}

	public void setMusergrouppk(Integer musergrouppk) {
		this.musergrouppk = musergrouppk;
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

	@Column(length = 20)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getUsergroupcode() {
		return this.usergroupcode;
	}

	public void setUsergroupcode(String usergroupcode) {
		this.usergroupcode = usergroupcode;
	}

	@Column(length = 100)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getUsergroupdesc() {
		return this.usergroupdesc;
	}

	public void setUsergroupdesc(String usergroupdesc) {
		this.usergroupdesc = usergroupdesc;
	}

	@Column(length = 40)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getUsergroupname() {
		return this.usergroupname;
	}

	public void setUsergroupname(String usergroupname) {
		this.usergroupname = usergroupname;
	}

}