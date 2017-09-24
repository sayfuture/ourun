package com.mxcx.erp.co.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.base.commons.controller.BaseController;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.util.StringCheck;
import com.mxcx.erp.co.dao.entity.CoContent;
import com.mxcx.erp.co.service.CoContentService;
import com.mxcx.erp.co.service.CoTypeService;

/**
 * 内容控制层
 * 
 * @author  20140910
 * 
 */
@Controller
public class CoContentAction extends BaseController {
	@Autowired
	private CoContentService coContentService; // 内容业务类
	@Autowired
	private CoTypeService coTypeService; // 内容类型业务层

	/**
	 * 内容页链接
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("/manager/erp/co/coContent.do")
	public String index(Model map) {
		return "/ftl/manager/co/coContent";
	}

	/**
	 * 查询内容分页
	 * 
	 * @param pageParameter分页信息
	 * @return 内容集合(json格式)
	 */
	@RequestMapping(value = "/manager/erp/co/{id}/show")
	public ModelAndView findDatagrid(@PathVariable String id ,HttpServletRequest request) {
		System.out.println(id);
		Map<String,Object> detailMap = new HashMap<String,Object>();
		CoContent coContent = (CoContent) coContentService.getOne(id, CoContent.class);
		detailMap.put("coContent", coContent);
		return  new ModelAndView("/ftl/manager/co/coContView","detailMap", detailMap);
	}
	
	@RequestMapping(value = "/manager/erp/co/findCoContent.do", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid view(HttpServletRequest request,@ModelAttribute("pp") PageParameter pageParameter) {
		DataGrid dataGrid= coContentService.findList(pageParameter,this.getLoginUser(request));
		 return dataGrid;
	}

	/**
	 * 添加内容
	 * 
	 * @param coContent
	 *            内容实体
	 * @param request
	 * @return是否成功
	 */
	@RequestMapping(value = "/manager/erp/co/addCoContent.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean addCoContent(CoContent coContent, HttpServletRequest request) {
		Boolean flag=coContentService.addCoContent(coContent,request, "strFileName", getLoginUser(request));
		coContentService.modifyNewsPage(coContent.getId());
		return flag;
	}

	/**
	 * 更新内容
	 * 
	 * @param coContent
	 *            内容实体
	 * @param request
	 * @return是否成功
	 */
	@RequestMapping(value = "/manager/erp/co/updateCoContent.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean updateCoContent(CoContent coContent, HttpServletRequest request) {
		 Boolean flag= coContentService.modifyCoContent(coContent, request,"strFileName", getLoginUser(request));
		 coContentService.modifyNewsPage(coContent.getId());
		return flag;
	}

	/**
	 * 删除内容（一个、多个）
	 * 
	 * @param ids内容实体id拼串
	 * @return是否成功
	 */
	@RequestMapping(value = "/manager/erp/co/deleteCoContents.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean deleteCoContents(String ids, HttpServletRequest request) {
		Boolean flag = true;
		try {
			if (StringCheck.stringCheck(ids)) {
				String[] idArray = ids.split(",");
				for (String id : idArray) {
					coContentService.removeCoContents(id, request, this.getLoginUser(request));
				}
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 获取一个内容
	 * 
	 * @param id内容Id
	 * @return内容对象
	 */
	@RequestMapping(value = "/manager/erp/co/getCoContent.do", method = RequestMethod.GET)
	@ResponseBody
	public CoContent getCoContent(String id) {
		CoContent c = (CoContent) coContentService.getOne(id, CoContent.class);
		return c;
	}

	/**
	 * 通过功能获取所有内容
	 * 
	 * @param id内容分类Id
	 * @return内容对象集合
	 */
	@RequestMapping(value = "/manager/erp/co/getCoContentByAuthority.do", method = RequestMethod.POST)
	@ResponseBody
	public List<CoContent> getCoContentByCoType(String onchageid,HttpServletRequest request) {
		return coContentService.getCoContentByCoType(onchageid,this.getLoginUser(request));
	}

	/**
	 * 设置点击量
	 * 
	 * @param id内容id内容对象ID
	 * @return是否成功
	 */
	@RequestMapping(value = "/manager/erp/co/setCoContentClick.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean setCoContentClick(String id) {
		CoContent coContent = (CoContent) coContentService.getOne(id,
				CoContent.class);
		coContent.setClick(coContent.getClick() + 1);
		return coContentService.modifyCoContentClick(coContent);
	}
	
	@RequestMapping(value = "/manager/erp/co/modifyNewsPage.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean modifyNewsPage(String id) {
		Boolean flag=coContentService.modifyNewsPage(id);
		return flag;
	}
	
	
}