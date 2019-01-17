package com.huanxi.renrentoutiao.ui.dialog;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.view.luckwalk.Callback;
import com.huanxi.renrentoutiao.ui.view.luckwalk.LuckyWalkBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by ZD on 2017/7/4.
 */

public class MyGiftDialog extends BaseDialog {

    private final List<LuckyWalkBean> mData;
    @BindView(R.id.iv_close)
    ImageView ivClose;

    @BindView(R.id.lvBody)
    RecyclerView lvBody;

    @BindView(R.id.tvUp)
    TextView tvUp;

    @BindView(R.id.tvPage)
    TextView tvPage;

    @BindView(R.id.tvNext)
    TextView tvNext;

    //LuckyWalkBody body;
    Callback callback;
    //LuckyGigtAdapter adapter;

    private List<LuckyWalkBean> records;
    int mPage = 1;
    int mPages = 1;
    private MyRecordAdapter mMyRecordAdapter;

    public MyGiftDialog(Context context,List<LuckyWalkBean> data) {
        super(context);
        mData = data;
        initView();
    }

    void initView() {
        setContentView(R.layout.dialog_my_gift);
        records = new ArrayList<>();
        //adapter = new LuckyGigtAdapter(records, getContext());

        mMyRecordAdapter = new MyRecordAdapter(mData);
        lvBody.setLayoutManager(new LinearLayoutManager(mContext));
        lvBody.setAdapter(mMyRecordAdapter);
    }



    @OnClick(R.id.iv_close)
    void close() {
        dismiss();
    }

    @OnClick(R.id.tvUp)
    void up() {
       /* if (mPage <= 1)
            return;
        if (callback != null)
            callback.callback(-1);*/
    }
    @OnClick(R.id.tvNext)
    void next() {
      /*  if (mPage >= mPages)
            return;
        if (callback != null)
            callback.callback(1);*/
    }
 /*
 public void setBody(int page,LuckyWalkBody body) {
       this.body = body;
        this.mPage = page;
        mPages = body.getPages();
        updateTxt();
    }
    */

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    void updateTxt() {
        tvPage.setText(mPage + "/" + mPages);
        records.clear();

    }

    public class MyRecordAdapter extends BaseQuickAdapter<LuckyWalkBean,BaseViewHolder>{

        public MyRecordAdapter( @Nullable List<LuckyWalkBean> data) {
            super(R.layout.item_lucky_gift, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, LuckyWalkBean item) {

            helper.setText(R.id.tv_time,item.getAddtime());
            helper.setText(R.id.tv_title,item.getMoney());
            helper.setText(R.id.tv_status,"已发放");
        }
    }
}
