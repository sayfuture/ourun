package com.mxcx.erp.co.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mxcx.erp.auremote.vo.TreeGridVo;
import com.mxcx.erp.base.adaptor.LogModule;
import com.mxcx.erp.base.commons.controller.BaseController;
import com.mxcx.ec.base.commons.util.UuidDitch;
import com.mxcx.erp.co.dao.entity.CoContent;
import com.mxcx.erp.co.dao.entity.CoType;
import com.mxcx.erp.co.service.CoTypeService;

/**
 * 内容分类管理控制层
 * 
 * @author 王森20140910
 * 
 */
@Controller
public class CoTypeAction extends BaseController {
	@Autowired
	private CoTypeService coTypeService;

	/**
	 * 内容分类管理
	 */
	@RequestMapping("/manager/erp/co/coType.do")
	public String index(Model map) {

		return "/ftl/manager/co/coType";
	}

	/**
	 * 添加新分类
	 * 
	 * @param coType新内容分类信息
	 * @return 对应操作成功或失败的布尔值
	 */
	@RequestMapping(value = "/manager/erp/co/addCoType.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean addCoType(CoType coType, HttpServletRequest request) {
		Boolean flag = true;
		try {
			if (coType.getSuperCoType() == null || coType.getSuperCoType().getId() == null || "".equals(coType.getSuperCoType().getId())) {
				coType.setSuperCoType(null);
			}
			coType.setId(UuidDitch.getId(LogModule.COTYPE.getModuleNo()));
			coTypeService.addPo(coType, this.getLoginUser(request));
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} 
		return flag;
	}

	/**
	 * 根据id删除内容分类
	 * 
	 * @param id功能id
	 * @return 对应操作成功或失败的布尔值
	 */

	@RequestMapping(value = "/manager/erp/co/deleteCoType.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean deleteCoType(String id, HttpServletRequest request) {
		Boolean flag = true;
		try {
			CoType coType = (CoType) this.coTypeService.getOne(id, CoType.class);
			this.coTypeService.removeByState(coType);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} 
		return flag;
	}

	/**
	 * 批量删除功能
	 * 
	 * @param ids待删除功能id集合
	 * @return 是否操作成功
	 */

	@RequestMapping(value = "/manager/erp/co/deleteBinchCoType.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean removeBinch(String ids, HttpServletRequest request) {
		String deptIds[] = ids != null ? ids.split(",") : null;
		Boolean flag = true;
		try {
			if (null != deptIds) {
				for (String string : deptIds) {
					this.deleteCoType(string, request);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	/**
	 * 修改内容分类信息
	 * 
	 * @return 对应操作成功或失败的布尔值
	 */
	@RequestMapping(value = "/manager/erp/co/updateCoType.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean updateCoType(CoType coType, HttpServletRequest request) {
		Boolean flag = true;
		try {
			CoType coTypeTemp = (CoType) this.coTypeService.getOne(coType.getId(), CoType.class);
			coTypeTemp.setName(coType.getName());
			coTypeTemp.setEngName(coType.getEngName());
			this.coTypeService.modify(coTypeTemp, this.getLoginUser(request));
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	/**
	 * 根据id查询内容分类信息
	 * 
	 * @param id
	 * 
	 */
	@RequestMapping(value = "/manager/erp/co/getCoType.do", method = RequestMethod.POST)
	@ResponseBody
	public CoType getById(String id) {
		CoType coType = (CoType) coTypeService.getOne(id, CoType.class);
		coType.setCoTypes(null);
		coType.setSuperCoType(null);
		return coType;
	}

	/**
	 * 查询内容类型的所有信息
	 * 
	 * @param pageParameter
	 *            分页信息
	 * @return 功能集合(json格式)
	 * @author Administrator 2014-9-11
	 * 
	 */
	@RequestMapping(value = "/manager/erp/co/findCoType.do", method = RequestMethod.POST)
	@ResponseBody
	public List<TreeGridVo> findList(HttpServletRequest request, String id) {
		List<TreeGridVo> list = coTypeService.findList(id);
		return list;
	}
	
	@RequestMapping(value = "/manager/erp/co/findCoTypeList.do", method = RequestMethod.POST)
	@ResponseBody
	public List<CoType> findCoTypeList(HttpServletRequest request, String level) {
		List<CoType> list = coTypeService.findCoList(level);
		return list;
	}	
	
	
	/**
	 * 验证商品分类名称
	 * 
	 * @param id功能id
	 * @return 分类列表
	 */
	@RequestMapping("/manager/erp/co/checkCoTypeName.do")
	@ResponseBody
	public Boolean checkCoTypeName(String name, String type) {
		return coTypeService.checkName(type, name);
	}
	
	/**
	 * 验证商品分类名称
	 * 
	 * @param id功能id
	 * @return 分类列表
	 */
	@RequestMapping("/manager/erp/co/checkCoTypeName2.do")
	@ResponseBody
	public int checkCoTypeName2(String name, String engName) {
		Boolean flag1 = coTypeService.checkName("name", name);
		Boolean flag2 = coTypeService.checkName("engName", engName);
		int i = 0; // 都符合
		if(flag1 == false && flag2 == false){
			i = 1; // 都不符合
		}
		if(flag1 == true && flag2 == false){
			i = 2; // 英文名不符合
		}
		if(flag1 == false && flag2 == true){
			i = 3; // 中文名不符合
		}
		return i;
	}
}