package com.huanxi.renrentoutiao.ui.activity.user;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.huanxi.renrentoutiao.MyApplication;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.model.bean.UserBean;
import com.huanxi.renrentoutiao.net.api.user.task.ApiUserSign;
import com.huanxi.renrentoutiao.net.api.user.userInfo.ApiGetUserSign;
import com.huanxi.renrentoutiao.net.bean.news.ResAward;
import com.huanxi.renrentoutiao.net.bean.sign.ResGetSignList;
import com.huanxi.renrentoutiao.ui.activity.base.BaseTitleAndLoadingActivity;
import com.huanxi.renrentoutiao.ui.adapter.AdBean;
import com.huanxi.renrentoutiao.ui.adapter.AdsAdapter;
import com.huanxi.renrentoutiao.ui.dialog.RedPacketDialog;
import com.huanxi.renrentoutiao.ui.view.sign.SignView;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class UserSignActivity extends BaseTitleAndLoadingActivity {

    private SignView mSvSignView;


    public TextView mTvSign;

    //这里签到的里面的RecyclerView；

    @BindView(R.id.rv_ads)
    public RecyclerView mRvAds;

    @BindView(R.id.rv_sign)
    public RecyclerView mRvSign;

    public int mContinueDays =0;
    public boolean mHasSign =false;
    private View mSignHeaderView;

    @Override
    public int getLoadingContentLayoutId() {
        return R.layout.activity_user_sign;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        setStatusBarImmersive(null);
        setTitle("签到");
        setBackText("");

        MyApplication application = (MyApplication) getApplication();

        mRvSign.setLayoutManager(new LinearLayoutManager(this));
        AdsAdapter adsAdapter = new AdsAdapter(null);
        initHeaderView();
        adsAdapter.addHeaderView(mSignHeaderView);
        mRvSign.setAdapter(adsAdapter);


        if(isHasAds()){
            //我们添加一个AD
            List<AdBean> tasklist = new ArrayList<>();
            AdBean adBean = new AdBean();
            adBean.setType(AdBean.TYPE_GDT);
            adBean.setAdtype(AdBean.TYPE_GDT_UP_TEXT_DOWN_IMG);
            tasklist.add(adBean);

            //这里我们使用一个广点通的
            AdsAdapter ads2Adapter = new AdsAdapter(tasklist);
            mRvAds.setLayoutManager(new LinearLayoutManager(this));
            mRvAds.setAdapter(ads2Adapter);
        }
    }

    private void initHeaderView() {

        mSignHeaderView = LayoutInflater.from(this).inflate(R.layout.item_user_sign_header, mRvSign, false);

        mSvSignView = ((SignView) mSignHeaderView.findViewById(R.id.sv_sign_view));
        mTvSign = ((TextView) mSignHeaderView.findViewById(R.id.tv_sign));

        mTvSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSign();
            }
        });
    }


    @Override
    protected void initData() {
        super.initData();
        //这里我们要获取服务器的内容；
        getSignInfo();
    }



    public void onClickSign(){

        UserBean userBean = ((MyApplication) getApplication()).getUserBean();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(ApiUserSign.FROM_UID,userBean.getUserid());

        ApiUserSign apiUserSign=new ApiUserSign(new HttpOnNextListener<ResAward>() {
            @Override
            public void onNext(ResAward resSign) {
                mSvSignView.setCheckedDays(mSvSignView.getCheckedDays()+1);
                mTvSign.setText("已签到");

                RedPacketDialog redPacketDialog=new RedPacketDialog();
                redPacketDialog.show(UserSignActivity.this,resSign.getIntegral()+"");
            }

        },this,hashMap);
        //弹出对话框，显示获取的金币；
        HttpManager.getInstance().doHttpDeal(apiUserSign);
    }

    public void getSignInfo() {

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiGetUserSign.FROM_UID,getUserBean().getUserid());

        ApiGetUserSign apiGetUserSign=new ApiGetUserSign(new HttpOnNextListener<ResGetSignList>() {

            @Override
            public void onNext(ResGetSignList resGetSignList) {
                //这里要做的一个操作就是显示签到信息
                mContinueDays=resGetSignList.getHasday();
                mHasSign=(resGetSignList.getHassign()==1);
                mSvSignView.setCheckedDays(mContinueDays);
                if(mHasSign){
                    //这里我们要做的一个操作就就是显示内容；
                    mTvSign.setText("已签到");
                    mTvSign.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            toast("今天已经签到!");
                        }
                    });
                }
                showSuccess();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                showFaild();
            }
        },this,paramsMap);
        HttpManager.getInstance().doHttpDeal(apiGetUserSign);
    }

}
