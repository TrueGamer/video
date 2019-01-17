package com.huanxi.renrentoutiao.ui.activity.user;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.net.api.user.browerRecord.ApiClearRecord;
import com.huanxi.renrentoutiao.net.bean.ResEmpty;
import com.huanxi.renrentoutiao.ui.activity.base.BaseTitleAndTabActivity;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseFragment;
import com.huanxi.renrentoutiao.ui.fragment.user.browerRecord.NewsBrowerRecordFragment;
import com.huanxi.renrentoutiao.ui.fragment.user.browerRecord.VideoBrowerRecordFragment;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.OnClick;

/**
 * Created by Dinosa on 2018/1/19.
 */

public class UserBrowerRecordsActivity extends BaseTitleAndTabActivity {

    private ArrayList<BaseFragment> mBaseFragments;

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        super.initView(rootView,savedInstanceState);
        setTitle("浏览记录");
        //setRightTextAndDrawable("",R.drawable.icon_clear);
        setRightText("清除");
    }

    @Override
    public List<String> getIndicatorTitle() {
        ArrayList<String> list = new ArrayList<>();
        list.add("文章");
        list.add("视频");
        return list;
    }

    @Override
    public List<BaseFragment> getFragments() {

        mBaseFragments = new ArrayList<>();
        mBaseFragments.add(new NewsBrowerRecordFragment());
        mBaseFragments.add(new VideoBrowerRecordFragment());
        return mBaseFragments;
    }




    @OnClick(R.id.tv_right_option)
    public void onClickClear(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("是否要清除所有的消息？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //这里调用Fragment里面的方法
                dialog.dismiss();
                doClear();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    /**
     * 这里我们要做一个操作就是清理
     */
    private void doClear() {
        final int currentItem = mVpViewpager.getCurrentItem();


        final BaseFragment baseFragment = mBaseFragments.get(currentItem);

        HashMap<String, String> paramsMap = new HashMap<>();

        paramsMap.put(ApiClearRecord.FROM_UID,getUserBean().getUserid());
        paramsMap.put(ApiClearRecord.TYPE,currentItem==0?ApiClearRecord.NEWS:ApiClearRecord.VIDEO);

        ApiClearRecord apiClearRecord=new ApiClearRecord(new HttpOnNextListener<ResEmpty>() {

            @Override
            public void onNext(ResEmpty resEmpty) {

                if (currentItem==0) {
                    NewsBrowerRecordFragment newsRecord = (NewsBrowerRecordFragment) baseFragment;
                    newsRecord.clear();
                }else{
                    VideoBrowerRecordFragment videoRecord = (VideoBrowerRecordFragment) baseFragment;
                    videoRecord.clear();
                }
                toast("清理成功");
            }

        },this,paramsMap);

        HttpManager.getInstance().doHttpDeal(apiClearRecord);
    }
}
