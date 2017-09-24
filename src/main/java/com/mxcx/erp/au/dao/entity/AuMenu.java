package com.mxcx.erp.au.dao.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.mxcx.erp.base.commons.controller.BasePo;

/**
 * 菜单组实体类
 * 
 * 
 */
@Entity
@Table(name = "AU_MENU")
public class AuMenu extends BasePo {
	private static final long serialVersionUID = 3520388577305439175L;
	
	/*
	 * 删除
	 */
	public static final int INDEX_STATE_DELETE = 0; // 删除
	/*
	 * 正常
	 */
	public static final int INDEX_STATE_NORMAL = 1; // 正常

	@Id
	@Column(name = "ID", nullable = false)
	private String id;

	@Column(name = "NAME")   
	private String name; // 工作组名称
	
	@Column(name = "NOTES")
	private String notes; // 菜单说明
	
	@OneToMany
	@JoinColumn(name="AU_MENU")
	@JsonIgnore
	private List<AuAuthority> auauthoritys; // 权限集合
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<AuAuthority> getAuauthoritys() {
		return auauthoritys;
	}

	public void setAuauthoritys(List<AuAuthority> auauthoritys) {
		this.auauthoritys = auauthoritys;
	}

	public void setName(String name) {
		this.name = name;
	}
}	