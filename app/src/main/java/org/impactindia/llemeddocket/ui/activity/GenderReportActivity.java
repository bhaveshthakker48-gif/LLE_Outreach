package org.impactindia.llemeddocket.ui.activity;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.intrusoft.scatter.ChartData;
import com.intrusoft.scatter.PieChart;

import org.impactindia.llemeddocket.DatabaseHelper;
import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.adapter.ActiveCampReport;
import org.impactindia.llemeddocket.orm.PopulationMedicalModel;
import org.impactindia.llemeddocket.orm.UserDetailsDAO;
import org.impactindia.llemeddocket.pojo.UserDetails;
import org.impactindia.llemeddocket.util.SharedPreference;

import java.util.ArrayList;
import java.util.List;

public class GenderReportActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    Toolbar toolbar;
    TextView txtmalecount, txtfemalecount, txtother, txtTotal;
    int malecount_per, femalecount_per, othercount_perce;
    Double total, malecount, femalecount, othercount;
    PieChart pieChart;
    TextView tvmaleper, tvFemaleper, tvOtherper;
    Float maleper, femaleper, otherper;
    Spinner spinner_genderreport;

    private SQLiteDatabase db;
    private UserDetailsDAO userDetailsDAO;
    ArrayList<UserDetails> userlist = new ArrayList<>();
    String data1;

    ArrayList<UserDetails> newuserlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender_report);
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

        db = new DatabaseHelper(this).getWritableDatabase();
        UserDetailsDAO.getInstance(this);
        userDetailsDAO = new UserDetailsDAO(this, db);
        PopulationMedicalModel.getInstance(this);
        init();
//        Log.i("Data=>",userlist.get(0).getUserId()+"");


        if (newuserlist.size() > 0) {
            data1 = String.valueOf(newuserlist.get(0).getUserId());
            setcounts(data1);
        }


    }

    private void findpercentage() {

        maleper = (float) ((Double.valueOf(txtmalecount.getText().toString()) / Double.valueOf(txtTotal.getText().toString())) * 100);
        femaleper = (float) ((Double.valueOf(txtfemalecount.getText().toString()) / Double.valueOf(txtTotal.getText().toString())) * 100);
        otherper = (float) ((Double.valueOf(txtother.getText().toString()) / Double.valueOf(txtTotal.getText().toString())) * 100);

        tvmaleper.setText(String.valueOf(maleper));
        tvFemaleper.setText(String.valueOf(femaleper));
        tvOtherper.setText(String.valueOf(otherper));

        drawgraph();
    }

    private void drawgraph() {

        List<ChartData> data = new ArrayList<>();
        if (!tvmaleper.getText().toString().equals("0.0")) {
            data.add(new ChartData(tvmaleper.getText().toString() + "%", maleper, Color.WHITE, Color.parseColor("#FFA726")));
        }
        if (!tvFemaleper.getText().toString().equals("0.0")) {
            data.add(new ChartData(tvFemaleper.getText().toString() + "%", femaleper, Color.WHITE, Color.parseColor("#66BB6A")));
        }
        if (!tvOtherper.getText().toString().equals("0.0")) {
            data.add(new ChartData(tvOtherper.getText().toString() + "%", otherper, Color.DKGRAY, Color.parseColor("#EF5350")));
        }
        pieChart.setChartData(data);
    }

    private void init() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Gender Wise Report");
        txtmalecount = findViewById(R.id.txtmalecount);
        txtfemalecount = findViewById(R.id.txtfemalecount);
        txtother = findViewById(R.id.txtother);
        txtTotal = findViewById(R.id.txtTotal);
        pieChart = findViewById(R.id.pie_chart);

        tvmaleper = findViewById(R.id.tvmaleper);
        tvFemaleper = findViewById(R.id.tvFemaleper);
        tvOtherper = findViewById(R.id.tvOtherper);

        spinner_genderreport = findViewById(R.id.spinner_genderreport);

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

        spinner_genderreport.setAdapter(report);
        spinner_genderreport.setOnItemSelectedListener(this);

    }

    private void setcounts(final String userid) {

        // txtTotal.setText(String.valueOf(PopulationMedicalModel.gettotalpopulationcount(SharedPreference.get("CAMPID"),SharedPreference.get("Userid"))));
        txtTotal.setText(String.valueOf(PopulationMedicalModel.gettotalpopulationcount(SharedPreference.get("CAMPID"), userid)));
        total = Double.valueOf(txtTotal.getText().toString());
        //txtmalecount.setText(String.valueOf(PopulationMedicalModel.gettotalMaleFemaleOthercount("Male",SharedPreference.get("CAMPID"))));
        txtmalecount.setText(String.valueOf(PopulationMedicalModel.gettotalMaleFemaleOthercount("Male", SharedPreference.get("CAMPID"), userid)));
        malecount = Double.valueOf(txtmalecount.getText().toString());
        //txtfemalecount.setText(String.valueOf(PopulationMedicalModel.gettotalMaleFemaleOthercount("Female",SharedPreference.get("CAMPID"))));
        txtfemalecount.setText(String.valueOf(PopulationMedicalModel.gettotalMaleFemaleOthercount("Female", SharedPreference.get("CAMPID"), userid)));
        //femalecount = Double.valueOf(txtfemalecount.getText().toString());
        femalecount = Double.valueOf(txtfemalecount.getText().toString());
        //txtother.setText(String.valueOf(PopulationMedicalModel.gettotalMaleFemaleOthercount("Other",SharedPreference.get("CAMPID"))));
        txtother.setText(String.valueOf(PopulationMedicalModel.gettotalMaleFemaleOthercount("Other", SharedPreference.get("CAMPID"), userid)));
        othercount = Double.valueOf(txtother.getText().toString());
        findpercentage();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int id = adapterView.getId();
        if (id == R.id.spinner_genderreport) {
            pieChart.clearFocus();
            data1 = newuserlist.get(i).getUserId().toString();
            setcounts(data1);
            // findpercentage();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
