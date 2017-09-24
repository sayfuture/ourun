package com.mxcx.erp.au.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.au.dao.entity.AuMenu;
import com.mxcx.erp.base.adaptor.LogModule;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.base.commons.service.BaseService;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.util.UuidDitch;

/**
 * 菜单组业务层接口实现
 * @author Administrator
 *20140904
 */
@Service
public class AuMenuServiceImpl extends BaseService<AuMenu> implements AuMenuService{
	/**
	 * 添加菜单组
	 * @param auMenu菜单对象
	 * @return
	 */
	@Override
	public Boolean addAuMenu(AuMenu auMenu, AuEmployee auEmployee) {
		auMenu.setId(UuidDitch.getId(LogModule.AUMENU.getModuleNo()));
		auMenu.setState(1);
		return this.addPo(auMenu, auEmployee);
	}


	/**
	 * 修改菜单组
	 * @param id
	 * @return
	 */
	@Override
	public Boolean modifyAuMenu(AuMenu auMenu, AuEmployee employee) {
		Boolean flag = true;
		try {
			AuMenu auMenuTemp = (AuMenu) this.baseDao.getById(AuMenu.class, auMenu.getId());
			auMenuTemp.setName(auMenu.getName());
			auMenuTemp.setNotes(auMenu.getNotes());
			this.modify(auMenuTemp ,employee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}finally{
		}
		return flag;
	}
	
	/**
	 * 删除菜单组
	 * @param  auMenu菜单对象
	 * @return
	 */
	@Override
	public Boolean deleteAuMenu(String id, AuEmployee employee) {
		AuMenu auMenu = null;
		Boolean flag = true;
		try{
			auMenu = (AuMenu)this.baseDao.getById(AuMenu.class, id);
			this.removeByState(auMenu);
		} catch (Exception e){
			e.printStackTrace();
			flag = false;
		} finally {
		}
		return flag;
	}
	
	/**
	   * 查询菜单组（集合）
	   * @param pageParameter
	   *
	   */
	@Override
	public List<AuMenu> findList() {
		return this.baseDao.find("from AuMenu x where x.state = 1 ");
	}
	/**
	 * 查询所有菜单组（分页）
	 * @param pageParameter
	 */
	@Override
	public DataGrid findList(PageParameter pageParameter) {
		Map<String, Object> map = new HashMap();
		String hql = "from AuMenu x where x.state = 1 ";
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("name")
				&& pageParameter.getParaMap().get("name") != null && !pageParameter.getParaMap().containsKey("type")) {
			hql += "and x.name=:name";
		}
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("name")
				&& pageParameter.getParaMap().get("name") != null && pageParameter.getParaMap().containsKey("type")) {
			hql += "and x.name like:name";
			map.put("name", "%"+ pageParameter.getParaMap().get("name")+"%");
			pageParameter.setParaMap(map);
		}
		return this.baseDao.findByhql(hql, pageParameter);
	}


	@Override
	public List<AuMenu> getAuMenuList() {
		return this.baseDao.find("from AuMenu x where x.state = 1 ");
	}



}
