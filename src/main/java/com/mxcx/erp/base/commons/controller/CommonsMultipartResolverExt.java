package com.mxcx.erp.base.commons.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.mxcx.ec.base.commons.util.FileUploadListener;

/**
 * 重写CommonsMultipartResolver 实现上传文件进度条 修改配置文件 CommonsMultipartResolver改为CommonsMultipartResolverExt
 * 
 * @author ws
 * @version 1.0
 */
public class CommonsMultipartResolverExt extends CommonsMultipartResolver {

    private HttpServletRequest request;

    @Override
    protected FileUpload newFileUpload(FileItemFactory fileItemFactory) {
        ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
        long aa = upload.getFileSizeMax();
        System.out.println(aa);
        if (request != null) {
            // /System.out.println("注入监听");
            FileUploadListener uploadProgressListener = new FileUploadListener();
            upload.setProgressListener(uploadProgressListener);
            HttpSession session = request.getSession();
            session.setAttribute("uploadProgressListener", uploadProgressListener);
        }
        return upload;
    }

    @Override
    public MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) throws MultipartException {
        this.request = request;// 获取到request,要用到session
        return super.resolveMultipart(request);
    }
}
