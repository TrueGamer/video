package com.huanxi.renrentoutiao.ui.view.sign;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.R;

import java.util.List;

/**
 * Created by Dinosa on 2018/3/31.
 * 这里我是我们的新的签到页面；
 */

public class NewSignView extends FrameLayout {

    public static final int SIGN_COUNT = 7;
    private RecyclerView mRvList;
    private MySignAdapter mMySignAdapter;

    public NewSignView(@NonNull Context context) {
        this(context,null);
    }

    public NewSignView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NewSignView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //这里实现一个操作；

        View.inflate(getContext(),R.layout.view_new_sign_list,this);

        mRvList = ((RecyclerView) findViewById(R.id.rv_sign_list));

        mRvList.setLayoutManager(new GridLayoutManager(getContext(), SIGN_COUNT){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mMySignAdapter = new MySignAdapter(null);
        mRvList.setAdapter(mMySignAdapter);
    }


    public class MySignAdapter extends BaseQuickAdapter<SignBean,BaseViewHolder>{

        public MySignAdapter(@Nullable List<SignBean> data) {
            super(R.layout.item_new_sign_view, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, SignBean item) {

            SmallSignView view=helper.getView(R.id.ssv_sign_view);

            view.setAwardText("+"+item.getSignAward());
            view.setIndicatorText(item.getContinueDays());
            view.setChecked(item.isSign());
        }
    }

    public void refreshData(List<SignBean> mData){
        mMySignAdapter.replaceData(mData);
    }

    public static class SignBean{

        public boolean isSign;
        public String continueDays;
        public String signAward;

        public SignBean(boolean isSign, String continueDays, String signAward) {
            this.isSign = isSign;
            this.continueDays = continueDays;
            this.signAward = signAward;
        }

        public boolean isSign() {
            return isSign;
        }

        public void setSign(boolean sign) {
            isSign = sign;
        }

        public String getContinueDays() {
            return continueDays;
        }

        public void setContinueDays(String continueDays) {
            this.continueDays = continueDays;
        }

        public String getSignAward() {
            return signAward;
        }

        public void setSignAward(String signAward) {
            this.signAward = signAward;
        }
    }

    /**
     * 更新签到的列表页面；
     */
    public  void updateSign(){

        List<SignBean> data = mMySignAdapter.getData();

        for (int i=0;i<data.size();i++){
            //找到第一个false为bean对象，然后将其置为null;
            SignBean signBean = data.get(i);
            if (!signBean.isSign) {
                signBean.isSign=true;
                mMySignAdapter.notifyItemChanged(i);
                break;
            }
        }
    }


}
