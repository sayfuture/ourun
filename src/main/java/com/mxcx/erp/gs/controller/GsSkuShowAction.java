package com.mxcx.erp.gs.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.base.commons.controller.BaseController;
import com.mxcx.erp.gs.service.GsSkuShowService;

/**
 * 
 */
@Controller
public class GsSkuShowAction  extends BaseController {

	@Autowired
	private GsSkuShowService gsSkuShowService;
	
	/**
	 * 展示SKU路由方法
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/gs/gsSKU.do")
	public String index(){
		return "/ftl/manager/gs/gsSKU";
	}
	
	/**
	 * 展示所有商品规格类型
	 * @param request
	 * @param pageParameter
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/gs/findSKUList.do")
	@ResponseBody
	public DataGrid findSKUList(HttpServletRequest request, @ModelAttribute("pp") PageParameter pageParameter){
		DataGrid dataGrid=gsSkuShowService.findSkuList(pageParameter);
		return dataGrid;
	}
	
	
	/**
	 *	查询商品销售单元
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/gs/searchForGsSKU.do")
	@ResponseBody
	public DataGrid searchForGsSKU(HttpServletRequest request, @ModelAttribute("pp") PageParameter pageParameter){
		DataGrid dataGrid=gsSkuShowService.searchForGsSKU(pageParameter);
		return dataGrid;
	}	

	
	
}