package com.ifeng.utils;

import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;

public class HttpAttr {
	private String contentType;
	private String accept;
	private String userAgent;
	private Boolean isKeepAlive;
	private Boolean isUseCache;
	private Boolean isInstanceFollowRedirect;
	private int Timeout;
	private String referer;

	
	public HttpAttr() {
		this.contentType = "application/x-www-form-urlencoded";
		this.accept = "*/*";
		this.userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:15.0) Gecko/20100101 Firefox/15.0.1";
		this.isKeepAlive = true;
		this.isUseCache = false;
		this.isInstanceFollowRedirect = true;
		this.Timeout = 300000;
		this.referer = "";
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String useragent) {
		this.userAgent = useragent;
	}

	public Boolean getIsKeepAlive() {
		return isKeepAlive;
	}

	public void setIsKeepAlive(Boolean isKeepAlive) {
		this.isKeepAlive = isKeepAlive;
	}

	public Boolean getIsUseCache() {
		return isUseCache;
	}

	public void setIsUseCache(Boolean isUseCache) {
		this.isUseCache = isUseCache;
	}

	public Boolean getIsInstanceFollowRedirect() {
		return isInstanceFollowRedirect;
	}

	public void setIsInstanceFollowRedirect(Boolean isInstanceFollowRedirect) {
		this.isInstanceFollowRedirect = isInstanceFollowRedirect;
	}

	public int getTimeout() {
		return Timeout;
	}

	public void setTimeout(int timeout) {
		Timeout = timeout;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}


	public static HttpAttr getDefaultInstance() {
		return new HttpAttr();
	}
}
