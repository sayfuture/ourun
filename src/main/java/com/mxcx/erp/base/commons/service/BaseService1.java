package com.mxcx.erp.base.commons.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.base.commons.controller.BasePo;
import com.mxcx.ec.base.commons.dao.IBaseDao1;
import com.mxcx.ec.base.commons.dao.entity.TransparentPo;
import com.mxcx.erp.me.dao.entity.MeMember;


/**
 * @see(功能介绍):BaseDao层
 * @version(版本号): 1.0
 * @date(创建日期): 2014-9-3
 * @author 王森
 */
@Service
public class BaseService1<T> implements IBaseService1<T>{

	@Autowired
	protected IBaseDao1<T> baseDao1; //BaseDao注入
	
	
	/**
	 *  增加
	 *  
	 * @param BasePo basePo
	 * @return boolean true false
	 */
	@Override
	public boolean addPo(TransparentPo basePo) {
		
		try {
			
			baseDao1.addObj(basePo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 *  修改
	 *  
	 * @param BasePo basePo
	 * @return boolean true false
	 */
	@Override
	public boolean modify(TransparentPo basePo) {
		try {
			baseDao1.modify(basePo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	
	/**
	 *  删除
	 *  
	 * @param BasePo basePo
	 * @return boolean true false
	 */
	@Override
	public boolean remove(TransparentPo basePo) {
		try {
			baseDao1.removeObj(basePo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	
	/**
	 *  查询一个
	 * @param Serializable id , Class<?> class1
	 * @return Object 
	 *  
	 */
	@Override
	public Object getOne(Serializable id , Class<?> class1) {
		return baseDao1.getById(class1,id);
	}
	
	/**
	 *  查询一个
	 * @param String hql , Map<String, Object> params
	 * @return Object 
	 *  
	 */
	@Override
	public Object getOne(String hql, Map<String, Object> params) {
		return baseDao1.get(hql, params);
	}
	
	/**
	 *  查询所有
	 * @param Class<?> classes
	 * @return List<?>
	 * 
	 */
	@Override
	public List<?> getAll(Class<?> classes) {
		String hql = "from "+ classes.getSimpleName();
		
		return baseDao1.find(hql);
	}

	public void delete(T o){
		baseDao1.remove(o);
	}
	
	
	/**
	 * 按ID删除实体类
	 * @param Serializable id , Class<?> class1
	 * @return boolean true false
	 *  
	 */
	@Override
	public boolean removeOne(Serializable id , Class<?> class1) {
		try {
			baseDao1.removeObj(getOne(id, class1));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}


	/**     
	 * 批量删除
	 * @param ids
	 * @param class1
	 * @return
	 * 
	 * @author Administrator 2014-3-10
	 * @version 1.0
	 */
	@Override
	public boolean removeBinch(String ids, Class<?> class1) {
		String[] idList = ids.split(",");
		try {
			for (int i = 0; i < idList.length; i++) {
				int id = Integer.parseInt(idList[i].trim());
				baseDao1.removeObj(getOne(id, class1));
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**     
	 * 新增
	 * @param basePo
	 * @param employee
	 * @return boolean
	 * 
	 * @author 王森 2014-9-3
	 * @version 1.0
	 */
	@Override
	public boolean addPo(BasePo basePo, AuEmployee employee) {
		try {
			
			basePo.setState(1);
			basePo.setCreateDate(new Date());
			basePo.setUpdateDate(new Date());
			basePo.setCreateUser(employee.getId().toString());
			basePo.setUpdateUser(employee.getId().toString());
			baseDao1.addObj(basePo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**     
	 * 新增
	 * @param basePo
	 * @param employee
	 * @return boolean
	 * 
	 * @author 王森 2014-9-3
	 * @version 1.0
	 */
	@Override
	public boolean addPo(BasePo basePo, MeMember member) {
		try {
			
			basePo.setState(1);
			basePo.setCreateDate(new Date());
			basePo.setUpdateDate(new Date());
			basePo.setCreateUser(member.getId());
			basePo.setUpdateUser(member.getId());
			
			baseDao1.addObj(basePo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**     
	 * 新增
	 * @param basePo
	 * @param opratorId
	 * @return boolean
	 * 
	 * @author   2014-10-3
	 * @version 1.0
	 */
	@Override
	public boolean addPo(BasePo basePo, String opratorId)throws Exception {
		Boolean flag = false;
		try {
			
			basePo.setState(1);
			basePo.setCreateDate(new Date());
			basePo.setUpdateDate(new Date());
			basePo.setCreateUser(opratorId);
			basePo.setUpdateUser(opratorId);
			Object obj = baseDao1.addObj(basePo);
			if(obj != null){
				flag = true;
			}else{
				throw new NullPointerException("Fail to persist this BasePo --->" + basePo.getClass());
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return flag;
	}
	
	/**
	 * 修改
	 *  
	 * @param BasePo basePo
	 * @return boolean true false
	 */
	@Override
	public boolean modify(BasePo basePo, AuEmployee employee) {
		try {
			basePo.setUpdateDate(new Date());
			basePo.setUpdateUser(employee.getId().toString());
			baseDao1.modify(basePo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	

	/**
	 * 修改
	 *  
	 * @param BasePo basePo
	 * @return boolean true false
	 */
	@Override
	public boolean modify(BasePo basePo, MeMember member) {
		try {
			basePo.setUpdateDate(new Date());
			basePo.setUpdateUser(member.getId());
			baseDao1.modify(basePo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 修改
	 *  
	 * @param BasePo basePo
	 * @return boolean true false
	 */
	@Override
	public boolean modify(BasePo basePo, String opratorId) {
		try {
			basePo.setUpdateDate(new Date());
			basePo.setUpdateUser(opratorId);
			baseDao1.modify(basePo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 删除
	 *  
	 * @param TransparentPo basePo
	 * @return boolean true false
	 */
	@Override
	public boolean removeByState(BasePo basePo) {
		try {
			basePo.setState(0);
			baseDao1.modify(basePo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
