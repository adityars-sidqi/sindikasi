package com.sdd.sindikasi.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vpipelineunit implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer munitpk;
	private String isbumn;
	private String unitcode;
	private Integer tdebitur;
	private BigDecimal tpc;
	private BigDecimal tcredit;
	private BigDecimal tsp;
	private BigDecimal tfee;
	
	@Id
	public Integer getMunitpk() {
		return munitpk;
	}
	public void setMunitpk(Integer munitpk) {
		this.munitpk = munitpk;
	}
	@Id
	public String getIsbumn() {
		return isbumn;
	}
	public void setIsbumn(String isbumn) {
		this.isbumn = isbumn;
	}
	@Id
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
