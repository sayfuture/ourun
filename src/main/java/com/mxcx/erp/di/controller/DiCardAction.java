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
import com.mxcx.erp.au.dao.entity.AuDept;
import com.mxcx.erp.base.commons.controller.BaseController;
import com.mxcx.erp.di.dao.entity.DiCard;
import com.mxcx.erp.di.service.DiCardService;

/**
 * DiCardAction Thu Dec 29 20:51:23 CST 2016 hmy
 */

@Controller
public class DiCardAction extends BaseController {

	@Autowired
	private DiCardService diCardService;

	@RequestMapping(value = "/manager/erp/di/diCard.do")
	public String index() {
		return "/ftl/manager/di/diCard";
	}

	@RequestMapping("/manager/erp/di/findDiCardList.do")
	@ResponseBody
	public DataGrid findDiCardList(HttpServletRequest request,
			@ModelAttribute("pp") PageParameter pageParameter) {
		DataGrid dataGrid = diCardService.findDiCardList(pageParameter,this.getLoginUser(request));
		return dataGrid;
	}

	@RequestMapping(value = "/manager/erp/di/addDiCard.do", method = RequestMethod.POST)
	@ResponseBody
	public boolean addDiCard(DiCard diCard, HttpServletRequest request) {
		return diCardService.addDiCard(diCard, this.getLoginUser(request),request);
	}

	@RequestMapping(value = "/manager/erp/di/modifyDiCard.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean modifyDiCard(DiCard diCard, HttpServletRequest request) {
		return diCardService.modifyDiCard(diCard, this.getLoginUser(request),request);
	}

	@RequestMapping(value = "/manager/erp/di/deleteDiCard.do")
	@ResponseBody
	public Boolean deleteDiCard(String ids, HttpServletRequest request) {
		String teamIds[] = ids != null ? ids.split(",") : null;
		Boolean flag = true;
		try {
			if (null != teamIds) {
				for (String id : teamIds) {
					diCardService.deleteDiCard(id, this.getLoginUser(request));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@RequestMapping(value = "/manager/erp/di/goToModifyDiCard.do")
	@ResponseBody
	public DiCard goToModifyDiCard(String id) {
		DiCard diCard = diCardService.findDiCardByID(Integer.valueOf(id));
		return diCard;
	}
}
