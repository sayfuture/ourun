package com.mxcx.erp.area.dao.entity;

import com.mxcx.erp.au.dao.entity.AuAuthority;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * 按钮实体类
 * 
 * @author 2014/09/03
 * 
 */
@Entity
@Table(name = "PROVINCES")
public class Provinces {

	@Id
	@Column(name = "ID", nullable = false)
	private String id;

	@Column(name = "PROVINCE", nullable = false)
	private String p; // 省级


	@OneToMany(fetch=FetchType.EAGER,mappedBy="povinces",cascade = {CascadeType.MERGE,CascadeType.REFRESH})
	private Set<Cities> c;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	public Set<Cities> getC() {
		return c;
	}

	public void setC(Set<Cities> c) {
		this.c = c;
	}
}