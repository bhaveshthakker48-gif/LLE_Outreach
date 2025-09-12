package org.impactindia.llemeddocket.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.impactindia.llemeddocket.pojo.SubTag;
import org.impactindia.llemeddocket.util.CursorUtils;
import org.impactindia.llemeddocket.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SubTagDAO extends BaseDAO<SubTag> {

    public static final String TAG = "SubTagDAO";
    public static final String TABLE_NAME = "sub_tag";

    public static final String CREATE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY, " +
            SubTag.SUB_TAG_ID + " INTEGER, " +
            SubTag.SUB_TAG_NAME + " TEXT, " +
            SubTag.TAG_ID + " INTEGER " +
            ");";

    public SubTagDAO(Context context, SQLiteDatabase db) {
        super(context, db);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public SubTag fromCursor(Cursor c) {
        SubTag subTag = new SubTag();
        subTag.setId(CursorUtils.extractLongOrNull(c, ID));
        subTag.setSubTagId(CursorUtils.extractLongOrNull(c, SubTag.SUB_TAG_ID));
        subTag.setSubTagName(CursorUtils.extractStringOrNull(c, SubTag.SUB_TAG_NAME));
        subTag.setTagId(CursorUtils.extractLongOrNull(c, SubTag.TAG_ID));
        return subTag;
    }

    @Override
    public ContentValues values(SubTag subTag) {
        ContentValues cv = new ContentValues();
        cv.put(ID, subTag.getId());
        cv.put(SubTag.SUB_TAG_ID, subTag.getSubTagId());
        cv.put(SubTag.SUB_TAG_NAME, subTag.getSubTagName());
        cv.put(SubTag.TAG_ID, subTag.getTagId());
        return cv;
    }

    public List<SubTag> findAllByTagIds(String value, String orderConditions) {
        Cursor c = null;
        List<SubTag> result = null;
        try {
            c = db.rawQuery("select * from " + getTableName() +
                            " where " + SubTag.TAG_ID + " in (" + value + ") " + StringUtils.safe(orderConditions),
                    null);
            result = new ArrayList<SubTag>();
            if (c.moveToFirst()) {
                do {
                    result.add(fromCursor(c));
                } while (c.moveToNext());
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return result;
    }
}
