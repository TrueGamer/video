package com.huanxi.renrentoutiao.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.huanxi.renrentoutiao.R;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import cn.jzvd.JZUserActionStandard;
import cn.jzvd.JZUtils;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by Dinosa on 2018/3/2.
 */

public class MyVideoPlayer extends JZVideoPlayerStandard {


    public MyVideoPlayer(Context context) {
        super(context);
        //这里是需要设置返回键的大小的操作；
        initBack();
    }

    public MyVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);

        initBack();
    }

    private void initBack() {

        //返回键的大小的操作----重新设置大小返回键的大小；；
        View textView =  findViewById(R.id.back);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)textView.getLayoutParams();
        layoutParams.width= UIUtil.dip2px(getContext(),50);
        textView.setPadding(UIUtil.dip2px(getContext(),12),5,UIUtil.dip2px(getContext(),12),5);
        textView.setLayoutParams(layoutParams);
    }

    /**
     * 这里是把点击播放视频的逻辑抽取出来~
     */
    public void play(){

        if (dataSourceObjects == null || JZUtils.getCurrentFromDataSource(dataSourceObjects, currentUrlMapIndex) == null) {
            Toast.makeText(getContext(), getResources().getString(R.string.no_url), Toast.LENGTH_SHORT).show();
            return;
        }
        if (currentState == CURRENT_STATE_NORMAL) {
            if (!JZUtils.getCurrentFromDataSource(dataSourceObjects, currentUrlMapIndex).toString().startsWith("file") &&
                    !JZUtils.getCurrentFromDataSource(dataSourceObjects, currentUrlMapIndex).toString().startsWith("/") &&
                    !JZUtils.isWifiConnected(getContext()) && !WIFI_TIP_DIALOG_SHOWED) {
                showWifiDialog();
                return;
            }
            onEvent(JZUserActionStandard.ON_CLICK_START_THUMB);
            startVideo();
        } else if (currentState == CURRENT_STATE_AUTO_COMPLETE) {
            onClickUiToggle();
        }

    }
}
