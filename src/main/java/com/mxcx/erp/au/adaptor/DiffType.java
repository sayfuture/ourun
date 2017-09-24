package com.mxcx.erp.au.adaptor;

public enum DiffType{
	
	PSBS(1,"平台成功，银行（微信支付）成功"),
	PSBF(2,"平台成功，银行（微信支付）失败"),	
	PFBS(3,"平台失败，银行（微信支付）成功"),	
	PFBF(4,"平台失败，银行（微信支付）失败"),	
	PEBN(5,"平台有记录，银行（微信支付）无记录"),	
	PNBE(6,"平台无记录，银行（微信支付）有记录");	
	
	private Integer type;
	private String CN;
	private String a;
	private DiffType(Integer type, String CN) {
		this.type = type;
		this.CN = CN;
	}
	
	public Integer getType() {
			return type;
	}
	
	public String getCN() {
		return CN;
	}
	
}