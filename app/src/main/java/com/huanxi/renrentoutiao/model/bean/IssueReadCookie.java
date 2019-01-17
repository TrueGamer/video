package com.huanxi.renrentoutiao.model.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Dinosa on 2018/2/2.
 * 这两个操作是用户阅读文章的一个记录；
 */
@Entity
public class IssueReadCookie {

    @Id
    public String issue_id;

}
