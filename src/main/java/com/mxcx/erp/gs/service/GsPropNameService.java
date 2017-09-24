package com.mxcx.erp.gs.service;

import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.gs.dao.entity.GsPropName;

/**
 * 会员级别业务层接口
 * 
 * @author  20140912
 * 
 */
public interface GsPropNameService {
	
	public Boolean addGsPropName(GsPropName gsPropName, AuEmployee auEmployee);
	
	public Boolean deleteGsPropName(String id, AuEmployee auEmployee);

	public Boolean modifyGsPropName(GsPropName gsPropName, AuEmployee auEmployee);

	public DataGrid findGsPropNameList(PageParameter pageParameter);
	
	public GsPropName findGsPropNameByID(String id);
	
	
	
}