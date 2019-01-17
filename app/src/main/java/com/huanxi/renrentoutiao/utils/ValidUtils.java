package com.huanxi.renrentoutiao.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by Dinosa on 2018/1/31.
 */

public class ValidUtils {

    /**
     * @param mobiles
     * @return 验证手机号格式
     */
    public static boolean isMobileNO(String mobiles) {

        String telRegex = "[1][3456789]\\d{9}";
        if (TextUtils.isEmpty(mobiles))//
            return false;
        else
            return mobiles.matches(telRegex);
    }

    public static boolean isEmail(String email){
        String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        return Pattern.matches(regex, email);
    }
}
