package com.mxcx.erp.area.dao.entity;

import com.mxcx.erp.au.dao.entity.AuAuthority;
import com.mxcx.erp.base.commons.controller.BasePo;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;

/**
 * 按钮实体类
 * 
 * @author 2014/09/03
 * 
 */
@Entity
@Table(name = "CITIES")
public class Cities  {

	@Id
	@Column(name = "ID", nullable = false)
	private String id;

	@Column(name = "CITY", nullable = false)
	private String n; // 城市名称

	@ManyToOne
	@JoinColumn(name = "PROVINCEID")
	@JsonIgnore
	private Provinces povinces; // 省区ID

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public Provinces getPovinces() {
		return povinces;
	}

	public void setPovinces(Provinces povinces) {
		this.povinces = povinces;
	}
}