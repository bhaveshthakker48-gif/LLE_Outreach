package org.impactindia.llemeddocket.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.impactindia.llemeddocket.pojo.Camp;
import org.impactindia.llemeddocket.pojo.Tag;
import org.impactindia.llemeddocket.util.CursorUtils;

public class CampDAO extends BaseDAO<Camp> {

    public static final String TAG = "CampDAO";
    public static final String TABLE_NAME = "camp";

    public static final String CREATE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY, " +
            Camp.CAMP_ID + " INTEGER, " +
            Camp.CAMP_YEAR + " TEXT, " +
            Camp.CAMP_NO + " TEXT, " +
            Camp.CAMP_LOCATION + " TEXT, " +
            Camp.FROM_DATE + " TEXT, " +
            Camp.TO_DATE + " TEXT " +
            ");";

    public CampDAO(Context context, SQLiteDatabase db) {
        super(context, db);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Camp fromCursor(Cursor c) {
        Camp camp = new Camp();
        camp.setId(CursorUtils.extractLongOrNull(c, ID));
        camp.setCampId(CursorUtils.extractLongOrNull(c, Camp.CAMP_ID));
        camp.setCampYear(CursorUtils.extractStringOrNull(c, Camp.CAMP_YEAR));
        camp.setCampNo(CursorUtils.extractStringOrNull(c, Camp.CAMP_NO));
        camp.setCampLocation(CursorUtils.extractStringOrNull(c, Camp.CAMP_LOCATION));
        camp.setFromDate(CursorUtils.extractStringOrNull(c, Camp.FROM_DATE));
        camp.setToDate(CursorUtils.extractStringOrNull(c, Camp.TO_DATE));
        return camp;
    }

    @Override
    public ContentValues values(Camp camp) {
        ContentValues cv = new ContentValues();
        cv.put(ID, camp.getId());
        cv.put(Camp.CAMP_ID, camp.getCampId());
        cv.put(Camp.CAMP_YEAR, camp.getCampYear());
        cv.put(Camp.CAMP_NO, camp.getCampNo());
        cv.put(Camp.CAMP_LOCATION, camp.getCampLocation());
        cv.put(Camp.FROM_DATE, camp.getFromDate());
        cv.put(Camp.TO_DATE, camp.getToDate());
        return cv;
    }
}
