package com.huanxi.renrentoutiao.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.huanxi.renrentoutiao.model.bean.JPushBroadcastBean;
import com.huanxi.renrentoutiao.ui.activity.WebHelperActivity;
import com.huanxi.renrentoutiao.ui.activity.news.NewsDetailActivity2;
import com.huanxi.renrentoutiao.ui.activity.other.InviteFriendActivityNew;
import com.huanxi.renrentoutiao.ui.activity.other.MainActivity;
import com.huanxi.renrentoutiao.ui.activity.other.SplashActivity;
import com.huanxi.renrentoutiao.ui.activity.user.IntergralShopActivity;
import com.huanxi.renrentoutiao.ui.activity.user.UserTaskActivity;
import com.huanxi.renrentoutiao.ui.activity.video.VideoItemDetailActivity;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.video.VideoListBean;
import com.huanxi.renrentoutiao.utils.GsonUtils;
import com.huanxi.renrentoutiao.utils.MyActivityManager;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Dinosa on 2018/4/13.
 */

public class JPushReceiver extends BroadcastReceiver {

    //type:1 表是新闻，2.视频详情 3.任务中心 4，邀请好友 5. 兑换提现页面 6.h5页面的逻辑

    @Override
    public void onReceive(Context context, Intent intent) {

        //这里我们要做的一个操作就是获取数据；
        //intent.getAction()
       if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {

            Bundle bundle = intent.getExtras();

            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            String content = bundle.getString(JPushInterface.EXTRA_ALERT);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            int notificationId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            String fileHtml = bundle.getString(JPushInterface.EXTRA_RICHPUSH_HTML_PATH);
            //这里对其进行一个格式化的操作；
            JPushBroadcastBean jPushBroadcastBean = GsonUtils.getInstance().toBean(extras, JPushBroadcastBean.class);

            //这里要做的一个逻辑就是判断是否app是否还在存活；
            if (MyActivityManager.isAppAlive()) {

                //这里表示我们要通过splash启动这个逻辑；
                if (jPushBroadcastBean != null && jPushBroadcastBean.getType()!=null) {
                    if (jPushBroadcastBean.getType().equals("1")) {
                        //这里我们是需要跳转到新闻的详情页面；

                        Intent detailIntent = NewsDetailActivity2.getIntent(context,
                                jPushBroadcastBean.getUrl(), jPushBroadcastBean.getUrlmd5() , "xiaohua" , 1);
                        detailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(detailIntent);

                    }
                    if (jPushBroadcastBean.getType().equals("2")) {
                        //这里是视频的详情页面；

                        VideoListBean videoBean = new VideoListBean(null, jPushBroadcastBean.getUrlmd5(), jPushBroadcastBean.getTitle(), null, null, null, jPushBroadcastBean.getVideo_id(), null, null);

                        Intent videoIntent = VideoItemDetailActivity.getIntent(context, videoBean,null);
                        videoIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        context.startActivity(videoIntent);

                    } else if (jPushBroadcastBean.getType().equals("3")) {
                        //这里跳转到任务中心；
                        Intent taskIntent = new Intent(context, UserTaskActivity.class);
                        taskIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(taskIntent);

                    } else if (jPushBroadcastBean.getType().equals("4")) {
                        //邀请好友
                        Intent inviteFriendActivity = new Intent(context, InviteFriendActivityNew.class);
                        inviteFriendActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(inviteFriendActivity);

                    } else if (jPushBroadcastBean.getType().equals("5")) {
                        //兑换提现的页面；
                        Intent intergral = new Intent(context, IntergralShopActivity.class);
                        intergral.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intergral);

                    } else if (jPushBroadcastBean.getType().equals("6")) {
                        //这里是h5逻辑
                        Intent webIntent = WebHelperActivity.getIntent(context, jPushBroadcastBean.getUrl(), jPushBroadcastBean.getTitle(), false);
                        webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(webIntent);
                    }
                }

            } else {
                //这里我们要做的一个逻辑就是通过splash页面启动app;

                Intent intent1 = new Intent(context, SplashActivity.class);
                if(jPushBroadcastBean !=null ){
                    intent1.putExtra(MainActivity.JPUSH_BROAD_CAST_BEAN,jPushBroadcastBean);
                }
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }

        }

    }

}
