package com.mxcx.erp.au.service;

import java.util.List;

import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.au.dao.entity.AuMenu;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.base.commons.service.IBaseService;
import com.mxcx.ec.base.commons.util.DataGrid;

/**
 * 菜单组业务层接口
 * 
 * @author 王森 20140904
 */
public interface AuMenuService extends IBaseService<AuMenu> {

	/**
	 * 添加菜单组
	 * 
	 * @param auMenu菜单对象
	 * @return
	 */
	public Boolean addAuMenu(AuMenu auMenu, AuEmployee auEmployee);

	/**
	 * 删除菜单组
	 * @param  auMenu菜单对象
	 * @return
	 */
	public Boolean deleteAuMenu(String id, AuEmployee auEmployee);

	/**
	 * 修改菜单组
	 * @param id
	 * @return
	 */
	public Boolean modifyAuMenu(AuMenu auMenu, AuEmployee auEmployee);

	/**
   * 查询菜单组（集合）
   * @param pageParameter
   *
   */
	public List<AuMenu> findList();
	
	/**
	 * 查询所有菜单组（分页）
	 * @param pageParameter
	 */
	
	public DataGrid findList(PageParameter pageParameter);
	
	
	public List<AuMenu> getAuMenuList();
	
	
}
