package org.impactindia.llemeddocket.ui.activity;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
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
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;


public class BloodPresureActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    Toolbar toolbar;
    TextView txtnormalperce,txthighbp_perc,txtlowblood_perc,txtstage2_hyper_perc,txtstage1_hyper_perc,txtprehyper_per,txthypogly_per,txtdnacount;
    TextView txtnormalcount,txthighbp_count,txtlowblood_count,txtstage2_hyper_count,txtstage1_hyper_count,
            txtprehyper_count,txthypogly_count,txtTotalcount,txttotalperce,txtdnaperce;
    Float normalper,highbp_per,lowbp_per,stage1_per,stage2_per,prehyper_per,hypo_per,datanotavil_per;
    float test,dna;
    PieChart piechartage;
    ArrayList<UserDetails> newuserlist = new ArrayList<>();

    Spinner spinner_bloodpressure;
    private SQLiteDatabase db;
    private UserDetailsDAO userDetailsDAO;
    ArrayList<UserDetails> userlist = new ArrayList<>();
    String data1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_presure);

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
        if (newuserlist.size()>0){
            data1 = String.valueOf(newuserlist.get(0).getUserId());
            setbscount(data1);
        }

      /*  percentage();
        drawpiegraph();*/

    }



    private void init() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Blood Pressure Report");

        txtnormalperce = findViewById(R.id.txtnormalperce);
        txthighbp_perc = findViewById(R.id.txthighbp_perc);
        txtlowblood_perc = findViewById(R.id.txtlowblood_perc);
        txtstage2_hyper_perc = findViewById(R.id.txtstage2_hyper_perc);
        txtstage1_hyper_perc = findViewById(R.id.txtstage1_hyper_perc);
        txtprehyper_per = findViewById(R.id.txtprehyper_per);
        txthypogly_per = findViewById(R.id.txthypogly_per);
        txtdnaperce = findViewById(R.id.txtdnaperce);

        txtnormalcount = findViewById(R.id.txtnormalcount);
        txthighbp_count = findViewById(R.id.txthighbp_count);
        txtlowblood_count = findViewById(R.id.txtlowblood_count);
        txtstage2_hyper_count = findViewById(R.id.txtstage2_hyper_count);
        txtstage1_hyper_count = findViewById(R.id.txtstage1_hyper_count);
        txtprehyper_count = findViewById(R.id.txtprehyper_count);
        txthypogly_count = findViewById(R.id.txthypogly_count);

        txtTotalcount = findViewById(R.id.txtTotalcount);
        txtdnacount = findViewById(R.id.txtdnacount);

        piechartage = findViewById(R.id.pie_chart);

        txttotalperce = findViewById(R.id.txttotalperce);
        txttotalperce.setText("100");

        spinner_bloodpressure = findViewById(R.id.spinner_bloodpressure);
        spinner_bloodpressure.setOnItemSelectedListener(this);


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

        Log.i("ckm=>ActivCamp", newuserlist.size() + "");
        ActiveCampReport report = new ActiveCampReport(this, newuserlist);
        spinner_bloodpressure.setAdapter(report);

    }

    private void setbscount(final String id) {
        txtnormalcount.setText(String.valueOf(PopulationMedicalModel.getBloodPressurCount("Normal", SharedPreference.get("CAMPID"),id)));
        txthighbp_count.setText(String.valueOf(PopulationMedicalModel.getBloodPressurCount("High Blood Pressure", SharedPreference.get("CAMPID"),id)));
        txtlowblood_count.setText(String.valueOf(PopulationMedicalModel.getBloodPressurCount("Low Blood Pressure", SharedPreference.get("CAMPID"),id)));
        txtstage2_hyper_count.setText(String.valueOf(PopulationMedicalModel.getBloodPressurCount("Stage 2 Hypertension", SharedPreference.get("CAMPID"),id)));
        txtstage1_hyper_count.setText(String.valueOf(PopulationMedicalModel.getBloodPressurCount("Stage 1 Hypertension", SharedPreference.get("CAMPID"),id)));
        txtprehyper_count.setText(String.valueOf(PopulationMedicalModel.getBloodPressurCount("Prehypertension", SharedPreference.get("CAMPID"),id)));
        txthypogly_count.setText(String.valueOf(PopulationMedicalModel.getBloodPressurCount("Hypotension", SharedPreference.get("CAMPID"),id)));
        txtdnacount.setText(String.valueOf(PopulationMedicalModel.getBloodPressurCount("dna", SharedPreference.get("CAMPID"),id)));
        txtTotalcount.setText(String.valueOf(PopulationMedicalModel.gettotalpopulationcount(SharedPreference.get("CAMPID"),id)));
        percentage();
    }

    public void percentage()
    {
        test = (float) ((Double.valueOf(txthypogly_count.getText().toString())/Double.valueOf(txtTotalcount.getText().toString())) * 100);
        prehyper_per = (float) ((Double.valueOf(txtprehyper_count.getText().toString())/Double.valueOf(txtTotalcount.getText().toString())) * 100);
        stage1_per = (float) ((Double.valueOf(txtstage1_hyper_count.getText().toString())/Double.valueOf(txtTotalcount.getText().toString())) * 100);
        stage2_per = (float) ((Double.valueOf(txtstage2_hyper_count.getText().toString())/Double.valueOf(txtTotalcount.getText().toString())) * 100);
        highbp_per = (float) ((Double.valueOf(txthighbp_count.getText().toString())/Double.valueOf(txtTotalcount.getText().toString())) * 100);
        lowbp_per = (float) ((Double.valueOf(txtlowblood_count.getText().toString())/Double.valueOf(txtTotalcount.getText().toString())) * 100);
        normalper = (float) ((Double.valueOf(txtnormalcount.getText().toString())/Double.valueOf(txtTotalcount.getText().toString())) * 100);
        dna = (float) ((Double.valueOf(txtdnacount.getText().toString())/Double.valueOf(txtTotalcount.getText().toString())) * 100);
        txtnormalperce.setText(String.valueOf(normalper));
        txthighbp_perc.setText(String.valueOf(highbp_per));
        txtlowblood_perc.setText(String.valueOf(lowbp_per));
        txtstage1_hyper_perc.setText(String.valueOf(stage1_per));
        txtstage2_hyper_perc.setText(String.valueOf(stage2_per));
        txtprehyper_per.setText(String.valueOf(prehyper_per));
        txthypogly_per.setText(String.valueOf(test));
        txtdnaperce.setText(String.valueOf(dna));
        drawpiegraph();
    }


    private void drawpiegraph() {

        List<ChartData> data = new ArrayList<>();
        if (!txthypogly_per.getText().toString().equals("0.0"))
        {
            data.add(new ChartData(txthypogly_per.getText().toString() + "%", test, Color.WHITE, Color.parseColor("#FFA726")));
        }

        if (!txtprehyper_per.getText().toString().equals("0.0"))
        {
            data.add(new ChartData(txtprehyper_per.getText().toString() + "%", prehyper_per, Color.WHITE, Color.parseColor("#000000")));
        }

        if (!txtstage1_hyper_perc.getText().toString().equals("0.0"))
        {
            data.add(new ChartData(txtstage1_hyper_perc.getText().toString() + "%", stage1_per, Color.WHITE, Color.parseColor("#C6FF00")));
        }

        if (!txtstage2_hyper_perc.getText().toString().equals("0.0"))
        {
            data.add(new ChartData(txtstage2_hyper_perc.getText().toString() + "%", stage2_per, Color.WHITE, Color.parseColor("#A7FFEB")));
        }

        if (!txtlowblood_perc.getText().toString().equals("0.0"))
        {
            data.add(new ChartData(txtlowblood_perc.getText().toString() + "%", lowbp_per, Color.WHITE, Color.parseColor("#66BB6A")));
        }

        if (!txthighbp_perc.getText().toString().equals("0.0"))
        {
            data.add(new ChartData(txthighbp_perc.getText().toString() + "%", highbp_per, Color.WHITE, Color.parseColor("#6A1B9A")));
        }

        if (!txtnormalperce.getText().toString().equals("0.0"))
        {
            data.add(new ChartData(txtnormalperce.getText().toString() + "%", normalper, Color.WHITE, Color.parseColor("#78909C")));
        }

        if (!txtdnaperce.getText().toString().equals("0.0"))
        {
            data.add(new ChartData(txtdnaperce.getText().toString() + "%", dna, Color.WHITE, Color.parseColor("#D50000")));
        }

        piechartage.setChartData(data);

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int id = adapterView.getId();

        if (id == R.id.spinner_bloodpressure) {
            data1 = newuserlist.get(i).getUserId().toString();
            setbscount(data1);
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
