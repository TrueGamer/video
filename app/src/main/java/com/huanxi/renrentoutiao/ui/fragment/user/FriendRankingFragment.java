package com.huanxi.renrentoutiao.ui.fragment.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.net.api.user.ApiMyFriends;
import com.huanxi.renrentoutiao.net.api.user.ApiMyFriendsNew;
import com.huanxi.renrentoutiao.net.api.user.ApiUserShare;
import com.huanxi.renrentoutiao.net.bean.ResMyFriends;
import com.huanxi.renrentoutiao.net.bean.ResRequestShare;
import com.huanxi.renrentoutiao.ui.adapter.bean.MyFriendBean;
import com.huanxi.renrentoutiao.ui.dialog.ShareDialog;
import com.huanxi.renrentoutiao.ui.dialog.invite.InviteFriendShareDialogFour;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseLoadingRecyclerViewFragment;
import com.huanxi.renrentoutiao.utils.UIUtils;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * Created by Dinosa on 2018/4/9.
 */

public class FriendRankingFragment extends BaseLoadingRecyclerViewFragment {

    private InvitateFriendListAdapter mInvitateFriendListAdapter;
    private InviteFriendShareDialogFour mInviteFriendShareDialogFour;

    @Override
    public RecyclerView.Adapter getAdapter() {
        if (mInvitateFriendListAdapter == null) {
            mInvitateFriendListAdapter = new InvitateFriendListAdapter(null);

            View header = View.inflate(getBaseActivity(), R.layout.header_ranking_friend, null);

            mInvitateFriendListAdapter.addHeaderView(header);

            View emptyView = View.inflate(getBaseActivity(), R.layout.fragment_invite_friend_empty, null);

            TextView textView =(TextView) emptyView.findViewById(R.id.tv_empty_desc);

            String html="暂无好友 , <font color='#ff7c34'>立即去邀请</font>";
            textView.setText(Html.fromHtml(html));

            mInvitateFriendListAdapter.setEmptyView(emptyView);

            emptyView.findViewById(R.id.ll_invite_friend).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //这里我们要做的一个操作就是显示分享的对话框；
                    //这里是邀请好友的链接操作；

                    doShareAction();
                }
            });
        }
        return mInvitateFriendListAdapter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String uid="";
        if (getUserBean() != null) {
            uid=getUserBean().getUserid();
        }

        mInviteFriendShareDialogFour = new InviteFriendShareDialogFour(getBaseActivity(),null,uid);

    }

    /**
     * 这里是做分享的一个操作；
     */
    private void doShareAction() {

        mInviteFriendShareDialogFour.show();

    }



    /**
     * 获取分享的内容；
     */
    private void getShareContent() {

        HashMap<String, String> paramsMap = new HashMap<>();

        paramsMap.put(ApiUserShare.TYPE, ApiUserShare.TYPE_INVITE);
        paramsMap.put(ApiUserShare.FROM_UID, getUserBean().getUserid());

        ApiUserShare apiUserShare = new ApiUserShare(new HttpOnNextListener<ResRequestShare>() {
            @Override
            public void onNext(ResRequestShare resRequestShare) {

                //这里要做的一个操作就是请求服务器的一个操作；

                ShareDialog shareDialog = new ShareDialog(getBaseActivity());
                shareDialog.show(resRequestShare.getTitle(), resRequestShare.getDesc(), resRequestShare.getUrl(), new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(Platform platform, int i) {

                    }
                });
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        }, getBaseActivity(), paramsMap);
        apiUserShare.setShowProgress(true);
        HttpManager.getInstance().doHttpDeal(apiUserShare);
    }

    @Override
    protected void initData() {
        super.initData();

        int leftPadding= UIUtils.dip2px(getBaseActivity(),10);
        getRvHome().setPadding(leftPadding,0,leftPadding,0);
    }

    @Override
    public void requestAdapterData(final boolean isFirst) {

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiMyFriends.FROM_UID,getUserBean().getUserid());
        paramsMap.put(ApiMyFriends.PAGE_SIZE,"1");

        ApiMyFriendsNew apiMyFriends = new ApiMyFriendsNew(new HttpOnNextListener<ResMyFriends>() {

            @Override
            public void onNext(ResMyFriends resMyFriends) {

                List<MyFriendBean> list = resMyFriends.getList();

                if (list!=null && !list.isEmpty()) {
                    //这里表示服务器有数据；
                    mInvitateFriendListAdapter.replaceData(list);
                    page=2;
                }

                if(isFirst){
                    if(list==null && list.isEmpty()){
                        showEmpty();
                    }else{
                        showSuccess();
                    }
                }else{
                    refreshComplete();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (isFirst) {
                    showFaild();
                }else{
                    refreshComplete();
                }
            }
        },getBaseActivity(),paramsMap);
        HttpManager.getInstance().doHttpDeal(apiMyFriends);
    }

    public int page=1;

    @Override
    public void requestNextAdapterData() {

        HashMap<String, String> paramsMap = new HashMap<>();

        paramsMap.put(ApiMyFriends.FROM_UID,getUserBean().getUserid());

        ApiMyFriends apiMyFriends = new ApiMyFriends(new HttpOnNextListener<ResMyFriends>() {

            @Override
            public void onNext(ResMyFriends resMyFriends) {

                List<MyFriendBean> list = resMyFriends.getList();
                if(list==null || list.isEmpty()){
                    //这里表示没有数据；
                    page++;
                    mInvitateFriendListAdapter.addData(list);
                }
                loadMoreComplete();
            }
        },getBaseActivity(),paramsMap);
        HttpManager.getInstance().doHttpDeal(apiMyFriends);
    }


    public class InvitateFriendListAdapter extends BaseQuickAdapter<MyFriendBean,BaseViewHolder>{

        public InvitateFriendListAdapter( @Nullable List<MyFriendBean> data) {
            super(R.layout.item_invite_friend_ranking, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MyFriendBean item) {
            helper.setText(R.id.tv_ranking_number,(helper.getAdapterPosition())+"");
            helper.setText(R.id.tv_nick_name,item.getNickname()+"");
            helper.setText(R.id.tv_date,"最近登陆 "+item.getAddtime());
            helper.setText(R.id.tv_prices,item.getTotalPrice()+"");
        }
    }
}
