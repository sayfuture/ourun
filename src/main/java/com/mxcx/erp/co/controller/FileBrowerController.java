package com.mxcx.erp.co.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mxcx.ec.base.commons.util.PropertiesReader;
/**
 * 浏览服务器的图片
 *  
 * 
 */
@Controller
@RequestMapping("/manager/erp/co/browerServer.do")
public class FileBrowerController {
	protected final Logger logger = Logger
			.getLogger(FileBrowerController.class);
	/** ~~~ 上传文件的保存路径 */
	private static final String FILE_UPLOAD_DIR = "upload";
	/** ~~~ 上传文件的保存的下一级路径，标示存储类型 */
	private static final String FILE_UPLOAD_SUB_IMG_DIR = "co";

	@RequestMapping(method = RequestMethod.GET)
	public void processBrower(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		processBrowerPost(modelMap, request, response);
		return;
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(method = RequestMethod.POST)
	public void processBrowerPost(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {

		String typeStr = request.getParameter("type");
		String floderName = request.getParameter("fo");
		
		if (logger.isDebugEnabled()) {
			logger.debug("浏览文件，文件格式:" + typeStr);
		}

		// 定位到目标文件夹 ： 上传目录
		String realPath = "";
		if(StringUtils.isNotBlank(floderName)){
			floderName = URLDecoder.decode(floderName);
			// 如果请求中存在文件夹名称，则定位到文件夹中
			realPath = request.getSession().getServletContext().getRealPath(floderName);
			if(logger.isInfoEnabled()){
				logger.info("sub floder:"+realPath);
			}
		}else if(StringUtils.equalsIgnoreCase(typeStr, "Image")){
			// 如果请求中不存在文件夹名称，则使用默认的文件夹
			realPath = request.getSession().getServletContext().getRealPath(File.separator);
			realPath = realPath.substring(0, realPath.lastIndexOf(PropertiesReader.getInstance().getConfigItem("rootPath").replace("/", ""))) + PropertiesReader.getInstance().getConfigItem("frontProject").replace("/", "") + File.separator +FILE_UPLOAD_DIR+ File.separator+FILE_UPLOAD_SUB_IMG_DIR+File.separator;
			
//			realPath = request.getSession().getServletContext().getRealPath(FILE_UPLOAD_DIR+ File.separator+FILE_UPLOAD_SUB_IMG_DIR);
			if(logger.isInfoEnabled()){
				logger.info("default floder:"+realPath);
			}
		}
		
		File folder = new File(realPath);
		if(!folder.exists()){
			return;
		}
		
		// 存储子目录 ,路径需要从/freemarker开始
		List<String> subFolderSet = new ArrayList<String>();
		subFolderSet.add(FILE_UPLOAD_DIR);
		subFolderSet.add(FILE_UPLOAD_SUB_IMG_DIR);
		// 存储文件夹
		List<String> subFileerSet = new ArrayList<String>();
		File[] subFiles = folder.listFiles();
		if(null != subFiles && 0 < subFiles.length){
			for(int i=0;i < subFiles.length; i++){
				File _file = subFiles[i];
				String name = _file.getName();
				String fileFix = StringUtils.substring(name,name.lastIndexOf(".") + 1);
				if (StringUtils.equalsIgnoreCase(fileFix, "jpg")
						|| StringUtils.equalsIgnoreCase(fileFix, "jpeg")
						|| StringUtils.equalsIgnoreCase(fileFix, "bmp")
						|| StringUtils.equalsIgnoreCase(fileFix, "jpg")
						|| StringUtils.equalsIgnoreCase(fileFix, "gif")
						|| StringUtils.equalsIgnoreCase(fileFix, "png")) {
					String url = "http://"+((HttpServletRequest) request).getHeader("Host")+ PropertiesReader.getInstance().getConfigItem("frontProject")+ "/"+FILE_UPLOAD_DIR+"/"+FILE_UPLOAD_SUB_IMG_DIR +"/"+_file.getName();
					subFileerSet.add(url);
				}
			}
		}
		
		String callback = request.getParameter("CKEditorFuncNum");
		PrintWriter out;
		
		response.setContentType("text/html");
		response.setCharacterEncoding("GB2312");
		try {
			out = response.getWriter();
			out.println("<script type='text/javascript'>");
			
			// 定义点击选择js
			out.println("function choose(obj){");
			out.println("window.opener.CKEDITOR.tools.callFunction(" + callback
					+ ",obj)");
			out.println("window.close();");
			out.println("}");
			out.println("</script>");
			
			
			// 如果是文件，则点击就选择文件到控件中
			if(0 < subFileerSet.size()){
				for (String fileUrl : subFileerSet) {
					out.print("<div style='width:150px;height:150px;float:left;word-break:break-all;padding:5px;background:#666699;margin:5px;'>");
					out.print("<a href='javascript:void(0)' href='javascript:void(0)' onclick=choose('"+fileUrl+"')><img style='border:none;width:145px;height:145px;' src='"+fileUrl+"' title='"+fileUrl+"'/></a>");
					out.print("</div>");
					
					if(logger.isDebugEnabled()){
						logger.debug("添加文件："+fileUrl);
					}
				}}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}