package com.mxcx.erp.base.commons.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.base.commons.controller.BasePo;
import com.mxcx.ec.base.commons.dao.entity.TransparentPo;
import com.mxcx.erp.me.dao.entity.MeMember;


/**
 * @see(功能介绍):service模板
 * @author ：王森
 * @version(版本号): 1.0
 * @date(创建日期): 2014-9-2
 * @param <T>
 */
public interface IBaseService1<T> {
	/**
	 * 添加
	 * @param basePo			类实例
	 * 
	 * @return 					执行结果是否成功
	 */
	public boolean addPo(TransparentPo basePo);

	/**
	 * 添加 - 自动注入 人员以及状态信息
	 * @param basePo			类实例
	 * 
	 * @return 					执行结果是否成功
	 */
	public boolean addPo(BasePo basePo,AuEmployee employee);
	
	/**
	 * 添加 - 自动注入 人员以及状态信息
	 * @param basePo			类实例
	 * 
	 * @return 					执行结果是否成功
	 */
	public boolean addPo(BasePo basePo, MeMember member);
	
	/**
	 * 添加 - 自动注入 操作员以及状态信息
	 * @param basePo			类实例
	 * 
	 * @return 					执行结果是否成功
	 */
	public boolean addPo(BasePo basePo, String opratorId) throws Exception;
	
	/**
	 * 更新
	 * @param basePo			类实例			
	 * 
	 * @return					执行结果是否成功			
	 */
	public boolean modify(TransparentPo basePo);
	
	/**
	 * 更新 - 自动注入 人员以及状态信息
	 * @param basePo			类实例			
	 * 
	 * @return					执行结果是否成功			
	 */
	public boolean modify(BasePo basePo,AuEmployee employee);
	
	/**
	 * 更新 - 自动注入 人员以及状态信息
	 * @param basePo			类实例
	 * 
	 * @return 					执行结果是否成功
	 */
	public boolean modify(BasePo basePo, MeMember member);
	
	/**
	 * 更新 - 自动注入 人员以及状态信息
	 * @param basePo			类实例
	 * 
	 * @return 					执行结果是否成功
	 */
	public boolean modify(BasePo basePo, String opratorId);
	
	/**
	 * 删除
	 * @param basePo			类实例
	 * 
	 * @return					执行结果是否成功
	 */
	public boolean remove(TransparentPo basePo);
	/**
	 * 删除 - 变更删除状态
	 * @param basePo			类实例
	 * 
	 * @return					执行结果是否成功
	 */
	public boolean removeByState(BasePo basePo);
	/**
	 * 指定id查询
	 * @param id				序列化id
	 * @param class1			表对应类型
	 * 
	 * @return					结果对象
	 */
	public Object getOne(Serializable id , Class<?> class1);

	/**
	 * 根据HQL语句查询一个对象
	 * @param hql				HQL语句
	 * @param params			参数Map
	 * 
	 * @return					结果对象
	 */
	public Object getOne(String hql, Map<String, Object> params);
	
	/**
	 * 集合查询
	 * @param class1			表对应类型
	 * 
	 * @return					结果列表
	 */
	public List<?> getAll(Class<?> class1);
	
	/**
	 * 删除指定id对象
	 * @param id				序列化id
	 * @param class1			表对应类型
	 * 
	 * @return					执行是否成功					
	 */
	public boolean removeOne(Serializable id , Class<?> class1);
	
	/**
	 * 批量删除
	 * @param ids				id列表串
	 * @param class1			表对应类型
	 * 
	 * @return					执行是否成功
	 */
	public boolean removeBinch(String ids, Class<?> class1);
}
