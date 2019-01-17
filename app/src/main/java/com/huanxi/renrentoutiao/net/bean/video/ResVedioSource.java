package com.huanxi.renrentoutiao.net.bean.video;

/**
 * Created by Dinosa on 2018/1/25.
 */

public class ResVedioSource {


    /**
     * iscollect : false
     * url : {"code":0,"data":{"auto_definition":"480p","enable_ssl":false,"poster_url":"http://p3.pstatp.com/origin/5f8d0002529fcdd452f2","status":10,"user_id":"toutiao","validate":"","video_duration":184.64,"video_id":"f23dbc39a66c444aa36208cdcb5f9327","video_list":{"video_1":{"backup_url_1":"http://v7.pstatp.com/c3af4b29b153f68cd9252bbf7c9a684e/5a757397/video/m/220e897a23abfb941bd81f7d43bb48d865f1154523400003221fa739782/","bitrate":577115,"codec_type":"h264","definition":"480p","file_hash":"05d395b2e763f9fb4eb45569093dc705","logo_type":"xigua_share","main_url":"http://v9-tt.ixigua.com/9ad46792a1d55dc07c07a4e01ce75d66/5a757397/video/m/220e897a23abfb941bd81f7d43bb48d865f1154523400003221fa739782/","preload_interval":60,"preload_max_step":10,"preload_min_step":5,"preload_size":327680,"size":14954123,"socket_buffer":12985020,"user_video_proxy":1,"vheight":480,"vtype":"mp4","vwidth":854}}},"message":"success","total":1}
     */

    private boolean iscollect;
    private UrlBean url;

    public boolean isIscollect() {
        return iscollect;
    }

    public void setIscollect(boolean iscollect) {
        this.iscollect = iscollect;
    }

    public UrlBean getUrl() {
        return url;
    }

    public void setUrl(UrlBean url) {
        this.url = url;
    }

    public static class UrlBean {
        /**
         * code : 0
         * data : {"auto_definition":"480p","enable_ssl":false,"poster_url":"http://p3.pstatp.com/origin/5f8d0002529fcdd452f2","status":10,"user_id":"toutiao","validate":"","video_duration":184.64,"video_id":"f23dbc39a66c444aa36208cdcb5f9327","video_list":{"video_1":{"backup_url_1":"http://v7.pstatp.com/c3af4b29b153f68cd9252bbf7c9a684e/5a757397/video/m/220e897a23abfb941bd81f7d43bb48d865f1154523400003221fa739782/","bitrate":577115,"codec_type":"h264","definition":"480p","file_hash":"05d395b2e763f9fb4eb45569093dc705","logo_type":"xigua_share","main_url":"http://v9-tt.ixigua.com/9ad46792a1d55dc07c07a4e01ce75d66/5a757397/video/m/220e897a23abfb941bd81f7d43bb48d865f1154523400003221fa739782/","preload_interval":60,"preload_max_step":10,"preload_min_step":5,"preload_size":327680,"size":14954123,"socket_buffer":12985020,"user_video_proxy":1,"vheight":480,"vtype":"mp4","vwidth":854}}}
         * message : success
         * total : 1
         */

