package com.huanxi.renrentoutiao.ui.adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IntRange;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.IExpandable;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.net.api.ApiSMSSend;
import com.huanxi.renrentoutiao.net.api.user.ApiRequestAddInviteCode;
import com.huanxi.renrentoutiao.net.api.user.userInfo.ApiBindPhoneNumber;
import com.huanxi.renrentoutiao.net.bean.news.ResAward;
import com.huanxi.renrentoutiao.ui.activity.WebHelperActivity;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.activity.other.MainActivity;
import com.huanxi.renrentoutiao.ui.activity.user.ExposureIncomeActivity;
import com.huanxi.renrentoutiao.ui.activity.user.IntergralShopActivity;
import com.huanxi.renrentoutiao.ui.activity.user.MyFriendsActivity;
import com.huanxi.renrentoutiao.ui.activity.user.UserSignActivity;
import com.huanxi.renrentoutiao.ui.adapter.bean.TaskItemBean;
import com.huanxi.renrentoutiao.ui.adapter.bean.TaskItemContentBean;
import com.huanxi.renrentoutiao.ui.adapter.bean.TaskTitleBean;
import com.huanxi.renrentoutiao.ui.dialog.InputDialog;
import com.huanxi.renrentoutiao.utils.ValidUtils;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Dinosa on 2018/1/22.
 */

public class NormalTaskAdapter extends BaseAdsAdapter<MultiItemEntity> {


    public static final int TYPE_LEVEL_0 = 0;   //一级标题
    public static final int TYPE_LEVEL_1 = 1;   //二级标题；
    public static final int TYPE_LEVEL_2 = 2;    //三级标题；
    private final TypeLevel0Presenter mTypeLevel0Presenter;
    private final TypeLevel1Presenter mTypeLevel1Presenter;
    private final TypeLevel2Presenter mTypeLevel2Presenter;
    private final BaseActivity mBaseActivity;


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public NormalTaskAdapter(BaseActivity baseActivity, List<MultiItemEntity> data) {
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

        public void init(final BaseViewHolder viewHolder, final TaskTitleBean bean, final NormalTaskAdapter adapter){



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

        public void init(final BaseViewHolder viewHolder, final TaskItemBean bean, final NormalTaskAdapter adapter){


            int imgRes=bean.isExpanded()?R.drawable.icon_arrow_up:R.drawable.icon_arrow_down;

            viewHolder.setImageResource(R.id.iv_arrow,imgRes);

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

            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    System.out.println("item:position:"+viewHolder.getAdapterPosition());
                    remove(viewHolder.getAdapterPosition());
                    return false;
                }
            });

        }
    }

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

                    if(bean.getButtonContent().equals("签到")){

                        mBaseActivity.startActivity(new Intent(mBaseActivity, UserSignActivity.class));


                    } else if(bean.getButtonContent().equals("拆红包")){

                        //这里是直接滚动到上面去；
                        getRecyclerView().scrollToPosition(0);

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
                        intent.putExtra(MainActivity.TAB_INDEX,0);
                        mBaseActivity.startActivity(intent);

                    } else if(bean.getButtonContent().equals("分享")){

                        //跳转到首页；
                        Intent intent = new Intent(mContext,MainActivity.class);
                        intent.putExtra(MainActivity.TAB_INDEX,0);
                        mBaseActivity.startActivity(intent);

                    } else if(bean.getButtonContent().equals("邀请好友")){

                        //邀请好友的操作；
                        mBaseActivity.startActivity(MyFriendsActivity.getIntent(mBaseActivity, true));

                    }else if(bean.getButtonContent().equals("提现")){

                        mBaseActivity.startActivity(new Intent(mBaseActivity, IntergralShopActivity.class));

                    }else if("绑定手机号".equals(bean.getButtonContent())){

                        //这里我们要执行绑定手机号的逻辑；
                        doShowAddPhoneNumber(viewHolder);

                    } else if (!TextUtils.isEmpty(bean.getUrl())) {
                        //这里表示跳转网页；
                        mBaseActivity.startActivity(WebHelperActivity.getIntent(mBaseActivity,bean.getUrl(),bean.getTitle()));
                    }
                }
            });
        }
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

        ApiBindPhoneNumber apiBindPhoneNumber=new ApiBindPhoneNumber(new HttpOnNextListener<String>() {
            @Override
            public void onNext(String userStr) {

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
