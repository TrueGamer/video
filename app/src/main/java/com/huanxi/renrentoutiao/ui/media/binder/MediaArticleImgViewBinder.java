package com.huanxi.renrentoutiao.ui.media.binder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.model.bean.media.MultiMediaArticleBean;
import com.huanxi.renrentoutiao.model.bean.media.MultiNewsArticleDataBean;
import com.huanxi.renrentoutiao.ui.media.MediaArticleDetailActivity;
import com.huanxi.renrentoutiao.ui.media.TTMediaBean;
import com.huanxi.renrentoutiao.utils.ErrorAction;
import com.huanxi.renrentoutiao.utils.IntentAction;
import com.huanxi.renrentoutiao.utils.NetWorkUtil;
import com.huanxi.renrentoutiao.utils.SettingUtil;
import com.huanxi.renrentoutiao.utils.TimeUtil;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Meiji on 2017/6/8.
 * 带图片的 item
 */

public class MediaArticleImgViewBinder extends ItemViewBinder<TTMediaBean, MediaArticleImgViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected MediaArticleImgViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_media_article_img, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull final MediaArticleImgViewBinder.ViewHolder holder, @NonNull final TTMediaBean item) {

        final Context context = holder.itemView.getContext();

        try {
            String imgUrl = "";
            List<String> imageList = item.getPicUrl();
            if (imageList != null && imageList.size() > 0) {
                String url = imageList.get(0);
                if (!TextUtils.isEmpty(url)) {
//                    if (NetWorkUtil.isWifiConnected(context)) {
//                        // 加载高清图
//                        url = url.replace("list", "large");
//                    }
                    Glide.with(context).load(url).into(holder.iv_image);
//                    imgUrl = imageList.get(0).replace("list", "large");
                }
            }

            String title = item.getTitle();
            String abstractX = item.getContent();
            String readCount = item.getZan() + "阅读量";
            String countmmentCount = item.getPinglun() + "评论";
            String time = item.getTime() + "";
//            if (!TextUtils.isEmpty(time)) {
//                time = TimeUtil.getTimeStampAgo(time);
//            }

            holder.tv_title.setText(title);
            holder.tv_title.setTextSize(SettingUtil.getInstance().getTextSize());
            holder.tv_abstract.setText(abstractX);
            holder.tv_extra.setText(readCount + " - " + countmmentCount + " - " + time);
            final String finalImgUrl = imgUrl;
//            RxView.clicks(holder.itemView)
//                    .throttleFirst(1, TimeUnit.SECONDS)
//                    .subscribe(o -> {
//                        String mediaId = item.getId();
//                        Intent intent = new Intent(context , MediaArticleDetailActivity.class);
//                        intent.putExtra("mediaId" , mediaId);
//                        intent.putExtra("title" , title);
//                        context.startActivity(intent);
//                    });

//            RxView.clicks(holder.itemView)
//                    .throttleFirst(1, TimeUnit.SECONDS)
//                    .subscribe(o -> {
//                        MultiNewsArticleDataBean bean = new MultiNewsArticleDataBean();
//                        bean.setTitle(item.getTitle());
//                        bean.setDisplay_url(item.getDisplay_url());
//                        bean.setMedia_name(item.getSource());
//                        bean.setGroup_id(item.getGroup_id());
//                        bean.setItem_id(item.getItem_id());
//                        MultiNewsArticleDataBean.MediaInfoBean mediaInfo = new MultiNewsArticleDataBean.MediaInfoBean();
//                        mediaInfo.setMedia_id(item.getMedia_id() + "");
//                        bean.setMedia_info(mediaInfo);
////                        NewsContentActivity.launch(bean, finalImgUrl);
//                    });

//            holder.iv_dots.setOnClickListener(view -> {
//                PopupMenu popupMenu = new PopupMenu(context,
//                        holder.iv_dots, Gravity.END, 0, R.style.MyPopupMenu);
//                popupMenu.inflate(R.menu.menu_share);
//                popupMenu.setOnMenuItemClickListener(menu -> {
//                    int itemId = menu.getItemId();
//                    if (itemId == R.id.action_share) {
//                        IntentAction.send(context, item.getTitle() + "\n" + item.getDisplay_url());
//                    }
//                    return false;
//                });
//                popupMenu.show();
//            });
        } catch (Exception e) {
            ErrorAction.print(e);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_image;
        private TextView tv_title;
        private TextView tv_abstract;
        private TextView tv_extra;
        private ImageView iv_dots;

        ViewHolder(View itemView) {
            super(itemView);
            this.iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            this.tv_abstract = (TextView) itemView.findViewById(R.id.tv_abstract);
            this.tv_extra = (TextView) itemView.findViewById(R.id.tv_extra);
            this.iv_dots = (ImageView) itemView.findViewById(R.id.iv_dots);
        }
    }
}
