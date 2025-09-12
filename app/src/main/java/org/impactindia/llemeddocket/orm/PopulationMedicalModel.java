package org.impactindia.llemeddocket.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.impactindia.llemeddocket.DatabaseHelper;
import org.impactindia.llemeddocket.pojo.PopMedicalData;

import java.util.ArrayList;

public class  PopulationMedicalModel {

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

    public static String TABLE_POPULATIONMEDICAL = "populationNmedical";
    public static String MPOPULATION_ID = "pid";
    public static String MPHCPADAID = "padaAshaID";
    public static String MCAMPID = "campId";
    public static String MUSERID = "userid";
    public static String MOUTREACHMEMBERDETAILID = "outreachmemberdetailid";
    public static String MOUTREACHDETAILSID = "outreachdetailsid";
    public static String MHOUSEHOLDNO = "housholdid";
    public static String MTITLE = "title";
    public static String MFNAME = "fname";
    public static String MMNAME = "mname";
    public static String MLNAME = "lname";
    public static String MRELATION = "relation";
    public static String MRELATIONDETAILS = "relationdetails";
    public static String MGENDER = "gender";
    public static String MDOB = "dob";
    public static String MAGE = "age";
    public static String MUNIT = "unit";
    public static String MHEIGHTFIT = "heightfit";
    public static String MHEIGHTINCH = "heightinch";
    public static String MWEIGHT = "weight";
    public static String MBMI = "bmi";
    public static String MBMIINTER = "bmiinter";
    public static String WEIGHTUNIT = "weightunit";
    public static String MPULSE = "pulse";
    public static String MOXYSATURATION = "oxysaturation";
    public static String MBPSYSTOLIC = "bpsystolic";
    public static String MBPDIASTOLIC = "bpdiastolic";
    public static String MBPINTERPRETN = "bpinterpretn";
    public static String MBLOODSUGAR = "bloodsugar";
    public static String MBLOODSUGARINTERPRETN = "bloodsugarierpretn";
    public static String MVISUALACURITYRIGHTEYENEAR = "visualacuityrighteyenear";
    public static String MVISUALACURITYRIGHTEYEDISTANCE = "visualacuityrighteyedistant";
    public static String MVISUALACURITYRIGHTEYEINTERPR = "visualacuityrighteyeinter";
    public static String MVISUALACURITYLEFTEYENEAR = "visualacuitylefteyenear";
    public static String MVISUALACURITYLEFTEYEDISTANCE = "visualacuitylefteyedistance";
    public static String MVISUALACURITYLEFTEYEINTER = "visualacuitylefteyeinter";
    public static String MDIABETES = "diabetes";
    public static String MHYPERTENSION = "hypertension";
    public static String MCATARACT = "cataract";
    public static String MCATARACTDIAGONESD = "cataractdiagnosed";
    public static String MCATARACTDETAILS = "cataractdetails";
    public static String MEPILEPSY = "epilepsy";
    public static String MEPILEPSYDETAILS = "epilepsydetails";
    public static String MEPILEPSYDIAGONESD = "epilepsydiagnosed";
    public static String MORALPROBLEM = "oralproblem";
    public static String MORALPRODETAILS = "oralprodetails";
    public static String MORALPROBLEMDIAGNOSED = "oralproblemdiagnoesd";
    public static String MTB = "tb";
    public static String MTBDETAILS = "tbdetails";
    public static String MTBDIAGNOSED = "tbdiagnosed";
    public static String MISCHEMICHERTDISEASE = "ischemicheartdisease";
    public static String MISCHEMICHEADISDETAILS = "ischemicheadisdetails";
    public static String MISCHEMICHEADIAGNOSED = "ischemicheadisddiagnosed";
    public static String MSTROKE = "stroke";
    public static String MSTROKEDETAILS = "strokedetails";
    public static String MSTROKEDIAGNOSED = "strokediagnosed";
    public static String MCANCER = "cancer";
    public static String MCANCERDETAILS = "cancerdetails";
    public static String MCANCERDIAGNOSED = "cancerdiagnosed";
    public static String MCOGENITALABNORMALITIES = "cogenitalabnormalities";
    public static String MCOGENITALDETAILS = "cogenitaldetails";
    public static String MHEARINGDEFECT = "hearingdefect";
    public static String MHEARINGDEFECTDETAILS = "hearingdefectdetails";
    public static String MHEARINGDEFECTDIAGNOSED = "hearingdefectdiagnosed";
    public static String MEARPROB = "earprob";
    public static String MGYNAECPROB = "gynaecprob";
    public static String MGYNAECDETAILS = "gynaecdetails";
    public static String MGYNAECDIAGNOSED = "gynaecdiagnosed";
    public static String MTOBACCO = "tobacco";
    public static String MSMOKER = "smoker";
    public static String MALCOHOL = "alcohol";
    public static String MEYEOPD = "eyeopd";
    public static String MEYESUERGERY = "eyesurgery";
    public static String MEPILEPSYOPD = "epilepsyopd";
    public static String MDENTALOPD = "dentalopd";
    public static String MPLASTICSURGERYOPD = "plasticsurgeryopd";
    public static String MORTHOPEDICSURGERY = "orthopedicsurgery";
    public static String MENTOPD = "entopd";
    public static String MGYNAECOPD = "gynaecopd";
    public static String MPINCODE = "pincode";
    public static String MMOBILENO = "mobileno";
    public static String MSTREETNAME = "streetname";
    public static String MRESIDENCENO = "residenceno";
    public static String MAADHARNO = "aadharno";
    public static String MVOTERID = "voteridno";
    public static String MEMAILD = "emailid";
    public static String MREFERREDBY = "referredby";
    public static String MTAGID = "tagid";
    public static String MTAGNAME = "tagname";
    public static String MEXTRS1 = "extra1";
    public static String MORALCANCEROPD = "oralcanceropd";
    public static String ISFRESH = "isfresh";
    public static String MEXTRA = "extra";
    public static String DATEOFEXAMINATION = "dateofexamination";
    public static String EARPROBDETAILS = "earprobdetails";
    public static String PATIENTREFUSED = "patientrefused";



    public static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_POPULATIONMEDICAL + " (" +
            MPOPULATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MPHCPADAID + " TEXT, " +
            MCAMPID + " TEXT, " +
            MUSERID + " TEXT, " +
            MOUTREACHMEMBERDETAILID + " TEXT, " +
            MOUTREACHDETAILSID + " TEXT, " +
            MHOUSEHOLDNO + " TEXT, " +
            DATEOFEXAMINATION + " TEXT, " +
            EARPROBDETAILS + " TEXT, " +
            PATIENTREFUSED + " INTEGER, " +
            MTITLE + " TEXT, " +
            MFNAME + " TEXT, " +
            MMNAME + " TEXT, " +
            MLNAME + " TEXT, " +
            MRELATION + " TEXT, " +
            MRELATIONDETAILS + " TEXT, " +
            MGENDER + " TEXT, " +
            MDOB + " TEXT, " +
            MAGE + " TEXT, " +
            MUNIT + " TEXT, " +
            MHEIGHTFIT + " TEXT, " +
            MHEIGHTINCH + " TEXT, " +
            MWEIGHT + " TEXT, " +
            WEIGHTUNIT + " TEXT, " +
            MBMI + " TEXT, " +
            MBMIINTER + " TEXT, " +
            MPULSE + " TEXT, " +
            MOXYSATURATION + " TEXT, " +
            MBPSYSTOLIC + " TEXT, " +
            MBPDIASTOLIC + " TEXT, " +
            MBPINTERPRETN + " TEXT, " +
            MBLOODSUGAR + " TEXT, " +
            MBLOODSUGARINTERPRETN + " TEXT, " +
            MVISUALACURITYRIGHTEYENEAR + " TEXT, " +
            MVISUALACURITYRIGHTEYEDISTANCE + " TEXT, " +
            MVISUALACURITYRIGHTEYEINTERPR + " TEXT, " +
            MVISUALACURITYLEFTEYENEAR + " TEXT, " +
            MVISUALACURITYLEFTEYEDISTANCE + " TEXT, " +
            MVISUALACURITYLEFTEYEINTER + " TEXT, " +
            MDIABETES + " TEXT, " +
            MHYPERTENSION + " TEXT, " +
            MCATARACT + " TEXT, " +
            MCATARACTDIAGONESD + " TEXT, " +
            MCATARACTDETAILS + " TEXT, " +
            MEPILEPSY + " TEXT, " +
            MEPILEPSYDETAILS + " TEXT, " +
            MEPILEPSYDIAGONESD + " TEXT, " +
            MORALPROBLEM + " TEXT, " +
            MORALPRODETAILS + " TEXT, " +
            MORALPROBLEMDIAGNOSED + " TEXT, " +
            MTB + " TEXT, " +
            MTBDETAILS + " TEXT, " +
            MTBDIAGNOSED + " TEXT, " +
            MISCHEMICHERTDISEASE + " TEXT, " +
            MISCHEMICHEADISDETAILS + " TEXT, " +
            MISCHEMICHEADIAGNOSED + " TEXT, " +
            MSTROKE + " TEXT, " +
            MSTROKEDETAILS + " TEXT, " +
            MSTROKEDIAGNOSED + " TEXT, " +
            MCANCER + " TEXT, " +
            MCANCERDETAILS + " TEXT, " +
            MCANCERDIAGNOSED + " TEXT, " +
            MCOGENITALABNORMALITIES + " TEXT, " +
            MCOGENITALDETAILS + " TEXT, " +
            MHEARINGDEFECT + " TEXT, " +
            MHEARINGDEFECTDETAILS + " TEXT, " +
            MHEARINGDEFECTDIAGNOSED + " TEXT, " +
            MEARPROB + " TEXT, " +
            MGYNAECPROB + " TEXT, " +
            MGYNAECDETAILS + " TEXT, " +
            MGYNAECDIAGNOSED + " TEXT, " +
            MGYNAECOPD + " TEXT, " +
            MTOBACCO + " TEXT, " +
            MSMOKER + " TEXT, " +
            MALCOHOL + " TEXT, " +
            MEYEOPD + " TEXT, " +
            MEYESUERGERY + " TEXT, " +
            MEPILEPSYOPD + " TEXT, " +
            MDENTALOPD + " TEXT, " +
            MPLASTICSURGERYOPD + " TEXT, " +
            MORTHOPEDICSURGERY + " TEXT, " +
            MENTOPD + " TEXT, " +
            MPINCODE + " TEXT, " +
            MMOBILENO + " TEXT, " +
            MSTREETNAME + " TEXT, " +
            MRESIDENCENO + " TEXT, " +
            MAADHARNO + " TEXT, " +
            MVOTERID + " TEXT, " +
            MEMAILD + " TEXT, " +
            MREFERREDBY + " TEXT, " +
            MORALCANCEROPD + " TEXT, " +
            MTAGID + " TEXT, " +
            MTAGNAME + " TEXT, " +
            MEXTRS1 + " TEXT, " +
            MEXTRA + " TEXT, " +
            ISFRESH + " TEXT " +
            ");";



