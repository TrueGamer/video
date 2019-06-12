package com.huanxi.renrentoutiao.ui.fragment.news;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.globle.ConstantUrl;
import com.huanxi.renrentoutiao.net.api.ApiGetRedPacketTime;
import com.huanxi.renrentoutiao.net.api.user.ApiInviteFriendDesc;
import com.huanxi.renrentoutiao.net.bean.ResInviteFriendDesc;
import com.huanxi.renrentoutiao.net.bean.ResSplashAds;
import com.huanxi.renrentoutiao.net.bean.news.HomeTabBean;
import com.huanxi.renrentoutiao.net.bean.user.ResGetRedPacketBean;
import com.huanxi.renrentoutiao.presenter.LoginPresenter;
import com.huanxi.renrentoutiao.presenter.NewsFragmentPresenter;
import com.huanxi.renrentoutiao.presenter.ads.customer.CustomFloatAd;
import com.huanxi.renrentoutiao.ui.activity.WebHelperActivity;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.activity.news.search.SearchActivity;
import com.huanxi.renrentoutiao.ui.activity.other.LuckyWalkActivity;
import com.huanxi.renrentoutiao.ui.activity.other.MainActivity;
import com.huanxi.renrentoutiao.ui.activity.user.UserTaskActivity;
import com.huanxi.renrentoutiao.ui.adapter.FloatAdBean;
import com.huanxi.renrentoutiao.ui.adapter.HomeViewPagerAdapter;
import com.huanxi.renrentoutiao.ui.adapter.NewAdapter;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseFragment;
import com.huanxi.renrentoutiao.ui.view.NewsRefreshBanner;
import com.huanxi.renrentoutiao.ui.view.TabMenuView;
import com.huanxi.renrentoutiao.ui.view.VeticalDrawerLayout;
import com.huanxi.renrentoutiao.ui.view.ads.TAFloatView;
import com.huanxi.renrentoutiao.ui.view.indicator.HomeTabIndicatorAdapter;
import com.huanxi.renrentoutiao.utils.FormatUtils;
import com.huanxi.renrentoutiao.utils.SharedPreferencesUtils;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.huanxi.renrentoutiao.R.id.tv_search;

/**
 * Created by Dinosa on 2018/1/12.
 */

public class HomeFragment extends BaseFragment {

    @BindView(tv_search)
    TextView mTvSearch;


    @BindView(R.id.vp_viewpager)
    ViewPager mVpViewpager;

    @BindView(R.id.iv_add)
    View mIvAdd;


    @BindView(R.id.tmv_tab_menu)
    TabMenuView mTmvTabMenu;

    @BindView(R.id.vdl_layout)
    VeticalDrawerLayout mVdlLayout;

    @BindView(R.id.navigation)
    LinearLayout mLlNavigation;

    @BindView(R.id.ll_navigation_default)
    LinearLayout mLlNavigationNoLogin;

    @BindView(R.id.tv_navigation_notify_title)
    TextView mTvNavigationText;

    @BindView(R.id.ll_red_pack)
    LinearLayout ll_red_pack;

    //这里是请求任务的界面
    public static int REQUEST_TASK_CODE = 2;

    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;


    @BindView(R.id.nrb_refresh_banner)
    NewsRefreshBanner mRefreshBanner;
//    @BindView(R.id.fl_float_container)
//    FrameLayout fl_float_container;

    private HomeTabIndicatorAdapter mTabIndicatorAdapter;
    private NewsFragmentPresenter mPresenter;

    private HomeViewPagerAdapter mViewPagerAdapter;
    private List<NativeExpressADView> mGdtAdList;
    private LoginPresenter mLoginPresenter;
    private boolean tabSpecs;

    @Override
    protected View getContentView() {
        return inflatLayout(R.layout.fragment_home);
    }

    public void  showRefreshBanner(int content){
        mRefreshBanner.show(content);
    }

