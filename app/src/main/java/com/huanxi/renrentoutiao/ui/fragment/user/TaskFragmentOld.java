package com.huanxi.renrentoutiao.ui.fragment.user;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.model.bean.UserBean;
import com.huanxi.renrentoutiao.net.api.ApiGetRedPacket;
import com.huanxi.renrentoutiao.net.api.ApiGetRedPacketTime;
import com.huanxi.renrentoutiao.net.api.user.task.ApiUserTaskList;
import com.huanxi.renrentoutiao.net.bean.user.ResGetRedPacketBean;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.adapter.TaskAdapter;
import com.huanxi.renrentoutiao.ui.adapter.bean.TaskTitleBean;
import com.huanxi.renrentoutiao.ui.dialog.RedPacketDialog;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseLoadingRecyclerViewFragment;
import com.huanxi.renrentoutiao.utils.FormatUtils;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Dinosa on 2018/1/22.
 */

public class TaskFragmentOld extends BaseLoadingRecyclerViewFragment {

    private TaskAdapter mTaskAdapter;
    private View mHeaderView;
    //private View mIvRedPacket;
    private BaseActivity mActivity;
    private TextView mTvCountTimer;
    private View mIvReadPackLogo;
    private TextView mTvOpenRedpack;

    @Override
    public RecyclerView.Adapter getAdapter() {
        if(mTaskAdapter==null){
            mTaskAdapter = new TaskAdapter(getBaseActivity(),null);
            //添加一个头部；
            mHeaderView = View.inflate(getActivity(), R.layout.header_task_new, null);
            initHeaderView(mHeaderView);
            //这里我们添加一个头部
            mTaskAdapter.addHeaderView(mHeaderView);
        }
        return mTaskAdapter;
    }

    public boolean isCanGet=false;

