package com.mxcx.erp.lo.service;

import java.util.List;

import com.mxcx.erp.base.commons.service.IBaseService;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.lo.dao.entity.MemberLog;

/**
 * 会员日志业务层
 * @author王森20140917
 *
 */

public interface MemberLogService extends IBaseService<MemberLog>{

	
	/**
	 * 查询所有会员日志
	 */
	public DataGrid findList(PageParameter pageParameter);
	
	/**
	 * 查询会员日志集合
	 */
	public List< MemberLog> findList();
}
