package com.huanxi.renrentoutiao.ui.adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.IntRange;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.IExpandable;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.net.api.ApiSMSSend;
import com.huanxi.renrentoutiao.net.api.user.ApiRequestAddInviteCode;
import com.huanxi.renrentoutiao.net.api.user.task.ApiCustomTaskEnd;
import com.huanxi.renrentoutiao.net.api.user.task.ApiCustomTaskStart;
import com.huanxi.renrentoutiao.net.api.user.userInfo.ApiBindPhoneNumber;
import com.huanxi.renrentoutiao.net.api.user.userInfo.ApiOnlyBindPhoneNumber;
import com.huanxi.renrentoutiao.net.bean.ResEmpty;
import com.huanxi.renrentoutiao.net.bean.news.ResAward;
import com.huanxi.renrentoutiao.ui.activity.RewardVideoActivity;
import com.huanxi.renrentoutiao.ui.activity.WebHelperActivity;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.activity.other.InviteFriendActivityNew;
import com.huanxi.renrentoutiao.ui.activity.other.LuckyWalkActivity;
import com.huanxi.renrentoutiao.ui.activity.other.MainActivity;
import com.huanxi.renrentoutiao.ui.activity.user.ExposureIncomeActivity;
import com.huanxi.renrentoutiao.ui.activity.user.UserTaskActivity;
import com.huanxi.renrentoutiao.ui.adapter.bean.TaskItemBean;
import com.huanxi.renrentoutiao.ui.adapter.bean.TaskItemContentBean;
import com.huanxi.renrentoutiao.ui.adapter.bean.TaskTitleBean;
import com.huanxi.renrentoutiao.ui.dialog.InputDialog;
import com.huanxi.renrentoutiao.ui.dialog.RedPacketDialog;
import com.huanxi.renrentoutiao.utils.TTAdManagerHolder;
import com.huanxi.renrentoutiao.utils.TToast;
import com.huanxi.renrentoutiao.utils.ValidUtils;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Dinosa on 2018/1/22.
 */

public class TaskAdapter extends BaseAdsAdapter<MultiItemEntity> {


    public static final int TYPE_LEVEL_0 = 0;   //一级标题
    public static final int TYPE_LEVEL_1 = 1;   //二级标题；
    public static final int TYPE_LEVEL_2 = 2;    //三级标题；

    protected final TypeLevel0Presenter mTypeLevel0Presenter;
    protected final TypeLevel1Presenter mTypeLevel1Presenter;
    protected final TypeLevel2Presenter mTypeLevel2Presenter;
    protected final BaseActivity mBaseActivity;

