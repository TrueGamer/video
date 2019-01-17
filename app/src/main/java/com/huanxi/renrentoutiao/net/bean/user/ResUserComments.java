package com.huanxi.renrentoutiao.net.bean.user;

import com.google.gson.JsonObject;

import java.util.List;

/**
 * Created by Dinosa on 2018/2/3.
 */

public class ResUserComments {

    //这里是将评论放在一起；

    private List<UserCommentBean> list;

    public List<UserCommentBean> getList() {
        return list;
    }

    public void setList(List<UserCommentBean> list) {
        this.list = list;
    }

    public static class UserCommentBean {
        /**
         * addtime : 2018-02-03 12:56:52
         * avatar : http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTI7rN1s2LpobRt3l4qTZJJ1Y1ko7YROqmjtF8A06de99Thjb5XJ6sJVYqfSAQ3ARKlqyb1uvmQ9Bw/132
         * content : 解结局
         * id : 40
         * newcontent : {"date":"2018-02-02 08:45","hotnews":"1","ispicnews":"0","lbimg":[{"imgheight":"199","imgwidth":"399","src":"http://00.imgmini.eastday.com/mobile/20180202/20180202084542_d180411fd7a6bdeb6ea9b17854ef0bdb_1_mwpl_05500201.jpg"}],"miniimg":[{"imgheight":"240","imgwidth":"320","src":"http://00.imgmini.eastday.com/mobile/20180202/20180202084542_d180411fd7a6bdeb6ea9b17854ef0bdb_1_mwpm_03200403.jpg"}],"miniimg02":[{"imgheight":"180","imgwidth":"320","src":"http://00.imgmini.eastday.com/mobile/20180202/20180202084542_d180411fd7a6bdeb6ea9b17854ef0bdb_1_mwpm_03201609.jpg"}],"miniimg02_size":"1","miniimg_size":"1","picnums":"0","pk":"5c413e5d12209e73b871e904a023d219","praisecnt":"0","rowkey":"0008_9223370519322432957_d7a6bdeb6ea9b178","source":"人民网","sourceurl":"http://mini.eastday.com/mobile/180202084542850.html","topic":"人类\u201c走出非洲\u201d时间或更早","tramplecnt":"0","type":"keji","url":"http://mini.eastday.com/mobile/180202084542850.html?qid=qid02561","urlpv":"0"}
         * nickname : Goodboy
         * praisenum : 0
         */

        private String addtime;
        private String avatar;
        private String content;
        private String id;
        private JsonObject newcontent;
        private String nickname;
        private String praisenum;

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public JsonObject getNewcontent() {
            return newcontent;
        }

        public void setNewcontent(JsonObject newcontent) {
            this.newcontent = newcontent;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPraisenum() {
            return praisenum;
        }

        public void setPraisenum(String praisenum) {
            this.praisenum = praisenum;
        }

    }

}
