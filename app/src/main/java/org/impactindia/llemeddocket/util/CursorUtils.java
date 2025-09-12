package org.impactindia.llemeddocket.util;

import android.database.Cursor;

public class CursorUtils {
	
	public static Integer extractIntegerOrNull(Cursor c, String columnName) {
		int columnIndex = 0;
		try {
			columnIndex = c.getColumnIndex(columnName);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return c.isNull(columnIndex) ? null : c.getInt(columnIndex);
	}	

	public static Long extractLongOrNull(Cursor c, String columnName) {
		int columnIndex = 0;
		try {
			columnIndex = c.getColumnIndex(columnName);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return c.isNull(columnIndex) ? null : c.getLong(columnIndex);
	}
	
	public static Float extractFloatOrNull(Cursor c, String columnName) {
		int columnIndex = 0;
		try {
			columnIndex = c.getColumnIndex(columnName);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return c.isNull(columnIndex) ? null : c.getFloat(columnIndex);
	}

	public static Double extractDoubleOrNull(Cursor c, String columnName) {
		int columnIndex = 0;
		try {
			columnIndex = c.getColumnIndex(columnName);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return c.isNull(columnIndex) ? null : c.getDouble(columnIndex);
	}
	
    public static boolean extractBoolean(Cursor c, String columnName) {
		int value = 0;
		try {
			value = c.getInt(c.getColumnIndex(columnName));
		} catch (Exception e) {
			e.printStackTrace();
		}
        return value == 1;
    }
    
    public static byte[] extractBlobOrNull(Cursor c, String columnName) {
		int columnIndex;
		try {
			columnIndex = c.getColumnIndex(columnName);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
        return c.isNull(columnIndex) ? null : c.getBlob(columnIndex);
    }
    
    public static String extractStringOrNull(Cursor c, String columnName) {
		int columnIndex = 0;
		try {
			columnIndex = c.getColumnIndex(columnName);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
        return c.isNull(columnIndex) ? null : c.getString(columnIndex);
    }
}
