package org.impactindia.llemeddocket.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.orm.PopulationMedicalModel;
import org.impactindia.llemeddocket.pojo.PopMedicalData;
import org.impactindia.llemeddocket.util.SharedPreference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import info.hoang8f.android.segmented.SegmentedGroup;

public class ViewDetailsActivity extends BaseActivity {

    SegmentedGroup sgPages;
    RadioButton rbMemberdetails, rbHealthdata, rbMedicalhistroy, rbCallTolle;
    LinearLayout llmemberdata, llhealthdata, llmedical, llcalltolle;

    TextView txt_householdno, txt_Membertype, txt_Title, txt_fmlname,
            txt_Gender, txt_dob, txt_Age, txt_Pincode, txt_mobileNo, txt_StreetName, txt_ResidNo, txt_AdharNo, txt_VoterId, txt_Email, txt_Refeby;

    TextView edt_ft, edt_inches, edt_weight, edt_bmi, edt_pulse, edt_o2saturation, edt_bloodsugar, bloodsugar_interpretation, edtSys, bloodpressure_interpretation, txtReyeNear,
            txtReyeDistance, txt_reyeinterpre, txt_LeyeNear, txt_LeyeDistance, txt_leyeinterpre, edtDias;

    String viewid, gender;

    TextView isdiabetes, ishypertension, iscataract, dof_cataract_d, cataractinfo, isepf, dof_epf_d, epfinfo, isoralprblm, dof_oralprblm_d,
            oralprblminfo, istb, dof_tb_d, tbinfo, isschemic, dof_schemic_d, ischemicinfo, isstroke, dof_stroke_d, strokinfo, isearproblem,
            iscancer, dof_cancer_d, cancerinfo, congential_ab, ishearingdefect, dof_hearingdefect_d, hearinginfo, isgynac, dof_gynac_d, gaynacinfo,
            istobacco, issmoke, isalcohol, isearproblemdetails, txt_cancer_opd;

    TextView txt_visualopd, txt_cataractopd, txt_epfopd, txt_oralopd, txt_congab_opd, txt_hearing_opd, txt_gynaec_opd, txt_earprblm_opd;
    ArrayList<PopMedicalData> getdataforupdate = new ArrayList<PopMedicalData>();

