package com.huanxi.renrentoutiao.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huanxi.renrentoutiao.R;

import java.util.List;

/**
 * 这里我们做的一个操作就是测试一下RecyclerViewItem过长引起的问题；
 */
public class TestRecyclerView extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_recycler_view);

        mRecyclerView = ((RecyclerView) findViewById(R.id.rv_recycler));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        MuiltyItemAdapter muiltyItemAdapter = new MuiltyItemAdapter(null);

        muiltyItemAdapter.addData(new LongItem());
        muiltyItemAdapter.addData(new NormalItem());
        muiltyItemAdapter.addData(new NormalItem());
        muiltyItemAdapter.addData(new NormalItem());

        mRecyclerView.setAdapter(muiltyItemAdapter);
    }

    public static class MuiltyItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity,BaseViewHolder> {

        public static int TYPE_LONG_ITEM=0;
        public static int TYPE_NORMAL_ITEM=1;
        /**
         * Same as QuickAdapter#QuickAdapter(Context,int) but with
         * some initialization data.
         *
         * @param data A new list is created out of this one to avoid mutable list
         */
        public MuiltyItemAdapter(List<MultiItemEntity> data) {
            super(data);
            addItemType(TYPE_LONG_ITEM,R.layout.item_long_item);
            addItemType(TYPE_NORMAL_ITEM,R.layout.item_vedio_detail_comment_title);
        }

        @Override
        protected void convert(BaseViewHolder helper, MultiItemEntity item) {

        }
    }

    public class LongItem implements MultiItemEntity{

        @Override
        public int getItemType() {
            return MuiltyItemAdapter.TYPE_LONG_ITEM;
        }
    }

    public class NormalItem implements MultiItemEntity{

        @Override
        public int getItemType() {
            return MuiltyItemAdapter.TYPE_NORMAL_ITEM;
        }
    }
}
