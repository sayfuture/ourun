package com.mxcx.erp.area.dao.entity;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;

/**
 * 按钮实体类
 * 
 * @author 2014/09/03
 * 
 */
@Entity
@Table(name = "AREAS")
public class Areas {

	@Id
	@Column(name = "ID", nullable = false)
	private String id;

	@Column(name = "AREA", nullable = false)
	private String s; // 区县名称

	@ManyToOne
	@JoinColumn(name = "CITYID")
	@JsonIgnore
	private Cities cities; // 省区ID

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public Cities getCities() {
		return cities;
	}

	public void setCities(Cities cities) {
		this.cities = cities;
	}
}