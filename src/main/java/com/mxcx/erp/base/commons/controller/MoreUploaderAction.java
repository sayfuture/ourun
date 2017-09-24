package com.mxcx.erp.base.commons.controller;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * 
 * @author 王森20160127
 * 
 */
@Controller
public class MoreUploaderAction extends BaseController {
	private final static Logger log = Logger.getLogger(MoreUploaderAction.class);
	
		@RequestMapping("/manager/erp/sy/uploader.do")
		public ModelAndView  index(String chunk,String picTypeId,String roomId ,String roomTypeId,String projectId) {
			ModelAndView modelAndView = new ModelAndView( "/ftl/manager/sy/uploader");
			modelAndView.addObject("chunk",chunk);
			modelAndView.addObject("picTypeId",picTypeId);
			modelAndView.addObject("roomId",roomId);
			modelAndView.addObject("roomTypeId",roomTypeId);
			modelAndView.addObject("projectId",projectId);
			return modelAndView;
		}
	}


