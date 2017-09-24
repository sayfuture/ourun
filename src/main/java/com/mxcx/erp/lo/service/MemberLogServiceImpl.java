package com.mxcx.erp.lo.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mxcx.erp.base.commons.service.BaseService;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.dao.IBaseDao;
import com.mxcx.ec.base.commons.util.DateUtil;
import com.mxcx.erp.lo.dao.entity.MemberLog;
/**
 * 会员日志业务层实现
 * @author 王森
 *
 */
@Service
public class MemberLogServiceImpl extends BaseService<MemberLog> implements
		MemberLogService {

	@Autowired
	private IBaseDao<MemberLog> baseDao;

	/**
	 * 分页查看会员日志
	 */
	@Override
	public DataGrid findList(PageParameter pageParameter) {
		Map<String, Object> map = new HashMap();
		String hql = "from MemberLog x where x.state = 1";

		// 根据时间段查询
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("startDate")
				&& !pageParameter.getParaMap().get("startDate").equals("")) {
			hql += " and x.createDate >=:startDate";
			Date startDate = DateUtil.format((String) pageParameter
					.getParaMap().get("startDate"), "yyyy-MM-dd");
			map.put("startDate", startDate);
		}
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("endDate")
				&& !pageParameter.getParaMap().get("endDate").equals("")) {
			hql += " and x.createDate <:endDate";
			Calendar c = Calendar.getInstance();
			Date endDate = DateUtil.format((String) pageParameter.getParaMap()
					.get("endDate"), "yyyy-MM-dd");
			c.setTime(endDate); // 设置当前日期
			c.add(Calendar.DATE, 1); // 日期分钟加1,Calendar.DATE(天),Calendar.HOUR(小时)
			Date date = c.getTime(); // 结果
			map.put("endDate", date);
		}
		// 根据会员行为查询
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("action")
				&& pageParameter.getParaMap().get("action") != null
				&& pageParameter.getParaMap().get("action") != "") {
			hql += " and x.logFunctions like:action";
			map.put("action", "%" + pageParameter.getParaMap().get("action")
					+ "%");

		}
		// 根据ip地址查询
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("remoteIp")
				&& pageParameter.getParaMap().get("remoteIp") != null
				&& pageParameter.getParaMap().get("remoteIp") != "") {
			hql += " and x.remoteIp like:remoteIp";
			map.put("remoteIp", "%" + pageParameter.getParaMap().get("remoteIp") + "%");
		}
		// 根据会员名查询
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("name")
				&& pageParameter.getParaMap().get("name") != null
				&& pageParameter.getParaMap().get("name") != "") {
			hql += " and x.meMember.realName like:name";
			map.put("name",
					"%" + (String) pageParameter.getParaMap().get("name") + "%");
		}
		hql += " order by x.createDate desc";
		pageParameter.setParaMap(map);
		return this.baseDao.findByhql(hql, pageParameter);
	}

	/**
	 * 查看会员日志
	 */
	@Override
	public List<MemberLog> findList() {
		return this.baseDao.find("from MemberLog x where x.state = 1 ");

	}

}
