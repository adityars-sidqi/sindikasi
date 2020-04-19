package com.sdd.sindikasi.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

/**
 * The persistent class for the musergroupmenu database table.
 * 
 */
@Entity
@NamedQuery(name="Musergroupmenu.findAll", query="SELECT m FROM Musergroupmenu m")
public class Musergroupmenu implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer musergroupmenupk;
	private Mmenu mmenu;
	private Musergroup musergroup;

	public Musergroupmenu() {
	}


	@Id
	@SequenceGenerator(name="MUSERGROUPMENU_MUSERGROUPMENUPK_GENERATOR", sequenceName="MUSERGROUPMENU_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MUSERGROUPMENU_MUSERGROUPMENUPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getMusergroupmenupk() {
		return this.musergroupmenupk;
	}

	public void setMusergroupmenupk(Integer musergroupmenupk) {
		this.musergroupmenupk = musergroupmenupk;
	}


	//bi-directional many-to-one association to Mmenu
	@ManyToOne
	@JoinColumn(name="mmenufk")
	public Mmenu getMmenu() {
		return this.mmenu;
	}

	public void setMmenu(Mmenu mmenu) {
		this.mmenu = mmenu;
	}


	//bi-directional many-to-one association to Musergroup
	@ManyToOne
	@JoinColumn(name="musergroupfk")
	public Musergroup getMusergroup() {
		return this.musergroup;
	}

	public void setMusergroup(Musergroup musergroup) {
		this.musergroup = musergroup;
	}

}