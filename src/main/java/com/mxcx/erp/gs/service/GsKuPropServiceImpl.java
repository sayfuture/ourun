package com.mxcx.erp.gs.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.base.adaptor.LogFunction;
import com.mxcx.erp.base.adaptor.LogModule;
import com.mxcx.erp.base.commons.service.BaseService;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.dao.IBaseDao;
import com.mxcx.ec.base.commons.util.StringCheck;
import com.mxcx.ec.base.commons.util.ToolUtils;
import com.mxcx.ec.base.commons.util.UuidDitch;
import com.mxcx.erp.gs.dao.entity.GsKuProp;
import com.mxcx.erp.gs.dao.entity.GsPropValue;
import com.mxcx.erp.lo.service.LogManagementService;

@Service
@Transactional
public class GsKuPropServiceImpl extends BaseService<GsKuProp> implements GsKuPropService {

	@Autowired
	private IBaseDao<GsKuProp>  gsKuPropDao;
	
	@Autowired
	private GsSkuService gsSkuService;
	
	@Autowired
	private LogManagementService logManagementService;
	
	@Autowired
	private GsPropValueService gsPropValueService;

	@Override
	public DataGrid findGsKuPropList(PageParameter pageParameter) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" select a.id,b.product_name as goodsName,c.prop_name as propName ,a.create_date as createDate, d.loginname as createUser from GS_SKU_PROP a ,gs_goods b,gs_prop_name c,au_employee d where a.gid=b.id and a.propid=c.id and a.create_user=d.id and a.state=1 ");
		// 以下添加查询条件
		String gid = pageParameter.getParaMap().get("gid")+"";
		if(StringCheck.stringCheck(gid)) {
			sqlBuffer.append("  and a.gid= '"+gid+"'");
		}
		return gsKuPropDao.findbatisByMysql("find_mybatis_GsKuProp", sqlBuffer.toString(), pageParameter);
		
	}

	private List getGskuList(List newlist ,List oldlist){
		List returnlist = new ArrayList();
		for (int i = 0; i < newlist.size(); i++) {
			if(!oldlist.contains(newlist.get(i))){
				returnlist.add(newlist.get(i));
			}
		}
		return returnlist;
		
	}
	public boolean bandGsKuProp(String goodsid, String[] propArray, AuEmployee auEmployee) {
		boolean flag = true;
		try {
			String hql = "select propid from GsKuProp t where t.gid=:goodsid and t.state=1 ";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("goodsid", goodsid);
			List oldlist =this.baseDao.find(hql, map);
			List newlist =  Arrays.asList(propArray);
			List returnlist = getGskuList(newlist,oldlist);
			for (Object  propid : returnlist.toArray()) {
//				System.out.println(propid);
				GsKuProp gk = new GsKuProp();
				try {
					gk.setId(UuidDitch.getId(LogModule.GS_GSKUPROP.getModuleNo()));
					gk.setPropid(propid+"");
					gk.setGid(goodsid);
					this.addPo(gk, auEmployee);
				} catch (Exception e) {
					flag = false;
				} finally {
					logManagementService.addLogManagement(auEmployee,ToolUtils.getHostAddress(), flag,	LogModule.GS_GSKUPROP.toString(),LogFunction.GS_GSKUPROP_BAND.toString(), gk.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
	
		return flag;
	}

	@Override
	public boolean removeGsPropName(String id, AuEmployee auEmployee) {
		GsKuProp gsKuProp = null;
		Boolean flag = true;
		try {
			gsKuProp = (GsKuProp) this.getOne(id, GsKuProp.class);
			removeByState(gsKuProp);
			
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee,ToolUtils.getHostAddress(), flag,	LogModule.GS_GSKUPROP.toString(),LogFunction.GS_GSKUPROP_DELETE.toString(), gsKuProp.toString());
		}
		return flag;
		
	}


	@Override
	public List<GsKuProp> findGsKuPropListView(String goodsid) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" select a.id,a.propid,b.product_name as goodsName,c.prop_name as propName ,a.create_date as createDate, d.loginname as createUser" +
				" from GS_SKU_PROP a ,gs_goods b,gs_prop_name c,au_employee d where a.gid=b.id and a.propid=c.id and a.create_user=d.id and a.state=1 ");
		// 以下添加查询条件
		if(StringCheck.stringCheck(goodsid)) {
			sqlBuffer.append("  and a.gid= '"+goodsid+"'");
		}
		 List<GsKuProp> proplist = gsKuPropDao.findbatisBysql("find_mybatis_GsKuPropAll", sqlBuffer.toString());
		 for (int i = 0; i < proplist.size(); i++) {
			 GsKuProp gp= proplist.get(i);
			 List<GsPropValue> propValuelist =  gsPropValueService.findGsPropValueByPropidList(gp.getPropid());
			 gp.setGsPropValueList(propValuelist);
		}
		 return proplist;
	}
	
	
}
