package com.test.chuanyi.myapplication;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/02
 *     desc  : utils about phone
 * </pre>
 */
public final class PhoneUtils {

    /**
     * 是否存在SD卡
     * @return
     */
    public static boolean existSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }


    /**
     * 获取SD剩余大小，单位MB
     * @return
     */
    public static long getSDFreeSize(){
        File path = Environment.getExternalStorageDirectory();
        try {
            StatFs sf = new StatFs(path.getPath());
            long blockSize = sf.getBlockSize();
            long freeBlocks = sf.getAvailableBlocks();
            //return freeBlocks * blockSize;  //单位Byte
            //return (freeBlocks * blockSize)/1024;   //单位KB
            return (freeBlocks * blockSize)/1024 /1024; //单位MB
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 返回SD卡的总容量
     * @return
     */
    public static long getSDAllSize(){
        try {
            File path = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(path.getPath());
            long blockSize = sf.getBlockSize();
            long allBlocks = sf.getBlockCount();
            //return allBlocks * blockSize; //单位Byte
            //return (allBlocks * blockSize)/1024; //单位KB
            return (allBlocks * blockSize)/1024/1024; //单位MB
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
