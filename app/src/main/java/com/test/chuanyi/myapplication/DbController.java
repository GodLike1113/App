package com.test.chuanyi.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

/**
 * Author:  zengfeng
 * Time  :  2019/11/26 11:03
 * Des   :  GreenDao的控制层
 */
public class DbController {
    /**
     * Helper
     */
    private DaoMaster.DevOpenHelper mHelper;//获取Helper对象
    /**
     * 数据库
     */
    private SQLiteDatabase db;
    /**
     * DaoMaster
     */
    private DaoMaster mDaoMaster;
    /**
     * DaoSession
     */
    private DaoSession mDaoSession;
    /**
     * 上下文
     */
    private Context context;
    /**
     * dao
     */
    private PersonDao personInforDao;

    private static DbController mDbController;

    /**
     * 获取单例
     */
    public static DbController getInstance(Context context) {
        if (mDbController == null) {
            synchronized (DbController.class) {
                if (mDbController == null) {
                    mDbController = new DbController(context);
                }
            }
        }
        return mDbController;
    }

    /**
     * 初始化
     *
     * @param context
     */
    private DbController(Context context) {
        this.context = context;
        mHelper = new DaoMaster.DevOpenHelper(context, "person.db", null);
        mDaoMaster = new DaoMaster(getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
        personInforDao = mDaoSession.getPersonDao();
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(context, "person.db", null);
        }
        SQLiteDatabase db = mHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     *
     * @return
     */
    private SQLiteDatabase getWritableDatabase() {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(context, "person.db", null);
        }
        SQLiteDatabase db = mHelper.getWritableDatabase();
        return db;
    }

    /**
     * 会自动判定是插入还是替换
     *
     * @param personInfor
     */
    public void insertOrReplace(Person personInfor) {
        personInforDao.insertOrReplace(personInfor);
    }

    /**
     * 插入一条记录，表里面要没有与之相同的记录
     *
     * @param personInfor
     */
    public long insert(Person personInfor) {
        return personInforDao.insert(personInfor);
    }

    /**
     * 更新数据
     *
     * @param personInfor
     */
    public void update(Person personInfor) {
        Person mOldPersonInfor = personInforDao.queryBuilder().where(PersonDao.Properties.Id.eq(personInfor.getId())).build().unique();//拿到之前的记录
        if (mOldPersonInfor != null) {
            mOldPersonInfor.setName("张三");
            personInforDao.update(mOldPersonInfor);
        }
    }

    /**
     * 按条件查询数据
     */
    public List<Person> searchByWhere(String wherecluse) {
        List<Person> personInfors = (List<Person>) personInforDao.queryBuilder().where(PersonDao.Properties.Name.eq(wherecluse)).build().unique();
        return personInfors;
    }

    public void querySql() {
        List<Person> person = personInforDao.queryBuilder().where(new WhereCondition.StringCondition("AGE > 18")).list();
        for (Person person1 : person) {
            LogUtils.v("精确查询:" + person1.getName() + "," + person1.getPhoneNum() + "," + person1.getAge());
        }
    }

    public void queryThread() {
        final Query query = personInforDao.queryBuilder().build();
        ThreadPoolUtil.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                LogUtils.d("查当前线程：" + Thread.currentThread().getName()+",startTime:"+ System.currentTimeMillis());
                List list = query.forCurrentThread().list();
                LogUtils.d("查当前线程：" + Thread.currentThread().getName()+",endTime:"+ System.currentTimeMillis());
            }
        });
    }

    /**
     * 查询所有数据
     */
    public List<Person> searchAll() {
        List<Person> personInfors = personInforDao.queryBuilder().list();
        return personInfors;
    }

    /**
     * 删除数据
     */
    public void delete(String wherecluse) {
        personInforDao.queryBuilder().where(PersonDao.Properties.Name.eq(wherecluse)).buildDelete().executeDeleteWithoutDetachingEntities();
    }


//    //eq: equal 精确查询  名字等于jackie
//    public void queryEq() {
//        Person person = mPersonDao.queryBuilder().where(PersonDao.Properties.Name.eq("jackie")).unique();
//    }
//
//    //notEq: not equal 精确查询  名字不等于jackie
//    public void queryNotEq() {
//        Person person = mPersonDao.queryBuilder().where(PersonDao.Properties.Name.notEq("jackie")).unique();
//    }
//
//    //like  模糊查询  名字以jackie开头
//    public void queryLike() {
//        Person person = mPersonDao.queryBuilder().where(PersonDao.Properties.Name.like("jackie")).unique();
//        //通配符
//        List<Person> persons = mPersonDao.queryBuilder().where(PersonDao.Properties.Name.like("jackie%")).list();
//    }
//
//    //between 区间查询 年龄在20到30之间
//    public void queryBetween() {
//        List<Person> persons = mPersonDao.queryBuilder().where(PersonDao.Properties.Age.between(20, 30)).list();
//    }
//
//    //gt: greater than 半开区间查询，年龄大于18
//    public void queryGt() {
//        List<Person> persons = mPersonDao.queryBuilder().where(PersonDao.Properties.Age.gt(18)).list();
//    }
//
//    //ge: greater equal 半封闭区间查询，年龄大于或者等于18
//    public void queryGe() {
//        List<Person> persons = mPersonDao.queryBuilder().where(PersonDao.Properties.Age.ge(18)).list();
//    }
//
//    //lt: less than 半开区间查询，年龄小于18
//    public void queryLt() {
//        List<Person> persons = mPersonDao.queryBuilder().where(PersonDao.Properties.Age.lt(18)).list();
//    }
//
//    //le: less equal 半封闭区间查询，年龄小于或者等于18
//    public void queryLe() {
//        List<Person> persons = mPersonDao.queryBuilder().where(PersonDao.Properties.Age.le(18)).list();
//    }
//
//    //名字以jackie开头，年龄升序排序
//    public void queryLikeAsc() {
//        //通配符
//        List<Person> persons = mPersonDao.queryBuilder().where(PersonDao.Properties.Name.like("jackie%")).orderAsc(PersonDao.Properties.Age).list();
//    }
//
//    //名字以jackie开头，年龄降序排序
//    public void queryLikeDesc() {
//        List<Person> persons = mPersonDao.queryBuilder().where(PersonDao.Properties.Name.like("jackie%")).orderDesc(PersonDao.Properties.Age).list();
//    }

    /**
     * 自定义sql语句查询，查询年龄小于45的父亲的所有儿子
     */
//    public void querySql(){
//        List<Person> person =mPersonDao.queryBuilder().where(new WhereCondition.StringCondition("FATHER_ID IN (SELECT _ID FROM FATHER WHERE AGE < 45)")).list();
//    }

    /**
     * 在子线程中查询大量数据
     */
//    public void queryThread() {
//        final Query query = mPersonDao.queryBuilder().build();
//        new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        List list = query.list();
//                    }
//                }).start();
//    }
}
