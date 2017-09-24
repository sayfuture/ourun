package com.mxcx.erp.au.dao.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.mxcx.erp.base.commons.controller.BasePo;

/**
 * 功能实体类
 * 
 * @author  2014/06/23
 * 
 */
@Entity
@Table(name = "AU_AUTHORITY")
public class AuAuthority extends BasePo {
	private static final long serialVersionUID = -4177115491709634568L;
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
	private String name; // 功能名称
	
	@Column(name = "ENAME", nullable = false)
	private String ename; // 功能名称
	
	@Column(name = "URL")
	private String url; // 功能路径

	@Column(name = "REMARKS")
	private String remarks; // 备注
	
	@Column(name = "TYPE")
	private Integer type; // 状态 1:个人管理；2：组管理；3： 全体管理；
	
	@Column(name = "LEVEL1")
	private Integer level; // 级别 1：权限；2：；3：商户：4：活动量
	
//	@OneToMany
//	@JoinColumn(name = "AU_AUTHORITY")
////	@JsonIgnore
//	private List<AAuAuthorityosition> AauAuthorityositionList; // 角色权限
	
//	@OneToMany
//	@JoinColumn(name = "AU_AUTHORITY")
//	@JsonIgnore
//	private List<AuEmployeeAuthority> auEmployeeAuthorityList; // 人员权限
	
	@ManyToOne
	@JoinColumn(name = "AU_MENU")
	private AuMenu auMenu; // 按钮
	
	@Transient
	private String auMenuId; // 功能id（页面映射）
	
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

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

//	public List<AAuAuthorityosition> getAAuAuthorityositionList() {
//		return AauAuthorityositionList;
//	}
//
//	public void setAAuAuthorityositionList(
//			List<AAuAuthorityosition> AauAuthorityositionList) {
//		this.AauAuthorityositionList = AauAuthorityositionList;
//	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

//	public List<AuEmployeeAuthority> getAuEmployeeAuthorityList() {
//		return auEmployeeAuthorityList;
//	}
//
//	public void setAuEmployeeAuthorityList(
//			List<AuEmployeeAuthority> auEmployeeAuthorityList) {
//		this.auEmployeeAuthorityList = auEmployeeAuthorityList;
//	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public AuMenu getAuMenu() {
		return auMenu;
	}

	public void setAuMenu(AuMenu auMenu) {
		this.auMenu = auMenu;
	}

	public String getAuMenuId() {
		return auMenuId;
	}

	public void setAuMenuId(String auMenuId) {
		this.auMenuId = auMenuId;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}
	
	
}