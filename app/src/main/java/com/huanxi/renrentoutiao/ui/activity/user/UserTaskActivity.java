package com.huanxi.renrentoutiao.ui.activity.user;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.huanxi.renrentoutiao.MyApplication;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.net.api.ApiGetRedPacket;
import com.huanxi.renrentoutiao.net.bean.user.ResGetRedPacketBean;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.adapter.AdBean;
import com.huanxi.renrentoutiao.ui.adapter.AdsAdapter;
import com.huanxi.renrentoutiao.ui.dialog.RedPacketDialog;
import com.huanxi.renrentoutiao.ui.fragment.user.TaskFragment;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Dinosa on 2018/1/20.
 * 这里是新的任务中心
 */

public class UserTaskActivity extends BaseActivity {


    public boolean isShowingAnimator = false;
    public boolean isShow = true;
    @BindView(R.id.fl_task_container)
    FrameLayout mFlTaskContainer;
    @BindView(R.id.rv_ads)
    RecyclerView mRvAds;
    CountDownTimer mCountDownTimer;
    private TaskFragment mTaskFragment;

    @Override
    public int getContentLayout() {
        return R.layout.activity_user_task;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        setStatusBarImmersive(null);

        setTitle("任务中心");

        //填充广告；
        if (isHasAds()) {
            //我们添加一个AD
            MyApplication application = (MyApplication) getApplication();
            List<AdBean> tasklist = application.getResAds().getTasklist();
            //这里我们使用一个广点通的
            AdsAdapter adsAdapter = new AdsAdapter(tasklist);
            mRvAds.setLayoutManager(new LinearLayoutManager(this) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            mRvAds.setAdapter(adsAdapter);
        }


        //这里是任务中心的一个条目；
        mTaskFragment = new TaskFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_task_container, mTaskFragment)
                .commitAllowingStateLoss();

        mTaskFragment.setOnTaskScrollListener(new TaskFragment.OnTaskScrollListener() {
            @Override
            public void onScrollUp() {
                //这里将红包隐藏的动画
                redPackDismissAniamtor();
            }

            @Override
            public void onScrollDown() {
                //红包显示的动画
                redPackShowAnimator();
            }
        });

    }

    public TaskFragment getTaskFragment() {
        return mTaskFragment;
    }

    @Override
    protected void initData() {
        super.initData();
        getRedPacketTime();
    }

    public void getRedPacketTime() {
 /*
        BaseActivity activity = this;
        HashMap<String, String> paramsMap = new HashMap<>();

        paramsMap.put(ApiGetRedPacketTime.FROM_UID, activity.getUserBean().getUserid());

        ApiGetRedPacketTime apiGetRedPacketTime = new ApiGetRedPacketTime(new HttpOnNextListener<ResGetRedPacketBean>() {

            @Override
            public void onNext(ResGetRedPacketBean resGetRedPacketBean) {
                //这里我们将开始一个倒计时；
                if (resGetRedPacketBean.getLasttime() == 0) {
                    //这里表示可以领取；
                    showUI(true);
                    mTvCountTimer.setText("可领取");
                } else {
                    showUI(false);
                    startCountDown(resGetRedPacketBean.getLasttime());
                }
            }
        }, activity, paramsMap);

        HttpManager.getInstance().doHttpDeal(apiGetRedPacketTime);
        */
    }

    /**
     * 这里是获取红包的操作；
     */
    private void doGetRedPacket() {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiGetRedPacket.FROM_UID, getUserBean().getUserid());
        ApiGetRedPacket apiGetRedPacket = new ApiGetRedPacket(new HttpOnNextListener<ResGetRedPacketBean>() {
            @Override
            public void onNext(final ResGetRedPacketBean resGetRedPacketBean) {

                //这里表示已经领取成功了；

                RedPacketDialog redPacketDialog = new RedPacketDialog();
                redPacketDialog.show(UserTaskActivity.this, resGetRedPacketBean.getIntegral() + "", new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        //这里我们要做的一个操作就是
                        startCountDown(resGetRedPacketBean.getLasttime());
                        //恢复冷冻的状态；
                        showUI(false);
                        //这里要
                    }
                });
            }
        }, UserTaskActivity.this, paramsMap);
        HttpManager.getInstance().doHttpDeal(apiGetRedPacket);
    }

    /**
     * 是否可以打开；
     *
     * @param canOpen
     */
    private void showUI(boolean canOpen) {

    /*
        if (canOpen) {

            //设置可以点击，
            //mFlRedPacketContainer.setClickable(true);
            mFlRedPacketContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //这里我们要做的一个操作就是获取红包；
                    doGetRedPacket();
                }
            });
        } else {
            //设置不可以点击；
            //mFlRedPacketContainer.setClickable(false);
            mFlRedPacketContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toast(mTvCountTimer.getText().toString() + "之后解冻");
                }
            });
        }
        */
    }

    /**
     * 开启一个定时器的操作，时间到了，就可以拆红包；
     */
    private void startCountDown(int totalTime) {
   /*
        int timeUnit = 1 * 1000;
        totalTime = totalTime * 1000;
        //去做一个更新的操作；
        mCountDownTimer = new CountDownTimer(totalTime, timeUnit) {
            @Override
            public void onTick(long millisUntilFinished) {
                //去做一个更新的操作；
                //String str="还有"+ FormatUtils.formatMillisToTime(millisUntilFinished)+"解冻";
                if (mTvCountTimer != null) {

                    mTvCountTimer.setText(FormatUtils.formatMillisToTime(millisUntilFinished));

                }
            }

            @Override
            public void onFinish() {
                //这里我们将修改状态；变成可领取的状态；
                mTvCountTimer.setText("可领取");
                showUI(true);
            }
        };
        mCountDownTimer.start();
        */
    }

    @Override
    protected void onDestroy() {

        //清理的一个操作；

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }


        mTaskFragment = null;
        super.onDestroy();
    }

    public void redPackShowAnimator() {


      /*  if (!isShowingAnimator && !isShow) {
            //现在是正在展示动画；

            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mFlRedPacketContainer, "TranslationX", mFlRedPacketContainer.getWidth(), 0);

            objectAnimator.setDuration(500);

            objectAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    isShowingAnimator = false;
                    isShow = true;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            objectAnimator.start();
            isShowingAnimator = true;
        }*/


    }

    public void redPackDismissAniamtor() {

     /*   if (!isShowingAnimator && isShow) {
            //现在是正在展示动画；

            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(, "TranslationX", 0, mFlRedPacketContainer.getWidth());

            objectAnimator.setDuration(500);

            objectAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    isShowingAnimator = false;
                    isShow = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            objectAnimator.start();
            isShowingAnimator = true;
        }*/

    }


}
