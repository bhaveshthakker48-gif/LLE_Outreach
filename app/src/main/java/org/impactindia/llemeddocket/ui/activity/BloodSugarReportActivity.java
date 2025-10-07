package org.impactindia.llemeddocket.ui.activity;

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

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;


import com.intrusoft.scatter.ChartData;
import com.intrusoft.scatter.PieChart;

import org.eazegraph.lib.models.PieModel;
import org.impactindia.llemeddocket.DatabaseHelper;
import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.adapter.ActiveCampReport;
import org.impactindia.llemeddocket.orm.PopulationMedicalModel;
import org.impactindia.llemeddocket.orm.UserDetailsDAO;
import org.impactindia.llemeddocket.pojo.UserDetails;
import org.impactindia.llemeddocket.util.SharedPreference;

import java.util.ArrayList;
import java.util.List;

public class BloodSugarReportActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {


    TextView txtcounthypoglyce,txtcountnormal,txtcountprediabetes,txtdiabetes,txtdataNotAvail,txt_bptotal;
    Toolbar toolbar;
    Float perthypoglyce,pernormal,perprediabetes,perdiabetes,perdatanotavail,pertotal;
    TextView tvHypoglycemiaper,tvPreDiabetesper,tvDiabetesper,tvNormalper,tvdnaper;
    PieChart pieChart;
    Spinner spinner_bloodsugareport;
    ArrayList<UserDetails> newuserlist = new ArrayList<>();
    private SQLiteDatabase db;
    private UserDetailsDAO userDetailsDAO;
    ArrayList<UserDetails> userlist = new ArrayList<>();
    String data1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_sugar_report);

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


        PopulationMedicalModel.getInstance(this);
        db = new DatabaseHelper(this).getWritableDatabase();
        UserDetailsDAO.getInstance(this);
        userDetailsDAO = new UserDetailsDAO(this, db);
        init();


        if (newuserlist.size()>0){
            data1 = String.valueOf(newuserlist.get(0).getUserId());
            setbscount(data1);
        }
    }

    private void findpercentage() {
         perthypoglyce = (float) ((Double.valueOf(txtcounthypoglyce.getText().toString())/Double.valueOf(txt_bptotal.getText().toString())) * 100);
         pernormal = (float) ((Double.valueOf(txtcountnormal.getText().toString())/Double.valueOf(txt_bptotal.getText().toString())) * 100);
         perprediabetes = (float) ((Double.valueOf(txtcountprediabetes.getText().toString())/Double.valueOf(txt_bptotal.getText().toString())) * 100);
         perdiabetes = (float) ((Double.valueOf(txtdiabetes.getText().toString())/Double.valueOf(txt_bptotal.getText().toString())) * 100);
         perdatanotavail = (float) ((Double.valueOf(txtdataNotAvail.getText().toString())/Double.valueOf(txt_bptotal.getText().toString())) * 100);
        tvHypoglycemiaper.setText(String.valueOf(perthypoglyce));
        tvPreDiabetesper.setText(String.valueOf(perprediabetes));
        tvDiabetesper.setText(String.valueOf(perdiabetes));
        tvNormalper.setText(String.valueOf(pernormal));
        tvdnaper.setText(String.valueOf(perdatanotavail));
        drawpiegraph();

    }


    private void init() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Blood Sugar Report");
        txtcounthypoglyce = findViewById(R.id.txtcounthypoglyce);
        txtcountnormal = findViewById(R.id.txtcountnormal);
        txtcountprediabetes = findViewById(R.id.txtcountprediabetes);
        txtdiabetes = findViewById(R.id.txtdiabetes);
        txtdataNotAvail = findViewById(R.id.txtdataNotAvail);
        txt_bptotal = findViewById(R.id.txt_bptotal);

        tvHypoglycemiaper = findViewById(R.id.tvHypoglycemiaper);
        tvPreDiabetesper = findViewById(R.id.tvPreDiabetesper);
        tvDiabetesper = findViewById(R.id.tvDiabetesper);
        tvNormalper = findViewById(R.id.tvNormalper);
        tvdnaper = findViewById(R.id.tvdnaper);
        pieChart = findViewById(R.id.pie_chart);

        spinner_bloodsugareport = findViewById(R.id.spinner_bloodsugareport);
        spinner_bloodsugareport.setOnItemSelectedListener(this);

        userlist = userDetailsDAO.getuserlist(SharedPreference.get("CAMPID"));

        for (UserDetails obj : userlist)
        {
            if (obj.getUserId().equals(Long.valueOf(SharedPreference.get("Userid"))))
            {
                newuserlist.add(0,obj);
            }
            else
            {
                newuserlist.add(obj);
            }
        }

        Log.i("ckm=>ActivCamp", userlist.size() + "");
        ActiveCampReport report = new ActiveCampReport(this, newuserlist);
        spinner_bloodsugareport.setAdapter(report);
    }

    private void setbscount(final String userid) {
        txtcounthypoglyce.setText(String.valueOf(PopulationMedicalModel.getBloodSugarCount("Hypoglycemia", SharedPreference.get("CAMPID"),userid)));
        txtcountnormal.setText(String.valueOf(PopulationMedicalModel.getBloodSugarCount("Normal", SharedPreference.get("CAMPID"),userid)));
        txtcountprediabetes.setText(String.valueOf(PopulationMedicalModel.getBloodSugarCount("Pre-Diabetes", SharedPreference.get("CAMPID"),userid)));
        txtdiabetes.setText(String.valueOf(PopulationMedicalModel.getBloodSugarCount("Diabetes", SharedPreference.get("CAMPID"),userid)));
        txtdataNotAvail.setText(String.valueOf(PopulationMedicalModel.getBloodSugarCount("dna", SharedPreference.get("CAMPID"),userid)));
        txt_bptotal.setText(String.valueOf(PopulationMedicalModel.gettotalpopulationcount(SharedPreference.get("CAMPID"),userid)));
        findpercentage();
    }

    private void drawpiegraph() {

        List<ChartData> data = new ArrayList<>();


        if (!tvHypoglycemiaper.getText().toString().equals("0.0"))
        {
            data.add(new ChartData(tvHypoglycemiaper.getText().toString() + "%", perthypoglyce, Color.WHITE, Color.parseColor("#FFA726")));
        }
        if (!tvPreDiabetesper.getText().toString().equals("0.0"))
        {
            data.add(new ChartData(tvPreDiabetesper.getText().toString() + "%", perprediabetes, Color.WHITE, Color.parseColor("#000000")));
        }
        if (!tvDiabetesper.getText().toString().equals("0.0"))
        {
            data.add(new ChartData(tvDiabetesper.getText().toString() + "%", perdiabetes, Color.WHITE, Color.parseColor("#C6FF00")));
        }
        if (!tvNormalper.getText().toString().equals("0.0"))
        {
            data.add(new ChartData(tvNormalper.getText().toString() + "%", pernormal, Color.WHITE, Color.parseColor("#A7FFEB")));
        }
        if (!tvdnaper.getText().toString().equals("0.0"))
        {
            data.add(new ChartData(tvdnaper.getText().toString() + "%", perdatanotavail, Color.WHITE, Color.parseColor("#66BB6A")));
        }

        pieChart.setChartData(data);


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int id = adapterView.getId();

        if (id == R.id.spinner_bloodsugareport) {
            pieChart.clearFocus();
            data1 = newuserlist.get(i).getUserId().toString();
            Log.i("ckm=>BloodSugarID", data1);
            setbscount(data1);
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
