package com.huanxi.renrentoutiao.ui.media.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.media.HotMediaBean;

import java.util.List;

/**
 * 热榜适配器
 */
public class HotMediaAdapter extends BaseAdapter {

    private Context mContext;
    private List<HotMediaBean> list;

    public HotMediaAdapter(Context mContext, List<HotMediaBean> list) {
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
                    R.layout.item_media_hot_layout, null);
            holder = new ViewHolder();
            holder.img_hotIcon = (ImageView) convertView.findViewById(R.id.img_hotIcon);
            holder.tv_hotTitle = (TextView) convertView.findViewById(R.id.tv_hotTitle);
            holder.tv_hotSubTitle = (TextView) convertView.findViewById(R.id.tv_hotSubTitle);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String title = list.get(position).getTitle();
        String subTitle = list.get(position).getSubTitle();
        String imageUrl = list.get(position).getIconUrl();

        if(position == 0) {
            holder.img_hotIcon.setImageResource(R.drawable.icon_hot_media1);
        } else if(position == 1) {
            holder.img_hotIcon.setImageResource(R.drawable.icon_hot_media2);
        } else if(position == 2) {
            holder.img_hotIcon.setImageResource(R.drawable.icon_hot_media3);
        } else if(position == 3) {
            holder.img_hotIcon.setImageResource(R.drawable.icon_hot_media4);
        }

        if(position < 2) {
            holder.tv_hotSubTitle.setBackgroundResource(R.drawable.shape_rect_hot_bg1);
            holder.tv_hotSubTitle.setTextColor(mContext.getResources().getColor(R.color.color_e2));
            Drawable hot = mContext.getResources().getDrawable(R.drawable.icon_w_hot_item);
            hot.setBounds(0 , 0 , 35 , 35);
            holder.tv_hotSubTitle.setCompoundDrawables(hot , null , null , null);
        } else {
            holder.tv_hotSubTitle.setBackgroundResource(R.drawable.shape_rect_hot_bg1);
            holder.tv_hotSubTitle.setTextColor(mContext.getResources().getColor(R.color.color_cc));
        }

        if(imageUrl != null) {
            Glide.with(mContext).load(imageUrl)
                    .into(holder.img_hotIcon);
        }

        if(title != null) {
            holder.tv_hotTitle.setText(title);
        }
        if(subTitle != null) {
            holder.tv_hotSubTitle.setText(subTitle);
        }
        return convertView;
    }

    class ViewHolder {
        ImageView img_hotIcon;
        TextView tv_hotTitle;
        TextView tv_hotSubTitle;
    }

}
