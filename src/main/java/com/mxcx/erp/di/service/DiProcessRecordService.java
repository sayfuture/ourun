package com.mxcx.erp.di.service;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.di.dao.entity.DiProcess;
import com.mxcx.erp.di.dao.entity.DiProcessRecord;

   /**
    * DiProcessService 
    * Thu Dec 29 20:53:47 CST 2016 hmy
    */ 


public interface DiProcessRecordService {

	public Boolean addDiProcessRecord(DiProcessRecord diProcess, AuEmployee auEmployee);
	public Boolean deleteDiProcessRecord(String id, AuEmployee auEmployee);
	public Boolean modifyDiProcessRecord(DiProcessRecord diProcess, AuEmployee auEmployee);
	public DataGrid findDiProcessRecordList(PageParameter pageParameter,AuEmployee auEmployee);
	public DiProcessRecord findDiProcessRecordByID(String id);
}

