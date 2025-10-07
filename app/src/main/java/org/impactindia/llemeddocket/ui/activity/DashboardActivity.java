package org.impactindia.llemeddocket.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import org.impactindia.llemeddocket.AttributeSet;
import org.impactindia.llemeddocket.DatabaseHelper;
import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.orm.CampDAO;
import org.impactindia.llemeddocket.orm.UserDetailsDAO;
import org.impactindia.llemeddocket.pojo.Camp;
import org.impactindia.llemeddocket.service.AutoSyncService;
import org.impactindia.llemeddocket.setting.SettingsActivity;
import org.impactindia.llemeddocket.ui.fragment.PatientRegFrag;
import org.impactindia.llemeddocket.util.NetworkConnection;
import org.impactindia.llemeddocket.util.SharedPreference;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DashboardActivity extends BaseActivity implements View.OnClickListener {

    public static final String BROADCAST_ACTION = "com.chowgulemediconsult.meddocket.cms.service.receiver";

    public static final int PERM_REQ_MANAGE_OVERLAY = 1111;

    private Toolbar toolbar;
    private Button btnPatient, btnSettings;
    ImageView img_logout;
    private SQLiteDatabase db;
    private CampDAO campDAO;
    private Camp camp;
    private boolean isVisible;
    private UserDetailsDAO userDetailsDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

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
        SharedPreference.initialize(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(BROADCAST_ACTION);
        registerReceiver(syncCompleteReceiver, filter);

        db = new DatabaseHelper(this).getReadableDatabase();
        userDetailsDAO = new UserDetailsDAO(this, db);
        campDAO = new CampDAO(this, db);
        List<Camp> campList = campDAO.findAll();
        if (!campList.isEmpty()) {
            camp = campList.get(0);
        }
        checkDrawOverlayPermission();
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isVisible = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isVisible = false;
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Dashboard");

        btnPatient = (Button) findViewById(R.id.btnPatient);
        btnPatient.setOnClickListener(this);
        btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(this);
        img_logout = findViewById(R.id.img_logout);
        img_logout.setOnClickListener(this);

        if (getApp().getSettings().getCampEndDate() == null) {
            if (camp != null) {
                String startDateStr = camp.getFromDate();
                String endDateStr = camp.getToDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(AttributeSet.Constants.REVERSE_SHORT_DATE);
                try {
                    LocalDate endDate = LocalDate.parse(endDateStr, formatter);
                    LocalDate startDate = LocalDate.parse(startDateStr, formatter);
                    LocalDate today = LocalDate.now();
                    if (today.isAfter(endDate)) {
                        showNoActiveCampDialog();
                    } else if (today.isBefore(startDate)) {
                        showNoActiveCampDialog();
                    } else {
                        showCampStartsDialog(camp);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                showNoActiveCampDialog();
            }
        } else {
            String startDateStr = getApp().getSettings().getCampStartDate();
            String endDateStr = getApp().getSettings().getCampEndDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(AttributeSet.Constants.REVERSE_SHORT_DATE);
            try {
                LocalDate endDate = LocalDate.parse(endDateStr, formatter);
                LocalDate startDate = LocalDate.parse(startDateStr, formatter);
                LocalDate today = LocalDate.now();
                if (today.isAfter(endDate)) {
                    showCampEndsDialog(camp);
                } else if (today.isBefore(startDate)) {
                    showNoActiveCampDialog();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void checkDrawOverlayPermission() {
        /** check if we already  have permission to draw over other apps */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                /** if not construct intent to request permission */
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                /** request permission via start activity for result */
                startActivityForResult(intent, PERM_REQ_MANAGE_OVERLAY);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** check if received result code
         is equal our requested code for draw permission  */
        if (requestCode == PERM_REQ_MANAGE_OVERLAY) {
            /** if so check once again if we have permission */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    // continue here - permission was denied
                    showDrawOverOtherAppDialog();
                }
            }
        }
    }

    public void showDrawOverOtherAppDialog() {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);
        // Set dialog title
        alertDialog.setTitle(R.string.permission_diag_title);
        // Set dialog message
        alertDialog.setMessage(R.string.draw_over_other_app_perm_req_alert_msg);
        alertDialog.setPositiveButton(R.string.lbl_ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        android.app.AlertDialog alert = alertDialog.create();
        alert.show();
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.btnPatient) {
            showPatientOptions();
        } else if (viewId == R.id.btnSettings) {
            gotoScreen(SettingsActivity.class);
        } else if (viewId == R.id.img_logout) {
            getApp().getSettings().setUserId(Long.valueOf(0));
            getApp().getSettings().setActiveUserName("");
            SharedPreference.removeKey("designation");
            gotoScreen(LogInActivity.class);
            finish();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(syncCompleteReceiver);
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    public void gotoScreen(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public void showCampStartsDialog(final Camp camp) {
        if (camp != null) {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, R.style.dialog_style);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle(getString(R.string.title_active_camp));
            DateTimeFormatter dispFormatter = DateTimeFormatter.ofPattern(AttributeSet.Constants.SHORT_DATE_FORMAT);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(AttributeSet.Constants.REVERSE_SHORT_DATE);
            try {
                LocalDate startDate = LocalDate.parse(camp.getFromDate(), formatter);
                LocalDate endDate = LocalDate.parse(camp.getToDate(), formatter);
                builder.setMessage(String.format(getString(R.string.active_camp_starts_msg), camp.getCampYear(), camp.getCampNo(), camp.getCampLocation(), startDate.format(dispFormatter), endDate.format(dispFormatter)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            builder.setNeutralButton(R.string.lbl_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getApp().getSettings().setCampEndDate(camp.getToDate());
                    getApp().getSettings().setCampStartDate(camp.getFromDate());
                    dialog.dismiss();
                }
            });
            builder.setCancelable(false);
            builder.create().show();
        } else {
            showNoActiveCampDialog();
        }
    }

    public void showNoActiveCampDialog() {

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, R.style.dialog_style);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle(getString(R.string.title_active_camp));
        builder.setMessage(getString(R.string.no_active_camp_dialog_msg));
        builder.setPositiveButton(R.string.btn_lbl_sync, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (NetworkConnection.isNetworkAvailable(DashboardActivity.this)) {
                    startSync();
                } else {
                    longToast(getString(R.string.network_unavailable_error_msg));
                }
                // finish();
            }
        });
        builder.setCancelable(false);
        builder.create().show();

    }

    public void showCampEndsDialog(Camp camp) {
        if (camp != null) {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, R.style.dialog_style);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle(getString(R.string.title_active_camp));
            builder.setMessage(String.format(getString(R.string.active_camp_ends_msg), camp.getCampLocation()));
            builder.setPositiveButton(R.string.btn_lbl_yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(R.string.btn_lbl_no_sync_complete, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    if (NetworkConnection.isNetworkAvailable(DashboardActivity.this)) {
                        startSync();
                        dialog.dismiss();
                    } else {
                        shortToast(getString(R.string.network_unavailable_error_msg));
                    }
                }
            });
            builder.setCancelable(false);
            builder.create().show();
        }
    }

    /**
     * This will start the Synchronization process
     */
    private void startSync() {
        Intent service = new Intent(this, AutoSyncService.class);
        service.putExtra(AttributeSet.Constants.SYNC_MASTER, false);
        startService(service);
        showProgDialog(this, "Sync In Progress");
    }

    public void showPatientOptions() {
        List<String> patientOpt;
        patientOpt = new ArrayList<String>();
        patientOpt.add(getString(R.string.lbl_patient_registration));
        patientOpt.add(getString(R.string.title_my_patient_list));
        patientOpt.add(getString(R.string.title_camp_patient_list));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select One");
        final ArrayAdapter<String> patientOptAdapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.itemText, patientOpt);
        builder.setAdapter(patientOptAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(DashboardActivity.this, PatientActivity.class);
                intent.putExtra(PatientActivity.PATIENT_OPT, which);
                startActivity(intent);
            }
        });
        builder.show();
    }

    private BroadcastReceiver syncCompleteReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (DashboardActivity.this.isVisible) {
                closeProgDialog(context);
                getApp().getSettings().setFirstLaunch(false);
                getApp().getSettings().setCampEndDate(null);
                getApp().getSettings().setCampStartDate(null);
            }
        }
    };
}
