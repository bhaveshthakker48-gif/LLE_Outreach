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

public class VisualAcuityReportActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView txtnormalvisualcount, txtabnormalcount, tvabnormalperc, txtdnavisualcount, tvdnavisualper,
            txtTotalvisual, txtnormalvisualpercentage;
    Float normalper, abnormalper, dnaper;
    PieChart pie_chart_visualacurity;
    Toolbar toolbar;
    ArrayList<UserDetails> newuserlist = new ArrayList<>();
    Spinner spinner_visualacuity;
    private SQLiteDatabase db;
    private UserDetailsDAO userDetailsDAO;
    ArrayList<UserDetails> userlist = new ArrayList<>();
    String data1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual_acuity_report);
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
        PopulationMedicalModel.getInstance(this);
        init();


        if (newuserlist.size() > 0) {
            data1 = String.valueOf(newuserlist.get(0).getUserId());
            setcount(data1);
        }
        /* setcount();
        perc();
        drawgraph();*/
    }

    private void setcount(final String userid) {
        txtdnavisualcount.setText(String.valueOf(PopulationMedicalModel.getvisualnullcount(SharedPreference.get("CAMPID"), userid)));
        txtnormalvisualcount.setText(String.valueOf(PopulationMedicalModel.getvisualNormalCount(SharedPreference.get("CAMPID"), "normal", "normal", "0", userid)));
        txtabnormalcount.setText(String.valueOf(PopulationMedicalModel.getvisualNormalCount(SharedPreference.get("CAMPID"), "abnormal", "abnormal", "1", userid)));
        txtTotalvisual.setText(String.valueOf(PopulationMedicalModel.gettotalpopulationcount(SharedPreference.get("CAMPID"), userid)));
        perc();
    }

    private void perc() {
        normalper = (float) ((Double.valueOf(txtnormalvisualcount.getText().toString()) / Double.valueOf(txtTotalvisual.getText().toString())) * 100);
        abnormalper = (float) ((Double.valueOf(txtabnormalcount.getText().toString()) / Double.valueOf(txtTotalvisual.getText().toString())) * 100);
        dnaper = (float) ((Double.valueOf(txtdnavisualcount.getText().toString()) / Double.valueOf(txtTotalvisual.getText().toString())) * 100);

        tvabnormalperc.setText(String.valueOf(abnormalper));
        tvdnavisualper.setText(String.valueOf(dnaper));
        txtnormalvisualpercentage.setText(String.valueOf(normalper));

        pie_chart_visualacurity = findViewById(R.id.pie_chart_visualacurity);

        drawgraph();
    }

    public void drawgraph() {
        List<ChartData> data = new ArrayList<>();

        if (!txtnormalvisualpercentage.getText().toString().equals("0.0")) {
            data.add(new ChartData(txtnormalvisualpercentage.getText().toString() + "%", normalper, Color.WHITE, Color.parseColor("#FFA726")));
        }
        if (!tvabnormalperc.getText().toString().equals("0.0")) {
            data.add(new ChartData(tvabnormalperc.getText().toString() + "%", abnormalper, Color.WHITE, Color.parseColor("#66BB6A")));
        }
        if (!tvdnavisualper.getText().toString().equals("0.0")) {
            data.add(new ChartData(tvdnavisualper.getText().toString() + "%", dnaper, Color.DKGRAY, Color.parseColor("#EF5350")));
        }

        pie_chart_visualacurity.setChartData(data);

    }

    private void init() {
        txtnormalvisualcount = findViewById(R.id.txtnormalvisualcount);
        txtabnormalcount = findViewById(R.id.txtabnormalcount);
        tvabnormalperc = findViewById(R.id.tvabnormalperc);
        txtdnavisualcount = findViewById(R.id.txtdnavisualcount);
        tvdnavisualper = findViewById(R.id.tvdnavisualper);
        txtTotalvisual = findViewById(R.id.txtTotalvisual);
        txtnormalvisualpercentage = findViewById(R.id.txtnormalvisualpercentage);

        toolbar = findViewById(R.id.toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Visual Acuity");

        spinner_visualacuity = findViewById(R.id.spinner_visualacuity);
        spinner_visualacuity.setOnItemSelectedListener(this);
        userlist = userDetailsDAO.getuserlist(SharedPreference.get("CAMPID"));
        for (UserDetails obj : userlist) {
            if (obj.getUserId().equals(Long.valueOf(SharedPreference.get("Userid")))) {
                newuserlist.add(0, obj);
            } else {
                newuserlist.add(obj);
            }
        }

        Log.i("ckm=>ActivCamp", newuserlist.size() + "");
        ActiveCampReport report = new ActiveCampReport(this, newuserlist);
        spinner_visualacuity.setAdapter(report);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int viewId = adapterView.getId();
        if (viewId == R.id.spinner_visualacuity) {
            data1 = newuserlist.get(i).getUserId().toString();
            setcount(data1);
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
