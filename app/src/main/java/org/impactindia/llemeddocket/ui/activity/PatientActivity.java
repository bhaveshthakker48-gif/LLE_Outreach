package org.impactindia.llemeddocket.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.ui.fragment.CampPatientListFrag;
import org.impactindia.llemeddocket.ui.fragment.PatientListFrag;
import org.impactindia.llemeddocket.ui.fragment.PatientRegFrag;

public class PatientActivity extends BaseActivity {

    private static final String PATIENT_REG_FRAG = "PatientRegFrag";
    private static final String PATIENT_LIST_FRAG = "PatientListFrag";
    private static final String CAMP_PATIENT_LIST_FRAG = "CampPatientListFrag";
    public static final String PATIENT_OPT = "patient_opt";

    private Bundle args;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

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

        args = getIntent().getExtras();
        index = args.getInt(PATIENT_OPT);

        switch (index) {
            case 0:
                gotoPatientRegistration();
                break;
            case 1:
                gotoPatientList();
                break;
            case 2:
                gotoCampPatientList();
                break;
            default:
                gotoPatientRegistration();
        }

    }

    public void gotoPatientRegistration() {
        FragmentManager fragMgr = getSupportFragmentManager();
        PatientRegFrag regFrag = (PatientRegFrag) fragMgr.findFragmentByTag(PATIENT_REG_FRAG);
        if (regFrag == null) {
            regFrag = new PatientRegFrag();
        }
        FragmentTransaction fragTrans = fragMgr.beginTransaction();
        fragTrans.add(R.id.content_frame, regFrag, PATIENT_REG_FRAG);
        fragTrans.commit();

    }

    public void gotoPatientList() {
        FragmentManager fragMgr = getSupportFragmentManager();
        PatientListFrag listFrag = (PatientListFrag) fragMgr.findFragmentByTag(PATIENT_LIST_FRAG);
        if (listFrag == null) {
            listFrag = new PatientListFrag();
        }
        FragmentTransaction fragTrans = fragMgr.beginTransaction();
        fragTrans.add(R.id.content_frame, listFrag, PATIENT_LIST_FRAG);
        fragTrans.commit();

    }

    public void gotoCampPatientList() {
        FragmentManager fragMgr = getSupportFragmentManager();
        CampPatientListFrag listFrag = (CampPatientListFrag) fragMgr.findFragmentByTag(CAMP_PATIENT_LIST_FRAG);
        if (listFrag == null) {
            listFrag = new CampPatientListFrag();
        }
        FragmentTransaction fragTrans = fragMgr.beginTransaction();
        fragTrans.add(R.id.content_frame, listFrag, CAMP_PATIENT_LIST_FRAG);
        fragTrans.commit();
    }
}
