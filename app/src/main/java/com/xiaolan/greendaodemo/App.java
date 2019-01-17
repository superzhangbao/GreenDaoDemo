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


//        DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(this,getResources().getString(R.string.database_name),null);
//        Database db = helper.getWritableDb();

        MyDaoMaster helper = new MyDaoMaster(this, getResources().getString(R.string.database_name),null);
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
