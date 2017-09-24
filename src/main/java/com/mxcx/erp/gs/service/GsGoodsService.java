package com.mxcx.erp.gs.service;

import javax.servlet.http.HttpServletRequest;

import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.base.commons.service.IBaseService;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.gs.dao.entity.GsGoods;

/**
 * 
 * @author  20140912
 * 
 */
public interface GsGoodsService  extends IBaseService<GsGoods> {
	
	public Boolean addGsGoods(GsGoods gsGoods, AuEmployee auEmployee);
	
	public Boolean deleteGsGoods(String id, AuEmployee auEmployee, HttpServletRequest reques);

	public Boolean modifyGsGoods(GsGoods gsGoods, AuEmployee auEmployee);

	public DataGrid findGsGoodsList(PageParameter pageParameter);
	
	public GsGoods findGsGoodsByID(String id);

	public DataGrid findPublishList(PageParameter pageParameter);

	public Boolean stopPublish(String id,AuEmployee employee);

	public Boolean startPublish(String id,AuEmployee employee);

	public Boolean returnPublish(String id,AuEmployee employee);

	public Boolean audiGsgoods(String id,AuEmployee employee);

	
	
	
}