package com.mxcx.erp.di.service;
import java.util.List;

import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.di.dao.entity.DiCard;

   /**
    * DiCardService 
    * Thu Dec 29 20:51:23 CST 2016 hmy
    */ 


public interface DiGroupService {

	public Boolean groupSend(AuEmployee auEmployee,String desc,String clickDesc, List<String> openids, String cardId) throws Exception;
}

