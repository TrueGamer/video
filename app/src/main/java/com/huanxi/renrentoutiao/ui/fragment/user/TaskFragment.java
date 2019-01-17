package com.huanxi.renrentoutiao.ui.fragment.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.globle.ConstantUrl;
import com.huanxi.renrentoutiao.model.bean.UserBean;
import com.huanxi.renrentoutiao.net.api.ApiGetRedPacket;
import com.huanxi.renrentoutiao.net.api.ApiGetRedPacketTime;
import com.huanxi.renrentoutiao.net.api.user.task.ApiAllUserTaskList;
import com.huanxi.renrentoutiao.net.api.user.task.ApiUserSign;
import com.huanxi.renrentoutiao.net.api.user.task.ApiUserTaskList;
import com.huanxi.renrentoutiao.net.api.user.userInfo.ApiGetUserSign;
import com.huanxi.renrentoutiao.net.bean.news.ResAward;
import com.huanxi.renrentoutiao.net.bean.sign.ResGetSignList;
import com.huanxi.renrentoutiao.net.bean.user.ResGetRedPacketBean;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.activity.other.LuckyWalkActivity;
import com.huanxi.renrentoutiao.ui.activity.other.SplashActivity;
import com.huanxi.renrentoutiao.ui.activity.user.QuestionsActivity;
import com.huanxi.renrentoutiao.ui.adapter.AdvanceTaskAdapter;
import com.huanxi.renrentoutiao.ui.adapter.TaskAdapter;
import com.huanxi.renrentoutiao.ui.adapter.bean.TaskTitleBean;
import com.huanxi.renrentoutiao.ui.dialog.RedPacketDialog;
import com.huanxi.renrentoutiao.ui.dialog.SignDialog;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseLoadingFrament;
import com.huanxi.renrentoutiao.ui.view.sign.NewSignView;
import com.huanxi.renrentoutiao.utils.FormatUtils;
import com.huanxi.renrentoutiao.utils.SharedPreferencesUtils;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Dinosa on 2018/1/22.
 */

public class TaskFragment extends BaseLoadingFrament {


    @BindView(R.id.nsv_scroll_view)
    public NestedScrollView mNestedScrollView;


    @BindView(R.id.ll_task_container)
    LinearLayout mLlTaskContainer;

    @BindView(R.id.tv_open_red_pack_time)
    TextView mTvCountTimer;

    @BindView(R.id.fl_open_red_packet)
    FrameLayout mFlRedPacketContainer;

    @BindView(R.id.tv_sign)
    TextView mTvSign;

    @BindView(R.id.tv_sign_desc)
    TextView mTvSignDesc;

    @BindView(R.id.iv_red_packet_icon)
    ImageView mIvRedPacketIcon;

    @BindView(R.id.iv_gold_game)
    ImageView iv_gold_game;



    private TaskAdapter mNewTaskAdapter; //新手任务；
    private TaskAdapter mAdvanceAdapter; //高级任务；
    private TaskAdapter mNormalTaskAdapter; //日常任务；

    private View mNewTaskView;
    private View mAdvanceView;
    private View mNormalTaskView;


    @Override
    public View getLoadingContentView() {
        return inflatLayout(R.layout.fragment_newest_task);
    }

    @OnClick(R.id.iv_back)
    public void onClickBack(){
        getBaseActivity().finish();
    }

    @OnClick(R.id.iv_question)
    public void onClickQuestion(){

        startActivity(new Intent(getBaseActivity(), QuestionsActivity.class));

    }

