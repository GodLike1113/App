package com.test.chuanyi.myapplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author:  zengfeng
 * Time  :  2018/11/26 16:51
 * Des   :  线程池操作工具类
 */
public class ThreadPoolUtil {
    private static ThreadPoolUtil instance;
    private ExecutorService threadPool;
    private final int threadNum = 6;

    private ThreadPoolUtil() {
        threadPool = Executors.newFixedThreadPool(threadNum);
    }


    public static synchronized ThreadPoolUtil getInstance() {
        if (instance == null) {
            synchronized (ThreadPoolUtil.class) {
                if (instance == null) {
                    instance = new ThreadPoolUtil();
                }
            }
        }
        return instance;
    }


    public void execute(Runnable runnable) {
        if (instance != null) {
            threadPool.execute(runnable);
        }
    }

    public void shutDown(){
        if(instance!=null){
            threadPool.shutdown();
        }
    }

    public void shutDownNow(){
        if(instance!=null){
            threadPool.shutdownNow();
        }
    }
}
