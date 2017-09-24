package com.mxcx.erp.te;

public class Caller {
	private MyCallback myCallback;  
    
    public void doCall(){  
        myCallback.func();  
    }  
  
    public void setMyCallback(MyCallback myCallback) {  
        this.myCallback = myCallback;  
    }  
}
