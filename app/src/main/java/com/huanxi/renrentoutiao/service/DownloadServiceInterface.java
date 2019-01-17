package com.huanxi.renrentoutiao.service;

import java.io.Serializable;

/**
 * Created by Dinosa on 2018/3/28.
 *
 */

public interface DownloadServiceInterface extends Serializable{
    //这里表示启动下载service的一个接口

    public String getPackageName();
    public String getAppName();
    public Long getAppSize();
    public String getDownloadUrl();


}
