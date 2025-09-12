package org.impactindia.llemeddocket.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.impactindia.llemeddocket.DatabaseHelper;
import org.impactindia.llemeddocket.pojo.ChartNormalRangeData;
import org.impactindia.llemeddocket.pojo.ChartcommonMasterData;

import java.util.ArrayList;

public class ChartNormalRangeModel {

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

    public static final String TABLE_NAME = "md_and_chart_normalrange_master_kha";

    private final static String ID = "_id";
    public final static String CHART_ID = "chart_id";
    public final static String XVALUE = "xvalue";
    private final static String Y3 = "y3";
    private final static String Y5 = "y5";
    private final static String Y10 = "y10";
    private final static String Y25 = "y25";
    private final static String YNORMAL = "ynormal";
    private final static String Y75 = "y75";
    private final static String Y90 = "y90";
    private final static String Y95 = "y95";
    private final static String Y97 = "y97";
    private final static String CREATED_DATE = "created_date";
    private final static String RANGE_START = "range_start";
    private final static String RANGE_END = "range_end";
    private final static String Y23 = "y23";
    private final static String Y27 = "y27";
    private final static String MODIFIED_DATE = "modified_date";

    public static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY, " +
            CHART_ID + " TEXT, " +
            XVALUE + " TEXT, " +
            Y3 + " TEXT, " +
            Y5 + " TEXT, " +
            Y10 + " TEXT, " +
            Y25 + " TEXT, " +
            Y90 + " TEXT, " +
            Y95 + " TEXT, " +
            YNORMAL + " TEXT, " +
            Y75 + " TEXT, " +
            Y97 + " TEXT, " +
            CREATED_DATE + " TEXT, " +
            RANGE_START + " TEXT, " +
            RANGE_END + " TEXT, " +
            Y23 + " TEXT, " +
            Y27 + " TEXT, " +
            MODIFIED_DATE + " TEXT " +
            ");";


    public static void insert(ChartNormalRangeData chartNormalRangeData)
    {
        open();
        ContentValues cv = new ContentValues();
        cv.put(CHART_ID,chartNormalRangeData.getChartid());
        cv.put(XVALUE,chartNormalRangeData.getXvalue());
        cv.put(Y3,chartNormalRangeData.getY3());
        cv.put(Y5,chartNormalRangeData.getY5());
        cv.put(Y10,chartNormalRangeData.getY10());
        cv.put(Y25,chartNormalRangeData.getY25());
        cv.put(Y90,chartNormalRangeData.getY90());
        cv.put(Y75,chartNormalRangeData.getY75());
        cv.put(Y97,chartNormalRangeData.getY97());
        cv.put(Y95,chartNormalRangeData.getY95());
        cv.put(CREATED_DATE,chartNormalRangeData.getCreateddate());
        cv.put(RANGE_START,chartNormalRangeData.getRangestart());
        cv.put(RANGE_END,chartNormalRangeData.getRangend());
        cv.put(Y23,chartNormalRangeData.getY23());
        cv.put(Y27,chartNormalRangeData.getY27());
        cv.put(MODIFIED_DATE,chartNormalRangeData.getModifieddate());

        long result= database.insert(TABLE_NAME, null, cv);
        if (result == -1)
        { Log.i("LastID","NOT INSERTED");
        }
        else
        {//Log.i("LastID",result+"");
        }
        close();
    }

    public static  void chartNormaldeleted(){
        open();
        database.execSQL("delete  from "+ TABLE_NAME);
    }


    public static ArrayList<ChartNormalRangeData> getmatchresult(String chartid, String xvalue)
    {
        open();
        ArrayList<ChartNormalRangeData> arrayList = new ArrayList<>();
        arrayList.clear();
        //String selectQuery = "SELECT  *  FROM " + TABLE_CHARTCOMMONMASTER +  " where " + TYPE + " = " +type +   " AND  " + DATA_SOURCE + " = '" + datasource + "'   AND " +   GENDER +  " = '" + gender + "'   AND " + RANGE +  " = '" +  range + "' " ;
        String selectQuery = "select * from (select abs ('"+ chartid +"' - " + CHART_ID + ") as x, * from " + TABLE_NAME + " where " + XVALUE + " = '" + xvalue +"')t" + " order by t.x asc,t.xvalue desc limit 1" ;
        Log.i("ckm=>ChartNormalRange",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ChartNormalRangeData data = new ChartNormalRangeData();
                data.setChartid(cursor.getString(cursor.getColumnIndex(CHART_ID)));
                data.setY3(cursor.getString(cursor.getColumnIndex(Y3)));
                data.setY97(cursor.getString(cursor.getColumnIndex(Y97)));
                data.setY5(cursor.getString(cursor.getColumnIndex(Y5)));
                data.setY95(cursor.getString(cursor.getColumnIndex(Y95)));
                data.setY27(cursor.getString(cursor.getColumnIndex(Y27)));
                data.setY23(cursor.getString(cursor.getColumnIndex(Y23)));
                arrayList.add(data);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }




}
