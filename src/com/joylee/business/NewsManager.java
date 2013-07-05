package com.joylee.business;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.joylee.common.DbHelper;
import com.joylee.entity.newsentity;

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
                "insert into news (newstitle,createtime,anthor,newsdetails,newsimage,url)"
                        + " values(?,?,?,?,?,?)",
                new Object[]{newsinfo.getTitle(), newsinfo.getNewsDatetime(), newsinfo.getAnthor(), newsinfo.getDetail(), newsinfo.getNewsimage(), newsinfo.getUrl()
                });
    }


  /*  public newsentity GetstorageRegInfoByPosition(String positionString) {
        newsentity info = new newsentity();
        String sql = "select * from news where begindate<=datetime() and enddate>=datetime('now','-1 day') and position=?  LIMIT 1 ";
        Cursor result = dbHelper.query(sql, new String[] { positionString });
        if (result.getCount() > 0 && result.moveToNext()) {
            info.setLessee(result.getString(result
                    .getColumnIndex("lessee")));
            info.setPosition(result.getString(result
                    .getColumnIndex("position")));
            info.setBegindate(result.getString(result
                    .getColumnIndex("begindate")));
            info.setEnddate(result.getString(result
                    .getColumnIndex("enddate")));
            info.setStatus(result.getString(result.getColumnIndex("status")));
            info.setBignumber(result.getString(result
                    .getColumnIndex("bignumber")));
            info.setSmallnumber(result.getString(result
                    .getColumnIndex("smallnumber")));
            info.setPositiontxm(result.getString(result
                    .getColumnIndex("positiontxm")));
            info.setDescr(result.getString(result.getColumnIndex("descr")));
            info.setOrgcode(result.getString(result
                    .getColumnIndex("orgcode")));
            info.setMakeorg(result.getString(result
                    .getColumnIndex("orgcode")));
            info.setBuildingid(result.getString(result
                    .getColumnIndex("buildingid")));
            Close();
            return info;
        }
        else {
            return null;
        }

    }*/
}
