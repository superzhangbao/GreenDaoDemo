package com.xiaolan.greendaodemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.xiaolan.greendaodemo.bean.DaoSession;
import com.xiaolan.greendaodemo.bean.Zb;
import com.xiaolan.greendaodemo.bean.ZbDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.tv);
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        final ZbDao zbDao = daoSession.getZbDao();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    Zb zb = new Zb();
                    zb.setName("zhangbao" + i);
                    zb.setAge(i);
                    zb.setNickName("小宝"+i);
                    zbDao.insert(zb);
                }
                List<Zb> list = zbDao.loadAll();
                if (list.size() == 0) {
                    Log.e("tag","数据库为空");
                }else {
                    for (int i = 0; i < list.size(); i++) {
                        Log.e("tag",list.get(i).toString());
                    }
                }
            }
        }).start();
    }
}
