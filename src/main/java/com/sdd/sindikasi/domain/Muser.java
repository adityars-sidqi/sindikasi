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

/**
 * The persistent class for the muser database table.
 * 
 */
@Entity
@NamedQuery(name="Muser.findAll", query="SELECT m FROM Muser m")
public class Muser implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer muserpk;
	private String email;
	private Date lastupdated;
	private String password;
	private String updatedby;
	private String userid;
	private String username;
	private Musergroup musergroup;

	public Muser() {
	}


	@Id
	@SequenceGenerator(name="MUSER_MUSERPK_GENERATOR", sequenceName="MUSER_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MUSER_MUSERPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getMuserpk() {
		return this.muserpk;
	}

	public void setMuserpk(Integer muserpk) {
		this.muserpk = muserpk;
	}


	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastupdated() {
		return this.lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}


	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}


	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}


	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	//bi-directional many-to-one association to Musergroup
	@ManyToOne
	@JoinColumn(name="musergroupfk")
	public Musergroup getMusergroup() {
		return this.musergroup;
	}

	public void setMusergroup(Musergroup musergroup) {
		this.musergroup = musergroup;
	}

}