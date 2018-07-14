package com.hongzebin.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * 通用方法工具类，里面有进行数字月份对英文月份的转换和把SpannableStringBuilder字符变大的方法。
 * Created by 洪泽彬
 */

public class GlobalTools {

    /**
     * 获取英文月份
     *
     * @param month 数字月份
     * @return 英文月份
     */
    public static String getEnglishMonth(int month) {
        if (month == 1)
            return "January";
        else if (month == 2)
            return "Feb";
        else if (month == 3)
            return "Mar";
        else if (month == 4)
            return "Apr";
        else if (month == 5)
            return "May";
        else if (month == 6)
            return "June";
        else if (month == 7)
            return "July";
        else if (month == 8)
            return "Aug";
        else if (month == 9)
            return "Sept";
        else if (month == 10)
            return "Oct";
        else if (month == 11)
            return "Nov";
        else
            return "Dec";
    }

    /**
     * 使SpannableStringBuilder的字符变大
     *
     * @param string 需要变的字符串
     * @param size   字符变大的大小
     * @param start  起始字符位置
     * @param end    结束字符位置
     * @return 改变后的SpannableStringBuilder
     */
    public SpannableStringBuilder turnDateStyle(String string, int size, int start, int end) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(string);
        ssb.setSpan(new AbsoluteSizeSpan(size), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return ssb;
    }

    /**
     * 判断设备是否联网
     *
     * @param context 上下文
     * @return 是否联网
     */
    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo  = connectivityManager.getActiveNetworkInfo();
        if(networkInfo == null || !networkInfo.isAvailable()){
            return false;
        }
        else {
            return true;
        }
    }
}
