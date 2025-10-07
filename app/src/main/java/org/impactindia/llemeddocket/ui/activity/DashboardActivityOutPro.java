package org.impactindia.llemeddocket.ui.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import android.os.Looper;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.client.android.BuildConfig;

import org.impactindia.llemeddocket.AttributeSet;

import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.adapter.SychDateTimeAdapter;
import org.impactindia.llemeddocket.orm.CampProgramModel;
import org.impactindia.llemeddocket.orm.ChartNormalRangeModel;
import org.impactindia.llemeddocket.orm.ChartcommonMasterModel;
import org.impactindia.llemeddocket.orm.OutreachPlanModel;
import org.impactindia.llemeddocket.orm.PadaNAshworkerDetailModel;
import org.impactindia.llemeddocket.orm.PhcSubCenterModel;
import org.impactindia.llemeddocket.orm.PopulationMedicalModel;
import org.impactindia.llemeddocket.orm.syncModel;
import org.impactindia.llemeddocket.pojo.ChartNormalRangeData;
import org.impactindia.llemeddocket.pojo.ChartcommonMasterData;
import org.impactindia.llemeddocket.pojo.OutreachPlanData;
import org.impactindia.llemeddocket.pojo.PadanashaworkerData;
import org.impactindia.llemeddocket.pojo.PopMedicalData;
import org.impactindia.llemeddocket.pojo.SyncData;
import org.impactindia.llemeddocket.util.AppController;
import org.impactindia.llemeddocket.util.PrefKeys;
import org.impactindia.llemeddocket.util.SharedPreference;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.LocalDate;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
//import com.ajts.androidmads.library.SQLiteToExcel;


public class DashboardActivityOutPro extends BaseActivity implements View.OnClickListener {

    Toolbar toolbar;
    ImageView outreachimg_logout;
    Button btnCamp, btnReport;
    SQLiteDatabase db;
    ArrayList<PadanashaworkerData> asha = new ArrayList<>();
    ArrayList<PopMedicalData> popData = new ArrayList<>();
    ArrayList<OutreachPlanData> campname = new ArrayList<OutreachPlanData>();
    JsonObject object;
    Date outreachdatenddate;
    Date outreachfrom;
    Date currentdate;
    LocalDate currentdat2;
    JSONObject jo2;
    public int counter;
    String campcount, date1, dateString2;
    SychDateTimeAdapter sychDateTimeAdapter;
    Context context;
    ImageView imgsynch, campcomplete;
    TextView reportsButton;
    private ProgressDialog progressDialog;
    Date date = null;
    public static final String DATE_FORMAT_1 = "hh:mm a";
    String formattedDate, forcamprestricdata;
    LinearLayoutManager linearLayoutManager;
    ArrayList<SyncData> synchdata1 = new ArrayList<>();
    ArrayList<SyncData> synchdata2 = new ArrayList<>();
    RecyclerView syncdatetimerecylcer;
    public static final String SYNCH_BROADCAST = "synch_broadcast";

