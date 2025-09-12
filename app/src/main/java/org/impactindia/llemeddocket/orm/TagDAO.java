package org.impactindia.llemeddocket.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.impactindia.llemeddocket.pojo.Tag;
import org.impactindia.llemeddocket.util.CursorUtils;

public class TagDAO extends BaseDAO<Tag> {

    public static final String TAG = "TagDAO";
    public static final String TABLE_NAME = "tag";

    public TagDAO(Context context, SQLiteDatabase db) {
        super(context, db);
    }

    public static final String CREATE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY, " +
            Tag.TAG_ID + " INTEGER, " +
            Tag.USER_ID + " TEXT, " +
            Tag.TAG_NAME + " TEXT " +
            ");";

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Tag fromCursor(Cursor c) {
        Tag tag = new Tag();
        tag.setId(CursorUtils.extractLongOrNull(c, ID));
        tag.setTagId(CursorUtils.extractLongOrNull(c, Tag.TAG_ID));
        tag.setUserId(CursorUtils.extractLongOrNull(c, Tag.USER_ID));
        tag.setTagName(CursorUtils.extractStringOrNull(c, Tag.TAG_NAME));
        return tag;
    }

    @Override
    public ContentValues values(Tag tag) {
        ContentValues cv = new ContentValues();
        cv.put(ID, tag.getId());
        cv.put(Tag.TAG_ID, tag.getTagId());
        cv.put(Tag.USER_ID, tag.getUserId());
        cv.put(Tag.TAG_NAME, tag.getTagName());
        return cv;
    }
}
