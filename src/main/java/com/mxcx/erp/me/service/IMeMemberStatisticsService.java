package com.mxcx.erp.me.service;

import java.util.Date;
import java.util.List;

import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.base.commons.service.IBaseService;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.me.dao.entity.MeMember;

/**
 * 会员统计Service
 * 
 * @author 孙瑞 2014/10/22
 */
public interface IMeMemberStatisticsService {
	
	/**
	 * 获取会员统计数据
	 */
	public abstract List<MeMember> getMembers(Date startDate, Date endDate);
	
}