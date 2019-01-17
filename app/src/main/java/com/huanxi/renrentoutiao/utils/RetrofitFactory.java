package com.huanxi.renrentoutiao.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.huanxi.renrentoutiao.BuildConfig;
import com.huanxi.renrentoutiao.MyApplication;
import com.huanxi.renrentoutiao.net.api.mediao.INewsApi;
import com.zhxu.library.RxRetrofitApp;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Meiji on 2017/4/22.
 */

public class RetrofitFactory {

    /**
     * 缓存机制
     * 在响应请求之后在 data/data/<包名>/cache 下建立一个response 文件夹，保持缓存数据。
     * 这样我们就可以在请求的时候，如果判断到没有网络，自动读取缓存的数据。
     * 同样这也可以实现，在我们没有网络的情况下，重新打开App可以浏览的之前显示过的内容。
     * 也就是：判断网络，有网络，则从网络获取，并保存到缓存中，无网络，则从缓存中获取。
     * https://werb.github.io/2016/07/29/%E4%BD%BF%E7%94%A8Retrofit2+OkHttp3%E5%AE%9E%E7%8E%B0%E7%BC%93%E5%AD%98%E5%A4%84%E7%90%86/
     */
    private static final Interceptor cacheControlInterceptor = chain -> {
        Request request = chain.request();
        if (!NetWorkUtil.isNetworkConnected(MyApplication.mContext)) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
        }

        Response originalResponse = chain.proceed(request);
        if (NetWorkUtil.isNetworkConnected(MyApplication.mContext)) {
            // 有网络时 设置缓存为默认值
            String cacheControl = request.cacheControl().toString();
            return originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma") // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .build();
        } else {
            // 无网络时 设置超时为1周
            int maxStale = 60 * 60 * 24 * 7;
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("Pragma")
                    .build();
        }
    };
    private volatile static Retrofit retrofit;

    @NonNull
    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (RetrofitFactory.class) {
                if (retrofit == null) {
                    // 指定缓存路径,缓存大小 50Mb
                    Cache cache = new Cache(new File(MyApplication.mContext.getCacheDir(), "HttpCache"),
                            1024 * 1024 * 50);

                    // Cookie 持久化
//                    ClearableCookieJar cookieJar =
//                            new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(InitApp.AppContext));

                    OkHttpClient.Builder builder = new OkHttpClient.Builder()
//                            .cookieJar(cookieJar)
                            .cache(cache)
                            .addInterceptor(cacheControlInterceptor)
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(15, TimeUnit.SECONDS)
                            .retryOnConnectionFailure(true);

                    // Log 拦截器
                    if (BuildConfig.DEBUG) {
//                        builder = SdkManager.initInterceptor(builder);
                        if(RxRetrofitApp.isDebug()){
                            builder.addInterceptor(getHttpLoggingInterceptor());
                        }
                    }

                    retrofit = new Retrofit.Builder()
                            .baseUrl(INewsApi.HOST)
                            .client(builder.build())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();


                }
            }
        }
        return retrofit;
    }

    /**
     * 日志输出
     * 自行判定是否添加
     * @return
     */
    private static HttpLoggingInterceptor getHttpLoggingInterceptor(){
        //日志显示级别

        HttpLoggingInterceptor.Level level= HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("RxRetrofit","Retrofit====Message:"+message);
            }
        });
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }

}
