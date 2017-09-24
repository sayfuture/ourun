package com.mxcx.erp.lo.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.base.adaptor.LogModule;
import com.mxcx.erp.base.commons.service.BaseService;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.dao.IBaseDao;
import com.mxcx.ec.base.commons.util.DateUtil;
import com.mxcx.ec.base.commons.util.UuidDitch;
import com.mxcx.erp.lo.dao.entity.LogManagement;

/**
 * 系统日志管理业务层接口实现
 * 
 * @author Administrator
 * 
 */
@Service
public class LogManagementServiceImpl extends BaseService<LogManagement>
		implements LogManagementService {

	@Autowired
	private IBaseDao<LogManagement> baseDao;

	/**
	 * 添加系统日志
	 */
	@Override
	public Boolean addLogManagement(AuEmployee auEmployee, String ip,
			Boolean result, String logModule, String logFunctions,String data) {
		LogManagement logManagement = new LogManagement();
		Boolean flag = true;
		try {
			logManagement.setId(UuidDitch.getId(LogModule.LOGMANAGEMENT.getModuleNo())); // id
			logManagement.setAuEmployee(auEmployee); // 操作人信息
			logManagement.setFlag(result); // 操作成功与否
			logManagement.setIp(ip);
			logManagement.setLogModule(logModule);
			logManagement.setLogFunctions(logFunctions);
//			logManagement.setAuPosition(auEmployee.getAuPosition()); // 操作人角色
			logManagement.setTime(new Date()); // 操作时间
			logManagement.setState(1);
			logManagement.setDataValue(data);
			this.addPo(logManagement, auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	/**
	 * 通过时间段、操作人员和角色 查询日志
	 */
	@Override
	public DataGrid findList(PageParameter pageParameter) {
		Map<String, Object> map = new HashMap();
		String hql = "from LogManagement x where x.state = 1";

		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("startDate")
				&& !pageParameter.getParaMap().get("startDate").equals("")) {
			hql += " and x.time >=:startDate";
			Date startDate = DateUtil.format((String) pageParameter
					.getParaMap().get("startDate"), "yyyy-MM-dd");
			map.put("startDate", startDate);
		}
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("endDate")
				&& !pageParameter.getParaMap().get("endDate").equals("")) {
			hql += " and x.time <:endDate";
			Calendar c = Calendar.getInstance();
			Date endDate = DateUtil.format((String) pageParameter.getParaMap()
					.get("endDate"), "yyyy-MM-dd");
			c.setTime(endDate); // 设置当前日期
			c.add(Calendar.DATE, 1); // 日期分钟加1,Calendar.DATE(天),Calendar.HOUR(小时)
			Date date = c.getTime(); // 结果
			map.put("endDate", date);
		}
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("name")
				&& pageParameter.getParaMap().get("name") != null
				&& pageParameter.getParaMap().get("name") != "") {
			hql += " and x.auEmployee.realName like :name";
			map.put("name", "%" + pageParameter.getParaMap().get("name") + "%");
		}
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("logFunctions")
				&& pageParameter.getParaMap().get("logFunctions") != null
				&& pageParameter.getParaMap().get("logFunctions") != "") {
			hql += " and x.logFunctions like :logFunctions";
			map.put("logFunctions",
					"%" + pageParameter.getParaMap().get("logFunctions") + "%");
		}
		// 根据ip地址查询
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("ip")
				&& pageParameter.getParaMap().get("ip") != null
				&& pageParameter.getParaMap().get("ip") != "") {
			hql += " and x.ip like :ip";
			map.put("ip", "%" + pageParameter.getParaMap().get("ip") + "%");
		}
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("auPositionId")
				&& pageParameter.getParaMap().get("auPositionId") != null
				&& !pageParameter.getParaMap().get("auPositionId").equals("")) {
			hql += " and x.auPosition.id =:auPositionId";
			map.put("auPositionId",
					pageParameter.getParaMap().get("auPositionId"));
		}
		hql += " order by x.time desc";
		pageParameter.setParaMap(map);
		return this.baseDao.findByhql(hql, pageParameter);
	}

	/**
	 * 查看系统日志集合
	 */
	@Override
	public List<LogManagement> findList() {
		return this.baseDao.find("from LogManagement x where x.state = 1 ");
	}

}
