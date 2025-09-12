package org.impactindia.llemeddocket.ui.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import org.impactindia.llemeddocket.DatabaseHelper;
import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.orm.PopulationMedicalModel;
import org.impactindia.llemeddocket.pojo.PopMedicalData;
import org.impactindia.llemeddocket.pojo.SubTag;
import org.impactindia.llemeddocket.util.SharedPreference;
import org.impactindia.llemeddocket.view.MultiSelectSpinner;
import org.impactindia.llemeddocket.view.MultiSelectionSpinner;
import org.threeten.bp.LocalDate;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.SocketHandler;

public class MedicalHistoryActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, MultiSelectSpinner.OnMultipleItemsSelectedListener, View.OnClickListener {

    Spinner spinner_diabetes, spinner_cataract, spinner_hyperten, spinner_epilepsy, spinner_OralProblems,
            spinner_tb, spinner_ischemicheartdisease, spinner_Stroke, spinner_cancer, spinner_congenital_abnormal,
            spinner_hearing_defect, spinner_gynaec, spinner_tobacco, spinner_smoker, spinner_alcohol;
    Toolbar toolbar;
    private LocalDate birthDate;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Button btnAddMedicalhistrory;
    private ArrayAdapter<String> adapter_diabetes, adapter_hypertension, adapter_cataract, adapter_epf, adapter_oralprblm, adapter_tb, adapter_ischemic, adapter_stroke, adapter_cancer,
            adapter_congennital_abnormal, adapter_hearing, adapter_earprblm, adapter_breastprblm, adapter_tobacco, adapter_smoker, adapter_alcohol;

    private String[] list_diabetes, list_hypertension, list_cataract, list_epf, list_oralprblm, list_tb, list_ischemic, list_stroke, list_cancer,
            list_congennital_abnormal, list_hearing, list_earprblm, list_breastprblm, list_tobacco, list_smoker, list_alcohol;

    EditText cataract_details, epilepsy_details,
            oralproblems_details, tb_details, ischemicheartdisease_details,
            Stroke_details, cancer_details,
            congenital_abnormal_details, hearingdefect_details, gynaec_details, earproblems_details;
    String multispinner, multispinnerfinal;

    LinearLayout llgynace;

    TextView edt_Cataract_diagnosed_date, Oral_Pro_diagnosed_date, edt_epf_diagnosed_date, tb_diagnosed_date, ischemicheartdisease_diagnosed_date,
            Stroke_diagnosed_date, cancer_diagnosed_date, congenital_diagnosed_date, hearingdefect_diagnosed_date, gynaec_diagnosed_date, earproblems_diagnosed_date;

    String str_edt_cataract_diagnos_date, str_oral_Pro_diagnosed_date, str_edt_epf_diagnosed_date, str_tb_diagnosed_date, str_ischemicheartdisease_diagnosed_date,
            str_Stroke_diagnosed_date, str_cancer_diagnosed_date, str_congenital_diagnosed_date, str_hearingdefect_diagnosed_date, str_gynaec_diagnosed_date;

    MultiSelectSpinner mulSelSpnearprblm;

    private SQLiteDatabase db;

    String editid = null;

//////    String[] array = {"None", "Apple", "Google", "Facebook", "Tesla", "IBM", "Twitter"};

