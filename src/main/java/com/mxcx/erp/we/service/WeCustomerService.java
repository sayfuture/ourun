package com.mxcx.erp.we.service;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.we.dao.entity.WeCustomer;

   /**
    * WeCustomerService 
    * Thu Dec 29 20:55:34 CST 2016 hmy
    */ 


public interface WeCustomerService {

	public Boolean deleteWeCustomer(String id, AuEmployee auEmployee);

     public   void modifyWeCustomer(WeCustomer weCustomer);

       public Boolean modifyWeCustomer(WeCustomer weCustomer, AuEmployee auEmployee);
	public DataGrid findWeCustomerList(PageParameter pageParameter,AuEmployee auEmployee);
	public WeCustomer findWeCustomerByID(String id);
	public void saveWeChatUser();
	public Boolean addWeCustomer(WeCustomer weCustomer,AuEmployee auEmployee);
	Boolean modifyWeCustomerInfo(WeCustomer weCustomer, AuEmployee auEmployee);
}