    LocalDate outreachstartfrom, outreachstop;
    boolean synchfail = false;
    Button export;
    String directory_path = Environment.getExternalStorageDirectory().getPath() + "/Backups/Amit/";
    // DBQueries dbQueries;
//    SQLiteToExcel sqliteToExcel;
    int click = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_out_pro);

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

        syncdatetimerecylcer = findViewById(R.id.syncdatetimerecylcer);
        linearLayoutManager = new LinearLayoutManager(this);

        PopulationMedicalModel.getInstance(this);
        PhcSubCenterModel.getInstance(this);
        PadaNAshworkerDetailModel.getInstance(this);
        CampProgramModel.getInstance(this);
        ChartNormalRangeModel.getInstance(this);
        ChartcommonMasterModel.getInstance(this);
        OutreachPlanModel.getInstance(this);
        syncModel.getInstance(this);
        SharedPreference.initialize(this);
        AppController.initialize(this);

        Intent intent = getIntent();
        if (getIntent().getExtras() != null) {
            campcount = intent.getExtras().getString("OUTREACHCOUNT");
        }
        init();

        Log.i("ckm=>USERID", SharedPreference.get("Userid"));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        date1 = sdf.format(c.getTime());

        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateString2 = new SimpleDateFormat("yyyy-MM-dd").format(date);


        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

        try {
            currentdate = sdf2.parse(dateString2);
            if (outreachdatenddate != null) {
                outreachdatenddate = sdf2.parse(SharedPreference.get("OutreachTo"));
                outreachfrom = sdf2.parse(SharedPreference.get("OutreachFrom"));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        Calendar c1 = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c.getTime());
        Log.i("formattedDate", formattedDate);
        Log.i("formattedDate1", getCurrentTime());

        // Now formattedDate have current date/time
        forcamprestricdata = ds.format(c.getTime());
        Log.i("formattedDateNew", forcamprestricdata);
        currentdat2 = LocalDate.parse(forcamprestricdata);
        Log.i("ckm=>CurrentDate", currentdat2 + "");
        synchdata1 = syncModel.getsynchdata();
        Log.i("ckm=>DATA", synchdata1.size() + "");
        syncdatetimerecylcer.setLayoutManager(linearLayoutManager);
        sychDateTimeAdapter = new SychDateTimeAdapter(DashboardActivityOutPro.this, synchdata1);
        syncdatetimerecylcer.setAdapter(sychDateTimeAdapter);


        if (isEmpty(SharedPreference.get("CHARTADDED")) || SharedPreference.get("CHARTADDED").equals("")) {
            Log.i("ckm=>DataADD", "ChartAdded");
            getchartNormalRangeMaster();
            getchartCommonRangeMaster();
        }

        Log.i("ModelNumber", getPhoneModel());

        File file = new File(directory_path);
        if (!file.exists()) {
            Log.i("ckm=> File Created", String.valueOf(file.mkdirs()));
        }

        checkForAppUpdate();
        checkWhatsNew();

    }

    private void checkForAppUpdate() {
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                showUpdateDialog();
            }
        });
    }


    private void showUpdateDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Update Available")
                .setMessage("A new version of this app is available. Please update to continue.")
                .setCancelable(false) // force them to choose
                .setPositiveButton("Update", (dialog, which) -> {
                    openPlayStoreForUpdate();
                })
                .setNegativeButton("Later", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void openPlayStoreForUpdate() {
        final String appPackageName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    private void checkWhatsNew() {
        String currentVersion = BuildConfig.VERSION_NAME;

        String lastVersion = SharedPreference.get(PrefKeys.LAST_SEEN_VERSION);

        if (lastVersion == null || !currentVersion.equals(lastVersion)) {
            showWhatsNewBottomSheet();

            SharedPreference.save(PrefKeys.LAST_SEEN_VERSION, currentVersion);
        }
    }

    private void showWhatsNewBottomSheet() {
        try {
            String versionName = getPackageManager()
                    .getPackageInfo(getPackageName(), 0).versionName;

            final com.google.android.material.bottomsheet.BottomSheetDialog bottomSheetDialog =
                    new com.google.android.material.bottomsheet.BottomSheetDialog(this, R.style.CustomBottomSheetDialog);

            View view = getLayoutInflater().inflate(R.layout.layout_whats_new_bottomsheet, null);
            bottomSheetDialog.setContentView(view);

            // Set title + message
            TextView titleText = view.findViewById(R.id.titleText);
            TextView messageText = view.findViewById(R.id.messageText);
            Button btnGotIt = view.findViewById(R.id.btnGotIt);

            if (titleText != null) {
                titleText.setText("What's New in version " + versionName);
            }
            if (messageText != null) {
                messageText.setText("✨ Latest Updates:\n\n• Faster performance\n• Bug fixes\n• New dashboard UI");
            }

            if (btnGotIt != null) {
                btnGotIt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });
            }

            // Show with delay for smooth effect
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    bottomSheetDialog.show();
                }
            }, 600); // 600ms delay

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private String getPhoneModel() {
        return Build.MODEL;
    }

    public void getuploaddatafromSqlite() {
        asha = PadaNAshworkerDetailModel.getallpadadetails(SharedPreference.get("CAMPID"));
        Log.i("Data=>", asha.size() + "");

        if (asha.size() > 0) {
            // Convert to JSON
            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(asha, new TypeToken<List<PadaNAshworkerDetailModel>>() {}.getType());
            JsonArray jsonArray = element.getAsJsonArray();
            Log.i("ckm=>jsonarray", jsonArray + "");

            object = new JsonObject();
            object.add("outreachdata", jsonArray);
            Log.i("ckm=>jsonobject", object + "");

            try {
                jo2 = new JSONObject(object.toString());
                Log.i("JSON_OBJECT", jo2 + "");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            synchfail = false;
            callService();  // Only call if there is data

        } else {
            // No data to sync
            Toast.makeText(DashboardActivityOutPro.this, "No data to sync", Toast.LENGTH_LONG).show();
            Log.i("ckm=>synch", "No data found in SQLite to sync");
        }
    }


    private void getchartNormalRangeMaster() {
        showProgDialog(DashboardActivityOutPro.this);
        StringRequest request = new StringRequest(Request.Method.GET, AttributeSet.Params.GET_CHARTNORMAL_RANGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("NormalChart_RESPONSE", response);
                //    closeProgDialog(DashboardActivityOutPro.this);
                try {
                    JSONObject json = new JSONObject(response);
                    Log.i("Ckm=>DATA", json + "");
                    // Toast.makeText(LogInActivity.this,"InsideCampData",Toast.LENGTH_LONG).show();
                    if (json.getString("ErrorMessage").equals("success") && json.getString("ErrorCode").equals("0")) {
                        ChartNormalRangeModel.open();
                        ChartNormalRangeModel.chartNormaldeleted();

                        JSONArray jsonArray = json.getJSONArray("Data");
                        Log.i("JsonArraylength", jsonArray.length() + "");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Log.i("ckm=>INsideCampData", jsonObject1 + "");

                            ChartNormalRangeData chartNormalRangeData = new ChartNormalRangeData(jsonObject1.getString("ChartId"), jsonObject1.getString("XValue")
                                    , jsonObject1.getString("Y3"), jsonObject1.getString("Y5"), jsonObject1.getString("Y10"), jsonObject1.getString("Y25"),
                                    jsonObject1.getString("YNormal"), jsonObject1.getString("Y75"), jsonObject1.getString("Y90"), jsonObject1.getString("Y97")
                                    , jsonObject1.getString("CreatedDate"), jsonObject1.getString("RangeStart"), jsonObject1.getString("RangeEnd"), jsonObject1.getString("Y23")
                                    , jsonObject1.getString("Y27"));

                            ChartNormalRangeModel.insert(chartNormalRangeData);
                            // SharedPreference.save("CHARTADDED","chart");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                closeProgDialog(DashboardActivityOutPro.this);
                Toast.makeText(getApplicationContext(), "No Internet Connection Please try again later..", Toast.LENGTH_LONG).show();
            }
        });

        Log.i("STRINGREQURLOUTREACH", request + "");
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
            }
        });
        Log.i("SCI_PROGRAM", request + "");
        AppController.getInstance().add(request);

    }

    private void getchartCommonRangeMaster() {

//        showProgDialog(DashboardActivityOutPro.this);
        StringRequest request = new StringRequest(Request.Method.GET, AttributeSet.Params.GET_CHARTCOMMONMASTER_RANGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("COMMON_RESPONSE", response);
                closeProgDialog(DashboardActivityOutPro.this);
                try {

                    JSONObject json = new JSONObject(response);
                    Log.i("Ckm=>DATA", json + "");
                    // Toast.makeText(LogInActivity.this,"InsideCampData",Toast.LENGTH_LONG).show();
                    if (json.getString("ErrorMessage").equals("success") && json.getString("ErrorCode").equals("0")) {
                        ChartcommonMasterModel.open();
                        ChartcommonMasterModel.chartCommonMasterModeldeleted();

                        JSONArray jsonArray = json.getJSONArray("Data");
                        Log.i("JsonArraylength", jsonArray.length() + "");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Log.i("ckm=>INsideCampData", jsonObject1 + "");

                            ChartcommonMasterData chartcommonMasterData = new ChartcommonMasterData(jsonObject1.getString("ChartId"), jsonObject1.getString("Type"),
                                    jsonObject1.getString("Gender"), jsonObject1.getString("Range"), jsonObject1.getString("XMin"), jsonObject1.getString("XMax"),
                                    jsonObject1.getString("YMin"), jsonObject1.getString("YMax"), jsonObject1.getString("ChartTitle"), jsonObject1.getString("XTitle")
                                    , jsonObject1.getString("YTitle"), jsonObject1.getString("XInterval"), jsonObject1.getString("YInterval"), jsonObject1.getString("DataSource"), jsonObject1.getString("Flag"));


                            ChartcommonMasterModel.insert(chartcommonMasterData);
                            SharedPreference.save("CHARTADDED", "chart");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                closeProgDialog(DashboardActivityOutPro.this);
                Toast.makeText(getApplicationContext(), "No Internet Connection Please try again later..", Toast.LENGTH_LONG).show();
            }
        });

        Log.i("STRINGREQURLOUTREACH", request + "");
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
            }
        });
        Log.i("SCI_PROGRAM", request + "");
        AppController.getInstance().add(request);

    }


    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SYNCH_BROADCAST);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(messagesReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED);
        } else {
            registerReceiver(messagesReceiver, intentFilter);
        }
    }

    BroadcastReceiver messagesReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                updateMessagesBadge();
            } catch (PackageManager.NameNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    };

    private void updateMessagesBadge() throws PackageManager.NameNotFoundException {

        synchdata1 = syncModel.getsynchdata();
        //  Log.i("ckm=>DATA",synchdata1.size()+"");
        syncdatetimerecylcer.setLayoutManager(linearLayoutManager);
        sychDateTimeAdapter = new SychDateTimeAdapter(DashboardActivityOutPro.this, synchdata1);
        syncdatetimerecylcer.setAdapter(sychDateTimeAdapter);


        synchdata2 = syncModel.getsynchdatalast();
        //Get Last Synch record
        SyncData syncdatainfo = synchdata2.get(synchdata2.size() - 1);
        Log.i("CKM=>SynchDATA", syncdatainfo + "");

        String dateinddmmyyy = syncdatainfo.getSynchdate();
        Log.i("CKM=>Synchdate", dateinddmmyyy + "");
        String user_id = syncdatainfo.getUserid();
        String camp_id = syncdatainfo.getCampid();
        String synch_id = syncdatainfo.getSynchid();

        String dateNtime = getyyyymmdd(dateinddmmyyy);
        Log.i("CKM=>NewFormat", dateNtime + "");

        //Get total failed Count
        int totalfailed = PopulationMedicalModel.getFaileDataCount(SharedPreference.get("CAMPID"));
        Log.i("CKM=>FailedRecord", totalfailed + "");

        int versionCode = getPackageManager()
                .getPackageInfo(getPackageName(), 0).versionCode;

        String versionName = getPackageManager()
                .getPackageInfo(getPackageName(), 0).versionName;


        callSynchdetailsdata(camp_id, user_id, synch_id, String.valueOf(totalfailed), dateNtime,
                String.valueOf(versionCode), versionName, getPhoneModel());

    }

    public String getyyyymmdd(String mmddyyyy) {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(mmddyyyy);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateString2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        return dateString2;
    }

    public void sendMessagesBroadcast() {
        Intent intent = new Intent(DashboardActivityOutPro.SYNCH_BROADCAST);
        sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(messagesReceiver);
    }


    private void callSynchdetailsdata(final String campid, final String userid, final String synchid, final String failedcount,
                                      final String datetime, final String ver_code, final String ver_name, final String modelno) {

        JSONObject synchdata = new JSONObject();
        try {
            synchdata.put("outreachsyncdetailsid", "0");
            synchdata.put("campid", campid);
            synchdata.put("userid", userid);
            synchdata.put("id", synchid);
            synchdata.put("failcount", failedcount);
            synchdata.put("syncdatetime", datetime);
            synchdata.put("appversioncode", ver_code);
            synchdata.put("appversionnumber", ver_name);
            synchdata.put("mobilemodel", modelno);
            Log.i("ckm=>synchJSON", synchdata + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        showProgDialog("Updating sync details");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AttributeSet.Params.ADD_OUTREACH_SYNCH_DETAILS, synchdata,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        closeProgDialog();
                        Log.i("ckm=>synchdetailrespo", response + "");
                        try {
                            if (response.getString("ErrorMessage").equals("Success")) {
                                //shortToast("Synch details update successfully");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ErrorLog", error + "");
                Toast.makeText(DashboardActivityOutPro.this, "Network error occurred try again", Toast.LENGTH_LONG).show();
                closeProgDialog();
            }
        });

        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
            }
        });
        AppController.getInstance().add(jsonObjectRequest);
    }

    public static String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_1);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Outreach Dashboard");
        outreachimg_logout = findViewById(R.id.outreachimg_logout);
        outreachimg_logout.setOnClickListener(this);
        btnCamp = findViewById(R.id.btnCamp);
        btnReport = findViewById(R.id.btnReport);
        btnCamp.setOnClickListener(this);
        btnReport.setOnClickListener(this);
        imgsynch = findViewById(R.id.imgsynch);
        imgsynch.setOnClickListener(this);
        campcomplete = findViewById(R.id.campcomplete);
        campcomplete.setOnClickListener(this);
        PadaNAshworkerDetailModel.getInstance(this);
        export = findViewById(R.id.export);
        export.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.outreachimg_logout) {
            getApp().getSettings().setUserId(0L);
            getApp().getSettings().setActiveUserName("");
            SharedPreference.removeKey("designation");
            gotoScreen(LogInActivity.class);
            finish();

        } else if (id == R.id.btnCamp) {
            showCampoption();

        } else if (id == R.id.imgsynch) {
            getuploaddatafromSqlite();

        } else if (id == R.id.btnReport) {
            showReports();

        } else if (id == R.id.campcomplete) {
            campcompleted();

        } else if (id == R.id.export) {
            importdata();
        }
    }



    public void importdata() {
//        sqliteToExcel = new SQLiteToExcel(getApplicationContext(), DatabaseHelper.DB_NAME, directory_path);
//        sqliteToExcel.exportAllTables("userss.xls", new SQLiteToExcel.ExportListener() {
//            @Override
//            public void onStart() {
//                Log.i("ckm=>Onstart", "Exception");
//            }
//
//            @Override
//            public void onCompleted(String filePath) {
//                Log.i("ckm=>completed", "executed");
//            }
//
//            @Override
//            public void onError(Exception e) {
//                Log.i("ckm=>exception", "Exception");
//            }
//        });
    }


    private void campcompleted() {
        OutreachPlanModel.open();
        campname = OutreachPlanModel.getActiveCampName(SharedPreference.get("CAMPID"));

        if (campname.size() > 0) {
            String name = campname.get(0).getCamp_Location();
            String msg = name + " camp completed as on " + campname.get(0).getOutreach_To() + " ! " + " Please make sure you have done the sync to send all your data to server, data will be remove from the device.";
            new AlertDialog.Builder(this)
                    .setTitle("Note")
                    .setMessage(msg)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                            int totalfailed = PopulationMedicalModel.getFaileDataCount(SharedPreference.get("CAMPID"));

                            if (totalfailed > 0) {
                                longToast("Please Sync before camp complete");
                            } else {
                                PopulationMedicalModel.deletePopulationMedical();
                                PhcSubCenterModel.deletePhcSubCenterTable();
                                OutreachPlanModel.deleteMessageTable();
                                CampProgramModel.deleteCampProgram();
                                longToast("Camp completed successfully");
                                Intent intent = new Intent(DashboardActivityOutPro.this, LogInActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            longToast("camp already completed no data found");
        }
    }

    public void showCampoption() {
        List<String> patientOpt;
        patientOpt = new ArrayList<String>();
        patientOpt.add("Daily Work");
        patientOpt.add("Population Details");
        patientOpt.add("Health Worker");
        patientOpt.add("Camp Complete");
        patientOpt.add("About Us");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // builder.setTitle("Select");
        final ArrayAdapter<String> patientOptAdapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.itemText, patientOpt);
        builder.setAdapter(patientOptAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("ckm=>selsected", which + "");
                if (which == 0) {
                    Intent intent = new Intent(DashboardActivityOutPro.this, DailyworkPhcPadaActivity.class);
                    startActivity(intent);
                } else if (which == 1) {
                    Intent intent = new Intent(DashboardActivityOutPro.this, EditDataActivity.class);
                    startActivity(intent);
                } else if (which == 2) {
                    Intent intent = new Intent(DashboardActivityOutPro.this, HealthWorkerListActivity.class);
                    startActivity(intent);
                } else if (which == 3) {
                    camp_completed_operation();
                }else if (which == 4) {
                    aboutUsDialogueBox();
                }
            }
        });
        builder.show();
    }
    @SuppressWarnings("deprecation")
    private void aboutUsDialogueBox() {
        try {
            String appName = getString(R.string.app_name);
            String versionName = getPackageManager()
                    .getPackageInfo(getPackageName(), 0).versionName;

            LayoutInflater inflater = LayoutInflater.from(this);
            View view = inflater.inflate(R.layout.dialog_about_us, null);

            TextView aboutText = view.findViewById(R.id.about_text);

            String message = "<b>" + appName + "</b><br><br>" +
                    "Impact India Foundation’s Outreach App is meant for the use of " +
                    "<b>Lifeline Express Outreach team</b> and <b>volunteers</b> associated with each camp of Impact India Foundation’s Lifeline Express. " +
                    "Volunteers and staff involved in outreach activities, before the actual camp begins, use this app for entering demographic and basic health parameters of serviced population.<br><br>" +
                    "This app is an effort of Impact India Foundation to maintain complete data of all beneficiaries of Lifeline Express camps digitally, " +
                    "for the sake of transparency, analytics and improving quality of healthcare delivery to the served community.<br><br>" +
                    "You are currently using <b>version " + versionName + "</b> of the LLE Outreach App.";

            aboutText.setText(Html.fromHtml(message));

            VideoView logoVideo = view.findViewById(R.id.logo_video);

            Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.logo_animation);

            logoVideo.setVideoURI(videoUri);
            logoVideo.setMediaController(null); // optional, hides controls

            logoVideo.setOnPreparedListener(mp -> {
                mp.setLooping(true);   // loop forever
                logoVideo.start();
            });

            logoVideo.setOnErrorListener((mp, what, extra) -> {
                return false;
            });


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("About Us")
                    .setView(view)
                    .setPositiveButton("OK", null)
                    .show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void camp_completed_operation() {
        String msg = " Are you sure to complete the camp ?";
        new AlertDialog.Builder(this)
                .setTitle("Note")
                .setMessage(msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Send_camp_complete_status(SharedPreference.get("CAMPID"), SharedPreference.get("Userid"), formattedDate);

                    }
                })

                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    public void Send_camp_complete_status(String campid, String userid, String curretndate) {
        showProgDialog("Completing camp please wait...");
        JSONObject complete_data = new JSONObject();
        try {
            complete_data.put("campcompletestatusid", "0");
            complete_data.put("campid", campid);
            complete_data.put("id", "1");
            complete_data.put("outuserid", userid);
            complete_data.put("campcompletedate", curretndate);

            Log.e("ckm=>CampJson", complete_data + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AttributeSet.Params.SEND_CAMP_COMPLETE_STATUS, complete_data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            closeProgDialog();
                            if (response.getString("ErrorCode").equals("0") && response.getString("ErrorMessage").equals("Success")) {
                                Log.e("CAMP+>ID", response.getString("campid"));
                                Log.e("CKM+campidstatus", response.getString("campcompletestatusid"));
                                Log.e("CKM=>id", response.getString("id"));
                                // longToast("Camp completed successfully");
                                click++;
                                //Logout();
                                //callService();
                                getuploaddatafromSqlite();
                            } else if (response.getString("ErrorCode").equals("1") && response.getString("ErrorMessage").equals("Fail")) {
                                Log.e("ErrorLog", "");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                closeProgDialog();
                Log.e("ErrorLog", error + "");
                Toast.makeText(DashboardActivityOutPro.this, "Network error occurred try again", Toast.LENGTH_LONG).show();
            }
        });
        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
            }
        });
        AppController.getInstance().add(jsonObjectRequest);
    }

    public void showReports() {
        List<String> patientOpt;
        patientOpt = new ArrayList<String>();
        patientOpt.add("Daily Work Report");
        patientOpt.add("Dashboard");
        patientOpt.add("Gender wise Report");
        patientOpt.add("Age wise Report");
        patientOpt.add("Blood Sugar");
        patientOpt.add("Blood Pressure Report");
        patientOpt.add("Visual Acuity");
        patientOpt.add("BMI Interpretation");
        /* patientOpt.add("Health Worker");*/
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select");
        final ArrayAdapter<String> patientOptAdapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.itemText, patientOpt);
        builder.setAdapter(patientOptAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("ckm=>selsected", which + "");
                if (which == 0) {
                    Intent intent = new Intent(DashboardActivityOutPro.this, Dailywork_Report.class);
                    startActivity(intent);
                } else if (which == 1) {
                    Intent intent = new Intent(DashboardActivityOutPro.this, DailyworkDashboardActivity.class);
                    startActivity(intent);
                } else if (which == 2) {
                    Intent intent = new Intent(DashboardActivityOutPro.this, GenderReportActivity.class);
                    startActivity(intent);
                } else if (which == 3) {
                    Intent intent = new Intent(DashboardActivityOutPro.this, ReportbyAgeActivity.class);
                    startActivity(intent);
                } else if (which == 4) {
                    Intent intent = new Intent(DashboardActivityOutPro.this, BloodSugarReportActivity.class);
                    startActivity(intent);
                } else if (which == 5) {
                    Intent intent = new Intent(DashboardActivityOutPro.this, BloodPresureActivity.class);
                    startActivity(intent);
                } else if (which == 6) {
                    Intent intent = new Intent(DashboardActivityOutPro.this, VisualAcuityReportActivity.class);
                    startActivity(intent);
                } else if (which == 7) {
                    Intent intent = new Intent(DashboardActivityOutPro.this, BMIInterprettionActivity.class);
                    startActivity(intent);
                }


            }
        });
        builder.show();
    }

    private void callService() {

        showProgDialog("Sync In Progress");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AttributeSet.Params.UPLOAD_OUTREACH_DATA/*url */, jo2,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        closeProgDialog();
                        Log.i("ckm=>outreachdate", response + "");
                        //  imgsynch.setEnabled(false);
                        try {
                            JSONArray jsonArray = response.getJSONArray("outreachdata");
                            //                         Log.i("JSONARRAY",jsonArray+"");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                PadaNAshworkerDetailModel.UpdatepadaAshSynchData(jsonObject1.getString("id"), jsonObject1.getString("outreachdetailsid"));
                                if (jsonObject1.has("populationdetails")) {
                                    JSONArray jsonpopulationdetails = jsonObject1.getJSONArray("populationdetails");
                                    Log.i("jsonpopulationdetails", "jsonpopulationdetails");
                                    for (int j = 0; j < jsonpopulationdetails.length(); j++) {
                                        JSONObject jsonObject = jsonpopulationdetails.getJSONObject(j);
                                        Log.i("jsonpopulationdetails2", "jsonpopulationdetails2");
                                        if (jsonObject.getString("ErrorCode").equals("0") && jsonObject.getString("ErrorMessage").equals("Success")) {
                                            Log.i("CKM+>ID", jsonObject.getString("id"));
                                            Log.i("CKM+outreachmemetailid", jsonObject.getString("outreachmemberdetailid"));
                                            Log.i("CKM=>outreachdetailsid", jsonObject.getString("outreachdetailsid"));
                                            PopulationMedicalModel.UpdateSynchresult(jsonObject.getString("id"), jsonObject.getString("outreachmemberdetailid"), jsonObject.getString("outreachdetailsid"));
                                            Log.i("INtheEND", "jsonpopulationdetails");
                                        } else if (jsonObject.getString("ErrorCode").equals("1") && jsonObject.getString("ErrorMessage").equals("Fail")) {
                                            synchfail = true;
                                        }
                                    }
                                }

                                if (i == (jsonArray.length() - 1)) {//this is the last iteration of for loop
                                    Log.i("ckm=>thisislast", "Last loop");
                                }
                                Log.i("Endjsonpopulationdetail", "jsonpopulationdetails");
                            }
                            SyncData syncData = new SyncData(SharedPreference.get("CAMPID"), SharedPreference.get("Userid"), formattedDate, "", "");
                            syncModel.insert(syncData);
                            synchdata1 = syncModel.getsynchdata();
                            sychDateTimeAdapter.notifyDataSetChanged();
                            if (synchfail) {
                                longToast("Partially Sync Completed");
                            } else {

                                if (click > 1) {
                                    click = 1;
                                    longToast("Camp Completed Successfully");
                                    PopulationMedicalModel.deletePopulationMedical();
                                    PadaNAshworkerDetailModel.deletePadaNAshaworker();
                                    //syncModel.deleteSychModel();
                                    Log.i("click", "" + click);
                                    Logout();
                                } else {
                                    longToast("Sync Completed");
                                }

                            }

                            sendMessagesBroadcast();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ErrorLog", error + "");
                Toast.makeText(DashboardActivityOutPro.this, "Network error occurred try again", Toast.LENGTH_LONG).show();
                closeProgDialog();
            }
        });

        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
            }
        });
        AppController.getInstance().add(jsonObjectRequest);
    }

    public void Logout() {
        getApp().getSettings().setUserId(Long.valueOf(0));
        getApp().getSettings().setActiveUserName("");
        SharedPreference.removeKey("designation");
        gotoScreen(LogInActivity.class);
        finish();
    }



    public void gotoScreen(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public void closeProgDialog() {
        if (progressDialog != null || progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }


    public void showProgDialog(String msg) {
        if (progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = new ProgressDialog(this, R.style.prog_diag_style);
            progressDialog.setMessage(msg);
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, MultipleCampActivity.class);
        startActivity(i);
    }
}