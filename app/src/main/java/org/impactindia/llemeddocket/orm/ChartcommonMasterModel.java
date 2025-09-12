package org.impactindia.llemeddocket.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.impactindia.llemeddocket.DatabaseHelper;
import org.impactindia.llemeddocket.pojo.ChartcommonMasterData;
import org.impactindia.llemeddocket.pojo.PhcSubcenterData;
import org.impactindia.llemeddocket.pojo.PhcUserData;

import java.util.ArrayList;

public class ChartcommonMasterModel {

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


    private final static String TABLE_CHARTCOMMONMASTER = "chartcommonmaster";
    private final static String ID = "ID";
    private final static String CHART_ID = "ChartId";
    private final static String TYPE = "Type";
    private final static String GENDER = "Gender";
    private final static String RANGE = "Range";
    private final static String XMIN = "XMin";
    private final static String XMAX = "XMax";
    private final static String YMIN = "YMin";
    private final static String YMAX = "YMax";
    private final static String IS_DELETED = "IsDeleted";
    private final static String CHART_TITLE = "ChartTitle";
    private final static String XTITLE = "XTitle";
    private final static String YTITLE = "YTitle";
    private final static String YINTERVAL = "YInterval";
    private final static String XINTERVAL = "XInterval";
    private final static String DATA_SOURCE = "DataSource";
    private final static String FLAG = "Flag";

    public static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_CHARTCOMMONMASTER + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CHART_ID + " TEXT, " +
            TYPE + " TEXT, " +
            GENDER + " TEXT, " +
            RANGE + " TEXT, " +
            XMIN + " TEXT, " +
            XMAX + " TEXT, " +
            YMIN + " TEXT, " +
            YMAX + " TEXT, " +
            IS_DELETED + " TEXT, " +
            CHART_TITLE + " TEXT, " +
            XTITLE + " TEXT, " +
            YTITLE + " TEXT, " +
            YINTERVAL + " TEXT, " +
            XINTERVAL + " TEXT, " +
            DATA_SOURCE + " TEXT, " +
            FLAG + " TEXT " +
            ");";


    public static void insert(ChartcommonMasterData chartcommonMasterData)
    {
        open();
        ContentValues cv = new ContentValues();
        cv.put(CHART_ID,chartcommonMasterData.getChartid());
        cv.put(TYPE,chartcommonMasterData.getType());
        cv.put(GENDER,chartcommonMasterData.getGender());
        cv.put(RANGE,chartcommonMasterData.getRange());
        cv.put(XMIN,chartcommonMasterData.getXmin());
        cv.put(XMAX,chartcommonMasterData.getXmax());
        cv.put(YMIN,chartcommonMasterData.getYmin());
        cv.put(YMAX,chartcommonMasterData.getYmax());
        cv.put(IS_DELETED,chartcommonMasterData.isIsdelted());
        cv.put(CHART_TITLE,chartcommonMasterData.getCharttitle());
        cv.put(XTITLE,chartcommonMasterData.getXtitle());
        cv.put(YTITLE,chartcommonMasterData.getYtitle());
        cv.put(YINTERVAL,chartcommonMasterData.getYinterval());
        cv.put(XINTERVAL,chartcommonMasterData.getXinterval());
        cv.put(DATA_SOURCE,chartcommonMasterData.getDatasource());
        cv.put(FLAG,chartcommonMasterData.getFlag());
        long result= database.insert(TABLE_CHARTCOMMONMASTER, null, cv);
        if (result == -1)
        { Log.i("LastID","NOT INSERTED");
        }
        else
        {//Log.i("LastID",result+"");
        }
        close();
    }

    public static  void chartCommonMasterModeldeleted (){
        open();
        database.execSQL("delete  from "+ TABLE_CHARTCOMMONMASTER);
    }

    public static ArrayList<ChartcommonMasterData> getmatchdata(String type, String datasource, String gender, String range) {
        open();
        ArrayList<ChartcommonMasterData> arrayList = new ArrayList<>();
        arrayList.clear();
        String selectQuery = "SELECT  *  FROM " + TABLE_CHARTCOMMONMASTER +  " where " + TYPE + " =  '" + type + "'   AND  " + DATA_SOURCE + " = '" + datasource + "'   AND " +   GENDER +  " = '" + gender + "'   AND " + RANGE +  " = '" +  range + "' " ;
        Log.i("ckm=>ChartcommonData",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ChartcommonMasterData msg = new ChartcommonMasterData();
                msg.setChartid(cursor.getString(cursor.getColumnIndex(CHART_ID)));
                msg.setType(cursor.getString(cursor.getColumnIndex(TYPE)));
                msg.setGender(cursor.getString(cursor.getColumnIndex(GENDER)));
                arrayList.add(msg);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }




}
