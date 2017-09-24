package com.mxcx.erp.gs.service;

import java.util.List;

import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.gs.dao.entity.GsPropValue;

/**
 * 会员级别业务层接口
 * 
 * @author  20140912
 * 
 */
public interface GsPropValueService {
	
	public Boolean addGsPropValue(GsPropValue gsPropValue, AuEmployee auEmployee);
	
	public Boolean deleteGsPropValue(String id, AuEmployee auEmployee);

	public Boolean modifyGsPropValue(GsPropValue gsPropValue, AuEmployee auEmployee);

	public DataGrid findGsPropValueList(PageParameter pageParameter);
	
	public GsPropValue findGsPropValueByID(String id);
	
	public List<GsPropValue> findGsPropValueByPropidList(String propId);
	
//	public void findsss();
	
}