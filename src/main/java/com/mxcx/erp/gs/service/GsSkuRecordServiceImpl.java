package com.mxcx.erp.gs.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mxcx.erp.base.commons.service.BaseService;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.util.StringCheck;
import com.mxcx.erp.gs.dao.entity.GsSkuAddRecord;
import com.mxcx.erp.lo.service.LogManagementService;

/**
 * 
 */
@Service
@Transactional
public class GsSkuRecordServiceImpl extends BaseService<GsSkuAddRecord> implements GsSkuRecordService {
	@Autowired
	private LogManagementService logManagementService; // 日志记录


	@Override
	public DataGrid findGsSkusList(PageParameter pageParameter) {
		StringBuffer hql = new StringBuffer("from GsSkuAddRecord gs where gs.state = 1 ");
		hql.append(" order by gs.createDate desc");
		return this.baseDao.findByhql(hql.toString(), pageParameter);
	}

	@Override
	public DataGrid findGsName(String gsName, PageParameter pageParameter) {
		StringBuffer hql = new StringBuffer("from GsSkuAddRecord gs where gs.state = 1  ");
		if(StringCheck.stringCheck(gsName))
		{
			hql.append(" and gs.goodsName like '%"+gsName+"%'");
		}
		hql.append(" order by gs.createDate desc");
		return this.baseDao.findByhql(hql.toString(), pageParameter);
	}
}