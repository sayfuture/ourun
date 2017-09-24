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
import com.mxcx.erp.gs.service.GsSkuRecordService;

/**
 * 
 */
@Controller
public class GsSkuAddRecordAction  extends BaseController {

	@Autowired
	private GsSkuRecordService gsSkuRecordService;
	
	
	/**
	 * 查询补充货物记录路由方法
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/gs/gsAdditionalRecord.do")
	public String index(){
		return "/ftl/manager/gs/gsAdditionalRecord";
	}
	
	/**
	 * 补充货物全部记录
	 * @param request
	 * @param pageParameter
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/gs/findGsSkuAddRecord.do")
	@ResponseBody
	public DataGrid findGsSkuAddRecord(HttpServletRequest request, @ModelAttribute("pp") PageParameter pageParameter){
		DataGrid dataGrid = gsSkuRecordService.findGsSkusList(pageParameter );
		return dataGrid;
	}
	@RequestMapping(value = "/manager/erp/gs/findGsByName.do")
	@ResponseBody
	public DataGrid findGsByName(HttpServletRequest request, @ModelAttribute("pp") PageParameter pageParameter){
		String gsName=request.getParameter("gsName");
		DataGrid dataGrid = gsSkuRecordService.findGsName(gsName,pageParameter );
		return dataGrid;
	}
	
}