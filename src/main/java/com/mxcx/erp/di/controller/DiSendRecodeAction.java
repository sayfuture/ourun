package com.mxcx.erp.di.controller;

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
import com.mxcx.erp.di.dao.entity.DiSendRecode;
import com.mxcx.erp.di.service.DiSendRecodeService;

/**
 * DiSendRecodeAction Mon Jan 16 14:06:33 CST 2017 hmy
 */

@Controller
public class DiSendRecodeAction extends BaseController {

	@Autowired
	private DiSendRecodeService diSendRecodeService;

	@RequestMapping(value = "/manager/erp/di/diSendRecode.do")
	public String index() {
		return "/ftl/manager/di/diSendRecode";
	}

	@RequestMapping(value = "/manager/erp/di/addDiSendRecode.do", method = RequestMethod.POST)
	@ResponseBody
	public boolean addDiSendRecode(DiSendRecode diSendRecode,
			HttpServletRequest request) {
		return diSendRecodeService.addDiSendRecode(diSendRecode,
				this.getLoginUser(request));
	}

	@RequestMapping(value = "/manager/erp/di/goToModifyDiSendRecode.do")
	@ResponseBody
	public DiSendRecode goToModifyDiSendRecode(String id) {
		DiSendRecode diSendRecode = diSendRecodeService
				.findDiSendRecodeByID(id);
		return diSendRecode;
	}
	
	@RequestMapping("/manager/erp/di/findDiSendRecodeList.do")
	@ResponseBody
	public DataGrid findDiSendRecodeList(HttpServletRequest request,
			@ModelAttribute("pp") PageParameter pageParameter) {
		DataGrid dataGrid = diSendRecodeService.findDiSendRecodeList(pageParameter,this.getLoginUser(request));
		return dataGrid;
	}
	
	
}
