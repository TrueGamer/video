package com.huanxi.renrentoutiao.ui.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.net.bean.news.HomeTabBean;
import com.huanxi.renrentoutiao.ui.adapter.ItemDragHelperCallBack;
import com.huanxi.renrentoutiao.ui.adapter.NewAdapter;
import com.huanxi.renrentoutiao.ui.adapter.OnChannelListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dinosa on 2018/1/15.
 * home页面的tab选择的Menu菜单；
 */

public class TabMenuView extends FrameLayout implements OnChannelListener {


    List<HomeTabBean> mSelectedDatas = new ArrayList<>();    //选中的数据
    List<HomeTabBean> mUnSelectedDatas = new ArrayList<>();  //没有选中的数据；

    List<HomeTabBean> mDatas = new ArrayList<>();
    private RecyclerView mRecyclerView;

    NewAdapter mAdapter;
    private Context mContext;

    private boolean isUpdate = false;
    private NewAdapter.onEditListener mOnEditListener;
    private VeticalDrawerLayout mDrawerLayout;
    private boolean mIsClickMyChannel;

    public TabMenuView(@NonNull Context context) {
        this(context, null);
    }

    public TabMenuView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabMenuView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        //将View加载进去；
        LayoutInflater.from(context).inflate(R.layout.view_tab_menu, this);

        mRecyclerView = ((RecyclerView) findViewById(R.id.rv_recyclerView));

        ((ScrollView) findViewById(R.id.my_scroll_view)).smoothScrollTo(0, 20);


        findViewById(R.id.icon_collapse).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里我们要执行的一个操作回掉；
                mIsClickMyChannel=true;
                close();
                if (mDrawerLayout != null) {
                    mDrawerLayout.open();
                }
            }
        });

        processLogic();
    }

    /**
     * 这里我们要做的一个操作就是模拟关闭的逻辑
     */
    public void close() {
        if (mOnTabMenuChangeListener != null) {
            mOnTabMenuChangeListener.onClickClose(mAdapter.getData(), isUpdate);

        }
    }

    /**
     * 这里是设置viticalDrawerlayout，然后通过这个来打开和关闭；
     *
     * @param drawerLayout
     */
    public void setVeticalDrawerLayout(VeticalDrawerLayout drawerLayout) {
        mDrawerLayout = drawerLayout;

        if (mDrawerLayout != null) {
            mDrawerLayout.setOnDragStatusChange(new VeticalDrawerLayout.OnDragStatusChange() {
                @Override
                public void onOpen() {
                    //这里我们就是，如果是点击进入渠道关闭的，我们就不回掉操作；
                    if (!mIsClickMyChannel) {
                        close();
                    }
                }

                @Override
                public void onClose() {

                }

                @Override
                public void OnDrag(float percent) {

                }
            });
        }
    }


    public void setOnEditListener(NewAdapter.onEditListener onEditListener) {
        mOnEditListener = onEditListener;
    }

    public void refreshList(List<HomeTabBean> selectedDatas, List<HomeTabBean> unSelectedDatas) {
        mDatas.clear();
        isUpdate = false;
        mIsClickMyChannel=false;
        mSelectedDatas = selectedDatas;
        mUnSelectedDatas = unSelectedDatas;
        processLogic();


        if (mDrawerLayout != null) {
            mDrawerLayout.close();
        }
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
        mAdapter.setOnEditListener(mOnEditListener);

        GridLayoutManager manager = new GridLayoutManager(mContext, 4) {

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
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
        if (mDatas.get(endPos).getTitle().equals("头条")) return;
        //我的频道之间移动
        if (onChannelListener != null)
            onChannelListener.onItemMove(starPos - 1, endPos - 1);//去除标题所占的一个index
        onMove(starPos, endPos, false);
    }

    private String firstAddChannelName = "";

    private void onMove(int starPos, int endPos, boolean isAdd) {
        isUpdate = true;
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
        //这里表示选中了我的渠道的某一个；
        List<HomeTabBean> selectedList = getSelectedList();

        for (int i = 0; i < selectedList.size(); i++) {
            HomeTabBean homeTabBean = selectedList.get(i);
            if (homeTabBean.getTitle().equals(selectedChannelName)) {
                if (mOnTabMenuChangeListener != null) {

                    mIsClickMyChannel = true;
                    mOnTabMenuChangeListener.onClickMyChannel(homeTabBean, mAdapter.getData(), isUpdate);
                    if (mDrawerLayout != null) {
                        mDrawerLayout.open();
                    }
                }
                break;
            }
        }
    }

    /**
     * 获取选中的渠道；
     *
     * @return
     */
    public List<HomeTabBean> getSelectedList() {

        ArrayList<HomeTabBean> selectTabsBean = new ArrayList<>();

        if (mAdapter.getData() != null) {
            for (HomeTabBean homeTabBean : mAdapter.getData()) {

                if (homeTabBean.getItemType() == HomeTabBean.TYPE_MY_CHANNEL) {
                    selectTabsBean.add(homeTabBean);
                }
            }
        }
        return selectTabsBean;
    }

    public OnTabMenuViewChangeListener mOnTabMenuChangeListener;

    public void setOnTabMenuChangeListener(OnTabMenuViewChangeListener onTabMenuChangeListener) {
        mOnTabMenuChangeListener = onTabMenuChangeListener;
    }

    /**
     * 这里我们暴露出去提供回掉，操作；
     */
    public interface OnTabMenuViewChangeListener {

        /**
         * 这里表示点击了关闭的一个操作；
         *
         * @param allTabBean 这里返回所有频道的集合；
         */
        public void onClickClose(List<HomeTabBean> allTabBean, boolean isUpdate);

        /**
         * 这里表示点击我的频道的时候事件回掉；
         *
         * @param tabBean    选中的频道
         * @param allTabBean 全部的频道集合
         */
        public void onClickMyChannel(HomeTabBean tabBean, List<HomeTabBean> allTabBean, boolean isUpdate);
    }

}
