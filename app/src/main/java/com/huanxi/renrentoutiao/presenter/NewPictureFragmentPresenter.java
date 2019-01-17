package com.huanxi.renrentoutiao.presenter;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.huanxi.renrentoutiao.net.bean.news.HomeTabBean;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.activity.other.MainActivity;
import com.huanxi.renrentoutiao.ui.fragment.picture.PictureFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Dinosa on 2018/2/2.
 *
 * 这里是VideoFragment的一个业务逻辑类；
 */

public class NewPictureFragmentPresenter extends MenuTabPresenter{

    public MainActivity mBaseActivity;
    public PictureFragment mPictureFragment;
    final ArrayList<HomeTabBean> selectedTabs = new ArrayList<>();

    public NewPictureFragmentPresenter(BaseActivity baseActivity) {
        super(baseActivity);
        mBaseActivity= ((MainActivity) baseActivity);
        mPictureFragment= mBaseActivity.getPictureFragment();
    }


    /**
     * 这里要向服务器请求tab的数据；
     */
    public void requestNetFromTabData() {
        //这里模拟请求，得到Tab类型；

//        String url = "https://route.showapi.com/852-1?showapi_appid=70466&showapi_test_draft=false&showapi_timestamp=" +
//                "%s&showapi_sign=72cadafa49aa432191a8db13ed4d2e0c";
//        Date currentTime = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//        String dateString = formatter.format(currentTime);
//        OkHttpClient okHttpClient = new OkHttpClient();
//        final Request request = new Request.Builder()
//                .url(String.format(url , dateString))
//                .get()//默认就是GET请求，可以不写
//                .build();
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.i("info" , "url===="+e.toString());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String result = response.body().string();
//                try {
//                    JSONObject object = new JSONObject(result);
//                    int code = object.optInt("showapi_res_code");
//                    JSONObject showapi_res_body = object.optJSONObject("showapi_res_body");
//                    JSONArray jsonArray = showapi_res_body.optJSONArray("list");
//                    if(jsonArray != null && jsonArray.length()>0) {
//                        for(int i=0;i<jsonArray.length();i++) {
//                            JSONObject obj = jsonArray.getJSONObject(i);
//                            String name = obj.optString("name");
//                            if("社会百态".equals(name)) {
//                                JSONArray array = obj.optJSONArray("list");
//                                for(int j=0;j<array.length();j++) {
//                                    JSONObject obj2 = array.getJSONObject(j);
////                                PictureTabBean tabBean = new PictureTabBean();
//                                    HomeTabBean homeTabBean = new HomeTabBean(name);
//                                    homeTabBean.setTitle(obj2.optString("name"));
//                                    homeTabBean.setCode(obj2.optString("id"));
//                                    homeTabBean.setItemtype(HomeTabBean.TYPE_MY_CHANNEL);
////                                tabBean.setId(obj2.optInt("id"));
//                                    selectedTabs.add(homeTabBean);
//                                }
//                            }
//                        }
//                    }
//
//                    new Thread() {
//                        @Override
//                        public void run() {
//                            //这里写入子线程需要做的工作
//                            handler.sendMessage(handler.obtainMessage(1));
//                        }
//                    }.start();
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });

        // 萌宠  6002  、美丽风景  6004、中国男明星  2004、箱包 5003
        HomeTabBean homeTabBean = new HomeTabBean("美图");
        homeTabBean.setTitle("萌宠");
        homeTabBean.setCode("6002");
        homeTabBean.setItemtype(HomeTabBean.TYPE_MY_CHANNEL);

        HomeTabBean homeTabBean2 = new HomeTabBean("美图");
        homeTabBean2.setTitle("美丽风景");
        homeTabBean2.setCode("6004");
        homeTabBean2.setItemtype(HomeTabBean.TYPE_MY_CHANNEL);

//        HomeTabBean homeTabBean3 = new HomeTabBean("美图");
//        homeTabBean3.setTitle("中国男明星");
//        homeTabBean3.setCode("2004");
//        homeTabBean3.setItemtype(HomeTabBean.TYPE_MY_CHANNEL);

        HomeTabBean homeTabBean4 = new HomeTabBean("美图");
        homeTabBean4.setTitle("箱包");
        homeTabBean4.setCode("5003");
        homeTabBean4.setItemtype(HomeTabBean.TYPE_MY_CHANNEL);

        selectedTabs.add(homeTabBean);
        selectedTabs.add(homeTabBean2);
//        selectedTabs.add(homeTabBean3);
        selectedTabs.add(homeTabBean4);
        new Thread() {
            @Override
            public void run() {
                //这里写入子线程需要做的工作
                handler.sendMessage(handler.obtainMessage(1));
            }
        }.start();
    }

    private android.os.Handler handler = new android.os.Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mPictureFragment.updateTabs(selectedTabs ,getTabTitles(selectedTabs));
                    updateAllTabs(selectedTabs);
                    break;
                default:
                    break;
            }
        }
    };

//    private Handler handler = new Handler(new Handler.Callback(){
//
//        @Override
//        public boolean handleMessage(Message msg) {
//            mPictureFragment.updateTabs(selectedTabs ,getTabTitles(selectedTabs));
//            updateAllTabs(selectedTabs);
//            return false;
//        }
//
//    });

}
