package com.mxcx.erp.base.commons.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mxcx.ec.base.commons.util.PropertiesReader;

/**
 * @see(功能介绍):权限过滤器
 * @version(版本号): 1.0
 * @date(创建日期): 2014-9-2
 * @author 王森
 */
public class AuthorityFilter implements Filter{
	private static final String redirectURL = "/manager/main.do";
	private static Map<String, String> urlMap = new HashMap(AuthorityFilter.initMap()); // 免过滤列表
	
	/**
	 * 免过滤列表初始化
	 * @return map 免过滤列表
	 */
	private static Map<String, String> initMap(){
		Map<String, String> map = new HashMap();
		String path = PropertiesReader.getInstance().getConfigItem("rootPath");
		map.put(path+"/manager/main.do", null);
		map.put(path+"/manager/getCode.do", null);
		map.put(path+"/manager/loginAction.do", null);
		map.put(path+"/manager/androidLogin.do", null);
		map.put(path+"/manager/alipayapi/notify.do", null);
		return map;
	}
	
	@Override 
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;  
		HttpServletResponse rer = (HttpServletResponse) response;
		
		HttpSession session = req.getSession();
		
		String url = req.getRequestURI();
		if(url.contains("wechat")||url.contains("news")){
			filterChain.doFilter(request, response);
			return;
		}
		if (urlCheck(url)) {
			filterChain.doFilter(request, response);   
			return;  
		}
		if (session.getAttribute("auEmployee") == null) {
			rer.sendRedirect(req.getContextPath() + redirectURL);   
			return; 
		}
		
		filterChain.doFilter(request, response);   
		return;  
	}
	
	/**
	 * 校验URL列表
	 * @return boolean 是否进行过滤
	 */
	private boolean urlCheck(String url){
		boolean flag = false;
		if (AuthorityFilter.urlMap.containsKey(url)) { // 判断此URL是否进行过滤
			flag = true;
		}
		
		return flag;
	}
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
