package org.impactindia.llemeddocket.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import org.impactindia.llemeddocket.pojo.Model;
import org.impactindia.llemeddocket.pojo.Patient;
import org.impactindia.llemeddocket.util.CursorUtils;
import org.impactindia.llemeddocket.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devandroid on 16/5/17.
 */

public abstract class BaseDAO<T extends Model> implements DAO<T> {

    private static final String TAG = "BaseDAO";
//    protected static final String USER_ID = "user_id";
    protected static final String FRESH = "fresh";
    protected final SQLiteDatabase db;
    protected final Context context;

    public BaseDAO(Context context, SQLiteDatabase db) {
        this.context = context;
        this.db = db;
    }

    public abstract String getTableName();
    public abstract T fromCursor(Cursor c);
    public abstract ContentValues values(T t);

    public T findByPrimaryKey(Long id) throws DAOException {
        Cursor c = null;
        T t = null;
        try {
            String sql = "select * from " + getTableName() + " where " + ID + " = ?";
            c = db.rawQuery(sql, whereArgsForId(id));
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

    public T findFirstByField(String fieldName, String value, String fieldName2, String value2) throws DAOException {
        Cursor c = null;
        T t = null;

        try {
            String q = "select * from " + getTableName() + " where " + fieldName + " = ? and " + fieldName2 + " = ?";
            Log.d(TAG, q);
            c = db.rawQuery(q, new String[]{value, value2} );
            if (c.moveToFirst()) {
                t = fromCursor(c);
            }
        }
        catch (Exception e) {
            throw new DAOException(e);
        }
        finally {
            if (c!= null) { c.close(); }
        }
        return t;
    }

    public T findFirstByField(String fieldName, Long id) throws DAOException {
        Cursor c = null;
        T t = null;

        try {
            String q = "select * from " + getTableName() + " where " + fieldName + " = ?";
            Log.d(TAG, q);
            c = db.rawQuery(q, whereArgsForId(id));
            if (c.moveToFirst()) {
                t = fromCursor(c);
            }
        }
        catch (Exception e) {
            throw new DAOException(e);
        }
        finally {
            if (c!= null) { c.close(); }
        }
        return t;
    }

    public T findFirstByField(String fieldName, String value) throws DAOException {
        Cursor c = null;
        T t = null;

        try {
            String q = "select * from " + getTableName() + " where lower (" + fieldName + ") = lower (?)";
            Log.d(TAG, q);
            c = db.rawQuery(q, new String[]{value} );
            if (c.moveToFirst()) {
                t = fromCursor(c);
            }
        }
        catch (Exception e) {
            throw new DAOException(e);
        }
        finally {
            if (c!= null) { c.close(); }
        }
        return t;
    }

    public List<T> findAllUnuploaded() throws DAOException {
        Cursor c = null;
        List<T> result = null;
        try {
            String query = "select * from " + getTableName() + " where " + FRESH + " = 1";
            Log.d(TAG, query);
            c = db.rawQuery(query, null);
            result = new ArrayList<T>();
            if (c.moveToFirst()) {
                do {
                    result.add(fromCursor(c));
                } while (c.moveToNext());
            }
        } catch (Exception exception) {
            throw new DAOException(exception);
        } finally {
            if (c != null) { c.close(); }
        }
        return result;
    }

    public List<T> findAllUploaded() throws DAOException {
        Cursor c = null;
        List<T> result = null;
        try {
            String query = "select * from " + getTableName() + " where " + FRESH + " = 0";
            Log.d(TAG, query);
            c = db.rawQuery(query, null);
            result = new ArrayList<T>();
            if (c.moveToFirst()) {
                do {
                    result.add(fromCursor(c));
                } while (c.moveToNext());
            }
        } catch (Exception exception) {
            throw new DAOException(exception);
        } finally {
            if (c != null) { c.close(); }
        }
        return result;
    }

    public List<T> findAll() {
        Cursor c = null;
        List<T> result = null;
        try {
            c = db.rawQuery("select * from " + getTableName(), null);
            result = new ArrayList<T>();
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

    public List<T> findAll(String orderConditions) {
        Cursor c = null;
        List<T> result = null;
        try {
            c = db.rawQuery("select * from " + getTableName() + ' ' + StringUtils.safe(orderConditions), null);
            result = new ArrayList<T>();
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

    public List<T> findAllActive(String orderConditions) throws DAOException {
        Cursor c = null;
        List<T> result = null;
        try {
            String query = "select * from " + getTableName() + " where " + Patient.IS_ACTIVE + " = 1" + ' ' + StringUtils.safe(orderConditions);
            Log.d(TAG, query);
            c = db.rawQuery(query, null);
            result = new ArrayList<T>();
            if (c.moveToFirst()) {
                do {
                    result.add(fromCursor(c));
                } while (c.moveToNext());
            }
        } catch (Exception exception) {
            throw new DAOException(exception);
        } finally {
            if (c != null) { c.close(); }
        }
        return result;
    }

    public List<T> findAllActiveAndApproved(String orderConditions) throws DAOException {
        Cursor c = null;
        List<T> result = null;
        try {
            String query = "select * from " + getTableName() + " where is_active = 1 and is_approved = 1" + ' ' + StringUtils.safe(orderConditions);
            Log.d(TAG, query);
            c = db.rawQuery(query, null);
            result = new ArrayList<T>();
            if (c.moveToFirst()) {
                do {
                    result.add(fromCursor(c));
                } while (c.moveToNext());
            }
        } catch (Exception exception) {
            throw new DAOException(exception);
        } finally {
            if (c != null) { c.close(); }
        }
        return result;
    }

    public List<T> findAllByField(String fieldName, String value, String fieldName2, String value2, String orderConditions) {
        Cursor c = null;
        List<T> result = null;
        try {
            c = db.rawQuery("select * from " + getTableName() +
                            " where " + fieldName + " = ? " + " and " + fieldName2 + " = ? " + StringUtils.safe(orderConditions),
                    new String[]{value, value2});
            result = new ArrayList<T>();
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

    public List<T> findAllByField(String fieldName, String value, String orderConditions) {
        Cursor c = null;
        List<T> result = null;
        try {
            c = db.rawQuery("select * from " + getTableName() +
                            " where " + fieldName + " = ? " + StringUtils.safe(orderConditions),
                    new String[]{value});
            result = new ArrayList<T>();
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

    public List<T> findAllByField(String fieldName, String value, boolean fresh, String orderConditions) {
        Cursor c = null;
        List<T> result = null;
        int newlyAdded = 0;
        if (fresh) {
            newlyAdded = 1;
        }
        try {
            c = db.rawQuery("select * from " + getTableName() +
                            " where " + fieldName + " = ? and " + FRESH + " = " + newlyAdded + StringUtils.safe(orderConditions),
                    new String[]{value});
            result = new ArrayList<T>();
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

    public List<T> getSuggestionsFor(String fieldName, String value) throws DAOException {
        Cursor c = null;
        List<T> result = null;
        try {
            String query = "select * from " + getTableName() + " where " + fieldName + " like ? " + " order by " + ID + " asc";
            Log.d(TAG, query);
            c = db.rawQuery(query, new String[]{value + "%"});
            result = new ArrayList<T>();
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

    public List<T> getSuggestionsFor(String fieldName, String value, String fieldName2, String value2) throws DAOException {
        Cursor c = null;
        List<T> result = null;
        try {
            String query = "select * from " + getTableName() + " where " + fieldName + " like ? " + " and " + fieldName2 + " = ? " + " order by " + ID + " asc";
            Log.d(TAG, query);
            c = db.rawQuery(query, new String[]{value + "%", value2});
            result = new ArrayList<T>();
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

    public Long getMaxId() throws DAOException {
        Cursor c = null;
        Long id = null;
        try {
            String query = "select MAX(" + ID + ") as " + ID + " from " + getTableName();
            Log.d(TAG, query);
            c = db.rawQuery(query, null);
            if (c.moveToFirst()) {
                id = c.getLong(c.getColumnIndex(ID));
            }
        } catch (Exception exception) {
            throw new DAOException(exception);
        } finally {
            if (c != null) { c.close(); }
        }
        return id;
    }

    public String getMaxDateCreated() throws DAOException {
        Cursor c = null;
        String date = null;
        try {
            String query = "select MAX(date_created) as date_created from " + getTableName();
            Log.d(TAG, query);
            c = db.rawQuery(query, null);
            if (c.moveToFirst()) {
                date = c.getString(c.getColumnIndex("date_created"));
            }
        } catch (Exception exception) {
            throw new DAOException(exception);
        } finally {
            if (c != null) { c.close(); }
        }
        return date;
    }

    public String getMaxDateModified() throws DAOException {
        Cursor c = null;
        String date = null;
        try {
            String query = "select MAX(date_modified) as date_modified from " + getTableName();
            Log.d(TAG, query);
            c = db.rawQuery(query, null);
            if (c.moveToFirst()) {
                date = c.getString(c.getColumnIndex("date_modified"));
            }
        } catch (Exception exception) {
            throw new DAOException(exception);
        } finally {
            if (c != null) { c.close(); }
        }
        return date;
    }

    /**
     * Return the amount of unuploaded documents, whatever their nature (top level or details).
     */
    public int getUnuploadedCount() {
        String query = "select count(" + ID + ")from " + getTableName() + " where " + FRESH + " = ?";
        return getRecordCount(query, Long.valueOf(1));
    }

    /**
     * Return the amount of documents, whatever their nature (top level or details).
     */
    public int getCount() {
        String query = "select count(" + ID + ")from " + getTableName();
        return getRecordCount(query);
    }

    public int getRecordCount(String sqlRequest) {
        int result = 0;
        Cursor c = null;
        try {
            c = db.rawQuery(sqlRequest, null);
            if (c.moveToFirst()) {
                result = c.getInt(0);
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return result;
    }

    public int getRecordCount(String sqlRequest, Long id) {
        int result = 0;
        Cursor c = null;
        try {
            c = db.rawQuery(sqlRequest, whereArgsForId(id));
            if (c.moveToFirst()) {
                result = c.getInt(0);
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return result;
    }

    public synchronized long create(T model) throws DAOException {
        long id = db.insert(getTableName(), "XXX", values(model));
        if (id != -1) { model.setId(id); }
        return id;
    }

    public synchronized long update(T model) throws DAOException {
        long id = db.update(getTableName(), values(model), ID + " = ?", whereArgsForId(model.getId()));
        return id;
    }

    public synchronized void create(List<T> models) throws DAOException {
        for (T model : models) {
            Log.d(TAG, "table data row copied");
            create(model);
        }
    }

    public synchronized void update(List<T> models) throws DAOException {
        for (T model : models) {
            update(model);
        }
    }

    public synchronized long createOrUpdate(T model) throws DAOException {
        if (exists(model.getId())) {
            return update(model);
        } else {
            return create(model);
        }
    }

    public void delete(Long id) throws DAOException {
        db.delete(getTableName(), ID + " = ?", whereArgsForId(id));
    }

    public void deleteByField(String fieldName, Long id) throws DAOException {
        db.delete(getTableName(), fieldName + " = ?", whereArgsForId(id));
    }

    public void deleteAll() throws DAOException {
        db.delete(getTableName(), null, null);
    }

    public void deleteByField(String fieldName, String fieldValue) {
        db.delete(getTableName(), fieldName + " = ?", new String[] { fieldValue} );
    }

    public void deleteByFields(String fieldName, String fieldValue, String fieldName2, String fieldValue2) {
        db.delete(getTableName(), fieldName + " = ? and " + fieldName2 + " = ?", new String[] { fieldValue, fieldValue2 } );
    }

    public List<Long> getIds(String columnName, String fieldName, Long id, boolean fresh, String orderConditions) throws DAOException {
        Cursor c = null;
        List<Long> result;
        int newlyAdded = 0;
        if (fresh) {
            newlyAdded = 1;
        }
        try {
            String sql = "select " + columnName + " from " + getTableName() + " where " + fieldName + " = ? and " + FRESH + " = " + newlyAdded + StringUtils.safe(orderConditions);
            c = db.rawQuery(sql, whereArgsForId(id));
            result = new ArrayList<>();
            if (c.moveToFirst()) {
                do {
                    result.add(CursorUtils.extractLongOrNull(c, columnName));
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            throw new DAOException(e);
        } finally {
            if (c != null) { c.close(); }
        }
        return result;
    }

    public boolean exists(Long id) throws DAOException {
        Cursor c = null;
        try {
            String sql = "select " + ID + " from " + getTableName() + " where " + ID + " = ?";
            c = db.rawQuery(sql, whereArgsForId(id));
            return c.moveToFirst();
        } catch (Exception e) {
            throw new DAOException(e);
        } finally {
            if (c != null) { c.close(); }
        }
    }

    public boolean exists(String fieldName, Long id) throws DAOException {
        Cursor c = null;
        try {
            String sql = "select " + ID + " from " + getTableName() + " where " + fieldName + " = ?";
            c = db.rawQuery(sql, whereArgsForId(id) );
            return c.moveToFirst();
        } catch (Exception e) {
            throw new DAOException(e);
        } finally {
            if (c != null) { c.close(); }
        }
    }

    public boolean exists(String dateField, String dateValue) throws DAOException {
        Cursor c = null;
        try {
            String sql = "select " + ID + " from " + getTableName() + " where " + dateField + " = ?";
            c = db.rawQuery(sql, new String[] { dateValue } );
            return c.moveToFirst();
        } catch (Exception e) {
            throw new DAOException(e);
        } finally {
            if (c != null) { c.close(); }
        }
    }

    protected boolean isEmpty(String value) {
        if (TextUtils.isEmpty(value)) {
            return true;
        }
        return false;
    }

    protected String[] whereArgsForId(Long id) {
        return new String[] { String.valueOf(id) };
    }
}
