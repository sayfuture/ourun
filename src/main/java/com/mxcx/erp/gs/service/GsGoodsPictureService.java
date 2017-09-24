package com.mxcx.erp.gs.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.base.commons.service.IBaseService;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.gs.dao.entity.GsGoodsPicture;

/**
 * 商品图片业务层接口
 * 
 * @author DANDAN 2014/09/19
 * 
 */
public interface GsGoodsPictureService extends IBaseService<GsGoodsPicture> {
	
	/**
	 * 添加商品图片
	 * 
	 * @param auEmployee操作人员
	 * @param GsGoodsPicture商品图片对象
	 * @param filePath图片名称
	 * @return是否成功
	 */
	public Boolean addGsGoodsPicture(HttpServletRequest request, GsGoodsPicture paPackagePicture, AuEmployee auEmployee, String filePath);

	/**
	 * 修改商品图片
	 * @param auEmployee操作人员
	 * @param GsGoodsPicture商品图片对象
	 * @param filePath图片名称
	 * @return是否成功
	 */
	public Boolean modifyGsGoodsPicture(HttpServletRequest request, GsGoodsPicture paPackagePicture, AuEmployee auEmployee, String filePath);

	/**
	 * 删除商品图片
	 * 
	 * @param auEmployee操作人员
	 * @param id商品图片对象Id
	 * @param filePath图片名称
	 * @return是否成功
	 */
	public Boolean removeGsGoodsPictures(HttpServletRequest request, String id, AuEmployee auEmployee);

	/**
	 * 根据商品查询商品图片分页
	 * @param pageParameter  分页参数
	 * @param Id 商品Id
	 * @return 商品图片分页
	 */
	public DataGrid findList(PageParameter pageParameter, String id);
	
	/**
	 * 根据商品查询商品图片集合
	 * @param pageParameter  分页参数
	 * @param Id 商品Id
	 * @return 商品图片集合
	 */
	public List<GsGoodsPicture> findList(String id);

	/**
	 * 查询所有商品图片（集合）
	 * 
	 * @return 商品图片分页
	 */
	public List<GsGoodsPicture> findList();
	

	/**
	 * 修改图片尺寸
	 * @param request
	 */
	public void updateAllPicture(HttpServletRequest request);

	
	
	public boolean setGsGoodsPicture(String id, String imgId);
}