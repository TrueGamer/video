package com.huanxi.renrentoutiao.net.bean;

/**
 * Created by Dinosa on 2018/4/12.
 */

public class ResInviteFriendDesc {


    /**
     * inventtext : 您可通过微信、朋友圈、QQ、面对面邀请等方式邀请好友加入云赚视频。您可同步获得该好友完成新手任务所得金币，共2500金币。另外，您还可以持续获得徒弟收益的100%。
     简单阅读，轻松赚零花钱！快快行动起来，邀请更多好友加入进来！
     * showTextForLogin : 1
     * textforlogin : {"id":34,"title":"121212","cont":null,"imgurl":null,"url":"http://baidu.com","downurl":null,"size":null,"packename":null,"appname":null,"type":"10","code":null,"phonetype":null}
     * showTextForLoginDetail : 1
     * textforloginDetail : {"id":38,"title":"sdfsdf","cont":"wewewewewe","imgurl":null,"url":"http://baidu.com","downurl":null,"size":null,"packename":null,"appname":null,"type":"11","code":null,"phonetype":null}
     */

    private String inventtext;
    private String showTextForLogin;
    private TextforloginBean textforlogin;
    private String showTextForLoginDetail;
    private TextforloginDetailBean textforloginDetail;

    public String getInventtext() {
        return inventtext;
    }

    public void setInventtext(String inventtext) {
        this.inventtext = inventtext;
    }

    public String getShowTextForLogin() {
        return showTextForLogin;
    }

    public void setShowTextForLogin(String showTextForLogin) {
        this.showTextForLogin = showTextForLogin;
    }

    public String getShowTextForLoginDetail() {
        return showTextForLoginDetail;
    }

    public void setShowTextForLoginDetail(String showTextForLoginDetail) {
        this.showTextForLoginDetail = showTextForLoginDetail;
    }

    public TextforloginBean getTextforlogin() {
        return textforlogin;
    }

    public void setTextforlogin(TextforloginBean textforlogin) {
        this.textforlogin = textforlogin;
    }


    public TextforloginDetailBean getTextforloginDetail() {
        return textforloginDetail;
    }

    public void setTextforloginDetail(TextforloginDetailBean textforloginDetail) {
        this.textforloginDetail = textforloginDetail;
    }

    public static class TextforloginBean {
        /**
         * id : 34
         * title : 121212
         * cont : null
         * imgurl : null
         * url : http://baidu.com
         * downurl : null
         * size : null
         * packename : null
         * appname : null
         * type : 10
         * code : null
         * phonetype : null
         */

        private int id;
        private String title;
        private String cont;
        private Object imgurl;
        private String url;
        private String downurl;
        private Long size;
        private String packename;
        private String appname;
        private String type;
        private String code;
        private String phonetype;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCont() {
            return cont;
        }

        public void setCont(String cont) {
            this.cont = cont;
        }

        public Object getImgurl() {
            return imgurl;
        }

        public void setImgurl(Object imgurl) {
            this.imgurl = imgurl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDownurl() {
            return downurl;
        }

        public void setDownurl(String downurl) {
            this.downurl = downurl;
        }

        public Long getSize() {
            return size;
        }

        public void setSize(Long size) {
            this.size = size;
        }

        public String getPackename() {
            return packename;
        }

        public void setPackename(String packename) {
            this.packename = packename;
        }

        public String getAppname() {
            return appname;
        }

        public void setAppname(String appname) {
            this.appname = appname;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getPhonetype() {
            return phonetype;
        }

        public void setPhonetype(String phonetype) {
            this.phonetype = phonetype;
        }
    }

    public static class TextforloginDetailBean {
        /**
         * id : 38
         * title : sdfsdf
         * cont : wewewewewe
         * imgurl : null
         * url : http://baidu.com
         * downurl : null
         * size : null
         * packename : null
         * appname : null
         * type : 11
         * code : null
         * phonetype : null
         */

        private int id;
        private String title;
        private String cont;
        private String imgurl;
        private String url;
        private String downurl;
        private String size;
        private String packename;
        private String appname;
        private String type;
        private String code;
        private String phonetype;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCont() {
            return cont;
        }

        public void setCont(String cont) {
            this.cont = cont;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDownurl() {
            return downurl;
        }

        public void setDownurl(String downurl) {
            this.downurl = downurl;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getPackename() {
            return packename;
        }

        public void setPackename(String packename) {
            this.packename = packename;
        }

        public String getAppname() {
            return appname;
        }

        public void setAppname(String appname) {
            this.appname = appname;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getPhonetype() {
            return phonetype;
        }

        public void setPhonetype(String phonetype) {
            this.phonetype = phonetype;
        }
    }
}
