package com.test.chuanyi.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * Author:  zengfeng
 * Time  :  2019/4/3 15:17
 * Des   :
 */
public class A extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_layout);
        findViewById(R.id.a_tv).setOnClickListener(this);
        Log.d("vivi","A onCreate");
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this,B.class));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("vivi","A onNewIntent");
    }
}
