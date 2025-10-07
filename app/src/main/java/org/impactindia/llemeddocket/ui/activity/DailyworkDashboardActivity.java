package org.impactindia.llemeddocket.ui.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.impactindia.llemeddocket.DatabaseHelper;
import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.adapter.ActiveCampReport;
import org.impactindia.llemeddocket.adapter.CustomSpinnerPada;
import org.impactindia.llemeddocket.adapter.CustomSpinnerSubCenter;
import org.impactindia.llemeddocket.adapter.CustomSpinnerVillage;
import org.impactindia.llemeddocket.adapter.CustomeAnmSpinner;
import org.impactindia.llemeddocket.adapter.CustomeAshaSpinner;
import org.impactindia.llemeddocket.adapter.CustomeSpinnerAdapter;
import org.impactindia.llemeddocket.orm.PadaNAshworkerDetailModel;
import org.impactindia.llemeddocket.orm.PhcSubCenterModel;
import org.impactindia.llemeddocket.orm.PhcUsersModel;
import org.impactindia.llemeddocket.orm.PopulationMedicalModel;
import org.impactindia.llemeddocket.orm.UserDetailsDAO;
import org.impactindia.llemeddocket.pojo.PadanashaworkerData;
import org.impactindia.llemeddocket.pojo.PhcSubcenterData;
import org.impactindia.llemeddocket.pojo.PhcUserData;
import org.impactindia.llemeddocket.pojo.PopMedicalData;
import org.impactindia.llemeddocket.pojo.UserDetails;
import org.impactindia.llemeddocket.util.SharedPreference;

import java.util.ArrayList;
import java.util.List;

