package com.mxcx.erp.au.service;

import java.util.List;

import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.au.dao.entity.AuPosition;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.base.commons.service.IBaseService;
import com.mxcx.ec.base.commons.util.DataGrid;

/**
 * 角色业务层接口
 * 
 * @author 20140624 *
 */
public interface AuPositionService extends IBaseService<AuPosition> {
	/**
	 * 添加角色
	 * 
	 * @param auPosition角色对象
	 * @return是否成功
	 */
	public Boolean addAuPosition(AuPosition auPosition, AuEmployee auEmployee);
	
	/**
	 * 删除角色
	 * @param id角色id
	 * @return是否成功
	 */
	public Boolean deleteAuPosition(String id, AuEmployee auEmployee);

	/**
	 * 修改角色
	 * 
	 * @param auPosition 角色对象
	 * @return是否成功
	 */
	public Boolean modifyAuPosition(AuPosition auPosition, AuEmployee auEmployee);

	/**
	 * 查询所有角色（分页）
	 * 
	 * @param pageParameter本页数据信息
	 * @return功能分页
	 */
	public DataGrid findList(PageParameter pageParameter);
	
	/**
	 * 查询所有角色（集合）
	 * 
	 * @param pageParameter本页数据信息
	 * @return角色集合
	 */
	public List<AuPosition> findAuPositionList();
	
}