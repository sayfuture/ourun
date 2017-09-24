package com.mxcx.erp.gs.service;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.auremote.vo.TreeGridVo;
import com.mxcx.erp.base.commons.service.IBaseService;
import com.mxcx.erp.gs.dao.entity.GsGoodsType;

/**
 * 商品分类业务层接口
 * 
 */
public interface GsGoodsTypeService extends IBaseService<GsGoodsType> {

	/**
	 * 查询商品分类列表
	 * 
	 * @param id父级ID
	 * @return treeGridVo easyui封装树
	 */
	public List<TreeGridVo> findList(String id);

	/**
	 * 验证商品分类名称
	 * 
	 * @param name
	 *            分类名称
	 * @return boolean 是否存在
	 */
	public Boolean checkName(String name);

	/**
	 * 商品扩展属性选择类型
	 * 
	 * @param id
	 * @return
	 */
	public List<TreeGridVo> findList2(String id);

	/**
	 * 根据下级找上级
	 * 
	 * @param id下级Id
	 * @return
	 */
	public GsGoodsType findUpper(String id);
	
	/**
	 * 商品文件分类上传
	 * 
	 * @param request
	 * @return
	 */
	public File goodsTypeFileUpload(HttpServletRequest request);
	
	/**
	 * 批量导入商品分类
	 * 
	 * @param request
	 * @param file  商品分类文件
	 * @return
	 */
	public void batchAddGoodsType(AuEmployee auEmployee, File file);
	
	/**
	 * 设置热门分类
	 * @param id分类Id
	 * @param auEmployee操作人员
	 * @return是否成功
	 * @author 王森 2014-11-8 下午3:55:25
	 */
	public Boolean changeToHotGsGoodsType(String id, int type, AuEmployee auEmployee);
	
	
	
	public Boolean addGsGoodsType(GsGoodsType gsGoodsType, AuEmployee auEmployee) ;

	public Boolean deleteGsGoodsType(String id,AuEmployee auEmployee);

	public Boolean updateGsGoodsType(GsGoodsType gsGoodsType,AuEmployee auEmployee);
	
	
	
	
}