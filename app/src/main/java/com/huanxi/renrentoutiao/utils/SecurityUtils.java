package com.huanxi.renrentoutiao.utils;

import com.huanxi.renrentoutiao.MyApplication;
import com.zhxu.library.utils.rsa.Base64;
import com.zhxu.library.utils.rsa.RSAEncrypt;

import java.io.InputStream;

/**
 * Created by Dinosa on 2018/3/7.
 */

public class SecurityUtils {


    public static String decodeRsaData(String data){

        try {
            InputStream privateKeyInputStream = MyApplication.getConstantContext().getAssets().open("rsa/pkcs8_private_key.pem");

            // 私钥解密过程
            byte[] res = RSAEncrypt.decrypt(RSAEncrypt.loadPrivateKeyByStr(RSAEncrypt.loadPrivateKeyByFile(privateKeyInputStream)),
                    Base64.decode(data));
            String restr = new String(res,"utf-8");
            return restr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
