package com.ifeng.core.query;


public class OrderBy {
	public String name;
	public OrderByDirection direction;
	
	public OrderBy(String name,OrderByDirection direction) {
		this.name= name;
		this.direction = direction;
	}
}
