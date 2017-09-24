package com.mxcx.erp.gs.service;

import java.util.List;

import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.base.commons.service.IBaseService;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.gs.dao.entity.GsKuProp;

/**
 * 会员级别业务层接口
 * 
 * @author  20140912
 * 
 */
public interface GsKuPropService  extends IBaseService<GsKuProp> {
	

	public DataGrid findGsKuPropList(PageParameter pageParameter);
	
	public List <GsKuProp> findGsKuPropListView(String  goodsid);
	
	public boolean bandGsKuProp(String goodsid , String [] propArray ,AuEmployee auEmployee);

	public boolean removeGsPropName(String id, AuEmployee auEmployee);
	
	
	
}