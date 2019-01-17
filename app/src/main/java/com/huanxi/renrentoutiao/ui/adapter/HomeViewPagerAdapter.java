package com.huanxi.renrentoutiao.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.huanxi.renrentoutiao.net.bean.news.HomeTabBean;
import com.huanxi.renrentoutiao.ui.fragment.news.HomeTabFragmentOld;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Dinosa on 2018/1/15.
 */

public class HomeViewPagerAdapter extends FragmentStatePagerAdapter {

    private final FragmentManager mFragmentManager;
    public List<HomeTabBean> mHomeTabBeen=new LinkedList<>();
    private HomeTabFragmentOld mCurrentFragment;


    public HomeViewPagerAdapter(FragmentManager fm,List<HomeTabBean> homeTabBeen) {
        super(fm);
        mFragmentManager = fm;
        mHomeTabBeen=homeTabBeen;
    }


    @Override
    public Fragment getItem(int position) {
        HomeTabBean curHomeTabBean = mHomeTabBeen.get(position);
        curHomeTabBean.setChannelSelect(true);
        HomeTabFragmentOld mCurFragment = HomeTabFragmentOld.getHomeTabFragment(curHomeTabBean);
        return mCurFragment;
    }


    @Override
    public int getCount() {
        return mHomeTabBeen.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mHomeTabBeen.get(position).getTitle();
    }

    public void refresh(List<HomeTabBean> bean){
        mHomeTabBeen.clear();
        mHomeTabBeen.addAll(bean);
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentFragment = ((HomeTabFragmentOld) object);
        super.setPrimaryItem(container, position, object);
    }


    public HomeTabFragmentOld getCurrentFragment(){

        return mCurrentFragment;
    }
}
