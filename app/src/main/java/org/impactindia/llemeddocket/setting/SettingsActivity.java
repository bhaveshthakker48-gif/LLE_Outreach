package org.impactindia.llemeddocket.setting;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.impactindia.llemeddocket.LLEApplication;

public class SettingsActivity extends AppCompatActivity {

	private ActionBar actionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		actionBar = getSupportActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);

		// Display the fragment as the main content.
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		fragmentTransaction.replace(android.R.id.content, new SettingsFragment());
		fragmentTransaction.commit();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				goBack();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void goBack() {
		// Enable Up button only if there are entries in the back stack
		boolean canBack = getFragmentManager().getBackStackEntryCount() > 0;
		if (canBack) {
			// This method is called when the up button is pressed. Just the pop back stack;
			getFragmentManager().popBackStack();
		} else {
			finish();
		}
	}
	
	protected LLEApplication getApp() {
		LLEApplication app = null;
		Activity activity = this;
		if (activity != null) {
			app = (LLEApplication) activity.getApplication();
		}
		return app;
	}
}
