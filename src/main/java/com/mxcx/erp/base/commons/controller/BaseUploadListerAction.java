package com.mxcx.erp.base.commons.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mxcx.ec.base.commons.util.FileUploadListener;

/**
 * 
 */
@Controller
public class BaseUploadListerAction extends BaseController {

	
	  /**
     * 取得上传进度
     * 
     * 
     */
    @RequestMapping(value = "/manager/erp/comm/ProgressServlet.do", method = RequestMethod.POST)
    @ResponseBody
    public String progressServlet(HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        if (session == null) {
            return "Sorry, session is null";
        }
        FileUploadListener uploadProgressListener = (FileUploadListener) session.getAttribute("uploadProgressListener");
        if (uploadProgressListener == null) {
            return "Progress listener is null";
        }
        int ret = uploadProgressListener.getPercentDone();
        return ret + "";
    }

}