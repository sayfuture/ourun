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
import com.mxcx.erp.di.dao.entity.DiPermanent;
import com.mxcx.erp.di.service.DiPermanentService;

/**
 * DiPermanentAction Mon Jun 19 11:19:37 CST 2017 hmy
 */

@Controller
public class DiPermanentAction extends BaseController {

	@Autowired
	private DiPermanentService diPermanentService;

	@RequestMapping(value = "/manager/erp/di/diPermanent.do")
	public String index() {
		return "/ftl/manager/di/diPermanent";
	}

	@RequestMapping("/manager/erp/di/findDiPermanentList.do")
	@ResponseBody
	public DataGrid findDiPermanentList(HttpServletRequest request,
			@ModelAttribute("pp") PageParameter pageParameter) {
		DataGrid dataGrid = diPermanentService
				.findDiPermanentList(pageParameter,this.getLoginUser(request));
		return dataGrid;
	}

	@RequestMapping(value = "/manager/erp/di/addDiPermanent.do", method = RequestMethod.POST)
	@ResponseBody
	public boolean addDiPermanent(DiPermanent diPermanent,
			HttpServletRequest request) {
		return diPermanentService.addDiPermanent(diPermanent,
				this.getLoginUser(request));
	}

	@RequestMapping(value = "/manager/erp/di/modifyDiPermanent.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean modifyDiPermanent(DiPermanent diPermanent,
			HttpServletRequest request) {
		return diPermanentService.modifyDiPermanent(diPermanent,
				this.getLoginUser(request));
	}

	@RequestMapping(value = "/manager/erp/di/deleteDiPermanent.do")
	@ResponseBody
	public Boolean deleteDiPermanent(String ids, HttpServletRequest request) {
		String teamIds[] = ids != null ? ids.split(",") : null;
		Boolean flag = true;
		try {
			if (null != teamIds) {
				for (String id : teamIds) {
					diPermanentService.deleteDiPermanent(id,
							this.getLoginUser(request));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@RequestMapping(value = "/manager/erp/di/goToModifyDiPermanent.do")
	@ResponseBody
	public DiPermanent goToModifyDiPermanent(String id) {
		DiPermanent diPermanent = diPermanentService.findDiPermanentByID(id);
		return diPermanent;
	}
}
