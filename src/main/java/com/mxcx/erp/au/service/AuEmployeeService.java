package com.mxcx.erp.au.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mxcx.erp.au.dao.entity.AuAuthority;
import com.mxcx.erp.au.dao.entity.AuAuthorityPosition;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.base.commons.service.IBaseService;
import com.mxcx.ec.base.commons.util.DataGrid;

/**
 * 人员业务层接口
 * 
 * @author  20140625
 * 
 */
public interface AuEmployeeService extends IBaseService<AuEmployee> {
	/**
	 * 添加人员
	 * 
	 * @param AuEmployee人员对象
	 * @return
	 * @throws Exception 
	 */
	public Boolean addAuEmployee(AuEmployee auEmployee,	AuEmployee auEmployeeTemp,HttpServletRequest request) throws Exception;

	/**
	 * 删除人员
	 * 
	 * @param id人员Id
	 * @return
	 */
	public Boolean deleteAuEmployee(String id, AuEmployee employee);

	/**
	 * 修改人员
	 * 
	 * @param AuEmployee
	 *            人员对象
	 */
	public Boolean modifyAuEmployee(AuEmployee auEmployee,String positionId,AuEmployee auEmployeeTemp, HttpServletRequest request);

	/**
	 * 查询所有人员
	 * 
	 * @param pageParameter
	 *            本页数据信息
	 */
	public DataGrid findList(PageParameter pageParameter, AuEmployee employee);

	/**
	 * 用户登录名注册时验证
	 * 
	 * @param pageParameter
	 * @return人员分页
	 */
	public DataGrid findLoginNameList(PageParameter pageParameter);

	/**
	 * 用户搜索
	 * 
	 * @param pageParameter
	 * @param auEmployee
	 * @return人员分页
	 */
	public DataGrid findLoginName(PageParameter pageParameter,	AuEmployee auEmployee);

	/**
	 * 初始化密码
	 * 
	 * @param id人员Id
	 * @return
	 */
	public Boolean initPassword(String id, AuEmployee auEmployee);

	/**
	 * 修改密码
	 * 
	 * @param password1新密码
	 * @param password2确认密码
	 * @return
	 */
	public Boolean updatePassword(String id, String password1,String password2, AuEmployee auEmployee);

	/**
	 * 登录
	 * 
	 * @param name用户名
	 * @param password密码
	 * @return人员
	 */
	public AuEmployee login(String name, String password);

	/**
	 * 根据用户查找菜单权限map
	 * 
	 * @param auEmployee人员
	 * @return
	 */
	public Map<String, List<AuAuthority>> auMenuAuthority(AuEmployee auEmployee,List<AuAuthorityPosition> a);

	/**
	 * 登录验证
	 * 
	 * @param name用户名
	 * @param password密码
	 * @return人员
	 */
	public int loginCheck(String name, String password);

	/**
	 * 手机端登录
	 * 
	 * @param name登录名
	 * @param password密码
	 * @return人员对象
	 */
	public AuEmployee loginToM(String name, String password);

	/**
	 * 根据工作组查询人员
	 * 
	 * @param audeptId工作组ID
	 * @return人员集合
	 */
	public List<AuEmployee> findAuEmployeeListByDept(String audeptId);

	/**
	 * 根据角色查询人员
	 * 
	 * @param positionId
	 * @return人员集合
	 */
	public List<AuEmployee> findAuEmployeeListByPosition(String positionId);

	/**
	 * 根据id查找人员
	 * 
	 * @param id人员id
	 * @return人员对象
	 */
	public AuEmployee findAuEmployeeById(String id);

	/**
     * 根据真实名查询
     */
	public AuEmployee findAuEmployeeByRealName(String realName);

	/**
	 * 当前用户用户管理的权限1：个人；2：组；3：全体；
	 * 
	 * @param auEmployee
	 * @returntype
	 */
	public Integer checkType(AuEmployee auEmployee);

	/**
	 * 搜索用户
	 * 
	 * @param pageParameter
	 * @param audeptId组Id
	 * @return人员分页
	 */
	DataGrid findAuEmployeeListByDeptForES(PageParameter pageParameter,String audeptId);

	/**
	 * 传入dept字符串 1，2，3，4 得到对应的员工列表
	 * 
	 * @param audeptIds组Id
	 * @return人员集合
	 */
	List<AuEmployee> findAuEmployeeListByDepts(String audeptIds);

	public DataGrid findEmpCompanyList(PageParameter pageParameter,	AuEmployee auEmployee);
	
	/**
	 * 通过微信号查询店铺信息
	 * @param wxname
	 * @return
	 */
	public AuEmployee getAuEmployeeBywxname(String wxname);

	public AuEmployee findAuEmployeeBywxInfo(String appid, String secret);
	
	
}