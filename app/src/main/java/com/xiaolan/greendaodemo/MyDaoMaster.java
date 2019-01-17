package com.xiaolan.greendaodemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.xiaolan.greendaodemo.bean.DaoMaster;
import com.xiaolan.greendaodemo.bean.DaoMaster.OpenHelper;
import com.xiaolan.greendaodemo.bean.ZbDao;

import org.greenrobot.greendao.database.Database;


public class MyDaoMaster extends OpenHelper {

    private static final String TAG = "MyDaoMaster";
    public MyDaoMaster(Context context, String name) {
        super(context, name);
    }

    public MyDaoMaster(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db,ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db,ifExists);
            }
        },ZbDao.class);
    }
}
