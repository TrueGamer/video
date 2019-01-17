package com.huanxi.renrentoutiao.ui.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.db.ta.sdk.TMShTmListener;
import com.db.ta.sdk.TMShTmView;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.globle.ConstantAd;
import com.huanxi.renrentoutiao.ui.activity.other.MainActivity;
import com.huanxi.renrentoutiao.ui.activity.other.SplashActivity;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by Dinosa on 2018/2/22.
 *
 * 这里是推啊的广告位；
 */

public class SplashTuiAFragment extends BaseFragment {

    @BindView(R.id.TMSh_container)
    public TMShTmView mTMShTmView;

    @BindView(R.id.skip_view)
    public TextView skip_view;

    @Override
    protected View getContentView() {
        return inflatLayout(R.layout.fragment_splash_tuia);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SplashActivity splashActivity = ((SplashActivity) getBaseActivity());

        Intent intent = new Intent(getBaseActivity(),MainActivity.class);
        intent.putExtra(MainActivity.JPUSH_BROAD_CAST_BEAN,splashActivity.getJPushBroadcastBeanFromIntent());

        mTMShTmView.setTargetClass(getActivity(),MainActivity.class,intent);

        mTMShTmView.setAdListener(new TMShTmListener(){
            @Override
            public void onTimeOut() {
                Log.d("TMShActivity", "onTimeOut");
                //这里直接进行一个跳转的操作；
                ((SplashActivity) getBaseActivity()).startActivity();
                getBaseActivity().finish();
            }

            @Override
            public void onReceiveAd() {
                Log.d("TMShActivity", "onReceiveAd");
            }

            @Override
            public void onFailedToReceiveAd() {
                Log.d("TMShActivity", "onFailedToReceiveAd");
                //这里直接进行一个跳转的操作；
                //getBaseActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                ((SplashActivity) getBaseActivity()).startActivity();
                getBaseActivity().finish();
            }

            @Override
            public void onLoadFailed() {
                Log.d("TMShActivity", "onLoadFailed");
                //这里直接进行一个跳转的操作；
                //getBaseActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                ((SplashActivity) getBaseActivity()).startActivity();
                getBaseActivity().finish();
            }

            @Override
            public void onCloseClick() {
                Log.d("TMShActivity", "onCloseClick");
            }

            @Override
            public void onAdClick() {
                Log.d("TMShActivity", "onClick");
            }

            @Override
            public void onAdExposure() {
                Log.d("TMShActivity", "onAdExposure");
            }
        });

        mTMShTmView.loadAd(ConstantAd.TuiAAD.SPLASH_AD);

        skip_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SplashActivity) getBaseActivity()).startActivity();
                getBaseActivity().finish();
            }
        });
    }


    @Override
    public void onDestroy() {
        if (mTMShTmView != null) {
            mTMShTmView.destroy();
        }
        super.onDestroy();
    }
}
