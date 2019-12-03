package com.boot.config.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author  王俊辉
 * 2019年9月29日 上午9:49:43
 */

public class MapUtil {

    public static<T> void removeKeyUnderline(List<Map<String, T>> list){

        int size = list.size();
        for (int i = 0; i < size; i++) {
            removeKeyUnderline(list.get(i));
        }

    }

    public static<T> void removeKeyUnderline(Map<String, T> map){
        List<String> list = new ArrayList<String>();
        for(String key : map.keySet()){
            if (key.contains("_")) {
                list.add(key);
            }
        }
        for (String key : list) {
            map.put(upperTable(key), map.get(key));
        }
        for (String key : list) {
            map.remove(key);
        }
    }

    /**
     * 方法说明 :将首字母和带 _ 后第一个字母 转换成大写
     *
     * @return :String
     * @author :HFanss
     * @date :2018年5月31日下午9:52:19
     */
    public static String upperTable(String str)
    {
        // 字符串缓冲区
        StringBuffer sbf = new StringBuffer();
        // 如果字符串包含 下划线
        if (str.contains("_"))
        {
            // 按下划线来切割字符串为数组
            String[] split = str.split("_");
            sbf.append(split[0]);
            // 循环数组操作其中的字符串
            for (int i = 1, index = split.length; i < index; i++)
            {
                // 转换成字符数组
                char[] ch = split[i].toCharArray();
                // 利用ASCII码实现大写
                ch[0] = (char) (ch[0] - 32);
                // 添加到字符串缓冲区
                sbf.append(ch);
            }
        }
        return sbf.toString();
    }

}
