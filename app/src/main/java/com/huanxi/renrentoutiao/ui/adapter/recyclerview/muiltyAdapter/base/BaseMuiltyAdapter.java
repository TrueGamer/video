package com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.base;

import android.util.SparseIntArray;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Dinosa on 2018/4/10.
 * 我的MuiltyAdapter
 * 非入侵式的adapter;
 */
public class BaseMuiltyAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity,BaseViewHolder> {

    public SparseIntArray keys;
    private HashMap<Integer,MuiltyBean> mMuiltyData;
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public BaseMuiltyAdapter(List<MultiItemEntity> data) {
        super(data);
        keys = new SparseIntArray();
        mMuiltyData=new HashMap<>();
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        mMuiltyData.get(item.getItemType()).init(helper,item,helper.itemView.getContext());
    }

    /**
     * 这里是注册一组条目类型的；
     * @param data
     */
    public void register(List<MuiltyBean> data){

        if (data != null) {
            //这里将添加条目的类型；
            for (MuiltyBean muiltyBean : data) {
                if (keys.get(muiltyBean.getItemType())==0) {
                    //初始化所有的条目的类型的一个操作；
                    addItemType(muiltyBean.getItemType(),muiltyBean.getLayoutId());
                    keys.put(muiltyBean.getItemType(),muiltyBean.getLayoutId());
                    mMuiltyData.put(muiltyBean.getItemType(),muiltyBean);
                }
                //将所有的业务逻辑类抽出来；
            }
        }
    }


    /**
     * 这里是注册一种条目类型；
     * @param muiltyBean
     */
    public  void register(MuiltyBean muiltyBean){

        if (keys.get(muiltyBean.getItemType())==0) {
            //初始化所有的条目的类型的一个操作；
            addItemType(muiltyBean.getItemType(),muiltyBean.getLayoutId());
            keys.put(muiltyBean.getItemType(),muiltyBean.getLayoutId());
            mMuiltyData.put(muiltyBean.getItemType(),muiltyBean);
        }

    }
}
