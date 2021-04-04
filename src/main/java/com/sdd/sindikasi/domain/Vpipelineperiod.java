package com.sdd.sindikasi.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

@Entity
public class Vpipelineperiod implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer yearperiod;
	private String qperiod;
	private Integer munitpk;
	private String isbumn;
	private String unitcode;
	private Integer tdebitur;
	private BigDecimal tpc;
	private BigDecimal tcredit;
	private BigDecimal tsp;
	private BigDecimal tfee;
	
	@Id
	public Integer getYearperiod() {
		return yearperiod;
	}
	public void setYearperiod(Integer yearperiod) {
		this.yearperiod = yearperiod;
	}
	@Id
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getQperiod() {
		return qperiod;
	}
	public void setQperiod(String qperiod) {
		this.qperiod = qperiod;
	}
	@Id
	public Integer getMunitpk() {
		return munitpk;
	}
	public void setMunitpk(Integer munitpk) {
		this.munitpk = munitpk;
	}
	@Id
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getIsbumn() {
		return isbumn;
	}
	public void setIsbumn(String isbumn) {
		this.isbumn = isbumn;
	}
	@Id
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getUnitcode() {
		return unitcode;
	}
	public void setUnitcode(String unitcode) {
		this.unitcode = unitcode;
	}
	public Integer getTdebitur() {
		return tdebitur;
	}
	public void setTdebitur(Integer tdebitur) {
		this.tdebitur = tdebitur;
	}
	public BigDecimal getTpc() {
		return tpc;
	}
	public void setTpc(BigDecimal tpc) {
		this.tpc = tpc;
	}	
	public BigDecimal getTcredit() {
		return tcredit;
	}
	public void setTcredit(BigDecimal tcredit) {
		this.tcredit = tcredit;
	}
	public BigDecimal getTsp() {
		return tsp;
	}
	public void setTsp(BigDecimal tsp) {
		this.tsp = tsp;
	}
	public BigDecimal getTfee() {
		return tfee;
	}
	public void setTfee(BigDecimal tfee) {
		this.tfee = tfee;
	}
		
}
