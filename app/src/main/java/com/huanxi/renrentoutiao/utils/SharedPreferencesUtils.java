package com.huanxi.renrentoutiao.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.huanxi.renrentoutiao.globle.ConstantUrl;
import com.huanxi.renrentoutiao.model.bean.UserBean;
import com.huanxi.renrentoutiao.net.bean.news.HomeTabBean;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dinosa on 2018/1/31.
 */

public class SharedPreferencesUtils {

    public static final String CONFIG_NAME="config";

    //配置信息====================
    public static final String USER_BEAN="userBean"; //用户的信息；
    public static final String IS_SHOW_GUIDE="isShowGuide"; //是否有引导页面；
    public static final String IS_SHOW_GUIDE_NEW="isShowGuideNew"; //防止覆盖旧的版本没有新手引导页

    public static final String HISTORY="history";   //搜索记录；

    public static final String NEWS_TAB_MENU ="newsTabMenu";   //TabMenu的记录:用户选择的和用户没有选择的操作；
    public static final String VIDEO_TAB_MENU="videoTabMenu";   //TabMenu的记录:用户选择的和用户没有选择的操作；

    public static final String TOKEN="token";   //TabMenu的记录:用户选择的和用户没有选择的操作；

    public static final String SPLASH_TYPE = "splashType";// 记录当前开屏广告的类型
    public static final String Reward = "reward"; // 看广告获取奖励
    public static final String RewardNum = "rewardNum"; // 看广告获取奖励金币数

    public static final String WITHDRAW = "withdraw";   //   Ad ids

    public static final String CHANNEL = "channel";

    public static SharedPreferencesUtils sharedPreferencesUtils;
    private final SharedPreferences sharedPreferences;

    private SharedPreferencesUtils(Context context) {
        sharedPreferences = context.getSharedPreferences(CONFIG_NAME, context.MODE_PRIVATE);
    }

    public static SharedPreferencesUtils getInstance(Context context){

        if (sharedPreferencesUtils==null) {
            synchronized (SharedPreferencesUtils.class){
                sharedPreferencesUtils=new SharedPreferencesUtils(context);
            }
        }
        return sharedPreferencesUtils;
    }


    public void setUser(UserBean user){
        if(user != null){
            writeString(USER_BEAN, GsonUtils.getInstance().toString(user));
        }else{
            writeString(USER_BEAN,"");
        }
    }

    public UserBean getUserBean(){
        if (TextUtils.isEmpty(getString(USER_BEAN))) {
            return null;
        }
        return GsonUtils.getInstance().toBean(getString(USER_BEAN), UserBean.class);
    }


    public String getSplashType(Context context) {
        return SharedPreferencesUtils.getInstance(context).getString(SPLASH_TYPE);
    }

    public void setSplashType(Context context , String splashType) {
        SharedPreferencesUtils.getInstance(context).writeString(SPLASH_TYPE , splashType);
    }

    public String getReward(Context context) {
        return SharedPreferencesUtils.getInstance(context).getString(Reward);
    }

    public void setReward(Context context , String reward) {
        SharedPreferencesUtils.getInstance(context).writeString(Reward , reward);
    }

    public String getRewardNum(Context context) {
        return SharedPreferencesUtils.getInstance(context).getString(RewardNum);
    }

    public void setRewardNum(Context context , String reward) {
        SharedPreferencesUtils.getInstance(context).writeString(RewardNum , reward);
    }

    public String isShowGuide(Context context){
        return SharedPreferencesUtils.getInstance(context).getString(IS_SHOW_GUIDE_NEW);
    }

    public void setShowGuide(Context context,String versionCode){
        SharedPreferencesUtils.getInstance(context).writeString(IS_SHOW_GUIDE_NEW,versionCode);
    }


    public void saveToken(String token){

        writeString(TOKEN,token);

    }
    public String getToken(){
        return getString(TOKEN);
    }


    /**
     * 写入历史记录
     * @param history
     */
    public void updateHistory(String history){
        writeString(HISTORY,history);
    }

    /**
     * 这里是读取历史数据；
     * @return
     */
    public String getHistory(){
        return getString(HISTORY);
    }


    public void clearAllSearchHistory(){
        writeString(HISTORY,"");
    }


    /**
     * 这里要做的一个操作就是清除用户信息
     */
    public void clearUser(){
        setUser(null);
    }


    /**
     * 获取tab的缓存信息；
     */
    public List<HomeTabBean> getNewsTabMenu(){
        String menuStr = getString(NEWS_TAB_MENU);
        if (TextUtils.isEmpty(menuStr)) {
            return null;
        }

        Type type = new TypeToken<List<HomeTabBean>>() {
        }.getType();

        return GsonUtils.getInstance().toListBean(menuStr,type);
    }

    //将tab所有的内容进行一个缓存操作；
    public void setNewsTabMenu(List<HomeTabBean> tabMenu){
        if(tabMenu != null){
            writeString(NEWS_TAB_MENU, GsonUtils.getInstance().toString(tabMenu));
        }else{
            writeString(NEWS_TAB_MENU,"");
        }
    }

    /**
     * 获取tab的缓存信息；
     */
    public List<HomeTabBean> getVideoTabMenu(){
        String menuStr = getString(VIDEO_TAB_MENU);
        if (TextUtils.isEmpty(menuStr)) {
            return null;
        }

        Type type = new TypeToken<List<HomeTabBean>>() {
        }.getType();

        return GsonUtils.getInstance().toListBean(menuStr,type);
    }

    //将tab所有的内容进行一个缓存操作；
    public void setVideoTabMenu(List<HomeTabBean> tabMenu){
        if(tabMenu != null){
            writeString(VIDEO_TAB_MENU, GsonUtils.getInstance().toString(tabMenu));
        }else{
            writeString(VIDEO_TAB_MENU,"");
        }
    }

    /**
     * 将提现金额进行一个缓存操作；
     */
    public void setWithdraw(List<Integer> cg) {
        if (cg != null) {
            writeString(WITHDRAW, GsonUtils.getInstance().toString(cg));
        } else {
            writeString(WITHDRAW, "");
        }
    }


    /**
     * 获取提现金额的缓存信息；
     */
    public List<Integer> getWithdraw() {
        String menuStr = getString(WITHDRAW);
        if (TextUtils.isEmpty(menuStr)) {
            return new ArrayList<>();
        }

        Type type = new TypeToken<List<Integer>>() {
        }.getType();

        return GsonUtils.getInstance().toListBean(menuStr, type);
    }


    //============sp的基本操作；==================

    public void writeString(String key,String value){
        sharedPreferences
                .edit()
                .putString(key,value)
                .commit();
    }

    public String getString(String key){
        return sharedPreferences.getString(key,"");
    }

    public void writeBoolean(String key,boolean value){
        sharedPreferences
                .edit()
                .putBoolean(key,value)
                .commit();
    }

    public boolean getBoolean(String key){
        return sharedPreferences.getBoolean(key,false);
    }


    public void clearToken() {
        writeString(TOKEN,"");
    }
}
