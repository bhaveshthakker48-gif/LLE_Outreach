package org.impactindia.llemeddocket.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.impactindia.llemeddocket.DatabaseHelper;
import org.impactindia.llemeddocket.pojo.PadanashaworkerData;
import org.impactindia.llemeddocket.pojo.PopMedicalData;

import java.util.ArrayList;

public class PadaNAshworkerDetailModel {

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

    public static String TABLE_PADANASHAWORKERDETAILS = "padaNashworkerTable";
    public static String ASHID = "id";
    public static String DATEOFENTERDATA = "dateofdata";
    public static String CAMPID = "campId";
    public static String USERID = "USERId";
    public static String CAMPNO = "campNo";
    public static String ASHOUTREACHDATE = "outreachdate";
    public static String ASHPADA = "pada";
    public static String ASHVILLAGE = "village";
    public static String ASHSUBCENTRE = "subcentre";
    public static String ASHPHC = "phc";
    public static String ASHANMNAME = "anmname";
    public static String ASHANMMOBILE = "anmmobile";
    public static String ASHWORKERNAME = "ashaworkername";
    public static String ASHWORKERMOBILENO = "ashaworkermobile";
    public static String ASHGPNAME = "gpname";
    public static String ASHGPCONTACTNAME = "contactpersonname";
    public static String ASHCPMOBILENO = "cpmobile";
    public static String OUTREACHDETAILSID = "outreachdetailsid";
    public static String EXTRA = "extra";
    public static String EXTRA1 = "extra1";



    public static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_PADANASHAWORKERDETAILS + " (" +
            ASHID + " INTEGER PRIMARY KEY, " +
            CAMPID + " TEXT, " +
            CAMPNO + " TEXT, " +
            USERID + " TEXT, " +
            DATEOFENTERDATA + " TEXT, "+
            ASHOUTREACHDATE + " TEXT, " +
            ASHPADA + " TEXT, " +
            ASHVILLAGE + " TEXT, " +
            ASHSUBCENTRE + " TEXT, " +
            ASHPHC + " TEXT, " +
            ASHANMNAME + " TEXT, " +
            ASHANMMOBILE + " TEXT, " +
            ASHWORKERNAME + " TEXT, " +
            ASHWORKERMOBILENO + " TEXT, " +
            ASHGPNAME + " TEXT, " +
            ASHGPCONTACTNAME + " TEXT, " +
            ASHCPMOBILENO + " TEXT, " +
            OUTREACHDETAILSID + " text, " +
            EXTRA + " TEXT, " +
            EXTRA1 + " TEXT " +
            ");";


    public static Long insert(PadanashaworkerData padanashaworkerData)
    {
        open();
        ContentValues cv = new ContentValues();
        cv.put(CAMPID,padanashaworkerData.getCampid());
        //cv.put(CAMPNO,padanashaworkerData.getCampno());
        cv.put(USERID,padanashaworkerData.getUserid());
        cv.put(ASHOUTREACHDATE,padanashaworkerData.getDateofdataenter());
        cv.put(DATEOFENTERDATA,padanashaworkerData.getDateofdataenter());
        cv.put(ASHPADA,padanashaworkerData.getPada());
        cv.put(ASHVILLAGE,padanashaworkerData.getVillage());
        cv.put(ASHSUBCENTRE,padanashaworkerData.getSubcenter());
        cv.put(ASHPHC,padanashaworkerData.getPhc());
        cv.put(ASHANMNAME,padanashaworkerData.getAnmname());
        cv.put(ASHANMMOBILE,padanashaworkerData.getAnmmobileno());
        cv.put(ASHWORKERNAME,padanashaworkerData.getAshaworkername());
        cv.put(ASHWORKERMOBILENO,padanashaworkerData.getAshaworkermobile());
        cv.put(ASHGPNAME,padanashaworkerData.getGpname());
        cv.put(ASHCPMOBILENO,padanashaworkerData.getCpmobile());
        cv.put(ASHGPCONTACTNAME,padanashaworkerData.getContactpersonname());
        cv.put(OUTREACHDETAILSID,padanashaworkerData.getOutreachdetailsid());

        long result = database.insert(TABLE_PADANASHAWORKERDETAILS, null, cv);
        if (result == -1)
        { Log.i("LastID","NOT INSERTED");
        }
        else
        {//Log.i("LastID",result+"");
        }
        close();
        return result;
    }

