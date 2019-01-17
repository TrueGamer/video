package com.huanxi.renrentoutiao.net;

import com.huanxi.renrentoutiao.globle.ConstantUrl;
import com.huanxi.renrentoutiao.model.bean.AdsBean;
import com.huanxi.renrentoutiao.model.bean.QuestionBean;
import com.huanxi.renrentoutiao.model.bean.UserBean;
import com.huanxi.renrentoutiao.net.bean.ResCheckVersion;
import com.huanxi.renrentoutiao.net.bean.ResEmpty;
import com.huanxi.renrentoutiao.net.bean.ResHelpBean;
import com.huanxi.renrentoutiao.net.bean.ResInviteFriendContent;
import com.huanxi.renrentoutiao.net.bean.ResInviteFriendDesc;
import com.huanxi.renrentoutiao.net.bean.ResMyFriends;
import com.huanxi.renrentoutiao.net.bean.ResNewContactKeFu;
import com.huanxi.renrentoutiao.net.bean.ResReadAwarad;
import com.huanxi.renrentoutiao.net.bean.ResRequestShare;
import com.huanxi.renrentoutiao.net.bean.ResSplashAds;
import com.huanxi.renrentoutiao.net.bean.ResWallettotalBean;
import com.huanxi.renrentoutiao.net.bean.browerRecord.ResUserNewsBrowerRecord;
import com.huanxi.renrentoutiao.net.bean.browerRecord.ResUserVideoBrowerRecord;
import com.huanxi.renrentoutiao.net.bean.comment.ResNewsCommentList;
import com.huanxi.renrentoutiao.net.bean.luckywalk.ResLuckwalkProductBean;
import com.huanxi.renrentoutiao.net.bean.money.ResGoldDetailList;
import com.huanxi.renrentoutiao.net.bean.money.ResMoneyDetailList;
import com.huanxi.renrentoutiao.net.bean.money.ResWithdrawRecords;
import com.huanxi.renrentoutiao.net.bean.news.ResAward;
import com.huanxi.renrentoutiao.net.bean.news.ResHomeTabs;
import com.huanxi.renrentoutiao.net.bean.news.ResNewsAndVideoBean;
import com.huanxi.renrentoutiao.net.bean.news.ResNewsDetailBean;
import com.huanxi.renrentoutiao.net.bean.news.ResTabBean;
import com.huanxi.renrentoutiao.net.bean.sign.ResGetSignList;
import com.huanxi.renrentoutiao.net.bean.user.ResGetRedPacketBean;
import com.huanxi.renrentoutiao.net.bean.user.ResUserComments;
import com.huanxi.renrentoutiao.net.bean.video.ResVedioSource;
import com.huanxi.renrentoutiao.net.bean.video.ResVideoComment;
import com.huanxi.renrentoutiao.net.bean.video.ResVideoList;
import com.huanxi.renrentoutiao.ui.adapter.bean.TaskTitleBean;
import com.zhxu.library.api.BaseResultEntity;

import java.util.HashMap;
import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;


/**
 * Created by Dinosa on 2018/1/25.
 */

public interface ApiServices {
    //搜索的关键字
    @GET(ConstantUrl.GET_SEARCH_ADS)
    public Observable<BaseResultEntity<AdsBean>> geTads();
    //根据栏目获取对应的新闻
    @GET(ConstantUrl.NEWS_URL)
    public Observable<BaseResultEntity<ResTabBean>> getHomeNews(@QueryMap HashMap<String, String> paramsMap);


    //获取新闻详情；
    @GET(ConstantUrl.NEWS_DETAIL_URL)
    public Observable<BaseResultEntity<ResNewsDetailBean>> getNewsDetail(@QueryMap HashMap<String, String> paramsMap);


    //根据类型获取对应的栏目，1表示新闻，2表示视频
    @GET(ConstantUrl.NEWS_TYPE_URL)
    public Observable<BaseResultEntity<ResHomeTabs>> getHomeNewsType(@Query("type") String type);


    //获取视频列表；
    @GET(ConstantUrl.VEDIO_URL_NEW)
    public Observable<BaseResultEntity<ResVideoList>> getVedioList(@QueryMap HashMap<String, String> paramsMap);

    //获取视频源信息
    @GET(ConstantUrl.VEDIO_SOURCE_DEATIL_URL)
    public Observable<BaseResultEntity<ResVedioSource>> getVedioSourceDetail(@QueryMap HashMap<String, String> paramsMap);

    //获取视评论信息
    @GET(ConstantUrl.VEDIO_COMMENT_LIST)
    public Observable<BaseResultEntity<List<ResVideoComment.DataBean>>> getVedioComment(@QueryMap HashMap<String, String> paramsMap);

