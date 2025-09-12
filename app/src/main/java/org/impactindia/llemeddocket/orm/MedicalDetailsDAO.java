package org.impactindia.llemeddocket.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.impactindia.llemeddocket.pojo.MedicalDetails;
import org.impactindia.llemeddocket.util.CursorUtils;

public class MedicalDetailsDAO extends BaseDAO<MedicalDetails> {

    public static final String TAG = "MedicalDetailsDAO";
    public static final String TABLE_NAME = "medical_details";

    public static final String CREATE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY, " +
            MedicalDetails.CAMP_ID + " INTEGER, " +
            MedicalDetails.USER_ID + " TEXT, " +
            MedicalDetails.PATIENT_ID + " TEXT, " +
            MedicalDetails.DATE_OF_EXAMINATION + " TEXT, " +
            MedicalDetails.BP_SYSTOLIC + " TEXT, " +
            MedicalDetails.BP_DIASTOLIC + " TEXT, " +
            MedicalDetails.BP_INTER + " TEXT, " +
            MedicalDetails.BLD_GLUCOSE_RANDOM + " TEXT, " +
            MedicalDetails.BLD_GLUCOSE_RANDOM_INTER + " TEXT, " +
            MedicalDetails.WEIGHT + " TEXT, " +
            MedicalDetails.HBSAG + " TEXT, " +
            MedicalDetails.HIV + " TEXT, " +
            MedicalDetails.HEMOGLOBIN + " TEXT, " +
            MedicalDetails.HEMOGLOBIN_INTER + " TEXT, " +
            MedicalDetails.DIAGNOSIS + " TEXT, " +
            MedicalDetails.OPD + " INTEGER, " +
            MedicalDetails.SURGERY + " INTEGER, " +
            MedicalDetails.SUB_TAG_NAME + " TEXT, " +
            MedicalDetails.SUB_TAG_ID + " TEXT, " +
            MedicalDetails.TAG_ID + " TEXT, " +
            FRESH + " INTEGER " +
            ");";

    public MedicalDetailsDAO(Context context, SQLiteDatabase db) {
        super(context, db);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public MedicalDetails fromCursor(Cursor c) {
        MedicalDetails details = new MedicalDetails();
        details.setId(CursorUtils.extractLongOrNull(c, ID));
        details.setCampId(CursorUtils.extractLongOrNull(c, MedicalDetails.CAMP_ID));
        details.setUserId(CursorUtils.extractLongOrNull(c, MedicalDetails.USER_ID));
        details.setPatientId(CursorUtils.extractLongOrNull(c, MedicalDetails.PATIENT_ID));
        details.setDateOfExamination(CursorUtils.extractStringOrNull(c, MedicalDetails.DATE_OF_EXAMINATION));
        details.setBpSystolic(CursorUtils.extractStringOrNull(c, MedicalDetails.BP_SYSTOLIC));
        details.setBpDiastolic(CursorUtils.extractStringOrNull(c, MedicalDetails.BP_DIASTOLIC));
        details.setBpInterPretation(CursorUtils.extractStringOrNull(c, MedicalDetails.BP_INTER));
        details.setBldGlucoseRandom(CursorUtils.extractStringOrNull(c, MedicalDetails.BLD_GLUCOSE_RANDOM));
        details.setBldGlucoseRandomInterPretation(CursorUtils.extractStringOrNull(c, MedicalDetails.BLD_GLUCOSE_RANDOM_INTER));
        details.setWeight(CursorUtils.extractStringOrNull(c, MedicalDetails.WEIGHT));
        details.setHbsag(CursorUtils.extractStringOrNull(c, MedicalDetails.HBSAG));
        details.setHiv(CursorUtils.extractStringOrNull(c, MedicalDetails.HIV));
        details.setHemoglobin(CursorUtils.extractStringOrNull(c, MedicalDetails.HEMOGLOBIN));
        details.setHemoglobinInter(CursorUtils.extractStringOrNull(c, MedicalDetails.HEMOGLOBIN_INTER));
        details.setDiagnosis(CursorUtils.extractStringOrNull(c, MedicalDetails.DIAGNOSIS));
        details.setOpd(CursorUtils.extractBoolean(c, MedicalDetails.OPD));
        details.setSurgery(CursorUtils.extractBoolean(c, MedicalDetails.SURGERY));
        details.setSubTagName(CursorUtils.extractStringOrNull(c, MedicalDetails.SUB_TAG_NAME));
        details.setSubTagId(CursorUtils.extractStringOrNull(c, MedicalDetails.SUB_TAG_ID));
        details.setTagId(CursorUtils.extractStringOrNull(c, MedicalDetails.TAG_ID));
        details.setFresh(CursorUtils.extractBoolean(c, FRESH));
        return details;
    }

    @Override
    public ContentValues values(MedicalDetails medicalDetails) {
        ContentValues cv = new ContentValues();
        cv.put(ID, medicalDetails.getId());
        cv.put(MedicalDetails.CAMP_ID, medicalDetails.getCampId());
        cv.put(MedicalDetails.USER_ID, medicalDetails.getUserId());
        cv.put(MedicalDetails.PATIENT_ID, medicalDetails.getPatientId());
        cv.put(MedicalDetails.DATE_OF_EXAMINATION, medicalDetails.getDateOfExamination());
        cv.put(MedicalDetails.BP_SYSTOLIC, medicalDetails.getBpSystolic());
        cv.put(MedicalDetails.BP_DIASTOLIC, medicalDetails.getBpDiastolic());
        cv.put(MedicalDetails.BP_INTER, medicalDetails.getBpInterPretation());
        cv.put(MedicalDetails.BLD_GLUCOSE_RANDOM, medicalDetails.getBldGlucoseRandom());
        cv.put(MedicalDetails.BLD_GLUCOSE_RANDOM_INTER, medicalDetails.getBldGlucoseRandomInterPretation());
        cv.put(MedicalDetails.WEIGHT, medicalDetails.getWeight());
        cv.put(MedicalDetails.HBSAG, medicalDetails.getHbsag());
        cv.put(MedicalDetails.HIV, medicalDetails.getHiv());
        cv.put(MedicalDetails.HEMOGLOBIN, medicalDetails.getHemoglobin());
        cv.put(MedicalDetails.HEMOGLOBIN_INTER, medicalDetails.getHemoglobinInter());
        cv.put(MedicalDetails.DIAGNOSIS, medicalDetails.getDiagnosis());
        cv.put(MedicalDetails.OPD, medicalDetails.isOpd() ? 1 : 0);
        cv.put(MedicalDetails.SURGERY, medicalDetails.isSurgery() ? 1 : 0);
        cv.put(MedicalDetails.SUB_TAG_NAME, medicalDetails.getSubTagName());
        cv.put(MedicalDetails.SUB_TAG_ID, medicalDetails.getSubTagId());
        cv.put(MedicalDetails.TAG_ID, medicalDetails.getTagId());
        cv.put(FRESH, medicalDetails.isFresh() ? 1 : 0);

        return cv;
    }
}
