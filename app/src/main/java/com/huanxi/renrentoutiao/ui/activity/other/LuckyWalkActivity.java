package com.huanxi.renrentoutiao.ui.activity.other;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.model.bean.UserBean;
import com.huanxi.renrentoutiao.net.api.ApiRequestUserAwardRecord;
import com.huanxi.renrentoutiao.net.api.ApiSMSSend;
import com.huanxi.renrentoutiao.net.api.luckywalk.ApiLuckyWalkDes;
import com.huanxi.renrentoutiao.net.api.luckywalk.ApiLuckyWalkProductList;
import com.huanxi.renrentoutiao.net.api.luckywalk.ApiUserStartLuckyWalk;
import com.huanxi.renrentoutiao.net.api.luckywalk.ApiUserWalkProduct;
import com.huanxi.renrentoutiao.net.api.user.userInfo.ApiBindPhoneNumber;
import com.huanxi.renrentoutiao.net.api.user.userInfo.ApiOnlyBindPhoneNumber;
import com.huanxi.renrentoutiao.net.bean.ResEmpty;
import com.huanxi.renrentoutiao.net.bean.luckywalk.ResLuckwalkProductBean;
import com.huanxi.renrentoutiao.ui.activity.TiXianWebActivity;
import com.huanxi.renrentoutiao.ui.activity.base.BaseTitleActivity;
import com.huanxi.renrentoutiao.ui.activity.user.UserTaskActivity;
import com.huanxi.renrentoutiao.ui.activity.user.WithdrawalsActivity2;
import com.huanxi.renrentoutiao.ui.dialog.LuckyWalkRulesDialog;
import com.huanxi.renrentoutiao.ui.dialog.LuckyWalkStartFailedDialog;
import com.huanxi.renrentoutiao.ui.dialog.MyGiftDialog;
import com.huanxi.renrentoutiao.ui.dialog.RedPacketDialog;
import com.huanxi.renrentoutiao.ui.view.luckwalk.Callback;
import com.huanxi.renrentoutiao.ui.view.luckwalk.LuckyContainerView;
import com.huanxi.renrentoutiao.ui.view.luckwalk.LuckyWalkBean;
import com.huanxi.renrentoutiao.ui.view.luckwalk.Luckywalk;
import com.huanxi.renrentoutiao.utils.Utils;
import com.huanxi.renrentoutiao.utils.ValidUtils;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Dinosa on 2018/4/19.
 */

public class LuckyWalkActivity extends BaseTitleActivity {


    @BindView(R.id.lwView)
    Luckywalk mLwView;

    @BindView(R.id.btGift)
    Button mBtGift;

    @BindView(R.id.btStart)
    Button mBtStart;

    @BindView(R.id.btRule)
    Button mBtRule;

    @BindView(R.id.lvContainer)
    LuckyContainerView mLvContainer;

