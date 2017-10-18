package com.mxcx.erp.we.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.mxcx.ec.base.commons.dao.IBaseDao;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.util.DateUtil;
import com.mxcx.ec.base.commons.util.StringCheck;
import com.mxcx.ec.base.commons.util.ToolUtils;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.au.service.AuEmployeeService;
import com.mxcx.erp.base.adaptor.LogFunction;
import com.mxcx.erp.base.adaptor.LogModule;
import com.mxcx.erp.base.commons.service.BaseService;
import com.mxcx.erp.lo.service.LogManagementService;
import com.mxcx.erp.utils.Constant;
import com.mxcx.erp.we.dao.entity.WeCustomer;
import com.mxcx.erp.wechat.service.WeChatService;

/**
 * WeCustomerServiceImpl Thu Dec 29 20:55:34 CST 2016 hmy
 */

@Service
public class WeCustomerServiceImpl extends BaseService<WeCustomer> implements
		WeCustomerService {

	@Autowired
	private IBaseDao<WeCustomer> weCustomerDao;
	@Autowired
	private LogManagementService logManagementService;
	@Autowired
	private WeChatService weChatService;
	@Autowired
	private AuEmployeeService auEmployeeService;
	
	@Override
	public Boolean deleteWeCustomer(String id, AuEmployee auEmployee) {
		WeCustomer weCustomer = null;
		Boolean flag = true;
		try {
			weCustomer = (WeCustomer) this.getOne(id, WeCustomer.class);
			removeByState(weCustomer);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee,
					ToolUtils.getHostAddress(), flag,
					LogModule.WECUSTOMER.toString(),
					LogFunction.WECUSTOMER_DELETE.toString(),
					weCustomer.toString());
		}
		return flag;
	}
	@Override
	public void modifyWeCustomer(WeCustomer weCustomer){
		this.modify(weCustomer);
	}
	@Override
	public Boolean modifyWeCustomer(WeCustomer weCustomer, AuEmployee auEmployee) {
		Boolean flag = true;
		WeCustomer weCustomerTemp = new WeCustomer();
		try {
			weCustomerTemp = (WeCustomer) this.weCustomerDao.getById(
					WeCustomer.class, weCustomer.getOpenId());
			weCustomerTemp.setCustomer_name(weCustomer.getCustomer_name());
			weCustomerTemp.setPhone(weCustomer.getPhone());
			weCustomerTemp.setIs_follow(weCustomer.getIs_follow());
			weCustomerTemp.setCompanyIds(weCustomer.getCompanyIds());
			this.modify(weCustomerTemp, auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee,
					ToolUtils.getHostAddress(), flag,
					LogModule.WECUSTOMER.toString(),
					LogFunction.WECUSTOMER_UPDATE.toString(),
					weCustomer.toString());
		}
		return flag;
	}
	@Override
	public Boolean modifyWeCustomerInfo(WeCustomer weCustomer, AuEmployee auEmployee) {
		Boolean flag = true;
		WeCustomer weCustomerTemp = new WeCustomer();
		try {
			weCustomerTemp = (WeCustomer) this.weCustomerDao.getById(
					WeCustomer.class, weCustomer.getOpenId());
			weCustomerTemp.setCustomer_name(weCustomer.getCustomer_name());
			weCustomerTemp.setPhone(weCustomer.getPhone());
			weCustomerTemp.setCar_type(weCustomer.getCar_type());
			weCustomerTemp.setKilometers(weCustomer.getKilometers());
			weCustomerTemp.setNext_maintain_content(weCustomer.getNext_maintain_content());
			weCustomerTemp.setNext_maintain_time(weCustomer.getNext_maintain_time());
			this.modify(weCustomerTemp, auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee,
					ToolUtils.getHostAddress(), flag,
					LogModule.WECUSTOMER.toString(),
					LogFunction.WECUSTOMER_UPDATE.toString(),
					weCustomer.toString());
		}
		return flag;
	}
	@Override
	public DataGrid findWeCustomerList(PageParameter pageParameter,AuEmployee auEmployee) {
		String customer_name = (String) pageParameter.getParaMap().get("customer_name");
		String starttime= (String) pageParameter.getParaMap().get("start_time");
		String endtime = (String) pageParameter.getParaMap().get("end_time");
		StringBuffer hql = new StringBuffer(
				"from WeCustomer weCustomer where weCustomer.state = 1");
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String companyId=auEmployee.getCompanyId();
		if (StringCheck.stringCheck(customer_name)) {
			hql.append(" and weCustomer.customer_name like :customer_name ");
			paraMap.put("customer_name", "%" + customer_name + "%");
		}		
		if (StringCheck.stringCheck(starttime)) {
			Date start=DateUtil.format(starttime, "yyyy-MM-dd");
			hql.append(" and weCustomer.next_maintain_time >= :start ");
			paraMap.put("start", start);
		}	
		if (StringCheck.stringCheck(endtime)) {
			Date end=DateUtil.format(endtime, "yyyy-MM-dd");
			hql.append(" and weCustomer.next_maintain_time <= :end ");
			paraMap.put("end", end);
		}	
		if(StringUtils.isNotEmpty(companyId)&&!companyId.equals(Constant.COMPANYID)){
			hql.append(" and weCustomer.companyIds like :companyId");
			paraMap.put("companyId","%"+companyId+"|%");
		}
		pageParameter.setParaMap(paraMap);
		hql.append(" order by weCustomer.createDate desc");
		return weCustomerDao.findByhql(hql.toString(), pageParameter);
	}

	@Override
	public WeCustomer findWeCustomerByID(String id) {
		return (WeCustomer) getOne(id, WeCustomer.class);
	}
	
	@Override
	public Boolean addWeCustomer(WeCustomer weCustomer,AuEmployee auEmployee) {
		Boolean flag = true;
	try{
		this.addPo(weCustomer,auEmployee);
	} catch (Exception e) {
		e.printStackTrace();
		flag = false;
	} finally {
		this.logManagementService.addLogManagement(auEmployee, ToolUtils.getHostAddress(), flag, LogModule.WECUSTOMER.toString(), LogFunction.WECUSTOMER_CREATE.toString(),weCustomer.toString());
	}
	return flag;
	}
	
	  @Scheduled(cron="0 0/30 0,1  * * ? ")
	  @Override
	  public void saveWeChatUser(){
//		List<AuEmployee> auEmployees=(List<AuEmployee>) auEmployeeService.getAll(AuEmployee.class);
//		for(AuEmployee auEmployee:auEmployees){
//		List<WeCustomer> list=weCustomerDao.find("from WeCustomer w where w.state=1 and w.is_follow=1 and w.companyIds like '%"+auEmployee.getAuDept().getId()+"|%'");
		  List<WeCustomer> list=weCustomerDao.find("from WeCustomer w where w.state=1 and w.is_follow=1 ");
			  for(WeCustomer weCustomer:list){
				 Map<String,Object> map=new HashMap<String,Object>();
				 try{
					 map=weChatService.getUserinfo(weCustomer.getOpenId());
				 }catch(Exception e){
				try {
					map=weChatService.getUserinfo(weCustomer.getOpenId());
					  } catch (Exception e1) {
						  weCustomer.setIs_follow(0);
							Log.error(e1);
						}
					  }
				 Integer t=(Integer) map.get("subscribe");
				 if(t!=null&&t.equals(1)){
						weCustomer.setWechat_name(map.get("nickname").toString());
				 }else{
					 weCustomer.setIs_follow(0);	 
				 }
				 this.modify(weCustomer);
			 }
//		}
	  }

}
