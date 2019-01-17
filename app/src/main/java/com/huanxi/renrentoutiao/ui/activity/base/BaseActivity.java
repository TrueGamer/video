package com.huanxi.renrentoutiao.ui.activity.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huanxi.renrentoutiao.MyApplication;
import com.huanxi.renrentoutiao.model.bean.UserBean;
import com.huanxi.renrentoutiao.net.bean.ResSplashAds;
import com.huanxi.renrentoutiao.ui.activity.other.LoginActivity;
import com.huanxi.renrentoutiao.ui.dialog.LoadingDialog;
import com.huanxi.renrentoutiao.utils.MyActivityManager;
import com.huanxi.renrentoutiao.utils.UIUtils;
import com.jaeger.library.StatusBarUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Dinosa on 2018/1/4.
 */

public abstract class BaseActivity  extends SupportActivity{

    //请求登陆
    private static final int REQUEST_LOGIN = 2;

    public Unbinder mUnbinder;

    private View mRootView;
    private CheckLoginCallBack mCheckLoginCallBack;
    LoadingDialog mLoadingDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置竖屏模式
        if(savedInstanceState != null){
            ResSplashAds resSplashAds = (ResSplashAds) savedInstanceState.getSerializable(ALL_ADS);
            getMyApplication().setResAds(resSplashAds);
        }

        mRootView = createView(getLayoutInflater(), null, savedInstanceState);
        setContentView(mRootView);

        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);

        initView(mRootView, savedInstanceState);
        initData();

        MyActivityManager.addActivity(this);
    }



    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getContentView(inflater,container);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 如果需要重写我们直接覆盖这个方法就可以了
     * @param inflater
     * @param container
     * @return
     */
    public View getContentView(LayoutInflater inflater, ViewGroup container){
        return inflater.inflate(getContentLayout(), container);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder=null;
        }

        MyActivityManager.removeActivity(this);
    }

    /**
     * 默认情况下需要返回contentLayout布局
     * @return
     */
    public int getContentLayout(){
        return 0;
    }


    protected abstract void initView(View rootView, Bundle savedInstanceState);

    protected void initData(){

    }



    //沉浸式状态栏
    public void setStatusBarImmersive(View viewNeedOffset) {
        StatusBarUtil.setTransparentForImageView(this, viewNeedOffset);
    }

    public void setStatusBarImmersiveInCoordinatorLayout() {
        StatusBarUtil.setTranslucentForCoordinatorLayout(this, 0);
    }

    public void toast(String text){
        UIUtils.toast(this,text);
    }


    public boolean isLogin(){
        if (((MyApplication) getApplication()).getUserBean()==null) {
            return false;
        }
        return true;
    }

    //=============这里是操作与用户相关的功能===============
    public void updateUser(UserBean userBean){
        ((MyApplication) getApplication()).updateUser(userBean);
    }

    public MyApplication getMyApplication(){
        return ((MyApplication) getApplication());
    }

    public UserBean getUserBean(){
       return ((MyApplication) getApplication()).getUserBean();
    }

    public void clearUser(){
        ((MyApplication) getApplication()) .clearUser();
    }


    public void checkLogin(CheckLoginCallBack  checkLoginCallBack){
        mCheckLoginCallBack = checkLoginCallBack;
        
        if(isLogin()){
            mCheckLoginCallBack.loginSuccess();    
        }else{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent,REQUEST_LOGIN);
        }
    }


    public interface CheckLoginCallBack{
        public void loginSuccess();
        public void loginFaild();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_LOGIN ){
            if(resultCode==RESULT_OK){
                if (mCheckLoginCallBack != null) {
                    mCheckLoginCallBack.loginSuccess();
                }
            }else{
                if (mCheckLoginCallBack != null) {
                    mCheckLoginCallBack.loginFaild();
                }
            }
        }
    }

    /**
     * 显示对话框；
     */
    public void showDialog(){
        if (mLoadingDialog == null) {
            mLoadingDialog=new LoadingDialog(this);
        }
        mLoadingDialog.show();
    }

    /**
     * 隐藏对话框
     */
    public void dismissDialog(){
        try {
            mLoadingDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public boolean isHasAds(){
        return getMyApplication().isHasAds();
    }


    String ALL_ADS="ads";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        MyApplication myApplication = getMyApplication();
        ResSplashAds resAds = myApplication.getResAds();
        outState.putSerializable(ALL_ADS,resAds);
    }


}
