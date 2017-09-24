package com.mxcx.erp.base.commons.controller;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.mxcx.ec.base.commons.dao.entity.TransparentPo;
import com.mxcx.ec.base.commons.util.DateUtil;

/**
 * @see(功能介绍):基础类
 * @version(版本号): 1.0
 * @date(创建日期): 2014-9-2
 * @author 王森
 */
@MappedSuperclass
public abstract class BasePo implements TransparentPo{
	private static final long serialVersionUID = -8881924957668182402L;
	
	@Column(name = "STATE")
	private Integer state; // 状态
	
	@Column(name = "CREATE_USER")
	private String createUser; // 创建人UUID

	@Column(name = "CREATE_DATE")
	private Date createDate; // 创建时间

	@Column(name = "UPDATE_USER")
	private String updateUser; // 修改人UUID
	
	@Column(name = "UPDATE_DATE")
	private Date updateDate; // 修改时间

	@Transient
	private String createTimeBasePo; // 创建时间
	
	@Transient
	private String updateTimeBasePo; // 创建时间
	
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getUpdateTimeBasePo() {
		if(null != updateDate && !updateDate.equals("0000-00-00 00:00:00")){
			updateTimeBasePo = DateUtil.format(updateDate, "");
		}
		return updateTimeBasePo;
	}

	public void setUpdateTimeBasePo(String updateTimeBasePo) {
		this.updateTimeBasePo = updateTimeBasePo;
	}

	public String getCreateTimeBasePo() {
		if(null != createDate && !createDate.equals("0000-00-00 00:00:00")){
			createTimeBasePo = DateUtil.format(createDate, "");
		}
		return createTimeBasePo;
	}

	public void setCreateTimeBasePo(String createTimeBasePo) {
		this.createTimeBasePo = createTimeBasePo;
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
	
//	@Override
//	public String toString(){
//		return JSON.toJSONString(this, true);
//	}
}
