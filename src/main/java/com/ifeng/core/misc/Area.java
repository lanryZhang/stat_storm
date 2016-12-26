package com.ifeng.core.misc;

import java.io.Serializable;

/**
 * <title> Area</title>
 * 
 * <pre>
 * 
 * 用于定义ipserver中的区域，包含运营商、省份、城市三个属性。
 */
public class Area implements Serializable{
	//运营商
	private String netName;
	//省份
	private String province;
	//城市
	private String city;

	public String getNetName() {
		return netName;
	}

	public void setNetName(String netName) {
		this.netName = netName;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Area))
			return false;
		Area o1 = (Area) o;
		return o1.netName.equals(this.netName)
				&& o1.province.equals(this.province)
				&& o1.city.equals(this.city);
	}
	
	@Override
	public int hashCode() {
		return this.netName.hashCode() * 31 + this.province.hashCode() * 13
				+ this.city.hashCode();
	}
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("netname is : ");
		sb.append(this.netName);
		sb.append(" province is : ");
		sb.append(this.province);
		sb.append(" city is : ");
		sb.append(this.city);
		return sb.toString();
	}
}
