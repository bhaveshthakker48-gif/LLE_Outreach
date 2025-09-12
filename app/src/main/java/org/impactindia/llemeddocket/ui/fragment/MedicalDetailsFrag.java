package org.impactindia.llemeddocket.ui.fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.impactindia.llemeddocket.AttributeSet;
import org.impactindia.llemeddocket.DatabaseHelper;
import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.orm.CampPatientDAO;
import org.impactindia.llemeddocket.orm.DAOException;
import org.impactindia.llemeddocket.orm.MedicalDetailsDAO;
import org.impactindia.llemeddocket.pojo.MedicalDetails;
import org.impactindia.llemeddocket.pojo.Patient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MedicalDetailsFrag extends BaseFrag implements View.OnClickListener {

    private static final String MEDICAL_DETAILS_ADD_FRAG = "MedicalDetailsAddFrag";

    private Toolbar toolbar;
    private TextView lblPatientName, lblPatientAgeGender;
    private EditText txtDOE, txtDiagnosis, txtVisitType, txtWeight, txtBP, txtHemoglobin, txtBldGlucose, txtHiv, txtHbsag, txtFinalTags;
    private Button btnOk;

    private Bundle args;
    private SQLiteDatabase db;
    private Long patientId;
    private Patient patient;
    private CampPatientDAO campPatientDAO;
    private MedicalDetailsDAO medicalDetailsDAO;
    private MedicalDetails medicalDetails;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = getArguments();
        patientId = args.getLong(MedicalDetailsAddFrag.PATIENT_ID);
        db = new DatabaseHelper(getActivity()).getWritableDatabase();
        campPatientDAO = new CampPatientDAO(getActivity(), db);
        medicalDetailsDAO = new MedicalDetailsDAO(getActivity(), db);
        if (patientId != null) {
            try {
                patient = campPatientDAO.findFirstByField(Patient.PATIENT_ID, patientId);
                medicalDetails = medicalDetailsDAO.findFirstByField(MedicalDetails.PATIENT_ID, patientId);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.frag_medical_details, container, false);
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
        txtDiagnosis = view.findViewById(R.id.txtDiagnosis);
        txtVisitType = view.findViewById(R.id.txtVisitType);
        txtWeight = view.findViewById(R.id.txtWeight);
        txtBP = view.findViewById(R.id.txtBP);
        txtHemoglobin = view.findViewById(R.id.txtHemoglobin);
        txtBldGlucose = view.findViewById(R.id.txtBldGlucose);
        txtHiv = view.findViewById(R.id.txtHiv);
        txtHbsag = view.findViewById(R.id.txtHbsag);
        txtFinalTags = view.findViewById(R.id.txtFinalTags);
        btnOk = view.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);

        if (medicalDetails != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(AttributeSet.Constants.SHORT_DATE_FORMAT);
            SimpleDateFormat reversSDF = new SimpleDateFormat(AttributeSet.Constants.REVERSE_SHORT_DATE);
            try {
                Date examDate = reversSDF.parse(medicalDetails.getDateOfExamination());
                txtDOE.setText(sdf.format(examDate));
            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            txtDiagnosis.setText(medicalDetails.getDiagnosis());
            if (medicalDetails.isOpd() || medicalDetails.isSurgery()) {
                StringBuilder sb = new StringBuilder();
                sb.append(medicalDetails.isOpd() ? "OPD" : "");
                if (!sb.toString().isEmpty() && medicalDetails.isSurgery()) {
                    sb.append("/");
                }
                sb.append(medicalDetails.isSurgery() ? "Surgery" : "");
                txtVisitType.setText(sb.toString());
            }
            txtWeight.setText(isEmpty(medicalDetails.getWeight()) ? null : medicalDetails.getWeight() + " " + getString(R.string.lbl_units_kgs));
            txtBP.setText(isEmpty(medicalDetails.getBpSystolic()) ? null : new StringBuilder().append(medicalDetails.getBpSystolic()).append(" / ").append(medicalDetails.getBpDiastolic()).append(" ").append(getString(R.string.lbl_units_mm_of_hg)));
            txtHemoglobin.setText(isEmpty(medicalDetails.getHemoglobin()) ? null : medicalDetails.getHemoglobin() + " " + getString(R.string.lbl_units_g_dl));
            txtBldGlucose.setText(isEmpty(medicalDetails.getBldGlucoseRandom()) ? null : medicalDetails.getBldGlucoseRandom() + " " + getString(R.string.lbl_units_mg_dl));
            txtHiv.setText(medicalDetails.getHiv());
            txtHbsag.setText(medicalDetails.getHbsag());
            txtFinalTags.setText(medicalDetails.getSubTagName().replace("~", ", "));

        }
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.btnOk) {
            getActivity().getSupportFragmentManager().popBackStackImmediate();
        }
    }

    public void gotoAddMedicalDetails() {
        FragmentManager mgr = getActivity().getSupportFragmentManager();
        MedicalDetailsAddFrag medicalDetailsAddFrag = (MedicalDetailsAddFrag) mgr.findFragmentByTag(MEDICAL_DETAILS_ADD_FRAG);
        if (medicalDetailsAddFrag == null) {
            medicalDetailsAddFrag = new MedicalDetailsAddFrag();
        }
        medicalDetailsAddFrag.setArguments(args);
        FragmentTransaction transaction = mgr.beginTransaction();
        transaction.replace(R.id.content_frame, medicalDetailsAddFrag, MEDICAL_DETAILS_ADD_FRAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