    String products="[\n" +
            "{\n" +
            "\"id\": 90,\n" +
            "\"title\": \"https://hx-ad.oss-cn-beijing.aliyuncs.com/activity/0419_10.png\",\n" +
            "\"content\": \"10\",\n" +
            "\"type\": 9,\n" +
            "\"status\": 0,\n" +
            "\"proid\": \"0.489\",\n" +
            "\"vname\": null,\n" +
            "\"vcode\": null,\n" +
            "\"vcontent\": null\n" +
            "},\n" +
            "{\n" +
            "\"id\": 91,\n" +
            "\"title\": \"https://hx-ad.oss-cn-beijing.aliyuncs.com/activity/0419_30.png\",\n" +
            "\"content\": \"30\",\n" +
            "\"type\": 9,\n" +
            "\"status\": 0,\n" +
            "\"proid\": \"0.25\",\n" +
            "\"vname\": null,\n" +
            "\"vcode\": null,\n" +
            "\"vcontent\": null\n" +
            "},\n" +
            "{\n" +
            "\"id\": 92,\n" +
            "\"title\": \"https://hx-ad.oss-cn-beijing.aliyuncs.com/activity/0419_50.png\",\n" +
            "\"content\": \"50\",\n" +
            "\"type\": 9,\n" +
            "\"status\": 0,\n" +
            "\"proid\": \"0.2\",\n" +
            "\"vname\": null,\n" +
            "\"vcode\": null,\n" +
            "\"vcontent\": null\n" +
            "},\n" +
            "{\n" +
            "\"id\": 93,\n" +
            "\"title\": \"https://hx-ad.oss-cn-beijing.aliyuncs.com/activity/0419_100.png\",\n" +
            "\"content\": \"100\",\n" +
            "\"type\": 9,\n" +
            "\"status\": 0,\n" +
            "\"proid\": \"0.05\",\n" +
            "\"vname\": null,\n" +
            "\"vcode\": null,\n" +
            "\"vcontent\": null\n" +
            "},\n" +
            "{\n" +
            "\"id\": 94,\n" +
            "\"title\": \"https://hx-ad.oss-cn-beijing.aliyuncs.com/activity/0419_1k.png\",\n" +
            "\"content\": \"1000\",\n" +
            "\"type\": 9,\n" +
            "\"status\": 0,\n" +
            "\"proid\": \"0.01\",\n" +
            "\"vname\": null,\n" +
            "\"vcode\": null,\n" +
            "\"vcontent\": null\n" +
            "},\n" +
            "{\n" +
            "\"id\": 96,\n" +
            "\"title\": \"https://hx-ad.oss-cn-beijing.aliyuncs.com/activity/0419_1w.png\",\n" +
            "\"content\": \"10000\",\n" +
            "\"type\": 9,\n" +
            "\"status\": 0,\n" +
            "\"proid\": \"0.001\",\n" +
            "\"vname\": null,\n" +
            "\"vcode\": null,\n" +
            "\"vcontent\": null\n" +
            "},\n" +
            "{\n" +
            "\"id\": 97,\n" +
            "\"title\": \"https://hx-ad.oss-cn-beijing.aliyuncs.com/activity/0419_5k.png\",\n" +
            "\"content\": \"5000\",\n" +
            "\"type\": 9,\n" +
            "\"status\": 0,\n" +
            "\"proid\": \"0\",\n" +
            "\"vname\": null,\n" +
            "\"vcode\": null,\n" +
            "\"vcontent\": null\n" +
            "},\n" +
            "{\n" +
            "\"id\": 98,\n" +
            "\"title\": \"https://hx-ad.oss-cn-beijing.aliyuncs.com/activity/0419_10w.png\",\n" +
            "\"content\": \"100000\",\n" +
            "\"type\": 9,\n" +
            "\"status\": 0,\n" +
            "\"proid\": \"0\",\n" +
            "\"vname\": null,\n" +
            "\"vcode\": null,\n" +
            "\"vcontent\": null\n" +
            "}\n" +
            "]";
    private List<ResLuckwalkProductBean> mProductBeanList;


