package com.joylee.business;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.joylee.common.DbHelper;
import com.joylee.entity.newsentity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lih on 13-7-5.
 */
public class NewsManager {

    private DbHelper dbHelper;
    private SQLiteDatabase db;
    private Context applicationContext;

    public NewsManager(Context context) {
        dbHelper = new DbHelper(context);
        applicationContext = context;
    }

    public void Close() {
        dbHelper.close();
    }

    public void InsertNews(newsentity newsinfo) {
        DbHelper dbHelper = new DbHelper(applicationContext);
        dbHelper.execSQL(
                "insert into news (newstitle,createtime,anthor,newsdetails,newsimage,url,newsid,source)"
                        + " values(?,?,?,?,?,?,?,?)",
                new Object[]{newsinfo.getTitle(), newsinfo.getNewsDatetime(), newsinfo.getAnthor(), newsinfo.getDetail(), newsinfo.getNewsimage(), newsinfo.getUrl(),newsinfo.getNewsid(),newsinfo.getSource()
                });
    }


    public newsentity GetInfoByID(String id,String source) {
        newsentity info = new newsentity();
        String sql = "select * from news where newsid=? and source=?";
        Cursor result = dbHelper.query(sql, new String[]{id,source});
        if (result.getCount() > 0) {
            if (result.moveToFirst()) {
                info.setNewsDatetime(result.getString(result
                        .getColumnIndex("createtime")));
                info.setTitle(result.getString(result
                        .getColumnIndex("newstitle")));
                info.setAnthor(result.getString(result
                        .getColumnIndex("anthor")));
                info.setDetail(result.getString(result
                        .getColumnIndex("newsdetails")));
                info.setUrl(result.getString(result
                        .getColumnIndex("url")));
                info.setNewsimage(result.getString(result
                        .getColumnIndex("newsimage")));
                info.setNewsid(result.getString(result
                        .getColumnIndex("newsid")));
                info.setSource(result.getString(result
                        .getColumnIndex("source")));
                Close();
                return info;
            }
        }
        return null;


    }

    public List<newsentity> GetList(String source) {
        List<newsentity> list = new ArrayList<newsentity>();

        String sql = "select * from news where source=? order  by id desc";
        Cursor result = dbHelper.query(sql,new String[]{source});
        if (result.getCount() > 0) {
            while (result.moveToNext()) {
                newsentity info = new newsentity();
                info.setNewsDatetime(result.getString(result
                        .getColumnIndex("createtime")));
                info.setTitle(result.getString(result
                        .getColumnIndex("newstitle")));
                info.setAnthor(result.getString(result
                        .getColumnIndex("anthor")));
                info.setDetail(result.getString(result
                        .getColumnIndex("newsdetails")));
                info.setUrl(result.getString(result
                        .getColumnIndex("url")));
                info.setNewsimage(result.getString(result
                        .getColumnIndex("newsimage")));
                info.setNewsid(result.getString(result
                        .getColumnIndex("newsid")));
                info.setSource(result.getString(result
                        .getColumnIndex("source")));
                list.add(info);
            }
        }
        return list;
    }


}
