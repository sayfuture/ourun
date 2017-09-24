package com.mxcx.erp.gs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.base.commons.controller.BaseController;
import com.mxcx.erp.gs.dao.entity.GsGoods;
import com.mxcx.erp.gs.dao.entity.GsKuProp;
import com.mxcx.erp.gs.service.GsGoodsService;
import com.mxcx.erp.gs.service.GsKuPropService;

@Controller
public class GsKuPropAction  extends BaseController {

	@Autowired
	private GsKuPropService gsKuPropService;
	
	@Autowired
	private GsGoodsService  gsGoodsService;
	
	@RequestMapping(value = "/manager/erp/gs/gsKuProp.do")
	public String index() {
		return "/ftl/manager/gs/gsKuProp";
	}
	
	@RequestMapping(value = "/manager/erp/gs/findGsKuPropList.do")
	@ResponseBody
	public DataGrid findGsKuPropList(HttpServletRequest request, @ModelAttribute("pp") PageParameter pageParameter){
		DataGrid dataGrid = gsKuPropService.findGsKuPropList(pageParameter );
		return dataGrid;
	}
	
	@RequestMapping(value = "/manager/erp/gs/bandGsKuProp.do")
	@ResponseBody
	public int bandGsKuProp(String ids,String gid, HttpServletRequest request) {
		
		int  flag = 0;
		GsGoods gs = gsGoodsService.findGsGoodsByID(gid);
		if(gs.getStatus()==0){
			return 2;
		}
		String pnids[] = ids != null ? ids.split(",") : null;
		try {
			if (null != pnids) {
				gsKuPropService.bandGsKuProp(gid,pnids,this.getLoginUser(request));
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = 1;
		}
		return flag;
	}
	
	@RequestMapping(value = "/manager/erp/gs/removeGsPropName.do")
	@ResponseBody
	public Boolean removeGsPropName(String ids, HttpServletRequest request) {
		String pnids[] = ids != null ? ids.split(",") : null;
		Boolean flag = true;
		try {
			if (null != pnids) {
				for (String id : pnids) {
					gsKuPropService.removeGsPropName(id, this.getLoginUser(request));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	@RequestMapping(value = "/manager/erp/gs/showGsSku.do")
	@ResponseBody
	public List <GsKuProp>  showGsSku( HttpServletRequest request, String gid) {
		List <GsKuProp> gsKuProplist =gsKuPropService.findGsKuPropListView(gid);
		return gsKuProplist;
	}
	
}