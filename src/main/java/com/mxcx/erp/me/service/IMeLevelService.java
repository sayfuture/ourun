package com.mxcx.erp.me.service;

import java.util.List;

import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.base.commons.service.IBaseService;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.me.dao.entity.MeLevel;

/**
 * 会员级别业务层接口
 * 
 * @author  20140912
 * 
 */
public interface IMeLevelService {
	
	/**
	 * 添加级别
	 * @param meLevel
	 *            会员级别对象
	 * @param auEmployee
	 *            登录员工对象
	 * @return
	 */
	public Boolean addMemberLevel(MeLevel meLevel, AuEmployee auEmployee);
	
	/**
	 * 删除级别
	 * @param id	级别Id
	 * @param auEmployee
	 *            登录员工对象
	 * @return
	 */
	public Boolean deleteMemberLevel(String id, AuEmployee auEmployee);

	/**
	 * 修改级别
	 * 
	 * @param meLevel
	 *            会员级别对象
	 * @param auEmployee
	 *            登录员工对象
	 */
	public Boolean modifyMemberLevel(MeLevel meLevel, AuEmployee auEmployee);

	/**
	 * 分页查询会员级别
	 * 
	 * @param pageParameter
	 *            本页数据信息
	 */
	public DataGrid findMemberLevelList(PageParameter pageParameter);
	
	/**
	 * 查询所有会员级别
	 * @return
	 */
	public List<MeLevel> findMemberLevelList();
	
	/**
	 * 根据ID查询指定级别
	 * @param id	级别Id
	 * @return
	 */
	public MeLevel findMemberLevelByID(String id);
	
	/**
	 * 根据级别名称查询指定级别
	 * @param id	级别Id
	 * @return
	 */
	public DataGrid findMemberLevelByName(PageParameter pageParameter);
	
	/**
	 * 根据传入的积分转成相应的中文级别
	 * @return
	 */
	public String scoreToLevelName(List<MeLevel> levelList,Long score);
	
	public MeLevel findMemberLevelbyScore(Integer score);
	
}