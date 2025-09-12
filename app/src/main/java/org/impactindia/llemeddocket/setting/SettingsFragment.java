package org.impactindia.llemeddocket.setting;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.impactindia.llemeddocket.AttributeSet;
import org.impactindia.llemeddocket.DatabaseHelper;
import org.impactindia.llemeddocket.LLEApplication;
import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.orm.DAOException;
import org.impactindia.llemeddocket.orm.UserDetailsDAO;
import org.impactindia.llemeddocket.pojo.UserDetails;
import org.impactindia.llemeddocket.service.AutoSyncService;
import org.impactindia.llemeddocket.util.NetworkConnection;
import org.impactindia.llemeddocket.view.ProgressHUD;

public class SettingsFragment extends PreferenceFragment implements OnCancelListener {

	public static final String BROADCAST_ACTION = "com.chowgulemediconsult.meddocket.cms.service.receiver";
	private static final String TAG = "SettingsFragment";
	private SQLiteDatabase db;
	private UserDetailsDAO dataDAO;
	private UserDetails userData;
	private boolean campComplete;
	private ProgressHUD progress;
	private String tag;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		logInfo("onCreate");
		IntentFilter filter = new IntentFilter();
		filter.addAction(BROADCAST_ACTION);
		getActivity().registerReceiver(syncCompleteAlertReceiver, filter);

		db = new DatabaseHelper(getActivity()).getWritableDatabase();
		dataDAO = new UserDetailsDAO(getActivity(), db);
		try {
			userData = dataDAO.findFirstByUserId(getApp().getSettings().getUserId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
			addPreferencesFromResource(R.xml.preferences);
			
			Preference syncPref = (Preference) findPreference("sync");
			syncPref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			    public boolean onPreferenceClick(Preference preference) {
//			    	if (isvalidationSuccess()) {
			    		showSyncDialog();
			    		//showDialog(1,R.string.pref_dialog_start_sync_desc);
//			    	}
			    	return true;
			    }
			});
			
			Preference syncMasterPref = findPreference("complete_camp");
			syncMasterPref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
				
				@Override
				public boolean onPreferenceClick(Preference preference) {
					showCompleteCampDialog();
					return false;
				}
			});

	}
	
	protected String getAppVersionName() {
		PackageInfo packageInfo = null;
		try {
			packageInfo = getActivity().getPackageManager().getPackageInfo(
					getActivity().getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		if (packageInfo != null) {
			return packageInfo.versionName;
		} else {
			return "";
		}
	}
	

	protected LLEApplication getApp() {
		LLEApplication app = (LLEApplication) getActivity()
				.getApplication();
		return app;
	}
	
	public void showSyncDialog() {
		try {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle(R.string.pref_dialog_title);
			builder.setMessage(R.string.pref_dialog_start_sync_desc);
			builder.setPositiveButton(R.string.lbl_ok,
                    new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	if(isvalidationSuccess())
						campComplete = false;
                		startSync(campComplete);

					}
				});
			builder.setNegativeButton(R.string.lbl_cancel,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        	dialog.dismiss();
                        }
                    }
                )
                .create().show();
		} catch (Exception e) {
			Log.e(TAG, "showSyncDialog" +e.getMessage());
		}
	}
	
	public void showCompleteCampDialog() {
		try {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle(R.string.pref_dialog_title);
			builder.setMessage(R.string.pref_dialog_complete_camp_desc);
			builder.setPositiveButton(R.string.lbl_ok, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (isvalidationSuccess()) {
						campComplete = true;
						startSync(campComplete);
						getApp().getSettings().setCampEndDate(null);
						getApp().getSettings().setCampStartDate(null);
						getApp().getSettings().setFirstLaunch(true);
					}
					
				}
			});
			builder.setNegativeButton(R.string.lbl_cancel, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			})
			.create().show();
		} catch (Exception e) {
			Log.e(TAG, "showCompleteCampDialog" + e.getMessage());
		}
	}
	
	public void startSync(boolean campComplete) {
		Intent service = new Intent(getActivity(), AutoSyncService.class);
		service.putExtra(AttributeSet.Constants.CAMP_COMPLETE, campComplete);
		getActivity().startService(service);
		showProgressDialog(getString(R.string.sync_prog_desc));
	}
	
	public boolean isvalidationSuccess() {
		if (!NetworkConnection.isNetworkAvailable(getActivity())) {
			shortToast(getString(R.string.network_unavailable_error_msg));
		} else {
			return true;
		}
		return false;
	}
	
	protected void shortToast(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		logInfo("onCreateView");
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		logInfo("onStart");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		logInfo("onResume");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		logInfo("onPause");
	}
	
	@Override
	public void onStop() {
		super.onStop();
		logInfo("onStop");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		logInfo("onDestroyView");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		logInfo("onDestroy");
		getActivity().unregisterReceiver(syncCompleteAlertReceiver);
	}

	protected String getAppTag() {
		if (tag == null) {
			tag = getString(R.string.app_name) + " " + getClass().getSimpleName();
		}
		return tag;
	}

	protected void log(String msg) {
		Log.d(getAppTag(), msg);
	}

	protected void logInfo(String msg) {
		Log.i(getAppTag(), msg);
	}

	protected void log(String msg, Throwable tr) {
		Log.e(getAppTag(), msg, tr);
	}

	/**
	 * This receiver is registered to receive sync complete event notification
	 * will receive the sync complete event when it happens and perform the task
	 * needed
	 */
	private BroadcastReceiver syncCompleteAlertReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			closeProgressDialog();
			if (campComplete) {
				getActivity().finishAffinity();
			}
			campComplete = false;
		}
	};
	
	protected void showProgressDialog() {
		progress = ProgressHUD.show(getActivity(),null, true,false,this);
		progress.setCanceledOnTouchOutside(false);
	}
	
	protected void showProgressDialog(String message) {
		progress = ProgressHUD.show(getActivity(),message, true,false,this);
		progress.setCanceledOnTouchOutside(false);
	}
	
	protected void closeProgressDialog() {
		if (progress != null && progress.isShowing()) {
			progress.dismiss();
			progress = null;
		}
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		// TODO Auto-generated method stub
		
	}
}
