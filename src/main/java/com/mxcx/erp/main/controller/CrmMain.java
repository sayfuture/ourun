package com.mxcx.erp.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mxcx.erp.base.commons.controller.BaseController;

@Controller
public class CrmMain extends BaseController{
	
	@RequestMapping("/manager/main.do")
	public String main(Model map) {
		return "/jsp/login";
	}
}
