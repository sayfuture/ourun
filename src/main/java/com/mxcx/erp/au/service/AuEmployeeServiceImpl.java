package com.mxcx.erp.au.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mxcx.ec.base.commons.dao.IBaseDao;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.util.Encrypt;
import com.mxcx.ec.base.commons.util.StringCheck;
import com.mxcx.ec.base.commons.util.ToolUtils;
import com.mxcx.ec.base.commons.util.UuidDitch;
import com.mxcx.erp.au.adaptor.AuHQL;
import com.mxcx.erp.au.adaptor.FunctionType;
import com.mxcx.erp.au.dao.entity.AuAuthority;
import com.mxcx.erp.au.dao.entity.AuAuthorityPosition;
import com.mxcx.erp.au.dao.entity.AuDept;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.au.dao.entity.AuEmployeeAuthority;
import com.mxcx.erp.au.dao.entity.AuEmployeeDept;
import com.mxcx.erp.au.dao.entity.AuMenu;
import com.mxcx.erp.au.dao.entity.AuPosition;
import com.mxcx.erp.base.adaptor.FilePath;
import com.mxcx.erp.base.adaptor.LogFunction;
import com.mxcx.erp.base.adaptor.LogModule;
import com.mxcx.erp.base.commons.service.BaseService;
import com.mxcx.erp.base.commons.service.ISystemUpload;
import com.mxcx.erp.lo.service.LogManagementService;
import com.mxcx.erp.utils.Constant;

/**
 * 人员业务层接口实现
 * 
 * @author 20140625
 * 
 */
