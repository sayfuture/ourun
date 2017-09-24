package com.mxcx.erp.base.commons.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import com.mxcx.ec.base.commons.util.PropertiesReader;

/**
 * @see(功能介绍): 项目路径
 * @version(版本号): 1.0
 * @date(创建日期): 2014-9-3
 */
public class MyFreeMarkerView extends FreeMarkerView {  

	private static final String CONTEXT_PATH = "base"; //页面直接引用 ${base}   

	/**
	 *  从写exposeHelpers函数
	 *  
	 * @param Map<String, Object> model, HttpServletRequest request
	 */
	@Override 
	protected void exposeHelpers(Map<String, Object> model,  HttpServletRequest request) throws Exception {  
		model.put(CONTEXT_PATH, PropertiesReader.getInstance().getConfigItem("rootPath") ); 
        super.exposeHelpers(model, request);  
	}
} 