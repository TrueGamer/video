package com.huanxi.renrentoutiao.ui.fragment.user;

import android.content.DialogInterface;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.model.bean.UserBean;
import com.huanxi.renrentoutiao.net.api.user.task.ApiAllUserTaskList;
import com.huanxi.renrentoutiao.net.api.user.task.ApiUserSign;
import com.huanxi.renrentoutiao.net.api.user.task.ApiUserTaskList;
import com.huanxi.renrentoutiao.net.api.user.userInfo.ApiGetUserSign;
import com.huanxi.renrentoutiao.net.bean.news.ResAward;
import com.huanxi.renrentoutiao.net.bean.sign.ResGetSignList;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.adapter.AdvanceTaskAdapter;
import com.huanxi.renrentoutiao.ui.adapter.TaskAdapter;
import com.huanxi.renrentoutiao.ui.adapter.bean.TaskTitleBean;
import com.huanxi.renrentoutiao.ui.dialog.RedPacketDialog;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseLoadingFrament;
import com.huanxi.renrentoutiao.ui.view.sign.NewSignView;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Dinosa on 2018/1/22.
 */

public class TaskFragmentNewOld extends BaseLoadingFrament {


    @BindView(R.id.iv_sign_buttom)
    ImageView mIvSignButtom;

    @BindView(R.id.nsv_sign_view)
    NewSignView mNsvSignView;

    @BindView(R.id.rv_new_task)
    RecyclerView  mRvNewTask;

    @BindView(R.id.rv_normal_task)
    RecyclerView mRvNormalTask;

    @BindView(R.id.rv_advance_task)
    RecyclerView mRvAvanceTask;

    @BindView(R.id.ll_new_task)
    LinearLayout mLlNewTask;

    private TaskAdapter mNormalTaskAdapter;
    private TaskAdapter mNewTaskAdapter;

    private TaskAdapter mAdvanceAdapter;

    @BindView(R.id.nsv_scroll_view)
    public NestedScrollView mNestedScrollView;


    @Override
    public View getLoadingContentView() {
        return inflatLayout(R.layout.fragment_new_task);
    }

    @Override
    protected void initView() {
        super.initView();
        //设置适配器；

        mRvNewTask.setLayoutManager(new LinearLayoutManager(getBaseActivity()){

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mNewTaskAdapter=new TaskAdapter(getBaseActivity(),null);
        mNewTaskAdapter.bindToRecyclerView(mRvNewTask);


        mRvAvanceTask.setLayoutManager(new LinearLayoutManager(getBaseActivity()){

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mAdvanceAdapter=new AdvanceTaskAdapter(getBaseActivity(),null);
        mAdvanceAdapter.bindToRecyclerView(mRvAvanceTask);


        //==============
        mRvNormalTask.setLayoutManager(new LinearLayoutManager(getBaseActivity()){

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mNormalTaskAdapter=new TaskAdapter(getBaseActivity(),null);
        mNormalTaskAdapter.bindToRecyclerView(mRvNormalTask);

        mIvSignButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里我们要做的一个操作就是签到成功；
                onClickSign();
            }
        });

        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                System.out.println("scrollY:"+scrollY+" oldScrolly"+oldScrollY);
                //这里我们做一个处理的操作；
                if (scrollY-oldScrollY>0) {
                    //这里表示向上滚动
                    if (mOnTaskScrollListener != null) {
                        mOnTaskScrollListener.onScrollUp();
                    }
                }else{
                    //向下滚动；
                    if (mOnTaskScrollListener != null) {
                        mOnTaskScrollListener.onScrollDown();
                    }
                }

            }
        });


    }

    public  void scrollToTop(){
        mNestedScrollView.scrollTo(0,0);
    }


