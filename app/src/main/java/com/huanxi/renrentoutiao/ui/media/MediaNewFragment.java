package com.huanxi.renrentoutiao.ui.media;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.google.gson.Gson;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.globle.ConstantUrl;
import com.huanxi.renrentoutiao.model.bean.UserBean;
import com.huanxi.renrentoutiao.net.bean.news.HomeTabBean;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.media.adapter.HotMediaAdapter;
import com.huanxi.renrentoutiao.ui.media.adapter.TTMediaListAdapter;
import com.huanxi.renrentoutiao.ui.media.home.MediaHomeActivity;
import com.huanxi.renrentoutiao.ui.view.MyGridView;
import com.huanxi.renrentoutiao.ui.view.MyListView;
import com.huanxi.renrentoutiao.utils.TTAdManagerHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MediaNewFragment extends Fragment implements View.OnClickListener{

    private Context mContext;
    private ImageView imgSearch;
    private MyGridView gvHotList;
    private MyListView lvWTTList;
    private SmartRefreshLayout refreshLayout;

    private HotMediaAdapter hotMediaAdapter;
    private List<HotMediaBean> hotMediaBeanList = new ArrayList<>();

    private TTMediaListAdapter ttMediaListAdapter;
    private List<TTMediaBean> ttMediaBeanList = new ArrayList<>();

    private Gson gson;
    private int page = 0;
    private String userId;

    private List<TTFeedAd> mData; // 网盟广告列表
    private TTAdNative mTTAdNative; // 网盟广告

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media_layout , null);

        gson = new Gson();
        UserBean userBean = ((BaseActivity) mContext).getUserBean();
        if(userBean != null) {
            userId = userBean.getUserid();
        }
        initView(view);
        loadMediaList();

        // --- 网盟广告
        TTAdManager ttAdManager = TTAdManagerHolder.getInstance(mContext);
        mTTAdNative = ttAdManager.createAdNative(mContext);
        //申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
        TTAdManagerHolder.getInstance(mContext).requestPermissionIfNecessary(mContext);
        initListView();

        return view;
    }

    /**
     * 初始化网盟广告数据
     */
    private void initListView() {
        mData = new ArrayList<TTFeedAd>();
    }

    /**
     * 初始化页面
     * @param view
     */
    private void initView(View view) {
        imgSearch = view.findViewById(R.id.img_search);
        imgSearch.setOnClickListener(this);

        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 0;
                loadMediaList();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                loadMediaList();
            }
        });

        TextView tv_hot = (TextView) view.findViewById(R.id.tv_hot);
        Drawable hot = getResources().getDrawable(R.drawable.icon_w_hot);
        hot.setBounds(0 , 0 , 45 , 45);
        tv_hot.setCompoundDrawables(hot , null , null , null);

        gvHotList = (MyGridView) view.findViewById(R.id.gv_hotList);
        lvWTTList = (MyListView) view.findViewById(R.id.lv_wTTList);

        HotMediaBean hotMediaBean = new HotMediaBean();
        hotMediaBean.setTitle("#延禧攻略#");
        hotMediaBean.setSubTitle("明玉微访谈");

        HotMediaBean hotMediaBean1 = new HotMediaBean();
        hotMediaBean1.setTitle("#职场问张萌#");
        hotMediaBean1.setSubTitle("职场最新指南");

        HotMediaBean hotMediaBean2 = new HotMediaBean();
        hotMediaBean2.setTitle("#旅行夫妇访谈#");
        hotMediaBean2.setSubTitle("最美的非洲");

        HotMediaBean hotMediaBean3 = new HotMediaBean();
        hotMediaBean3.setTitle("#搞笑段子#");
        hotMediaBean3.setSubTitle("#搞笑段子#");

        hotMediaBeanList.add(hotMediaBean);
        hotMediaBeanList.add(hotMediaBean1);
        hotMediaBeanList.add(hotMediaBean2);
        hotMediaBeanList.add(hotMediaBean3);

        hotMediaAdapter = new HotMediaAdapter(mContext , hotMediaBeanList);
        gvHotList.setAdapter(hotMediaAdapter);

        ttMediaListAdapter = new TTMediaListAdapter(mContext , ttMediaBeanList);
        lvWTTList.setAdapter(ttMediaListAdapter);

        lvWTTList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("info" , "click==============================================");
                // 4377795668   ttMediaBeanList.get(i).getId()
                MediaHomeActivity.launch(ttMediaBeanList.get(i).getUserName() , userId);
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id =view.getId();
        switch (id) {
            case R.id.img_search:

                break;
        }
    }

    /**
     * 加载头条号列表
     */
    private void loadMediaList() {
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(String.format(ConstantUrl.MediaList_URL , page , userId))
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i("info" , "MediaList_URL="+result);
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                if(page == 0) {
                    ttMediaBeanList.clear();
                    refreshLayout.finishRefresh(200);
                } else {
                    refreshLayout.finishLoadMore(200);
                }
                page ++ ;
                try {
                    JSONObject obj = new JSONObject(result);
                    int code = obj.optInt("code");
                    if(code == 200) {
                        JSONArray jsonArray = obj.optJSONArray("data");

                        for(int i=0;i<jsonArray.length();i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            TTMediaBean ttMediaBean = gson.fromJson(object.toString() , TTMediaBean.class);

                            ttMediaBeanList.add(ttMediaBean);
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ttMediaListAdapter.notifyDataSetChanged();
                            }
                        });

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
