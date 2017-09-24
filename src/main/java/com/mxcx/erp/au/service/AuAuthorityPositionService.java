package com.mxcx.erp.au.service;

import java.util.List;

import com.mxcx.erp.au.dao.entity.AuAuthorityPosition;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.base.commons.service.IBaseService;
import com.mxcx.ec.base.commons.util.DataGrid;

/**
 * 角色功能关系业务层接口
 * 
 * @author  20140624
 * 
 */
public interface AuAuthorityPositionService extends IBaseService<AuAuthorityPosition> {
	/**
	 * 添加功能
	 * 
	 * @param AuAuthorityPosition功能对象
	 * @return
	 */
	public Boolean addAuAuthorityPosition(String positionId, String authorityId, String[] i);

	/**
	 * 分页查询
	 * 
	 * @param page页码
	 * @param rows行数
	 * @return功能集合
	 */
	public List<AuAuthorityPosition> queryDAO(int page, int rows);

	/**
	 * 统计总数
	 * 
	 */
	public int queryCountDAO();

	/**
	 * 根据角色查询角色功能关系
	 * 
	 * @param positionId角色Id
	 */
	public List<AuAuthorityPosition> findAuAuthorityPositionListByPosition(String positionIds);

	/**
	 * 查询所有功能
	 * 
	 * @param pageParameter
	 *            本页数据信息
	 */
	public DataGrid findList(PageParameter pageParameter);

	/**
	 * 根据功能查询角色功能关系集合
	 * 
	 * @param auAuthorityId功能ID
	 * @return
	 */
	public List<AuAuthorityPosition> findAuAuthorityPositionListByAuAhority(String auAuthorityId);
	
	/**
	 * 根据功能删除功能角色关系
	 * @param authorityId
	 */
	public void deleteAuthorityAuEmployee(String authorityId);
	
	
	/**
	 * 通过角色和权限查找对应的权限信息   zzq
	 * @param positionId
	 * @param authorityId
	 * @return
	 */
	public List<AuAuthorityPosition> getButtonByPositionAndAuthority(
			String positionId, String authorityId);
}