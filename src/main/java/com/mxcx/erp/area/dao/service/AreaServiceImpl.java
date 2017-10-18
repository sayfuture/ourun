package com.mxcx.erp.area.dao.service;

import com.mxcx.ec.base.commons.dao.IBaseDao;
import com.mxcx.erp.area.dao.entity.Cities;
import com.mxcx.erp.area.dao.entity.Provinces;
import com.mxcx.erp.base.commons.service.BaseService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * DiCardServiceImpl Thu Dec 29 20:51:23 CST 2016 hmy
 */

@Service
public class AreaServiceImpl extends BaseService<T> implements
		AreaService {

	@Autowired
	private IBaseDao<T> Dao;

	@Override
	public List<Cities> findCities(String povincesId) {
		StringBuffer hql = new StringBuffer(
				"from Cities cities where cities.povinces.povincesId='"+povincesId+"'");
		List list=Dao.find(hql.toString());
		return list;
	}

	@Override
	public List<Provinces> findProvinces() {
		StringBuffer hql = new StringBuffer(
				"from Provinces ");
		List list=Dao.find(hql.toString());
		return list;
	}

	@Override
	public Provinces findProvincesById(String id) {
		StringBuffer hql = new StringBuffer(
				"from Provinces p where p.id='"+id+"'");
		List list=Dao.find(hql.toString());
		Provinces provinces= (Provinces) list.get(0);
		return provinces;
	}

	@Override
	public Cities findCitiesById(String cityId) {
		StringBuffer hql = new StringBuffer(
				"from Cities cities where cities.id='"+cityId+"'");
		List list=Dao.find(hql.toString());
		Cities cities= (Cities) list.get(0);
		return cities;
	}
}