    @Override
    protected void initView() {
        super.initView();
        //设置适配器；

        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                System.out.println("scrollY:" + scrollY + " oldScrolly" + oldScrollY);
                //这里我们做一个处理的操作；
                if (scrollY - oldScrollY > 0) {
                    //这里表示向上滚动
                    if (mOnTaskScrollListener != null) {
                        mOnTaskScrollListener.onScrollUp();
                    }
                } else {
                    //向下滚动；
                    if (mOnTaskScrollListener != null) {
                        mOnTaskScrollListener.onScrollDown();
                    }
                }

            }
        });



    }


    @OnClick(R.id.iv_gold_game)
    public void onClickGoldGame(){

//        Intent intent = new Intent(getBaseActivity(), LuckyWalkActivity.class);
//        startActivity(intent);
        Intent intent = new Intent(getBaseActivity(), SplashActivity.class);
        intent.putExtra("goldGame" , "goldGame");
        startActivity(intent);

    }

    public void scrollToTop() {
        mNestedScrollView.scrollTo(0, 0);
    }


    public void onClickSign() {

        UserBean userBean = getMyApplication().getUserBean();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(ApiUserSign.FROM_UID, userBean.getUserid());

        ApiUserSign apiUserSign = new ApiUserSign(new HttpOnNextListener<ResAward>() {
            @Override
            public void onNext(ResAward resSign) {

                setSignButonUnEnable(resSign.getIntegral());

                String integral = getUserBean().getIntegral();

                int totalMoney=0;
                int getMoney=0;
                try {
                    totalMoney = Integer.parseInt(integral);
                    getMoney=Integer.parseInt(resSign.getIntegral());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                SignDialog signDialog=new SignDialog(getBaseActivity(),totalMoney+getMoney+"",getMoney+"");

                signDialog.show();
               /* RedPacketDialog redPacketDialog = new RedPacketDialog();
                redPacketDialog.show(getBaseActivity(), resSign.getNextintegral() + "", new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                        getAllTask(false);

                    }
                });*/
                getAllTask(false);
            }
        }, getBaseActivity(), hashMap);
        //弹出对话框，显示获取的金币；
        HttpManager.getInstance().doHttpDeal(apiUserSign);
    }

    private boolean tabSpecs;

    @Override
    protected void initData() {
        super.initData();
        //这里请求接口签到接口信息；

        tabSpecs = SharedPreferencesUtils.getInstance(getBaseActivity()).getBoolean(ConstantUrl.IS_SHOW);
        if(tabSpecs) {
            mFlRedPacketContainer.setVisibility(View.VISIBLE);
            iv_gold_game.setVisibility(View.VISIBLE);
        } else {
            mFlRedPacketContainer.setVisibility(View.GONE);
            iv_gold_game.setVisibility(View.GONE);
        }

        //请求数据；
        getAllTask(true);
        getSignInfo();//获取签到信息
        getRedPacketTime();
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllTask(false);

        if (mNormalTaskAdapter != null) {
            mNormalTaskAdapter.onResume();
        }
        if (mAdvanceAdapter != null) {
            mAdvanceAdapter.onResume();
        }

        if (mNewTaskAdapter != null) {
            mNewTaskAdapter.onResume();
        }
    }

    public void getSignInfo() {

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiGetUserSign.FROM_UID, getUserBean().getUserid());

        ApiGetUserSign apiGetUserSign = new ApiGetUserSign(new HttpOnNextListener<ResGetSignList>() {

            @Override
            public void onNext(ResGetSignList resGetSignList) {
                //这里要做的一个操作就是显示签到信息

                //mNsvSignView.refreshData(getSignBean(resGetSignList));

                if (resGetSignList.getHassign() == 1) {
                    //这里我们要做的一个操作就就是显示内容；
                    setSignButonUnEnable(resGetSignList.getIntegral());
                }else{
                    //这里表示没有签到过；
                    mTvSign.setText("签到+"+resGetSignList.getIntegral()+"金币");
                    mTvSign.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //这里是需要弹出一个对话框的操作的； 先加载一个广告
                            if(tabSpecs) {
                                Intent intent = new Intent(getBaseActivity() , SplashActivity.class);
                                intent.putExtra("sign" , "sign");
                                startActivityForResult(intent , 121);
                            } else {
                                onClickSign();
                            }
                        }
                    });
                }
            }
        }, getBaseActivity(), paramsMap);
        HttpManager.getInstance().doHttpDeal(apiGetUserSign);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 121) {
            onClickSign();
        } else if(resultCode == 122) {
            doGetRedPacket();
        }
    }

    public void setSignButonUnEnable(final String integral) {

        mTvSign.setEnabled(false);
        mTvSign.setText("明天+"+integral+"金币");
        mTvSignDesc.setText("今日已签到");
        mTvSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("今天已经签到,明天继续签到获"+integral+"金币！");
            }
        });
    }

    /**
     * 这里我们组装一下签到的信息
     *
     * @param resGetSignList
     * @return
     */
    private List<NewSignView.SignBean> getSignBean(ResGetSignList resGetSignList) {

        ArrayList<NewSignView.SignBean> datas = new ArrayList<NewSignView.SignBean>();

        List<String> integrallist = resGetSignList.getIntegrallist();
        for (int i = 0; i < integrallist.size(); i++) {

            String award = integrallist.get(i);

            if (i < resGetSignList.getHasday()) {
                NewSignView.SignBean signBean = new NewSignView.SignBean(true, (i + 1) + "天", award);
                datas.add(signBean);
            } else {
                NewSignView.SignBean signBean = new NewSignView.SignBean(false, (i + 1) + "天", award);
                datas.add(signBean);
            }
        }
        return datas;
    }

    /**
     * 这里是获取所有的任务的列表操作；
     */
    public void getAllTask(final boolean isFirst) {

        BaseActivity activity = (BaseActivity) getActivity();

        UserBean userBean = activity.getUserBean();

        if(userBean != null) {
            HashMap<String, String> paramsMap = new HashMap<>();
            paramsMap.put(ApiUserTaskList.from_uid, userBean.getUserid());

            ApiAllUserTaskList apiUserTaskList = new ApiAllUserTaskList(new HttpOnNextListener<List<TaskTitleBean>>() {

                @Override
                public void onNext(List<TaskTitleBean> taskTitleBeen) {
                    //这里我们处理一下数据的操作
                    try {
                        refreshTask(taskTitleBeen);
                        if (isFirst) {
                            showSuccess();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    if (isFirst) {
                        showFaild();
                    }
                }
            }, activity, paramsMap);

            HttpManager.getInstance().doHttpDeal(apiUserTaskList);
        }
    }

    /**
     * 所有的任务的View;
     */
    public  List<View> mTaskViews=new ArrayList<>();


    private void refreshTask(List<TaskTitleBean> taskTitleBeen) {

        ArrayList<TaskTitleBean> allItem = new ArrayList<>();

        if (taskTitleBeen != null) {

            for (TaskTitleBean taskTitleBean : taskTitleBeen) {

                taskTitleBean.addAllSubItem();
                if ("新手任务".equals(taskTitleBean.getTitle())) {

                    if (taskTitleBean.getList() != null && taskTitleBean.getList().size() > 0) {

                        if (mNewTaskView == null) {

                            //这里要做的一个操作就是加载然后填充；

                            mNewTaskView = LayoutInflater.from(getBaseActivity()).inflate(R.layout.layout_task_item, mLlTaskContainer, false);

                            ImageView newTaskIcon = (ImageView) mNewTaskView.findViewById(R.id.iv_task_icon);
                            TextView newTaskDesc = (TextView) mNewTaskView.findViewById(R.id.tv_task_desc);

                            newTaskDesc.setText("新手任务");
                            newTaskIcon.setImageResource(R.drawable.icon_new_task);

                            RecyclerView newList = (RecyclerView) mNewTaskView.findViewById(R.id.rv_new_task);
                            newList.setLayoutManager(new LinearLayoutManager(getBaseActivity()){

                                @Override
                                public boolean canScrollVertically() {
                                    return false;
                                }
                            });
                            mNewTaskAdapter=new TaskAdapter(getBaseActivity(),null);
                            newList.setAdapter(mNewTaskAdapter);
                            mNewTaskAdapter.replaceData(taskTitleBean.getList());

                            mLlTaskContainer.addView(mNewTaskView);

                        }else{
                            mNewTaskAdapter.replaceData(taskTitleBean.getList());
                        }

                    } else {
                        if (mNewTaskView != null) {
                            mNewTaskAdapter.replaceData(null);
                            mNewTaskView.setVisibility(View.GONE);
                        }
                    }
                }
                if ("日常任务".equals(taskTitleBean.getTitle())) {
                    //这里是日常任务；

                    if (taskTitleBean.getList() != null && taskTitleBean.getList().size() > 0) {

                        if (mNormalTaskView == null) {

                            //这里要做的一个操作就是加载然后填充；

                            mNormalTaskView = LayoutInflater.from(getBaseActivity()).inflate(R.layout.layout_task_item, mLlTaskContainer, false);

                            ImageView newTaskIcon = (ImageView) mNormalTaskView.findViewById(R.id.iv_task_icon);
                            TextView newTaskDesc = (TextView) mNormalTaskView.findViewById(R.id.tv_task_desc);

                            newTaskDesc.setText("日常任务");
                            newTaskIcon.setImageResource(R.drawable.icon_normal_task);

                            RecyclerView newList = (RecyclerView) mNormalTaskView.findViewById(R.id.rv_new_task);
                            newList.setLayoutManager(new LinearLayoutManager(getBaseActivity()){

                                @Override
                                public boolean canScrollVertically() {
                                    return false;
                                }
                            });
                            mNormalTaskAdapter=new TaskAdapter(getBaseActivity(),null);
                            newList.setAdapter(mNormalTaskAdapter);
                            mNormalTaskAdapter.replaceData(taskTitleBean.getList());

                            mLlTaskContainer.addView(mNormalTaskView);

                        }else{
                            mNormalTaskAdapter.replaceData(taskTitleBean.getList());
                        }

                    } else {
                        if (mNormalTaskView != null) {
                            mNormalTaskAdapter.replaceData(null);
                            mNormalTaskView.setVisibility(View.GONE);
                        }
                    }
                }

                if ("高佣任务".equals(taskTitleBean.getTitle())) {

                    if (taskTitleBean.getList() != null && taskTitleBean.getList().size() > 0) {

                        if (mAdvanceView == null) {

                            //这里要做的一个操作就是加载然后填充；

                            mAdvanceView = LayoutInflater.from(getBaseActivity()).inflate(R.layout.layout_task_item, mLlTaskContainer, false);

                            ImageView newTaskIcon = (ImageView) mAdvanceView.findViewById(R.id.iv_task_icon);
                            TextView newTaskDesc = (TextView) mAdvanceView.findViewById(R.id.tv_task_desc);

                            newTaskDesc.setText("高佣任务");
                            newTaskIcon.setImageResource(R.drawable.icon_advance_task);

                            RecyclerView newList = (RecyclerView) mAdvanceView.findViewById(R.id.rv_new_task);
                            newList.setLayoutManager(new LinearLayoutManager(getBaseActivity()){

                                @Override
                                public boolean canScrollVertically() {
                                    return false;
                                }

                            });
                            mAdvanceAdapter = new AdvanceTaskAdapter(getBaseActivity(),null);
                            newList.setAdapter(mAdvanceAdapter);
                            mAdvanceAdapter.replaceData(taskTitleBean.getList());

                            mLlTaskContainer.addView(mAdvanceView);

                        }else{
                            mAdvanceAdapter.replaceData(taskTitleBean.getList());
                        }

                    } else {
                        if (mAdvanceView != null) {
                            mAdvanceAdapter.replaceData(null);
                            mAdvanceView.setVisibility(View.GONE);
                        }
                    }
                }

            }
        }

        //这里对数据进行一个解析的操作；
    }



    //这里是滚动监听的回掉操作；
    public OnTaskScrollListener mOnTaskScrollListener;

    public OnTaskScrollListener getOnTaskScrollListener() {
        return mOnTaskScrollListener;
    }

    public void setOnTaskScrollListener(OnTaskScrollListener onTaskScrollListener) {
        mOnTaskScrollListener = onTaskScrollListener;
    }

    public interface OnTaskScrollListener {

        //向上滚动；
        public void onScrollUp();

        //向下滚动
        public void onScrollDown();
    }

    @Override
    protected void onRetry() {
        super.onRetry();
        //请求数据；
        getAllTask(true);
        getSignInfo();//获取签到信息
        getRedPacketTime();
    }

    public void getRedPacketTime() {
        HashMap<String, String> paramsMap = new HashMap<>();

        paramsMap.put(ApiGetRedPacketTime.FROM_UID,getUserBean().getUserid());

        ApiGetRedPacketTime apiGetRedPacketTime=new ApiGetRedPacketTime(new HttpOnNextListener<ResGetRedPacketBean>() {

            @Override
            public void onNext(ResGetRedPacketBean resGetRedPacketBean) {
                //这里我们将开始一个倒计时；
                if (resGetRedPacketBean.getLasttime()==0) {
                    //这里表示可以领取；
                    showUI(true);
                   // mTvCountTimer.setText("可领取");
                }else{
                    showUI(false);
                    startCountDown(resGetRedPacketBean.getLasttime());
                }
            }
        },getBaseActivity(),paramsMap);

        HttpManager.getInstance().doHttpDeal(apiGetRedPacketTime);
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
                //String str="还有"+ FormatUtils.formatMillisToTime(millisUntilFinished)+"解冻";
                if (mTvCountTimer != null) {

                    mTvCountTimer.setText(FormatUtils.formatMillisToTime(millisUntilFinished));

                }
            }

            @Override
            public void onFinish() {
                //这里我们将修改状态；变成可领取的状态；
              //  mTvCountTimer.setText("可领取");
                showUI(true);
            }
        };
        mCountDownTimer.start();
    }


    /**
     * 是否可以打开；
     * @param canOpen
     */
    private void showUI(boolean canOpen) {

        if(canOpen){
            //设置可以点击，
            //mFlRedPacketContainer.setClickable(true);
            mFlRedPacketContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //这里我们要做的一个操作就是获取红包； 先打开广告
                    Intent intent = new Intent(getBaseActivity() , SplashActivity.class);
                    intent.putExtra("redPacket" , "redPacket");
                    startActivityForResult(intent , 122);
                }
            });
            mIvRedPacketIcon.setImageResource(R.drawable.bg_task_open_red_packet_new);
            mTvCountTimer.setVisibility(View.INVISIBLE);
        }else{
            //设置不可以点击；
            //mFlRedPacketContainer.setClickable(false);
            mFlRedPacketContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toast(mTvCountTimer.getText().toString()+"可拆");
                }
            });
            mIvRedPacketIcon.setImageResource(R.drawable.bg_task_red_packet_waiting);
            mTvCountTimer.setVisibility(View.VISIBLE);
        }

    }



    /**
     * 这里是获取红包的操作；
     */
    private void doGetRedPacket() {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiGetRedPacket.FROM_UID,getUserBean().getUserid());
        ApiGetRedPacket apiGetRedPacket=new ApiGetRedPacket(new HttpOnNextListener<ResGetRedPacketBean>() {
            @Override
            public void onNext(final ResGetRedPacketBean resGetRedPacketBean) {

                //这里表示已经领取成功了；

                RedPacketDialog redPacketDialog = new RedPacketDialog();
                redPacketDialog.show(getBaseActivity(), resGetRedPacketBean.getIntegral() + "", new DialogInterface.OnDismissListener() {
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
        },getBaseActivity(),paramsMap);
        HttpManager.getInstance().doHttpDeal(apiGetRedPacket);
    }




}
