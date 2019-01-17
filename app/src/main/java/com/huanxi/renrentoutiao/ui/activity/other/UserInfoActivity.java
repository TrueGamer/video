package com.huanxi.renrentoutiao.ui.activity.other;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.model.bean.ProviceBean;
import com.huanxi.renrentoutiao.model.bean.UserBean;
import com.huanxi.renrentoutiao.net.api.user.ApiAlterUserInfo;
import com.huanxi.renrentoutiao.net.bean.ResEmpty;
import com.huanxi.renrentoutiao.ui.activity.base.BaseTitleActivity;
import com.huanxi.renrentoutiao.ui.dialog.InputDialog;
import com.huanxi.renrentoutiao.utils.FormatUtils;
import com.huanxi.renrentoutiao.utils.ValidUtils;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class UserInfoActivity extends BaseTitleActivity {


    @BindView(R.id.tv_sex)
    TextView mTvSex;
    @BindView(R.id.ll_sex)
    LinearLayout mLlSex;
    @BindView(R.id.tv_birthday)
    TextView mTvBirthday;
    @BindView(R.id.ll_birthday)
    LinearLayout mLlBirthday;
    @BindView(R.id.tv_area)
    TextView mTvArea;
    @BindView(R.id.ll_area)
    LinearLayout mLlArea;
    @BindView(R.id.tv_phone_number)
    TextView mTvPhoneNumber;
    @BindView(R.id.ll_phone)
    LinearLayout mLlPhone;
    @BindView(R.id.tv_email)
    TextView mTvEmail;
    @BindView(R.id.ll_email)
    LinearLayout mLlEmail;
    private UserBean mUserBean;


    @Override
    public int getBodyLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        super.initView(rootView, savedInstanceState);
        setTitle("完善资料");
        setBackText("");
    }

    String mSex = "";
    String mBirthday = "";
    String mPhoneNumber = "";
    String mEmail = "";
    String mProvice = "";
    String mCity = "";

    public static final String UNDEFINE_STR = "未填写";

    @Override
    protected void initData() {
        super.initData();
        initJsonData();
        mUserBean = getUserBean();

        mSex = mUserBean.getSex();

        mBirthday = mUserBean.getBirthday();
        mEmail = mUserBean.getEmail();

        mProvice = mUserBean.getProvince();
        mCity = mUserBean.getCity();
        mPhoneNumber = mUserBean.getPhone();

        updateUI();
    }

    public String getString(String str) {
        if (TextUtils.isEmpty(str)) {
            return UNDEFINE_STR;
        }
        return str;
    }

    private void updateUI() {

        mTvEmail.setText(getString(mEmail));
        mTvBirthday.setText(getString(mBirthday));
        if (getString(mProvice).equals(UNDEFINE_STR) || getString(mCity).equals(UNDEFINE_STR)) {
            mTvArea.setText(UNDEFINE_STR);
        } else {
            mTvArea.setText(mProvice + " " + mCity);
        }
        mTvPhoneNumber.setText(getString(mPhoneNumber));
        mTvSex.setText(mSex.equals("1") ? "男" : "女");
    }

   /*
    @OnClick(R.id.ll_phone)
    public void onClickPhoneNumber(){
        //绑定手机号的逻辑；
        //这要做隐藏的逻辑
    }
    */

    @OnClick(R.id.ll_sex)
    public void onClickSex() {

        final String[] sex = {"男", "女"};
        int index = mSex.equals("1") ? 0 : 1;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(sex, index, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                mSex = (which+1)+"";
                updateUI();
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @OnClick(R.id.ll_birthday)
    public void onClickBirthday() {
        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                //这里执行回掉的方法；
                mBirthday = FormatUtils.defaultFormatDateToString(date.getTime());
                updateUI();
            }
        }).setType(new boolean[]{true, true, true, false, false, false}).build();

        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。

        if (mBirthday!=null && !mBirthday.equals(UNDEFINE_STR)) {
            //这里表示没有绑定的话的一个操作；
            //这里我们是需要将yyyy-MM-dd转换为 Date类型的对象；
            Date date = FormatUtils.defaultFormatStringToDate(mBirthday);
            if (date != null) {
                Calendar instance = Calendar.getInstance();
                instance.setTime(date);
                pvTime.setDate(instance);
            }
        }
        pvTime.show();
    }

    @OnClick(R.id.ll_area)
    public void onClickArea() {

        //这里要做的一个操作就是将Json读取到内存中；
        ShowAreas();
    }

    @Override
    public void onClickBack() {
        onBackPressed();
    }

    @OnClick(R.id.ll_email)
    public void onClickEmail() {

        //inputDialog;
        final InputDialog inputDialog=new InputDialog();

        inputDialog.show(this, mEmail, new InputDialog.OnDialogClickListener() {
            @Override
            public void onDialogClickSure(String inputContent) {
                //在这里我们对其进行一个验证的操作；
                if (ValidUtils.isEmail(inputContent)) {
                    //判断邮箱是否有效
                    mEmail = inputContent;
                    updateUI();
                    inputDialog.dismiss();
                } else {
                    toast("邮箱格式有问题！！！");
                }

            }
        },"绑定邮箱","邮箱");
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //这里我们判断一下是否修改了
        if (!isAlter()) {
            //这里表示没有修改
            finish();
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("是否保存修改资料？")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    }).setPositiveButton("保存", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doSaveOperation(dialog);
                }
            }).show();
        }
    }

    /**
     * 判断是否修改了内容
     *
     * @return
     */
    public boolean isAlter() {
        UserBean userBean = getUserBean();
        if (mSex != null) {
            if (!mSex.equals(userBean.getSex())) {
                return true;
            }
        }
        if (mCity != null) {
            if (!mCity.equals(userBean.getCity())) {
                return true;
            }
        }
        if (mBirthday != null) {
            if (!mBirthday.equals(userBean.getBirthday())) {
                return true;
            }
        }
        if (mEmail != null) {
            if (!mEmail.equals(userBean.getEmail())) {
                return true;
            }
        }

        if (mProvice != null) {
            if (!mProvice.equals(userBean.getProvince())) {
                return true;
            }
        }
        return false;
    }

    private void doSaveOperation(final DialogInterface dialog) {
        //这里我们对其进行一个提交操作；
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiAlterUserInfo.FROM_UID, getUserBean().getUserid());
        if (!TextUtils.isEmpty(mBirthday)) {
            paramsMap.put(ApiAlterUserInfo.BITHDAY, mBirthday);
        }
        if (!TextUtils.isEmpty(mProvice)) {
            paramsMap.put(ApiAlterUserInfo.PROVICE, mProvice);
        }
        if (!TextUtils.isEmpty(mCity)) {
            paramsMap.put(ApiAlterUserInfo.CITY, mCity);
        }
        if (!TextUtils.isEmpty(mSex)) {
            paramsMap.put(ApiAlterUserInfo.SEX, mSex);
        }
        if (!TextUtils.isEmpty(mEmail)) {
            paramsMap.put(ApiAlterUserInfo.EAMIL, mEmail);
        }
        ApiAlterUserInfo apiAlterUserInfo = new ApiAlterUserInfo(new HttpOnNextListener<ResEmpty>() {

            @Override
            public void onNext(ResEmpty resEmpty) {
                toast("保存成功！");
                UserBean userBean = getUserBean();
                userBean.setBirthday(mBirthday);
                userBean.setProvince(mProvice);
                userBean.setEmail(mEmail);
                userBean.setSex(mSex);
                updateUser(userBean);
                if (dialog != null) {
                    dialog.dismiss();
                }
                finish();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                toast("保存失败！");
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        }, this, paramsMap);
        HttpManager.getInstance().doHttpDeal(apiAlterUserInfo);
    }

    //===============这里我们要做的一个操作就是读取本地的对象；======================

    private void ShowAreas() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                mProvice = options1Items.get(options1).getPickerViewText();
                mCity = options2Items.get(options1).get(options2);
                updateUI();
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        //pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        if (!getString(mProvice).equals(UNDEFINE_STR) && !getString(mCity).equals(UNDEFINE_STR)) {
            int proviceIndex = options1Items.indexOf(new ProviceBean(mProvice));
            int cityIndex = options2Items.get(proviceIndex).indexOf(mCity);
            pvOptions.setSelectOptions(proviceIndex, cityIndex);
        }
        // pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器
        pvOptions.show();
    }


    private ArrayList<ProviceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = getJson(this, "config/province.json");//获取assets目录下的json文件数据

        ArrayList<ProviceBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
    }


    public ArrayList<ProviceBean> parseData(String result) {//Gson 解析
        ArrayList<ProviceBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                ProviceBean entity = gson.fromJson(data.optJSONObject(i).toString(), ProviceBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }


    public String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    @OnClick(R.id.tv_save)
    public void onClickSave(){

        //这里表示保存的一个操作；

        if(isAlter()){
            doSaveOperation(null);
        }else{
            toast("保存成功!!!");
        }
    }

}
