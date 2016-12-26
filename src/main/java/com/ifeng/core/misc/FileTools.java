package com.ifeng.core.misc;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * <title>FileTools </title>
 * 
 * <pre>
 * 文件访问相关的工具类接口.
 * </pre>
 * 
 * Copyright © 2011 Phoenix New Media Limited All Rights Reserved.
 */

public class FileTools {
	/**
	 * 连接基础URL和文件名，返回结果URL
	 * 
	 * @param baseURL
	 *            基础URL，不允许null
	 * @param value
	 *            文件名，相对于基础URL
	 * @return 结果URL
	 */
	public static URL combineURL(URL baseURL, String value)
			throws MalformedURLException {
		String baseURLFile = baseURL.getFile();
		if (!baseURLFile.endsWith("/")) {
			baseURLFile += '/';
		}
		return new URL(baseURL.getProtocol(), baseURL.getHost(),
				baseURL.getPort(), baseURLFile + value);
	}

	/**
	 * 对指定的文件名，返回一个规范的文件名表示方式，包括： 1. 将相对路径变成绝对路径 2. 将'\'替换为'/'
	 * 
	 * @param file
	 *            文件名
	 * @return 规范的文件名
	 */
	public static String formatFilePath(String file) {
		if (file == null) {
			return null;
		}

		return new File(file).getAbsolutePath().replace('\\', '/');
	}

	/**
	 * 根据带路径的文件名，获得路径名. 基本按照Unix的dirname的规则，但有一处不同，对于以'/'或'\\'结尾的路径名
	 * 如"/opt/"，Unix的basename返回"/"，这里返回"/opt"
	 * 
	 * @param fileName
	 *            带路径的文件名.
	 * @return 不带文件名的路径名，除为根路径的情况外，不含结尾的 "/".
	 */
	public static String dirName(String fileName) {
		return dirName(fileName, false);
	}

	/**
	 * 根据带路径的文件名，获得路径名. 基本按照Unix的dirname的规则，但有一处不同，对于以'/'或'\\'结尾的路径名
	 * 如"/opt/"，Unix的basename返回"/"，这里返回"/opt"
	 * 
	 * @param fileName
	 *            带路径的文件名.
	 * @param preserveTrailingSlash
	 *            是否保留结尾的斜线。(即使是false，当结果为根路径时， 结尾仍然可能是斜线)
	 * @return 不带文件名的路径名.
	 */
	public static String dirName(String fileName, boolean preserveTrailingSlash) {
		if (StringUtils.isEmpty(fileName)) {
			return ".";
		}
		int index = fileName.lastIndexOf('/');
		if (index == -1) {
			index = fileName.lastIndexOf('\\');
		}
		switch (index) {
		case 0:
			return "/";
		case -1:
			return ".";
		default:
			if (preserveTrailingSlash) {
				return fileName.substring(0, index + 1);
			}
			return fileName.substring(0, index);
		}
	}

	/**
	 * 得到一个文件名的“基本文件名部分”，将前面的路径部分去掉.
	 * 基本按照Unix的basename的规则，但有一处不同，对于以'/'或'\\'结尾的路径名
	 * 如"/opt/"，Unix的basename返回"opt"，这里返回""
	 * 
	 * @param fileName
	 *            （可能）带路径的文件名
	 * @return 不带路径的文件名，不含"/"
	 */
	public static String baseName(String fileName) {
		if (StringUtils.isEmpty(fileName)) {
			return "";
		}
		int index = fileName.lastIndexOf('/');
		if (index == -1) {
			index = fileName.lastIndexOf('\\');
		}
		// if index == -1, index + 1 will be 0
		return fileName.substring(index + 1);
	}

	/**
	 * 判断一个文件名或路径名是否为绝对文件名或路径名
	 * 
	 * @param path
	 *            文件名
	 * @return true 如果是绝对路径名
	 */
	public static boolean isAbsolutePath(String path) {
		return new File(path).isAbsolute();
	}

	public static BufferedWriter getBufferedWriter(File f) throws IOException {
		try {
			return new BufferedWriter(new FileWriter(f));
		} catch (IOException e1) {
			throw e1;
		}
	}

	public static BufferedReader getInputStream(String filePath)
			throws FileNotFoundException {
		File readFile = new File(filePath);
		try {
			return new BufferedReader(new FileReader(readFile));
		} catch (FileNotFoundException e) {
			throw e;
		}
	}

	public static PrintStream getOutputStream(boolean newFile, String path)
			throws FileNotFoundException {
		if (newFile) {
			File outPutFile = new File(path);

		}
		try {
			PrintStream out = new PrintStream(new FileOutputStream(path, true));

			return out;
		} catch (FileNotFoundException e) {
			throw e;
		}
	}
}
