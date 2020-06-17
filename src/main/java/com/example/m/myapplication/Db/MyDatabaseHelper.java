package com.example.m.myapplication.Db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    // 表的建表语句

    private static final String CREATE_BOOK = "create table inf("
            +"id integer primary key autoincrement,"
            +"name text,"
            +"time text)";
    private static final String CREATE_BOOK2 = "create table talkinf("
            +"id integer primary key autoincrement,"
            +"name text,"
            +"neixin integer,"
            +"contex text)";
    private static final String CREATE_BOOK3 = "create table cook("
            +"id integer primary key autoincrement,"
            +"cookier text)";

    private Context mContext;

    /**
     * 构造方法
     * @param context
     * @param name 数据库名
     * @param factory 允许我们在查询数据的时候返回一个自定义的 Cursor，一般都是传入 null
     * @param version 当前数据库的版本号，可用于对数据库进行升级操作
     */
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    /**
     * 创建数据库
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 执行建表语句
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_BOOK2);
        db.execSQL(CREATE_BOOK3);
    }

    /**
     * 升级数据库
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}