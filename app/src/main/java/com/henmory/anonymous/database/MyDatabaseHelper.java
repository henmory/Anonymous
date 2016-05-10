package com.henmory.anonymous.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dan on 16/5/9.
 */
/**
* 数据的添删查改都有两种方法实现
* 1.执行相应的SQL语句（基本程序员应该会的）
* 2.使用安卓提供的方法（安卓特有的）
 * 安卓的方法不好用，而且还得从新学习，还是直接采用SQL的比较好
 * 今天下午搞了很久终于把基本数据库查询语句弄正确了，现在发现多一个空格都不可以,同时注意中英文
 * tools下由sqlite3工具，可以帮助数据库的操作
 * 可以把模拟器的数据库倒入到本地磁盘上
 * 数据库的操作需要在到单独线程中，因为很耗费时间
* */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "anonymous.db"; //数据库名称
    public static final int DATABASE_VERSION = 1;//数据库版本
    public static final String TABLE_NAME = "my_table";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" + "username varchar(20) not null,"
                    + "password varchar(60) not null" + ");"; //学习创建主键


    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
        System.out.println("database onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //打开数据库
    public static SQLiteDatabase getDatabase(Context context){

        MyDatabaseHelper database = new MyDatabaseHelper(context);
        SQLiteDatabase db = null;
        db = database.getReadableDatabase();//如果没有创建数据库，那么会自动调用onCreate方法，否则打开
        return db;
    }
}
