package com.huanxi.renrentoutiao.utils;

import com.zhxu.library.download.DaoMaster;
import com.zhxu.library.download.DaoSession;
import com.zhxu.library.http.cookie.CookieResulte;
import com.zhxu.library.http.cookie.CookieResulteDao;
import com.zhxu.library.utils.CookieDbUtil;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by Dinosa on 2018/2/2.
 * 这里是数据库的帮助类；
 */

public class DBUtils {

    public static DBUtils mDBUtils;

    private final CookieDbUtil mInstance;

    public DBUtils() {
        mInstance = CookieDbUtil.getInstance();
    }

    /**
     * 获取单例
     * @return
     */
    public static DBUtils getInstance() {
        if (mDBUtils == null) {
            synchronized (CookieDbUtil.class) {
                if (mDBUtils == null) {
                    mDBUtils = new DBUtils();
                }
            }
        }
        return mDBUtils;
    }

    /**
     *插入一条阅读过的记录；
     */
    public void insertIssueReadCookie(){

    }


    ///======这里我们用来存储这里阅读记录===============
    public CookieResulte queryCookieBy(String  url) {
        DaoMaster daoMaster = new DaoMaster(mInstance.getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CookieResulteDao downInfoDao = daoSession.getCookieResulteDao();
        QueryBuilder<CookieResulte> qb = downInfoDao.queryBuilder();
        qb.where(CookieResulteDao.Properties.Url.eq(url));
        List<CookieResulte> list = qb.list();
        if(list.isEmpty()){
            return null;
        }else{
            return list.get(0);
        }
    }


    public void saveCookie(CookieResulte info){
        DaoMaster daoMaster = new DaoMaster(mInstance.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CookieResulteDao downInfoDao = daoSession.getCookieResulteDao();
        downInfoDao.insert(info);
    }

}
