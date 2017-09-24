package com.mxcx.erp.au.vo;
import com.mxcx.erp.au.dao.entity.AuEmployee;

public class AuEmployeeManagerVo {
	private boolean flag;
	private AuEmployee auEmployee;
	
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public AuEmployee getAuEmployee() {
		return auEmployee;
	}
	public void setAuEmployee(AuEmployee auEmployee) {
		this.auEmployee = auEmployee;
	}
}
