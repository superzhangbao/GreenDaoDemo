package com.xiaolan.greendaodemo;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * 将数据库文件保存在sd卡上指定位置
 */
public class GreenDaoContext extends ContextWrapper {
    private static final String TAG = "GreenDaoContext";
    private Context mContext;

    public GreenDaoContext(Context context) {
        super(context);
        mContext = context;
    }

    /**
     *获得数据库路径，如果不存在，则创建对象
     */
    @Override
    public File getDatabasePath(String dbName) {
        File oldDBFile = mContext.getDatabasePath(getString(R.string.database_name));
        Log.e(TAG, "getDatabasePath: oldDBFilePath--> " + oldDBFile.getAbsolutePath());
//        Log.e(TAG, "getDatabasePath: 旧数据库文件大小--> " + FormatFileSizeUtil.formatDataSize(oldDBFile.length()));

        String newBDDirPath = Environment.getExternalStorageDirectory().getPath()
                + File.separator + getPackageName() +
                File.separator + getString(R.string.offline_dir_name) +
                File.separator + getString(R.string.database_dir_name);
        Log.e(TAG, "getDatabasePath: newBDDirPath--> " + newBDDirPath);
        File newDBDir = new File(newBDDirPath);

        File newDBFile = new File(newDBDir, getString(R.string.database_name));
        Log.e(TAG, "getDatabasePath: newDBFilePath--> " + newDBFile.getAbsolutePath());
        if (!newDBFile.exists()) {
            if (!newDBDir.exists()) {
                boolean isNewDBDirCreated = newDBDir.mkdirs();
                Log.e(TAG, "getDatabasePath: isNewDBDirCreated--> " + isNewDBDirCreated);
            }
            if (newDBDir.exists()) {
                // 指定位置数据库文件不存在，则拷贝旧的数据库文件到指定位置
                if (!oldDBFile.exists()) {
                    Log.e(TAG, "getDatabasePath: 没有找到旧数据库文件--> " + oldDBFile.getAbsolutePath());
                    Log.e(TAG, "getDatabasePath: 将创建新的数据库文件--> " + newDBFile.getAbsolutePath());
                    // 旧的数据库文件不存在，则创建新的数据库文件
                    try {
                        boolean isCreateNewDBFileSuccess = newDBFile.createNewFile();
                        Log.e(TAG, "getDatabasePath: isCreateNewDBFileSuccess--> " + isCreateNewDBFileSuccess);
                    } catch (IOException e) {
                        Log.e(TAG, "getDatabasePath: ", e);
                    }
                } else {
                    Log.e(TAG, "getDatabasePath: 存在旧的数据库文件，拷贝已有数据库到新位置...");
                    // 旧的数据库文件存在，复制旧的数据库文件到新位置
                    copyDataBaseToSD(oldDBFile, newDBFile);
                }
            } else {
                Log.e(TAG, "getDatabasePath: 未能成功创建新的数据库文件夹--> " + newDBDir.getAbsolutePath());
            }
        } else {
//            Log.e(TAG, "getDatabasePath: 新数据库文件大小--> " + FormatFileSizeUtil.formatDataSize(newDBFile.length()));
        }

        if (newDBFile.exists()) {
            return newDBFile;
        } else {
            return super.getDatabasePath(dbName);
        }
    }

    /**

     重载该方法，用来打开SD卡上的数据库的，android 2.3及以下会调用这个方法。
     */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode,
                                               SQLiteDatabase.CursorFactory factory) {
        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), factory);
    }

    /**

     Android 4.0会调用此方法获取数据库。
     @see android.content.ContextWrapper#openOrCreateDatabase(java.lang.String, int,
             android.database.sqlite.SQLiteDatabase.CursorFactory,
             android.database.DatabaseErrorHandler)
     */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory,
                                               DatabaseErrorHandler errorHandler) {
        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), factory);
    }

    /**

     拷贝原有数据库到指定位置

     @param oldDBFile 原有的数据库文件

     @param newDBFile 新的目标数据库文件
     */
    private void copyDataBaseToSD(File oldDBFile, File newDBFile) {
        Log.e(TAG, "copyDataBaseToSD: 拷贝数据库...");
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Log.e(TAG, "copyDataBaseToSD: 请检查是存在sd卡");
            return;
        }

        FileChannel inChannel = null, outChannel = null;
        try {
            boolean isNewDBFileCreated = newDBFile.createNewFile();
            Log.e(TAG, "copyDataBaseToSD: isNewDBFileCreated--> " + isNewDBFileCreated);
            Log.e(TAG, "copyDataBaseToSD: 新数据库文件位置--> " + newDBFile.getAbsolutePath());
            inChannel = new FileInputStream(oldDBFile).getChannel();
            outChannel = new FileOutputStream(newDBFile).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (Exception e) {
            Log.e(TAG, "copy dataBase to SD error:", e);
        } finally {
            try {
                if (inChannel != null) {
                    inChannel.close();
                    inChannel = null;
                }
                if (outChannel != null) {
                    outChannel.close();
                    outChannel = null;
                }
//                Log.e(TAG, "copyDataBaseToSD: 旧数据库文件大小--> " + FormatFileSizeUtil.formatDataSize(oldDBFile.length()));
//                Log.e(TAG, "copyDataBaseToSD: 新数据库文件大小--> " + FormatFileSizeUtil.formatDataSize(newDBFile.length()));
            } catch (IOException e) {
                Log.e(TAG, "newDBFile close error:", e);
            }
        }
    }
}
