package com.huanxi.renrentoutiao.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.adapter.bean.PictureBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.base.BaseMuiltyAdapter;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.base.MuiltyBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.PictureListBeanHolder;

import java.util.ArrayList;
import java.util.List;

public class PictureListAdapter extends BaseMuiltyAdapter {

    public PictureListAdapter(List<MultiItemEntity> data) {
        super(data);
        //这里要注册多种数据类型；
        //这里要注册多种数据类型；
        ArrayList<MuiltyBean> muiltyBeen = new ArrayList<>();

        muiltyBeen.add(new MuiltyBean(new PictureListBeanHolder(), R.layout.item_new_picture_layout));  //美图；

        //每次增加这种绑定关系就ojbk
        //muiltyBeen.add(new MuiltyBean(new Banner20_3Holder(), R.layout.item_new_vedio_layout));  //其他
        register(muiltyBeen);
    }
}
