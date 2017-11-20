package com.mxcx.erp.me.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.base.adaptor.LogFunction;
import com.mxcx.erp.base.adaptor.LogModule;
import com.mxcx.erp.base.commons.service.BaseService;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.dao.IBaseDao;
import com.mxcx.ec.base.commons.util.Encrypt;
import com.mxcx.ec.base.commons.util.StringCheck;
import com.mxcx.ec.base.commons.util.StringUtil;
import com.mxcx.ec.base.commons.util.ToolUtils;
import com.mxcx.ec.base.commons.util.UuidDitch;
import com.mxcx.erp.lo.service.LogManagementService;
import com.mxcx.erp.me.dao.entity.MeMember;
import com.mxcx.erp.utils.Constant;

/**
 * 会员业务层接口实现
 * 
 * @author  20140909
 * 
 */
@Service
public class MeMemberServiceImpl extends BaseService<MeMember> implements
		IMeMemberService {

	/**
	 * 会员DAO
	 */
	@Autowired
	private IBaseDao<MeMember> memberDao;

	/**
	 * 系统日志服务
	 */
	@Autowired
	private LogManagementService logManagementService;
	

	/**
	 * @see com.mxcx.erp.me.service.IMeMemberService#deleteMember(String id,
	 *      AuEmployee auEmployee)
	 */
	@Override
	public Boolean deleteMember(String id, AuEmployee auEmployee) {
		MeMember member = null;
		Boolean flag = true;
		try {
			member = (MeMember) this.getOne(id, MeMember.class);
			flag = removeByState(member);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee,ToolUtils.getHostAddress(), flag,LogModule.MEMBER.toString(),LogFunction.MEMBER_DELETE.toString(),member.toString());
		}
		return flag;
	}

	/**
	 * @see com.mxcx.erp.me.service.IMeMemberService#modifyMember(MeMember
	 *      member, AuEmployee auEmployee)
	 */
	@Override
	public Boolean modifyMember(MeMember member, AuEmployee auEmployee) {
		MeMember oriMember = findMemerByID(member.getId());
		if(member.getSex()!=null)
			oriMember.setSex(member.getSex());
		oriMember.setBirthday(member.getBirthday());
		oriMember.setEmail(member.getEmail());
		oriMember.setCellphone(member.getCellphone());
		oriMember.setRealName(member.getRealName());
		oriMember.setIdentity(member.getIdentity());
		oriMember.setMaritalState(member.getMaritalState());
		oriMember.setInterest(member.getInterest());
		oriMember.setUser_id(member.getUser_id());
		Boolean flag = true;
		try {
			
			flag = modify(oriMember, auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee,ToolUtils.getHostAddress(), flag,LogModule.MEMBER.toString(),LogFunction.MEMBER_UPDATE.toString(),member.toString());
		}
		return flag;
	}

	@Override
	public DataGrid findMemberList(PageParameter pageParameter) {
//		StringBuilder sql=new StringBuilder("select x.id,x.RECONNEND_NO as reconnendNo,x.REAL_NAME as realName,x.sex,x.birthday,x.identity,");
//		sql.append("x.MARITAL_STATE as maritalState,x.activity,x.availability,x.score,x.registerMethod,x.validationMethod,x.CELLPHONE_BINDING as cellphoneBinding");
//		sql.append("t.pvsub")
		try{
		String hql = "from MeMember x  where x.state = 1   order by x.createDate desc";
		DataGrid a = memberDao.findByhql(hql, pageParameter);
		return a;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @see com.mxcx.erp.me.service.IMeMemberService#findMemerByID(String id)
	 */
	@Override
	public MeMember findMemerByID(String id) {
		return (MeMember) getOne(id, MeMember.class);
	}
	@Override
	public MeMember findMemerByUserId(String user_id) {
		String hql="from MeMember mm where mm.state=1 and mm.user_id="+user_id;
		List<MeMember> list=memberDao.find(hql);
		if(list.size()>0)
			return list.get(0);
		else
		return null;
	}
	/**
	 * 日志查询
	 */
	@Override
	public MeMember findMemberByRealName(String realName, String nickName) {
		String hql = "from MeMember x where x.state = 1 and x.realName =:realName";
		Map<String, Object> paraMap = new HashMap();
		if (realName != null) {
			paraMap.put("realName", realName);
			hql += " and x.realName =:realName";
		}
		if (nickName != null) {
			hql += " and x.nickName =:nickName";
			paraMap.put("nickName", nickName);
		}
		return this.memberDao.get(hql, paraMap);
	}

	/**
	 * @see com.mxcx.erp.me.service.IMeMemberService#initPassword(String id,
	 *      AuEmployee auEmployee)
	 */
	@Override
	public Boolean initPassword(String id, AuEmployee auEmployee) {
		Boolean flag = true;
		MeMember member  =null;
		try {
			member = findMemerByID(id);
			member.setPassword(Encrypt.md5(String.valueOf(111111)));
			flag = modify(member, auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee,
					ToolUtils.getHostAddress(), flag,
					LogModule.MEMBER.toString(),
					LogFunction.MEMBER_INITPWD.toString(),member.toString());
		}
		return flag;
	}

	/**
	 * @see com.mxcx.erp.me.service.IMeMemberService#initScore(String id,
	 *      AuEmployee auEmployee)
	 */
	@Override
	public Boolean initScore(String id, AuEmployee auEmployee) {
		Boolean flag = true;
		MeMember member = null;
		try {
			member = findMemerByID(id);
			member.setScore(20);
			flag = modify(member, auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee,ToolUtils.getHostAddress(), flag,LogModule.MEMBER.toString(),LogFunction.MEMBER_INITSCORE.toString(),member.toString());
		}
		return flag;
	}

	/**
	 * @see PageParameter
	 *      pageParameter)
	 */
	@Override
	public DataGrid findMemberByConditionGroup(PageParameter pageParameter,AuEmployee auEmployee) {
		// 拼接HQL
		StringBuffer hql = new StringBuffer(
				"from MeMember x  where x.state = 1 ");
		Map<String, Object> paraMap = new HashMap<String, Object>();

		String loginName = (String) pageParameter.getParaMap().get("loginName");
		String realName = (String) pageParameter.getParaMap().get("realName");
		String cellphone = (String) pageParameter.getParaMap().get("cellphone");
//		String isDealer =(String)pageParameter.getParaMap().get("companyId");
		String availability = (String) pageParameter.getParaMap().get("availability");
		String companyId=auEmployee.getCompanyId();
		// 封装查询参数，如果为空就不拼接
		if (StringCheck.stringCheck(loginName)) {
			hql.append(" and x.loginName like :loginName ");
			paraMap.put("loginName", "%" + loginName + "%");
		}
		if (StringUtils.isNotEmpty(realName)) {
			hql.append(" and x.realName like :realName ");
			paraMap.put("realName", "%" + realName + "%");
		}
		if (StringUtils.isNotEmpty(cellphone)) {
			hql.append(" and x.cellphone like :cellphone ");
			paraMap.put("cellphone", "%" + cellphone + "%");
		}


		if (StringUtils.isNotEmpty(availability) && !availability.equals("-1")) {
			hql.append(" and x.availability = :availability ");
			paraMap.put("availability", Integer.valueOf(availability));
		}
		if(StringUtils.isNotEmpty(companyId)&&!companyId.equals(Constant.COMPANYID)){
			hql.append(" and x.companyId = :companyId");
			paraMap.put("companyId", companyId);
		}
		// 按日期倒叙排
		hql.append(" order by x.createDate desc");
		pageParameter.setParaMap(paraMap);
		return memberDao.findByhql(hql.toString(), pageParameter);
	}

	@Override
	public Boolean validateMemer(String id, AuEmployee auEmployee) {
		Boolean flag = true;
		MeMember member = null;
		try {
			 member = findMemerByID(id);
			if (member.getAvailability() == 1) {
				member.setAvailability(0);
			} else {
				member.setAvailability(1);
			}
			flag = modify(member, auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee,ToolUtils.getHostAddress(), flag,LogModule.MEMBER.toString(),LogFunction.MEMBER_VALIDATE.toString(),member.toString());
		}
		return flag;
	}

	
	/**
	 * @see com.mxcx.erp.me.service.IMeMemberService#findMemberByConditionGroup(String
	 *      loginName,String realName, String email,String activity,String
	 *      availability,String cellphone,String level)
	 */
	@Override
	public List<MeMember> findMemberByConditionGroup(String nickName,
			String realName, String email, String activity,
			String availability, String cellphone, String level) {

		// 拼接HQL
		StringBuffer hql = new StringBuffer(
				"from MeMember x  where x.state = 1");

		// 封装查询参数，如果为空就不拼接
		Map<String, Object> paraMap = new HashMap();
		if (StringCheck.stringCheck(nickName)) {
			hql.append(" and am.nickName like :nickName ");
			paraMap.put("nickName", "%" + nickName + "%");
		}
		if (StringCheck.stringCheck(realName)) {
			hql.append(" and am.realName like :realName ");
			paraMap.put("realName", "%" + realName + "%");
		}
		if (StringCheck.stringCheck(email)) {
			hql.append(" and am.email like :email ");
			paraMap.put("email", "%" + email + "%");
		}
		if (StringCheck.stringCheck(cellphone)) {
			hql.append(" and am.cellphone like :cellphone ");
			paraMap.put("cellphone", "%" + cellphone + "%");
		}
		if (StringCheck.stringCheck(activity) && !activity.equals("-1")) {
			hql.append(" and am.activity = :activity ");
			paraMap.put("activity", Integer.valueOf(activity));
		}

		if (StringCheck.stringCheck(availability) && !availability.equals("-1")) {
			hql.append(" and am.availability = :availability ");
			paraMap.put("availability", Integer.valueOf(availability));
		}

		if (StringCheck.stringCheck(level) && !level.equals("-1")) {
			hql.append(" and (am.level.id = :levelID)");
			paraMap.put("levelID", level);
		}
		// 按日期倒叙排
		hql.append(" order by am.createDate desc");
		return memberDao.find(hql.toString(), paraMap);
	}
	
	/**
	 * @see com.mxcx.erp.me.service.IMeMemberService#isFullInfo(MeMember member)
	 */
	@Override
	public Boolean isFullInfo(MeMember member) {

		//用户名
		if(StringUtil.isEmpty(member.getNickName())){
			return false;
		}
		//真实名
		if(StringUtil.isEmpty(member.getRealName())){
			return false;
		}
		//性别
		if(member.getSex() == null){
			return false;
		}
		//生日
		if(member.getBirthday() == null){
			return false;
		}
		//手机
		if(StringUtil.isEmpty(member.getCellphone())){
			return false;
		}
		//身份
		if(member.getIdentity() == null){
			return false;
		}
		//个人情况
		if(member.getMaritalState() == null){
			return false;
		}
		//兴趣 
		if(StringUtil.isEmpty(member.getInterest())){
			return false;
		}
		//邮箱
		if(StringUtil.isEmpty(member.getEmail())){
			return false;
		}
		//激活状态
		if(member.getActivity().intValue() == 0){
			return false;
		}
		//头像路径
		if(StringUtil.isEmpty(member.getHeadShowPath())){
			return false;
		}
		//邮箱绑定
		if(member.getActivity().intValue() == 0){
			return false;
		}
		//手机绑定
		if(member.getActivity().intValue() == 0){
			return false;
		}
		
		return true;
	}

	@Override
	public Boolean bindCard(MeMember member) {
		Boolean flag=true;
		try{
			MeMember memberPersistent=(MeMember) this.getOne(member.getId(), MeMember.class);
			memberPersistent.setDiCard(member.getDiCard());
			memberPersistent.setDiCard1(member.getDiCard1());
			memberPersistent.setGet_limit(member.getGet_limit());
			memberPersistent.setGet_limit1(member.getGet_limit1());
			memberPersistent.setUsed_num(member.getUsed_num());
			memberPersistent.setUsed_num1(member.getUsed_num1());
			memberDao.addOrModify(memberPersistent);
		}catch(Exception e){
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Boolean addMember(MeMember member, AuEmployee loginUser) {
			Boolean flag=false;
		try{
			member.setId(UuidDitch.getId(LogModule.MEMBER.getModuleNo()));
			member.setCompanyId(loginUser.getCompanyId());
			flag = addPo(member, loginUser);
		}catch(Exception e){
			flag=true;
			e.printStackTrace();
		}
		return flag;
	}
}