    public static void  UpdatepadaAshdata(String id, PadanashaworkerData padanashaworkerData)
    {
        open();
        ContentValues cv = new ContentValues();

        cv.put(USERID,padanashaworkerData.getUserid());
        cv.put(ASHOUTREACHDATE,padanashaworkerData.getOutreachdate());
       // cv.put(DATEOFENTERDATA,padanashaworkerData.getDateofdataenter());
        cv.put(ASHPADA,padanashaworkerData.getPada());
        cv.put(ASHVILLAGE,padanashaworkerData.getVillage());
        cv.put(ASHSUBCENTRE,padanashaworkerData.getSubcenter());
        cv.put(ASHPHC,padanashaworkerData.getPhc());
        cv.put(ASHANMNAME,padanashaworkerData.getAnmname());
        cv.put(ASHANMMOBILE,padanashaworkerData.getAnmmobileno());
        cv.put(ASHWORKERNAME,padanashaworkerData.getAshaworkername());
        cv.put(ASHWORKERMOBILENO,padanashaworkerData.getAshaworkermobile());
        cv.put(ASHGPNAME,padanashaworkerData.getGpname());
        cv.put(ASHCPMOBILENO,padanashaworkerData.getCpmobile());
        cv.put(ASHGPCONTACTNAME,padanashaworkerData.getContactpersonname());
       // cv.put(OUTREACHDETAILSID,padanashaworkerData.getOutreachdetailsid());

        Log.i("SQLITE Data",id);
        database.update(TABLE_PADANASHAWORKERDETAILS , cv, "id = "+ " '" + id +  "' " , null);
    }



    public static void  UpdatepadaAshSynchData(String id, String outreachdetailsid)
    {
        open();
        ContentValues cv = new ContentValues();
        cv.put(OUTREACHDETAILSID,outreachdetailsid);
        Log.i("SQLITE Data",id);
        database.update(TABLE_PADANASHAWORKERDETAILS , cv, "id = "+ " '" + id +  "' " , null);
    }


