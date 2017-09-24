package com.mxcx.erp.au.dao.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 部门权限关系表
 * 
 * @author  20140626
 * 
 */
@Entity
@Table(name = "AU_EMPLOYEE_DEPT")
public class AuEmployeeDept implements Serializable {
	private static final long serialVersionUID = -2684934497699038930L;

	@Id
	@Column(name = "ID", nullable = false)
	private String id;

	@ManyToOne
	@JoinColumn(name = "AU_DEPT", nullable = false)
	private AuDept auDept; // 公司

	@ManyToOne
	@JoinColumn(name = "AU_EMPLOYEE", nullable = false)
	private AuEmployee auEmployee; // 人员

	@ManyToOne
	@JoinColumn(name = "AU_COMPANY")
	private AuDept superAuDept; // 分公司顶级节点
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AuDept getAuDept() {
		return auDept;
	}

	public void setAuDept(AuDept auDept) {
		this.auDept = auDept;
	}

	public AuEmployee getAuEmployee() {
		return auEmployee;
	}

	public void setAuEmployee(AuEmployee auEmployee) {
		this.auEmployee = auEmployee;
	}

	public AuDept getSuperAuDept() {
		return superAuDept;
	}

	public void setSuperAuDept(AuDept superAuDept) {
		this.superAuDept = superAuDept;
	}
	
	
}