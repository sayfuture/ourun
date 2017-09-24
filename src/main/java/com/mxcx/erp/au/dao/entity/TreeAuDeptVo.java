package com.mxcx.erp.au.dao.entity;

import java.util.Date;
import java.util.List;

import com.mxcx.ec.base.commons.util.DateUtil;

/**
 * 工作组实体类
 * 
 * @author  2014/06/23
 * 
 */
public class TreeAuDeptVo  {

	/*
	 * 删除
	 */
	public static final int INDEX_STATE_DELETE = 0; // 删除
	/*
	 * 正常
	 */
	public static final int INDEX_STATE_NORMAL = 1; // 正常
	private String id;

	private String name; // 工作组名称

	private String notes; // 小组说明
	
//	private TreeAuDeptVo superAuDept;
	
	private List<AuDept> auDepts;
	
	private boolean checked=false;//是否选中
	
	private String createUser; // 创建人UUID

	private Date createDate; // 创建时间

	private String updateUser; // 修改人UUID
	
	private Date updateDate; // 修改时间
	
	private String state;

	private Integer level;
	
	private Integer seqno;
	
	private String createTimeBasePo; // 创建时间
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

	public List<AuDept> getAuDepts() {
		return auDepts;
	}

	public void setAuDepts(List<AuDept> auDepts) {
		this.auDepts = auDepts;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
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

	public String getCreateTimeBasePo() {
		if(null != createDate && !createDate.equals("0000-00-00 00:00:00")){
			createTimeBasePo = DateUtil.format(createDate, "yyyy-MM-dd HH:mm:ss");
		}
		return createTimeBasePo;
	}

	public void setCreateTimeBasePo(String createTimeBasePo) {
		this.createTimeBasePo = createTimeBasePo;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}


	public Integer getSeqno() {
		return seqno;
	}

	public void setSeqno(Integer seqno) {
		this.seqno = seqno;
	}
	
}