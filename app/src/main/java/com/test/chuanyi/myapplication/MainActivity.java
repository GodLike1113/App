package com.test.chuanyi.myapplication;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.test.chuanyi.myapplication.recyclerview.RecyclerViewAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.click_tv).setOnClickListener(this);
//        int num =0;
//        while(true){
//            Bitmap bitmap =BitmapFactory.decodeResource(getResources(),R.drawable.bizhi);
//            num++;
//            System.out.println("num is "+num);
//        }
        String externalFilesDir = this.getExternalFilesDir(null).getAbsolutePath() + File.separator + "text.txt";
        System.out.println("lujing--" + externalFilesDir);
        File file = new File(externalFilesDir);
        try {
            FileWriter fw = new FileWriter(file, true);
            fw.write("hahhahaha");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        initData();
    }

    private void initData() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        List<String> srcList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            srcList.add("Num" + i);
        }
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        //增加分割线
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divideritem_decoration));
        recyclerView.addItemDecoration(dividerItemDecoration);

        //设置Item增加、移除动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, srcList);

        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                Toast.makeText(MainActivity.this,"click - "+position,Toast.LENGTH_SHORT).show();
                Log.d("vivi","click - "+position);
            }
        });

        adapter.setOnItemLongClickListener(new RecyclerViewAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClickListener(View v, int position) {
                Toast.makeText(MainActivity.this,"longClick - "+position,Toast.LENGTH_SHORT).show();
                Log.d("vivi","longClick - "+position);
            }
        });

        adapter.setOnItemTextClickListener(new RecyclerViewAdapter.OnItemTextClickListener() {
            @Override
            public void onTextClickListener(View v, int position) {
                Toast.makeText(MainActivity.this,"Textclick - "+position,Toast.LENGTH_SHORT).show();
                Log.d("vivi","Textclick - "+position);
            }

            @Override
            public void onTextLongClickListener(View v, int position) {
                Toast.makeText(MainActivity.this,"TextLongclick - "+position,Toast.LENGTH_SHORT).show();
                Log.d("vivi","TextLongclick - "+position);
            }
        });
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "点我了哈哈", Toast.LENGTH_LONG).show();
//        startActivity(new Intent(this,GetAppLaunchInfoActivity.class));
        startActivity(new Intent(this, A.class));

//        List<String> mobileNum = HardwareInfoUtils.getMobileNum(this);
//        for (int i = 0; i <mobileNum.size() ; i++) {
//            Log.d("vivi","手机卡是："+mobileNum.get(i));
//        }
//
//        test();
//        while(tag){
//            Log.d("vivi","wait....");
//        }
//        Log.d("vivi","hhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");

        //   getAlarm(this);

//        String systemAlarm = getSystemAlarm();
//        String str = Settings.System.getString(getContentResolver(),
//                Settings.System.NEXT_ALARM_FORMATTED);
//        Log.d("vivi","获取到闹钟："+systemAlarm+",alarm_alert:"+str);
//
//        HardwareInfoUtils.getCalendarInfo(this);

//        List<Photo> sDcardImage = getSDcardImage(this);
//        Log.d("vivi","sDcardImage大小："+sDcardImage.size());
        //      getAppList(this);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                uploadLogGetKey("http://10.240.36.210:8083/v1/reportpolicy","aaa","bbb","ccc","ddd");
//                Log.d("vivi","after Thread is "+Thread.currentThread().getName());
//
//            }
//        }).start();
        Gson gson = new Gson();
