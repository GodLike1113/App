package com.transsnet.mylibrary;

import android.content.Context;
import android.widget.Toast;

/**
 * Author:  zengfeng
 * Time  :  2019/11/4 16:26
 * Des   :
 */
public class ToastUtils {
    public static void show(Context context,String content){
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
    }
}
