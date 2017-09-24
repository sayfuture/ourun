package com.mxcx.erp.gs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.base.commons.service.BaseService;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.util.StringCheck;
import com.mxcx.erp.gs.dao.entity.GsGoods;
import com.mxcx.erp.gs.dao.entity.GsSku;
import com.mxcx.erp.lo.service.LogManagementService;

/**
 * 
 */
@Service
@Transactional
public class GsSkuServiceImpl extends BaseService<GsSku> implements GsSkuService {
	@Autowired
	private LogManagementService logManagementService; // 日志记录

	@Autowired
	private GsGoodsService gsGoodsService; 
	@Override
	public Boolean addGsSkus(String gid,List<GsSku> gsSkus,AuEmployee employee) {
		boolean b =true;
		try {
				String hql = "update  GsSku t   set t.state=0  where t.goods_id=:goods_id";
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("goods_id", gid);
				this.baseDao.executeHql(hql, map);
				GsGoods gsGoods =(GsGoods) gsGoodsService.getOne(gid, GsGoods.class);
				for (int i = 0; i < gsSkus.size(); i++) {
					GsSku	gsSku	=gsSkus.get(i);
					gsSku.setStatus(1);//设置商品规格公布
					if(i==0){
		        		gsSku.setIs_def(1);
		        		gsGoods.setPv(gsSku.getPv());
		        		gsGoods.setPrice(gsSku.getPrice());
		        		this.modify(gsGoods, employee);
//		        		this.gsGoodsService.addPo(gsGoods, employee);
		        	}
		        	else{
		        		gsSku.setIs_def(0);
		        		
		        	}
					this.addPo(gsSku, employee);
				}
				
		} catch (Exception e) {
			e.printStackTrace();
			b=false;
		}
		return b;
	}

	@Override
	public Boolean deleteGsSkus(String gid,AuEmployee employee) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataGrid findGsSkusList(PageParameter pageParameter) {
		StringBuffer hql = new StringBuffer("from GsSku gs where gs.state = 1 ");
		String gid = pageParameter.getParaMap().get("gid")+"";
		if(StringCheck.stringCheck(gid)) {
			hql.append("  and gs.goods_id=:goods_id");
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("goods_id",gid);
			pageParameter.setParaMap(paraMap);
		}
		hql.append(" order by gs.createDate desc");
		return this.baseDao.findByhql(hql.toString(), pageParameter);
	}

	@Override
	public int saveNotGsSku(String gid,AuEmployee employee,GsSku gsSku) {
		int flag = 0;
		try {
			if(StringCheck.stringCheck(gid)){
				String hql="select count(*) from GsSku x where x.state = 1 and x.goods_id =:goods_id and  x.props is  null ";
				Map<String, Object>  map= new HashMap<String ,Object>();
				map.put("goods_id", gid);
				if(this.baseDao.count(hql,map)==0){
					 hql = "update  GsSku t   set t.state=0  where t.goods_id=:goods_id";
					 map = new HashMap<String, Object>();
					 map.put("goods_id", gid);
					 this.baseDao.executeHql(hql, map);
					gsSku.setPropsname("默认规格");
					gsSku.setIs_def(1);
					gsSku.setState(1);
			    	gsSku.setStatus(1);//设置商品规格公布
			    	this.addPo(gsSku, employee);	
			    	GsGoods gsGoods =(GsGoods) gsGoodsService.getOne(gid, GsGoods.class);
	        		gsGoods.setPv(gsSku.getPv());
	        		gsGoods.setPrice(gsSku.getPrice());
	        		this.modify(gsGoods, employee);
			    	
				}
				else{
					return 1;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 2;
		}
		return flag;
	}

	@Override
	public Boolean updateGsSku(GsSku gsSku, AuEmployee employee) {
		boolean b =true;
		try {
			if(gsSku.getIs_def()==1){
				GsGoods gsGoods =(GsGoods) gsGoodsService.getOne(gsSku.getGoods_id(), GsGoods.class);
				gsGoods.setPv(gsSku.getPv());
				gsGoods.setPrice(gsSku.getPrice());
				this.modify(gsGoods, employee);
			}
			this.modify(gsSku, employee);
		} catch (Exception e) {
			b =false;
		}
		return b;
	}

	@Override
	public Boolean editGsSkuStatus(HttpServletRequest request) {
		Boolean flag=true;
		String id=request.getParameter("id");
		String status=request.getParameter("status");
		GsSku gsSku=null;
		if(StringCheck.stringCheck(id)){
			gsSku=(GsSku) this.getOne(id, GsSku.class);
		}else{
			flag=false;
		}
		if(StringCheck.stringCheck(status)){
			gsSku.setStatus(Integer.valueOf(status));
			this.modify(gsSku);
		}else{
			flag=false;
		}
		return flag;
	}

}