package com.test.chuanyi.myapplication;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Author:  zengfeng
 * Time  :  2019/11/26 10:13
 * Des   :  https://www.jianshu.com/p/967d402d411d
 *          https://blog.csdn.net/u010227042/article/details/79808414
 */
@Entity
public class Person implements Serializable {
    public final  static long serialVersionUID =100;
    @Id(autoincrement = true)
    private Long id;

    @Index(unique = true)
    private String phoneNum;

    private String name;

    private int age;

    @Generated(hash = 1234374670)
    public Person(Long id, String phoneNum, String name, int age) {
        this.id = id;
        this.phoneNum = phoneNum;
        this.name = name;
        this.age = age;
    }

    @Generated(hash = 1024547259)
    public Person() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    

}
