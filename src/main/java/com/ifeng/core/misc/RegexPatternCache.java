package com.ifeng.core.misc;

import org.apache.commons.collections.map.LRUMap;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式的cache。提高正则表达式的性能 不支持Pattern额外的flags，可以把flags用(?...)的方式放在字符串中
 * 
 * @author jinmy
 */
public final class RegexPatternCache {

	public static final int DEFAULT_CACHE_SIZE = 128;

	private static Map patterns = new LRUMap(DEFAULT_CACHE_SIZE);

	private RegexPatternCache() {
		// utility class
	}

	/**
	 * 改变cache size
	 */
	public static synchronized void setCacheSize(int size) {
		Map newPatterns = new LRUMap(size);
		newPatterns.putAll(patterns);
		patterns = newPatterns;
	}

	/**
	 * 根据一个字符串得到pattern。先从cache中取得，如果cache中没有
	 * 
	 * @param strPattern
	 */
	public static synchronized Pattern getPattern(String strPattern) {
		Pattern pattern = (Pattern) patterns.get(strPattern);
		if (pattern == null) {
			pattern = Pattern.compile(strPattern);
			patterns.put(strPattern, pattern);
		}
		return pattern;
	}

	/**
	 * 用这个方法，代替Pattern.matches
	 */
	public static boolean matches(String strPattern, CharSequence text) {
		return getPattern(strPattern).matcher(text).matches();
	}

	/**
	 * 得到一个Matcher，可以做replace等操作
	 */
	public static Matcher matcher(String strPattern, CharSequence text) {
		return getPattern(strPattern).matcher(text);
	}

	private static final String[] EMPTY_STRING_ARRAY = new String[0];

	/**
	 * 用这个方法，代替Pattern.split和String.split。 <b>与Pattern.split不同的是(@see
	 * Pattern#split(java.lang.CharSequence)),
	 * 如果text是一个空串，这里不会返回包含一个空串的数组(一个元素)，而是返回一个空数组。</b>
	 */
	public static String[] split(String strPattern, CharSequence text) {
		String[] result = getPattern(strPattern).split(text);
		if (result.length == 1 && result[0].length() == 0) {
			return EMPTY_STRING_ARRAY;
		}
		return result;
	}

	/**
	 * 用这个方法，代替Pattern.split和String.split。 <b>与Pattern.split不同的是(@see
	 * Pattern#split(java.lang.CharSequence)),
	 * 如果text是一个空串，这里不会返回包含一个空串的数组(一个元素)，而是返回一个空数组。</b>
	 */
	public static String[] split(String strPattern, CharSequence text, int limit) {
		String[] result = getPattern(strPattern).split(text, limit);
		if (result.length == 1 && result[0].length() == 0) {
			return EMPTY_STRING_ARRAY;
		}
		return result;
	}

}
