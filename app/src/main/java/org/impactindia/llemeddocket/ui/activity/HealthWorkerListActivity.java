package org.impactindia.llemeddocket.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.adapter.AshworkerAdapter;
import org.impactindia.llemeddocket.adapter.PopulationDetailsAdapter;
import org.impactindia.llemeddocket.orm.PadaNAshworkerDetailModel;
import org.impactindia.llemeddocket.orm.PopulationMedicalModel;
import org.impactindia.llemeddocket.pojo.PadanashaworkerData;
import org.impactindia.llemeddocket.pojo.PopMedicalData;
import org.impactindia.llemeddocket.util.SharedPreference;

import java.util.ArrayList;

public class HealthWorkerListActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recycler_healthworker;
    LinearLayoutManager linearLayoutManager;
    ArrayList<PadanashaworkerData> data = new ArrayList<>();
    AshworkerAdapter ashworkerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_worker_list);


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
        init();
    }

    private void init() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Health Workers");
        recycler_healthworker = findViewById(R.id.recycler_healthworker);
        PadaNAshworkerDetailModel.getInstance(this);
        PadaNAshworkerDetailModel.open();
        data = PadaNAshworkerDetailModel.getallpadadetails(SharedPreference.get("CAMPID"));
        Log.i("ckm=>datahealth",data.size()+"");


        linearLayoutManager = new LinearLayoutManager(this);
        recycler_healthworker.setLayoutManager(linearLayoutManager);
        ashworkerAdapter = new AshworkerAdapter(this,data);
        recycler_healthworker.setAdapter(ashworkerAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}