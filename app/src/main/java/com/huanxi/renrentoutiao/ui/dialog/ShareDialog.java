package com.huanxi.renrentoutiao.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.R;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by andy on 2017/7/17.
 *
 * 这里是分享的Didlog;
 */

public class ShareDialog {

    private final Context mContext;
    public RecyclerView mRecyclerView;

    private List<ShareBean> mShareList;
    private MyAdapter mShareAdapter;
    private PlatformActionListener mListener;
    private Dialog mDialog;
    private String mTitle;
    private String mContent;
    private String mUrl;


    public ShareDialog(Activity context) {

        mContext = context;
        initView();
        initData();

    }

    private void initData() {
        mShareList = new ArrayList<>();
        mShareList.add(new ShareBean(R.drawable.icon_wechat_circle,"微信", Wechat.NAME));
        mShareList.add(new ShareBean(R.drawable.icon_wechat_friend_circle,"朋友圈", WechatMoments.NAME));
        mShareList.add(new ShareBean(R.drawable.icon_qq_circle,"QQ分享", QQ.NAME));
        mShareList.add(new ShareBean(R.drawable.icon_qrcode_v1,"扫码邀请",null));
        mShareAdapter.replaceData(mShareList);
    }

    private void initView() {


        mDialog = new Dialog(mContext, R.style.dialog_share);

        Window window = mDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        View shareDialogView = View.inflate(mContext, R.layout.layout_share_dialog, null);

        mRecyclerView = ((RecyclerView) shareDialogView.findViewById(R.id.rv_share));

        shareDialogView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });


        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,4));

        mShareAdapter=new MyAdapter(null);
        mRecyclerView.setAdapter(mShareAdapter);

        mDialog.setContentView(shareDialogView);

        WindowManager systemService = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = systemService.getDefaultDisplay().getWidth();
        //将对话框的宽度设置为屏幕的宽度；
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width=width;

        window.setAttributes(attributes);
    }

    public  class  MyAdapter extends BaseQuickAdapter<ShareBean,BaseViewHolder> {

        public MyAdapter(List<ShareBean> DList) {
            super(R.layout.item_share,DList);
        }

        @Override
        protected void convert(BaseViewHolder helper,final ShareBean item) {

            helper.setText(R.id.tv_share_text,item.getDrawableDesc());
            helper.setImageResource(R.id.iv_share_icon,item.getDrawableResId());
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //之类我们要进行不同的跳转；
                    mDialog.dismiss();
                    if(!TextUtils.isEmpty(item.getShareType())){
                        share(item.getShareType());
                    }else{
                        //这里我们弹出对话框
                        Toast.makeText(mContext,"暂未开通",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * 这里是封装的show的方法；
     * @param title
     * @param content
     * @param url
     * @param listener
     */
    public  void show(String title,String content,String url,PlatformActionListener listener){

        mTitle = title;
        mContent = content;
        mUrl = url;
        setShareListener(listener);
        mDialog.show();
    }

    public void dismiss(){
        mDialog.dismiss();
    }


    public void share(String platformName) {

        Platform.ShareParams wechatMoments = new Platform.ShareParams();
        wechatMoments.setTitle(mTitle);
        wechatMoments.setText(mContent);
        wechatMoments.setUrl(mUrl);


        wechatMoments.setImageData(BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.ic_launcher));
        wechatMoments.setShareType(Platform.SHARE_WEBPAGE);
        Platform weixin = ShareSDK.getPlatform(platformName);

        if(mListener!=null){
            weixin.setPlatformActionListener(mListener);
        }

        weixin.share(wechatMoments);
    }

    public void setShareListener(PlatformActionListener listener) {
        this.mListener = listener;
    }



    public class ShareBean {

        public int drawableResId;
        public String drawableDesc;

        String shareType;      //分享的类型


        public ShareBean(int drawableResId, String drawableDesc,String shareType) {
            this.drawableResId = drawableResId;
            this.drawableDesc = drawableDesc;


            this.shareType=shareType;
        }

        public String getShareType() {
            return shareType;
        }

        public void setShareType(String shareType) {
            this.shareType = shareType;
        }

        public int getDrawableResId() {
            return drawableResId;
        }

        public void setDrawableResId(int drawableResId) {
            this.drawableResId = drawableResId;
        }

        public String getDrawableDesc() {
            return drawableDesc;
        }

        public void setDrawableDesc(String drawableDesc) {
            this.drawableDesc = drawableDesc;
        }
    }
}
