package com.hongzebin.util;

import java.util.ArrayList;
import java.util.List;

/**
 * list和str之间的转换
 * Created by 洪泽彬
 */
public class ListTurning {
    //list转换成string
    public static String listToStr(List<String> list) {
        String str = "";
        for (String x : list) {
            str += x + "@";
        }
        return str;
    }

    //string转换成list
    public static List<String> strToList(String str) {
        List<String> list = new ArrayList<>();
        String[] strings = str.split("@");
        for (String x : strings) {
            list.add(x);
        }
        return list;
    }
}
