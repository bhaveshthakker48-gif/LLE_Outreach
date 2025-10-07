package org.impactindia.llemeddocket.ui.activity;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
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

public class ReportbyAgeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Toolbar toolbar;
    TextView txtage0to18,txtage18to29,txtage30to39,txtage40to49,txtage50to59,txtageabove60,txtagetotal;
    TextView tv0to18per,tv18to29per,tv30to39per,tv40to49per,tv50to59per,tv60per;
    Float zeroto18count_per,eighteento29count_per,thirtyto39count_per,fourtyto49count_per,fiftyto59coount_per,sixtycount_per;
    PieChart piechartage;

    private SQLiteDatabase db;
    private UserDetailsDAO userDetailsDAO;
    ArrayList<UserDetails> userlist = new ArrayList<>();
    String data1;
    Spinner spinner_genderreport;
    ActiveCampReport report;
    ArrayList<UserDetails> newuserlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportby_age);

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

        UserDetailsDAO.getInstance(this);
        db = new DatabaseHelper(this).getWritableDatabase();
        userDetailsDAO = new UserDetailsDAO(this, db);
        init();



        if (!newuserlist.isEmpty()) {
            data1 = String.valueOf(newuserlist.get(0).getUserId());
            setage(data1);
        } else {
            Log.w("ReportbyAgeActivity", "No user data found for CAMPID=" + SharedPreference.get("CAMPID"));
            txtage0to18.setText("0");
            txtage18to29.setText("0");
            txtage30to39.setText("0");
            txtage40to49.setText("0");
            txtage50to59.setText("0");
            txtageabove60.setText("0");
            txtagetotal.setText("0");
        }

    }
    private void drawpiegraph() {

        List<ChartData> data = new ArrayList<>();

        try {
            float val0to18 = Float.parseFloat(tv0to18per.getText().toString());
            float val18to29 = Float.parseFloat(tv18to29per.getText().toString());
            float val30to39 = Float.parseFloat(tv30to39per.getText().toString());
            float val40to49 = Float.parseFloat(tv40to49per.getText().toString());
            float val50to59 = Float.parseFloat(tv50to59per.getText().toString());
            float val60plus = Float.parseFloat(tv60per.getText().toString());

            if (val0to18 > 0) {
                data.add(new ChartData(val0to18 + "%", zeroto18count_per, Color.WHITE, Color.parseColor("#FFA726")));
            }

            if (val18to29 > 0) {
                data.add(new ChartData(val18to29 + "%", eighteento29count_per, Color.WHITE, Color.parseColor("#000000")));
            }

            if (val30to39 > 0) {
                data.add(new ChartData(val30to39 + "%", thirtyto39count_per, Color.WHITE, Color.parseColor("#C6FF00")));
            }

            if (val40to49 > 0) {
                data.add(new ChartData(val40to49 + "%", fourtyto49count_per, Color.WHITE, Color.parseColor("#A7FFEB")));
            }

            if (val50to59 > 0) {
                data.add(new ChartData(val50to59 + "%", fiftyto59coount_per, Color.WHITE, Color.parseColor("#66BB6A")));
            }

            if (val60plus > 0) {
                data.add(new ChartData(val60plus + "%", sixtycount_per, Color.WHITE, Color.parseColor("#EF5350")));
            }

            // ✅ Only set data if not empty
            if (!data.isEmpty()) {
                piechartage.setChartData(data);
            } else {
                Log.w("ReportbyAgeActivity", "No chart data available, skipping PieChart rendering");
//                 piechartage.clearChart();
            }

        } catch (NumberFormatException e) {
            Log.e("ReportbyAgeActivity", "Error parsing percentage values", e);
        }
    }

    private void findPercentage() {
        zeroto18count_per = (float) ((Double.valueOf(txtage0to18.getText().toString())/Double.valueOf(txtagetotal.getText().toString())) * 100);
        eighteento29count_per = (float) ((Double.valueOf(txtage18to29.getText().toString())/Double.valueOf(txtagetotal.getText().toString())) * 100);
        thirtyto39count_per = (float) ((Double.valueOf(txtage30to39.getText().toString())/Double.valueOf(txtagetotal.getText().toString())) * 100);
        fourtyto49count_per = (float) ((Double.valueOf(txtage40to49.getText().toString())/Double.valueOf(txtagetotal.getText().toString())) * 100);
        fiftyto59coount_per = (float) ((Double.valueOf(txtage50to59.getText().toString())/Double.valueOf(txtagetotal.getText().toString())) * 100);
        sixtycount_per = (float) ((Double.valueOf(txtageabove60.getText().toString())/Double.valueOf(txtagetotal.getText().toString())) * 100);

        tv0to18per.setText(String.valueOf(zeroto18count_per));
        tv18to29per.setText(String.valueOf(eighteento29count_per));
        tv30to39per.setText(String.valueOf(thirtyto39count_per));
        tv40to49per.setText(String.valueOf(fourtyto49count_per));
        tv50to59per.setText(String.valueOf(fiftyto59coount_per));
        tv60per.setText(String.valueOf(sixtycount_per));

        drawpiegraph();
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Age Wise Report");
        txtage0to18 = findViewById(R.id.txtage0to18);
        txtage18to29 = findViewById(R.id.txtage18to29);
        txtage30to39 = findViewById(R.id.txtage30to39);
        txtage40to49 = findViewById(R.id.txtage40to49);
        txtage50to59 = findViewById(R.id.txtage50to59);
        txtageabove60 = findViewById(R.id.txtageabove60);
        tv60per = findViewById(R.id.tv60per);

        tv0to18per = findViewById(R.id.tv0to18per);
        tv18to29per = findViewById(R.id.tv18to29per);
        tv30to39per = findViewById(R.id.tv30to39per);
        tv40to49per = findViewById(R.id.tv40to49per);
        tv50to59per = findViewById(R.id.tv50to59per);
        txtagetotal = findViewById(R.id.txtagetotal);

        piechartage = findViewById(R.id.pie_chart);

        PopulationMedicalModel.getInstance(this);
        spinner_genderreport = findViewById(R.id.spinner_genderreport);

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
        report = new ActiveCampReport(this, newuserlist);

        spinner_genderreport.setAdapter(report);
        spinner_genderreport.setOnItemSelectedListener(this);
    }

    private void setage(String data1) {
        //getAgecount
        txtage0to18.setText(String.valueOf(PopulationMedicalModel.getAgecount("0","17", SharedPreference.get("CAMPID"),data1)));
        txtage18to29.setText(String.valueOf(PopulationMedicalModel.getAgecount("18","29", SharedPreference.get("CAMPID"),data1)));
        txtage30to39.setText(String.valueOf(PopulationMedicalModel.getAgecount("30","39", SharedPreference.get("CAMPID"),data1)));
        txtage40to49.setText(String.valueOf(PopulationMedicalModel.getAgecount("40","49", SharedPreference.get("CAMPID"),data1)));
        txtage50to59.setText(String.valueOf(PopulationMedicalModel.getAgecount("50","59", SharedPreference.get("CAMPID"),data1)));
        txtageabove60.setText(String.valueOf(PopulationMedicalModel.getAgecountgreaterthan60( SharedPreference.get("CAMPID"),data1)));
        txtagetotal.setText(String.valueOf(PopulationMedicalModel.gettotalpopulationcount(SharedPreference.get("CAMPID"),data1)));

        findPercentage();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int viewId = adapterView.getId();
        if (viewId == R.id.spinner_genderreport) {
                data1 = newuserlist.get(i).getUserId().toString();
                Log.i("ckm=>ID",data1);
                setage(data1);
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
