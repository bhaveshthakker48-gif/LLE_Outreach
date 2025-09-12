package org.impactindia.llemeddocket.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


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
import org.impactindia.llemeddocket.adapter.PopulationDetailsAdapter;
import org.impactindia.llemeddocket.orm.PopulationMedicalModel;
import org.impactindia.llemeddocket.pojo.PopMedicalData;
import org.impactindia.llemeddocket.util.SharedPreference;

import java.util.ArrayList;

public class EditDataActivity extends BaseActivity implements SearchView.OnQueryTextListener {

    Toolbar toolbar;
    RecyclerView recycler_alldata;
    LinearLayoutManager linearLayoutManager;
    ArrayList<PopMedicalData> data = new ArrayList<>();
    PopulationDetailsAdapter populationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);


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

        init();
    }
    public void init()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Population list");
        recycler_alldata = findViewById(R.id.recycler_alldata);
        PopulationMedicalModel.getInstance(this);
        PopulationMedicalModel.open();
        data = PopulationMedicalModel.getpadanPopulationdetails(SharedPreference.get("CAMPID"));
        Log.i("ckm=>data",data.size()+"");
        for (int i=0;i<data.size();i++)
        {
            Log.i("ckm=>USERID",data.get(i).getId()+"");
        }
        recycler_alldata = findViewById(R.id.recycler_alldata);
        linearLayoutManager = new LinearLayoutManager(this);
        recycler_alldata.setLayoutManager(linearLayoutManager);
        populationAdapter = new PopulationDetailsAdapter(this,data);
        recycler_alldata.setAdapter(populationAdapter);

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
        populationAdapter.getFilter().filter(s);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        populationAdapter.getFilter().filter(s);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, DashboardActivityOutPro.class);
        startActivity(i);
    }
}