package com.ifeng.core.misc;

/**
 *
 * 
 * <pre>
 * 本类是一个“插件”， 此插件用于集成到一个责任链中，完成更新ipRangeManager接口中对应存储地址段到区域之间映射关系的平衡二叉树的能力。
 * 本类的配置方式为：
 * &lt;... type="com.ifeng.ipserver.service.listener.plugin。IpRangeUpdaterPlugin"&gt;
 * 	 &lt;file-path .../&gt;
 * 	 &lt;iprange-manager .../&gt;
 * 	 &lt;separator .../&gt;
 * &lt/...&gt 
 *其中：
 *	file-path
 *		类型：String
 *		用途：用户声明一个本地文件存储的路径
 *  iprange-manager
 *		类型：IpRangeManager
 *		用途：IpRangeManager的接口实现，实现了查询ip地址对应的区域的能力
 *  separator
 *		类型：String
 *		用途：存储映射信息本地文件中，每行中的不同属性之间的分隔符
 * </pre>
 * 
 * Copyright © 2012 Phoenix New Media Limited All Rights Reserved.
 */
public class IpRangeInitializer {
	private static IpRange ipRange;

}
