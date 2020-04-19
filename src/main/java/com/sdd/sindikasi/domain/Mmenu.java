package com.sdd.sindikasi.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Type;

/**
 * The persistent class for the mmenu database table.
 * 
 */
@Entity
@NamedQuery(name = "Mmenu.findAll", query = "SELECT m FROM Mmenu m")
public class Mmenu implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer mmenupk;
	private String menugroup;
	private String menugroupicon;
	private String menuicon;
	private String menuname;
	private Integer menuorderno;
	private String menuparamname;
	private String menuparamvalue;
	private String menupath;
	private String menusubgroup;
	private String menusubgroupicon;

	public Mmenu() {
	}

	@Id
	@SequenceGenerator(name = "MMENU_MMENUPK_GENERATOR")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMENU_MMENUPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getMmenupk() {
		return this.mmenupk;
	}

	public void setMmenupk(Integer mmenupk) {
		this.mmenupk = mmenupk;
	}

	@Column(length = 40)
	@Type(type = "com.sdd.utils.usertype.TrimUserType")
	public String getMenugroup() {
		return this.menugroup;
	}

	public void setMenugroup(String menugroup) {
		this.menugroup = menugroup;
	}

	@Column(length = 50)
	@Type(type = "com.sdd.utils.usertype.TrimUserType")
	public String getMenugroupicon() {
		return this.menugroupicon;
	}

	public void setMenugroupicon(String menugroupicon) {
		this.menugroupicon = menugroupicon;
	}

	@Column(length = 50)
	@Type(type = "com.sdd.utils.usertype.TrimUserType")
	public String getMenuicon() {
		return this.menuicon;
	}

	public void setMenuicon(String menuicon) {
		this.menuicon = menuicon;
	}

	@Column(length = 100)
	@Type(type = "com.sdd.utils.usertype.TrimUserType")
	public String getMenuname() {
		return this.menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public Integer getMenuorderno() {
		return this.menuorderno;
	}

	public void setMenuorderno(Integer menuorderno) {
		this.menuorderno = menuorderno;
	}

	@Column(length = 10)
	@Type(type = "com.sdd.utils.usertype.TrimUserType")
	public String getMenuparamname() {
		return this.menuparamname;
	}

	public void setMenuparamname(String menuparamname) {
		this.menuparamname = menuparamname;
	}

	@Column(length = 30)
	@Type(type = "com.sdd.utils.usertype.TrimUserType")
	public String getMenuparamvalue() {
		return this.menuparamvalue;
	}

	public void setMenuparamvalue(String menuparamvalue) {
		this.menuparamvalue = menuparamvalue;
	}

	@Column(length = 100)
	@Type(type = "com.sdd.utils.usertype.TrimUserType")
	public String getMenupath() {
		return this.menupath;
	}

	public void setMenupath(String menupath) {
		this.menupath = menupath;
	}

	@Column(length = 40)
	@Type(type = "com.sdd.utils.usertype.TrimUserType")
	public String getMenusubgroup() {
		return this.menusubgroup;
	}

	public void setMenusubgroup(String menusubgroup) {
		this.menusubgroup = menusubgroup;
	}

	@Column(length = 50)
	@Type(type = "com.sdd.utils.usertype.TrimUserType")
	public String getMenusubgroupicon() {
		return this.menusubgroupicon;
	}

	public void setMenusubgroupicon(String menusubgroupicon) {
		this.menusubgroupicon = menusubgroupicon;
	}

}