    //获取获取验证码
    @GET(ConstantUrl.VALIDE_PHONE_NUMBER_CODE)
    public Observable<BaseResultEntity<ResEmpty>> getValidPhoneNumber(@QueryMap HashMap<String, String> paramsMap);

    //获取请求登陆的逻辑
    @GET(ConstantUrl.LOGIN_URL)
    public Observable<BaseResultEntity<String>> requestLogin(@QueryMap HashMap<String, String> paramsMap);

    //获取请求微信登陆的逻辑
    @GET(ConstantUrl.WECHAT_LOGIN_URL)
    public Observable<BaseResultEntity<String>> requestWeChatLogin(@QueryMap HashMap<String, String> paramsMap);

    //获取获取检查版本号；
    @GET(ConstantUrl.CHECK_VERSION)
    public Observable<BaseResultEntity<ResCheckVersion>> checkVersion(@QueryMap HashMap<String, String> paramsMap);


    //请求用户签到的模块；
    @GET(ConstantUrl.USER_SIGN)
    public Observable<BaseResultEntity<ResAward>> requestSign(@QueryMap HashMap<String, String> paramsMap);


    //获取用户信息；
    @GET(ConstantUrl.GET_USE_INFO)
    public Observable<BaseResultEntity<UserBean>> getUserInfo(@QueryMap HashMap<String, String> paramsMap);

    //添加用户评论
    @GET(ConstantUrl.USER_COMMENT)
    public Observable<BaseResultEntity<ResEmpty>> requestReleaseComment(@QueryMap HashMap<String, String> paramsMap);


    //获取评论列表；
    @GET(ConstantUrl.COMMENT_LIST)
    public Observable<BaseResultEntity<ResNewsCommentList>> getCommentList(@QueryMap HashMap<String, String> paramsMap);

    //获取评论列表；
    @GET(ConstantUrl.USER_COMMENT_LIST)
    public Observable<BaseResultEntity<ResUserComments>> getMineCommentList(@QueryMap HashMap<String, String> paramsMap);

    //获取添加或者删除收藏；
    @GET(ConstantUrl.USER_COLLECTION)
    public Observable<BaseResultEntity<ResEmpty>> requestAlterCollection(@QueryMap HashMap<String, String> paramsMap);

    //获取收藏列表；
    @GET(ConstantUrl.USER_COLLECTION_LIST)
    public Observable<BaseResultEntity<ResUserNewsBrowerRecord>> requestMineCollection(@QueryMap HashMap<String, String> paramsMap);

    //获取视频收藏列表；
    @GET(ConstantUrl.USER_COLLECTION_LIST)
    public Observable<BaseResultEntity<ResUserVideoBrowerRecord>> requestMineVideoCollection(@QueryMap HashMap<String, String> paramsMap);

    //新闻浏览记录；
    @GET(ConstantUrl.USER_BROWER_RECORD)
    public Observable<BaseResultEntity<ResUserNewsBrowerRecord>> getNewsBrowerRecordList(@QueryMap HashMap<String, String> paramsMap);

    //金币的流水；
    @GET(ConstantUrl.USER_GOLD_COIN_DETAIL_LIST)
    public Observable<BaseResultEntity<ResGoldDetailList>> getGoldCoinDetailList(@QueryMap HashMap<String, String> paramsMap);

    //余额的流水；
    @GET(ConstantUrl.USER_MONEY_DETAIL_LIST)
    public Observable<BaseResultEntity<ResMoneyDetailList>> getMoneyDetailList(@QueryMap HashMap<String, String> paramsMap);

    //进行提现操作；
    @GET(ConstantUrl.USER_WITHDRAWALS_MONEY)
    public Observable<BaseResultEntity<ResEmpty>> requestWithdrawalsMoney(@QueryMap HashMap<String, String> paramsMap);

    //进行提现进度；
    @GET(ConstantUrl.USER_WITHDRAWAL_RECORDS)
    public Observable<BaseResultEntity<ResWithdrawRecords>> getWithdrawalRecordsList(@QueryMap HashMap<String, String> paramsMap);

    //进行提现进度；
    @GET(ConstantUrl.START_READ_ISSURE)
    public Observable<BaseResultEntity<ResEmpty>> startReadIssue(@QueryMap HashMap<String, String> paramsMap);

    @GET(ConstantUrl.NEW_AD_LOG)
    public Observable<BaseResultEntity<String>> newAdLog(@QueryMap HashMap<String, String> paramsMap);

    @GET(ConstantUrl.CHECK_SERVICE)
    public Observable<BaseResultEntity<String>> checkService(@QueryMap HashMap<String, String> paramsMap);

    //进行提现进度；
    @GET(ConstantUrl.END_READ_ISSURE)
    public Observable<BaseResultEntity<ResReadAwarad>> endReadIssue(@QueryMap HashMap<String, String> paramsMap);

