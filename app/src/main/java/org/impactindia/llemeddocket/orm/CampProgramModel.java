package org.impactindia.llemeddocket.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.impactindia.llemeddocket.DatabaseHelper;
import org.impactindia.llemeddocket.pojo.CampProgramData;
import org.impactindia.llemeddocket.pojo.OutreachPlanData;
import org.impactindia.llemeddocket.pojo.PhcUserData;

import java.util.ArrayList;

public class CampProgramModel {
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

    public static String TABLE_CAMPPROGRAM = "campProgram";
    public static String ID = "campprogram_id";
    public static String CAMPID = "campId";
    public static String CAMPNO = "campNo";
    public static String CAMPPROGRAM = "campProgram";
    public static String CAMPPROGRAMFROM = "campProgramFrom";
    public static String CAMPPROGRAMTO = "campProgramTo";


    public static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_CAMPPROGRAM + " (" +
            ID + " INTEGER PRIMARY KEY, " +
            CAMPID + " TEXT, " +
            CAMPNO + " TEXT, " +
            CAMPPROGRAM + " TEXT, " +
            CAMPPROGRAMFROM + " TEXT, " +
            CAMPPROGRAMTO + " TEXT " +
            ");";

    public static void insert(CampProgramData campProgramData)
    {
        open();
        ContentValues cv = new ContentValues();
        cv.put(CAMPID,campProgramData.getCamp_id());
        cv.put(CAMPNO,campProgramData.getCamp_no());
        cv.put(CAMPPROGRAM,campProgramData.getCampprogram());
        cv.put(CAMPPROGRAMFROM,campProgramData.getCampfrom());
        cv.put(CAMPPROGRAMTO,campProgramData.getCampto());

        long result= database.insert(TABLE_CAMPPROGRAM, null, cv);
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
        database.execSQL("delete  from "+ TABLE_CAMPPROGRAM);
    }




    public static ArrayList<CampProgramData> getcampprogramdata(String campid, String campprogram) {
        open();
        ArrayList<CampProgramData> arrayList = new ArrayList<>();
        arrayList.clear();
        String selectQuery = "SELECT  *  FROM " + TABLE_CAMPPROGRAM +  " where " + CAMPID + " = '" +campid + "'   AND "+  CAMPPROGRAM +  " = '" +  campprogram + "' " ;
        Log.i("ckm=>PHCUsers",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                 CampProgramData msg = new CampProgramData();
                 msg.setCampprogram(cursor.getString(cursor.getColumnIndex(CAMPPROGRAM)));
                 msg.setCampfrom(cursor.getString(cursor.getColumnIndex(CAMPPROGRAMFROM)));
                 msg.setCampto(cursor.getString(cursor.getColumnIndex(CAMPPROGRAMTO)));
                 arrayList.add(msg);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }

    public static void deleteCampProgram()
    {
        open();
        database.execSQL("delete  from "+ TABLE_CAMPPROGRAM);
       // close();
    }

}
