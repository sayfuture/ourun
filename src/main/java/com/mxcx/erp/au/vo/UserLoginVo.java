package com.mxcx.erp.au.vo;

import com.mxcx.erp.au.dao.entity.AuEmployee;

public class UserLoginVo {
	private Boolean flag;
	private AuEmployee auEmployee;
	
	
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	public AuEmployee getAuEmployee() {
		return auEmployee;
	}
	public void setAuEmployee(AuEmployee auEmployee) {
		this.auEmployee = auEmployee;
	}
}
