package com.mxcx.erp.au.dao.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.mxcx.erp.base.commons.controller.BasePo;

/**
 * 部门实体类
 * 
 * @author  2014/06/23
 * 
 */
@Entity
@Table(name = "AU_DEPT")
public class AuDept extends BasePo {
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

	@Column(name = "NAME", nullable = false)
	private String name; // 工作组名称

	@Column(name = "NOTES")
	private String notes; // 小组说明
	
	@Column(name = "NODE_LEVEL")
	private Integer level; // 节点级别
	
	@Column(name = "SEQNO")
	private Integer seqno; 
	
	@ManyToOne
	@JoinColumn(name="PARENT_COMPANY_ID")
	private AuDept superAuDept;
	
	@OneToMany(mappedBy="superAuDept")
	@JsonIgnore
	private List<AuDept> auDepts;
	
	@Column(name = "CITYID")
	private String cityId; 

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

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public AuDept getSuperAuDept() {
		return superAuDept;
	}

	public void setSuperAuDept(AuDept superAuDept) {
		this.superAuDept = superAuDept;
	}

	public List<AuDept> getAuDepts() {
		return auDepts;
	}

	public void setAuDepts(List<AuDept> auDepts) {
		this.auDepts = auDepts;
	}

	public Integer getSeqno() {
		return seqno;
	}

	public void setSeqno(Integer seqno) {
		this.seqno = seqno;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	
	
}