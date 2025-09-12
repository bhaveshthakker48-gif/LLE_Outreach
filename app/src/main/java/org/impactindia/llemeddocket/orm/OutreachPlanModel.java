package org.impactindia.llemeddocket.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.impactindia.llemeddocket.DatabaseHelper;
import org.impactindia.llemeddocket.pojo.OutreachPlanData;
import org.impactindia.llemeddocket.pojo.PhcSubcenterData;

import java.util.ArrayList;

public class OutreachPlanModel {

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


    public static String TABLE_OUTREACH = "outreachPlan";
    public static String OID = "o_id";
    public static String CAMPID = "campId";
    public static String CAMPYEAR = "campYear";
    public static String CAMPLOCATION = "campLocation";
    public static String CAMPSTATE = "state";
    public static String CAMPFROM = "camp_from";
    public static String CAMPTO = "camp_to";
    public static String CAMPNO = "campNO";
    public static String CAMPDISTRIC = "distric";
    public static String CAMPTALUKA = "taluka";
    public static String OUTREACHFROM = "outrechfrom";
    public static String OUTREACHTO = "outrechto";
    public static String OUTREACHPLANID = "outrech_PlanId";
    public static String USERID = "user_ID";
    public static String USERDESIGNATION = "user_DESIGNATION";

    public static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_OUTREACH + " (" +
            OID + " INTEGER PRIMARY KEY, " +
            CAMPID + " TEXT, " +
            CAMPNO + " TEXT, " +
            CAMPYEAR + " TEXT, " +
            CAMPLOCATION + " TEXT, " +
            CAMPSTATE + " TEXT, " +
            CAMPFROM + " TEXT, " +
            CAMPTO + " TEXT, " +
            CAMPDISTRIC + " TEXT, " +
            CAMPTALUKA + " TEXT, " +
            OUTREACHFROM + " TEXT, " +
            OUTREACHTO + " TEXT, " +
            OUTREACHPLANID + " TEXT, " +
            USERID + " TEXT, " +
            USERDESIGNATION + " TEXT " +
            ");";

    public static void insert(OutreachPlanData outreachPlanData)
    {
        open();
        ContentValues cv = new ContentValues();
        cv.put(CAMPID,outreachPlanData.getCampID());
        cv.put(CAMPYEAR,outreachPlanData.getCampYear());
        cv.put(CAMPLOCATION,outreachPlanData.getCamp_Location());
        cv.put(CAMPSTATE,outreachPlanData.getState());
        cv.put(CAMPFROM,outreachPlanData.getFromDate());
        cv.put(CAMPTO,outreachPlanData.getToDate());
        cv.put(CAMPNO,outreachPlanData.getCampNo());
        cv.put(CAMPDISTRIC,outreachPlanData.getDistric());
        cv.put(CAMPTALUKA,outreachPlanData.getTaluka());
        cv.put(OUTREACHFROM,outreachPlanData.getOutreach_From());
        cv.put(OUTREACHTO,outreachPlanData.getOutreach_To());
        cv.put(OUTREACHPLANID,outreachPlanData.getOutreachplan_ID());
        cv.put(USERID,outreachPlanData.getOutrach_userId());
        cv.put(USERDESIGNATION,outreachPlanData.getDesignation());
        long result= database.insert(TABLE_OUTREACH, null, cv);
        if (result == -1)
        { Log.i("LastID","NOT INSERTED");
        }
        else
        {//Log.i("LastID",result+"");
        }
        close();
    }


