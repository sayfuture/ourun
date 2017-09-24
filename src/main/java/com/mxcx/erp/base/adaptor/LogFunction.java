package com.mxcx.erp.base.adaptor;


/**
 * 系统功能码表
 * @author 
 *
 */
public enum LogFunction {
	
	/*会员管理方法*/
	 MEMBER_UPDATE("会员修改",LogModule.MEMBER), MEMBER_DELETE("会员删除",LogModule.MEMBER),MEMBER_INITPWD("初始化会员密码",LogModule.MEMBER),
	 
	 SYSDICKEY_CREATE("字典名称添加",LogModule.MEMBER), SYSDICKEY_DELETE("字典名称删除",LogModule.MEMBER),SYSDICKEY_UPDATE("字典名称修改",LogModule.MEMBER),
	 
	 SYSDICVALUE_CREATE("字典值添加",LogModule.MEMBER), SYSDICVALUE_DELETE("字典值删除",LogModule.MEMBER),SYSDICVALUE_UPDATE("字典值修改",LogModule.MEMBER),
	 
	 PRBASEINFO_CREATE("项目楼盘添加",LogModule.MEMBER), PRBASEINFO_DELETE("项目楼盘删除",LogModule.MEMBER),PRBASEINFO_UPDATE("项目楼盘修改",LogModule.MEMBER),
	 
	 MEMBER_INITSCORE("初始化会员积分",LogModule.MEMBER),MEMBER_VALIDATE("启用/冻结用户",LogModule.MEMBER),
	 
	 /*会员级别方法*/
	 AUBUTTON_CREATE("按钮添加",LogModule.AUBUTTON),AUBUTTON_UPDATE("按钮修改",LogModule.AUBUTTON), AUBUTTON_DELETE("按钮删除",LogModule.AUBUTTON),
	 
	/*会员级别方法*/
	 MEMBER_LEVEL_CREATE("会员级别添加",LogModule.MEMBER),MEMBER_LEVEL_UPDATE("会员级别修改",LogModule.MEMBER), MEMBER_LEVEL_DELETE("会员级别删除",LogModule.MEMBER),
	
	/*商户管理方法*/
	ENTERPRESE_CREATE("商户创建",LogModule.ENTERPRISE), ENTERPRESE_UPDATE("商户修改",LogModule.ENTERPRISE), ENTERPRESE_DELETE("商户删除",LogModule.ENTERPRISE),ENTERPRESE_UPDATE_RATE("商户变更费率",LogModule.ENTERPRISE),

	/*组管理方法*/
	AUTEAM_CREATE("组创建",LogModule.AUTEAM), AUTEAM_UPDATE("组修改",LogModule.AUTEAM), AUTEAM_DELETE("组删除",LogModule.AUTEAM),

	/*权限管理方法*/
	AUAUTHORITY_CREATE("权限创建",LogModule.AUAUTHORITY), AUAUTHORITY_UPDATE("权限修改",LogModule.AUAUTHORITY), AUAUTHORITY_DELETE("权限删除",LogModule.AUAUTHORITY),


	
	/*人员管理方法*/
	AUEMPLOYEE_CREATE("人员创建",LogModule.AUEMPLOYEE), AUEMPLOYEE_UPDATE("人员修改",LogModule.AUEMPLOYEE), AUEMPLOYEE_DELETE("人员删除",LogModule.AUEMPLOYEE),AUEMPLOYEE_INITPWD("初始化用户密码",LogModule.AUEMPLOYEE),
	
	/*人员权限方法*/
	AUEMPLOYEEAUTHORITY_CREATE("人员权限创建",LogModule.AUEMPLOYEEAUTHORITY),
	
	
	/*角色管理方法*/
	AUPOSITION_CREATE("角色创建",LogModule.AUPOSITION), AUPOSITION_UPDATE("角色修改",LogModule.AUPOSITION), AUPOSITION_DELETE("角色删除",LogModule.AUPOSITION),

	/*内容管理方法*/
	COCONTENT_CREATE("内容创建",LogModule.COCONTENT), COCONTENT_UPDATE("内容修改",LogModule.COCONTENT), COCONTENT_DELETE("内容删除",LogModule.COCONTENT),

	/*商品分类方法*/
	GS_GOODS_TYPE_CREATE("商品分类创建", LogModule.GS_GOODS_TYPE),GS_GOODS_TYPE_UPDATE("商品分类修改", LogModule.GS_GOODS_TYPE),GS_GOODS_TYPE_DELETE("商品分类删除", LogModule.GS_GOODS_TYPE),GS_GOODS_TYPE_HOT("商品分类设置热门分类", LogModule.GS_GOODS_TYPE),
	
	GS_GOODS_CREATE("商品添加",LogModule.MEMBER), GS_GOODS_DELETE("商品删除",LogModule.MEMBER),GS_GOODS_UPDATE("商品修改",LogModule.MEMBER),
	/*商品 图片方法*/
	GS_GOODS_PICTURE_CREATE("套餐图片创建", LogModule.GS_GOODS_PICTURE),
	GS_GOODS_PICTURE_UPDATE("套餐图片更新", LogModule.GS_GOODS_PICTURE),
	GS_GOODS_PICTURE_DELETE("套餐图片删除", LogModule.GS_GOODS_PICTURE),
	
	
	GS_GSKUPROP_BAND("属性绑定创建", LogModule.GS_GSKUPROP),
	GS_GSKUPROP_DELETE("属性绑定删除", LogModule.GS_GSKUPROP),
	GS_GSKUPROP_UPDATE("属性绑定更新", LogModule.GS_GSKUPROP),
	
