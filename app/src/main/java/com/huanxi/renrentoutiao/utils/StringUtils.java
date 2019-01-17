package com.huanxi.renrentoutiao.utils;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yanfuchang on 2017/11/20.
 */

public class StringUtils {
    private static final long K = 1024;
    private static final long M = K * K;
    private static final long G = M * K;
    private static final long T = G * K;

    public static String bytesToHuman(final long value) {
        final long[] dividers = new long[]{T, G, M, K, 1};
        final String[] units = new String[]{"TB", "GB", "MB", "KB", "B"};
        if (value < 1)
            return 0 + " " + units[units.length - 1];
        String result = null;
        for (int i = 0; i < dividers.length; i++) {
            final long divider = dividers[i];
            if (value >= divider) {
                result = format(value, divider, units[i]);
                break;
            }
        }
        return result;
    }

    private static String format(final long value,
                                 final long divider,
                                 final String unit) {
        final double result =
                divider > 1 ? (double) value / (double) divider : (double) value;
        return new DecimalFormat("#.##").format(result) + " " + unit;
    }

    /**
     * @param string 字符串
     * @param i      第i次出现
     * @param str    子字符串
     * @return
     */
    public static int getIndex(String string, int i, String str) {
        //这里是获取"/"符号第三次出现的下标
//        Matcher slashMatcher = Pattern.compile("/").matcher(string);
        Matcher slashMatcher = Pattern.compile(str).matcher(string);
        int mIdx = 0;
        while (slashMatcher.find()) {
            mIdx++;
            //当"/"符号第三次出现的位置
            if (mIdx == i) {
                break;
            }
        }
        return slashMatcher.start();
    }
}
