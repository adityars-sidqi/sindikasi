package com.sdd.sindikasi.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vpipelinesector implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer msectorpk;
	private String sectorname;
	private Integer tdebitur;
	private BigDecimal tpc;
	private BigDecimal tcredit;
	private BigDecimal tsp;
	private BigDecimal tfee;
	
	@Id
	public Integer getMsectorpk() {
		return msectorpk;
	}
	public void setMsectorpk(Integer msectorpk) {
		this.msectorpk = msectorpk;
	}
	@Id
	public String getSectorname() {
		return sectorname;
	}
	public void setSectorname(String sectorname) {
		this.sectorname = sectorname;
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