    private void initHeaderView(View mHeaderView) {

        //========这里是新的任务中心的头部的UI===========
        mTvOpenRedpack = ((TextView) mHeaderView.findViewById(R.id.tv_open_red_pack));

        mTvOpenRedpack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCanGet){
                    //这里表示可以领取
                    doGetRedPacket();
                }
            }
        });

        //设置可以点击的操作；
        mTvOpenRedpack.setClickable(true);
        //这里是倒计时的操作；
        mTvCountTimer=((TextView) mHeaderView.findViewById(R.id.tv_timer));
        //显示的是否可拆红包的图标；
        mIvReadPackLogo =  mHeaderView.findViewById(R.id.icon_red_pack);

    }

    /**
     * 这里是获取红包的操作；
     */
    private void doGetRedPacket() {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiGetRedPacket.FROM_UID,mActivity.getUserBean().getUserid());
        ApiGetRedPacket apiGetRedPacket=new ApiGetRedPacket(new HttpOnNextListener<ResGetRedPacketBean>() {
            @Override
            public void onNext(final ResGetRedPacketBean resGetRedPacketBean) {

                //这里表示已经领取成功了；

                RedPacketDialog redPacketDialog = new RedPacketDialog();
                redPacketDialog.show(mActivity, resGetRedPacketBean.getIntegral() + "", new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        //这里我们要做的一个操作就是
                        startCountDown(resGetRedPacketBean.getLasttime());
                        //恢复冷冻的状态；
                        showUI(false);
                        //这里要
                    }
                });
                isCanGet=false;
            }
        },mActivity,paramsMap);
        HttpManager.getInstance().doHttpDeal(apiGetRedPacket);
    }

    public void showUI(boolean canOpen){

        if(canOpen){
            mTvOpenRedpack.setClickable(true);
            mTvOpenRedpack.setClickable(true);
            mTvOpenRedpack.setText("拆红包");
            mTvCountTimer.setVisibility(View.INVISIBLE);
        }else{
            mTvOpenRedpack.setClickable(false);
            mTvOpenRedpack.setEnabled(false);
            mTvOpenRedpack.setText("冷冻中");
            mTvCountTimer.setVisibility(View.VISIBLE);
        }
    }


    CountDownTimer mCountDownTimer;
    /**
     * 开启一个定时器的操作，时间到了，就可以拆红包；
     */
    private void startCountDown(int totalTime) {
        int timeUnit=1*1000;
        totalTime=totalTime*1000;
        //去做一个更新的操作；
        mCountDownTimer = new CountDownTimer(totalTime,timeUnit) {
            @Override
            public void onTick(long millisUntilFinished) {
                //去做一个更新的操作；
                String str="还有"+FormatUtils.formatMillisToTime(millisUntilFinished)+"解冻";
                mTvCountTimer.setText(str);
            }

            @Override
            public void onFinish() {
                //这里我们将修改状态；变成可领取的状态；
                mTvCountTimer.setText("可领取");
                isCanGet=true;
            }
        };
        mCountDownTimer.start();
    }

    @Override
    protected void initView() {
        super.initView();
        mActivity = ((BaseActivity) getActivity());
        //getEasyRefreshLayout().setLoadMoreModel(LoadModel.NONE);
        setLoadingEnable(false);
        getRvHome().setBackgroundColor(Color.WHITE);

    }

    @Override
    protected void initData() {
        super.initData();

        //getEasyRefreshLayout().setEnablePullToRefresh(false);
        //getEasyRefreshLayout().setLoadMoreModel(LoadModel.NONE);
        setLoadingEnable(false);
        setRefreshEnable(false);

        getRedPacketTime();
    }


    public void getRedPacketTime() {
        BaseActivity activity = (BaseActivity) getActivity();
        HashMap<String, String> paramsMap = new HashMap<>();

        paramsMap.put(ApiGetRedPacketTime.FROM_UID,activity.getUserBean().getUserid());

        ApiGetRedPacketTime apiGetRedPacketTime=new ApiGetRedPacketTime(new HttpOnNextListener<ResGetRedPacketBean>() {

            @Override
            public void onNext(ResGetRedPacketBean resGetRedPacketBean) {
                //这里我们将开始一个倒计时；
                if (resGetRedPacketBean.getLasttime()==0) {
                    //这里表示可以领取；
                    isCanGet=true;
                    showUI(true);
                }else{
                    isCanGet=false;
                    showUI(false);
                    startCountDown(resGetRedPacketBean.getLasttime());
                }
            }
        },activity,paramsMap);

        HttpManager.getInstance().doHttpDeal(apiGetRedPacketTime);
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllTask(false);
    }

    @Override
    public void requestAdapterData(boolean isFirst) {
        //这里我们请求数据；
       getAllTask(true);
    }

    @Override
    public void requestNextAdapterData() {
        loadMoreComplete();
    }

    /**
     * 这里是获取所有的任务的列表操作；
     */
    public void getAllTask(final boolean isFirst){

        BaseActivity activity = (BaseActivity) getActivity();

        UserBean userBean = activity.getUserBean();

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiUserTaskList.from_uid,userBean.getUserid());

        ApiUserTaskList apiUserTaskList=new ApiUserTaskList(new HttpOnNextListener<List<TaskTitleBean>>() {

            @Override
            public void onNext(List<TaskTitleBean> taskTitleBeen) {
                //这里我们处理一下数据的操作
                refreshTask(taskTitleBeen);
                if(isFirst){
                    showSuccess();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (isFirst) {
                    showFaild();
                }
            }
        },activity,paramsMap);

        HttpManager.getInstance().doHttpDeal(apiUserTaskList);
    }

    private void refreshTask(List<TaskTitleBean> taskTitleBeen) {

        ArrayList<TaskTitleBean> allItem = new ArrayList<>();
        if (taskTitleBeen != null) {
            for (TaskTitleBean taskTitleBean : taskTitleBeen) {
                taskTitleBean.addAllSubItem();
                allItem.add(taskTitleBean);
            }
        }
        mTaskAdapter.replaceData(allItem);
        mTaskAdapter.expandAll();

    }
}
