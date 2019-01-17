package com.huanxi.renrentoutiao.ui.fragment.user;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.model.bean.QuestionBean;
import com.huanxi.renrentoutiao.net.api.ApiQuestion;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseLoadingRecyclerViewFragment;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.List;

/**
 * Created by Dinosa on 2018/1/19.
 */

public class QuestionsFragment extends BaseLoadingRecyclerViewFragment {

    QuestionsApdater mQuestionsAdapter;
    @Override
    public RecyclerView.Adapter getAdapter() {
        if(mQuestionsAdapter == null){
            mQuestionsAdapter =new QuestionsApdater(null);
        }
        return mQuestionsAdapter;
    }

    @Override
    protected void initData() {
        super.initData();
        //getEasyRefreshLayout().setLoadMoreModel(LoadModel.NONE);
        //getEasyRefreshLayout().setEnablePullToRefresh(false);
        setLoadingEnable(false);
        setRefreshEnable(false);
    }

    @Override
    public void requestAdapterData(final boolean isFirst) {


        ApiQuestion apiQuestion=new ApiQuestion(new HttpOnNextListener<List<QuestionBean>>() {

            @Override
            public void onNext(List<QuestionBean> questionBeen) {

                if(questionBeen!=null && questionBeen.size()>0){
                    mQuestionsAdapter.replaceData(questionBeen);
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

        HttpManager.getInstance().doHttpDeal(apiQuestion);
    }

    @Override
    public void requestNextAdapterData() {

    }

    public class QuestionsApdater extends BaseQuickAdapter<QuestionBean,BaseViewHolder> {

        public QuestionsApdater( @Nullable List<QuestionBean> data) {
            super(R.layout.item_questions, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, QuestionBean item) {

            helper.setText(R.id.tv_questions,"问:"+item.getTitle());
            helper.setText(R.id.tv_answer,"答:"+item.getContent());
        }
    }


}
