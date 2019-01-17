package com.huanxi.renrentoutiao.net.bean;

import java.util.List;

/**
 * Created by Dinosa on 2018/2/26.
 * 推啊自定义的广告；
 */

public class ResTaCustomAdBean {


    /**
     * request_id : A98FD03187D15A08B523CFFFC6A3B459816015196268118839156
     * error_code : 0
     * adslot_id : 8160
     * activity_id : 2000005918
     * ad_icon : https://yun.tuia.cn/upload/8ujO01480924005776.png
     * ad_icon_visible : false
     * ad_close : https://yun.tuia.cn/upload/4UyUC1480924005775.png
     * ad_close_visible : false
     * ad_title : 抢100000红包
     * activity_title : 抢100000红包
     * description : 抢100000红包
     * img_url : https://yun.tuia.cn/mami-media/img/12qzzjdb2j.jpg
     * img_height : 250
     * img_width : 500
     * ad_content : null
     * ad_type : 7
     * click_url : http://activity.guavapie.cn/activity/index?id=5918&slotId=8160&login=normal&appKey=3ThfjmMjDSM7z5gk5QBx3nbho1Dy&deviceId=A98FD03187D15A08B523CFFFC6A3B459&dsm=1.8160.0.0&tenter=SOW&tck_rid_6c8=0a0a1e5fjdbgfnqy-258789731&tck_loc_c5d=tactivity-5918&dcm=401.8160.0.22194&
     * expire : 0
     * wdata_token : null
     * server_time : null
     * material_id : 22194
     * sck_Id : 2420
     * dcm : 401.8160.0.22194
     * material_list : [{"ms_item_id":261,"item_type":1,"image_url":"//yun.tuia.cn/mami-media/img/12qzzjdb2j.jpg","image_width":500,"image_height":250,"text":null}]
     * activity_way : 4
     * material_way : 1
     * data1 : 0a0a1e5fjdbgfnqy-258789731
     * data2 : {"clickUrl":"/activity/index?id=5918&slotId=8160&login=normal&appKey=3ThfjmMjDSM7z5gk5QBx3nbho1Dy&deviceId=A98FD03187D15A08B523CFFFC6A3B459&dsm=1.8160.0.0","materialId":22194,"sckId":2420}
     * rid : 0a0a1e5fjdbgfnqy-258789731
     * source : 1
     * app_id : 43051
     * device_id : A98FD03187D15A08B523CFFFC6A3B459
     * outputSource : 0
     * host : null
     */

    private String request_id;
    private String error_code;
    private int adslot_id;
    private String activity_id;
    private String ad_icon;
    private boolean ad_icon_visible;
    private String ad_close;
    private boolean ad_close_visible;
    private String ad_title;
    private String activity_title;
    private String description;
    private String img_url;
    private int img_height;
    private int img_width;
    private Object ad_content;
    private int ad_type;
    private String click_url;
    private int expire;
    private Object wdata_token;
    private Object server_time;
    private int material_id;
    private int sck_Id;
    private String dcm;
    private int activity_way;
    private int material_way;
    private String data1;
    private String data2;
    private String rid;
    private int source;
    private int app_id;
    private String device_id;
    private int outputSource;
    private Object host;
    private List<MaterialListBean> material_list;

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public int getAdslot_id() {
        return adslot_id;
    }

    public void setAdslot_id(int adslot_id) {
        this.adslot_id = adslot_id;
    }

    public String getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }

    public String getAd_icon() {
        return ad_icon;
    }

    public void setAd_icon(String ad_icon) {
        this.ad_icon = ad_icon;
    }

    public boolean isAd_icon_visible() {
        return ad_icon_visible;
    }

    public void setAd_icon_visible(boolean ad_icon_visible) {
        this.ad_icon_visible = ad_icon_visible;
    }

    public String getAd_close() {
        return ad_close;
    }

    public void setAd_close(String ad_close) {
        this.ad_close = ad_close;
    }

    public boolean isAd_close_visible() {
        return ad_close_visible;
    }

    public void setAd_close_visible(boolean ad_close_visible) {
        this.ad_close_visible = ad_close_visible;
    }

    public String getAd_title() {
        return ad_title;
    }

    public void setAd_title(String ad_title) {
        this.ad_title = ad_title;
    }

    public String getActivity_title() {
        return activity_title;
    }

    public void setActivity_title(String activity_title) {
        this.activity_title = activity_title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getImg_height() {
        return img_height;
    }

    public void setImg_height(int img_height) {
        this.img_height = img_height;
    }

    public int getImg_width() {
        return img_width;
    }

    public void setImg_width(int img_width) {
        this.img_width = img_width;
    }

    public Object getAd_content() {
        return ad_content;
    }

    public void setAd_content(Object ad_content) {
        this.ad_content = ad_content;
    }

    public int getAd_type() {
        return ad_type;
    }

    public void setAd_type(int ad_type) {
        this.ad_type = ad_type;
    }

    public String getClick_url() {
        return click_url;
    }

    public void setClick_url(String click_url) {
        this.click_url = click_url;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public Object getWdata_token() {
        return wdata_token;
    }

    public void setWdata_token(Object wdata_token) {
        this.wdata_token = wdata_token;
    }

    public Object getServer_time() {
        return server_time;
    }

    public void setServer_time(Object server_time) {
        this.server_time = server_time;
    }

    public int getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(int material_id) {
        this.material_id = material_id;
    }

    public int getSck_Id() {
        return sck_Id;
    }

    public void setSck_Id(int sck_Id) {
        this.sck_Id = sck_Id;
    }

    public String getDcm() {
        return dcm;
    }

    public void setDcm(String dcm) {
        this.dcm = dcm;
    }

    public int getActivity_way() {
        return activity_way;
    }

    public void setActivity_way(int activity_way) {
        this.activity_way = activity_way;
    }

    public int getMaterial_way() {
        return material_way;
    }

    public void setMaterial_way(int material_way) {
        this.material_way = material_way;
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getApp_id() {
        return app_id;
    }

    public void setApp_id(int app_id) {
        this.app_id = app_id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public int getOutputSource() {
        return outputSource;
    }

    public void setOutputSource(int outputSource) {
        this.outputSource = outputSource;
    }

    public Object getHost() {
        return host;
    }

    public void setHost(Object host) {
        this.host = host;
    }

    public List<MaterialListBean> getMaterial_list() {
        return material_list;
    }

    public void setMaterial_list(List<MaterialListBean> material_list) {
        this.material_list = material_list;
    }

    public static class MaterialListBean {
        /**
         * ms_item_id : 261
         * item_type : 1
         * image_url : //yun.tuia.cn/mami-media/img/12qzzjdb2j.jpg
         * image_width : 500
         * image_height : 250
         * text : null
         */

        private int ms_item_id;
        private int item_type;
        private String image_url;
        private int image_width;
        private int image_height;
        private Object text;

        public int getMs_item_id() {
            return ms_item_id;
        }

        public void setMs_item_id(int ms_item_id) {
            this.ms_item_id = ms_item_id;
        }

        public int getItem_type() {
            return item_type;
        }

        public void setItem_type(int item_type) {
            this.item_type = item_type;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public int getImage_width() {
            return image_width;
        }

        public void setImage_width(int image_width) {
            this.image_width = image_width;
        }

        public int getImage_height() {
            return image_height;
        }

        public void setImage_height(int image_height) {
            this.image_height = image_height;
        }

        public Object getText() {
            return text;
        }

        public void setText(Object text) {
            this.text = text;
        }
    }
}
