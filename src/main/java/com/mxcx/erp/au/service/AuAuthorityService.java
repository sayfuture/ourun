package com.mxcx.erp.au.service;

import java.util.List;

import com.mxcx.erp.au.dao.entity.AuAuthority;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.base.commons.service.IBaseService;
import com.mxcx.ec.base.commons.util.DataGrid;

/**
 * 功能业务层接口
 * 
 * @author  2014/06/23
 */
public interface AuAuthorityService extends IBaseService<AuAuthority> {
	
	/**
	 * 添加功能
	 * 
	 * @param auAuthority功能对象
	 * @param employee登录用户
	 * @return是否成功
	 */
	public Boolean addAuAuthority(AuAuthority auAuthority, AuEmployee employee);
	
	/**
	 * 删除功能
	 * @param id功能Id
	 * @param employee登录用户
	 * @return是否成功
	 */
	public Boolean deleteAuAuthority(String id, AuEmployee employee);

	/**
	 * 修改功能
	 * 
	 * @param auAuthority功能对象
	 * @return是否成功
	 */
	public Boolean modifyAuAuthority(AuAuthority auAuthority, AuEmployee employee);

	/**
	 * 查询所有功能
	 * 
	 * @param pageParameter本页数据信息
	 * @return功能分页
	 */
	public DataGrid findList(PageParameter pageParameter);

	/**
	 * 根据角色查询功能集合
	 * 
	 * @param positionId角色ID
	 * @return功能集合
	 */
	public List<AuAuthority> queryList(String positionId);
	
	/**
	 * 通过人员查找权限 
	 * @param employeeId人员Id
	 * @return权限集合
	 */
	public List<AuAuthority> findAuAuthorityListByEmployee(String employeeId); 
}