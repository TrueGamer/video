package com.huanxi.renrentoutiao.ui.fragment.base;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huanxi.renrentoutiao.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Dinosa on 2018/1/19.
 *
 * 这里是与BaseActivity相对应的实现操作；
 */

public  abstract class BaseTitleFragment extends BaseFragment {

    @Nullable
    @BindView(R.id.tv_title)
    public TextView mTvTitle;

    @Nullable
    @BindView(R.id.tv_back)
    public TextView mTvBack;

    @Nullable
    @BindView(R.id.tv_right_option)
    public TextView mTvRight;


    @Override
    protected View getContentView() {
        ViewGroup view = (ViewGroup)inflatLayout(R.layout.activity_base_title);

        ViewGroup viewGroup = (ViewGroup) view.findViewById(R.id.fl_container);
        if (viewGroup != null) {
            viewGroup.addView(getBodyView());
        }
        return view;
    }

    /**
     * 加载bodyd
     * @return
     */
    public abstract View getBodyView();



    @OnClick(R.id.tv_back)
    public void onClickBack(){
        getActivity().finish();
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
                mTvBack.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getActivity(), resImgId), null, null, null);
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
                mTvRight.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getActivity(), resImgId), null, null, null);
            }
        }
    }

}
