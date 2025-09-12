package org.impactindia.llemeddocket.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.impactindia.llemeddocket.DatabaseHelper;
import org.impactindia.llemeddocket.pojo.OutreachPlanData;
import org.impactindia.llemeddocket.pojo.PhcSubcenterData;
import org.impactindia.llemeddocket.pojo.PhcUserData;

import java.util.ArrayList;

public class PhcSubCenterModel {

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


    public static String TABLE_PHCSUBCENTER = "phcSubcenterTable";
    public static String ID = "phcsub_id";
    public static String CAMPID = "campId";
    public static String CAMPNO = "campNo";
    public static String PHC = "phc";
    public static String VILLAGE ="village";
    public static String PADA = "pada";
    public static String SUBCENTER = "subcenter";

    public static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_PHCSUBCENTER + " (" +
            ID + " INTEGER PRIMARY KEY, " +
            CAMPID + " TEXT, " +
            CAMPNO + " TEXT, " +
            PHC + " TEXT, " +
            VILLAGE + " TEXT, " +
            PADA + " TEXT, " +
            SUBCENTER + " TEXT " +
            ");";

    public static void insert(PhcSubcenterData phcSubcenterData)
    {
        open();
        ContentValues cv = new ContentValues();
        cv.put(CAMPID,phcSubcenterData.getCampID());
        cv.put(CAMPNO,phcSubcenterData.getCampNo());
        cv.put(PHC,phcSubcenterData.getPhc());
        cv.put(SUBCENTER,phcSubcenterData.getSubCenter());
        cv.put(VILLAGE,phcSubcenterData.getVillage());
        cv.put(PADA,phcSubcenterData.getPada());
        long result= database.insert(TABLE_PHCSUBCENTER, null, cv);
        if (result == -1)
        { Log.i("LastID","NOT INSERTED");
        }
        else
        {//Log.i("LastID",result+"");
        }
        close();
    }

    public static  void deleteMessageTable() {
        open();
        database.execSQL("delete  from "+ TABLE_PHCSUBCENTER);
    }



    public static ArrayList<PhcSubcenterData> getSubcenterByPhc(String phc) {
        open();
        ArrayList<PhcSubcenterData> arrayList = new ArrayList<>();
        arrayList.clear();
        String selectQuery = "SELECT DISTINCT subcenter  FROM " + TABLE_PHCSUBCENTER +  " where " + PHC +  " = '" +   phc  + "' " ;
        Log.i("ckm=>PHCUsers",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PhcSubcenterData msg = new PhcSubcenterData();
                msg.setSubCenter(cursor.getString(cursor.getColumnIndex(SUBCENTER)));
                arrayList.add(msg);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }

    public static ArrayList<PhcSubcenterData> getsubcenterbycampid(String campid) {
        open();
        ArrayList<PhcSubcenterData> arrayList = new ArrayList<>();
        arrayList.clear();
        String selectQuery = "SELECT DISTINCT subcenter  FROM " + TABLE_PHCSUBCENTER +  " where " + CAMPID +  " = '" +   campid  + "' " ;
        Log.i("ckm=>PHCUsers",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PhcSubcenterData msg = new PhcSubcenterData();
                msg.setSubCenter(cursor.getString(cursor.getColumnIndex(SUBCENTER)));
                arrayList.add(msg);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }

    public static ArrayList<PhcSubcenterData> getmatchdat(String phc) {
        open();
        ArrayList<PhcSubcenterData> arrayList = new ArrayList<>();
        arrayList.clear();
        String selectQuery = "SELECT  *  FROM " + TABLE_PHCSUBCENTER +  " where " + PHC +  " = '" +   phc  + "' " ;
        Log.i("ckm=>PHCUsers",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PhcSubcenterData msg = new PhcSubcenterData();
                msg.setCampID(cursor.getString(cursor.getColumnIndex(CAMPID)));
                msg.setSubCenter(cursor.getString(cursor.getColumnIndex(SUBCENTER)));
                msg.setCampNo(cursor.getString(cursor.getColumnIndex(CAMPNO)));
                msg.setPhc(cursor.getString(cursor.getColumnIndex(PHC)));
                arrayList.add(msg);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }


    public static ArrayList<PhcSubcenterData> getPadaBySubcentreUserId(String subcentre,String phc,String village) {
        open();
        ArrayList<PhcSubcenterData> arrayList = new ArrayList<>();
        arrayList.clear();
        String selectQuery = "SELECT  pada  FROM " + TABLE_PHCSUBCENTER +  " where " + VILLAGE + " =  '" +village +   "'   AND  " + SUBCENTER + " = '" + subcentre + "'   AND " +   PHC +  " = '" + phc + "' " ;
        Log.i("ckm=>padabysubcentre",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PhcSubcenterData msg = new PhcSubcenterData();
                msg.setPada(cursor.getString(cursor.getColumnIndex(PADA)));
                arrayList.add(msg);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }

    public static ArrayList<PhcSubcenterData> getPadabycampid(String campid) {
        open();
        ArrayList<PhcSubcenterData> arrayList = new ArrayList<>();
        arrayList.clear();
        String selectQuery = "SELECT  pada  FROM " + TABLE_PHCSUBCENTER +  " where " + CAMPID + " =  '" +campid +   "' " ;
        Log.i("ckm=>getpadacampid",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PhcSubcenterData msg = new PhcSubcenterData();
                msg.setPada(cursor.getString(cursor.getColumnIndex(PADA)));
                arrayList.add(msg);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }


    public static  void deletePhcSubCenterTable() {
        open();
        database.execSQL("delete  from "+ TABLE_PHCSUBCENTER);
    }
}
