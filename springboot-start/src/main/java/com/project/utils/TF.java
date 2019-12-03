package com.project.utils;

import java.util.List;

public class TF {

	/**
	 * 作者：王俊辉
	 * 判断是否为空字符串
	 * @param string
	 * @return true：非空 false：空
	 * @2019年1月22日上午10:08:37
	 */
	public static boolean isNotBlank(String string) {
		if (string==null||string.trim().equals("")) {
			return false;
		}
		return true;
	}
	
	/**
	 * 作者：王俊辉
	 * 判断所有参数是否为空字符串
	 * @param string
	 * @return true：非空 false：空
	 * @2019年1月22日上午10:08:37
	 */
	public static boolean isNotBlank(String... string) {
		
		for (int i = 0; i < string.length; i++) {
			if (string==null||string[i].trim().equals("")) {
				return false;
			}
		}
		return true;
	}

	public static boolean isBlank(String string) {
		if (string==null||string.trim().equals("")) {
			return true;
		}
		return false;
	}

	public static boolean isNotEmpty(List<?> infoList) {
		if (infoList == null || infoList.size() == 0) {
			return false;
		}
		
		return true;
	}

	public static boolean isNotNull(Object obj) {
		
		if (obj != null) {
			return true;
		}
		
		return false;
	}

	public static boolean isNull(Object obj) {
		if (obj == null) {
			return true;
		}
		return false;
	}
	
}