    public static ArrayList<PadanashaworkerData> getAshaworker(String campid) {
        open();
        ArrayList<PadanashaworkerData> arrayList = new ArrayList<>();
        arrayList.clear();
        String selectQuery = "SELECT DISTINCT ashaworkername FROM padaNashworkerTable " +  " where " + CAMPID + " = " +campid + " AND (ashaworkername IS NOT NULL AND ashaworkername != '') " ;
        Log.i("ckm=>ASHAWORKER",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PadanashaworkerData msg = new PadanashaworkerData();
                msg.setAshaworkername(cursor.getString(cursor.getColumnIndex(ASHWORKERNAME)));
                arrayList.add(msg);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }


    public static ArrayList<PadanashaworkerData> getAnmworker(String campid) {
        open();
        ArrayList<PadanashaworkerData> arrayList = new ArrayList<>();
        arrayList.clear();
        String selectQuery = "SELECT DISTINCT anmname FROM padaNashworkerTable " +  " where " + CAMPID + " = " +campid + " AND (anmname IS NOT NULL AND anmname != '') " ;
        Log.i("ckm=>ANMWORKER",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PadanashaworkerData msg = new PadanashaworkerData();
                msg.setAnmname(cursor.getString(cursor.getColumnIndex(ASHANMNAME)));
                arrayList.add(msg);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }


    public static ArrayList<PadanashaworkerData> getPadafromAsha(String campid,String ashaname) {
        open();
        ArrayList<PadanashaworkerData> arrayList = new ArrayList<>();
        arrayList.clear();
        String selectQuery = "SELECT pada FROM padaNashworkerTable " +  " where " + ASHWORKERNAME + " ='" + ashaname + "' AND " + CAMPID + " = " + campid + " " ;
        Log.i("ckm=>GetPadaFromAsha",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PadanashaworkerData msg = new PadanashaworkerData();
                msg.setPada(cursor.getString(cursor.getColumnIndex(ASHPADA)));
                arrayList.add(msg);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }
    public static ArrayList<PadanashaworkerData> getPadafromAnm(String campid,String anmname) {
        open();
        ArrayList<PadanashaworkerData> arrayList = new ArrayList<>();
        arrayList.clear();
        String selectQuery = "SELECT pada FROM padaNashworkerTable " +  " where " + ASHANMNAME + " ='" + anmname + "' AND " + CAMPID + " = " + campid + " " ;
        Log.i("ckm=>GetPadaFromANm",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PadanashaworkerData msg = new PadanashaworkerData();
                msg.setPada(cursor.getString(cursor.getColumnIndex(ASHPADA)));
                arrayList.add(msg);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }

    public static ArrayList<PadanashaworkerData> getOnlyPada(String campid) {
        open();
        ArrayList<PadanashaworkerData> arrayList = new ArrayList<>();
        arrayList.clear();
        String selectQuery = "SELECT DISTINCT pada FROM padaNashworkerTable " +  " where "  + CAMPID + " = " + campid + " " ;
        Log.i("ckm=>GetPadaFromAsha",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PadanashaworkerData msg = new PadanashaworkerData();
                msg.setPada(cursor.getString(cursor.getColumnIndex(ASHPADA)));
                arrayList.add(msg);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }

    public static ArrayList<PadanashaworkerData> getOnlyVillage(String campid) {
        open();
        ArrayList<PadanashaworkerData> arrayList = new ArrayList<>();
        arrayList.clear();
        String selectQuery = "SELECT DISTINCT village FROM padaNashworkerTable " +  " where "  + CAMPID + " = " + campid + " " ;
        Log.i("ckm=>GetPadaFromAsha",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PadanashaworkerData msg = new PadanashaworkerData();
                msg.setVillage(cursor.getString(cursor.getColumnIndex(ASHVILLAGE)));
                arrayList.add(msg);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }




    public static ArrayList<PadanashaworkerData> getcampprogramdata(String userid,String phc,String subcenter,String village,String pada,String campid) {
        open();
        ArrayList<PadanashaworkerData> arrayList = new ArrayList<>();
        arrayList.clear();
        String selectQuery = "SELECT  *  FROM " + TABLE_PADANASHAWORKERDETAILS +  " where " + USERID + " = '" + userid + "' AND " +  ASHPHC + " ='" + phc + "' AND "+  ASHSUBCENTRE + " ='" + subcenter + "' AND "+ ASHVILLAGE + "='" + village  +   "' AND "+  ASHPADA +  "='" + pada + "' AND "+ CAMPID + "='" + campid +"' " ;
        Log.i("ckm=>repeted",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PadanashaworkerData msg = new PadanashaworkerData();
             //   msg.setId(cursor.getInt(cursor.getColumnIndex(ASHID)));
                msg.setCampid(cursor.getInt(cursor.getColumnIndex(CAMPID)));
                msg.setCampno(cursor.getString(cursor.getColumnIndex(CAMPNO)));
                msg.setUserid(cursor.getInt(cursor.getColumnIndex(USERID)));
                msg.setOutreachdate(cursor.getString(cursor.getColumnIndex(ASHOUTREACHDATE)));
                msg.setDateofdataenter(cursor.getString(cursor.getColumnIndex(DATEOFENTERDATA)));
                msg.setPada(cursor.getString(cursor.getColumnIndex(ASHPADA)));
                msg.setVillage(cursor.getString(cursor.getColumnIndex(ASHVILLAGE)));
                msg.setSubcenter(cursor.getString(cursor.getColumnIndex(ASHSUBCENTRE)));
                msg.setPhc(cursor.getString(cursor.getColumnIndex(ASHPHC)));
                msg.setAshaworkername(cursor.getString(cursor.getColumnIndex(ASHWORKERNAME)));
                msg.setAshaworkermobile(cursor.getString(cursor.getColumnIndex(ASHWORKERMOBILENO)));
                msg.setAnmname(cursor.getString(cursor.getColumnIndex(ASHANMNAME)));
                msg.setAnmmobileno(cursor.getString(cursor.getColumnIndex(ASHANMMOBILE)));
                msg.setGpname(cursor.getString(cursor.getColumnIndex(ASHGPNAME)));
                msg.setContactpersonname(cursor.getString(cursor.getColumnIndex(ASHGPCONTACTNAME)));
                msg.setCpmobile(cursor.getString(cursor.getColumnIndex(ASHCPMOBILENO)));
                msg.setId(cursor.getInt(cursor.getColumnIndex(ASHID)));
                msg.setOutreachdetailsid(cursor.getInt(cursor.getColumnIndex(OUTREACHDETAILSID)));
                arrayList.add(msg);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }

    public static ArrayList<PadanashaworkerData> getallpadadetails(String campid) {
        open();
        ArrayList<PadanashaworkerData> arrayList = new ArrayList<>();
        arrayList.clear();
        String selectQuery = "SELECT  *  FROM " + TABLE_PADANASHAWORKERDETAILS +  " where " + CAMPID + " = '" + campid  +"' " ;
        Log.i("ckm=>AllPada",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PadanashaworkerData msg = new PadanashaworkerData();
                msg.setId(cursor.getInt(cursor.getColumnIndex(ASHID)));
                msg.setCampid(cursor.getInt(cursor.getColumnIndex(CAMPID)));
                msg.setCampno(cursor.getString(cursor.getColumnIndex(CAMPNO)));
                msg.setUserid(cursor.getInt(cursor.getColumnIndex(USERID)));
                msg.setOutreachdate(cursor.getString(cursor.getColumnIndex(ASHOUTREACHDATE)));
                msg.setDateofdataenter(cursor.getString(cursor.getColumnIndex(DATEOFENTERDATA)));
                msg.setPada(cursor.getString(cursor.getColumnIndex(ASHPADA)));
                msg.setVillage(cursor.getString(cursor.getColumnIndex(ASHVILLAGE)));
                msg.setSubcenter(cursor.getString(cursor.getColumnIndex(ASHSUBCENTRE)));
                msg.setPhc(cursor.getString(cursor.getColumnIndex(ASHPHC)));
                msg.setAshaworkername(cursor.getString(cursor.getColumnIndex(ASHWORKERNAME)));
                msg.setAshaworkermobile(cursor.getString(cursor.getColumnIndex(ASHWORKERMOBILENO)));
                msg.setAnmname(cursor.getString(cursor.getColumnIndex(ASHANMNAME)));
                msg.setAnmmobileno(cursor.getString(cursor.getColumnIndex(ASHANMMOBILE)));
                msg.setGpname(cursor.getString(cursor.getColumnIndex(ASHGPNAME)));
                msg.setContactpersonname(cursor.getString(cursor.getColumnIndex(ASHGPCONTACTNAME)));
                msg.setCpmobile(cursor.getString(cursor.getColumnIndex(ASHCPMOBILENO)));
                msg.setId(cursor.getInt(cursor.getColumnIndex(ASHID)));
                msg.setOutreachdetailsid(cursor.getInt(cursor.getColumnIndex(OUTREACHDETAILSID)));


                ArrayList<PopMedicalData> popData = new ArrayList<>();
                popData = PopulationMedicalModel.getall(cursor.getString(cursor.getColumnIndex(ASHID)),campid);
                msg.setPopulationdetails(popData);

                arrayList.add(msg);

            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }

    public static void deletePadaNAshaworker()
    {
        open();
        database.execSQL("delete  from "+ TABLE_PADANASHAWORKERDETAILS);
        close();
    }


}
