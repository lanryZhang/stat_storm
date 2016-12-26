package com.ifeng.utils;



public class HttpReturnData {
	private String data;
	private HttpAttr updatedAttr;
	private String encoding;
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public HttpAttr getUpdatedAttr() {
		return updatedAttr;
	}

	public void setUpdatedAttr(HttpAttr updatedAttr) {
		this.updatedAttr = updatedAttr;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	
	public HttpReturnData(String data, HttpAttr attr, String encoding) {  
        
		this.data = data;  
        this.updatedAttr = attr;  
        this.encoding = encoding;  
    } 
	
	public byte[] getReturnDataBytes() {
		try {
			return this.data.getBytes();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
