package com.mxcx.erp.di.service;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.di.dao.entity.DiCard;
import com.mxcx.erp.di.dao.entity.DiProcess;
import com.mxcx.erp.di.dao.entity.DiSendRecode;
import com.mxcx.erp.we.dao.entity.WeCustomer;

   /**
    * DiProcessService 
    * Thu Dec 29 20:53:47 CST 2016 hmy
    */ 


public interface DiProcessService {

	public Boolean addDiProcess(DiProcess diProcess, AuEmployee auEmployee);
	public Boolean deleteDiProcess(String id, AuEmployee auEmployee);
	public Boolean modifyDiProcess(DiProcess diProcess, AuEmployee auEmployee);
	public DataGrid findDiProcessList(PageParameter pageParameter,AuEmployee auEmployee);
	public DiProcess findDiProcessByID(String id);
	public DiProcess findDiProcessByID(String cardId,String openId);
	public Boolean saveDiProcessInfo(AuEmployee auEmployee, DiCard diCard,
			WeCustomer weCustomer);
	public void saveProcessInfo(HttpServletRequest request, ModelAndView view, DiSendRecode diSendRecode,
                                String openId, String cardId, AuEmployee auEmployee, String provId, String cityId, String car_type, String address, String phone);
	
}

