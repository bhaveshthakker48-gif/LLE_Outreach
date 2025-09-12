package org.impactindia.llemeddocket.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.util.SharedPreference;
import android.widget.TextView;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;


public class SplashActivity extends BaseActivity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        WindowInsetsControllerCompat insetsController =
                WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        if (insetsController != null) {
            insetsController.setAppearanceLightStatusBars(true);
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content),
                new OnApplyWindowInsetsListener() {
                    @Override
                    public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets) {
                        Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                        view.setPadding(
                                systemBars.left,
                                systemBars.top,
                                systemBars.right,
                                systemBars.bottom
                        );
                        return insets;
                    }
                });

        TextView versionText = findViewById(R.id.text);

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String versionName = packageInfo.versionName;
            versionText.setText("App Version : " + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        SharedPreference.initialize(this);
        if (savedInstanceState == null) {
            /* New Handler to start the Main Activity and close this splash screen after some seconds. */
            new BackgroundTask(this).execute();
        }
    }

    private class BackgroundTask extends AsyncTask<Void, Void, Void> {

        private Context context;

        public BackgroundTask(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(SPLASH_DISPLAY_LENGTH);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (isEmpty(SharedPreference.get("designation")))
            {
                Intent intent = new Intent(SplashActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
            else if(!isEmpty(SharedPreference.get("totalcamp"))  && Integer.valueOf(SharedPreference.get("totalcamp")) > 1)
            {
                    Intent intent = new Intent(SplashActivity.this, MultipleCampActivity.class);
                    startActivity(intent);
                    finish();
            }
            else if (SharedPreference.get("designation").equals("27"/*"158"*/))
            {
                Intent intent = new Intent(SplashActivity.this, DashboardActivityOutPro.class);
                startActivity(intent);
                finish();
            }
            else if(!SharedPreference.get("designation").equals("27"/*"158"*/))
            {
                Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
