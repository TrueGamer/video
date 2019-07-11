package com.huanxi.renrentoutiao.globle;

import com.zhxu.library.api.BaseApi;

/**
 * Created by Dinosa on 2018/1/12.
 * 这里是配置对应url
 */

public class ConstantUrl {


//    public static final String BASE_URL="http://118.31.4.145/html/hxtoutiao/public/api/index.php/news/";
    public static final String GET_SEARCH_ADS ="ad/ads";
    //新闻
    //public static final String NEWS_URL="top/get_news_list";
    public static final String NEWS_URL="top/get_news_list_threa";
    public static final String NEWS_TYPE_URL="top/get_new_menu";
    public static final String NEWS_DETAIL_URL="top/get_news_content";

    //视频
    public static final String VEDIO_URL="top/get_video_list";
    public static final String VEDIO_URL_NEW="top/get_video_list_threa";
    public static final String VEDIO_URL_DETAIL="top/get_video_list_four";

    public static final String VEDIO_SOURCE_DEATIL_URL="top/get_video_detail";

    public static final String VEDIO_COMMENT_LIST="top/get_comments";

    //用户登陆
    public static final String LOGIN_URL="user/login";
    //微信登陆
    public static final String WECHAT_LOGIN_URL="user/wx_login";

    public static final String GET_USE_INFO="user/getuserinfo";
    //验证码
    public static final String VALIDE_PHONE_NUMBER_CODE="user/code";
    //检查版本号；
    public static final String CHECK_VERSION="user/checkversion";
    public static final String USER_SIGN="user/userSign ";

    public static final String USER_COMMENT="new/addcommnet";
    public static final String USER_COMMENT_LIST="new/getusercommentlist";

    public static final String COMMENT_LIST="new/getcommentlist";

    public static final String USER_COLLECTION="new/collectaction";

    public static final String USER_COLLECTION_LIST="new/usercollectionlist";

    //新闻的浏览记录
    public static final String USER_BROWER_RECORD="new/getrecordlist";

    //获取金币明细；
    public static final String USER_GOLD_COIN_DETAIL_LIST="wallet/integrallist";
    //余额明细的
    public static final String USER_MONEY_DETAIL_LIST="wallet/moneylist";
    //提现操作；
    public static final String USER_WITHDRAWALS_MONEY="wallet/withdrawals";
    //阅读开始
    public static final String USER_WITHDRAWAL_RECORDS="wallet/withdrawalslist";
    //阅读结束
    public static final String END_READ_ISSURE="new/readmoneyend";
    //开始阅读文章
    public static final String START_READ_ISSURE="new/readmoneystart";

    // 开始读取广告
    public static final String START_READ_AD = "new/checkTaskStart";
    // 获取阅读广告的金币
    public static final String END_READ_AD = "new/checkTaskEnd";

    //修改用户信息
    public static final String ALTER_USER_INFO="user/setuserinfo";
    //绑定手机号
    public static final String BIND_USER_PHONE ="user/bindphone";
    //获取签到信息
    public static final String GET_SIGN_INFO ="user/getSign";
    //获取任务的接口；
    public static final String GET_ALL_TASK ="task/tasklist";
    //获取拆红包的倒计时；
    public static final String GET_RED_PACKET_TIME ="new/redlasttime";
    //获取拆红包的红包
    public static final String GET_RED_PACKET ="new/addredbag";
    //获取搜索的关键字
    public static final String GET_SEARCH_KEY_LIST ="new/hottext";
    //填写邀请码
    public static final String ADD_INVITE_CODE ="new/addinvent";
    //提交帮助与反馈的接口
    public static final String HELP_AND_CALL_BACK ="user/addfeedback";
    //获取客服信息
    public static final String GET_KEFU_INFO ="user/getvipinfo";
    //获取客户金币兑换以及金钱信息
    public static final String GET_WALLET_TOTAL ="wallet/wallettotal";
    //获取常见的问题
    public static final String GET_QUESTION_LIST ="user/helper";
    //清理浏览记录
    public static final String CLEAR_RECORD ="new/deleterecord";
    //获取红包
    @Deprecated
    public static final String REQUEST_SHARE ="new/shareaction";
    //获取
    public static final String SHARE_SUCCESS ="new/sharesuccess";