    public static ArrayList<OutreachPlanData> getActiveOutreachCamp() {
        open();
//        database = dbHelper.getWritableDatabase();
        ArrayList<OutreachPlanData> arrayList = new ArrayList<>();
        arrayList.clear();
        String selectQuery = "SELECT * FROM " + TABLE_OUTREACH  ;
        Log.i("Outreach",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                OutreachPlanData outreachPlanData = new OutreachPlanData();
                outreachPlanData.setId(cursor.getInt(cursor.getColumnIndex(OID)));
                outreachPlanData.setCampID(cursor.getString(cursor.getColumnIndex(CAMPID)));
                outreachPlanData.setCampNo(cursor.getString(cursor.getColumnIndex(CAMPNO)));
                outreachPlanData.setCampYear(cursor.getString(cursor.getColumnIndex(CAMPYEAR)));
                outreachPlanData.setCamp_Location(cursor.getString(cursor.getColumnIndex(CAMPLOCATION)));
                outreachPlanData.setState(cursor.getString(cursor.getColumnIndex(CAMPSTATE)));
                outreachPlanData.setFromDate(cursor.getString(cursor.getColumnIndex(CAMPFROM)));
                outreachPlanData.setToDate(cursor.getString(cursor.getColumnIndex(CAMPTO)));
                outreachPlanData.setDistric(cursor.getString(cursor.getColumnIndex(CAMPDISTRIC)));
                outreachPlanData.setTaluka(cursor.getString(cursor.getColumnIndex(CAMPTALUKA)));
                outreachPlanData.setOutreach_From(cursor.getString(cursor.getColumnIndex(OUTREACHFROM)));
                outreachPlanData.setOutreach_To(cursor.getString(cursor.getColumnIndex(OUTREACHTO)));
                outreachPlanData.setOutreachplan_ID(cursor.getString(cursor.getColumnIndex(OUTREACHPLANID)));
                outreachPlanData.setOutrach_userId(cursor.getString(cursor.getColumnIndex(USERID)));
                arrayList.add(outreachPlanData);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }

    public static ArrayList<OutreachPlanData> getActiveCampName(String campid) {

        open();
        ArrayList<OutreachPlanData> arrayList = new ArrayList<>();
        arrayList.clear();
        String selectQuery = "SELECT * FROM " + TABLE_OUTREACH  + " WHERE " + CAMPID +  " ='" + campid + "' " ;
        Log.i("Outreach",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                OutreachPlanData outreachPlanData = new OutreachPlanData();
                outreachPlanData.setId(cursor.getInt(cursor.getColumnIndex(OID)));
                outreachPlanData.setCampID(cursor.getString(cursor.getColumnIndex(CAMPID)));
                outreachPlanData.setCampNo(cursor.getString(cursor.getColumnIndex(CAMPNO)));
                outreachPlanData.setCampYear(cursor.getString(cursor.getColumnIndex(CAMPYEAR)));
                outreachPlanData.setCamp_Location(cursor.getString(cursor.getColumnIndex(CAMPLOCATION)));
                outreachPlanData.setState(cursor.getString(cursor.getColumnIndex(CAMPSTATE)));
                outreachPlanData.setFromDate(cursor.getString(cursor.getColumnIndex(CAMPFROM)));
                outreachPlanData.setToDate(cursor.getString(cursor.getColumnIndex(CAMPTO)));
                outreachPlanData.setDistric(cursor.getString(cursor.getColumnIndex(CAMPDISTRIC)));
                outreachPlanData.setTaluka(cursor.getString(cursor.getColumnIndex(CAMPTALUKA)));
                outreachPlanData.setOutreach_From(cursor.getString(cursor.getColumnIndex(OUTREACHFROM)));
                outreachPlanData.setOutreach_To(cursor.getString(cursor.getColumnIndex(OUTREACHTO)));
                outreachPlanData.setOutreachplan_ID(cursor.getString(cursor.getColumnIndex(OUTREACHPLANID)));
                outreachPlanData.setOutrach_userId(cursor.getString(cursor.getColumnIndex(USERID)));
                arrayList.add(outreachPlanData);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }

    public static  void deleteMessageTable() {
       open();
        database.execSQL("delete  from "+ TABLE_OUTREACH);
 //       close();
    }

    public static ArrayList<OutreachPlanData> getActiveCamp() {
        open();
        ArrayList<OutreachPlanData> arrayList = new ArrayList<>();
        arrayList.clear();
        String selectQuery = "SELECT DISTINCT campLocation,outrechfrom  FROM " + TABLE_OUTREACH +"  " ;
        Log.i("ckm=>camplist",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                OutreachPlanData msg = new OutreachPlanData();
                // msg.setCampID(cursor.getString(cursor.getColumnIndex(CAMPID)));
                msg.setCamp_Location(cursor.getString(cursor.getColumnIndex(CAMPLOCATION)));
                msg.setOutreach_From(cursor.getString(cursor.getColumnIndex(OUTREACHFROM)));
                //msg.setPhc(cursor.getString(cursor.getColumnIndex(PHC)));
                arrayList.add(msg);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }

    public static void DeleteOutreachPlanModel()
    {
        open();
        database.execSQL("delete  from "+ TABLE_OUTREACH);
        close();
    }

}
