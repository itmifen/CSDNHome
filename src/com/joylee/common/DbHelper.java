package com.joylee.common;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 赛格通数据操作通用类
 * 
 */
public class DbHelper extends SQLiteOpenHelper {
	private final static int DATABASE_VERSION = 2;
	private final static String DATABASE_NAME = "joynews.db";

	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// 数据库不存在时执行,调用 getReadableDatabase时触发
	@Override
	public void onCreate(SQLiteDatabase db) {
		// 新闻表
		db.execSQL("CREATE TABLE news (id INTEGER PRIMARY KEY,newstitle varchar(250) NOT NULL,createtime datetime  NULL,anthor varchar(50) null,newsdetails TEXT null,newsimage BLOB null,url varchar(1000)  null,newsid INTEGER null,source INTEGER not null);");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	/**
	 * 查询数据
	 * 
	 * @param sql
	 */
	public Cursor query(String sql) {
		return query(sql, null);
	}

	/**
	 * 查询数据
	 * 
	 * @param sql
	 * @param args
	 */
	public Cursor query(String sql, String[] args) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, args);

		return cursor;
	}

	/**
	 * 执行sql,需一条条执行,否定只会执行第一条
	 * 
	 * @param sql
	 */
	public void execSQL(String sql) {
		SQLiteDatabase db = this.getReadableDatabase();

		db.execSQL(sql);
	}

	/**
	 * 执行sql,需一条条执行,否定只会执行第一条
	 * 
	 * @param sql
	 * @param args
	 */
	public void execSQL(String sql, Object[] bindArgs) {
		SQLiteDatabase db = this.getReadableDatabase();

		db.execSQL(sql, bindArgs);

	}
}
