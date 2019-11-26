package com.test.chuanyi.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * Author:  zengfeng
 * Time  :  2019/11/26 11:09
 * Des   : https://www.jianshu.com/p/967d402d411d
 * https://blog.csdn.net/u010227042/article/details/79808414
 */
public class GreenDaoActivity extends Activity {
    DbController mDbController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.greendao_layout);
        initGreenDao();
    }


    private void initGreenDao() {
        mDbController = DbController.getInstance(GreenDaoActivity.this);
    }

    public void insertClick(View v) {
        ThreadPoolUtil.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                LogUtils.d("线程名:"+Thread.currentThread().getName()+",开始插入时间："+ System.currentTimeMillis());
                for (int i = 0; i < 100000; i++) {
                    long id = mDbController.insert(new Person(null, "" + i, "张三丰", 18));
                    LogUtils.e("第"+id+"插入结束");
                }
                LogUtils.d("线程名:"+Thread.currentThread().getName()+",结束插入时间："+ System.currentTimeMillis());
            }
        });
    }

    public void deleteClick(View v) {
        mDbController.delete("甄姬");
    }

    public void updateClick(View v) {
        mDbController.insertOrReplace(new Person(null, "0701223", "杰update1", 201));
    }

    public void queryClick(View v) {
//        mDbController.querySql();
        mDbController.queryThread();
//        for (Person person : specialList) {
//            LogUtils.d("special :name:"+person.getName());
//        }
//        List<Person> list = mDbController.searchAll();
        Log.d("vivi","从Feature1.0分支");
    }


}
