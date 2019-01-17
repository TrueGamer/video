package com.huanxi.renrentoutiao.ui.media.home.tab;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.huanxi.renrentoutiao.globle.ConstantUrl;
import com.huanxi.renrentoutiao.model.bean.media.MediaWendaBean;
import com.huanxi.renrentoutiao.model.bean.media.MultiMediaArticleBean;
import com.huanxi.renrentoutiao.net.api.mediao.IMobileMediaApi;
import com.huanxi.renrentoutiao.net.api.mediao.INewsApi;
import com.huanxi.renrentoutiao.ui.media.MediaUserBean;
import com.huanxi.renrentoutiao.ui.media.TTMediaBean;
import com.huanxi.renrentoutiao.utils.Constant;
import com.huanxi.renrentoutiao.utils.ErrorAction;
import com.huanxi.renrentoutiao.utils.RetrofitFactory;
import com.huanxi.renrentoutiao.utils.TimeUtil;
import com.huanxi.renrentoutiao.utils.ToutiaoUtil;
import com.zhxu.library.RxRetrofitApp;
import com.zhxu.library.http.ParamsInterceptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Meiji on 2017/7/1.
 */

public class MediaTabPresenter implements IMediaProfile.Presenter {

    static final int TYPE_ARTICLE = 0;
    static final int TYPE_VIDEO = 1;
    static final int TYPE_WENDA = 2;
    private static final String TAG = "MediaTabPresenter";
    private IMediaProfile.View view;
    private String mediaId;
    private String userId;
    private String articleTime;
    private String videoTime;
    private int wendatotal;
    private String wendaCursor;
    private List<TTMediaBean> articleList = new ArrayList<>();
    private List<MultiMediaArticleBean.DataBean> videoList = new ArrayList<>();
    private List<MediaWendaBean.AnswerQuestionBean> wendaList = new ArrayList<>();

    MediaTabPresenter(IMediaProfile.View view) {
        this.view = view;
        this.articleTime = TimeUtil.getCurrentTimeStamp();
        this.videoTime = TimeUtil.getCurrentTimeStamp();
    }

    @Override
    public void doRefresh() {

    }

    @Override
    public void doRefresh(int type) {
        switch (type) {
            case TYPE_ARTICLE:
                if (articleList.size() > 0) {
                    articleList.clear();
                    articleTime = TimeUtil.getCurrentTimeStamp();
                }
                doLoadArticle();
                break;
            case TYPE_VIDEO:
                if (videoList.size() > 0) {
                    videoList.clear();
                    videoTime = TimeUtil.getCurrentTimeStamp();
                }
                doLoadVideo();
                break;
            case TYPE_WENDA:
                if (wendaList.size() > 0) {
                    wendaList.clear();
                }
                doLoadWenda();
                break;
        }
    }

    @Override
    public void doShowNetError() {
        view.onHideLoading();
        view.onShowNetError();
    }

    @Override
    public void doLoadArticle(String... mediaId) {
        try {
            if (TextUtils.isEmpty(this.mediaId)) {
                this.mediaId = mediaId[0];
                this.userId = mediaId[1];
            }
        } catch (Exception e) {
            ErrorAction.print(e);
        }
        Map<String, String> map = ToutiaoUtil.getAsCp();

//        RetrofitFactory.getRetrofit().create(IMobileMediaApi.class)
//                .getMediaArticle(this.mediaId, this.articleTime, map.get(Constant.AS), map.get(Constant.CP))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
////                .as(view.bindAutoDispose())
//                .subscribe(bean -> {
//                    articleTime = bean.getNext().getMax_behot_time();
//                    List<MultiMediaArticleBean.DataBean> list = bean.getData();
//                    if (null != list && list.size() > 0) {
//                        doSetAdapter(list);
//                    } else {
//                        doShowNoMore();
//                    }
//                }, throwable -> {
//                    doShowNetError();
//                    ErrorAction.print(throwable);
//                });
        Gson gson = new Gson();
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(String.format(ConstantUrl.MediaDetailURL , this.mediaId , userId))
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                doShowNetError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i("info" , "MediaDetailURL="+result);
                try {
                    JSONObject obj = new JSONObject(result);
                    int code = obj.optInt("code");
                    if(code == 200) {
                        JSONObject jsonObject = obj.optJSONObject("data");

                        MediaUserBean userBean = gson.fromJson(jsonObject.toString() , MediaUserBean.class);
                        List<TTMediaBean> list = userBean.getNewsList();

                        if(list.size() > 0) {
                            doSetAdapter(list);
                        } else {
//                            doShowNoMore();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void doLoadVideo(String... mediaId) {
        try {
            if (TextUtils.isEmpty(this.mediaId)) {
                this.mediaId = mediaId[0];
            }
        } catch (Exception e) {
            ErrorAction.print(e);
        }
        Map<String, String> map = ToutiaoUtil.getAsCp();

//        RetrofitFactory.getRetrofit().create(IMobileMediaApi.class)
//                .getMediaVideo(this.mediaId, this.videoTime, map.get(Constant.AS), map.get(Constant.CP))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
////                .as(view.bindAutoDispose())
//                .subscribe(bean -> {
//                    videoTime = bean.getNext().getMax_behot_time();
//                    List<MultiMediaArticleBean.DataBean> list = bean.getData();
//                    if (null != list && list.size() > 0) {
//                        doSetAdapter(list);
//                    } else {
//                        doShowNoMore();
//                    }
//                }, throwable -> {
//                    doShowNetError();
//                    ErrorAction.print(throwable);
//                });
    }

    @Override
    public void doLoadWenda(String... mediaId) {
        try {
            if (TextUtils.isEmpty(this.mediaId)) {
                this.mediaId = mediaId[0];
            }
        } catch (Exception e) {
            ErrorAction.print(e);
        }
        RetrofitFactory.getRetrofit().create(IMobileMediaApi.class)
                .getMediaWenda(this.mediaId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .as(view.bindAutoDispose())
                .subscribe(bean -> {
                    wendatotal = bean.getTotal();
                    wendaCursor = bean.getCursor();
                    List<MediaWendaBean.AnswerQuestionBean> list = bean.getAnswer_question();
                    if (null != list && list.size() > 0) {
                        doSetWendaAdapter(list);
                    } else {
                        doShowNoMore();
                    }
                }, throwable -> {
                    doShowNetError();
                    ErrorAction.print(throwable);
                });
    }

    @Override
    public void doLoadMoreData(int type) {
        switch (type) {
            case TYPE_ARTICLE:
                doLoadArticle();
                break;
            case TYPE_VIDEO:
                doLoadVideo();
                break;
            case TYPE_WENDA:

                break;
        }
    }

    @Override
    public void doSetAdapter(List<TTMediaBean> list) {
        articleList.addAll(list);
        view.onSetAdapter(articleList);
        view.onHideLoading();
    }

    @Override
    public void doSetWendaAdapter(List<MediaWendaBean.AnswerQuestionBean> list) {
        wendaList.addAll(list);
        view.onSetAdapter(wendaList);
        view.onHideLoading();
    }

    @Override
    public void doShowNoMore() {
        view.onHideLoading();
        view.onShowNoMore();
    }
}
