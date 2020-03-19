package com.hrishi.restspringproject.springrestservice.beans;

public class HelloWorldBean {

	String mesasge;
	
	public HelloWorldBean(String msg) {
		this.mesasge = msg;
	}

	@Override
	public String toString() {
		return "HelloWorldBean [mesasge=" + mesasge + "]";
	}


	public String getMesasge() {
		return mesasge;
	}

	public void setMesasge(String mesasge) {
		this.mesasge = mesasge;
	}

}