package com.mxcx.erp.au.service;

import java.util.List;

import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.au.dao.entity.AuDept;
import com.mxcx.erp.au.dao.entity.TreeAuDeptVo;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.base.commons.service.IBaseService;
import com.mxcx.ec.base.commons.util.DataGrid;

/**
 * 工作组业务层接口
 * 
 * @author  20140625
 * 
 */
public interface AuDeptService extends IBaseService<AuDept> {
	/**
	 * 添加工作组
	 * 
	 * @param AuDept工作组对象
	 * @param employee登录用户
	 * @return是否成功
	 */
	public Boolean addAuDept(AuDept auDept, AuEmployee employee);

	/**
	 * 删除工作组
	 * 
	 * @param id组Id
	 * @param employee登录用户
	 * @return是否成功
	 */
	public Boolean deleteAuDept(String id, AuEmployee auEmployee);

	/**
	 * 修改工作组
	 * 
	 * @param AuDept工作组对象
	 * @param employee登录用户
	 * @return是否成功
	 */
	public Boolean modifyAuDept(AuDept auDept, AuEmployee employee);

	/**
	 * 查询所有工作组（分页）
	 * 
	 * @param pageParameter本页数据信息
	 * @return组分页
	 */
	public DataGrid findList(PageParameter pageParameter);

	/**
	 * 查询所有工作组（集合）
	 * 
	 * @param pageParameter本页数据信息
	 * @return组集合
	 */
	public List<AuDept> findList();
	
	
	
	public List<TreeAuDeptVo> findTree(String parentId);

	public AuDept getParentDept(AuDept auDept);

	public AuDept getOne(String id);
}