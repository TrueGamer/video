package com.huanxi.renrentoutiao.ui.fragment.picture;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;
import com.huanxi.renrentoutiao.model.bean.PictureTabBean;
import com.huanxi.renrentoutiao.net.api.video.ApiVedioList;
import com.huanxi.renrentoutiao.net.bean.news.HomeTabBean;
import com.huanxi.renrentoutiao.net.bean.video.ResVideoList;
import com.huanxi.renrentoutiao.ui.activity.other.MainActivity;
import com.huanxi.renrentoutiao.ui.adapter.PictureListAdapter;
import com.huanxi.renrentoutiao.ui.adapter.bean.PictureBean;
import com.huanxi.renrentoutiao.ui.adapter.bean.PictureImageBean;
import com.huanxi.renrentoutiao.ui.adapter.bean.VideoBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.VideoListAdapter;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.custom.CustomBanner20_3Bean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.custom.CustomBigBannerBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.custom.CustomLeftTitlRightImgBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.custom.CustomTitleDownThreeImgBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.custom.CustomUpTitleDownImgBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.gdt.GDTImgAds;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.gdt.GdtBigBannerBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.ta.TaUpTitleDownImgAds;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.ta.TaUpTitleDownImgBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.video.VideoListBean;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseLoadingRecyclerViewFragment;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;
import com.zhxu.library.utils.SystemUtils;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Dinosa on 2018/1/15.
 * <p>
 * 封装抽取；
 */

public class PictureTabFragment extends BaseLoadingRecyclerViewFragment {


    public static final String TAB_BEAN = "tabBean";

