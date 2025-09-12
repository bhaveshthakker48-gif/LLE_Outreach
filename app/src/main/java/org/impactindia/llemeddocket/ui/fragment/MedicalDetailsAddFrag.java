package org.impactindia.llemeddocket.ui.fragment;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.icu.util.LocaleData;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.impactindia.llemeddocket.AttributeSet;
import org.impactindia.llemeddocket.DatabaseHelper;
import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.orm.BloodPressureNRDataDAO;
import org.impactindia.llemeddocket.orm.CampDAO;
import org.impactindia.llemeddocket.orm.CampPatientDAO;
import org.impactindia.llemeddocket.orm.DAOException;
import org.impactindia.llemeddocket.orm.MedicalDetailsDAO;
import org.impactindia.llemeddocket.orm.SubTagDAO;
import org.impactindia.llemeddocket.pojo.BloodPressureNRData;
import org.impactindia.llemeddocket.pojo.Camp;
import org.impactindia.llemeddocket.pojo.MedicalDetails;
import org.impactindia.llemeddocket.pojo.Patient;
import org.impactindia.llemeddocket.pojo.SubTag;
import org.impactindia.llemeddocket.util.MoneyValueFilter;
import org.impactindia.llemeddocket.view.MultiSelectionSpinner;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.Month;
import org.threeten.bp.ZoneId;
import org.threeten.bp.temporal.ChronoUnit;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MedicalDetailsAddFrag extends BaseFrag implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    public static final String PATIENT_ID = "patient_id";

    private Toolbar toolbar;
    private EditText txtDOE, txtDiagnosis, txtWeight, txtBldPressureMM, txtHemoglobin, txtBldPressureHG, txtBldGlucose;
    private TextView lblPatientName, lblPatientAgeGender, lblBPInt, lblHemoglobinInt, lblBldGlucoseInt;
    private Spinner spnHIV, spnHBsAg;
    private MultiSelectionSpinner mulSelSpnAssignTag;
    private CheckBox chkOPD, chkSurgery;
    private Button btnSubmit;

    private Bundle args;
    private SQLiteDatabase db;
    private BloodPressureNRDataDAO bloodPressureDAO;
    private CampPatientDAO campPatientDAO;
    private CampDAO campDAO;
    private SubTagDAO subTagDAO;
    private MedicalDetailsDAO medicalDetailsDAO;
    private Long patientId;
    private Patient patient;
    private Camp camp;
    private LocalDate examDate;
    private String[] hivTestArr, hbsagTestArr;
    private ArrayAdapter<String> hivTestAdapter;
    private ArrayAdapter<String> hbsagTestAdapter;
    private List<SubTag> subTagList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = getArguments();
        patientId = args.getLong(PATIENT_ID);
        hivTestArr = getResources().getStringArray(R.array.test_unit);
        hbsagTestArr = getResources().getStringArray(R.array.test_unit);
        db = new DatabaseHelper(getActivity()).getWritableDatabase();
        medicalDetailsDAO = new MedicalDetailsDAO(getActivity(), db);
        bloodPressureDAO = new BloodPressureNRDataDAO(getActivity(), db);
        campPatientDAO = new CampPatientDAO(getActivity(), db);
        if (patientId != null) {
            try {
                patient = campPatientDAO.findFirstByField(Patient.PATIENT_ID, patientId);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }
        subTagDAO = new SubTagDAO(getActivity(), db);
        if (patient != null) {
            subTagList = subTagDAO.findAllByTagIds(patient.getTagId(), "order by " + SubTag.TAG_ID);
        }
        campDAO = new CampDAO(getActivity(), db);
        List<Camp> campList = campDAO.findAll();
        if (!campList.isEmpty()) {
            camp = campList.get(0);
        }
        hivTestAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, R.id.itemText, hivTestArr);
        hbsagTestAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, R.id.itemText, hbsagTestArr);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.frag_medical_details_add, container, false);
        initViews(view, savedInstanceState);
        return view;
    }

    public void initViews(View view, Bundle savedInstanceState) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_medical_details);

        lblPatientName = view.findViewById(R.id.lblPatientName);
        lblPatientAgeGender = view.findViewById(R.id.lblPatientAgeGender);
        if (patient != null) {
            lblPatientName.setText(patient.getfName() + " " + patient.getlName());
            lblPatientAgeGender.setText(patient.getAge() + " " + patient.getAgeUnit() + " / " + patient.getGender());
        }
        txtDOE = view.findViewById(R.id.txtDOE);
        txtDOE.setOnClickListener(this);
        txtDiagnosis = view.findViewById(R.id.txtDiagnosis);
        chkOPD = view.findViewById(R.id.chkOPD);
        chkSurgery = view.findViewById(R.id.chkSurgery);
        txtWeight = view.findViewById(R.id.txtWeight);
        txtWeight.setFilters(new InputFilter[]{new MoneyValueFilter()});
        txtBldPressureMM = view.findViewById(R.id.txtBldPressureMM);
        txtBldPressureMM.addTextChangedListener(bpwatcher);
        txtBldPressureHG = view.findViewById(R.id.txtBldPressureHG);
        txtBldPressureHG.addTextChangedListener(bpwatcher);
        lblBPInt = view.findViewById(R.id.lblBPInt);
        txtHemoglobin = view.findViewById(R.id.txtHemoglobin);
        txtHemoglobin.addTextChangedListener(hemoglobinWatcher);
        lblHemoglobinInt = view.findViewById(R.id.lblHemoglobinInt);
        txtBldGlucose = view.findViewById(R.id.txtBldGlucose);
        txtBldGlucose.setFilters(new InputFilter[]{new MoneyValueFilter()});
        txtBldGlucose.addTextChangedListener(bldGlucoseWatcher);
        lblBldGlucoseInt = view.findViewById(R.id.lblBldGlucoseInt);
        spnHIV = view.findViewById(R.id.spnHIV);
        spnHIV.setAdapter(hivTestAdapter);
        spnHBsAg = view.findViewById(R.id.spnHBsAg);
        spnHBsAg.setAdapter(hbsagTestAdapter);
        mulSelSpnAssignTag = view.findViewById(R.id.mulSelSpnAssignTag);
        mulSelSpnAssignTag.setEnabled(false);
        if (subTagList != null && !subTagList.isEmpty()) {
            List<String> subTagStrList = new ArrayList<>();
            for (SubTag subTag : subTagList) {
                subTagStrList.add(subTag.getSubTagName());
            }
            if (subTagStrList.isEmpty()) {
                mulSelSpnAssignTag.setEnabled(false);
            } else {
                mulSelSpnAssignTag.setItems(subTagStrList);
                mulSelSpnAssignTag.setEnabled(true);
            }
        }
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        setLastVisitDateAsExaminationDate();
    }

    TextWatcher hemoglobinWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String hemoglobinInt = getHemoglobiniInterpretation(s.toString());
            if (hemoglobinInt != null && hemoglobinInt.equals("Normal")) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    lblHemoglobinInt.setTextColor(getResources().getColor(R.color.green, null));
                } else {
                    lblHemoglobinInt.setTextColor(getResources().getColor(R.color.green));
                }
            } else {
                lblHemoglobinInt.setTextColor(Color.RED);
            }
            lblHemoglobinInt.setText(hemoglobinInt);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    TextWatcher bldGlucoseWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String bldGlucoseInt = getBloodGlucoseInterpretation(txtBldGlucose.getText().toString());
            if (bldGlucoseInt != null && bldGlucoseInt.equals("Normal")) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    lblBldGlucoseInt.setTextColor(getResources().getColor(R.color.green, null));
                } else {
                    lblBldGlucoseInt.setTextColor(getResources().getColor(R.color.green));
                }
            } else {
                lblBldGlucoseInt.setTextColor(Color.RED);
            }
            lblBldGlucoseInt.setText(bldGlucoseInt);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public String getHemoglobiniInterpretation(String hemoglobinStr) {
        String hemoglobinInt = null;
        if (patient != null && !isEmpty(hemoglobinStr)) {
            if (!hemoglobinStr.equals(".")) {
                Double hemoglobin = Double.parseDouble(hemoglobinStr);
                LocalDate todaysDate;
                todaysDate = LocalDate.now();
                SimpleDateFormat sdf = new SimpleDateFormat(
                        AttributeSet.Constants.REVERSE_SHORT_DATE, Locale.ENGLISH);
                Date date = null;
                try {
                    date = sdf.parse(patient.getDob());
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
                long ageInYears = ChronoUnit.YEARS.between(birthDate, todaysDate);
                if (ageInMonths < 6) {
                    hemoglobinInt = null;
                } else if (ageInMonths >= 6 && ageInMonths <= 59) {
                    if (hemoglobin < 7) {
                        hemoglobinInt = "Severe Anemia";
                    } else if (hemoglobin >= 7 && hemoglobin <= 9.9) {
                        hemoglobinInt = "Moderate Anemia";
                    } else if (hemoglobin >= 10 && hemoglobin <= 10.9) {
                        hemoglobinInt = "Mild Anemia";
                    } else {
                        hemoglobinInt = "Normal";
                    }
                } else if (ageInYears >= 5 && ageInYears <= 11) {
                    if (hemoglobin < 8) {
                        hemoglobinInt = "Severe Anemia";
                    } else if (hemoglobin >= 8 && hemoglobin <= 10.9) {
                        hemoglobinInt = "Moderate Anemia";
                    } else if (hemoglobin >= 11 && hemoglobin <= 11.4) {
                        hemoglobinInt = "Mild Anemia";
                    } else {
                        hemoglobinInt = "Normal";
                    }
                } else if (ageInYears >= 12 && ageInYears <= 14) {
                    if (hemoglobin < 8) {
                        hemoglobinInt = "Severe Anemia";
                    } else if (hemoglobin >= 8 && hemoglobin <= 10.9) {
                        hemoglobinInt = "Moderate Anemia";
                    } else if (hemoglobin >= 11 && hemoglobin <= 11.9) {
                        hemoglobinInt = "Mild Anemia";
                    } else {
                        hemoglobinInt = "Normal";
                    }
                } else if (ageInYears > 14) {
                    if (patient.getGender().equals("Male")) {
                        if (hemoglobin < 8) {
                            hemoglobinInt = "Severe Anemia";
                        } else if (hemoglobin >= 8 && hemoglobin <= 10.9) {
                            hemoglobinInt = "Moderate Anemia";
                        } else if (hemoglobin >= 11 && hemoglobin <= 12.9) {
                            hemoglobinInt = "Mild Anemia";
                        } else {
                            hemoglobinInt = "Normal";
                        }
                    } else if (patient.getGender().equals("Female")) {
                        if (hemoglobin < 8) {
                            hemoglobinInt = "Severe Anemia";
                        } else if (hemoglobin >= 8 && hemoglobin <= 10.9) {
                            hemoglobinInt = "Moderate Anemia";
                        } else if (hemoglobin >= 11 && hemoglobin <= 11.9) {
                            hemoglobinInt = "Mild Anemia";
                        } else {
                            hemoglobinInt = "Normal";
                        }
                    } else {
                        hemoglobinInt = null;
                    }
                }
            }
        }
        return hemoglobinInt;
    }

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

    TextWatcher bpwatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String bpInt = getBloodPressureInterpretation(txtBldPressureMM.getText().toString(), txtBldPressureHG.getText().toString());
            if (bpInt != null && bpInt.equals("Normal")) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    lblBPInt.setTextColor(getResources().getColor(R.color.green, null));
                } else {
                    lblBPInt.setTextColor(getResources().getColor(R.color.green));
                }
            } else {
                lblBPInt.setTextColor(Color.RED);
            }
            lblBPInt.setText(bpInt);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    public long getAgeInMonths() {
        if (patient != null) {
            LocalDate todaysDate;
            todaysDate = LocalDate.now();
            SimpleDateFormat sdf = new SimpleDateFormat(
                    AttributeSet.Constants.REVERSE_SHORT_DATE, Locale.ENGLISH);
            Date date = null;
            try {
                date = sdf.parse(patient.getDob());
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
            return ageInMonths;
        }
        return 0;
    }

    public String getBloodPressureInterpretation(String systolic, String diastolic) {
        if (patient != null && (!isEmpty(systolic) || !isEmpty(diastolic))) {
            LocalDate todaysDate;
            todaysDate = LocalDate.now();
            SimpleDateFormat sdf = new SimpleDateFormat(
                    AttributeSet.Constants.REVERSE_SHORT_DATE, Locale.ENGLISH);
            Date date = null;
            try {
                date = sdf.parse(patient.getDob());
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

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.btnSubmit) {
            if (isValidationSuccess()) {
                submitMedicalData();
            }
        } else if (viewId == R.id.txtDOE) {
            showDatePickerDialog();
        }
    }

    public void submitMedicalData() {
        if (camp != null && patient != null) {
            StringBuilder tagSB = new StringBuilder();
            StringBuilder subTagIdSB = new StringBuilder();
            StringBuilder tagIdSB = new StringBuilder();
            boolean foundOne = false;
            for (Integer index : mulSelSpnAssignTag.getSelectedIndices()) {
                if (foundOne) {
                    tagSB.append("~");
                    subTagIdSB.append(",");
                    tagIdSB.append(",");
                }
                foundOne = true;
                tagSB.append(subTagList.get(index).getSubTagName());
                subTagIdSB.append(subTagList.get(index).getSubTagId());
                tagIdSB.append(subTagList.get(index).getTagId());
            }
            MedicalDetails details = new MedicalDetails();
            details.setCampId(camp.getCampId());
            details.setUserId(patient.getUserId());
            details.setPatientId(patient.getPatientId());
            SimpleDateFormat sdf = new SimpleDateFormat(AttributeSet.Constants.SHORT_DATE_FORMAT);
            SimpleDateFormat shortDF = new SimpleDateFormat(AttributeSet.Constants.REVERSE_SHORT_DATE);
            try {
                Date examDate = sdf.parse(txtDOE.getText().toString());
                details.setDateOfExamination(shortDF.format(examDate));
            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            details.setWeight(txtWeight.getText().toString());
            details.setBpSystolic(txtBldPressureMM.getText().toString());
            details.setBpDiastolic(txtBldPressureHG.getText().toString());
            details.setBpInterPretation(lblBPInt.getText().toString());
            details.setBldGlucoseRandom(txtBldGlucose.getText().toString());
            details.setBldGlucoseRandomInterPretation(lblBldGlucoseInt.getText().toString());
            details.setHbsag(spnHBsAg.getSelectedItemPosition() == AttributeSet.Constants.ZERO ? null : spnHBsAg.getSelectedItem().toString());
            details.setHiv(spnHIV.getSelectedItemPosition() == AttributeSet.Constants.ZERO ? null : spnHIV.getSelectedItem().toString());
            details.setHemoglobin(txtHemoglobin.getText().toString());
            details.setHemoglobinInter(lblHemoglobinInt.getText().toString());
            details.setDiagnosis(txtDiagnosis.getText().toString());
            details.setOpd(chkOPD.isChecked());
            details.setSurgery(chkSurgery.isChecked());
            details.setSubTagName(tagSB.toString().isEmpty() ? null : tagSB.toString());
            details.setSubTagId(subTagIdSB.toString().isEmpty() ? null : subTagIdSB.toString());
            details.setTagId(tagIdSB.toString().isEmpty() ? null : tagIdSB.toString());
            details.setFresh(true);
            try {
                long id = medicalDetailsDAO.create(details);
                if (id != -1) {
                    clearValues();
                    shortToast(getString(R.string.data_added_successfully));
                    getActivity().getSupportFragmentManager().popBackStackImmediate();
                } else {
                    longToast(getString(R.string.submit_medical_details));
                }
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isValidationSuccess() {
        if (isEmpty(txtDOE.getText().toString())) {
            shortToast(getString(R.string.doe_req_err_msg));
        } else if (isEmpty(txtDiagnosis.getText().toString())) {
            shortToast(getString(R.string.diagnosis_req_err_msg));
        } else if (!chkOPD.isChecked() && !chkSurgery.isChecked()) {
            shortToast(getString(R.string.visit_type_req_err_msg));
        } else if (getAgeInMonths() >= 300 && isEmpty(txtBldPressureMM.getText().toString())) {
            shortToast(getString(R.string.systolic_bp_req_err_msg));
        } else if (getAgeInMonths() >= 300 && isEmpty(txtBldPressureHG.getText().toString())) {
            shortToast(getString(R.string.diastolic_bp_req_err_msg));
        } else if (getAgeInMonths() >= 300 && isEmpty(txtBldGlucose.getText().toString())) {
            shortToast(getString(R.string.blood_glucose_random_req_err_msg));
        } else if (mulSelSpnAssignTag.getSelectedIndices().isEmpty()) {
            shortToast(getString(R.string.tags_req_err_msg));
        } else {
            return true;
        }
        return false;
    }

    public void clearValues() {
        txtDOE.setText(null);
        txtWeight.setText(null);
        txtBldPressureMM.setText(null);
        txtBldPressureHG.setText(null);
        lblBPInt.setText(null);
        txtBldGlucose.setText(null);
        lblBldGlucoseInt.setText(null);
        txtDiagnosis.setText(null);
        chkOPD.setChecked(false);
        chkSurgery.setChecked(false);
        txtHemoglobin.setText(null);
        lblHemoglobinInt.setText(null);
        spnHIV.setSelection(AttributeSet.Constants.ZERO);
        spnHBsAg.setSelection(AttributeSet.Constants.ZERO);
        mulSelSpnAssignTag.clearAdapter();
    }

    public void setTodayDateAsExaminationDate() {
        Calendar c;
        c = Calendar.getInstance();
        int year, monthOfYear, dayOfMonth;

        year = c.get(Calendar.YEAR);
        monthOfYear = c.get(Calendar.MONTH);
        dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        monthOfYear++;

        DecimalFormat mformat;
        mformat = new DecimalFormat("00");
        examDate = LocalDate.of(year, monthOfYear, dayOfMonth);

        StringBuilder sb;
        sb = new StringBuilder();
        sb.append(mformat.format(dayOfMonth));
        sb.append("-");
        sb.append(mformat.format(monthOfYear));
        sb.append("-");
        sb.append(year);
        txtDOE.setText(sb.toString());
        txtDOE.setError(null);
    }

    public void setLastVisitDateAsExaminationDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(AttributeSet.Constants.SHORT_DATE_FORMAT);
        SimpleDateFormat reverseSDF = new SimpleDateFormat(AttributeSet.Constants.REVERSE_SHORT_DATE);
        try {
            Date date = reverseSDF.parse(patient.getLastVisitDate());
            txtDOE.setText(sdf.format(date));
            examDate = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (ParseException e1) {
            e1.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private void showDatePickerDialog() {
        Calendar c;
        c = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this,
                c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        if (isEventDateSelectedValid(year, monthOfYear, dayOfMonth)) {
            ++monthOfYear;
            DecimalFormat mformat;
            mformat = new DecimalFormat("00");
            examDate = LocalDate.of(year, monthOfYear, dayOfMonth);

            StringBuilder sb;
            sb = new StringBuilder();
            sb.append(mformat.format(dayOfMonth));
            sb.append("-");
            sb.append(mformat.format(monthOfYear));
            sb.append("-");
            sb.append(year);
            txtDOE.setText(sb.toString());
            txtDOE.setError(null);
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
}
