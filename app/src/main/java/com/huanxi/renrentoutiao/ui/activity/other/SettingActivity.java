package com.huanxi.renrentoutiao.ui.activity.other;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.model.bean.UserBean;
import com.huanxi.renrentoutiao.net.api.user.ApiAlterUserInfo;
import com.huanxi.renrentoutiao.net.bean.ResEmpty;
import com.huanxi.renrentoutiao.ui.activity.base.BaseTitleActivity;
import com.huanxi.renrentoutiao.ui.dialog.InputDialog;
import com.huanxi.renrentoutiao.utils.ImageUtils;
import com.huanxi.renrentoutiao.utils.SystemUtils;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Dinosa on 2018/1/31.
 */

public class SettingActivity extends BaseTitleActivity {

    @BindView(R.id.iv_icon_user)
    CircleImageView mIvIconUser;
    @BindView(R.id.ll_userinfo)
    LinearLayout mLlUserinfo;

    @BindView(R.id.ll_about_us)
    LinearLayout mLlAboutUs;
    @BindView(R.id.ll_exit)
    LinearLayout mLlExit;
    @BindView(R.id.tv_nick_name)
    TextView mTvNickName;
    @BindView(R.id.tv_clear_cache)
    TextView mTvClearCache;
    private UserBean mUserBean;

    @Override
    public int getBodyLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        super.initView(rootView, savedInstanceState);
        setBackText("");
        setTitle("设置");
    }

    @Override
    protected void initData() {
        super.initData();
        mUserBean = getUserBean();

        ImageUtils.loadImage(this,mUserBean.getAvatar(),mIvIconUser);
        mTvNickName.setText(mUserBean.getNickname());
    }

    @OnClick(R.id.ll_exit)
    public void onClickQuit() {

        //取消授权
        Platform platform = ShareSDK.getPlatform(Wechat.NAME);
        if (platform.isAuthValid()) {
            platform.removeAccount(true);
        }

        //这里我们要做的一个操作就是；
        clearUser();

        //startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @OnClick(R.id.ll_userinfo)
    public void onClickUserInfo() {
        startActivity(new Intent(this, UserInfoActivity.class));
    }

    @OnClick(R.id.ll_about_us)
    public void onClickAboutUs() {
        startActivity(new Intent(this, AboutUsActivity.class));
    }

    @OnClick(R.id.ll_clear_cache)
    public void onClickClearCache(){

        new AlertDialog.Builder(this)
                .setMessage("是否清理缓存？")
                .setNegativeButton("取消",null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mTvClearCache.setText("0.0");
                toast("清理完毕！");
            }
        }).show();
    }

    @OnClick(R.id.ll_alter_nickname)
    public void onClickAlterNickName(){
        //这里我们要执行修改nickname的操作；
        final InputDialog inputDialog = new InputDialog();
      inputDialog.show(this, mUserBean.getNickname(), new InputDialog.OnDialogClickListener() {
          @Override
          public void onDialogClickSure(String inputContent) {

              String nickName=inputContent;

              if(!TextUtils.isEmpty(nickName)){
                  //如果名字没有修改，我们就不做任何的操作；
                  if(nickName.equals(mUserBean.getNickname())){
                      inputDialog.dismiss();
                      return;
                  }
                  //判断邮箱是否有效
                  doUpdateNickName(nickName,inputDialog);
              }else{
                  toast("昵称不能为空！！！");
              }
              inputDialog.dismiss();
          }
      }, "修改昵称", "昵称");

    }

    /**
     * 这里我们要执行的一个操作就是修改昵称；
     * @param trim
     */
    private void doUpdateNickName(final String trim, final InputDialog dialog ) {
        //这里我们对其进行一个提交操作；
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiAlterUserInfo.FROM_UID,getUserBean().getUserid());
        paramsMap.put(ApiAlterUserInfo.USERNAME,trim);
        ApiAlterUserInfo apiAlterUserInfo=new ApiAlterUserInfo(new HttpOnNextListener<ResEmpty>() {

            @Override
            public void onNext(ResEmpty resEmpty) {
                toast("保存成功！");
                UserBean userBean = getUserBean();
                userBean.setNickname(trim);
                updateUser(userBean);
                mTvNickName.setText(trim);
                dialog.dismiss();
                finish();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                toast("修改失败！");
                dialog.dismiss();
            }
        },this,paramsMap);
        HttpManager.getInstance().doHttpDeal(apiAlterUserInfo);
    }

    @OnClick(R.id.ll_help)
    public void onClickHelp(){
        startActivity(new Intent(this,HelpActivity.class));
    }

    @OnClick(R.id.ll_code)
    public void onClickCode(){
        goToMarket(this, SystemUtils.getPackageName(this));
    }

    public  void goToMarket(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            toast("没有可用应用市场");
        }
    }
}
