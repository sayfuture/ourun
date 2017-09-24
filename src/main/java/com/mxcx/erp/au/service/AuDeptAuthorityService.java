//package com.mxcx.erp.au.service;
//
//import java.util.List;
//
//import com.mxcx.erp.au.dao.entity.AuDeptAuthority;
//import com.mxcx.erp.au.dao.entity.AuEmployeeDept;
//import com.mxcx.erp.base.commons.service.IBaseService;
//
///**
// * 公司权限关系业务层接口
// * 
// * @author  20140626
// * 
// */
//public interface AuDeptAuthorityService extends IBaseService<AuDeptAuthority> {
//	
//	/**
//	 * 添加公司权限关系
//	 * @param DeptId 公司id
//	 * @param authorityId 功能id
//	 * @param type 对应的按钮（id）组
//	 * @return
//	 * @author 
//	 */
//	public boolean addAuDeptAuthority(String DeptId,String authorityId, String[] type);
//
//	/**
//	 * 通过公司id删除公司权限关系
//	 * @param DeptId 公司id
//	 * @author 
//	 */
//	public Boolean deleteAuDeptAuthority(String DeptId);
//	
//	/**
//	 * 删除指定公司权限关系
//	 * @param authorityId 权限id
//	 * @param DeptId 公司id
//	 * @author 
//	 */
//	public Boolean deleteAuthorityAuDept(String authorityId,Integer DeptId);
//	
//	/**
//	 * 通过权限id删除公司权限关系
//	 * @param authorityId
//	 * @author 
//	 */
//	public Boolean deleteAuthorityAuDeptByAuthority(String authorityId);
//	
//	/**
//	 * 根据公司查询公司权限关系
//	 * @param auDeptId 公司id
//	 * @return
//	 * @author 
//	 */
//	public List<AuDeptAuthority> findAuDeptAuthorityListByAuDept(String auDeptId);
//	
//	/**
//	 * 根据权限查询公司权限关系
//	 * @param auAuthorityId 权限id
//	 * @return
//	 * @author 
//	 */
//	public List<AuDeptAuthority> findAuDeptAuthorityListByAuthority(String auAuthorityId);
//
//	/**
//	 * 通过公司和功能查找该公司在该功能下所具有的按钮
//	 * @param DeptId 公司id
//	 * @param authorityId 权限id
//	 * @return
//	 * @author 
//	 */
//	public List<AuDeptAuthority> getButtonByDeptAndAuthority(
//			String DeptId, String authorityId);
//}
