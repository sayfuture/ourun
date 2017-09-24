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
import com.mxcx.erp.gs.dao.entity.GsGoods;
import com.mxcx.erp.gs.service.GsGoodsPictureService;
import com.mxcx.erp.gs.service.GsGoodsService;
import com.mxcx.erp.gs.service.GsGoodsTypeService;


@Controller
public class GsPublishAction extends BaseController {
	@Autowired
	private GsGoodsService gsGoodsService;
	@Autowired
	private GsGoodsTypeService gsGoodsTypeService;
	
	
	@RequestMapping(value = "/manager/erp/gs/gsPublish.do")
	public String index() {
		return "/ftl/manager/gs/gsPublish";
	}

	@RequestMapping(value = "/manager/erp/gs/findPublishList.do")
	@ResponseBody
	public DataGrid findGsGoodsList(HttpServletRequest request, @ModelAttribute("pp") PageParameter pageParameter) {
		DataGrid dataGrid = gsGoodsService.findPublishList(pageParameter);
		return dataGrid;
	}
	@RequestMapping(value = "/manager/erp/gs/startPublish.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean startPublish(String id, HttpServletRequest request) {
		return gsGoodsService.startPublish(id,this.getLoginUser(request));
	}

	@RequestMapping(value = "/manager/erp/gs/stopPublish.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean stopPublish(String id, HttpServletRequest request) {
		return gsGoodsService.stopPublish(id,this.getLoginUser(request));
	}
	
	@RequestMapping(value = "/manager/erp/gs/returnPublish.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean returnPublish(String id, HttpServletRequest request) {
		return gsGoodsService.returnPublish(id,this.getLoginUser(request));
	}
	
	@RequestMapping(value = "/manager/erp/gs/audiGsgoods.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean audiGsgoods(String id, HttpServletRequest request) {
		return gsGoodsService.audiGsgoods(id,this.getLoginUser(request));
	}
	
	
}