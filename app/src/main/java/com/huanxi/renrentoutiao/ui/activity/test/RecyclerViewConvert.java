package com.huanxi.renrentoutiao.ui.activity.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
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

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Dinosa on 2018/3/3.
 * <p>
 * RecyclerView+NestedRecyclerView
 */

public class RecyclerViewConvert extends BaseTitleActivity {


    @BindView(R.id.rv_recycler)
    RecyclerView mRvRecycler;

    private DemoAdapter mDemoAdapter;

    @Override
    public int getBodyLayoutId() {
        return R.layout.activity_recyler_view_convert;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        super.initView(rootView, savedInstanceState);
        setBackText("");
        setTitle("demo");

        ((NestedScrollView) findViewById(R.id.scroll_view)).setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                System.out.println("childCount: "+ mRvRecycler.getChildCount());
            }
        });
    }


    @Override
    protected void initData() {
        super.initData();

        mRvRecycler.setLayoutManager(new LinearLayoutManager(this));


        ArrayList<MyBean> myBeen = new ArrayList<>();
        myBeen.add(new MyBean());
        myBeen.add(new MyBean());
        myBeen.add(new MyBean());
        myBeen.add(new MyBean());
        myBeen.add(new MyBean());
        myBeen.add(new MyBean());
        myBeen.add(new MyBean());
        myBeen.add(new MyBean());
        myBeen.add(new MyBean());
        myBeen.add(new MyBean());
        myBeen.add(new MyBean());
        myBeen.add(new MyBean());
        myBeen.add(new MyBean());
        myBeen.add(new MyBean());

        mDemoAdapter = new DemoAdapter(myBeen);

        mRvRecycler.setAdapter(mDemoAdapter);
    }


    public class DemoAdapter extends BaseQuickAdapter<MyBean, BaseViewHolder> {

        List<View> mViews = new ArrayList<>();

        public DemoAdapter(@Nullable List<MyBean> data) {
            super(R.layout.item_home_img_three, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MyBean item) {
            mViews.add(helper.itemView);
        }

        public void addView() {

            View view = mViews.get(0);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtil.dip2px(RecyclerViewConvert.this, 200));
            TextView textView = new TextView(RecyclerViewConvert.this);
            textView.setLayoutParams(layoutParams);
            textView.setText("3232323232");
            ((ViewGroup) view).addView(textView);

        }

    }

    public class MyBean {
    }
}
