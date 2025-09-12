package org.impactindia.llemeddocket.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.impactindia.llemeddocket.AttributeSet;
import org.impactindia.llemeddocket.DatabaseHelper;
import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.orm.CampDAO;
import org.impactindia.llemeddocket.orm.CampProgramModel;
import org.impactindia.llemeddocket.orm.DAOException;
import org.impactindia.llemeddocket.orm.OutreachPlanModel;
import org.impactindia.llemeddocket.orm.PhcSubCenterModel;
import org.impactindia.llemeddocket.orm.PhcUsersModel;
import org.impactindia.llemeddocket.orm.UserDetailsDAO;
import org.impactindia.llemeddocket.pojo.Base;
import org.impactindia.llemeddocket.pojo.Camp;
import org.impactindia.llemeddocket.pojo.CampProgramData;
import org.impactindia.llemeddocket.pojo.OutreachPlanData;
import org.impactindia.llemeddocket.pojo.PhcSubcenterData;
import org.impactindia.llemeddocket.pojo.PhcUserData;
import org.impactindia.llemeddocket.pojo.UserDetails;
import org.impactindia.llemeddocket.pojo.WsModel;
import org.impactindia.llemeddocket.service.AutoSyncService;
import org.impactindia.llemeddocket.util.AppController;
import org.impactindia.llemeddocket.util.NetworkConnection;
import org.impactindia.llemeddocket.util.SharedPreference;
import org.impactindia.llemeddocket.view.MultiSelectSpinner;
import org.impactindia.llemeddocket.ws.AsyncWebServiceAdapter;
import org.impactindia.llemeddocket.ws.WebService;
import org.impactindia.llemeddocket.ws.WsCallCompleteListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogInActivity extends BaseActivity implements View.OnClickListener, WsCallCompleteListener {

    public static final String BROADCAST_ACTION = "com.chowgulemediconsult.meddocket.cms.service.receiver";

    private static final int GET_CLINIC_USER_DATA = 111;

    private TextView lblUserName;
    private EditText txtUserName, txtPwd;
    private Button btnSignIn;
    ArrayList<OutreachPlanData> outreachplandata1 = new ArrayList<OutreachPlanData>();
    private SQLiteDatabase db;
    private UserDetailsDAO userDetailsDAO;
    private CampDAO campDAO;
    private Camp camp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentFilter filter = new IntentFilter();
        filter.addAction(BROADCAST_ACTION);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(syncCompleteReceiver, filter, Context.RECEIVER_NOT_EXPORTED);
        } else {
            registerReceiver(syncCompleteReceiver, filter);
        }
        db = new DatabaseHelper(this).getWritableDatabase();
        OutreachPlanModel.getInstance(this);
        CampProgramModel.getInstance(this);
        PhcSubCenterModel.getInstance(this);
        PhcUsersModel.getInstance(this);
        userDetailsDAO = new UserDetailsDAO(this, db);
        campDAO = new CampDAO(this, db);
        List<Camp> campList = campDAO.findAll();

        if (!campList.isEmpty()) {
            camp = campList.get(0);
        }
        setContentView(R.layout.activity_login);

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
        AppController.initialize(this);
        initViews();
    }

    private void initViews() {

        lblUserName = (TextView) findViewById(R.id.lblUserName);
        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtPwd = (EditText) findViewById(R.id.txtPwd);
        txtPwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                        || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    submitButtonClick();
                }
                return false;
            }
        });
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(this);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(syncCompleteReceiver);
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSignIn) {
            hideKeyboard(txtPwd);
            submitButtonClick();
        }
    }


    public void submitButtonClick() {
        hideKeyboard(txtPwd);
        if (isValidationSuccess()) {

            if (NetworkConnection.isNetworkAvailable(this)) {
                getClinicUsersData();
            } else {
                if (isUserValid(txtUserName.getText().toString(), txtPwd
                        .getText().toString())) {
                    if (SharedPreference.get("designation").equals("27"/*"158"*/))
                    {
                        Log.d("LoginCheck", "Designation = " + SharedPreference.get("designation"));
                        Intent i = new Intent(LogInActivity.this,DashboardActivityOutPro.class);
                     startActivity(i);
                     finish();
                    }
                    else
                    {
                        gotoDashboard();
                    }
                } else {
                 //   shortToast(getString(R.string.login_err_msg));
                    shortToast(getString(R.string.network_unavailable_error_msg));
//                        longToast(getString(R.string.network_unavailable_error_msg));
                }
            }
        }
    }

    public boolean isUserValid(String userName, String pwd) {
        try {
            UserDetails userDetails = userDetailsDAO.findFirstByField(UserDetails.USER_NAME, userName, UserDetails.PASSWORD, pwd);
            if (userDetails != null && userDetails.getFirstName() != null) {
                getApp().getSettings().setUserId(
                        userDetails.getUserId());
                SharedPreference.save("designation",userDetails.getDesignation());
                getApp().getSettings().setActiveUserName(userDetails.getFirstName() + " " + userDetails.getLastName());
                return true;
            }
            return false;
        } catch (DAOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void getClinicUsersData() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("UserName", txtUserName.getText().toString());
        params.put("Password", txtPwd.getText().toString());
//        params.put("SyncDate", null);
        WebService<WsModel> getUserData = new WebService<WsModel>(params,
                AttributeSet.Params.GET_LOGIN_DETAIL, Base.class, 0);
        getUserData.setDebug(true);
        if (!getApp().getSettings().isFirstLaunch()) {
            getUserData.setConnTimeout(true);
        }
        AsyncWebServiceAdapter<WsModel> getUserDataAdapter = new AsyncWebServiceAdapter<WsModel>();
        getUserDataAdapter.getResponseObject(getUserData, this,
                GET_CLINIC_USER_DATA);
        showProgDialog(this);
    }

    private boolean isValidationSuccess() {
        if (isEmpty(txtUserName.getText().toString())) {
            txtUserName.setFocusableInTouchMode(true);
            txtUserName.requestFocus();
            txtUserName
                    .setError(getSpanStringBuilder(getString(R.string.user_name_req_err_msg)));
            // userName.setError(Html
            // .fromHtml("<font color ='red'>Please enter username</font>"));
        } else if (isEmpty(txtPwd.getText().toString())) {
            txtPwd.setFocusable(true);
            txtPwd.requestFocus();
            txtPwd.setError(getSpanStringBuilder(getString(R.string.pwd_req_err_msg)));
        } else {
            return true;
        }
        return false;
    }

    @Override
    public void onCallComplete(Object result, int type) {
        if (type == GET_CLINIC_USER_DATA) {
            closeProgDialog(this);
            if (result != null) {
                Base base = (Base) result;
                if (base != null && base.getErrorCode().longValue() == AttributeSet.Constants.ZERO) {
                    List<UserDetails> userData = base.getLogInData();
                    if (userData != null && !userData.isEmpty()) {
                        for (UserDetails userDetails : userData) {
                            try {
                                UserDetails oldUser = userDetailsDAO.findFirstByUserId(userDetails.getUserId());
                                if (oldUser == null) {
                                    userDetailsDAO.create(userDetails);
                                } else {
                                    userDetails.setId(oldUser.getId());
                                    userDetailsDAO.update(userDetails);
                                }
                                SharedPreference.save("designation",userDetails.getDesignation());
                                SharedPreference.save("Userid",userDetails.getUserId());
                                if (txtUserName.getText().toString().trim().equals(userDetails.getUserName().trim())) {
                                    getApp().getSettings().setUserId(
                                            userDetails.getUserId());
                                    getApp().getSettings().setActiveUserName(userDetails.getFirstName() + " " + userDetails.getLastName());
                                }
                                if(userDetails.getDesignation().equals("27"/*"158"*/))
                                {
                                    getdata();
                                }
                                else
                                {
                                    gotoDashboard();
                                }

                            } catch (DAOException e) {
                                e.printStackTrace();
                            }
                        }
                        //Commented by chetan
                        if (getApp().getSettings().isFirstLaunch() || camp == null) {
                            startSync();
                        /*else {
                            gotoDashboard();
                        }*/
                        }
                    } else {
                        shortToast(getString(R.string.login_err_msg));
                    }
                } else {
                    shortToast("Incorrect username or password");
                }
            }
        }
    }

    private void startSync() {
        Intent service = new Intent(this, AutoSyncService.class);
        service.putExtra(AttributeSet.Constants.SYNC_MASTER, true);
        startService(service);
        showProgDialog(this,"Sync In Progress");
    }

    private void gotoDashboard() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    private BroadcastReceiver syncCompleteReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            getApp().getSettings().setFirstLaunch(false);
        }
    };


    public void getdata() {

        showProgDialog(LogInActivity.this);
        StringRequest request = new StringRequest(Request.Method.GET, AttributeSet.Params.GET_FUTURE_CAMPLIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("LOGIN_RESPONSE", response);
                closeProgDialog(LogInActivity.this);
                try {
                    JSONObject json=new JSONObject(response);
                    Log.i("Ckm=>DATA",json+"");
                   // Toast.makeText(LogInActivity.this,"InsideCampData",Toast.LENGTH_LONG).show();
                    if (json.getString("ErrorMessage").equals("success") && json.getString("ErrorCode").equals("0"))
                    {
                        JSONArray jsonArray,jsonArray1,jsonArray2,jsonArrayphc,jsonPhcList;
                        JSONObject jsonObject1,jsonObject2,jsonObject_program,jsonObject3,jsonObject4;

                        OutreachPlanModel.deleteMessageTable();
                        CampProgramModel.deleteMessageTable();
                        PhcSubCenterModel.deleteMessageTable();
                        PhcUsersModel.deleteMessageTable();

                        jsonArray = json.getJSONArray("CampData");
                        Log.i("JsonArraylength",jsonArray.length()+"");

                        for (int i = 0 ; i<jsonArray.length();i++)
                        {
                            jsonObject1 = jsonArray.getJSONObject(i);
                            Log.i("ckm=>INsideCampData",jsonObject1+"");
                            jsonArray1 = jsonObject1.getJSONArray("outreachplan");
                            Log.i("outreachplan",jsonArray1.length()+"");
                            SharedPreference.save("totalcamp",jsonArray1.length());

                            if (jsonArray1.length() != 0)
                            {

                                Log.i("ckm=>campyear",jsonObject1.getString("CampYear"));
                                Log.i("ckm=>campID",jsonObject1.getString("CampId"));
                                Log.i("ckm=>campLocation",jsonObject1.getString("CampLocation"));
                                SharedPreference.save("CAMPID",jsonObject1.getString("CampId"));

                                jsonObject2 = jsonArray1.getJSONObject(0);
                                Log.i("outreachplan12",jsonObject2+"");


                                SharedPreference.save("OutreachFrom",jsonObject2.getString("outreachprogfrom"));
                                SharedPreference.save("OutreachTo",jsonObject2.getString("outreachprogto"));

                                OutreachPlanData outreachPlanData = new OutreachPlanData(jsonObject1.getString("CampId"),jsonObject1.getString("CampNo"),SharedPreference.get("Userid"),
                                        jsonObject1.getString("CampYear"),jsonObject1.getString("CampLocation"),jsonObject1.getString("State"),jsonObject2.getString("outreachprogfrom"),
                                        jsonObject2.getString("outreachprogto"),jsonObject1.getString("FromDate"),jsonObject1.getString("ToDate"),jsonObject2.getString("district"),
                                        jsonObject2.getString("taluka"),jsonObject2.getString("outreachplanid"),SharedPreference.get("designation"));
                                OutreachPlanModel.insert(outreachPlanData);

                                jsonArray2 = jsonObject1.getJSONArray("programarray");
                                {
                                    for (int p=0 ; p<jsonArray2.length(); p++)
                                    {
                                        jsonObject_program = jsonArray2.getJSONObject(p);
                                        CampProgramData campProgramData = new CampProgramData(jsonObject1.getString("CampId"),jsonObject1.getString("CampNo"),
                                                jsonObject_program.getString("campprogram"),jsonObject_program.getString("campprogramfrom"),jsonObject_program.getString("campprogramto"));
                                            CampProgramModel.insert(campProgramData);
                                        Log.i("ckm=>programArray",jsonObject_program+"");
                                    }
                                }

                                jsonArrayphc = jsonObject2.getJSONArray("phcuserlist");
                                jsonPhcList = jsonObject2.getJSONArray("phclist");

                                for (int k = 0 ; k< jsonArrayphc.length(); k++)
                                {
                                    jsonObject3 = jsonArrayphc.getJSONObject(k);
                                    PhcUserData phcUserData = new PhcUserData(jsonObject1.getString("CampId"),jsonObject1.getString("CampNo"),jsonObject3.getString("phc"),
                                            jsonObject3.getString("userid"),jsonObject3.getString("subcenter"),jsonObject3.getString("village"),jsonObject3.getString("pada"));
                                    PhcUsersModel.open();

                                    PhcUsersModel.insert(phcUserData);
                                    Log.i("ckm=>PhcUserList",jsonObject3+"");
                                }

                                for (int l = 0;l<jsonPhcList.length();l++)
                                {
                                    jsonObject4 = jsonPhcList.getJSONObject(l);
                                    PhcSubcenterData phcSubcenterData = new PhcSubcenterData(jsonObject1.getString("CampId"),jsonObject1.getString("CampNo"),jsonObject4.getString("subcenter"),
                                            jsonObject4.getString("phc"),jsonObject4.getString("village"),jsonObject4.getString("pada"));
                                    PhcSubCenterModel.open();
                                    PhcSubCenterModel.insert(phcSubcenterData);
                                    Log.i("ckm=>PhcList",jsonObject4+"");
                                }
                                outreachplandata1 = OutreachPlanModel.getActiveCamp();

                                if (outreachplandata1.size() > 1)
                                {
                                    Intent intent = new Intent(LogInActivity.this, MultipleCampActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    Intent intent = new Intent(LogInActivity.this,DashboardActivityOutPro.class);
                                    startActivity(intent);
                                    finish();

                                }
                            }
                            else
                            {
                                Intent intent1 = new Intent(LogInActivity.this,DashboardActivityOutPro.class);
                                intent1.putExtra("OUTREACHCOUNT","0");
                                startActivity(intent1);
                            }
                        }
                    }
                    else if (json.getString("ErrorCode").equals("0") || json.getString("ErrorMessage").equals("Data not available"))
                    {
                        shortToast("Currently there is no active camp ");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                closeProgDialog(LogInActivity.this);
                Toast.makeText(getApplicationContext(),"No Internet Connection Please try again later..",Toast.LENGTH_LONG).show();
            }
        });

        Log.i("STRINGREQURLOUTREACH",request+"");
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
        Log.i("SCI_PROGRAM",request+"");
        AppController.getInstance().add(request);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
