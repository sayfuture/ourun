package com.mxcx.erp.lo.service;

import java.util.List;

import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.base.commons.service.IBaseService;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.lo.dao.entity.LogManagement;

/**
 * 日志管理业务层
 * @author王森20140912
 *
 */
public interface LogManagementService extends IBaseService<LogManagement>{

	/**
	 * 添加系统日志
	 * @param 人们信息
	 * @param ip地址
	 * @param  flag操作结果
	 * @param  logModule 模块名
	 * @param  logFunctions 功能名
	 * @return
	 */
	public Boolean addLogManagement(AuEmployee auEmployee, String ip,
			Boolean flag, String logModule, String logFunctions,String data);
	
	
	/**
	 * 查询所有系统日志
	 */
	public DataGrid findList(PageParameter pageParameter);
	
	/**
	 * 查询系统日志集合
	 */
	public List<LogManagement> findList();
	
}
