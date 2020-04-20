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
 * The persistent class for the mdebitur database table.
 * 
 */
@Entity
@NamedQuery(name="Mdebitur.findAll", query="SELECT m FROM Mdebitur m")
public class Mdebitur implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer mdebiturpk;
	private String debitur;
	private String diremail;
	private String dirname;
	private String dirphone;
	private Date lastupdated;
	private String officeaddress;
	private String officeemail;
	private String officefax;
	private String officephone;
	private String updatedby;

	public Mdebitur() {
	}


	@Id
	@SequenceGenerator(name="MDEBITUR_MDEBITURPK_GENERATOR", sequenceName="MDEBITUR_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MDEBITUR_MDEBITURPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getMdebiturpk() {
		return this.mdebiturpk;
	}

	public void setMdebiturpk(Integer mdebiturpk) {
		this.mdebiturpk = mdebiturpk;
	}

	@Column(length = 100)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getDebitur() {
		return this.debitur;
	}

	public void setDebitur(String debitur) {
		this.debitur = debitur;
	}

	@Column(length = 40)
	@Type(type = "com.sdd.utils.usertype.TrimUserType")
	public String getDiremail() {
		return this.diremail;
	}

	public void setDiremail(String diremail) {
		this.diremail = diremail;
	}

	@Column(length = 40)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getDirname() {
		return this.dirname;
	}

	public void setDirname(String dirname) {
		this.dirname = dirname;
	}

	@Column(length = 40)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getDirphone() {
		return this.dirphone;
	}

	public void setDirphone(String dirphone) {
		this.dirphone = dirphone;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastupdated() {
		return this.lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	@Column(length = 200)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getOfficeaddress() {
		return this.officeaddress;
	}

	public void setOfficeaddress(String officeaddress) {
		this.officeaddress = officeaddress;
	}

	@Column(length = 100)
	@Type(type = "com.sdd.utils.usertype.TrimUserType")
	public String getOfficeemail() {
		return this.officeemail;
	}

	public void setOfficeemail(String officeemail) {
		this.officeemail = officeemail;
	}

	@Column(length = 40)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getOfficefax() {
		return this.officefax;
	}

	public void setOfficefax(String officefax) {
		this.officefax = officefax;
	}

	@Column(length = 40)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getOfficephone() {
		return this.officephone;
	}

	public void setOfficephone(String officephone) {
		this.officephone = officephone;
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