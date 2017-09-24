package com.mxcx.erp.gs.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.base.commons.controller.BaseController;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.util.StringCheck;
import com.mxcx.erp.gs.dao.entity.GsGoodsPicture;
import com.mxcx.erp.gs.service.GsGoodsPictureService;
import com.mxcx.erp.gs.service.GsGoodsService;

/**
 * 商品控制层
 * 
 * @author dandan20140915
 */
@Controller
public class GsGoodsPictureAction extends BaseController{
	@Autowired
	private GsGoodsPictureService gsGoodsPictureService; // 商品图片业务层接口
	@Autowired
	private GsGoodsService gsGoodsService; // 商品业务层接口
	
	/**
	 * 商品图片管理
	 * 
	 * @return商品图片界面
	 */
	@RequestMapping("/manager/erp/gs/GsGoodsPicture.do")
	public String index(Model map) {
		return "/ftl/manager/gs/GsGoodsPicture";
	}
	
	/**
	 * 查询商品图片的所有信息
	 * 
	 * @param pageParameter 分页信息
	 * @return 商品图片集合(json格式)
	 */
	@RequestMapping(value = "/manager/erp/gs/findGsGoodsPicture.do", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid findDatagrid(HttpServletRequest request, @ModelAttribute("pp") PageParameter pageParameter, String id) {
		 DataGrid dd = gsGoodsPictureService.findList(pageParameter, id);
		 return dd;
	}

	/**
	 * 添加商品图片
	 * 
	 * @param gsGoodsPicture 商品图片实体
	 * @param request
	 * @return是否成功
	 */
	@RequestMapping(value = "/manager/erp/gs/addGsGoodsPicture.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean addGsGoodsPicture(GsGoodsPicture gsGoodsPicture, HttpServletRequest request) {
		return gsGoodsPictureService.addGsGoodsPicture(request, gsGoodsPicture, getLoginUser(request), "strFileName");
	}

	/**
	 * 更新商品图片
	 * 
	 * @param GsGoodsPicture商品图片实体
	 * @param request
	 * @return是否成功
	 */
	@RequestMapping(value = "/manager/erp/gs/updateGsGoodsPicture.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean updateGsGoodsPicture(GsGoodsPicture gsGoodsPicture, HttpServletRequest request) {
		return gsGoodsPictureService.modifyGsGoodsPicture(request, gsGoodsPicture, getLoginUser(request), "strFileName");
	}

	/**
	 * 删除商品图片（一个、多个）
	 * 
	 * @param ids商品图片实体Id拼串
	 * @return是否成功
	 */
	@RequestMapping(value = "/manager/erp/gs/deleteGsGoodsPictures.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean deleteGsGoodsPictures(String ids, HttpServletRequest request) {
		Boolean flag = true;
		try {
			if (StringCheck.stringCheck(ids)) {
				String[] idArray = ids.split(",");
				for (String id : idArray) {
					gsGoodsPictureService.removeGsGoodsPictures(request, id, this.getLoginUser(request));
				}
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 获取一个商品图片
	 * 
	 * @param id商品图片实体Id
	 * @return商品图片实体对象
	 */
	@RequestMapping(value = "/manager/erp/gs/getGsGoodsPicture.do", method = RequestMethod.GET)
	@ResponseBody
	public GsGoodsPicture getGsGoodsPicture(String id) {
		return (GsGoodsPicture) gsGoodsPictureService.getOne(id, GsGoodsPicture.class);
	}
	
	/**
	 * 获取一个商品图片
	 * 
	 * @param id商品图片实体Id
	 * @return商品图片实体对象
	 */
	@RequestMapping(value = "/manager/erp/gs/setAllGsGoodsPicture.do", method = RequestMethod.GET)
	public void setAllGsGoodsPicture(HttpServletRequest request) {
		gsGoodsPictureService.updateAllPicture(request);
	}
}