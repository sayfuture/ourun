package com.mxcx.erp.di.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

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
import com.mxcx.erp.lo.service.LogManagementService;
import com.mxcx.erp.utils.Constant;

/**
 * DiCardServiceImpl Thu Dec 29 20:51:23 CST 2016 hmy
 */

@Service
public class DiCardServiceImpl extends BaseService<DiCard> implements
		DiCardService {

	@Autowired
	private IBaseDao<DiCard> diCardDao;
	@Autowired
	private LogManagementService logManagementService;

	@Override
	public Boolean addDiCard(DiCard diCard, AuEmployee auEmployee) {
		boolean flag = false;
		try {
			Date d=new Date();
			String s=d.getTime()+"";
			synchronized (DiCardServiceImpl.class) {
				try {
	                 //sleep()方法导致了程序暂停执行指定的时间，让出cpu该其他线程，
	                 //但是他的监控状态依然保持者，当指定的时间到了又会自动恢复运行状态。
	                 //在调用sleep()方法的过程中，线程不会释放对象锁。
	                 Thread.sleep(5l);
	             } catch (Exception e) {
	                 e.printStackTrace();
	             }
			}
//			diCard.setId(s.substring(4, s.length()));
			diCard.setCompanyId(auEmployee.getCompanyId());
			diCard.setUsed_num(0);
			flag = addPo(diCard, auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee,
					ToolUtils.getHostAddress(), flag,
					LogModule.DICARD.toString(),
					LogFunction.DICARD_CREATE.toString(), diCard.toString());
		}
		return flag;
	}

	@Override
	public Boolean deleteDiCard(String id, AuEmployee auEmployee) {
		DiCard diCard = null;
		Boolean flag = true;
		try {
			diCard = (DiCard) this.getOne(Integer.valueOf(id), DiCard.class);
			removeByState(diCard);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee,
					ToolUtils.getHostAddress(), flag,
					LogModule.DICARD.toString(),
					LogFunction.DICARD_DELETE.toString(), diCard.toString());
		}
		return flag;
	}

	@Override
	public Boolean modifyDiCard(DiCard diCard, AuEmployee auEmployee) {
		Boolean flag = true;
		DiCard diCardTemp = new DiCard();
		try {
			diCardTemp = (DiCard) this.diCardDao.getById(DiCard.class,diCard.getId());
			diCardTemp.setCard_name(diCard.getCard_name());
			diCardTemp.setCard_worth(diCard.getCard_worth());
			diCardTemp.setCoContent(diCard.getCoContent());
			diCardTemp.setShare_num(diCard.getShare_num());
			diCardTemp.setUse_explain(diCard.getUse_explain());
			diCardTemp.setVaildtime(diCard.getVaildtime());
			diCardTemp.setUse_num(diCard.getUse_num());
			diCardTemp.setTotal_num(diCard.getTotal_num());
			this.modify(diCardTemp, auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee,
					ToolUtils.getHostAddress(), flag,
					LogModule.DICARD.toString(),
					LogFunction.DICARD_UPDATE.toString(), diCard.toString());
		}
		return flag;
	}
	@Override
	public Boolean modifyDiCardUsedNum(DiCard diCard, AuEmployee auEmployee) {
		Boolean flag = true;
		DiCard diCardTemp = new DiCard();
		try {
			diCardTemp = (DiCard) this.diCardDao.getById(DiCard.class,diCard.getId());
			diCardTemp.setUsed_num(diCard.getUsed_num());
			this.modify(diCardTemp, auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee,
					ToolUtils.getHostAddress(), flag,
					LogModule.DICARD.toString(),
					LogFunction.DICARD_UPDATE.toString(), diCard.toString());
		}
		return flag;
	}
	@Override
	public DataGrid findDiCardList(PageParameter pageParameter,AuEmployee auEmployee) {
		String name = (String) pageParameter.getParaMap().get("name");
		StringBuffer hql = new StringBuffer(
				"from DiCard diCard where diCard.state = 1");
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String companyId=auEmployee.getCompanyId();

		if (StringCheck.stringCheck(name)) {
			hql.append(" and diCard.name like :name ");
			paraMap.put("name", "%" + name + "%");
		}
		if(StringUtils.isNotEmpty(companyId)&&!companyId.equals(Constant.COMPANYID)){
			hql.append(" and diCard.companyId= :companyId");
			paraMap.put("companyId",companyId);
		}
		pageParameter.setParaMap(paraMap);
		hql.append(" order by diCard.createDate desc");
		return diCardDao.findByhql(hql.toString(), pageParameter);
	}

	@Override
	public DiCard findDiCardByID(Integer id) {
		return (DiCard) getOne(id, DiCard.class);
	}
	
}
