package com.mxcx.erp.gs.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mxcx.erp.base.adaptor.FilePath;
import com.mxcx.erp.base.commons.service.ISystemUpload;
import com.mxcx.ec.base.commons.util.PropertiesReader;

/**
 * 上传图片
 * 
 */
@Controller
@RequestMapping("/manager/erp/gs/upload.do")
public class ImgUploadController {
	@Autowired
	private ISystemUpload systemUpload;
	public static final String FILESEPARATOR = System.getProperty("file.separator");
	protected final Logger logger = Logger.getLogger(ImgUploadController.class);

	/** ~~~ 上传文件的保存路径 */
	private static final String FILE_UPLOAD_DIR = "/upload";
	/** ~~~ 为了能让CKEDITOR加载到上传的图片，此处将位置限制在了upload下 */
	private static final String FOR_FREEMARKER_LOAD_DIR = "/gs/";
	/** ~~~ 上传文件的最大文件大小 */
	private static final long MAX_FILE_SIZE = 1024 * 1024 * 2;

	@RequestMapping(method = RequestMethod.GET)
	public void processUpload(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		processUploadPost(modelMap, request, response);
		return;
	} 

	@RequestMapping(method = RequestMethod.POST)
	public void processUploadPost(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {

		// 判断提交的请求是否包含文件
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		if (!isMultipart) {
			return;
		}

		try {
			response.setContentType("text/html; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out = response.getWriter();
			// 上传文件的返回地址
			String fileUrl = "";

			FileItemFactory factory = new DiskFileItemFactory();

			ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
			servletFileUpload.setFileSizeMax(MAX_FILE_SIZE);
			String timedate = FilePath.getDatetime()+"//";
			String fileClientName = this.systemUpload.systemUpload(request,
					"uploadFile", FilePath.GS_PICTURE_UPLOAD_FILE_PATH.filePath+timedate);
			String frontProject = PropertiesReader.getInstance().getConfigItem("frontProject");
			fileUrl = "http://"+request.getLocalAddr()+":"+request.getLocalPort()+frontProject+FILE_UPLOAD_DIR+FOR_FREEMARKER_LOAD_DIR + FilePath.getDatetime()+"/"+fileClientName;
			String callback = request.getParameter("CKEditorFuncNum");
			out.println("<script type='text/javascript'>"
					+ "window.parent.CKEDITOR.tools.callFunction(" + callback
					+ ",'" + fileUrl + "',''" + ")" + "</script>");

			out.flush();
			out.close();

		} catch (IOException e) {
			logger.error("上传文件发生异常！", e);
		} catch (FileUploadException e) {
			logger.error("上传文件发生异常！", e);
		} catch (Exception e) {
			logger.error("上传文件发生异常！", e);
		}
		return;
	}
}