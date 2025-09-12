package org.impactindia.llemeddocket.ui.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import org.impactindia.llemeddocket.AttributeSet;
import org.impactindia.llemeddocket.DatabaseHelper;
import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.adapter.StateAdapter;
import org.impactindia.llemeddocket.orm.CampDAO;
import org.impactindia.llemeddocket.orm.DAOException;
import org.impactindia.llemeddocket.orm.PatientDAO;
import org.impactindia.llemeddocket.orm.StateDAO;
import org.impactindia.llemeddocket.orm.TagDAO;
import org.impactindia.llemeddocket.orm.UserDetailsDAO;
import org.impactindia.llemeddocket.pojo.Base;
import org.impactindia.llemeddocket.pojo.Camp;
import org.impactindia.llemeddocket.pojo.Patient;
import org.impactindia.llemeddocket.pojo.State;
import org.impactindia.llemeddocket.pojo.Tag;
import org.impactindia.llemeddocket.pojo.UserDetails;
import org.impactindia.llemeddocket.pojo.WsModel;
import org.impactindia.llemeddocket.ui.activity.PatientRegPdfActivity;
import org.impactindia.llemeddocket.util.CursorUtils;
import org.impactindia.llemeddocket.util.EmailValidator;
import org.impactindia.llemeddocket.util.ImageFilePath;
import org.impactindia.llemeddocket.util.NetworkConnection;
import org.impactindia.llemeddocket.view.FontedAutoCompleteTextView;
import org.impactindia.llemeddocket.view.MultiSelectionSpinner;
import org.impactindia.llemeddocket.ws.AsyncWebServiceAdapter;
import org.impactindia.llemeddocket.ws.WebService;
import org.impactindia.llemeddocket.ws.WsCallCompleteListener;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.Period;
import org.threeten.bp.temporal.ChronoUnit;
import org.threeten.bp.temporal.WeekFields;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientRegFrag extends BaseFrag implements View.OnClickListener, DatePickerDialog.OnDateSetListener, WsCallCompleteListener, View.OnFocusChangeListener {

    private static final String AUTHORITY = "org.impactindia.llemeddocket";
    public static final String TAG = "PatientRegFrag";

    public static final int PERM_REQ_WRITE_EXT_STORAGE = 0002;
    private static final int GET_TAG_DETAIL = 111;
    private static final int GET_STATE_DETAIL = 222;

    private final static int REQUEST_CAMERA = 0;
    private final static int SELECT_FILE = 1;

    public static final String USER_ID = "user_id";
    public static final String REG_NO = "reg_no";
    public static final String P_ID = "p_id";
    public static final String SKIP_PDF = "skip_pdf";

    private Toolbar toolbar;
    private TextView lblRegNo;
    private EditText txtRegNo;
    private ImageView imgPatientPhoto;
    private EditText txtFName, txtMName, txtLName, txtDOB, txtAge;
    private EditText txtHouseNoName, txtStreetName, txtLocality, txtCity, txtTaluka, txtDistrict, txtPincode;
    private EditText txtMobileNo, txtResidenceNo, txtAadharNo, txtEmailId, txtRefBy;
    private MultiSelectionSpinner mulSelSpnAssignTag;
    private FontedAutoCompleteTextView autoTxtState, autoTxtCountry;
    private Spinner spnGender, spnAgeUnit;
    private Button btnRegister;

    private String[] genderArr;
    private String[] ageUnitArr;
    private ArrayAdapter<String> ageUnitAdapter;
    private ArrayAdapter<String> genderAdapter;
    private LocalDate birthDate;
    private List<Tag> tagList;

    private SQLiteDatabase db;
    private CampDAO campDAO;
    private TagDAO tagDAO;
    private PatientDAO patientDAO;
    private UserDetailsDAO userDetailsDAO;
    private UserDetails userDetails;
    private StateDAO stateDAO;
    private StateAdapter stateAdapter;
    private Camp camp;
    private String profileImgPath, profileImgName;
    private Uri imgUri;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ageUnitArr = getResources().getStringArray(R.array.age_unit);
        genderArr = getResources().getStringArray(R.array.gender);
        db = new DatabaseHelper(getActivity()).getReadableDatabase();
        campDAO = new CampDAO(getActivity(), db);
        tagDAO = new TagDAO(getActivity(), db);
        patientDAO = new PatientDAO(getActivity(), db);
        userDetailsDAO = new UserDetailsDAO(getActivity(), db);
        stateDAO = new StateDAO(getActivity(), db);
        List<Camp> campList = campDAO.findAll();
        if (!campList.isEmpty()) {
            camp = campList.get(0);
        }
        if (getApp().getSettings().getUserId() != null) {
            try {
                userDetails = userDetailsDAO.findFirstByUserId(getApp().getSettings().getUserId());
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }
        ageUnitAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, R.id.itemText, ageUnitArr);
        genderAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item, R.id.itemText, genderArr);
        stateAdapter = new StateAdapter(getActivity(), R.layout.list_item, R.id.itemText, stateDAO.findAll());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.frag_patient_reg, container, false);
        initViews(view, savedInstanceState);
        return view;
    }

    private void initViews(View view, Bundle savedInstanceState) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Patient Registration");

        lblRegNo = view.findViewById(R.id.lblRegNo);
        txtRegNo = view.findViewById(R.id.txtRegNo);
        if (userDetails != null) {
            txtRegNo.setText(getRegNo(userDetails.getPrefix()));
        }
        imgPatientPhoto = view.findViewById(R.id.imgPatientPhoto);
        imgPatientPhoto.setOnClickListener(this);

        txtFName = view.findViewById(R.id.txtFName);
        txtMName = view.findViewById(R.id.txtMName);
        txtLName = view.findViewById(R.id.txtLName);
        txtDOB = view.findViewById(R.id.txtDOB);
        txtDOB.setOnClickListener(this);
        txtAge = view.findViewById(R.id.txtAge);
        txtAge.setOnFocusChangeListener(this);
        txtHouseNoName = view.findViewById(R.id.txtHouseNoName);
        txtStreetName = view.findViewById(R.id.txtStreetName);
        txtLocality = view.findViewById(R.id.txtLocality);
        txtCity = view.findViewById(R.id.txtCity);
        txtTaluka = view.findViewById(R.id.txtTaluka);
        txtDistrict = view.findViewById(R.id.txtDistrict);
        txtPincode = view.findViewById(R.id.txtPincode);
        txtMobileNo = view.findViewById(R.id.txtMobileNo);
        txtResidenceNo = view.findViewById(R.id.txtResidenceNo);
        txtAadharNo = view.findViewById(R.id.txtAadharNo);
        txtEmailId = view.findViewById(R.id.txtEmailId);
        txtRefBy = view.findViewById(R.id.txtRefBy);

        mulSelSpnAssignTag = view.findViewById(R.id.mulSelSpnAssignTag);
        mulSelSpnAssignTag.setEnabled(true);
        mulSelSpnAssignTag.setTitle("Select Tags");

        spnGender = view.findViewById(R.id.spnGender);
        spnGender.setAdapter(genderAdapter);
        autoTxtState = view.findViewById(R.id.autoTxtState);
        autoTxtState.setAdapter(stateAdapter);
        autoTxtState.setOnFocusChangeListener(this);
        autoTxtState.addTextChangedListener(stateWatcher);
        autoTxtCountry = view.findViewById(R.id.autoTxtCountry);

        spnAgeUnit = view.findViewById(R.id.spnAgeUnit);
        spnAgeUnit.setAdapter(ageUnitAdapter);
        spnAgeUnit.setOnItemSelectedListener(spnAgeUnitListener);

        btnRegister = view.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        tagList = tagDAO.findAll("order by " + Tag.TAG_NAME);
        List<String> tagStringList = new ArrayList<String>();
        for (Tag tag : tagList) {
            tagStringList.add(tag.getTagName());
        }
        if (tagStringList.isEmpty()) {
            mulSelSpnAssignTag.setClickable(false);
        } else {
            mulSelSpnAssignTag.setItems(tagStringList);
            mulSelSpnAssignTag.setClickable(true);
        }

        askAccessStoragePermissionAndSavePdfFile();
        if (!isPdfViewerInstalled()) {
            showPdfViewerNotAvailableDialog();
        }
    }

    public boolean isPdfViewerInstalled() {
        PackageManager packageManager = getActivity().getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > AttributeSet.Constants.ZERO) {
            return true;
        }
        return false;
    }

    public void showPdfViewerNotAvailableDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setMessage(R.string.pdf_reader_not_installed);
        alertDialog.setPositiveButton(R.string.btn_lbl_install_now, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Uri uri = Uri.parse("market://details?id=" + "com.adobe.reader");
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException exception) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.adobe.reader")));
                }
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void askAccessStoragePermissionAndSavePdfFile() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showAccessStoragePermsDialog();
            } else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERM_REQ_WRITE_EXT_STORAGE);
            }
        }
    }

    public void showAccessStoragePermsDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        // Set dialog title
        alertDialog.setTitle(R.string.permission_diag_title);
        // Set dialog message
        alertDialog.setMessage(R.string.req_storage_access_perms_msg);
        alertDialog.setPositiveButton(R.string.lbl_ok, new DialogInterface.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERM_REQ_WRITE_EXT_STORAGE);
            }
        });
        AlertDialog alert = alertDialog.create();
