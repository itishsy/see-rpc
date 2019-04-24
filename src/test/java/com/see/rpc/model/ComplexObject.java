package com.see.rpc.model;

import java.io.Serializable;

public class ComplexObject implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6821030944861062296L;

	private String id;
	
	private Double age;
	
	public ComplexObject() {
		super();
	}

	public ComplexObject(String id, Double age) {
		super();
		this.id = id;
		this.age = age;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getAge() {
		return age;
	}

	public void setAge(Double age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "ComplexObject [id=" + id + ", age=" + age + "]";
	}
	
}
