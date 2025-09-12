package org.impactindia.llemeddocket;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.impactindia.llemeddocket.orm.BloodPressureNRDataDAO;
import org.impactindia.llemeddocket.orm.CampDAO;
import org.impactindia.llemeddocket.orm.CampPatientDAO;
import org.impactindia.llemeddocket.orm.CampProgramModel;
import org.impactindia.llemeddocket.orm.ChartNormalRangeModel;
import org.impactindia.llemeddocket.orm.ChartcommonMasterModel;
import org.impactindia.llemeddocket.orm.MedicalDetailsDAO;
import org.impactindia.llemeddocket.orm.OutreachPlanModel;
import org.impactindia.llemeddocket.orm.PadaNAshworkerDetailModel;
import org.impactindia.llemeddocket.orm.PatientDAO;
import org.impactindia.llemeddocket.orm.PhcSubCenterModel;
import org.impactindia.llemeddocket.orm.PhcUsersModel;
import org.impactindia.llemeddocket.orm.PopulationMedicalModel;
import org.impactindia.llemeddocket.orm.StateDAO;
import org.impactindia.llemeddocket.orm.SubTagDAO;
import org.impactindia.llemeddocket.orm.TagDAO;
import org.impactindia.llemeddocket.orm.UserDetailsDAO;
import org.impactindia.llemeddocket.orm.syncModel;
import org.impactindia.llemeddocket.pojo.ChartNormalRangeData;
import org.impactindia.llemeddocket.pojo.MedicalDetails;
import org.impactindia.llemeddocket.pojo.Patient;
import org.impactindia.llemeddocket.pojo.SyncData;
import org.impactindia.llemeddocket.pojo.UserDetails;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "DatabaseHelper";
    public static String DB_NAME = "llemeddocket.db";
    public static String STATIC = "";
    private static final int VERSION_INITIAL = 1;
    private static final int VERSION_HAS_IMG = 2;
    private static final int VERSION_MEDICAL_DATA = 3;
    private static final int VER_UPDATE_MEDICAL_DATA = 4;
    private static final int VER_UPDATE_USERDETAILS_DATA = 5;
    private static final int VER_UPDATE_OUTREACHTABLES_PADA_VILLAGE = 6;
    private static final int VER_UPDATE_SYNCHDATA = 7;
    private static final int VER_UPDATE_ORALCANCEROPD = 8;
    private static final int VER_UPDATE_CHART = 9;
    public static final int VERSION = VER_UPDATE_CHART;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");

        db.execSQL(UserDetailsDAO.CREATE_SQL);
        Log.d(TAG, UserDetailsDAO.TABLE_NAME + " table created");

        db.execSQL(CampDAO.CREATE_SQL);
        Log.d(TAG, CampDAO.TABLE_NAME + " table created");

        db.execSQL(StateDAO.CREATE_SQL);
        Log.d(TAG, StateDAO.TABLE_NAME + " table created");

        db.execSQL(TagDAO.CREATE_SQL);
        Log.d(TAG, TagDAO.TABLE_NAME + " table created");

        db.execSQL(PatientDAO.CREATE_SQL);
        Log.d(TAG, PatientDAO.TABLE_NAME + " table created");

        db.execSQL(CampPatientDAO.CREATE_SQL);
        Log.d(TAG, CampPatientDAO.TABLE_NAME + " table created");

        db.execSQL(BloodPressureNRDataDAO.CREATE_SQL);
        Log.d(TAG, BloodPressureNRDataDAO.TABLE_NAME + " table created");

        db.execSQL(MedicalDetailsDAO.CREATE_SQL);
        Log.d(TAG, MedicalDetailsDAO.TABLE_NAME + " table created");

        db.execSQL(SubTagDAO.CREATE_SQL);
        Log.d(TAG, SubTagDAO.TABLE_NAME + " table created");

         db.execSQL(CampProgramModel.CREATE_SQL);
        Log.d(TAG, CampProgramModel.TABLE_CAMPPROGRAM + " table created");

        db.execSQL(OutreachPlanModel.CREATE_SQL);
        Log.d(TAG, OutreachPlanModel.TABLE_OUTREACH + " table created");

        db.execSQL(PhcSubCenterModel.CREATE_SQL);
        Log.d(TAG, PhcSubCenterModel.TABLE_PHCSUBCENTER + " table created");

        db.execSQL(PhcUsersModel.CREATE_SQL);
        Log.d(TAG, PhcUsersModel.TABLE_PHCUSER + " table created");

        db.execSQL(PadaNAshworkerDetailModel.CREATE_SQL);
        Log.d(TAG, PadaNAshworkerDetailModel.TABLE_PADANASHAWORKERDETAILS + " table created");

        db.execSQL(PopulationMedicalModel.CREATE_SQL);
        Log.d(TAG, PopulationMedicalModel.TABLE_POPULATIONMEDICAL + " table created");


        db.execSQL(syncModel.CREATE_SQL);
        Log.d(TAG, PopulationMedicalModel.TABLE_POPULATIONMEDICAL + " table created");

        db.execSQL(ChartNormalRangeModel.CREATE_SQL);
        Log.d(TAG, ChartNormalRangeModel.CREATE_SQL + " table created");

        db.execSQL(ChartcommonMasterModel.CREATE_SQL);
        Log.d(TAG, ChartcommonMasterModel.CREATE_SQL + " table created");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade");
        switch (oldVersion) {
            case VERSION_INITIAL:
                db.execSQL("alter table " + PatientDAO.TABLE_NAME + " add column " + Patient.HAS_IMG + " INTEGER");
                break;
            case VERSION_HAS_IMG:
                db.execSQL("alter table " + PatientDAO.TABLE_NAME + " add column " + Patient.CAMP_ID + " INTEGER");

                db.execSQL(CampPatientDAO.CREATE_SQL);
                Log.d(TAG, CampPatientDAO.TABLE_NAME + " table created");

                db.execSQL(BloodPressureNRDataDAO.CREATE_SQL);
                Log.d(TAG, BloodPressureNRDataDAO.TABLE_NAME + " table created");

                db.execSQL(MedicalDetailsDAO.CREATE_SQL);
                Log.d(TAG, MedicalDetailsDAO.TABLE_NAME + " table created");
                break;
            case VERSION_MEDICAL_DATA:
                db.execSQL("alter table " + MedicalDetailsDAO.TABLE_NAME + " add column " + MedicalDetails.HBSAG + " TEXT");
                db.execSQL("alter table " + MedicalDetailsDAO.TABLE_NAME + " add column " + MedicalDetails.HIV + " TEXT");
                db.execSQL("alter table " + MedicalDetailsDAO.TABLE_NAME + " add column " + MedicalDetails.HEMOGLOBIN + " TEXT");
                db.execSQL("alter table " + MedicalDetailsDAO.TABLE_NAME + " add column " + MedicalDetails.HEMOGLOBIN_INTER + " TEXT");
                db.execSQL("alter table " + MedicalDetailsDAO.TABLE_NAME + " add column " + MedicalDetails.DIAGNOSIS + " TEXT");
                db.execSQL("alter table " + MedicalDetailsDAO.TABLE_NAME + " add column " + MedicalDetails.OPD + " INTEGER");
                db.execSQL("alter table " + MedicalDetailsDAO.TABLE_NAME + " add column " + MedicalDetails.SURGERY + " INTEGER");
                db.execSQL("alter table " + MedicalDetailsDAO.TABLE_NAME + " add column " + MedicalDetails.SUB_TAG_NAME + " TEXT");
                db.execSQL("alter table " + MedicalDetailsDAO.TABLE_NAME + " add column " + MedicalDetails.SUB_TAG_ID + " TEXT");
                db.execSQL("alter table " + MedicalDetailsDAO.TABLE_NAME + " add column " + MedicalDetails.TAG_ID + " TEXT");

                db.execSQL("alter table " + CampPatientDAO.TABLE_NAME + " add column " + Patient.TAG_ID + " TEXT");

                db.execSQL(SubTagDAO.CREATE_SQL);
                Log.d(TAG, SubTagDAO.TABLE_NAME + " table created");

                break;
            case VER_UPDATE_MEDICAL_DATA:
                db.execSQL("alter table " + UserDetailsDAO.TABLE_NAME + " add column " + UserDetails.DESIGNATION + " TEXT");

                db.execSQL(CampProgramModel.CREATE_SQL);
                Log.d(TAG, CampProgramModel.TABLE_CAMPPROGRAM + " table created");

                db.execSQL(OutreachPlanModel.CREATE_SQL);
                Log.d(TAG, OutreachPlanModel.TABLE_OUTREACH + " table created");

                db.execSQL(PhcSubCenterModel.CREATE_SQL);
                Log.d(TAG, PhcSubCenterModel.TABLE_PHCSUBCENTER + " table created");

                db.execSQL(PhcUsersModel.CREATE_SQL);
                Log.d(TAG, PhcUsersModel.TABLE_PHCUSER + " table created");

                db.execSQL(PadaNAshworkerDetailModel.CREATE_SQL);
                Log.d(TAG, PadaNAshworkerDetailModel.TABLE_PADANASHAWORKERDETAILS + " table created");

                db.execSQL(PopulationMedicalModel.CREATE_SQL);
                Log.d(TAG, PopulationMedicalModel.TABLE_POPULATIONMEDICAL + " table created");
                break;
            case VER_UPDATE_USERDETAILS_DATA:

                db.execSQL("alter table " + PopulationMedicalModel.TABLE_POPULATIONMEDICAL + " add column " + PopulationMedicalModel.MGYNAECOPD + " TEXT");

                db.execSQL("alter table " + PhcSubCenterModel.TABLE_PHCSUBCENTER + " add column " + PhcSubCenterModel.VILLAGE + " TEXT");
                db.execSQL("alter table " + PhcSubCenterModel.TABLE_PHCSUBCENTER + " add column " + PhcSubCenterModel.PADA + " TEXT");

                db.execSQL("alter table " + PhcUsersModel.TABLE_PHCUSER + " add column " + PhcUsersModel.SUBCENTER + " TEXT");
                db.execSQL("alter table " + PhcUsersModel.TABLE_PHCUSER + " add column " + PhcUsersModel.VILLAGE + " TEXT");
                db.execSQL("alter table " + PhcUsersModel.TABLE_PHCUSER + " add column " + PhcUsersModel.PADA + " TEXT");

                break;

            case VER_UPDATE_OUTREACHTABLES_PADA_VILLAGE:
                db.execSQL(syncModel.CREATE_SQL);
                Log.d(TAG, syncModel.TABLE_SYNCH + " table created");

                db.execSQL("alter table " + PopulationMedicalModel.TABLE_POPULATIONMEDICAL + " add column " + PopulationMedicalModel.DATEOFEXAMINATION + " TEXT");
                db.execSQL("alter table " + PopulationMedicalModel.TABLE_POPULATIONMEDICAL + " add column " + PopulationMedicalModel.EARPROBDETAILS + " TEXT");
                db.execSQL("alter table " + PopulationMedicalModel.TABLE_POPULATIONMEDICAL + " add column " + PopulationMedicalModel.PATIENTREFUSED + " INTEGER");
                break;

            case VER_UPDATE_SYNCHDATA:
                db.execSQL("alter table " + PopulationMedicalModel.TABLE_POPULATIONMEDICAL + " add column " + PopulationMedicalModel.MORALCANCEROPD + " TEXT");
                break;

            case VER_UPDATE_ORALCANCEROPD:
                db.execSQL(ChartNormalRangeModel.CREATE_SQL);
                Log.d(TAG, ChartNormalRangeModel.CREATE_SQL + " table created");

                db.execSQL(ChartcommonMasterModel.CREATE_SQL);
                Log.d(TAG, ChartcommonMasterModel.CREATE_SQL + " table created");

                db.execSQL("alter table " + PopulationMedicalModel.TABLE_POPULATIONMEDICAL + " add column " + PopulationMedicalModel.MBMIINTER + " TEXT");
                db.execSQL("alter table " + PopulationMedicalModel.TABLE_POPULATIONMEDICAL + " add column " + PopulationMedicalModel.WEIGHTUNIT + " TEXT");
                db.execSQL("alter table " + PopulationMedicalModel.TABLE_POPULATIONMEDICAL + " add column " + PopulationMedicalModel.MRELATIONDETAILS + " TEXT");
                break;
        }
    }
}
