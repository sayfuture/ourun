package com.mxcx.erp.te;

public class CallBackTest implements MyCallback{
	
	public static void main(String[] args) {  
        Caller caller = new Caller();  
        //实例化具体回调函数，实现回调方法  
        caller.setMyCallback(new MyCallback() {  
            @Override  
            public void func() {  
                System.out.println("Hello world!");  
            }  
        });  
          
        caller.doCall();  
    }

	@Override
	public void func() {
		// TODO Auto-generated method stub
		
	}  
}
