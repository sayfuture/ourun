package com.mxcx.erp.au.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mxcx.erp.au.dao.entity.AuAuthority;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.au.dao.entity.AuMenu;
import com.mxcx.erp.base.adaptor.LogFunction;
import com.mxcx.erp.base.adaptor.LogModule;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.dao.IBaseDao;
import com.mxcx.erp.base.commons.service.BaseService;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.util.ToolUtils;
import com.mxcx.ec.base.commons.util.UuidDitch;
import com.mxcx.erp.lo.service.LogManagementService;

/**
 * 功能业务层接口实现
 * 
 * @author  2014/06/23
 * 
 */
@Service
public class AuAuthorityServiceImpl extends BaseService<AuAuthority> implements AuAuthorityService {
	@Autowired
	private IBaseDao<AuAuthority> auAuthorityDao; // 功能持久层
	@Autowired
	private LogManagementService logManagementService; // 日志记录
	@Autowired
	private AuMenuService auMenuService; // 菜单业务层接口
	@Autowired
	private AuEmployeeAuthorityService auEmployeeAuthorityService; // 人员功能业务层接口
	@Autowired
	private AuAuthorityPositionService auAhorityPositionService; // 功能角色关系控制层接口

	/**
	 * 添加功能
	 * 
	 * @param auAuthority功能对象
	 * @param employee登录用户
	 * @return是否成功
	 */
	@Override
	public Boolean addAuAuthority(AuAuthority auAuthority, AuEmployee employee) {
		Boolean flag = true;
		try{
			auAuthority.setId(UuidDitch.getId(LogModule.AUAUTHORITY.getModuleNo())); // 获取Id
			auAuthority.setState(1);
			auAuthority.setAuMenu((AuMenu)auMenuService.getOne(auAuthority.getAuMenuId(), AuMenu.class));
			this.addPo(auAuthority, employee);
		} catch (Exception e){
			e.printStackTrace();
			flag = false;
		} finally {
			this.logManagementService.addLogManagement(employee, ToolUtils.getHostAddress(), flag, LogModule.AUAUTHORITY.toString(), LogFunction.AUAUTHORITY_CREATE.toString(),auAuthority.toString());
		}
		return flag;
	}
	
	/**
	 * 删除功能
	 * @param id功能Id
	 * @param employee登录用户
	 * @return是否成功
	 */
	@Override
	public Boolean deleteAuAuthority(String id, AuEmployee employee) {
		AuAuthority auAuthority = null;
		Boolean flag = true;
		try{
			auAuthority = (AuAuthority) this.getOne(id, AuAuthority.class);
			this.removeByState(auAuthority);
			auEmployeeAuthorityService.deleteAuthorityAuEmployeeByAuthority(auAuthority.getId());
			auAhorityPositionService.deleteAuthorityAuEmployee(auAuthority.getId());
		} catch (Exception e){
			e.printStackTrace();
			flag = false;
		} finally {
			this.logManagementService.addLogManagement(employee, ToolUtils.getHostAddress(), flag, LogModule.AUAUTHORITY.toString(), LogFunction.AUAUTHORITY_DELETE.toString(),auAuthority.toString());
		}
		return flag;
	}

	/**
	 * 修改功能
	 * 
	 * @param auAuthority功能对象
	 * @return是否成功
	 */
	@Override
	public Boolean modifyAuAuthority(AuAuthority auAuthority, AuEmployee employee) {
		Boolean flag = true;
		AuAuthority auAuthorityTemp =null;
		try {
			 auAuthorityTemp = (AuAuthority) this.auAuthorityDao.getById(AuAuthority.class, auAuthority.getId());
			auAuthorityTemp.setName(auAuthority.getName());
			auAuthorityTemp.setUrl(auAuthority.getUrl());
			auAuthorityTemp.setRemarks(auAuthority.getRemarks());
			auAuthorityTemp.setEname(auAuthority.getEname());
			auAuthorityTemp.setLevel(auAuthority.getLevel());
			auAuthorityTemp.setAuMenu((AuMenu)auMenuService.getOne(auAuthority.getAuMenuId(), AuMenu.class));
			this.modify(auAuthorityTemp, employee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			this.logManagementService.addLogManagement(employee, ToolUtils.getHostAddress(), flag, LogModule.AUAUTHORITY.toString(), LogFunction.AUAUTHORITY_UPDATE.toString(),auAuthorityTemp.toString());
		}
		return flag;
	}

	/**
	 * 查询所有功能
	 * 
	 * @param pageParameter本页数据信息
	 * @return功能分页
	 */
	@Override
	public DataGrid findList(PageParameter pageParameter) {
		Map<String, Object> map = new HashMap();
		String hql = "from AuAuthority x inner join fetch x.auMenu m where x.state = 1 ";
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("name")
				&& pageParameter.getParaMap().get("name") != null
				&& !pageParameter.getParaMap().containsKey("type")) {
			hql += "and x.name=:name ";
			map.put("name", pageParameter.getParaMap().get("name"));
		}
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("name")
				&& pageParameter.getParaMap().get("name") != null
				&& pageParameter.getParaMap().containsKey("type")) {
			hql += "and x.name like:name ";
			map.put("name", "%" + pageParameter.getParaMap().get("name") + "%");
		}
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("funType")
				&& pageParameter.getParaMap().get("funType") != null
				&& pageParameter.getParaMap().containsKey("funType")) {
			hql += "and m.id=:funType ";
			map.put("funType", pageParameter.getParaMap().get("funType"));
		}
		hql+=" order by x.createDate  desc";
		pageParameter.setParaMap(map);
		return this.auAuthorityDao.findByhql(hql, pageParameter);
	}

	/**
	 * 根据角色查询功能集合
	 * 
	 * @param positionId角色ID
	 * @return功能集合
	 */
	@Override
	public List<AuAuthority> queryList(String positionId) {
		String hql = "select distinct(x) from AuAuthority x inner join fetch x.auAhorityPositionList as apt inner join fetch apt.auPosition as ap where ap.id=:id and x.state = 1 ";
		hql+=" order by x.createDate desc";
		Map<String, Object> map = new HashMap();
		map.put("id", positionId);

		List<AuAuthority> auEmployeeList = auAuthorityDao.find(hql, map);
		return auEmployeeList;
	}

	/**
	 * 根据人员查询人员匹配的权限
	 * 
	 * @param employeeId人员Id
	 * @return权限集合
	 */
	@Override
	public List<AuAuthority> findAuAuthorityListByEmployee(String employeeId) {
		String hql = "select distinct(x) from AuAuthority x inner join fetch x.auEmployeeAuthorityList as apt inner join fetch apt.auEmployee as ap where ap.id=:id and x.state = 1 ";
		hql+=" order by x.createDate desc";
		Map<String, Object> map = new HashMap();
		map.put("id", Integer.valueOf(employeeId));
		List<AuAuthority> auEmployeeList = auAuthorityDao.find(hql, map);
		return auEmployeeList;
	}
}