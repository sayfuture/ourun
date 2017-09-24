package com.mxcx.erp.au.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mxcx.erp.au.dao.entity.AuAuthority;
import com.mxcx.erp.au.dao.entity.AuButton;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.au.dao.entity.AuEmployeeAuthority;
import com.mxcx.erp.base.adaptor.LogModule;
import com.mxcx.ec.base.commons.dao.IBaseDao;
import com.mxcx.erp.base.commons.service.BaseService;
import com.mxcx.ec.base.commons.util.StringCheck;
import com.mxcx.ec.base.commons.util.UuidDitch;

/**
 * 人员权限关系业务层接口实现
 * 
 * @author  20140626
 * 
 */
@Service
public class AuEmployeeAuthorityServiceImpl extends
		BaseService<AuEmployeeAuthority> implements AuEmployeeAuthorityService {
	@Autowired
	private IBaseDao<AuEmployeeAuthority> baseDao;  //人员权限dao
	@Autowired
	private IBaseDao<AuButton> buttonDao;	// 按钮dao
	@Autowired
	private AuEmployeeService auEmployeeService; //人员服务层
	@Autowired
	private AuAuthorityService auAuthorityService; //权限业务层
	
	/**
	 * 添加人员权限关系
	 * 
	 * @author 
	 */
	@Override
	public boolean addAuEmployeeAuthority(String employeeId, String authorityId, String[] type) {
		Boolean flag = true;
		try {
			AuEmployee auEmployee = (AuEmployee) this.auEmployeeService.getOne(employeeId, AuEmployee.class);
			AuAuthority auAuthority = (AuAuthority) this.auAuthorityService.getOne(authorityId, AuAuthority.class);
			AuEmployeeAuthority ay = null;
			AuButton button ;
			//如果有按钮对象要绑定
			//循环按钮对象
			if(null!=type&&type.length>0){
				for(String btnId : type){
					ay = new AuEmployeeAuthority();
					//该id对应的按钮对象
					button = buttonDao.get(AuButton.class, btnId);
					//设置id
					ay.setId(UuidDitch.getId(LogModule.AUEMPLOYEEAUTHORITY.getModuleNo()));
					//设置权限
					ay.setAuAuthority(auAuthority);
					//设置人员
					ay.setAuEmployee(auEmployee);
					//设置按钮
					ay.setAuButton(button);
					this.baseDao.add(ay); 
				}
			}else{
				ay = new AuEmployeeAuthority();
				//设置id
				ay.setId(UuidDitch.getId(LogModule.AUEMPLOYEEAUTHORITY.getModuleNo()));
				//设置权限
				ay.setAuAuthority(auAuthority);
				//设置人员
				ay.setAuEmployee(auEmployee);
				this.baseDao.add(ay);
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 根据人员查询人员权限关系
	 * 
	 * @author 
	 */
	@Override
	public List<AuEmployeeAuthority> findAuEmployeeAuthorityListByAuEmployee(
			String auEmployeeId) {
		String hql = "from AuEmployeeAuthority x inner join fetch x.auEmployee as ae where ae.id=:id";
		Map<String, Object> map = new HashMap();
		map.put("id", auEmployeeId);
		return this.baseDao.find(hql, map);
	}
	
	/**
	 * 根据权限查询人员权限关系
	 * 
	 * @author 
	 */
	@Override
	public List<AuEmployeeAuthority> findAuEmployeeAuthorityListByAuthority(
			String auAuthorityId) {
		String hql = "from AuEmployeeAuthority x inner join fetch x.auAuthority as ae where ae.id=:id";
		Map<String, Object> map = new HashMap();
		map.put("id", auAuthorityId);
		return this.baseDao.find(hql, map);
	}
	
	/**
	 * 通过人员id删除人员权限关系
	 * 
	 * @author 
	 */
	@Override
	public Boolean deleteAuEmployeeAuthority(String employeeId) {
		Boolean flag = true;
		try {
			//改员工下的权限关系
			List<AuEmployeeAuthority> auEmployeeAuthorities = this.findAuEmployeeAuthorityListByAuEmployee(employeeId);
			//循环删除
			for (AuEmployeeAuthority auEmployeeAuthority : auEmployeeAuthorities) {
				this.baseDao.remove(auEmployeeAuthority);
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 删除指定人员权限关系
	 * @author 
	 */
	@Override
	public Boolean deleteAuthorityAuEmployee(String authorityId,Integer employeeId) { 
		Boolean flag = true;
		try {
			List<AuEmployeeAuthority> auEmployeeAuthorities = this.findAuEmployeeAuthorityListByAuthority(authorityId);
			for (AuEmployeeAuthority auEmployeeAuthority : auEmployeeAuthorities) {
				if(auEmployeeAuthority.getAuEmployee().getId().equals(employeeId)){
					this.baseDao.remove(auEmployeeAuthority);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 通过权限id删除人员权限关系
	 * @author 
	 */
	@Override
	public Boolean deleteAuthorityAuEmployeeByAuthority(String authorityId) {
		Boolean flag = true;
		try {
			//人员权限关系
			List<AuEmployeeAuthority> auEmployeeAuthorities = this.findAuEmployeeAuthorityListByAuthority(authorityId);
			//循环删除
			for (AuEmployeeAuthority auEmployeeAuthority : auEmployeeAuthorities) {
				this.baseDao.remove(auEmployeeAuthority);
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 通过人员和功能查找该人员在该功能下所具有的按钮
	 * @author 
	 */
	@Override
	public List<AuEmployeeAuthority> getButtonByEmployeeAndAuthority(
			String employeeId, String authorityId) {
		String hql = "from AuEmployeeAuthority x inner join fetch x.auAuthority as aa inner join fetch x.auEmployee as ae where ae.id=:employeeId and aa.id=:authorityId";
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringCheck.stringCheck(authorityId)&&StringCheck.stringCheck(employeeId)){
			map.put("employeeId", employeeId);
			map.put("authorityId", authorityId);
		}
		return this.baseDao.find(hql, map);
	}
}