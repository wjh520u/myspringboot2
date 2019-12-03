package com.project.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTool {

    private static final ConcurrentHashMap<String, Pattern> pattern=new ConcurrentHashMap<String,Pattern>();
    
    private static Pattern getPattern(String regex){
		if(pattern.get(regex)==null){
			synchronized (regex.intern()) {
				if(pattern.get(regex)==null){
					pattern.put(regex, Pattern.compile(regex));
				}
				return pattern.get(regex);
			}
		}
		return pattern.get(regex);
	}
	
	/**
	 * @param regex  正则式
	 * @param input  校验值
	 * @return  是否全文匹配
	 */
	public final static boolean match(String regex, String input) {
		if (input==null) {
			return false;
		}
		return getPattern(regex).matcher(input).matches();
    }
	
	public final static List<String> matchStrings( String input,String pre,String suf) {
		
		List<String> matcherList = new ArrayList<String>();
		if (input==null) {
			return matcherList;
		}
		int start = 0;
		int endIndexOf = -1;
		int startIndexOf = input.indexOf(pre,start);
		while (startIndexOf != -1) {
			endIndexOf = input.indexOf(suf,startIndexOf);
			if (endIndexOf != -1) {
				matcherList.add(input.substring(startIndexOf+1, endIndexOf));
				start = endIndexOf;
				startIndexOf = input.indexOf(pre,start);
			}else {
				startIndexOf = -1;
			}
		}
		return matcherList;
    }
	
	public final static String matchOneStrings( String input,String pre,String suf) {
	
		if (input==null) {
			return null;
		}
		int start = 0;
		int endIndexOf = -1;
		int startIndexOf = input.indexOf(pre,start);
			endIndexOf = input.indexOf(suf,startIndexOf);
		if (endIndexOf != -1) {
			return input.substring(startIndexOf+pre.length(), endIndexOf);
		};
		return null;
    }
    
	/**
     * 匹配所有
     *
     * @param regex      正则表达式
     * @param regexGroup 分组号
     * @param content    要进行匹配的内容
     * @return
     */
    public static List<String> matchAll(String regex, String content) {
        List<String> list = new LinkedList<String>();
        Matcher m = getPattern(regex).matcher(content);
        int i = 0;
        while (m.find()) {
            list.add(m.group(i));
            i++;
        }
        return list;
    }
	
} 
