package com.huanxi.renrentoutiao.presenter;

import com.huanxi.renrentoutiao.net.api.ApiSearchKeyWord;
import com.huanxi.renrentoutiao.net.api.news.ApiHomeTabs;
import com.huanxi.renrentoutiao.net.bean.news.HomeTabBean;
import com.huanxi.renrentoutiao.net.bean.news.ResHomeTabs;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.activity.other.MainActivity;
import com.huanxi.renrentoutiao.ui.fragment.news.HomeFragment;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dinosa on 2018/2/2.
 *
 * 这里是VideoFragment的一个业务逻辑类；
 */

public class NewsFragmentPresenter extends MenuTabPresenter{

    public MainActivity mBaseActivity;
    public HomeFragment mNewsFragment;

    public NewsFragmentPresenter(BaseActivity baseActivity) {
        super(baseActivity);
        mBaseActivity= ((MainActivity) baseActivity);
        mNewsFragment =mBaseActivity.getNewsFragment();
    }


    /**
     * 这里要向服务器请求tab的数据；
     */
    public void requestNetFromTabData() {
        //头条，国际，娱乐，科技，国学，文化，军事，电影，财经，时尚，体育，社会，汽车，星座，读书，历史，历史
        //这里模拟请求，得到Tab类型；
        ApiHomeTabs apiHomeTabs = new ApiHomeTabs(new HttpOnNextListener<ResHomeTabs>() {

            @Override
            public void onNext(ResHomeTabs resHomeTabs) {
                //这里我们要获取所有的栏目信息
                List<HomeTabBean> list = resHomeTabs.getList();
                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        HomeTabBean homeTabBean = list.get(i);
                        homeTabBean.setItemtype(HomeTabBean.TYPE_MY_CHANNEL);
                        if (i == 0) {
                            homeTabBean.setChannelType(1);
                        }
                    }
                    ArrayList<HomeTabBean> selectedTabs = getSelectedTabs(list);
                    mNewsFragment.updateUI(selectedTabs,getTabTitles(selectedTabs));

                    updateAllTabs(list);
                }
            }

            @Override
            public void onCacheNext(String string) {
                super.onCacheNext(string);
                //是需要我们自己来做解析的操作；
                System.out.println("cache: "+string);
            }
        }, mBaseActivity, ApiHomeTabs.TYPE_NEWS);

        HttpManager.getInstance().doHttpDeal(apiHomeTabs);
    }

    /**
     * 这里我们是获取搜索的key的内容；
     */
    public void getSearchKey() {

        ApiSearchKeyWord apiSearchKeyWord=new ApiSearchKeyWord(new HttpOnNextListener<List<String>>() {

            @Override
            public void onNext(List<String> strings) {
                //这里要初始化
                if (strings != null && strings.size()>0) {

                    mNewsFragment.updateSearchKey(strings.get(0));
                }
            }
        },mBaseActivity);

        HttpManager.getInstance().doHttpDeal(apiSearchKeyWord);
    }
}
