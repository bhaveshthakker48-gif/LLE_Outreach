package org.impactindia.llemeddocket.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.impactindia.llemeddocket.pojo.Patient;
import org.impactindia.llemeddocket.pojo.Tag;
import org.impactindia.llemeddocket.util.CursorUtils;

import java.util.ArrayList;
import java.util.List;

public class PatientDAO extends BaseDAO<Patient> {

    public static final String TAG = "PatientDAO";
    public static final String TABLE_NAME = "patient";

    public PatientDAO(Context context, SQLiteDatabase db) {
        super(context, db);
    }

    public static final String CREATE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY, " +
            Patient.CAMP_ID + " INTEGER, " +
            Patient.USER_ID + " INTEGER, " +
            Patient.REG_NO + " TEXT, " +
            Patient.PATIENT_ID + " INTEGER, " +
            Patient.FNAME + " TEXT, " +
            Patient.MNAME + " TEXT, " +
            Patient.LNAME + " TEXT, " +
            Patient.GENDER + " TEXT, " +
            Patient.DOB + " TEXT, " +
            Patient.AGE + " INTEGER, " +
            Patient.AGE_UNIT + " TEXT, " +
            Patient.HOUSE_NAME_NO + " TEXT, " +
            Patient.STREET_NAME + " TEXT, " +
            Patient.LOCALITY_AREA_PADA + " TEXT, " +
            Patient.CITY_TOWN_VILLAGE + " TEXT, " +
            Patient.TALUKA + " TEXT, " +
            Patient.DISTRICT + " TEXT, " +
            Patient.STATE_ID + " INTEGER, " +
            Patient.STATE_NAME + " TEXT, " +
            Patient.PIN_CODE + " TEXT, " +
            Patient.COUNTRY_NAME + " TEXT, " +
            Patient.MOBILE_NO + " TEXT, " +
            Patient.RESIDENCE_NO + " TEXT, " +
            Patient.AADHAR_NO + " TEXT, " +
            Patient.EMAIL_ID + " TEXT, " +
            Patient.REFERRED_BY + " TEXT, " +
            Patient.TAG_NAME + " TEXT, " +
            Patient.TAG_ID + " TEXT, " +
            Patient.PROFILE_IMG_PATH + " TEXT, " +
            Patient.PROFILE_IMG_NAME + " TEXT, " +
            Patient.HAS_IMG + " INTEGER, " +
            Patient.IS_ACTIVE + " INTEGER, " +
            Patient.LAST_VISIT_DATE + " TEXT, " +
            Patient.DATE_CREATED + " TEXT, " +
            Patient.DATE_MODIFIED + " TEXT, " +
            FRESH + " INTEGER " +
            ");";

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Patient fromCursor(Cursor c) {
        Patient patient = new Patient();
        patient.setId(CursorUtils.extractLongOrNull(c, ID));
        patient.setCampId(CursorUtils.extractLongOrNull(c, Patient.CAMP_ID));
        patient.setUserId(CursorUtils.extractLongOrNull(c,Patient.USER_ID));
        patient.setRegNo(CursorUtils.extractStringOrNull(c,Patient.REG_NO));
        patient.setPatientId(CursorUtils.extractLongOrNull(c,Patient.PATIENT_ID));
        patient.setfName(CursorUtils.extractStringOrNull(c,Patient.FNAME));
        patient.setmName(CursorUtils.extractStringOrNull(c,Patient.MNAME));
        patient.setlName(CursorUtils.extractStringOrNull(c,Patient.LNAME));
        patient.setGender(CursorUtils.extractStringOrNull(c,Patient.GENDER));
        patient.setDob(CursorUtils.extractStringOrNull(c,Patient.DOB));
        patient.setAge(CursorUtils.extractIntegerOrNull(c,Patient.AGE));
        patient.setAgeUnit(CursorUtils.extractStringOrNull(c,Patient.AGE_UNIT));
        patient.setHouseNameNo(CursorUtils.extractStringOrNull(c,Patient.HOUSE_NAME_NO));
        patient.setStreetName(CursorUtils.extractStringOrNull(c,Patient.STREET_NAME));
        patient.setLocalityAreaPada(CursorUtils.extractStringOrNull(c,Patient.LOCALITY_AREA_PADA));
        patient.setCityTownVillage(CursorUtils.extractStringOrNull(c,Patient.CITY_TOWN_VILLAGE));
        patient.setTaluka(CursorUtils.extractStringOrNull(c,Patient.TALUKA));
        patient.setDistrict(CursorUtils.extractStringOrNull(c,Patient.DISTRICT));
        patient.setStateId(CursorUtils.extractLongOrNull(c,Patient.STATE_ID));
        patient.setStateName(CursorUtils.extractStringOrNull(c,Patient.STATE_NAME));
        patient.setPinCode(CursorUtils.extractStringOrNull(c,Patient.PIN_CODE));
        patient.setCountryName(CursorUtils.extractStringOrNull(c,Patient.COUNTRY_NAME));
        patient.setMobileNo(CursorUtils.extractStringOrNull(c,Patient.MOBILE_NO));
        patient.setResidenceNo(CursorUtils.extractStringOrNull(c,Patient.RESIDENCE_NO));
        patient.setAadharNo(CursorUtils.extractStringOrNull(c,Patient.AADHAR_NO));
        patient.setEmailId(CursorUtils.extractStringOrNull(c,Patient.EMAIL_ID));
        patient.setReferredBy(CursorUtils.extractStringOrNull(c,Patient.REFERRED_BY));
        patient.setTagName(CursorUtils.extractStringOrNull(c,Patient.TAG_NAME));
        patient.setTagId(CursorUtils.extractStringOrNull(c,Patient.TAG_ID));
        patient.setProfileImgPath(CursorUtils.extractStringOrNull(c,Patient.PROFILE_IMG_PATH));
        patient.setProfileImgName(CursorUtils.extractStringOrNull(c,Patient.PROFILE_IMG_NAME));
        patient.setHasImg(CursorUtils.extractBoolean(c, Patient.HAS_IMG));
        patient.setActive(CursorUtils.extractBoolean(c,Patient.IS_ACTIVE));
        patient.setLastVisitDate(CursorUtils.extractStringOrNull(c,Patient.LAST_VISIT_DATE));
        patient.setDateCreated(CursorUtils.extractStringOrNull(c,Patient.DATE_CREATED));
        patient.setDateModified(CursorUtils.extractStringOrNull(c,Patient.DATE_MODIFIED));
        patient.setFresh(CursorUtils.extractBoolean(c,FRESH));
        return patient;
    }

