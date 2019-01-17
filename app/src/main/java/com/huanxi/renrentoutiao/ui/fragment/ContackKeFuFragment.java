package com.huanxi.renrentoutiao.ui.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.net.api.ApiNewKeFuContact;
import com.huanxi.renrentoutiao.net.bean.ResNewContactKeFu;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseLoadingRecyclerViewFragment;
import com.huanxi.renrentoutiao.utils.SystemUtils;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.List;

/**
 * Created by Dinosa on 2018/3/22.
 */

public class ContackKeFuFragment extends BaseLoadingRecyclerViewFragment {

    private MyAdapter mMyAdapter;

    @Override
    public RecyclerView.Adapter getAdapter() {

        if (mMyAdapter == null) {
            mMyAdapter = new MyAdapter(null);
        }
        return mMyAdapter;
    }

    @Override
    protected void initData() {
        super.initData();

        setRefreshEnable(false);
        setLoadingEnable(false);
    }

    @Override
    public void requestAdapterData(boolean isFirst) {

        ApiNewKeFuContact apiGetHelpInfo=new ApiNewKeFuContact(new HttpOnNextListener<List<ResNewContactKeFu>>() {


            @Override
            public void onNext(List<ResNewContactKeFu> resNewContactKeFus) {

                if (resNewContactKeFus != null && resNewContactKeFus.size()>0) {
                    mMyAdapter.replaceData(resNewContactKeFus);
                    showSuccess();
                }else{
                    showEmpty();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                showFaild();
            }
        },getBaseActivity());

        HttpManager.getInstance().doHttpDeal(apiGetHelpInfo);
    }

    @Override
    public void requestNextAdapterData() {

    }

    public class MyAdapter extends BaseQuickAdapter<ResNewContactKeFu,BaseViewHolder>{


        public MyAdapter( @Nullable List<ResNewContactKeFu> data) {
            super(R.layout.item_contact_kefu, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final ResNewContactKeFu item) {


            helper.setText(R.id.tv_type,item.getTitle());

            helper.setText(R.id.tv_number,item.getValue());
            if("qq".equals(item.getType())){
                //这里是客服；
                helper.setText(R.id.tv_kefu_desc,"点击聊天");

                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startQQ(item.getValue());
                    }
                });

            }else if("qq_q".equals(item.getType())){

                helper.setText(R.id.tv_kefu_desc,"点击加群");
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startQQqun(item.getKey());
                    }
                });

            }else{
                //这里
                helper.setText(R.id.tv_kefu_desc,"点击复制");
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SystemUtils.setClipString(getBaseActivity(),item.getValue());
                        toast("复制成功");

                        Intent intent = new Intent();
                        ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");// 报名该有activity

                        intent.setAction(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_LAUNCHER);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setComponent(cmp);

                        startActivityForResult(intent, 0);
                    }
                });

            }

        }
    }

    /**
     * 这里是唤醒qq群；
     * @param value
     */
    private void startQQqun(String value) {

        if(isQQClientAvailable(getBaseActivity())){

            Intent intent = new Intent();
            intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + value));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);

        }else{
            toast("没有检测到qq客户端!!!");
        }
    }


    public void startQQ(String qqNumber){

        if(isQQClientAvailable(getBaseActivity())){
            String url = "mqqwpa://im/chat?chat_type=wpa&uin="+qqNumber;
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }else{
            toast("没有检测到qq客户端!!!");
        }

    }


    /**
     * 判断qq是否可用
     *
     * @param context
     * @return
     */
    public  boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

}
