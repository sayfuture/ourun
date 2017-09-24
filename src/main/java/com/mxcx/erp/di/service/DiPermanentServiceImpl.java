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
import com.mxcx.erp.di.dao.entity.DiPermanent;
import com.mxcx.erp.lo.service.LogManagementService;
import com.mxcx.erp.utils.Constant;

/**
 * DiPermanentServiceImpl Mon Jun 19 11:19:37 CST 2017 hmy
 */

@Service
public class DiPermanentServiceImpl extends BaseService<DiPermanent> implements
		DiPermanentService {

	@Autowired
	private IBaseDao<DiPermanent> diPermanentDao;
	@Autowired
	private LogManagementService logManagementService;

	@Override
	public Boolean addDiPermanent(DiPermanent diPermanent, AuEmployee auEmployee) {
		boolean flag = false;
		try {
			diPermanent.setId(UuidDitch.getId(LogModule.DIPERMANENT.getModuleNo()));
			diPermanent.setCompanyId(auEmployee.getCompany().getId());
			flag = addPo(diPermanent, auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee,
					ToolUtils.getHostAddress(), flag,
					LogModule.DIPERMANENT.toString(),
					LogFunction.DIPERMANENT_CREATE.toString(),
					diPermanent.toString());
		}
		return flag;
	}

	@Override
	public Boolean deleteDiPermanent(String id, AuEmployee auEmployee) {
		DiPermanent diPermanent = null;
		Boolean flag = true;
		try {
			diPermanent = (DiPermanent) this.getOne(id, DiPermanent.class);
			removeByState(diPermanent);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee,
					ToolUtils.getHostAddress(), flag,
					LogModule.DIPERMANENT.toString(),
					LogFunction.DIPERMANENT_DELETE.toString(),
					diPermanent.toString());
		}
		return flag;
	}

	@Override
	public Boolean modifyDiPermanent(DiPermanent diPermanent,
			AuEmployee auEmployee) {
		Boolean flag = true;
		DiPermanent diPermanentTemp = new DiPermanent();
		try {
			diPermanentTemp = (DiPermanent) this.diPermanentDao.getById(
					DiPermanent.class, diPermanent.getId());
			diPermanentTemp.setMeMember(diPermanent.getMeMember());
			diPermanentTemp.setDiCard(diPermanent.getDiCard());
			this.modify(diPermanentTemp, auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee,
					ToolUtils.getHostAddress(), flag,
					LogModule.DIPERMANENT.toString(),
					LogFunction.DIPERMANENT_UPDATE.toString(),
					diPermanent.toString());
		}
		return flag;
	}

	@Override
	public DataGrid findDiPermanentList(PageParameter pageParameter,AuEmployee auEmployee) {
		String name = (String) pageParameter.getParaMap().get("name");
		StringBuffer hql = new StringBuffer(
				"from DiPermanent diPermanent where diPermanent.state = 1");
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String companyId=auEmployee.getCompanyId();
		if (StringUtils.isNotEmpty(name)) {
			hql.append(" and diPermanent.name like :name ");
			paraMap.put("name", "%" + name + "%");
		}
		if(StringUtils.isNotEmpty(companyId)&&!companyId.equals(Constant.COMPANYID)){
			hql.append(" and diPermanent.companyId= :companyId");
			paraMap.put("companyId",companyId);
		}
		hql.append(" order by diPermanent.createDate desc");
		pageParameter.setParaMap(paraMap);
		return diPermanentDao.findByhql(hql.toString(), pageParameter);
	}

	@Override
	public DiPermanent findDiPermanentByID(String id) {
		return (DiPermanent) getOne(id, DiPermanent.class);
	}
}
