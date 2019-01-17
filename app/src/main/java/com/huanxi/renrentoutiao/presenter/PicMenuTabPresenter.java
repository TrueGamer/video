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

public class PicMenuTabPresenter extends BasePresenter{

    private List<PictureTabBean> mAllHomeTabs;

    public PicMenuTabPresenter(BaseActivity baseActivity) {
        super(baseActivity);
    }


    public List<PictureTabBean> getSelectedTabs(){
        return getSelectedTabs(mAllHomeTabs);
    }


    public List<String> getTabTitles(){
        return getTabTitles(getSelectedTabs(mAllHomeTabs));
    }


    public List<PictureTabBean> getUnSelectedTabs(){
        return getUnSelectedTab(mAllHomeTabs);
    }


    /**
     * 获取所有的我的渠道；
     * @param homeTabBeen
     * @return
     */
    protected ArrayList<PictureTabBean> getSelectedTabs(List<PictureTabBean> homeTabBeen) {
        ArrayList<PictureTabBean> tabBeen = new ArrayList<>();
        if (homeTabBeen != null) {
            for (PictureTabBean homeTabBean : homeTabBeen) {
//                if (homeTabBean.getItemType() == HomeTabBean.TYPE_MY_CHANNEL) {
                    tabBeen.add(homeTabBean);
//                }
            }
        }
        return tabBeen;
    }


    /**
     * 获取所有的没有选中的Tab;
     * @param homeTabBeen
     * @return
     */
    protected ArrayList<PictureTabBean> getUnSelectedTab(List<PictureTabBean> homeTabBeen){

        ArrayList<PictureTabBean> tabBeen = new ArrayList<>();
        if (homeTabBeen != null) {
            for (PictureTabBean homeTabBean : homeTabBeen) {
//                if (homeTabBean.getItemType() == HomeTabBean.TYPE_OTHER_CHANNEL) {
                    tabBeen.add(homeTabBean);
//                }
            }
        }
        return tabBeen;
    }


    public List<String> getTabTitles(final List<PictureTabBean> selectedTabs){
        ArrayList<String> strings = new ArrayList<>();
        for (PictureTabBean selectedTab : selectedTabs) {
            strings.add(selectedTab.getName());
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
    public int indexOfSelectedTabs(PictureTabBean tabBean){

        List<PictureTabBean> selectedTabs = getSelectedTabs();
        if (selectedTabs != null) {

            for (int i = 0; i < selectedTabs.size(); i++) {
                PictureTabBean selectedTab = selectedTabs.get(i);
                if(tabBean.getName().equals(selectedTab.getName())){
                    //这里返回指定的位置；
                    return i;
                }
            }
        }
        return 0;
    }

    public void updateAllTabs(List<PictureTabBean> allHomeTabs){
        mAllHomeTabs=allHomeTabs;
    }

    /**
     * 获取所有的Tab内容;
     * @return
     */
    public List<PictureTabBean> getAllTabs(){
        return mAllHomeTabs;
    }
}
