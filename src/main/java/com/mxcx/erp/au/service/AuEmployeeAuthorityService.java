package com.mxcx.erp.au.service;

import java.util.List;

import com.mxcx.erp.au.dao.entity.AuEmployeeAuthority;
import com.mxcx.erp.base.commons.service.IBaseService;

/**
 * 人员权限关系业务层接口
 * 
 * @author  20140626
 * 
 */
public interface AuEmployeeAuthorityService extends
		IBaseService<AuEmployeeAuthority> {
	
	/**
	 * 添加人员权限关系
	 * @param employeeId 人员id
	 * @param authorityId 功能id
	 * @param type 对应的按钮（id）组
	 * @return
	 * @author 
	 */
	public boolean addAuEmployeeAuthority(String employeeId,
			String authorityId, String[] type);

	/**
	 * 通过人员id删除人员权限关系
	 * @param employeeId 人员id
	 * @author 
	 */
	public Boolean deleteAuEmployeeAuthority(String employeeId);
	
	/**
	 * 删除指定人员权限关系
	 * @param authorityId 权限id
	 * @param employeeId 人员id
	 * @author 
	 */
	public Boolean deleteAuthorityAuEmployee(String authorityId,Integer employeeId);
	
	/**
	 * 通过权限id删除人员权限关系
	 * @param authorityId
	 * @author 
	 */
	public Boolean deleteAuthorityAuEmployeeByAuthority(String authorityId);
	
	/**
	 * 根据人员查询人员权限关系
	 * @param auEmployeeId 人员id
	 * @return
	 * @author 
	 */
	public List<AuEmployeeAuthority> findAuEmployeeAuthorityListByAuEmployee(String auEmployeeId);
	
	/**
	 * 根据权限查询人员权限关系
	 * @param auAuthorityId 权限id
	 * @return
	 * @author 
	 */
	public List<AuEmployeeAuthority> findAuEmployeeAuthorityListByAuthority(String auAuthorityId);

	/**
	 * 通过人员和功能查找该人员在该功能下所具有的按钮
	 * @param employeeId 人员id
	 * @param authorityId 权限id
	 * @return
	 * @author 
	 */
	public List<AuEmployeeAuthority> getButtonByEmployeeAndAuthority(
			String employeeId, String authorityId);
}
