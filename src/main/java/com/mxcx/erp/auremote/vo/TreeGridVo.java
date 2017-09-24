package com.mxcx.erp.auremote.vo;
import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import com.mxcx.ec.base.commons.util.DateUtil;

public class TreeGridVo {
	private String id;
	private String name;
	private String state;
	private String engName;
	private boolean checked=false;//是否选中
	
	private String createUser; // 创建人UUID

	private Date createDate; // 创建时间

	private String updateUser; // 修改人UUID
	
	private Date updateDate; // 修改时间
	
	private List<TreeGridVo> children;
	
	private String createTimeBasePo; // 创建时间
	
	private Integer levelNode;

	public String getCreateTimeBasePo() {
		if(null != createDate && !createDate.equals("0000-00-00 00:00:00")){
			createTimeBasePo = DateUtil.format(createDate, "yyyy-MM-dd HH:mm:ss");
		}
		return createTimeBasePo;
	}
	public boolean getChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
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
	public List<TreeGridVo> getChildren() {
		return children;
	}
	public void setChildren(List<TreeGridVo> children) {
		this.children = children;
	}
	public String getEngName() {
		return engName;
	}
	public void setEngName(String engName) {
		this.engName = engName;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getLevelNode() {
		return levelNode;
	}
	public void setLevelNode(Integer levelNode) {
		this.levelNode = levelNode;
	}
	
	
}
