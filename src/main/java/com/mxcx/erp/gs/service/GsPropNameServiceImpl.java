package com.mxcx.erp.gs.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.base.adaptor.LogFunction;
import com.mxcx.erp.base.adaptor.LogModule;
import com.mxcx.erp.base.commons.service.BaseService;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.dao.IBaseDao;
import com.mxcx.ec.base.commons.util.ToolUtils;
import com.mxcx.ec.base.commons.util.UuidDitch;
import com.mxcx.erp.gs.dao.entity.GsPropName;
import com.mxcx.erp.lo.service.LogManagementService;

/**
 * 级别业务层接口实现
 * 
 * @author 20140912
 * 
 */
@Service
public class GsPropNameServiceImpl extends BaseService<GsPropName> implements
		GsPropNameService {

	@Autowired
	private IBaseDao<GsPropName> gsPropNameDao;

	/**
	 * 系统日志服务
	 */
	@Autowired
	private LogManagementService logManagementService;

	/**
	 * @see com.mxcx.erp.me.service.GsPropNameService#addGsPropName(GsPropName GsPropName,
	 *      AuEmployee auEmployee)
	 */
	@Override
	public Boolean addGsPropName(GsPropName gsPropName, AuEmployee auEmployee) {
		boolean flag = false;
		try {
			gsPropName.setId(UuidDitch.getId(LogModule.GS_PROP_NAME.getModuleNo()));
			gsPropName.setState(1);
			flag = addPo(gsPropName, auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee,
					ToolUtils.getHostAddress(), flag,
					LogModule.GS_PROP_NAME.toString(),
					LogFunction.GS_PROP_NAME_CREATE.toString(), gsPropName.toString());
		}
		return flag;
	}

	@Override
	public Boolean deleteGsPropName(String id, AuEmployee auEmployee) {
		GsPropName gsPropName = null;
		Boolean flag = true;
		try {
			gsPropName = (GsPropName) this.getOne(id, GsPropName.class);
			removeByState(gsPropName);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee,
					ToolUtils.getHostAddress(), flag,
					LogModule.GS_PROP_NAME.toString(),
					LogFunction.GS_PROP_NAME_DELETE.toString(), gsPropName.toString());
		}
		return flag;
	}

	@Override
	public Boolean modifyGsPropName(GsPropName gsPropName, AuEmployee auEmployee) {
		Boolean flag = true;
		
		GsPropName tmpGsPropName = null;
		try {
			tmpGsPropName = (GsPropName) this.gsPropNameDao.getById(GsPropName.class, gsPropName.getId());
			tmpGsPropName.setPropName(gsPropName.getPropName());
			tmpGsPropName.setSeqNo(gsPropName.getSeqNo());
			tmpGsPropName.setIsColorProp(gsPropName.getIsColorProp());
			tmpGsPropName.setIsSizeProp(gsPropName.getIsSizeProp());
			tmpGsPropName.setIsSaleProp(gsPropName.getIsSaleProp());
			tmpGsPropName.setIsKeyProp(gsPropName.getIsKeyProp());
			tmpGsPropName.setIsFilterProp(gsPropName.getIsFilterProp());
			tmpGsPropName.setIsCustProp(gsPropName.getIsCustProp());
			tmpGsPropName.setIsMust(gsPropName.getIsMust());
			tmpGsPropName.setIsMulti(gsPropName.getIsMulti());
			modify(tmpGsPropName, auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee,
					ToolUtils.getHostAddress(), flag,
					LogModule.GS_PROP_NAME.toString(),
					LogFunction.GS_PROP_NAME_UPDATE.toString(), gsPropName.toString());
		}
		return flag;
	}

	@Override
	public DataGrid findGsPropNameList(PageParameter pageParameter) {
		StringBuffer hql = new StringBuffer("from GsPropName a where a.state = 1");
		
		// 以下添加查询条件
		Map<String, Object> parmMap = new HashMap<String, Object>();
		if(pageParameter.getParaMap() != null
				&& pageParameter.getParaMap().containsKey("propName")
				&& pageParameter.getParaMap().get("propName") != null
				&& !"".equals(pageParameter.getParaMap().get("propName"))) {
			
			hql.append(" and a.propName = :propName");
			parmMap.put("propName", pageParameter.getParaMap().get("propName"));
		}
		
		// 排序
		hql.append(" order by a.createDate desc");
		
		pageParameter.setParaMap(parmMap);
		return gsPropNameDao.findByhql(hql.toString(), pageParameter);
	}

	/**
	 * @see com.mxcx.erp.me.service.GsPropNameService#findGsPropNameByID(String id)
	 */
	@Override
	public GsPropName findGsPropNameByID(String id) {
		return (GsPropName) getOne(id, GsPropName.class);
	}

}
