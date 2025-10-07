package org.impactindia.llemeddocket.ui.activity;

import android.app.DatePickerDialog;
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

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.impactindia.llemeddocket.AttributeSet;
import org.impactindia.llemeddocket.DatabaseHelper;
import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.adapter.CustomAutocomVillageAdapter;
import org.impactindia.llemeddocket.adapter.CustomSpinnerSubCenter;
import org.impactindia.llemeddocket.adapter.CustomeSpinnerAdapter;
import org.impactindia.llemeddocket.adapter.PadaUserList;
import org.impactindia.llemeddocket.adapter.PadalistAdapter;
import org.impactindia.llemeddocket.adapter.SpinnerSubCenters;
import org.impactindia.llemeddocket.adapter.VillagelistAdapter;
import org.impactindia.llemeddocket.orm.PadaNAshworkerDetailModel;
import org.impactindia.llemeddocket.orm.PhcSubCenterModel;
import org.impactindia.llemeddocket.orm.PhcUsersModel;
import org.impactindia.llemeddocket.pojo.PadanashaworkerData;
import org.impactindia.llemeddocket.pojo.PhcSubcenterData;
import org.impactindia.llemeddocket.pojo.PhcUserData;
import org.impactindia.llemeddocket.util.SharedPreference;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DailyworkPhcPadaActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    Toolbar toolbar;
   // MultiSelectionSpinner mulSelSpn;
    Button btnAddPada,btnAddHousehold,btnWorkbegin;
    AutoCompleteTextView selet_village,selet_pada;
    SQLiteDatabase db;
    TextView txtPadaDate;
    PadaUserList PadauserListAdapter;
    EditText edtanmName,edtanmMobno,edtashaname,edtashamobileno,edtgrampanchyatname,edtcontactperson,edtcontmobileno;
    ArrayList<PhcUserData> userdata = new ArrayList<PhcUserData>();
    ArrayList<PhcUserData> getvillage = new ArrayList<PhcUserData> ();
    ArrayList<PhcUserData> subcenterdata = new ArrayList<PhcUserData>();
    ArrayList<PhcSubcenterData> getpada = new ArrayList<PhcSubcenterData>();
    ArrayList<PadanashaworkerData> checkdata = new ArrayList<PadanashaworkerData>();
    PadalistAdapter adapternew;
    VillagelistAdapter villagelistAdapter;

    List<PhcUserData> mList;
    String padadate,dateString2;

    Spinner spinner_phc,spinner_subcentre;
    String date1;
    LocalDate endDate,startDate,todayplus;
    String spnStrphc,spnStrSubcenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailywork_phc_pada);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content),
                (view, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    Insets ime = insets.getInsets(WindowInsetsCompat.Type.ime());

                    int bottom = Math.max(systemBars.bottom, ime.bottom);

                    view.setPadding(systemBars.left, systemBars.top, systemBars.right, bottom);
                    return insets;
                });


        SharedPreference.initialize(this);
        init();
        db = new DatabaseHelper(this).getWritableDatabase();

        //For Set Current Date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");

        Calendar c = Calendar.getInstance();
        date1 = sdf.format(c.getTime());
        txtPadaDate.setText(date1);

        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateString2 = new SimpleDateFormat("yyyy-MM-dd").format(date);
        Log.i("ckm=>Dateyyymmdd",dateString2);
    }

    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {

        String myFormat = AttributeSet.Constants.REVERSE_SHORT_DATE; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        String myFormat1 = AttributeSet.Constants.SHORT_DATE_FORMAT; //In which you need put here
        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);

        Log.i("ckm=>updateLable",sdf.format(myCalendar.getTime()));
        String end = SharedPreference.get("OutreachTo");
        Log.i("end",end);
        String start = SharedPreference.get("OutreachFrom");
        Log.i("start",start);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(AttributeSet.Constants.REVERSE_SHORT_DATE);
        try {
            endDate = LocalDate.parse(SharedPreference.get("OutreachTo"),formatter);
            Log.i("ckm=>ENDATE",endDate+"");
            startDate = LocalDate.parse(SharedPreference.get("OutreachFrom"),formatter);
            Log.i("ckm=>STARTDATE",startDate+"");
            LocalDate today = LocalDate.now();
            Log.i("ckm=>TODAYDATE",startDate+"");
            todayplus = LocalDate.now().plusDays(1);
            Log.i("ckm=>todayDate",today+"");
            Log.i("ckm=>todayplus",todayplus+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Get selected Date from date Picker
        String today_date = sdf.format(myCalendar.getTime());
        //Convert String Date to Local Date
        LocalDate localDate = LocalDate.parse(today_date);
        Log.i("cccc",localDate+"");

        if (localDate.isAfter(endDate)) {
            Toast.makeText(this,"Please select between Outreach date",Toast.LENGTH_SHORT).show();
            txtPadaDate.setText(date1);
            Log.i("ckm=>","after end date");
        } else if (localDate.isBefore(startDate)) {
            Toast.makeText(this,"Please select between Outreach date",Toast.LENGTH_SHORT).show();
           // Log.i("ckm=>","before start date");
            txtPadaDate.setText(date1);
        }
        else if (localDate.equals(todayplus) || localDate.isAfter(todayplus) )
        {
            txtPadaDate.setText(date1);
            Toast.makeText(this,"Please select less than current date",Toast.LENGTH_SHORT).show();
        }
        else
        {
            String serversenddate = sdf.format(myCalendar.getTime());
            Log.i("ckm=>serverdate",serversenddate);
            txtPadaDate.setText(sdf1.format(myCalendar.getTime()));
        }

        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(txtPadaDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateString2 = new SimpleDateFormat("yyyy-MM-dd").format(date);
        Log.i("ckm=>Dateyyymmdd",dateString2);
    }



    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Daily Work");
        db = new DatabaseHelper(this).getReadableDatabase();
        PhcUsersModel.getInstance(getApplicationContext());
        PhcSubCenterModel.getInstance(getApplicationContext());
        PadaNAshworkerDetailModel.getInstance(this);
        btnAddPada = findViewById(R.id.btnAddPada);
        btnAddHousehold = findViewById(R.id.btnAddHousehold);
        btnWorkbegin = findViewById(R.id.btnWorkbegin);
        txtPadaDate = findViewById(R.id.txtPadaDate);

        selet_village = findViewById(R.id.selet_village);
        selet_pada = findViewById(R.id.selet_pada);


        spinner_phc = findViewById(R.id.spinner_phc);
        spinner_subcentre = findViewById(R.id.spinner_subcentre);
        spinner_phc.setOnItemSelectedListener(this);
        spinner_subcentre.setOnItemSelectedListener(this);



        edtanmName = findViewById(R.id.edtanmName);
        edtanmMobno = findViewById(R.id.edtanmMobno);
        edtashaname = findViewById(R.id.edtashaname);
        edtashamobileno = findViewById(R.id.edtashamobileno);
        edtgrampanchyatname = findViewById(R.id.edtgrampanchyatname);
        edtcontactperson = findViewById(R.id.edtcontactperson);
        edtcontmobileno = findViewById(R.id.edtcontmobileno);

        PhcUsersModel.open();
        userdata = PhcUsersModel.getPhcByUserId(SharedPreference.get("Userid"),SharedPreference.get("CAMPID"));
        CustomeSpinnerAdapter adapter = new CustomeSpinnerAdapter(this,userdata);
        spinner_phc.setAdapter(adapter);
        spinner_phc.setOnItemSelectedListener(this);




        txtPadaDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(DailyworkPhcPadaActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnWorkbegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty(selet_village.getText().toString()))
                {
                    shortToast("Please enter village name");
                }
                else if (isEmpty(selet_pada.getText().toString()))
                {
                    shortToast("Please enter pada name");
                }
                else
                {
                    checkdata = PadaNAshworkerDetailModel.getcampprogramdata(SharedPreference.get("Userid"),spinner_phc.getSelectedItemPosition() == 0 ? userdata.get(0).getPhc(): spnStrphc,
                        spinner_subcentre.getSelectedItemPosition() == 0 ? subcenterdata.get(0).getSubcenter(): spnStrSubcenter,selet_village.getText().toString(),selet_pada.getText().toString(),SharedPreference.get("CAMPID"));
                    if (checkdata.size() > 0)
                    {
                        if (!isEmpty(checkdata.get(0).getAnmname()))
                        {
                            edtanmName.setText(checkdata.get(0).getAnmname());
                        }
                        if (!isEmpty(checkdata.get(0).getAnmmobileno()))
                        {
                            edtanmMobno.setText(checkdata.get(0).getAnmmobileno());
                        }
                        edtashaname.setText(checkdata.get(0).getAshaworkername());
                        edtashamobileno.setText(checkdata.get(0).getAshaworkermobile());
                        edtgrampanchyatname.setText(checkdata.get(0).getGpname());
                        edtcontactperson.setText(checkdata.get(0).getContactpersonname());
                        edtcontmobileno.setText(checkdata.get(0).getCpmobile());

                    }
                    else
                    {
                        edtanmName.setText("");
                        edtanmMobno.setText("");
                        edtashaname.setText("");
                        edtashamobileno.setText("");
                        edtgrampanchyatname.setText("");
                        edtcontactperson.setText("");
                        edtcontmobileno.setText("");
                    }
                }
            }
        });


        btnAddHousehold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isEmpty(selet_village.getText().toString()))
                {
                    shortToast("Please enter village name");
                }
                else if (isEmpty(selet_pada.getText().toString()))
                {
                    shortToast("Please enter pada name");
                }
                else
                {
                    Log.i("ckm=>pada",txtPadaDate.getText().toString());
                    padadate = txtPadaDate.getText().toString();

                    Date date = null;
                    try {
                        date = new SimpleDateFormat("dd/MM/yyyy").parse(padadate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String dateString2 = new SimpleDateFormat("yyyy-MM-dd").format(date);
                    Log.i("ckm=>Dateyyymmdd",dateString2);

                    PadanashaworkerData data = new PadanashaworkerData(Integer.valueOf(SharedPreference.get("CAMPID")),Integer.valueOf(SharedPreference.get("Userid")),SharedPreference.get("OutreachFrom"),
                            dateString2,selet_pada.getText().toString(),selet_village.getText().toString(),spinner_phc.getSelectedItemPosition() == 0 ? userdata.get(0).getPhc(): spnStrphc,
                            spinner_subcentre.getSelectedItemPosition() == 0 ? subcenterdata.get(0).getSubcenter(): spnStrSubcenter,edtanmName.getText().toString(),edtanmMobno.getText().toString()
                            ,edtashaname.getText().toString(),edtashamobileno.getText().toString(),edtgrampanchyatname.getText().toString(),edtcontactperson.getText().toString(),edtcontmobileno.getText().toString(),0);
                    PadaNAshworkerDetailModel.open();

                    //Working code for check Phc AND PADA
                    checkdata = PadaNAshworkerDetailModel.getcampprogramdata(SharedPreference.get("Userid"),spinner_phc.getSelectedItemPosition() == 0 ? userdata.get(0).getPhc(): spnStrphc,
                            spinner_subcentre.getSelectedItemPosition() == 0 ? subcenterdata.get(0).getSubcenter(): spnStrSubcenter,selet_village.getText().toString(),selet_pada.getText().toString(),SharedPreference.get("CAMPID"));



                    if (checkdata.size() > 0)
                    {
                        SharedPreference.save("LastInsertID",checkdata.get(0).getId());
                        Toast.makeText(getApplicationContext(),"Phc and pada details Update successfully",Toast.LENGTH_LONG).show();
                        PadaNAshworkerDetailModel.UpdatepadaAshdata(String.valueOf(checkdata.get(0).getId()),data);
                        Intent intent = new Intent(DailyworkPhcPadaActivity.this,AddHouseholdActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Long last_insertID = PadaNAshworkerDetailModel.insert(data);
                        if (last_insertID != -1 )
                        {
                            SharedPreference.save("LastInsertID",last_insertID);
                            Toast.makeText(getApplicationContext(),"PHC and Pada details added successfully",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(DailyworkPhcPadaActivity.this,AddHouseholdActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        Log.i("InsertedID",last_insertID+"");
                    }

                }

            }
        });

        btnAddPada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isEmpty(selet_village.getText().toString()))
                {
                    shortToast("Please enter village name");
                }
                else if (isEmpty(selet_pada.getText().toString()))
                {
                    shortToast("Please enter pada name");
                }
                else if (isEmpty(edtashaname.getText().toString()))
                {
                    shortToast("Please enter ASHA Worker name");
                }
                else if (isEmpty(edtashamobileno.getText().toString()))
                {
                    shortToast("Please enter ASHA Worker Mobile No");
                }
                else if (edtashamobileno.getText().toString().length() != 10)
                {
                    shortToast("please enter valid mobile no");
                }
                else if (isEmpty(edtgrampanchyatname.getText().toString()))
                {
                    shortToast("Please enter gram panchayat name");
                }
                else if (isEmpty(edtcontactperson.getText().toString()))
                {
                    shortToast("Please enter contact person name");
                }
                else if(isEmpty(edtcontmobileno.getText().toString()))
                {
                    shortToast("please enter mobileno");
                }
                else
                {
                    Log.i("ckm=>pada",txtPadaDate.getText().toString());
                    padadate = txtPadaDate.getText().toString();

                    Date date = null;
                    try {
                        date = new SimpleDateFormat("dd/MM/yyyy").parse(padadate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String dateString2 = new SimpleDateFormat("yyyy-MM-dd").format(date);
                    Log.i("ckm=>Dateyyymmdd",dateString2);

                //Working code comment for no camp in database


                    Log.i("ckm=>subcenter",spinner_subcentre.getSelectedItemPosition() == 0 ? subcenterdata.get(0).getSubcenter(): spnStrSubcenter);
                    Log.i("ckm=>village",selet_village.getText().toString());
                    Log.i("ckm=>pada",selet_pada.getText().toString());
                    Log.i("ckm=>phc",spinner_phc.getSelectedItemPosition() == 0 ? userdata.get(0).getPhc(): spnStrphc);

                    PadanashaworkerData data = new PadanashaworkerData(Integer.valueOf(SharedPreference.get("CAMPID")),Integer.valueOf(SharedPreference.get("Userid")),SharedPreference.get("OutreachFrom"),
                            dateString2,selet_pada.getText().toString(),selet_village.getText().toString(),spinner_phc.getSelectedItemPosition() == 0 ? userdata.get(0).getPhc(): spnStrphc,
                            spinner_subcentre.getSelectedItemPosition() == 0 ? subcenterdata.get(0).getSubcenter(): spnStrSubcenter,edtanmName.getText().toString(),edtanmMobno.getText().toString()
                            ,edtashaname.getText().toString(),edtashamobileno.getText().toString(),edtgrampanchyatname.getText().toString(),edtcontactperson.getText().toString(),edtcontmobileno.getText().toString(),0);
                    PadaNAshworkerDetailModel.open();

                                 //Working code for check Phc AND PADA
                    checkdata = PadaNAshworkerDetailModel.getcampprogramdata(SharedPreference.get("Userid"),spinner_phc.getSelectedItemPosition() == 0 ? userdata.get(0).getPhc(): spnStrphc,
                            spinner_subcentre.getSelectedItemPosition() == 0 ? subcenterdata.get(0).getSubcenter(): spnStrSubcenter,selet_village.getText().toString(),selet_pada.getText().toString(),SharedPreference.get("CAMPID"));

                    if (checkdata.size() > 0)
                    {
                        SharedPreference.save("LastInsertID",checkdata.get(0).getId());
                        Toast.makeText(getApplicationContext(),"Pada and Health worker details Update successfully",Toast.LENGTH_LONG).show();
                        PadaNAshworkerDetailModel.UpdatepadaAshdata(String.valueOf(checkdata.get(0).getId()),data);
                        Intent intent = new Intent(DailyworkPhcPadaActivity.this,AddHouseholdActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Long last_insertID = PadaNAshworkerDetailModel.insert(data);
                        if (last_insertID != -1 )
                        {
                            SharedPreference.save("LastInsertID",last_insertID);
                            Toast.makeText(getApplicationContext(),"Pada and Health workers details added successfully",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(DailyworkPhcPadaActivity.this,AddHouseholdActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        Log.i("InsertedID",last_insertID+"");
                    }
                }
                //PadaNAshworkerDetailModel.open();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        int viewId = parent.getId();

        if (viewId == R.id.spinner_phc) {

             //  Toast.makeText(getApplicationContext(), userdata.get(position).getPhc(), Toast.LENGTH_LONG).show();
               spnStrphc = userdata.get(position).getPhc();
               Log.i("ckm=>selected",userdata.get(position).getPhc());
               if (spinner_phc.getSelectedItemPosition() == 0)
               {
                   spnStrphc = userdata.get(0).getPhc();
                   Log.i("ckm=>ZeroPosition1",spnStrphc);
               }


               PhcSubCenterModel.open();

               subcenterdata = PhcUsersModel.getSubCenterByUserID(SharedPreference.get("Userid"),userdata.get(position).getPhc(),SharedPreference.get("CAMPID"));
               SpinnerSubCenters spinnersubcenters = new SpinnerSubCenters(this,subcenterdata);
               spinner_subcentre.setAdapter(spinnersubcenters);
               String item = String.valueOf(parent.getItemAtPosition(position));
               Log.i("ckm=>selectedPosition",item);
        } else if (viewId == R.id.spinner_subcentre) {
                if (spinner_subcentre.getSelectedItemPosition() == 0)
                {
                    spnStrSubcenter = subcenterdata.get(0).getSubcenter();
                    Log.i("ckm=>subcentre1",spnStrSubcenter);
                }
                else
                {
                    spnStrSubcenter = subcenterdata.get(position).getSubcenter();
                    Log.i("ckm=>subcentre222",spnStrSubcenter);
                }

                getvillage = PhcUsersModel.getVillageBySubcentreUserId(SharedPreference.get("Userid"),SharedPreference.get("CAMPID"),spnStrSubcenter,spnStrphc);
                Log.i("ckm=>getvillage",getvillage.size()+"");
                loadvillages();
       }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void loadvillages() {
        //  selet_village.setAdapter(new CustomAutocomVillageAdapter(getApplicationContext(), getvillage));
        villagelistAdapter = new VillagelistAdapter(this,R.layout.activity_dailywork_phc_pada,R.id.auto_text,getvillage);
        selet_village.setAdapter(villagelistAdapter);
        selet_village.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhcUserData data = (PhcUserData) parent.getItemAtPosition(position);
                selet_village.setText(data.getVillage());
                Log.i("ckm=>village",data.getVillage());
                mList = PhcUsersModel.getPadaByUserId(SharedPreference.get("Userid"),SharedPreference.get("CAMPID"),spnStrSubcenter,spnStrphc,selet_village.getText().toString());
                if (!mList.isEmpty())
                {

                    String date12 = mList.get(0).getPada();
                    Log.i("ckm=>data",date12);
                    //ArrayList<PhcUserData> imp = new ArrayList<>();
                    mList.clear();
                    List<String> elephantList = Arrays.asList(date12.split(","));
                    Log.i("ckmList",elephantList.size()+"");

                    for (int i = 0 ; i < elephantList.size(); i++)
                    {
                        PhcUserData da = new PhcUserData();
                        da.setPada(elephantList.get(i));
                        mList.add(da);
                    }

                    Log.i("ck",mList+"");
                }
                loadpada();
           }
       });

        selet_village.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Log.i("VillageDropDown", "TextBox focused, showing dropdown");
                    selet_village.showDropDown();
                } else {
                    Log.i("VillageDropDown", "TextBox lost focus, hiding dropdown");
                    selet_village.dismissDropDown();
                }
            }
        });

        selet_village.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("VillageDropDown", "Textbox clicked, forcing dropdown open");
                selet_village.showDropDown();
            }
        });


    }

    private void loadpada() {
        PadauserListAdapter = new PadaUserList(this,R.layout.activity_dailywork_phc_pada,R.id.auto_text1,mList);
        selet_pada.setAdapter(PadauserListAdapter);
        selet_pada.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhcUserData data1 = (PhcUserData) parent.getItemAtPosition(position);
                selet_pada.setText(data1.getPada());
               // Log.i("ckm=>pada",data1.getPada());
            }
        });

        selet_pada.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i("ckm=>BeforeText",selet_pada.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("ckm=>OnTextChange",selet_pada.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i("ckm=>AfterTextChange",selet_pada.getText().toString());

            }
        });


        selet_pada.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                selet_pada.showDropDown();
                selet_pada.requestFocus();
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this,DashboardActivityOutPro.class);
        startActivity(i);
    }
}


