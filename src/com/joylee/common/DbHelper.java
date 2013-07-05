package com.joylee.common;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * ����ͨ���ݲ���ͨ����
 * 
 */
public class DbHelper extends SQLiteOpenHelper {
	private final static int DATABASE_VERSION = 1;
	private final static String DATABASE_NAME = "joynews.db";

	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// ���ݿⲻ����ʱִ��,���� getReadableDatabaseʱ����
	@Override
	public void onCreate(SQLiteDatabase db) {
		// ���ű�
		db.execSQL("CREATE TABLE news (id INTEGER PRIMARY KEY,newstitle varchar(250) NOT NULL,createtime datetime  NULL,anthor varchar(50) null,newsdetails TEXT null,newsimage BLOB null,url varchar(1000)  null);");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	/**
	 * ��ѯ����
	 * 
	 * @param sql
	 */
	public Cursor query(String sql) {
		return query(sql, null);
	}

	/**
	 * ��ѯ����
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
	 * ִ��sql,��һ����ִ��,��ֻ��ִ�е�һ��
	 * 
	 * @param sql
	 */
	public void execSQL(String sql) {
		SQLiteDatabase db = this.getReadableDatabase();

		db.execSQL(sql);
	}

	/**
	 * ִ��sql,��һ����ִ��,��ֻ��ִ�е�һ��
	 * 
	 * @param sql
	 * @param args
	 */
	public void execSQL(String sql, Object[] bindArgs) {
		SQLiteDatabase db = this.getReadableDatabase();

		db.execSQL(sql, bindArgs);

	}
}
