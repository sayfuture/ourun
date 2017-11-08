package com.mxcx.erp.area.dao.service;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.erp.area.dao.entity.Areas;
import com.mxcx.erp.area.dao.entity.Cities;
import com.mxcx.erp.area.dao.entity.Provinces;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.di.dao.entity.DiCard;

import java.util.List;

/**
 * DiCardService
 * Thu Dec 29 20:51:23 CST 2016 hmy
 */


public interface AreaService {

 public List<Cities> findCities(String povincesId);
 public List<Provinces> findProvinces();

    Provinces findProvincesById(String id);

    Cities findCitiesById(String cityId);

    Areas findDistById(String distId);

    List<Provinces> findProvinceses();

    List<Areas> findAreas(String cityId);
}

