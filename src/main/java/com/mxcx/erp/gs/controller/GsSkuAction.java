package com.mxcx.erp.gs.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mxcx.erp.base.adaptor.LogModule;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.base.commons.controller.BaseController;
import com.mxcx.ec.base.commons.util.StringCheck;
import com.mxcx.ec.base.commons.util.UuidDitch;
import com.mxcx.erp.gs.dao.entity.GsGoods;
import com.mxcx.erp.gs.dao.entity.GsSku;
import com.mxcx.erp.gs.service.GsGoodsService;
import com.mxcx.erp.gs.service.GsSkuService;

/**
 * 
 */
@Controller
public class GsSkuAction  extends BaseController {
	@Autowired
	private GsSkuService gsSkuService;
	@Autowired
	private GsGoodsService gsGoodsService;
	
	@RequestMapping(value = "/manager/erp/gs/findGsSkus.do")
	@ResponseBody
	public DataGrid findGsKuPropList(HttpServletRequest request, @ModelAttribute("pp") PageParameter pageParameter){
		DataGrid dataGrid = gsSkuService.findGsSkusList(pageParameter );
		return dataGrid;
	}
	
	
	@RequestMapping(value = "/manager/erp/gs/getGsSku.do")
	@ResponseBody
	public GsSku getGsSku(String id){
		GsSku gsSku = (GsSku)gsSkuService.getOne(id, GsSku.class);
		return gsSku;
	}
	@RequestMapping(value = "/manager/erp/gs/saveNotGsSku.do")
	@ResponseBody
	public int saveNotGsSku(HttpServletRequest request,String gid,String price,String pv,String quantity) {
		GsSku gsSku = new GsSku();
		gsSku.setId(UuidDitch.getId(LogModule.GS_SKU.getModuleNo()));
    	gsSku.setGoods_id(gid);
    	gsSku.setQuantity(Integer.parseInt(quantity));
    	gsSku.setPv(Integer.parseInt(pv));
    	gsSku.setPrice(new Double(price));
    	return this.gsSkuService.saveNotGsSku(gid, this.getLoginUser(request), gsSku);
	}
	
	@RequestMapping(value = "/manager/erp/gs/addGsSkus.do")
	@ResponseBody
	public int addGsSkus(HttpServletRequest request ,String len,String gid) {
		int  flag = 0;
		GsGoods gs = gsGoodsService.findGsGoodsByID(gid);
		if(gs.getStatus()==0){
			return 2;
		}
		try {
		 	List<GsSku> gsskuList = new ArrayList<GsSku>();
	        int l = Integer.parseInt(len);
	        for (int i = 0; i <l; i++) {
	        	GsSku gsSku = new GsSku();
	        	int kucun = Integer.parseInt(request.getParameter("kucun"+i));
	        	int pv = Integer.parseInt(request.getParameter("pv"+i));
	        	Double price =new Double(request.getParameter("price"+i));
	        	String propvalues = request.getParameter("propvalues"+i);
	        	String propnames = request.getParameter("propnames"+i);
	        	gsSku.setId(UuidDitch.getId(LogModule.GS_SKU.getModuleNo()));
	        	gsSku.setGoods_id(gid);
	        	gsSku.setQuantity(kucun);
	        	gsSku.setPv(pv);
	        	gsSku.setPrice(price);
	        	gsSku.setPropsname(propnames);
	        	gsSku.setProps(propvalues);
	        	gsskuList.add(gsSku);
			}
	        if(!gsSkuService.addGsSkus(gid,gsskuList, this.getLoginUser(request))){
	        	flag = 1;
	        }
		} catch (Exception e) {
					e.printStackTrace();
					flag=1;
		}
		return flag;
	}
	
	
	@RequestMapping(value = "/manager/erp/gs/updateGsSku.do")
	@ResponseBody
	public int updateGsSku(HttpServletRequest request ,String gsSkuId,String price,String pv,String quantity){
		int flag =0;
		try {
			if(StringCheck.stringCheck(gsSkuId)){
				GsSku gsSku = (GsSku)this.gsSkuService.getOne(gsSkuId, GsSku.class);
			  	gsSku.setQuantity(Integer.parseInt(quantity));
		    	gsSku.setPv(Integer.parseInt(pv));
		    	gsSku.setPrice(new Double(price));
				boolean b = gsSkuService.updateGsSku(gsSku, this.getLoginUser(request));
				if(b==false){
					flag =2; 
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return flag =2;
		}
		return flag;
	}
	
	/**
	 * 是否取消商品规格发布
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/gs/editGsSkuStatus.do")
	@ResponseBody
	public Boolean editGsSkuStatus(HttpServletRequest request){
		Boolean flag=gsSkuService.editGsSkuStatus(request);
		return flag;
	}
	
}