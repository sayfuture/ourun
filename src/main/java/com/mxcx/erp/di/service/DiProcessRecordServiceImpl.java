package com.mxcx.erp.di.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mxcx.ec.base.commons.dao.IBaseDao;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.util.StringCheck;
import com.mxcx.ec.base.commons.util.ToolUtils;
import com.mxcx.ec.base.commons.util.UuidDitch;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.base.adaptor.LogFunction;
import com.mxcx.erp.base.adaptor.LogModule;
import com.mxcx.erp.base.commons.service.BaseService;
import com.mxcx.erp.di.dao.entity.DiProcess;
import com.mxcx.erp.di.dao.entity.DiProcessRecord;
import com.mxcx.erp.lo.service.LogManagementService;
import com.mxcx.erp.utils.Constant;

/**
 * DiProcessServiceImpl Thu Dec 29 20:53:47 CST 2016 hmy
 */

@Service
public class DiProcessRecordServiceImpl extends BaseService<DiProcessRecord> implements
		DiProcessRecordService {

	@Autowired
	private IBaseDao<DiProcessRecord> diProcessRecordDao;
	@Autowired
	private LogManagementService logManagementService;

	@Override
	public Boolean addDiProcessRecord(DiProcessRecord diProcessRecord, AuEmployee auEmployee) {
		boolean flag = true;
		try {
//			diProcessRecord.setId(UuidDitch.getId(LogModule.DIPROCESSRECORD.getModuleNo()));
			baseDao.addObj(diProcessRecord);
//			flag = addPo(diProcess, auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee,
					ToolUtils.getHostAddress(), flag,
					LogModule.DIPROCESSRECORD.toString(),
					LogFunction.DIPROCESSRECORD_CREATE.toString(),
					diProcessRecord.toString());
		}
		return flag;
	}

	@Override
	public Boolean deleteDiProcessRecord(String id, AuEmployee auEmployee) {
		DiProcessRecord diProcess = null;
		Boolean flag = true;
		try {
			diProcess = (DiProcessRecord) this.getOne(id, DiProcess.class);
			baseDao.modify(diProcess);
//			removeByState(diProcess);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee,
					ToolUtils.getHostAddress(), flag,
					LogModule.DIPROCESSRECORD.toString(),
					LogFunction.DIPROCESSRECORD_DELETE.toString(),
					diProcess.toString());
		}
		return flag;
	}

	@Override
	public Boolean modifyDiProcessRecord(DiProcessRecord diProcess, AuEmployee auEmployee) {
		Boolean flag = true;
		DiProcessRecord diProcessTemp = new DiProcessRecord();
		try {
//			diProcessTemp = (DiProcessRecord) this.diProcessRecordDao.getById(
//					DiProcessRecord.class, diProcess.getId());
			// diProcessTemp.setName(diProcess.getName());
			baseDao.modify(diProcess);
//			this.modify(diProcessTemp, auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
//			logManagementService.addLogManagement(auEmployee,
//					ToolUtils.getHostAddress(), flag,
//					LogModule.DIPROCESSRECORD.toString(),
//					LogFunction.DIPROCESSRECORD_UPDATE.toString(),
//					diProcess.toString());
		}
		return flag;
	}

	@Override
	public DataGrid findDiProcessRecordList(PageParameter pageParameter,AuEmployee auEmployee) {
		String name = (String) pageParameter.getParaMap().get("name");
		StringBuffer hql = new StringBuffer(
				"from DiProcessRecord diProcessRecord where diProcessRecord.status in(1,2) ");
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String companyId=auEmployee.getCompanyId();
		if (StringCheck.stringCheck(name)) {
			hql.append(" and diProcessRecord.weCustomer.customer_name like :name ");
			paraMap.put("name", "%" + name + "%");
		}
		if(StringUtils.isNotEmpty(companyId)&&!companyId.equals(Constant.COMPANYID)){
			hql.append(" and diProcessRecord.companyId= :companyId");
			paraMap.put("companyId",companyId);
		}
		pageParameter.setParaMap(paraMap);
//		hql.append(" order by diProcess.createDate desc");
		return diProcessRecordDao.findByhql(hql.toString(), pageParameter);
	}

	@Override
	public DiProcessRecord findDiProcessRecordByID(String id) {
		return (DiProcessRecord) getOne(id, DiProcessRecord.class);
	}
}
