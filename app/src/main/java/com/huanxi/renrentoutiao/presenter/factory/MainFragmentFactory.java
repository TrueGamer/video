package com.huanxi.renrentoutiao.presenter.factory;

import com.huanxi.renrentoutiao.ui.fragment.base.BaseFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dinosa on 2018/1/13.
 *
 * 这里是MainActiivty里面的Fragment的简单工厂
 */
public class MainFragmentFactory {

    private  Map<String,BaseFragment> mBaseFragments;
    private static MainFragmentFactory mMainFragmentFactory;

    private MainFragmentFactory() {
        mBaseFragments = new HashMap<>();
    }

    public  static MainFragmentFactory newInstance(){

        if(mMainFragmentFactory == null){
            synchronized (MainFragmentFactory.class){
                mMainFragmentFactory = new MainFragmentFactory();
            }
        }
        return mMainFragmentFactory;
    }

    /**
     * 获取指定的Fragment;
     * @param clazz
     * @return
     */
    public BaseFragment getFragment(Class clazz){
        String simpleName = clazz.getSimpleName();
        BaseFragment baseFragment = mBaseFragments.get(simpleName);

        if(baseFragment == null){
            try {
                baseFragment=(BaseFragment) clazz.newInstance();
                mBaseFragments.put(simpleName,baseFragment);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return baseFragment;
    }
}
