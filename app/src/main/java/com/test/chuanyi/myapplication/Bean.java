package com.test.chuanyi.myapplication;

import java.io.Serializable;

/**
 * Author:  zengfeng
 * Time  :  2019/1/30 13:59
 * Des   :
 */
public class Bean implements Serializable {
//    {"bvnSurName":"zhang zhang","cc":"86","phone":"18676764937","bvnFirstName":"linli linli"}
    private String bvnSurName;
    private String bvnFirstName;
    private String cc;
    private String phone;

    public String getBvnSurName() {
        return bvnSurName;
    }

    public void setBvnSurName(String bvnSurName) {
        this.bvnSurName = bvnSurName;
    }

    public String getBvnFirstName() {
        return bvnFirstName;
    }

    public void setBvnFirstName(String bvnFirstName) {
        this.bvnFirstName = bvnFirstName;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "bvnSurName='" + bvnSurName + '\'' +
                ", bvnFirstName='" + bvnFirstName + '\'' +
                ", cc='" + cc + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