    public static Long insert(PopMedicalData padanashaworkerData)
    {
        open();
        ContentValues cv = new ContentValues();
        cv.put(MPHCPADAID,padanashaworkerData.getPadashaid());
        cv.put(MCAMPID,padanashaworkerData.getCampid());
        cv.put(MUSERID,padanashaworkerData.getUserid());
        cv.put(MOUTREACHMEMBERDETAILID,padanashaworkerData.getOutreachmemberdetailid());
        cv.put(MHOUSEHOLDNO,padanashaworkerData.getHouseholdno());
        cv.put(MOUTREACHDETAILSID,padanashaworkerData.getOutreachdetailsid());
        cv.put(MTITLE,padanashaworkerData.getTitle());
        cv.put(MFNAME,padanashaworkerData.getFname());
        cv.put(MMNAME,padanashaworkerData.getMname());
        cv.put(MLNAME,padanashaworkerData.getLname());
        cv.put(MRELATION,padanashaworkerData.getRelation());
        cv.put(MRELATIONDETAILS,padanashaworkerData.getRelationdetails());
        cv.put(MGENDER,padanashaworkerData.getGender());
        cv.put(MDOB,padanashaworkerData.getDob());
        cv.put(MAGE,padanashaworkerData.getAge());
        cv.put(MUNIT,padanashaworkerData.getAgeunit());
        cv.put(MHEIGHTFIT,padanashaworkerData.getHeightinfit());
        cv.put(MHEIGHTINCH,padanashaworkerData.getHeightininches());
        cv.put(MWEIGHT,padanashaworkerData.getWeight());
        cv.put(WEIGHTUNIT,"kgs");
        cv.put(MBMI,padanashaworkerData.getBmi());
        cv.put(MBMIINTER,padanashaworkerData.getBmiinter());
        cv.put(MPULSE,padanashaworkerData.getPulse());
        cv.put(MOXYSATURATION,padanashaworkerData.getOxysaturation());
        cv.put(MBPSYSTOLIC,padanashaworkerData.getBpsystolic());
        cv.put(MBPDIASTOLIC,padanashaworkerData.getBpdiastolic());
        cv.put(MBPINTERPRETN,padanashaworkerData.getBpinter());
        cv.put(MBLOODSUGAR,padanashaworkerData.getBloodsugar());
        cv.put(MBLOODSUGARINTERPRETN,padanashaworkerData.getBloodsugarinter());
        cv.put(MVISUALACURITYRIGHTEYENEAR,padanashaworkerData.getVisualacuityrighteyenear());
        cv.put(MVISUALACURITYRIGHTEYEDISTANCE,padanashaworkerData.getVisualacuityrighteyedistant());
        cv.put(MVISUALACURITYRIGHTEYEINTERPR,padanashaworkerData.getVisualacuityrighteyenear());
        cv.put(MVISUALACURITYLEFTEYENEAR,padanashaworkerData.getVisualacuitylefteyenear());
        cv.put(MVISUALACURITYLEFTEYEDISTANCE,padanashaworkerData.getVisualacuitylefteyedistant());
        cv.put(MVISUALACURITYLEFTEYEINTER,padanashaworkerData.getVisualacuitylefteyeinter());
        cv.put(MDIABETES,padanashaworkerData.getDiabetes());
        cv.put(MHYPERTENSION,padanashaworkerData.getHypertension());
        cv.put(MCATARACT,padanashaworkerData.getCataract());
        cv.put(MCATARACTDIAGONESD,padanashaworkerData.getCartaractdiagno());
        cv.put(MCATARACTDETAILS,padanashaworkerData.getCataratdetails());
        cv.put(MEPILEPSY,padanashaworkerData.getEpilepsy());
        cv.put(MEPILEPSYDETAILS,padanashaworkerData.getEpilepsydetails());
        cv.put(MEPILEPSYDIAGONESD,padanashaworkerData.getEpilepsydiagnosed());
        cv.put(MORALPROBLEM,padanashaworkerData.getOralproblem());
        cv.put(MORALPRODETAILS,padanashaworkerData.getOralprodetails());
        cv.put(MORALPROBLEMDIAGNOSED,padanashaworkerData.getOralproblemdiagnoesd());
        cv.put(MTB,padanashaworkerData.getTb());
        cv.put(MTBDETAILS,padanashaworkerData.getTbdetails());
        cv.put(MTBDIAGNOSED,padanashaworkerData.getTbdiagnosed());
        cv.put(MISCHEMICHERTDISEASE,padanashaworkerData.getIschemicheartdisease());
        cv.put(MISCHEMICHEADISDETAILS,padanashaworkerData.getIschemicheadisdetails());
        cv.put(MISCHEMICHEADIAGNOSED,padanashaworkerData.getIschemicheadisddiagnosed());
        cv.put(MSTROKE,padanashaworkerData.getStroke());
        cv.put(MSTROKEDETAILS,padanashaworkerData.getStrokedetails());
        cv.put(MSTROKEDIAGNOSED,padanashaworkerData.getStrokediagnosed());
        cv.put(MCANCER,padanashaworkerData.getCancer());
        cv.put(MCANCERDETAILS,padanashaworkerData.getCancerdetails());
        cv.put(MCANCERDIAGNOSED ,padanashaworkerData.getCancerdiagnosed());
        cv.put(MCOGENITALABNORMALITIES,padanashaworkerData.getCogenitalabnormalities());
        cv.put(MCOGENITALDETAILS,padanashaworkerData.getCogenitaldetails());
        cv.put(MHEARINGDEFECT,padanashaworkerData.getHearingdefect());
        cv.put(MHEARINGDEFECTDETAILS,padanashaworkerData.getHearingdefectdetails());
        cv.put(MHEARINGDEFECTDIAGNOSED,padanashaworkerData.getHearingdefectdiagnosed());
        cv.put(MEARPROB,padanashaworkerData.getEarprob());
        cv.put(MGYNAECPROB,padanashaworkerData.getGynaecprob());
        cv.put(MGYNAECDETAILS,padanashaworkerData.getGynaecdetails());
        cv.put(MGYNAECDIAGNOSED,padanashaworkerData.getGynaecdiagnosed());
        cv.put(MTOBACCO,padanashaworkerData.getTobacco());
        cv.put(MSMOKER,padanashaworkerData.getSmoker());
        cv.put(MEYEOPD,padanashaworkerData.getEyeopd());
        cv.put(MEYESUERGERY,padanashaworkerData.getEyesurgery());
        cv.put(MEPILEPSYOPD,padanashaworkerData.getEpilepsyopd());
        cv.put(MDENTALOPD,padanashaworkerData.getDentalopd());
        cv.put(MPLASTICSURGERYOPD,padanashaworkerData.getPlasticsurgeryopd());
        cv.put(MENTOPD,padanashaworkerData.getEntopd());
        cv.put(MGYNAECOPD,padanashaworkerData.getGynaecopd());
        cv.put(MPINCODE,padanashaworkerData.getPincode());
        cv.put(MMOBILENO,padanashaworkerData.getMobileno());
        cv.put(MSTREETNAME,padanashaworkerData.getStreetname());
        cv.put(MRESIDENCENO,padanashaworkerData.getResidenceno());
        cv.put(MAADHARNO,padanashaworkerData.getAadharno());
        cv.put(MVOTERID,padanashaworkerData.getVoteridno());
        cv.put(MEMAILD,padanashaworkerData.getEmailid());
        cv.put(MREFERREDBY,padanashaworkerData.getReferredby());
        cv.put(ISFRESH,padanashaworkerData.getIsfresh());
        cv.put(PATIENTREFUSED,padanashaworkerData.getPatientrefused());
        cv.put(EARPROBDETAILS,padanashaworkerData.getEarprobdetails());
        cv.put(DATEOFEXAMINATION,padanashaworkerData.getDateofexamination());
        cv.put(MORALCANCEROPD,padanashaworkerData.getOralcanceropd());


        long result = database.insert(TABLE_POPULATIONMEDICAL, null, cv);
        if (result == -1)
        { Log.i("LastID","NOT INSERTED");
        }
        else
        {//Log.i("LastID",result+"");
        }
        close();
        return result;
    }

