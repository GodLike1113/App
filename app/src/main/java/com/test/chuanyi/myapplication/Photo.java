package com.test.chuanyi.myapplication;

import java.io.Serializable;

/**
 * Author:  zengfeng
 * Time  :  2018/11/26 18:19
 * Des   :  设备照片信息Bean
 */
public class Photo implements Serializable {
    private String photo_name;

    private String dir;

    private Exif exif;

    public void setPhoto_name(String photo_name) {
        this.photo_name = photo_name;
    }

    public String getPhoto_name() {
        return this.photo_name;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getDir() {
        return this.dir;
    }

    public void setExif(Exif exif) {
        this.exif = exif;
    }

    public Exif getExif() {
        return this.exif;
    }

}
