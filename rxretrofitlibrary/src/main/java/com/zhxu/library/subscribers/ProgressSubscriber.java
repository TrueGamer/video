package com.zhxu.library.subscribers;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.google.gson.Gson;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.RxRetrofitApp;
import com.zhxu.library.api.BaseApi;
import com.zhxu.library.exception.HttpTimeException;
import com.zhxu.library.http.cookie.CookieResulte;
import com.zhxu.library.listener.HttpOnNextListener;
import com.zhxu.library.utils.AppUtil;
import com.zhxu.library.utils.CookieDbUtil;
import com.zhxu.library.utils.GsonUtils;

import java.lang.ref.SoftReference;
import java.lang.reflect.Constructor;

import rx.Observable;
import rx.Subscriber;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 */
public class ProgressSubscriber<T> extends Subscriber<T> {
    /*是否弹框*/
    private boolean showPorgress = true;
    /* 软引用回调接口*/
    //private SoftReference<HttpOnNextListener> mSubscriberOnNextListener;
    private HttpOnNextListener<T> mListener;
    /*软引用防止内存泄露*/
    private SoftReference<RxAppCompatActivity> mActivity;
    /*加载框可自己定义*/
//    private ProgressDialog pd;
    private Dialog pd;

    /*请求数据*/
    private BaseApi api;

    /**
     * 构造
     *
     * @param api
     */
    public ProgressSubscriber(BaseApi api, Class dialogClass) {
        this.api = api;
        //this.mSubscriberOnNextListener = api.getListener();
        this.mListener = api.getListener();

        this.mActivity = new SoftReference<>(api.getRxAppCompatActivity());
        setShowPorgress(api.isShowProgress());
        if (api.isShowProgress()) {
            createProgressDialog(dialogClass);
        }
        Log.e(getClass().getSimpleName(), "ProgressSubscriber");
    }

    /***
     * 创建一个对话框
     * @param dialogClass
     */
    private void createProgressDialog(Class dialogClass) {
        if (dialogClass == null) {
            initProgressDialog(api.isCancel());
        } else {
            try {
                Constructor constructor = dialogClass.getConstructor(Context.class);
                Object o = constructor.newInstance(mActivity.get());
                setProgressDialog(((Dialog) o), api.isCancel());
            } catch (Exception e) {
                //这里只要出问题了，我们都使用默认的加载框
                e.printStackTrace();
                initProgressDialog(api.isCancel());
            }
        }
    }


    /**
     * 初始化加载框
     */
    private void initProgressDialog(boolean cancel) {
        //这里是默认一个对话框；
        Context context = mActivity.get();
        if (context != null) {
            setProgressDialog(new ProgressDialog(context), cancel);
        }
    }

