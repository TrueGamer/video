package com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.base;

import android.content.Context;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Dinosa on 2018/4/10.
 * 这里是多类型的BaseViewHolder对象；
 */

public abstract class BaseMuiltyViewHolder<T> {



    /**
     * 这里是做一个初始化的一个操作；
     */
    public abstract void init(T t, BaseViewHolder helper, Context context);

}