//        {"bvnFirstName":"linli linli","bvnSurName":"zhang zhang","cc":"86","phone":"18676764937"}
        HashMap<String, String> map = new HashMap<>();
        map.put("bvnFirstName", "linli linli");
        map.put("bvnSurName", "zhang zhang");
        map.put("cc", "86");
        map.put("phone", "18676764937");
        String s = gson.toJson(map);
        Log.d("vivi", "map转json 是：" + s);
        Bean bean = gson.fromJson(s, Bean.class);
        Log.d("vivi", bean.toString());


    }

    /**
     * http://www.cnblogs.com/owner/p/3934736.html
     *
     * @return
     */
    public String getSystemAlarm() {
        String str = Settings.System.getString(getContentResolver(),
                Settings.System.NEXT_ALARM_FORMATTED);
        return str;
    }

    public static void getAlarm(Context context) {
        final String tag_alarm = "tag_alarm";
        Uri uri = Uri.parse("content://com.android.alarmclock/alarm");
        Cursor c = context.getContentResolver().query(uri, null, null, null, null);
        if (c == null) {
            Log.d(tag_alarm, "c为null");
            return;
        }
        Log.i(tag_alarm, "no of records are " + c.getCount());
        Log.i(tag_alarm, "no of columns are " + c.getColumnCount());
        if (c != null) {
            String names[] = c.getColumnNames();
            for (String temp : names) {
                System.out.println(temp);
            }
            if (c.moveToFirst()) {
                do {
                    for (int j = 0; j < c.getColumnCount(); j++) {
                        Log.i(tag_alarm, c.getColumnName(j)
                                + " which has value " + c.getString(j));
                    }
                } while (c.moveToNext());
            }
            c.close();
        }
    }

    boolean tag = true;

    public void test() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("开始睡眠");
                            Log.d("vivi", "开始睡眠");
                            Thread.sleep(10000);
                            System.out.println("结束睡眠");
                            Log.d("vivi", "结束睡眠");
                            tag = false;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
        System.out.println("主线程打印");
        Log.d("vivi", "主线程打印");
    }


    /**
     * 获取图库的照片信息
     */
    private static List<Photo> getImages(Context context, Uri uri) {
        List<Photo> photoList = new ArrayList<>();
        // 获取ContentResolver
        ContentResolver contentResolver = context.getContentResolver();
        // 查询的字段
        String[] projection = {MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATA, MediaStore.Images.Media.SIZE, MediaStore.Images.Media.DATE_MODIFIED};

        // 条件
        String selection = MediaStore.Images.Media.MIME_TYPE + "=?";

        // 条件值(這裡的参数不是图片的格式，而是标准，所有不要改动)
        String[] selectionArgs = {"image/jpeg"};

        // 排序
        String sortOrder = MediaStore.Images.Media.DATA + " desc";
        // 查询sd卡上的图片
        // Cursor cursor = contentResolver.query(uri, projection, selection,selectionArgs, sortOrder);
        Cursor cursor = contentResolver.query(uri, projection, selection, selectionArgs, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                // 获得图片所在的路径(可以使用路径构建URI)
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                Log.d("vivi", "相册照片路径：" + path);
                long fileSize = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));

                if (!new File(path).exists()) {
                    continue;
                }

                long timer = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED));
                //获得图片显示的名称
                String imageName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));


                String fileSuffix = imageName.substring(imageName.lastIndexOf(".") + 1);

