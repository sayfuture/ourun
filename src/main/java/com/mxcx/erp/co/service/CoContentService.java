package com.mxcx.erp.co.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.base.commons.service.IBaseService;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.erp.co.dao.entity.CoContent;

/**
 * 内容管理接口
 * 
 * @author  20140910
 * 
 */
public interface CoContentService extends IBaseService<CoContent> {
	/**
	 * 添加内容
	 * 
	 * @param coContent内容对象
	 * @param auEmployee人员对象
	 * @return是否成功
	 */
	public Boolean addCoContent(CoContent coContent, HttpServletRequest request, String fileName, AuEmployee auEmployee);

	/**
	 * 修改内容
	 * 
	 * @param coContent内容对象
	 * @param auEmployee人员对象
	 * @return是否成功
	 */
	public Boolean modifyCoContent(CoContent coContent, HttpServletRequest request, String fileName, AuEmployee auEmployee);

	/**
	 * 删除内容
	 * 
	 * @param ids内容id
	 * @param auEmployee人员对象
	 * @return是否成功
	 */
	public Boolean removeCoContents(String id, HttpServletRequest request, AuEmployee auEmployee);

	/**
	 * 查询所有的内容（分页）
	 * 
	 * @param pageParameter本页数据信息
	 * @return内容分页
	 */
	public DataGrid findList(PageParameter pageParameter,AuEmployee auEmployee);

	/**
	 * 查询所有内容（集合）
	 * 
	 * @return内容集合
	 */
	public List<CoContent> findList();

	/**
	 * 根据类型查找对应的内容（全）
	 * 
	 * @param id内容类型id
	 * @return内容结合
	 */
	public List<CoContent> getCoContentByCoType(String id,AuEmployee  auEmployee );

	/**
	 * 设置点击量
	 * 
	 * @param coContent内容
	 * @return是否成功
	 */
	public Boolean modifyCoContentClick(CoContent coContent);

	public Boolean modifyNewsPage(String id);
}