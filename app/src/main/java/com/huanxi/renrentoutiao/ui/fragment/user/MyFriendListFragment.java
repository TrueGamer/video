package com.huanxi.renrentoutiao.ui.fragment.user;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.net.api.user.ApiMyFriends;
import com.huanxi.renrentoutiao.net.api.user.ApiMyFriendsNew;
import com.huanxi.renrentoutiao.net.bean.ResMyFriends;
import com.huanxi.renrentoutiao.ui.adapter.bean.MyFriendBean;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseLoadingRecyclerViewFragment;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Dinosa on 2018/1/20.
 * 这里是通过获取我的好友贡献金币的列表的；
 */

public class MyFriendListFragment extends BaseLoadingRecyclerViewFragment {


    private InvitateFriendListAdapter mInvitateFriendListAdapter;

    @Override
    public RecyclerView.Adapter getAdapter() {
        if (mInvitateFriendListAdapter == null) {
             mInvitateFriendListAdapter = new InvitateFriendListAdapter(null);
        }
        return mInvitateFriendListAdapter;
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
            super(R.layout.item_invitate_friend, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MyFriendBean item) {
            helper.setText(R.id.tv_username,(helper.getAdapterPosition()+1)+"");
            helper.setText(R.id.tv_date,item.getNickname());
            helper.setText(R.id.tv_prices,"+"+item.getTotalPrice()+"金币");
        }
    }

}
