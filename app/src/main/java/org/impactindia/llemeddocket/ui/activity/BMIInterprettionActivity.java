package org.impactindia.llemeddocket.ui.activity;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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

import org.impactindia.llemeddocket.DatabaseHelper;
import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.adapter.ActiveCampReport;
import org.impactindia.llemeddocket.orm.PopulationMedicalModel;
import org.impactindia.llemeddocket.orm.UserDetailsDAO;
import org.impactindia.llemeddocket.pojo.UserDetails;
import org.impactindia.llemeddocket.util.SharedPreference;

import java.util.ArrayList;
import java.util.List;

public class BMIInterprettionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView txtageunder, txtageBtntwoFive, txtunderweight,txtcountnormalbmi,txtoverweight,txtoverweight1,txtobese,txtobesegrade2,txtmoderateobesity3,txtmorbidobesityiv,txt_totalbmicount,txt_datanotavailable;
    TextView tvtageunder, tvtageBtntwoFive, tvtunderweight,tvNormalperbmi,tvoverweightper,tvoverweightper1,tvobeseper,txtmoderateobesityper3,txtmorbidobesityperiv,txtdatanotavailbleper,txtobesegrade2per;
    Toolbar toolbar;
    Float underAge, underwaitper,normalper,overweightper,overweight1per,obeseper,obesegrade2per,moderateob3per,morbidiv_per,dna_per;
    PieChart pie_chart;
    ArrayList<UserDetails> newuserlist = new ArrayList<>();

    Spinner spinner_bmiInter;
    private SQLiteDatabase db;
    private UserDetailsDAO userDetailsDAO;
    ArrayList<UserDetails> userlist = new ArrayList<>();
    String data1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_b_m_i_interprettion);
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


        if (newuserlist.size()>0){
            data1 = String.valueOf(newuserlist.get(0).getUserId());
            setcount(data1);
        }
    }
    private void drawpiechart() {
        List<ChartData> data = new ArrayList<>();

        if (!tvtageunder.getText().toString().equals(0.0)){
            data.add(new ChartData(tvtageunder.getText().toString() + "%", underAge, Color.WHITE, Color.parseColor("#42A5F5")));
        }
        if (!tvtunderweight.getText().toString().equals("0.0")) {
            data.add(new ChartData(tvtunderweight.getText().toString() + "%", underwaitper, Color.WHITE, Color.parseColor("#FFA726")));
        }
        if (!tvNormalperbmi.getText().toString().equals("0.0")) {
            data.add(new ChartData(tvNormalperbmi.getText().toString() + "%", normalper, Color.WHITE, Color.parseColor("#000000")));
        }
        if (!tvoverweightper.getText().toString().equals("0.0")) {
            data.add(new ChartData(tvoverweightper.getText().toString() + "%", overweightper, Color.WHITE, Color.parseColor("#C6FF00")));
        }
        if (!tvoverweightper1.getText().toString().equals("0.0")) {
            data.add(new ChartData(tvoverweightper1.getText().toString() + "%", overweight1per, Color.WHITE, Color.parseColor("#A7FFEB")));
        }
        if (!tvobeseper.getText().toString().equals("0.0")) {
            data.add(new ChartData(tvobeseper.getText().toString() + "%", obeseper, Color.WHITE, Color.parseColor("#66BB6A")));
        }
        if (!txtobesegrade2per.getText().toString().equals("0.0")) {
            data.add(new ChartData(txtobesegrade2per.getText().toString() + "%", obesegrade2per, Color.WHITE, Color.parseColor("#FFCDD2")));
        }
        if (!txtmoderateobesityper3.getText().toString().equals("0.0")) {
            data.add(new ChartData(txtmoderateobesityper3.getText().toString() + "%", moderateob3per, Color.WHITE, Color.parseColor("#FFCDD2")));
        }
        if (!txtmorbidobesityperiv.getText().toString().equals("0.0")) {
            data.add(new ChartData(txtmorbidobesityperiv.getText().toString() + "%", morbidiv_per, Color.WHITE, Color.parseColor("#880E4F")));
        }
        if (!txtdatanotavailbleper.getText().toString().equals("0.0")) {
            data.add(new ChartData(txtdatanotavailbleper.getText().toString() + "%", dna_per, Color.WHITE, Color.parseColor("#00BFA5")));
        }

        if (data.isEmpty()) {
            // Prevent crash by adding a default value or skipping chart
            data.add(new ChartData("0%", 0f, Color.WHITE, Color.GRAY));
        }

        pie_chart.setChartData(data);
    }


    private void findper() {
        underAge = (float) ((Double.valueOf(txtageunder.getText().toString())/Double.valueOf(txt_totalbmicount.getText().toString())) * 100);
        underwaitper = (float) ((Double.valueOf(txtunderweight.getText().toString())/Double.valueOf(txt_totalbmicount.getText().toString())) * 100);
        normalper = (float) ((Double.valueOf(txtcountnormalbmi.getText().toString())/Double.valueOf(txt_totalbmicount.getText().toString())) * 100);
        overweightper = (float) ((Double.valueOf(txtoverweight.getText().toString())/Double.valueOf(txt_totalbmicount.getText().toString())) * 100);
        overweight1per = (float) ((Double.valueOf(txtoverweight1.getText().toString())/Double.valueOf(txt_totalbmicount.getText().toString())) * 100);
        obeseper = (float) ((Double.valueOf(txtobese.getText().toString())/Double.valueOf(txt_totalbmicount.getText().toString())) * 100);
        obesegrade2per = (float) ((Double.valueOf(txtobesegrade2.getText().toString())/Double.valueOf(txt_totalbmicount.getText().toString())) * 100);
        moderateob3per = (float) ((Double.valueOf(txtmoderateobesity3.getText().toString())/Double.valueOf(txt_totalbmicount.getText().toString())) * 100);
        morbidiv_per = (float) ((Double.valueOf(txtmorbidobesityiv.getText().toString())/Double.valueOf(txt_totalbmicount.getText().toString())) * 100);
        dna_per = (float) ((Double.valueOf(txt_datanotavailable.getText().toString())/Double.valueOf(txt_totalbmicount.getText().toString())) * 100);

        tvtageunder.setText(String.valueOf(underAge));
        tvtunderweight.setText(String.valueOf(underwaitper));
        tvNormalperbmi.setText(String.valueOf(normalper));
        tvoverweightper.setText(String.valueOf(overweightper));
        tvoverweightper1.setText(String.valueOf(overweight1per));
        tvobeseper.setText(String.valueOf(obeseper));
        txtobesegrade2per.setText(String.valueOf(obesegrade2per));
        txtmorbidobesityperiv.setText(String.valueOf(morbidiv_per));
        txtmoderateobesityper3.setText(String.valueOf(moderateob3per));
        txtdatanotavailbleper.setText(String.valueOf(dna_per));

        drawpiechart();
    }

    private void setcount(final String data1) {
        txtageunder.setText(String.valueOf(PopulationMedicalModel.getBMI_inter_Count("UnderAge", SharedPreference.get("CAMPID"),data1)));
        txtunderweight.setText(String.valueOf(PopulationMedicalModel.getBMI_inter_Count("Underweight", SharedPreference.get("CAMPID"),data1)));
        txtcountnormalbmi.setText(String.valueOf(PopulationMedicalModel.getBMI_inter_Count("Normal", SharedPreference.get("CAMPID"),data1)));
        txtoverweight.setText(String.valueOf(PopulationMedicalModel.getBMI_inter_Count("Overweight", SharedPreference.get("CAMPID"),data1)));
        txtoverweight1.setText(String.valueOf(PopulationMedicalModel.getBMI_inter_Count("Overweight = Grade-I Obesity", SharedPreference.get("CAMPID"),data1)));
        txtobese.setText(String.valueOf(PopulationMedicalModel.getBMI_inter_Count("Obese", SharedPreference.get("CAMPID"),data1)));
        txtobesegrade2.setText(String.valueOf(PopulationMedicalModel.getBMI_inter_Count("Obese = Grade-II Obesity", SharedPreference.get("CAMPID"),data1)));
        txtmoderateobesity3.setText(String.valueOf(PopulationMedicalModel.getBMI_inter_Count("Moderately Obese = Grade-III Obesity", SharedPreference.get("CAMPID"),data1)));
        txtmorbidobesityiv.setText(String.valueOf(PopulationMedicalModel.getBMI_inter_Count("Morbid Obesity = Grade-IV Obesity", SharedPreference.get("CAMPID"),data1)));
        txt_datanotavailable.setText(String.valueOf(PopulationMedicalModel.getBMI_inter_Count("dna", SharedPreference.get("CAMPID"),data1)));
        txt_totalbmicount.setText(String.valueOf(String.valueOf(PopulationMedicalModel.gettotalpopulationcount(SharedPreference.get("CAMPID"),data1))));
        findper();
    }

    private void init() {
        txtobesegrade2per = findViewById(R.id.txtobesegrade2per);
        pie_chart = findViewById(R.id.pie_chart);
        txtageunder = findViewById(R.id.txtunderAge);
        txtunderweight = findViewById(R.id.txtunderweight);
        txtcountnormalbmi = findViewById(R.id.txtcountnormalbmi);
        txtoverweight = findViewById(R.id.txtoverweight);
        txtoverweight1 = findViewById(R.id.txtoverweight1);
        txtobese = findViewById(R.id.txtobese);
        txtmoderateobesity3 = findViewById(R.id.txtmoderateobesity3);
        txtmorbidobesityiv = findViewById(R.id.txtmorbidobesityiv);
        txt_totalbmicount = findViewById(R.id.txt_totalbmicount);
        txtobesegrade2 = findViewById(R.id.txtobesegrade2);
        txt_datanotavailable = findViewById(R.id.txt_datanotavailable);

        tvtageunder = findViewById(R.id.tvtUnderAge);
        tvtunderweight = findViewById(R.id.tvtunderweight);
        tvNormalperbmi = findViewById(R.id.tvNormalperbmi);
        tvoverweightper = findViewById(R.id.tvoverweightper);
        tvoverweightper1 = findViewById(R.id.tvoverweightper1);
        tvobeseper = findViewById(R.id.tvobeseper);
        txtmoderateobesityper3 = findViewById(R.id.txtmoderateobesityper3);
        txtmorbidobesityperiv = findViewById(R.id.txtmorbidobesityperiv);
        txtdatanotavailbleper = findViewById(R.id.txtdatanotavailbleper);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("BMI Interpretation Report");


        spinner_bmiInter = findViewById(R.id.spinner_bmiInter);

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
        spinner_bmiInter.setAdapter(report);
        spinner_bmiInter.setOnItemSelectedListener(this);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int id = adapterView.getId();

        if (id == R.id.spinner_bmiInter) {
            data1 = newuserlist.get(i).getUserId().toString();
            setcount(data1);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Optional: do nothing
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}