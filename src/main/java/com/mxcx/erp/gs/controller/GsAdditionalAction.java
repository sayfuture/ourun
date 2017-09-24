package com.mxcx.erp.gs.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.base.commons.controller.BaseController;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.erp.gs.service.GsAdditionalService;

/**
 * 
 */
@Controller
public class GsAdditionalAction  extends BaseController {

	@Autowired
	private GsAdditionalService gsAdditionalService;
	
	/**
	 * 补充货物路由方法
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/gs/gsAdditional.do")
	public String index(){
		return "/ftl/manager/gs/gsAdditional";
	}
	
	/**
	 * 展示所有商品规格类型
	 * @param request
	 * @param pageParameter
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/gs/findList.do")
	@ResponseBody
	public DataGrid findList(HttpServletRequest request, @ModelAttribute("pp") PageParameter pageParameter){
		DataGrid dataGrid=gsAdditionalService.findAdditionalList(pageParameter);
		return dataGrid;
	}
	
	
	/**
	 * 增加商品数量
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/gs/gsAdd.do")
	@ResponseBody
	public Boolean gsAdd(HttpServletRequest request){
		Boolean flag=gsAdditionalService.gsAdd(request);
		return flag;
	}	

	
	/**
	 * 按商品名称查询
	 * @param request
	 * @param pageParameter
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/gs/findGsName.do")
	@ResponseBody
	public DataGrid findGsName(HttpServletRequest request, @ModelAttribute("pp") PageParameter pageParameter){
		String gsName=request.getParameter("gsName");
		DataGrid dataGrid=gsAdditionalService.findGsName(gsName,pageParameter);
		return dataGrid;
	}	
	
}