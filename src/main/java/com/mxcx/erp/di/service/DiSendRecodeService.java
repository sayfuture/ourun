package com.mxcx.erp.di.service;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.di.dao.entity.DiSendRecode;

   /**
    * DiSendRecodeService 
    * Mon Jan 16 14:06:33 CST 2017 hmy
    */ 


public interface DiSendRecodeService {
	public Boolean addDiSendRecode(DiSendRecode diSendRecode, AuEmployee auEmployee);
	public DataGrid findDiSendRecodeList(PageParameter pageParameter,AuEmployee auEmployee);
	public DiSendRecode findDiSendRecodeByID(String id);
	public DiSendRecode findDiSendRecode(String openId,String cardId);
	public Boolean updateDiSendRecode(DiSendRecode diSendRecode);
}

