package com.mxcx.erp.me.service;

import java.util.List;

import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.base.commons.service.IBaseService;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.me.dao.entity.MeMember;

/**
 * 会员业务层接口
 * 
 * @author  20140909
 * 
 */
public interface IMeMemberService extends IBaseService<MeMember> {
	
	/**
	 * 删除会员
	 * @param id	会员Id
	 * @return
	 */
	public Boolean deleteMember(String id, AuEmployee auEmployee);

	/**
	 * 修改会员
	 * 
	 * @param member
	 *            会员对象
	 * @param auEmployee
	 *            登录员工对象
	 */
	public Boolean modifyMember(MeMember member, AuEmployee auEmployee);

	
	/**
	 * 分页查询会员
	 * 
	 * @param pageParameter
	 *            本页数据信息
	 */
	public DataGrid findMemberList(PageParameter pageParameter);
	
	/**
	 * 根据ID查询指定会员
	 * @param id	会员Id
	 * @return
	 */
	public MeMember findMemerByID(String id);
	/**
	 * 根据会员真实名查询
	 * 方便日志查询
	 * @param realName
	 * @return
	 */
	public MeMember findMemberByRealName(String realName,String nickName);
	
	/**
	 * 根据组合条件查询指定会员
	 * @param pageParameter
	 * 				本页数据信息
	 * @return
	 */
	public DataGrid findMemberByConditionGroup(PageParameter pageParameter,AuEmployee auEmployee);
	
	
	/**
	 * 根据组合条件查询指定会员
	 * @param loginName
	 * @param realName
	 * @param email
	 * @param activity
	 * @param availability
	 * @param cellphone
	 * @param level
	 * @return
	 */
	public List<MeMember> findMemberByConditionGroup(String loginName,
			String realName, String email,String activity,String availability
			,String cellphone,String level);
	
	/**
	 * 初始化密码
	 * 
	 * @param id 会员Id
	 * @param auEmployee
	 *            登录员工对象
	 * @return
	 */
	public Boolean initPassword(String id, AuEmployee auEmployee);
	
	/**
	 * 初始化积分
	 * 
	 * @param id 会员Id
	 * @param auEmployee
	 *            登录员工对象
	 * @return
	 */
	public Boolean initScore(String id, AuEmployee auEmployee);
	
	/**
	 * 启用/冻结会员
	 * @param id	会员Id
	 * @param auEmployee
	 *            登录员工对象
	 * @return
	 */
	public Boolean validateMemer(String id, AuEmployee auEmployee);
	
	/**
	 * 会员信息是否完善
	 * @param member
	 * @return
	 */
	public Boolean isFullInfo(MeMember member);
	
	/**
	 * 员工绑定集客券
	 * @param member
	 * @return
	 */
	public Boolean bindCard(MeMember member);

	/**
	 * 新增店员
	 * @param member
	 * @param loginUser
	 * @return
	 */
	public Boolean addMember(MeMember member, AuEmployee loginUser);
	
	public MeMember findMemerByUserId(String user_id);
	
}