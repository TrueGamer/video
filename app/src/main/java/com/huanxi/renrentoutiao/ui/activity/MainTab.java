package com.huanxi.renrentoutiao.ui.activity;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.fragment.apprentice.ApprenticeFragment;
import com.huanxi.renrentoutiao.ui.fragment.news.HomeFragment;
import com.huanxi.renrentoutiao.ui.fragment.picture.PictureFragment;
import com.huanxi.renrentoutiao.ui.fragment.user.TaskFragment;
import com.huanxi.renrentoutiao.ui.fragment.user.UserFragmentV1;
import com.huanxi.renrentoutiao.ui.fragment.video.NewVideoFragment;
import com.huanxi.renrentoutiao.ui.media.MediaNewFragment;
import com.huanxi.renrentoutiao.ui.media.channel.MediaChannelView;


/**
 * Created by andy on 2017/3/8.
 *
 * 这里我们以枚举的方式实现单例的Fragment：
 */

public enum MainTab {

/*    HOME(0, R.string.main_tab_name_home,R.drawable.main_tab_icon_home, HomeFragment.class),
    STORE(1,R.string.main_tab_name_store, R.drawable.main_tab_icon_store, CategoryListLoadingFragment.class),
    MAP(2,R.string.main_tab_name_map, R.drawable.main_tab_icon_map, MapFragment.class),
    ACCOUNT(3,R.string.main_tab_name_account, R.drawable.main_tab_icon_account, AccountFragment.class);*/



    HOME(0, R.string.main_tab_name_home,R.drawable.selector_tab_home, HomeFragment.class),
    VEDIO(1,R.string.main_tab_name_video, R.drawable.selector_tab_vedio, NewVideoFragment.class),
//    Picture(2 , R.string.main_tab_name_picture , R.drawable.selector_tab_picture , PictureFragment.class),
    Picture(2 , R.string.main_tab_name_rrh , R.drawable.selector_tab_rrh , MediaNewFragment.class),
    Apprentice(3,R.string.main_tab_name_apprentice, R.drawable.selector_tab_apprentice, ApprenticeFragment.class),
    TASK(4,R.string.main_tab_task, R.drawable.selector_tab_task, TaskFragment.class),
    USER(5,R.string.main_tab_name_user, R.drawable.selector_tab_user, UserFragmentV1.class);

    private final int mIdx;
    private final int mDescResource;
    private final int mDrawableResource;
    private final Class mFragmentClazz;

    private MainTab(int idx, int descResource, int drawableResource, Class fragmentClazz) {
        mIdx = idx;
        mDescResource = descResource;
        mDrawableResource = drawableResource;
        mFragmentClazz = fragmentClazz;
    }

    public int getIdx() {
        return mIdx;
    }

    public int getDescResource() {
        return mDescResource;
    }

    public int getDrawableResource() {
        return mDrawableResource;
    }

    public Class getFragmentClazz() {
        return mFragmentClazz;
    }
}
