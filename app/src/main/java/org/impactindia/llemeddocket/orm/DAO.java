package org.impactindia.llemeddocket.orm;

import android.content.ContentValues;
import android.database.Cursor;

public interface DAO<T> {

	public static final String ID = "_id";
	
	public T findByPrimaryKey(Long id) throws DAOException;
	public long create(T object) throws DAOException;
	public long update(T object) throws DAOException;
	public long createOrUpdate(T object) throws DAOException;
	public void delete(Long id) throws DAOException;
	public boolean exists(Long id) throws DAOException;
	
	public T fromCursor(Cursor c);
	public ContentValues values(T t);
}
