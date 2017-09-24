package com.mxcx.erp.au.service;

import java.util.List;

import com.mxcx.erp.au.dao.entity.AuButton;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.base.commons.service.IBaseService;
import com.mxcx.ec.base.commons.util.DataGrid;

/**
 * 按钮业务层接口
 * 
 * @author 20140903
 * 
 */
public interface AuButtonService extends IBaseService<AuButton> {
	/**
	 * 添加按钮
	 * 
	 * @param AuDept工作组对象
	 * @return
	 */
	public Boolean addAuButton(AuButton auButton, AuEmployee auEmployee);

	/**
	 * 修改按钮
	 * 
	 * @param auButton
	 *            按钮对象
	 */
	public Boolean modifyAuButton(AuButton auButton, AuEmployee auEmployee);

	/**
	 * 删除按钮
	 * 
	 * @param id
	 *            按钮id
	 * @return
	 */
	public Boolean removeAuButton(String id, AuEmployee auEmployee);

	/**
	 * 查询所有的按钮（分页）
	 * 
	 * @param pageParameter
	 *            本页数据信息
	 */
	public DataGrid findList(PageParameter pageParameter);

	/**
	 * 查询所有按钮（集合）
	 * 
	 * @param pageParameter
	 *            本页数据信息
	 */
	public List<AuButton> findList();
	
	/**
	 * 根据功能查找对应的按钮（全）
	 * @param id
	 * @return
	 */
	public List<AuButton> getAubuttonByAuthority(String id);
}