//                LocalFile localFile = new LocalFile();
//                localFile.setFileName(imageName);
//                localFile.setFilePath(data);
//                localFile.setFillSubffix(fileSuffix);
//                localFile.setFileSize(fileSize);
//                localFile.setDateModified(timer);
//                fileList.add(localFile);

                Photo photo = new Photo();
                photo.setPhoto_name(imageName);
                photo.setDir(path);
                Exif exif = new Exif();

                try {
                    ExifInterface exifInterface = new ExifInterface(path);
                    String tag_compression = exifInterface.getAttribute(ExifInterface.TAG_COMPRESSION);
                    exif.setCompression(tag_compression);

                    String tag_datetime = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
                    exif.setDate_time(tag_datetime); //拍摄时间
                    exif.setExif_tag("");
                    exif.setFile_size(String.valueOf(fileSize));

                    String tag_gps_latitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
                    String tag_gps_longitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
                    String tag_gps_lat_ref = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
                    String tag_gps_lng_ref = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
                    exif.setGps_latitude(tag_gps_latitude);
                    exif.setGps_longitude(tag_gps_longitude);
                    exif.setGps_latitude_ref(tag_gps_lat_ref);
                    exif.setGps_longitude_ref(tag_gps_lng_ref);

                    String tag_gps_map_datum = exifInterface.getAttribute(ExifInterface.TAG_GPS_MAP_DATUM);
                    exif.setGps_map_datum(tag_gps_map_datum);
                    exif.setGps_tag("");

                    String tag_gps_version_id = exifInterface.getAttribute(ExifInterface.TAG_GPS_VERSION_ID);
                    exif.setGps_version_id(tag_gps_version_id);

                    String tag_image_length = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH);
                    String tag_image_width = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
                    exif.setImage_width(tag_image_width);
                    exif.setImage_height(tag_image_length);

                    String interchange_format = exifInterface.getAttribute(ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT);
                    String interchange_format_length = exifInterface.getAttribute(ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT_LENGTH);
                    exif.setJpeg_interchange_format(interchange_format);
                    exif.setJpeg_interchange_format_length(interchange_format_length);

                    //判断拍摄图片朝向
                    int degree = 0; // 图片旋转角度
                    if (exif != null) {
                        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
                        if (orientation != -1) {
                            switch (orientation) {
                                case ExifInterface.ORIENTATION_ROTATE_90:
                                    degree = 90;
                                    break;
                                case ExifInterface.ORIENTATION_ROTATE_180:
                                    degree = 180;
                                    break;
                                case ExifInterface.ORIENTATION_ROTATE_270:
                                    degree = 270;
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                    exif.setOrientation(String.valueOf(degree));

                    String tag_resolution_unit = exifInterface.getAttribute(ExifInterface.TAG_RESOLUTION_UNIT);
                    exif.setResolution_unit(tag_resolution_unit);

                    String tag_x_resolution = exifInterface.getAttribute(ExifInterface.TAG_X_RESOLUTION);
                    String tag_y_resolution = exifInterface.getAttribute(ExifInterface.TAG_Y_RESOLUTION);
                    exif.setX_resolution(tag_x_resolution);
                    exif.setY_resolution(tag_y_resolution);

                    String tag_software = exifInterface.getAttribute(ExifInterface.TAG_SOFTWARE);
                    exif.setSoftware(tag_software);

                    exif.setModify_time(timer);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                photo.setExif(exif);
                photoList.add(photo);
            }
            // 关闭cursor
            cursor.close();
        }
        return photoList;

    }

    /**
     * 获取内部存储卡和外置SD卡的图片信息
     *
     * @return
     */
    public static List<Photo> getSDcardImage(Context context) {
        // 指定要查询的uri资源
        Uri externalUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        List<Photo> otherlist = getImages(context, externalUri);

        Uri internalUri = MediaStore.Images.Media.INTERNAL_CONTENT_URI;
        List<Photo> list = getImages(context, internalUri);
        list.addAll(otherlist);

        Collections.sort(list, new SortByDateMODIFIED());
        return list;
    }

    ;

    //根据修改时间排序
    static class SortByDateMODIFIED implements Comparator {
        public int compare(Object obj1, Object obj2) {
            Photo point1 = (Photo) obj1;
            Photo point2 = (Photo) obj2;
            if (point1.getExif().getModify_time() < point2.getExif().getModify_time())
                return 1;
            else if (point1.getExif().getModify_time() == point2.getExif().getModify_time()) {
                return 0;
            }
            return -1;
        }
    }

    public static void getAppList(Context context) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        PackageManager pm = context.getPackageManager();
        // Return a List of all packages that are installed on the device.
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        for (PackageInfo packageInfo : packages) {
            // 判断系统/非系统应用
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) // 非系统应用
            {

                long firstInstallTime = packageInfo.firstInstallTime;//应用第一次安装的时间
                long lastUpdateTime = packageInfo.lastUpdateTime;//最后更新时间
                String installTime = sdf.format(new Date(firstInstallTime));
                String updateTime = sdf.format(new Date(lastUpdateTime));

                int versionCode = packageInfo.versionCode;//应用现在的版本号
                String versionName = packageInfo.versionName;//应用现在的版本名称
                String Name = "";

                //如下可获得更多信息
                ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                String packageName = applicationInfo.packageName;
                try {
                    Name = pm.getApplicationLabel(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA)).toString();
                } catch (Exception e) {
                    Name = "";
                }

                Log.d("vivi", "包名：" + packageName + ",应用名字:" + Name + ",安装时间：" + installTime + ",更新时间：" + updateTime);
            } else {
                // 系统应用
            }
        }
    }

//    Calendar cal = new GregorianCalendar();
//       cal.setTime(getDayEnd());
//        cal.add(Calendar.DAY_OF_MONTH, -1);
    //   https://blog.csdn.net/zhangjin12312/article/details/76855079
//    Calendar beginCal = Calendar.getInstance();
//    beginCal.add(Calendar.HOUR_OF_DAY, -1);
//    Calendar endCal = Calendar.getInstance();
//    UsageStatsManager manager=(UsageStatsManager)getApplicationContext().getSystemService(USAGE_STATS_SERVICE);
//    List<UsageStats> stats=manager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,beginCal.getTimeInMillis(),endCal.getTimeInMillis());


    /**
     * 获取log上传的公钥和私钥
     */
    public void uploadLogGetKey(final String urlPath, final String appId, final String appKey, final String timestamp, final String sign) {
        try {
            Log.d("vivi", "Post Thread " + Thread.currentThread().getName());
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String parameter = "appId=" + appId + "&appKey=" + appKey + "&timestamp=" + timestamp + "&sign=" + sign;
            conn.setRequestProperty("Content-Length", parameter.length() + "");

            conn.setDoInput(true);
            conn.getOutputStream().write(parameter.getBytes());
            SystemClock.sleep(1000);

            int responseCode = conn.getResponseCode();
            Log.d("vivi", "Receive Thread " + Thread.currentThread().getName());
            if (responseCode == 200) {
                InputStream in = conn.getInputStream();
                String result = decodeStream(in);
                Log.d("vivi", "请求成功：" + result);
            } else {
                Log.d("vivi", "请求失败！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("vivi", "请求异常:" + e);
        }
    }

    private String decodeStream(InputStream in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len;
        byte[] arr = new byte[1024];
        while ((len = in.read(arr)) != -1) {
            baos.write(arr, 0, len);
        }
        String content = baos.toString();

        return content;

    }
}
