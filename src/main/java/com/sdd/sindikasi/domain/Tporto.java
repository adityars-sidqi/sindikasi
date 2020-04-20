package com.sdd.sindikasi.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Type;

/**
 * The persistent class for the tporto database table.
 * 
 */
@Entity
@NamedQuery(name = "Tporto.findAll", query = "SELECT t FROM Tporto t")
public class Tporto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tportopk;
	private BigDecimal creditfacility;
	private String currency;
	private String debitur;
	private String diremail;
	private String dirname;
	private String dirphone;
	private BigDecimal feeamount;
	private BigDecimal feepercent;
	private Date lastupdated;
	private Msector msector;
	private Munit munit;
	private String officeaddress;
	private String officeemail;
	private String officefax;
	private String officephone;
	private Date portodate;
	private String project;
	private BigDecimal projectamount;
	private String regid;
	private Date regtime;
	private String rmcredit;
	private String rmgroupcode;
	private String rmid;
	private String rmname;
	private BigDecimal selfportion;
	private Date targetpk;
	private String updatedby;

	public Tporto() {
	}

	@Id
	@SequenceGenerator(name = "TPORTO_TPORTOPK_GENERATOR", sequenceName = "TPORTO_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TPORTO_TPORTOPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTportopk() {
		return this.tportopk;
	}

	public void setTportopk(Integer tportopk) {
		this.tportopk = tportopk;
	}

	public BigDecimal getCreditfacility() {
		return this.creditfacility;
	}

	public void setCreditfacility(BigDecimal creditfacility) {
		this.creditfacility = creditfacility;
	}

	@Column(length = 5)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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

	public BigDecimal getFeeamount() {
		return this.feeamount;
	}

	public void setFeeamount(BigDecimal feeamount) {
		this.feeamount = feeamount;
	}

	public BigDecimal getFeepercent() {
		return this.feepercent;
	}

	public void setFeepercent(BigDecimal feepercent) {
		this.feepercent = feepercent;
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

	@Temporal(TemporalType.DATE)
	public Date getPortodate() {
		return this.portodate;
	}

	public void setPortodate(Date portodate) {
		this.portodate = portodate;
	}

	@Column(length = 100)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProject() {
		return this.project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public BigDecimal getProjectamount() {
		return this.projectamount;
	}

	public void setProjectamount(BigDecimal projectamount) {
		this.projectamount = projectamount;
	}

	@Column(length = 13)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getRegid() {
		return this.regid;
	}

	public void setRegid(String regid) {
		this.regid = regid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getRegtime() {
		return this.regtime;
	}

	public void setRegtime(Date regtime) {
		this.regtime = regtime;
	}

	@Column(length = 40)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getRmcredit() {
		return this.rmcredit;
	}

	public void setRmcredit(String rmcredit) {
		this.rmcredit = rmcredit;
	}

	@Column(length = 10)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getRmgroupcode() {
		return this.rmgroupcode;
	}

	public void setRmgroupcode(String rmgroupcode) {
		this.rmgroupcode = rmgroupcode;
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

	public BigDecimal getSelfportion() {
		return this.selfportion;
	}

	public void setSelfportion(BigDecimal selfportion) {
		this.selfportion = selfportion;
	}

	@Temporal(TemporalType.DATE)
	public Date getTargetpk() {
		return this.targetpk;
	}

	public void setTargetpk(Date targetpk) {
		this.targetpk = targetpk;
	}

	@Column(length = 30)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	// bi-directional many-to-one association to Msector
	@ManyToOne
	@JoinColumn(name = "msectorfk")
	@ForeignKey(name = "TPORTO_FK1")
	public Msector getMsector() {
		return msector;
	}

	public void setMsector(Msector msector) {
		this.msector = msector;
	}

	// bi-directional many-to-one association to Munit
	@ManyToOne
	@JoinColumn(name = "munitfk")
	@ForeignKey(name = "TPORTO_FK2")
	public Munit getMunit() {
		return munit;
	}

	public void setMunit(Munit munit) {
		this.munit = munit;
	}

}