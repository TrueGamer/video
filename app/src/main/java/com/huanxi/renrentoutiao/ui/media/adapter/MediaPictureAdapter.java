package com.huanxi.renrentoutiao.ui.media.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.huanxi.renrentoutiao.R;

import java.util.List;

/**
 * 微头条图片列表适配器
 */
public class MediaPictureAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> list;

    public MediaPictureAdapter(Context mContext, List<String> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_media_pic_layout, null);
            holder = new ViewHolder();
            holder.img_pic = convertView.findViewById(R.id.img_pic);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String picUrl = list.get(position);
        //http://p3.pstatp.com/list/190x124/pgc-image/1534037661009fa54c9beaa
        if(picUrl != null) {
            if(picUrl.contains("http")) {
                Glide.with(mContext).load(picUrl)
                        .into(holder.img_pic);
            } else {
                Glide.with(mContext).load("http:"+picUrl)
                        .into(holder.img_pic);
            }
        }

        return convertView;
    }

    class ViewHolder {
        ImageView img_pic;
    }

}
