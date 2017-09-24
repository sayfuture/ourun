package com.mxcx.erp.base.commons.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import com.mxcx.ec.base.commons.util.CustomTimestampEditor;
import com.mxcx.erp.au.dao.entity.AuEmployee;
/**
 * @see(功能介绍):BaseDao层
 * @version(版本号): 1.0
 * @date(创建日期): 2014-9-3
 * @author 王森
 */
@ControllerAdvice
public class BaseController {
	
	/**
	 * 获取当前登陆人员
	 * 
	 * @param request
	 * @return auEmployee 登陆人员对象
	 * @version 1.0
	 */
	protected AuEmployee getLoginUser(HttpServletRequest request){
		AuEmployee auEmployee = (AuEmployee) request.getSession().getAttribute("auEmployee");
		return auEmployee;
	}
	
	

	
	
	/**
	 * 添加日期类型绑定，可以从页面时间字符串转换至java的日期类型
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		datetimeFormat.setLenient(false);
		//自动转换日期类型的字段格式
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(java.sql.Timestamp.class, new CustomTimestampEditor(datetimeFormat, true));
		//防止XSS攻击
		//binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false));
	}
	protected HttpSession getCurrentSession(HttpServletRequest request){
		return request.getSession(false);
	}
	
}
