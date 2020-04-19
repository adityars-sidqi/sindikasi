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

import org.hibernate.annotations.Type;

/**
 * The persistent class for the tpipeline database table.
 * 
 */
@Entity
@NamedQuery(name="Tpipeline.findAll", query="SELECT t FROM Tpipeline t")
public class Tpipeline implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tpipelinepk;
	private BigDecimal creditfacility;
	private String currency;
	private BigDecimal feeamount;
	private BigDecimal feepercent;
	private Date lastupdated;
	private String notes;
	private String project;
	private BigDecimal projectamount;
	private String regid;
	private Date regtime;
	private String rmcredit;
	private BigDecimal selfportion;
	private String status;
	private Date targetpk;
	private String updatedby;
	private List<Tmemo> tmemos;
	private Mdebitur mdebitur;
	private Mrm mrm;
	private Msector msector;
	private Munit munit;
	private List<Tpipelinepart> tpipelineparts;

	public Tpipeline() {
	}


	@Id
	@SequenceGenerator(name="TPIPELINE_TPIPELINEPK_GENERATOR", sequenceName="TPIPELINE_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TPIPELINE_TPIPELINEPK_GENERATOR")
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
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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

	@Column(length = 30)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}


	//bi-directional many-to-one association to Tmemo
	@OneToMany(mappedBy="tpipeline")
	public List<Tmemo> getTmemos() {
		return this.tmemos;
	}

	public void setTmemos(List<Tmemo> tmemos) {
		this.tmemos = tmemos;
	}

	public Tmemo addTmemo(Tmemo tmemo) {
		getTmemos().add(tmemo);
		tmemo.setTpipeline(this);

		return tmemo;
	}

	public Tmemo removeTmemo(Tmemo tmemo) {
		getTmemos().remove(tmemo);
		tmemo.setTpipeline(null);

		return tmemo;
	}


	//bi-directional many-to-one association to Mdebitur
	@ManyToOne
	@JoinColumn(name="mdebiturfk")
	public Mdebitur getMdebitur() {
		return this.mdebitur;
	}

	public void setMdebitur(Mdebitur mdebitur) {
		this.mdebitur = mdebitur;
	}


	//bi-directional many-to-one association to Mrm
	@ManyToOne
	@JoinColumn(name="mrmfk")
	public Mrm getMrm() {
		return this.mrm;
	}

	public void setMrm(Mrm mrm) {
		this.mrm = mrm;
	}


	//bi-directional many-to-one association to Msector
	@ManyToOne
	@JoinColumn(name="msectorfk")
	public Msector getMsector() {
		return this.msector;
	}

	public void setMsector(Msector msector) {
		this.msector = msector;
	}


	//bi-directional many-to-one association to Munit
	@ManyToOne
	@JoinColumn(name="munitfk")
	public Munit getMunit() {
		return this.munit;
	}

	public void setMunit(Munit munit) {
		this.munit = munit;
	}


	//bi-directional many-to-one association to Tpipelinepart
	@OneToMany(mappedBy="tpipeline")
	public List<Tpipelinepart> getTpipelineparts() {
		return this.tpipelineparts;
	}

	public void setTpipelineparts(List<Tpipelinepart> tpipelineparts) {
		this.tpipelineparts = tpipelineparts;
	}

	public Tpipelinepart addTpipelinepart(Tpipelinepart tpipelinepart) {
		getTpipelineparts().add(tpipelinepart);
		tpipelinepart.setTpipeline(this);

		return tpipelinepart;
	}

	public Tpipelinepart removeTpipelinepart(Tpipelinepart tpipelinepart) {
		getTpipelineparts().remove(tpipelinepart);
		tpipelinepart.setTpipeline(null);

		return tpipelinepart;
	}

}