    private TTAdNative mTTAdNative;
    private TTRewardVideoAd mttRewardVideoAd;
    private boolean mHasShowDownloadActive = false;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public TaskAdapter(BaseActivity baseActivity,List<MultiItemEntity> data) {
        super(data);

        mBaseActivity = baseActivity;

        mTypeLevel0Presenter = new TypeLevel0Presenter();
        mTypeLevel1Presenter = new TypeLevel1Presenter();
        mTypeLevel2Presenter = new TypeLevel2Presenter();

        addItemType(TYPE_LEVEL_0, R.layout.item_task_title);
        addItemType(TYPE_LEVEL_1, R.layout.item_task_second_title);
        addItemType(TYPE_LEVEL_2, R.layout.item_task_third_content);

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
                    mTypeLevel2Presenter.init(helper, ((TaskItemContentBean) item));
                    break;
            }
        }


    }


    @Override
    public int expandAll(int position, boolean animate, boolean notify) {
        position -= getHeaderLayoutCount();

        MultiItemEntity endItem = null;
        if (position + 1 < this.mData.size()) {
            endItem = getItem(position + 1);
        }

        IExpandable expandable = getExpandableItem(position);
        if (expandable == null || !hasSubItems(expandable)) {
            return 0;
        }

        int count = expand(position + getHeaderLayoutCount(), false, false);
        for (int i = position + 1; i < this.mData.size(); i++) {
            MultiItemEntity item = getItem(i);

            if (item == endItem) {
                break;
            }
            if (isExpandable(item)) {
               // count += expand(i + getHeaderLayoutCount(), false, false);
            }
        }

        if (notify) {
            if (animate) {
                notifyItemRangeInserted(position + getHeaderLayoutCount() + 1, count);
            } else {
                notifyDataSetChanged();
            }
        }
        return count;
    }

    private IExpandable getExpandableItem(int position) {
        MultiItemEntity item = getItem(position);
        if (isExpandable(item)) {
            return (IExpandable) item;
        } else {
            return null;
        }
    }

    private boolean hasSubItems(IExpandable item) {

        if (item == null) {
            return false;
        }

        List list = item.getSubItems();
        return list != null && list.size() > 0;
    }





    /**
     * 任务的分类
     */
    public class TypeLevel0Presenter{

        public void init(final BaseViewHolder viewHolder, final TaskTitleBean bean, final TaskAdapter adapter){



            int imgRes=bean.isExpanded()?R.drawable.icon_arrow_up:R.drawable.icon_arrow_down;

            viewHolder.setImageResource(R.id.iv_arrow,imgRes);

            viewHolder.setText(R.id.tv_title,bean.getTitle());

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (bean.isExpanded()) {
                        //是需要让其收缩起来的；
                        adapter.collapse(viewHolder.getAdapterPosition());
                    }else{
                        adapter.expand(viewHolder.getAdapterPosition());
                    }

                }
            });
        }

    }

    /**
     * 内容的标题；
     */
    public class TypeLevel1Presenter{

        public void init(final BaseViewHolder viewHolder, final TaskItemBean bean, final TaskAdapter adapter){

            int imgRes=bean.isExpanded()?R.drawable.icon_arrow_up : R.drawable.icon_arrow_down;

            viewHolder.setImageResource(R.id.iv_arrow , imgRes);

            ((CheckBox) viewHolder.getView(R.id.cb_is_complete)).setChecked(bean.isComplete());

            viewHolder.setText(R.id.tv_task_title,bean.getTitle());
            viewHolder.setText(R.id.tv_task_money,bean.getIntegral());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (bean.isExpanded()) {
                        //是需要让其收缩起来的；
                        adapter.collapse(viewHolder.getAdapterPosition());
                    }else{
                        adapter.expand(viewHolder.getAdapterPosition());
                    }

                }
            });

        }
    }

    protected String mCurTaskId;

    /**
     * 这里是任务的内容；
     */
    public class TypeLevel2Presenter{

        public void init(final BaseViewHolder viewHolder, final TaskItemContentBean bean){

            viewHolder.setText(R.id.tv_task_content,bean.getContent());
            viewHolder.setText(R.id.tv_task_button,bean.getButtonContent());


            viewHolder.getView(R.id.tv_task_button).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Log.i("info" , "getButtonContent="+bean.getButtonContent());
                    if(bean.getButtonContent().equals("签到")){
                        UserTaskActivity baseActivity = (UserTaskActivity) mBaseActivity;
                        baseActivity.getTaskFragment().scrollToTop();
                        //这里是直接滚动到上面去；
                        //getRecyclerView().scrollToPosition(0);
                    } else if(bean.getButtonContent().equals("拆红包")){

                        //这里要做的一个操作就是让显示出来；
                        ((UserTaskActivity) mBaseActivity).redPackShowAnimator();

                    } else if (bean.getButtonContent().equals("晒收入")) {

                        //这里执行的是一个晒收入的页面；
                        mBaseActivity.startActivity(new Intent(mBaseActivity,ExposureIncomeActivity.class));

                    } else if(bean.getButtonContent().equals("填写邀请码")){

                        //填写邀请码的页面；
                        //输入框操作；
                        doAddInviteCode(viewHolder);

                    }else if(bean.getButtonContent().equals("邀请码奖励")){

                        //这里是领取邀请码的奖励
                        //关闭这个条目；
                        getInviteAward(viewHolder);

                    } else if(bean.getButtonContent().equals("浏览")){

                        //跳转到首页；
                        Intent intent = new Intent(mContext,MainActivity.class);
                        intent.putExtra(MainActivity.TAB_INDEX,MainActivity.HOME);
                        mBaseActivity.startActivity(intent);

                    } else if(bean.getButtonContent().equals("新闻浏览")){

                        //跳转到首页；
                        Intent intent = new Intent(mContext,MainActivity.class);
                        intent.putExtra(MainActivity.TAB_INDEX,MainActivity.HOME);
                        mBaseActivity.startActivity(intent);

                    } else if(bean.getButtonContent().equals("视频浏览")){

                        //跳转到首页；
                        Intent intent = new Intent(mContext,MainActivity.class);
                        intent.putExtra(MainActivity.TAB_INDEX,MainActivity.VIDEO);
                        mBaseActivity.startActivity(intent);

                    } else if(bean.getButtonContent().equals("点击得金币")) {
                        Log.i("info" , "====");
                        //跳转到首页；
                        Intent intent = new Intent(mContext,MainActivity.class);
                        intent.putExtra(MainActivity.TAB_INDEX,MainActivity.HOME);
                        mBaseActivity.startActivity(intent);

                    } else if(bean.getButtonContent().equals("分享文章")){

                        //跳转到首页；
                        Intent intent = new Intent(mContext,MainActivity.class);
                        intent.putExtra(MainActivity.TAB_INDEX,0);
                        mBaseActivity.startActivity(intent);

                    }else if(bean.getButtonContent().equals("金币大转盘")){

                        mBaseActivity.startActivity(LuckyWalkActivity.getIntent(mBaseActivity,false));

                    } else if(bean.getButtonContent().equals("邀请好友")){

                        //邀请好友的操作；
                        //mBaseActivity.startActivity(MyFriendsActivity.getIntent(mBaseActivity, true));
                        Intent intent = new Intent(mBaseActivity, InviteFriendActivityNew.class);
                        mBaseActivity.startActivity(intent);

                    } else if(bean.getButtonContent().equals("提现")){

                        //mBaseActivity.startActivity(new Intent(mBaseActivity, IntergralShopActivity.class));
                        mBaseActivity.startActivity(LuckyWalkActivity.getIntent(mBaseActivity,true));

                    } else if("绑定手机号".equals(bean.getButtonContent())){

                        // 这里我们要执行绑定手机号的逻辑；
                        doShowAddPhoneNumber(viewHolder);

                    } else if("46".equals(bean.getId())){
                        // 浏览激励视频
                        mCurTaskId = bean.getId();
                        HashMap<String, String> paramsMap = new HashMap<>();
                        paramsMap.put(ApiCustomTaskEnd.FROM_UID,mBaseActivity.getUserBean().getUserid());
                        paramsMap.put(ApiCustomTaskEnd.TASK_ID, mCurTaskId);

                        ApiCustomTaskStart apiCustomTaskStart = new ApiCustomTaskStart(new HttpOnNextListener<ResAward>() {

                            @Override
                            public void onNext(ResAward s) {
                                loadRewardCoin();
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                e.printStackTrace();
                                loadRewardCoin();
                            }
                        },mBaseActivity,paramsMap);

                        HttpManager.getInstance().doHttpDeal(apiCustomTaskStart);
                    } else if("跳转到小程序".equals(bean.getButtonContent())) {
                        mCurTaskId = bean.getId();
                        HashMap<String, String> paramsMap = new HashMap<>();
                        paramsMap.put(ApiCustomTaskEnd.FROM_UID,mBaseActivity.getUserBean().getUserid());
                        paramsMap.put(ApiCustomTaskEnd.TASK_ID, mCurTaskId);

                        ApiCustomTaskStart apiCustomTaskStart = new ApiCustomTaskStart(new HttpOnNextListener<ResAward>() {

                            @Override
                            public void onNext(ResAward s) {
                                jumpWCApp();
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                e.printStackTrace();
                                jumpWCApp();
                            }
                        },mBaseActivity,paramsMap);
                        HttpManager.getInstance().doHttpDeal(apiCustomTaskStart);

                    } else if("45".equals(bean.getId())) {
                        // 短视频
                        Intent intent = new Intent(mContext,MainActivity.class);
                        intent.putExtra(MainActivity.TAB_INDEX,MainActivity.APPRENTICE);
                        mBaseActivity.startActivity(intent);
                    } else if (!TextUtils.isEmpty(bean.getUrl())) {
                        //这里表示跳转网页；

                        //mBaseActivity.startActivity(WebHelperActivity.getIntent(mBaseActivity,bean.getUrl(),bean.getTitle(),false));
                       // mBaseActivity.startActivity(TaskWebHelperActivity.getIntent(mBaseActivity,bean.getUrl(),bean.getTitle(),false,bean.getId()));
                        mBaseActivity.startActivity(WebHelperActivity.getIntent(mBaseActivity,bean.getUrl(),bean.getTitle(),false));

//                        mCurTaskId = bean.getId();
//
//                        HashMap<String, String> paramsMap = new HashMap<>();
//                        paramsMap.put(ApiCustomTaskEnd.FROM_UID,mBaseActivity.getUserBean().getUserid());
//                        paramsMap.put(ApiCustomTaskEnd.TASK_ID, mCurTaskId);
//
//                        ApiCustomTaskStart apiCustomTaskStart=new ApiCustomTaskStart(new HttpOnNextListener<ResAward>() {
//
//                            @Override
//                            public void onNext(ResAward s) {
//
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                super.onError(e);
//                                mBaseActivity.startActivity(WebHelperActivity.getIntent(mBaseActivity,bean.getUrl(),bean.getTitle(),false));
//                            }
//                        },mBaseActivity,paramsMap);
//
//                        HttpManager.getInstance().doHttpDeal(apiCustomTaskStart);
                    }
                }
            });
        }
    }

    public void onResume(){
//        endTask();
    }

    /**
     * 跳转微信小程序
     */
    private void jumpWCApp(){
        String appId = "wxaddf8032a6b4fc30"; // 填应用AppId
        IWXAPI api = WXAPIFactory.createWXAPI(mBaseActivity, appId);

        if(api.isWXAppInstalled()) {
            WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
            req.userName = "gh_b6664957bab7"; // 小程序原始id  gh_b6664957bab7
            req.path = "";    //拉起小程序页面的可带参路径，不填默认拉起小程序首页
            req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;
            api.sendReq(req);

            try {
                Thread.sleep(1000);
                endTask();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mBaseActivity , "请安装微信" , Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 跳转激励视频
     */
    private void loadRewardCoin() {
        TTAdManager ttAdManager = TTAdManagerHolder.getInstance(mBaseActivity);
        TTAdManagerHolder.getInstance(mBaseActivity).requestPermissionIfNecessary(mBaseActivity);
        //step3:创建TTAdNative对象,用于调用广告请求接口
        mTTAdNative = ttAdManager.createAdNative(mBaseActivity);
        loadAd("902510040", TTAdConstant.VERTICAL);
    }

    private void loadAd(String codeId, int orientation) {
        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(1080, 1920)
                .setRewardName("金币") //奖励的名称
                .setRewardAmount(3)  //奖励的数量
                .setUserID(mBaseActivity.getUserBean().getUserid()) //用户id,必传参数
                .setMediaExtra("media_extra") //附加参数，可选
                .setOrientation(orientation) //必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
                .build();
        //step5:请求广告
        mTTAdNative.loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {
            @Override
            public void onError(int code, String message) {
            }

            //视频广告加载后，视频资源缓存到本地的回调，在此回调后，播放本地视频，流畅不阻塞。
            @Override
            public void onRewardVideoCached() {
            }

            //视频广告的素材加载完毕，比如视频url等，在此回调后，可以播放在线视频，网络不好可能出现加载缓冲，影响体验。
            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd ad) {
                mttRewardVideoAd = ad;
                mttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {

                    @Override
                    public void onAdShow() {
                    }

                    @Override
                    public void onAdVideoBarClick() {
                    }

                    @Override
                    public void onAdClose() {
                    }

                    //视频播放完成回调
                    @Override
                    public void onVideoComplete() {
                        // 播放完成  获取金币
                        Log.i("info" , "complete============");
                    }

                    @Override
                    public void onVideoError() {
                    }

                    //视频播放完成后，奖励验证回调，rewardVerify：是否有效，rewardAmount：奖励梳理，rewardName：奖励名称
                    @Override
                    public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName) {
                        Log.i("info" , "onRewardVerify============");
                        endTask();
                    }

//                    @Override
//                    public void onSkippedVideo() {
//                        TToast.show(RewardVideoActivity.this, "rewardVideoAd has onSkippedVideo");
//                    }
                });
                mttRewardVideoAd.setDownloadListener(new TTAppDownloadListener() {
                    @Override
                    public void onIdle() {
                        mHasShowDownloadActive = false;
                    }

                    @Override
                    public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                        if (!mHasShowDownloadActive) {
                            mHasShowDownloadActive = true;
                        }
                    }

                    @Override
                    public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                    }

                    @Override
                    public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                    }

                    @Override
                    public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                    }

                    @Override
                    public void onInstalled(String fileName, String appName) {
                    }
                });

                if (mttRewardVideoAd != null) {
                    //展示广告，并传入广告展示的场景
                    mttRewardVideoAd.showRewardVideoAd(mBaseActivity);
                    mttRewardVideoAd = null;
                } else {
                    TToast.show(mBaseActivity, "请先加载广告");
                }
            }
        });
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

    /**
     * 这里是获取邀请奖励的操作；
     * @param viewHolder
     */
    private void getInviteAward(final BaseViewHolder viewHolder) {

        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put(ApiRequestAddInviteCode.FROM_UID,mBaseActivity.getUserBean().getUserid());

        ApiRequestAddInviteCode apiRequestAddInviteCode=new ApiRequestAddInviteCode(new HttpOnNextListener<ResAward>() {

            @Override
            public void onNext(ResAward resSign) {

                //这里表示领取成功
                mBaseActivity.toast("恭喜领取"+resSign.getIntegral()+"金币");
                remove(viewHolder.getAdapterPosition()-2);
            }
        },mBaseActivity,paramMap);
        HttpManager.getInstance().doHttpDeal(apiRequestAddInviteCode);
    }

    /**
     * 这里是填写邀请码的操作；
     */
    private void doAddInviteCode(final BaseViewHolder viewHolder) {

        final InputDialog inputDialog = new InputDialog();
        inputDialog.show(mBaseActivity, "", new InputDialog.OnDialogClickListener() {
            @Override
            public void onDialogClickSure(String inputContent) {

                if(!TextUtils.isEmpty(inputContent)){
                    //这里表示表示邀请码，不为空;

                    HashMap<String, String> paramMap = new HashMap<>();
                    paramMap.put(ApiRequestAddInviteCode.FROM_UID,mBaseActivity.getUserBean().getUserid());
                    paramMap.put(ApiRequestAddInviteCode.INVENTCODE,inputContent);

                    ApiRequestAddInviteCode apiRequestAddInviteCode=new ApiRequestAddInviteCode(new HttpOnNextListener<ResAward>() {

                        @Override
                        public void onNext(ResAward resSign) {

                            //这里表示领取成功
                            mBaseActivity.toast("恭喜领取"+resSign.getIntegral()+"金币");
                            remove(viewHolder.getAdapterPosition()-2);
                            inputDialog.dismiss();
                        }
                    },mBaseActivity,paramMap);
                    HttpManager.getInstance().doHttpDeal(apiRequestAddInviteCode);

                }else{
                    mBaseActivity.toast("邀请码不能为空");
                }
            }
        },"输入邀请码","邀请码");
    }



    @Override
    public void remove(@IntRange(from = 0L) int position) {
        if (mData == null
                || position < 0
                || position >= mData.size()) return;

        MultiItemEntity entity = mData.get(position);
        if (entity instanceof IExpandable) {
            removeAllChild((IExpandable) entity, position);
        }
        removeDataFromParent(entity);
        super.remove(position);
    }

    /**
     * 移除父控件时，若父控件处于展开状态，则先移除其所有的子控件
     *
     * @param parent         父控件实体
     * @param parentPosition 父控件位置
     */
    protected void removeAllChild(IExpandable parent, int parentPosition) {
        if (parent.isExpanded()) {
            List<MultiItemEntity> chidChilds = parent.getSubItems();
            if (chidChilds == null || chidChilds.size() == 0) return;

            int childSize = chidChilds.size();
            for (int i = 0; i < childSize; i++) {
                remove(parentPosition + 1);
            }
        }
    }

    /**
     * 移除子控件时，移除父控件实体类中相关子控件数据，避免关闭后再次展开数据重现
     *
     * @param child 子控件实体
     */
    protected void removeDataFromParent(MultiItemEntity child) {
        int position = getParentPosition(child);
        if (position >= 0) {
            IExpandable parent = (IExpandable) mData.get(position);
            parent.getSubItems().remove(child);
        }
    }



    /**
     * 这里我们是需要绑定手机号的一个操作；
     */
    private void doShowAddPhoneNumber(final BaseViewHolder viewHolder) {

        final HashMap<String ,String > paramsMap=new HashMap<>();
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("绑定手机号");
        View view = View.inflate(mContext, R.layout.dialog_bind_phone_number, null);

        final EditText mEtPhone = (EditText) view.findViewById(R.id.et_phone);
        final EditText mEtPhoneCode = (EditText) view.findViewById(R.id.et_phone_number_code);
        final TextView tvSendCode = (TextView) view.findViewById(R.id.tv_send_proof);
        tvSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = mEtPhone.getText().toString().trim();
                if(!ValidUtils.isMobileNO(trim)){
                    toast("请输入正确的手机号!");
                }

                //发送短信的一个方法；
                ApiSMSSend.defaultSend(mBaseActivity,tvSendCode,trim,ApiSMSSend.BIND_PHONE_CODE);
            }
        });
        builder.setView(view);

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setPositiveButton("确定", null);
        final AlertDialog alertDialog = builder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                //这里我们对进行一个验证的操作；

                Button button =  alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        //这里我们对输入的内容进行一个校验操作；
                        String trim = mEtPhone.getText().toString().trim();
                        if(!ValidUtils.isMobileNO(trim)){
                            toast("请输入正确的手机号!");
                            return;
                        }
                        String phoneCode = mEtPhoneCode.getText().toString().trim();
                        if(TextUtils.isEmpty(phoneCode)){
                            toast("验证码不能为空!");
                            return;
                        }

                        paramsMap.put(ApiBindPhoneNumber.PHONE_NUMBER,trim);
                        paramsMap.put(ApiBindPhoneNumber.PHONE_CODE,phoneCode);
                        //这里实现绑定手机号的逻辑
                        bindPhoneNumber(paramsMap,alertDialog,viewHolder);
                    }
                });
            }
        });
        alertDialog.show();
    }


    /**
     * 这里要执行绑定手机号的逻辑；
     */
    private void bindPhoneNumber(HashMap<String,String> paramsMap, final AlertDialog alertDialog, final BaseViewHolder viewHolder) {

        ApiOnlyBindPhoneNumber apiBindPhoneNumber=new ApiOnlyBindPhoneNumber(new HttpOnNextListener<ResEmpty>() {
            @Override
            public void onNext(ResEmpty userStr) {

                remove(viewHolder.getAdapterPosition()-2);
                //这表示绑定成功；
                toast("绑定成功！");
                //这里是需要将绑定给移除的；
                alertDialog.dismiss();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                //这里表示绑定出错
            }
        },mBaseActivity,paramsMap);
        HttpManager.getInstance().doHttpDeal(apiBindPhoneNumber);
    }


    public void toast(String text){
        mBaseActivity.toast(text);
    }

}
