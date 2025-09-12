package org.impactindia.llemeddocket.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.impactindia.llemeddocket.pojo.BloodPressureNRData;
import org.impactindia.llemeddocket.pojo.UserDetails;
import org.impactindia.llemeddocket.util.CursorUtils;

public class BloodPressureNRDataDAO extends BaseDAO<BloodPressureNRData> {

    private static final String TAG = "BloodPressureNRDataDAO";
    public static final String TABLE_NAME = "md_and_school_bloodpressure_normalrange_master";

    public final static String AGE = "age";
    public final static String SYSTOLIC_MMHG = "systolic_mmhg";
    private final static String DIASTOLIC_MMHG = "diastolic_mmhg";
    private final static String AGE_RANGE_START = "agerange_start";
    private final static String AGE_RANGE_END = "agerange_end";
    private final static String AGE_RANGE_TYPE = "agerange_type";
    private final static String SYSTOLIC_RANGE_START = "systolicrange_start";
    private final static String SYSTOLIC_RANGE_END = "systolicrange_end";
    private final static String DIASTOLIC_RANGE_START = "diastolicrange_start";
    private final static String DIASTOLIC_RANGE_END = "diastolicrange_end";
    private final static String CREATED_DATE = "created_date";
    private final static String MODIFIED_DATE = "modified_date";

    public static final String CREATE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY, " +
            AGE + " INTEGER, " +
            AGE_RANGE_START + " TEXT, " +
            AGE_RANGE_END + " TEXT, " +
            AGE_RANGE_TYPE + " TEXT, " +
            SYSTOLIC_MMHG + " TEXT, " +
            SYSTOLIC_RANGE_START + " TEXT, " +
            SYSTOLIC_RANGE_END + " TEXT, " +
            DIASTOLIC_MMHG + " TEXT, " +
            DIASTOLIC_RANGE_START + " TEXT, " +
            DIASTOLIC_RANGE_END + " TEXT, " +
            CREATED_DATE + " TEXT, " +
            MODIFIED_DATE + " TEXT " +
            ");";

    public BloodPressureNRDataDAO(Context context, SQLiteDatabase db) {
        super(context, db);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public BloodPressureNRData fromCursor(Cursor c) {
        BloodPressureNRData data = new BloodPressureNRData();
        data.setId(CursorUtils.extractLongOrNull(c, ID));
        data.setAge(CursorUtils.extractStringOrNull(c, AGE));
        data.setAgeRangeStart(CursorUtils.extractStringOrNull(c, AGE_RANGE_START));
        data.setAgeRangeEnd(CursorUtils.extractStringOrNull(c, AGE_RANGE_END));
        data.setAgeRangeType(CursorUtils.extractStringOrNull(c, AGE_RANGE_TYPE));
        data.setSystolicMMHG(CursorUtils.extractStringOrNull(c, SYSTOLIC_MMHG));
        data.setSystolicRangeStart(CursorUtils.extractStringOrNull(c, SYSTOLIC_RANGE_START));
        data.setSystolicRangeEnd(CursorUtils.extractStringOrNull(c, SYSTOLIC_RANGE_END));
        data.setDiastolicMMHG(CursorUtils.extractStringOrNull(c, DIASTOLIC_MMHG));
        data.setDiastolicRangeStart(CursorUtils.extractStringOrNull(c, DIASTOLIC_RANGE_START));
        data.setDiastolicRangeEnd(CursorUtils.extractStringOrNull(c, DIASTOLIC_RANGE_END));
        data.setCreatedDate(CursorUtils.extractStringOrNull(c, CREATED_DATE));
        data.setModifiedDate(CursorUtils.extractStringOrNull(c, MODIFIED_DATE));
        return data;
    }

    @Override
    public ContentValues values(BloodPressureNRData data) {
        ContentValues cv = new ContentValues();
        cv.put(AGE, data.getAge());
        cv.put(AGE_RANGE_START, data.getAgeRangeStart());
        cv.put(AGE_RANGE_END, data.getAgeRangeEnd());
        cv.put(AGE_RANGE_TYPE, data.getAgeRangeType());
        cv.put(SYSTOLIC_MMHG, data.getSystolicMMHG());
        cv.put(SYSTOLIC_RANGE_START, data.getSystolicRangeStart());
        cv.put(SYSTOLIC_RANGE_END, data.getSystolicRangeEnd());
        cv.put(DIASTOLIC_MMHG, data.getDiastolicMMHG());
        cv.put(DIASTOLIC_RANGE_START, data.getDiastolicRangeStart());
        cv.put(DIASTOLIC_RANGE_END, data.getDiastolicRangeEnd());
        cv.put(CREATED_DATE, data.getCreatedDate());
        cv.put(MODIFIED_DATE, data.getModifiedDate());
        return cv;
    }

    public BloodPressureNRData getBloodPressureRangeByAge(String ageInMonths) throws DAOException {
        Cursor c = null;
        BloodPressureNRData data = null;
        try {
            String query = "select * from " + getTableName() + " where " + "CAST(" + AGE_RANGE_START + " AS INTEGER)" + " <= ?" + " and "
                    + "CAST(" + AGE_RANGE_END + " AS INTEGER)" + " > ?";
            Log.d(TAG, query);
            c = db.rawQuery(query, new String[]{ageInMonths, ageInMonths});
            if (c.moveToFirst()) {
                data = fromCursor(c);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return data;
    }

}
