package org.impactindia.llemeddocket.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.impactindia.llemeddocket.DatabaseHelper;
import org.impactindia.llemeddocket.pojo.PhcUserData;
import org.impactindia.llemeddocket.pojo.SyncData;

import java.util.ArrayList;

public class syncModel {

    static SQLiteDatabase database;
    static DatabaseHelper dbHelper;

    public static DatabaseHelper getInstance(Context context){
        if(dbHelper == null)
            dbHelper = new DatabaseHelper(context);
        return dbHelper;
    }

    public static void open(){
        database = dbHelper.getWritableDatabase();
    }
    public static void close(){
        database.close();
    }


    public static String TABLE_SYNCH = "synchTable";
    public static String SYNCHID = "synch_id";
    public static String CAMPID = "campId";
    public static String USERID = "userid";
    public static String SYNCHDATE ="synchdate";
    public static String SYNCHTIME = "synchtime";
    public static String EXTRA = "extra";


    public static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_SYNCH + " (" +
            SYNCHID + " INTEGER PRIMARY KEY, " +
            CAMPID + " TEXT, " +
            USERID + " TEXT, " +
            SYNCHDATE + " TEXT, " +
            SYNCHTIME + " TEXT, " +
            EXTRA + " TEXT " +
            ");";


    public static void insert(SyncData syncData)
    {
        open();
        ContentValues cv = new ContentValues();
        cv.put(CAMPID,syncData.getCampid());
        cv.put(USERID,syncData.getUserid());
        cv.put(SYNCHDATE,syncData.getSynchdate());
        cv.put(SYNCHTIME,syncData.getSynchtime());
        cv.put(EXTRA,syncData.getExtra());

        long result= database.insert(TABLE_SYNCH, null, cv);
        if (result == -1)
        { Log.i("LastID","NOT INSERTED");
        }
        else
        {//Log.i("LastID",result+"");
        }
        close();
    }


    public static ArrayList<SyncData> getsynchdata() {
        open();
        ArrayList<SyncData> arrayList = new ArrayList<>();
        arrayList.clear();
        String selectQuery = "SELECT * FROM synchTable ORDER BY synch_id DESC LIMIT 3 " ;

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                SyncData msg = new SyncData();
                msg.setSynchdate(cursor.getString(cursor.getColumnIndex(SYNCHDATE)));
                msg.setCampid(cursor.getString(cursor.getColumnIndex(CAMPID)));
                msg.setSynchid(cursor.getString(cursor.getColumnIndex(SYNCHID)));
                msg.setUserid(cursor.getString(cursor.getColumnIndex(USERID)));
                arrayList.add(msg);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }

    public static ArrayList<SyncData> getsynchdatalast() {
        open();
        ArrayList<SyncData> arrayList = new ArrayList<>();
        arrayList.clear();
        String selectQuery = "SELECT * FROM synchTable ORDER BY synch_id ASC " ;

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                SyncData msg = new SyncData();
                msg.setSynchdate(cursor.getString(cursor.getColumnIndex(SYNCHDATE)));
                msg.setCampid(cursor.getString(cursor.getColumnIndex(CAMPID)));
                msg.setSynchid(cursor.getString(cursor.getColumnIndex(SYNCHID)));
                msg.setUserid(cursor.getString(cursor.getColumnIndex(USERID)));
                arrayList.add(msg);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }


    public static  void deleteSychModel() {
        open();
        database.execSQL("delete  from "+ TABLE_SYNCH);
    }


}
