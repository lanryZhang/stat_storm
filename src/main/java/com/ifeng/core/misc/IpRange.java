package com.ifeng.core.misc;


import com.ifeng.core.misc.RangeSet.Range;

import java.io.*;
import java.util.List;
import java.util.StringTokenizer;

/**
 * <title> IpRange</title>
 * 
 * <pre>
 * ip范围查询接口的实现类，实现了根据ip地址得到对应的区域的能力。<br>
 * </pre>
 * 
 * Copyright © 2012 Phoenix New Media Limited All Rights Reserved.
 */
public class IpRange implements Serializable{
	//查询以来的平衡二叉树，具体的构造方式参见com.ifeng.common.misc.RangeSet
	private  RangeSet ipRangeSet;

	/* (non-Javadoc)
	 * @see com.ifeng.ipserver.service.intf.IpRangeManager#getIpRangeSet()
	 */
	public  RangeSet getIpRangeSet() {
		return ipRangeSet;
	}
	//private static final Logger log = Logger.getLogger(IpRange.class);
	/* (non-Javadoc)
	 * @see com.ifeng.ipserver.service.intf.IpRangeManager#getAreaByIp(com.ifeng.common.misc.IpAddress)
	 */
	public  Area getAreaByIp(IpAddress ip) throws IpServerException {
		List areaList = ipRangeSet.inRanges(ip);
		if(areaList.size()==0){
			return null;
		}else if(areaList.size()==1){
			return ((Area)((Range)areaList.get(0)).getParam());
		}else{
			return ((Area)((Range)areaList.get(0)).getParam());
		}
	}

	private  String separator = "|";
	public  void initRangeSet() {
		if (ipRangeSet != null && ipRangeSet.size() > 0){
			return;
		}
		BufferedReader br = null;
		try {
			ipRangeSet = new RangeSet();
			InputStream inputStream = IpRange.class.getClassLoader().getResourceAsStream("ipArea.txt");
			InputStreamReader reader = new InputStreamReader(inputStream);
			br = new BufferedReader(reader);
			String line = br.readLine();
			while((line!=null)&&(line.trim().length()>0)){
				StringTokenizer st = new StringTokenizer(line,separator);
				if(st.countTokens()>=5){
					IpV4Address startIp = new IpV4Address(st.nextToken());
					IpV4Address endIp = new IpV4Address(st.nextToken());
					Area area = new Area();
					area.setNetName(st.nextToken());
					area.setProvince(st.nextToken());
					area.setCity(st.nextToken());
					RangeSet.Range range = new RangeSet.Range(startIp,endIp,area);
					ipRangeSet.add(range);
				}
				line = br.readLine();
			}
		} catch (FileNotFoundException e) {
		}catch (IOException e) {
		}catch(Exception e){
		}
		finally{
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