    @Override
    public ContentValues values(Patient patient) {
        ContentValues cv = new ContentValues();
        cv.put(ID, patient.getId());
        cv.put(Patient.CAMP_ID, patient.getCampId());
        cv.put(Patient.USER_ID, patient.getUserId());
        cv.put(Patient.REG_NO, patient.getRegNo());
        cv.put(Patient.PATIENT_ID, patient.getPatientId());
        cv.put(Patient.FNAME, patient.getfName());
        cv.put(Patient.MNAME, patient.getmName());
        cv.put(Patient.LNAME, patient.getlName());
        cv.put(Patient.GENDER, patient.getGender());
        cv.put(Patient.DOB, patient.getDob());
        cv.put(Patient.AGE, patient.getAge());
        cv.put(Patient.AGE_UNIT, patient.getAgeUnit());
        cv.put(Patient.HOUSE_NAME_NO, patient.getHouseNameNo());
        cv.put(Patient.STREET_NAME, patient.getStreetName());
        cv.put(Patient.LOCALITY_AREA_PADA, patient.getLocalityAreaPada());
        cv.put(Patient.CITY_TOWN_VILLAGE, patient.getCityTownVillage());
        cv.put(Patient.TALUKA, patient.getTaluka());
        cv.put(Patient.DISTRICT, patient.getDistrict());
        cv.put(Patient.STATE_ID, patient.getStateId());
        cv.put(Patient.STATE_NAME, patient.getStateName());
        cv.put(Patient.PIN_CODE, patient.getPinCode());
        cv.put(Patient.COUNTRY_NAME, patient.getCountryName());
        cv.put(Patient.MOBILE_NO, patient.getMobileNo());
        cv.put(Patient.RESIDENCE_NO, patient.getResidenceNo());
        cv.put(Patient.AADHAR_NO, patient.getAadharNo());
        cv.put(Patient.EMAIL_ID, patient.getEmailId());
        cv.put(Patient.REFERRED_BY, patient.getReferredBy());
        cv.put(Patient.TAG_NAME, patient.getTagName());
        cv.put(Patient.TAG_ID, patient.getTagId());
        cv.put(Patient.PROFILE_IMG_PATH, patient.getProfileImgPath());
        cv.put(Patient.PROFILE_IMG_NAME, patient.getProfileImgName());
        cv.put(Patient.HAS_IMG, patient.isHasImg() ? 1 : 0);
        cv.put(Patient.IS_ACTIVE, patient.isActive() ? 1 : 0);
        cv.put(Patient.LAST_VISIT_DATE, patient.getLastVisitDate());
        cv.put(Patient.DATE_CREATED, patient.getDateCreated());
        cv.put(Patient.DATE_MODIFIED, patient.getDateModified());
        cv.put(FRESH, patient.isFresh() ? 1 : 0);
        return cv;
    }

    public List<Patient> findAllPatientsWithProfileImg() throws DAOException {
        Cursor c = null;
        List<Patient> result = null;
        String query;
        try {
            query = "select * from " + getTableName() + " where " + Patient.HAS_IMG + " = 1";
            Log.d(TAG, query);
            c = db.rawQuery(query, null);
            result = new ArrayList<>();
            if (c.moveToFirst()) {
                do {
                    result.add(fromCursor(c));
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            throw new DAOException(e);
        } finally {
            if (c != null) { c.close(); }
        }
        return result;
    }

    public List<Patient> getSuggestionsByNameOrPhoneNo(String value) throws DAOException {
        Cursor c = null;
        List<Patient> result = null;
        try {
            String query = "select * from " + getTableName() + " where " + "(" + Patient.FNAME + "||' '||" + Patient.LNAME + " like ? or " + Patient.FNAME + " like ? or " + Patient.LNAME + " like ? "  + " or " + Patient.REG_NO + " like ? " + " or " + Patient.MOBILE_NO + " like ? " + " or " + Patient.RESIDENCE_NO + " like ? )" + " order by " + ID + " asc";
            Log.d(TAG, query);
            c = db.rawQuery(query, new String[]{"%" + value + "%", "%" + value + "%", "%" + value + "%", "%" + value + "%", "%" + value + "%", "%" + value + "%"});
            result = new ArrayList<Patient>();
            if (c.moveToFirst()) {
                do {
                    result.add(fromCursor(c));
                } while (c.moveToNext());
            }
        } catch (Exception exception) {
            throw new DAOException(exception);
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return result;
    }
}
