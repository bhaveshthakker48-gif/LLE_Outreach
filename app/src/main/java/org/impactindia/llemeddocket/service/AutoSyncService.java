package org.impactindia.llemeddocket.service;

import android.app.AlertDialog;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import org.impactindia.llemeddocket.AttributeSet;
import org.impactindia.llemeddocket.LLEApplication;
import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.task.GetBloodPressureMasterTask;
import org.impactindia.llemeddocket.task.GetCampDetailsTask;
import org.impactindia.llemeddocket.task.GetCampPatientsTask;
import org.impactindia.llemeddocket.task.GetMedicalDetailsTask;
import org.impactindia.llemeddocket.task.GetStatesTask;
import org.impactindia.llemeddocket.task.GetSubTagTask;
import org.impactindia.llemeddocket.task.GetTagsTask;
import org.impactindia.llemeddocket.task.add.AddMedicalDetailsTask;
import org.impactindia.llemeddocket.task.add.AddPatientProfileImgTask;
import org.impactindia.llemeddocket.task.add.AddPatientRegistrationTask;

import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AutoSyncService extends IntentService {

	public static final String NOTIFICATION = "com.chowgulemediconsult.meddocket.cms.service.receiver";
	
	private static final String TAG = "AutoSyncService";
	private ExecutorService executor = Executors.newFixedThreadPool(1); // This will run 1 threads at a time simultaneously
	private Handler handler;
	String msg;
	
	public AutoSyncService() {
		super("AutoSyncService");
		handler = new Handler();
	}

	private void createNotificationChannel(String channelId) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			CharSequence name = getString(R.string.app_name);
			String desc = "Auto sync";
			int importance = NotificationManager.IMPORTANCE_DEFAULT;
			NotificationChannel channel = new NotificationChannel(channelId, name, importance);
			channel.setDescription(desc);
			NotificationManager notificationManager = getSystemService(NotificationManager.class);
			notificationManager.createNotificationChannel(channel);
		}
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		boolean campComplete = intent.getBooleanExtra(AttributeSet.Constants.CAMP_COMPLETE, false);
		if (getApp().getSettings().isFirstLaunch()) {
			executeGetMasterDataTask(campComplete);
			executeAddUserDataTask(campComplete);
			executeGetUserDataTask(campComplete);
		} else {
            executeGetMasterDataTask(campComplete);
            executeAddUserDataTask(campComplete);
            executeGetUserDataTask(campComplete);
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
			
		}
		final boolean syncSuccess = intent.getBooleanExtra("SyncSuccess", true);

		publishResults(intent.getExtras());
		Log.d("TEST", "Finished all threads");
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				try {
					if(syncSuccess) {
						msg = getString(R.string.pref_sync_success_msg);
					} else {
						msg = getString(R.string.pref_sync_fail_msg);
					}
					createAlertDialog(msg);
				} catch (Exception e) {
					Log.e(TAG, "run" +e.getMessage());
				}
			}

		});
	}
	
	protected String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat(AttributeSet.Constants.MODIFIED_DATE_FORMAT);
		return sdf.format(System.currentTimeMillis());
	}
	
	protected LLEApplication getApp() {
		LLEApplication app = null;
		IntentService service = this;
		if (service != null) {
			app = (LLEApplication) service.getApplication();
		}
		return app;
	}
	
	protected void createAlertDialog(String msg) {
		try {
			AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.diag_style)
					.setIcon(R.mipmap.ic_launcher)
					.setTitle(R.string.pref_dialog_title)
					.setMessage(msg)
					.setPositiveButton(R.string.lbl_ok,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
								}
							}).create();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
            } else {
                alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_PHONE);
            }
			alertDialog.show();
		} catch (Exception e) {
			Log.e(TAG, "createAlertDialog :-" + e.getMessage());
		}
	}
	
	private void executeGetUserDataTask(boolean campComplete) {
		// GET WebService
		executor.execute(new GetCampDetailsTask(this, campComplete));
		executor.execute(new GetCampPatientsTask(this, campComplete));
		executor.execute(new GetMedicalDetailsTask(this, campComplete));
	}
	
	private void executeAddUserDataTask(boolean campComplete) {
		executor.execute(new AddPatientRegistrationTask(this, campComplete));
		executor.execute(new AddPatientProfileImgTask(this, campComplete));
		executor.execute(new AddMedicalDetailsTask(this, campComplete));
	}
	
	private void executeGetMasterDataTask(boolean campComplete) {
		executor.execute(new GetStatesTask(this, campComplete));
		executor.execute(new GetTagsTask(this, campComplete));
		executor.execute(new GetSubTagTask(this, campComplete));
        executor.execute(new GetBloodPressureMasterTask(this));
	}
	
	/**
	 * This will notify the receiver in Login Fragment and Settings Fragment screen about the completion of the sync process
	 */
	private void publishResults(Bundle args) {
		Intent intent = new Intent(NOTIFICATION);
//		intent.putExtras(args);
		sendBroadcast(intent);
	}

}
