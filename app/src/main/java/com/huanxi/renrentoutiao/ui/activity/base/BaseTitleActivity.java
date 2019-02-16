package com.huanxi.renrentoutiao.ui.activity.base;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.utils.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Dinosa on 2018/1/19.
 * 这里是公共的title基础类；
 */
public abstract class BaseTitleActivity extends BaseActivity {

    @Nullable
    @BindView(R.id.tv_title)
    public TextView mTvTitle;

    @Nullable
    @BindView(R.id.tv_back)
    public TextView mTvBack;

    @Nullable
    @BindView(R.id.tv_right_option)
    public TextView mTvRight;

    @Nullable
    @BindView(R.id.rl_title)
    public View mRlTitle;

    @Nullable
    @BindView(R.id.fake_status_bar)
    public View mStatusBar;

    @Nullable
    @BindView(R.id.img_close)
    public View imgClose;

    @Override
    public View getContentView(LayoutInflater inflater, ViewGroup container) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.activity_base_title, container, false);
        ViewGroup viewGroup = (ViewGroup) view.findViewById(R.id.fl_container);
        View bodyView = getBodyView(inflater, view);
        if (bodyView != null) {
            viewGroup.addView(bodyView);
        }
        return view;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        setStatusBarImmersive(null);
    }

    public  View getBodyView(LayoutInflater inflater, ViewGroup container) {
        if (getBodyLayoutId() != 0) {
            View view = inflater.inflate(getBodyLayoutId(),container,false);
            return view;
        }
        return null;
    }

    /**
     * 如果是默认的情况下，必须返回这个layoutId;
     * @return
     */
    public int getBodyLayoutId(){
        return 0;
    }

    @OnClick(R.id.tv_back)
    public void onClickBack(){
        finish();
    }

    /**
     * 设置返回键的文字和图片；
     * @param text
     * @param resImgId
     */
    public void setBackTextAndDrawable(String text,int resImgId){

        if (mTvBack != null) {
            mTvBack.setVisibility(View.VISIBLE);
            mTvBack.setText(text);
            if(resImgId>-1){
                Drawable drawable = ContextCompat.getDrawable(this, resImgId);
                drawable.setBounds(0,0, UIUtils.dip2px(this,25),UIUtils.dip2px(this,25));
                mTvBack.setCompoundDrawables(drawable, null, null, null);
            }
        }
    }

    /**
     * 设置返回键的文字
     * @param text
     */
    public void setBackText(String text){
        setBackTextAndDrawable(text,-1);
    }

    /**
     * 设置右边按钮的文字
     * @param text
     */
    public void setRightText(String text){
        setRightTextAndDrawable(text,-1);
    }

    public void setTitle(String text){
        if (mTvTitle != null) {
            mTvTitle.setText(text);
        }
    }

    /**
     * 设置右边的文字和图片
     * @param text
     * @param resImgId
     */
    public void setRightTextAndDrawable(String text,int resImgId){
        if (mTvRight != null) {
            mTvRight.setVisibility(View.VISIBLE);
            mTvRight.setText(text);
            if(resImgId>-1){
                Drawable drawable = ContextCompat.getDrawable(this, resImgId);
                drawable.setBounds(0,0, UIUtils.dip2px(this,25),UIUtils.dip2px(this,25));
                mTvRight.setCompoundDrawables(drawable, null, null, null);
            }
        }
    }

    /**
     * 这里我们获取title
     * @return
     */
    public View  getTitleBar(){
        return mRlTitle;
    }

    /**
     * 获取关闭页面按钮
     * @return
     */
    public View getColseImg() {
        return imgClose;
    }

    /**
     * 获取我们的状态栏
     * @return
     */
    public View getStatueBar(){
        return mStatusBar;
    }
}
