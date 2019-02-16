package com.huanxi.renrentoutiao.ui.adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.net.api.user.task.ApiCustomTaskEnd;
import com.huanxi.renrentoutiao.net.api.user.task.ApiCustomTaskStart;
import com.huanxi.renrentoutiao.net.bean.news.ResAward;
import com.huanxi.renrentoutiao.ui.activity.WebHelperActivity;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.adapter.bean.TaskItemBean;
import com.huanxi.renrentoutiao.ui.adapter.bean.TaskItemContentBean;
import com.huanxi.renrentoutiao.ui.adapter.bean.TaskTitleBean;
import com.huanxi.renrentoutiao.ui.dialog.RedPacketDialog;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Dinosa on 2018/1/22.
 */

public class AdvanceTaskAdapter extends TaskAdapter{


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param baseActivity
     * @param data         A new list is created out of this one to avoid mutable list
     */
    public AdvanceTaskAdapter(BaseActivity baseActivity, List<MultiItemEntity> data) {
        super(baseActivity, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        super.convert(helper,item);

        if (item instanceof AdBean) {
        }else{

            switch (helper.getItemViewType()) {
                case TYPE_LEVEL_0:
                    //这里是一级title的操作；
                    mTypeLevel0Presenter.init(helper, ((TaskTitleBean) item),this);
                    break;
                case TYPE_LEVEL_1:
                    //这里是二级标题；
                    mTypeLevel1Presenter.init(helper, ((TaskItemBean) item),this);
                    break;
                case TYPE_LEVEL_2:
                    //这里是三级内容的标签的；
                   // mTypeLevel2Presenter.init(helper, ((TaskItemContentBean) item));
                    //这里要做的一个操作就是显示
                    init(helper, ((TaskItemContentBean) item));
                    break;
            }
        }
    }

    private long time = -1;
    private CountDownTimer countDownTimer;

    public void init(final BaseViewHolder viewHolder, final TaskItemContentBean bean){

        viewHolder.setText(R.id.tv_task_content,bean.getContent());
        viewHolder.setText(R.id.tv_task_button,bean.getButtonContent());

        viewHolder.getView(R.id.tv_task_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(bean.getUrl())) {
                    //这里表示跳转网页；
                    //这里要做的一个操作就是领取高佣任务的奖励的操作；
                    Random random = new Random();
                    int num = random.nextInt(10)+1;
                    if(num %2 == 0) { // 偶数触发计时器
                        countDownTimer = new CountDownTimer(20000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                time = millisUntilFinished / 1000;
                            }

                            @Override
                            public void onFinish() {
                                time = 0;
                            }
                        }.start();
                    }

                    mCurTaskId = bean.getId();
                    HashMap<String, String> paramsMap = new HashMap<>();
                    paramsMap.put(ApiCustomTaskEnd.FROM_UID,mBaseActivity.getUserBean().getUserid());
                    paramsMap.put(ApiCustomTaskEnd.TASK_ID, mCurTaskId);

                    ApiCustomTaskStart apiCustomTaskStart = new ApiCustomTaskStart(new HttpOnNextListener<ResAward>() {

                        @Override
                        public void onNext(ResAward s) {
                            mBaseActivity.startActivity(WebHelperActivity.getIntent(mBaseActivity,bean.getUrl(),bean.getTitle(),false));
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            e.printStackTrace();
                            mBaseActivity.startActivity(WebHelperActivity.getIntent(mBaseActivity,bean.getUrl(),bean.getTitle(),false));
                        }
                    },mBaseActivity,paramsMap);

                    HttpManager.getInstance().doHttpDeal(apiCustomTaskStart);
                }else{
                    // 这里是youmi 操作 已经删除
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(time == 0) { // 倒计时结束
            endTask();
        } else {
            if(countDownTimer != null) {
                countDownTimer.cancel();
            }
        }
    }

    /**
     * 结束任务；
     */
    public void endTask(){

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiCustomTaskEnd.FROM_UID,mBaseActivity.getUserBean().getUserid());
        paramsMap.put(ApiCustomTaskEnd.TASK_ID, mCurTaskId);

        ApiCustomTaskEnd apiCustomTaskStart=new ApiCustomTaskEnd(new HttpOnNextListener<ResAward>() {

            @Override
            public void onNext(ResAward resAward) {

                RedPacketDialog.show(mBaseActivity, resAward.getIntegral(), null);

            }

        },mBaseActivity,paramsMap);

        HttpManager.getInstance().doHttpDeal(apiCustomTaskStart);
    }

}
