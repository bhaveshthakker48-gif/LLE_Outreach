package org.impactindia.llemeddocket.util;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {
	
	static RequestQueue queue;
	static Context con;
	
	public static void initialize(Context context){
		if(con == null)
			con = context;
			
	}
	
	public static RequestQueue getInstance(){
		if(queue == null)
			queue = Volley.newRequestQueue(con);
		return queue;
	}
}
