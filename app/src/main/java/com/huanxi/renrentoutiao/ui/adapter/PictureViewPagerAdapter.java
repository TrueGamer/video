package com.huanxi.renrentoutiao.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.huanxi.renrentoutiao.model.bean.PictureTabBean;
import com.huanxi.renrentoutiao.net.bean.news.HomeTabBean;
import com.huanxi.renrentoutiao.ui.fragment.picture.PictureTabFragment;
import com.huanxi.renrentoutiao.ui.fragment.video.VideoTabFragment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Dinosa on 2018/1/15.
 */

public class PictureViewPagerAdapter extends FragmentStatePagerAdapter {

    private final FragmentManager mFragmentManager;
    public List<HomeTabBean> mHomeTabBeen = new LinkedList<>();

    List<Fragment> mFragments = new ArrayList<Fragment>();
    private PictureTabFragment mCurrentFragment;

    public PictureViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
    }

    public int mPreposition=0;

    @Override
    public Fragment getItem(int position) {
        mHomeTabBeen.get(mPreposition).setChannelSelect(false);
        HomeTabBean curHomeTabBean = mHomeTabBeen.get(position);
        curHomeTabBean.setChannelSelect(true);
        PictureTabFragment homeTabFragment = PictureTabFragment.getPictureTabFragment(curHomeTabBean);
        return homeTabFragment;
    }

    public void refresh(List<HomeTabBean> bean){
        mHomeTabBeen.clear();
        mHomeTabBeen.addAll(bean);
        //这里要清除FragmentTranscation里面所有的Fragment;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mHomeTabBeen.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mHomeTabBeen.get(position).getTitle();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentFragment = ((PictureTabFragment) object);
        super.setPrimaryItem(container, position, object);
    }

    public PictureTabFragment getCurrentFragment(){
        return mCurrentFragment;
    }
}
