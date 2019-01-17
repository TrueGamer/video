package com.huanxi.renrentoutiao.ui.activity.news.search;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.fragment.SearchKeyFragment;
import com.huanxi.renrentoutiao.utils.SharedPreferencesUtils;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.huanxi.renrentoutiao.R.id.ev_search;

public class SearchActivity extends BaseActivity {


    @BindView(ev_search)
    EditText mEvSearch;
    private SearchKeyFragment mSearchKeyFragment;


    @Override
    public int getContentLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        //这里我们对搜索的页面进行一个初始化；
        setStatusBarImmersive(null);

        mSearchKeyFragment = new SearchKeyFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_serach_container, mSearchKeyFragment)
                .commitAllowingStateLoss();
    }

    public void updateSearhKey(String searchKey){
        mEvSearch.setText(searchKey);
        mEvSearch.setSelection(searchKey.length());
    }


    @OnClick(R.id.iv_back)
    public void onClickBack() {
        finish();
    }

    @OnClick(R.id.tv_search)
    public void onClickSearch() {
        //实现搜索的页面；
        doSearch(mEvSearch.getText().toString());
    }

    public void doSearch(String searchKey) {
        if (!TextUtils.isEmpty(searchKey)) {

            saveHistory(searchKey);

            //这里我们进行一个搜索的操作；
            startActivity(SearchDetailActivity.getSearchIntent(this, searchKey));
        }
    }

    private void saveHistory(String searchKey) {

        LinkedList<String> histories = getHistoryLinkedList();

        histories.remove(searchKey);
        histories.add(searchKey);
        saveHistory(histories);
    }


    /**
     * 保存搜索的历史记录
     * @param histories
     */
    public void saveHistory(LinkedList<String> histories){
        String searchHistory = "";
        //这里我们从前面移除
        while (histories.size() > 5) {
            histories.removeFirst();
        }
        for (int i = 0; i < histories.size(); i++) {
            searchHistory+=histories.get(i);
            if(i<histories.size()-1){
                searchHistory+=",";
            }
        }
        SharedPreferencesUtils.getInstance(this).updateHistory(searchHistory);
    }

    /**
     * 获取存储的历史记录
     * @return
     */
    public LinkedList<String> getHistoryLinkedList(){
        LinkedList<String> histories = new LinkedList<>();

        SharedPreferencesUtils instance = SharedPreferencesUtils.getInstance(this);
        String history = instance.getHistory();

        if (!TextUtils.isEmpty(history)) {
            String[] split = history.split(",");

            if (split != null && split.length > 0) {
                for (String s : split) {
                    if(!TextUtils.isEmpty(s)){
                        histories.add(s);
                    }
                }
            }
        }
        return histories;
    }

    public void deleteHistory(String searchKey){
        LinkedList<String> histories = getHistoryLinkedList();
        histories.remove(searchKey);
        saveHistory(histories);
    }


    /**
     * 清除所有的搜索记录
     */
    public void clearAllSearchHistory(){

        SharedPreferencesUtils.getInstance(this).clearAllSearchHistory();

    }

}
