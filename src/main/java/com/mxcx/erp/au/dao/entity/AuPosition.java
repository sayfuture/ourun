package com.mxcx.erp.au.dao.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.mxcx.erp.base.commons.controller.BasePo;

/**
 * 角色实体类
 * 
 * @author 2014/06/23
 * 
 */
@Entity
@Table(name = "AU_POSITION")
public class AuPosition extends BasePo {
	private static final long serialVersionUID = -1673757726630774014L;
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

	@Column(name = "NAME", nullable = false)
	private String name; // 角色名称

	@Transient
	private Integer[] authorityIds; // 权限数组

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer[] getAuthorityIds() {
		return authorityIds;
	}

	public void setAuthorityIds(Integer[] authorityIds) {
		this.authorityIds = authorityIds;
	}


}