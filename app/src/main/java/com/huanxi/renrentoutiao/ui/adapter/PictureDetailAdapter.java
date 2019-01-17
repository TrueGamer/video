package com.huanxi.renrentoutiao.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.adapter.bean.PictureImageBean;

import java.util.List;

public class PictureDetailAdapter extends BaseAdapter {

    private List<PictureImageBean> datas;
    private Context mContext;

    public PictureDetailAdapter(List<PictureImageBean> datas, Context mContext) {
        this.datas = datas;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_new_picture_layout, null);
            holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
            holder.tv_news_title = (TextView) convertView.findViewById(R.id.tv_news_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_news_title.setVisibility(View.GONE);
        String imgUrl = datas.get(position).getMiddle();
        if(imgUrl != null) {
            Glide.with(mContext).load(imgUrl)
                    .into(holder.iv_image);
        }

        return convertView;
    }

    class ViewHolder {
        ImageView iv_image;
        TextView tv_news_title;
    }
}
