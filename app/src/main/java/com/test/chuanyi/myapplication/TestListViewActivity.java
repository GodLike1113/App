package com.test.chuanyi.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:  zengfeng
 * Time  :  2019/7/4 16:04
 * Des   :
 */
public class TestListViewActivity extends Activity implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private ListViewAdapter mAdapter;
    List<String> srcList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_lv_layout);

        initView();
    }

    private void initView() {
        findViewById(R.id.refresh_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        srcList.add("String  hahha");
        mListView = findViewById(R.id.lv);
        mListView.setOnItemClickListener(this);
        mAdapter = new ListViewAdapter(this, new ArrayList<String>());
        mListView.setAdapter(mAdapter);
        mAdapter.setData(srcList);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        mListView.setOnItemClickListener(null);

        ThreadPoolUtil.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                double random = Math.random();
//                srcList.clear();
                SystemClock.sleep(2000);
                srcList.clear();
                int var = (int) (random * 100);
                if (var == 0) {
                    var = 3;
                }
                for (int i = 0; i < var; i++) {
                    srcList.add("String  " + i);
                }
                handler.sendMessage(Message.obtain());
            }
        });

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mAdapter.notifyDataSetChanged();
        }
    };


}