@Service
public class AuEmployeeServiceImpl extends BaseService<AuEmployee> implements
		AuEmployeeService {
	@Autowired
	private IBaseDao<AuEmployee> AuEmployeeDao; // 人员持久层
	@Autowired
	private AuEmployeeAuthorityService auEmployeeAuthorityService; // 人员功能业务层接口
	@Autowired
	private AuAuthorityPositionService auAuthorityPositionService; // 角色、功能业务层
	@Autowired
	private LogManagementService logManagementService; // 日志管理业务层接口

	@Autowired
	private IBaseDao<AuEmployeeAuthority> employeeAuthorityDao; // 人员功能dao
	@Autowired
	private ISystemUpload systemUpload; // 上传
	/**
	 * 添加人员
	 * 
	 * @param AuEmployee人员对象
	 * @return是否成功
	 */
	@Override
	public Boolean addAuEmployee(AuEmployee auEmployee,
			AuEmployee auEmployeeTemp,HttpServletRequest request) throws Exception {
		Boolean flag = true;
		try {
			auEmployee.setPassword(Encrypt.md5(String.valueOf(111111)));
			auEmployee
					.setId(UuidDitch.getId(LogModule.AUEMPLOYEE.getModuleNo()));
			auEmployee.setState(1);
			String companyId = getUpAuEmployeeDept(auEmployee.getAuDept()
					.getId());
			AuDept company = new AuDept();
			company.setId(companyId);
			auEmployee.setCompany(company);
			auEmployee.setLoginCount(0);
			String timedate = FilePath.getDatetime()+"//";
			
			String pathurl = FilePath.SA_CONSULANT_UPLOAD_FILE_PATH.filePath+timedate;
			String filePathName=null;
			if(this.systemUpload.systemUpload(request, "shop_file_name", pathurl)==null){
				
			}else{
				filePathName= this.systemUpload.systemUpload(request, "shop_file_name", pathurl);
			
			}
			if(null!=filePathName && !filePathName.isEmpty()){
				auEmployee.setShop_file_name1(timedate.replace("//", "/")+filePathName);
			}
			auEmployee.setAppid(Constant.APPID);
			auEmployee.setAppsecret(Constant.APPSECRET);
			auEmployee.setWxname(Constant.WXNAME);
			this.addPo(auEmployee, auEmployeeTemp);

			// AuDept auDept =
			// (AuDept)this.getOne(auEmployee.getAuDept().getId(),AuDept.class);
			// auEmployeeTemp2.setAuDept(auDept);
			// auEmployeeTemp2.setCityId(auDept.getCityId());
			// 通过角色初始化人员权限
			// if (null != auEmployee.getAuPosition().getId()) {
			// List<AuAuthorityPosition> auAuthorityPositionList =
			// this.auAuthorityPositionService.findAuAuthorityPositionListByPosition(auEmployee.getAuPosition().getId());
			// if(null!=auAuthorityPositionList&&auAuthorityPositionList.size()>0){
			// for(AuAuthorityPosition aap:auAuthorityPositionList){
			// AuEmployeeAuthority aea = new AuEmployeeAuthority();
			// // aea.setAuAuthority(aap.getAuAuthority());
			// aea.setAuButton(aap.getAuButton());
			// aea.setAuEmployee(auEmployee);
			// aea.setId(UuidDitch.getId(LogModule.AUEMPLOYEEAUTHORITY.getModuleNo()));
			// this.employeeAuthorityDao.add(aea);
			// }
			// }
			// }
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			this.logManagementService.addLogManagement(auEmployeeTemp,
					ToolUtils.getHostAddress(), flag,
					LogModule.AUEMPLOYEE.toString(),
					LogFunction.AUEMPLOYEE_CREATE.toString(),
					auEmployee.toString());
		}
		return flag;
	}

	/**
	 * 删除人员
	 * 
	 * @param id人员Id
	 * @return是否成功
	 */
	public Boolean deleteAuEmployee(String id, AuEmployee employee) {
		AuEmployee auEmployee = null;
		Boolean flag = true;
		try {
			auEmployee = (AuEmployee) this.getOne(id, AuEmployee.class);
			this.removeByState(auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			this.logManagementService.addLogManagement(employee,
					ToolUtils.getHostAddress(), flag,
					LogModule.AUEMPLOYEE.toString(),
					LogFunction.AUEMPLOYEE_DELETE.toString(),
					auEmployee.toString());
		}
		return flag;
	}

	/**
	 * 修改人员
	 * 
	 * @param AuEmployee
	 *            人员对象
	 * @return是否成功
	 */
	@Override
	public Boolean modifyAuEmployee(AuEmployee auEmployee, String positionId,
			AuEmployee auEmployeeTemp, HttpServletRequest request) {
		Boolean flag = true;
		AuEmployee auEmployeeTemp2 = null;
		try {
			auEmployeeTemp2 = (AuEmployee) this.AuEmployeeDao.getById(
					AuEmployee.class, auEmployee.getId());
			auEmployeeTemp2.setAge(auEmployee.getAge());
			auEmployeeTemp2.setEmail(auEmployee.getEmail());
			auEmployeeTemp2.setRealName(auEmployee.getRealName());
			auEmployeeTemp2.setSex(auEmployee.getSex());
			auEmployeeTemp2.setTel(auEmployee.getTel());
			auEmployeeTemp2.setTel2(auEmployee.getTel2());
//			auEmployeeTemp2.setAppid(auEmployee.getAppid());
//			auEmployeeTemp2.setAppsecret(auEmployee.getAppsecret());
//			auEmployeeTemp2.setWxname(auEmployee.getWxname());
			auEmployeeTemp2.setAddress(auEmployee.getAddress());
			if (auEmployeeTemp2.getPositionList() != null) {
				Set positionlist = auEmployeeTemp2.getPositionList();
				positionlist.clear();

				if (StringCheck.stringCheck(positionId)) {
					String arr[] = positionId.split(",");
					Set<AuPosition> positionList = new HashSet<AuPosition>();
					for (int i = 0; i < arr.length; i++) {
						System.out.println(arr[i]);
						AuPosition an = (AuPosition) this.getOne(arr[i],
								AuPosition.class);
						positionList.add(an);
					}
					auEmployeeTemp2.setPositionList(positionList);
				}

			}
			AuDept auDept = (AuDept) this.getOne(
					auEmployee.getAuDept().getId(), AuDept.class);
			String companyId = getUpAuEmployeeDept(auEmployee.getAuDept()
					.getId());
			AuDept company = new AuDept();
			company.setId(companyId);
			auEmployeeTemp2.setAuDept(auDept);
//			auEmployeeTemp2.setCityId(auDept.getCityId());
			auEmployeeTemp2.setCompany(company);
				String timedate = FilePath.getDatetime()+"//";
				//返回图片名称（全文件名） ， 通过fileNameTemp截取32位
				String pathurl = FilePath.SA_CONSULANT_UPLOAD_FILE_PATH.filePath+timedate;
				String filePathName=null;
//				if(this.systemUpload.systemUpload(request, "shop_file_name", pathurl)==null){
//				}else{
				 filePathName = this.systemUpload.systemUpload(request, "shop_file_name", pathurl); 
//				}
				// 删除文件
				if(null!=filePathName && !filePathName.isEmpty()){
					this.systemUpload.systemDeleteFile(request,FilePath.SA_CONSULANT_UPLOAD_FILE_PATH.filePath, auEmployeeTemp2.getShop_file_name1());
					auEmployeeTemp2.setShop_file_name1(timedate.replace("//", "/")+filePathName);
				}
				auEmployeeTemp2.setShop_file_name1(timedate.replace("//", "/")+filePathName);
			this.modify(auEmployeeTemp2, auEmployeeTemp);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			this.logManagementService.addLogManagement(auEmployeeTemp,
					ToolUtils.getHostAddress(), flag,
					LogModule.AUEMPLOYEE.toString(),
					LogFunction.AUEMPLOYEE_UPDATE.toString(),
					auEmployeeTemp2.toString());
		}
		return flag;
	}

	/**
	 * 查询所有人员
	 * 
	 * @param pageParameter
	 *            本页数据信息
	 * @return人员分页
	 */
	@Override
	public DataGrid findList(PageParameter pageParameter, AuEmployee auEmployee) {

		String hql = "from AuEmployee x where x.state = 1";
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("realName")
				&& !pageParameter.getParaMap().get("realName").equals("")
				&& pageParameter.getParaMap().get("realName") != null) {
			hql += hql.indexOf("where") > -1 ? " and x.realName like:realName "
					: "where x.realName =:realName ";
			paraMap.put("realName",
					"%" + pageParameter.getParaMap().get("realName") + "%");
		}
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("companyId")
				&& !pageParameter.getParaMap().get("companyId").equals("")
				&& pageParameter.getParaMap().get("companyId") != null) {
			hql += hql.indexOf("where") > -1 ? " and x.company.id =:companyId "
					: "where x.companyId =:companyId ";
			paraMap.put("companyId", pageParameter.getParaMap()
					.get("companyId"));
		}
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("posId")
				&& !pageParameter.getParaMap().get("posId").equals("")
				&& pageParameter.getParaMap().get("posId") != null) {
			hql += " and x.auPosition.id =:posId ";
			paraMap.put("posId", pageParameter.getParaMap().get("posId"));
		}

		hql += " order by x.createDate desc";
		pageParameter.setParaMap(paraMap);
		DataGrid datagrid = this.AuEmployeeDao.findByhql(hql, pageParameter);
		List<AuEmployee> list = (List<AuEmployee>) datagrid.getRows();
		for (AuEmployee auEmployee1 : list) {
			Set<AuEmployeeDept> set = auEmployee1.getAuEmployeeDeptList();
			for (AuEmployeeDept auEmployeeDept : set) {
				auEmployeeDept.setAuEmployee(null);
			}
			auEmployee1.setAuEmployeeDeptList1(set);
		}
		return datagrid;

	}

	/**
	 * 用户登录名注册时验证
	 * 
	 * @param pageParameter
	 * @return人员分页
	 */
	@Override
	public DataGrid findLoginNameList(PageParameter pageParameter) {
		String hql = "from AuEmployee x where x.state = 1 ";
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("loginName")
				&& pageParameter.getParaMap().get("loginName") != null) {
			hql += " and x.loginName =:loginName  ";
		}
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("auPositionId")
				&& pageParameter.getParaMap().get("auPositionId") != null) {
			hql += " and x.auPosition.id =:auPositionId ";
		}
		hql += " order by x.createDate desc";
		return this.AuEmployeeDao.findByhql(hql, pageParameter);
	}

	/**
	 * 用户搜索
	 * 
	 * @param pageParameter
	 * @param auEmployee
	 * @return人员分页
	 */
	@Override
	public DataGrid findLoginName(PageParameter pageParameter,
			AuEmployee auEmployee) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String hql = "from AuEmployee x " +
		// "inner join fetch x.auPosition as po " +
				"inner join fetch x.auDept as au ";
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("realName")
				&& pageParameter.getParaMap().get("realName") != null) {
			hql += hql.indexOf("where") > -1 ? " and x.realName like:realName and x.state = 1"
					: "where x.realName like:realName and x.state = 1";
			paraMap.put("realName", "%"
					+ (String) pageParameter.getParaMap().get("realName") + "%");
			String posId = (String) pageParameter.getParaMap().get("posId");
			// if (null != posId && !posId.isEmpty() && !posId.equals("0")) {
			// hql += " and po.id=:posId";
			// paraMap.put("posId", posId);
			// }
			String deptId = (String) pageParameter.getParaMap().get("deptId");
			if (null != deptId && !deptId.isEmpty() && !deptId.equals("0")) {
				hql += " and au.id=:deptId";
				paraMap.put("deptId", deptId);
			}
			hql += " order by x.createDate desc";
			pageParameter.setParaMap(paraMap);
		}
		return this.AuEmployeeDao.findByhql(hql, pageParameter);
	}

	/**
	 * 初始化密码
	 * 
	 * @param id人员Id
	 * @return
	 */
	@Override
	public Boolean initPassword(String id, AuEmployee auEmployee) {
		Boolean flag = true;
		try {
			AuEmployee auEmployeeTemp = (AuEmployee) this.AuEmployeeDao
					.getById(AuEmployee.class, id);
			auEmployeeTemp.setPassword(Encrypt.md5(String.valueOf(111111)));
			this.modify(auEmployeeTemp, auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	/**
	 * 修改密码
	 * 
	 * @param password1新密码
	 * @param password2确认密码
	 * @return
	 */
	@Override
	public Boolean updatePassword(String id, String password1,
			String password2, AuEmployee auEmployee) {
		Boolean flag = true;
		try {
			AuEmployee auEmployeeTemp = (AuEmployee) this.AuEmployeeDao
					.getById(AuEmployee.class, id);
			auEmployeeTemp.setPassword(Encrypt.md5(password1));
			this.modify(auEmployeeTemp, auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	/**
	 * 登录
	 * 
	 * @param name用户名
	 * @param password密码
	 * @return人员
	 */
	@Transactional
	public AuEmployee login(String name, String password) {
		String hql = "select distinct(x) from AuEmployee x left join fetch x.auEmployeeAuthorityList where x.loginName=:name and x.password=:password and x.state= 1";

		Map<String, Object> map = new HashMap();
		map.put("name", name);
		map.put("password", Encrypt.md5(password));

		List<AuEmployee> auEmployeeList = AuEmployeeDao.find(hql, map);
		AuEmployee auEmployee = auEmployeeList.size() != 0 ? auEmployeeList
				.get(0) : null;
		if (auEmployee != null) {
			auEmployee.setCompanyId(auEmployee.getCompany().getId());
			auEmployee.setLastdate(new Date());
			System.out.println(auEmployee.getLoginCount() + 1);
			auEmployee.setLoginCount(auEmployee.getLoginCount() + 1);
			this.baseDao.modify(auEmployee);
		}

		/*
		 * 判断人是否存在
		 */
		// int bbb =
		// this.baseDao.executeSql("update GS_GOODS  t set  t.pv=900 where t.id='129201508221151366251906026' ");
		// int aaa =
		// this.baseDao1.executeSql("update GS_GOODS  t set  t.pv1=900 where t.id='129201510261308498806199026'");
		// String id=(String)pageParameter.getParaMap().get("id");
		// CpMmMdBean cp=new CpMmMdBean();
		// StringBuilder sql=new StringBuilder("select ");
		// sql.append("mm.nick_Name nickName,");
		// sql.append("mm.real_name realName,");
		// sql.append("mm.cellphone cellPhone,");
		// sql.append("ml.level_name as levelName,");
		// sql.append("mm.SCORE score,");
		// sql.append("mm.AVAILABILITY availability,");
		// sql.append("mm.JUDGE judge,");
		// sql.append("mm.REG_TIME regTime,");
		// sql.append("mm.LAST_LOGIN_TIME lastLoginTime,");
		// sql.append("mm.user_id as userId");
		// //创建时间，人
		// // sql.append("mm.CREATE_USER as ")
		// // sql.append("mm.CREATE_DATE as")
		//
		// sql.append(" from cp_order co,me_member mm, me_level ml where 1=1");
		// sql.append(" and mm.level_id=ml.id and co.user_id=mm.user_id  ");
		// sql.append(" order by mm.create_date desc");
		// List<CpMmMdBean>
		// list=this.baseDao1.findbatisBysql("find_mybatis_CpMmMdBean",
		// sql.toString());

		// // List<MeMember> list = dao1.find("from MeMember");
		// // System.out.println(list);
		// System.out.println(aaa);
		return auEmployee;
	}

	public String getUpAuEmployeeDept(String id) {
		AuDept auDept = null;
		for (int i = 0; i <= 5; i++) {
			auDept = (AuDept) this.getOne(id, AuDept.class);
			if (auDept.getSuperAuDept() != null) {
				id = auDept.getSuperAuDept().getId();
			} else {
				return id;
			}
		}
		return "";

	}

	/**
	 * 根据用户查找菜单权限map
	 * 
	 * @param auEmployee人员
	 * @return
	 */
	@Override
	public Map<String, List<AuAuthority>> auMenuAuthority(
			AuEmployee auEmployee, List<AuAuthorityPosition> a) {
		Map<AuMenu, List<AuAuthority>> map = new HashMap<AuMenu, List<AuAuthority>>();
		Map<String, List<AuAuthority>> map2 = new HashMap<String, List<AuAuthority>>();
		// int aaa = a.get(1).getAuAuthority().getAuMenu().getState() ;
		// System.out.println(aaa);
		for (AuEmployeeAuthority ay : auEmployee.getAuEmployeeAuthorityList()) {
			if (null != ay.getAuAuthority().getAuMenu()
					&& ay.getAuAuthority().getAuMenu().getState() != 0) {
				// System.out.println(ay.getAuAuthority().getAuMenu().getName());
				map.put(ay.getAuAuthority().getAuMenu(),
						new ArrayList<AuAuthority>());
			}
		}
		for (AuAuthorityPosition ay : a) {
			if (null != ay.getAuAuthority().getAuMenu()
					&& ay.getAuAuthority().getAuMenu().getState() != 0) {
				// System.out.println(ay.getAuAuthority().getAuMenu().getName());
				map.put(ay.getAuAuthority().getAuMenu(),
						new ArrayList<AuAuthority>());
			}
		}
		for (AuEmployeeAuthority ay : auEmployee.getAuEmployeeAuthorityList()) {
			if (map.size() > 0) {
				for (AuMenu auMenu : map.keySet()) {
					if (null != ay.getAuAuthority().getAuMenu()) {
						if (ay.getAuAuthority().getAuMenu().getId()
								.equals(auMenu.getId())) {
							if (!map.get(auMenu).contains(ay.getAuAuthority())) {
								map.get(auMenu).add(ay.getAuAuthority());
							}
						}
					}
				}
			}
		}
		for (AuAuthorityPosition ay : a) {
			if (map.size() > 0) {
				for (AuMenu auMenu : map.keySet()) {
					if (null != ay.getAuAuthority().getAuMenu()) {
						if (ay.getAuAuthority().getAuMenu().getId()
								.equals(auMenu.getId())) {
							List<AuAuthority> au = map.get(auMenu);
							boolean b = false;
							for (int i = 0; i < au.size(); i++) {
								if (au.get(i).getName()
										.equals(ay.getAuAuthority().getName())) {
									b = true;
								}
							}
							if (b == false) {
								map.get(auMenu).add(ay.getAuAuthority());
							}
							// if
							// (!map.get(auMenu).contains(ay.getAuAuthority()))
							// {
							// System.out.println(!map.get(auMenu).contains(ay.getAuAuthority()));
							// System.out.println(auMenu.getName()+"   "+ay.getAuAuthority().getName());
							//
							// }
						}
					}
				}
			}
		}
		for (AuMenu auMenu : map.keySet()) {
			map2.put(auMenu.getName(), map.get(auMenu));
		}
		return map2;
	}

	/**
	 * 登录验证
	 * 
	 * @param name用户名
	 * @param password密码
	 * @return人员
	 */
	@Override
	public int loginCheck(String name, String password) {

		String hql = "select distinct(x) from AuEmployee x left join fetch x.auEmployeeAuthorityList where x.state=1 ";
		int type = 0; // 正常
		Map<String, Object> map = new HashMap();
		if (null != name && !name.isEmpty()) {
			hql += " and x.loginName=:name ";
			map.put("name", name);
			List<AuEmployee> auEmployeeList = AuEmployeeDao.find(hql, map);
			type = auEmployeeList.size() != 0 ? 0 : 3; // 3：名称有误
			if (type == 0) {
				AuEmployee auEmployee = auEmployeeList.get(0);
				if (null != password && !password.isEmpty()) {
					if (!auEmployee.getPassword().equals(Encrypt.md5(password))) {
						type = 4; // 4:密码有错
					}
				}
			}

		}
		return type;
	}

	/**
	 * 手机端登录
	 * 
	 * @param name登录名
	 * @param password密码
	 * @return人员对象
	 */
	@Override
	public AuEmployee loginToM(String name, String password) {
		String hql = "select distinct(x) from AuEmployee x inner join fetch x.auPosition inner join fetch x.auDept where x.loginName=:name and x.password=:password";

		Map<String, Object> map = new HashMap();
		map.put("name", name);
		map.put("password", password);

		List<AuEmployee> auEmployeeList = AuEmployeeDao.find(hql, map);
		/*
		 * 判断人是否存在
		 */
		return auEmployeeList.size() != 0 ? auEmployeeList.get(0) : null;
	}

	/**
	 * 根据工作组查询人员
	 * 
	 * @param audeptId工作组ID
	 * @return人员集合
	 */
	@Override
	public List<AuEmployee> findAuEmployeeListByDept(String audeptId) {
		String hql = "select distinct(x) from AuEmployee x inner join fetch x.auDept as am where am.id=:id and x.state= 1";
		hql += " order by x.createDate desc";
		Map<String, Object> map = new HashMap();
		map.put("id", audeptId);

		List<AuEmployee> auEmployeeList = AuEmployeeDao.find(hql, map);
		return auEmployeeList;
	}

	/**
	 * 传入dept字符串 1，2，3，4 得到对应的员工列表
	 * 
	 * @param audeptIds组Id
	 * @return人员集合
	 */
	@Override
	public List<AuEmployee> findAuEmployeeListByDepts(String audeptIds) {
		List<AuEmployee> listAll = new ArrayList<AuEmployee>();
		try {
			if (null != audeptIds && !"".equals(audeptIds.trim())) {
				String[] deptIdsArr = audeptIds.split(",");
				List<AuEmployee> list;
				for (String id : deptIdsArr) {
					list = this.findAuEmployeeListByDept(id);
					if (null != list && list.size() > 0) {
						listAll.addAll(list);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listAll;
	}

	/**
	 * 根据角色查询人员
	 * 
	 * @param positionId
	 * @return人员集合
	 */
	@Override
	public List<AuEmployee> findAuEmployeeListByPosition(String positionId) {
		String hql = "select distinct(x) from AuEmployee x inner join fetch x.auPosition as am where am.id=:id and x.state= 1";
		hql += " order by x.createDate desc";
		Map<String, Object> map = new HashMap();
		map.put("id", positionId);

		List<AuEmployee> auEmployeeList = AuEmployeeDao.find(hql, map);
		return auEmployeeList;
	}

	/**
	 * 通过id查找人员
	 */
	@Override
	public AuEmployee findAuEmployeeById(String id) {
		return AuEmployeeDao.get(AuEmployee.class, id);
	}

	/**
	 * 根据真实名查人员
	 */
	@Override
	public AuEmployee findAuEmployeeByRealName(String realName) {
		String hql = "from AuEmployee x where x.state = 1 and x.realName =:realName";
		Map<String, Object> paraMap = new HashMap();
		paraMap.put("realName", realName);
		return this.AuEmployeeDao.get(hql, paraMap);
	}

	/**
	 * 搜索用户
	 */
	@Override
	public DataGrid findAuEmployeeListByDeptForES(PageParameter pageParameter,
			String audeptId) {
		String hql = "from AuEmployee x inner join fetch x.auDept as am where am.id in ("
				+ audeptId + ") and x.state= 1";
		Map<String, Object> paraMap = new HashMap();
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("name")
				&& pageParameter.getParaMap().get("name") != null
				&& !"".equals(pageParameter.getParaMap().get("name"))) {
			hql += " and x.realName like:name";
			paraMap.put("name",
					"%" + (String) pageParameter.getParaMap().get("name") + "%");
			pageParameter.setParaMap(paraMap);
		} else {
			pageParameter.setParaMap(paraMap);
		}
		hql += " order by x.createDate desc";
		return AuEmployeeDao.findByhql(hql, pageParameter);
	}

	/**
	 * 当前用户用户管理的权限1：个人；2：组；3：全体；
	 * 
	 * @param auEmployee
	 * @returntype
	 */
	@Override
	public Integer checkType(AuEmployee auEmployee) {
		Integer type = AuHQL.getType(auEmployee, FunctionType.auEmployee);
		return type;
	}

	@Override
	public DataGrid findEmpCompanyList(PageParameter pageParameter,
			AuEmployee auEmployee) {
		Map<String, Object> paraMap = new HashMap();
		String hql = " from AuEmployee x     ";
		hql += "  inner join fetch  x.positionList as positions  where positions.id='"
				+ Constant.MONITORROLE + "' and x.state =1 ";
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("realName")
				&& pageParameter.getParaMap().get("realName") != null) {
			hql += hql.indexOf("where") > -1 ? " and x.realName like:realName and x.state = 1"
					: "where x.realName like:realName and x.state = 1";
			paraMap.put("realName", "%"
					+ (String) pageParameter.getParaMap().get("realName") + "%");
		}
		if (StringCheck.stringCheck(auEmployee.getCompanyId())) {
			hql += "and x.company.id=:companyId";
			paraMap.put("companyId", auEmployee.getCompanyId());
		}
		System.out.println(auEmployee.getCompanyId());

		hql += " order by x.createDate desc";
		pageParameter.setParaMap(paraMap);
		return this.AuEmployeeDao.findByhql(hql, pageParameter);
	}
	@Override
	public AuEmployee getAuEmployeeBywxname(String wxname){
		String hql="from AuEmployee a where a.state=1 and a.wxname='"+wxname+"'";
		List<AuEmployee> list=AuEmployeeDao.find(hql);
		if(list.size()>0)
			return list.get(0);
		return null;
	}

	@Override
	public AuEmployee findAuEmployeeBywxInfo(String appid, String secret) {
		String hql="from AuEmployee a where a.state=1 " ;
		if(StringUtils.isNotEmpty(appid)){
			hql+=" and a.appid='"+appid+"'";
		}
		if(StringUtils.isNotEmpty(secret)){
			hql+=" and a.appsecret='"+secret+"'";
		}
		List<AuEmployee> list=AuEmployeeDao.find(hql);
		if(list.size()>0)
			return list.get(0);
		return null;
		
	}
}