    public static ArrayList<PopMedicalData> getmatchhousehold(String housno, String membertype, String padaashaid) {
        open();
        ArrayList<PopMedicalData> arrayList = new ArrayList<>();
        arrayList.clear();
        String selectQuery = "SELECT  *  FROM " + TABLE_POPULATIONMEDICAL +  " where " + MHOUSEHOLDNO + " = '" +housno + "'   AND "+  MRELATION +  " = '" +  membertype + "'  AND "+  MPHCPADAID + " = '" +  padaashaid + "'  " ;
        Log.i("ckm=>PHCUsers",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PopMedicalData msg = new PopMedicalData();
                msg.setHouseholdno(cursor.getString(cursor.getColumnIndex(MHOUSEHOLDNO)));
                msg.setFname(cursor.getString(cursor.getColumnIndex(MFNAME)));
                arrayList.add(msg);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }

    public static ArrayList<PopMedicalData> getmatchhouse(String housno, String padaashaid) {
        open();
        ArrayList<PopMedicalData> arrayList = new ArrayList<>();
        arrayList.clear();
        String selectQuery = "SELECT * FROM " + TABLE_POPULATIONMEDICAL +  " where " + MHOUSEHOLDNO + " = '" +housno + "'   AND "+  MPHCPADAID + " = '" +  padaashaid + "' " ;
        Log.i("ckm=>GetPADAID",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PopMedicalData msg = new PopMedicalData();
                msg.setHouseholdno(cursor.getString(cursor.getColumnIndex(MHOUSEHOLDNO)));
                msg.setFname(cursor.getString(cursor.getColumnIndex(MFNAME)));
                arrayList.add(msg);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }

    public static ArrayList<PopMedicalData> getall(String id,String campid)
    {
        open();
        ArrayList<PopMedicalData> arrayList = new ArrayList<>();
        arrayList.clear();
        //String selectQuery = "SELECT  *  FROM " + TABLE_PADANASHAWORKERDETAILS +  " where " + DATEOFENTERDATA + " = '" +date + "' AND " +  ASHPHC + " ='" + phc + "' AND "+  ASHSUBCENTRE + " ='" + subcenter + "' AND "+ ASHVILLAGE + "='" + village  +   "' AND "+  ASHPADA +  "='" + pada + "' " ;
        String selectQuery = "SELECT  *  FROM " + TABLE_POPULATIONMEDICAL  + " where " + MPHCPADAID + " = '" + id + "'  AND " + MCAMPID +  " = '" + campid + "'  AND " + ISFRESH + " = '" + 0 + "' ";

        Log.i("ckm=>repeted",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                PopMedicalData msg = new PopMedicalData();
                //msg.setOutreachmemberdetailid();
                //MPOPULATION_ID
                msg.setId(cursor.getInt(cursor.getColumnIndex(MPOPULATION_ID)));
                msg.setPadashaid(cursor.getInt(cursor.getColumnIndex(MPHCPADAID)));
                msg.setCampid(cursor.getInt(cursor.getColumnIndex(MCAMPID)));
                msg.setUserid(cursor.getInt(cursor.getColumnIndex(MUSERID)));
                msg.setOutreachmemberdetailid(cursor.getInt(cursor.getColumnIndex(MOUTREACHMEMBERDETAILID)));
                msg.setOutreachdetailsid(cursor.getInt(cursor.getColumnIndex(MOUTREACHDETAILSID)));
                msg.setHouseholdno(cursor.getString(cursor.getColumnIndex(MHOUSEHOLDNO)));
                msg.setTitle(cursor.getString(cursor.getColumnIndex(MTITLE)));
                msg.setFname(cursor.getString(cursor.getColumnIndex(MFNAME)));
                msg.setMname(cursor.getString(cursor.getColumnIndex(MMNAME)));
                msg.setLname(cursor.getString(cursor.getColumnIndex(MLNAME)));
                msg.setRelation(cursor.getString(cursor.getColumnIndex(MRELATION)));
                msg.setRelationdetails(cursor.getString(cursor.getColumnIndex(MRELATIONDETAILS)));
                msg.setGender(cursor.getString(cursor.getColumnIndex(MGENDER)));
                msg.setDob(cursor.getString(cursor.getColumnIndex(MDOB)));
                msg.setAge(cursor.getInt(cursor.getColumnIndex(MAGE)));
                msg.setAgeunit(cursor.getString(cursor.getColumnIndex(MUNIT)));
                msg.setHeightinfit(cursor.getInt(cursor.getColumnIndex(MHEIGHTFIT)));
                msg.setHeightininches(cursor.getInt(cursor.getColumnIndex(MHEIGHTINCH)));
                msg.setWeight(cursor.getInt(cursor.getColumnIndex(MWEIGHT)));
                msg.setBmi(cursor.getString(cursor.getColumnIndex(MBMI)));
                msg.setBmiinter(cursor.getString(cursor.getColumnIndex(MBMIINTER)));
                msg.setPulse(cursor.getInt(cursor.getColumnIndex(MPULSE)));
                msg.setOxysaturation(cursor.getInt(cursor.getColumnIndex(MOXYSATURATION)));
                msg.setBpsystolic(cursor.getInt(cursor.getColumnIndex(MBPSYSTOLIC)));
                msg.setBpdiastolic(cursor.getInt(cursor.getColumnIndex(MBPDIASTOLIC)));
                msg.setBpinter(cursor.getString(cursor.getColumnIndex(MBPINTERPRETN)));
                msg.setBloodsugar(cursor.getInt(cursor.getColumnIndex(MBLOODSUGAR)));
                msg.setBloodsugarinter(cursor.getString(cursor.getColumnIndex(MBLOODSUGARINTERPRETN)));
                msg.setVisualacuityrighteyenear(cursor.getString(cursor.getColumnIndex(MVISUALACURITYRIGHTEYENEAR)));
                msg.setVisualacuityrighteyedistant(cursor.getString(cursor.getColumnIndex(MVISUALACURITYRIGHTEYEDISTANCE)));
                msg.setVisualacuityrighteyeinter(cursor.getString(cursor.getColumnIndex(MVISUALACURITYRIGHTEYEINTERPR)));
                msg.setVisualacuitylefteyenear(cursor.getString(cursor.getColumnIndex(MVISUALACURITYLEFTEYENEAR)));
                msg.setVisualacuitylefteyedistant(cursor.getString(cursor.getColumnIndex(MVISUALACURITYLEFTEYEDISTANCE)));
                msg.setVisualacuitylefteyeinter(cursor.getString(cursor.getColumnIndex(MVISUALACURITYLEFTEYEINTER)));
                msg.setDiabetes(cursor.getString(cursor.getColumnIndex(MDIABETES)));
                msg.setWeightunit("kgs");
                msg.setHypertension(cursor.getString(cursor.getColumnIndex(MHYPERTENSION)));
                msg.setCataract(cursor.getString(cursor.getColumnIndex(MCATARACT)));
                msg.setCartaractdiagno(cursor.getString(cursor.getColumnIndex(MCATARACTDIAGONESD)));
                msg.setCataratdetails(cursor.getString(cursor.getColumnIndex(MCATARACTDETAILS)));
                msg.setEpilepsy(cursor.getString(cursor.getColumnIndex(MEPILEPSY)));
                msg.setEpilepsydetails(cursor.getString(cursor.getColumnIndex(MEPILEPSYDETAILS)));
                msg.setEpilepsydiagnosed(cursor.getString(cursor.getColumnIndex(MEPILEPSYDIAGONESD)));
                msg.setOralproblem(cursor.getString(cursor.getColumnIndex(MORALPROBLEM)));
                msg.setOralprodetails(cursor.getString(cursor.getColumnIndex(MORALPRODETAILS)));
                msg.setOralproblemdiagnoesd(cursor.getString(cursor.getColumnIndex(MORALPROBLEMDIAGNOSED)));
                msg.setTb(cursor.getString(cursor.getColumnIndex(MTB)));
                msg.setTbdetails(cursor.getString(cursor.getColumnIndex(MTBDETAILS)));
                msg.setTbdiagnosed(cursor.getString(cursor.getColumnIndex(MTBDIAGNOSED)));
                msg.setIschemicheartdisease(cursor.getString(cursor.getColumnIndex(MISCHEMICHERTDISEASE)));
                msg.setIschemicheadisdetails(cursor.getString(cursor.getColumnIndex(MISCHEMICHEADISDETAILS)));
                msg.setIschemicheadisddiagnosed(cursor.getString(cursor.getColumnIndex(MISCHEMICHEADIAGNOSED)));
                msg.setStroke(cursor.getString(cursor.getColumnIndex(MSTROKE)));
                msg.setStrokedetails(cursor.getString(cursor.getColumnIndex(MSTROKEDETAILS)));
                msg.setStrokediagnosed(cursor.getString(cursor.getColumnIndex(MSTROKEDIAGNOSED)));
                msg.setCancer(cursor.getString(cursor.getColumnIndex(MCANCER)));
                msg.setCancerdetails(cursor.getString(cursor.getColumnIndex(MCANCERDETAILS)));
                msg.setCancerdiagnosed(cursor.getString(cursor.getColumnIndex(MCANCERDIAGNOSED)));
                msg.setCogenitalabnormalities(cursor.getString(cursor.getColumnIndex(MCOGENITALABNORMALITIES)));
                msg.setCogenitaldetails(cursor.getString(cursor.getColumnIndex(MCOGENITALDETAILS)));
                msg.setHearingdefect(cursor.getString(cursor.getColumnIndex(MHEARINGDEFECT)));
                msg.setHearingdefectdetails(cursor.getString(cursor.getColumnIndex(MHEARINGDEFECTDETAILS)));
                msg.setHearingdefectdiagnosed(cursor.getString(cursor.getColumnIndex(MHEARINGDEFECTDIAGNOSED)));
                msg.setEarprob(cursor.getString(cursor.getColumnIndex(MEARPROB)));
                msg.setGynaecprob(cursor.getString(cursor.getColumnIndex(MGYNAECPROB)));
                msg.setGynaecdetails(cursor.getString(cursor.getColumnIndex(MGYNAECDETAILS)));
                msg.setGynaecdiagnosed(cursor.getString(cursor.getColumnIndex(MGYNAECDIAGNOSED)));
                msg.setGynaecopd(cursor.getString(cursor.getColumnIndex(MGYNAECOPD)));
                msg.setTobacco(cursor.getString(cursor.getColumnIndex(MTOBACCO)));
                msg.setSmoker(cursor.getString(cursor.getColumnIndex(MSMOKER)));
                msg.setAlcohol(cursor.getString(cursor.getColumnIndex(MALCOHOL)));
                msg.setEyesurgery(cursor.getString(cursor.getColumnIndex(MEYESUERGERY)));
                msg.setEpilepsyopd(cursor.getString(cursor.getColumnIndex(MEPILEPSYOPD)));
                msg.setDentalopd(cursor.getString(cursor.getColumnIndex(MDENTALOPD)));
                msg.setPlasticsurgeryopd(cursor.getString(cursor.getColumnIndex(MPLASTICSURGERYOPD)));
                msg.setOrthopedicsurgery(cursor.getString(cursor.getColumnIndex(MORTHOPEDICSURGERY)));
                msg.setEyeopd(cursor.getString(cursor.getColumnIndex(MEYEOPD)));
                msg.setEntopd(cursor.getString(cursor.getColumnIndex(MENTOPD)));
                msg.setPincode(cursor.getInt(cursor.getColumnIndex(MPINCODE)));
                msg.setMobileno(cursor.getString(cursor.getColumnIndex(MMOBILENO)));
                msg.setStreetname(cursor.getString(cursor.getColumnIndex(MSTREETNAME)));
                msg.setResidenceno(cursor.getString(cursor.getColumnIndex(MRESIDENCENO)));
                msg.setAadharno(cursor.getString(cursor.getColumnIndex(MAADHARNO)));
                msg.setVoteridno(cursor.getString(cursor.getColumnIndex(MVOTERID)));
                msg.setEmailid(cursor.getString(cursor.getColumnIndex(MEMAILD)));
                msg.setReferredby(cursor.getString(cursor.getColumnIndex(MREFERREDBY)));
                msg.setIsfresh(cursor.getString(cursor.getColumnIndex(ISFRESH)));
                msg.setExtra(cursor.getString(cursor.getColumnIndex(MEXTRA)));
                msg.setPatientrefused(cursor.getInt(cursor.getColumnIndex(PATIENTREFUSED)));
                msg.setEarprobdetails(cursor.getString(cursor.getColumnIndex(EARPROBDETAILS)));
                msg.setDateofexamination(cursor.getString(cursor.getColumnIndex(DATEOFEXAMINATION)));
                msg.setOralcanceropd(cursor.getString(cursor.getColumnIndex(MORALCANCEROPD)));
                arrayList.add(msg);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }



//For Heath Data Update
    public static void  Updatehealthdata(String id, PopMedicalData popMedicalData)
    {
        open();
        ContentValues contentValues = new ContentValues();

        contentValues.put(MHEIGHTFIT, popMedicalData.getHeightinfit());
        contentValues.put(MHEIGHTINCH, popMedicalData.getHeightininches());
        contentValues.put(MWEIGHT, popMedicalData.getWeight());
        contentValues.put(MBMI, popMedicalData.getBmi());
        contentValues.put(MBMIINTER, popMedicalData.getBmiinter());
        contentValues.put(MBLOODSUGAR, popMedicalData.getBloodsugar());
        contentValues.put(MBLOODSUGARINTERPRETN, popMedicalData.getBloodsugarinter());
        contentValues.put(MOXYSATURATION,popMedicalData.getOxysaturation());
        contentValues.put(MPULSE, popMedicalData.getPulse());
        contentValues.put(MBPSYSTOLIC, popMedicalData.getBpsystolic());
        contentValues.put(MBPDIASTOLIC,popMedicalData.getBpdiastolic());
        contentValues.put(MBPINTERPRETN, popMedicalData.getBpinter());
        contentValues.put(PATIENTREFUSED, popMedicalData.getPatientrefused());
        contentValues.put(MVISUALACURITYLEFTEYEDISTANCE,popMedicalData.getVisualacuitylefteyedistant());
        contentValues.put(MVISUALACURITYLEFTEYENEAR,popMedicalData.getVisualacuitylefteyenear());
        contentValues.put(MVISUALACURITYLEFTEYEINTER,popMedicalData.getVisualacuitylefteyeinter());
        contentValues.put(MVISUALACURITYRIGHTEYEDISTANCE,popMedicalData.getVisualacuityrighteyedistant());
        contentValues.put(MVISUALACURITYRIGHTEYENEAR,popMedicalData.getVisualacuityrighteyenear());
        contentValues.put(MVISUALACURITYRIGHTEYEINTERPR,popMedicalData.getVisualacuityrighteyeinter());

        Log.i("SQLITE Data",id);
        database.update(TABLE_POPULATIONMEDICAL ,contentValues, "pid = "+ " '" + id +  "' " , null);
    }

    public static void  UpdateSynchresult(String id,String outreachmembid,String outreachdetailsid)
    {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MOUTREACHMEMBERDETAILID, outreachmembid);
        contentValues.put(MOUTREACHDETAILSID, outreachdetailsid);
        contentValues.put(ISFRESH, "1");
        Log.i("SQLITE Data",id);
        database.update(TABLE_POPULATIONMEDICAL , contentValues, "pid = "+ " '" + id +  "' " , null);
    }


    public static void  UpdateCalltoll(String id, PopMedicalData padanashaworkerData) {
        open();
        ContentValues cv = new ContentValues();
        cv.put(MEYEOPD,padanashaworkerData.getEyeopd());
        cv.put(MEYESUERGERY,padanashaworkerData.getEyesurgery());
        cv.put(MEPILEPSYOPD,padanashaworkerData.getEpilepsyopd());
        cv.put(MDENTALOPD,padanashaworkerData.getDentalopd());
        cv.put(MPLASTICSURGERYOPD,padanashaworkerData.getPlasticsurgeryopd());
        cv.put(MORTHOPEDICSURGERY,padanashaworkerData.getOrthopedicsurgery());
        cv.put(MENTOPD,padanashaworkerData.getEntopd());
        cv.put(MGYNAECOPD,padanashaworkerData.getGynaecopd());
        cv.put(MORALCANCEROPD,padanashaworkerData.getOralcanceropd());
         database.update(TABLE_POPULATIONMEDICAL , cv, "pid = "+ " '" + id +  "' " , null);
    }

    public static void  UpdateMedicalData(String id, PopMedicalData padanashaworkerData)
    {
        open();
        ContentValues cv = new ContentValues();
        cv.put(MDIABETES,padanashaworkerData.getDiabetes());
        cv.put(MHYPERTENSION,padanashaworkerData.getHypertension());
        cv.put(MCATARACT,padanashaworkerData.getCataract());
        cv.put(MCATARACTDIAGONESD,padanashaworkerData.getCartaractdiagno());
        cv.put(MCATARACTDETAILS,padanashaworkerData.getCataratdetails());
        cv.put(MEPILEPSY,padanashaworkerData.getEpilepsy());
        cv.put(MEPILEPSYDETAILS,padanashaworkerData.getEpilepsydetails());
        cv.put(MEPILEPSYDIAGONESD,padanashaworkerData.getEpilepsydiagnosed());
        cv.put(MORALPROBLEM,padanashaworkerData.getOralproblem());
        cv.put(MORALPRODETAILS,padanashaworkerData.getOralprodetails());
        cv.put(MORALPROBLEMDIAGNOSED,padanashaworkerData.getOralproblemdiagnoesd());
        cv.put(MTB,padanashaworkerData.getTb());
        cv.put(EARPROBDETAILS,padanashaworkerData.getEarprobdetails());
        cv.put(MTBDETAILS,padanashaworkerData.getTbdetails());
        cv.put(MTBDIAGNOSED,padanashaworkerData.getTbdiagnosed());
        cv.put(MISCHEMICHERTDISEASE,padanashaworkerData.getIschemicheartdisease());
        cv.put(MISCHEMICHEADISDETAILS,padanashaworkerData.getIschemicheadisdetails());
        cv.put(MISCHEMICHEADIAGNOSED,padanashaworkerData.getIschemicheadisddiagnosed());
        cv.put(MSTROKE,padanashaworkerData.getStroke());
        cv.put(MSTROKEDETAILS,padanashaworkerData.getStrokedetails());
        cv.put(MSTROKEDIAGNOSED,padanashaworkerData.getStrokediagnosed());
        cv.put(MCANCER,padanashaworkerData.getCancer());
        cv.put(MCANCERDETAILS,padanashaworkerData.getCancerdetails());
        cv.put(MCANCERDIAGNOSED ,padanashaworkerData.getCancerdiagnosed());
        cv.put(MCOGENITALABNORMALITIES,padanashaworkerData.getCogenitalabnormalities());
        cv.put(MHEARINGDEFECT,padanashaworkerData.getHearingdefect());
        cv.put(MHEARINGDEFECTDETAILS,padanashaworkerData.getHearingdefectdetails());
        cv.put(MHEARINGDEFECTDIAGNOSED,padanashaworkerData.getHearingdefectdiagnosed());
        cv.put(MEARPROB,padanashaworkerData.getEarprob());
        cv.put(MGYNAECPROB,padanashaworkerData.getGynaecprob());
        cv.put(MGYNAECDETAILS,padanashaworkerData.getGynaecdetails());
        cv.put(MGYNAECDIAGNOSED,padanashaworkerData.getGynaecdiagnosed());
        cv.put(MTOBACCO,padanashaworkerData.getTobacco());
        cv.put(MSMOKER,padanashaworkerData.getSmoker());
       // cv.put(MEYEOPD,padanashaworkerData.getEyeopd());
        cv.put(MALCOHOL,padanashaworkerData.getAlcohol());
        Log.i("ckm=>db",padanashaworkerData.getAlcohol() + " " +padanashaworkerData.getGynaecprob());

        Log.i("SQLITE Data",id);
        database.update(TABLE_POPULATIONMEDICAL , cv, "pid = "+ " '" + id +  "' " , null);
    }


    public static ArrayList<PopMedicalData> getpadanPopulationdetails(String campid) {
        open();
        ArrayList<PopMedicalData> arrayList = new ArrayList<>();
        arrayList.clear();
        //String selectQuery = "SELECT  *  FROM " + TABLE_POPULATIONMEDICAL  ;
        String selectQuery = "SELECT populationNmedical.campId,populationNmedical.userid,populationNmedical.pid," +
                "populationNmedical.housholdid,padaNashworkerTable.dateofdata,padaNashworkerTable.pada,padaNashworkerTable.village,padaNashworkerTable.subcentre," +
                "padaNashworkerTable.phc,padaNashworkerTable.anmname,padaNashworkerTable.anmmobile,populationNmedical.fname,populationNmedical.mname,populationNmedical.lname," +
                "populationNmedical.isfresh,populationNmedical.dateofexamination,populationNmedical.gender,populationNmedical.age FROM  padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE populationNmedical.campId =  '"+ campid + "'   ORDER BY pid DESC;";
        Log.i( "ckm=>joinedquery",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PopMedicalData popMedicalData = new PopMedicalData();
                popMedicalData.setCampid(cursor.getInt(cursor.getColumnIndex(MCAMPID)));
                popMedicalData.setUserid(cursor.getInt(cursor.getColumnIndex(MUSERID)));
                popMedicalData.setId(cursor.getInt(cursor.getColumnIndex(MPOPULATION_ID)));
                popMedicalData.setHouseholdno(cursor.getString(cursor.getColumnIndex(MHOUSEHOLDNO)));
                popMedicalData.setDateofdata(cursor.getString(cursor.getColumnIndex("dateofdata")));
                popMedicalData.setPada(cursor.getString(cursor.getColumnIndex("pada")));
                popMedicalData.setVillage(cursor.getString(cursor.getColumnIndex("village")));
                popMedicalData.setSubcentre(cursor.getString(cursor.getColumnIndex("subcentre")));
                popMedicalData.setPhc(cursor.getString(cursor.getColumnIndex("phc")));
                popMedicalData.setFname(cursor.getString(cursor.getColumnIndex(MFNAME)));
                popMedicalData.setMname(cursor.getString(cursor.getColumnIndex(MMNAME)));
                popMedicalData.setGender(cursor.getString(cursor.getColumnIndex(MGENDER)));
                popMedicalData.setAge(cursor.getInt(cursor.getColumnIndex(MAGE)));
                popMedicalData.setLname(cursor.getString(cursor.getColumnIndex(MLNAME)));
                popMedicalData.setIsfresh(cursor.getString(cursor.getColumnIndex(ISFRESH)));
                popMedicalData.setDateofexamination(cursor.getString(cursor.getColumnIndex(DATEOFEXAMINATION)));

                arrayList.add(popMedicalData);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }

    public static ArrayList<PopMedicalData> getAlldata() {
        open();
        ArrayList<PopMedicalData> arrayList = new ArrayList<>();
        arrayList.clear();
        String selectQuery = "SELECT  *  FROM " + TABLE_POPULATIONMEDICAL  ;
        Log.i("ckm=>PHCUsers",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PopMedicalData popMedicalData = new PopMedicalData();
                popMedicalData.setId(cursor.getInt(cursor.getColumnIndex(MPOPULATION_ID)));
                popMedicalData.setPadashaid(cursor.getInt(cursor.getColumnIndex(MPHCPADAID)));
                popMedicalData.setCampid(cursor.getInt(cursor.getColumnIndex(MCAMPID)));
                popMedicalData.setUserid(cursor.getInt(cursor.getColumnIndex(MUSERID)));
                popMedicalData.setHouseholdno(cursor.getString(cursor.getColumnIndex(MHOUSEHOLDNO)));
                popMedicalData.setFname(cursor.getString(cursor.getColumnIndex(MFNAME)));
                popMedicalData.setLname(cursor.getString(cursor.getColumnIndex(MLNAME)));
                popMedicalData.setHeightinfit(cursor.getInt(cursor.getColumnIndex(MHEIGHTFIT)));
                popMedicalData.setHeightininches(cursor.getInt(cursor.getColumnIndex(MHEIGHTINCH)));
                popMedicalData.setBmi(cursor.getString(cursor.getColumnIndex(MBMI)));
                popMedicalData.setBmi(cursor.getString(cursor.getColumnIndex(MBMI)));
                popMedicalData.setBpinter(cursor.getString(cursor.getColumnIndex(MBPINTERPRETN)));
                arrayList.add(popMedicalData);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }


    public static ArrayList<PopMedicalData> getdataforupdate(String id)
    {
        open();
        ArrayList<PopMedicalData> arrayList = new ArrayList<>();
        arrayList.clear();
        //String selectQuery = "SELECT  *  FROM " + TABLE_PADANASHAWORKERDETAILS +  " where " + DATEOFENTERDATA + " = '" +date + "' AND " +  ASHPHC + " ='" + phc + "' AND "+  ASHSUBCENTRE + " ='" + subcenter + "' AND "+ ASHVILLAGE + "='" + village  +   "' AND "+  ASHPADA +  "='" + pada + "' " ;
        String selectQuery = "SELECT  *  FROM " + TABLE_POPULATIONMEDICAL  + " where " + MPOPULATION_ID + " = '" + id + "'  /*AND " + ISFRESH + " = '" + 0 + "'*/ ";

        Log.i("ckm=>repeted",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                PopMedicalData msg = new PopMedicalData();
                //msg.setOutreachmemberdetailid();
                //MPOPULATION_ID
                msg.setId(cursor.getInt(cursor.getColumnIndex(MPOPULATION_ID)));
                msg.setPadashaid(cursor.getInt(cursor.getColumnIndex(MPHCPADAID)));
                msg.setCampid(cursor.getInt(cursor.getColumnIndex(MCAMPID)));
                // msg.setUserid(cursor.getInt(cursor.getColumnIndex(MUSERID)));
                msg.setOutreachmemberdetailid(cursor.getInt(cursor.getColumnIndex(MOUTREACHMEMBERDETAILID)));
                msg.setOutreachdetailsid(cursor.getInt(cursor.getColumnIndex(MOUTREACHDETAILSID)));
                msg.setHouseholdno(cursor.getString(cursor.getColumnIndex(MHOUSEHOLDNO)));
                msg.setTitle(cursor.getString(cursor.getColumnIndex(MTITLE)));
                msg.setFname(cursor.getString(cursor.getColumnIndex(MFNAME)));
                msg.setMname(cursor.getString(cursor.getColumnIndex(MMNAME)));
                msg.setLname(cursor.getString(cursor.getColumnIndex(MLNAME)));
                msg.setRelation(cursor.getString(cursor.getColumnIndex(MRELATION)));
                msg.setRelationdetails(cursor.getString(cursor.getColumnIndex(MRELATIONDETAILS)));
                msg.setGender(cursor.getString(cursor.getColumnIndex(MGENDER)));
                msg.setDob(cursor.getString(cursor.getColumnIndex(MDOB)));
                msg.setAge(cursor.getInt(cursor.getColumnIndex(MAGE)));
                msg.setAgeunit(cursor.getString(cursor.getColumnIndex(MUNIT)));
                msg.setHeightinfit(cursor.getInt(cursor.getColumnIndex(MHEIGHTFIT)));
                msg.setHeightininches(cursor.getInt(cursor.getColumnIndex(MHEIGHTINCH)));
                msg.setWeight(cursor.getInt(cursor.getColumnIndex(MWEIGHT)));
                msg.setBmi(cursor.getString(cursor.getColumnIndex(MBMI)));
                msg.setBmiinter(cursor.getString(cursor.getColumnIndex(MBMIINTER)));
                msg.setPulse(cursor.getInt(cursor.getColumnIndex(MPULSE)));
                msg.setOxysaturation(cursor.getInt(cursor.getColumnIndex(MOXYSATURATION)));
                msg.setBpsystolic(cursor.getInt(cursor.getColumnIndex(MBPSYSTOLIC)));
                msg.setBpdiastolic(cursor.getInt(cursor.getColumnIndex(MBPDIASTOLIC)));
                msg.setBpinter(cursor.getString(cursor.getColumnIndex(MBPINTERPRETN)));
                msg.setBloodsugar(cursor.getInt(cursor.getColumnIndex(MBLOODSUGAR)));
                msg.setBloodsugarinter(cursor.getString(cursor.getColumnIndex(MBLOODSUGARINTERPRETN)));
                msg.setVisualacuityrighteyenear(cursor.getString(cursor.getColumnIndex(MVISUALACURITYRIGHTEYENEAR)));
                msg.setVisualacuityrighteyedistant(cursor.getString(cursor.getColumnIndex(MVISUALACURITYRIGHTEYEDISTANCE)));
                msg.setVisualacuityrighteyeinter(cursor.getString(cursor.getColumnIndex(MVISUALACURITYRIGHTEYEINTERPR)));
                msg.setVisualacuitylefteyenear(cursor.getString(cursor.getColumnIndex(MVISUALACURITYLEFTEYENEAR)));
                msg.setVisualacuitylefteyedistant(cursor.getString(cursor.getColumnIndex(MVISUALACURITYLEFTEYEDISTANCE)));
                msg.setVisualacuitylefteyeinter(cursor.getString(cursor.getColumnIndex(MVISUALACURITYLEFTEYEINTER)));
                msg.setDiabetes(cursor.getString(cursor.getColumnIndex(MDIABETES)));
                msg.setHypertension(cursor.getString(cursor.getColumnIndex(MHYPERTENSION)));
                msg.setCataract(cursor.getString(cursor.getColumnIndex(MCATARACT)));
                msg.setCartaractdiagno(cursor.getString(cursor.getColumnIndex(MCATARACTDIAGONESD)));
                msg.setCataratdetails(cursor.getString(cursor.getColumnIndex(MCATARACTDETAILS)));
                msg.setEpilepsy(cursor.getString(cursor.getColumnIndex(MEPILEPSY)));
                msg.setEpilepsydetails(cursor.getString(cursor.getColumnIndex(MEPILEPSYDETAILS)));
                msg.setEpilepsydiagnosed(cursor.getString(cursor.getColumnIndex(MEPILEPSYDIAGONESD)));
                msg.setOralproblem(cursor.getString(cursor.getColumnIndex(MORALPROBLEM)));
                msg.setOralprodetails(cursor.getString(cursor.getColumnIndex(MORALPRODETAILS)));
                msg.setOralproblemdiagnoesd(cursor.getString(cursor.getColumnIndex(MORALPROBLEMDIAGNOSED)));
                msg.setTb(cursor.getString(cursor.getColumnIndex(MTB)));
                msg.setTbdetails(cursor.getString(cursor.getColumnIndex(MTBDETAILS)));
                msg.setTbdiagnosed(cursor.getString(cursor.getColumnIndex(MTBDIAGNOSED)));
                msg.setIschemicheartdisease(cursor.getString(cursor.getColumnIndex(MISCHEMICHERTDISEASE)));
                msg.setIschemicheadisdetails(cursor.getString(cursor.getColumnIndex(MISCHEMICHEADISDETAILS)));
                msg.setIschemicheadisddiagnosed(cursor.getString(cursor.getColumnIndex(MISCHEMICHEADIAGNOSED)));
                msg.setStroke(cursor.getString(cursor.getColumnIndex(MSTROKE)));
                msg.setStrokedetails(cursor.getString(cursor.getColumnIndex(MSTROKEDETAILS)));
                msg.setStrokediagnosed(cursor.getString(cursor.getColumnIndex(MSTROKEDIAGNOSED)));
                msg.setCancer(cursor.getString(cursor.getColumnIndex(MCANCER)));
                msg.setCancerdetails(cursor.getString(cursor.getColumnIndex(MCANCERDETAILS)));
                msg.setCancerdiagnosed(cursor.getString(cursor.getColumnIndex(MCANCERDIAGNOSED)));
                msg.setCogenitalabnormalities(cursor.getString(cursor.getColumnIndex(MCOGENITALABNORMALITIES)));
                msg.setCogenitaldetails(cursor.getString(cursor.getColumnIndex(MCOGENITALDETAILS)));
                msg.setHearingdefect(cursor.getString(cursor.getColumnIndex(MHEARINGDEFECT)));
                msg.setHearingdefectdetails(cursor.getString(cursor.getColumnIndex(MHEARINGDEFECTDETAILS)));
                msg.setHearingdefectdiagnosed(cursor.getString(cursor.getColumnIndex(MHEARINGDEFECTDIAGNOSED)));
                msg.setEarprob(cursor.getString(cursor.getColumnIndex(MEARPROB)));
                msg.setGynaecprob(cursor.getString(cursor.getColumnIndex(MGYNAECPROB)));
                msg.setGynaecdetails(cursor.getString(cursor.getColumnIndex(MGYNAECDETAILS)));
                msg.setGynaecdiagnosed(cursor.getString(cursor.getColumnIndex(MGYNAECDIAGNOSED)));
                msg.setGynaecopd(cursor.getString(cursor.getColumnIndex(MGYNAECOPD)));
                msg.setTobacco(cursor.getString(cursor.getColumnIndex(MTOBACCO)));
                msg.setSmoker(cursor.getString(cursor.getColumnIndex(MSMOKER)));
                msg.setAlcohol(cursor.getString(cursor.getColumnIndex(MALCOHOL)));
                msg.setEyesurgery(cursor.getString(cursor.getColumnIndex(MEYESUERGERY)));
                msg.setEyeopd(cursor.getString(cursor.getColumnIndex(MEYEOPD)));
                msg.setEpilepsyopd(cursor.getString(cursor.getColumnIndex(MEPILEPSYOPD)));
                msg.setDentalopd(cursor.getString(cursor.getColumnIndex(MDENTALOPD)));
                msg.setPlasticsurgeryopd(cursor.getString(cursor.getColumnIndex(MPLASTICSURGERYOPD)));
                msg.setOrthopedicsurgery(cursor.getString(cursor.getColumnIndex(MORTHOPEDICSURGERY)));
                msg.setEntopd(cursor.getString(cursor.getColumnIndex(MENTOPD)));
                msg.setPincode(cursor.getInt(cursor.getColumnIndex(MPINCODE)));
                msg.setMobileno(cursor.getString(cursor.getColumnIndex(MMOBILENO)));
                msg.setStreetname(cursor.getString(cursor.getColumnIndex(MSTREETNAME)));
                msg.setResidenceno(cursor.getString(cursor.getColumnIndex(MRESIDENCENO)));
                msg.setAadharno(cursor.getString(cursor.getColumnIndex(MAADHARNO)));
                msg.setVoteridno(cursor.getString(cursor.getColumnIndex(MVOTERID)));
                msg.setEmailid(cursor.getString(cursor.getColumnIndex(MEMAILD)));
                msg.setReferredby(cursor.getString(cursor.getColumnIndex(MREFERREDBY)));
                msg.setIsfresh(cursor.getString(cursor.getColumnIndex(ISFRESH)));
                msg.setExtra(cursor.getString(cursor.getColumnIndex(MEXTRA)));
                msg.setEarprobdetails(cursor.getString(cursor.getColumnIndex(EARPROBDETAILS)));
                msg.setPatientrefused(cursor.getInt(cursor.getColumnIndex(PATIENTREFUSED)));
                msg.setDateofexamination(cursor.getString(cursor.getColumnIndex(DATEOFEXAMINATION)));
                msg.setOralcanceropd(cursor.getString(cursor.getColumnIndex(MORALCANCEROPD)));
                arrayList.add(msg);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }

    //Update Data for 1st Page

    public static void updateHouseHold(String id, PopMedicalData popMedicalData)
    {
        open();
        ContentValues contentValues = new ContentValues();

        contentValues.put(MHOUSEHOLDNO, popMedicalData.getHouseholdno());
        contentValues.put(MRELATION, popMedicalData.getRelation());
        contentValues.put(MTITLE, popMedicalData.getTitle());
        contentValues.put(MFNAME, popMedicalData.getFname());
        contentValues.put(MMNAME, popMedicalData.getMname());
        contentValues.put(MLNAME, popMedicalData.getLname());
        contentValues.put(MGENDER, popMedicalData.getGender());
        contentValues.put(MDOB, popMedicalData.getDob());
        contentValues.put(MAGE,popMedicalData.getAge());
        contentValues.put(MUNIT, popMedicalData.getAgeunit());
        contentValues.put(MPINCODE, popMedicalData.getPincode());
        contentValues.put(MMOBILENO, popMedicalData.getMobileno());
        contentValues.put(MSTREETNAME, popMedicalData.getStreetname());
        contentValues.put(MRESIDENCENO, popMedicalData.getResidenceno());
        contentValues.put(MAADHARNO, popMedicalData.getAadharno());
        contentValues.put(MVOTERID, popMedicalData.getVoteridno());
        contentValues.put(MEMAILD, popMedicalData.getEmailid());
        contentValues.put(MREFERREDBY, popMedicalData.getReferredby());


        Log.i("SQLITE Data",id);
        database.update(TABLE_POPULATIONMEDICAL ,contentValues, "pid = "+ " '" + id +  "' " , null);
    }


    public static int gethouseholdcount(String campid,String userid) {
        open();
        int mydata = 0 ;
        //String countQuery = "SELECT count(DISTINCT housholdid) FROM populationNmedical WHERE campId =  '" + campid + "' ";
        String countQuery = "SELECT sum(t) FROM (SELECT count(DISTINCT housholdid) as t FROM populationNmedical WHERE campId = '" + campid + "' AND  userid ='" + userid +  "' group by padaAshaID)";
        Cursor cursor = database.rawQuery(countQuery, null);
        //Log.i("ckm=>houseCount",cursor+"");
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            mydata = cursor.getInt(0);
        }
        cursor.close();
        return mydata;
    }

    public static int getFaileDataCount(String campid) {
        open();
        int mydata = 0 ;
        String countQuery = "SELECT count(isfresh)  FROM populationNmedical WHERE campId = '"+ campid + "'  AND  isfresh = '0' ";
        Cursor cursor = database.rawQuery(countQuery, null);
        //Log.i("ckm=>houseCount",cursor+"");
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            mydata = cursor.getInt(0);
        }
        cursor.close();
        return mydata;
    }

    public static int gettotalpopulationcount(String campid,String userid)
    {
        open();
        int mydata1 = 0;
        String countQuery = "SELECT count(pid) FROM populationNmedical WHERE campId =  '" + campid + "'  AND  userid = '" + userid +  "'   " ;
        Log.i("ckm=>populationCount",countQuery+"");
        Cursor cursor = database.rawQuery(countQuery, null);
        //Log.i("ckm=>populationCount",cursor+"");
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            mydata1 = cursor.getInt(0);
        }
        cursor.close();
        return mydata1;
    }

    public static int getAgecount(String startage,String endage,String campid,String uid)
    {
        open();
        int mydata1 = 0;
        String countQuery = "SELECT count(age) as age FROM populationNmedical WHERE (age BETWEEN '" + startage + "' AND  '" + endage + "') AND  campId =  '" + campid + "' AND  userid =  '" + uid + "'";
        Log.i("ckm=>Agebycount",countQuery+"");
        Cursor cursor = database.rawQuery(countQuery, null);
        //Log.i("ckm=>populationCount",cursor+"");
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            mydata1 = cursor.getInt(0);
        }
        cursor.close();
        return mydata1;
    }

    public static int getAgecountgreaterthan60(String campid,String uid)
    {
        open();
        int mydata1 = 0;
        String countQuery = "SELECT count(age) as age FROM populationNmedical WHERE (age >= 60) AND  campId =  '" + campid + "' AND  userid =  '" + uid + "' ";
        Log.i("ckm=>Agebycount",countQuery+"");
        Cursor cursor = database.rawQuery(countQuery, null);
        //Log.i("ckm=>populationCount",cursor+"");
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            mydata1 = cursor.getInt(0);
        }
        cursor.close();
        return mydata1;
    }