    //进行提现进度；
    @GET(ConstantUrl.START_READ_AD)
    public Observable<BaseResultEntity<ResEmpty>> startReadAd(@QueryMap HashMap<String, String> paramsMap);

    //进行提现进度；
    @GET(ConstantUrl.END_READ_AD)
    public Observable<BaseResultEntity<ResReadAwarad>> endReadAd(@QueryMap HashMap<String, String> paramsMap);

    //修改个人信息；
    @GET(ConstantUrl.ALTER_USER_INFO)
    public Observable<BaseResultEntity<ResEmpty>> alterUserInfo(@QueryMap HashMap<String, String> paramsMap);

    //绑定手机号
    @GET(ConstantUrl.BIND_USER_PHONE)
    public Observable<BaseResultEntity<String>> bindPhoneNumber(@QueryMap HashMap<String, String> paramsMap);

    //获取签到信息
    @GET(ConstantUrl.GET_SIGN_INFO)
    public Observable<BaseResultEntity<ResGetSignList>> getSignInfo(@QueryMap HashMap<String, String> paramsMap);

    //获取获取任务的接口
    @GET(ConstantUrl.GET_ALL_TASK)
    public Observable<BaseResultEntity<List<TaskTitleBean>>> getAllTask(@QueryMap HashMap<String, String> paramsMap);

    //视频浏览记录；
    @GET(ConstantUrl.USER_BROWER_RECORD)
    public Observable<BaseResultEntity<ResUserVideoBrowerRecord>> getVideoBrowerRecordList(@QueryMap HashMap<String, String> paramsMap);

    //获取拆红包的倒计时
    @GET(ConstantUrl.GET_RED_PACKET_TIME)
    public Observable<BaseResultEntity<ResGetRedPacketBean>> getRedPacketTime(@QueryMap HashMap<String, String> paramsMap);

    //获取拆红包的倒计时
    @GET(ConstantUrl.GET_RED_PACKET)
    public Observable<BaseResultEntity<ResGetRedPacketBean>> getRedPacket(@QueryMap HashMap<String, String> paramsMap);

    //搜索的关键字
    @GET(ConstantUrl.GET_SEARCH_KEY_LIST)
    public Observable<BaseResultEntity<List<String>>> getSearchKeyList();

    //填写邀请码
    @GET(ConstantUrl.ADD_INVITE_CODE)
    public Observable<BaseResultEntity<ResAward>> addInviteCode(@QueryMap HashMap<String, String> paramsMap);

    //提交帮助与反馈的接口
    @GET(ConstantUrl.HELP_AND_CALL_BACK)
    public Observable<BaseResultEntity<ResEmpty>> helpCallBack(@QueryMap HashMap<String, String> paramsMap);

    //获取客服信息
    @GET(ConstantUrl.GET_KEFU_INFO)
    public Observable<BaseResultEntity<List<String>>> getContactInfo();

    //获取钱包信息
    @GET(ConstantUrl.GET_WALLET_TOTAL)
    public Observable<BaseResultEntity<ResWallettotalBean>> getWalletToal(@QueryMap HashMap<String, String> paramsMap);

    //获取钱包信息
    @GET(ConstantUrl.GET_QUESTION_LIST)
    public Observable<BaseResultEntity<List<QuestionBean>>> getQuestionList();

    //获取钱包信息
    @GET(ConstantUrl.CLEAR_RECORD)
    public Observable<BaseResultEntity<ResEmpty>> clearBrowerRecord(@QueryMap HashMap<String, String> paramsMap);

    //获取请求分享内容
    @GET(ConstantUrl.REQUEST_SHARE)
    public Observable<BaseResultEntity<ResRequestShare>> getShareContent(@QueryMap HashMap<String, String> paramsMap);

    //获取请求分享成功
    @GET(ConstantUrl.SHARE_SUCCESS)
    public Observable<BaseResultEntity<ResAward>> shareSuccess(@QueryMap HashMap<String, String> paramsMap);

    //获取请求分享成功
    @GET(ConstantUrl.SPLASH_AD_INFOS)
    public Observable<BaseResultEntity<ResSplashAds>> getSplashAds();

    //获取请求点赞
    @GET(ConstantUrl.REQUEST_DO_LIKE)
    public Observable<BaseResultEntity<ResEmpty>> requestDoLike(@QueryMap HashMap<String, String> paramsMap);

    //获取请求点赞
    @GET(ConstantUrl.REQUEST_FRIENDS)
    public Observable<BaseResultEntity<ResMyFriends>> requestMyFriends(@QueryMap HashMap<String, String> paramsMap);

