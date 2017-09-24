package com.mxcx.erp.au.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mxcx.erp.base.commons.controller.BasePo;

/**
 * 按钮实体类
 * 
 * @author 2014/09/03
 * 
 */
@Entity
@Table(name = "AU_BUTTON")
public class AuButton extends BasePo {
	private static final long serialVersionUID = 1L;

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

	@Column(name = "FUN_NAME", nullable = false)
	private String funName; // 方法名
	
	@Column(name = "BTN_NAME", nullable = false)
	private String btnName; // 按钮名

	@Column(name = "NOTES")
	private String notes; // 按钮说明
	
	@ManyToOne
	@JoinColumn(name = "AU_AUTHORITY")
	private AuAuthority auAuthority; // 功能
	
	@Transient
	private String authorityId; // 功能id（页面映射）

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFunName() {
		return funName;
	}

	public void setFunName(String funName) {
		this.funName = funName;
	}

	public String getBtnName() {
		return btnName;
	}

	public void setBtnName(String btnName) {
		this.btnName = btnName;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public AuAuthority getAuAuthority() {
		return auAuthority;
	}

	public void setAuAuthority(AuAuthority auAuthority) {
		this.auAuthority = auAuthority;
	}

	public String getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(String authorityId) {
		this.authorityId = authorityId;
	}
}