package com.huanxi.renrentoutiao.net.bean.browerRecord;

import com.huanxi.renrentoutiao.model.bean.NewsItemBean;

import java.util.List;

/**
 * Created by Dinosa on 2018/2/3.
 */

public class ResUserNewsBrowerRecord {

    private List<NewsRecordBean> list;

    public List<NewsRecordBean> getList() {
        return list;
    }

    public void setList(List<NewsRecordBean> list) {
        this.list = list;
    }

    public static class NewsRecordBean {
        /**
         * addtime : 2018-02-03 11:30:30
         * content : {"date":"2018-02-01 13:18","hotnews":"1","ispicnews":"0","lbimg":[{"imgheight":"275","imgwidth":"550","src":"http://06.imgmini.eastday.com/mobile/20180201/20180201_686ba4fa9d756e59fe320632c88d6dcf_mwpl_05500201.jpg"}],"miniimg":[{"imgheight":"240","imgwidth":"320","src":"http://06.imgmini.eastday.com/mobile/20180201/20180201_686ba4fa9d756e59fe320632c88d6dcf_mwpm_03200403.jpg"}],"miniimg02":[{"imgheight":"180","imgwidth":"320","src":"http://06.imgmini.eastday.com/mobile/20180201/20180201_686ba4fa9d756e59fe320632c88d6dcf_mwpm_03201609.jpg"}],"miniimg02_size":"1","miniimg_size":"1","picnums":"0","pk":"bef8009ee674ecef3c7a590faa70eb9a","praisecnt":"0","rowkey":"0019_9223370519392446243_70e775949bdd02eb","source":"手术通","sourceurl":"http://mini.eastday.com/mobile/180201131849564.html","topic":"中国人最大死亡原因找到！做好这一点，能减少80%的死亡","tramplecnt":"0","type":"jiankang","url":"http://mini.eastday.com/mobile/180201131849564.html?qid=qid02561","urlpv":"0"}
         * id : 3362
         * urlmd5 : 8041c9b4e7c94fd9ef5464dfe17fa61a
         */

        private String addtime;
        private NewsItemBean content;
        private String id;
        private String urlmd5;

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public NewsItemBean getContent() {
            return content;
        }

        public void setContent(NewsItemBean content) {
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrlmd5() {
            return urlmd5;
        }

        public void setUrlmd5(String urlmd5) {
            this.urlmd5 = urlmd5;
        }


    }
}
