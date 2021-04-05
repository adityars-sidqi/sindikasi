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

import org.hibernate.annotations.Type;

/**
 * The persistent class for the tpipeline database table.
 * 
 */
@Entity
@NamedQuery(name = "Tpipeline.findAll", query = "SELECT t FROM Tpipeline t")
public class Tpipeline implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tpipelinepk;
	private BigDecimal creditfacility;
	private String creditfacilitycurr;
	private int creditplan;
	private BigDecimal feeamount;
	private String feecurrency;
	private BigDecimal feepercent;
	private String isagentcol;
	private String isagentesc;
	private String isagentfac;
	private Date lastupdated;
	private String notes;
	private String project;
	private BigDecimal projectamount;
	private String projectcurrency;
	private String regid;
	private Date regtime;
	private String rmcredit;
	private BigDecimal selfportion;
	private String selfportioncurrency;
	private String status;
	private Date targetpk;
	private String tranche;
	private String updatedby;
	private int yearperiod;
	private int monthperiod;
	private String qperiod;
	private Mdebitur mdebitur;
	private Mrm mrm;
	private Msector msector;
	private Munit munit;
	private BigDecimal participantportion;
	private String participantportioncurrency;
	private BigDecimal kipokok;
	private String kipokokcurr;
	private BigDecimal kiidc;
	private String kiidccurr;
	private BigDecimal kmk;
	private String kmkcurr;
	private BigDecimal termloan;
	private String termloancurr;
	private BigDecimal corploan;
	private String corploancurr;
	private BigDecimal ncl;
	private String nclcurr;

	public Tpipeline() {
	}

	@Id
	@SequenceGenerator(name = "TPIPELINE_TPIPELINEPK_GENERATOR", sequenceName = "TPIPELINE_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TPIPELINE_TPIPELINEPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTpipelinepk() {
		return this.tpipelinepk;
	}

	public void setTpipelinepk(Integer tpipelinepk) {
		this.tpipelinepk = tpipelinepk;
	}

	public BigDecimal getCreditfacility() {
		return this.creditfacility;
	}

	public void setCreditfacility(BigDecimal creditfacility) {
		this.creditfacility = creditfacility;
	}

	@Column(length = 5)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getCreditfacilitycurr() {
		return creditfacilitycurr;
	}

	public void setCreditfacilitycurr(String creditfacilitycurr) {
		this.creditfacilitycurr = creditfacilitycurr;
	}

	public int getCreditplan() {
		return creditplan;
	}

	public void setCreditplan(int creditplan) {
		this.creditplan = creditplan;
	}

	public BigDecimal getFeeamount() {
		return this.feeamount;
	}

	public void setFeeamount(BigDecimal feeamount) {
		this.feeamount = feeamount;
	}

	@Column(length = 5)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getFeecurrency() {
		return feecurrency;
	}

	public void setFeecurrency(String feecurrency) {
		this.feecurrency = feecurrency;
	}

	public BigDecimal getFeepercent() {
		return this.feepercent;
	}

	public void setFeepercent(BigDecimal feepercent) {
		this.feepercent = feepercent;
	}

	public String getIsagentcol() {
		return isagentcol;
	}

	public void setIsagentcol(String isagentcol) {
		this.isagentcol = isagentcol;
	}

	public String getIsagentesc() {
		return isagentesc;
	}

	public void setIsagentesc(String isagentesc) {
		this.isagentesc = isagentesc;
	}

	public String getIsagentfac() {
		return isagentfac;
	}

	public void setIsagentfac(String isagentfac) {
		this.isagentfac = isagentfac;
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
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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

	@Column(length = 5)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProjectcurrency() {
		return projectcurrency;
	}

	public void setProjectcurrency(String projectcurrency) {
		this.projectcurrency = projectcurrency;
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

	public BigDecimal getSelfportion() {
		return this.selfportion;
	}

	public void setSelfportion(BigDecimal selfportion) {
		this.selfportion = selfportion;
	}

	@Column(length = 2)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getSelfportioncurrency() {
		return selfportioncurrency;
	}

	public void setSelfportioncurrency(String selfportioncurrency) {
		this.selfportioncurrency = selfportioncurrency;
	}

	@Column(length = 2)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.DATE)
	public Date getTargetpk() {
		return this.targetpk;
	}

	public void setTargetpk(Date targetpk) {
		this.targetpk = targetpk;
	}

	@Column(length = 40)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getTranche() {
		return tranche;
	}

	public void setTranche(String tranche) {
		this.tranche = tranche;
	}

	@Column(length = 30)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	public int getYearperiod() {
		return yearperiod;
	}

	public void setYearperiod(int yearperiod) {
		this.yearperiod = yearperiod;
	}

	public int getMonthperiod() {
		return monthperiod;
	}

	public void setMonthperiod(int monthperiod) {
		this.monthperiod = monthperiod;
	}

	public String getQperiod() {
		return qperiod;
	}

	public void setQperiod(String qperiod) {
		this.qperiod = qperiod;
	}

	public BigDecimal getParticipantportion() {
		return participantportion;
	}

	public void setParticipantportion(BigDecimal participantportion) {
		this.participantportion = participantportion;
	}

	@Column(length = 5)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getParticipantportioncurrency() {
		return participantportioncurrency;
	}

	public void setParticipantportioncurrency(String participantportioncurrency) {
		this.participantportioncurrency = participantportioncurrency;
	}
	
	

	public BigDecimal getKipokok() {
		return kipokok;
	}

	public void setKipokok(BigDecimal kipokok) {
		this.kipokok = kipokok;
	}

	@Column(length = 5)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getKipokokcurr() {
		return kipokokcurr;
	}

	public void setKipokokcurr(String kipokokcurr) {
		this.kipokokcurr = kipokokcurr;
	}

	public BigDecimal getKiidc() {
		return kiidc;
	}

	public void setKiidc(BigDecimal kiidc) {
		this.kiidc = kiidc;
	}

	@Column(length = 5)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getKiidccurr() {
		return kiidccurr;
	}

	public void setKiidccurr(String kiidccurr) {
		this.kiidccurr = kiidccurr;
	}

	public BigDecimal getKmk() {
		return kmk;
	}

	public void setKmk(BigDecimal kmk) {
		this.kmk = kmk;
	}

	@Column(length = 5)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getKmkcurr() {
		return kmkcurr;
	}

	public void setKmkcurr(String kmkcurr) {
		this.kmkcurr = kmkcurr;
	}

	public BigDecimal getTermloan() {
		return termloan;
	}

	public void setTermloan(BigDecimal termloan) {
		this.termloan = termloan;
	}

	@Column(length = 5)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getTermloancurr() {
		return termloancurr;
	}

	public void setTermloancurr(String termloancurr) {
		this.termloancurr = termloancurr;
	}

	public BigDecimal getCorploan() {
		return corploan;
	}

	public void setCorploan(BigDecimal corploan) {
		this.corploan = corploan;
	}

	@Column(length = 5)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getCorploancurr() {
		return corploancurr;
	}

	public void setCorploancurr(String corploancurr) {
		this.corploancurr = corploancurr;
	}

	public BigDecimal getNcl() {
		return ncl;
	}

	public void setNcl(BigDecimal ncl) {
		this.ncl = ncl;
	}

	@Column(length = 5)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getNclcurr() {
		return nclcurr;
	}

	public void setNclcurr(String nclcurr) {
		this.nclcurr = nclcurr;
	}

	// bi-directional many-to-one association to Mdebitur
	@ManyToOne
	@JoinColumn(name = "mdebiturfk")
	public Mdebitur getMdebitur() {
		return this.mdebitur;
	}

	public void setMdebitur(Mdebitur mdebitur) {
		this.mdebitur = mdebitur;
	}

	// bi-directional many-to-one association to Mrm
	@ManyToOne
	@JoinColumn(name = "mrmfk")
	public Mrm getMrm() {
		return this.mrm;
	}

	public void setMrm(Mrm mrm) {
		this.mrm = mrm;
	}

	// bi-directional many-to-one association to Msector
	@ManyToOne
	@JoinColumn(name = "msectorfk")
	public Msector getMsector() {
		return this.msector;
	}

	public void setMsector(Msector msector) {
		this.msector = msector;
	}

	// bi-directional many-to-one association to Munit
	@ManyToOne
	@JoinColumn(name = "munitfk")
	public Munit getMunit() {
		return this.munit;
	}

	public void setMunit(Munit munit) {
		this.munit = munit;
	}

}