    public static int gettotalMaleFemaleOthercount(String gender, String campid,String uid)
    {
        open();
        int mydata1 = 0;
        String countQuery = "SELECT count(gender) as count FROM populationNmedical WHERE gender = '" + gender + "'  AND  campId =  '" + campid + "'  AND  userid =  '" + uid + "' ";
        Log.i("ckm=>popCountMale",countQuery+"");
        Cursor cursor = database.rawQuery(countQuery, null);
        //Log.i("ckm=>populationCount",cursor+"");
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            mydata1 = cursor.getInt(0);
        }
        cursor.close();
        return mydata1;
    }


    /*Get bloodsugar count for report*/
    public static int getBloodSugarCount(String bloodsugarname,String campid,String userid)
    {
        open();
        int mydata1 = 0;
        //String countQuery = "SELECT count(age) as age FROM populationNmedical WHERE age BETWEEN '" + startage + "' AND  '" + endage + "' AND  campId =  '" + campid + "' ";
        String countQuery;
        if (bloodsugarname.equals("dna"))
        {
            countQuery = " SELECT count(*) FROM populationNmedical WHERE (bloodsugarierpretn = '' OR bloodsugarierpretn = NULL)  AND  campId =  '" + campid + "' AND  userid =  '" + userid + "' ";
        }
        else
        {
            countQuery = " SELECT count(*) FROM populationNmedical WHERE bloodsugarierpretn = '" + bloodsugarname + "'  AND  campId =  '" + campid + "' AND  userid =  '" + userid + "' ";
        }

        Log.i("ckm=>GetBloodsugarcount",countQuery+"");
        Cursor cursor = database.rawQuery(countQuery, null);
        //Log.i("ckm=>populationCount",cursor+"");
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            mydata1 = cursor.getInt(0);
        }
        cursor.close();
        return mydata1;
    }


    /* Get BMI interpretation count for report */
    public static int getBMI_inter_Count(String bmiinter,String campid,String userid)
    {
        open();
        int mydata1 = 0;
        String countQuery;
        if (bmiinter.equals("dna"))
        {
            countQuery = " SELECT count(*) FROM populationNmedical WHERE (bmiinter = '' OR bmiinter = NULL )  AND  campId =  '" + campid + "'  AND  userid =  '" + userid + "'";
        }
        else
        {
            countQuery = " SELECT count(*) FROM populationNmedical WHERE bmiinter = '" + bmiinter + "'  AND  campId =  '" + campid + "'  AND  userid =  '" + userid + "' ";
        }

        Log.i("ckm=>GetBMIcount",countQuery+"");
        Cursor cursor = database.rawQuery(countQuery, null);
        //Log.i("ckm=>populationCount",cursor+"");
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            mydata1 = cursor.getInt(0);
        }
        cursor.close();
        return mydata1;
    }



    /*Get blood pressure count for report*/

    public static int getBloodPressurCount(String bloodpressername,String campid,String id)
    {
        open();
        int mydata1 = 0;
        String countQuery;
        if (bloodpressername.equals("dna"))
        {
            countQuery = " SELECT count(*) FROM populationNmedical WHERE (bpinterpretn = '' OR bpinterpretn = NULL) AND  campId =  '" + campid + "' AND  userid =  '" + id + "' ";
        }
        else
        {
            countQuery = " SELECT count(*) FROM populationNmedical WHERE bpinterpretn = '" + bloodpressername + "'  AND  campId =  '" + campid + "'  AND  userid =  '" + id + "'  ";
        }

        Log.i("ckm=>GetBloodsugarcount",countQuery+"");
        Cursor cursor = database.rawQuery(countQuery, null);
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            mydata1 = cursor.getInt(0);
        }
        cursor.close();
        return mydata1;
    }


    public static ArrayList<PopMedicalData> getdailyworkrepot(String startdate,String enddate,String campid,String userid) {
        open();
        ArrayList<PopMedicalData> arrayList = new ArrayList<>();
        arrayList.clear();
        String selectQuery = "SELECT populationNmedical.userid,populationNmedical.pid," +
                "count(housholdid), count(DISTINCT housholdid),padaNashworkerTable.pada,padaNashworkerTable.village,padaNashworkerTable.subcentre," +
                "padaNashworkerTable.phc,populationNmedical.fname,populationNmedical.dateofexamination FROM  padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id" +
                " WHERE populationNmedical.campId = '" + campid+ "'  AND populationNmedical.userid = '"+ userid +"' AND  populationNmedical.dateofexamination BETWEEN '"+ startdate +"' AND '"+ enddate +"' GROUP BY padaNashworkerTable.id, populationNmedical.dateofexamination ORDER BY pid DESC;";
        Log.i( "ckm=>multipleJoin",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PopMedicalData popMedicalData = new PopMedicalData();
//                popMedicalData.setCampid(cursor.getInt(cursor.getColumnIndex(MCAMPID)));
                popMedicalData.setUserid(cursor.getInt(cursor.getColumnIndex(MUSERID)));
               // popMedicalData.setId(cursor.getInt(cursor.getColumnIndex(MPOPULATION_ID)));
               popMedicalData.setHousholscount(cursor.getString(cursor.getColumnIndex("count(DISTINCT housholdid)")));
               popMedicalData.setPopultioncount(cursor.getString(cursor.getColumnIndex("count(housholdid)")));
                // popMedicalData.setDateofdata(cursor.getString(cursor.getColumnIndex("dateofdata")));
                popMedicalData.setPada(cursor.getString(cursor.getColumnIndex("pada")));
                popMedicalData.setVillage(cursor.getString(cursor.getColumnIndex("village")));
                popMedicalData.setSubcentre(cursor.getString(cursor.getColumnIndex("subcentre")));
                popMedicalData.setPhc(cursor.getString(cursor.getColumnIndex("phc")));
                popMedicalData.setDateofexamination(cursor.getString(cursor.getColumnIndex(DATEOFEXAMINATION)));

                arrayList.add(popMedicalData);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }

    public static ArrayList<PopMedicalData> getTotalbyPhc(String phc,String campid) {
        open();
        ArrayList<PopMedicalData> arrayList1 = new ArrayList<>();
        arrayList1.clear();
        String selectQuery = "SELECT populationNmedical.campId,populationNmedical.userid,populationNmedical.pid," +
                "count(housholdid), count(DISTINCT housholdid),padaNashworkerTable.pada,padaNashworkerTable.village,padaNashworkerTable.subcentre," +
                "padaNashworkerTable.phc,populationNmedical.fname,populationNmedical.dateofexamination FROM  padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id" +
                " WHERE phc = '"  + phc + "'  AND populationNmedical.campId = '" + campid +  "' ";
        Log.i( "ckm=>totalbyphc",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PopMedicalData popMedicalData = new PopMedicalData();
                popMedicalData.setUserid(cursor.getInt(cursor.getColumnIndex(MUSERID)));
                popMedicalData.setHousholscount(cursor.getString(cursor.getColumnIndex("count(DISTINCT housholdid)")));
                popMedicalData.setPopultioncount(cursor.getString(cursor.getColumnIndex("count(housholdid)")));
                popMedicalData.setPada(cursor.getString(cursor.getColumnIndex("pada")));
                popMedicalData.setVillage(cursor.getString(cursor.getColumnIndex("village")));
                popMedicalData.setSubcentre(cursor.getString(cursor.getColumnIndex("subcentre")));
                popMedicalData.setPhc(cursor.getString(cursor.getColumnIndex("phc")));

                arrayList1.add(popMedicalData);
            }
            while (cursor.moveToNext());
        }
        return arrayList1;
    }
    public static ArrayList<PopMedicalData> getTotalbySubcenter(String subcenter,String campid) {
        open();
        ArrayList<PopMedicalData> arrayList = new ArrayList<>();
        arrayList.clear();
        String selectQuery = "SELECT populationNmedical.campId,populationNmedical.userid,populationNmedical.pid," +
                "count(housholdid), count(DISTINCT housholdid),padaNashworkerTable.pada,padaNashworkerTable.village,padaNashworkerTable.subcentre," +
                "padaNashworkerTable.phc,populationNmedical.fname,populationNmedical.dateofexamination FROM  padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id" +
                " WHERE subcentre = '"  + subcenter + "'  AND populationNmedical.campId = '" + campid +  "' ";
        Log.i( "ckm=>totalbyphc",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PopMedicalData popMedicalData = new PopMedicalData();
                popMedicalData.setUserid(cursor.getInt(cursor.getColumnIndex(MUSERID)));
                popMedicalData.setHousholscount(cursor.getString(cursor.getColumnIndex("count(DISTINCT housholdid)")));
                popMedicalData.setPopultioncount(cursor.getString(cursor.getColumnIndex("count(housholdid)")));
                popMedicalData.setPada(cursor.getString(cursor.getColumnIndex("pada")));
                popMedicalData.setVillage(cursor.getString(cursor.getColumnIndex("village")));
                popMedicalData.setSubcentre(cursor.getString(cursor.getColumnIndex("subcentre")));
                popMedicalData.setPhc(cursor.getString(cursor.getColumnIndex("phc")));

                arrayList.add(popMedicalData);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }
    public static ArrayList<PopMedicalData> getTotalbyVillage(String village,String campid) {
        open();
        ArrayList<PopMedicalData> arrayList = new ArrayList<>();
        arrayList.clear();
        String selectQuery = "SELECT populationNmedical.campId,populationNmedical.userid,populationNmedical.pid," +
                "count(housholdid), count(DISTINCT housholdid),padaNashworkerTable.pada,padaNashworkerTable.village,padaNashworkerTable.subcentre," +
                "padaNashworkerTable.phc,populationNmedical.fname,populationNmedical.dateofexamination FROM  padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id" +
                " WHERE village = '"  + village + "'  AND populationNmedical.campId = '" + campid +  "' ";
        Log.i( "ckm=>totalbyphc",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PopMedicalData popMedicalData = new PopMedicalData();
                popMedicalData.setUserid(cursor.getInt(cursor.getColumnIndex(MUSERID)));
                popMedicalData.setHousholscount(cursor.getString(cursor.getColumnIndex("count(DISTINCT housholdid)")));
                popMedicalData.setPopultioncount(cursor.getString(cursor.getColumnIndex("count(housholdid)")));
                popMedicalData.setPada(cursor.getString(cursor.getColumnIndex("pada")));
                popMedicalData.setVillage(cursor.getString(cursor.getColumnIndex("village")));
                popMedicalData.setSubcentre(cursor.getString(cursor.getColumnIndex("subcentre")));
                popMedicalData.setPhc(cursor.getString(cursor.getColumnIndex("phc")));

                arrayList.add(popMedicalData);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }
    public static ArrayList<PopMedicalData> getTotalbyPada(String pada,String campid) {
        open();
        ArrayList<PopMedicalData> arrayList = new ArrayList<>();
        arrayList.clear();
        String selectQuery = "SELECT populationNmedical.campId,populationNmedical.userid,populationNmedical.pid," +
                "count(housholdid), count(DISTINCT housholdid),padaNashworkerTable.pada,padaNashworkerTable.village,padaNashworkerTable.subcentre," +
                "padaNashworkerTable.phc,populationNmedical.fname,populationNmedical.dateofexamination FROM  padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id" +
                " WHERE pada = '"  + pada + "'  AND populationNmedical.campId = '" + campid +  "' ";
        Log.i( "ckm=>totalbyphc",selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PopMedicalData popMedicalData = new PopMedicalData();
                popMedicalData.setUserid(cursor.getInt(cursor.getColumnIndex(MUSERID)));
                popMedicalData.setHousholscount(cursor.getString(cursor.getColumnIndex("count(DISTINCT housholdid)")));
                popMedicalData.setPopultioncount(cursor.getString(cursor.getColumnIndex("count(housholdid)")));
                popMedicalData.setPada(cursor.getString(cursor.getColumnIndex("pada")));
                popMedicalData.setVillage(cursor.getString(cursor.getColumnIndex("village")));
                popMedicalData.setSubcentre(cursor.getString(cursor.getColumnIndex("subcentre")));
                popMedicalData.setPhc(cursor.getString(cursor.getColumnIndex("phc")));

                arrayList.add(popMedicalData);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }

    public static int getEyeopdCount(String campid,String uid) {
        open();
        int mydata = 0 ;
        String countQuery = "SELECT count(*) FROM populationNmedical WHERE (eyeopd IS NOT NULL AND eyeopd != '') AND campId =  '" + campid + "' AND userid = '" + uid +"' ";
        Cursor cursor = database.rawQuery(countQuery, null);
        Log.i("ckm=>getEyeopdCount",countQuery+"");
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            mydata = cursor.getInt(0);
        }
        cursor.close();
        return mydata;
    }

    public static int getEntOpdCount(String campid,String uid) {
        open();
        int mydata = 0 ;
        String countQuery = "SELECT count(*) FROM populationNmedical WHERE (entopd IS NOT NULL AND entopd != '') AND campId =  '" + campid + "' AND userid = '" + uid +"' ";
        Cursor cursor = database.rawQuery(countQuery, null);
        //Log.i("ckm=>houseCount",cursor+"");
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            mydata = cursor.getInt(0);
        }
        cursor.close();
        return mydata;
    }


    public static int getCataractCount(String campid,String uid) {
        open();
        int mydata = 0 ;
        String countQuery = "SELECT count(*) FROM populationNmedical WHERE (eyesurgery IS NOT NULL AND eyesurgery != '') AND campId =  '" + campid + "' AND userid = '" + uid +"' ";
        Cursor cursor = database.rawQuery(countQuery, null);
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            mydata = cursor.getInt(0);
        }
        cursor.close();
        return mydata;
    }
    public static int getdentalCount(String campid,String uid) {
        open();
        int mydata = 0 ;
        String countQuery = "SELECT count(*) FROM populationNmedical WHERE (dentalopd IS NOT NULL AND dentalopd != '') AND campId =  '" + campid + "' AND userid = '" + uid +"' ";
        Cursor cursor = database.rawQuery(countQuery, null);
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            mydata = cursor.getInt(0);
        }
        cursor.close();
        return mydata;
    }
    public static int getplasticsurgereryCount(String campid,String uid) {
        open();
        int mydata = 0 ;
        String countQuery = "SELECT count(*) FROM populationNmedical WHERE (plasticsurgeryopd IS NOT NULL AND plasticsurgeryopd != '' ) AND campId =  '" + campid + "' AND userid = '" + uid +"' ";
        Cursor cursor = database.rawQuery(countQuery, null);
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            mydata = cursor.getInt(0);
        }
        cursor.close();
        return mydata;
    }
    public static int getepfCount(String campid,String uid) {
        open();
        int mydata = 0 ;
        String countQuery = "SELECT count(*) FROM populationNmedical WHERE (epilepsyopd IS NOT NULL AND epilepsyopd != '' ) AND campId =  '" + campid + "' AND userid = '" + uid +"' ";
        Cursor cursor = database.rawQuery(countQuery, null);
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            mydata = cursor.getInt(0);
        }
        cursor.close();
        return mydata;
    }

    public static int getvisualNormalCount(String campid,String righteyevalue,String lefteyevalue,String flag,String uid) {
        open();
        int mydata = 0 ;
        String countQuery;
        if (flag.equals("1"))
        {
            countQuery = "SELECT count(*) FROM populationNmedical WHERE (visualacuitylefteyeinter = '" + lefteyevalue +"' or visualacuityrighteyeinter = '" + righteyevalue +"') AND campId =  '" + campid + "'  AND userid = '" + uid + "' ";
        }
        else
        {
            countQuery = "SELECT count(*) FROM populationNmedical WHERE (visualacuitylefteyeinter = '" + lefteyevalue +"' AND  visualacuityrighteyeinter = '" + righteyevalue +"') AND campId =  '" + campid + "'   AND userid = '" + uid + "' ";
        }
        Log.i("Query=>visual",countQuery);
        Cursor cursor = database.rawQuery(countQuery, null);
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            mydata = cursor.getInt(0);
        }
        cursor.close();
        return mydata;
    }
    public static int getvisualnullcount(String campid,String userid) {
        open();
        int mydata = 0 ;
        //String countQuery = "SELECT count(*) FROM populationNmedical WHERE visualacuitylefteyeinter = '" + lefteyevalue +"' or visualacuityrighteyeinter = '" + righteyevalue +"' AND campId =  '" + campid + "' ";
        String countQuery = "SELECT count(*) FROM populationNmedical WHERE (visualacuitylefteyeinter = '' OR visualacuitylefteyeinter = NULL  AND visualacuityrighteyeinter = '' OR visualacuityrighteyeinter = NULL) AND campId =  '" + campid + "' AND userid =  '" + userid + "' ";
        Log.i("CKm=>Null",countQuery);
        Cursor cursor = database.rawQuery(countQuery, null);
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            mydata = cursor.getInt(0);
        }
        cursor.close();
        return mydata;
    }

    public static int getorthopedicCount(String campid,String uid) {
        open();
        int mydata = 0 ;
        String countQuery = "SELECT count(*) FROM populationNmedical WHERE (orthopedicsurgery IS NOT NULL AND orthopedicsurgery != '' ) AND campId =  '" + campid + "' AND userid = '" + uid +"' ";
        Cursor cursor = database.rawQuery(countQuery, null);
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            mydata = cursor.getInt(0);
        }
        cursor.close();
        return mydata;
    }

    public static int getentCount(String campid) {
        open();
        int mydata = 0 ;
        String countQuery = "SELECT count(*) FROM populationNmedical WHERE entopd IS NOT NULL AND campId =  '" + campid + "' ";
        Cursor cursor = database.rawQuery(countQuery, null);
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            mydata = cursor.getInt(0);
        }
        cursor.close();
        return mydata;
    }

    public static int getgynaecopdCount(String campid,String uid) {
        open();
        int mydata = 0 ;
        String countQuery = "SELECT count(*) FROM populationNmedical WHERE (gynaecopd IS NOT NULL AND gynaecopd != '') AND campId =  '" + campid + "' AND userid = '" + uid +"' ";
        Cursor cursor = database.rawQuery(countQuery, null);
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            mydata = cursor.getInt(0);
        }
        cursor.close();
        return mydata;
    }

    public static ArrayList<PopMedicalData> getOpdcountbyfilter(String opd,String type,String typename,String campid) {
        open();
        ArrayList<PopMedicalData> arrayList = new ArrayList<>();
        arrayList.clear();
        String query = "SELECT populationNmedical.campId,populationNmedical.userid,populationNmedical.pid," +
                "count(*) AS countof, padaNashworkerTable.pada,padaNashworkerTable.village,padaNashworkerTable.subcentre," +
                "padaNashworkerTable.phc,populationNmedical.fname FROM " +
                " padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE " + opd + " IS NOT NULL AND " + type  + "  = '" + typename + "'  AND populationNmedical.campId = '" + campid + "' ";

        Log.i( "ckm=>opdbyfilter",query);

        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                PopMedicalData popMedicalData = new PopMedicalData();
                popMedicalData.setUserid(cursor.getInt(cursor.getColumnIndex(MUSERID)));
                popMedicalData.setCountof(cursor.getString(cursor.getColumnIndex("countof")));
                popMedicalData.setPada(cursor.getString(cursor.getColumnIndex("pada")));
                popMedicalData.setVillage(cursor.getString(cursor.getColumnIndex("village")));
                popMedicalData.setSubcentre(cursor.getString(cursor.getColumnIndex("subcentre")));
                popMedicalData.setPhc(cursor.getString(cursor.getColumnIndex("phc")));

                arrayList.add(popMedicalData);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }

    public static ArrayList<PopMedicalData> getAllOpdcountbyfilter(String type,String typename,String campid,String uid) {
        open();
        ArrayList<PopMedicalData> arrayList = new ArrayList<>();
        arrayList.clear();

        String query = "SELECT (count(housholdid)) as totalhousehold,(count(DISTINCT housholdid)) as houseunique," +
                "(select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (eyeopd IS NOT NULL AND eyeopd != '') AND " + type  + "= '"+ typename + "' AND populationNmedical.campId = '"+ campid +"') AS eyeopdcount," +
                "(select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (dentalopd IS NOT NULL AND dentalopd != '') AND " + type  + "= '"+ typename + "' AND populationNmedical.campId = '"+ campid +"') AS dentalopdcount," +
                "(select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (epilepsyopd IS NOT NULL AND epilepsyopd != '') AND " + type  + "= '"+ typename + "' AND populationNmedical.campId = '"+ campid +"') AS epfcount," +
                "(select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (eyesurgery IS NOT NULL AND eyesurgery != '') AND " + type  + "= '"+ typename + "' AND populationNmedical.campId = '"+ campid +"') AS cataractcount," +
                "(select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (orthopedicsurgery IS NOT NULL AND orthopedicsurgery != '') AND " + type  + "= '"+ typename + "' AND populationNmedical.campId = '"+ campid +"') AS orthocount," +
                "(select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (plasticsurgeryopd IS NOT NULL AND plasticsurgeryopd != '') AND " + type  + "= '"+ typename + "' AND populationNmedical.campId = '"+ campid +"') AS plasticsurgerycount," +
                " (select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (entopd IS NOT NULL AND entopd != '') AND " + type  + "= '"+ typename + "' AND populationNmedical.campId = '"+ campid +"') AS entcount," +
                " (select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (gynaecopd IS NOT NULL AND gynaecopd != '') AND " + type  + "= '"+ typename + "' AND populationNmedical.campId = '"+ campid +"') AS gynaecount," +
                " (select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (oralcanceropd IS NOT NULL AND oralcanceropd != '') AND " + type  + "= '"+ typename + "' AND populationNmedical.campId = '"+ campid +"') AS oralcancercount" +
                "  FROM  padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE " + type  + "= '"+ typename + "' AND populationNmedical.campId = '" +campid + "' AND populationNmedical.userid = '" + uid + "' ";



        Log.i( "ckm=>opdbyfilter",query);

        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                PopMedicalData popMedicalData = new PopMedicalData();
                //popMedicalData.setUserid(cursor.getInt(cursor.getColumnIndex(MUSERID)));
                popMedicalData.setPopultioncount(cursor.getString(cursor.getColumnIndex("totalhousehold")));
                popMedicalData.setHousholscount(cursor.getString(cursor.getColumnIndex("houseunique")));

                popMedicalData.setEyeopdcount(cursor.getString(cursor.getColumnIndex("eyeopdcount")));
                popMedicalData.setDentalopdcount(cursor.getString(cursor.getColumnIndex("dentalopdcount")));
                popMedicalData.setEpfcount(cursor.getString(cursor.getColumnIndex("epfcount")));
                popMedicalData.setCataractcount(cursor.getString(cursor.getColumnIndex("cataractcount")));
                popMedicalData.setOrthocount(cursor.getString(cursor.getColumnIndex("orthocount")));
                popMedicalData.setPlasticsurgerycount(cursor.getString(cursor.getColumnIndex("plasticsurgerycount")));
                popMedicalData.setEntcount(cursor.getString(cursor.getColumnIndex("entcount")));
                popMedicalData.setGynaecount(cursor.getString(cursor.getColumnIndex("gynaecount")));
                popMedicalData.setOralcancercount(cursor.getString(cursor.getColumnIndex("oralcancercount")));
                arrayList.add(popMedicalData);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }

    public static ArrayList<PopMedicalData> getAllOpdcountbyfilterForAsha(String padaname,String campid) {
        open();
        ArrayList<PopMedicalData> arrayList = new ArrayList<>();
        arrayList.clear();
        String query = "SELECT (count(housholdid)) as totalhousehold,(count(DISTINCT housholdid)) as houseunique," +
                "(select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (eyeopd IS NOT NULL AND eyeopd != '') AND pada  = '"+ padaname + "' AND populationNmedical.campId = '"+ campid +"') AS eyeopdcount," +
                "(select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (dentalopd IS NOT NULL AND dentalopd != '') AND pada  = '"+ padaname + "' AND populationNmedical.campId = '"+ campid +"') AS dentalopdcount," +
                "(select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (epilepsyopd IS NOT NULL AND epilepsyopd != '') AND pada  = '"+ padaname + "' AND populationNmedical.campId = '"+ campid +"') AS epfcount," +
                "(select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (eyesurgery IS NOT NULL AND eyesurgery != '')  AND pada  = '"+ padaname + "' AND populationNmedical.campId = '"+ campid +"') AS cataractcount," +
                "(select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (orthopedicsurgery IS NOT NULL AND orthopedicsurgery != '')  AND pada  = '"+ padaname + "' AND populationNmedical.campId = '"+ campid +"') AS orthocount," +
                "(select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (plasticsurgeryopd IS NOT NULL AND plasticsurgeryopd != '')  AND pada  = '"+ padaname + "' AND populationNmedical.campId = '"+ campid +"') AS plasticsurgerycount," +
                " (select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (entopd IS NOT NULL AND entopd != '')  AND pada  = '"+ padaname + "' AND populationNmedical.campId = '"+ campid +"') AS entcount," +
                " (select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (gynaecopd IS NOT NULL AND gynaecopd != '')  AND pada  = '"+ padaname + "' AND populationNmedical.campId = '"+ campid +"') AS gynaecount," +
                " (select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (oralcanceropd IS NOT NULL AND oralcanceropd != '')  AND pada  = '"+ padaname + "' AND populationNmedical.campId = '"+ campid +"') AS oralcancercount" +
                "  FROM  padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id" +
                " WHERE pada = '" + padaname + "'  AND populationNmedical.campId = '" + campid +"' ";



        Log.i( "ckm=>opdbyfilter",query);

        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                PopMedicalData popMedicalData = new PopMedicalData();
                //popMedicalData.setUserid(cursor.getInt(cursor.getColumnIndex(MUSERID)));
                popMedicalData.setHousholscount(cursor.getString(cursor.getColumnIndex("houseunique")));
                popMedicalData.setPopultioncount(cursor.getString(cursor.getColumnIndex("totalhousehold")));

                popMedicalData.setEyeopdcount(cursor.getString(cursor.getColumnIndex("eyeopdcount")));
                popMedicalData.setDentalopdcount(cursor.getString(cursor.getColumnIndex("dentalopdcount")));
                popMedicalData.setEpfcount(cursor.getString(cursor.getColumnIndex("epfcount")));
                popMedicalData.setCataractcount(cursor.getString(cursor.getColumnIndex("cataractcount")));
                popMedicalData.setOrthocount(cursor.getString(cursor.getColumnIndex("orthocount")));
                popMedicalData.setPlasticsurgerycount(cursor.getString(cursor.getColumnIndex("plasticsurgerycount")));
                popMedicalData.setEntcount(cursor.getString(cursor.getColumnIndex("entcount")));
                popMedicalData.setGynaecount(cursor.getString(cursor.getColumnIndex("gynaecount")));
                popMedicalData.setOralcancercount(cursor.getString(cursor.getColumnIndex("oralcancercount")));

                arrayList.add(popMedicalData);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }

    public static ArrayList<PopMedicalData> getOpdPatientList(String padaname,String campid) {
        open();
        ArrayList<PopMedicalData> arrayList = new ArrayList<>();
        arrayList.clear();
        String query = "SELECT populationNmedical.campId,populationNmedical.userid,populationNmedical.pid,padaNashworkerTable.pada,padaNashworkerTable.village,padaNashworkerTable.subcentre," +
                "padaNashworkerTable.phc,populationNmedical.dateofexamination, populationNmedical.housholdid,populationNmedical.fname,populationNmedical.mname,populationNmedical.lname," +
                "populationNmedical.gynaecopd,populationNmedical.oralcanceropd,populationNmedical.eyeopd,populationNmedical.eyesurgery,populationNmedical.epilepsyopd,populationNmedical.dentalopd,populationNmedical.plasticsurgeryopd,populationNmedical.orthopedicsurgery,populationNmedical.entopd," +
                "populationNmedical.gender,populationNmedical.dob,populationNmedical.age FROM  padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id" +
                " WHERE (gynaecopd IS NOT NULL AND gynaecopd != '' OR eyeopd IS NOT NULL AND eyeopd != '' OR eyesurgery IS NOT NULL AND eyesurgery != '' OR epilepsyopd IS NOT NULL AND epilepsyopd != '' OR dentalopd IS NOT NULL AND dentalopd != '' OR entopd IS NOT NULL AND entopd != '' OR plasticsurgeryopd  IS NOT NULL AND plasticsurgeryopd = '' " +
                "OR orthopedicsurgery IS NOT NULL AND orthopedicsurgery != '') AND pada = '" + padaname +"'  AND populationNmedical.campId = '" +  campid +"' ";

        Log.i( "ckm=>getopdpatientlist",query);

        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                PopMedicalData popMedicalData = new PopMedicalData();

                popMedicalData.setFname(cursor.getString(cursor.getColumnIndex("fname")));
                popMedicalData.setLname(cursor.getString(cursor.getColumnIndex("lname")));
                popMedicalData.setMname(cursor.getString(cursor.getColumnIndex("mname")));
                popMedicalData.setAge(cursor.getInt(cursor.getColumnIndex("age")));
                popMedicalData.setGender(cursor.getString(cursor.getColumnIndex("gender")));
                popMedicalData.setDateofexamination(cursor.getString(cursor.getColumnIndex(DATEOFEXAMINATION)));

                popMedicalData.setGynaecopd(cursor.getString(cursor.getColumnIndex(MGYNAECOPD)));
                popMedicalData.setEyeopd(cursor.getString(cursor.getColumnIndex(MEYEOPD)));
                popMedicalData.setEyesurgery(cursor.getString(cursor.getColumnIndex(MEYESUERGERY)));
                popMedicalData.setEpilepsyopd(cursor.getString(cursor.getColumnIndex(MEPILEPSYOPD)));
                popMedicalData.setDentalopd(cursor.getString(cursor.getColumnIndex(MDENTALOPD)));
                popMedicalData.setPlasticsurgeryopd(cursor.getString(cursor.getColumnIndex(MPLASTICSURGERYOPD)));
                popMedicalData.setOrthopedicsurgery(cursor.getString(cursor.getColumnIndex(MORTHOPEDICSURGERY)));
                popMedicalData.setEntopd(cursor.getString(cursor.getColumnIndex(MENTOPD)));
                popMedicalData.setHouseholdno(cursor.getString(cursor.getColumnIndex(MHOUSEHOLDNO)));
                popMedicalData.setOralcanceropd(cursor.getString(cursor.getColumnIndex(MORALCANCEROPD)));


                popMedicalData.setPada(cursor.getString(cursor.getColumnIndex("pada")));
                popMedicalData.setVillage(cursor.getString(cursor.getColumnIndex("village")));
                popMedicalData.setSubcentre(cursor.getString(cursor.getColumnIndex("subcentre")));
                popMedicalData.setPhc(cursor.getString(cursor.getColumnIndex("phc")));

                arrayList.add(popMedicalData);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }

    public static ArrayList<PopMedicalData> getPerticularOpdPatientList(String opdname,String campid) {
        open();
        ArrayList<PopMedicalData> arrayList = new ArrayList<>();
        arrayList.clear();

        String query = "SELECT populationNmedical.campId,populationNmedical.userid,populationNmedical.pid,padaNashworkerTable.pada,padaNashworkerTable.village,padaNashworkerTable.subcentre," +
                "padaNashworkerTable.phc,populationNmedical.dateofexamination, populationNmedical.housholdid,populationNmedical.fname,populationNmedical.mname,populationNmedical.lname," +
                "populationNmedical."+ opdname +"," +
                "populationNmedical.gender,populationNmedical.dob,populationNmedical.age FROM  padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id" +
                " WHERE ( "+ opdname +" IS NOT NULL AND "+ opdname +" != '' ) AND populationNmedical.campId = '" +  campid +"' ";

        Log.i( "ckm=>getperticularopd",query);

        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                PopMedicalData popMedicalData = new PopMedicalData();

                popMedicalData.setFname(cursor.getString(cursor.getColumnIndex("fname")));
                popMedicalData.setLname(cursor.getString(cursor.getColumnIndex("lname")));
                popMedicalData.setMname(cursor.getString(cursor.getColumnIndex("mname")));
                popMedicalData.setAge(cursor.getInt(cursor.getColumnIndex("age")));
                popMedicalData.setGender(cursor.getString(cursor.getColumnIndex("gender")));
                popMedicalData.setDateofexamination(cursor.getString(cursor.getColumnIndex(DATEOFEXAMINATION)));

                if (opdname.equals("gynaecopd"))
                {
                    popMedicalData.setGynaecopd(cursor.getString(cursor.getColumnIndex(MGYNAECOPD)));
                }
                if (opdname.equals("eyeopd"))
                {
                    popMedicalData.setEyeopd(cursor.getString(cursor.getColumnIndex(MEYEOPD)));
                }
                if (opdname.equals("eyesurgery"))
                {
                    popMedicalData.setEyesurgery(cursor.getString(cursor.getColumnIndex(MEYESUERGERY)));
                }
                if (opdname.equals("epilepsyopd"))
                {
                    popMedicalData.setEpilepsyopd(cursor.getString(cursor.getColumnIndex(MEPILEPSYOPD)));
                }
                if (opdname.equals("dentalopd"))
                {
                    popMedicalData.setDentalopd(cursor.getString(cursor.getColumnIndex(MDENTALOPD)));
                }
                if (opdname.equals("plasticsurgeryopd"))
                {
                    popMedicalData.setPlasticsurgeryopd(cursor.getString(cursor.getColumnIndex(MPLASTICSURGERYOPD)));
                }
                if (opdname.equals("orthopedicsurgery"))
                {
                    popMedicalData.setOrthopedicsurgery(cursor.getString(cursor.getColumnIndex(MORTHOPEDICSURGERY)));
                }
                if (opdname.equals("entopd"))
                {
                    popMedicalData.setEntopd(cursor.getString(cursor.getColumnIndex(MENTOPD)));
                }
                if (opdname.equals("oralcanceropd"))
                {
                    popMedicalData.setOralcanceropd(cursor.getString(cursor.getColumnIndex(MORALCANCEROPD)));
                }

                popMedicalData.setHouseholdno(cursor.getString(cursor.getColumnIndex(MHOUSEHOLDNO)));
                popMedicalData.setPada(cursor.getString(cursor.getColumnIndex("pada")));
                popMedicalData.setVillage(cursor.getString(cursor.getColumnIndex("village")));
                popMedicalData.setSubcentre(cursor.getString(cursor.getColumnIndex("subcentre")));
                popMedicalData.setPhc(cursor.getString(cursor.getColumnIndex("phc")));

                arrayList.add(popMedicalData);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }
    //OpD with Pada n all
    public static ArrayList<PopMedicalData> getPerticularOpdPatientListwithtype(String typename,String type,String opdname,String campid) {
        open();
        ArrayList<PopMedicalData> arrayList = new ArrayList<>();
        arrayList.clear();

        String query = "SELECT populationNmedical.campId,populationNmedical.userid,populationNmedical.pid,padaNashworkerTable.pada,padaNashworkerTable.anmname,padaNashworkerTable.village,padaNashworkerTable.subcentre," +
                "padaNashworkerTable.phc,populationNmedical.dateofexamination, populationNmedical.housholdid,populationNmedical.fname,populationNmedical.mname,populationNmedical.lname," +
                "populationNmedical."+ opdname +"," +
                "populationNmedical.gender,populationNmedical.dob,populationNmedical.age FROM  padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id" +
                " WHERE ( "+ opdname +" IS NOT NULL AND "+ opdname +" != '' ) AND populationNmedical.campId = '" +  campid +"' AND  " + typename +" = '" + type + "' ";

        Log.i( "ckm=>getperticularopd",query);

        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                PopMedicalData popMedicalData = new PopMedicalData();

                popMedicalData.setFname(cursor.getString(cursor.getColumnIndex("fname")));
                popMedicalData.setLname(cursor.getString(cursor.getColumnIndex("lname")));
                popMedicalData.setMname(cursor.getString(cursor.getColumnIndex("mname")));
                popMedicalData.setAge(cursor.getInt(cursor.getColumnIndex("age")));
                popMedicalData.setGender(cursor.getString(cursor.getColumnIndex("gender")));
                popMedicalData.setDateofexamination(cursor.getString(cursor.getColumnIndex(DATEOFEXAMINATION)));

                if (opdname.equals("gynaecopd"))
                {
                    popMedicalData.setGynaecopd(cursor.getString(cursor.getColumnIndex(MGYNAECOPD)));
                }
                if (opdname.equals("eyeopd"))
                {
                    popMedicalData.setEyeopd(cursor.getString(cursor.getColumnIndex(MEYEOPD)));
                }
                if (opdname.equals("eyesurgery"))
                {
                    popMedicalData.setEyesurgery(cursor.getString(cursor.getColumnIndex(MEYESUERGERY)));
                }
                if (opdname.equals("epilepsyopd"))
                {
                    popMedicalData.setEpilepsyopd(cursor.getString(cursor.getColumnIndex(MEPILEPSYOPD)));
                }
                if (opdname.equals("dentalopd"))
                {
                    popMedicalData.setDentalopd(cursor.getString(cursor.getColumnIndex(MDENTALOPD)));
                }
                if (opdname.equals("plasticsurgeryopd"))
                {
                    popMedicalData.setPlasticsurgeryopd(cursor.getString(cursor.getColumnIndex(MPLASTICSURGERYOPD)));
                }
                if (opdname.equals("orthopedicsurgery"))
                {
                    popMedicalData.setOrthopedicsurgery(cursor.getString(cursor.getColumnIndex(MORTHOPEDICSURGERY)));
                }
                if (opdname.equals("entopd"))
                {
                    popMedicalData.setEntopd(cursor.getString(cursor.getColumnIndex(MENTOPD)));
                }
                if (opdname.equals("oralcanceropd"))
                {
                    popMedicalData.setOralcanceropd(cursor.getString(cursor.getColumnIndex(MORALCANCEROPD)));
                }

                popMedicalData.setHouseholdno(cursor.getString(cursor.getColumnIndex(MHOUSEHOLDNO)));
                popMedicalData.setPada(cursor.getString(cursor.getColumnIndex("pada")));
                popMedicalData.setVillage(cursor.getString(cursor.getColumnIndex("village")));
                popMedicalData.setSubcentre(cursor.getString(cursor.getColumnIndex("subcentre")));
                popMedicalData.setPhc(cursor.getString(cursor.getColumnIndex("phc")));

                arrayList.add(popMedicalData);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }

    public static ArrayList<PopMedicalData> getAllcountbyAshaname(String ashaworkername,String campid) {
        open();
        ArrayList<PopMedicalData> arrayList = new ArrayList<>();
        arrayList.clear();

        String query = "SELECT (count(housholdid)) as totalhousehold,(count(DISTINCT housholdid)) as houseunique," +
                "(select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (eyeopd IS NOT NULL AND eyeopd != '') AND padaNashworkerTable.ashaworkername  = '"+ ashaworkername + "' AND populationNmedical.campId = '"+ campid +"') AS eyeopdcount," +
                "(select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (dentalopd IS NOT NULL AND dentalopd != '') AND padaNashworkerTable.ashaworkername  = '"+ ashaworkername + "' AND populationNmedical.campId = '"+ campid +"') AS dentalopdcount," +
                "(select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (epilepsyopd IS NOT NULL AND epilepsyopd != '')  AND padaNashworkerTable.ashaworkername  = '"+ ashaworkername + "' AND populationNmedical.campId = '"+ campid +"') AS epfcount," +
                "(select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (eyesurgery IS NOT NULL AND eyesurgery != '')  AND padaNashworkerTable.ashaworkername  = '"+ ashaworkername + "' AND populationNmedical.campId = '"+ campid +"') AS cataractcount," +
                "(select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (orthopedicsurgery IS NOT NULL AND orthopedicsurgery != '')  AND padaNashworkerTable.ashaworkername  = '"+ ashaworkername + "' AND populationNmedical.campId = '"+ campid +"') AS orthocount," +
                "(select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (plasticsurgeryopd IS NOT NULL AND plasticsurgeryopd != '')  AND padaNashworkerTable.ashaworkername  = '"+ ashaworkername + "' AND populationNmedical.campId = '"+ campid +"') AS plasticsurgerycount," +
                " (select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (entopd IS NOT NULL AND entopd != '')  AND padaNashworkerTable.ashaworkername  = '"+ ashaworkername + "' AND populationNmedical.campId = '"+ campid +"') AS entcount," +
                " (select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (gynaecopd IS NOT NULL AND gynaecopd != '')  AND padaNashworkerTable.ashaworkername  = '"+ ashaworkername + "' AND populationNmedical.campId = '"+ campid +"') AS gynaecount," +
                " (select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (oralcanceropd IS NOT NULL AND oralcanceropd != '')  AND padaNashworkerTable.ashaworkername  = '"+ ashaworkername + "' AND populationNmedical.campId = '"+ campid +"') AS oralcancercount" +
                "  FROM  padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE padaNashworkerTable.ashaworkername = '"+ ashaworkername + "' AND populationNmedical.campId = '" +campid + "'";



        Log.i( "ckm=>allcountbyashaname",query);

        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                PopMedicalData popMedicalData = new PopMedicalData();
              //  popMedicalData.setUserid(cursor.getInt(cursor.getColumnIndex(MUSERID)));
                popMedicalData.setHousholscount(cursor.getString(cursor.getColumnIndex("houseunique")));
                popMedicalData.setPopultioncount(cursor.getString(cursor.getColumnIndex("totalhousehold")));

                popMedicalData.setEyeopdcount(cursor.getString(cursor.getColumnIndex("eyeopdcount")));
                popMedicalData.setDentalopdcount(cursor.getString(cursor.getColumnIndex("dentalopdcount")));
                popMedicalData.setEpfcount(cursor.getString(cursor.getColumnIndex("epfcount")));
                popMedicalData.setCataractcount(cursor.getString(cursor.getColumnIndex("cataractcount")));
                popMedicalData.setOrthocount(cursor.getString(cursor.getColumnIndex("orthocount")));
                popMedicalData.setPlasticsurgerycount(cursor.getString(cursor.getColumnIndex("plasticsurgerycount")));
                popMedicalData.setEntcount(cursor.getString(cursor.getColumnIndex("entcount")));
                popMedicalData.setGynaecount(cursor.getString(cursor.getColumnIndex("gynaecount")));
                popMedicalData.setOralcancercount(cursor.getString(cursor.getColumnIndex("oralcancercount")));

                arrayList.add(popMedicalData);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }


    public static ArrayList<PopMedicalData> getAllcountbyAnmname(String anmworkername,String campid) {
        open();
        ArrayList<PopMedicalData> arrayList = new ArrayList<>();
        arrayList.clear();

        String query = "SELECT (count(housholdid)) as totalhousehold,(count(DISTINCT housholdid)) as houseunique," +
                "(select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (eyeopd IS NOT NULL AND eyeopd != '') AND padaNashworkerTable.anmname  = '"+ anmworkername + "' AND populationNmedical.campId = '"+ campid +"') AS eyeopdcount," +
                "(select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (dentalopd IS NOT NULL AND dentalopd != '') AND padaNashworkerTable.anmname  = '"+ anmworkername + "' AND populationNmedical.campId = '"+ campid +"') AS dentalopdcount," +
                "(select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (epilepsyopd IS NOT NULL AND epilepsyopd != '')  AND padaNashworkerTable.anmname  = '"+ anmworkername + "' AND populationNmedical.campId = '"+ campid +"') AS epfcount," +
                "(select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (eyesurgery IS NOT NULL AND eyesurgery != '')  AND padaNashworkerTable.anmname  = '"+ anmworkername + "' AND populationNmedical.campId = '"+ campid +"') AS cataractcount," +
                "(select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (orthopedicsurgery IS NOT NULL AND orthopedicsurgery != '')  AND padaNashworkerTable.anmname  = '"+ anmworkername + "' AND populationNmedical.campId = '"+ campid +"') AS orthocount," +
                "(select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (plasticsurgeryopd IS NOT NULL AND plasticsurgeryopd != '')  AND padaNashworkerTable.anmname  = '"+ anmworkername + "' AND populationNmedical.campId = '"+ campid +"') AS plasticsurgerycount," +
                " (select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (entopd IS NOT NULL AND entopd != '')  AND padaNashworkerTable.anmname  = '"+ anmworkername + "' AND populationNmedical.campId = '"+ campid +"') AS entcount," +
                " (select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (gynaecopd IS NOT NULL AND gynaecopd != '')  AND padaNashworkerTable.anmname  = '"+ anmworkername + "' AND populationNmedical.campId = '"+ campid +"') AS gynaecount," +
                " (select count(*) from padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE (oralcanceropd IS NOT NULL AND oralcanceropd != '')  AND padaNashworkerTable.anmname  = '"+ anmworkername + "' AND populationNmedical.campId = '"+ campid +"') AS oralcancercount" +
                "  FROM  padaNashworkerTable INNER JOIN populationNmedical ON populationNmedical.padaAshaID = padaNashworkerTable.id WHERE padaNashworkerTable.anmname = '"+ anmworkername + "' AND populationNmedical.campId = '" +campid + "'";





        Log.i( "ckm=>opdbyfilter",query);

        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                PopMedicalData popMedicalData = new PopMedicalData();
              //  popMedicalData.setUserid(cursor.getInt(cursor.getColumnIndex(MUSERID)));
                popMedicalData.setHousholscount(cursor.getString(cursor.getColumnIndex("houseunique")));
                popMedicalData.setPopultioncount(cursor.getString(cursor.getColumnIndex("totalhousehold")));

                popMedicalData.setEyeopdcount(cursor.getString(cursor.getColumnIndex("eyeopdcount")));
                popMedicalData.setDentalopdcount(cursor.getString(cursor.getColumnIndex("dentalopdcount")));
                popMedicalData.setEpfcount(cursor.getString(cursor.getColumnIndex("epfcount")));
                popMedicalData.setCataractcount(cursor.getString(cursor.getColumnIndex("cataractcount")));
                popMedicalData.setOrthocount(cursor.getString(cursor.getColumnIndex("orthocount")));
                popMedicalData.setPlasticsurgerycount(cursor.getString(cursor.getColumnIndex("plasticsurgerycount")));
                popMedicalData.setEntcount(cursor.getString(cursor.getColumnIndex("entcount")));
                popMedicalData.setGynaecount(cursor.getString(cursor.getColumnIndex("gynaecount")));
                popMedicalData.setOralcancercount(cursor.getString(cursor.getColumnIndex("oralcancercount")));

                arrayList.add(popMedicalData);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }


    public static  void deletePopulationMedical() {
        open();
        database.execSQL("delete  from "+ TABLE_POPULATIONMEDICAL);
    }

    public static  Cursor raw() {
        open();
      //  SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = database.rawQuery("SELECT * FROM " + TABLE_POPULATIONMEDICAL , new String[]{});
        return res;
    }

}
