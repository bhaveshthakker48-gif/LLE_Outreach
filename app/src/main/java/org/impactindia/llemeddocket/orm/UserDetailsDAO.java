package org.impactindia.llemeddocket.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.impactindia.llemeddocket.DatabaseHelper;
import org.impactindia.llemeddocket.pojo.OutreachPlanData;
import org.impactindia.llemeddocket.pojo.PhcSubcenterData;
import org.impactindia.llemeddocket.pojo.UserDetails;
import org.impactindia.llemeddocket.util.CursorUtils;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsDAO extends BaseDAO<UserDetails> {

    public static final String TAG = "UserDetailsDAO";
    public static final String TABLE_NAME = "user_details";

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

    public static final String CREATE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY, " +
            UserDetails.USER_ID + " INTEGER, " +
            UserDetails.USER_NAME + " TEXT, " +
            UserDetails.PASSWORD + " TEXT, " +
            UserDetails.TITLE + " TEXT, " +
            UserDetails.FIRST_NAME + " TEXT, " +
            UserDetails.LAST_NAME + " TEXT, " +
            UserDetails.NAME + " TEXT, " +
            UserDetails.PREFIX + " TEXT, " +
            UserDetails.USER_TYPE + " TEXT, " +
            UserDetails.ADDRESS + " TEXT, " +
            UserDetails.MOBILE_NUMBER + " TEXT, " +
            UserDetails.CONTACT_NO + " TEXT, " +
            UserDetails.DESIGNATION + " TEXT, " +
            UserDetails.EMAIL_ID + " TEXT " +
            ");";

    public UserDetailsDAO(Context context, SQLiteDatabase db) {
        super(context, db);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public UserDetails fromCursor(Cursor c) {
        UserDetails userDetails = new UserDetails();
        userDetails.setId(CursorUtils.extractLongOrNull(c, ID));
        userDetails.setUserId(CursorUtils.extractLongOrNull(c, UserDetails.USER_ID));
        userDetails.setUserName(CursorUtils.extractStringOrNull(c, UserDetails.USER_NAME));
        userDetails.setPassword(CursorUtils.extractStringOrNull(c, UserDetails.PASSWORD));
        userDetails.setTitle(CursorUtils.extractStringOrNull(c, UserDetails.TITLE));
        userDetails.setFirstName(CursorUtils.extractStringOrNull(c, UserDetails.FIRST_NAME));
        userDetails.setLastName(CursorUtils.extractStringOrNull(c, UserDetails.LAST_NAME));
        userDetails.setName(CursorUtils.extractStringOrNull(c, UserDetails.NAME));
        userDetails.setPrefix(CursorUtils.extractStringOrNull(c, UserDetails.PREFIX));
        userDetails.setUserType(CursorUtils.extractStringOrNull(c, UserDetails.USER_TYPE));
        userDetails.setAddress(CursorUtils.extractStringOrNull(c, UserDetails.ADDRESS));
        userDetails.setMobileNumber(CursorUtils.extractStringOrNull(c, UserDetails.MOBILE_NUMBER));
        userDetails.setContactNo(CursorUtils.extractStringOrNull(c, UserDetails.CONTACT_NO));
        userDetails.setDesignation(CursorUtils.extractStringOrNull(c,UserDetails.DESIGNATION));
        userDetails.setEmailId(CursorUtils.extractStringOrNull(c, UserDetails.EMAIL_ID));
        return userDetails;
    }

    @Override
    public ContentValues values(UserDetails userDetails) {
        ContentValues cv = new ContentValues();
        cv.put(ID, userDetails.getId());
        cv.put(UserDetails.USER_ID, userDetails.getUserId());
        cv.put(UserDetails.USER_NAME, userDetails.getUserName());
        cv.put(UserDetails.PASSWORD, userDetails.getPassword());
        cv.put(UserDetails.TITLE, userDetails.getTitle());
        cv.put(UserDetails.FIRST_NAME, userDetails.getFirstName());
        cv.put(UserDetails.LAST_NAME, userDetails.getLastName());
        cv.put(UserDetails.NAME, userDetails.getName());
        cv.put(UserDetails.PREFIX, userDetails.getPrefix());
        cv.put(UserDetails.USER_TYPE, userDetails.getUserType());
        cv.put(UserDetails.ADDRESS, userDetails.getAddress());
        cv.put(UserDetails.MOBILE_NUMBER, userDetails.getMobileNumber());
        cv.put(UserDetails.CONTACT_NO, userDetails.getContactNo());
        cv.put(UserDetails.EMAIL_ID, userDetails.getEmailId());
        cv.put(UserDetails.DESIGNATION, userDetails.getDesignation());
        return cv;
    }

    public boolean userExists(Long userId) throws DAOException {
        Cursor c = null;
        try {
            String sql = "select " + UserDetails.USER_ID + " from " + getTableName() + " where " + UserDetails.USER_ID + " = ?";
            c = db.rawQuery(sql, whereArgsForId(userId));
            return c.moveToFirst();
        } catch (Exception e) {
            throw new DAOException(e);
        } finally {
            if (c != null) { c.close(); }
        }
    }

    public UserDetails findFirstByUserId(Long userId) throws DAOException {
        Cursor c = null;
        UserDetails t = null;
        try {
            String sql = "select * from " + getTableName() + " where " + UserDetails.USER_ID + " = ? ";
            c = db.rawQuery(sql, whereArgsForId(userId) );
            if (c.moveToFirst()) {
                t = fromCursor(c);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        } finally {
            if (c != null) { c.close(); }
        }
        return t;
    }

    public List<UserDetails> findAllByUserId(Long userId) throws DAOException {
        Cursor c = null;
        List<UserDetails> result = null;
        try {
            String sql = "select * from " + getTableName() + " where " + UserDetails.USER_ID + " = ? ";
            c = db.rawQuery(sql, whereArgsForId(userId) );
            result = new ArrayList<UserDetails>();
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


    public ArrayList<UserDetails> getuserlist(String campid) {
        open();
        ArrayList<UserDetails> arrayList = new ArrayList<>();
        arrayList.clear();
        String select = "SELECT user_details.UserName,user_details.Userid,user_details.Name FROM padaNashworkerTable INNER JOIN user_details ON padaNashworkerTable.USERId = user_details.userid WHERE padaNashworkerTable.campId = '" + campid + "'  ";
        Log.i("ckm=>getUserlist", select);
        Cursor cursor = database.rawQuery(select, null);
        if (cursor.moveToFirst()) {
                do {
                    UserDetails msg = new UserDetails();
                    msg.setUserName(cursor.getString(cursor.getColumnIndex(UserDetails.USER_NAME)));
                    msg.setUserId(cursor.getLong(cursor.getColumnIndex(UserDetails.USER_ID)));
                    msg.setName(cursor.getString(cursor.getColumnIndex(UserDetails.NAME)));
                    //arrayList.add(fromCursor(cursor));
                    arrayList.add(msg);
                } while (cursor.moveToNext());
            }
          return arrayList;
        }





    public List<UserDetails> findAllByUserIdAndUserType(String userId, String userType) throws DAOException {
        Cursor c = null;
        List<UserDetails> result = null;
        try {
            String sql = "select * from " + getTableName() + " where " + UserDetails.USER_ID + " = ? and user_type = ? and " + FRESH + " = 0";
            c = db.rawQuery(sql, new String[]{userId, userType});
            result = new ArrayList<UserDetails>();
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

    public List<UserDetails> findAllNewByUserIdAndUserType(String userId, String userType) throws DAOException {
        Cursor c = null;
        List<UserDetails> result = null;
        try {
            String sql = "select * from " + getTableName() + " where " + UserDetails.USER_ID + " = ? and user_type = ? and " + FRESH + " = 1";
            c = db.rawQuery(sql, new String[]{userId, userType});
            result = new ArrayList<UserDetails>();
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
}
