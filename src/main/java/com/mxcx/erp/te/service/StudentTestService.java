package com.mxcx.erp.te.service;

import java.util.List;

import com.mxcx.erp.te.dao.entity.StudentTest;

public interface StudentTestService {

	/** 
     * 新增 
     * <br>------------------------------<br> 
     * @param user 
     * @return 
     */  
    boolean add(StudentTest studentTest);  
      
    /** 
     * 批量新增 使用pipeline方式 
     * <br>------------------------------<br> 
     * @param list 
     * @return 
     */  
    boolean add(List<StudentTest> list);  
      
    /** 
     * 删除 
     * <br>------------------------------<br> 
     * @param key 
     */  
    void delete(String key);  
      
    /** 
     * 删除多个 
     * <br>------------------------------<br> 
     * @param keys 
     */  
    void delete(List<String> keys);  
      
    /** 
     * 修改 
     * <br>------------------------------<br> 
     * @param user 
     * @return  
     */  
    boolean update(StudentTest studentTest);  
  
    /** 
     * 通过key获取 
     * <br>------------------------------<br> 
     * @param keyId 
     * @return  
     */  
    StudentTest get(String keyId);  
	
	
}
