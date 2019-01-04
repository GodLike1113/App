package com.test.chuanyi.myapplication;

/**
 * Author:  zengfeng
 * Time  :  2018/11/30 19:52
 * Des   :
 */
public abstract class Animal {
    public void eat() {
        System.out.println("Animal eat");
        init();

    }

    abstract public void init();


}