public class DailyworkDashboardActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    Spinner phc_spinner,subcenter_spinner,village_spinner,pada_spinner,asha_worker_spn,asha_pada_spinner;
    ArrayList<PhcUserData> userdata = new ArrayList<PhcUserData>();
    ArrayList<PadanashaworkerData> getvillage = new ArrayList<PadanashaworkerData> ();
    ArrayList<PhcSubcenterData> subcenterdata = new ArrayList<PhcSubcenterData>();

    ArrayList<PadanashaworkerData> padashaworker = new ArrayList<PadanashaworkerData>();
    ArrayList<PadanashaworkerData> anmworker = new ArrayList<PadanashaworkerData>();
    ArrayList<PadanashaworkerData> getpada = new ArrayList<PadanashaworkerData>();
    ArrayList<PadanashaworkerData> getanmpada = new ArrayList<PadanashaworkerData>();

    String str_phc,str_subcenter,str_village,str_pada,str_asha_worker,str_ashapada,str_anmwork,str_anmpadaworker;

    ArrayList<PhcSubcenterData> mList = new ArrayList<PhcSubcenterData>();

    ArrayList<PadanashaworkerData> mListNew = new ArrayList<PadanashaworkerData>();

    TextView txtoralcanceropd,txttotalpopulationcount,txttotalhousecount,txteyeopdcount,txteyeopddetails,txtCataractcount,
            txtcataractdetails,txtepfcount,txtepfdetails,txtdentalcount,txtdentaldetails,txtplasticsurgcount,
            txtplasticsurdetails,txtorthosurgcount,txtorthosurdetails,txtentcount,txtentdetails,txtgynaeccount,txtgynaecdetails,txtdetailoralcancer;

    ArrayList<PopMedicalData> totalbyphc = new ArrayList<PopMedicalData>();
    ArrayList<PopMedicalData> totalbysubcenter = new ArrayList<PopMedicalData>();
    ArrayList<PopMedicalData> totalbyvillage = new ArrayList<PopMedicalData>();
    ArrayList<PopMedicalData> totalbypada = new ArrayList<PopMedicalData>();
    ArrayList<PopMedicalData> arrgetashpadacount = new ArrayList<PopMedicalData>();
    ArrayList<PopMedicalData> ashworkercountarr = new ArrayList<PopMedicalData>();

    int total_household = 0;
    int total_population = 0 ;
    String data1;

    int eyeopdcount = 0;
    int cataract = 0;
    int epfcount = 0;
    int dentalcount = 0;
    int plasticsurgerycount = 0;
    int orthosurgerycount = 0;
    int entcount = 0;
    int gynacecount = 0;

    Toolbar toolbar;
    Button btnDetails,anmbtnDetails;
    Spinner anm_pada_spinner,anm_worker_spn;
    Spinner spinner_dashboard;
    private SQLiteDatabase db;
    private UserDetailsDAO userDetailsDAO;
    ArrayList<UserDetails> userlist = new ArrayList<>();
    ArrayList<UserDetails> newuserlist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailywork_dashboard);

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
        userDetailsDAO = new UserDetailsDAO(this, db);

        PhcUsersModel.getInstance(this);
        PhcSubCenterModel.getInstance(this);
        PopulationMedicalModel.getInstance(this);
        PadaNAshworkerDetailModel.getInstance(this);
        UserDetailsDAO.getInstance(this);
        PhcUsersModel.open();
        init();

        if (getpada.isEmpty())
        {
            PadanashaworkerData data = new PadanashaworkerData();
            data.setPada("Select pada");
            getpada.add(0,data);
            CustomeSpinnerForAshaPada setpada = new CustomeSpinnerForAshaPada(this,getpada);
            asha_pada_spinner.setAdapter(setpada);
         //   asha_pada_spinner.setOnItemSelectedListener(this);
        }

        if (getanmpada.isEmpty())
        {
            PadanashaworkerData data = new PadanashaworkerData();
            data.setPada("Select pada");
            getanmpada.add(0,data);
            CustomeSpinnerForAshaPada setpada = new CustomeSpinnerForAshaPada(this,getanmpada);
            anm_pada_spinner.setAdapter(setpada);
            //   asha_pada_spinner.setOnItemSelectedListener(this);
        }

        //For ANM Worker
        anmworker = PadaNAshworkerDetailModel.getAnmworker(SharedPreference.get("CAMPID"));
        PadanashaworkerData anmworker1 = new PadanashaworkerData();
        anmworker1.setAnmname("Select");
        anmworker.add(0,anmworker1);
        CustomeAnmSpinner adapteranm = new CustomeAnmSpinner(this,anmworker);
        anm_worker_spn.setAdapter(adapteranm);
        anm_worker_spn.setOnItemSelectedListener(this);

        //For asha Worker
        padashaworker = PadaNAshworkerDetailModel.getAshaworker(SharedPreference.get("CAMPID"));
        PadanashaworkerData ashaworker = new PadanashaworkerData();
        ashaworker.setAshaworkername("Select");
        padashaworker.add(0,ashaworker);
        CustomeAshaSpinner adapterash = new CustomeAshaSpinner(this,padashaworker);
        asha_worker_spn.setAdapter(adapterash);
        asha_worker_spn.setOnItemSelectedListener(this);


        //For Phc
        userdata = PhcUsersModel.getPhcByUserId(SharedPreference.get("Userid"),SharedPreference.get("CAMPID"));
        PhcUserData phcuser = new PhcUserData();
        phcuser.setPhc("Select Phc");
        userdata.add(0,phcuser);
        CustomeSpinnerAdapter adapter = new CustomeSpinnerAdapter(this,userdata);
        phc_spinner.setAdapter(adapter);
        phc_spinner.setOnItemSelectedListener(this);

        //For subcenter
        subcenterdata = PhcSubCenterModel.getsubcenterbycampid(SharedPreference.get("CAMPID"));
        PhcSubcenterData subcenter = new PhcSubcenterData();
        subcenter.setSubCenter("Select Subcenter");
        subcenterdata.add(0,subcenter);
        CustomSpinnerSubCenter subCenteradapter = new CustomSpinnerSubCenter(this,subcenterdata);
        subcenter_spinner.setAdapter(subCenteradapter);
        subcenter_spinner.setOnItemSelectedListener(this);

        mListNew = PadaNAshworkerDetailModel.getOnlyPada(SharedPreference.get("CAMPID"));
        PadanashaworkerData msg1 = new PadanashaworkerData();
        msg1.setPada("Select Pada");
        mListNew.add(0,msg1);
        Log.i("padasize",mListNew.size()+"");
        CustomeSpinnerForAshaPada adapter1 = new CustomeSpinnerForAshaPada(this,mListNew);
        pada_spinner.setAdapter(adapter1);
        pada_spinner.setOnItemSelectedListener(this);

        //For Village
        getvillage = PadaNAshworkerDetailModel.getOnlyVillage(SharedPreference.get("CAMPID"));
        PadanashaworkerData phcuser1 = new PadanashaworkerData();
        phcuser1.setVillage("Select Village");
        getvillage.add(0,phcuser1);
        CustomSpinnerVillage villagadapter = new CustomSpinnerVillage(this,getvillage);
        village_spinner.setAdapter(villagadapter);
        village_spinner.setOnItemSelectedListener(this);


        asha_pada_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_ashapada = getpada.get(position).getPada();
                Log.i("ckm=>ite",str_ashapada);

                if (!str_ashapada.equals("Select pada"))
                {
                    btnDetails.setVisibility(View.VISIBLE);
                    arrgetashpadacount = PopulationMedicalModel.getAllOpdcountbyfilterForAsha(str_ashapada,SharedPreference.get("CAMPID"));
                    if (arrgetashpadacount.size() > 0)
                    {
                        txttotalpopulationcount.setText(arrgetashpadacount.get(0).getPopultioncount());
                        txttotalhousecount.setText(arrgetashpadacount.get(0).getHousholscount());
                    }
                    if (!isEmpty(arrgetashpadacount.get(0).getEyeopdcount()))
                    {
                        txteyeopdcount.setText(arrgetashpadacount.get(0).getEyeopdcount());
                    }
                    if (!isEmpty(arrgetashpadacount.get(0).getEpfcount()))
                    {
                        txtepfcount.setText(arrgetashpadacount.get(0).getEpfcount());
                    }
                    if (!isEmpty(arrgetashpadacount.get(0).getCataractcount()))
                    {
                        txtCataractcount.setText(arrgetashpadacount.get(0).getCataractcount());
                    }
                    if (!isEmpty(arrgetashpadacount.get(0).getDentalopdcount()))
                    {
                        txtdentalcount.setText(arrgetashpadacount.get(0).getDentalopdcount());
                    }
                    if (!isEmpty(arrgetashpadacount.get(0).getPlasticsurgerycount()))
                    {
                        txtplasticsurgcount.setText(arrgetashpadacount.get(0).getPlasticsurgerycount());
                    }
                    if (!isEmpty(arrgetashpadacount.get(0).getOrthocount()))
                    {
                        txtorthosurgcount.setText(arrgetashpadacount.get(0).getOrthocount());
                    }
                    if (!isEmpty(arrgetashpadacount.get(0).getEntcount()))
                    {
                        txtentcount.setText(arrgetashpadacount.get(0).getEntcount());
                    }
                    if (!isEmpty(arrgetashpadacount.get(0).getGynaecount()))
                    {
                        txtgynaeccount.setText(arrgetashpadacount.get(0).getGynaecount());
                    }
                    if (!isEmpty(arrgetashpadacount.get(0).getOralcancercount()))
                    {
                        txtoralcanceropd.setText(arrgetashpadacount.get(0).getOralcancercount());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        anm_pada_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_anmpadaworker = getanmpada.get(position).getPada();
                Log.i("ckm=>ite",str_anmpadaworker);

                if (!str_anmpadaworker.equals("Select pada"))
                {
                    anmbtnDetails.setVisibility(View.VISIBLE);
                    arrgetashpadacount = PopulationMedicalModel.getAllOpdcountbyfilterForAsha(str_anmpadaworker,SharedPreference.get("CAMPID"));
                    if (arrgetashpadacount.size() > 0)
                    {
                        txttotalpopulationcount.setText(arrgetashpadacount.get(0).getPopultioncount());
                        txttotalhousecount.setText(arrgetashpadacount.get(0).getHousholscount());
                    }
                    if (!isEmpty(arrgetashpadacount.get(0).getEyeopdcount()))
                    {
                        txteyeopdcount.setText(arrgetashpadacount.get(0).getEyeopdcount());
                    }
                    if (!isEmpty(arrgetashpadacount.get(0).getEpfcount()))
                    {
                        txtepfcount.setText(arrgetashpadacount.get(0).getEpfcount());
                    }
                    if (!isEmpty(arrgetashpadacount.get(0).getCataractcount()))
                    {
                        txtCataractcount.setText(arrgetashpadacount.get(0).getCataractcount());
                    }
                    if (!isEmpty(arrgetashpadacount.get(0).getDentalopdcount()))
                    {
                        txtdentalcount.setText(arrgetashpadacount.get(0).getDentalopdcount());
                    }
                    if (!isEmpty(arrgetashpadacount.get(0).getPlasticsurgerycount()))
                    {
                        txtplasticsurgcount.setText(arrgetashpadacount.get(0).getPlasticsurgerycount());
                    }
                    if (!isEmpty(arrgetashpadacount.get(0).getOrthocount()))
                    {
                        txtorthosurgcount.setText(arrgetashpadacount.get(0).getOrthocount());
                    }
                    if (!isEmpty(arrgetashpadacount.get(0).getEntcount()))
                    {
                        txtentcount.setText(arrgetashpadacount.get(0).getEntcount());
                    }
                    if (!isEmpty(arrgetashpadacount.get(0).getGynaecount()))
                    {
                        txtgynaeccount.setText(arrgetashpadacount.get(0).getGynaecount());
                    }
                    if (!isEmpty(arrgetashpadacount.get(0).getOralcancercount()))
                    {
                        txtoralcanceropd.setText(arrgetashpadacount.get(0).getOralcancercount());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void init() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Dashboard");

        spinner_dashboard = findViewById(R.id.spinner_dashboard);
        anm_pada_spinner = findViewById(R.id.anm_pada_spinner);
        anm_worker_spn = findViewById(R.id.anm_worker_spn);
        anmbtnDetails = findViewById(R.id.anmbtnDetails);
        anm_worker_spn.setOnItemSelectedListener(this);

        txtdetailoralcancer = findViewById(R.id.txtdetailoralcancer);
        txtdetailoralcancer.setOnClickListener(this);

        btnDetails = findViewById(R.id.btnDetails);
        phc_spinner = findViewById(R.id.phc_spinner);
        subcenter_spinner = findViewById(R.id.subcenter_spinner);
        village_spinner = findViewById(R.id.village_spinner);
        pada_spinner = findViewById(R.id.pada_spinner);
        txttotalpopulationcount = findViewById(R.id.txttotalpopulation);
        txttotalhousecount = findViewById(R.id.txttotalhouse);
        txteyeopdcount = findViewById(R.id.txteyeopdcount);
        txteyeopddetails = findViewById(R.id.txteyeopddetails);
        txteyeopddetails.setOnClickListener(this);

        txtCataractcount = findViewById(R.id.txtCataractcount);
        txtcataractdetails = findViewById(R.id.txtcataractdetails);
        txtcataractdetails.setOnClickListener(this);
        txtepfcount = findViewById(R.id.txtepfcount);
        txtoralcanceropd = findViewById(R.id.txtoralcanceropd);


        txtepfdetails = findViewById(R.id.txtepfdetails);
        txtepfdetails.setOnClickListener(this);
        txtdentalcount = findViewById(R.id.txtdentalcount);
        txtdentaldetails = findViewById(R.id.txtdentaldetails);
        txtdentaldetails.setOnClickListener(this);
        txtplasticsurgcount = findViewById(R.id.txtplasticsurgcount);
        txtplasticsurdetails = findViewById(R.id.txtplasticsurdetails);
        txtplasticsurdetails.setOnClickListener(this);
        txtorthosurgcount = findViewById(R.id.txtorthosurgcount);
        txtorthosurdetails = findViewById(R.id.txtorthosurdetails);
        txtorthosurdetails.setOnClickListener(this);
        txtentdetails = findViewById(R.id.txtentdetails);
        txtentdetails.setOnClickListener(this);
        txtgynaeccount = findViewById(R.id.txtgynaeccount);
        txtgynaecdetails = findViewById(R.id.txtgynaecdetails);
        txtgynaecdetails.setOnClickListener(this);
        txtentcount = findViewById(R.id.txtentcount);

        asha_worker_spn = findViewById(R.id.asha_worker_spn);
        asha_pada_spinner = findViewById(R.id.asha_pada_spinner);
        asha_worker_spn.setOnItemSelectedListener(this);


        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DailyworkDashboardActivity.this,OpdPatientListActivity.class);
                intent.putExtra("PADA",str_ashapada);
                Log.i("ckm=>pda",str_ashapada);
                startActivity(intent);
            }
        });

        anmbtnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DailyworkDashboardActivity.this,OpdPatientListActivity.class);
                intent.putExtra("PADA",str_anmpadaworker);
                Log.i("ckm=>pda",str_anmpadaworker);
                startActivity(intent);
            }
        });

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
        spinner_dashboard.setAdapter(report);
        spinner_dashboard.setOnItemSelectedListener(this);

    }

    public void setpadainital(){
        getpada.clear();
        PadanashaworkerData data = new PadanashaworkerData();
        data.setPada("Select pada");
        getpada.add(0,data);
        CustomeSpinnerForAshaPada setpada = new CustomeSpinnerForAshaPada(this,getpada);
        asha_pada_spinner.setAdapter(setpada);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int viewId = parent.getId();
        if (viewId == R.id.phc_spinner) {
                str_phc = userdata.get(position).getPhc();
                Log.i("ckm=>phc_spinner",str_phc);
                if (!str_phc.equals("Select Phc"))
                {
                    totalbyphc = PopulationMedicalModel.getAllOpdcountbyfilter("phc",str_phc,SharedPreference.get("CAMPID"),data1);
                    if (totalbyphc.size() > 0)
                    {
                        if (!isEmpty(totalbyphc.get(0).getPopultioncount()))
                        {
                            txttotalpopulationcount.setText(totalbyphc.get(0).getPopultioncount());
                        }
                        if (!isEmpty(totalbyphc.get(0).getHousholscount()))
                        {
                            txttotalhousecount.setText(totalbyphc.get(0).getHousholscount());
                        }
                        if (!isEmpty(totalbyphc.get(0).getEyeopdcount()))
                        {
                            txteyeopdcount.setText(totalbyphc.get(0).getEyeopdcount());
                        }
                        if (!isEmpty(totalbyphc.get(0).getEpfcount()))
                        {
                            txtepfcount.setText(totalbyphc.get(0).getEpfcount());
                        }
                        if (!isEmpty(totalbyphc.get(0).getCataractcount()))
                        {
                            txtCataractcount.setText(totalbyphc.get(0).getCataractcount());
                        }
                        if (!isEmpty(totalbyphc.get(0).getDentalopdcount()))
                        {
                            txtdentalcount.setText(totalbyphc.get(0).getDentalopdcount());
                        }
                        if (!isEmpty(totalbyphc.get(0).getPlasticsurgerycount()))
                        {
                            txtplasticsurgcount.setText(totalbyphc.get(0).getPlasticsurgerycount());
                        }
                        if (!isEmpty(totalbyphc.get(0).getOrthocount()))
                        {
                            txtorthosurgcount.setText(totalbyphc.get(0).getOrthocount());
                        }
                        if (!isEmpty(totalbyphc.get(0).getEntcount()))
                        {
                            txtentcount.setText(totalbyphc.get(0).getEntcount());
                        }
                        if (!isEmpty(totalbyphc.get(0).getGynaecount()))
                        {
                            txtgynaeccount.setText(totalbyphc.get(0).getGynaecount());
                        }
                        if (!isEmpty(totalbyphc.get(0).getOralcancercount()))
                        {
                            txtoralcanceropd.setText(totalbyphc.get(0).getOralcancercount());
                        }

                        subcenter_spinner.setSelection(0);
                        village_spinner.setSelection(0);
                        pada_spinner.setSelection(0);
                        btnDetails.setVisibility(View.GONE);
                        anmbtnDetails.setVisibility(View.GONE);
                        asha_pada_spinner.setSelection(0);
                        setpadainital();
                        asha_worker_spn.setSelection(0);
                        anm_pada_spinner.setSelection(0);
                        anm_worker_spn.setSelection(0);
                    }
                }
        } else if (viewId == R.id.subcenter_spinner) {
                str_subcenter = subcenterdata.get(position).getSubCenter();
                Log.i("ckm=>subcenter",str_subcenter);
                if (!str_subcenter.equals("Select Subcenter"))
                {
//                    phc_spinner.setSelection(0);
//                    village_spinner.setSelection(0);
//                    pada_spinner.setSelection(0);
                    setpadainital();
                    btnDetails.setVisibility(View.GONE);
                    anmbtnDetails.setVisibility(View.GONE);
//                    asha_pada_spinner.setSelection(0);
//                    asha_worker_spn.setSelection(0);
//                    anm_pada_spinner.setSelection(0);
//                    anm_worker_spn.setSelection(0);

                    totalbysubcenter = PopulationMedicalModel.getAllOpdcountbyfilter("subcentre",str_subcenter,SharedPreference.get("CAMPID"),data1);

                    if (totalbysubcenter.size() > 0)
                    {
                        txttotalpopulationcount.setText(totalbysubcenter.get(0).getPopultioncount());
                        txttotalhousecount.setText(totalbysubcenter.get(0).getHousholscount());
                    }
                    if (!isEmpty(totalbysubcenter.get(0).getEyeopdcount()))
                    {
                        txteyeopdcount.setText(totalbysubcenter.get(0).getEyeopdcount());
                    }
                    if (!isEmpty(totalbysubcenter.get(0).getEpfcount()))
                    {
                        txtepfcount.setText(totalbysubcenter.get(0).getEpfcount());
                    }
                    if (!isEmpty(totalbysubcenter.get(0).getCataractcount()))
                    {
                        txtCataractcount.setText(totalbysubcenter.get(0).getCataractcount());
                    }
                    if (!isEmpty(totalbysubcenter.get(0).getDentalopdcount()))
                    {
                        txtdentalcount.setText(totalbysubcenter.get(0).getDentalopdcount());
                    }
                    if (!isEmpty(totalbysubcenter.get(0).getPlasticsurgerycount()))
                    {
                        txtplasticsurgcount.setText(totalbysubcenter.get(0).getPlasticsurgerycount());
                    }
                    if (!isEmpty(totalbysubcenter.get(0).getOrthocount()))
                    {
                        txtorthosurgcount.setText(totalbysubcenter.get(0).getOrthocount());
                    }
                    if (!isEmpty(totalbysubcenter.get(0).getEntcount()))
                    {
                        txtentcount.setText(totalbysubcenter.get(0).getEntcount());
                    }
                    if (!isEmpty(totalbysubcenter.get(0).getGynaecount()))
                    {
                        txtgynaeccount.setText(totalbysubcenter.get(0).getGynaecount());
                    }
                    if (!isEmpty(totalbysubcenter.get(0).getOralcancercount()))
                    {
                        txtoralcanceropd.setText(totalbysubcenter.get(0).getOralcancercount());
                    }

                }
        } else if (viewId == R.id.village_spinner) {
//                phc_spinner.setSelection(0);
//                subcenter_spinner.setSelection(0);
//                pada_spinner.setSelection(0);
                setpadainital();
//                asha_pada_spinner.setSelection(0);
//                asha_worker_spn.setSelection(0);
//                anm_pada_spinner.setSelection(0);
//                anm_worker_spn.setSelection(0);
                btnDetails.setVisibility(View.GONE);
                anmbtnDetails.setVisibility(View.GONE);
                str_village = getvillage.get(position).getVillage();
                Log.i("ckm=>village",str_village);
                if (!str_village.equals("Select Village"))
                {
                    totalbyvillage = PopulationMedicalModel.getAllOpdcountbyfilter("village",str_village,SharedPreference.get("CAMPID"),data1);
                    if (totalbyvillage.size() > 0)
                    {
                        txttotalpopulationcount.setText(totalbyvillage.get(0).getPopultioncount());
                        txttotalhousecount.setText(totalbyvillage.get(0).getHousholscount());
                    }
                    if (!isEmpty(totalbyvillage.get(0).getEyeopdcount()))
                    {
                    txteyeopdcount.setText(totalbyvillage.get(0).getEyeopdcount());
                    }
                    if (!isEmpty(totalbyvillage.get(0).getEpfcount()))
                    {
                        txtepfcount.setText(totalbyvillage.get(0).getEpfcount());
                    }
                    if (!isEmpty(totalbyvillage.get(0).getCataractcount()))
                    {
                        txtCataractcount.setText(totalbyvillage.get(0).getCataractcount());
                    }
                    if (!isEmpty(totalbyvillage.get(0).getDentalopdcount()))
                    {
                        txtdentalcount.setText(totalbyvillage.get(0).getDentalopdcount());
                    }
                    if (!isEmpty(totalbyvillage.get(0).getPlasticsurgerycount()))
                    {
                        txtplasticsurgcount.setText(totalbyvillage.get(0).getPlasticsurgerycount());
                    }
                    if (!isEmpty(totalbyvillage.get(0).getOrthocount()))
                    {
                        txtorthosurgcount.setText(totalbyvillage.get(0).getOrthocount());
                    }
                    if (!isEmpty(totalbyvillage.get(0).getEntcount()))
                    {
                        txtentcount.setText(totalbyvillage.get(0).getEntcount());
                    }
                    if (!isEmpty(totalbyvillage.get(0).getGynaecount()))
                    {
                        txtgynaeccount.setText(totalbyvillage.get(0).getGynaecount());
                    }
                    if (!isEmpty(totalbyvillage.get(0).getOralcancercount()))
                    {
                        txtoralcanceropd.setText(totalbyvillage.get(0).getOralcancercount());
                    }
                }
        } else if (viewId == R.id.pada_spinner) {
//                phc_spinner.setSelection(0);
//                subcenter_spinner.setSelection(0);
//                village_spinner.setSelection(0);
               // pada_spinner.setSelection(0);
//                asha_pada_spinner.setSelection(0);
//                asha_worker_spn.setSelection(0);
//                anm_pada_spinner.setSelection(0);
//                anm_worker_spn.setSelection(0);
                setpadainital();
                btnDetails.setVisibility(View.GONE);
                anmbtnDetails.setVisibility(View.GONE);
                str_pada = mListNew.get(position).getPada();
                Log.i("ckm=>pada_spinner",str_pada);
                if (!str_pada.equals("Select Pada"))
                {
                    totalbypada = PopulationMedicalModel.getAllOpdcountbyfilter("pada",str_pada,SharedPreference.get("CAMPID"),data1);
                    if (totalbypada.size() > 0)
                    {
                        txttotalpopulationcount.setText(totalbypada.get(0).getPopultioncount());
                        txttotalhousecount.setText(totalbypada.get(0).getHousholscount());
                    }
                    if (!isEmpty(totalbypada.get(0).getEyeopdcount()))
                    {
                    txteyeopdcount.setText(totalbypada.get(0).getEyeopdcount());
                    }
                    if (!isEmpty(totalbypada.get(0).getEpfcount()))
                    {
                        txtepfcount.setText(totalbypada.get(0).getEpfcount());
                    }
                    if (!isEmpty(totalbypada.get(0).getCataractcount()))
                    {
                        txtCataractcount.setText(totalbypada.get(0).getCataractcount());
                    }
                    if (!isEmpty(totalbypada.get(0).getDentalopdcount()))
                    {
                        txtdentalcount.setText(totalbypada.get(0).getDentalopdcount());
                    }
                    if (!isEmpty(totalbypada.get(0).getPlasticsurgerycount()))
                    {
                        txtplasticsurgcount.setText(totalbypada.get(0).getPlasticsurgerycount());
                    }
                    if (!isEmpty(totalbypada.get(0).getOrthocount()))
                    {
                        txtorthosurgcount.setText(totalbypada.get(0).getOrthocount());
                    }
                    if (!isEmpty(totalbypada.get(0).getEntcount()))
                    {
                        txtentcount.setText(totalbypada.get(0).getEntcount());
                    }
                    if (!isEmpty(totalbypada.get(0).getGynaecount()))
                    {
                        txtgynaeccount.setText(totalbypada.get(0).getGynaecount());
                    }
                    if (!isEmpty(totalbypada.get(0).getOralcancercount()))
                    {
                        txtoralcanceropd.setText(totalbypada.get(0).getOralcancercount());
                    }
                }
        } else if (viewId == R.id.asha_worker_spn) {
//                phc_spinner.setSelection(0);
//                subcenter_spinner.setSelection(0);
//                village_spinner.setSelection(0);
//                pada_spinner.setSelection(0);
//                anm_pada_spinner.setSelection(0);
//                anm_worker_spn.setSelection(0);
//                asha_pada_spinner.setSelection(0);
                anmbtnDetails.setVisibility(View.GONE);


                str_asha_worker = padashaworker.get(position).getAshaworkername();
                Log.i("ashaworker",str_asha_worker);
                if (!str_asha_worker.equals("Select"))
                {
                    getpada.clear();
                    getpada = PadaNAshworkerDetailModel.getPadafromAsha(SharedPreference.get("CAMPID"),str_asha_worker);
                    PadanashaworkerData data = new PadanashaworkerData();
                    data.setPada("Select pada");
                    getpada.add(0,data);
                    Log.i("ckm=>PadaCount",getpada.size()+"");
                    CustomeSpinnerForAshaPada setpada = new CustomeSpinnerForAshaPada(this,getpada);
                    asha_pada_spinner.setAdapter(setpada);
                    ashworkercountarr = PopulationMedicalModel.getAllcountbyAshaname(str_asha_worker,SharedPreference.get("CAMPID"));
                   // asha_pada_spinner.setOnItemSelectedListener(this);
                    if (ashworkercountarr.size()> 0)
                    {
                       // arrgetashpadacount = PopulationMedicalModel.getAllOpdcountbyfilterForAsha(getpada.get(0).getPada(),SharedPreference.get("CAMPID"));
                        if (ashworkercountarr.size() > 0)
                        {
                            txttotalpopulationcount.setText(ashworkercountarr.get(0).getPopultioncount());
                            txttotalhousecount.setText(ashworkercountarr.get(0).getHousholscount());
                        }
                        if (!isEmpty(ashworkercountarr.get(0).getEyeopdcount()))
                        {
                            txteyeopdcount.setText(ashworkercountarr.get(0).getEyeopdcount());
                        }
                        if (!isEmpty(ashworkercountarr.get(0).getEpfcount()))
                        {
                            txtepfcount.setText(ashworkercountarr.get(0).getEpfcount());
                        }
                        if (!isEmpty(ashworkercountarr.get(0).getCataractcount()))
                        {
                            txtCataractcount.setText(ashworkercountarr.get(0).getCataractcount());
                        }
                        if (!isEmpty(ashworkercountarr.get(0).getDentalopdcount()))
                        {
                            txtdentalcount.setText(ashworkercountarr.get(0).getDentalopdcount());
                        }
                        if (!isEmpty(ashworkercountarr.get(0).getPlasticsurgerycount()))
                        {
                            txtplasticsurgcount.setText(ashworkercountarr.get(0).getPlasticsurgerycount());
                        }
                        if (!isEmpty(ashworkercountarr.get(0).getOrthocount()))
                        {
                            txtorthosurgcount.setText(ashworkercountarr.get(0).getOrthocount());
                        }
                        if (!isEmpty(ashworkercountarr.get(0).getEntcount()))
                        {
                            txtentcount.setText(ashworkercountarr.get(0).getEntcount());
                        }
                        if (!isEmpty(ashworkercountarr.get(0).getGynaecount()))
                        {
                            txtgynaeccount.setText(ashworkercountarr.get(0).getGynaecount());
                        }
                        if (!isEmpty(ashworkercountarr.get(0).getOralcancercount()))
                        {
                            txtoralcanceropd.setText(ashworkercountarr.get(0).getOralcancercount());
                        }
                    }
                }
                else
                {
                    btnDetails.setVisibility(View.GONE);
                    setpadainital();
                }
        } else if (viewId == R.id.anm_worker_spn) {
//                phc_spinner.setSelection(0);
//                subcenter_spinner.setSelection(0);
//                village_spinner.setSelection(0);
//                pada_spinner.setSelection(0);
//                asha_pada_spinner.setSelection(0);
//                asha_worker_spn.setSelection(0);
                str_anmwork = anmworker.get(position).getAnmname();
                Log.i("anmworker",str_anmwork);
                if (!str_anmwork.equals("Select"))
                {
                    getanmpada.clear();
                    getanmpada = PadaNAshworkerDetailModel.getPadafromAnm(SharedPreference.get("CAMPID"),str_anmwork);
                    PadanashaworkerData data = new PadanashaworkerData();
                    data.setPada("Select pada");
                    getanmpada.add(0,data);
                    Log.i("ckm=>PadaCount",getanmpada.size()+"");
                    CustomeSpinnerForAshaPada setpada = new CustomeSpinnerForAshaPada(this,getanmpada);
                    anm_pada_spinner.setAdapter(setpada);
                    ashworkercountarr = PopulationMedicalModel.getAllcountbyAnmname(str_anmwork,SharedPreference.get("CAMPID"));
                    // asha_pada_spinner.setOnItemSelectedListener(this);
                    if (ashworkercountarr.size()> 0)
                    {
                        // arrgetashpadacount = PopulationMedicalModel.getAllOpdcountbyfilterForAsha(getpada.get(0).getPada(),SharedPreference.get("CAMPID"));
                        if (ashworkercountarr.size() > 0)
                        {
                            txttotalpopulationcount.setText(ashworkercountarr.get(0).getPopultioncount());
                            txttotalhousecount.setText(ashworkercountarr.get(0).getHousholscount());
                        }
                        if (!isEmpty(ashworkercountarr.get(0).getEyeopdcount()))
                        {
                            txteyeopdcount.setText(ashworkercountarr.get(0).getEyeopdcount());
                        }
                        if (!isEmpty(ashworkercountarr.get(0).getEpfcount()))
                        {
                            txtepfcount.setText(ashworkercountarr.get(0).getEpfcount());
                        }
                        if (!isEmpty(ashworkercountarr.get(0).getCataractcount()))
                        {
                            txtCataractcount.setText(ashworkercountarr.get(0).getCataractcount());
                        }
                        if (!isEmpty(ashworkercountarr.get(0).getDentalopdcount()))
                        {
                            txtdentalcount.setText(ashworkercountarr.get(0).getDentalopdcount());
                        }
                        if (!isEmpty(ashworkercountarr.get(0).getPlasticsurgerycount()))
                        {
                            txtplasticsurgcount.setText(ashworkercountarr.get(0).getPlasticsurgerycount());
                        }
                        if (!isEmpty(ashworkercountarr.get(0).getOrthocount()))
                        {
                            txtorthosurgcount.setText(ashworkercountarr.get(0).getOrthocount());
                        }
                        if (!isEmpty(ashworkercountarr.get(0).getEntcount()))
                        {
                            txtentcount.setText(ashworkercountarr.get(0).getEntcount());
                        }
                        if (!isEmpty(ashworkercountarr.get(0).getGynaecount()))
                        {
                            txtgynaeccount.setText(ashworkercountarr.get(0).getGynaecount());
                        }
                        if (!isEmpty(ashworkercountarr.get(0).getOralcancercount()))
                        {
                            txtoralcanceropd.setText(ashworkercountarr.get(0).getOralcancercount());
                        }
                    }
                }
                else
                {
                    anmbtnDetails.setVisibility(View.GONE);
                    setpadainital();
                }
        } else if (viewId == R.id.spinner_dashboard) {
//                phc_spinner.setSelection(0);
//                subcenter_spinner.setSelection(0);
//                village_spinner.setSelection(0);
//                pada_spinner.setSelection(0);
//                asha_pada_spinner.setSelection(0);
//                asha_worker_spn.setSelection(0);
                data1 = newuserlist.get(position).getUserId().toString();
                Log.i("ckm=>Data",data1);
                gethouseNpopulationCount(data1);
                getSeperateOPDCount(data1);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void gethouseNpopulationCount(final String userid)
    {
        total_household = PopulationMedicalModel.gethouseholdcount(SharedPreference.get("CAMPID"),userid);
        txttotalhousecount.setText(String.valueOf(total_household));

        total_population = PopulationMedicalModel.gettotalpopulationcount(SharedPreference.get("CAMPID"),userid);
        txttotalpopulationcount.setText(String.valueOf(total_population));

    }

    public void getSeperateOPDCount(final String userid)
    {
        //For EyeOpdCount
        eyeopdcount = PopulationMedicalModel.getEyeopdCount(SharedPreference.get("CAMPID"),userid);
        txteyeopdcount.setText(String.valueOf(eyeopdcount));

        //For Cataract
        cataract = PopulationMedicalModel.getCataractCount(SharedPreference.get("CAMPID"),userid);
        txtCataractcount.setText(String.valueOf(cataract));

        //For Epf
        epfcount = PopulationMedicalModel.getepfCount(SharedPreference.get("CAMPID"),userid);
        txtepfcount.setText(String.valueOf(epfcount));

        //For dental
        dentalcount = PopulationMedicalModel.getdentalCount(SharedPreference.get("CAMPID"),userid);
        txtdentalcount.setText(String.valueOf(dentalcount));

        //For plastic surgerycount
        plasticsurgerycount = PopulationMedicalModel.getplasticsurgereryCount(SharedPreference.get("CAMPID"),userid);
        txtplasticsurgcount.setText(String.valueOf(plasticsurgerycount));

        //For Orthopedic
        orthosurgerycount = PopulationMedicalModel.getorthopedicCount(SharedPreference.get("CAMPID"),userid);
        txtorthosurgcount.setText(String.valueOf(orthosurgerycount));

        // For Ent
        entcount = PopulationMedicalModel.getEntOpdCount(SharedPreference.get("CAMPID"),userid);
        txtentcount.setText(String.valueOf(entcount));

        //For gynac
        gynacecount = PopulationMedicalModel.getgynaecopdCount(SharedPreference.get("CAMPID"),userid);
        txtgynaeccount.setText(String.valueOf(gynacecount));

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.txteyeopddetails) {
                if(!txteyeopdcount.getText().toString().equals("0"))
                {
                    if (!isEmpty(str_phc) && !str_phc.equals("Select Phc"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Eye OPD ");
                        i.putExtra("OPD","eyeopd");

                        i.putExtra("type","phc");
                        i.putExtra("val",str_phc);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_subcenter) && !str_subcenter.equals("Select Subcenter"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Eye OPD ");
                        i.putExtra("OPD","eyeopd");
                        i.putExtra("type","subcentre");
                        i.putExtra("val",str_subcenter);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_village) && !str_village.equals("Select Village"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Eye OPD ");
                        i.putExtra("OPD","eyeopd");
                        i.putExtra("type","village");
                        i.putExtra("val",str_village);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_pada) && !str_pada.equals("Select Pada"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Eye OPD ");
                        i.putExtra("OPD","eyeopd");
                        i.putExtra("type","pada");
                        i.putExtra("val",str_pada);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_asha_worker) && !str_asha_worker.equals("Select"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Eye OPD ");
                        i.putExtra("OPD","eyeopd");
                        i.putExtra("type","ashaworkername");
                        i.putExtra("val",str_asha_worker);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_anmwork) && !str_anmwork.equals("Select"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Eye OPD ");
                        i.putExtra("OPD","eyeopd");
                        i.putExtra("type","anmname");
                        i.putExtra("val",str_anmwork);
                        startActivity(i);
                    }
                    else
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Eye OPD ");
                        i.putExtra("OPD","eyeopd");
                        startActivity(i);
                    }
                }
        } else if (id == R.id.txtcataractdetails) {
                if(!txtCataractcount.getText().toString().equals("0"))
                {
                    if (!isEmpty(str_phc) && !str_phc.equals("Select Phc"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Cataract ");
                        i.putExtra("OPD","eyesurgery");
                        i.putExtra("type","phc");
                        i.putExtra("val",str_phc);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_subcenter) && !str_subcenter.equals("Select Subcenter"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Cataract ");
                        i.putExtra("OPD","eyesurgery");
                        i.putExtra("type","subcentre");
                        i.putExtra("val",str_subcenter);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_village) && !str_village.equals("Select Village"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Cataract ");
                        i.putExtra("OPD","eyesurgery");
                        i.putExtra("type","village");
                        i.putExtra("val",str_village);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_pada) && !str_pada.equals("Select Pada"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Cataract ");
                        i.putExtra("OPD","eyesurgery");
                        i.putExtra("type","pada");
                        i.putExtra("val",str_pada);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_asha_worker) && !str_asha_worker.equals("Select"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Cataract ");
                        i.putExtra("OPD","eyesurgery");
                        i.putExtra("type","ashaworkername");
                        i.putExtra("val",str_asha_worker);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_anmwork) && !str_anmwork.equals("Select"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Cataract ");
                        i.putExtra("OPD","eyesurgery");
                        i.putExtra("type","anmname");
                        i.putExtra("val",str_anmwork);
                        startActivity(i);
                    }
                    else
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Cataract ");
                        i.putExtra("OPD","eyesurgery");
                        startActivity(i);
                    }

                }
        } else if (id == R.id.txtepfdetails) {
                if(!txtepfcount.getText().toString().equals("0"))
                {
                    if (!isEmpty(str_phc) && !str_phc.equals("Select Phc"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Epilepsy OPD ");
                        i.putExtra("OPD","epilepsyopd");
                        i.putExtra("type","phc");
                        i.putExtra("val",str_phc);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_subcenter) && !str_subcenter.equals("Select Subcenter"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Epilepsy OPD ");
                        i.putExtra("OPD","epilepsyopd");
                        i.putExtra("type","subcentre");
                        i.putExtra("val",str_subcenter);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_village) && !str_village.equals("Select Village"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Epilepsy OPD ");
                        i.putExtra("OPD","epilepsyopd");
                        i.putExtra("type","village");
                        i.putExtra("val",str_village);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_pada) && !str_pada.equals("Select Pada"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Epilepsy OPD ");
                        i.putExtra("OPD","epilepsyopd");
                        i.putExtra("type","pada");
                        i.putExtra("val",str_pada);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_asha_worker) && !str_asha_worker.equals("Select"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Epilepsy OPD ");
                        i.putExtra("OPD","epilepsyopd");
                        i.putExtra("type","ashaworkername");
                        i.putExtra("val",str_asha_worker);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_anmwork) && !str_anmwork.equals("Select"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Epilepsy OPD ");
                        i.putExtra("OPD","epilepsyopd");
                        i.putExtra("type","anmname");
                        i.putExtra("val",str_anmwork);
                        startActivity(i);
                    }
                    else
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Epilepsy OPD ");
                        i.putExtra("OPD","epilepsyopd");
                        startActivity(i);
                    }

                }
        } else if (id == R.id.txtdentaldetails) {
                if(!txtdentalcount.getText().toString().equals("0"))
                {
                    if (!isEmpty(str_phc) && !str_phc.equals("Select Phc"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Dental OPD ");
                        i.putExtra("OPD","dentalopd");
                        i.putExtra("type","phc");
                        i.putExtra("val",str_phc);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_subcenter) && !str_subcenter.equals("Select Subcenter"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Dental OPD ");
                        i.putExtra("OPD","dentalopd");
                        i.putExtra("type","subcentre");
                        i.putExtra("val",str_subcenter);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_village) && !str_village.equals("Select Village"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Dental OPD ");
                        i.putExtra("OPD","dentalopd");
                        i.putExtra("type","village");
                        i.putExtra("val",str_village);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_pada) && !str_pada.equals("Select Pada"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Dental OPD ");
                        i.putExtra("OPD","dentalopd");
                        i.putExtra("type","pada");
                        i.putExtra("val",str_pada);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_asha_worker) && !str_asha_worker.equals("Select"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Dental OPD ");
                        i.putExtra("OPD","dentalopd");
                        i.putExtra("type","ashaworkername");
                        i.putExtra("val",str_asha_worker);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_anmwork) && !str_anmwork.equals("Select"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Dental OPD ");
                        i.putExtra("OPD","dentalopd");
                        i.putExtra("type","anmname");
                        i.putExtra("val",str_anmwork);
                        startActivity(i);
                    }
                    else
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Eye OPD ");
                        i.putExtra("OPD","dentalopd");
                        startActivity(i);
                    }

                }
        } else if (id == R.id.txtplasticsurdetails) {
                if(!txtplasticsurgcount.getText().toString().equals("0"))
                {
                    if (!isEmpty(str_phc) && !str_phc.equals("Select Phc"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Plastic Surgery OPD ");
                        i.putExtra("OPD","plasticsurgeryopd");
                        i.putExtra("type","phc");
                        i.putExtra("val",str_phc);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_subcenter) && !str_subcenter.equals("Select Subcenter"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Plastic Surgery OPD ");
                        i.putExtra("OPD","plasticsurgeryopd");
                        i.putExtra("type","subcentre");
                        i.putExtra("val",str_subcenter);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_village) && !str_village.equals("Select Village"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Plastic Surgery OPD ");
                        i.putExtra("OPD","plasticsurgeryopd");
                        i.putExtra("type","village");
                        i.putExtra("val",str_village);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_pada) && !str_pada.equals("Select Pada"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Plastic Surgery OPD ");
                        i.putExtra("OPD","plasticsurgeryopd");
                        i.putExtra("type","pada");
                        i.putExtra("val",str_pada);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_asha_worker) && !str_asha_worker.equals("Select"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Plastic Surgery OPD ");
                        i.putExtra("OPD","plasticsurgeryopd");
                        i.putExtra("type","ashaworkername");
                        i.putExtra("val",str_asha_worker);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_anmwork) && !str_anmwork.equals("Select"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Plastic Surgery OPD ");
                        i.putExtra("OPD","plasticsurgeryopd");
                        i.putExtra("type","anmname");
                        i.putExtra("val",str_anmwork);
                        startActivity(i);
                    }
                    else
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Plastic Surgery OPD ");
                        i.putExtra("OPD","plasticsurgeryopd");
                        startActivity(i);
                    }

                }
        } else if (id == R.id.txtorthosurdetails) {
                if(!txtorthosurgcount.getText().toString().equals("0"))
                {
                    if (!isEmpty(str_phc) && !str_phc.equals("Select Phc"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Orthopaedic Surgery OPD ");
                        i.putExtra("OPD","orthopedicsurgery");
                        i.putExtra("type","phc");
                        i.putExtra("val",str_phc);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_subcenter) && !str_subcenter.equals("Select Subcenter"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Orthopaedic Surgery OPD ");
                        i.putExtra("OPD","orthopedicsurgery");
                        i.putExtra("type","subcentre");
                        i.putExtra("val",str_subcenter);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_village) && !str_village.equals("Select Village"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Orthopaedic Surgery OPD ");
                        i.putExtra("OPD","orthopedicsurgery");
                        i.putExtra("type","village");
                        i.putExtra("val",str_village);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_pada) && !str_pada.equals("Select Pada"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Orthopaedic Surgery OPD ");
                        i.putExtra("OPD","orthopedicsurgery");
                        i.putExtra("type","pada");
                        i.putExtra("val",str_pada);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_asha_worker) && !str_asha_worker.equals("Select"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Orthopaedic Surgery OPD ");
                        i.putExtra("OPD","orthopedicsurgery");
                        i.putExtra("type","ashaworkername");
                        i.putExtra("val",str_asha_worker);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_anmwork) && !str_anmwork.equals("Select"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Orthopaedic Surgery OPD ");
                        i.putExtra("OPD","orthopedicsurgery");
                        i.putExtra("type","anmname");
                        i.putExtra("val",str_anmwork);
                        startActivity(i);
                    }
                    else
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Orthopaedic Surgery OPD ");
                        i.putExtra("OPD","orthopedicsurgery");
                        startActivity(i);
                    }
                }
        } else if (id == R.id.txtentdetails) {
                if(!txtentcount.getText().toString().equals("0"))
                {
                    if (!isEmpty(str_phc) && !str_phc.equals("Select Phc"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","ENT OPD ");
                        i.putExtra("OPD","entopd");
                        i.putExtra("type","phc");
                        i.putExtra("val",str_phc);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_subcenter) && !str_subcenter.equals("Select Subcenter"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","ENT OPD ");
                        i.putExtra("OPD","entopd");
                        i.putExtra("type","subcentre");
                        i.putExtra("val",str_subcenter);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_village) && !str_village.equals("Select Village"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","ENT OPD ");
                        i.putExtra("OPD","entopd");
                        i.putExtra("type","village");
                        i.putExtra("val",str_village);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_pada) && !str_pada.equals("Select Pada"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","ENT OPD ");
                        i.putExtra("OPD","entopd");
                        i.putExtra("type","pada");
                        i.putExtra("val",str_pada);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_asha_worker) && !str_asha_worker.equals("Select"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","ENT OPD ");
                        i.putExtra("OPD","entopd");
                        i.putExtra("type","ashaworkername");
                        i.putExtra("val",str_asha_worker);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_anmwork) && !str_anmwork.equals("Select"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","ENT OPD ");
                        i.putExtra("OPD","entopd");
                        i.putExtra("type","anmname");
                        i.putExtra("val",str_anmwork);
                        startActivity(i);
                    }
                    else
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","ENT OPD ");
                        i.putExtra("OPD","entopd");
                        startActivity(i);
                    }

                }
        } else if (id == R.id.txtgynaecdetails) {
                if(!txtgynaeccount.getText().toString().equals("0"))
                {
                    if (!isEmpty(str_phc) && !str_phc.equals("Select Phc"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Gyanec OPD ");
                        i.putExtra("OPD","gynaecopd");

                        i.putExtra("type","phc");
                        i.putExtra("val",str_phc);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_subcenter) && !str_subcenter.equals("Select Subcenter"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Gyanec OPD ");
                        i.putExtra("OPD","gynaecopd");

                        i.putExtra("type","subcentre");
                        i.putExtra("val",str_subcenter);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_village) && !str_village.equals("Select Village"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Gyanec OPD ");
                        i.putExtra("OPD","gynaecopd");

                        i.putExtra("type","village");
                        i.putExtra("val",str_village);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_pada) && !str_pada.equals("Select Pada"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Gyanec OPD ");
                        i.putExtra("OPD","gynaecopd");

                        i.putExtra("type","pada");
                        i.putExtra("val",str_pada);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_asha_worker) && !str_asha_worker.equals("Select"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Gyanec OPD ");
                        i.putExtra("OPD","gynaecopd");

                        i.putExtra("type","ashaworkername");
                        i.putExtra("val",str_asha_worker);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_anmwork) && !str_anmwork.equals("Select"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Gyanec OPD ");
                        i.putExtra("OPD","gynaecopd");

                        i.putExtra("type","anmname");
                        i.putExtra("val",str_anmwork);
                        startActivity(i);
                    }
                    else
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Gyanec OPD ");
                        i.putExtra("OPD","gynaecopd");

                        startActivity(i);
                    }
                }
        } else if (id == R.id.txtdetailoralcancer) {
                if (!txtoralcanceropd.getText().toString().equals("0"))
                {
                    Log.i("ckm=>NOTZERO","Not ZERo");
                    if (!isEmpty(str_phc) && !str_phc.equals("Select Phc"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Oral Cancer OPD ");
                        i.putExtra("OPD","oralcanceropd");
                        i.putExtra("type","phc");
                        i.putExtra("val",str_phc);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_subcenter) && !str_subcenter.equals("Select Subcenter"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Oral Cancer OPD ");
                        i.putExtra("OPD","oralcanceropd");
                        i.putExtra("type","subcentre");
                        i.putExtra("val",str_subcenter);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_village) && !str_village.equals("Select Village"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Oral Cancer OPD ");
                        i.putExtra("OPD","oralcanceropd");
                        i.putExtra("type","village");
                        i.putExtra("val",str_village);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_pada) && !str_pada.equals("Select Pada"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Oral Cancer OPD ");
                        i.putExtra("OPD","oralcanceropd");
                        i.putExtra("type","pada");
                        i.putExtra("val",str_pada);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_asha_worker) && !str_asha_worker.equals("Select"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Oral Cancer OPD ");
                        i.putExtra("OPD","oralcanceropd");
                        i.putExtra("type","ashaworkername");
                        i.putExtra("val",str_asha_worker);
                        startActivity(i);
                    }
                    else if (!isEmpty(str_anmwork) && !str_anmwork.equals("Select"))
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Oral Cancer OPD ");
                        i.putExtra("OPD","oralcanceropd");
                        i.putExtra("type","anmname");
                        i.putExtra("val",str_anmwork);
                        startActivity(i);
                    }
                    else
                    {
                        Intent i = new Intent(this,OpddetailActivity.class);
                        i.putExtra("Title","Oral Cancer OPD ");
                        i.putExtra("OPD","oralcanceropd");
                        startActivity(i);
                    }
                }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}