package com.huanxi.renrentoutiao.ui.fragment.user.collection;

import com.huanxi.renrentoutiao.model.bean.UserBean;
import com.huanxi.renrentoutiao.net.api.user.browerRecord.ApiBrowerRecordNewsList;
import com.huanxi.renrentoutiao.net.api.user.collection.ApiUserNewsCollections;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.fragment.user.browerRecord.NewsBrowerRecordFragment;
import com.zhxu.library.http.HttpManager;

import java.util.HashMap;

/**
 * Created by Dinosa on 2018/2/3.
 */

public class UserNewsCollectionFragment extends NewsBrowerRecordFragment {


    @Override
    public void requestAdapterData(boolean isFirst) {

        page=1;
        UserBean userBean = ((BaseActivity) getActivity()).getUserBean();

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiBrowerRecordNewsList.FROM_UID,userBean.getUserid());
        paramsMap.put(ApiBrowerRecordNewsList.PAGE_NUM,1+"");

        ApiUserNewsCollections apiUserCollection=new ApiUserNewsCollections(getRequestResultListener(isFirst), ((BaseActivity) getActivity()),paramsMap);
        HttpManager.getInstance().doHttpDeal(apiUserCollection);
    }


    public String getEmptyDesc(){

        return "暂无收藏 , <font color='#ff7c34'>去阅读</font>";
    }

    @Override
    public void requestNextAdapterData() {
        UserBean userBean = ((BaseActivity) getActivity()).getUserBean();

        HashMap<String, String> paramsMap = new HashMap<>();

        paramsMap.put(ApiBrowerRecordNewsList.FROM_UID,userBean.getUserid());
        paramsMap.put(ApiBrowerRecordNewsList.PAGE_NUM,page+"");

        ApiUserNewsCollections apiUserCollection=new ApiUserNewsCollections(getLoadMoreResultListener(), ((BaseActivity) getActivity()),paramsMap);
        HttpManager.getInstance().doHttpDeal(apiUserCollection);
    }
}
