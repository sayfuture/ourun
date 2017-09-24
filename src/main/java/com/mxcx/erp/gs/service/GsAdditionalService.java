package com.mxcx.erp.gs.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.base.commons.service.IBaseService;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.gs.dao.entity.GsSku;

/**
 */
public interface GsAdditionalService extends IBaseService<GsSku> {


	public DataGrid findAdditionalList(PageParameter pageParameter);
	
	public Boolean gsAdd(HttpServletRequest request);
	
	public DataGrid findGsName(String gsName,PageParameter pageParameter);
}