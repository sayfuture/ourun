package com.mxcx.erp.co.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mxcx.ec.base.commons.dao.IBaseDao;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.util.DateUtil;
import com.mxcx.ec.base.commons.util.PropertiesReader;
import com.mxcx.ec.base.commons.util.StaticPge;
import com.mxcx.ec.base.commons.util.StringCheck;
import com.mxcx.ec.base.commons.util.ToolUtils;
import com.mxcx.ec.base.commons.util.UuidDitch;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.base.adaptor.FilePath;
import com.mxcx.erp.base.adaptor.LogFunction;
import com.mxcx.erp.base.adaptor.LogModule;
import com.mxcx.erp.base.commons.service.BaseService;
import com.mxcx.erp.base.commons.service.ISystemUpload;
import com.mxcx.erp.co.dao.entity.CoContent;
import com.mxcx.erp.co.dao.entity.CoType;
import com.mxcx.erp.lo.service.LogManagementService;
import com.mxcx.erp.utils.Constant;

/**
 * 内容接口实现
 * 
 * @author  20140910
 * 
 */
@Service
public class CoContentServiceImpl extends BaseService<CoContent> implements
		CoContentService {
	@Autowired
	private IBaseDao<CoContent> dao; // 内容持久层
	@Autowired
	private IBaseDao<CoType> coTypeyDao; // 内容类型持久层
	@Autowired
	private LogManagementService logManagementService; // 日志记录
	@Autowired
	private ISystemUpload systemUpload; // 上传

	private static String   COCONTENT_PAGEURL="news/";
	/**
	 * 添加内容
	 * 
	 * @param coContent内容对象
	 * @param auEmployee人员对象
	 * @return是否成功
	 */
	@Override
	public Boolean addCoContent(CoContent coContent, HttpServletRequest request, String fileName, AuEmployee auEmployee) {
		Boolean flag = true;
		try {
			CoType coType = (CoType) coTypeyDao.get(CoType.class, coContent.getCoTypeId());
			coContent.setCoType(coType);
			coContent.setState(1);
			coContent.setClick(0);
			coContent.setTime(new Date());
			coContent.setId(UuidDitch.getId(LogModule.COCONTENT.getModuleNo()));
			coContent.setCompanyid(auEmployee.getAuDept().getId());
			//返回图片名称（全文件名） 
			System.out.println(FilePath.CO_CONTENT_UPLOAD_FILE_PATH.filePath);
//			String url=PropertiesReader.getInstance().getConfigItem("websiteUrl")+COCONTENT_PAGEURL+coContent.getId()+".do";
//			coContent.setPregetUrl(url);
			String timedate = FilePath.getDatetime()+"//";
		
			String pathurl = FilePath.CO_CONTENT_UPLOAD_FILE_PATH.filePath+timedate;
			
			String filePathName=null;
			if(this.systemUpload.systemUpload(request, fileName, pathurl)==null){
				
			}else{
				filePathName= this.systemUpload.systemUpload(request, fileName, pathurl);
			
			}
			if(null!=filePathName && !filePathName.isEmpty()){
				coContent.setFileUrl(timedate.replace("//", "/")+filePathName);
			}
			this.addPo(coContent, auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			this.logManagementService.addLogManagement(auEmployee, ToolUtils.getHostAddress(), flag, LogModule.COCONTENT.toString(), LogFunction.COCONTENT_CREATE.toString(),coContent.toString());
		}
		return flag;
	}

	/**
	 * 修改内容
	 * 
	 * @param coContent内容对象
	 * @param auEmployee人员对象
	 * @return是否成功
	 */
	@Override
	public Boolean modifyCoContent(CoContent coContent, HttpServletRequest request, String fileName, AuEmployee auEmployee) {
		Boolean flag = true;
		CoContent coContentTemp = null;
		try {
			coContentTemp = (CoContent) this.dao.getById(CoContent.class, coContent.getId());
			coContentTemp.setBody(coContent.getBody());
			CoType coType = (CoType) coTypeyDao.get(CoType.class, coContent.getCoTypeId());
			coContentTemp.setCoType(coType);
			coContentTemp.setIntroduce(coContent.getIntroduce());
			coContentTemp.setSource(coContent.getSource());
			coContentTemp.setTitle(coContent.getTitle());
			coContentTemp.setWeight(coContent.getWeight());
			coContentTemp.setKeywords(coContent.getKeywords());
//			if(StringUtils.isEmpty(coContent.getCityId())){
//				coContentTemp.setCityId(null);
//			}else{
//				coContentTemp.setCityId(coContent.getCityId());
//			}
			
//			String url=PropertiesReader.getInstance().getConfigItem("websiteUrl")+COCONTENT_PAGEURL+coContentTemp.getId()+".do";
//			coContentTemp.setPregetUrl(url);
			
			if(null!=fileName && !fileName.isEmpty()){
				String timedate = FilePath.getDatetime()+"//";
				//返回图片名称（全文件名） ， 通过fileNameTemp截取32位
				String pathurl = FilePath.CO_CONTENT_UPLOAD_FILE_PATH.filePath+timedate;
				String filePathName=null;
				if(this.systemUpload.systemUpload(request, fileName, pathurl)==null){
				}else{
				 filePathName = this.systemUpload.systemUpload(request, fileName, pathurl); 
				}
				// 删除文件
				if(null!=filePathName && !filePathName.isEmpty()){
					this.systemUpload.systemDeleteFile(request,FilePath.CO_CONTENT_UPLOAD_FILE_PATH.filePath, coContentTemp.getFileUrl());
					coContentTemp.setFileUrl(timedate.replace("//", "/")+filePathName);
				}
			}
			this.modify(coContentTemp, auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			this.logManagementService.addLogManagement(auEmployee, ToolUtils.getHostAddress(), flag, LogModule.COCONTENT.toString(), LogFunction.COCONTENT_UPDATE.toString(),coContentTemp.toString());
		}
		return flag;
	}

	/**
	 * 删除内容
	 * 
	 * @param ids内容id
	 * @param auEmployee人员对象
	 * @return是否成功
	 */
	@Override
	public Boolean removeCoContents(String id, HttpServletRequest request, AuEmployee auEmployee) {
		Boolean flag = true;
		CoContent coContent = null;
		try {
			 coContent = (CoContent) this.getOne(id, CoContent.class);
			this.systemUpload.systemDeleteFile(request,FilePath.CO_CONTENT_UPLOAD_FILE_PATH.filePath, coContent.getFileUrl());
			this.removeByState(coContent);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			this.logManagementService.addLogManagement(auEmployee, ToolUtils.getHostAddress(), flag, LogModule.COCONTENT.toString(), LogFunction.COCONTENT_DELETE.toString(),coContent.toString());
		}
		return flag;
	}

	/**
	 * 查询所有的内容（分页）
	 * 
	 * @param pageParameter本页数据信息
	 * @return内容分页
	 */
	@Override
	public DataGrid findList(PageParameter pageParameter,AuEmployee auEmployee) {
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = "from CoContent x where x.state = 1 ";
		String companyId=auEmployee.getCompanyId();
		// 查询类型
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("coTypeId")
				&& pageParameter.getParaMap().get("coTypeId") != null
				&& !pageParameter.getParaMap().get("coTypeId").equals("")
				&& pageParameter.getParaMap().containsKey("coTypeId")) {
			map.put("coTypeId", pageParameter.getParaMap().get("coTypeId"));
			hql += " and x.coType.id=:coTypeId";
		}
		
		// 查询功能名称(模糊搜索)
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("title")
				&& pageParameter.getParaMap().get("title") != null
				&& !pageParameter.getParaMap().get("title").equals("")
				&& pageParameter.getParaMap().containsKey("title")
				&& pageParameter.getParaMap().containsKey("type")) {
			hql += " and x.title like:title";
			map.put("title", "%" + pageParameter.getParaMap().get("title")
					+ "%");
		}

		// 查询功能名称
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("title")
				&& pageParameter.getParaMap().get("title") != null
				&& !pageParameter.getParaMap().get("title").equals("")
				&& pageParameter.getParaMap().containsKey("title")
				&& !pageParameter.getParaMap().containsKey("type")) {
			hql += " and x.title =:title";
			map.put("title", pageParameter.getParaMap().get("title"));
		}

		// 开始时间搜索
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("time1")
				&& pageParameter.getParaMap().get("time1") != null
				&& !pageParameter.getParaMap().get("time1").equals("")
				&& pageParameter.getParaMap().containsKey("time1")) {
			hql += " and x.time >=:time1";
			String time = (String) pageParameter.getParaMap().get("time1");
			map.put("time1", DateUtil.format(time, "yyyy-MM-dd"));
		}

		// 结束时间搜索
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("time2")
				&& pageParameter.getParaMap().get("time2") != null
				&& !pageParameter.getParaMap().get("time2").equals("")
				&& pageParameter.getParaMap().containsKey("time2")) {
			hql += " and x.time <=:time2";
			Calendar c = Calendar.getInstance();
			String time = (String) pageParameter.getParaMap().get("time2");
			c.setTime(DateUtil.format(time, "yyyy-MM-dd"));
			c.add(Calendar.DATE, 1);
			Date date = c.getTime(); // 结果
			map.put("time2", date);
		}		
		if(StringUtils.isNotEmpty(companyId)&&!companyId.equals(Constant.COMPANYID)){
			hql+=" and x.companyid= :companyId";
			map.put("companyId",companyId);
		}
		hql += " order by x.weight desc";
		pageParameter.setParaMap(map);
		return this.baseDao.findByhql(hql, pageParameter);
	}

	/**
	 * 查询所有内容（集合）
	 * 
	 * @return内容集合
	 */
	@Override
	public List<CoContent> findList() {
		return this.baseDao.find("from CoContent x where x.state = 1 order by x.createDate desc");
	}

	/**
	 * 根据类型查找对应的内容（全）
	 * 
	 * @param id内容类型id
	 * @return内容结合
	 */
	@Override
	public List<CoContent> getCoContentByCoType(String id,AuEmployee auEmployee ) {
		Map<String, Object> map = new HashMap<String, Object>();
		String companyId=auEmployee.getCompanyId();
		String hql = "from CoContent x  where x.state = 1 ";
		if (StringCheck.stringCheck(id)) {
			map.put("coType", id);
			hql += "and x.coType.id=:coType";
		}
		if(StringUtils.isNotEmpty(companyId)&&!companyId.equals(Constant.COMPANYID)){
			hql+=" and x.companyid= :companyId";
			map.put("companyId",companyId);
		}
		hql += " order by x.createDate desc";
		return this.baseDao.find(hql, map);
	}

	/**
	 * 设置点击量
	 * 
	 * @param coContent内容
	 * @return是否成功
	 */
	@Override
	public Boolean modifyCoContentClick(CoContent coContent) {
		try {
			this.modify(coContent);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Boolean modifyNewsPage(String id) {
		Boolean flag=true;
		CoContent coContent = (CoContent) this.getOne(id, CoContent.class);
		try{
		String tempPath=PropertiesReader.getInstance().getConfigItem("staticRootPath");
		String url=PropertiesReader.getInstance().getConfigItem("websiteUrl")+COCONTENT_PAGEURL+coContent.getId()+".do";
		String filePath=StaticPge.htmlGenerator(coContent.getId(),url,false);
		 filePath=filePath.replace(tempPath,"");
		 dao.addOrModify(coContent);
		}catch(Exception e){
			flag=false;
			dao.addOrModify(coContent);
			e.printStackTrace();
			 
			}
		return flag;
	}
}