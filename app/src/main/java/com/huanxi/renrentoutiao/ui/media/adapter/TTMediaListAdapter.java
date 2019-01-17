package com.huanxi.renrentoutiao.ui.media.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.media.TTMediaBean;
import com.huanxi.renrentoutiao.ui.view.CircleImageView;
import com.huanxi.renrentoutiao.ui.view.MyGridView;

import java.util.List;

/**
 * 微头条列表
 */
public class TTMediaListAdapter extends BaseAdapter {

    private Context mContext;
    private List<TTMediaBean> list;

    public TTMediaListAdapter(Context mContext, List<TTMediaBean> list) {
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
                    R.layout.item_media_new_list_layout, null);
            holder = new ViewHolder();
            holder.cImg_mediaUserIcon = convertView.findViewById(R.id.cImg_mediaUserIcon);
            holder.img_close = convertView.findViewById(R.id.img_close);
            holder.tv_mediaAttention = convertView.findViewById(R.id.tv_mediaAttention);
            holder.tv_mediaUserName = convertView.findViewById(R.id.tv_mediaUserName);
            holder.tv_mediaRes = convertView.findViewById(R.id.tv_mediaRes);
            holder.tv_mediaContent = convertView.findViewById(R.id.tv_mediaContent);
            holder.img_mediaPicOne = convertView.findViewById(R.id.img_mediaPicOne);
            holder.mGv_mediaPicTwo = convertView.findViewById(R.id.mGv_mediaPicTwo);
            holder.mGv_mediaPic = convertView.findViewById(R.id.mGv_mediaPic);
            holder.tv_zhuanfa = convertView.findViewById(R.id.tv_zhuanfa);
            holder.tv_pinglun = convertView.findViewById(R.id.tv_pinglun);
            holder.tv_zan = convertView.findViewById(R.id.tv_zan);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TTMediaBean ttMediaBean = list.get(position);
        if(ttMediaBean != null) {
            String userIconUrl = ttMediaBean.getUserIcon();
            String userName = ttMediaBean.getUserName();
            String time = ttMediaBean.getTime();
            String content = ttMediaBean.getContent();
            int zhuanFa = ttMediaBean.getZhuanFa();
            int pinglun = ttMediaBean.getPinglun();
            int zan = ttMediaBean.getZan();

            String type = ttMediaBean.getImgType();
            List<String> imgList = ttMediaBean.getPicUrl();

            if(userIconUrl != null) {
                Glide.with(mContext).load("http:"+userIconUrl)
                        .into(holder.cImg_mediaUserIcon);
            }

            if(userName != null) {
                holder.tv_mediaUserName.setText(userName);
            }
            if(time != null) {
                holder.tv_mediaRes.setText(time);
            }
            if(content != null) {
                holder.tv_mediaContent.setText(content);
            }
            holder.tv_zhuanfa.setText(""+zhuanFa);
            holder.tv_pinglun.setText(""+pinglun);
            holder.tv_zan.setText(""+zan);

            if(imgList != null && imgList.size()>0) {
                holder.img_mediaPicOne.setVisibility(View.GONE);
                holder.mGv_mediaPicTwo.setVisibility(View.GONE);
                holder.mGv_mediaPic.setVisibility(View.GONE);
                if("1".equals(type)) {
                    holder.img_mediaPicOne.setVisibility(View.VISIBLE);
                    String imgUrl = imgList.get(0);
                    if(imgUrl != null) {
                        if(imgUrl.contains("http")) {
                            Glide.with(mContext).load(imgUrl).into(holder.img_mediaPicOne);
                        } else {
                            Glide.with(mContext).load("http:"+imgUrl).into(holder.img_mediaPicOne);
                        }
                    }
                } else if("2".equals(type) || "4".equals(type)) {
                    holder.mGv_mediaPicTwo.setVisibility(View.VISIBLE);

                    MediaPictureAdapter mediaPictureAdapter = new MediaPictureAdapter(mContext , imgList);
                    holder.mGv_mediaPicTwo.setAdapter(mediaPictureAdapter);

                } else if(Integer.parseInt(type) > 2 && !"4".equals(type)) {
                    holder.mGv_mediaPic.setVisibility(View.VISIBLE);

                    MediaPictureAdapter mediaPictureAdapter;
                    if(Integer.parseInt(type) < 10) {
                        mediaPictureAdapter = new MediaPictureAdapter(mContext , imgList);
                    } else {
                        mediaPictureAdapter = new MediaPictureAdapter(mContext , imgList.subList(0 , 9));
                    }
                    holder.mGv_mediaPic.setAdapter(mediaPictureAdapter);
                }
            }
        }

        return convertView;
    }

    class ViewHolder {
        CircleImageView cImg_mediaUserIcon;
        ImageView img_close;
        TextView tv_mediaAttention , tv_mediaUserName , tv_mediaRes , tv_mediaContent;
        ImageView img_mediaPicOne;
        MyGridView mGv_mediaPicTwo;
        MyGridView mGv_mediaPic;
        TextView tv_zhuanfa , tv_pinglun , tv_zan;
    }
}
