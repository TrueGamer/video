package com.huanxi.renrentoutiao.ui.fragment.user.comment;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.model.bean.NewsItemBean;
import com.huanxi.renrentoutiao.model.bean.UserBean;
import com.huanxi.renrentoutiao.net.api.user.browerRecord.ApiBrowerRecordNewsList;
import com.huanxi.renrentoutiao.net.api.user.comment.ApiUserCommentsList;
import com.huanxi.renrentoutiao.net.bean.user.ResUserComments;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.activity.news.NewsDetailActivity2;
import com.huanxi.renrentoutiao.ui.adapter.bean.VideoBean;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseLoadingRecyclerViewFragment;
import com.huanxi.renrentoutiao.utils.ImageUtils;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Dinosa on 2018/1/26.
 * //这里是用户评论的接口；
 *
 */

public class UserCommentFragment extends BaseLoadingRecyclerViewFragment {

    private UserCommentAdapter mUserCommentAdapter;
    public int page = 1;

    @Override
    public RecyclerView.Adapter getAdapter() {

        if (mUserCommentAdapter == null) {
            mUserCommentAdapter = new UserCommentAdapter(null);
        }
        return mUserCommentAdapter;
    }

    @Override
    public void requestAdapterData(final boolean isFirst) {

        page = 1;
        UserBean userBean = ((BaseActivity) getActivity()).getUserBean();


        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiBrowerRecordNewsList.FROM_UID, userBean.getUserid());
        paramsMap.put(ApiBrowerRecordNewsList.PAGE_NUM, page + "");


        //这里我们的接口会发生变化；
        ApiUserCommentsList apiBrowerRecordNewsList = new ApiUserCommentsList(new HttpOnNextListener<ResUserComments>() {

            @Override
            public void onNext(ResUserComments resTabBean) {
                if (resTabBean.getList() != null) {
                    mUserCommentAdapter.replaceData(resTabBean.getList());
                    if (isFirst) {
                        showSuccess();
                    } else {
                        //getEasyRefreshLayout().refreshComplete();
                        refreshComplete();
                    }
                } else {
                    if (page == 1) {
                        showEmpty();
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (isFirst) {
                    showFaild();
                } else {
                    //getEasyRefreshLayout().refreshComplete();
                    refreshComplete();
                }
            }
        }, ((BaseActivity) getActivity()), paramsMap);

        HttpManager.getInstance().doHttpDeal(apiBrowerRecordNewsList);
    }

    @Override
    public void requestNextAdapterData() {
        loadMoreComplete();
    }


    public class UserCommentAdapter extends BaseQuickAdapter<ResUserComments.UserCommentBean, BaseViewHolder> {


        public UserCommentAdapter(@Nullable List<ResUserComments.UserCommentBean> data) {
            super(R.layout.item_my_comment, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final ResUserComments.UserCommentBean item) {

            ImageView userIcon = helper.getView(R.id.iv_icon_user);
            ImageView newsIcon = helper.getView(R.id.iv_news_icon);

            ImageUtils.loadImage(getActivity(), item.getAvatar(), userIcon);

            Gson gson = new Gson();


            JsonObject jsonObject = item.getNewcontent();


            JSONObject newcontent=null;
            String hotnews = "";
            try {
                newcontent = new JSONObject(jsonObject.toString());
                hotnews = newcontent.getString("hotnews");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (!TextUtils.isEmpty(hotnews)) {

                //这里表示是新闻；

                final NewsItemBean newsItemBean = gson.fromJson(newcontent.toString(), NewsItemBean.class);

                if (newsItemBean.getMiniimg() != null && newsItemBean.getMiniimg().size() > 0) {
                    NewsItemBean.MiniimgBean miniimgBean = newsItemBean.getMiniimg().get(0);
                    if (miniimgBean != null) {
                        ImageUtils.loadImage(getActivity(), miniimgBean.getSrc(), newsIcon);
                    }
                }

                helper.setText(R.id.tv_nick_name, item.getNickname());
                helper.setText(R.id.tv_comment_content, item.getContent());
                helper.setText(R.id.tv_news_content, newsItemBean.getTopic());

                helper.setText(R.id.tv_date, item.getAddtime());

                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //这里我们是需要跳转到不同的界面：
                        //这里我们跳转到新闻的页面中去；
                        getActivity().startActivity(NewsDetailActivity2.getIntent(getActivity(),
                                newsItemBean.getUrl(),newsItemBean.getUrlmd5() , "xiaohua" , 1));

                    }
                });

            } else {

                //这里表示是视频；

                final VideoBean newsItemBean = gson.fromJson(newcontent.toString(), VideoBean.class);

                Glide.with(getActivity()).load(newsItemBean.getContent().getLarge_image_list().get(0).getUrl()).into(newsIcon);

                helper.setText(R.id.tv_nick_name, item.getNickname());
                helper.setText(R.id.tv_comment_content, item.getContent());
                helper.setText(R.id.tv_news_content, newsItemBean.getContent().getTitle());

                helper.setText(R.id.tv_date, item.getAddtime());

                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //这里我们是需要跳转到不同的界面：
                        //这里我们跳转到新闻的页面中去；
                        // TODO: 2018/4/11
                       // getActivity().startActivity(VideoItemDetailActivity.getIntent(getActivity(), newsItemBean));
                    }
                });
            }
        }
    }


}
