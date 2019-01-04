package com.test.chuanyi.myapplication;

import android.app.Activity;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Author:  zengfeng
 * Time  :  2018/12/3 16:44
 * Des   :  https://blog.csdn.net/zhangjin12312/article/details/76855079
 */
public class GetAppLaunchInfoActivity extends Activity {

    private EditText et;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getapp_info_layout);

     //   LocalBroadcastManager.getInstance(this).registerReceiver();

        TextView tv = (TextView) findViewById(R.id.get_app_info_tv);
       // et = (EditText)findViewById(R.id.et);
        tv.setOnClickListener(new View.OnClickListener() {

            private BufferedWriter bw;
            private FileWriter fw;

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                Toast.makeText(GetAppLaunchInfoActivity.this, "hah", Toast.LENGTH_SHORT).show();
                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                getTimeZone();
                                getApplicationStartTimesAndTimes();
                                getTraffic(GetAppLaunchInfoActivity.this);
                                try {
                                    reflect();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                ).start();

                File[] path = getExternalCacheDirs();
                String absolutePath = getExternalCacheDir().getAbsolutePath();
                Log.d("vivi","path=="+path+",absolutePath=="+absolutePath);

                File file = new File(absolutePath+File.separator+"a.txt");
                String data ="Hello Android!";
                try {
                    fw = new FileWriter(file,true);
                    bw = new BufferedWriter(fw);
                    bw.write(data);
                    bw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        fw.close();
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

        drawerLayout = findViewById(R.id.drawer_ll);
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {
                Log.d("vivi","onDrawerSlide");
            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                Log.d("vivi","onDrawerOpened");
            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                Log.d("vivi","onDrawerClosed");
            }

            @Override
            public void onDrawerStateChanged(int i) {
                Log.d("vivi","onDrawerStateChanged");
            }
        });

        drawerLayout.openDrawer(Gravity.LEFT);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (drawerLayout.isDrawerOpen(findViewById(R.id.menu_content_ll))) {
            drawerLayout.closeDrawers();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        System.out.println("onSaveInstanceState is execute");
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void reflect() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<?> aClass = Class.forName("com.test.chuanyi.myapplication.Dog");
        Dog instance = (Dog) aClass.newInstance();
        Method method = aClass.getDeclaredMethod("methodInDog");
        method.invoke(instance);

    }

    private void getApplicationStartTimesAndTimes() {
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP){
            return;
        }
        Calendar beginCal = Calendar.getInstance();
        beginCal.add(Calendar.HOUR_OF_DAY, -1);
        Calendar endCal = Calendar.getInstance();
        UsageStatsManager manager = (UsageStatsManager) getApplicationContext().getSystemService(USAGE_STATS_SERVICE);
        List<UsageStats> stats = manager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, beginCal.getTimeInMillis(), endCal.getTimeInMillis());
        if (stats != null && stats.size() == 0) { //无读取其他应用使用信息权限
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
            int times = 0;
            while (stats != null && stats.size() == 0) {
                SystemClock.sleep(1000);
                System.out.println("到达循环");
                times++;
                stats = manager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, beginCal.getTimeInMillis(), endCal.getTimeInMillis());
                if (times > 30) {
                    System.out.println("到达30次，自动退出循环");
                    break;
                }
                getDetailInfo(stats);
            }
        } else { //有权限，直接获取
            getDetailInfo(stats);
        }

    }

    private void getDetailInfo(List<UsageStats> stats) {
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP){
            return;
        }
        Calendar cal = Calendar.getInstance();
        int offset = cal.get(Calendar.ZONE_OFFSET); //时区偏差值，如8*60*60*1000
        StringBuilder sb = new StringBuilder();
        PackageManager pm = getApplicationContext().getPackageManager();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (UsageStats us : stats) {
            try {
                ApplicationInfo applicationInfo = pm.getApplicationInfo(us.getPackageName(), PackageManager.GET_META_DATA);
                if ((applicationInfo.flags & applicationInfo.FLAG_SYSTEM) <= 0) {
                    String lastTime = format.format(new Date(us.getLastTimeUsed()));
                    //    String totalTime = format.format(new Date(us.getTotalTimeInForeground() - offset));
                    long value = us.getTotalTimeInForeground() - offset;
                    long total = us.getTotalTimeInForeground();
//                    String totalTime = getFormatTime(value);
                    String totalTime = total / (60 * 60 * 1000) + "时" + ((total % (60 * 60 * 1000)) / (60 * 1000)) + "分" + ((total % (60 * 60 * 1000)) % (60 * 1000))/1000+"秒";
                    int descr = us.describeContents();
                    int times = us.getClass().getDeclaredField("mLaunchCount").getInt(us);
                    String firstTimeStamp = format.format(new Date(us.getFirstTimeStamp()));
                    sb.append(pm.getApplicationLabel(applicationInfo)+"，包名："+applicationInfo.packageName + "\t" + lastTime + "\t" + totalTime + "\t" + total + "\t" + firstTimeStamp +
                            "启动次数：" + times + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.d("vivi", " 信息：" + sb.toString());
        System.out.println(" 信息：" + sb.toString());
    }

    private String getFormatTime(long value) {
        int day = (int) (value / (24 * 60 * 60 * 1000));
        int hour = (int) (value % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000);
        int remainHour = (int) (value % (24 * 60 * 60 * 1000) % (60 * 60 * 1000));
        int min = remainHour / (60 * 1000);
        int second = remainHour % (60 * 1000);

        return day + "天" + hour + "时" + min + "分" + second + "秒";
    }

    public static String getTimeZone() {
        Calendar cal = Calendar.getInstance();
        int offset = cal.get(Calendar.ZONE_OFFSET);
        cal.add(Calendar.MILLISECOND, -offset);
        Long timeStampUTC = cal.getTimeInMillis();
        Long timeStamp = System.currentTimeMillis();
        Long timeZone = (timeStamp - timeStampUTC) / (1000 * 3600);
        System.out.println(timeZone.intValue() + ",timeStampUTC = " + timeStampUTC + ",timeZone = " + timeZone + ",offset = " + offset);
        //8,timeStampUTC = 1543801443780,timeZone = 8,offset = 28800000
        return String.valueOf(timeZone);

    }


    private Long getTotalBytesManual(int localUid) {
        File dir = new File("/proc/uid_stat/");
        String[] children = dir.list();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < children.length; i++) {
            stringBuffer.append(children[i]);
            stringBuffer.append("   ");
        }
        Log.e("children*****", children.length + "");
        Log.e("vivi", stringBuffer.toString());
        if (!Arrays.asList(children).contains(String.valueOf(localUid))) {
            return 0L;
        }
        File uidFileDir = new File("/proc/uid_stat/" + String.valueOf(localUid));
        File uidActualFileReceived = new File(uidFileDir, "tcp_rcv");
        File uidActualFileSent = new File(uidFileDir, "tcp_snd");
        String textReceived = "0";
        String textSent = "0";
        try {
            BufferedReader brReceived = new BufferedReader(new FileReader(uidActualFileReceived));
            BufferedReader brSent = new BufferedReader(new FileReader(uidActualFileSent));
            String receivedLine;
            String sentLine;

            if ((receivedLine = brReceived.readLine()) != null) {
                textReceived = receivedLine;
               Log.e("receivedLine*****", "receivedLine:" + receivedLine);
            }
            if ((sentLine = brSent.readLine()) != null) {
                textSent = sentLine;
                Log.e("sentLine*****", "sentLine:" + sentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("IOException*****", e.toString());
        }
//        Log.e("BytesManualEnd*****", "localUid:" + localUid);
        return Long.valueOf(textReceived).longValue() + Long.valueOf(textSent).longValue();
    }


     public void getTraffic(Context context){
         PackageManager pm = context.getPackageManager();
         List<PackageInfo> packages = pm.getInstalledPackages(0);
         for (PackageInfo packageInfo : packages) {


             ApplicationInfo applicationInfo = packageInfo.applicationInfo;
             String packageName = applicationInfo.packageName;
//            long appReiveByte = TrafficStats.getUidRxBytes(packageInfo.applicationInfo.uid);//App接收的字节
//            long appSendByte = TrafficStats.getUidTxBytes(packageInfo.applicationInfo.uid);//App发送的字节
//            app.setDownlink(appReiveByte);
//            app.setUplink(appSendByte);
//            System.out.println("appName-->"+appName+",appReiveByte--->"+appReiveByte+"byte,appSendByte--->"+appSendByte)

             TrafficInfo info = new TrafficInfo(context,packageInfo.applicationInfo.uid);
             Log.d("vivi", "包名：" + packageName+"下载流量：" +info.getRcvTraffic());

         }
     }

//    private void getAppTrafficInfo(Context context) throws RemoteException {
//        hasPermissionToReadNetworkStats();
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
//            NetworkStatsManager networkStatsManager = (NetworkStatsManager) getSystemService(NETWORK_STATS_SERVICE);
//            // 获取subscriberId
//            TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
//            String subId = tm.getSubscriberId();
//
//            NetworkStats summaryStats;
//            long summaryRx = 0;
//            long summaryTx = 0;
//            NetworkStats.Bucket summaryBucket = new NetworkStats.Bucket();
//            long summaryTotal = 0;
//
//            summaryStats = networkStatsManager.querySummary(ConnectivityManager.TYPE_MOBILE, subId,0L, System.currentTimeMillis());
//            do {
//                summaryStats.getNextBucket(summaryBucket);
//                int summaryUid = summaryBucket.getUid();
//                if (uid == summaryUid) {
//                    summaryRx += summaryBucket.getRxBytes();
//                    summaryTx += summaryBucket.getTxBytes();
//                }
//                Log.i(MainActivity.class.getSimpleName(), "uid:" + summaryBucket.getUid() + " rx:" + summaryBucket.getRxBytes() +
//                        " tx:" + summaryBucket.getTxBytes());
//                summaryTotal += summaryBucket.getRxBytes() + summaryBucket.getTxBytes();
//            } while (summaryStats.hasNextBucket());
//        }else{
//            return;
//        }
//
//
//
//    }

    private boolean hasPermissionToReadNetworkStats() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        final AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), getPackageName());
        if (mode == AppOpsManager.MODE_ALLOWED) {
            return true;
        }

        requestReadNetworkStats();
        return false;
    }
    // 打开“有权查看使用情况的应用”页面
    private void requestReadNetworkStats() {
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivity(intent);
    }

}
