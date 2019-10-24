package com.test.chuanyi.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Author:  zengfeng
 * Time  :  2019/10/16 17:08
 * Des   :
 */
public class MyBoradCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String tag = intent.getStringExtra("tag");
            Log.d("vivi", "收到的Tag是：" + tag);
        }
    }
}
