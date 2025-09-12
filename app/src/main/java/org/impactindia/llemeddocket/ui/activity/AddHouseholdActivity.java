package org.impactindia.llemeddocket.ui.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import org.impactindia.llemeddocket.AttributeSet;
import org.impactindia.llemeddocket.DatabaseHelper;
import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.orm.DAOException;
import org.impactindia.llemeddocket.orm.PopulationMedicalModel;
import org.impactindia.llemeddocket.orm.TagDAO;
import org.impactindia.llemeddocket.pojo.Base;
import org.impactindia.llemeddocket.pojo.PopMedicalData;
import org.impactindia.llemeddocket.pojo.Tag;
import org.impactindia.llemeddocket.util.SharedPreference;
import org.impactindia.llemeddocket.view.MultiSelectionSpinner;
import org.impactindia.llemeddocket.ws.WsCallCompleteListener;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.Period;
import org.threeten.bp.temporal.ChronoUnit;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import info.hoang8f.android.segmented.SegmentedGroup;

public class AddHouseholdActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener,DatePickerDialog.OnDateSetListener/*, WsCallCompleteListener*/ {

    Toolbar toolbar;
    Spinner spinner_gender,houshold_spnAgeUnit,spinner_membertype,spinner_relationship;
    EditText edtdob,edtage,edt_householdNo,edtTotalMemberCount,edtFirstName,edtMiddleName,edtlastname;
    private LocalDate birthDate;
    private ArrayAdapter<String> ageUnitAdapter;
    private ArrayAdapter<String> relationshipadapter;
    private ArrayAdapter<String> membertypeadapter;
    private String[] ageUnitArr;
    SegmentedGroup segmentedGroup;
    List<String> gender;
    String[] relationshiparr;
    String[] membertypearr;
    Button btnAddPadaDetails,btnAdd_memberdata;
    RadioButton rbMr,rbMrs,rbMaster,rbMs;
    String title = "null";
    String rel;
    ArrayList<PopMedicalData> data2 = new ArrayList<PopMedicalData>();
    ArrayList<PopMedicalData> matchhouse = new ArrayList<PopMedicalData>();
    SQLiteDatabase db;
    EditText edtpincode,edtmobileno,edtstreetname,edtresidenceno,edtaadharno,edtvoterid,edtemailid,edtreferby,edtothersdetails;
   // MultiSelectionSpinner mulSpnAssignTag;
    private List<Tag> tagList;
    private TagDAO tagDAO;
    String editid = null;
    String housenodatano = null;
    ArrayList<PopMedicalData> getdataforupdate = new ArrayList<PopMedicalData>();
    String  date1;
    LinearLayout llothers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_household);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content),
                (view, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    Insets ime = insets.getInsets(WindowInsetsCompat.Type.ime());

                    // pick whichever is larger (IME when keyboard visible)
                    int bottom = Math.max(systemBars.bottom, ime.bottom);

                    view.setPadding(systemBars.left, systemBars.top, systemBars.right, bottom);
                    return insets;
                });


        init();


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        date1 = sdf.format(c.getTime());
        Log.i("DATEYYYYMMDD",date1);

        Bundle bundle1 = getIntent().getExtras();
        if (bundle1 != null) {
            housenodatano = bundle1.getString("FILLHOUSENO");
            if (!isEmpty(housenodatano))
            {
                Log.i("ckm=>housenodatano", housenodatano);
                if (!isEmpty(SharedPreference.get("HOUSENO")))
                {
                    edt_householdNo.setText(SharedPreference.get("HOUSENO"));
                }
            }
        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            editid = bundle.getString("EDITID");
            if (!isEmpty(editid))
            {
                Log.i("ckm=>ID",editid);
                getdataforupdate = PopulationMedicalModel.getdataforupdate(editid);
                Log.i("ckm=>sizeofarray",getdataforupdate.size()+"");
                edt_householdNo.setText(getdataforupdate.get(0).getHouseholdno());

                //Spinner logic for membertype  and relationship
                if (!isEmpty(getdataforupdate.get(0).getRelation()))
                {
                    if (getdataforupdate.get(0).getRelation().equals("self"))
                    {
                        membertypeadapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,membertypearr);
                        spinner_membertype.setAdapter(membertypeadapter);
                        int sp_pos = membertypeadapter.getPosition("Head of family");
                        spinner_membertype.setSelection(sp_pos);

                        spinner_relationship.setEnabled(false);
                        spinner_relationship.setClickable(false);

                    }
                    else if(!getdataforupdate.get(0).getRelation().equals("self"))
                    {
                        membertypeadapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,membertypearr);
                        spinner_membertype.setAdapter(membertypeadapter);
                        int sp_pos = membertypeadapter.getPosition("Family Member");
                        spinner_membertype.setSelection(sp_pos);

                        relationshipadapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,relationshiparr);
                        spinner_relationship.setAdapter(relationshipadapter);
                        int spn_reln_value = relationshipadapter.getPosition(getdataforupdate.get(0).getRelation());
                        spinner_relationship.setSelection(spn_reln_value);

                        Log.i("INsideOther","Other");

                        if (getdataforupdate.get(0).getRelation().equals("Other"))
                        {
                            llothers.setVisibility(View .VISIBLE);
                            edtothersdetails.setText(getdataforupdate.get(0).getRelationdetails());
                            Log.i("INsideOther","OtherVISISBLE");
                        }
                        else
                        {
                            llothers.setVisibility(View.GONE);
                            Log.i("INsideOther","OtherGONE");
                        }

                    }
                }


                if (getdataforupdate.get(0).getTitle().equals("Ms"))
                {
                    segmentedGroup.check(R.id.rbMs);
                }
                else if (getdataforupdate.get(0).getTitle().equals("Mrs"))
                {
                    segmentedGroup.check(R.id.rbMrs);
                }
                else if (getdataforupdate.get(0).getTitle().equals("Master"))
                {
                    segmentedGroup.check(R.id.rbMaster);
                }
                else if (getdataforupdate.get(0).getTitle().equals("Mr"))
                {
                    segmentedGroup.check(R.id.rbMr);
                }


                if (!isEmpty(getdataforupdate.get(0).getGender())) {
                    ArrayAdapter<String> data = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,gender);
                    spinner_gender.setAdapter(data);
                    int sp_pos = data.getPosition(getdataforupdate.get(0).getGender());
                    spinner_gender.setSelection(sp_pos);
                }

                if (!isEmpty(getdataforupdate.get(0).getAgeunit()))
                {
                    ageUnitAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ageUnitArr);
                    houshold_spnAgeUnit.setAdapter(ageUnitAdapter);
                    int sp_ageunit = ageUnitAdapter.getPosition(getdataforupdate.get(0).getAgeunit());
                    houshold_spnAgeUnit.setSelection(sp_ageunit);
                }

                edtFirstName.setText(getdataforupdate.get(0).getFname());
                edtMiddleName.setText(getdataforupdate.get(0).getMname());
                edtlastname.setText(getdataforupdate.get(0).getLname());
                edtdob.setText(getdataforupdate.get(0).getDob());
                edtage.setText(String.valueOf(getdataforupdate.get(0).getAge()));
                edtpincode.setText(String.valueOf(getdataforupdate.get(0).getPincode()));
                edtmobileno.setText(getdataforupdate.get(0).getMobileno());
                edtstreetname.setText(getdataforupdate.get(0).getStreetname());
                edtresidenceno.setText(getdataforupdate.get(0).getResidenceno());
                edtaadharno.setText(getdataforupdate.get(0).getAadharno());
                edtvoterid.setText(getdataforupdate.get(0).getVoteridno());
                edtemailid.setText(getdataforupdate.get(0).getEmailid());
                edtreferby.setText(getdataforupdate.get(0).getReferredby());

                btnAddPadaDetails.setVisibility(View.GONE);
                btnAdd_memberdata.setText("Update");
            }
        }
    }


    private void init() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Population Details");
        SharedPreference.initialize(this);
        db = new DatabaseHelper(this).getWritableDatabase();
        tagDAO = new TagDAO(this, db);
        PopulationMedicalModel.getInstance(this);

        llothers = findViewById(R.id.llothers);
        edtothersdetails = findViewById(R.id.edtothersdetails);

        spinner_gender = findViewById(R.id.spinner_gender);
        houshold_spnAgeUnit = findViewById(R.id.houshold_spnAgeUnit);
        edtdob = findViewById(R.id.edtdob);
        edtage = findViewById(R.id.edtage);
        edt_householdNo = findViewById(R.id.edt_householdNo);
        edtTotalMemberCount = findViewById(R.id.edtTotalMemberCount);
        edtFirstName = findViewById(R.id.edtFirstName);
        edtMiddleName = findViewById(R.id.edtMiddleName);
        edtlastname = findViewById(R.id.edtlastname);
        btnAddPadaDetails = findViewById(R.id.btnAddPadaDetails);
        btnAdd_memberdata = findViewById(R.id.btnAdd_memberdata);
        spinner_membertype = findViewById(R.id.spinner_membertype);
        spinner_relationship = findViewById(R.id.spinner_relationship);

        edtpincode = findViewById(R.id.edtpincode);
        edtmobileno = findViewById(R.id.edtmobileno);
        edtstreetname = findViewById(R.id.edtstreetname);
        edtresidenceno = findViewById(R.id.edtresidenceno);
        edtaadharno = findViewById(R.id.edtaadharno);
        edtvoterid = findViewById(R.id.edtvoterid);
        edtemailid = findViewById(R.id.edtemailid);
        edtpincode = findViewById(R.id.edtpincode);
        edtreferby = findViewById(R.id.edtreferby);

        segmentedGroup = findViewById(R.id.sgTitle);
        rbMr = findViewById(R.id.rbMr);
        rbMrs = findViewById(R.id.rbMrs);
        rbMaster = findViewById(R.id.rbMaster);
        rbMs = findViewById(R.id.rbMs);
        segmentedGroup.setOnCheckedChangeListener(listener);


        btnAdd_memberdata.setOnClickListener(this);
        btnAddPadaDetails.setOnClickListener(this);
        edtdob.setOnClickListener(this);
        edtage.setOnClickListener(this);


        gender = new ArrayList<String>();
        gender.add("Gender");
        gender.add("Male");
        gender.add("Female");
        gender.add("Other");

        ArrayAdapter<String> data = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,gender);
        spinner_gender.setAdapter(data);

        relationshiparr = getResources().getStringArray(R.array.relationship);
             relationshipadapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,relationshiparr);
        spinner_relationship.setAdapter(relationshipadapter);


        membertypearr = getResources().getStringArray(R.array.member);
        membertypeadapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,membertypearr);
        spinner_membertype.setAdapter(membertypeadapter);


        ageUnitArr = getResources().getStringArray(R.array.age_unit);
        ageUnitAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, ageUnitArr);
        houshold_spnAgeUnit.setAdapter(ageUnitAdapter);

        houshold_spnAgeUnit.setOnItemSelectedListener(spnAgeUnitListener);
       /* if (!isEmpty(editid))
        {

        }*/
        spinner_relationship.setOnItemSelectedListener(this);
        spinner_membertype.setOnItemSelectedListener(this);
        edtage.addTextChangedListener(txtdateofofbirthusingage);
        edt_householdNo.addTextChangedListener(edthouseholdnocheck);

    }


    RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.rbMs) {
                title = "Ms";
            } else if (checkedId == R.id.rbMrs) {
                title = "Mrs";
            } else if (checkedId == R.id.rbMaster) {
                title = "Master";
            } else if (checkedId == R.id.rbMr) {
                title = "Mr";
            }
        }
    };





    private AdapterView.OnItemSelectedListener spnAgeUnitListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            if (!isEmpty(edtage.getText().toString())) {
                setDOB(String.valueOf(parent.getItemAtPosition(position)), Integer.parseInt(edtage.getText().toString()));
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void setDOB(String ageUnit, int age) {
        LocalDateTime dob = LocalDateTime.now();
        if (ageUnit.equals("Years")) {
            dob = LocalDateTime.now().minusYears(age);
        } else if (ageUnit.equals("Months")) {
            dob = LocalDateTime.now().minusMonths(age);
        } else if (ageUnit.equals("Weeks")) {
            dob = LocalDateTime.now().minusWeeks(age);
        } else if (ageUnit.equals("Days")) {
            dob = LocalDateTime.now().minusDays(age);
        }

        DecimalFormat mformat;
        mformat = new DecimalFormat("00");

        StringBuilder sb;
        sb = new StringBuilder();
        sb.append(mformat.format(dob.getDayOfMonth()));
        sb.append("-");
        sb.append(mformat.format(dob.getMonth().getValue()));
        sb.append("-");
        sb.append(dob.getYear());
        edtdob.setText(sb.toString());
        edtdob.setError(null);
    }


    TextWatcher txtdateofofbirthusingage = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!isEmpty(edtage.getText().toString()))
            {
              //  txtLeftEyeInterp.setText(EYEInter(leyenearvalue.getText().toString(),leyedistancevalue.getText().toString()));
              setDOB(houshold_spnAgeUnit.getSelectedItemPosition() == 0 ? "Years" : houshold_spnAgeUnit.getSelectedItem().toString(),Integer.parseInt(edtage.getText().toString()));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    TextWatcher edthouseholdnocheck = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (isEmpty(housenodatano))
            {
                if (!isEmpty(edt_householdNo.getText().toString()))
                {
                    matchhouse = PopulationMedicalModel.getmatchhouse(edt_householdNo.getText().toString(),SharedPreference.get("LastInsertID"));
                    Log.i("ckm=>data",matchhouse.size()+"");
                    if (matchhouse.size() > 0)
                    {
                        longToast("The Household you entered already exist it will link to previous enter data");
                    }
                }

            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        int parentId = parent.getId();

        if (parentId == R.id.spinner_relationship) {
            String reln = parent.getItemAtPosition(position).toString();
            if (reln.equals("Other")) {
                Log.i("reln", "in");
                llothers.setVisibility(View.VISIBLE);
            } else {
                llothers.setVisibility(View.GONE);
                Log.i("reln", "out");
            }

        } else if (parentId == R.id.spinner_membertype) {
            String member_type = parent.getSelectedItem().toString();
            if (member_type.equals("Head of family")) {
                Log.i("ckm=>Member", "Inside");
                spinner_relationship.setEnabled(false);
                spinner_relationship.setClickable(false);
                relationshipadapter.notifyDataSetChanged();
                if (isEmpty(editid)) {
                    spinner_relationship.setSelection(0);
                }
            } else {
                spinner_relationship.setEnabled(true);
                spinner_relationship.setClickable(true);
                relationshipadapter.notifyDataSetChanged();
                if (isEmpty(editid)) {
                    spinner_relationship.setSelection(0);
                }
            }

        } else if (parentId == R.id.spinner_gender) {
            String gender = parent.getItemAtPosition(position).toString();
            Log.i("Log_Gender", gender);

        } else if (parentId == R.id.houshold_spnAgeUnit) {
            String unit = parent.getItemAtPosition(position).toString();
            Log.i("ckm=>gg", unit);
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void showDatePickerDialog() {
        Calendar c;
        c = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, this,
                c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        dialog.show();
    }
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        if (isEventDateSelectedValid(year, monthOfYear, dayOfMonth)) {
            ++monthOfYear;
            DecimalFormat mformat;
            mformat = new DecimalFormat("00");
            birthDate = LocalDate.of(year,monthOfYear,dayOfMonth);

            StringBuilder sb;
            sb = new StringBuilder();
            sb.append(mformat.format(dayOfMonth));
            sb.append("-");
            sb.append(mformat.format(monthOfYear));
            sb.append("-");
            sb.append(year);
            edtdob.setText(sb.toString());
            edtdob.setError(null);
            setAge();
        }
    }

    private void setAge() {
        LocalDate today;
        today = LocalDate.now();
        Period period = Period.between(birthDate, today);
        edtage.setText(period.getYears() != 0 ? String.valueOf(period.getYears()) : period.getMonths() != 0 ? String.valueOf(period.getMonths()) : ChronoUnit.WEEKS.between(birthDate, today) != 0 ? String.valueOf(ChronoUnit.WEEKS.between(birthDate, today)) : String.valueOf(period.getDays()));
        if (period.getYears() != 0) {
            houshold_spnAgeUnit.setSelection(0, true);
        } else if (period.getMonths() != 0) {
            houshold_spnAgeUnit.setSelection(1, true);
        } else if (ChronoUnit.WEEKS.between(birthDate, today) != 0) {
            houshold_spnAgeUnit.setSelection(2, true);
        } else if (period.getDays() != 0) {
            houshold_spnAgeUnit.setSelection(3, true);
        }
    }

    public boolean isEventDateSelectedValid(int year, int monthOfYear,
                                            int dayOfMonth) {
        Calendar c;
        c = Calendar.getInstance();
        int curryear, currmonthOfYear, currdayOfMonth;
        curryear = c.get(Calendar.YEAR);
        currmonthOfYear = c.get(Calendar.MONTH);
        currdayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        if (year > curryear) {
            shortToast(getString(R.string.date_invalid_err_msg));
        } else if (monthOfYear > currmonthOfYear && year == curryear) {
            shortToast(getString(R.string.date_invalid_err_msg));
        } else if (dayOfMonth > currdayOfMonth && year == curryear
                && monthOfYear == currmonthOfYear) {
            shortToast(getString(R.string.date_invalid_err_msg));
        } else {
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
            if (id == R.id.edtdob) {
                showDatePickerDialog();

            } else if (id == R.id.edtage) {

            } else if (id == R.id.btnAdd_memberdata) {
                if (spinner_membertype.getSelectedItem().toString().equals("Head of family"))
                {
                    rel = "self" ;
                }
                else
                {
                    rel = "null";
                }

                if (isEmpty(edt_householdNo.getText().toString()))
                {
                    shortToast("please enter house hold no");
                }
                else if (spinner_membertype.getSelectedItemPosition() == 0)
                {
                    shortToast("Please select Member type");
                }
                else if (rel.equals("null") && spinner_relationship.getSelectedItemPosition() == 0)
                {
                        shortToast("Please select Relation");
                }
                else if (isEmpty(edtFirstName.getText().toString()))
                {
                    shortToast("Please enter first name");
                }
                else if (title.equals(null))
                {
                    shortToast("Please select the title");
                }
                else if (isEmpty(edtlastname.getText().toString()))
                {
                    shortToast("please enter last name");
                }
                else if (spinner_gender.getSelectedItemPosition() == 0)
                {
                    shortToast("please select gender");
                }
                else if (isEmpty(edtdob.getText().toString()))
                {
                    shortToast("Please select DOB");
                }
                else if (isEmpty(edtpincode.getText().toString()))
                {
                   shortToast("Please enter pincode");
                }
                else if(isEmpty(edtmobileno.getText().toString()))
                {
                    shortToast("Please enter mobileno");
                }
                else if (!isEmpty(edtaadharno.getText().toString()) && edtaadharno.getText().toString().length() != 12) {
                    shortToast("Please enter correct aadhar");
                }
                else
                {
                    String dob = edtdob.getText().toString();
                    Date date = null;
                    try {
                        date = new SimpleDateFormat("dd-MM-yyyy").parse(dob);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String dateString2 = new SimpleDateFormat("yyyy-MM-dd").format(date);
                    Log.i("ckm=>convertedDOb",dateString2);
                    PopulationMedicalModel.open();

                    if (!isEmpty(editid) &&  editid != null)
                    {

                        PopMedicalData data = new PopMedicalData();
                        data.setHouseholdno(edt_householdNo.getText().toString());
                        data.setRelation(spinner_relationship.getSelectedItemPosition() == 0 ? rel:spinner_relationship.getSelectedItem().toString());
                        data.setRelationdetails(edtothersdetails.getText().toString());
                        data.setTitle(title);
                        data.setFname(edtFirstName.getText().toString());
                        data.setMname(edtMiddleName.getText().toString());
                        data.setLname(edtlastname.getText().toString());
                        data.setGender(spinner_gender.getSelectedItem().toString());
                        data.setDob(dateString2);
                        data.setAge(Integer.valueOf(edtage.getText().toString()));
                        data.setAgeunit(houshold_spnAgeUnit.getSelectedItemPosition() == 0 ? "Years" : houshold_spnAgeUnit.getSelectedItem().toString());
                        data.setPincode(isEmpty(edtpincode.getText().toString())? null: Integer.valueOf(edtpincode.getText().toString()));
                        data.setMobileno(edtmobileno.getText().toString());
                        data.setStreetname(edtstreetname.getText().toString());
                        data.setResidenceno(edtresidenceno.getText().toString());
                        data.setAadharno(edtaadharno.getText().toString());
                        data.setVoteridno(edtvoterid.getText().toString());
                        data.setEmailid(edtemailid.getText().toString());
                        data.setReferredby(edtreferby.getText().toString());

                        PopulationMedicalModel.updateHouseHold(editid,data);
                        SharedPreference.save("HOUSEHOLDID",editid);
                        SharedPreference.save("AGE",edtage.getText().toString());
                        SharedPreference.save("GENDER",spinner_gender.getSelectedItem().toString());
                        SharedPreference.save("DOB",edtdob.getText().toString());
                        shortToast("Data update successfully");
                        Log.i("ckm=>ageunit",houshold_spnAgeUnit.getSelectedItemPosition() == 0 ? "Years" : houshold_spnAgeUnit.getSelectedItem().toString());
                        Intent i = new Intent(AddHouseholdActivity.this,HealthDataActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("EDITID",editid);
                        i.putExtras(bundle);
                        startActivity(i);
                        //finish();
                    }
                    else
                    {
                        if (!isEmpty(rel) && rel.equals("self"))
                        {
                           // data2 = PopulationMedicalModel.getmatchhousehold(edt_householdNo.getText().toString(),spinner_relationship.getSelectedItemPosition() == 0 ? rel : spinner_relationship.getSelectedItem().toString());
                            data2 = PopulationMedicalModel.getmatchhousehold(edt_householdNo.getText().toString(),rel,SharedPreference.get("LastInsertID"));
                            Log.i("ckm=>data",data2.size()+"");
                            if (data2.size() > 0)
                            {
                                shortToast("Head of the family details for current house hold is already added");
                            }
                            else
                            {
                                PopMedicalData data = new PopMedicalData(Integer.valueOf(Integer.valueOf(SharedPreference.get("LastInsertID"))),Integer.valueOf(SharedPreference.get("CAMPID")),Integer.valueOf(SharedPreference.get("Userid")),
                                        edt_householdNo.getText().toString(),0,0,title, edtFirstName.getText().toString(),
                                        edtMiddleName.getText().toString(),edtlastname.getText().toString(),spinner_relationship.getSelectedItemPosition() == 0 ? rel:spinner_relationship.getSelectedItem().toString(),spinner_gender.getSelectedItem().toString(),dateString2,Integer.valueOf(edtage.getText().toString()),
                                        houshold_spnAgeUnit.getSelectedItemPosition() == 0 ? "Years" : houshold_spnAgeUnit.getSelectedItem().toString(),null,null,null,"",null,null,null,null,"",null,"",null,null,null,null,null,"","","","","","","","","","","","","","","","","","","","","","",
                                        "","","","","","","","","","","","","","","","","","","","","","","0","",
                                        isEmpty(edtpincode.getText().toString())? null: Integer.valueOf(edtpincode.getText().toString()),
                                        edtmobileno.getText().toString(),edtstreetname.getText().toString(),edtresidenceno.getText().toString(),edtaadharno.getText().toString(),
                                        edtvoterid.getText().toString(),edtemailid.getText().toString(),edtreferby.getText().toString(),null,date1,"","","",edtothersdetails.getText().toString());


                                Log.i("datadate",date1);
                                long pid = PopulationMedicalModel.insert(data);
                                SharedPreference.save("HOUSEHOLDID",pid);
                                SharedPreference.save("HOUSENO",edt_householdNo.getText().toString());
                                SharedPreference.save("AGE",edtage.getText().toString());
                                SharedPreference.save("GENDER",spinner_gender.getSelectedItem().toString());
                                SharedPreference.save("DOB",edtdob.getText().toString());
                                Log.i("ckm=>ageunit",houshold_spnAgeUnit.getSelectedItemPosition() == 0 ? "Years" : houshold_spnAgeUnit.getSelectedItem().toString());
                                shortToast("Data added successfully");
                                Intent i = new Intent(AddHouseholdActivity.this,HealthDataActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }
                        else
                        {
                            PopMedicalData data = new PopMedicalData(Integer.valueOf(Integer.valueOf(SharedPreference.get("LastInsertID"))),Integer.valueOf(SharedPreference.get("CAMPID")),Integer.valueOf(SharedPreference.get("Userid")),
                                    edt_householdNo.getText().toString(),0,0,title, edtFirstName.getText().toString(),
                                    edtMiddleName.getText().toString(),edtlastname.getText().toString(),spinner_relationship.getSelectedItemPosition() == 0 ? rel:spinner_relationship.getSelectedItem().toString(),spinner_gender.getSelectedItem().toString(),dateString2,Integer.valueOf(edtage.getText().toString()),
                                    houshold_spnAgeUnit.getSelectedItemPosition() == 0 ? "Years" : houshold_spnAgeUnit.getSelectedItem().toString(),null,null,null,"",null,null,null,null,"",null,"",null,null,null,null,null,"","","","","","","","","","","","","","","","","","","","","","",
                                    "","","","","","","","","","","","","","","","","","","","","","","0","",
                                    isEmpty(edtpincode.getText().toString())? null: Integer.valueOf(edtpincode.getText().toString()),
                                    edtmobileno.getText().toString(),edtstreetname.getText().toString(),edtresidenceno.getText().toString(),edtaadharno.getText().toString(),
                                    edtvoterid.getText().toString(),edtemailid.getText().toString(),edtreferby.getText().toString(),null,date1,"","","",edtothersdetails.getText().toString());


                            Log.i("datadate",date1);

                            long sid = PopulationMedicalModel.insert(data);
                            SharedPreference.save("HOUSEHOLDID",sid);
                            SharedPreference.save("HOUSENO",edt_householdNo.getText().toString());
                            SharedPreference.save("AGE",edtage.getText().toString());
                            SharedPreference.save("GENDER",spinner_gender.getSelectedItem().toString());
                            SharedPreference.save("DOB",edtdob.getText().toString());
                            Log.i("ckm=>ageunit",houshold_spnAgeUnit.getSelectedItemPosition() == 0 ? "Years" : houshold_spnAgeUnit.getSelectedItem().toString());
                            shortToast("Data added successfully");
                            Intent i = new Intent(AddHouseholdActivity.this,HealthDataActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                }
            } else if (id == R.id.btnAddPadaDetails) {
                Intent i = new Intent(AddHouseholdActivity.this, DailyworkPhcPadaActivity.class);
                startActivity(i);
                finish();
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.population_list,menu);
        return true;
       // return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.population_list) {
            Intent intent = new Intent(AddHouseholdActivity.this, EditDataActivity.class);
            startActivity(intent);
            return true; // handled
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        displayalert();
    }

    public void displayalert()
    {
        new AlertDialog.Builder(this)
                .setTitle("Note")
                .setMessage("Do you want to go home screen ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation

                        Intent i = new Intent(AddHouseholdActivity.this,DashboardActivityOutPro.class);
                        startActivity(i);
                        finish();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}