package com.mxcx.erp.co.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mxcx.ec.base.commons.dao.IBaseDao;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.util.StringCheck;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.base.commons.service.BaseService;
import com.mxcx.erp.co.dao.entity.CoTemplate;
import com.mxcx.erp.di.dao.entity.DiCard;
import com.mxcx.erp.lo.service.LogManagementService;
import com.mxcx.erp.utils.Constant;
import com.mxcx.erp.wechat.service.WeChatService;
import com.mxcx.erp.wechat.service.WeChatServiceImpl;

/**
 * CoTemplateServiceImpl Fri Feb 17 09:56:39 CST 2017 hmy
 */

@Service
public class CoTemplateServiceImpl extends BaseService<CoTemplate> implements
		CoTemplateService {

	@Autowired
	private IBaseDao<CoTemplate> coTemplateDao;
	@Autowired
	private LogManagementService logManagementService;
	@Autowired
	private WeChatService weChatService;
	@Override
	public Boolean saveRefreshCoTemplate(AuEmployee auEmployee) {
		boolean flag = true;
		try {
			Map<String,Object> map=weChatService.newsTemplateList(auEmployee);
			JSONArray array=(JSONArray) map.get("template_list");
			
			List<CoTemplate> newList=new ArrayList<CoTemplate>();
			for(int i=0;i<array.size();i++){
				JSONObject result=(JSONObject) array.get(i);
				Map<String, Object> map1=WeChatServiceImpl.json(result.toString());
				String template_id=(String) map1.get("template_id");
				String title=(String) map1.get("title");
				String content=(String) map1.get("content");
				CoTemplate coTemplate1=(CoTemplate) this.findCoTemplateByID(template_id);
				if(coTemplate1==null){
						 coTemplate1=new CoTemplate();
						coTemplate1.setId(template_id);
						coTemplate1.setTitle(title);
						coTemplate1.setContent(content);
						coTemplate1.setCompanyId(auEmployee.getAuDept().getId());
						coTemplate1.setCreateTime(new Date());
						this.baseDao.addOrModify(coTemplate1);
				}
					newList.add(coTemplate1);
				
			}
			List<CoTemplate> list=this.baseDao.find("from CoTemplate c where c.companyId='"+auEmployee.getAuDept().getId()+"'");
			List<CoTemplate> dellist=new ArrayList<CoTemplate>();
			for(CoTemplate coTemplate:list){
				Boolean f=true;
				for(CoTemplate coTemplate1:newList){
					if(coTemplate.getId().equals(coTemplate1.getId())){
						f=false;
						break;
					}
				}
				if(f){
					dellist.add(coTemplate);
				}
			}
				for(CoTemplate coTemplate:dellist){
					this.baseDao.executeSql("delete from Co_Template where id='"+coTemplate.getId()+"'");
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} 
		return flag;
	}

	public Boolean  sendCoTemplate(AuEmployee auEmployee,DiCard diCard,String openId,String templateId){
		boolean flag = true;
		try{
			Map<String,Object> resultMap=weChatService.sendNews(auEmployee, templateId, openId, diCard);
			 if(resultMap.containsKey("errcode")&&!resultMap.get("errcode").equals(0)){
				 flag=false;
			 	}
		}catch(Exception e){
			flag=false;
			e.printStackTrace();
		}
		return flag;
	} 
	@Override
	public DataGrid findCoTemplateList(PageParameter pageParameter,AuEmployee auEmployee) {
		String name = (String) pageParameter.getParaMap().get("name");
		StringBuffer hql = new StringBuffer(
				"from CoTemplate coTemplate where 1=1");
		String companyId=auEmployee.getCompanyId();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if (StringCheck.stringCheck(name)) {
			hql.append(" and coTemplate.name like :name ");
			paraMap.put("name", "%" + name + "%");
		}
		if(StringUtils.isNotEmpty(companyId)&&!companyId.equals(Constant.COMPANYID)){
			hql.append(" and coTemplate.companyId= :companyId");
			paraMap.put("companyId",companyId);
		}
		pageParameter.setParaMap(paraMap);
		hql.append(" order by coTemplate.createTime desc");
		return coTemplateDao.findByhql(hql.toString(), pageParameter);
	}

	@Override
	public CoTemplate findCoTemplateByID(String id) {
		return (CoTemplate) getOne(id, CoTemplate.class);
	}
	public static void main(String[] args) {
		String s="{\"template_list\":[{\"template_id\":\"cjsA3FII4DXUhbllUdgTskTMLGMO7J_1lisXcLlcHrw\",\"title\":\"hello\",\"primary_industry\":\"\",\"deputy_industry\":\"\",\"content\":\"hello,world!你好，来自中国\",\"example\":\"\"},{\"template_id\":\"G4Wtql1BDSG8-IDFZzWE216XkANNT-QW-rr1eC-GrAo\",\"title\":\"测试第二条\",\"primary_industry\":\"\",\"deputy_industry\":\"\",\"content\":\"这是测试第二条内容\",\"example\":\"\"}]}";
		JSONObject j=JSONObject.fromObject(s);
    	Map<String,Object> map=j;
    	JSONArray o=(JSONArray) map.get("template_list");
    	for(int i=0;i<o.size();i++){
    		Map<String,Object> map1=(Map<String, Object>) o.get(i);
//        	Map<String,Object> map1=j1;
        	String o1= (String) map1.get("template_id");
    		System.out.println(o1);	
    	}
    	
	}
}