    //获取闪屏所有的广告；
    public static final String SPLASH_AD_INFOS ="ad/adlist_android";

    //这里是执行点赞的逻辑
    public static final String REQUEST_DO_LIKE ="new/addliketocomment";

    //这里是执行获取我的好友的逻辑
    public static final String REQUEST_FRIENDS ="Wallet/integralPeoplelist";
    //领取邀请码接口；
    public static final String REQUEST_INVITE_AWARD ="new/getinvent";

    //这里请求客服的接口；
    public static final String REQUEST_CONTACT_NUMBER ="user/getvipintolist";

    //这里是我们的好友的最新的列表的接口；
    public static final String REQUEST_FRIENDS_NEW ="wallet/integralPeopletotal";

    //新闻和视频的接口
    public static final String REQUEST_NEWS_AND_VIDEO ="top/get_news_list_four";

    public static final String REQUEST_NEW_ALL_TASK ="Task/tasklist_one";

    public static final String REQUEST_INVITE_FRIEND_DESC ="ad/starttext";

    //这里我们要要做的
    public static final String REQUEST_INVITE_FRIEND_CONTENT ="Share/shareFriend";

    //文章的分享的内容；
    public static final String REQUEST_SHARE_ARTCLE_AND_VIDEO ="Share/sharearticle";

    //高佣任务的接口：
    public static final String REQUEST_ADVANCE_TASK_AWARD ="Task/taskBytime";

    //联系客服接口
    public static final String REQEUST_NEW_KEFU_CONTACT ="user/getvipintolist_new";

    //这里直接登陆逻辑
    public static final String WECHAT_LOGIN_NEW ="user/wx_loginAndRe";

    //这里直接绑定手机号；
    public static final String ONLY_BIND_PHONE ="user/onlybindphone";

    //自定义任务开始；
    public static final String START_CUSTOM_TASK ="task/taskBytimestart";
    //自定义任务结束；
    public static final String END_CUSTOM_TASK ="task/taskBytimeend";


    //大转盘开奖信息；
    public static final String REQUEST_DAZHUANPAN_KAIJIANG ="Dazhuanpan/kaijiang";
    //大转盘的领取记录
    public static final String REQUEST_USER_LUCK_AWARD_LIST ="Dazhuanpan/lingqulist";
    //大转盘商品的接口；
    public static final String REQUEST_PRODUCT_LIST ="Dazhuanpan/productlist/";
    //游戏说明；
    public static final String REQUEST_LUCK_GAME_DESCRIPTION ="Dazhuanpan/shuoming";
    //这里表示用户的中奖记录
    public static final String REQUST_USER_AWARD_RECORD_LIST ="Dazhuanpan/zhongjianglist";

    public static final String IS_SHOW ="ads_show";
    //收徒网页地址
    public static final String JKD_WEB_URL="http://game.appshow.cn/html/h5/jkd/weixin20/shoutu.html?userId=";

    // 头条号列表 http://game.appshow.cn/public/api/index.php/news/
    // http://118.190.204.3:8180/apps/app/news/
    public static final String MediaList_URL = "http://118.190.204.3:8180/apps/app/news/newsList?start=%d&pageSize=20&userId=%s";

    // 头条号详情
//    public static final String MediaDetailURL = "https://is.snssdk.com/user/profile/homepage/v3/json/?media_id=%sto_html=0&source=article_top_author&refer=all&aid=13";
    // 头条号详情
    public static final String MediaDetailURL = "http://118.190.204.3:8180/apps/app/news/userNewsList?username=%s&userId=%s&start=0&pageSize=10&type=1";

    // 文章详情
    public static final String MediaArticalDetailURL = "http://118.190.204.3:8180/apps/app/news/view?id=%s&userId=%s";

    //小视频开始播放
    public static final String START_Play_ISSURE="top/checkTaskVideoHongbaoMini";
    //小视频播放结束
    public static final String END_Play_ISSURE="top/taskVideoHongbaoMini";

}
