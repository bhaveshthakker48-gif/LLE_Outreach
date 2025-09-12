package org.impactindia.llemeddocket.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.impactindia.llemeddocket.DatabaseHelper;
import org.impactindia.llemeddocket.pojo.PhcSubcenterData;
import org.impactindia.llemeddocket.pojo.PhcUserData;
import org.impactindia.llemeddocket.util.CursorUtils;
import org.impactindia.llemeddocket.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class PhcUsersModel {

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



    public static String TABLE_PHCUSER = "phcuserTable";
    public static String ID = "phcsub_id";
    public static String CAMPID = "campId";
    public static String CAMPNO = "campNo";
    public static String PHC = "phc";
    public static String SUBCENTER ="subcenter";
    public static String VILLAGE = "village";
    public static String PADA = "pada";
    public static String PHCUSERID = "phcuserID";

    public static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_PHCUSER + " (" +
            ID + " INTEGER PRIMARY KEY, " +
            CAMPID + " TEXT, " +
            CAMPNO + " TEXT, " +
            PHC + " TEXT, " +
            SUBCENTER + " TEXT, " +
            VILLAGE + " TEXT, " +
            PADA + " TEXT, " +
            PHCUSERID + " TEXT " +
            ");";

    public static void insert(PhcUserData phcUserData)
    {
        open();
        ContentValues cv = new ContentValues();
        cv.put(CAMPID,phcUserData.getCampID());
        cv.put(CAMPNO,phcUserData.getCampNo());
        cv.put(PHC,phcUserData.getPhc());
        cv.put(PHCUSERID,phcUserData.getPhc_userID());
        cv.put(SUBCENTER,phcUserData.getSubcenter());
        cv.put(VILLAGE,phcUserData.getVillage());
        cv.put(PADA,phcUserData.getPada());


        long result= database.insert(TABLE_PHCUSER, null, cv);
        if (result == -1)
        { Log.i("LastID","NOT INSERTED");
        }
        else
        {//Log.i("LastID",result+"");
        }
        close();
    }

    public static ArrayList<PhcUserData> getPhcByUserId(String userid,String campid) {
        open();
        ArrayList<PhcUserData> arrayList = new ArrayList<>();
        arrayList.clear();
        String selectQuery = "SELECT DISTINCT  PHC  FROM " + TABLE_PHCUSER +  " where " + CAMPID + " = " +campid +   " AND  "+  PHCUSERID +  " LIKE '" + "%" + userid + "%" + "' " ;
        Log.i("ckm=>PHCUsers",selectQuery);
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PhcUserData msg = new PhcUserData();
                msg.setPhc(cursor.getString(cursor.getColumnIndex(PHC)));
                arrayList.add(msg);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }


    public static ArrayList<PhcUserData> getSubCenterByUserID(String userid,String phc,String campid) {
        open();
        ArrayList<PhcUserData> arrayList = new ArrayList<>();
        arrayList.clear();
        String selectQuery = "SELECT DISTINCT  subcenter  FROM " + TABLE_PHCUSER +  " where " + CAMPID + " = " +campid +   " AND  " + PHC + " = '" + phc +  "'  AND  " +  PHCUSERID +  " LIKE '" + "%" + userid + "%" + "' " ;
        Log.i("ckm=>USERSUBCENTERDATA",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PhcUserData msg = new PhcUserData();
                msg.setSubcenter(cursor.getString(cursor.getColumnIndex(SUBCENTER)));
                arrayList.add(msg);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }





    public static ArrayList<PhcUserData> getVillageBySubcentreUserId(String userid,String campid,String subcentre,String phc) {
        open();
        ArrayList<PhcUserData> arrayList = new ArrayList<>();
        arrayList.clear();
        String selectQuery = "SELECT  village  FROM " + TABLE_PHCUSER +  " where " + CAMPID + " = " +campid +   " AND  " + SUBCENTER + " = '" + subcentre + "'   AND " +   PHC +  " = '" + phc + "'   AND " + PHCUSERID +  " LIKE '" + "%" + userid + "%" + "' " ;
        Log.i("ckm=>villagebysubcentre",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PhcUserData msg = new PhcUserData();
                msg.setVillage(cursor.getString(cursor.getColumnIndex(VILLAGE)));
         //       msg.setPhc(cursor.getString(cursor.getColumnIndex(PHC)));
                arrayList.add(msg);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }

    public static ArrayList<PhcUserData> getPadaByUserId(String userid,String campid,String subcentre,String phc,String village) {
        open();
        ArrayList<PhcUserData> arrayList = new ArrayList<>();
        arrayList.clear();
        String selectQuery = "SELECT  pada  FROM " + TABLE_PHCUSER +  " where " + CAMPID + " = " +campid +   " AND  " + VILLAGE + " = '" + village + "'   AND "+ SUBCENTER + " = '" + subcentre + "'   AND " +   PHC +  " = '" + phc + "'   AND " + PHCUSERID +  " LIKE '" + "%" + userid + "%" + "' " ;
        Log.i("ckm=>getPadaByUserID",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PhcUserData msg = new PhcUserData();
                msg.setPada(cursor.getString(cursor.getColumnIndex(PADA)));
                //       msg.setPhc(cursor.getString(cursor.getColumnIndex(PHC)));
                arrayList.add(msg);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }

    public static ArrayList<PhcUserData> getVillageByCampId(String campid) {
        open();
        ArrayList<PhcUserData> arrayList = new ArrayList<>();
        arrayList.clear();
        String selectQuery = "SELECT  village  FROM " + TABLE_PHCUSER +  " where " + CAMPID + " = " +campid +   " " ;
        Log.i("ckm=>villagebysubcentre",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PhcUserData msg = new PhcUserData();
                msg.setVillage(cursor.getString(cursor.getColumnIndex(VILLAGE)));
         //       msg.setPhc(cursor.getString(cursor.getColumnIndex(PHC)));
                arrayList.add(msg);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }




    public static  void deleteMessageTable() {
        open();
        database.execSQL("delete  from "+ TABLE_PHCUSER);
    }


    public static PhcUserData fromCursor(Cursor c) {
        PhcUserData proguserdata = new PhcUserData();
        //proguserdata.setP_id(CursorUtils.extractIntegerOrNull(c,ID));
        proguserdata.setCampID(CursorUtils.extractStringOrNull(c, CAMPID));
        proguserdata.setCampNo(CursorUtils.extractStringOrNull(c, CAMPNO));
        proguserdata.setPhc(CursorUtils.extractStringOrNull(c, PHC));
        return proguserdata;
    }
}



