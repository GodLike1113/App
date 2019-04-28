package com.test.chuanyi.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Author:  zengfeng
 * Time  :  2019/1/22 19:12
 * Des   :
 */
public class TestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_layout);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;//屏幕高度像素
        int screenWidth = metrics.widthPixels;//屏幕宽度像素
        //density = densityDpi / 160
        float density = metrics.density;// "屏幕密度"（0.75 / 1.0 / 1.5）
        int densityDpi = metrics.densityDpi;// 屏幕密度dpi（120 / 160 / 240）每一英寸的屏幕所包含的像素数.值越高的设备，其屏幕显示画面的效果也就越精细
        // 屏幕宽度算法:屏幕宽度（像素）/"屏幕密度"   px = dp * (dpi / 160)
        int height = (int) (screenHeight / density);//屏幕高度dp
        int width = (int) (screenWidth/density);

        Log.d("vivi","宽度dp:"+width+",高度dp:"+height);
    }
}
