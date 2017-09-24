package com.mxcx.erp.co.service;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.co.dao.entity.CoTemplate;

   /**
    * CoTemplateService 
    * Fri Feb 17 09:56:39 CST 2017 hmy
    */ 


public interface CoTemplateService {

	public Boolean saveRefreshCoTemplate(AuEmployee auEmployee);
	public DataGrid findCoTemplateList(PageParameter pageParameter,AuEmployee auEmployee);
	public CoTemplate findCoTemplateByID(String id);
}

