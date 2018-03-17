package com.mxcx.erp.utils;

public class Constant {

	 
	public static String MONITORROLE = "3";
	
	public static String TOKEN="ourun2015";
	
	public static Integer USED_SIGN=2;

	public static Integer JSTICKET_TIME=1;//一小时
	
	/**
	 * 总公司ID
	 */
	public static final String  COMPANYID="100201601081053";//截取后半段  574386316004  原例：100201601081053574386316004
	/**
	 * 微信appid与secret
	 */
//	public static final String APPID="wx1a998ac595c7e6f4";
//	public static final String APPSECRET="c2d45bb1abbe76bce6ee6568c62ce218";
//	public static final String WXNAME="gh_7ced5e7bab26";
	public static final String APPID="wxcddc3ffbcdf9acf3";
	public static final String APPSECRET="ac3f6ae887ab4a45eae281c8b0db9c6b";
	public static final String WXNAME="gh_a02648605eb2";
	/**
	 * tokenURL
	 */
	public static final String TOKENURL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	/**
	 * jsapi_ticket
	 * GET
	 */
	public static final String JSAPI_TICKET="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	
	/**
	 * 临时二维码URL
	 */	
	public static final String QRCODE="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";

	/**
	 * 永久二维码URL
	 * http请求方式: POST
	 */	
	public static final String PERMANENTQRCODE="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKENPOST";
	
	/**
	 * 获取用户列表URL
	 * &next_openid=NEXT_OPENID用户超过10000时候再考虑
	 */	
	public static final String USERLIST="https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
	
	
	/**
	 * 通过openID获取用户详细信息
	 */
	public static final String USERINFO="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	/**
	 * 上传图文消息内的图片获取URL【订阅号与服务号认证后均可用】
	 * http请求方式: POST
	 */
	public static final String UPLOADIMAGE="https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";

	/**
	 * 上传图文消息素材【订阅号与服务号认证后均可用】
	 * http请求方式: POST
	 * curl -F media=@test.jpg
	 */
	public static final String UPLOADNEWS="https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";
	/**
	 * 图文封面图片上传接口地址
	 */
	public static final String POSTIMAGEMEDIAURL = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=image";

	/**
	 * 新增永久图文素材
	 * http请求方式: POST
	 */
	public static final String ADDNEWS="https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=ACCESS_TOKEN";

	/**
	 * 新增永久图片
	 */
	public static final String UPLOADIMG="http://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";

	/**
	 * 用户同意授权，获取code
	 */
	public static final String AUTHORIZE_CODE="https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
	
	/**
	 * 通过code换取网页授权access_token
	 */
	public static final String OAUTH_ACCESS_TOKEN="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

	/**
	 * 客服接口-发消息
	 * http请求方式: POST
	 */
	public static final String CUSTOMER_SEND="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

	/**
	 * 获取模板列表
	 * http请求方式：GET
	 */
	public static final String NEWS_TEMPLATE="https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=ACCESS_TOKEN";

	/**
	 * 发送模板消息
	 * http请求方式: POST
	 */
	public static final String SEND_NEWS_TEMPLATE="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";


	/**
	 * 根据OpenID列表群发【订阅号不可用，服务号认证后可用】
	 * http请求方式: POST
	 */
	public static final String SEND_OPENID="https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";


	/**
	 * 自定义菜单创建接口
	 * http请求方式: POST
	 */
	public static final String  CREATE_MENU="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	/**
	 * 自定义菜单删除接口
	 * http请求方式：GET
	 */
	public static final String DELETE_MENU="https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
}
