package org.impactindia.llemeddocket.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import org.impactindia.llemeddocket.adapter.OpdListAdapter;
import org.impactindia.llemeddocket.orm.PopulationMedicalModel;
import org.impactindia.llemeddocket.pojo.PopMedicalData;
import org.impactindia.llemeddocket.util.SharedPreference;

import java.util.ArrayList;

public class OpdPatientListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    RecyclerView recycler_opd;
    Toolbar toolbar;
    String pada;
    LinearLayoutManager linearLayoutManager;
    ArrayList<PopMedicalData> data = new ArrayList<PopMedicalData>();
    OpdListAdapter opdListAdapter;
    TextView nodata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opd_patient_list);

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

        Intent intent = getIntent();
        pada = intent.getStringExtra("PADA");
        Log.i("ckm=>oas",pada);
        init();
    }
    public void init()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Patient OPD");
        nodata = findViewById(R.id.nodata);
        recycler_opd = findViewById(R.id.recycler_opd);
        PopulationMedicalModel.getInstance(this);



        data = PopulationMedicalModel.getOpdPatientList(pada, SharedPreference.get("CAMPID"));
        if (data.size() < 1)
        {
            nodata.setVisibility(View.VISIBLE);
            recycler_opd.setVisibility(View.GONE);
        }
        else
        {
            nodata.setVisibility(View.GONE);
            recycler_opd.setVisibility(View.VISIBLE);
        }

        linearLayoutManager = new LinearLayoutManager(this);
        recycler_opd.setLayoutManager(linearLayoutManager);
        opdListAdapter = new OpdListAdapter(this,data);
        recycler_opd.setAdapter(opdListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // return super.onCreateOptionsMenu(menu);
        menu.clear();
        getMenuInflater().inflate(R.menu.population_menu, menu);

        MenuItem search = menu.findItem(R.id.action_search);
        search.setVisible(true);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("search");
        searchView.setBackgroundColor(getResources().getColor(R.color.light_grey));
        return true;


    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        opdListAdapter.getFilter().filter(s);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        opdListAdapter.getFilter().filter(s);
        return true;
    }

}