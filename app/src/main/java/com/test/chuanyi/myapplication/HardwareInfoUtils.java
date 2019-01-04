package com.test.chuanyi.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:  zengfeng
 * Time  :  2018/11/26 19:56
 * Des   :  获取手机硬件信息的工具类
 */
public class HardwareInfoUtils {

    private HardwareInfoUtils() {
    }

    /**
     * 获取单卡或者双卡的手机号
     *
     * @return
     */
    public static List<String> getMobileNum(Context context) {
        List<String> mobileList = new ArrayList<>();
        TelephonyManager tm =
                (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        boolean isPhone = tm != null && tm.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
        Log.d("vivi", "是否为手机：" + isPhone);
        if (!isPhone) { //判断是否为手机
            return mobileList;
        }
       boolean isDouble= isDoubleSim(context);
        Log.d("vivi","是否为双卡："+isDouble);
        // TelephonyManager tm = (TelephonyManager) Utils.getApp().getSystemService(Context.TELEPHONY_SERVICE);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                || PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)) {
            if (tm.getSimState() == tm.SIM_STATE_READY) {
                String phoneNumber1 = tm.getLine1Number();
                mobileList.add(phoneNumber1);
            }
        }
        return mobileList;
    }

    /**
     * 判断是否为双卡双待手机
     *
     * @return
     */
    public static boolean isDoubleSim(Context context) {
        boolean isDouble = true;
        Method method = null;
        Object result_0 = null;
        Object result_1 = null;
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            // 只要在反射getSimStateGemini 这个函数时报了错就是单卡手机
            method = TelephonyManager.class.getMethod("getSimStateGemini", new Class[]{int.class});
            // 获取SIM卡1
            result_0 = method.invoke(tm, new Object[]{new Integer(0)});
            // 获取SIM卡2
            result_1 = method.invoke(tm, new Object[]{new Integer(1)});
        } catch (SecurityException e) {
            isDouble = false;
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            isDouble = false;
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            isDouble = false;
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            isDouble = false;
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            isDouble = false;
            e.printStackTrace();
        } catch (Exception e) {
            isDouble = false;
            e.printStackTrace();
        }
        return isDouble;
    }


    public static void getCalendarInfo(Context context) {
        String CALANDER_EVENT_URL = "content://com.android.calendar/events";
        Uri uri = Uri.parse(CALANDER_EVENT_URL);
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        while (cursor.moveToNext()) {

            //事件的ID
            String id = cursor.getString(cursor.getColumnIndex(CalendarContract.Events._ID)); //不同于Events.CALENDAR_ID
            //事件的标题
            String title = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE));
            //事件的起始时间
            String dtstart = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.DTSTART));
            //事件的结束时间 ，如果事件是每天/周,那么就没有结束时间
            String dtend = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.DTEND));
            //事件的描述
            String description = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.DESCRIPTION));
            //事件的重复规律
            String rrule = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.RRULE));
            //事件的复发日期。通常RDATE要联合RRULE一起使用来定义一个重复发生的事件的合集。
            String rdate = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.RDATE));
            //事件是否是全天的
            String allDay = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.ALL_DAY));
            //事件的地点
            String location = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.EVENT_LOCATION));
            //事件持续时间，例如“PT1H”表示事件持续1小时的状态， “P2W”指明2周的持续时间。P3600S表示3600秒
            String duration = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.DURATION));

            //other
            String last_date = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.LAST_DATE));
            String original_id = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.ORIGINAL_ID));
            String maxReminders = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.MAX_REMINDERS));
            String allowedReminders = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.ALLOWED_REMINDERS));

            Log.d("vivi","获取设置日历信息："+"id="+id+",title="+title+",dtstart="+dtstart+",dtend="+dtend+",description="+description+",loaction="+location);
        }
        cursor.close();
    }
}
