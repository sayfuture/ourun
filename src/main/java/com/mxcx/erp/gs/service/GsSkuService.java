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
public interface GsSkuService extends IBaseService<GsSku> {

	public Boolean addGsSkus(String gid,List<GsSku> gsSkus,AuEmployee employee);
	
	public Boolean deleteGsSkus(String gid,AuEmployee employee);
	
	public Boolean updateGsSku(GsSku gsSku,AuEmployee employee);

	public DataGrid findGsSkusList(PageParameter pageParameter);
	
	public int  saveNotGsSku(String gid,AuEmployee employee,GsSku gsSku);
	
	public Boolean editGsSkuStatus(HttpServletRequest request);
	
}