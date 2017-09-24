package com.mxcx.erp.gs.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.base.commons.controller.BaseController;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.util.FileUploadListener;
import com.mxcx.erp.gs.dao.entity.GsGoods;
import com.mxcx.erp.gs.dao.entity.GsGoodsType;
import com.mxcx.erp.gs.service.GsGoodsPictureService;
import com.mxcx.erp.gs.service.GsGoodsService;
import com.mxcx.erp.gs.service.GsGoodsTypeService;

/**
 * 
 */
@Controller
public class GsGoodsAction extends BaseController {
	@Autowired
	private GsGoodsService gsGoodsService;
	@Autowired
	private GsGoodsTypeService gsGoodsTypeService;
	
	@Autowired
	GsGoodsPictureService gsGoodsPictureService;
	
	@RequestMapping(value = "/manager/erp/gs/gsGoods.do")
	public String index() {
		return "/ftl/manager/gs/gsGoods";
	}

	@RequestMapping(value = "/manager/erp/gs/findGsGoodsList.do")
	@ResponseBody
	public DataGrid findGsGoodsList(HttpServletRequest request, @ModelAttribute("pp") PageParameter pageParameter) {
		DataGrid dataGrid = gsGoodsService.findGsGoodsList(pageParameter);
		return dataGrid;
	}

    
    
	@RequestMapping(value = "/manager/erp/gs/addGsGoods.do", method = RequestMethod.POST)
	@ResponseBody
	public String addGsGoods(GsGoods gsGoods, HttpServletRequest request) {
		try {
			gsGoods.setStatus(1);
			gsGoods.setIsTop(0);
			gsGoods.setFavourNum(0);
			gsGoods.setHitsNum(0);
			gsGoods.setBuycount(0);
			gsGoodsService.addGsGoods(gsGoods, this.getLoginUser(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gsGoods.getId();
	}

	@RequestMapping(value = "/manager/erp/gs/modifyGsGoods.do", method = RequestMethod.POST)
	@ResponseBody
	public String modifyGsGoods(GsGoods gsGoods, HttpServletRequest request) {
		System.out.println(gsGoods);
		gsGoodsService.modifyGsGoods(gsGoods, this.getLoginUser(request));
		return gsGoods.getId();
	}

	@RequestMapping(value = "/manager/erp/gs/deleteGsGoods.do")
	@ResponseBody
	public Boolean deleteGsGoods(String ids, HttpServletRequest request) {
		String deptIds[] = ids != null ? ids.split(",") : null;
		Boolean flag = true;
		try {
			if (null != deptIds) {
				for (String id : deptIds) {
					gsGoodsService.deleteGsGoods(id, this.getLoginUser(request), request);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@RequestMapping(value = "/manager/erp/gs/goToModifyGsGoods.do")
	@ResponseBody
	public GsGoods goToModifyLevel(String id) {
		GsGoods gsGoods = gsGoodsService.findGsGoodsByID(id);
		if(gsGoods != null) {
			GsGoodsType gsGoodsType = (GsGoodsType) gsGoodsTypeService.getOne(gsGoods.getCatId(), GsGoodsType.class);
			if(gsGoodsType != null) {
				gsGoods.setCatName(gsGoodsType.getName());
			}
		}
		return gsGoods;
	}

	@RequestMapping(value = "/manager/erp/gs/setGsGoodsPicture.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean setGsGoodsPicture(String id, String imgId, HttpServletRequest request) {
		Boolean flag = true;
		try {
			flag = gsGoodsPictureService.setGsGoodsPicture(id,imgId);
		} catch (Exception e) {
			flag=false;
		}
		 
		
		return flag;
	}
	@RequestMapping(value = "/manager/erp/gs/getGsGoodsImg.do", method = RequestMethod.POST)
	@ResponseBody
	public String getGsGoodsImg(String id) {
		GsGoods gsGoods = (GsGoods) gsGoodsService.getOne(id, GsGoods.class);
		return gsGoods.getPicUrl();
	}

}