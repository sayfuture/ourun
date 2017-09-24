package com.mxcx.erp.di.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mxcx.ec.base.commons.dao.IBaseDao;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.util.DateUtil;
import com.mxcx.ec.base.commons.util.StringCheck;
import com.mxcx.ec.base.commons.util.ToolUtils;
import com.mxcx.ec.base.commons.util.UuidDitch;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.base.adaptor.LogFunction;
import com.mxcx.erp.base.adaptor.LogModule;
import com.mxcx.erp.base.commons.service.BaseService;
import com.mxcx.erp.di.dao.entity.DiCard;
import com.mxcx.erp.di.dao.entity.DiSendRecode;
import com.mxcx.erp.lo.service.LogManagementService;
import com.mxcx.erp.utils.Constant;
import com.mxcx.erp.we.dao.entity.WeCustomer;
import com.mxcx.erp.we.service.WeCustomerService;

/**
 * DiSendRecodeServiceImpl Mon Jan 16 14:06:33 CST 2017 hmy
 */

@Service
public class DiSendRecodeServiceImpl extends BaseService<DiSendRecode>
		implements DiSendRecodeService {

	@Autowired
	private IBaseDao<DiSendRecode> diSendRecodeDao;
	@Autowired
	private WeCustomerService weCustomerService;
	@Autowired
	private DiCardService diCardService;
	@Autowired
	private LogManagementService logManagementService;

	@Override
	public Boolean addDiSendRecode(DiSendRecode diSendRecode,
			AuEmployee auEmployee) {
		boolean flag = false;
		try {
			diSendRecode.setId(UuidDitch.getId(LogModule.DISENDRECODE
					.getModuleNo()));
			flag = addPo(diSendRecode, auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee,
					ToolUtils.getHostAddress(), flag,
					LogModule.DISENDRECODE.toString(),
					LogFunction.DISENDRECODE_CREATE.toString(),
					diSendRecode.toString());
		}
		return flag;
	}

	@Override
	public DataGrid findDiSendRecodeList(PageParameter pageParameter,AuEmployee auEmployee) {
		String name = (String) pageParameter.getParaMap().get("name");
		String meId=(String) pageParameter.getParaMap().get("meId");
		String starttime= (String) pageParameter.getParaMap().get("start_time");
		String endtime = (String) pageParameter.getParaMap().get("end_time");
//		StringBuffer hql = new StringBuffer(
//				"from DiSendRecode diSendRecode where diSendRecode.state = 1");
		StringBuilder sql=new StringBuilder("SELECT ds.id as id,ds.sharenum as sharenum,ds.openid as openid,ds.cardid as cardid" );
			sql.append(" from di_send_recode ds,di_process dp where  ds.cardid=dp.card_id");
			sql.append(" and ds.openid=dp.wechat_customerid");
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String companyId=auEmployee.getCompanyId();
		if (StringCheck.stringCheck(name)) {
			sql.append(" and ds.name like "+"%" + name + "%");
//			paraMap.put("name", "%" + name + "%");
		}
		if(StringUtils.isNotEmpty(meId)){
			sql.append(" and ds.meid = "+meId);
//			paraMap.put("name", meId);
		}
		if (StringCheck.stringCheck(starttime)) {
			Date start=DateUtil.format(starttime, "yyyy-MM-dd");
			sql.append(" and ds.create_date >= "+start );
//			paraMap.put("start", start);
		}	
		if (StringCheck.stringCheck(endtime)) {
			Date end=DateUtil.format(endtime, "yyyy-MM-dd");
			sql.append(" and ds.create_date <= "+end );
//			paraMap.put("end", end);
		}
		if(StringUtils.isNotEmpty(companyId)&&!companyId.equals(Constant.COMPANYID)){
			sql.append(" and ds.companyid= "+companyId);
//			paraMap.put("companyId",companyId);
		}
		pageParameter.setParaMap(paraMap);
		sql.append(" GROUP BY openid,cardid order by ds.create_date desc");
		DataGrid dataGrid=diSendRecodeDao.findbatisByMysql("find_mybatis_DiSendRecord", sql.toString(), pageParameter);
		for(int i=0;i<dataGrid.getRows().size();i++){
			DiSendRecode diSendRecode=(DiSendRecode) dataGrid.getRows().get(i);
			WeCustomer weCustomer=weCustomerService.findWeCustomerByID(diSendRecode.getOpenid());
			DiCard diCard=diCardService.findDiCardByID(diSendRecode.getCardid());
			diSendRecode.setWeCustomer(weCustomer);
			diSendRecode.setDiCard(diCard);
		}
		return 	dataGrid;
	}

	@Override
	public DiSendRecode findDiSendRecodeByID(String id) {
		return (DiSendRecode) getOne(id, DiSendRecode.class);
	}

	@Override
	public DiSendRecode findDiSendRecode(String openId, String cardId) {
		String hql="from DiSendRecode di where di.state=1 and di.weCustomer.openId='"+openId+"' and di.diCard.id="+cardId;
		List<DiSendRecode> list=diSendRecodeDao.find(hql);
		if(list.size()>0){
			return list.get(0);
		}
		else
		return null;
	}

	@Override
	public Boolean updateDiSendRecode(DiSendRecode diSendRecode) {
		Boolean flag=true;
		try{
			diSendRecodeDao.modify(diSendRecode);
		}catch(Exception e){
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}
}