        private int code;
        private DataBean data;
        private String message;
        private int total;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public static class DataBean {
            /**
             * auto_definition : 480p
             * enable_ssl : false
             * poster_url : http://p3.pstatp.com/origin/5f8d0002529fcdd452f2
             * status : 10
             * user_id : toutiao
             * validate :
             * video_duration : 184.64
             * video_id : f23dbc39a66c444aa36208cdcb5f9327
             * video_list : {"video_1":{"backup_url_1":"http://v7.pstatp.com/c3af4b29b153f68cd9252bbf7c9a684e/5a757397/video/m/220e897a23abfb941bd81f7d43bb48d865f1154523400003221fa739782/","bitrate":577115,"codec_type":"h264","definition":"480p","file_hash":"05d395b2e763f9fb4eb45569093dc705","logo_type":"xigua_share","main_url":"http://v9-tt.ixigua.com/9ad46792a1d55dc07c07a4e01ce75d66/5a757397/video/m/220e897a23abfb941bd81f7d43bb48d865f1154523400003221fa739782/","preload_interval":60,"preload_max_step":10,"preload_min_step":5,"preload_size":327680,"size":14954123,"socket_buffer":12985020,"user_video_proxy":1,"vheight":480,"vtype":"mp4","vwidth":854}}
             */

            private String auto_definition;
            private boolean enable_ssl;
            private String poster_url;
            private int status;
            private String user_id;
            private String validate;
            private double video_duration;
            private String video_id;
            private VideoListBean video_list;

            public String getAuto_definition() {
                return auto_definition;
            }

            public void setAuto_definition(String auto_definition) {
                this.auto_definition = auto_definition;
            }

            public boolean isEnable_ssl() {
                return enable_ssl;
            }

            public void setEnable_ssl(boolean enable_ssl) {
                this.enable_ssl = enable_ssl;
            }

            public String getPoster_url() {
                return poster_url;
            }

            public void setPoster_url(String poster_url) {
                this.poster_url = poster_url;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getValidate() {
                return validate;
            }

            public void setValidate(String validate) {
                this.validate = validate;
            }

            public double getVideo_duration() {
                return video_duration;
            }

            public void setVideo_duration(double video_duration) {
                this.video_duration = video_duration;
            }

            public String getVideo_id() {
                return video_id;
            }

            public void setVideo_id(String video_id) {
                this.video_id = video_id;
            }

            public VideoListBean getVideo_list() {
                return video_list;
            }

            public void setVideo_list(VideoListBean video_list) {
                this.video_list = video_list;
            }

            public static class VideoListBean {
                /**
                 * video_1 : {"backup_url_1":"http://v7.pstatp.com/c3af4b29b153f68cd9252bbf7c9a684e/5a757397/video/m/220e897a23abfb941bd81f7d43bb48d865f1154523400003221fa739782/","bitrate":577115,"codec_type":"h264","definition":"480p","file_hash":"05d395b2e763f9fb4eb45569093dc705","logo_type":"xigua_share","main_url":"http://v9-tt.ixigua.com/9ad46792a1d55dc07c07a4e01ce75d66/5a757397/video/m/220e897a23abfb941bd81f7d43bb48d865f1154523400003221fa739782/","preload_interval":60,"preload_max_step":10,"preload_min_step":5,"preload_size":327680,"size":14954123,"socket_buffer":12985020,"user_video_proxy":1,"vheight":480,"vtype":"mp4","vwidth":854}
                 */

                private Video1Bean video_1;

                public Video1Bean getVideo_1() {
                    return video_1;
                }

                public void setVideo_1(Video1Bean video_1) {
                    this.video_1 = video_1;
                }

                public static class Video1Bean {
                    /**
                     * backup_url_1 : http://v7.pstatp.com/c3af4b29b153f68cd9252bbf7c9a684e/5a757397/video/m/220e897a23abfb941bd81f7d43bb48d865f1154523400003221fa739782/
                     * bitrate : 577115
                     * codec_type : h264
                     * definition : 480p
                     * file_hash : 05d395b2e763f9fb4eb45569093dc705
                     * logo_type : xigua_share
                     * main_url : http://v9-tt.ixigua.com/9ad46792a1d55dc07c07a4e01ce75d66/5a757397/video/m/220e897a23abfb941bd81f7d43bb48d865f1154523400003221fa739782/
                     * preload_interval : 60
                     * preload_max_step : 10
                     * preload_min_step : 5
                     * preload_size : 327680
                     * size : 14954123
                     * socket_buffer : 12985020
                     * user_video_proxy : 1
                     * vheight : 480
                     * vtype : mp4
                     * vwidth : 854
                     */

                    private String backup_url_1;
                    private int bitrate;
                    private String codec_type;
                    private String definition;
                    private String file_hash;
                    private String logo_type;
                    private String main_url;
                    private int preload_interval;
                    private int preload_max_step;
                    private int preload_min_step;
                    private int preload_size;
                    private int size;
                    private int socket_buffer;
                    private int user_video_proxy;
                    private int vheight;
                    private String vtype;
                    private int vwidth;

                    public String getBackup_url_1() {
                        return backup_url_1;
                    }

                    public void setBackup_url_1(String backup_url_1) {
                        this.backup_url_1 = backup_url_1;
                    }

                    public int getBitrate() {
                        return bitrate;
                    }

                    public void setBitrate(int bitrate) {
                        this.bitrate = bitrate;
                    }

                    public String getCodec_type() {
                        return codec_type;
                    }

                    public void setCodec_type(String codec_type) {
                        this.codec_type = codec_type;
                    }

                    public String getDefinition() {
                        return definition;
                    }

                    public void setDefinition(String definition) {
                        this.definition = definition;
                    }

                    public String getFile_hash() {
                        return file_hash;
                    }

                    public void setFile_hash(String file_hash) {
                        this.file_hash = file_hash;
                    }

                    public String getLogo_type() {
                        return logo_type;
                    }

                    public void setLogo_type(String logo_type) {
                        this.logo_type = logo_type;
                    }

                    public String getMain_url() {
                        return main_url;
                    }

                    public void setMain_url(String main_url) {
                        this.main_url = main_url;
                    }

                    public int getPreload_interval() {
                        return preload_interval;
                    }

                    public void setPreload_interval(int preload_interval) {
                        this.preload_interval = preload_interval;
                    }

                    public int getPreload_max_step() {
                        return preload_max_step;
                    }

                    public void setPreload_max_step(int preload_max_step) {
                        this.preload_max_step = preload_max_step;
                    }

                    public int getPreload_min_step() {
                        return preload_min_step;
                    }

                    public void setPreload_min_step(int preload_min_step) {
                        this.preload_min_step = preload_min_step;
                    }

                    public int getPreload_size() {
                        return preload_size;
                    }

                    public void setPreload_size(int preload_size) {
                        this.preload_size = preload_size;
                    }

                    public int getSize() {
                        return size;
                    }

                    public void setSize(int size) {
                        this.size = size;
                    }

                    public int getSocket_buffer() {
                        return socket_buffer;
                    }

                    public void setSocket_buffer(int socket_buffer) {
                        this.socket_buffer = socket_buffer;
                    }

                    public int getUser_video_proxy() {
                        return user_video_proxy;
                    }

                    public void setUser_video_proxy(int user_video_proxy) {
                        this.user_video_proxy = user_video_proxy;
                    }

                    public int getVheight() {
                        return vheight;
                    }

                    public void setVheight(int vheight) {
                        this.vheight = vheight;
                    }

                    public String getVtype() {
                        return vtype;
                    }

                    public void setVtype(String vtype) {
                        this.vtype = vtype;
                    }

                    public int getVwidth() {
                        return vwidth;
                    }

                    public void setVwidth(int vwidth) {
                        this.vwidth = vwidth;
                    }
                }
            }
        }
    }
}
