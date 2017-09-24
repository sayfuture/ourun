package com.mxcx.erp.gs.service;


import javax.servlet.http.HttpServletRequest;

import com.mxcx.erp.base.commons.service.IBaseService;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.gs.dao.entity.GsSku;

/**
 */
public interface GsSkuShowService extends IBaseService<GsSku> {


	public DataGrid findSkuList(PageParameter pageParameter);
	
	public Boolean gsAdd(HttpServletRequest request);
	
	public DataGrid searchForGsSKU(PageParameter pageParameter);

}