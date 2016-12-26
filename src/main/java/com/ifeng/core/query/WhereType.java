package com.ifeng.core.query;

public enum WhereType {
	Equal("$eq"),
	NotEqual("$ne"),
	GreaterThan("$gt"),
	LessThan("$lt"),
	GreaterAndEqual("$gte"), 
	LessAndEqual("$lte"),
	All("$all"),Not("$not"),In("$in"),
	NotIn("$nin");
	private String value;

	WhereType(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}
}
