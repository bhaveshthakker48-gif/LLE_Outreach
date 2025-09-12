//package org.impactindia.llemeddocket.ui.activity;
//
//
//public class MultipleCampActivity extends AppComapply plugin: 'com.android.application'
//
//        android {
//        compileSdkVersion 33
//
//        defaultConfig {
//        applicationId "org.impactindia.llemeddocket"
//        minSdkVersion 21
//        targetSdkVersion 33
//        versionCode 42
//        versionName "6.2.0"
//
//        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
//        }
//
//        lintOptions {
//        checkReleaseBuilds false
//        abortOnError false
//        }
//
//        buildTypes {
//        release {
//        minifyEnabled false
//        proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
//        }
//        }
//        }
//
//        dependencies {
//        implementation fileTree(dir: "libs", include: ["*.jar"])
//
//        // AndroidX replacements
//        implementation "androidx.appcompat:appcompat:1.6.1"
//        implementation "com.google.android.material:material:1.9.0"
//        implementation "androidx.constraintlayout:constraintlayout:2.1.4"
//
//        // Other libraries
//        implementation "com.google.code.gson:gson:2.8.9"
//        implementation "com.itextpdf:itextg:5.5.10"
//        implementation "com.android.volley:volley:1.2.1"
//        implementation "info.hoang8f:android-segmented:1.0.6"
//        implementation "com.jakewharton.threetenabp:threetenabp:1.3.0"
//        implementation "com.github.blackfizz:eazegraph:1.2.5l@aar"
//        implementation "com.nineoldandroids:library:2.4.0"
//        implementation "com.github.IntruderShanky:scatter-piechart:1.0.0"
//        implementation "com.loopj.android:android-async-http:1.4.9"
//        implementation "com.journeyapps:zxing-android-embedded:3.6.0"
//
//        // Unit testing
//        testImplementation "junit:junit:4.13.2"
//
//        // AndroidX testing
//        androidTestImplementation "androidx.test.ext:junit:1.1.5"
//        androidTestImplementation "androidx.test.espresso:espresso-core:3.5.1"
//        }
//        patActivity {
//
//    Toolbar toolbar;
//    SQLiteDatabase db;
//    RecyclerView recycler_activcamp;
//    LinearLayoutManager linearLayoutManager;
//    ArrayList<OutreachPlanData> data = new ArrayList<OutreachPlanData>();
//    ActiveCampAdapter adapter;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_multiple_camp);
//        init();
//        OutreachPlanModel.open();
//        data = OutreachPlanModel.getActiveOutreachCamp();
//        Log.i("ckm=>Data",data.size()+"");
//        adapter = new ActiveCampAdapter(getApplicationContext(),data);
//        recycler_activcamp.setAdapter(adapter);
//    }
//
//    private void init() {
//
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setTitle("Active Camp");
//        recycler_activcamp = findViewById(R.id.recycler_activcamp);
//        linearLayoutManager = new LinearLayoutManager(this);
//        recycler_activcamp.setLayoutManager(linearLayoutManager);
//
//        db = new DatabaseHelper(this).getReadableDatabase();
//        OutreachPlanModel.getInstance(getApplicationContext());
//    }
//
//
//
//
//}

package org.impactindia.llemeddocket.ui.activity;

import android.database.sqlite.SQLiteDatabase;
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

import org.impactindia.llemeddocket.DatabaseHelper;
import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.adapter.ActiveCampAdapter;
import org.impactindia.llemeddocket.orm.OutreachPlanModel;
import org.impactindia.llemeddocket.pojo.OutreachPlanData;

import java.util.ArrayList;

public class MultipleCampActivity extends AppCompatActivity {

    Toolbar toolbar;
    SQLiteDatabase db;
    RecyclerView recycler_activcamp;
    LinearLayoutManager linearLayoutManager;
    ArrayList<OutreachPlanData> data = new ArrayList<>();
    ActiveCampAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_camp);
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
        OutreachPlanModel.open();
        data = OutreachPlanModel.getActiveOutreachCamp();

        Log.i("ckm=>Data", data.size() + "");

        adapter = new ActiveCampAdapter(getApplicationContext(), data);
        recycler_activcamp.setAdapter(adapter);
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Active Camp");

        recycler_activcamp = findViewById(R.id.recycler_activcamp);
        linearLayoutManager = new LinearLayoutManager(this);
        recycler_activcamp.setLayoutManager(linearLayoutManager);

        db = new DatabaseHelper(this).getReadableDatabase();
        OutreachPlanModel.getInstance(getApplicationContext());
    }
}
