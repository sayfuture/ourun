package com.mxcx.erp.base.adaptor;


/**
 * 系统模块码表
 * @author zhangbin
 *
 */
public enum LogModule {
	
	/*会员管理模块*/
	MEMBER("会员模块","001",new LogFunction[]{
			LogFunction.MEMBER_UPDATE,LogFunction.MEMBER_DELETE,
			LogFunction.MEMBER_INITPWD,LogFunction.MEMBER_INITSCORE,LogFunction.MEMBER_VALIDATE
	}),
	/*会员管理模块*/
	
	/*会员级别管理模块*/
	MEMBER_LEVEL("会员模块","002",new LogFunction[]{
			LogFunction.MEMBER_LEVEL_CREATE,LogFunction.MEMBER_LEVEL_UPDATE,
			LogFunction.MEMBER_LEVEL_DELETE
	}),
	/*商户管理模块*/
	ENTERPRISE("商户模块","003",new LogFunction[]{
			LogFunction.ENTERPRESE_CREATE,LogFunction.ENTERPRESE_UPDATE,LogFunction.ENTERPRESE_DELETE,LogFunction.ENTERPRESE_UPDATE_RATE
	}),
	/*组管理模块*/
	AUTEAM("部门模块","004",new LogFunction[]{
			LogFunction.AUTEAM_CREATE,LogFunction.AUTEAM_UPDATE,LogFunction.AUTEAM_DELETE
	}),
	
	/*权限管理模块*/
	AUAUTHORITY("权限模块","005",new LogFunction[]{
			LogFunction.AUAUTHORITY_CREATE,LogFunction.AUAUTHORITY_UPDATE,LogFunction.AUAUTHORITY_DELETE
	}),
	/*人员管理模块*/
	AUEMPLOYEE("人员模块","006",new LogFunction[]{
			LogFunction.AUEMPLOYEE_CREATE,LogFunction.AUEMPLOYEE_UPDATE,LogFunction.AUEMPLOYEE_DELETE,LogFunction.AUEMPLOYEE_INITPWD
	}),
	
	/*人员管理模块*/
	AUEMPLOYEEAUTHORITY("人员权限模块","007",new LogFunction[]{
			LogFunction.AUEMPLOYEEAUTHORITY_CREATE
	}),
	
	/*角色管理模块*/
	AUPOSITION("角色模块","008",new LogFunction[]{
			LogFunction.AUPOSITION_CREATE,LogFunction.AUPOSITION_UPDATE,LogFunction.AUPOSITION_DELETE
	}),
	/*内容管理模块*/
	COCONTENT("内容模块","009",new LogFunction[]{
			LogFunction.COCONTENT_CREATE,LogFunction.COCONTENT_UPDATE,LogFunction.COCONTENT_DELETE
	}),
	
	AUBUTTON("按扭模块","010",new LogFunction[]{
			LogFunction.AUBUTTON_CREATE,LogFunction.AUBUTTON_UPDATE,
			LogFunction.AUBUTTON_DELETE
	}),
	GS_GOODS("商品模块","013",new LogFunction[]{
			LogFunction.GS_GOODS_CREATE,LogFunction.GS_GOODS_UPDATE,
			LogFunction.GS_GOODS_DELETE
	}),
	/*商品分类模块*/
	GS_GOODS_TYPE("商品分类模块","012",new LogFunction[]{
		LogFunction.GS_GOODS_TYPE_CREATE,LogFunction.GS_GOODS_TYPE_UPDATE,LogFunction.GS_GOODS_TYPE_DELETE,LogFunction.GS_GOODS_TYPE_HOT
	}),
	/*套餐图片模块*/
	GS_GOODS_PICTURE("商品图片模块","014",new LogFunction[]{
		LogFunction.GS_GOODS_PICTURE_CREATE,LogFunction.GS_GOODS_PICTURE_UPDATE,LogFunction.GS_GOODS_PICTURE_DELETE
	}),
	
	/**
	 * 商品属性名
	 */
	GS_PROP_NAME("商品属性名", "015", new LogFunction[] {
		LogFunction.GS_PROP_NAME_CREATE,
		LogFunction.GS_PROP_NAME_UPDATE,
		LogFunction.GS_PROP_NAME_DELETE
	}),
	/**
	 * 商品属性值
	 */
	GS_PROP_VALUE("商品属性值", "016", new LogFunction[] {
		LogFunction.GS_PROP_VALUE_CREATE,
		LogFunction.GS_PROP_VALUE_UPDATE,
		LogFunction.GS_PROP_VALUE_DELETE
	}),
	/**
	 * 商品属性关联
	 */
	GS_GOODS_PROP("商品属性关联", "017", new LogFunction[] {
		LogFunction.GS_GOODS_PROP_CREATE,
		LogFunction.GS_GOODS_PROP_UPDATE,
		LogFunction.GS_GOODS_PROP_DELETE
	}),

