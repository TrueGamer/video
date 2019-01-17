package com.huanxi.renrentoutiao.utils;

import android.support.annotation.NonNull;

import com.huanxi.renrentoutiao.interfaces.IOnItemLongClickListener;
import com.huanxi.renrentoutiao.model.bean.LoadingBean;
import com.huanxi.renrentoutiao.model.bean.LoadingEndBean;
import com.huanxi.renrentoutiao.model.bean.media.MediaChannelBean;
import com.huanxi.renrentoutiao.model.bean.media.MediaProfileBean;
import com.huanxi.renrentoutiao.model.bean.media.MediaWendaBean;
import com.huanxi.renrentoutiao.model.bean.media.MultiMediaArticleBean;
import com.huanxi.renrentoutiao.ui.media.MediaUserBean;
import com.huanxi.renrentoutiao.ui.media.TTMediaBean;
import com.huanxi.renrentoutiao.ui.media.binder.LoadingEndViewBinder;
import com.huanxi.renrentoutiao.ui.media.binder.LoadingViewBinder;
import com.huanxi.renrentoutiao.ui.media.binder.MediaArticleHeaderViewBinder;
import com.huanxi.renrentoutiao.ui.media.binder.MediaArticleImgViewBinder;
import com.huanxi.renrentoutiao.ui.media.binder.MediaArticleTextViewBinder;
import com.huanxi.renrentoutiao.ui.media.binder.MediaArticleVideoViewBinder;
import com.huanxi.renrentoutiao.ui.media.binder.MediaChannelViewBinder;
import com.huanxi.renrentoutiao.ui.media.binder.MediaWendaViewBinder;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Meiji on 2017/6/9.
 */

public class Register {

    public static void registerMediaChannelItem(@NonNull MultiTypeAdapter adapter, @NonNull IOnItemLongClickListener listener) {
        adapter.register(MediaChannelBean.class, new MediaChannelViewBinder(listener));
    }

    public static void registerMediaArticleItem(@NonNull MultiTypeAdapter adapter) {
//        adapter.register(MultiMediaArticleBean.DataBean.class)
//                .to(new MediaArticleImgViewBinder(),
//                        new MediaArticleVideoViewBinder(),
//                        new MediaArticleTextViewBinder())
//                .withClassLinker((position, item) -> {
//                    if (item.isHas_video()) {
//                        return MediaArticleVideoViewBinder.class;
//                    }
//                    if (null != item.getImage_list() && item.getImage_list().size() > 0) {
//                        return MediaArticleImgViewBinder.class;
//                    }
//                    return MediaArticleTextViewBinder.class;
//                });
        adapter.register(TTMediaBean.class)
                .to(new MediaArticleImgViewBinder())
                .withClassLinker((position, item) -> {
                    return MediaArticleImgViewBinder.class;
                });
        adapter.register(MediaUserBean.class, new MediaArticleHeaderViewBinder());
        adapter.register(LoadingBean.class, new LoadingViewBinder());
        adapter.register(LoadingEndBean.class, new LoadingEndViewBinder());
    }

    public static void registerMediaWendaItem(@NonNull MultiTypeAdapter adapter) {
        adapter.register(MediaWendaBean.AnswerQuestionBean.class, new MediaWendaViewBinder());
        adapter.register(LoadingBean.class, new LoadingViewBinder());
        adapter.register(LoadingEndBean.class, new LoadingEndViewBinder());
    }
}
