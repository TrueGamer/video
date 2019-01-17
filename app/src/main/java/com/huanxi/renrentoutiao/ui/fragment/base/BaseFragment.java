package com.huanxi.renrentoutiao.ui.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huanxi.renrentoutiao.MyApplication;
import com.huanxi.renrentoutiao.model.bean.UserBean;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.jaeger.library.StatusBarUtil;

import butterknife.ButterKnife;

/**
 * Created by Dinosa on 2018/1/4.
 * 基类的处理
 */
public abstract class BaseFragment extends SupportFragment {




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = getContentView();
        ButterKnife.bind(this,contentView);
        initView();
        return contentView;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initData();
    }

    /**
     * 对View进行一个数据填充；
     */
    protected void initData() {

    }

    /**
     * 初始化View；
     */
    protected void initView() {

    }

    public View inflatLayout(int layoutId){
        View view = View.inflate(getActivity(), layoutId, null);
        return view;
    }


    /**
     * 这里是获取Fragment里面的View;
     * @return
     */
    protected abstract View getContentView();




    //沉浸式状态栏
    public void setStatusBarImmersive(View viewNeedOffset) {
        StatusBarUtil.setTransparentForImageView(getActivity(), viewNeedOffset);
    }

    public void setStatusBarImmersiveInCoordinatorLayout() {
        StatusBarUtil.setTranslucentForCoordinatorLayout(getActivity(), 0);
    }

    public BaseActivity getBaseActivity(){
        return ((BaseActivity) getActivity());
    }

    /**
     * 判断是否登陆的逻辑
     * @return
     */
    public boolean isLogin(){
        return getBaseActivity().isLogin();
    }

    /**
     * 这里登陆的检验的登陆的回掉；
     * @param callBack
     */
    public void checkLogin(BaseActivity.CheckLoginCallBack callBack){
        getBaseActivity().checkLogin(callBack);
    }

    public boolean isHasAds(){
        return getBaseActivity().isHasAds();
    }

    public UserBean getUserBean(){
        return getBaseActivity().getUserBean();
    }

    public void updateUserBean(UserBean userBean){
        getBaseActivity().updateUser(userBean);
    }

    public void toast(String content){
        if(getBaseActivity() != null && !TextUtils.isEmpty(content)) {
            getBaseActivity().toast(content);
        }
    }

    public MyApplication getMyApplication(){
        return getBaseActivity().getMyApplication();
    }


    public void showDialog(){
        getBaseActivity().showDialog();
    }

    public void dismissDialog(){
        getBaseActivity().dismissDialog();
    }



}
