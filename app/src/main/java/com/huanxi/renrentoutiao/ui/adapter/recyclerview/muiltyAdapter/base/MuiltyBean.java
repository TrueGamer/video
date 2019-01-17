package com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.base;

import android.content.Context;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by Dinosa on 2018/4/10.
 *  这里是多类型的bean对象；
 */

public class MuiltyBean<T extends BaseMuiltyViewHolder> implements MultiItemEntity {

    public T obj;
    public int layoutId;

    public MuiltyBean(T obj, int layoutId) {
        this.obj = obj;
        this.layoutId = layoutId;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    /**
     * 这里是具体的业务逻辑类的hascode值；
     * @return
     */
    @Override
    public int getItemType() {
        return obj.getClass().hashCode();
    }


    public void init(BaseViewHolder view, MultiItemEntity entity, Context context){
        obj.init(entity,view,context);
    }
}