	/* 商品属性名 */
	GS_PROP_NAME_CREATE("商品属性名创建", LogModule.GS_PROP_NAME),
	GS_PROP_NAME_UPDATE("商品属性名修改", LogModule.GS_PROP_NAME),
	GS_PROP_NAME_DELETE("商品属性名删除", LogModule.GS_PROP_NAME),
	
	/* 商品属性值 */
	GS_PROP_VALUE_CREATE("商品属性值创建", LogModule.GS_PROP_VALUE),
	GS_PROP_VALUE_UPDATE("商品属性值修改", LogModule.GS_PROP_VALUE),
	GS_PROP_VALUE_DELETE("商品属性值删除", LogModule.GS_PROP_VALUE),
	
	/* 商品属性 */
	GS_GOODS_PROP_CREATE("商品属性创建", LogModule.GS_GOODS_PROP),
	GS_GOODS_PROP_UPDATE("商品属性修改", LogModule.GS_GOODS_PROP),
	GS_GOODS_PROP_DELETE("商品属性删除", LogModule.GS_GOODS_PROP),
	
	/* 商品SKU */
	GS_SKU_CREATE("商品SKU创建", LogModule.GS_SKU),
	GS_SKU_UPDATE("商品SKU修改", LogModule.GS_SKU),
	GS_SKU_DELETE("商品SKU删除", LogModule.GS_SKU),
	
	/* 收藏 */
	GS_FAVORITE_CREATE("收藏创建", LogModule.GS_FAVORITE),
	GS_FAVORITE_UPDATE("收藏修改", LogModule.GS_FAVORITE),
	GS_FAVORITE_DELETE("收藏删除", LogModule.GS_FAVORITE),
	
	/* 运费维护*/
	CC_FREIGHT_CREATE("运费添加",LogModule.CC_FREIGHT),
	CC_FREIGHT_DELETE("运费删除",LogModule.CC_FREIGHT),
	CC_FREIGHT_UPDATE("运费修改",LogModule.CC_FREIGHT),
	
	AUAUTHORITYPOSITION_CREATE("角色创建",LogModule.AUAUTHORITYPOSITION),
	AUAUTHORITYPOSITION_DELETE("角色删除",LogModule.AUAUTHORITYPOSITION),
	AUAUTHORITYPOSITION_UPDATE("角色修改",LogModule.AUAUTHORITYPOSITION),
	
	AUMENU_CREATE("菜单创建",LogModule.AUMENU),
	AUMENU_DELETE("菜单删除",LogModule.AUMENU),
	AUMENU_UPDATE("菜单修改",LogModule.AUMENU),
	
	COTYPE_CREATE("内容创建",LogModule.COTYPE),
	COTYPE_DELETE("内容删除",LogModule.COTYPE),
	COTYPE_UPDATE("内容修改",LogModule.COTYPE),
	
	GSGOODS_CREATE("商品创建",LogModule.GSGOODS),
	GSGOODS_DELETE("商品删除",LogModule.GSGOODS),
	GSGOODS_UPDATE("商品修改",LogModule.GSGOODS),
	
	LOGMANAGEMENT_CREATE("日志创建",LogModule.LOGMANAGEMENT),
	LOGMANAGEMENT_DELETE("日志删除",LogModule.LOGMANAGEMENT),
	LOGMANAGEMENT_UPDATE("日志修改",LogModule.LOGMANAGEMENT),
	
	 
	 AUDEPTAUTHORITY_CREATE("公司权限创建",LogModule.AUDEPTAUTHORITY), 
	 AUEMPLOYEEAUDEPT_CREATE("人员公司创建",LogModule.AUEMPLOYEEAUDEPT), 
	 DICARD_CREATE("活动创建",LogModule.DICARD),
	 DICARD_UPDATE("活动修改",LogModule.DICARD),
	 DICARD_DELETE("活动删除",LogModule.DICARD), 
	 DIPROCESS_CREATE("活动核销创建",LogModule.DIPROCESS), 
	 DIPROCESS_UPDATE("活动核销修改",LogModule.DIPROCESS),  
	 DIPROCESS_DELETE("活动核销删除",LogModule.DIPROCESS), 
	 WECUSTOMER_CREATE("微信用户创建",LogModule.WECUSTOMER), 
	 WECUSTOMER_UPDATE("微信用户修改",LogModule.WECUSTOMER), 
	 WECUSTOMER_DELETE("微信用户删除",LogModule.WECUSTOMER),
	 DISENDRECODE_CREATE("集客券发送创建",LogModule.WECUSTOMER), 
	 DIPROCESSRECORD_CREATE("集客券使用创建",LogModule.DIPROCESSRECORD),
	 DIPROCESSRECORD_UPDATE("集客券使用更新",LogModule.DIPROCESSRECORD),
	 DIPROCESSRECORD_DELETE("集客券使用删除",LogModule.DIPROCESSRECORD), 
	 DIPERMANENT_CREATE("长期二维码创建",LogModule.DIPERMANENT),
	 DIPERMANENT_UPDATE("长期二维码修改",LogModule.DIPERMANENT),
	 DIPERMANENT_DELETE("长期二维码删除",LogModule.DIPERMANENT),
	;
	
 	public String name;
	public LogModule logModule;
	
	private LogFunction(String name,LogModule logModule) {
		this.name = name;
		this.logModule = logModule;
	}
	
	 @Override
    public String toString() {
        return this.name;
    }
}
