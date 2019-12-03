package com.boot.config.utils;

import com.boot.config.properties.exception.DBException;
import com.project.utils.TF;

public class DBAssertTool {

	
	private static void AssertException(String msg) {
		new DBException(msg).throwGoalb();
	}
	
	public static void isRealUpdate(Integer updateCount) {
		if (updateCount == null ||updateCount == 0) {
			AssertException("数据更新操作异常。");
		}
	}

	public static void isRealUpdate(Integer updateCount,int real) {
		if (updateCount == null ||updateCount == 0 || updateCount != real) {
			AssertException("数据更新操作异常。");
		}
	}
	
	public static void isRealDelete(Integer deleteCount) {
		if (deleteCount == null ||deleteCount == 0) {
			AssertException("数据删除操作异常。");
		}
	}
	
	public static void isRealDelete(Integer deleteCount,int real) {
		if (deleteCount == null ||deleteCount == 0 || deleteCount != real) {
			AssertException("数据删除操作异常。");
		}
	}
	
	public static void isRealInsert(Integer insertCount) {
		if (insertCount == null ||insertCount == 0) {
			AssertException("数据添加操作异常。");
		}
	}
	
	public static void isRealInsert(Integer insertCount,int real) {
		if (insertCount == null ||insertCount == 0 || insertCount != real) {
			AssertException("数据添加操作异常。");
		}
	}

	public static void isIdValid(String id) {
		if (TF.isBlank(id)) {
			AssertException("数据操作异常。");
		}
	}
	
	public static void isNotBlank(String string,String msg) {
		if (string==null||string.trim().equals("")) {
			AssertException(msg);
		}
	}
	
	public static void isNotNull(Object string,String msg) {
		if (string==null) {
			AssertException(msg);
		}
	}

	public static void isExits(int selectCount,String msg) {
		if (selectCount != 0) {
			AssertException(msg);
		}
	}

	public static void isNotNull(Object obj) {
		if (obj==null) {
			AssertException("数据操作异常。");
		}
	}

}
