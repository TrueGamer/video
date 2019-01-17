package com.huanxi.renrentoutiao.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.net.bean.news.HomeTabBean;
import com.huanxi.renrentoutiao.ui.adapter.ItemDragHelperCallBack;
import com.huanxi.renrentoutiao.ui.adapter.NewAdapter;
import com.huanxi.renrentoutiao.ui.adapter.OnChannelListener;
import com.zhxu.library.download.DownInfo;
import com.zhxu.library.download.HttpDownManager;
import com.zhxu.library.listener.HttpDownOnNextListener;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity implements OnChannelListener{


    List<HomeTabBean> mSelectedDatas=new ArrayList<>();    //选中的数据
    List<HomeTabBean> mUnSelectedDatas=new ArrayList<>();  //没有选中的数据；

    List<HomeTabBean> mDatas=new ArrayList<>();
    private RecyclerView mRecyclerView;

    NewAdapter mAdapter;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_tab_menu);


        findViewById(R.id.btn_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               download();
            }
        });



        mRecyclerView = ((RecyclerView) findViewById(R.id.rv_recyclerView));

        findViewById(R.id.icon_collapse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里我们是需要输出list的内容的；
                System.out.println("selectedDatas: "+mSelectedDatas);
                System.out.println("unSelectedDatas: "+mUnSelectedDatas);

                for (HomeTabBean homeTabBean : mAdapter.getData()) {
                    //这里
                    if (homeTabBean.getItemType()== HomeTabBean.TYPE_MY_CHANNEL) {
                        System.out.println("selected: "+homeTabBean);
                    }else if(homeTabBean.getItemType() == HomeTabBean.TYPE_OTHER_CHANNEL){
                        System.out.println("unselected: "+homeTabBean);
                    }
                }
            }
        });

        HomeTabBean recommand = new HomeTabBean("推荐");
        recommand.setChannelType(1);
        mSelectedDatas.add(recommand);
        mSelectedDatas.add(new HomeTabBean("武汉"));
        mSelectedDatas.add(new HomeTabBean("北京"));
        mSelectedDatas.add(new HomeTabBean("美国"));
        mSelectedDatas.add(new HomeTabBean("233"));
        mUnSelectedDatas.add(new HomeTabBean("军事"));
        mUnSelectedDatas.add(new HomeTabBean("电影"));
        mUnSelectedDatas.add(new HomeTabBean("美女"));
        mUnSelectedDatas.add(new HomeTabBean("漫画"));
        mUnSelectedDatas.add(new HomeTabBean("电影"));

        processLogic();
    }

    private void download() {
        String url="";
        DownInfo downInfo = new DownInfo(url, new HttpDownOnNextListener() {
            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void updateProgress(long readLength, long countLength) {

            }
        });
        HttpDownManager.getInstance().startDown(downInfo);
    }


    private void processLogic() {
        HomeTabBean channel = new HomeTabBean("我的频道");
        channel.setItemtype(HomeTabBean.TYPE_MY);
        mDatas.add(channel);

        setDataType(mSelectedDatas, HomeTabBean.TYPE_MY_CHANNEL);
        setDataType(mUnSelectedDatas, HomeTabBean.TYPE_OTHER_CHANNEL);
        mDatas.addAll(mSelectedDatas);

        HomeTabBean morechannel = new HomeTabBean("频道推荐");
        morechannel.setItemtype(HomeTabBean.TYPE_OTHER);
        mDatas.add(morechannel);

        mDatas.addAll(mUnSelectedDatas);

        ItemDragHelperCallBack callback = new ItemDragHelperCallBack(this);
        final ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecyclerView);

        mAdapter = new NewAdapter(mDatas, helper);
        GridLayoutManager manager = new GridLayoutManager(mContext, 4);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int itemViewType = mAdapter.getItemViewType(position);
                return itemViewType == HomeTabBean.TYPE_MY_CHANNEL || itemViewType == HomeTabBean.TYPE_OTHER_CHANNEL ? 1 : 4;
            }
        });
        mAdapter.OnChannelListener(this);
    }


    private void setDataType(List<HomeTabBean> datas, int type) {
        for (int i = 0; i < datas.size(); i++) {
            datas.get(i).setItemtype(type);
        }
    }

    private OnChannelListener onChannelListener;

    public void setOnChannelListener(OnChannelListener onChannelListener) {
        this.onChannelListener = onChannelListener;
    }

    @Override
    public void onItemMove(int starPos, int endPos) {
        if (starPos < 0 || endPos < 0) return;
        if (mDatas.get(endPos).getTitle().equals("推荐")) return;
        //我的频道之间移动
        if (onChannelListener != null)
            onChannelListener.onItemMove(starPos - 1, endPos - 1);//去除标题所占的一个index
        onMove(starPos, endPos, false);
    }
    private String firstAddChannelName = "";

    private void onMove(int starPos, int endPos, boolean isAdd) {
        //isUpdate = true;
        HomeTabBean startChannel = mDatas.get(starPos);
        //先删除之前的位置
        mDatas.remove(starPos);
        //添加到现在的位置
        mDatas.add(endPos, startChannel);
        mAdapter.notifyItemMoved(starPos, endPos);
        if (isAdd) {
            if (TextUtils.isEmpty(firstAddChannelName)) {
                firstAddChannelName = startChannel.getTitle();
            }
        } else {
            if (startChannel.getTitle().equals(firstAddChannelName)) {
                firstAddChannelName = "";
            }
        }
    }

    @Override
    public void onMoveToMyChannel(int starPos, int endPos) {
        onMove(starPos, endPos, true);
    }

    @Override
    public void onMoveToOtherChannel(int starPos, int endPos) {
        onMove(starPos, endPos, false);
    }

    @Override
    public void onFinish(String selectedChannelName) {
        //这里表示，我们点击我们选中的列表的数据；
        System.out.println("selectedChannelName: "+selectedChannelName);
    }
}
