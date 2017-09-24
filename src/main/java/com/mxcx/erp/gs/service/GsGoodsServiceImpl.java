package com.mxcx.erp.gs.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.base.adaptor.LogFunction;
import com.mxcx.erp.base.adaptor.LogModule;
import com.mxcx.erp.base.commons.service.BaseService;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.dao.IBaseDao;
import com.mxcx.ec.base.commons.util.StringCheck;
import com.mxcx.ec.base.commons.util.ToolUtils;
import com.mxcx.ec.base.commons.util.UuidDitch;
import com.mxcx.erp.gs.dao.entity.GsGoods;
import com.mxcx.erp.gs.dao.entity.GsGoodsPicture;
import com.mxcx.erp.gs.dao.entity.GsSku;
import com.mxcx.erp.lo.service.LogManagementService;

/**
 * 
 * 
 */
@Service
public class GsGoodsServiceImpl extends BaseService<GsGoods> implements GsGoodsService {

	@Autowired
	private IBaseDao<GsGoods> gsGoodsDao;
	@Autowired
	private IBaseDao<GsSku> gsSkuDao;
	
	@Autowired
	private GsGoodsPictureService gsGoodsPictureService; 

	@Autowired
	private LogManagementService logManagementService;

	/**
	 * @see com.mxcx.erp.me.service.GsGoodsService#addGsGoods(GsGoods gsGoods,
	 *      AuEmployee auEmployee)
	 */
	@Override
	public Boolean addGsGoods(GsGoods gsGoods, AuEmployee auEmployee) {
		boolean flag = true;
		try {
			gsGoods.setId(UuidDitch.getId(LogModule.GSGOODS.getModuleNo()));
			flag = addPo(gsGoods, auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee,ToolUtils.getHostAddress(), flag,LogModule.GS_GOODS.toString(),LogFunction.GS_GOODS_CREATE.toString(), gsGoods.toString());
		}
		return flag;
	}

	@Override
	public Boolean deleteGsGoods(String id, AuEmployee auEmployee, HttpServletRequest request) {
		GsGoods gsGoods = null;
		Boolean flag = true;
		try {
			gsGoods = (GsGoods) this.getOne(id, GsGoods.class);
			removeByState(gsGoods);
			
			List<GsGoodsPicture> list = this.gsGoodsPictureService.findList(id);
			for (GsGoodsPicture paPackagePicture : list) {
				this.gsGoodsPictureService.removeGsGoodsPictures(request, paPackagePicture.getId(), auEmployee);
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee,ToolUtils.getHostAddress(), flag,	LogModule.GS_GOODS.toString(),LogFunction.GS_GOODS_DELETE.toString(), gsGoods.toString());
		}
		return flag;
	}

	@Override
	public Boolean modifyGsGoods(GsGoods gsGoods, AuEmployee auEmployee) {
		Boolean flag = true;
		GsGoods gsGoodsTemp = new GsGoods();
		try {
			gsGoodsTemp = (GsGoods) this.gsGoodsDao.getById(GsGoods.class, gsGoods.getId());
			gsGoodsTemp.setCatId(gsGoods.getCatId());
			gsGoodsTemp.setProductName(gsGoods.getProductName());
			gsGoodsTemp.setProductCode(gsGoods.getProductCode());
//			gsGoodsTemp.setPrice(gsGoods.getPrice());
//			gsGoodsTemp.setPv(gsGoods.getPv());
			gsGoodsTemp.setRecommend(gsGoods.getRecommend());
			gsGoodsTemp.setGoodsDesc(gsGoods.getGoodsDesc());
			gsGoodsTemp.setTitle(gsGoods.getTitle());
			this.modify(gsGoodsTemp, auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee,ToolUtils.getHostAddress(), flag,LogModule.GS_GOODS.toString(),LogFunction.GS_GOODS_UPDATE.toString(), gsGoods.toString());
		}
		return flag;
	}

