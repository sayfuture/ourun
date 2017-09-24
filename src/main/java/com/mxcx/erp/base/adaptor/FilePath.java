package com.mxcx.erp.base.adaptor;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.mxcx.ec.base.commons.util.PropertiesReader;

/**
 * 文件上传路径配置
 */
public enum FilePath {
	
	CO_CAROUSE_UPLOAD_FILE_PATH("轮播图文件上传路径", "upload"+PropertiesReader.getInstance().getConfigItem("file.separator")+"co"+PropertiesReader.getInstance().getConfigItem("file.separator")),
	PR_BASEINFODETAIL_UPLOAD_FILE_PATH("楼盘详细图片上传", "upload"+PropertiesReader.getInstance().getConfigItem("file.separator")+"pd"+PropertiesReader.getInstance().getConfigItem("file.separator")),
	PR_BASEINFO_UPLOAD_FILE_PATH("楼盘图片上传", "upload"+PropertiesReader.getInstance().getConfigItem("file.separator")+"pr"+PropertiesReader.getInstance().getConfigItem("file.separator")),
	
	AD_INFO_UPLOAD_FILE_PATH("广告图片上传", "upload"+PropertiesReader.getInstance().getConfigItem("file.separator")+"ad"+PropertiesReader.getInstance().getConfigItem("file.separator")),
	CO_CONTENT_UPLOAD_FILE_PATH("内容主体上传", "upload"+PropertiesReader.getInstance().getConfigItem("file.separator")+"co"+PropertiesReader.getInstance().getConfigItem("file.separator")),
	GS_PICTURE_UPLOAD_FILE_PATH("商品图片上传", "upload"+PropertiesReader.getInstance().getConfigItem("file.separator")+"gs"+PropertiesReader.getInstance().getConfigItem("file.separator")),
	RA_PICTURE_UPLOAD_FILE_PATH("奖品图片上传", "upload"+PropertiesReader.getInstance().getConfigItem("file.separator")+"ra"+PropertiesReader.getInstance().getConfigItem("file.separator")),
	PI_CYCLE_UPLOAD_FILE_PATH("轮播图图片上传", "upload"+PropertiesReader.getInstance().getConfigItem("file.separator")+"pi"+PropertiesReader.getInstance().getConfigItem("file.separator")),
	SA_CONSULANT_UPLOAD_FILE_PATH("店铺图像上传", "upload"+PropertiesReader.getInstance().getConfigItem("file.separator")+"sa"+PropertiesReader.getInstance().getConfigItem("file.separator")),
	FHT_PI_CYCLE_UPLOAD_FILE_PATH("房互通轮播图上传", "upload"+PropertiesReader.getInstance().getConfigItem("file.separator")+"fc"+PropertiesReader.getInstance().getConfigItem("file.separator")),
	;
	
	public String name;//名称
	public String filePath;//文件上传路径
	
	private FilePath(String name, String filePath) {
		this.name = name;
		this.filePath = filePath;
	}
	public static String getDatetime(){
		Date date = new Date(); 
		String time =new SimpleDateFormat("yyyyMMdd").format(date);
		return time;
		
	}
	
}
