package com.see.rpc.model;

import java.io.Serializable;

@AnnoModelB(name="model super",value="model super")
public class Model implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8189769128202773323L;
	
	private static final String STATIC_FINAL = "this is model's static final value";
	
	private static String STATIC_ = "";
	
	@AnnoModelB(name="super class  aaa")
	private String a;
	
	@AnnoModelB(name="super class  bbbb")
	private String b;

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}
	
}