    /**
     * 设置加载对话框
     */
    public void setProgressDialog(Dialog dialog, boolean cancel) {
        if (dialog == null) {
            initProgressDialog(cancel);
            return;
        }
        pd = dialog;
        pd.setCancelable(cancel);
        if (cancel) {
            pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    //这里是取消对话框的回掉；
                  /*  if (mSubscriberOnNextListener.get() != null) {
                        mSubscriberOnNextListener.get().onCancel();
                    }*/
                    if (mListener != null) {
                        mListener.onCancel();
                    }
                    onCancelProgress();
                }
            });
        }
    }


    /**
     * 显示加载框
     */
    private void showProgressDialog() {
        if (!isShowPorgress()) return;
        Context context = mActivity.get();
        if (pd == null || context == null) return;

        if (!((Activity) context).isFinishing()) {
            if (!pd.isShowing()) {
                pd.show();
            }
        }
    }


    /**
     * 隐藏
     */
    private void dismissProgressDialog() {
        try {
            if (!isShowPorgress()) return;
            if (pd != null && pd.isShowing()) {
                pd.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        Log.e(getClass().getSimpleName(), "onStart");
        showProgressDialog();
        /*缓存并且有网*/
        if (api.isCache() && AppUtil.isNetworkAvailable(RxRetrofitApp.getApplication())) {
             /*获取缓存数据*/  //GreenDAO
            CookieResulte cookieResulte = CookieDbUtil.getInstance().queryCookieBy(api.getUrl());
            if (cookieResulte != null) {
                long time = (System.currentTimeMillis() - cookieResulte.getTime()) / 1000;
                if (time < api.getCookieNetWorkTime()) {
                   /* if (mSubscriberOnNextListener.get() != null) {
                        mSubscriberOnNextListener.get().onCacheNext(cookieResulte.getResulte());
                    }*/
                    if (mListener != null) {
                        //mListener.onCacheNext(cookieResulte.getResulte());
                        T t = (T) new Gson().fromJson(cookieResulte.getResulte(), mListener.mType);
                        mListener.onNext(t);
                    }
                    onCompleted();
                    //取消订阅，因为已经从缓存里获取到数据了,所以没有必要再调用onNext()
                    unsubscribe();
                }
            }
        }
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        Log.e(getClass().getSimpleName(), "onCompleted");
        dismissProgressDialog();
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        Log.e(getClass().getSimpleName(), "onError");

        dismissProgressDialog();
        /*需要緩存并且本地有缓存才返回*/
        if (api.isCache()) {
            Observable.just(api.getUrl()).subscribe(new Subscriber<String>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    errorDo(e);
                }

                @Override
                public void onNext(String s) {
                    /*获取缓存数据*/
                    CookieResulte cookieResulte = CookieDbUtil.getInstance().queryCookieBy(s);
                    if (cookieResulte == null) {
                        //网络获取失败，同时缓存又没有改数据
                        throw new HttpTimeException(400, "网络错误");
                    }
                    long time = (System.currentTimeMillis() - cookieResulte.getTime()) / 1000;
                    if (time < api.getCookieNoNetWorkTime()) {
                       /* if (mSubscriberOnNextListener.get() != null) {
                            //网络获取失败，但是缓存里有数据，并且在缓存的时间内
                            mSubscriberOnNextListener.get().onCacheNext(cookieResulte.getResulte());
                        }*/
                        if (mListener != null) {
                            // mListener.onCacheNext(cookieResulte.getResulte());
                            T t = (T) new Gson().fromJson(cookieResulte.getResulte(), mListener.mType);
                            mListener.onNext(t);
                        }
                    } else {
                        //网络获取失败，虽然缓存里有数据，但是超时了（缓存时间超时）
                        CookieDbUtil.getInstance().deleteCookie(cookieResulte);
                        throw new HttpTimeException(400, "网络错误");
                    }
                }
            });
        } else {
            errorDo(e);
        }
    }

    /*错误统一处理*/
    private void errorDo(Throwable e) {
        Context context = mActivity.get();
        if (context == null) return;
        mActivity.get().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                api.handleException(e);
        /*if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onError(e);
        }*/
                if (mListener != null) {
                    mListener.onError(e);
                }
                e.printStackTrace();
            }
        });

    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        //Log.e(getClass().getSimpleName(), "onNext: "+mSubscriberOnNextListener.get());
        Log.e(getClass().getSimpleName(), "onNext: " + mListener);

        if (mListener != null) {
            mListener.onNext(t);
        }

        saveCookie(t);

    }

    private void saveCookie(T t) {
        CookieDbUtil dbUtil = CookieDbUtil.getInstance();
        //在这里我们对其进行一个缓存的操作；
        CookieResulte resulte = dbUtil.queryCookieBy(api.getUrl());
        long time = System.currentTimeMillis();
        String dataStr = GsonUtils.getInstance().toString(t);
            /*保存和更新本地数据*/
        if (resulte == null) {
            resulte = new CookieResulte(api.getUrl(), dataStr, time);
            dbUtil.saveCookie(resulte);
        } else {
            resulte.setResulte(dataStr);
            resulte.setTime(time);
            dbUtil.updateCookie(resulte);
        }

    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }


    public boolean isShowPorgress() {
        return showPorgress;
    }

    /**
     * 是否需要弹框设置
     *
     * @param showPorgress
     */
    public void setShowPorgress(boolean showPorgress) {
        this.showPorgress = showPorgress;
    }
}