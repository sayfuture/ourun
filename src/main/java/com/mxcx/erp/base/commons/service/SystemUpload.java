package com.mxcx.erp.base.commons.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mxcx.ec.base.commons.util.PropertiesReader;


@Component
public class SystemUpload implements ISystemUpload {
	public static final String FILESEPARATOR = PropertiesReader.getInstance().getConfigItem("file.separator");  
	
	private final static Logger log = Logger.getLogger(SystemUpload.class);
	
	/**
	 * 上传文件
	 * @param request
	 * @param fileName文件名称
	 * @param systemPath文件路径
	 * @return文件名称
	 * @throws Exception
	 */
	@Override
	public String systemUpload(HttpServletRequest request, String fileName, String systemPath) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile mfile1 = multipartRequest.getFile(fileName);
		System.out.println(mfile1.getOriginalFilename());
		if (mfile1.getSize() == 0) {
			return null;
		} else {
			//判断文件名称长度，超过50，则进行截串操作，避免数据库长度不够
			Map<String, String> fileInfo = this.getFileInfoMap(mfile1.getOriginalFilename(), 50); 
			String systemUUID = UUID.randomUUID().toString();
			String returnName = "";
			String newName = "";
			String fileFix = fileInfo.get("fileType");
			if (StringUtils.equalsIgnoreCase(fileFix, ".jpg")
					|| StringUtils.equalsIgnoreCase(fileFix, ".jpeg")
					|| StringUtils.equalsIgnoreCase(fileFix, ".bmp")
					|| StringUtils.equalsIgnoreCase(fileFix, ".gif")
					|| StringUtils.equalsIgnoreCase(fileFix, ".png")) {
				newName = systemUUID + fileInfo.get("fileType");
				returnName = systemUUID + fileFix;
			}else{
				newName = systemUUID + fileInfo.get("fileType");
				returnName = systemUUID + fileFix;	
			}
			String newFile = FILESEPARATOR +systemPath + FILESEPARATOR + newName;
			
			String baseUrl = PropertiesReader.getInstance().getConfigItem("frontProjectAddress") ;
			log.error("baseUrl" +baseUrl);
//			baseUrl = baseUrl.substring(0, baseUrl.lastIndexOf(PropertiesReader.getInstance().getConfigItem("rootPath").replace("/", ""))) + PropertiesReader.getInstance().getConfigItem("frontProject").replace("/", "") + FILESEPARATOR;
			//File file = new File(request.getSession().getServletContext().getRealPath(FILESEPARATOR+"upload") + newFile);
			File file = new File(baseUrl+systemPath.replace("//", "/")+newName);
			System.out.println("$$$$$$$$$$$$"+baseUrl+"######"+systemPath.replace("//", "/")+"######"+newName+"*************");
			if (!file.exists() && !file.isDirectory()) {
				file.mkdirs();
			}
			mfile1.transferTo(file);
			return newName;
		}
	}
	
	
	
	public String systemUpload(String newName, String oldName,String systemPath,String len) throws Exception {
			String returnName = "";
			String baseUrl = PropertiesReader.getInstance().getConfigItem("frontProjectAddress") ;
			log.error("baseUrl" +baseUrl);
//			baseUrl = baseUrl.substring(0, baseUrl.lastIndexOf(PropertiesReader.getInstance().getConfigItem("rootPath").replace("/", ""))) + PropertiesReader.getInstance().getConfigItem("frontProject").replace("/", "") + FILESEPARATOR;
			//File file = new File(request.getSession().getServletContext().getRealPath(FILESEPARATOR+"upload") + newFile);
			File file = new File(baseUrl+systemPath.replace("//", "/")+newName);
			System.out.println("$$$$$$$$$$$$"+baseUrl+"######"+systemPath.replace("//", "/")+"######"+newName+"*************");
			if (!file.exists() && !file.isDirectory()) {
				file.mkdirs();
			}
//			SyPicture sp = new SyPicture();
//			sp.setPicFilePath(newName);
//			sp.setFilesize(len);
//			sp.setName(oldName);
			return "";
	}
	 /**
     * 删除文件
     * 
     * @param request
     * @param systemPath文件路径
     */
	@Override
	public void systemDeleteFile(HttpServletRequest request, String mkfile, String systemPath) {
		String newFile =  mkfile  + systemPath; 
		String baseUrl = PropertiesReader.getInstance().getConfigItem("frontProjectAddress") ;
		
		String fileStr = baseUrl+newFile;
//		fileStr = fileStr.replace(PropertiesReader.getInstance().getConfigItem("rootPath").replace("/", ""), PropertiesReader.getInstance().getConfigItem("frontProject").replace("/", ""));
		System.out.println(fileStr);
		File file = new File(fileStr);
		if (file.exists())
			file.delete();
	}
	
	/**
	 * 获取路径
	 * @param systemPath
	 * @return
	 */
	private String getRealFilePath(String systemPath){
		String systemPath1 = "";
		String fileName = "";
		String realFileName = "";
		String strtype = "";
		System.getProperty("os.name");
		if(systemPath!=null){
			if(systemPath.lastIndexOf(FILESEPARATOR)>-1){//说明含有路径符号
				systemPath1 = systemPath.substring(0,systemPath.lastIndexOf(FILESEPARATOR)+1);
				fileName = systemPath.substring(systemPath.lastIndexOf(FILESEPARATOR), systemPath.length())
										.replace(FILESEPARATOR, "");
			}else{
				fileName = systemPath;
			}
			
			if(fileName!=null && fileName.length()>36){
				realFileName = fileName.substring(0, 36);
			}else{
				realFileName = fileName;
			}
			if(fileName.lastIndexOf(".")>-1){
				strtype = fileName.substring(fileName.lastIndexOf("."), fileName.length());
			}
			systemPath = systemPath1 + fileName ;
		}
		return systemPath;
	}
	
	/**
	 * 判断文件名称长度，超过长度参数限制，则按长度参数进行截串操作，避免数据库长度不够
	 * 文件后缀不算在内
	 * 返回信息包括：
	 * 【fileType】文件后缀
	 * 【fileOldName】文件原名称（不含后缀）
	 * 【fileNewName】文件处理后名称（不含后缀）
	 */
	private Map<String, String> getFileInfoMap(String fileName, int limitNum){
		Map<String, String> fileInfo = new HashMap<String, String>();
		String fileType = "";//文件后缀
		String fileOldName = "";//文件原名称（不含后缀）
		String fileNewName = "";//文件处理后名称（不含后缀）
		if(fileName != null){
			if(fileName.indexOf(".")>-1){
				fileType = fileName.substring(fileName.lastIndexOf(".", fileName.length()));
				fileOldName = fileName.substring(0, fileName.lastIndexOf("."));
			}else{
				fileOldName = fileName;
			}
			if(limitNum>0 && limitNum<fileOldName.length()){
				fileNewName = fileOldName.substring(limitNum);
			}else{
				fileNewName = fileOldName;
			}
		}
		fileInfo.put("fileType", fileType);
		fileInfo.put("fileOldName", fileOldName);
		fileInfo.put("fileNewName", fileNewName);
		return fileInfo;
	}

}