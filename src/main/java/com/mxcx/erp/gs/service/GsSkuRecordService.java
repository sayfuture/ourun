package com.mxcx.erp.gs.service;
import com.mxcx.erp.base.commons.service.IBaseService;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.gs.dao.entity.GsSkuAddRecord;

/**
 */
public interface GsSkuRecordService extends IBaseService<GsSkuAddRecord> {

	public DataGrid findGsSkusList(PageParameter pageParameter);
	
	public DataGrid findGsName(String gsName, PageParameter pageParameter);
	
}