	/**
	 * 商品SKU
	 */
	GS_SKU("商品SKU", "018", new LogFunction[] {
		LogFunction.GS_SKU_CREATE,
		LogFunction.GS_SKU_UPDATE,
		LogFunction.GS_SKU_DELETE
	}),
	/**
	 * 收藏
	 */
	GS_FAVORITE("收藏", "019", new LogFunction[] {
		LogFunction.GS_FAVORITE_CREATE,
		LogFunction.GS_FAVORITE_UPDATE,
		LogFunction.GS_FAVORITE_DELETE
	}),
	/*套餐图片模块*/
	GS_GSKUPROP("绑定属性","020",new LogFunction[]{
		LogFunction.GS_GSKUPROP_BAND,LogFunction.GS_GSKUPROP_DELETE,LogFunction.GS_GSKUPROP_UPDATE
	}),
	CC_FREIGHT("运费模块","021",new LogFunction[]{
			LogFunction.CC_FREIGHT_CREATE,LogFunction.CC_FREIGHT_DELETE,
			LogFunction.CC_FREIGHT_UPDATE
	}),

	AUAUTHORITYPOSITION("角色模块","023",new LogFunction[]{
			LogFunction.AUAUTHORITYPOSITION_CREATE,LogFunction.AUAUTHORITYPOSITION_DELETE,LogFunction.AUAUTHORITYPOSITION_UPDATE
	}),
	AUMENU("菜单模块","024",new LogFunction[]{
			LogFunction.AUMENU_CREATE,LogFunction.AUMENU_DELETE,LogFunction.AUMENU_UPDATE
	}),
	COTYPE("内容分类模块","025",new LogFunction[]{
			LogFunction.COCONTENT_CREATE,LogFunction.COTYPE_DELETE,LogFunction.COTYPE_UPDATE
	}),
	GSGOODS("商品管理","026",new LogFunction[]{
			LogFunction.GSGOODS_CREATE,LogFunction.GSGOODS_DELETE,LogFunction.GSGOODS_UPDATE
	}),
	LOGMANAGEMENT("日志模块","027",new LogFunction[]{
			LogFunction.LOGMANAGEMENT_CREATE,LogFunction.LOGMANAGEMENT_DELETE,LogFunction.LOGMANAGEMENT_UPDATE
	}),
	SYSDICKEY("字典名称模块","061",new LogFunction[]{
			LogFunction.SYSDICKEY_CREATE,LogFunction.SYSDICKEY_UPDATE,
			LogFunction.SYSDICKEY_DELETE
	}),
	SYSDICVALUE("字典值模块","062",new LogFunction[]{
			LogFunction.SYSDICVALUE_CREATE,LogFunction.SYSDICVALUE_UPDATE,
			LogFunction.SYSDICVALUE_DELETE
	}),
	AUDEPTAUTHORITY("公司权限模块","065",new LogFunction[]{
			LogFunction.AUDEPTAUTHORITY_CREATE
	}),
	AUEMPLOYEEAUDEPT("人员公司模块","066",new LogFunction[]{
			LogFunction.AUEMPLOYEEAUDEPT_CREATE
	}),
	DICARD("设置活动模块","067",new LogFunction[]{
			LogFunction.DICARD_CREATE,LogFunction.DICARD_UPDATE,
			LogFunction.DICARD_DELETE
	}), 
	DIPROCESS("活动核销模块","068",new LogFunction[]{
			LogFunction.DIPROCESS_CREATE,LogFunction.DIPROCESS_UPDATE,
			LogFunction.DIPROCESS_DELETE
	}), 
	WECUSTOMER("微信客户模块","069",new LogFunction[]{
			LogFunction.WECUSTOMER_CREATE,LogFunction.WECUSTOMER_UPDATE,
			LogFunction.WECUSTOMER_DELETE
	}), DISENDRECODE("集客券发送记录模块","070",new LogFunction[]{
			LogFunction.DISENDRECODE_CREATE
	}),
	DIPROCESSRECORD("活动核销模块","070",new LogFunction[]{
			LogFunction.DIPROCESSRECORD_CREATE,LogFunction.DIPROCESSRECORD_UPDATE,
			LogFunction.DIPROCESSRECORD_DELETE
	}), 
	DIPERMANENT("长期二维码模块","071",new LogFunction[]{
			LogFunction.DIPERMANENT_CREATE,LogFunction.DIPERMANENT_UPDATE,
			LogFunction.DIPERMANENT_DELETE
	}), 
	;
	
	private String name;
	private String moduleNo;
	private LogFunction[] logFunctions;
	
    private LogModule(String name,String moduleNo,LogFunction[] logFunctions) {
        this.name = name;
        this.moduleNo = moduleNo;
        this.logFunctions = logFunctions;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    public String getModuleNo() {
		return moduleNo;
	}
    
    
    public static LogModule[] getMemberModule() {
    	return new LogModule[]{LogModule.MEMBER};
	}
    
    
    public static void main(String[] args) {
    	
    	/*
    	System.out.println(LogModule.MEMBER);
		
		for (LogModule string : LogModule.values()) {
			System.out.println(string.name());
		}*/
		
		/*System.out.println(LogModule.TEST.moduleNo);
		
		System.out.println(LogModule.valueOf("USER"));*/
    	
    	//System.out.println(LogModule.getMemberModule());
	}
}
