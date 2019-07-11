package com.zhxu.library.api;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.widget.Toast;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.exception.HttpTimeException;
import com.zhxu.library.listener.HttpOnNextListener;

import java.lang.ref.SoftReference;

import retrofit2.Retrofit;
import rx.Observable;
import rx.functions.Func1;

/**
 * 请求统一封装
 * @param <T>
 */
public abstract class BaseApi<T> implements Func1<BaseResultEntity<T>, T> {
    //rx生命周期管理
    protected SoftReference<RxAppCompatActivity> rxAppCompatActivity;
    /*回调*/
    //private SoftReference<HttpOnNextListener> listener;
    private HttpOnNextListener<T> listener;

    /*是否能取消加载框*/
    private boolean cancel;
    /*是否显示加载框*/
    private boolean showProgress;
    /*是否需要缓存处理*/
    private boolean cache;
    /*基础url*/

    //这里是测试地址
    //private String baseUrl = "http://192.168.31.100:8888/api/index.php/news/";
    //这里是一个正式的
     private String baseUrl = "http://game.appshow.cn/api/index.php/news/";
//     private String baseUrl = "http://app.ddtt.me/api/index.php/news/";
//    private String baseUrl = "http://www.appshow.cn/toutiao/public/api/index.php/news/";
//    private String baseUrl = "http://api.zhidaoweilai.com/public/api/index.php/news/";

//    private static final String DEBUG_URL="http://118.31.4.145/html/hxtoutiao/public/api/index.php/news/";
//    private static final String RELEASE_URL="http://118.31.4.145/html/hxtoutiao/public/api/index.php/news/";

     private static final String DEBUG_URL="http://game.appshow.cn/api/index.php/news/";
//     private static final String DEBUG_URL="http://app.ddtt.me/api/index.php/news/";
     private static final String RELEASE_URL="http://game.appshow.cn/api/index.php/news/";
//     private static final String RELEASE_URL="http://app.ddtt.me/api/index.php/news/";
//    private static final String DEBUG_URL="http://api.zhidaoweilai.com/public/api/index.php/news/";
//    private static final String RELEASE_URL="http://api.zhidaoweilai.com/public/api/index.php/news/";


//    private static final String DEBUG_URL="http://www.appshow.cn/toutiao/public/api/index.php/news/";
//    private static final String RELEASE_URL="http://www.appshow.cn/toutiao/public/api/index.php/news/";


    /*方法-如果需要缓存必须设置这个参数；不需要不用設置*/
    private String mothed;
    /*超时时间-默认6秒*/
    private int connectionTime = 6;
    /*有网情况下的本地缓存时间默认60秒*/
    private int cookieNetWorkTime = 60;
    /*无网络的情况下本地缓存时间默认30天*/
    private int cookieNoNetWorkTime = 24 * 60 * 60 * 30;
    /* 失败后retry次数*/
    private int retryCount = 1;
    /*失败后retry延迟*/
    private long retryDelay = 100;
    /*失败后retry叠加延迟*/
    private long retryIncreaseDelay = 10;

    public BaseApi(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity) {
        setListener(listener);
        setRxAppCompatActivity(rxAppCompatActivity);
        setShowProgress(false);
        setCache(false);
        setCancel(true);

        setCookieNetWorkTime(60);
        setCookieNoNetWorkTime(24*60*60);

        //这里获取是debug,还是release

       // setBaseUrl(RELEASE_URL);

        if (isDebug(rxAppCompatActivity)) {
            setBaseUrl(DEBUG_URL);
        }else{
            setBaseUrl(RELEASE_URL);
        }
    }

    public boolean isDebug(Context context){
        boolean isDebug = context.getApplicationInfo()!=null&&
                (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE)!=0;
        return isDebug;
    }

    /**
     * 设置参数
     *
     * @param retrofit
     * @return
     */
    public abstract Observable getObservable(Retrofit retrofit);


    public int getCookieNoNetWorkTime() {
        return cookieNoNetWorkTime;
    }

    public void setCookieNoNetWorkTime(int cookieNoNetWorkTime) {
        this.cookieNoNetWorkTime = cookieNoNetWorkTime;
    }

    public int getCookieNetWorkTime() {
        return cookieNetWorkTime;
    }

    public void setCookieNetWorkTime(int cookieNetWorkTime) {
        this.cookieNetWorkTime = cookieNetWorkTime;
    }

    public String getMothed() {
        return mothed;
    }

    public int getConnectionTime() {
        return connectionTime;
    }

    public void setConnectionTime(int connectionTime) {
        this.connectionTime = connectionTime;
    }

    public void setMothed(String mothed) {
        this.mothed = mothed;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getUrl() {
        return baseUrl + mothed;
    }

    public void setRxAppCompatActivity(RxAppCompatActivity rxAppCompatActivity) {
        this.rxAppCompatActivity = new SoftReference(rxAppCompatActivity);
    }

    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public boolean isShowProgress() {
        return showProgress;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public HttpOnNextListener getListener() {
      //  return listener;
        return listener;
    }

    public void setListener(HttpOnNextListener listener) {
        //this.listener = new SoftReference(listener);
        this.listener=listener;
    }


    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public long getRetryDelay() {
        return retryDelay;
    }

    public void setRetryDelay(long retryDelay) {
        this.retryDelay = retryDelay;
    }

    public long getRetryIncreaseDelay() {
        return retryIncreaseDelay;
    }

    public void setRetryIncreaseDelay(long retryIncreaseDelay) {
        this.retryIncreaseDelay = retryIncreaseDelay;
    }

    /*
         * 获取当前rx生命周期
         * @return
         */
    public RxAppCompatActivity getRxAppCompatActivity() {
        return rxAppCompatActivity.get();
    }

    @Override
    public T call(BaseResultEntity<T> httpResult) {
        //map 定义转换规则
        if (httpResult.getCode() != 1) {//0失败，1成功
            throw new HttpTimeException(httpResult.getCode(),httpResult.getMsg());
        }
        return httpResult.getData();
    }

    /**
     * 处理异常的一个类
     */
    public void handleException(Throwable e){

        if(e instanceof  HttpTimeException){
            Toast.makeText(rxAppCompatActivity.get(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(rxAppCompatActivity.get(), "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        }
       /* if (e instanceof IOException) {
            Toast.makeText(rxAppCompatActivity.get(), "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        }
        else {


            Toast.makeText(rxAppCompatActivity.get(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }*/

        //Toast.makeText(rxAppCompatActivity.get(), "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();

    }
}