package com.mxcx.erp.gs.controller;

import java.io.File;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.auremote.vo.TreeGridVo;
import com.mxcx.erp.base.commons.controller.BaseController;
import com.mxcx.erp.gs.dao.entity.GsGoodsType;
import com.mxcx.erp.gs.service.GsGoodsTypeService;

/**
 * 商品分类管理
 * 
 * @author 王森
 * 
 */
@Controller
public class GsGoodsTypeAction extends BaseController {
	@Autowired
	private GsGoodsTypeService gsGoodsTypeService; // 商品分类业务层接口

	/**
	 * 商品分类管理
	 */
	@RequestMapping("/manager/erp/gs/gsGoodsType.do")
	public String index(Model map) {
		return "/ftl/manager/gs/gsGoodsType";
	}

	/**
	 * 根据id查询子节点分类
	 * 
	 * @param id功能id
	 * @return 分类列表
	 */
	@RequestMapping(value = "/manager/erp/gs/findGsGoodsTypeList.do")
	@ResponseBody
	public List<TreeGridVo> findList(HttpServletRequest request, String id) {
		return gsGoodsTypeService.findList(id);
	}

	/**
	 * 新增商品分类
	 * 
	 * @param id功能id
	 * @return 分类列表
	 */
	@RequestMapping(value = "/manager/erp/gs/addGsGoodsType.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean addGsGoodsType(HttpServletRequest request,GsGoodsType gsGoodsType) {
		return gsGoodsTypeService.addGsGoodsType(gsGoodsType,getLoginUser(request));

	}

	/**
	 * 查询商品分类
	 * 
	 * @param id功能id
	 * @return 分类列表
	 */
	@RequestMapping("/manager/erp/gs/getGsGoodsType.do")
	@ResponseBody
	public GsGoodsType getGsGoodsType(String id) {
		GsGoodsType gsGoodsType = (GsGoodsType) gsGoodsTypeService.getOne(id,GsGoodsType.class);
		gsGoodsType.setGsGoodsType(null);
		gsGoodsType.setGsGoodsTypes(null);
		return gsGoodsType;
	}

	/**
	 * 验证商品分类名称
	 * 
	 * @param id功能id
	 * @return 分类列表
	 */
	@RequestMapping("/manager/erp/gs/checkGsGoodsTypeName.do")
	@ResponseBody
	public Boolean checkName(String name) {
		return gsGoodsTypeService.checkName(name);
	}

	/**
	 * 删除商品分类
	 * 
	 * @param id功能id
	 * @return 是否成功
	 */
	@RequestMapping("/manager/erp/gs/deleteGsGoodsType.do")
	@ResponseBody
	public Boolean deleteGsGoodsType(HttpServletRequest request, String id) {
		return gsGoodsTypeService.deleteGsGoodsType(id, this.getLoginUser(request));
	}

	/**
	 * 修改商品分类
	 * 
	 * @param id功能id
	 * @return 是否成功
	 */
	@RequestMapping("/manager/erp/gs/updateGsGoodsType.do")
	@ResponseBody
	public Boolean updateGsGoodsType(HttpServletRequest request,GsGoodsType gsGoodsType) {
		return gsGoodsTypeService.updateGsGoodsType(gsGoodsType, this.getLoginUser(request));
		
	}

	/**
	 * 录入商品分类文件
	 * 
	 * @param request
	 */
	@RequestMapping("/manager/erp/gs/batchAddGsGoodsType.do")
	@ResponseBody
	public void batchAddGsGoodsType(HttpServletRequest request) {
		AuEmployee auEmployee = getLoginUser(request);
		try {
			// 获取商品分类文件
			File file = gsGoodsTypeService.goodsTypeFileUpload(request);
			// 商品分类入库
			gsGoodsTypeService.batchAddGoodsType(auEmployee, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设定分类为热门分类
	 */
	@RequestMapping("/manager/erp/gs/changeToHotGsGoodsType.do")
	@ResponseBody
	public Boolean changeToHotGsGoodsType(String id, int type,
			HttpServletRequest request) {
		return gsGoodsTypeService.changeToHotGsGoodsType(id, type,
				this.getLoginUser(request));
	}

}
