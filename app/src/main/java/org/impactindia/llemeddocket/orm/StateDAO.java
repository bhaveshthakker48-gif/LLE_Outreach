package org.impactindia.llemeddocket.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.impactindia.llemeddocket.pojo.State;
import org.impactindia.llemeddocket.pojo.Tag;
import org.impactindia.llemeddocket.util.CursorUtils;

public class StateDAO extends BaseDAO<State> {

    public static final String TAG = "StateDAO";
    public static final String TABLE_NAME = "state";

    public static final String CREATE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY, " +
            State.STATE_ID + " INTEGER, " +
            State.STATE_NAME + " TEXT, " +
            State.COUNTRY_ID + " INTEGER " +
            ");";


    public StateDAO(Context context, SQLiteDatabase db) {
        super(context, db);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public State fromCursor(Cursor c) {
        State state = new State();
        state.setId(CursorUtils.extractLongOrNull(c, ID));
        state.setStateId(CursorUtils.extractLongOrNull(c, State.STATE_ID));
        state.setStateName(CursorUtils.extractStringOrNull(c, State.STATE_NAME));
        state.setCountryId(CursorUtils.extractLongOrNull(c, State.COUNTRY_ID));
        return state;
    }

    @Override
    public ContentValues values(State state) {
        ContentValues cv = new ContentValues();
        cv.put(ID, state.getId());
        cv.put(State.STATE_ID, state.getStateId());
        cv.put(State.STATE_NAME, state.getStateName());
        cv.put(State.COUNTRY_ID, state.getCountryId());
        return cv;
    }
}