    ArrayList<PopMedicalData> getdataforupdate = new ArrayList<PopMedicalData>();


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content),
                (view, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    Insets ime = insets.getInsets(WindowInsetsCompat.Type.ime());

                    // pick whichever is larger (IME when keyboard visible)
                    int bottom = Math.max(systemBars.bottom, ime.bottom);

                    view.setPadding(systemBars.left, systemBars.top, systemBars.right, bottom);
                    return insets;
                });

        SharedPreference.initialize(this);
        init();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            editid = bundle.getString("EDITID");
            Log.i("ckm=>ID", editid);
            getdataforupdate = PopulationMedicalModel.getdataforupdate(editid);
            Log.i("ckm=>sizeofarray", getdataforupdate.size() + "");
            btnAddMedicalhistrory.setText("Update");
            //Log.i("ckm=>data",getdataforupdate.get(0).getEarprob());
            if (!isEmpty(getdataforupdate.get(0).getEarprob())) {
                multispinnerfinal = getdataforupdate.get(0).getEarprob();
                List<String> result = Arrays.asList(getdataforupdate.get(0).getEarprob().split("\\s*,\\s*"));
                Log.i("List", result.size() + "");

                mulSelSpnearprblm.setItems(list_earprblm);
                mulSelSpnearprblm.hasNoneOption(true);
                mulSelSpnearprblm.setSelection(result);
                boolean contains = Arrays.asList(list_earprblm).contains("Other");
                if (contains) {
                    earproblems_details.setVisibility(View.VISIBLE);
                    earproblems_details.setText(getdataforupdate.get(0).getEarprobdetails());
                } else {
                    earproblems_details.setVisibility(View.GONE);
                }
            }

            if (!isEmpty(getdataforupdate.get(0).getDiabetes())) {
                adapter_diabetes = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, list_diabetes);
                spinner_diabetes.setAdapter(adapter_diabetes);
                int pos = adapter_diabetes.getPosition(getdataforupdate.get(0).getDiabetes());
                spinner_diabetes.setSelection(pos);
            }

            if (!isEmpty(getdataforupdate.get(0).getHypertension())) {
                adapter_hypertension = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, list_hypertension);
                spinner_hyperten.setAdapter(adapter_hypertension);
                int pos = adapter_hypertension.getPosition(getdataforupdate.get(0).getHypertension());
                spinner_hyperten.setSelection(pos);
            }

            if (!isEmpty(getdataforupdate.get(0).getCataract())) {
                adapter_cataract = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, list_cataract);
                spinner_cataract.setAdapter(adapter_cataract);
                int pos = adapter_cataract.getPosition(getdataforupdate.get(0).getCataract());
                spinner_cataract.setSelection(pos);

                if (getdataforupdate.get(0).getCataract().equals("Yes")) {
                    edt_Cataract_diagnosed_date.setVisibility(View.VISIBLE);
                    cataract_details.setVisibility(View.VISIBLE);
                    if (!isEmpty(getdataforupdate.get(0).getCartaractdiagno())) {
                        edt_Cataract_diagnosed_date.setText(getmmddyyyy(getdataforupdate.get(0).getCartaractdiagno()));
                    }
                    cataract_details.setText(getdataforupdate.get(0).getCataratdetails());
                }
            }

            if (!isEmpty(getdataforupdate.get(0).getGynaecprob())) {
                adapter_breastprblm = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, list_breastprblm);
                spinner_gynaec.setAdapter(adapter_breastprblm);
                int pos = adapter_breastprblm.getPosition(getdataforupdate.get(0).getGynaecprob());
                spinner_gynaec.setSelection(pos);

                if (getdataforupdate.get(0).getGynaecprob().equals("Yes") && SharedPreference.get("GENDER").equals("Female")) {
                    gynaec_diagnosed_date.setVisibility(View.VISIBLE);
                    gynaec_details.setVisibility(View.VISIBLE);
                    if (!isEmpty(getdataforupdate.get(0).getGynaecdiagnosed())) {
                        gynaec_diagnosed_date.setText(getmmddyyyy(getdataforupdate.get(0).getGynaecdiagnosed()));
                    }
                    gynaec_details.setText(getdataforupdate.get(0).getGynaecdetails());
                }
            }

            if (!isEmpty(getdataforupdate.get(0).getEpilepsy())) {
                adapter_epf = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, list_epf);
                spinner_epilepsy.setAdapter(adapter_epf);
                int pos = adapter_epf.getPosition(getdataforupdate.get(0).getEpilepsy());
                spinner_epilepsy.setSelection(pos);

                if (getdataforupdate.get(0).getEpilepsy().equals("Yes")) {
                    edt_epf_diagnosed_date.setVisibility(View.VISIBLE);
                    epilepsy_details.setVisibility(View.VISIBLE);
                    if (!isEmpty(getdataforupdate.get(0).getEpilepsydiagnosed())) {
                        edt_epf_diagnosed_date.setText(getmmddyyyy(getdataforupdate.get(0).getEpilepsydiagnosed()));
                    }
                    epilepsy_details.setText(getdataforupdate.get(0).getEpilepsydetails());

                }
            }

            if (!isEmpty(getdataforupdate.get(0).getOralproblem())) {
                adapter_oralprblm = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, list_oralprblm);
                spinner_OralProblems.setAdapter(adapter_oralprblm);
                int pos = adapter_oralprblm.getPosition(getdataforupdate.get(0).getOralproblem());
                spinner_OralProblems.setSelection(pos);

                if (getdataforupdate.get(0).getOralproblem().equals("Yes")) {
                    oralproblems_details.setVisibility(View.VISIBLE);
                    Oral_Pro_diagnosed_date.setVisibility(View.VISIBLE);
                    oralproblems_details.setText(getdataforupdate.get(0).getOralprodetails());
                    if (!isEmpty(getdataforupdate.get(0).getOralproblemdiagnoesd())) {
                        Oral_Pro_diagnosed_date.setText(getmmddyyyy(getdataforupdate.get(0).getOralproblemdiagnoesd()));
                    }
                }
            }

            if (!isEmpty(getdataforupdate.get(0).getTb())) {
                adapter_tb = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, list_tb);
                spinner_tb.setAdapter(adapter_tb);
                int pos = adapter_tb.getPosition(getdataforupdate.get(0).getTb());
                spinner_tb.setSelection(pos);
                if (getdataforupdate.get(0).getTb().equals("Yes")) {
                    tb_diagnosed_date.setVisibility(View.VISIBLE);
                    tb_details.setVisibility(View.VISIBLE);
                    tb_details.setText(getdataforupdate.get(0).getTbdetails());
                    if (!isEmpty(getdataforupdate.get(0).getTbdiagnosed())) {
                        tb_diagnosed_date.setText(getmmddyyyy(getdataforupdate.get(0).getTbdiagnosed()));
                    }
                }
            }

            if (!isEmpty(getdataforupdate.get(0).getIschemicheartdisease())) {
                adapter_ischemic = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, list_ischemic);
                spinner_ischemicheartdisease.setAdapter(adapter_ischemic);
                int pos = adapter_ischemic.getPosition(getdataforupdate.get(0).getIschemicheartdisease());
                spinner_ischemicheartdisease.setSelection(pos);
                if (getdataforupdate.get(0).getIschemicheartdisease().equals("Yes")) {
                    ischemicheartdisease_diagnosed_date.setVisibility(View.VISIBLE);
                    ischemicheartdisease_details.setVisibility(View.VISIBLE);
                    ischemicheartdisease_details.setText(getdataforupdate.get(0).getIschemicheadisdetails());
                    if (!isEmpty(getdataforupdate.get(0).getIschemicheadisddiagnosed())) {
                        ischemicheartdisease_diagnosed_date.setText(getmmddyyyy(getdataforupdate.get(0).getIschemicheadisddiagnosed()));
                    }

                }
            }

            if (!isEmpty(getdataforupdate.get(0).getStroke())) {
                adapter_stroke = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, list_stroke);
                spinner_Stroke.setAdapter(adapter_stroke);
                int pos = adapter_stroke.getPosition(getdataforupdate.get(0).getStroke());
                spinner_Stroke.setSelection(pos);
                if (getdataforupdate.get(0).getStroke().equals("Yes")) {
                    Stroke_diagnosed_date.setVisibility(View.VISIBLE);
                    Stroke_details.setVisibility(View.VISIBLE);
                    Stroke_details.setText(getdataforupdate.get(0).getStrokedetails());
                    if (!isEmpty(getdataforupdate.get(0).getStrokediagnosed())) {
                        Stroke_diagnosed_date.setText(getmmddyyyy(getdataforupdate.get(0).getStrokediagnosed()));
                    }
                }
            }

            if (!isEmpty(getdataforupdate.get(0).getCancer())) {
                adapter_cancer = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, list_cancer);
                spinner_cancer.setAdapter(adapter_cancer);
                int pos = adapter_cancer.getPosition(getdataforupdate.get(0).getCancer());
                spinner_cancer.setSelection(pos);
                if (getdataforupdate.get(0).getCancer().equals("Yes")) {
                    cancer_diagnosed_date.setVisibility(View.VISIBLE);
                    cancer_details.setVisibility(View.VISIBLE);
                    if (!isEmpty(getdataforupdate.get(0).getCancerdiagnosed())) {
                        cancer_diagnosed_date.setText(getmmddyyyy(getdataforupdate.get(0).getCancerdiagnosed()));
                    }
                    cancer_details.setText(getdataforupdate.get(0).getCancerdetails());

                }
            }

            if (!isEmpty(getdataforupdate.get(0).getCogenitalabnormalities())) {
                adapter_congennital_abnormal = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, list_congennital_abnormal);
                spinner_congenital_abnormal.setAdapter(adapter_congennital_abnormal);
                int pos = adapter_congennital_abnormal.getPosition(getdataforupdate.get(0).getCogenitalabnormalities());
                spinner_congenital_abnormal.setSelection(pos);
            }

            if (!isEmpty(getdataforupdate.get(0).getHearingdefect())) {
                adapter_hearing = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, list_hearing);
                spinner_hearing_defect.setAdapter(adapter_hearing);
                int pos = adapter_hearing.getPosition(getdataforupdate.get(0).getHearingdefect());
                spinner_hearing_defect.setSelection(pos);
                if (getdataforupdate.get(0).getHearingdefect().equals("Yes")) {
                    hearingdefect_diagnosed_date.setVisibility(View.VISIBLE);
                    hearingdefect_details.setVisibility(View.VISIBLE);
                    if (!isEmpty(getdataforupdate.get(0).getHearingdefectdiagnosed())) {
                        hearingdefect_diagnosed_date.setText(getmmddyyyy(getdataforupdate.get(0).getHearingdefectdiagnosed()));
                    }
                    hearingdefect_details.setText(getdataforupdate.get(0).getHearingdefectdetails());
                }
            }
            if (!isEmpty(getdataforupdate.get(0).getTobacco())) {
                adapter_tobacco = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, list_tobacco);
                spinner_tobacco.setAdapter(adapter_tobacco);
                int pos = adapter_tobacco.getPosition(getdataforupdate.get(0).getTobacco());
                spinner_tobacco.setSelection(pos);
            }

            if (!isEmpty(getdataforupdate.get(0).getSmoker())) {
                adapter_smoker = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, list_smoker);
                spinner_smoker.setAdapter(adapter_smoker);
                int pos = adapter_smoker.getPosition(getdataforupdate.get(0).getSmoker());
                spinner_smoker.setSelection(pos);
            }

            if (!isEmpty(getdataforupdate.get(0).getAlcohol())) {
                adapter_alcohol = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, list_alcohol);
                spinner_alcohol.setAdapter(adapter_alcohol);
                int pos = adapter_alcohol.getPosition(getdataforupdate.get(0).getAlcohol());
                spinner_alcohol.setSelection(pos);
            }


            //  mulSelSpnearprblm.setListener(this);

        }
        //edt_Cataract_diagnosed_date.setOnClickListener(new On);

        if (SharedPreference.get("GENDER").equals("Female")) {
            llgynace.setVisibility(View.VISIBLE);
        } else {
            llgynace.setVisibility(View.GONE);
        }


    }

    public void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Medical History");

        db = new DatabaseHelper(this).getWritableDatabase();
        PopulationMedicalModel.getInstance(this);

        edt_Cataract_diagnosed_date = findViewById(R.id.edt_Cataract_diagnosed_date);
        edt_Cataract_diagnosed_date.setOnClickListener(this);
        cataract_details = findViewById(R.id.cataract_details);

        llgynace = findViewById(R.id.llgynace1);


        btnAddMedicalhistrory = findViewById(R.id.btnAddMedicalhistrory);

        edt_epf_diagnosed_date = findViewById(R.id.edt_epf_diagnosed_date);
        edt_epf_diagnosed_date.setOnClickListener(this);
        epilepsy_details = findViewById(R.id.epilepsy_details);

        Oral_Pro_diagnosed_date = findViewById(R.id.Oral_Pro_diagnosed_date);
        Oral_Pro_diagnosed_date.setOnClickListener(this);
        oralproblems_details = findViewById(R.id.oralproblems_details);

        tb_diagnosed_date = findViewById(R.id.tb_diagnosed_date);
        tb_diagnosed_date.setOnClickListener(this);
        tb_details = findViewById(R.id.tb_details);

        ischemicheartdisease_diagnosed_date = findViewById(R.id.ischemicheartdisease_diagnosed_date);
        ischemicheartdisease_diagnosed_date.setOnClickListener(this);
        ischemicheartdisease_details = findViewById(R.id.ischemicheartdisease_details);

        Stroke_diagnosed_date = findViewById(R.id.Stroke_diagnosed_date);
        Stroke_diagnosed_date.setOnClickListener(this);
        Stroke_details = findViewById(R.id.Stroke_details);

        cancer_diagnosed_date = findViewById(R.id.cancer_diagnosed_date);
        cancer_diagnosed_date.setOnClickListener(this);
        cancer_details = findViewById(R.id.cancer_details);

        congenital_diagnosed_date = findViewById(R.id.congenital_diagnosed_date);
        congenital_diagnosed_date.setOnClickListener(this);
        congenital_abnormal_details = findViewById(R.id.congenital_abnormal_details);

        hearingdefect_diagnosed_date = findViewById(R.id.hearingdefect_diagnosed_date);
        hearingdefect_diagnosed_date.setOnClickListener(this);
        hearingdefect_details = findViewById(R.id.hearingdefect_details);

        gynaec_diagnosed_date = findViewById(R.id.gynaec_diagnosed_date);
        gynaec_diagnosed_date.setOnClickListener(this);
        gynaec_details = findViewById(R.id.gynaec_details);

        earproblems_diagnosed_date = findViewById(R.id.earproblems_diagnosed_date);
        earproblems_details = findViewById(R.id.earproblems_details);


        spinnerslist();
        bindadapter();
        initspinners();

        mulSelSpnearprblm = findViewById(R.id.mulSelSpnearprblm);
        mulSelSpnearprblm.setItems(list_earprblm);
        mulSelSpnearprblm.hasNoneOption(true);
        mulSelSpnearprblm.setSelection(new int[]{0});
        mulSelSpnearprblm.setListener(this);


        btnAddMedicalhistrory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (spinner_diabetes.getSelectedItemPosition() == 0) {
                    shortToast("Please select diabetes");
                } else if (spinner_hyperten.getSelectedItemPosition() == 0) {
                    shortToast("Please select Hypertension");
                } else if (spinner_cataract.getSelectedItemPosition() == 0) {
                    shortToast("Please select cataract");
                } else if (spinner_epilepsy.getSelectedItemPosition() == 0) {
                    shortToast("Please select epilepsy");
                } else if (spinner_OralProblems.getSelectedItemPosition() == 0) {
                    shortToast("Please select oral problem");
                } else if (spinner_tb.getSelectedItemPosition() == 0) {
                    shortToast("Please select Tb");
                } else if (spinner_ischemicheartdisease.getSelectedItemPosition() == 0) {
                    shortToast("Please select ischemicheart disease");
                } else if (spinner_Stroke.getSelectedItemPosition() == 0) {
                    shortToast("Please select stroke");
                } else if (spinner_cancer.getSelectedItemPosition() == 0) {
                    shortToast("Please select cancer");
                }
            /*    else if (spinner_hearing_defect.getSelectedItemPosition() == 0)
                {
                    shortToast("Please select hearing defect");
                }*/
                else if (SharedPreference.get("GENDER").equals("Female") && spinner_gynaec.getSelectedItemPosition() == 0) {
                    shortToast("Please select breast problem");
                } else if (spinner_tobacco.getSelectedItemPosition() == 0) {
                    shortToast("Please select tabacco");
                } else if (spinner_smoker.getSelectedItemPosition() == 0) {
                    shortToast("please select smoker");
                } else if (spinner_alcohol.getSelectedItemPosition() == 0) {
                    shortToast("please select alcohol");
                } else {

                    SharedPreference.save("LLECATARACT", spinner_cataract.getSelectedItem().toString());
                    SharedPreference.save("LLEEPF", spinner_epilepsy.getSelectedItem().toString());
                    SharedPreference.save("LLEORALPRBM", spinner_OralProblems.getSelectedItem().toString());
                    SharedPreference.save("LLECONGITAL", spinner_congenital_abnormal.getSelectedItem().toString());
                    // SharedPreference.save("LLEHEARINGDEF",spinner_hearing_defect.getSelectedItem().toString());
                    SharedPreference.save("LLEEARPRBLM", multispinnerfinal);

                    if (SharedPreference.get("GENDER").equals("Female")) {
                        SharedPreference.save("LLEGYNAEC", spinner_gynaec.getSelectedItem().toString());
                    }
                    SharedPreference.save("LLECANCER", spinner_cancer.getSelectedItem().toString());


                    if (!isEmpty(edt_Cataract_diagnosed_date.getText().toString())) {
                        str_edt_cataract_diagnos_date = getyyyymmdd(edt_Cataract_diagnosed_date.getText().toString());
                        //                      Log.i("ckm=>date",str_edt_cataract_diagnos_date);
                    }
                    if (!isEmpty(Oral_Pro_diagnosed_date.getText().toString())) {
                        str_oral_Pro_diagnosed_date = getyyyymmdd(Oral_Pro_diagnosed_date.getText().toString());
                        //                    Log.i("ckm=>date",str_edt_cataract_diagnos_date);
                    }
                    if (!isEmpty(edt_epf_diagnosed_date.getText().toString())) {
                        str_edt_epf_diagnosed_date = getyyyymmdd(edt_epf_diagnosed_date.getText().toString());
                        //                  Log.i("ckm=>date",str_edt_cataract_diagnos_date);
                    }
                    if (!isEmpty(tb_diagnosed_date.getText().toString())) {
                        str_tb_diagnosed_date = getyyyymmdd(tb_diagnosed_date.getText().toString());
                        //                Log.i("ckm=>date",str_edt_cataract_diagnos_date);
                    }
                    if (!isEmpty(ischemicheartdisease_diagnosed_date.getText().toString())) {
                        str_ischemicheartdisease_diagnosed_date = getyyyymmdd(ischemicheartdisease_diagnosed_date.getText().toString());
                        //              Log.i("ckm=>date",str_edt_cataract_diagnos_date);
                    }
                    if (!isEmpty(Stroke_diagnosed_date.getText().toString())) {
                        str_Stroke_diagnosed_date = getyyyymmdd(Stroke_diagnosed_date.getText().toString());
                        //            Log.i("ckm=>date",str_edt_cataract_diagnos_date);
                    }
                    if (!isEmpty(cancer_diagnosed_date.getText().toString())) {
                        str_cancer_diagnosed_date = getyyyymmdd(cancer_diagnosed_date.getText().toString());
//                        Log.i("ckm=>date",str_edt_cataract_diagnos_date);
                    }
                    if (!isEmpty(congenital_diagnosed_date.getText().toString())) {
                        str_congenital_diagnosed_date = getyyyymmdd(congenital_diagnosed_date.getText().toString());
                        //          Log.i("ckm=>date",str_edt_cataract_diagnos_date);
                    }
                    if (!isEmpty(hearingdefect_diagnosed_date.getText().toString())) {
                        str_hearingdefect_diagnosed_date = getyyyymmdd(hearingdefect_diagnosed_date.getText().toString());
                        //        Log.i("ckm=>date",str_edt_cataract_diagnos_date);
                    }
                    if (!isEmpty(gynaec_diagnosed_date.getText().toString())) {
                        str_gynaec_diagnosed_date = getyyyymmdd(gynaec_diagnosed_date.getText().toString());
                        //      Log.i("ckm=>date",str_edt_cataract_diagnos_date);
                    }


                    PopMedicalData pop = new PopMedicalData();
                    pop.setDiabetes(spinner_diabetes.getSelectedItem().toString());
                    pop.setHypertension(spinner_hyperten.getSelectedItem().toString());
                    pop.setCataract(spinner_cataract.getSelectedItem().toString());
                    // Log.i("ccc=>catractdat",str_edt_cataract_diagnos_date);
                    pop.setCartaractdiagno(isEmpty(str_edt_cataract_diagnos_date) ? null : str_edt_cataract_diagnos_date);
                    pop.setCataratdetails(isEmpty(cataract_details.getText().toString()) ? null : cataract_details.getText().toString());
                    pop.setEpilepsy(spinner_epilepsy.getSelectedItem().toString());
                    pop.setEpilepsydiagnosed(isEmpty(str_edt_epf_diagnosed_date) ? null : str_edt_epf_diagnosed_date);
                    pop.setEpilepsydetails(isEmpty(epilepsy_details.getText().toString()) ? null : epilepsy_details.getText().toString());
                    pop.setOralproblem(spinner_OralProblems.getSelectedItem().toString());
                    pop.setOralproblemdiagnoesd(isEmpty(str_oral_Pro_diagnosed_date) ? null : str_oral_Pro_diagnosed_date);
                    pop.setOralprodetails(isEmpty(oralproblems_details.getText().toString()) ? null : oralproblems_details.getText().toString());
                    pop.setTb(spinner_tb.getSelectedItem().toString());
                    pop.setTbdetails(isEmpty(tb_details.getText().toString()) ? null : tb_details.getText().toString());
                    pop.setTbdiagnosed(isEmpty(str_tb_diagnosed_date) ? null : str_tb_diagnosed_date);
                    pop.setIschemicheartdisease(spinner_ischemicheartdisease.getSelectedItem().toString());
                    pop.setIschemicheadisddiagnosed(isEmpty(str_ischemicheartdisease_diagnosed_date) ? null : str_ischemicheartdisease_diagnosed_date);
                    pop.setIschemicheadisdetails(isEmpty(ischemicheartdisease_details.getText().toString()) ? null : ischemicheartdisease_details.getText().toString());
                    pop.setStroke(spinner_Stroke.getSelectedItem().toString());
                    pop.setStrokediagnosed(isEmpty(str_Stroke_diagnosed_date) ? null : str_Stroke_diagnosed_date);
                    pop.setStrokedetails(isEmpty(Stroke_details.getText().toString()) ? null : Stroke_details.getText().toString());
                    pop.setEarprob(multispinnerfinal);
                    pop.setCancer(spinner_cancer.getSelectedItem().toString());
                    pop.setCancerdiagnosed(isEmpty(str_cancer_diagnosed_date) ? null : str_cancer_diagnosed_date);
                    pop.setCancerdetails(isEmpty(cancer_details.getText().toString()) ? null : cancer_details.getText().toString());
                    pop.setCogenitalabnormalities(spinner_congenital_abnormal.getSelectedItem().toString().equals("Select") ? "" : spinner_congenital_abnormal.getSelectedItem().toString());
                    pop.setHearingdefect(null);
                    pop.setHearingdefectdetails(isEmpty(hearingdefect_details.getText().toString()) ? null : hearingdefect_details.getText().toString());
                    pop.setHearingdefectdiagnosed(isEmpty(str_hearingdefect_diagnosed_date) ? null : str_hearingdefect_diagnosed_date);

                    pop.setGynaecprob(spinner_gynaec.getSelectedItem().toString().equals("Select") ? null : spinner_gynaec.getSelectedItem().toString());
                    pop.setGynaecdiagnosed(isEmpty(str_gynaec_diagnosed_date) ? null : str_gynaec_diagnosed_date);
                    pop.setGynaecdetails(isEmpty(gynaec_details.getText().toString()) ? null : gynaec_details.getText().toString());
                    pop.setTobacco(spinner_tobacco.getSelectedItem().toString());
                    pop.setSmoker(spinner_smoker.getSelectedItem().toString());
                    pop.setAlcohol(spinner_alcohol.getSelectedItem().toString());
                    pop.setEarprobdetails(earproblems_details.getText().toString());
                    PopulationMedicalModel.UpdateMedicalData(SharedPreference.get("HOUSEHOLDID"), pop);

                    Intent i = new Intent(MedicalHistoryActivity.this, CallToLLEActivity.class);

                    if (!isEmpty(editid) && editid != null) {
                        // PopulationMedicalModel.UpdateMedicalData(editid,pop);
                        Bundle bundle = new Bundle();
                        bundle.putString("EDITID", editid);
                        i.putExtras(bundle);
                        shortToast("Data updated successfully");
                        startActivity(i);
                    } else {
                        shortToast("Medical history added successfully");
                        startActivity(i);
                    }
                }

            }
        });

    }


    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {
        Toast.makeText(this.getApplicationContext(), "Selected problems" + strings, Toast.LENGTH_LONG).show();

        multispinner = String.valueOf(strings);
        Log.i("ckm=>MUL", multispinner);
        multispinner = multispinner.substring(1);
        Log.i("ckm=>MUL1", multispinner);
        multispinnerfinal = multispinner.substring(0, multispinner.length() - 1);
        Log.i("ckm=>multispinner", multispinnerfinal);
        if (multispinnerfinal.toLowerCase().indexOf(("Other").toLowerCase()) != -1) {
            Log.i("foundData", "FoundOther");
            earproblems_details.setVisibility(View.VISIBLE);
        } else {
            earproblems_details.setVisibility(View.GONE);
        }
    }

    public String getyyyymmdd(String mmddyyyy) {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd-MM-yyyy").parse(mmddyyyy);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateString2 = new SimpleDateFormat("yyyy-MM-dd").format(date);
        //  System.out.println(dateString2); // 2011-
        //Log.i("ckm=>convertedDate",dateString2);
        return dateString2;
    }

    private void initspinners() {
        spinner_diabetes = findViewById(R.id.spinner_diabetes);
        spinner_diabetes.setAdapter(adapter_diabetes);

        spinner_hyperten = findViewById(R.id.spinner_hyperten);
        spinner_hyperten.setAdapter(adapter_hypertension);

        spinner_epilepsy = findViewById(R.id.spinner_epilepsy);
        spinner_epilepsy.setAdapter(adapter_epf);

        spinner_OralProblems = findViewById(R.id.spinner_OralProblems);
        spinner_OralProblems.setAdapter(adapter_oralprblm);

        spinner_tb = findViewById(R.id.spinner_tb);
        spinner_tb.setAdapter(adapter_tb);

        spinner_ischemicheartdisease = findViewById(R.id.spinner_ischemicheartdisease);
        spinner_ischemicheartdisease.setAdapter(adapter_ischemic);

        spinner_Stroke = findViewById(R.id.spinner_Stroke);
        spinner_Stroke.setAdapter(adapter_stroke);

        spinner_cancer = findViewById(R.id.spinner_cancer);
        spinner_cancer.setAdapter(adapter_cancer);

        spinner_congenital_abnormal = findViewById(R.id.spinner_congenital_abnormal);
        spinner_congenital_abnormal.setAdapter(adapter_congennital_abnormal);

        spinner_hearing_defect = findViewById(R.id.spinner_hearing_defect);
        spinner_hearing_defect.setAdapter(adapter_hearing);

        spinner_tobacco = findViewById(R.id.spinner_tobacco);
        spinner_tobacco.setAdapter(adapter_tobacco);

        spinner_gynaec = findViewById(R.id.spinner_gynaec);
        spinner_gynaec.setAdapter(adapter_breastprblm);

        spinner_smoker = findViewById(R.id.spinner_smoker);
        spinner_smoker.setAdapter(adapter_smoker);

        spinner_alcohol = findViewById(R.id.spinner_alcohol);
        spinner_alcohol.setAdapter(adapter_alcohol);

        spinner_cataract = findViewById(R.id.spinner_cataract);
        spinner_cataract.setAdapter(adapter_cataract);

        spinner_hyperten.setOnItemSelectedListener(this);
        spinner_diabetes.setOnItemSelectedListener(this);
        spinner_cataract.setOnItemSelectedListener(this);
        spinner_epilepsy.setOnItemSelectedListener(this);
        spinner_OralProblems.setOnItemSelectedListener(this);
        spinner_tb.setOnItemSelectedListener(this);
        spinner_ischemicheartdisease.setOnItemSelectedListener(this);
        spinner_Stroke.setOnItemSelectedListener(this);
        spinner_cancer.setOnItemSelectedListener(this);
        spinner_congenital_abnormal.setOnItemSelectedListener(this);
        spinner_hearing_defect.setOnItemSelectedListener(this);
        spinner_gynaec.setOnItemSelectedListener(this);
        spinner_tobacco.setOnItemSelectedListener(this);
        spinner_smoker.setOnItemSelectedListener(this);
        spinner_alcohol.setOnItemSelectedListener(this);

    }

    private void bindadapter() {
        adapter_diabetes = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, list_diabetes);
        adapter_hypertension = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, list_hypertension);
        adapter_cataract = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, list_cataract);
        adapter_epf = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, list_epf);
        adapter_oralprblm = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, list_oralprblm);
        adapter_tb = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, list_tb);
        adapter_ischemic = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, list_ischemic);
        adapter_stroke = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, list_stroke);
        adapter_cancer = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, list_cancer);
        adapter_congennital_abnormal = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, list_congennital_abnormal);
        adapter_hearing = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, list_hearing);
        adapter_earprblm = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, list_earprblm);
        adapter_breastprblm = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, list_breastprblm);
        adapter_tobacco = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, list_tobacco);
        adapter_smoker = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, list_smoker);
        adapter_alcohol = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, list_alcohol);
    }

    private void spinnerslist() {
        list_diabetes = getResources().getStringArray(R.array.diabetes);
        list_hypertension = getResources().getStringArray(R.array.hypertension);
        list_cataract = getResources().getStringArray(R.array.all_inone_new);
        list_epf = getResources().getStringArray(R.array.all_inone_new);
        list_oralprblm = getResources().getStringArray(R.array.all_inone);
        list_tb = getResources().getStringArray(R.array.all_inone_new);
        list_ischemic = getResources().getStringArray(R.array.all_inone_new);
        list_stroke = getResources().getStringArray(R.array.all_inone_new);
        list_cancer = getResources().getStringArray(R.array.cancer);
        list_congennital_abnormal = getResources().getStringArray(R.array.congital_abnormal);
        list_hearing = getResources().getStringArray(R.array.all_inone);
        list_earprblm = getResources().getStringArray(R.array.ear_problm);
        list_breastprblm = getResources().getStringArray(R.array.all_inone_new);
        list_tobacco = getResources().getStringArray(R.array.tobacco);
        list_smoker = getResources().getStringArray(R.array.smoker);
        list_alcohol = getResources().getStringArray(R.array.alchohol);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int viewId = parent.getId();
        if (viewId == R.id.spinner_diabetes) {
            String diabet = parent.getItemAtPosition(position).toString();
            Log.i("ckm=>diabates", diabet);
        } else if (viewId == R.id.spinner_hyperten) {
            String hyperten = parent.getItemAtPosition(position).toString();
            Log.i("ckm=>hyperten", hyperten);
        } else if (viewId == R.id.spinner_cataract) {
            String cataract = parent.getItemAtPosition(position).toString();
            Log.i("ckm=>cataract", cataract);
            if (cataract.equals("Yes")) {
                cataract_details.setVisibility(View.VISIBLE);
                edt_Cataract_diagnosed_date.setVisibility(View.VISIBLE);
            } else {
                cataract_details.setVisibility(View.GONE);
                edt_Cataract_diagnosed_date.setVisibility(View.GONE);
            }
        } else if (viewId == R.id.spinner_epilepsy) {
            String epilepsy = parent.getItemAtPosition(position).toString();
            if (epilepsy.equals("Yes")) {
                epilepsy_details.setVisibility(View.VISIBLE);
                edt_epf_diagnosed_date.setVisibility(View.VISIBLE);
            } else {
                epilepsy_details.setVisibility(View.GONE);
                edt_epf_diagnosed_date.setVisibility(View.GONE);
            }
        } else if (viewId == R.id.spinner_OralProblems) {
            String OralProblems = parent.getItemAtPosition(position).toString();
            if (OralProblems.equals("Yes")) {
                oralproblems_details.setVisibility(View.VISIBLE);
                Oral_Pro_diagnosed_date.setVisibility(View.VISIBLE);
            } else {
                oralproblems_details.setVisibility(View.GONE);
                Oral_Pro_diagnosed_date.setVisibility(View.GONE);
            }
        } else if (viewId == R.id.spinner_tb) {
            String tb = parent.getItemAtPosition(position).toString();
            if (tb.equals("Yes")) {
                tb_details.setVisibility(View.VISIBLE);
                tb_diagnosed_date.setVisibility(View.VISIBLE);
            } else {
                tb_details.setVisibility(View.GONE);
                tb_diagnosed_date.setVisibility(View.GONE);
            }
        } else if (viewId == R.id.spinner_ischemicheartdisease) {
            String ischemicheartdisease = parent.getItemAtPosition(position).toString();
            if (ischemicheartdisease.equals("Yes")) {
                ischemicheartdisease_details.setVisibility(View.VISIBLE);
                ischemicheartdisease_diagnosed_date.setVisibility(View.VISIBLE);
            } else {
                ischemicheartdisease_details.setVisibility(View.GONE);
                ischemicheartdisease_diagnosed_date.setVisibility(View.GONE);
            }
        } else if (viewId == R.id.spinner_Stroke) {
            String Stroke = parent.getItemAtPosition(position).toString();
            if (Stroke.equals("Yes")) {
                Stroke_details.setVisibility(View.VISIBLE);
                Stroke_diagnosed_date.setVisibility(View.VISIBLE);
            } else {
                Stroke_details.setVisibility(View.GONE);
                Stroke_diagnosed_date.setVisibility(View.GONE);
            }
        } else if (viewId == R.id.spinner_cancer) {
            String cancer = parent.getItemAtPosition(position).toString();
            if (cancer.equals("Oral") || cancer.equals("Others")) {
                cancer_details.setVisibility(View.VISIBLE);
                cancer_diagnosed_date.setVisibility(View.VISIBLE);
            } else {
                cancer_details.setVisibility(View.GONE);
                cancer_diagnosed_date.setVisibility(View.GONE);
            }
        } else if (viewId == R.id.spinner_congenital_abnormal) {
            String congenital_abnormal = parent.getItemAtPosition(position).toString();
            if (congenital_abnormal.equals("Others")) {
                congenital_abnormal_details.setVisibility(View.VISIBLE);
                congenital_diagnosed_date.setVisibility(View.VISIBLE);
            } else {
                congenital_abnormal_details.setVisibility(View.GONE);
                congenital_diagnosed_date.setVisibility(View.GONE);
            }
        } else if (viewId == R.id.spinner_gynaec) {
            String gynaec = parent.getItemAtPosition(position).toString();
            Log.i("ckm=>gynac", gynaec);
            if (gynaec.equals("Yes") && SharedPreference.get("GENDER").equals("Female")) {
                gynaec_details.setVisibility(View.VISIBLE);
                gynaec_diagnosed_date.setVisibility(View.VISIBLE);
            } else {
                gynaec_details.setVisibility(View.GONE);
                gynaec_diagnosed_date.setVisibility(View.GONE);
            }
        } else if (viewId == R.id.spinner_tobacco) {
            String tobacco = parent.getItemAtPosition(position).toString();
        } else if (viewId == R.id.spinner_smoker) {
            String smoker = parent.getItemAtPosition(position).toString();
        } else if (viewId == R.id.spinner_alcohol) {
            String alcohol = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.edt_Cataract_diagnosed_date) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            monthOfYear += 1;
                            String mt, dy;
                            if (monthOfYear < 10)
                                mt = "0" + monthOfYear; //if month less than 10 then ad 0 before month
                            else mt = String.valueOf(monthOfYear);

                            if (dayOfMonth < 10)
                                dy = "0" + dayOfMonth;
                            else dy = String.valueOf(dayOfMonth);
                            edt_Cataract_diagnosed_date.setText(dy + "-" + mt + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        } else if (viewId == R.id.edt_epf_diagnosed_date) {

            final Calendar c1 = Calendar.getInstance();
            mYear = c1.get(Calendar.YEAR);
            mMonth = c1.get(Calendar.MONTH);
            mDay = c1.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog1 = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {


                            monthOfYear += 1;
                            String mt, dy;
                            if (monthOfYear < 10)
                                mt = "0" + monthOfYear; //if month less than 10 then ad 0 before month
                            else mt = String.valueOf(monthOfYear);

                            if (dayOfMonth < 10)
                                dy = "0" + dayOfMonth;
                            else dy = String.valueOf(dayOfMonth);
                            edt_epf_diagnosed_date.setText(dy + "-" + mt + "-" + year);
                            //  edt_epf_diagnosed_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog1.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog1.show();
        } else if (viewId == R.id.Oral_Pro_diagnosed_date) {
            final Calendar c2 = Calendar.getInstance();
            mYear = c2.get(Calendar.YEAR);
            mMonth = c2.get(Calendar.MONTH);
            mDay = c2.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog2 = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            monthOfYear += 1;
                            String mt, dy;
                            if (monthOfYear < 10)
                                mt = "0" + monthOfYear; //if month less than 10 then ad 0 before month
                            else mt = String.valueOf(monthOfYear);

                            if (dayOfMonth < 10)
                                dy = "0" + dayOfMonth;
                            else dy = String.valueOf(dayOfMonth);
                            Oral_Pro_diagnosed_date.setText(dy + "-" + mt + "-" + year);
                            //  Oral_Pro_diagnosed_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog2.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog2.show();
        } else if (viewId == R.id.tb_diagnosed_date) {
            final Calendar c3 = Calendar.getInstance();
            mYear = c3.get(Calendar.YEAR);
            mMonth = c3.get(Calendar.MONTH);
            mDay = c3.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog3 = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            monthOfYear += 1;
                            String mt, dy;
                            if (monthOfYear < 10)
                                mt = "0" + monthOfYear; //if month less than 10 then ad 0 before month
                            else mt = String.valueOf(monthOfYear);

                            if (dayOfMonth < 10)
                                dy = "0" + dayOfMonth;
                            else dy = String.valueOf(dayOfMonth);
                            tb_diagnosed_date.setText(dy + "-" + mt + "-" + year);
                            // tb_diagnosed_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog3.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog3.show();

        } else if (viewId == R.id.ischemicheartdisease_diagnosed_date) {
            final Calendar c4 = Calendar.getInstance();
            mYear = c4.get(Calendar.YEAR);
            mMonth = c4.get(Calendar.MONTH);
            mDay = c4.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog4 = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            monthOfYear += 1;
                            String mt, dy;
                            if (monthOfYear < 10)
                                mt = "0" + monthOfYear; //if month less than 10 then ad 0 before month
                            else mt = String.valueOf(monthOfYear);

                            if (dayOfMonth < 10)
                                dy = "0" + dayOfMonth;
                            else dy = String.valueOf(dayOfMonth);
                            ischemicheartdisease_diagnosed_date.setText(dy + "-" + mt + "-" + year);
                            //ischemicheartdisease_diagnosed_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog4.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog4.show();
        } else if (viewId == R.id.Stroke_diagnosed_date) {
            final Calendar c5 = Calendar.getInstance();
            mYear = c5.get(Calendar.YEAR);
            mMonth = c5.get(Calendar.MONTH);
            mDay = c5.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog5 = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            monthOfYear += 1;
                            String mt, dy;
                            if (monthOfYear < 10)
                                mt = "0" + monthOfYear; //if month less than 10 then ad 0 before month
                            else mt = String.valueOf(monthOfYear);

                            if (dayOfMonth < 10)
                                dy = "0" + dayOfMonth;
                            else dy = String.valueOf(dayOfMonth);
                            Stroke_diagnosed_date.setText(dy + "-" + mt + "-" + year);
                            //  Stroke_diagnosed_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog5.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog5.show();
        } else if (viewId == R.id.cancer_diagnosed_date) {
            final Calendar c6 = Calendar.getInstance();
            mYear = c6.get(Calendar.YEAR);
            mMonth = c6.get(Calendar.MONTH);
            mDay = c6.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog6 = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            monthOfYear += 1;
                            String mt, dy;
                            if (monthOfYear < 10)
                                mt = "0" + monthOfYear; //if month less than 10 then ad 0 before month
                            else mt = String.valueOf(monthOfYear);

                            if (dayOfMonth < 10)
                                dy = "0" + dayOfMonth;
                            else dy = String.valueOf(dayOfMonth);
                            cancer_diagnosed_date.setText(dy + "-" + mt + "-" + year);
                            //  cancer_diagnosed_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog6.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog6.show();
        } else if (viewId == R.id.congenital_diagnosed_date) {
            final Calendar c7 = Calendar.getInstance();
            mYear = c7.get(Calendar.YEAR);
            mMonth = c7.get(Calendar.MONTH);
            mDay = c7.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog7 = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            monthOfYear += 1;
                            String mt, dy;
                            if (monthOfYear < 10)
                                mt = "0" + monthOfYear; //if month less than 10 then ad 0 before month
                            else mt = String.valueOf(monthOfYear);

                            if (dayOfMonth < 10)
                                dy = "0" + dayOfMonth;
                            else dy = String.valueOf(dayOfMonth);
                            congenital_diagnosed_date.setText(dy + "-" + mt + "-" + year);

                            // congenital_diagnosed_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog7.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog7.show();
        } else if (viewId == R.id.hearingdefect_diagnosed_date) {
            final Calendar c8 = Calendar.getInstance();
            mYear = c8.get(Calendar.YEAR);
            mMonth = c8.get(Calendar.MONTH);
            mDay = c8.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog8 = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            monthOfYear += 1;
                            String mt, dy;
                            if (monthOfYear < 10)
                                mt = "0" + monthOfYear; //if month less than 10 then ad 0 before month
                            else mt = String.valueOf(monthOfYear);

                            if (dayOfMonth < 10)
                                dy = "0" + dayOfMonth;
                            else dy = String.valueOf(dayOfMonth);
                            hearingdefect_diagnosed_date.setText(dy + "-" + mt + "-" + year);

                            //  hearingdefect_diagnosed_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog8.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog8.show();
        } else if (viewId == R.id.gynaec_diagnosed_date) {
            final Calendar c9 = Calendar.getInstance();
            mYear = c9.get(Calendar.YEAR);
            mMonth = c9.get(Calendar.MONTH);
            mDay = c9.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog9 = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            monthOfYear += 1;
                            String mt, dy;
                            if (monthOfYear < 10)
                                mt = "0" + monthOfYear; //if month less than 10 then ad 0 before month
                            else mt = String.valueOf(monthOfYear);

                            if (dayOfMonth < 10)
                                dy = "0" + dayOfMonth;
                            else dy = String.valueOf(dayOfMonth);
                            gynaec_diagnosed_date.setText(dy + "-" + mt + "-" + year);
                            //  gynaec_diagnosed_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog9.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog9.show();
        }
    }

    public String getmmddyyyy(String yyyymmdd) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(yyyymmdd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateString2 = new SimpleDateFormat("dd-MM-yyyy").format(date);
        //  System.out.println(dateString2); // 2011-
        //Log.i("ckm=>convertedDate",dateString2);
        return dateString2;
    }
}