//		alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR);
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERM_REQ_WRITE_EXT_STORAGE:
                // If request is cancelled, result arrays are empty
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted, so do the storage related task you need to do
                } else {
                    // Finish the activity if permission is denied.
                    getActivity().finish();
                }
                break;
        }
    }


    private AdapterView.OnItemSelectedListener spnAgeUnitListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            if (!isEmpty(txtAge.getText().toString())) {
                setDOB(String.valueOf(parent.getItemAtPosition(position)), Integer.parseInt(txtAge.getText().toString()));
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void setAge() {
        LocalDate today;
        today = LocalDate.now();
        Period period = Period.between(birthDate, today);
//		txtAge.setText(period.getYears() != 0 ? String.format(getString(R.string.lbl_birth_in_years), period.getYears()) : period.getMonths() != 0 ? String.format(getString(R.string.lbl_birth_in_months), period.getMonths()) : String.format(getString(R.string.lbl_birth_in_days), period.getDays()));
        txtAge.setText(period.getYears() != 0 ? String.valueOf(period.getYears()) : period.getMonths() != 0 ? String.valueOf(period.getMonths()) : ChronoUnit.WEEKS.between(birthDate, today) != 0 ? String.valueOf(ChronoUnit.WEEKS.between(birthDate, today)) : String.valueOf(period.getDays()));
        if (period.getYears() != 0) {
            spnAgeUnit.setSelection(0, true);
        } else if (period.getMonths() != 0) {
            spnAgeUnit.setSelection(1, true);
        } else if (ChronoUnit.WEEKS.between(birthDate, today) != 0) {
            spnAgeUnit.setSelection(2, true);
        } else if (period.getDays() != 0) {
            spnAgeUnit.setSelection(3, true);
        }
    }

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
        txtDOB.setText(sb.toString());
        txtDOB.setError(null);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.imgPatientPhoto) {
            selectImage();
        } else if (viewId == R.id.txtDOB) {
            showDatePickerDialog();
        } else if (viewId == R.id.btnRegister) {
            if (isValidationSuccess()) {
                registerPatient();
            }
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Pick From Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialg_title_add_photo);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogItemClick(dialog, which);
            }
        });
        builder.show();
    }

    public void dialogItemClick(DialogInterface dialog, int id) {
        switch (id) {
            case 0:
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (file.exists()) {
//                    imgUri = FileProvider.getUriForFile(getActivity(), AUTHORITY, file);
                    imgUri = Uri.fromFile(file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                    startActivityForResult(intent, REQUEST_CAMERA);
                }
                break;
            case 1:
                Intent intentPickImage = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intentPickImage.setType("image/*");
                intentPickImage.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intentPickImage, getString(R.string.img_gallery_title)), SELECT_FILE);
                break;
            case 2:
                dialog.dismiss();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                if (imgUri != null) {
                    try {
                        Bitmap profileImgBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imgUri);
                        imgPatientPhoto.setImageBitmap(profileImgBitmap);
                        profileImgPath = imgUri.getPath();
                        profileImgName = imgUri.getLastPathSegment();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    String[] projection = {MediaStore.MediaColumns.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(selectedImageUri, projection, null, null, null);
                    if (cursor.moveToFirst()) {
                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        String selectedImagePath = cursor.getString(column_index);
                        cursor.close();
                        if (!isEmpty(selectedImagePath)) {
                            Bitmap bm;
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inJustDecodeBounds = true;
                            BitmapFactory.decodeFile(selectedImagePath, options);
                            final int REQUIRED_SIZE = 200;
                            int scale = 1;
                            while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE) {
                                scale *= 2;
                            }
                            options.inSampleSize = scale;
                            options.inJustDecodeBounds = false;
                            bm = BitmapFactory.decodeFile(selectedImagePath, options);
                            imgPatientPhoto.setImageBitmap(bm);
                            profileImgPath = selectedImagePath;
                            String[] filePathArray;
                            filePathArray = selectedImagePath.split("/");
                            profileImgName = filePathArray[filePathArray.length - 1];
                        }
                    }
                }
            }
        }
    }

    public void registerPatient() {
        long id = 0;
        StringBuilder tagString = new StringBuilder();
        boolean foundOne = false;
        for (Integer index : mulSelSpnAssignTag.getSelectedIndices()) {
            if (foundOne) {
                tagString.append(", ");
            }
            foundOne = true;
            tagString.append(String.valueOf(tagList.get(index).getTagId()));
        }

        State state = null;
        try {
            state = stateDAO.findFirstByField(State.STATE_NAME, autoTxtState.getText().toString());
        } catch (DAOException e) {
            e.printStackTrace();
        }

        Patient patient = new Patient();
        if (userDetails != null) {
            patient.setUserId(userDetails.getUserId());
            patient.setRegNo(getRegNo(userDetails.getPrefix()));
        }
        if (camp != null) {
            patient.setCampId(camp.getCampId());
        }
        patient.setfName(txtFName.getText().toString());
        patient.setmName(txtMName.getText().toString());
        patient.setlName(txtLName.getText().toString());
        patient.setGender(spnGender.getSelectedItem().toString());

        SimpleDateFormat sdf = new SimpleDateFormat(AttributeSet.Constants.SHORT_DATE_FORMAT);
        SimpleDateFormat shortDF = new SimpleDateFormat(AttributeSet.Constants.REVERSE_SHORT_DATE);
        try {
            Date birthDate = sdf.parse(txtDOB.getText().toString());
            patient.setDob(shortDF.format(birthDate));
        } catch (ParseException e1) {
            e1.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        patient.setAge(Integer.parseInt(txtAge.getText().toString()));
        patient.setAgeUnit(spnAgeUnit.getSelectedItem().toString());
        patient.setHouseNameNo(txtHouseNoName.getText().toString());
        patient.setStreetName(txtStreetName.getText().toString());
        patient.setLocalityAreaPada(txtLocality.getText().toString());
        patient.setCityTownVillage(txtCity.getText().toString());
        patient.setTaluka(txtTaluka.getText().toString());
        patient.setDistrict(txtDistrict.getText().toString());
        patient.setStateId(state == null ? 0 : state.getStateId() == null ? 0 : state.getStateId());
        patient.setStateName(autoTxtState.getText().toString());
        patient.setPinCode(txtPincode.getText().toString());
        patient.setCountryName(autoTxtCountry.getText().toString());
        patient.setMobileNo(txtMobileNo.getText().toString());
        patient.setResidenceNo(txtResidenceNo.getText().toString());
        patient.setAadharNo(txtAadharNo.getText().toString());
        patient.setEmailId(txtEmailId.getText().toString());
        patient.setReferredBy(txtRefBy.getText().toString());
        patient.setTagName(mulSelSpnAssignTag.getSelectedItemsAsString());
        patient.setTagId(tagString.toString());
        if (!isEmpty(profileImgName)) {
            patient.setProfileImgPath(profileImgPath);
            patient.setProfileImgName(profileImgName);
            patient.setHasImg(true);
        }
        patient.setActive(true);
        patient.setLastVisitDate(getCurrentDateTime());
        patient.setDateCreated(getCurrentDateTime());
//        patient.setDateModified(CursorUtils.extractStringOrNull(c,Patient.DATE_MODIFIED));
        patient.setFresh(true);

        try {
            id = patientDAO.create(patient);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        if (id != 0 && id != -1) {
            gotoScreen(PatientRegPdfActivity.class, patient.getRegNo(), id);
            getActivity().finish();
        }
    }

    private String getRegNo(String prefix) {
        try {
            StringBuilder regNoBuilder = new StringBuilder();
            Long id = patientDAO.getMaxId();
            if (id == null) {
                regNoBuilder.append(prefix);
                regNoBuilder.append("1");
                return regNoBuilder.toString();
            } else {
                regNoBuilder.append(prefix);
                regNoBuilder.append(++id);
                return regNoBuilder.toString();
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return prefix;
    }

    private boolean isValidationSuccess() {
        if (isEmpty(txtFName.getText().toString())) {
            txtFName.setFocusableInTouchMode(true);
            txtFName.requestFocus();
            txtFName.setError(getSpanStringBuilder(getString(R.string.first_name_req_err_msg)));
        } else if (!txtFName.getText().toString().matches("[a-zA-Z. ]+")) {
            txtFName.setFocusableInTouchMode(true);
            txtFName.requestFocus();
            txtFName.setError(getSpanStringBuilder(getString(R.string.name_invalid_err_msg)));
        } else if (isEmpty(txtLName.getText().toString())) {
            txtLName.setFocusableInTouchMode(true);
            txtLName.requestFocus();
            txtLName.setError(getSpanStringBuilder(getString(R.string.last_name_req_err_msg)));
        } else if (!txtLName.getText().toString().matches("[a-zA-Z. ]+")) {
            txtLName.setFocusableInTouchMode(true);
            txtLName.requestFocus();
            txtLName.setError(getSpanStringBuilder(getString(R.string.name_invalid_err_msg)));
        } else if (spnGender.getSelectedItemPosition() == AttributeSet.Constants.ZERO) {
            shortToast(getString(R.string.select_gender_hint));
        } else if (isEmpty(txtDOB.getText().toString())) {
            shortToast(getString(R.string.dob_req_alert_msg));
        } else if (!isEmpty(txtEmailId.getText().toString()) && !EmailValidator.validate(txtEmailId.getText().toString())) {
            txtEmailId.setFocusableInTouchMode(true);
            txtEmailId.requestFocus();
            txtEmailId.setError(getSpanStringBuilder(getString(R.string.email_invalid_alert)));
        } else if (isEmpty(txtLocality.getText().toString())) {
            txtLocality.setFocusableInTouchMode(true);
            txtLocality.requestFocus();
            txtLocality.setError(getSpanStringBuilder(getString(R.string.locality_req_err_msg)));
        } else if (isEmpty(txtCity.getText().toString())) {
            txtCity.setFocusableInTouchMode(true);
            txtCity.requestFocus();
            txtCity.setError(getSpanStringBuilder(getString(R.string.city_town_village_req_err_msg)));
        } else if (isEmpty(txtTaluka.getText().toString())) {
            txtTaluka.setFocusableInTouchMode(true);
            txtTaluka.requestFocus();
            txtTaluka.setError(getSpanStringBuilder(getString(R.string.taluka_req_err_msg)));
        } else if (isEmpty(txtDistrict.getText().toString())) {
            txtDistrict.setFocusableInTouchMode(true);
            txtDistrict.requestFocus();
            txtDistrict.setError(getSpanStringBuilder(getString(R.string.district_req_err_msg)));
        } else if (isEmpty(autoTxtCountry.getText().toString())) {
            autoTxtCountry.setFocusableInTouchMode(true);
            autoTxtCountry.requestFocus();
            autoTxtCountry.setError(getSpanStringBuilder(getString(R.string.country_req_err_msg)));
        } else if (mulSelSpnAssignTag.getSelectedIndices().isEmpty()) {
            shortToast(getString(R.string.tags_req_err_msg));
        } else if (!isEmpty(txtAadharNo.getText().toString()) && txtAadharNo.getText().toString().length() != 12) {
            shortToast(getString(R.string.aadhar_invalid_alert));
        } else {
            return true;
        }
        return false;
    }

    public void gotoScreen(Class<?> cls, String regNo, Long id) {
        Bundle args = new Bundle();
        args.putString(REG_NO, regNo);
        args.putLong(P_ID, id);
        args.putSerializable(SKIP_PDF, false);
        Intent intent = new Intent(getActivity(), cls);
        intent.putExtras(args);
        startActivity(intent);
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
            birthDate = LocalDate.of(year, monthOfYear, dayOfMonth);

            StringBuilder sb;
            sb = new StringBuilder();
            sb.append(mformat.format(dayOfMonth));
            sb.append("-");
            sb.append(mformat.format(monthOfYear));
            sb.append("-");
            sb.append(year);
            txtDOB.setText(sb.toString());
            txtDOB.setError(null);
            setAge();
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
    public void onCallComplete(Object result, int type) {
        if (isAdded()) {
            if (type == GET_TAG_DETAIL) {
                closeProgDialog();
                Base base = (Base) result;
                if (base != null && base.getErrorCode() != null && base.getErrorCode().longValue() == AttributeSet.Constants.ZERO) {
                    tagList = base.getTagList();
                    List<String> tagStringList = new ArrayList<String>();
                    try {
                        tagDAO.deleteAll();
                        tagDAO.create(tagList);
                    } catch (DAOException e) {
                        e.printStackTrace();
                    }
                    for (Tag tag : tagList) {
                        tagStringList.add(tag.getTagName());
                    }
                    if (tagStringList.isEmpty()) {
                        mulSelSpnAssignTag.setClickable(false);
                    } else {
                        mulSelSpnAssignTag.setItems(tagStringList);
                        mulSelSpnAssignTag.setClickable(true);
                    }
                }
            }
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        Activity activity = getActivity();
        if (activity != null && !activity.isFinishing() && isAdded()) {
            int viewId = view.getId();
            if (viewId == R.id.txtAge) {
                if (!hasFocus) {
                    if (!isEmpty(txtAge.getText().toString())) {
                        setDOB(String.valueOf(spnAgeUnit.getSelectedItem()), Integer.parseInt(txtAge.getText().toString()));
                    }
                }
            } else if (viewId == R.id.autoTxtState) {
                if (hasFocus) {
                    autoTxtState.showDropDown();
                }
            }
        }
    }

    TextWatcher stateWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            if (autoTxtState.isPerformingCompletion()) {
                // An item has been selected from the list. Ignore.
                return;
            } else if (!isEmpty(String.valueOf(charSequence))) {
                // if you want to see in the logcat what the user types
                autoTxtState.setError(null);
                Log.d(TAG, "User input: " + charSequence);
                // query the database based on the user input
                try {
                    List<State> stateList = stateDAO.getSuggestionsFor(State.STATE_NAME, charSequence.toString());
                    stateAdapter.setStateData(stateList);
                    stateAdapter.notifyDataSetChanged();
                } catch (DAOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}