    TableRow GynaecDetTex, GynacDiaTex, EarProbDetTex, CancerDetTex, cancerDiaTex, StrokeDetTex, StrokeDiaTex, ischemicDetTxt, gynaecrow,
            IschemicDiaTex, TBDetTex, TBDiagTex, oralDetTex, oralDiaTex, epilepsyDetTex, EpilespyDiaText, cataract_detailsTxt, cataract_DiaText;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

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
        init();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("View Details");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            viewid = bundle.getString("EDITID");
            gender = bundle.getString("GENDER");
            Log.i("ckm=>ID", viewid);
            getdataforupdate = PopulationMedicalModel.getdataforupdate(viewid);
            Log.i("ckm=>sizeofarray", getdataforupdate.size() + "");
            Log.i("ckm=>sizeofarray1", getdataforupdate.get(0).getOrthopedicsurgery() + "");
            setmemberdata();
            sethealthdata();
            setMedicaldata();
            setCalltoll();

        }
    }

    public void setmemberdata() {
        txt_householdno.setText(getdataforupdate.get(0).getHouseholdno());
        txt_Membertype.setText(getdataforupdate.get(0).getRelation());
        // txt_Title.setText(getdataforupdate.get(0).getTitle());
        txt_fmlname.setText(getdataforupdate.get(0).getTitle() + " " + getdataforupdate.get(0).getFname() + " " + getdataforupdate.get(0).getMname() + " " + getdataforupdate.get(0).getLname());
        txt_Gender.setText(getdataforupdate.get(0).getGender());
        txt_dob.setText(getddmmyyy(getdataforupdate.get(0).getDob()));
        txt_Age.setText(String.valueOf(getdataforupdate.get(0).getAge()) + " " + "Years");
        txt_Pincode.setText(String.valueOf(getdataforupdate.get(0).getPincode()));
        txt_mobileNo.setText(getdataforupdate.get(0).getMobileno());
        txt_StreetName.setText(getdataforupdate.get(0).getStreetname());
        txt_ResidNo.setText(getdataforupdate.get(0).getResidenceno());
        txt_AdharNo.setText(getdataforupdate.get(0).getAadharno());
        txt_VoterId.setText(getdataforupdate.get(0).getVoteridno());
        txt_Email.setText(getdataforupdate.get(0).getEmailid());
        txt_Refeby.setText(getdataforupdate.get(0).getReferredby());

    }

    public void sethealthdata() {
        edt_ft.setText(String.valueOf(getdataforupdate.get(0).getHeightinfit()));
        edt_inches.setText(String.valueOf(getdataforupdate.get(0).getHeightininches()));
        edt_weight.setText(String.valueOf(getdataforupdate.get(0).getWeight()));
        edt_bmi.setText(getdataforupdate.get(0).getBmi() + " , " + getdataforupdate.get(0).getBmiinter());
        edt_pulse.setText(String.valueOf(getdataforupdate.get(0).getPulse()));
        edt_o2saturation.setText(String.valueOf(getdataforupdate.get(0).getOxysaturation()));
        edt_bloodsugar.setText(String.valueOf(getdataforupdate.get(0).getBloodsugar()));
        bloodsugar_interpretation.setText(getdataforupdate.get(0).getBloodsugarinter());
        edtSys.setText(String.valueOf(getdataforupdate.get(0).getBpsystolic()));
        bloodpressure_interpretation.setText(getdataforupdate.get(0).getBpinter());
        txtReyeNear.setText(getdataforupdate.get(0).getVisualacuityrighteyenear());
        txtReyeDistance.setText(getdataforupdate.get(0).getVisualacuityrighteyedistant());
        txt_reyeinterpre.setText(getdataforupdate.get(0).getVisualacuityrighteyeinter());
        txt_LeyeNear.setText(getdataforupdate.get(0).getVisualacuitylefteyenear());
        txt_LeyeDistance.setText(getdataforupdate.get(0).getVisualacuitylefteyedistant());
        txt_leyeinterpre.setText(getdataforupdate.get(0).getVisualacuitylefteyeinter());
        edtDias.setText(String.valueOf(getdataforupdate.get(0).getBpdiastolic()));

    }

    public void setCalltoll() {

        if (!isEmpty(getdataforupdate.get(0).getEyeopd())) {
            txt_visualopd.setText(getddmmyyy(getdataforupdate.get(0).getEyeopd()));
        }
        if (!isEmpty(getdataforupdate.get(0).getEyesurgery())) {
            txt_cataractopd.setText(getddmmyyy(getdataforupdate.get(0).getEyesurgery()));
        }
        if (!isEmpty(getdataforupdate.get(0).getEpilepsyopd())) {
            txt_epfopd.setText(getddmmyyy(getdataforupdate.get(0).getEpilepsyopd()));
        }
        if (!isEmpty(getdataforupdate.get(0).getDentalopd())) {
            txt_oralopd.setText(getddmmyyy(getdataforupdate.get(0).getDentalopd()));
        }
        if (!isEmpty(getdataforupdate.get(0).getEntopd())) {
            txt_earprblm_opd.setText(getddmmyyy(getdataforupdate.get(0).getEntopd()));
        }
        if (!isEmpty(getdataforupdate.get(0).getGynaecopd())) {
            txt_gynaec_opd.setText(getddmmyyy(getdataforupdate.get(0).getGynaecopd()));
        }

        if (!isEmpty(getdataforupdate.get(0).getPlasticsurgeryopd())) {
            Log.i("ckm=>ortho1", getdataforupdate.get(0).getPlasticsurgeryopd());
            txt_congab_opd.setText(getddmmyyy(getdataforupdate.get(0).getPlasticsurgeryopd()));
        }
        if (!isEmpty(getdataforupdate.get(0).getOrthopedicsurgery())) {
            Log.i("ckm=>ortho2", getdataforupdate.get(0).getOrthopedicsurgery());
            txt_congab_opd.setText(getddmmyyy(getdataforupdate.get(0).getOrthopedicsurgery()));
        }

        if (!isEmpty(getdataforupdate.get(0).getOralcanceropd())) {
            txt_cancer_opd.setText(getddmmyyy(getdataforupdate.get(0).getOralcanceropd()));
        }

    }

    public void setMedicaldata() {
        isdiabetes.setText(getdataforupdate.get(0).getDiabetes());
        ishypertension.setText(getdataforupdate.get(0).getHypertension());
        iscataract.setText(getdataforupdate.get(0).getCataract());
        if (!isEmpty(getdataforupdate.get(0).getCartaractdiagno())) {
            dof_cataract_d.setText(getddmmyyy(getdataforupdate.get(0).getCartaractdiagno()));
        } else {
            cataract_DiaText.setVisibility(View.GONE);
        }

        if (!isEmpty(getdataforupdate.get(0).getCataratdetails())) {
            cataractinfo.setText(getdataforupdate.get(0).getCataratdetails());
        } else {
            cataract_detailsTxt.setVisibility(View.GONE);
        }

        isepf.setText(getdataforupdate.get(0).getEpilepsy());

        if (!isEmpty(getdataforupdate.get(0).getEpilepsydiagnosed())) {
            dof_epf_d.setText(getddmmyyy(getdataforupdate.get(0).getEpilepsydiagnosed()));
        } else {
            EpilespyDiaText.setVisibility(View.GONE);
        }

        if (!isEmpty(getdataforupdate.get(0).getEpilepsydetails())) {
            epfinfo.setText(getdataforupdate.get(0).getEpilepsydetails());
        } else {
            epilepsyDetTex.setVisibility(View.GONE);
        }

        isoralprblm.setText(getdataforupdate.get(0).getOralproblem());

        if (!isEmpty(getdataforupdate.get(0).getOralproblemdiagnoesd())) {
            dof_oralprblm_d.setText(getddmmyyy(getdataforupdate.get(0).getOralproblemdiagnoesd()));
        } else {
            oralDiaTex.setVisibility(View.GONE);
        }

        if (!isEmpty(getdataforupdate.get(0).getOralprodetails())) {
            oralprblminfo.setText(getdataforupdate.get(0).getOralprodetails());
        } else {
            oralDetTex.setVisibility(View.GONE);
        }

        istb.setText(getdataforupdate.get(0).getTb());

        if (!isEmpty(getdataforupdate.get(0).getTbdiagnosed())) {
            dof_tb_d.setText(getddmmyyy(getdataforupdate.get(0).getTbdiagnosed()));
        } else {
            TBDiagTex.setVisibility(View.GONE);
        }

        if (!isEmpty(getdataforupdate.get(0).getTbdetails())) {
            tbinfo.setText(getdataforupdate.get(0).getTbdetails());
        } else {
            TBDetTex.setVisibility(View.GONE);
        }

        isschemic.setText(getdataforupdate.get(0).getIschemicheartdisease());

        if (!isEmpty(getdataforupdate.get(0).getIschemicheadisddiagnosed())) {
            dof_schemic_d.setText(getddmmyyy(getdataforupdate.get(0).getIschemicheadisddiagnosed()));
        } else {
            IschemicDiaTex.setVisibility(View.GONE);
        }

        if (!isEmpty(getdataforupdate.get(0).getIschemicheadisdetails())) {
            ischemicinfo.setText(getdataforupdate.get(0).getIschemicheadisdetails());
        } else {
            ischemicDetTxt.setVisibility(View.GONE);
        }


        isstroke.setText(getdataforupdate.get(0).getStroke());

        if (!isEmpty(getdataforupdate.get(0).getStrokediagnosed())) {
            dof_stroke_d.setText(getddmmyyy(getdataforupdate.get(0).getStrokediagnosed()));
        } else {
            StrokeDiaTex.setVisibility(View.GONE);
        }

        if (!isEmpty(getdataforupdate.get(0).getStrokedetails())) {
            strokinfo.setText(getdataforupdate.get(0).getStrokedetails());
        } else {
            StrokeDetTex.setVisibility(View.GONE);
        }

        // isearproblem.setText(getdataforupdate.get(0).getEarprob());


        iscancer.setText(getdataforupdate.get(0).getCancer());

        if (!isEmpty(getdataforupdate.get(0).getCancerdiagnosed())) {
            dof_cancer_d.setText(getddmmyyy(getdataforupdate.get(0).getCancerdiagnosed()));
        } else {
            cancerDiaTex.setVisibility(View.GONE);
        }

        if (!isEmpty(getdataforupdate.get(0).getCancerdetails())) {
            cancerinfo.setText(getdataforupdate.get(0).getCancerdetails());
        } else {
            CancerDetTex.setVisibility(View.GONE);
        }

        congential_ab.setText(getdataforupdate.get(0).getCogenitalabnormalities());
        ishearingdefect.setText(getdataforupdate.get(0).getEarprob());
        if (!isEmpty(getdataforupdate.get(0).getEarprobdetails())) {
            isearproblemdetails.setText(getdataforupdate.get(0).getEarprobdetails());
        } else {
            EarProbDetTex.setVisibility(View.GONE);
        }

        if (gender.equals("Female")) {
            //gynaecrow.setVisibility(View.VISIBLE);
            isgynac.setText(getdataforupdate.get(0).getGynaecprob());
            if (!isEmpty(getdataforupdate.get(0).getGynaecdiagnosed())) {
                dof_gynac_d.setText(getddmmyyy(getdataforupdate.get(0).getGynaecdiagnosed()));
            } else {
                GynacDiaTex.setVisibility(View.GONE);
            }

            if (!isEmpty(getdataforupdate.get(0).getGynaecdetails())) {
                gaynacinfo.setText(getdataforupdate.get(0).getGynaecdetails());
            } else {
                GynaecDetTex.setVisibility(View.GONE);
            }
        } else {
            gynaecrow.setVisibility(View.GONE);
            isgynac.setVisibility(View.GONE);
            dof_gynac_d.setVisibility(View.GONE);
            GynacDiaTex.setVisibility(View.GONE);
            gaynacinfo.setVisibility(View.GONE);
            GynaecDetTex.setVisibility(View.GONE);
            Log.i("ckm=>gendermale", "Male");
        }


        istobacco.setText(getdataforupdate.get(0).getTobacco());
        issmoke.setText(getdataforupdate.get(0).getStroke());
        isalcohol.setText(getdataforupdate.get(0).getAlcohol());

    }

    private void init() {
        //Main layout
        llmemberdata = findViewById(R.id.llmemberdata);
        llhealthdata = findViewById(R.id.llhealthdata);
        llmedical = findViewById(R.id.llmedical);
        llcalltolle = findViewById(R.id.llcalltolle);
        isearproblemdetails = findViewById(R.id.isearproblemdetails);

        //Child View
        sgPages = findViewById(R.id.sgPages);
        rbMemberdetails = findViewById(R.id.rbMemberdetails);
        rbHealthdata = findViewById(R.id.rbHealthdata);
        rbMedicalhistroy = findViewById(R.id.rbMedicalhistroy);
        rbCallTolle = findViewById(R.id.rbCallTolle);

        sgPages.setOnCheckedChangeListener(allpaagesdata);
        sgPages.check(R.id.rbMemberdetails);

        //Member detailsId
        //,,,,,,,,,,,,,,txt_Refeby
        txt_householdno = findViewById(R.id.txt_householdno);
        txt_Membertype = findViewById(R.id.txt_Membertype);
        txt_Title = findViewById(R.id.txt_Title);
        txt_fmlname = findViewById(R.id.txt_fmlname);
        txt_Gender = findViewById(R.id.txt_Gender);
        txt_dob = findViewById(R.id.txt_dob);
        txt_Age = findViewById(R.id.txt_Age);
        txt_Pincode = findViewById(R.id.txt_Pincode);
        txt_mobileNo = findViewById(R.id.txt_mobileNo);
        txt_StreetName = findViewById(R.id.txt_StreetName);
        txt_ResidNo = findViewById(R.id.txt_ResidNo);
        txt_AdharNo = findViewById(R.id.txt_AdharNo);
        txt_VoterId = findViewById(R.id.txt_VoterId);
        txt_Email = findViewById(R.id.txt_Email);
        txt_Refeby = findViewById(R.id.txt_Refeby);

        //Health Data

        edt_ft = findViewById(R.id.edt_ft);
        edt_inches = findViewById(R.id.edt_inches);
        edt_weight = findViewById(R.id.edt_weight);
        edt_bmi = findViewById(R.id.edt_bmi);
        edt_pulse = findViewById(R.id.edt_pulse);
        edt_o2saturation = findViewById(R.id.edt_o2saturation);
        edt_bloodsugar = findViewById(R.id.edt_bloodsugar);
        bloodsugar_interpretation = findViewById(R.id.bloodsugar_interpretation);
        edtSys = findViewById(R.id.edtSys);
        bloodpressure_interpretation = findViewById(R.id.bloodpressure_interpretation);
        txtReyeNear = findViewById(R.id.txtReyeNear);
        txtReyeDistance = findViewById(R.id.txtReyeDistance);
        txt_reyeinterpre = findViewById(R.id.txt_reyeinterpre);
        txt_LeyeNear = findViewById(R.id.txt_LeyeNear);
        txt_LeyeDistance = findViewById(R.id.txt_LeyeDistance);
        txt_leyeinterpre = findViewById(R.id.txt_leyeinterpre);
        edtDias = findViewById(R.id.edtDias);

        //MedicalData

        gynaecrow = findViewById(R.id.gynaecrow);
        isdiabetes = findViewById(R.id.isdiabetes);
        ishypertension = findViewById(R.id.ishypertension);
        iscataract = findViewById(R.id.iscataract);
        dof_cataract_d = findViewById(R.id.dof_cataract_d);
        cataractinfo = findViewById(R.id.cataractinfo);
        isepf = findViewById(R.id.isepf);
        dof_epf_d = findViewById(R.id.dof_epf_d);
        epfinfo = findViewById(R.id.epfinfo);
        isoralprblm = findViewById(R.id.isoralprblm);
        dof_oralprblm_d = findViewById(R.id.dof_oralprblm_d);

        oralprblminfo = findViewById(R.id.oralprblminfo);
        istb = findViewById(R.id.istb);
        dof_tb_d = findViewById(R.id.dof_tb_d);
        tbinfo = findViewById(R.id.tbinfo);
        isschemic = findViewById(R.id.isschemic);
        dof_schemic_d = findViewById(R.id.dof_schemic_d);
        ischemicinfo = findViewById(R.id.ischemicinfo);
        isstroke = findViewById(R.id.isstroke);
        dof_stroke_d = findViewById(R.id.dof_stroke_d);
        strokinfo = findViewById(R.id.strokinfo);


        GynaecDetTex = findViewById(R.id.GynaecDetTex);
        GynacDiaTex = findViewById(R.id.GynacDiaTex);
        EarProbDetTex = findViewById(R.id.EarProbDetTex);
        CancerDetTex = findViewById(R.id.CancerDetTex);
        cancerDiaTex = findViewById(R.id.cancerDiaTex);
        StrokeDetTex = findViewById(R.id.StrokeDetTex);
        StrokeDiaTex = findViewById(R.id.StrokeDiaTex);
        ischemicDetTxt = findViewById(R.id.ischemicDetTxt);
        IschemicDiaTex = findViewById(R.id.IschemicDiaTex);
        TBDetTex = findViewById(R.id.TBDetTex);
        TBDiagTex = findViewById(R.id.TBDiagTex);
        oralDetTex = findViewById(R.id.oralDetTex);
        oralDiaTex = findViewById(R.id.oralDiaTex);
        epilepsyDetTex = findViewById(R.id.epilepsyDetTex);
        EpilespyDiaText = findViewById(R.id.EpilespyDiaText);
        cataract_detailsTxt = findViewById(R.id.cataract_detailsTxt);
        cataract_DiaText = findViewById(R.id.cataract_DiaText);


        // isearproblem = findViewById(R.id.isearproblem);
        iscancer = findViewById(R.id.iscancer);
        dof_cancer_d = findViewById(R.id.dof_cancer_d);
        cancerinfo = findViewById(R.id.cancerinfo);
        congential_ab = findViewById(R.id.congential_ab);
        ishearingdefect = findViewById(R.id.ishearingdefect);
        //hearinginfo = findViewById(R.id.hearinginfo);
        //dof_hearingdefect_d = findViewById(R.id.dof_hearingdefect_d);
        isgynac = findViewById(R.id.isgynac);
        dof_gynac_d = findViewById(R.id.dof_gynac_d);
        gaynacinfo = findViewById(R.id.gaynacinfo);
        istobacco = findViewById(R.id.istobacco);
        issmoke = findViewById(R.id.issmoke);
        isalcohol = findViewById(R.id.isalcohol);


        //Call to lle
        txt_visualopd = findViewById(R.id.txt_visualopd);
        txt_cataractopd = findViewById(R.id.txt_cataractopd);
        txt_epfopd = findViewById(R.id.txt_epfopd);
        txt_oralopd = findViewById(R.id.txt_oralopd);
        txt_congab_opd = findViewById(R.id.txt_congab_opd);
        txt_hearing_opd = findViewById(R.id.txt_hearing_opd);
        txt_gynaec_opd = findViewById(R.id.txt_gynaec_opd);
        txt_earprblm_opd = findViewById(R.id.txt_earprblm_opd);
        txt_cancer_opd = findViewById(R.id.txt_cancer_opd);

    }

    RadioGroup.OnCheckedChangeListener allpaagesdata = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.rbMemberdetails) {
                llmemberdata.setVisibility(View.VISIBLE);
                llhealthdata.setVisibility(View.GONE);
                llmedical.setVisibility(View.GONE);
                llcalltolle.setVisibility(View.GONE);

                //setmemberdata();
            } else if (checkedId == R.id.rbHealthdata) {
                llmemberdata.setVisibility(View.GONE);
                llhealthdata.setVisibility(View.VISIBLE);
                llmedical.setVisibility(View.GONE);
                llcalltolle.setVisibility(View.GONE);
                // sethealthdata();
            } else if (checkedId == R.id.rbMedicalhistroy) {
                llmemberdata.setVisibility(View.GONE);
                llhealthdata.setVisibility(View.GONE);
                llmedical.setVisibility(View.VISIBLE);
                llcalltolle.setVisibility(View.GONE);
                // setMedicaldata();
            } else if (checkedId == R.id.rbCallTolle) {
                llmemberdata.setVisibility(View.GONE);
                llhealthdata.setVisibility(View.GONE);
                llmedical.setVisibility(View.GONE);
                llcalltolle.setVisibility(View.VISIBLE);
                //title = "Mr";
            }
        }
    };


    public String getddmmyyy(String yyyymmdd) {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, EditDataActivity.class);
        startActivity(i);
    }
}