package com.mxcx.erp.gs.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.base.commons.controller.BaseController;
import com.mxcx.erp.gs.dao.entity.GsPropName;
import com.mxcx.erp.gs.dao.entity.GsPropValue;
import com.mxcx.erp.gs.service.GsPropNameService;
import com.mxcx.erp.gs.service.GsPropValueService;

/**
 * 级别管理Action层
 * 
 * @author  201409015
 * 
 */
@Controller
public class GsPropValueAction  extends BaseController {

	@Autowired
	private GsPropValueService gsPropValueService;
	@Autowired
	private GsPropNameService gsPropNameService;
	
	@RequestMapping(value = "/manager/erp/gs/gsPropValue.do")
	public String index() {
		return "/ftl/manager/gs/gsPropValue";
	}
	
	@RequestMapping(value = "/manager/erp/gs/findGsPropValueList.do")
	@ResponseBody
	public DataGrid findGsPropValueList(HttpServletRequest request, @ModelAttribute("pp") PageParameter pageParameter){
		
//		gsPropValueService.findsss();
		
		DataGrid dataGrid = gsPropValueService.findGsPropValueList(pageParameter);
		return dataGrid;
	}

	@RequestMapping(value = "/manager/erp/gs/addGsPropValue.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean addMember(GsPropValue gsPropValue, HttpServletRequest request){
		return gsPropValueService.addGsPropValue(gsPropValue, this.getLoginUser(request));
	}
	
	@RequestMapping(value = "/manager/erp/gs/modifyGsPropValue.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean modifyGsPropValue(GsPropValue gsPropValue, HttpServletRequest request){
		return gsPropValueService.modifyGsPropValue(gsPropValue, this.getLoginUser(request));
	}
	
	@RequestMapping(value = "/manager/erp/gs/deleteGsPropValue.do")
	@ResponseBody
	public Boolean deleteGsPropValue(String ids, HttpServletRequest request){
		String deptIds[] = ids != null ? ids.split(",") : null;
		Boolean flag = true;
		try {
			if (null != deptIds) {
				for (String id : deptIds) {
					gsPropValueService.deleteGsPropValue(id, this.getLoginUser(request));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	@RequestMapping(value = "/manager/erp/gs/goToModifyGsPropValue.do")
	@ResponseBody
	public GsPropValue goToModifyLevel(String id){
		GsPropValue gsPropValue = gsPropValueService.findGsPropValueByID(id);
		if(gsPropValue != null) {
			GsPropName gsPropName = gsPropNameService.findGsPropNameByID(gsPropValue.getPropId());
			if(gsPropName != null) {
				gsPropValue.setPropName(gsPropName.getPropName());
			}
		}

		return gsPropValue;
	}
	
}