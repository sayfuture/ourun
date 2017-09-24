package com.mxcx.erp.di.service;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.di.dao.entity.DiPermanent;

   /**
    * DiPermanentService 
    * Mon Jun 19 11:19:37 CST 2017 hmy
    */ 


public interface DiPermanentService {

	public Boolean addDiPermanent(DiPermanent diPermanent, AuEmployee auEmployee);
	public Boolean deleteDiPermanent(String id, AuEmployee auEmployee);
	public Boolean modifyDiPermanent(DiPermanent diPermanent, AuEmployee auEmployee);
	public DataGrid findDiPermanentList(PageParameter pageParameter,AuEmployee auEmployee);
	public DiPermanent findDiPermanentByID(String id);
}

