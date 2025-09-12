package org.impactindia.llemeddocket.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
import org.impactindia.llemeddocket.adapter.CustomSpinnerPada;
import org.impactindia.llemeddocket.adapter.CustomSpinnerSubCenter;
import org.impactindia.llemeddocket.adapter.CustomSpinnerVillage;
import org.impactindia.llemeddocket.adapter.CustomeSpinnerAdapter;
import org.impactindia.llemeddocket.adapter.OpdListAdapter;
import org.impactindia.llemeddocket.orm.PhcSubCenterModel;
import org.impactindia.llemeddocket.orm.PhcUsersModel;
import org.impactindia.llemeddocket.orm.PopulationMedicalModel;
import org.impactindia.llemeddocket.pojo.PhcSubcenterData;
import org.impactindia.llemeddocket.pojo.PhcUserData;
import org.impactindia.llemeddocket.pojo.PopMedicalData;
import org.impactindia.llemeddocket.util.SharedPreference;

import java.util.ArrayList;

public class OpddetailActivity extends BaseActivity implements SearchView.OnQueryTextListener {


    Toolbar toolbar;
    String title,opdname;
    RecyclerView recycler_diff_opd;

    LinearLayoutManager linearLayoutManager;
    ArrayList<PopMedicalData> data = new ArrayList<PopMedicalData>();
    OpdListAdapter opdListAdapter;
    String type = null;
    String type_value = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opddetail);
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
        //init();
        Intent intent = getIntent();
        title = intent.getStringExtra("Title");
        opdname = intent.getStringExtra("OPD");

        if (intent.getStringExtra("type") != null)
        {
            type = intent.getStringExtra("type");
        }
        if (intent.getStringExtra("val") != null)
        {
            type_value =  intent.getStringExtra("val");
        }

        Log.i("Data=>",title + opdname + type + type_value );
        PhcUsersModel.getInstance(this);
        PhcSubCenterModel.getInstance(this);
        PopulationMedicalModel.getInstance(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(title);
        init();
    }

    private void init() {
        recycler_diff_opd = findViewById(R.id.recycler_diff_opd);

        if (type_value == null && type == null)
        {
            data = PopulationMedicalModel.getPerticularOpdPatientList(opdname, SharedPreference.get("CAMPID"));
        }
        else
        {
            data = PopulationMedicalModel.getPerticularOpdPatientListwithtype(type,type_value,opdname,SharedPreference.get("CAMPID"));
        }

        linearLayoutManager = new LinearLayoutManager(this);
        recycler_diff_opd.setLayoutManager(linearLayoutManager);
        opdListAdapter = new OpdListAdapter(this,data);
        recycler_diff_opd.setAdapter(opdListAdapter);
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