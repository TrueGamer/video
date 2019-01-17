package com.huanxi.renrentoutiao.ui.activity.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.activity.base.BaseTitleActivity;
import com.huanxi.renrentoutiao.ui.adapter.AdsAdapter;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Dinosa on 2018/3/3.
 *
 * 这里我们发现一个问题就是RecyclerView包裹内容的时候加载广告是有问题的；
 */

public class RecyclerViewDemo extends BaseTitleActivity {



    @BindView(R.id.rv_ads)
    public RecyclerView mRvAds;
    private DemoAdapter mDemoAdapter;

    @Override
    public int getBodyLayoutId() {
        return R.layout.activity_recycler_view_demo;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        super.initView(rootView, savedInstanceState);
        setBackText("");
        setTitle("demo");

        findViewById(R.id.btn_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDemoAdapter.addView();
            }
        });
    }


    AdsAdapter mAdsAdapter;

    @Override
    protected void initData() {
        super.initData();


       /* //我们添加一个AD
        List<AdBean> tasklist = new ArrayList<>();
        AdBean adBean = new AdBean();
        adBean.setType(AdBean.TYPE_GDT);
        adBean.setAdtype(AdBean.TYPE_GDT_UP_TEXT_DOWN_IMG);
        tasklist.add(adBean);

        //这里我们使用一个广点通的
        AdsAdapter ads2Adapter = new AdsAdapter(tasklist);
        mRvAds.setLayoutManager(new LinearLayoutManager(this));
        mRvAds.setAdapter(ads2Adapter);*/



        mRvAds.setLayoutManager(new LinearLayoutManager(this){

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

       // ResSplashAds resAds = ((MyApplication) getApplication()).getResAds();

       // mAdsAdapter = new AdsAdapter(resAds.getMy());

        ArrayList<MyBean> myBeen = new ArrayList<>();
        myBeen.add(new MyBean());
        myBeen.add(new MyBean());


        mDemoAdapter = new DemoAdapter(myBeen);

        mRvAds.setAdapter(mDemoAdapter);
    }

    public class DemoAdapter extends BaseQuickAdapter<MyBean,BaseViewHolder>{

        List<View> mViews=new ArrayList<>();

        public DemoAdapter(@Nullable List<MyBean> data) {
            super(R.layout.item_home_img_three, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MyBean item) {
           mViews.add(helper.itemView);
        }

        public void addView(){

            View view = mViews.get(0);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtil.dip2px(RecyclerViewDemo.this, 200));
            TextView textView = new TextView(RecyclerViewDemo.this);
            textView.setLayoutParams(layoutParams);
            textView.setText("3232323232");
            ((ViewGroup) view).addView(textView);

        }

    }

    public class MyBean{}
}