	@Override
	public DataGrid findGsGoodsList(PageParameter pageParameter) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" select a.id");
		sqlBuffer.append("       ,a.product_code  productCode");
		sqlBuffer.append("       ,a.product_name  productName");
		sqlBuffer.append("       ,a.status");
		sqlBuffer.append("       ,a.cat_id        catId");
		sqlBuffer.append("       ,a.price");
		sqlBuffer.append("       ,a.pv");
		sqlBuffer.append("       ,a.picId       picId");
		sqlBuffer.append("       ,a.online_date   onlineDate");
		sqlBuffer.append("       ,a.offline_date  offlineDate");
		sqlBuffer.append("       ,a.goods_desc    goodsDesc");
		sqlBuffer.append("       ,a.is_top        isTop");
		sqlBuffer.append("       ,a.top_date      topDate");
		sqlBuffer.append("       ,a.top_user      topUser");
		sqlBuffer.append("       ,a.state");
		sqlBuffer.append("       ,a.create_date   createDate");
		sqlBuffer.append("       ,c.realname   createUser");
		sqlBuffer.append("       ,a.update_date   updateDate");
		sqlBuffer.append("       ,d.realname   updateUser");
		sqlBuffer.append("       ,b.name          catName");
		sqlBuffer.append("       ,a.recommend          recommend");
		sqlBuffer.append("       ,a.title          title");
		
		sqlBuffer.append(" from gs_goods a");
		sqlBuffer.append(" left join gs_goods_type b on b.id = a.cat_id");
		sqlBuffer.append("  left join au_employee c  on a.create_user = c.id   left join au_employee d on a.update_user = d.id "); 
		sqlBuffer.append(" where 1 = 1 and (a.status =1 or a.status =4 or a.status =3) ");
		sqlBuffer.append(" and a.state = 1 ");
		
		// 以下添加查询条件
		String productCode = (String) pageParameter.getParaMap().get("productCode");
		if(StringCheck.stringCheck(productCode)) {
			sqlBuffer.append(" and a.product_code like '%").append(productCode).append("%'");
		}
		String productName = (String) pageParameter.getParaMap().get("productName");
		if(StringCheck.stringCheck(productName)) {
			sqlBuffer.append(" and a.product_name like '%").append(productName).append("%'");
		}
		sqlBuffer.append(" order by a.create_date desc");
		return gsGoodsDao.findbatisByMysql("find_mybatis_GsGoods", sqlBuffer.toString(), pageParameter);
	}

	/**
	 * @see com.mxcx.erp.me.service.GsGoodsService#findGsGoodsByID(String id)
	 */
	@Override
	public GsGoods findGsGoodsByID(String id) {
		return (GsGoods) getOne(id, GsGoods.class);
	}

	@Override
	public DataGrid findPublishList(PageParameter pageParameter) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" select a.id");
		sqlBuffer.append("       ,a.product_code  productCode");
		sqlBuffer.append("       ,a.product_name  productName");
		sqlBuffer.append("       ,a.status");
		sqlBuffer.append("       ,a.cat_id        catId");
		sqlBuffer.append("       ,a.price");
		sqlBuffer.append("       ,a.pv");
		sqlBuffer.append("       ,a.picId       picId");
		sqlBuffer.append("       ,a.online_date   onlineDate");
		sqlBuffer.append("       ,a.offline_date  offlineDate");
		sqlBuffer.append("       ,a.goods_desc    goodsDesc");
		sqlBuffer.append("       ,a.is_top        isTop");
		sqlBuffer.append("       ,a.top_date      topDate");
		sqlBuffer.append("       ,a.top_user      topUser");
		sqlBuffer.append("       ,a.state");
		sqlBuffer.append("       ,a.create_date   createDate");
		sqlBuffer.append("       ,c.realname   createUser");
		sqlBuffer.append("       ,a.update_date   updateDate");
		sqlBuffer.append("       ,d.realname   updateUser");
		sqlBuffer.append("       ,b.name          catName");
		sqlBuffer.append("       ,a.recommend          recommend");
		sqlBuffer.append("       ,a.title          title");
		sqlBuffer.append(" from gs_goods a");
		sqlBuffer.append(" left join gs_goods_type b on b.id = a.cat_id");
		sqlBuffer.append("  left join au_employee c  on a.create_user = c.id   left join au_employee d on a.update_user = d.id "); 
		sqlBuffer.append(" where 1 = 1 and a.status != 1 and  a.status != 4 ");
		sqlBuffer.append(" and a.state = 1");
		
		// 以下添加查询条件
		String productCode = (String) pageParameter.getParaMap().get("productCode");
		if(StringCheck.stringCheck(productCode)) {
			sqlBuffer.append(" and a.product_code like '%").append(productCode).append("%'");
		}
		String productName = (String) pageParameter.getParaMap().get("productName");
		if(StringCheck.stringCheck(productName)) {
			sqlBuffer.append(" and a.product_name like '%").append(productName).append("%'");
		}
		String productStatus= (String)pageParameter.getParaMap().get("productStatus");
		if(StringCheck.stringCheck(productStatus)&&!productStatus.equals("-1")) {
			sqlBuffer.append(" and a.status = '").append(productStatus).append("'");
		}
		sqlBuffer.append(" order by a.update_date desc");
		return gsGoodsDao.findbatisByMysql("find_mybatis_GsGoods", sqlBuffer.toString(), pageParameter);
	}

	@Override
	public Boolean stopPublish(String id,AuEmployee employee) {
		Boolean  b = true;
		try {
			GsGoods gs = (GsGoods) getOne(id, GsGoods.class);
			gs.setStatus(3);
			gs.setOfflineDate(new Date());
			this.modify(gs,employee);
		} catch (Exception e) {
			b=false;e.printStackTrace();
		}
		return b;
	}

	@Override
	public Boolean startPublish(String id,AuEmployee employee) {
		Boolean  b = true;
		try {
		GsGoods gs = (GsGoods) getOne(id, GsGoods.class);
		StringBuilder sql=new StringBuilder("select count(*) from Gs_Sku gs where gs.state=1 and gs.goods_Code is null ");
		sql.append(" and gs.goods_id='"+gs.getId().toString()+"'");
		List list= gsSkuDao.findBySql(sql.toString(), null);
		Integer temp=new Integer( list.get(0).toString());
		if(temp>0){
			b=false;
		}else
		{
			gs.setOnlineDate(new Date());
			gs.setStatus(0);
			this.modify(gs,employee);
		}
		} catch (Exception e) {
			b=false;
		} 
		return b;
	}

	@Override
	public Boolean returnPublish(String id,AuEmployee employee) {
		Boolean  b = true;
		try {
		GsGoods gs = (GsGoods) getOne(id, GsGoods.class);
		gs.setStatus(4);
		this.modify(gs,employee);
		} catch (Exception e) {
			b=false;
		}
		return b;
	}

	@Override
	public Boolean audiGsgoods(String id,AuEmployee employee) {
		Boolean  b = true;
		try {
		GsGoods gs = (GsGoods) getOne(id, GsGoods.class);
		gs.setStatus(2);
		this.modify(gs,employee);
		} catch (Exception e) {
			b=false;
		}
		return b;
	}

}
