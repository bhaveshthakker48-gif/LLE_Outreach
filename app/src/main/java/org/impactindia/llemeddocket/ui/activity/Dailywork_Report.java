package org.impactindia.llemeddocket.ui.activity;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.impactindia.llemeddocket.AttributeSet;
import org.impactindia.llemeddocket.DatabaseHelper;
import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.adapter.ActiveCampReport;
import org.impactindia.llemeddocket.adapter.DailyworkReportAdapter;
import org.impactindia.llemeddocket.orm.OutreachPlanModel;
import org.impactindia.llemeddocket.orm.PopulationMedicalModel;
import org.impactindia.llemeddocket.orm.UserDetailsDAO;
import org.impactindia.llemeddocket.pojo.Base;
import org.impactindia.llemeddocket.pojo.OutreachPlanData;
import org.impactindia.llemeddocket.pojo.PopMedicalData;
import org.impactindia.llemeddocket.pojo.UserDetails;
import org.impactindia.llemeddocket.util.SharedPreference;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Dailywork_Report extends BaseActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    Spinner spinner_dailyworkreport;
    ArrayList<OutreachPlanData> outreachplandata = new ArrayList<OutreachPlanData>();
    ArrayList<UserDetails> userlist = new ArrayList<>();
    ArrayList<UserDetails> newuserlist = new ArrayList<>();
    ArrayList<PopMedicalData> popMedicalData = new ArrayList<PopMedicalData>();
    String Strcamplist;
    TextView txtbeforedate, txtcurrentdate, txttotalhousholds, txtTotalpopulationcount;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String dateoutreachfrom;
    LocalDate beforesevendaydate, outreachstartfrom, beforeselectdate;
    DateTimeFormatter formatter;
    int total_household = 0;
    int total_population = 0;
    Button btnsubmitdate;
    RecyclerView recycler_records;
    DailyworkReportAdapter dailyworkReportAdapter;
    LinearLayoutManager linearLayoutManager;
    SearchView searchView;
    int total = 0;
    TextView txtNorecord;
    private SQLiteDatabase db;
    Toolbar toolbar;
    private UserDetailsDAO userDetailsDAO;
    String data1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailywork__report);
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
        OutreachPlanModel.getInstance(this);
        PopulationMedicalModel.getInstance(this);
        UserDetailsDAO.getInstance(this);
        db = new DatabaseHelper(this).getWritableDatabase();
        userDetailsDAO = new UserDetailsDAO(this, db);
        init();
        data1 = SharedPreference.get("Userid");
        gettotalhoushold(data1);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                dailyworkReportAdapter.getFilter().filter(s);
                if (dailyworkReportAdapter.getItemCount() < 2) {
                    txtNorecord.setVisibility(View.VISIBLE);
                    Toast.makeText(Dailywork_Report.this, " No records found", Toast.LENGTH_SHORT).show();
                } else {
                    txtNorecord.setVisibility(View.GONE);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                dailyworkReportAdapter.getFilter().filter(s);
                if (dailyworkReportAdapter.getItemCount() < 2) {
                    txtNorecord.setVisibility(View.VISIBLE);
                    Toast.makeText(Dailywork_Report.this, " No records found", Toast.LENGTH_SHORT).show();
                } else {
                    txtNorecord.setVisibility(View.GONE);
                }
                return true;
            }
        });
    }

    public void gettotalhoushold(final String data) {
        total_household = PopulationMedicalModel.gethouseholdcount(SharedPreference.get("CAMPID"), data);
        txttotalhousholds.setText(String.valueOf(total_household));

        if (!isEmpty(txtbeforedate.getText().toString()) && !isEmpty(txtcurrentdate.getText().toString())) {
            popMedicalData = PopulationMedicalModel.getdailyworkrepot(getyyyymmdd(txtbeforedate.getText().toString()), getyyyymmdd(txtcurrentdate.getText().toString()), SharedPreference.get("CAMPID"), data1);
        }

        linearLayoutManager = new LinearLayoutManager(this);
        recycler_records.setLayoutManager(linearLayoutManager);
        dailyworkReportAdapter = new DailyworkReportAdapter(this, popMedicalData);
        recycler_records.setAdapter(dailyworkReportAdapter);
        for (int i = 0; i < popMedicalData.size(); i++) {
            total += Integer.valueOf(popMedicalData.get(i).getHousholscount());
        }

        total_population = PopulationMedicalModel.gettotalpopulationcount(SharedPreference.get("CAMPID"), data);
        txtTotalpopulationcount.setText(String.valueOf(total_population));

    }

    public void init() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Dailywork Report");

        spinner_dailyworkreport = findViewById(R.id.spinner_dailyworkreport);
        txtbeforedate = findViewById(R.id.txtbeforedate);
        txtcurrentdate = findViewById(R.id.txtcurrentdate);
        searchView = findViewById(R.id.searchView);
        txtbeforedate.setOnClickListener(this);
        txtcurrentdate.setOnClickListener(this);
        txttotalhousholds = findViewById(R.id.txttotalhousholds);
        txtTotalpopulationcount = findViewById(R.id.txtTotalpopulationcount);

        txtNorecord = findViewById(R.id.txtNorecord);
        outreachplandata = OutreachPlanModel.getActiveCamp();
        OutreachPlanData msg = new OutreachPlanData();
        Log.i("ckm=>ActivCamp", outreachplandata.size() + "");
        userlist = userDetailsDAO.getuserlist(SharedPreference.get("CAMPID"));
        for (UserDetails obj : userlist) {
            if (obj.getUserId().equals(Long.valueOf(SharedPreference.get("Userid")))) {
                newuserlist.add(0, obj);
            } else {
                newuserlist.add(obj);
            }
        }

        Log.i("ckm=>ActivCamp", userlist.size() + "");
        ActiveCampReport report = new ActiveCampReport(this, newuserlist);
        spinner_dailyworkreport.setAdapter(report);
        spinner_dailyworkreport.setPrompt("Select");
        spinner_dailyworkreport.setOnItemSelectedListener(this);
        btnsubmitdate = findViewById(R.id.btnsubmitdate);
        btnsubmitdate.setOnClickListener(this);

        recycler_records = findViewById(R.id.recycler_records);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar currentCal = Calendar.getInstance();
        String currentdate = dateFormat.format(currentCal.getTime());
        currentCal.add(Calendar.DATE, -7);
        String toDate = dateFormat.format(currentCal.getTime());
        dateoutreachfrom = outreachplandata.get(0).getOutreach_From();
        formatter = DateTimeFormatter.ofPattern(AttributeSet.Constants.REVERSE_SHORT_DATE);
        outreachstartfrom = LocalDate.parse(dateoutreachfrom);
        beforesevendaydate = LocalDate.parse(toDate);
        if (beforesevendaydate.isAfter(outreachstartfrom)) {
            txtbeforedate.setText(getddmmyyyy(toDate));
        } else {
            txtbeforedate.setText(getddmmyyyy(dateoutreachfrom));
        }
        txtcurrentdate.setText(getddmmyyyy(currentdate));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinner_dailyworkreport) {
            Strcamplist = newuserlist.get(0).getName();
            Log.i("ckm=>selectedCamp", Strcamplist);
            if (spinner_dailyworkreport.getSelectedItemPosition() == 0) {
                Strcamplist = newuserlist.get(0).getName();
                Log.i("ckm=>InsideselectedCamp", Strcamplist);
            }
            String data = newuserlist.get(position).getName().toString();
            data1 = newuserlist.get(position).getUserId().toString();
            Log.i("ckm=>Data", data1);
            gettotalhoushold(data1);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.txtbeforedate) {
            final Calendar c2 = Calendar.getInstance();
            mYear = c2.get(Calendar.YEAR);
            mMonth = c2.get(Calendar.MONTH);
            mDay = c2.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog2 = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            monthOfYear += 1;
                            String mt, dy;
                            if (monthOfYear < 10)
                                mt = "0" + monthOfYear; //if month less than 10 then ad 0 before month
                            else mt = String.valueOf(monthOfYear);

                            if (dayOfMonth < 10)
                                dy = "0" + dayOfMonth;
                            else dy = String.valueOf(dayOfMonth);
                            String selectedDate = dy + "-" + mt + "-" + year;
                            beforeselectdate = LocalDate.parse(getyyyymmdd(selectedDate), formatter);
                            if (beforeselectdate.isAfter(outreachstartfrom)) {
                                txtbeforedate.setText(dy + "-" + mt + "-" + year);
                            } else if (beforeselectdate.equals(outreachstartfrom)) {
                                txtbeforedate.setText(dy + "-" + mt + "-" + year);
                            } else {
                                shortToast("Please select correct date");
                            }

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog2.show();
        } else if (viewId == R.id.txtcurrentdate) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            monthOfYear += 1;
                            String mt, dy;
                            if (monthOfYear < 10)
                                mt = "0" + monthOfYear; //if month less than 10 then ad 0 before month
                            else mt = String.valueOf(monthOfYear);

                            if (dayOfMonth < 10)
                                dy = "0" + dayOfMonth;
                            else dy = String.valueOf(dayOfMonth);
                            txtcurrentdate.setText(dy + "-" + mt + "-" + year);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        } else if (viewId == R.id.btnsubmitdate) {
            updateCounts(getyyyymmdd(txtbeforedate.getText().toString()), getyyyymmdd(txtcurrentdate.getText().toString()));
            popMedicalData = PopulationMedicalModel.getdailyworkrepot(getyyyymmdd(txtbeforedate.getText().toString()), getyyyymmdd(txtcurrentdate.getText().toString()), SharedPreference.get("CAMPID"), data1);
            linearLayoutManager = new LinearLayoutManager(this);
            recycler_records.setLayoutManager(linearLayoutManager);
            dailyworkReportAdapter = new DailyworkReportAdapter(this, popMedicalData);
            recycler_records.setAdapter(dailyworkReportAdapter);
        }
    }

    private void updateCounts(String beforeDate, String currentDate) {
        // Total households
        total_household = PopulationMedicalModel.gethouseholdcount(SharedPreference.get("CAMPID"), data1);

        // Total population
        total_population = PopulationMedicalModel.gettotalpopulationcount(SharedPreference.get("CAMPID"), data1);

        popMedicalData = PopulationMedicalModel.getdailyworkrepot(beforeDate, currentDate, SharedPreference.get("CAMPID"), data1);

        int totalHousesInRange = 0;
        for (int i = 0; i < popMedicalData.size(); i++) {
            totalHousesInRange += Integer.parseInt(popMedicalData.get(i).getHousholscount());
        }

        int totalPopulationInRange = 0;
        for (int i = 0; i < popMedicalData.size(); i++) {
            try {
                totalPopulationInRange += Integer.parseInt(popMedicalData.get(i).getHousholscount());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        txttotalhousholds.setText(String.valueOf(totalHousesInRange));
        txtTotalpopulationcount.setText(String.valueOf(totalPopulationInRange));


        if (popMedicalData.isEmpty()) {
            txtTotalpopulationcount.setText("0");
            txttotalhousholds.setText("0");
        } else {
            txtNorecord.setVisibility(View.GONE);
        }
    }


    public String getyyyymmdd(String mmddyyyy) {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd-MM-yyyy").parse(mmddyyyy);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateString2 = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return dateString2;
    }

    public String getddmmyyyy(String getddmmyyyy) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(getddmmyyyy);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateString2 = new SimpleDateFormat("dd-MM-yyyy").format(date);
        return dateString2;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}