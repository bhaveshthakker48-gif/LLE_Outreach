package org.impactindia.llemeddocket.task;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.impactindia.llemeddocket.DatabaseHelper;
import org.impactindia.llemeddocket.LLEApplication;
import org.impactindia.llemeddocket.orm.UserDetailsDAO;

public class BaseServiceTask {

	protected Context context;
	protected SQLiteDatabase db;
	protected UserDetailsDAO userDetailsDAO;
	
	public BaseServiceTask(Context context) {
        this.context = context;
		db = new DatabaseHelper(context).getWritableDatabase();
		userDetailsDAO = new UserDetailsDAO(context, db);
    }
	
	public Context getContext() {
        return context;
    }
	
	protected LLEApplication getApp() {
        return (LLEApplication) context.getApplicationContext();
    }
	
    protected void releaseResources() {
    	if (db != null && db.isOpen()) {
			db.close();
		}
    }
}
