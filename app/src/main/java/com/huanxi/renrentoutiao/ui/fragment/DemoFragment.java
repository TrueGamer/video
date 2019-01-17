package com.huanxi.renrentoutiao.ui.fragment;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Dinosa on 2018/1/19.
 */

public class DemoFragment extends BaseFragment {

    @BindView(R.id.rv_recycler)
    RecyclerView rv_recycler;
    private QuestionsApdater mQuestionsApdater;

    @Override
    protected View getContentView() {
        return inflatLayout(R.layout.fragment_demo);
    }

    @Override
    protected void initView() {
        super.initView();

        rv_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mQuestionsApdater = new QuestionsApdater(null);
        rv_recycler.setAdapter(mQuestionsApdater);

    }

    @Override
    protected void initData() {
        super.initData();
        requestAdapterData(false);
    }

    public void requestAdapterData(boolean isFirst) {
        ArrayList arrayList = new ArrayList<QuestionBean>();
        arrayList.add(new QuestionBean());
        arrayList.add(new QuestionBean());
        arrayList.add(new QuestionBean());
        arrayList.add(new QuestionBean());
        arrayList.add(new QuestionBean());
        arrayList.add(new QuestionBean());
        arrayList.add(new QuestionBean());
        arrayList.add(new QuestionBean());

        mQuestionsApdater.replaceData(arrayList);
    }


    public class QuestionsApdater extends BaseQuickAdapter<QuestionBean,BaseViewHolder> {

        public QuestionsApdater( @Nullable List<QuestionBean> data) {
            super(R.layout.item_questions, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, QuestionBean item) {

        }
    }

    public class QuestionBean{

    }
}
