package com.mxcx.erp.di.service;
import java.util.List;

import com.mxcx.erp.au.dao.entity.AuEmployee;

/**
    * DiCardService 
    * Thu Dec 29 20:51:23 CST 2016 hmy
    */ 


public interface DiGroupService {

	public Boolean groupSend(AuEmployee auEmployee, String desc, String clickDesc, List<String> openids, String cardId) throws Exception;
}

