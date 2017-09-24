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
import com.mxcx.erp.gs.service.GsPropNameService;

/**
 * 级别管理Action层
 * 
 * @author  201409015
 * 
 */
@Controller
public class GsPropNameAction  extends BaseController {

	@Autowired
	private GsPropNameService gsPropNameService;
	
	@RequestMapping(value = "/manager/erp/gs/gsPropName.do")
	public String index() {
		return "/ftl/manager/gs/gsPropName";
	}
	
	@RequestMapping(value = "/manager/erp/gs/findGsPropNameList.do")
	@ResponseBody
	public DataGrid findGsPropNameList(HttpServletRequest request, @ModelAttribute("pp") PageParameter pageParameter){
		DataGrid dataGrid = gsPropNameService.findGsPropNameList(pageParameter);
		return dataGrid;
	}

	@RequestMapping(value = "/manager/erp/gs/addGsPropName.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean addMember(GsPropName gsPropName, HttpServletRequest request){
		return gsPropNameService.addGsPropName(gsPropName, this.getLoginUser(request));
	}
	
	@RequestMapping(value = "/manager/erp/gs/modifyGsPropName.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean modifyGsPropName(GsPropName gsPropName, HttpServletRequest request){
		return gsPropNameService.modifyGsPropName(gsPropName, this.getLoginUser(request));
	}
	
	@RequestMapping(value = "/manager/erp/gs/deleteGsPropName.do")
	@ResponseBody
	public Boolean deleteGsPropName(String ids, HttpServletRequest request){
		String deptIds[] = ids != null ? ids.split(",") : null;
		Boolean flag = true;
		try {
			if (null != deptIds) {
				for (String id : deptIds) {
					gsPropNameService.deleteGsPropName(id, this.getLoginUser(request));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	@RequestMapping(value = "/manager/erp/gs/goToModifyGsPropName.do")
	@ResponseBody
	public GsPropName goToModifyLevel(String id){
		GsPropName gsPropName = gsPropNameService.findGsPropNameByID(id);
		return gsPropName;
	}
	
}