package com.mxcx.erp.base.commons.service;

import javax.servlet.http.HttpServletRequest;
/**
 * 上传管理
 * @author  
 *
 */
public interface ISystemUpload {
	/**
	 * 上传文件
	 * @param request
	 * @param fileName文件名称
	 * @param systemPath文件路径
	 * @return文件名称
	 * @throws Exception
	 */
    public String systemUpload(HttpServletRequest request, String fileName, String systemPath) throws Exception;
    
    
    public String systemUpload(String newName , String oldName, String systemPath,String len) throws Exception ;
    
    /**
     * 删除文件
     * @param request
     * @param systemPath文件路径
     */
    public void systemDeleteFile(HttpServletRequest request, String mkfile, String systemPath);


}
