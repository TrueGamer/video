package com.huanxi.renrentoutiao.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.huanxi.renrentoutiao.net.api.ads.ApiEndReadAd;
import com.huanxi.renrentoutiao.net.api.ads.ApiStartReadAd;
import com.huanxi.renrentoutiao.net.api.news.ApiNewAdLog;
import com.huanxi.renrentoutiao.net.bean.ResEmpty;
import com.huanxi.renrentoutiao.net.bean.ResReadAwarad;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.zhxu.library.exception.HttpTimeException;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Utils {
    public static String randomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789"; //生成字符串从此序列中取
        Random random = new Random();
        StringBuilder randomStr = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(36);
            randomStr.append(base.charAt(number));
        }
        return randomStr.toString();
    }

    enum HASH_ALGORITHM {MD5, SHA1, SHA256, SHA384, SHA512}

    //MD5,SHA-1,SHA-256,SHA-384,SHA-512
    static String getHash(String data, HASH_ALGORITHM hashType) {
        return getHash(data.getBytes(), hashType);
    }

    static String getHash(byte[] data, HASH_ALGORITHM hashAlgorithm) {
        String hashType = "";
        if (hashAlgorithm == HASH_ALGORITHM.MD5) {
            hashType = "MD5";
        } else if (hashAlgorithm == HASH_ALGORITHM.SHA1) {
            hashType = "SHA-1";
        } else if (hashAlgorithm == HASH_ALGORITHM.SHA256) {
            hashType = "SHA-256";
        } else if (hashAlgorithm == HASH_ALGORITHM.SHA384) {
            hashType = "SHA-384";
        } else if (hashAlgorithm == HASH_ALGORITHM.SHA512) {
            hashType = "SHA-512";
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance(hashType);
            md5.update(data);
            return toHexString(md5.digest());
        } catch (Exception e) {
            return "";
        }
    }

    //HmacMD5, HmacSHA1, HmacSHA256, HmacSHA384, HmacSHA512
    static String getHmacHash(String data, String key, HASH_ALGORITHM hashAlgorithm) {
        return getHmacHash(data.getBytes(), key.getBytes(), hashAlgorithm);
    }

    static String getHmacHash(byte[] data, byte[] key, HASH_ALGORITHM hashAlgorithm) {
        String hashType = "";
        if (hashAlgorithm == HASH_ALGORITHM.MD5) {
            hashType = "HmacMD5";
        } else if (hashAlgorithm == HASH_ALGORITHM.SHA1) {
            hashType = "HmacSHA1";
        } else if (hashAlgorithm == HASH_ALGORITHM.SHA256) {
            hashType = "HmacSHA256";
        } else if (hashAlgorithm == HASH_ALGORITHM.SHA384) {
            hashType = "HmacSHA384";
        } else if (hashAlgorithm == HASH_ALGORITHM.SHA512) {
            hashType = "HmacSHA512";
        }
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key, hashType);
            Mac mac = Mac.getInstance(hashType);
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data);
            return toHexString(rawHmac);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    static String toHexString(byte[] b) {
        char[] hexChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (byte aB : b) {
            sb.append(hexChar[(aB & 0xf0) >>> 4]);
            sb.append(hexChar[aB & 0x0f]);
        }
        return sb.toString();
    }

    static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 这里开始计时操作?
     */
    public static void getBeganStart(BaseActivity mActivity , String adId){
        /*HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiStartReadAd.FROM_UID, mActivity.getUserBean().getUserid());
        paramsMap.put(ApiStartReadAd.ADID , adId);
        paramsMap.put(ApiStartReadAd.TYPE , "90");
        ApiStartReadAd apiStartReadAd = new ApiStartReadAd(new HttpOnNextListener<ResEmpty>() {

            @Override
            public void onNext(ResEmpty resEmpty) {
//                mActivity.requestStartCountDown();
                getReadCoin(mActivity , adId);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (e instanceof HttpTimeException) {
                    HttpTimeException exception = (HttpTimeException) e;
                    //这里表示已经领取过该任务了
                    Toast toast = Toast.makeText(mActivity , exception.getMessage() , Toast.LENGTH_SHORT);
                    TextView v = (TextView)toast.getView().findViewById(android.R.id.message);
                    v.setTextSize(20);
                    toast.show();
//                   mActivity.hideReadProgress();
                }
            }
        }, mActivity ,paramsMap);
        HttpManager.getInstance().doHttpDeal(apiStartReadAd);*/
        InfoUtil.getNetIp(new InfoUtil.NetCallback() {
            @Override
            public void onSuccess(String value) {
                HashMap<String, String> paramsMap = new HashMap<>();
                paramsMap.put(ApiNewAdLog.TYPE,"2");
                paramsMap.put(ApiNewAdLog.SERVER_NUMBER,SharedPreferencesUtils.getInstance(mActivity).getString(SharedPreferencesUtils.CHANNEL));
                paramsMap.put(ApiNewAdLog.MAC_ADDRESS,InfoUtil.getMacAddress());
                paramsMap.put(ApiNewAdLog.PHONE_BRAND,Build.BRAND);
                paramsMap.put(ApiNewAdLog.PHONE_MODULE,Build.MODEL);
                paramsMap.put(ApiNewAdLog.SYSTEM_VERSION,Build.VERSION.RELEASE);
                paramsMap.put(ApiNewAdLog.IP,value);
                paramsMap.put(ApiNewAdLog.AD_CHANNEL_NUM,ApiNewAdLog.AD_CHANNEL_ADHUB);
                paramsMap.put(ApiNewAdLog.AD_ID,adId);
//                paramsMap.put(ApiNewAdLog.NEWS_ID,mMd5Url);

                ApiNewAdLog apiStartReadIssure = new ApiNewAdLog(new HttpOnNextListener<String>() {

                    @Override
                    public void onNext(String str) {
//                mActivity.requestStartCountDown();
                        getReadCoin(mActivity,adId);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (e instanceof HttpTimeException) {
                            HttpTimeException exception = (HttpTimeException) e;
                            //这里表示已经领取过该任务了
                            Toast toast = Toast.makeText(mActivity , exception.getMessage() , Toast.LENGTH_SHORT);
                            TextView v = (TextView)toast.getView().findViewById(android.R.id.message);
                            v.setTextSize(20);
                            toast.show();
//                   mActivity.hideReadProgress();
                        }
                    }
                },mActivity,paramsMap);
                HttpManager.getInstance().doHttpDeal(apiStartReadIssure);
            }

            @Override
            public void onFail(Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 结束计时操作
     */
    public static void getReadCoin(BaseActivity mActivity , String adId) {
        /*HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiEndReadAd.FROM_UID,mActivity.getUserBean().getUserid());
        paramsMap.put(ApiEndReadAd.ADID , adId);
        paramsMap.put(ApiStartReadAd.TYPE , "90");
        ApiEndReadAd apiEndReadAd = new ApiEndReadAd(new HttpOnNextListener<ResReadAwarad>() {
            @Override
            public void onNext(ResReadAwarad resReadNewsAward) {
                Log.i("info" , "ApiEndReadAd="+resReadNewsAward.toString());
                //这里要更新进度；
                SharedPreferencesUtils.getInstance(mActivity).setReward(mActivity
                        , resReadNewsAward.getLastcount_new());
                SharedPreferencesUtils.getInstance(mActivity).setRewardNum(mActivity ,
                        resReadNewsAward.getIntegral()+"");
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        },mActivity,paramsMap);
        HttpManager.getInstance().doHttpDeal(apiEndReadAd);*/
        InfoUtil.getNetIp(new InfoUtil.NetCallback() {
            @Override
            public void onSuccess(String value) {
                HashMap<String, String> paramsMap = new HashMap<>();
                paramsMap.put(ApiNewAdLog.TYPE,"3");
                paramsMap.put(ApiNewAdLog.SERVER_NUMBER,SharedPreferencesUtils.getInstance(mActivity).getString(SharedPreferencesUtils.CHANNEL));
                paramsMap.put(ApiNewAdLog.MAC_ADDRESS,InfoUtil.getMacAddress());
                paramsMap.put(ApiNewAdLog.PHONE_BRAND,Build.BRAND);
                paramsMap.put(ApiNewAdLog.PHONE_MODULE,Build.MODEL);
                paramsMap.put(ApiNewAdLog.SYSTEM_VERSION,Build.VERSION.RELEASE);
                paramsMap.put(ApiNewAdLog.IP,value);
                paramsMap.put(ApiNewAdLog.AD_CHANNEL_NUM,ApiNewAdLog.AD_CHANNEL_ADHUB);
                paramsMap.put(ApiNewAdLog.AD_ID,adId);
//                paramsMap.put(ApiNewAdLog.NEWS_ID,mMd5Url);

                ApiNewAdLog apiStartReadIssure = new ApiNewAdLog(new HttpOnNextListener<String>() {

                    @Override
                    public void onNext(String str) {
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                },mActivity,paramsMap);
                HttpManager.getInstance().doHttpDeal(apiStartReadIssure);
            }

            @Override
            public void onFail(Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 判断某个Activity 界面是否在前台
     * @param context
     * @param className 某个界面名称
     * @return
     */
    public static boolean  isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
