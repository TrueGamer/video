package com.huanxi.renrentoutiao.presenter;

import com.huanxi.renrentoutiao.model.bean.PictureTabBean;
import com.huanxi.renrentoutiao.net.bean.news.HomeTabBean;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dinosa on 2018/2/2.
 *
 * 这里是栏目分栏的一个基础的业务逻辑类；
 */

public class MenuTabPresenter extends BasePresenter{



    private List<HomeTabBean> mAllHomeTabs;

    public MenuTabPresenter(BaseActivity baseActivity) {
        super(baseActivity);
    }


    public List<HomeTabBean> getSelectedTabs(){
        return getSelectedTabs(mAllHomeTabs);
    }


    public List<String> getTabTitles(){
        return getTabTitles(getSelectedTabs(mAllHomeTabs));
    }


    public List<HomeTabBean> getUnSelectedTabs(){
        return getUnSelectedTab(mAllHomeTabs);
    }


    /**
     * 获取所有的我的渠道；
     * @param homeTabBeen
     * @return
     */
    protected ArrayList<HomeTabBean> getSelectedTabs(List<HomeTabBean> homeTabBeen) {
        ArrayList<HomeTabBean> tabBeen = new ArrayList<>();
        if (homeTabBeen != null) {
            for (HomeTabBean homeTabBean : homeTabBeen) {
                if (homeTabBean.getItemType() == HomeTabBean.TYPE_MY_CHANNEL) {
                    tabBeen.add(homeTabBean);
                }
            }
        }
        return tabBeen;
    }


    /**
     * 获取所有的没有选中的Tab;
     * @param homeTabBeen
     * @return
     */
    protected ArrayList<HomeTabBean> getUnSelectedTab(List<HomeTabBean> homeTabBeen){

        ArrayList<HomeTabBean> tabBeen = new ArrayList<>();
        if (homeTabBeen != null) {
            for (HomeTabBean homeTabBean : homeTabBeen) {
                if (homeTabBean.getItemType() == HomeTabBean.TYPE_OTHER_CHANNEL) {
                    tabBeen.add(homeTabBean);
                }
            }
        }
        return tabBeen;
    }


    public List<String> getTabTitles(final List<HomeTabBean> selectedTabs){
        ArrayList<String> strings = new ArrayList<>();
        for (HomeTabBean selectedTab : selectedTabs) {
            strings.add(selectedTab.getTitle());
        }
        return strings;
    }

    public List<String> getPictureTabTitles(final List<PictureTabBean> selectedTabs){
        ArrayList<String> strings = new ArrayList<>();
        for (PictureTabBean selectedTab : selectedTabs) {
            strings.add(selectedTab.getName());
        }
        return strings;
    }

    /**
     * 获取bean在选中的bar中的位置；
     * @return
     */
    public int indexOfSelectedTabs(HomeTabBean tabBean){

        List<HomeTabBean> selectedTabs = getSelectedTabs();
        if (selectedTabs != null) {

            for (int i = 0; i < selectedTabs.size(); i++) {
                HomeTabBean selectedTab = selectedTabs.get(i);
                if(tabBean.getTitle().equals(selectedTab.getTitle())){
                    //这里返回指定的位置；
                    return i;
                }
            }
        }
        return 0;
    }

    public void updateAllTabs(List<HomeTabBean> allHomeTabs){
        mAllHomeTabs=allHomeTabs;
    }

    /**
     * 获取所有的Tab内容;
     * @return
     */
    public List<HomeTabBean> getAllTabs(){
        return mAllHomeTabs;
    }
}
