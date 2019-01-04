package com.test.chuanyi.myapplication;

/**
 * Author:  zengfeng
 * Time  :  2018/11/30 20:05
 * Des   :
 */
public class Dog extends Animal {
    @Override
    public void init() {
        System.out.println("dog init");
    }

    private void methodInDog(){
        System.out.println("恭喜你！成功获得 Dog类中的方法");
    }
}
