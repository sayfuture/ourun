package com.mxcx.erp.au.service;

import java.util.List;

import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.au.dao.entity.AuEmployeeDept;
import com.mxcx.erp.base.commons.service.IBaseService;

/**
 * 公司权限关系业务层接口
 * 
 * @author  20140626
 * 
 */
public interface AuEmployeeDeptService extends
		IBaseService<AuEmployeeDept> {
	
	/**
	 * 添加公司人员关系
	 * @param DeptId 公司id
	 * @return
	 * @author 
	 * @param deptId 
	 */
	public boolean addAuDeptAuthority(String employeeId,String deptId, AuEmployee employee);

	boolean removeAuDeptAuthority(String employeeId);

}
