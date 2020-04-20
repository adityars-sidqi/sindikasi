package com.sdd.sindikasi.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Vsummary {
	
	private String status;
	private int totaldata;
	private BigDecimal amounttotal;
	
	@Id
	public int getTotaldata() {
		return totaldata;
	}
	public void setTotaldata(int totaldata) {
		this.totaldata = totaldata;
	}
	public BigDecimal getAmounttotal() {
		return amounttotal;
	}
	public void setAmounttotal(BigDecimal amounttotal) {
		this.amounttotal = amounttotal;
	}

	
}