    @Override
    public int getBodyLayoutId() {
        return R.layout.activity_lucky_walk;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        super.initView(rootView, savedInstanceState);

        setBackText("");
        setTitle("幸运大转盘");

        boolean booleanExtra = getIntent().getBooleanExtra(IS_TI_XIAN,false);

        if (booleanExtra) {
            setRightText("提现");
            mTvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    withdrawal();
                }
            });
        }else{
            setRightText("赚金币");

            mTvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LuckyWalkActivity.this, UserTaskActivity.class);
                    startActivity(intent);
                }
            });
        }



    }

    /**
     * 这里做一下提现的操作；
     */
    private void withdrawal() {
        UserBean userBean = getUserBean();
        if(!TextUtils.isEmpty(userBean.getPhone())){
            onClickWithdrawals();
        }else{
            //这里要绑定手机号；
            doShowAddPhoneNumber();
        }

    }


    /**
     * 这里做提现的操作；
     */
    private void onClickWithdrawals() {
        startActivity(new Intent(this,WithdrawalsActivity2.class));
//        startActivity(new Intent(this, TiXianWebActivity.class));
    }


    /**
     * 这里我们是需要绑定手机号的一个操作；
     */
    private void doShowAddPhoneNumber() {

        final HashMap<String ,String > paramsMap=new HashMap<>();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("绑定手机号");
        View view = View.inflate(this, R.layout.dialog_bind_phone_number, null);

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
                ApiSMSSend.defaultSend(LuckyWalkActivity.this,tvSendCode,trim,ApiSMSSend.BIND_PHONE_CODE);
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
                        bindPhoneNumber(paramsMap,alertDialog,trim);
                    }
                });
            }
        });
        alertDialog.show();
    }



    /**
     * 这里要执行绑定手机号的逻辑；
     */
    private void bindPhoneNumber(final HashMap<String,String> paramsMap, final AlertDialog alertDialog, final String phoneNumber) {


        ApiOnlyBindPhoneNumber apiBindPhoneNumber=new ApiOnlyBindPhoneNumber(new HttpOnNextListener<ResEmpty>() {
            @Override
            public void onNext(ResEmpty userStr) {

                //这表示绑定成功；
                toast("绑定成功！");
                //这里是需要将绑定给移除的；this
                alertDialog.dismiss();

                //这里要更新
                UserBean userBean = getUserBean();
                userBean.setPhone(phoneNumber);
                updateUser(userBean);
                //这里要做的一个操作；
                startActivity(new Intent(LuckyWalkActivity.this,WithdrawalsActivity2.class));

            }

        },this,paramsMap);
        HttpManager.getInstance().doHttpDeal(apiBindPhoneNumber);
    }



    @Override
    protected void initData() {
        super.initData();

        if(isLogin()){
            mLvContainer.addMyGoldView(getUserBean().getIntegral());
        }

        getProductList();
        getGameDesc();
        getQueen();


    }

    private void getUserAward() {

        if(getUserBean()==null){
            return;
        }

        HashMap<String, String> paramMap = new HashMap<>();

        paramMap.put(ApiRequestUserAwardRecord.FROM_UID,getUserBean().getUserid());

        ApiRequestUserAwardRecord userAwardRecord=new ApiRequestUserAwardRecord(new HttpOnNextListener<List<ResLuckwalkProductBean>>() {

            @Override
            public void onNext(List<ResLuckwalkProductBean> beanList) {

                List<LuckyWalkBean> walkBeen = filterDataToRecord(beanList);
                //这里要做的一个逻辑就是显示中奖记录；
                MyGiftDialog dialog=new MyGiftDialog(LuckyWalkActivity.this,walkBeen);
                dialog.show();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        },this,paramMap);

        HttpManager.getInstance().doHttpDeal(userAwardRecord);
    }

    /**
     * 这里是用户的中奖记录的操作；
     * @param beanList
     * @return
     */
    private List<LuckyWalkBean> filterDataToRecord(List<ResLuckwalkProductBean> beanList) {

        ArrayList<LuckyWalkBean> luckyWalkBeen = new ArrayList<>();

        if (beanList != null) {
            for (ResLuckwalkProductBean resLuckwalkProductBean : beanList) {

                LuckyWalkBean luckyWalkBean = new LuckyWalkBean("",resLuckwalkProductBean.getDesc(),resLuckwalkProductBean.getPrice(),"");
                luckyWalkBean.setAddtime(resLuckwalkProductBean.getAddtime());
                luckyWalkBeen.add(luckyWalkBean);

            }
        }

        return luckyWalkBeen;
    }


    private String mGameRules;

    /**
     * 获取游戏的描述的内容；
     */
    private void getGameDesc() {

        ApiLuckyWalkDes apiLuckyWalkDes=new ApiLuckyWalkDes(new HttpOnNextListener<ResLuckwalkProductBean>() {


            @Override
            public void onNext(ResLuckwalkProductBean bean) {

                mGameRules = bean.getContent();

            }
        },this);

        HttpManager.getInstance().doHttpDeal(apiLuckyWalkDes);
    }

    private void getProductList() {


        ApiLuckyWalkProductList productList=new ApiLuckyWalkProductList(new HttpOnNextListener<List<ResLuckwalkProductBean>>() {

            @Override
            public void onNext(List<ResLuckwalkProductBean> resLuckwalkProductBeen) {
                //这里我们讲resLuckWalkProduct转换为我们需要的类型的；
                mLwView.setDatas(filterData(resLuckwalkProductBeen));
            }
        },this);
        HttpManager.getInstance().doHttpDeal(productList);

      /*  Gson gson = new Gson();
        mProductBeanList =gson.fromJson(products,new TypeToken<ArrayList<ResLuckwalkProductBean> >(){}.getType());
        mLwView.setDatas(filterData(mProductBeanList));*/

    }

    private List<LuckyWalkBean> filterData(List<ResLuckwalkProductBean> resLuckwalkProductBeen) {

        ArrayList<LuckyWalkBean> luckyWalkBeen = new ArrayList<>();

        if (resLuckwalkProductBeen != null) {

            for (ResLuckwalkProductBean resLuckwalkProductBean : resLuckwalkProductBeen) {

               luckyWalkBeen.add(convertToLuckWalkBean(resLuckwalkProductBean));
            }
        }
        return luckyWalkBeen;
    }

    /**
     * 将服务器返回的数据转换为我们需要的数据；
     * @param resLuckwalkProductBean
     * @return
     */
    public LuckyWalkBean convertToLuckWalkBean(ResLuckwalkProductBean resLuckwalkProductBean){
        return new LuckyWalkBean(resLuckwalkProductBean.getId(),"","",resLuckwalkProductBean.getTitle());
    }

    int index=0;

    @OnClick(R.id.btStart)
    public void onClickStart(){
        //这里表示开始的操作；

        checkLogin(new CheckLoginCallBack() {
            @Override
            public void loginSuccess() {

                String integral = getUserBean().getIntegral();
                float userInergral=0;
                try {
                    userInergral=Float.parseFloat(integral);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                if(userInergral<50){

                    //这里判断一下金币
                    LuckyWalkStartFailedDialog luckyWalkStartFailedDialog = new LuckyWalkStartFailedDialog(LuckyWalkActivity.this, new LuckyWalkStartFailedDialog.OnClickListener() {
                        @Override
                        public void onClickConfirm() {
                            //这里要跳转到服任务中心；
                            Intent intent = new Intent(LuckyWalkActivity.this, UserTaskActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onClickCancel() {

                        }
                    });

                    luckyWalkStartFailedDialog.show();
                }else{

                    start();
                }

            }

            @Override
            public void loginFaild() {

            }
        });

    }

    /**
     * 开发访问服务器结果，并转动；
     */
    private void start() {


        if(mBtStart != null) {
            mBtStart.setClickable(false);
        }

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiUserStartLuckyWalk.FROM_UID,getUserBean().getUserid());

        ApiUserStartLuckyWalk apiUserStartLuckyWalk=new ApiUserStartLuckyWalk(new HttpOnNextListener<ResLuckwalkProductBean>() {
            @Override
            public void onNext(final ResLuckwalkProductBean bean) {

                //final ResLuckwalkProductBean resLuckwalkProductBean = mProductBeanList.get(index);

                final LuckyWalkBean luckyWalkBean = convertToLuckWalkBean(bean);

                String integral = getUserBean().getIntegral();
                float inter=0;
                try {
                    inter = Float.parseFloat(integral);
                    float v = inter - 50;
                    UserBean userBean = getUserBean();
                    userBean.setIntegral(v+"");
                    updateUser(userBean);
                    mLvContainer.addMyGoldView(getUserBean().getIntegral());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                mLwView.start(luckyWalkBean,new Callback(){

                    @Override
                    public void callbackSuccess() {
                        super.callbackSuccess();
                        //这里要修改用户的金额
                        //这里显示弹窗的操作；

                        RedPacketDialog redPacketDialog = new RedPacketDialog();
                        if(Utils.isForeground(LuckyWalkActivity.this
                                , LuckyWalkActivity.this.getClass().getName())) {
                            // 当前Activity在前台展示
                            redPacketDialog.show(LuckyWalkActivity.this, bean.getContent() + "",null);
                        }

                        String integral = getUserBean().getIntegral();
                        //这里是重新计算金额
                        float inter=0;
                        float content=0;
                        try {
                            inter = Float.parseFloat(integral);
                            content=Float.parseFloat(bean.getContent());
                            float v = inter + content;

                            UserBean userBean = getUserBean();
                            userBean.setIntegral(v+"");
                            updateUser(userBean);

                            mLvContainer.addMyGoldView(getUserBean().getIntegral());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if(mBtStart != null) {
                            mBtStart.setClickable(true);
                        }
                        // index++;
                    }
                });
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if(mBtStart != null) {
                    mBtStart.setClickable(true);
                }
            }
        },LuckyWalkActivity.this,paramsMap);
        HttpManager.getInstance().doHttpDeal(apiUserStartLuckyWalk);

    }


    @OnClick(R.id.btRule)
    public void onClickBtn(){
        LuckyWalkRulesDialog luckyWalkRulesDialog = new LuckyWalkRulesDialog(this,mGameRules);
        luckyWalkRulesDialog.show();
    }

    @OnClick(R.id.btGift)
    public void onClickMyAward(){
        //这里表示我的奖品的逻辑；

        /*final MyGiftDialog dialog = new MyGiftDialog(this);
        dialog.setCallback(new Callback(){
            @Override
            public void callback(final int v) {
                super.callback(v);
                page += v;
                myLuckyList(dialog,new Callback(){
                    @Override
                    public void callbackField() {
                        super.callbackField();
                        page -= v;
                    }
                });
            }
        });
        dialog.show();
        myLuckyList(dialog,null);*/

        checkLogin(new CheckLoginCallBack() {
            @Override
            public void loginSuccess() {
                //这里要做的一个逻辑就是查看我的奖品的逻辑
                getUserAward();
            }

            @Override
            public void loginFaild() {

            }
        });
    }

    public void getQueen(){


        HashMap<String, String> keyMap = new HashMap<>();

        ApiUserWalkProduct product=new ApiUserWalkProduct(new HttpOnNextListener<List<String>>() {

            @Override
            public void onNext(List<String> datas) {

                mLvContainer.addMarqueeView(datas);
            }

        },this,keyMap);

        HttpManager.getInstance().doHttpDeal(product);
    }

    private List<String> filterResLuckWalkProductToString(List<ResLuckwalkProductBean> datas) {

        ArrayList<String> strings = new ArrayList<>();

        if (datas != null) {
            for (ResLuckwalkProductBean data : datas) {

            }
        }

        return strings;
    }

    public static final String IS_TI_XIAN="isTiXian";

    /**
     * 这里要做的一个操作就是显示
     * @return
     */
    public static Intent getIntent(Context context,boolean isTiXian){

        Intent intent = new Intent(context, LuckyWalkActivity.class);

        intent.putExtra(IS_TI_XIAN,isTiXian);

        return intent;
    }

/*


    HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiUserStartLuckyWalk.FROM_UID,getUserBean().getUserid());

    ApiUserStartLuckyWalk apiUserStartLuckyWalk=new ApiUserStartLuckyWalk(new HttpOnNextListener<String>() {

        @Override
        public void onNext(String s) {

        }
    },this,paramsMap);
        HttpManager.getInstance().doHttpDeal(apiUserStartLuckyWalk);


    //==============


    HashMap<String, String> keyMap = new HashMap<>();
        paramsMap.put(ApiUserStartLuckyWalk.FROM_UID,getUserBean().getUserid());

    ApiUserWalkProduct product=new ApiUserWalkProduct(new HttpOnNextListener<String>() {

        @Override
        public void onNext(String s) {

        }
    },this,paramsMap);
        HttpManager.getInstance().doHttpDeal(product);*/
}