    //获取请求点赞
    @GET(ConstantUrl.REQUEST_FRIENDS_NEW)
    public Observable<BaseResultEntity<ResMyFriends>> requestMyFriendsNew(@QueryMap HashMap<String, String> paramsMap);

    //获取请求点赞
    @GET(ConstantUrl.REQUEST_INVITE_AWARD)
    public Observable<BaseResultEntity<ResAward>> requestInviteAward(@QueryMap HashMap<String, String> paramsMap);


    //获取客服信息
    @GET(ConstantUrl.REQUEST_CONTACT_NUMBER)
    public Observable<BaseResultEntity<List<ResHelpBean>>> getNewContactInfo();

    //获取新闻和视频的数据；
    @GET(ConstantUrl.REQUEST_NEWS_AND_VIDEO)
    public Observable<BaseResultEntity<ResNewsAndVideoBean>> getNewsAndVideo(@QueryMap HashMap<String, String> paramsMap);

    //获取所有的任务的操作；
    @GET(ConstantUrl.REQUEST_NEW_ALL_TASK)
    public Observable<BaseResultEntity<List<TaskTitleBean>>> getNewAllTask(@QueryMap HashMap<String, String> paramsMap);

    //获取邀请的文字描述的操作；
    @GET(ConstantUrl.REQUEST_INVITE_FRIEND_DESC)
    public Observable<BaseResultEntity<ResInviteFriendDesc>> getInviteFriendDesc(@QueryMap HashMap<String, String> paramsMap);

    //...........
    //获取获取邀请好友的分享的内容；
    @GET(ConstantUrl.REQUEST_INVITE_FRIEND_CONTENT)
    public Observable<BaseResultEntity<ResInviteFriendContent>> getInvietFriendContent(@QueryMap HashMap<String, String> paramsMap);

    //...........
    //分享文章和视频的的内容；
    @Deprecated
    @GET(ConstantUrl.REQUEST_SHARE_ARTCLE_AND_VIDEO)
    public Observable<BaseResultEntity<ResRequestShare>> getShareArticleAndVideoContent(@QueryMap HashMap<String, String> paramsMap);

    //...........
    //这里是高佣任务的api接口；
    @GET(ConstantUrl.REQUEST_ADVANCE_TASK_AWARD)
    public Observable<BaseResultEntity<ResAward>> getAdvanceTaskAward(@QueryMap HashMap<String, String> paramsMap);

    //新的客服的接口
    @GET(ConstantUrl.REQEUST_NEW_KEFU_CONTACT)
    public Observable<BaseResultEntity<List<ResNewContactKeFu>>> getNewKeFuContact();

    //新的客服的接口
    @GET(ConstantUrl.WECHAT_LOGIN_NEW)
    public Observable<BaseResultEntity<String>> requestWechatNew(@QueryMap HashMap<String, String> paramsMap);

    //新的客服的接口
    @GET(ConstantUrl.ONLY_BIND_PHONE)
    public Observable<BaseResultEntity<ResEmpty>> requestOnlyBindPhone(@QueryMap HashMap<String, String> paramsMap);

    //开始自定义任务的接口；
    @GET(ConstantUrl.START_CUSTOM_TASK)
    public Observable<BaseResultEntity<ResAward>> requestStartCustomTask(@QueryMap HashMap<String, String> paramsMap);

    //结束自定义任务的接口；
    @GET(ConstantUrl.END_CUSTOM_TASK)
    public Observable<BaseResultEntity<ResAward>> requestEndCustomTask(@QueryMap HashMap<String, String> paramsMap);

    //结束自定义任务的接口；
    @GET(ConstantUrl.REQUEST_PRODUCT_LIST)
    public Observable<BaseResultEntity<List<ResLuckwalkProductBean>>> getLuckyProductList();

    //结束自定义任务的接口；
    @GET(ConstantUrl.REQUEST_USER_LUCK_AWARD_LIST)
    public Observable<BaseResultEntity<List<String>>> getUserLuckyProductList(@QueryMap HashMap<String, String> paramsMap);

    //结束自定义任务的接口；
    @GET(ConstantUrl.REQUEST_LUCK_GAME_DESCRIPTION)
    public Observable<BaseResultEntity<ResLuckwalkProductBean>> getLuckGameDesc();

    //结束自定义任务的接口；
    @GET(ConstantUrl.REQUEST_DAZHUANPAN_KAIJIANG)
    public Observable<BaseResultEntity<ResLuckwalkProductBean>> requestStartLuckyWalk(@QueryMap HashMap<String, String> paramsMap);

    //结束自定义任务的接口；
    @GET(ConstantUrl.REQUST_USER_AWARD_RECORD_LIST)
    public Observable<BaseResultEntity<List<ResLuckwalkProductBean>>> requestUserLuckyWalkAwardRecord(@QueryMap HashMap<String, String> paramsMap);

}