    @Override
    protected void initView() {
        super.initView();
        mPresenter = new NewsFragmentPresenter(((MainActivity) getActivity()));

        mLoginPresenter = new LoginPresenter(getBaseActivity(), new LoginPresenter.OnLoginListener() {
            @Override
            public void onLoginSuccess() {
                dismissDialog();
                //这里我们要重新请求一次网络的逻辑
                initGetNavigationData();

            }

            @Override
            public void onLoginStart() {
                refresh();
                showDialog();
            }

            @Override
            public void onLoginFaild() {
                refresh();
                dismissDialog();
            }
        });
        tabSpecs = SharedPreferencesUtils.getInstance(getBaseActivity()).getBoolean(ConstantUrl.IS_SHOW);
        if(tabSpecs) {
            mFloatContainer.setVisibility(View.VISIBLE);
            mIvAdd.setVisibility(View.VISIBLE);
            ll_red_pack.setVisibility(View.VISIBLE);
        } else {
            mFloatContainer.setVisibility(View.GONE);
            mIvAdd.setVisibility(View.GONE);
            ll_red_pack.setVisibility(View.GONE);
        }

        initViewPager();

        initIndicator();

        initTabMenu();

        initFloatButton();

        initGetNavigationData();

    }


    /**
     * 获取Navigation里面的内容；
     */
    private void initGetNavigationData() {

        ApiInviteFriendDesc apiInviteFriendDesc = new ApiInviteFriendDesc(new HttpOnNextListener<ResInviteFriendDesc>() {

            @Override
            public void onNext(final ResInviteFriendDesc resInviteFriendDesc) {
                //这里要做的一个操作就是显示对应的
                if ("1".equals(resInviteFriendDesc.getShowTextForLogin())) {
                    //这里要做的一个逻辑就是显示title
                    mLlNavigation.setVisibility(View.VISIBLE);
                    //这里要做的另一个逻辑就是；
                    if(!isLogin()){

                        mLlNavigationNoLogin.setVisibility(View.VISIBLE);
                        mTvNavigationText.setVisibility(View.GONE);

                        mLlNavigationNoLogin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //去登陆的逻辑
                                mLoginPresenter.login();
                            }
                        });

                    }else{
                        if(!tabSpecs) {
                            mLlNavigation.setVisibility(View.GONE);
                        } else {
                            mLlNavigation.setVisibility(View.VISIBLE);
                            mLlNavigationNoLogin.setVisibility(View.GONE);
                            mTvNavigationText.setVisibility(View.VISIBLE);
                            mTvNavigationText.setText(resInviteFriendDesc.getTextforlogin().getTitle());
                            mTvNavigationText.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (!TextUtils.isEmpty(resInviteFriendDesc.getTextforlogin().getUrl())) {

                                        Intent intent = WebHelperActivity.getIntent(getBaseActivity(), resInviteFriendDesc.getTextforlogin().getUrl(), "", false);
                                        startActivity(intent);
                                    }

                                }
                            });
                        }
                    }

                }else{
                    //这里让其隐藏；
                    mLlNavigation.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        }, getBaseActivity(), new HashMap<String, String>());

        HttpManager.getInstance().doHttpDeal(apiInviteFriendDesc);
    }


    @BindView(R.id.fl_float_container)
    public FrameLayout mFloatContainer;

    TAFloatView mTMAwView;
    public View floatView=null;

    /**
     * 这里我们要初始化浮标
     */
    private void initFloatButton() {

        CustomFloatAd customFloatAd = new CustomFloatAd();

        ResSplashAds resAds = getBaseActivity().getMyApplication().getResAds();
        if (resAds != null) {
            ArrayList<FloatAdBean> newfubiao = resAds.getNewfubiao();
            if (newfubiao != null && newfubiao.size() > 0) {

                FloatAdBean floatAdBean = newfubiao.get(0);
                //这里是自定义浮标的问题;
                if ("ta".equals(floatAdBean.getType())) {
                    //这里表示推啊的广告；
                    //这里我们是需要使用推啊的广告；

                    floatView=View.inflate(getBaseActivity(),R.layout.layout_float_ta,null);
                    mTMAwView=(TAFloatView)floatView.findViewById(R.id.TMAw1);
                    mTMAwView.load();

                    mFloatContainer.addView(floatView);
                    //mFloatContainer
                }else{
                    //这里表示自定义的广告的逻辑
                    View view = View.inflate(getBaseActivity(), R.layout.layout_float_icon_ad, null);
                    mFloatContainer.addView(view);
                    customFloatAd.init(view, floatAdBean, getBaseActivity());
                }
            }
        }
    }

    private void initViewPager() {
        mViewPagerAdapter = new HomeViewPagerAdapter(getChildFragmentManager(), new ArrayList<HomeTabBean>());
        mVpViewpager.setAdapter(mViewPagerAdapter);
    }

    private void initTabMenu() {

        //为了解决滑动冲突
        mTmvTabMenu.setOnEditListener(new NewAdapter.onEditListener() {
            @Override
            public void onEdit(boolean isEdit) {
                if (isEdit) {
                    //这里我们让VeticalDrawerLayout不触发触摸事件；
                    mVdlLayout.setCanTouch(false);
                } else {
                    //可以触发触摸事件；
                    mVdlLayout.setCanTouch(true);
                }
            }
        });
        //这里将TabMenu传入Drawerlayout,这样用来实现实现对应的操作；
        mTmvTabMenu.setVeticalDrawerLayout(mVdlLayout);

        mTmvTabMenu.setOnTabMenuChangeListener(new TabMenuView.OnTabMenuViewChangeListener() {

            @Override
            public void onClickClose(List<HomeTabBean> allTabBean, boolean isUpdate) {
                if (!isUpdate) {
                    return;
                }
                mPresenter.updateAllTabs(allTabBean);
                mTabIndicatorAdapter.refreshData(mPresenter.getTabTitles());
                mViewPagerAdapter.refresh(mPresenter.getSelectedTabs());
                mVpViewpager.setCurrentItem(0, false);
                //4、将用户的的频道和所有的频道保存在本地；
                SharedPreferencesUtils.getInstance(getActivity()).setNewsTabMenu(mPresenter.getAllTabs());
            }

            @Override
            public void onClickMyChannel(HomeTabBean tabBean, List<HomeTabBean> allTabBean, boolean isUpdate) {
                if (isUpdate) {
                    //2、刷新数据；
                    mPresenter.updateAllTabs(allTabBean);
                    //1、获取选中的我的频道
                    mViewPagerAdapter.refresh(mPresenter.getSelectedTabs());
                    mTabIndicatorAdapter.refreshData(mPresenter.getTabTitles());
                }
                //3、滚动到指定频道
                mVpViewpager.setCurrentItem(mPresenter.indexOfSelectedTabs(tabBean), false);
                //4、将用户的的频道和所有的频道保存在本地；
                SharedPreferencesUtils.getInstance(getActivity()).setNewsTabMenu(mPresenter.getAllTabs());
            }
        });

    }

    private void initIndicator() {

        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setScrollPivotX(0.8f);
        mTabIndicatorAdapter = new HomeTabIndicatorAdapter(null, getActivity(), mVpViewpager);
        commonNavigator.setAdapter(mTabIndicatorAdapter);
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mVpViewpager);
    }


    @OnClick(R.id.iv_add)
    public void onClickAdd() {
        //这里我们要做的一个操作就是；
        mTmvTabMenu.refreshList(mPresenter.getSelectedTabs(), mPresenter.getUnSelectedTabs());

    }


    @Override
    protected void initData() {
        super.initData();
        //这里我们判断是否有本地缓存
        List<HomeTabBean> tabMenu = SharedPreferencesUtils.getInstance(getActivity()).getNewsTabMenu();

        if (tabMenu != null) {
            mPresenter.updateAllTabs(tabMenu);
            updateUI(mPresenter.getSelectedTabs(), mPresenter.getTabTitles(mPresenter.getSelectedTabs()));
        } else {
            mPresenter.requestNetFromTabData();
        }
        mPresenter.getSearchKey();
    }


    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @OnClick(R.id.ll_search)
    public void onClickSearch() {
        //跳转搜索页面；
        startActivity(new Intent(getActivity(), SearchActivity.class));
    }

    /**
     * 这里我们是需要更新ui的一个操作的；
     *
     * @param selectedTabs
     * @param tabTitles
     */
    public void updateUI(List<HomeTabBean> selectedTabs, List<String> tabTitles) {
        mViewPagerAdapter.refresh(selectedTabs);
        mTabIndicatorAdapter.refreshData(tabTitles);
    }

    @OnClick(R.id.ll_red_pack)
    public void onClickRedPack() {
        checkLogin(new BaseActivity.CheckLoginCallBack() {
            @Override
            public void loginSuccess() {
                //这里我们要做的一个操作就是红包；
//                startActivity(new Intent(getActivity() , UserTaskActivity.class));
                ((MainActivity) getBaseActivity()).newsStartTaskActivity();

//                startActivity(LuckyWalkActivity.getIntent(getBaseActivity(),false));
            }

            @Override
            public void loginFaild() {

            }
        });
    }


    @BindView(R.id.tv_timer)
    TextView mTvTimer;

    @Override
    public void onResume() {
        super.onResume();

        //这里我们要做的一个操作就是，请求接口；
        if (isLogin()) {
            getRedPacketTime();

        } else {
            mTvTimer.setText("领红包");
        }

        initGetNavigationData();
    }


    public void getRedPacketTime() {
        BaseActivity activity = (BaseActivity) getActivity();
        HashMap<String, String> paramsMap = new HashMap<>();

        paramsMap.put(ApiGetRedPacketTime.FROM_UID, activity.getUserBean().getUserid());

        ApiGetRedPacketTime apiGetRedPacketTime = new ApiGetRedPacketTime(new HttpOnNextListener<ResGetRedPacketBean>() {

            @Override
            public void onNext(ResGetRedPacketBean resGetRedPacketBean) {
                //这里我们将开始一个倒计时；
                if (resGetRedPacketBean.getLasttime() == 0) {
                    //这里表示可以领取；
                    mTvTimer.setText("可领取");
                } else {
                    startCountDown(resGetRedPacketBean.getLasttime());
                }
            }
        }, activity, paramsMap);

        HttpManager.getInstance().doHttpDeal(apiGetRedPacketTime);
    }


    private CountDownTimer mCountDownTimer;

    /**
     * 开启一个定时器的操作，时间到了，就可以拆红包；
     */
    private void startCountDown(int totalTime) {

        if (mCountDownTimer != null) {

            mCountDownTimer.cancel();

        }

        int timeUnit = 1000;
        totalTime = totalTime * 1000;
        //去做一个更新的操作；
        mCountDownTimer = new CountDownTimer(totalTime, timeUnit) {
            @Override
            public void onTick(long millisUntilFinished) {
                //去做一个更新的操作；
                mTvTimer.setText(FormatUtils.formatMillisToTime(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                //这里我们将修改状态；变成可领取的状态；
                mTvTimer.setText("可领取");
            }
        };
        mCountDownTimer.start();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {

            if (mPresenter != null) {
                //判断是否有数据，如果没有数据就请求服务器
                if (mPresenter.getAllTabs() == null || mPresenter.getAllTabs().size() == 0) {
                    //这里要做的一个操作；
                    mPresenter.requestNetFromTabData();
                }
            }

            if (!isLogin()) {
                if (mCountDownTimer != null) {
                    mCountDownTimer.cancel();
                }
                mTvTimer.setText("领红包");
            } else {
                if (mTvTimer.getText().equals("领红包")) {
                    //这里是需要重新请求一次；
                    getRedPacketTime();
                }
            }

            initGetNavigationData();

        }
    }

    /**
     * 这里是执行的一个判断是， 菜单式是否是展开的；
     */
    public boolean isMenuOpen() {

        return !mVdlLayout.isClose();
    }

    //关闭菜单；
    public void closeMenu() {
        mVdlLayout.open();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        //这里我们将去掉View；
        if (mGdtAdList != null) {
            for (NativeExpressADView nativeExpressADView : mGdtAdList) {
                nativeExpressADView.destroy();
            }
        }
    }

    /**
     * 这里是更新一个seachKey
     *
     * @param s
     */
    public void updateSearchKey(String s) {
        //这里是热搜的单词；
        mTvSearch.setHint(s);
    }


    /**
     * 这里表示刷新的
     */
    public void refresh() {
        //这里我们要找到对应的Fragment;
        if(mViewPagerAdapter != null && mViewPagerAdapter.getCurrentFragment() != null) {
            mViewPagerAdapter.getCurrentFragment().autoRefresh();
            //toast(mViewPagerAdapter.getCurrentFragment().getTabName());
        }
    }

}
