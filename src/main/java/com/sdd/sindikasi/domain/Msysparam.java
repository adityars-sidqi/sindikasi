package com.sdd.sindikasi.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

/**
 * The persistent class for the msysparam database table.
 * 
 */
@Entity
@NamedQuery(name="Msysparam.findAll", query="SELECT m FROM Msysparam m")
public class Msysparam implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer msysparampk;
	private String ismasked;
	private Date lastupdated;
	private Integer orderno;
	private String paramdesc;
	private String paramgroup;
	private String paramname;
	private String paramvalue;
	private String updatedby;

	public Msysparam() {
	}


	@Id
	@SequenceGenerator(name="MSYSPARAM_MSYSPARAMPK_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MSYSPARAM_MSYSPARAMPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getMsysparampk() {
		return this.msysparampk;
	}

	public void setMsysparampk(Integer msysparampk) {
		this.msysparampk = msysparampk;
	}

	@Column(length = 1)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getIsmasked() {
		return this.ismasked;
	}

	public void setIsmasked(String ismasked) {
		this.ismasked = ismasked;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastupdated() {
		return this.lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}


	public Integer getOrderno() {
		return this.orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

	@Column(length = 100)
	@Type(type = "com.sdd.utils.usertype.TrimUserType")
	public String getParamdesc() {
		return this.paramdesc;
	}

	public void setParamdesc(String paramdesc) {
		this.paramdesc = paramdesc;
	}

	@Column(length = 50)
	@Type(type = "com.sdd.utils.usertype.TrimUserType")
	public String getParamgroup() {
		return this.paramgroup;
	}

	public void setParamgroup(String paramgroup) {
		this.paramgroup = paramgroup;
	}

	@Column(length = 50)
	@Type(type = "com.sdd.utils.usertype.TrimUserType")
	public String getParamname() {
		return this.paramname;
	}

	public void setParamname(String paramname) {
		this.paramname = paramname;
	}

	@Column(length = 100)
	@Type(type = "com.sdd.utils.usertype.TrimUserType")
	public String getParamvalue() {
		return this.paramvalue;
	}

	public void setParamvalue(String paramvalue) {
		this.paramvalue = paramvalue;
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