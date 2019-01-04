package com.test.chuanyi.myapplication;

import java.io.Serializable;

/**
 * Author:  zengfeng
 * Time  :  2018/11/26 18:18
 * Des   :  设备照片Exif的信息
 */
public class Exif implements Serializable {
    private String compression;

    private String date_time;

    private String exif_tag;

    private String file_size;

    private String gps_latitude;

    private String gps_latitude_ref;

    private String gps_longitude;

    private String gps_longitude_ref;

    private String gps_map_datum;

    private String gps_tag;

    private String gps_version_id;

    private String image_height;

    private String image_width;

    private String jpeg_interchange_format;

    private String jpeg_interchange_format_length;

    private String orientation;

    private String resolution_unit;

    private String software;

    private String x_resolution;

    private String y_resolution;

    private long modify_time; //额外添加字段，方便排序取最近100张照片

    public long getModify_time() {
        return modify_time;
    }

    public void setModify_time(long modify_time) {
        this.modify_time = modify_time;
    }

    public void setCompression(String compression) {
        this.compression = compression;
    }

    public String getCompression() {
        return this.compression;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getDate_time() {
        return this.date_time;
    }

    public void setExif_tag(String exif_tag) {
        this.exif_tag = exif_tag;
    }

    public String getExif_tag() {
        return this.exif_tag;
    }

    public void setFile_size(String file_size) {
        this.file_size = file_size;
    }

    public String getFile_size() {
        return this.file_size;
    }

    public void setGps_latitude(String gps_latitude) {
        this.gps_latitude = gps_latitude;
    }

    public String getGps_latitude() {
        return this.gps_latitude;
    }

    public void setGps_latitude_ref(String gps_latitude_ref) {
        this.gps_latitude_ref = gps_latitude_ref;
    }

    public String getGps_latitude_ref() {
        return this.gps_latitude_ref;
    }

    public void setGps_longitude(String gps_longitude) {
        this.gps_longitude = gps_longitude;
    }

    public String getGps_longitude() {
        return this.gps_longitude;
    }

    public void setGps_longitude_ref(String gps_longitude_ref) {
        this.gps_longitude_ref = gps_longitude_ref;
    }

    public String getGps_longitude_ref() {
        return this.gps_longitude_ref;
    }

    public void setGps_map_datum(String gps_map_datum) {
        this.gps_map_datum = gps_map_datum;
    }

    public String getGps_map_datum() {
        return this.gps_map_datum;
    }

    public void setGps_tag(String gps_tag) {
        this.gps_tag = gps_tag;
    }

    public String getGps_tag() {
        return this.gps_tag;
    }

    public void setGps_version_id(String gps_version_id) {
        this.gps_version_id = gps_version_id;
    }

    public String getGps_version_id() {
        return this.gps_version_id;
    }

    public void setImage_height(String image_height) {
        this.image_height = image_height;
    }

    public String getImage_height() {
        return this.image_height;
    }

    public void setImage_width(String image_width) {
        this.image_width = image_width;
    }

    public String getImage_width() {
        return this.image_width;
    }

    public void setJpeg_interchange_format(String jpeg_interchange_format) {
        this.jpeg_interchange_format = jpeg_interchange_format;
    }

    public String getJpeg_interchange_format() {
        return this.jpeg_interchange_format;
    }

    public void setJpeg_interchange_format_length(String jpeg_interchange_format_length) {
        this.jpeg_interchange_format_length = jpeg_interchange_format_length;
    }

    public String getJpeg_interchange_format_length() {
        return this.jpeg_interchange_format_length;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getOrientation() {
        return this.orientation;
    }

    public void setResolution_unit(String resolution_unit) {
        this.resolution_unit = resolution_unit;
    }

    public String getResolution_unit() {
        return this.resolution_unit;
    }

    public void setSoftware(String software) {
        this.software = software;
    }

    public String getSoftware() {
        return this.software;
    }

    public void setX_resolution(String x_resolution) {
        this.x_resolution = x_resolution;
    }

    public String getX_resolution() {
        return this.x_resolution;
    }

    public void setY_resolution(String y_resolution) {
        this.y_resolution = y_resolution;
    }

    public String getY_resolution() {
        return this.y_resolution;
    }

    @Override
    public String toString() {
        return "Exif{" +
                "compression='" + compression + '\'' +
                ", date_time='" + date_time + '\'' +
                ", exif_tag='" + exif_tag + '\'' +
                ", file_size='" + file_size + '\'' +
                ", gps_latitude='" + gps_latitude + '\'' +
                ", gps_latitude_ref='" + gps_latitude_ref + '\'' +
                ", gps_longitude='" + gps_longitude + '\'' +
                ", gps_longitude_ref='" + gps_longitude_ref + '\'' +
                ", gps_map_datum='" + gps_map_datum + '\'' +
                ", gps_tag='" + gps_tag + '\'' +
                ", gps_version_id='" + gps_version_id + '\'' +
                ", image_height='" + image_height + '\'' +
                ", image_width='" + image_width + '\'' +
                ", jpeg_interchange_format='" + jpeg_interchange_format + '\'' +
                ", jpeg_interchange_format_length='" + jpeg_interchange_format_length + '\'' +
                ", orientation='" + orientation + '\'' +
                ", resolution_unit='" + resolution_unit + '\'' +
                ", software='" + software + '\'' +
                ", x_resolution='" + x_resolution + '\'' +
                ", y_resolution='" + y_resolution + '\'' +
                ", modify_time=" + modify_time +
                '}';
    }
}
