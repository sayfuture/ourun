package com.mxcx.erp.au.dao.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 角色功能实体类
 * 
 * @author  2014/06/23
 * 
 */
@Entity
@Table(name = "AU_AUTHORITY_POSITION")
public class AuAuthorityPosition implements Serializable {
	private static final long serialVersionUID = -6993680393890195360L;
	
	@Id
	@Column(name = "ID", nullable = false)
	private String id;

	@ManyToOne
	@JoinColumn(name = "AU_POSITION", nullable = false)
	private AuPosition auPosition; // 角色

	@ManyToOne
	@JoinColumn(name = "AU_AUTHORITY", nullable = false)
	private AuAuthority auAuthority; // 功能

	@ManyToOne
	@JoinColumn(name = "AU_BUTTON")
	private AuButton auButton; // 按钮
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AuButton getAuButton() {
		return auButton;
	}

	public void setAuButton(AuButton auButton) {
		this.auButton = auButton;
	}

	public AuPosition getAuPosition() {
		return auPosition;
	}

	public void setAuPosition(AuPosition auPosition) {
		this.auPosition = auPosition;
	}

	public AuAuthority getAuAuthority() {
		return auAuthority;
	}

	public void setAuAuthority(AuAuthority auAuthority) {
		this.auAuthority = auAuthority;
	}
}