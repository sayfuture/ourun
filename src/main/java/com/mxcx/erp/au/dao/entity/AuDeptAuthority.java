//package com.mxcx.erp.au.dao.entity;
//
//import java.io.Serializable;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
///**
// * 部门权限关系表
// * 
// * @author  20140626
// * 
// */
//@Entity
//@Table(name = "AU_DEPT_AUTHORITY")
//public class AuDeptAuthority implements Serializable {
//	private static final long serialVersionUID = -2684934497699038938L;
//
//	@Id
//	@Column(name = "ID", nullable = false)
//	private String id;
//
//	@ManyToOne
//	@JoinColumn(name = "AU_DEPT", nullable = false)
//	private AuDept auDept; // 公司
//
//	@ManyToOne
//	@JoinColumn(name = "AU_AUTHORITY", nullable = false)
//	private AuAuthority auAuthority; // 权限
//
//	@ManyToOne
//	@JoinColumn(name = "AU_BUTTON")
//	private AuButton auButton; // 按钮v
//	
//	
//
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public AuAuthority getAuAuthority() {
//		return auAuthority;
//	}
//
//	public void setAuAuthority(AuAuthority auAuthority) {
//		this.auAuthority = auAuthority;
//	}
//
//	public AuButton getAuButton() {
//		return auButton;
//	}
//
//	public void setAuButton(AuButton auButton) {
//		this.auButton = auButton;
//	}
//
//	public AuDept getAuDept() {
//		return auDept;
//	}
//
//	public void setAuDept(AuDept auDept) {
//		this.auDept = auDept;
//	}
//	
//}