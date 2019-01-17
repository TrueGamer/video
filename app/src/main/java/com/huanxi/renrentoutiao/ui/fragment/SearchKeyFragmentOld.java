package com.huanxi.renrentoutiao.ui.fragment;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.net.api.ApiSearchKeyWord;
import com.huanxi.renrentoutiao.ui.activity.news.search.SearchActivity;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseLoadingFrament;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Dinosa on 2018/2/7.
 * 这里是搜索的Fragment；
 */
public class SearchKeyFragmentOld extends BaseLoadingFrament {


    @BindView(R.id.fl_flowlayout)
    TagFlowLayout mFlFlowlayout;

    @BindView(R.id.iv_clear_history)
    ImageView mIvClearHistory;

    @BindView(R.id.rv_search_list)
    RecyclerView mRvSearchList;

    private SearchAdapter mSearchAdapter;
    private SearchActivity mBaseActivity;

    @Override
    public int getLoadingContentLayoutId() {
        return R.layout.fragment_search_key;
    }

    @Override
    protected void initView() {
        super.initView();

        mRvSearchList.setLayoutManager(new LinearLayoutManager(getActivity()){

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mSearchAdapter = new SearchAdapter(null);
        mRvSearchList.setAdapter(mSearchAdapter);

        mBaseActivity = ((SearchActivity) getBaseActivity());
    }

    @Override
    protected void initData() {
        super.initData();

        //处理回掉的点击事件；
        mFlFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {

                doSearch(mHotSearchKey.get(position));
                return true;
            }
        });
        getSearchKey();
    }

    private List<String> mHotSearchKey=new ArrayList<>();

    /**
     * 这里我们是获取搜索的key的内容；
     */
    public void getSearchKey() {

        ApiSearchKeyWord apiSearchKeyWord=new ApiSearchKeyWord(new HttpOnNextListener<List<String>>() {

            @Override
            public void onNext(List<String> strings) {
                //这里要初始化
                if (strings!=null && strings.size()>1) {

                    ((SearchActivity) getBaseActivity()).updateSearhKey(strings.get(0));

                    for (int i = 1; i < strings.size(); i++) {
                        mHotSearchKey.add(strings.get(i));
                    }
                }

                mFlFlowlayout.setAdapter(new TagAdapter(mHotSearchKey) {
                    @Override
                    public View getView(FlowLayout parent, int position, Object o) {
                        //这里我们要加载我们的View;
                        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_hot_search, parent, false);
                        ((TextView) view.findViewById(R.id.tv_hot_word)).setText(((String) o));
                        return view;
                    }
                });
                showSuccess();

            }
        },getBaseActivity());

        HttpManager.getInstance().doHttpDeal(apiSearchKeyWord);
    }

    @Override
    public void onResume() {
        super.onResume();

        mSearchAdapter.replaceData(getSearchHistory());
    }

    /**
     * 这里是倒叙将其插入；
     * @return
     */
    public LinkedList<String> getSearchHistory(){
        return mBaseActivity.getHistoryLinkedList();
    }


    public class SearchAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

        public SearchAdapter(@Nullable List<String> data) {
            super(R.layout.item_history_search, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final String item) {

            helper.setText(R.id.tv_history_content,item);
            helper.getView(R.id.iv_clear_history).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //这里要做操作就是删除；
                    String s = mBaseActivity.getHistoryLinkedList().get(helper.getLayoutPosition());
                    mBaseActivity.deleteHistory(s);
                    mSearchAdapter.replaceData(getSearchHistory());
                    notifyDataSetChanged();
                }
            });
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doSearch(getData().get(helper.getAdapterPosition()));
                }
            });
        }
    }

    /**
     * 这里我们做搜索的操作；
     * @param searchKey
     */
    public void doSearch(String searchKey){
        ((SearchActivity) getBaseActivity()).doSearch(searchKey);
    }

}