    private HomeTabBean mHomeTabBean;
    private List<PictureBean> pictureBeanList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            Object object = getArguments().getSerializable(TAB_BEAN);
            if (object != null) {
                mHomeTabBean = ((HomeTabBean) object);
            }
        }
    }

    @Override
    protected void initView() {
        super.initView();
    }

    private void getDataFromNet(final boolean isFirst) {

//        HashMap<String, String> paramsMap = new HashMap<>();
//        paramsMap.put(ApiVedioList.CATEGORY, mHomeTabBean.getCode());
//        paramsMap.put("device_id", SystemUtils.getIMEI(getActivity()));
//        paramsMap.put("device_platform", "android");
//        paramsMap.put("device_type", SystemUtils.getSystemModel());
//        paramsMap.put("device_brand", SystemUtils.getDeviceBrand());
//        paramsMap.put("os_api", SystemUtils.getSystemApi());
//        paramsMap.put("os_version", SystemUtils.getSystemVersion());
//        paramsMap.put("uuid", SystemUtils.getIMEI(getActivity()));
//        paramsMap.put("openudid", SystemUtils.getOpenUid(getActivity()));
//        paramsMap.put("resolution", SystemUtils.getResolution(getActivity()));
//        paramsMap.put("dpi", SystemUtils.getDensity(getActivity()));
//        paramsMap.put(ApiVedioList.PAGE_NUM, "1");
//
//        ApiVedioList apiVedioList = new ApiVedioList(getRefreshListener(isFirst), paramsMap, ((RxAppCompatActivity) getActivity()));
//
//        HttpManager.getInstance().doHttpDeal(apiVedioList);
        String url = "https://route.showapi.com/852-2?page=1&showapi_appid=70466&showapi_test_draft=false&showapi_timestamp=" +
                "%s&type=%s&showapi_sign=72cadafa49aa432191a8db13ed4d2e0c";
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(String.format(url , dateString , mHomeTabBean.getCode()))
                .get()//默认就是GET请求，可以不写
                .build();
        Log.i("info" , "url22="+String.format(url , dateString , mHomeTabBean.getCode()));
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("info" , "url===="+e.toString());
                showFaild();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i("info" , "picList===="+result);
                try {
                    JSONObject object = new JSONObject(result);
                    int code = object.optInt("showapi_res_code");
                    JSONObject showapi_res_body = object.optJSONObject("showapi_res_body");
                    JSONObject pagebean = showapi_res_body.optJSONObject("pagebean");
                    int allNum = pagebean.optInt("allNum");
                    int allPages = pagebean.optInt("allPages");
                    int currentPage = pagebean.optInt("currentPage");

                    JSONArray jsonArray = pagebean.optJSONArray("contentlist");
                    if(jsonArray != null && jsonArray.length()>0) {
                        pictureBeanList.clear();
                        for(int i=0;i<jsonArray.length();i++) {
                            PictureBean pictureBean = new Gson().fromJson(jsonArray.getJSONObject(i).toString()
                                    , PictureBean.class);

                            pictureBeanList.add(pictureBean);
                        }
                    }

                    if (isFirst) {
                        if (pictureBeanList != null && pictureBeanList.size() > 0) {
                            showSuccess();
                        } else {
                            showEmpty();
                        }

                    } else {
                        refreshComplete();
                        if (pictureBeanList.size() > 0) {
                            ((MainActivity) getBaseActivity()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ((MainActivity) getBaseActivity()).getPictureFragment().showRefreshBanner(pictureBeanList.size());
                                }
                            });
                        }
                    }

                    if (pictureBeanList != null && pictureBeanList.size() > 0) {
                        mPage = 2;
                        ((MainActivity) getBaseActivity()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pictureListAdapter.replaceData(filterData(pictureBeanList));
                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    /**
     * 这里我们要做的一个操作就是显示过滤数据的一个操作；
     *
     * @param list
     * @return
     */
    private List<MultiItemEntity> filterData(List<PictureBean> list) {

        ArrayList<MultiItemEntity> multiItemEntities = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (PictureBean pictureBean : list) {
//                if (pictureBean.isVideo()) {
                    multiItemEntities.add(getVideo(pictureBean));
//                }
            }
        }

        return multiItemEntities;
    }

    private MultiItemEntity getVideo(PictureBean pictureBean) {

        //这里表示是视频的逻辑
        MultiItemEntity multiItemEntity = null;

        /**
         * private String title;
         private String ct;
         private String typeName;
         private String itemId;
         private int type;
         private List<PictureImageBean> list;
         */
        String title = pictureBean.getTitle();
        String ct = pictureBean.getCt();
        String typeName = pictureBean.getTypeName();
        String imageUrl = "";
        String itemId = pictureBean.getItemId();
        int type = pictureBean.getType();
        List<PictureImageBean> list = pictureBean.getList();

        try {
            if(pictureBean.getList() != null) {
                imageUrl = pictureBean.getList().get(0).getMiddle();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        multiItemEntity = new PictureBean(title , ct , typeName , itemId ,type , list);

        return multiItemEntity;
    }

    @Override
    protected void onRetry() {
        super.onRetry();
        //之类我们执行相应的逻辑；
        getDataFromNet(true);
    }

    ///=========进行一个抽取=================
    private PictureListAdapter pictureListAdapter;

    @Override
    public RecyclerView.Adapter getAdapter() {
        if (pictureListAdapter == null) {
            pictureListAdapter = new PictureListAdapter(null);
        }
        return pictureListAdapter;
    }

    @Override
    public void requestAdapterData(boolean isFirst) {
        getDataFromNet(isFirst);
    }

    protected int mPage = 1;

    @Override
    public void requestNextAdapterData() {
        //请求更多数据；
        String url = "https://route.showapi.com/852-2?page=%s&showapi_appid=70466&showapi_test_draft=false&showapi_timestamp=" +
                "%s&type=%s&showapi_sign=72cadafa49aa432191a8db13ed4d2e0c";
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        Log.i("info" , "url33="+String.format(url , mPage+"" ,  dateString , mHomeTabBean.getCode()));
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(String.format(url , mPage+"" , dateString , mHomeTabBean.getCode()))
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadMoreComplete();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i("info" , "picList===="+result);
                try {
                    JSONObject object = new JSONObject(result);
                    int code = object.optInt("showapi_res_code");
                    JSONObject showapi_res_body = object.optJSONObject("showapi_res_body");
                    JSONObject pagebean = showapi_res_body.optJSONObject("pagebean");
                    int allNum = pagebean.optInt("allNum");
                    int allPages = pagebean.optInt("allPages");
                    int currentPage = pagebean.optInt("currentPage");

                    JSONArray jsonArray = pagebean.optJSONArray("contentlist");
                    pictureBeanList.clear();
                    if(jsonArray != null && jsonArray.length()>0) {
                        for(int i=0;i<jsonArray.length();i++) {
                            PictureBean pictureBean = new Gson().fromJson(jsonArray.getJSONObject(i).toString()
                                    , PictureBean.class);

                            pictureBeanList.add(pictureBean);
                        }
                    }

                    if (pictureBeanList != null) {
                        if (pictureBeanList.size() > 0) {
                            mPage++;
                        }
                        ((MainActivity) getBaseActivity()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pictureListAdapter.addData(filterData(pictureBeanList));
                            }
                        });
                    }
                    loadMoreComplete();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 获取HomeTabFragment
     *
     * @param bean 传入的对象；
     * @return 一个HomeTabFragment;
     */
    public static PictureTabFragment getPictureTabFragment(HomeTabBean bean) {
        PictureTabFragment homeTabFragment = new PictureTabFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAB_BEAN, bean);
        homeTabFragment.setArguments(bundle);
        return homeTabFragment;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
