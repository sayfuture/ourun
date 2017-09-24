package com.mxcx.erp.te.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class TestCache {
	private static TestCache testCache=null;
	
	Map<String,Object> map=new HashMap<String,Object>();
	
	public static TestCache getInstance(){
		if(testCache==null){
			testCache= new TestCache();
		}
		return testCache;
	}
	private TestCache(){
		//调用缓存内容
//		System.out.println("走一次@@@@@@@@@@@@@@@@@@@@@");
//		this.aaMap();
//		this.bbMap();
	} 
	 TestCache(String a){
//		System.out.println("调用我！！！！！！！！！！！！");
	}
	private void aaMap(){
		map.put("姓名", "张三");
		map.put("年龄", "666");
	}
	
	private void bbMap(){
		System.out.println(this.map.get("姓名"));
		System.out.println(this.map.get("年龄"));
	}
}
