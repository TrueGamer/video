package com.huanxi.renrentoutiao.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dinosa on 2018/2/2.
 * 格式化的一个帮助类；
 */

public class FormatUtils {

    /**
     * 这里最多只能容纳24小时超过的话就会有问题；
     * 格式化时间返回，1000*10 返回为 00：10；
     * 1000*60*60+1000*60*5   01:05:00;
     * @param
     * @return 返回时间对应的字符串；
     */
    public static String formatMillisToTime(long date){
        //这里的话， 我们对毫秒进行一个格式化的操作；
        long hours=0;
        long seconds = date / 1000;
        long minutes = seconds / 60;
        long second = seconds % 60;

        if (minutes>=60) {
            hours=minutes/60;
            minutes=minutes%60;
        }
        String time="";
        if(hours==0){
            //这里表示没有小时；
            time+=formatNumberToString(minutes);
            time+=":"+formatNumberToString(second);

        }else {
            //这里表示有小时的字符串拼接；
            time+=formatNumberToString(hours);
            time+=":"+formatNumberToString(minutes);
            time+=":"+formatNumberToString(second);
        }
        return time;
    }
    /**
     * 这里最多只能容纳24小时超过的话就会有问题；
     * 格式化时间返回，1000*10 返回为 00：10；
     * 1000*60*60+1000*60*5   01:05:00;
     * @param
     * @return 返回时间对应的字符串；
     */
    public static String formatSecondToTime(long date){
        //这里的话， 我们对毫秒进行一个格式化的操作；
        long hours=0;
        long seconds = date;
        long minutes = seconds / 60;
        long second = seconds % 60;

        if (minutes>=60) {
            hours=minutes/60;
            minutes=minutes%60;
        }
        String time="";
        if(hours==0){
            //这里表示没有小时；
            time+=formatNumberToString(minutes);
            time+=":"+formatNumberToString(second);

        }else {
            //这里表示有小时的字符串拼接；
            time+=formatNumberToString(hours);
            time+=":"+formatNumberToString(minutes);
            time+=":"+formatNumberToString(second);
        }
        return time;
    }



    /**
     * 这里我们是对大于0的number进行一个格式化；
     * @param number 需要格式化的数字
     * @return 小于10的，在前面+0，否则原样子返回；
     */
    public static String formatNumberToString(long number){
        String time="";
        if(number<10){
            time+="0"+number;
        }else{
            time+=number;
        }
        return time;
    }

    /**
     * 格式化日期
     * @param longTime 整形的时间；
     * @param pattern yyyy-MM-dd
     * @return
     */
    public static String formatDate(long longTime,String pattern){
        return new SimpleDateFormat(pattern).format(new Date(longTime));
    }

    /**
     * 默认的格式化日期；
     * @param longTime
     * @return
     */
    public static String defaultFormatDateToString(long longTime){
        return formatDate(longTime,"yyyy-MM-dd");
    }



    /**
     * 默认的格式化日期；
     * @param date
     * @return
     */
    public static Date defaultFormatStringToDate(String date){
        return formatStringToDate(date,"yyyy-MM-dd");
    }

    /**
     * 这里是将字符串转换为date数据；
     * @return
     */
    public static Date formatStringToDate(String str,String pattern){

        try {
            return new SimpleDateFormat(pattern).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保留1位小数
     * @param countPrice
     * @return
     */
    public static String decimalFormat(float countPrice){
        DecimalFormat df = new DecimalFormat("#.0");
        return formatDotString(df.format(countPrice));
    }

    /**
     * 保留2位小数
     * @param countPrice
     * @return
     */
    public static String decimalFormatTwo(float countPrice){
        DecimalFormat df = new DecimalFormat("#.00");
        String format = df.format(countPrice);
        //上面有一个问题就是如果是0.4会变成.4
        return formatDotString(format);
    }

    /**
     * 将.4 转换为0.4；
     * @param targetStr
     * @return
     */
    public static String formatDotString(String targetStr){
        String substring = targetStr.substring(0, 1);
        if(".".equals(substring)) {
            targetStr="0"+targetStr;
        }
        return targetStr;
    }


    /**
     * 使用md5的算法进行加密
     */
    public static String md5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }
}
