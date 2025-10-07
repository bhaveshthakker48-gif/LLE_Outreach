package org.impactindia.llemeddocket.ui.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.impactindia.llemeddocket.AttributeSet;
import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.orm.BloodPressureNRDataDAO;
import org.impactindia.llemeddocket.orm.ChartNormalRangeModel;
import org.impactindia.llemeddocket.orm.ChartcommonMasterModel;
import org.impactindia.llemeddocket.orm.DAOException;
import org.impactindia.llemeddocket.orm.PopulationMedicalModel;
import org.impactindia.llemeddocket.pojo.BloodPressureNRData;
import org.impactindia.llemeddocket.pojo.ChartNormalRangeData;
import org.impactindia.llemeddocket.pojo.ChartcommonMasterData;
import org.impactindia.llemeddocket.pojo.PopMedicalData;
import org.impactindia.llemeddocket.util.MoneyValueFilter;
import org.impactindia.llemeddocket.util.SharedPreference;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.temporal.ChronoUnit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HealthDataActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    Toolbar toolbar;
    TextView edtbmi,edtbmiinterpretation;
    Button btnAddhelathdata;
    TextView bloodpressure_interpretation,txtbloodsugar_interpretation,txtRightEyeInterp,txtLeftEyeInterp;
    EditText edtDias,edtSys,edtbloodsugar,edto2saturation,edtweight,edtinches,edtft,edtpulse,edtreyedistance,edtleyedistance;
    private SQLiteDatabase db;
    private BloodPressureNRDataDAO bloodPressureDAO;
    String [] arrleydis;
    String [] arrleynear;
    String [] arrReynear;
    String [] arrReydis;
    private LocalDate examinationDate;
    private ArrayAdapter<String> arrayLnearAdapter;
    private ArrayAdapter<String> arrayRnearAdapter;
    private ArrayAdapter<String> arrayRdistanceAdapter;
    private ArrayAdapter<String> arrayLdistanceAdapter;

    ArrayList<PopMedicalData> data1 = new ArrayList<>();
    Spinner spnleyedistance,spnleyenear,spnreyenear,spnrdistance;
    String righteyenear,righteyedistance,lefteyedistance,lefteyenear;
    TextView reyenearvalue,reyedistancevalue,leyenearvalue,leyedistancevalue;
    ArrayList<PopMedicalData> getdataforupdate = new ArrayList<PopMedicalData>();

    ArrayList<ChartcommonMasterData> commonmasterdata = new ArrayList<>();
    ArrayList<ChartNormalRangeData> normalrangedata = new ArrayList<>();

    String editid = null;
    CheckBox userrefused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_data);


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
        AndroidThreeTen.init(this);
        init();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            editid = bundle.getString("EDITID");
            Log.i("ckm=>HealthDataID", editid);
            getdataforupdate = PopulationMedicalModel.getdataforupdate(editid);
            edtft.setText(String.valueOf(getdataforupdate.get(0).getHeightinfit()));
            edtinches.setText(String.valueOf(getdataforupdate.get(0).getHeightininches()));
            edtweight.setText(String.valueOf(getdataforupdate.get(0).getWeight()));
            edtbmi.setText(getdataforupdate.get(0).getBmi());
            if (!isEmpty(getdataforupdate.get(0).getBmiinter()))
            {
                edtbmiinterpretation.setText(getdataforupdate.get(0).getBmiinter());
            }
            edtpulse.setText(String.valueOf(getdataforupdate.get(0).getPulse()));
            if (!isEmpty(String.valueOf(getdataforupdate.get(0).getOxysaturation())))
            {
                edto2saturation.setText(String.valueOf(getdataforupdate.get(0).getOxysaturation()));
            }
            edtbloodsugar.setText(String.valueOf(getdataforupdate.get(0).getBloodsugar()));
            txtbloodsugar_interpretation.setText(getdataforupdate.get(0).getBloodsugarinter());

            if (!isEmpty(String.valueOf(getdataforupdate.get(0).getBpsystolic())))
            {
                edtSys.setText(String.valueOf(getdataforupdate.get(0).getBpsystolic()));
            }
            if (!isEmpty(String.valueOf(getdataforupdate.get(0).getBpdiastolic())))
            {
                edtDias.setText(String.valueOf(getdataforupdate.get(0).getBpdiastolic()));
            }
            if (!isEmpty(getdataforupdate.get(0).getBpinter()))
            {
                bloodpressure_interpretation.setText(getdataforupdate.get(0).getBpinter());
            }


            if (!isEmpty(getdataforupdate.get(0).getVisualacuityrighteyenear()))
            {
                arrayRnearAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, arrReynear);
                spnreyenear.setAdapter(arrayRnearAdapter);
                int sp_reyenear = arrayRnearAdapter.getPosition(getdataforupdate.get(0).getVisualacuityrighteyenear());
                spnreyenear.setSelection(sp_reyenear);
            }

            if (!isEmpty(getdataforupdate.get(0).getVisualacuityrighteyedistant()))
            {
                arrayRdistanceAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, arrReydis);
                spnrdistance.setAdapter(arrayRdistanceAdapter);
                int sp_reyedistance = arrayRdistanceAdapter.getPosition(getdataforupdate.get(0).getVisualacuityrighteyedistant());
                spnrdistance.setSelection(sp_reyedistance);
            }

            if (!isEmpty(getdataforupdate.get(0).getVisualacuitylefteyenear()))
            {
                arrayLnearAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, arrleynear);
                spnleyenear.setAdapter(arrayLnearAdapter);
                int sp_leyenear = arrayLnearAdapter.getPosition(getdataforupdate.get(0).getVisualacuitylefteyenear());
                spnleyenear.setSelection(sp_leyenear);
            }

            if (!isEmpty(getdataforupdate.get(0).getVisualacuitylefteyedistant()))
            {
                arrayLdistanceAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, arrleydis);
                spnleyedistance.setAdapter(arrayLdistanceAdapter);
                int sp_leyedistance = arrayLdistanceAdapter.getPosition(getdataforupdate.get(0).getVisualacuitylefteyedistant());
                spnleyedistance.setSelection(sp_leyedistance);
            }
            if (getdataforupdate.get(0).getPatientrefused() == 1)
            {
                userrefused.setChecked(true);
            }
            else
            {
                userrefused.setChecked(false);
            }
            txtRightEyeInterp.setText(getdataforupdate.get(0).getVisualacuityrighteyeinter());
            txtLeftEyeInterp.setText(getdataforupdate.get(0).getVisualacuitylefteyeinter());
            btnAddhelathdata.setText("Update");
        }


    }

    private void init() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Health Data");

        PopulationMedicalModel.getInstance(this);
        ChartcommonMasterModel.getInstance(this);
        ChartNormalRangeModel.getInstance(this);

        LocalDate localDate = LocalDate.now();   //For reference Get current Date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedString = localDate.format(formatter);
        Log.i("ckm=>Formate",formattedString);

        LocalDate from = LocalDate.parse(SharedPreference.get("DOB"), formatter);  //Get DOB
        LocalDate to = LocalDate.parse(formattedString, formatter);

        System.out.println(from.until(to, ChronoUnit.MONTHS)); //Got Total No Of Months



        txtRightEyeInterp = findViewById(R.id.txtRightEyeInterp);
        txtLeftEyeInterp = findViewById(R.id.txtLeftEyeInterp);
        userrefused = findViewById(R.id.userrefused);

        spnleyedistance = findViewById(R.id.spnleyedistance);
        spnleyenear = findViewById(R.id.spnleyenear);
        spnreyenear = findViewById(R.id.spnreyenear);
        spnrdistance = findViewById(R.id.spnrdistance);

        reyenearvalue = findViewById(R.id.reyenearvalue);
        reyedistancevalue = findViewById(R.id.reyedistancevalue);
        leyenearvalue = findViewById(R.id.leyenearvalue);
        leyedistancevalue = findViewById(R.id. leyedistancevalue);

        reyenearvalue.addTextChangedListener(Reyeinterprewatcher);
        reyedistancevalue.addTextChangedListener(Reyeinterprewatcher);
        leyenearvalue.addTextChangedListener(leyeinterprewatcher);
        leyedistancevalue.addTextChangedListener(leyeinterprewatcher);


        edtbmiinterpretation = findViewById(R.id.edtbmiinterpretation);

        bloodpressure_interpretation = findViewById(R.id.bloodpressure_interpretation);
        txtbloodsugar_interpretation = findViewById(R.id.txtbloodsugar_interpretation);

        edtDias = findViewById(R.id.edtDias);
        edtSys = findViewById(R.id.edtSys);
        btnAddhelathdata = findViewById(R.id.btnAddhelathdata);

        edtSys.addTextChangedListener(bpwatcher);
        edtDias.addTextChangedListener(bpwatcher);

        edtbloodsugar = findViewById(R.id.edtbloodsugar);
        edtbloodsugar.setFilters(new InputFilter[]{new MoneyValueFilter()});
        edtbloodsugar.addTextChangedListener(bldGlucoseWatcher);

        edto2saturation = findViewById(R.id.edto2saturation);
        edtbmi = findViewById(R.id.edtbmi);
        edtweight = findViewById(R.id.edtweight);
        edtinches = findViewById(R.id.edtinches);
        edtft = findViewById(R.id.edtft);

        edtpulse = findViewById(R.id.edtpulse);

        edtft.addTextChangedListener(bmiwatcher);
        edtinches.addTextChangedListener(bmiwatcher);
        edtweight.addTextChangedListener(bmiwatcher);


        arrleydis = getResources().getStringArray(R.array.distance_vision);
        arrleynear = getResources().getStringArray(R.array.near_vision);
        arrReynear = getResources().getStringArray(R.array.near_vision);
        arrReydis = getResources().getStringArray(R.array.distance_vision);


        arrayLnearAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, arrleynear);
        arrayRnearAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, arrReynear);
        arrayLdistanceAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, arrleydis);
        arrayRdistanceAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.itemText, arrReydis);

        spnleyedistance.setAdapter(arrayLdistanceAdapter);
        spnrdistance.setAdapter(arrayRdistanceAdapter);
        spnleyenear.setAdapter(arrayLnearAdapter);
        spnreyenear.setAdapter(arrayRnearAdapter);

        spnreyenear.setOnItemSelectedListener(this);
        spnleyenear.setOnItemSelectedListener(this);
        spnrdistance.setOnItemSelectedListener(this);
        spnleyedistance.setOnItemSelectedListener(this);

        userrefused.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    edtbloodsugar.setText("");
                    edtbloodsugar.setEnabled(false);
                } else {
                    edtbloodsugar.setEnabled(true);
                }
            }
        });

        btnAddhelathdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty(edtft.getText().toString()) || isEmpty(edtinches.getText().toString()))
                {
                    shortToast("Please Enter height details");
                }
                else if (isEmpty(edtweight.getText().toString()))
                {
                    shortToast("Please enter weight");
                }
                else if (isEmpty(edtpulse.getText().toString()))
                {
                    shortToast("Please enter pulse");
                }
                else if (isEmpty(edtbloodsugar.getText().toString()) && !userrefused.isChecked())
                {
                    shortToast("Please enter blood sugar");
                }
                else if (Integer.valueOf(SharedPreference.get("AGE")) > 18)
                {
                    if(isEmpty(edtSys.getText().toString()) || isEmpty(edtDias.getText().toString()))
                    {
                        shortToast("Please enter blood pressure details");
                    }
                    else
                    {
                        SharedPreference.save("LEFTEYEINTER",txtLeftEyeInterp.getText().toString());
                        SharedPreference.save("RIGHTEYEINTER",txtRightEyeInterp.getText().toString());
                        PopMedicalData pop = new PopMedicalData();
                        pop.setHeightinfit(Integer.valueOf(edtft.getText().toString()));
                        pop.setHeightininches(Integer.valueOf(edtinches.getText().toString()));
                        pop.setWeight(Integer.valueOf(edtweight.getText().toString()));
                        pop.setBmi(edtbmi.getText().toString());
                        pop.setBmiinter(edtbmiinterpretation.getText().toString());
                        pop.setBloodsugar(isEmpty(edtbloodsugar.getText().toString())? null :Integer.valueOf(edtbloodsugar.getText().toString()));
                        pop.setPatientrefused(userrefused.isChecked()? 1: 0);
                        pop.setBloodsugarinter(txtbloodsugar_interpretation.getText().toString());
                        pop.setPulse(Integer.valueOf(edtpulse.getText().toString()));
                        pop.setBpsystolic(isEmpty(edtSys.getText().toString()) ? null : Integer.valueOf(edtSys.getText().toString()));
                        pop.setBpdiastolic(isEmpty(edtDias.getText().toString()) ? null : Integer.valueOf(edtDias.getText().toString()));
                        pop.setBpinter(bloodpressure_interpretation.getText().toString());
                        pop.setOxysaturation(isEmpty(edto2saturation.getText().toString())? null : Integer.valueOf(edto2saturation.getText().toString()) );
                        pop.setVisualacuitylefteyenear(lefteyenear.equals("Select")? null : lefteyenear);
                        pop.setVisualacuityrighteyenear(righteyenear.equals("Select")? null : righteyenear );
                        pop.setVisualacuitylefteyedistant(lefteyedistance.equals("Select")? null : lefteyedistance);
                        pop.setVisualacuityrighteyedistant(righteyedistance.equals("Select") ? null : righteyedistance);
                        pop.setVisualacuityrighteyeinter(txtRightEyeInterp.getText().toString());
                        pop.setVisualacuitylefteyeinter(txtLeftEyeInterp.getText().toString());
                        PopulationMedicalModel.Updatehealthdata(SharedPreference.get("HOUSEHOLDID"),pop);

                        Intent i = new Intent(HealthDataActivity.this,MedicalHistoryActivity.class);
                        if (!isEmpty(editid) && editid != null )
                        {
                            Bundle bundle = new Bundle();
                            bundle.putString("EDITID",editid);
                            i.putExtras(bundle);
                            shortToast("Data updated successfully");
                            startActivity(i);
                        }
                        else
                        {
                            shortToast("Data added successfully");
                            startActivity(i);
                        }
                    }
                }
                else
                {
                    SharedPreference.save("LEFTEYEINTER",txtLeftEyeInterp.getText().toString());
                    SharedPreference.save("RIGHTEYEINTER",txtRightEyeInterp.getText().toString());
                    PopMedicalData pop = new PopMedicalData();
                    pop.setHeightinfit(Integer.valueOf(edtft.getText().toString()));
                    pop.setHeightininches(Integer.valueOf(edtinches.getText().toString()));
                    pop.setWeight(Integer.valueOf(edtweight.getText().toString()));
                    pop.setBmi(edtbmi.getText().toString());
                    pop.setBmiinter(edtbmiinterpretation.getText().toString());
                    pop.setBloodsugar(isEmpty(edtbloodsugar.getText().toString())? null :Integer.valueOf(edtbloodsugar.getText().toString()));
                    pop.setPatientrefused(userrefused.isChecked()? 1: 0);
                    pop.setBloodsugarinter(txtbloodsugar_interpretation.getText().toString());
                    pop.setPulse(Integer.valueOf(edtpulse.getText().toString()));
                    pop.setBpsystolic(isEmpty(edtSys.getText().toString()) ? null : Integer.valueOf(edtSys.getText().toString()));
                    pop.setBpdiastolic(isEmpty(edtDias.getText().toString()) ? null : Integer.valueOf(edtDias.getText().toString()));
                    pop.setBpinter(bloodpressure_interpretation.getText().toString());
                    pop.setOxysaturation(isEmpty(edto2saturation.getText().toString())? null : Integer.valueOf(edto2saturation.getText().toString()) );
                    pop.setVisualacuitylefteyenear(lefteyenear.equals("Select")? null : lefteyenear);
                    pop.setVisualacuityrighteyenear(righteyenear.equals("Select")? null : righteyenear );
                    pop.setVisualacuitylefteyedistant(lefteyedistance.equals("Select")? null : lefteyedistance);
                    pop.setVisualacuityrighteyedistant(righteyedistance.equals("Select") ? null : righteyedistance);
                    pop.setVisualacuityrighteyeinter(txtRightEyeInterp.getText().toString());
                    pop.setVisualacuitylefteyeinter(txtLeftEyeInterp.getText().toString());
                    PopulationMedicalModel.Updatehealthdata(SharedPreference.get("HOUSEHOLDID"),pop);

                    Intent i = new Intent(HealthDataActivity.this,MedicalHistoryActivity.class);
                    if (!isEmpty(editid) && editid != null )
                    {
                        Bundle bundle = new Bundle();
                        bundle.putString("EDITID",editid);
                        i.putExtras(bundle);
                        shortToast("Data updated successfully");
                        startActivity(i);
                    }
                    else
                    {
                        shortToast("Data added successfully");
                        startActivity(i);
                    }

                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int viewId = parent.getId();
        if (viewId == R.id.spnreyenear) {
                righteyenear = parent.getSelectedItem().toString();
                Log.i("ckm=>righteyeNear", righteyenear);
                reyenearvalue.setText(righteyenear);

        } else if (viewId == R.id.spnrdistance) {

                righteyedistance = parent.getSelectedItem().toString();
                Log.i("ckm=>righteyeDistance", righteyedistance);
                reyedistancevalue.setText(righteyedistance);

        } else if (viewId == R.id.spnleyedistance) {

                lefteyedistance = parent.getSelectedItem().toString();
                Log.i("ckm=>lefteyedistance", lefteyedistance);
                leyedistancevalue.setText(lefteyedistance);

        } else if (viewId == R.id.spnleyenear) {

                lefteyenear = parent.getSelectedItem().toString();
                Log.i("ckm=>lefteyenear", lefteyenear);
                leyenearvalue.setText(lefteyenear);
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

//    reyenearvalue,reyedistancevalue,leyenearvalue,leyedistancevalue



    TextWatcher Reyeinterprewatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!isEmpty(reyenearvalue.getText().toString()) && !isEmpty(reyedistancevalue.getText().toString()))
            {
                txtRightEyeInterp.setText(EYEInter(reyenearvalue.getText().toString(),reyedistancevalue.getText().toString()));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
    TextWatcher leyeinterprewatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!isEmpty(leyenearvalue.getText().toString()) && !isEmpty(leyedistancevalue.getText().toString()))
            {
                txtLeftEyeInterp.setText(EYEInter(leyenearvalue.getText().toString(),leyedistancevalue.getText().toString()));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };


     public String getBMIValue(String bmi) throws DAOException {

         LocalDate localDate = LocalDate.now();   //For reference Get current Date
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
         String formattedString = localDate.format(formatter);
         Log.i("ckm=>Formate",formattedString);

         LocalDate from = LocalDate.parse(SharedPreference.get("DOB"), formatter);  //Get DOB
         LocalDate to = LocalDate.parse(formattedString, formatter);

         System.out.println(from.until(to, ChronoUnit.MONTHS)); //Got Total No Of Months

         String noofmonths = String.valueOf(from.until(to,ChronoUnit.MONTHS));
         int month = Integer.valueOf(noofmonths);

         Log.i("BMI_DEBUG", "BMI: " + bmi + " | Age (months): " + month);

         if (month < 24) {
             return "UnderAge";
         }
         else if (month >= 24 && month < 60)
         {
             if (SharedPreference.get("GENDER").equals("Male"))
             {
                 commonmasterdata = ChartcommonMasterModel.getmatchdata("BMI for Age","KHA","Boys","Birth to 18 Years");
                 normalrangedata = ChartNormalRangeModel.getmatchresult(commonmasterdata.get(0).getChartid(),String.valueOf(month));
             }
             else if (SharedPreference.get("GENDER").equals("Female"))
             {
                 commonmasterdata = ChartcommonMasterModel.getmatchdata("BMI for Age","KHA","Girls","Birth to 18 Years");
                 normalrangedata = ChartNormalRangeModel.getmatchresult(commonmasterdata.get(0).getChartid(),String.valueOf(month));
             }

             return "UnderAge";
         }
         else if (month >= 60 && month <= 216)
         {
             if (SharedPreference.get("GENDER").equals("Male"))
             {
                 commonmasterdata = ChartcommonMasterModel.getmatchdata("BMI for Age","KHA","Boys","Birth to 18 Years");
                 normalrangedata = ChartNormalRangeModel.getmatchresult(commonmasterdata.get(0).getChartid(),String.valueOf(month));
             }
             else if (SharedPreference.get("GENDER").equals("Female"))
             {
                 commonmasterdata = ChartcommonMasterModel.getmatchdata("BMI for Age","KHA","Girls","Birth to 18 Years");
                 normalrangedata = ChartNormalRangeModel.getmatchresult(commonmasterdata.get(0).getChartid(),String.valueOf(month));

                 Log.i("ckm=>nsdns",normalrangedata.get(0).getY3());
                 Log.i("ckm=>nsdns1",normalrangedata.get(0).getY23());
                 Log.i("ckm=>nsdns2",normalrangedata.get(0).getY27());
                 /*if (gcStnd.equals("KHA")) {*/
             }
             if (normalrangedata != null) {
                 if (Double.valueOf(bmi) < Double.valueOf(normalrangedata.get(0).getY3())) {
                     return "Underweight";
                 } else if (Double.valueOf(bmi) >= Double.valueOf(normalrangedata.get(0).getY3())
                         && Double.valueOf(bmi) < Double.valueOf(normalrangedata.get(0)
                         .getY23())) {
                     return "Normal";
                 } else if (Double.valueOf(bmi) >= Double.valueOf(normalrangedata.get(0)
                         .getY23())
                         && Double.valueOf(bmi) < Double.valueOf(normalrangedata.get(0).getY27())) {
                     return "Overweight";
                 } else if (Double.valueOf(bmi) >= Double.valueOf(normalrangedata.get(0).getY27())) {
                     return "Obese";
                 }
                 /*}*/
             }
         }
         else if (month > 216)
         {
             if (Double.valueOf(bmi) <= 18.5) {
                 return "Underweight";
             } else if (Double.valueOf(bmi) > 18.5 && Double.valueOf(bmi) <= 23) {
                 return "Normal";
             } else if (Double.valueOf(bmi) > 23 && Double.valueOf(bmi) <= 30) {
                 return "Overweight = Grade-I Obesity";
             } else if (Double.valueOf(bmi) > 30 && Double.valueOf(bmi) <= 35) {
                 return "Obese = Grade-II Obesity";
             } else if (Double.valueOf(bmi) > 35 && Double.valueOf(bmi) < 40) {
                 return "Moderately Obese = Grade-III Obesity";
             } else if (Double.valueOf(bmi) >= 40) {
                 return "Morbid Obesity = Grade-IV Obesity";
             }
         }
        return null;
    }

     public String EYEInter(String rnear,String rdistance)
     {
         String eyeresult;
         if (rnear.equals("N5") && rdistance.equals("6/9.5"))
         {
             eyeresult = "normal";
         }
         else if (rnear.equals("N5") && rdistance.equals("6/7.5"))
         {
             eyeresult = "normal";
         }
         else if (rnear.equals("N5") && rdistance.equals("6/6"))
         {
             eyeresult = "normal";
         }
         else if (rnear.equals("N6") && rdistance.equals("6/9.5"))
         {
             eyeresult = "normal";
         }
         else if (rnear.equals("N6") && rdistance.equals("6/7.5"))
         {
             eyeresult = "normal";
         }
         else if (rnear.equals("N6") && rdistance.equals("6/6"))
         {
             eyeresult = "normal";
         }
         else if (rnear.equals("N8") && rdistance.equals("6/9.5"))
         {
             eyeresult = "normal";
         }
         else if (rnear.equals("N8") && rdistance.equals("6/7.5"))
         {
             eyeresult = "normal";
         }
         else if (rnear.equals("N8") && rdistance.equals("6/6"))
         {
             eyeresult = "normal";
         }else if (rnear.equals("Select") || rdistance.equals("Select"))
         {
             eyeresult = "";
         }

         else
         { eyeresult = "abnormal";
         }         return eyeresult;
     }

     TextWatcher bmiwatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!isEmpty(edtft.getText().toString()) && !isEmpty(edtinches.getText().toString()) && !isEmpty(edtweight.getText().toString()))
            {
                double d = convertFeetandInchesToCentimeter(edtft.getText().toString(),edtinches.getText().toString());
                float  height = Float.parseFloat(String.valueOf(d)) / 100 ;
                float weight = Float.parseFloat(edtweight.getText().toString());
                float bmi = weight / (height * height);
                edtbmi.setText(displayBMI(bmi)); // just number
                try {
                    String interpretation = getBMIValue(String.valueOf(bmi));
                    if (interpretation != null) {
                        edtbmiinterpretation.setText(interpretation);
                    } else {
                        edtbmiinterpretation.setText("No interpretation");
                    }
                } catch (DAOException e) {
                    e.printStackTrace();
                    edtbmiinterpretation.setText("Error in calculation");
                }

            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };



    TextWatcher bldGlucoseWatcher = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String bldGlucoseInt = getBloodGlucoseInterpretation(edtbloodsugar.getText().toString());
            if (bldGlucoseInt != null && bldGlucoseInt.equals("Normal")) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    txtbloodsugar_interpretation.setTextColor(getResources().getColor(R.color.green, null));
                } else {
                    txtbloodsugar_interpretation.setTextColor(getResources().getColor(R.color.green));
                }
            } else {
                txtbloodsugar_interpretation.setTextColor(Color.RED);
            }
            txtbloodsugar_interpretation.setText(bldGlucoseInt);
        }

        @Override
        public void afterTextChanged(Editable s) { }
    };

    TextWatcher ReyeInterWatcher = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String righteyeinter = getIOPInterpretation(edtreyedistance.getText().toString());
            txtRightEyeInterp.setText(righteyeinter);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    TextWatcher LeyeInterWatcher = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String lefteyeinter = getIOPInterpretation(edtleyedistance.getText().toString());
            txtLeftEyeInterp.setText(lefteyeinter);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };


    public String getBloodGlucoseInterpretation(String bldGlucoseStr) {
        if (!isEmpty(bldGlucoseStr)) {
            if (!bldGlucoseStr.equals(".")) {
                double bldGlucose = Double.parseDouble(bldGlucoseStr);
                if (bldGlucose < 79.00) {
                    return "Hypoglycemia";
                } else if (bldGlucose >= 79 && bldGlucose <= 160) {
                    return "Normal";
                } else if (bldGlucose > 160 && bldGlucose <= 200) {
                    return "Pre-Diabetes";
                } else if (bldGlucose > 200.00) {
                    return "Diabetes";
                }
            }
        }
        return null;
    }

    public static double convertFeetandInchesToCentimeter(String feet, String inches) {
        double heightInFeet = 0;
        double heightInInches = 0;
        try {
            if (feet != null && feet.trim().length() != 0) {
                heightInFeet = Double.parseDouble(feet);
            }
            if (inches != null && inches.trim().length() != 0) {
                heightInInches = Double.parseDouble(inches);
            }
        } catch (NumberFormatException nfe) {

        }
        return (heightInFeet * 30.48) + (heightInInches * 2.54);
    }

    public String displayBMI(float bmi) {
        return String.format(Locale.US, "%.2f", bmi); // e.g. 22.35
    }



    public String getIOPInterpretation(String result) {
        if (!isEmpty(result)) {
            Double iop = Double.parseDouble(result);
            if (iop < 5) {
                return "Ocular Hypotony";
            } else if (iop < 8) {
                return "Ocular hypotension";
            } else if (iop <= 21) {
                return "Normal";
            } else if (iop > 21) {
                return "Ocular hypertension";
            }
        }
        return null;
    }

    //User After Service
    TextWatcher bpwatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String bpInt = getBloodPressureInterpretation(edtSys.getText().toString(), edtDias.getText().toString());
            if (bpInt != null && bpInt.equals("Normal")) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    bloodpressure_interpretation.setTextColor(getResources().getColor(R.color.green, null));
                } else {
                    bloodpressure_interpretation.setTextColor(getResources().getColor(R.color.green));
                }
            } else {
                bloodpressure_interpretation.setTextColor(Color.RED);
            }
            bloodpressure_interpretation.setText(bpInt);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

     public String getBloodPressureInterpretation(String systolic, String diastolic) {
            if (!isEmpty(systolic) && !isEmpty(diastolic) && !isEmpty(SharedPreference.get("DOB")))
            {
            LocalDate todaysDate;
            todaysDate = LocalDate.now();
            SimpleDateFormat sdf = new SimpleDateFormat(
                    AttributeSet.Constants.REVERSE_SHORT_DATE, Locale.ENGLISH);
            Date date = null;
            try {
                date = sdf.parse(SharedPreference.get("DOB"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            LocalDate birthDate = LocalDate.of(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.DAY_OF_MONTH));
            long ageInMonths = ChronoUnit.MONTHS.between(birthDate, todaysDate);
//            Months birthInMonths = Month.monthsBetween(birthDate, todaysDate);
            if (isEmpty(systolic)) {
                systolic = "0";
            }
            if (isEmpty(diastolic)) {
                diastolic = "0";
            }
            Integer intSystolic = Integer.parseInt(systolic);
            Integer intDiastolic = Integer.parseInt(diastolic);
            String systolicInt = null, diastolicInt = null;
            if (ageInMonths > 216) {
                if (intSystolic == 0) {
                    systolicInt = "";
                } else if (intSystolic < 90) {
                    systolicInt = "Hypotension";
                } else if (intSystolic < 120) {
                    systolicInt = "Normal";
                } else if (intSystolic >= 120 && intSystolic <= 139) {
                    systolicInt = "Prehypertension";
                } else if (intSystolic >= 140 && intSystolic <= 159) {
                    systolicInt = "Stage 1 Hypertension";
                } else if (intSystolic >= 160) {
                    systolicInt = "Stage 2 Hypertension";
                }

                if (intDiastolic == 0) {
                    diastolicInt = "";
                } else if (intDiastolic < 60) {
                    diastolicInt = "Hypotension";
                } else if (intDiastolic < 80) {
                    diastolicInt = "Normal";
                } else if (intDiastolic >= 80 && intDiastolic <= 89) {
                    diastolicInt = "Prehypertension";
                } else if (intDiastolic >= 90 && intDiastolic <= 99) {
                    diastolicInt = "Stage 1 Hypertension";
                } else if (intDiastolic >= 100) {
                    diastolicInt = "Stage 2 Hypertension";
                }
            } else {
                BloodPressureNRData data;
                try {
                    data = bloodPressureDAO.getBloodPressureRangeByAge(String.valueOf(ageInMonths));
                    if (data != null) {
                        if (intSystolic == 0) {
                            systolicInt = "";
                        } else if (!isEmpty(data.getSystolicRangeStart()) && intSystolic >= Integer.parseInt(data.getSystolicRangeStart()) && !isEmpty(data.getSystolicRangeEnd()) && intSystolic <= Integer.parseInt(data.getSystolicRangeEnd())) {
                            systolicInt = "Normal";
                        } else if (!isEmpty(data.getSystolicRangeStart()) && intSystolic < Integer.parseInt(data.getSystolicRangeStart())) {
                            systolicInt = "Low Blood Pressure";
                        } else if (!isEmpty(data.getSystolicRangeEnd()) && intSystolic > Integer.parseInt(data.getSystolicRangeEnd())) {
                            systolicInt = "High Blood Pressure";
                        }

                        if (intDiastolic == 0) {
                            diastolicInt = "";
                        } else if (!isEmpty(data.getDiastolicRangeStart()) && intDiastolic >= Integer.parseInt(data.getDiastolicRangeStart()) && !isEmpty(data.getDiastolicRangeEnd()) && intDiastolic <= Integer.parseInt(data.getDiastolicRangeEnd())) {
                            diastolicInt = "Normal";
                        } else if (!isEmpty(data.getDiastolicRangeStart()) && intDiastolic < Integer.parseInt(data.getDiastolicRangeStart())) {
                            diastolicInt = "Low Blood Pressure";
                        } else if (!isEmpty(data.getDiastolicRangeEnd()) && intDiastolic > Integer.parseInt(data.getDiastolicRangeEnd())) {
                            diastolicInt = "High Blood Pressure";
                        }
                    }
                } catch (DAOException e) {
                    e.printStackTrace();
                }
            }
            if (systolicInt != null && diastolicInt != null) {
                if (systolicInt.equals("Normal") && diastolicInt.equals("Normal")) {
                    return "Normal";
                } else if (systolicInt.equals("Normal") && !diastolicInt.equals("Normal")) {
                    return diastolicInt;
                } else if (!systolicInt.equals("Normal") && diastolicInt.equals("Normal")) {
                    return systolicInt;
                } else if (!systolicInt.equals("Normal") && !diastolicInt.equals("Normal")) {
                    return systolicInt;
                }
            }
            }
        return null;
    }
}




