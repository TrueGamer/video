package com.huanxi.renrentoutiao.ui.media.home.tab;

import com.huanxi.renrentoutiao.model.bean.media.MediaWendaBean;
import com.huanxi.renrentoutiao.ui.base.IBaseListView;
import com.huanxi.renrentoutiao.ui.base.IBasePresenter;
import com.huanxi.renrentoutiao.ui.media.TTMediaBean;

import java.util.List;

/**
 * Created by Meiji on 2017/7/1.
 */

public interface IMediaProfile {

    interface View extends IBaseListView<Presenter> {

        /**
         * 请求数据
         */
        void onLoadData();

        /**
         * 刷新
         */
        void onRefresh();
    }

    interface Presenter extends IBasePresenter {

        /**
         * 请求数据
         */
        void doLoadArticle(String... mediaId);

        void doLoadVideo(String... mediaId);

        void doLoadWenda(String... mediaId);

        /**
         * 再起请求数据
         */
        void doLoadMoreData(int type);

        /**
         * 设置适配器
         */
        void doSetAdapter(List<TTMediaBean> list);

        void doSetWendaAdapter(List<MediaWendaBean.AnswerQuestionBean> list);

        void doRefresh(int type);

        void doShowNoMore();
    }
}
