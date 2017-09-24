//package com.mxcx.erp.au.service;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.mxcx.erp.au.dao.entity.AuAuthority;
//import com.mxcx.erp.au.dao.entity.AuButton;
//import com.mxcx.erp.au.dao.entity.AuDept;
//import com.mxcx.erp.au.dao.entity.AuDeptAuthority;
//import com.mxcx.erp.base.adaptor.LogModule;
//import com.mxcx.ec.base.commons.dao.IBaseDao;
//import com.mxcx.erp.base.commons.service.BaseService;
//import com.mxcx.ec.base.commons.util.StringCheck;
//import com.mxcx.ec.base.commons.util.UuidDitch;
//
///**
// * 公司权限关系业务层接口实现
// * 
// * @author  20140626
// * 
// */
//@Service
//public class AuDeptAuthorityServiceImpl extends
//		BaseService<AuDeptAuthority> implements AuDeptAuthorityService {
//	@Autowired
//	private IBaseDao<AuDeptAuthority> baseDao;  //公司权限dao
//	@Autowired
//	private IBaseDao<AuButton> buttonDao;	// 按钮dao
//	@Autowired
//	private AuDeptService auDeptService; //公司服务层
//	@Autowired
//	private AuAuthorityService auAuthorityService; //权限业务层
//	
//	/**
//	 * 添加公司权限关系
//	 * 
//	 * @author 
//	 */
//	@Override
//	public boolean addAuDeptAuthority(String DeptId, String authorityId, String[] type) {
//		Boolean flag = true;
//		try {
//			AuDept auDept = (AuDept) this.auDeptService.getOne(DeptId, AuDept.class);
//			AuAuthority auAuthority = (AuAuthority) this.auAuthorityService.getOne(authorityId, AuAuthority.class);
//			AuDeptAuthority ay = null;
//			AuButton button ;
//			//如果有按钮对象要绑定
//			//循环按钮对象
//			if(null!=type&&type.length>0){
//				for(String btnId : type){
//					ay = new AuDeptAuthority();
//					//该id对应的按钮对象
//					button = buttonDao.get(AuButton.class, btnId);
//					//设置id
//					ay.setId(UuidDitch.getId(LogModule.AUDEPTAUTHORITY.getModuleNo()));
//					//设置权限
//					ay.setAuAuthority(auAuthority);
//					//设置公司
//					ay.setAuDept(auDept);
//					//设置按钮
//					ay.setAuButton(button);
//					this.baseDao.add(ay); 
//				}
//			}else{
//				ay = new AuDeptAuthority();
//				//设置id
//				ay.setId(UuidDitch.getId(LogModule.AUDEPTAUTHORITY.getModuleNo()));
//				//设置权限
//				ay.setAuAuthority(auAuthority);
//				//设置公司
//				ay.setAuDept(auDept);
//				this.baseDao.add(ay);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			flag = false;
//		}
//		return flag;
//	}
//	
//	/**
//	 * 根据公司查询公司权限关系
//	 * 
//	 * @author 
//	 */
//	@Override
//	public List<AuDeptAuthority> findAuDeptAuthorityListByAuDept(
//			String auDeptId) {
//		String hql = "from AuDeptAuthority x inner join fetch x.auDept as ae where ae.id=:id";
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("id", auDeptId);
//		return this.baseDao.find(hql, map);
//	}
//	
//	/**
//	 * 根据权限查询公司权限关系
//	 * 
//	 * @author 
//	 */
//	@Override
//	public List<AuDeptAuthority> findAuDeptAuthorityListByAuthority(
//			String auAuthorityId) {
//		String hql = "from AuDeptAuthority x inner join fetch x.auAuthority as ae where ae.id=:id";
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("id", auAuthorityId);
//		return this.baseDao.find(hql, map);
//	}
//	
//	/**
//	 * 通过公司id删除公司权限关系
//	 * 
//	 * @author 
//	 */
//	@Override
//	public Boolean deleteAuDeptAuthority(String DeptId) {
//		Boolean flag = true;
//		try {
//			//改员工下的权限关系
//			List<AuDeptAuthority> auDeptAuthorities = this.findAuDeptAuthorityListByAuDept(DeptId);
//			//循环删除
//			for (AuDeptAuthority auDeptAuthority : auDeptAuthorities) {
//				this.baseDao.remove(auDeptAuthority);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			flag = false;
//		}
//		return flag;
//	}
//	
//	/**
//	 * 删除指定公司权限关系
//	 * @author 
//	 */
//	@Override
//	public Boolean deleteAuthorityAuDept(String authorityId,Integer DeptId) { 
//		Boolean flag = true;
//		try {
//			List<AuDeptAuthority> auDeptAuthorities = this.findAuDeptAuthorityListByAuthority(authorityId);
//			for (AuDeptAuthority auDeptAuthority : auDeptAuthorities) {
//				if(auDeptAuthority.getAuDept().getId().equals(DeptId)){
//					this.baseDao.remove(auDeptAuthority);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			flag = false;
//		}
//		return flag;
//	}
//	
//	/**
//	 * 通过权限id删除公司权限关系
//	 * @author 
//	 */
//	@Override
//	public Boolean deleteAuthorityAuDeptByAuthority(String authorityId) {
//		Boolean flag = true;
//		try {
//			//公司权限关系
//			List<AuDeptAuthority> auDeptAuthorities = this.findAuDeptAuthorityListByAuthority(authorityId);
//			//循环删除
//			for (AuDeptAuthority auDeptAuthority : auDeptAuthorities) {
//				this.baseDao.remove(auDeptAuthority);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			flag = false;
//		}
//		return flag;
//	}
//	
//	/**
//	 * 通过公司和功能查找该公司在该功能下所具有的按钮
//	 * @author 
//	 */
//	@Override
//	public List<AuDeptAuthority> getButtonByDeptAndAuthority(
//			String DeptId, String authorityId) {
//		String hql = "from AuDeptAuthority x inner join fetch x.auAuthority as aa inner join fetch x.auDept as ae where ae.id=:DeptId and aa.id=:authorityId";
//		Map<String, Object> map = new HashMap<String, Object>();
//		if(StringCheck.stringCheck(authorityId)&&StringCheck.stringCheck(DeptId)){
//			map.put("DeptId", DeptId);
//			map.put("authorityId", authorityId);
//		}
//		return this.baseDao.find(hql, map);
//	}
//}