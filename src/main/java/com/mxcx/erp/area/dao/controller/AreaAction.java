package com.mxcx.erp.area.dao.controller;

import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.erp.area.dao.entity.Areas;
import com.mxcx.erp.area.dao.entity.Cities;
import com.mxcx.erp.area.dao.entity.CityList;
import com.mxcx.erp.area.dao.entity.Provinces;
import com.mxcx.erp.area.dao.service.AreaService;
import com.mxcx.erp.au.dao.entity.AuDept;
import com.mxcx.erp.au.dao.entity.TreeAuDeptVo;
import com.mxcx.erp.au.service.AuDeptService;
import com.mxcx.erp.base.commons.controller.BaseController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 工作组控制层
 * @author  20140625
 * 
 */
@Controller
public class AreaAction extends BaseController {
	private final static Logger log = Logger.getLogger(AreaAction.class);
	@Autowired
	private AreaService areaService; // 地区业务层接口

	@RequestMapping(value = "/news/erp/findProvinces.do")
	@ResponseBody
	public CityList findList(HttpServletRequest request) {
		CityList list=new CityList();
		list.setCitylist(areaService.findProvinces());
		return list;
	}
	@RequestMapping(value = "/news/erp/findProvincesById.do")
	@ResponseBody
	public Provinces findProvincesById(HttpServletRequest request) {
		String pid=request.getParameter("provinceId");
		Provinces provinces=areaService.findProvincesById(pid);
		return provinces;
	}
	@RequestMapping(value = "/news/erp/findCities.do")
	@ResponseBody
	public List<Cities> findCities(HttpServletRequest request) {
		String pid=request.getParameter("provincesId");
		return areaService.findCities(pid);
	}
	@RequestMapping(value = "/news/erp/findProvinceses.do")
	@ResponseBody
	public List<Provinces> findProvinceses(HttpServletRequest request) {
		return areaService.findProvinceses();
	}
	@RequestMapping(value = "/news/erp/findAreas.do")
	@ResponseBody
	public List<Areas> findAreas(HttpServletRequest request) {
		String cityId=request.getParameter("cityId");
		return areaService.findAreas(cityId);
	}



	@RequestMapping(value = "/news/erp/findCitiesById.do")
	@ResponseBody
	public Cities findCitiesById(HttpServletRequest request) {
		String cityId=request.getParameter("cityId");
		Cities cities=areaService.findCitiesById(cityId);
		return cities;
	}
	@RequestMapping(value = "/news/erp/findAreaById.do")
	@ResponseBody
	public Areas findDistById(HttpServletRequest request) {
		String areaId=request.getParameter("areaId");
		Areas areas=areaService.findDistById(areaId);
		return areas;
	}
}