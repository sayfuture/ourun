package com.mxcx.erp.di.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.mxcx.ec.base.commons.dao.IBaseDao;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.util.PropertiesReader;
import com.mxcx.ec.base.commons.util.StringCheck;
import com.mxcx.ec.base.commons.util.ToolUtils;
import com.mxcx.ec.base.commons.util.UuidDitch;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.base.adaptor.LogFunction;
import com.mxcx.erp.base.adaptor.LogModule;
import com.mxcx.erp.base.commons.service.BaseService;
import com.mxcx.erp.di.dao.entity.DiCard;
import com.mxcx.erp.di.dao.entity.DiProcess;
import com.mxcx.erp.di.dao.entity.DiSendRecode;
import com.mxcx.erp.lo.service.LogManagementService;
import com.mxcx.erp.utils.Constant;
import com.mxcx.erp.we.dao.entity.WeCustomer;
import com.mxcx.erp.we.service.WeCustomerService;
import com.mxcx.erp.wechat.service.WeChatService;

/**
 * DiProcessServiceImpl Thu Dec 29 20:53:47 CST 2016 hmy
 */

@Service
public class DiProcessServiceImpl extends BaseService<DiProcess> implements
		DiProcessService {
	private final static Logger log = Logger.getLogger(DiProcessServiceImpl.class);
	@Autowired
	private IBaseDao<DiProcess> diProcessDao;
	@Autowired
	private LogManagementService logManagementService;
	@Autowired
	private DiCardService diCardService;
	@Autowired
	private DiProcessRecordService diProcessRecordService;
	@Autowired
	private DiSendRecodeService diSendRecodeService;
	@Autowired
	private WeCustomerService weCustomerService;
	@Autowired
	private WeChatService weChatService;
	@Override
	public Boolean addDiProcess(DiProcess diProcess, AuEmployee auEmployee) {
		boolean flag = true;
		try {
			diProcess.setId(UuidDitch.getId(LogModule.DIPROCESS.getModuleNo()));
			baseDao.addObj(diProcess);
//			flag = addPo(diProcess, auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee,
					ToolUtils.getHostAddress(), flag,
					LogModule.DIPROCESS.toString(),
					LogFunction.DIPROCESS_CREATE.toString(),
					diProcess.toString());
		}
		return flag;
	}
	@Override
	public Boolean saveDiProcessInfo( AuEmployee auEmployee,DiCard diCard,WeCustomer weCustomer){
		Boolean flag=true;
		try{
			DiProcess diProcess=new DiProcess();
			diProcess.setCompanyId(auEmployee.getCompany().getId());
			diProcess.setDiCard(diCard);
			diProcess.setWeCustomer(weCustomer);
			Date date=new Date();
			diProcess.setGettime(date);
//			 Calendar   calendar   =   new   GregorianCalendar(); 
//		     calendar.setTime(date); 
//		     calendar.add(calendar.DATE,diCard.getVaildtime());
//		     date=calendar.getTime();   
			diProcess.setEnd_time(diCard.getVaildtime());
			diProcess.setStatus(0);
			diProcess.setCard_num(diCard.getUse_num());
			diProcess.setShare_card_num(diCard.getShare_num());
			flag=this.addDiProcess(diProcess, auEmployee);
		}catch(Exception e){
			flag=false;
			e.printStackTrace();
		}
		return flag;
		}
	@Override
	public Boolean deleteDiProcess(String id, AuEmployee auEmployee) {
		DiProcess diProcess = null;
		Boolean flag = true;
		try {
			diProcess = (DiProcess) this.getOne(id, DiProcess.class);
			baseDao.modify(diProcess);
//			removeByState(diProcess);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee,
					ToolUtils.getHostAddress(), flag,
					LogModule.DIPROCESS.toString(),
					LogFunction.DIPROCESS_DELETE.toString(),
					diProcess.toString());
		}
		return flag;
	}

	@Override
	public Boolean modifyDiProcess(DiProcess diProcess, AuEmployee auEmployee) {
		Boolean flag = true;
		DiProcess diProcessTemp = new DiProcess();
		try {
//			diProcessTemp = (DiProcess) this.diProcessDao.getById(
//					DiProcess.class, diProcess.getId());
			// diProcessTemp.setName(diProcess.getName());
			baseDao.modify(diProcess);
//			this.modify(diProcessTemp, auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
//			logManagementService.addLogManagement(auEmployee,
//					ToolUtils.getHostAddress(), flag,
//					LogModule.DIPROCESS.toString(),
//					LogFunction.DIPROCESS_UPDATE.toString(),
//					diProcess.toString());
		}
		return flag;
	}

	@Override
	public DataGrid findDiProcessList(PageParameter pageParameter,AuEmployee auEmployee) {
		String name = (String) pageParameter.getParaMap().get("name");
		StringBuffer hql = new StringBuffer(
				"from DiProcess diProcess where diProcess.status=0");
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String companyId=auEmployee.getCompanyId();
		if (StringCheck.stringCheck(name)) {
			hql.append(" and diProcess.weCustomer.customer_name like :name ");
			paraMap.put("name", "%" + name + "%");
		}
		if(StringUtils.isNotEmpty(companyId)&&!companyId.equals(Constant.COMPANYID)){
			hql.append(" and diCard.companyId= :companyId");
			paraMap.put("companyId",companyId);
		}
		pageParameter.setParaMap(paraMap);
//		hql.append(" order by diProcess.createDate desc");
		return diProcessDao.findByhql(hql.toString(), pageParameter);
	}

	@Override
	public DiProcess findDiProcessByID(String id) {
		return (DiProcess) getOne(id, DiProcess.class);
	}

	@Override
	public DiProcess findDiProcessByID(String cardId, String openId) {
		String hql="from DiProcess di where di.weCustomer.openId='"+openId+"' and di.diCard.id="+cardId;
		List<DiProcess> list=diProcessDao.find(hql);
		if(list.size()>0)
			return list.get(0);
		else
			return null;
	}
	@Override
	public void saveProcessInfo(HttpServletRequest request, ModelAndView view, DiSendRecode diSendRecode, String openId, String cardId, AuEmployee auEmployee, String provId, String cityId, String car_type, String phone, String areaId){
		Date date=new Date();
		if(diSendRecode!=null){
			//同一个人
			if(diSendRecode.getWeCustomer().getOpenId().equals(openId)||diSendRecode.getWeCustomer().getOpenId().equals("abcdef")){
				DiProcess diProcess=this.findDiProcessByID(cardId, openId);
				if(diProcess!=null){
					if(diProcess.getStatus().equals(Constant.USED_SIGN)){
						view.addObject("messageInfo", "您已使用过这张优惠券，敬请关注下次活动");}
					else{
					view.addObject("messageInfo", "已领取，请到店铺："+auEmployee.getAddress()+"使用！");}
				}else{
					DiCard diCard=diSendRecode.getDiCard();
					Integer used=diCard.getUsed_num();
					if(diCard.getUsed_num()<diCard.getTotal_num())
					{
						if(date.getTime()<diCard.getVaildtime().getTime()){
							diCard.setUsed_num(diCard.getUsed_num()+1);
							diCardService.modifyDiCardUsedNum(diCard, auEmployee);
							diSendRecode.setNew_weuser(diSendRecode.getNew_weuser()+1);
							this.saveDiProcessInfo(auEmployee, diSendRecode.getDiCard(), diSendRecode.getWeCustomer());
							diSendRecodeService.updateDiSendRecode(diSendRecode);
							view.addObject("messageInfo","恭喜您，领取成功！请到店铺："+auEmployee.getAddress()+"使用！");
						}else{
							view.addObject("messageInfo", "优惠券已过期，敬请关注下次活动！");
						}
					}else{
						view.addObject("messageInfo", "本次活动优惠券已领取完了，请关注下次活动！");
					}
					WeCustomer weCustomer=weCustomerService.findWeCustomerByID(openId);
					if(StringUtils.isEmpty(provId)||StringUtils.isEmpty(cityId)||StringUtils.isEmpty(areaId)){
					}else{
					weCustomer.setCar_type(car_type);
					weCustomer.setCity(cityId);
					weCustomer.setPhone(phone);
					weCustomer.setProvince(provId);
					weCustomer.setArea(areaId);}
					if(StringUtils.isNotEmpty(weCustomer.getCompanyIds())&&weCustomer.getCompanyIds().contains(auEmployee.getCompany().getId())){}else {
						weCustomer.setCompanyIds(weCustomer.getCompanyIds() + auEmployee.getCompany().getId() + "|");
					}
					weCustomerService.modifyWeCustomer(weCustomer);
				}
			}
			//分享出去的情况
			else{
				view.setViewName("/ftl/news/sharePage");
				String sence_id="41"+diSendRecode.getMeMember().getUser_id()+cardId;
					String filePath=weChatService.generQRcode(request,sence_id,auEmployee);
					view.addObject("path", PropertiesReader.getInstance().getConfigItem("rootPath")+"/upload/QRcode/"+filePath);
					DiCard diCard=diSendRecode.getDiCard();
					if(diCard.getUsed_num()<diCard.getTotal_num())
					{
						if(date.getTime()<diCard.getVaildtime().getTime()){
							WeCustomer weCustomer=weCustomerService.findWeCustomerByID(openId);
							if(weCustomer==null){
								diSendRecode.setNew_weuser(diSendRecode.getNew_weuser()+1);
							}else{
								if(StringUtils.isEmpty(provId)||StringUtils.isEmpty(cityId)||StringUtils.isEmpty(areaId)){
								}else{
									weCustomer.setCar_type(car_type);
									weCustomer.setCity(cityId);
									weCustomer.setPhone(phone);
									weCustomer.setProvince(provId);
									weCustomer.setArea(areaId);}
								if(StringUtils.isNotEmpty(weCustomer.getCompanyIds())&&weCustomer.getCompanyIds().contains(auEmployee.getCompany().getId())){}else {
									weCustomer.setCompanyIds(weCustomer.getCompanyIds() + auEmployee.getCompany().getId() + "|");
								}
								weCustomerService.modifyWeCustomer(weCustomer);
							}
							diSendRecode.setSharenum(diSendRecode.getSharenum()+1);
							diSendRecodeService.updateDiSendRecode(diSendRecode);
					}
				}
			}
		}else
		{	view.addObject("messageInfo", "领取失败，请重新领取！");}
	}
}
