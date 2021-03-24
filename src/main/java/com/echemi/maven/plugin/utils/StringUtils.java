package com.echemi.maven.plugin.utils;

/**
 * @author jacob
 * created in  2021/3/13 15:02
 * modified By:
 */
public class StringUtils {
	
	public static boolean isEmpty(final CharSequence cs) {
		return cs == null || cs.length() == 0;
	}
	
	public static boolean isNotEmpty(final CharSequence cs) {
		return !isEmpty(cs);
	}
}
