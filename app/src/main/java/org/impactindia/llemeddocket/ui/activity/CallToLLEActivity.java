package org.impactindia.llemeddocket.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.orm.CampProgramModel;
import org.impactindia.llemeddocket.orm.PopulationMedicalModel;
import org.impactindia.llemeddocket.pojo.CampProgramData;
import org.impactindia.llemeddocket.pojo.PopMedicalData;
import org.impactindia.llemeddocket.util.SharedPreference;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.impactindia.llemeddocket.util.StringUtils.isEmpty;

public class CallToLLEActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    Toolbar toolbar;
    ArrayList<String> llegync;
    ArrayList<String> oralarr;
    ArrayList<String> ep_plasticsurgery;
    ArrayList<String> entopd;
    ArrayList<String> earprblm;
    String strcataract, strvisual, strepf, strgync, strear, strhear, strcong, strlloral, strcancer;
    LinearLayout llvisualAc, llcata, lleeplipsy, lloralprblm, lle_congab, llehearing, lleEarprblm, llgynace,lle_cancer;
    Spinner llevisualacuspinner, llecataractspinner, lleEpilepsyspinner, lleoralspinner,
            lleCongABspinner, llehearingspinner, lleearprblmspinner, llegynaecspinner,lleoralCancerspinner;
    ArrayList<CampProgramData> data1 = new ArrayList<>();
    ArrayList<CampProgramData> data2 = new ArrayList<>();
    ArrayList<CampProgramData> data3 = new ArrayList<>();
    ArrayList<CampProgramData> data4 = new ArrayList<>();
    ArrayList<CampProgramData> data5 = new ArrayList<>();
    ArrayList<CampProgramData> data6 = new ArrayList<>();
    ArrayList<CampProgramData> data7 = new ArrayList<>();
    ArrayList<CampProgramData> data8 = new ArrayList<>();
    ArrayList<CampProgramData> data9 = new ArrayList<>();
    ArrayList<String> dsa = new ArrayList<>();
    ArrayAdapter<String> adapter_eye;
    ArrayAdapter<String> adapter_epf, adapter_catract, adapter_oralprblm, adapter_congenitalab, adapter_hearingdefect, adapter_earprblm, adapter_gynac, adapter_cancer;
    Button btnAddlldata,btnAddnewhousedata, btnSubmit;
    ArrayList<String> catractarr = new ArrayList<String>();
    ArrayList<String> epfarr = new ArrayList<String>();
    ArrayList<String> eyeopd = new ArrayList<String>();
    ArrayList<String> oralcancer = new ArrayList<String>();
    String editid = null;
    ArrayList<PopMedicalData> getdataforupdate = new ArrayList<PopMedicalData>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_to_l_l_e);
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
        PopulationMedicalModel.getInstance(this);
        init();
        llevisualacuspinner.setOnItemSelectedListener(this);
        llecataractspinner.setOnItemSelectedListener(this);
        lleEpilepsyspinner.setOnItemSelectedListener(this);
        lleoralspinner.setOnItemSelectedListener(this);
        lleCongABspinner.setOnItemSelectedListener(this);
        llehearingspinner.setOnItemSelectedListener(this);
        lleearprblmspinner.setOnItemSelectedListener(this);
        llegynaecspinner.setOnItemSelectedListener(this);
        lleoralCancerspinner.setOnItemSelectedListener(this);


        if (SharedPreference.get("LEFTEYEINTER").equals("abnormal") || SharedPreference.get("RIGHTEYEINTER").equals("abnormal")) {
            llvisualAc.setVisibility(View.VISIBLE);
            data1 = CampProgramModel.getcampprogramdata(SharedPreference.get("CAMPID"), "Eye OPD");
            Log.i("EyeSurgery", data1.size() + "");
            String Fromdate = getmmddyyyy(data1.get(0).getCampfrom());
            String Todate = getmmddyyyy(data1.get(0).getCampto());
            Log.i("ckm=>fromdate", Fromdate);
            Log.i("ckm=>Todate", Todate);

            eyeopd = getdatelist(Fromdate, Todate);
            Log.i("ckm=>eyeopd", eyeopd + "");
            adapter_eye = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, eyeopd);
            llevisualacuspinner.setAdapter(adapter_eye);
        } else {
            llvisualAc.setVisibility(View.GONE);
        }

        if (SharedPreference.get("LLECATARACT").equals("Yes")) {
            llcata.setVisibility(View.VISIBLE);
            data2 = CampProgramModel.getcampprogramdata(SharedPreference.get("CAMPID"), "Eye OPD");
            Log.i("Cateract", data2.size() + "");
            String Fromdate = getmmddyyyy(data2.get(0).getCampfrom());
            String Todate = getmmddyyyy(data2.get(0).getCampto());
            catractarr = getdatelist(Fromdate, Todate);
            adapter_catract = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, catractarr);
            llecataractspinner.setAdapter(adapter_catract);
        } else {
            llcata.setVisibility(View.GONE);
        }

        if (SharedPreference.get("LLECANCER").equals("Oral"))
        {
            lle_cancer.setVisibility(View.VISIBLE);
            data9 = CampProgramModel.getcampprogramdata(SharedPreference.get("CAMPID"),"Oral Cancer OPD");
            String Fromdate = getmmddyyyy(data9.get(0).getCampfrom());
            String Todate = getmmddyyyy(data9.get(0).getCampto());
            oralcancer = getdatelist(Fromdate, Todate);
            adapter_cancer = new ArrayAdapter<String>(this,R.layout.spinner_item,R.id.itemText,oralcancer);
            lleoralCancerspinner.setAdapter(adapter_cancer);
        }
        else
        {
            lle_cancer.setVisibility(View.GONE);
        }


        if (SharedPreference.get("LLEEPF").equals("Yes")) {
            lleeplipsy.setVisibility(View.VISIBLE);
            data3 = CampProgramModel.getcampprogramdata(SharedPreference.get("CAMPID"), "Epilepsy OPD");
            String Fromdate = getmmddyyyy(data3.get(0).getCampfrom());
            String Todate = getmmddyyyy(data3.get(0).getCampto());
            epfarr = getdatelist(Fromdate, Todate);
            adapter_epf = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, epfarr);
            lleEpilepsyspinner.setAdapter(adapter_epf);
        } else {
            lleeplipsy.setVisibility(View.GONE);
        }


        if (SharedPreference.get("LLEORALPRBM").equals("Yes")) {
            lloralprblm.setVisibility(View.VISIBLE);
            data4 = CampProgramModel.getcampprogramdata(SharedPreference.get("CAMPID"), "Dental OPD");
            String Fromdate = getmmddyyyy(data4.get(0).getCampfrom());
            String Todate = getmmddyyyy(data4.get(0).getCampto());
            oralarr = new ArrayList<String>();
            oralarr = getdatelist(Fromdate, Todate);
            adapter_oralprblm = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, oralarr);
            lleoralspinner.setAdapter(adapter_oralprblm);
        } else {
            lloralprblm.setVisibility(View.GONE);
        }

        if (SharedPreference.get("LLECONGITAL").equals("Cleft Lip")) {
            lle_congab.setVisibility(View.VISIBLE);
            data5 = CampProgramModel.getcampprogramdata(SharedPreference.get("CAMPID"), "Plastic Surgery OPD");
            String Fromdate = getmmddyyyy(data5.get(0).getCampfrom());
            String Todate = getmmddyyyy(data5.get(0).getCampto());
            Log.i("ckm=>congiFromDate", Fromdate);
            Log.i("ckm=>congiToDate", Todate);
            ep_plasticsurgery = new ArrayList<String>();
            ep_plasticsurgery = getdatelist(Fromdate, Todate);
            adapter_congenitalab = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, ep_plasticsurgery);
            lleCongABspinner.setAdapter(adapter_congenitalab);
        } else if (SharedPreference.get("LLECONGITAL").equals("Orthopaedic Deformities")) {
            lle_congab.setVisibility(View.VISIBLE);
            data5 = CampProgramModel.getcampprogramdata(SharedPreference.get("CAMPID"), "Orthopedic Surgery");

            String Fromdate = getmmddyyyy(data5.get(0).getCampfrom());
            String Todate = getmmddyyyy(data5.get(0).getCampto());

            ep_plasticsurgery = new ArrayList<String>();
            ep_plasticsurgery = getdatelist(Fromdate, Todate);
            adapter_congenitalab = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, ep_plasticsurgery);
            lleCongABspinner.setAdapter(adapter_congenitalab);
        } else {
            lle_congab.setVisibility(View.GONE);
        }

        if (!isEmpty(SharedPreference.get("LLEEARPRBLM")) && !SharedPreference.get("LLEEARPRBLM").equals("Normal") ) {
            lleEarprblm.setVisibility(View.VISIBLE);
            data7 = CampProgramModel.getcampprogramdata(SharedPreference.get("CAMPID"), "ENT OPD");

            String Fromdate = getmmddyyyy(data7.get(0).getCampfrom());
            String Todate = getmmddyyyy(data7.get(0).getCampto());

            earprblm = new ArrayList<String>();
            earprblm = getdatelist(Fromdate, Todate);
            adapter_earprblm = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, earprblm);
            lleearprblmspinner.setAdapter(adapter_earprblm);
        } else {
            lleEarprblm.setVisibility(View.GONE);
        }

        if (SharedPreference.get("GENDER").equals("Female"))
        {
            if (SharedPreference.get("LLEGYNAEC").equals("Yes") && SharedPreference.get("GENDER").equals("Female")) {
                llgynace.setVisibility(View.VISIBLE);
                data8 = CampProgramModel.getcampprogramdata(SharedPreference.get("CAMPID"), "Gynaec OPD");
                String Fromdate = getmmddyyyy(data8.get(0).getCampfrom());
                String Todate = getmmddyyyy(data8.get(0).getCampto());
                llegync = new ArrayList<String>();
                llegync = getdatelist(Fromdate, Todate);
                // llegync = getdatelist(data8.get(0).getCampfrom(),data8.get(0).getCampto());
                adapter_gynac = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, llegync);
                llegynaecspinner.setAdapter(adapter_gynac);
            } else {
                llgynace.setVisibility(View.GONE);
            }
        }
        else
        {
            llgynace.setVisibility(View.GONE);
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopMedicalData pop = new PopMedicalData();
                if (SharedPreference.get("LEFTEYEINTER").equals("abnormal") || SharedPreference.get("RIGHTEYEINTER").equals("abnormal")) {
                    if (!isEmpty(strvisual)) {
                        pop.setEyeopd(getyyyymmdd(strvisual));
                    }

                }
                if (SharedPreference.get("LLECATARACT").equals("Yes")) {
                    if (!isEmpty(strcataract)) {
                        pop.setEyesurgery(getyyyymmdd(strcataract));
                    }
                } else {
                    pop.setEyesurgery(llecataractspinner.getSelectedItemPosition() == 0 ? null : null);
                }
                if (SharedPreference.get("LLEEPF").equals("Yes")) {
                    pop.setEpilepsyopd(getyyyymmdd(strepf));
                } else {
                    pop.setEpilepsyopd(lleEpilepsyspinner.getSelectedItemPosition() == 0 ? null : null);
                }

                if (SharedPreference.get("LLECANCER").equals("Oral"))
                {
                    pop.setOralcanceropd(getyyyymmdd(strcancer));
                }
                else
                {
                    pop.setOralcanceropd(lleoralCancerspinner.getSelectedItemPosition() == 0 ? null : null);
                }

                if (SharedPreference.get("LLEORALPRBM").equals("Yes")) {
                    pop.setDentalopd(getyyyymmdd(strlloral));
                } else {
                    pop.setDentalopd(lleoralspinner.getSelectedItemPosition() == 0 ? null : null);
                }

                if (SharedPreference.get("LLECONGITAL").equals("Cleft Lip")) {
                    pop.setPlasticsurgeryopd(getyyyymmdd(strcong));
                } else if (SharedPreference.get("LLECONGITAL").equals("Orthopaedic Deformities")) {
                    pop.setOrthopedicsurgery(getyyyymmdd(strcong));
                } else {
                    pop.setOrthopedicsurgery(null);
                }

                if (!isEmpty(SharedPreference.get("LLEEARPRBLM")) && !SharedPreference.get("LLEEARPRBLM").equals("Normal")) {
                    pop.setEntopd(getyyyymmdd(strear));
                }
                if (!isEmpty(SharedPreference.get("LLEGYNAEC")))
                {
                    if (SharedPreference.get("LLEGYNAEC").equals("Yes") && SharedPreference.get("GENDER").equals("Female")) {
                        pop.setGynaecopd(getyyyymmdd(strgync));
                    } else {
                        pop.setGynaecopd(llegynaecspinner.getSelectedItemPosition() == 0 ? null : null);
                    }
                }

                Log.i("UpdateData=Data", pop.getEyeopd() + " " + pop.getEyesurgery() + " " + pop.getEpilepsyopd() + " " + pop.getDentalopd() + " " + pop.getEntopd() + " " + pop.getPlasticsurgeryopd() + "" +pop.getOrthopedicsurgery());
                PopulationMedicalModel.UpdateCalltoll(SharedPreference.get("HOUSEHOLDID"), pop);

                Intent i = new Intent(CallToLLEActivity.this, DashboardActivityOutPro.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                shortToast("Data submitted");
                startActivity(i);
                finish();
            }
        });


        btnAddnewhousedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopMedicalData pop = new PopMedicalData();
                if (SharedPreference.get("LEFTEYEINTER").equals("abnormal") || SharedPreference.get("RIGHTEYEINTER").equals("abnormal")) {
                    if (!isEmpty(strvisual)) {
                        pop.setEyeopd(getyyyymmdd(strvisual));
                    }

                }
                if (SharedPreference.get("LLECATARACT").equals("Yes")) {
                    if (!isEmpty(strcataract)) {
                        pop.setEyesurgery(getyyyymmdd(strcataract));
                    }
                } else {
                    pop.setEyesurgery(llecataractspinner.getSelectedItemPosition() == 0 ? null : null);
                }
                if (SharedPreference.get("LLEEPF").equals("Yes")) {
                    pop.setEpilepsyopd(getyyyymmdd(strepf));
                } else {
                    pop.setEpilepsyopd(lleEpilepsyspinner.getSelectedItemPosition() == 0 ? null : null);
                }

                if (SharedPreference.get("LLECANCER").equals("Oral"))
                {
                    pop.setOralcanceropd(getyyyymmdd(strcancer));
                }
                else
                {
                    pop.setOralcanceropd(lleoralCancerspinner.getSelectedItemPosition() == 0 ? null : null);
                }

                if (SharedPreference.get("LLEORALPRBM").equals("Yes")) {
                    pop.setDentalopd(getyyyymmdd(strlloral));
                } else {
                    pop.setDentalopd(lleoralspinner.getSelectedItemPosition() == 0 ? null : null);
                }

                if (SharedPreference.get("LLECONGITAL").equals("Cleft Lip")) {
                    pop.setPlasticsurgeryopd(getyyyymmdd(strcong));
                } else if (SharedPreference.get("LLECONGITAL").equals("Orthopaedic Deformities")) {
                    pop.setOrthopedicsurgery(getyyyymmdd(strcong));
                } else {
                    pop.setOrthopedicsurgery(null);
                }

                if (!isEmpty(SharedPreference.get("LLEEARPRBLM")) && !SharedPreference.get("LLEEARPRBLM").equals("Normal")) {
                    pop.setEntopd(getyyyymmdd(strear));
                }
                if (!isEmpty(SharedPreference.get("LLEGYNAEC")))
                {
                    if (SharedPreference.get("LLEGYNAEC").equals("Yes") && SharedPreference.get("GENDER").equals("Female")) {
                        pop.setGynaecopd(getyyyymmdd(strgync));
                    } else {
                        pop.setGynaecopd(llegynaecspinner.getSelectedItemPosition() == 0 ? null : null);
                    }
                }

                Log.i("UpdateData=Data", pop.getEyeopd() + " " + pop.getEyesurgery() + " " + pop.getEpilepsyopd() + " " + pop.getDentalopd() + " " + pop.getEntopd() + " " + pop.getPlasticsurgeryopd() + "" +pop.getOrthopedicsurgery());
                PopulationMedicalModel.UpdateCalltoll(SharedPreference.get("HOUSEHOLDID"), pop);

                Intent i = new Intent(CallToLLEActivity.this, AddHouseholdActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                shortToast("Data submitted");
                startActivity(i);
                finish();

            }
        });

        btnAddlldata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopMedicalData pop = new PopMedicalData();
                if (SharedPreference.get("LEFTEYEINTER").equals("abnormal") || SharedPreference.get("RIGHTEYEINTER").equals("abnormal")) {
                    if (!isEmpty(strvisual)) {
                        pop.setEyeopd(getyyyymmdd(strvisual));
                    }
                }
                if (SharedPreference.get("LLECATARACT").equals("Yes")) {
                    if (!isEmpty(strcataract)) {
                        pop.setEyesurgery(getyyyymmdd(strcataract));
                    }
                } else {
                    pop.setEyesurgery(llecataractspinner.getSelectedItemPosition() == 0 ? null : null);
                }
                if (SharedPreference.get("LLEEPF").equals("Yes")) {
                    pop.setEpilepsyopd(getyyyymmdd(strepf));
                } else {
                    pop.setEpilepsyopd(lleEpilepsyspinner.getSelectedItemPosition() == 0 ? null : null);
                }

                if (SharedPreference.get("LLECANCER").equals("Oral"))
                {
                    pop.setOralcanceropd(getyyyymmdd(strcancer));
                }
                else
                {
                    pop.setOralcanceropd(lleoralCancerspinner.getSelectedItemPosition() == 0 ? null : null);
                }

                if (SharedPreference.get("LLEORALPRBM").equals("Yes")) {
                    pop.setDentalopd(getyyyymmdd(strlloral));
                } else {
                    pop.setDentalopd(lleoralspinner.getSelectedItemPosition() == 0 ? null : null);
                }

                if (SharedPreference.get("LLECONGITAL").equals("Cleft Lip")) {
                    pop.setPlasticsurgeryopd(getyyyymmdd(strcong));
                } else if (SharedPreference.get("LLECONGITAL").equals("Orthopaedic Deformities")) {
                    pop.setOrthopedicsurgery(getyyyymmdd(strcong));
                } else {
                    pop.setOrthopedicsurgery(null);
                }


                if (!isEmpty(SharedPreference.get("LLEEARPRBLM")) && !SharedPreference.get("LLEEARPRBLM").equals("Normal")) {
                    pop.setEntopd(getyyyymmdd(strear));
                }
               if (!SharedPreference.get("GENDER").equals("Male"))
               {
                   if (SharedPreference.get("LLEGYNAEC").equals("Yes") && SharedPreference.get("GENDER").equals("Female")) {
                       pop.setGynaecopd(getyyyymmdd(strgync));
                   } else {
                       pop.setGynaecopd(llegynaecspinner.getSelectedItemPosition() == 0 ? null : null);
                   }
               }
               else
               {
                   pop.setGynaecopd(null);
               }

                Log.i("UpdateData=Data", pop.getEyeopd() + " " + pop.getEyesurgery() + " " + pop.getEpilepsyopd() + " " + pop.getDentalopd() + " " + pop.getEntopd() + " " + pop.getPlasticsurgeryopd() + "" +pop.getOrthopedicsurgery());
                PopulationMedicalModel.UpdateCalltoll(SharedPreference.get("HOUSEHOLDID"), pop);

                if (!isEmpty(editid) && editid != null) {
                    Intent i = new Intent(CallToLLEActivity.this, EditDataActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    shortToast("Data updated");
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(CallToLLEActivity.this, AddHouseholdActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Bundle bundle = new Bundle();
                    bundle.putString("FILLHOUSENO","1");
                    i.putExtras(bundle);
                    shortToast("Data submitted");
                    startActivity(i);
                    finish();
                }
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            editid = bundle.getString("EDITID");
            getdataforupdate = PopulationMedicalModel.getdataforupdate(editid);
            btnAddlldata.setText("Update");
            btnAddnewhousedata.setVisibility(View.GONE);
            btnSubmit.setVisibility(View.GONE);


            if (!isEmpty(getdataforupdate.get(0).getEyeopd())) {
                if (eyeopd != null && eyeopd.size() >0)
                {
                    adapter_eye = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, eyeopd);
                    llevisualacuspinner.setAdapter(adapter_eye);
                    int pos = adapter_eye.getPosition(getmmddyyyy(getdataforupdate.get(0).getEyeopd()));
                    Log.i("ckm=>visual_position", pos + "");
                    llevisualacuspinner.setSelection(pos);
                }
            }
            if (!isEmpty(getdataforupdate.get(0).getEyesurgery())) {
                if (catractarr != null && catractarr.size() >0)
                {
                    adapter_catract = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, catractarr);
                    llecataractspinner.setAdapter(adapter_catract);
                    int pos = adapter_catract.getPosition(getmmddyyyy(getdataforupdate.get(0).getEyesurgery()));
                    Log.i("ckm=>catract_position", pos + "");
                    llecataractspinner.setSelection(pos);
                }
            }

            if (!isEmpty(getdataforupdate.get(0).getOralcanceropd()))
            {
                if (oralcancer != null && oralcancer.size() > 0)
                {
                    adapter_cancer = new ArrayAdapter<String>(this,R.layout.spinner_item,R.id.itemText,oralcancer);
                    lleoralCancerspinner.setAdapter(adapter_cancer);
                    int pos = adapter_cancer.getPosition(getmmddyyyy(getdataforupdate.get(0).getOralcanceropd()));
                    lleoralCancerspinner.setSelection(pos);
                }
            }

            if (!isEmpty(getdataforupdate.get(0).getEpilepsyopd())) {
                if (epfarr != null && epfarr.size()>0)
                {
                    adapter_epf = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, epfarr);
                    lleEpilepsyspinner.setAdapter(adapter_epf);
                    int pos = adapter_epf.getPosition(getmmddyyyy(getdataforupdate.get(0).getEpilepsyopd()));
                    Log.i("ckm=>epf_position", pos + "");
                    lleEpilepsyspinner.setSelection(pos);
                }

            }
            if (!isEmpty(getdataforupdate.get(0).getDentalopd())) {
                if(oralarr != null && oralarr.size() > 0 )
                {
                    adapter_oralprblm = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, oralarr);
                    lleoralspinner.setAdapter(adapter_oralprblm);
                    int pos = adapter_oralprblm.getPosition(getmmddyyyy(getdataforupdate.get(0).getDentalopd()));
                    Log.i("ckm=>adental_position", pos + "");
                    if (pos == -1) {
                        lleoralspinner.setSelection(0);
                    } else {
                        lleoralspinner.setSelection(pos);
                    }
                }
            }
            if (!isEmpty(getdataforupdate.get(0).getPlasticsurgeryopd())) {
                if (ep_plasticsurgery != null && ep_plasticsurgery.size() > 0)
                {
                    adapter_congenitalab = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, ep_plasticsurgery);
                    lleCongABspinner.setAdapter(adapter_congenitalab);
                    int pos = adapter_congenitalab.getPosition(getmmddyyyy(getdataforupdate.get(0).getPlasticsurgeryopd()));
                    Log.i("ckm=>plastic_position", pos + "");
                    lleCongABspinner.setSelection(pos);
                }
            }
            if (!isEmpty(getdataforupdate.get(0).getOrthopedicsurgery())) {
                if (ep_plasticsurgery != null && ep_plasticsurgery.size() > 0)
                {
                    adapter_congenitalab = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, ep_plasticsurgery);
                    lleCongABspinner.setAdapter(adapter_congenitalab);
                    int pos = adapter_congenitalab.getPosition(getmmddyyyy(getdataforupdate.get(0).getOrthopedicsurgery()));
                    Log.i("ckm=>ortho_position", pos + "");
                    lleCongABspinner.setSelection(pos);
                }


            }

            if (!isEmpty(getdataforupdate.get(0).getEntopd())) {

                if (earprblm != null && earprblm.size() > 0)
                {
                    Log.i("ckm=>EMpty"," empty");
                    adapter_earprblm = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, earprblm);
                    lleearprblmspinner.setAdapter(adapter_earprblm);
                    int pos = adapter_earprblm.getPosition(getmmddyyyy(getdataforupdate.get(0).getEntopd()));
                    lleearprblmspinner.setSelection(pos);

                }
            }

            if (!isEmpty(getdataforupdate.get(0).getGynaecopd()) && SharedPreference.get("GENDER").equals("Female")) {
                if (llegync != null && llegync.size() > 0)
                {
                    adapter_gynac = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, llegync);
                    llegynaecspinner.setAdapter(adapter_gynac);
                    int pos = adapter_gynac.getPosition(getmmddyyyy(getdataforupdate.get(0).getGynaecopd()));
                    Log.i("ckm=>gync_position", pos + "");
                    llegynaecspinner.setSelection(pos);
                }
            }
        }

    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Call To LLE");
        CampProgramModel.getInstance(this);

        llcata = findViewById(R.id.llcata);
        lleeplipsy = findViewById(R.id.lleeplipsy);
        lloralprblm = findViewById(R.id.lloralprblm);
        lle_congab = findViewById(R.id.lle_congab);
        llehearing = findViewById(R.id.llehearing);
        lleEarprblm = findViewById(R.id.lleEarprblm);
        llgynace = findViewById(R.id.llgynace);
        llvisualAc = findViewById(R.id.llvisualAc);
        btnAddlldata = findViewById(R.id.btnAddlldata);
        btnAddnewhousedata = findViewById(R.id.btnAddnewhousedata);
        btnSubmit = findViewById(R.id.btnSubmit);
        lle_cancer = findViewById(R.id.lle_cancer);
        lleoralCancerspinner = findViewById(R.id.lleoralCancerspinner);

        llecataractspinner = findViewById(R.id.llecataractspinner);
        lleEpilepsyspinner = findViewById(R.id.lleEpilepsyspinner);
        lleoralspinner = findViewById(R.id.lleoralspinner);
        lleCongABspinner = findViewById(R.id.lleCongABspinner);
        llehearingspinner = findViewById(R.id.llehearingspinner);
        lleearprblmspinner = findViewById(R.id.lleearprblmspinner);
        llegynaecspinner = findViewById(R.id.llegynaecspinner);
        llevisualacuspinner = findViewById(R.id.llevisualacuspinner);

    }

    public ArrayList<String> getdatelist(String fromdate, String todate) {
        List<Date> dates = new ArrayList<Date>();
        ArrayList<String> arrayList = new ArrayList<>();

        DateFormat formatter;
        // formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date startDate = null;
        try {
            startDate = (Date) formatter.parse(fromdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date endDate = null;
        try {
            endDate = (Date) formatter.parse(todate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long interval = 24 * 1000 * 60 * 60; // 1 hour in millis
        long endTime = endDate.getTime(); // create your endtime here, possibly using Calendar or Date
        long curTime = startDate.getTime();
        while (curTime <= endTime) {
            dates.add(new Date(curTime));
            curTime += interval;
        }

        for (int i = 0; i < dates.size(); i++) {
            Date lDate = (Date) dates.get(i);
            String ds = formatter.format(lDate);
            System.out.println(" Date is ..." + ds);
            arrayList.add(ds);
        }
        return arrayList;
    }


    public String getyyyymmdd(String mmddyyyy) {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd-MM-yyyy").parse(mmddyyyy);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateString2 = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return dateString2;
    }


    public String getmmddyyyy(String yyyymmdd) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(yyyymmdd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateString2 = new SimpleDateFormat("dd-MM-yyyy").format(date);
        return dateString2;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int viewId = parent.getId();

        if (viewId == R.id.llevisualacuspinner) {
            Log.i("ckm=>visual", llevisualacuspinner.getSelectedItem().toString());
            strvisual = llevisualacuspinner.getSelectedItem().toString();
            Log.i("ckm=>vc", strvisual);

        } else if (viewId == R.id.llecataractspinner) {
            strcataract = llecataractspinner.getSelectedItem().toString();
            Log.i("ckm=>strcataract", strcataract);

        } else if (viewId == R.id.lleEpilepsyspinner) {
            strepf = lleEpilepsyspinner.getSelectedItem().toString();
            Log.i("ckm=>strepf", strepf);

        } else if (viewId == R.id.lleoralspinner) {
            strlloral = lleoralspinner.getSelectedItem().toString();
            Log.i("ckm=>strlloral", strlloral);

        } else if (viewId == R.id.lleCongABspinner) {
            strcong = lleCongABspinner.getSelectedItem().toString();
            Log.i("ckm=>strcong", strcong);

        } else if (viewId == R.id.lleearprblmspinner) {
            strear = lleearprblmspinner.getSelectedItem().toString();
            Log.i("ckm=>strear", strear);

        } else if (viewId == R.id.llegynaecspinner) {
            strgync = llegynaecspinner.getSelectedItem().toString();
            Log.i("ckm=>strgync", strgync);

        } else if (viewId == R.id.lleoralCancerspinner) {
            strcancer = lleoralCancerspinner.getSelectedItem().toString();
            Log.i("ckm=>strcancer", strcancer);
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CallToLLEActivity.this,DashboardActivityOutPro.class));
//        finishAffinity();
    }
}
