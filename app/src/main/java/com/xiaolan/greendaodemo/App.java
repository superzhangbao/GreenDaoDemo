package com.xiaolan.greendaodemo;

import android.app.Application;

import com.xiaolan.greendaodemo.bean.DaoMaster;
import com.xiaolan.greendaodemo.bean.DaoSession;

import org.greenrobot.greendao.database.Database;

public class App extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        MigrationHelper.DEBUG = true;
        MyDaoMaster helper = new MyDaoMaster(this, "zb.db",null);
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
