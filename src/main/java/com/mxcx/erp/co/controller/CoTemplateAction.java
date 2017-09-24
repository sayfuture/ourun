package com.mxcx.erp.co.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.erp.base.commons.controller.BaseController;
import com.mxcx.erp.co.dao.entity.CoTemplate;
import com.mxcx.erp.co.service.CoTemplateService;

/**
 * CoTemplateAction Fri Feb 17 09:56:39 CST 2017 hmy
 */

@Controller
public class CoTemplateAction extends BaseController {

	@Autowired
	private CoTemplateService coTemplateService;

	@RequestMapping(value = "/manager/erp/co/coTemplate.do")
	public String index() {
		return "/ftl/manager/co/coTemplate";
	}

	@RequestMapping("/manager/erp/co/findCoTemplateList.do")
	@ResponseBody
	public DataGrid findCoTemplateList(HttpServletRequest request,
			@ModelAttribute("pp") PageParameter pageParameter) {
		DataGrid dataGrid = coTemplateService.findCoTemplateList(pageParameter,
				this.getLoginUser(request));
		return dataGrid;
	}

	@RequestMapping(value = "/manager/erp/co/refreshCoTemplate.do", method = RequestMethod.POST)
	@ResponseBody
	public boolean refreshCoTemplate(HttpServletRequest request) {
		return coTemplateService.saveRefreshCoTemplate(this.getLoginUser(request));
	}

	@RequestMapping(value = "/manager/erp/co/goToModifyCoTemplate.do")
	@ResponseBody
	public CoTemplate goToModifyCoTemplate(String id) {
		CoTemplate coTemplate = coTemplateService.findCoTemplateByID(id);
		return coTemplate;
	}
}
