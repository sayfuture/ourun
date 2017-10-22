package com.mxcx.erp.area.dao.service;

import com.mxcx.ec.base.commons.dao.IBaseDao;
import com.mxcx.erp.area.dao.entity.Areas;
import com.mxcx.erp.area.dao.entity.Cities;
import com.mxcx.erp.area.dao.entity.Provinces;
import com.mxcx.erp.base.commons.service.BaseService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
		List list;
		Map<String,Object> cache=SystemCache.cache;
		if((!cache.isEmpty())&&cache.get("area")!=null){
			list= (List) cache.get("area");
		}else{
		StringBuffer hql = new StringBuffer(
				"from Provinces ");
		 list=Dao.find(hql.toString());
			SystemCache.cache.put("area",list);
		}
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

	@Override
	public Areas findDistById(String areasId) {
		StringBuffer hql = new StringBuffer(
				"from Areas areas where areas.id='"+areasId+"'");
		List list=Dao.find(hql.toString());
		Areas areas= (Areas) list.get(0);
		return areas;
	}
}
