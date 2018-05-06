package com.mxcx.erp.di.service;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.di.dao.entity.DiCard;

import javax.servlet.http.HttpServletRequest;

/**
    * DiCardService 
    * Thu Dec 29 20:51:23 CST 2016 hmy
    */ 


public interface DiCardService {

	public Boolean addDiCard(DiCard diCard, AuEmployee auEmployee, HttpServletRequest request);
	public Boolean deleteDiCard(String id, AuEmployee auEmployee);
	public Boolean modifyDiCard(DiCard diCard, AuEmployee auEmployee, HttpServletRequest request);
	public DiCard findDiCardByID(Integer id);
	public DataGrid findDiCardList(PageParameter pageParameter, AuEmployee auEmployee);
	Boolean modifyDiCardUsedNum(DiCard diCard, AuEmployee auEmployee);
}

