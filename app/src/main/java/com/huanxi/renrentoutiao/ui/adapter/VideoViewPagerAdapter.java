package com.huanxi.renrentoutiao.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.huanxi.renrentoutiao.net.bean.news.HomeTabBean;
import com.huanxi.renrentoutiao.ui.fragment.video.VideoTabFragment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Dinosa on 2018/1/15.
 */

public class VideoViewPagerAdapter extends FragmentStatePagerAdapter {

    private final FragmentManager mFragmentManager;
    public List<HomeTabBean> mHomeTabBeen=new LinkedList<>();

    List<Fragment> mFragments = new ArrayList<Fragment>();
    private VideoTabFragment mCurrentFragment;

    public VideoViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
    }

    public int mPreposition=0;

    @Override
    public Fragment getItem(int position) {
        mHomeTabBeen.get(mPreposition).setChannelSelect(false);
        HomeTabBean curHomeTabBean = mHomeTabBeen.get(position);
        curHomeTabBean.setChannelSelect(true);
        VideoTabFragment homeTabFragment = VideoTabFragment.getVideoTabFragment(curHomeTabBean);
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
        mCurrentFragment = ((VideoTabFragment) object);
        super.setPrimaryItem(container, position, object);
    }

    public VideoTabFragment getCurrentFragment(){
        return mCurrentFragment;
    }
}
