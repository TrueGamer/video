package com.huanxi.renrentoutiao.ui.fragment.user;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.activity.user.UserTaskActivity;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseLoadingRecyclerViewFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dinosa on 2018/1/20.
 */

public class MyMessageFragment extends BaseLoadingRecyclerViewFragment {
    private MessageAdapter mMessageAdapter;

    @Override
    public RecyclerView.Adapter getAdapter() {
        if (mMessageAdapter == null) {
            mMessageAdapter = new MessageAdapter(null);
        }
        return mMessageAdapter;
    }

    @Override
    public void requestAdapterData(boolean isFirst) {


        ArrayList<MessageBean> messageBeen = new ArrayList<>();

        mMessageAdapter.replaceData(messageBeen);

        //下面的是页面；
        View emptyView = View.inflate(getBaseActivity(), R.layout.fragment_invite_friend_empty, null);

        TextView textView =(TextView) emptyView.findViewById(R.id.tv_empty_desc);

        String html="暂无消息 , <font color='#ff7c34'>立即去赚金币</font>";
        textView.setText(Html.fromHtml(html));

        mMessageAdapter.setEmptyView(emptyView);

        emptyView.findViewById(R.id.ll_invite_friend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里我们要做的一个操作就是显示分享的对话框；
                //这里是邀请好友的链接操作；
                Intent intent = new Intent(getBaseActivity(), UserTaskActivity.class);
                startActivity(intent);
            }
        });


        if(isFirst){
            showSuccess();
        }else{
            refreshComplete();
        }

    }

    @Override
    public void requestNextAdapterData() {

        ArrayList<MessageBean> messageBeen = new ArrayList<>();

        messageBeen.add(new MessageBean());
        messageBeen.add(new MessageBean());
        messageBeen.add(new MessageBean());
        messageBeen.add(new MessageBean());
        messageBeen.add(new MessageBean());
        messageBeen.add(new MessageBean());
        messageBeen.add(new MessageBean());
        messageBeen.add(new MessageBean());
        messageBeen.add(new MessageBean());
        messageBeen.add(new MessageBean());
        messageBeen.add(new MessageBean());
        messageBeen.add(new MessageBean());
        messageBeen.add(new MessageBean());
        messageBeen.add(new MessageBean());

        mMessageAdapter.addData(messageBeen);
        loadMoreComplete();
    }

    /**
     * 清理所有的数据；
     */
    public void clear(){
        mMessageAdapter.replaceData(new ArrayList<MessageBean>());
    }

    public class MessageAdapter extends BaseQuickAdapter<MessageBean,BaseViewHolder>{

        public MessageAdapter(@Nullable List<MessageBean> data) {
            super(R.layout.item_my_message, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MessageBean item) {

        }
    }

    public class MessageBean{

    }
}