    public void onClickSign(){

        UserBean userBean = getMyApplication().getUserBean();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(ApiUserSign.FROM_UID,userBean.getUserid());

        ApiUserSign apiUserSign=new ApiUserSign(new HttpOnNextListener<ResAward>() {
            @Override
            public void onNext(ResAward resSign) {

                setSignButonUnEnable();
                mNsvSignView.updateSign();
                RedPacketDialog redPacketDialog=new RedPacketDialog();
                redPacketDialog.show(getBaseActivity(), resSign.getIntegral() + "", new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                        getAllTask(false);

                    }
                });
            }
        },getBaseActivity(),hashMap);
        //弹出对话框，显示获取的金币；
        HttpManager.getInstance().doHttpDeal(apiUserSign);
    }

    @Override
    protected void initData() {
        super.initData();
        //这里请求接口签到接口信息；

        //请求数据；
        getAllTask(true);
        getSignInfo();//获取签到信息

    }

    @Override
    public void onResume() {
        super.onResume();
        getAllTask(false);

        mNormalTaskAdapter.onResume();
        mAdvanceAdapter.onResume();
        mNewTaskAdapter.onResume();
    }

    public void getSignInfo() {

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiGetUserSign.FROM_UID,getUserBean().getUserid());

        ApiGetUserSign apiGetUserSign=new ApiGetUserSign(new HttpOnNextListener<ResGetSignList>() {

            @Override
            public void onNext(ResGetSignList resGetSignList) {
                //这里要做的一个操作就是显示签到信息

                mNsvSignView.refreshData(getSignBean(resGetSignList));

                if(resGetSignList.getHassign()==1){
                    //这里我们要做的一个操作就就是显示内容；
                    setSignButonUnEnable();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                showFaild();
            }
        },getBaseActivity(),paramsMap);
        HttpManager.getInstance().doHttpDeal(apiGetUserSign);
    }

    public void setSignButonUnEnable(){

        mIvSignButtom.setImageResource(R.drawable.bg_signed_button);
        mIvSignButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("今天已经签到！");
            }
        });
    }

    /**
     * 这里我们组装一下签到的信息
     * @param resGetSignList
     * @return
     */
    private List<NewSignView.SignBean> getSignBean(ResGetSignList resGetSignList) {

        ArrayList<NewSignView.SignBean> datas = new ArrayList<NewSignView.SignBean>();

        List<String> integrallist = resGetSignList.getIntegrallist();
        for (int i=0;i<integrallist.size();i++){

            String award = integrallist.get(i);

            if(i<resGetSignList.getHasday()){
                NewSignView.SignBean signBean = new NewSignView.SignBean(true, (i + 1) + "天", award);
                datas.add(signBean);
            }else{
                NewSignView.SignBean signBean = new NewSignView.SignBean(false, (i + 1) + "天", award);
                datas.add(signBean);
            }
        }
        return datas;
    }

    /**
     * 这里是获取所有的任务的列表操作；
     */
    public void getAllTask(final boolean isFirst){

        BaseActivity activity = (BaseActivity) getActivity();

        UserBean userBean = activity.getUserBean();

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiUserTaskList.from_uid,userBean.getUserid());

        ApiAllUserTaskList apiUserTaskList=new ApiAllUserTaskList(new HttpOnNextListener<List<TaskTitleBean>>() {

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
                if("新手任务".equals(taskTitleBean.getTitle())){

                    if(taskTitleBean.getList()!=null && taskTitleBean.getList().size()>0){
                        mLlNewTask.setVisibility(View.VISIBLE);
                        mNewTaskAdapter.replaceData(taskTitleBean.getList());
                    }else{
                        mLlNewTask.setVisibility(View.GONE);
                    }
                }
                if("日常任务".equals(taskTitleBean.getTitle())){
                    //这里是日常任务；
                    mNormalTaskAdapter.replaceData(taskTitleBean.getList());
                }

                if("高佣任务".equals(taskTitleBean.getTitle())){
                    mAdvanceAdapter.replaceData(taskTitleBean.getList());
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

    public interface OnTaskScrollListener{

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
    }
}
