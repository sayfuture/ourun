package com.mxcx.erp.au.dao.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 人员权限关系表
 * 
 * @author  20140626
 * 
 */
@Entity
@Table(name = "AU_EMPLOYEE_AUTHORITY")
public class AuEmployeeAuthority implements Serializable {
	private static final long serialVersionUID = -2684934497699038938L;

	@Id
	@Column(name = "ID", nullable = false)
	private String id;

	@ManyToOne
	@JoinColumn(name = "AU_EMPLOYEE", nullable = false)
	private AuEmployee auEmployee; // 人员

	@ManyToOne
	@JoinColumn(name = "AU_AUTHORITY", nullable = false)
	private AuAuthority auAuthority; // 权限

	@ManyToOne
	@JoinColumn(name = "AU_BUTTON")
	private AuButton auButton; // 按钮v
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AuEmployee getAuEmployee() {
		return auEmployee;
	}

	public void setAuEmployee(AuEmployee auEmployee) {
		this.auEmployee = auEmployee;
	}

	public AuAuthority getAuAuthority() {
		return auAuthority;
	}

	public void setAuAuthority(AuAuthority auAuthority) {
		this.auAuthority = auAuthority;
	}

	public AuButton getAuButton() {
		return auButton;
	}

	public void setAuButton(AuButton auButton) {
		this.auButton = auButton;
	}
}