package com.mxcx.erp.area.dao.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * 按钮实体类
 * 
 * @author 2014/09/03
 * 
 */
public class CityList {

	private List<Provinces> citylist;

	public List<Provinces> getCitylist() {
		return citylist;
	}

	public void setCitylist(List<Provinces> citylist) {
		this.citylist = citylist;
	}
}