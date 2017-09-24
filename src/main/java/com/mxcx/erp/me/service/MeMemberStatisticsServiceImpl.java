package com.mxcx.erp.me.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mxcx.ec.base.commons.dao.IBaseDao;
import com.mxcx.erp.me.dao.entity.MeMember;

/**
 * 会员统计Service
 * 
 * @author 孙瑞 2014/10/22
 */
@Service
public class MeMemberStatisticsServiceImpl implements IMeMemberStatisticsService {

	@Autowired
	private IBaseDao<MeMember> memberDao;

	@Override
	public List<MeMember> getMembers(Date startDate, Date endDate) {
		String hql = "from MeMember x where x.state=1 and x.createDate >= :startDate and x.createDate <= :endDate order by x.createDate asc";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		return memberDao.find(hql, params);
	}
	
}