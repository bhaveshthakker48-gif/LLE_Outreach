package org.impactindia.llemeddocket.pojo;

import com.google.gson.annotations.SerializedName;

public class MedicalDetails implements WsModel, Model {

    private final static String ERROR_CODE = "ErrorCode";
    private final static String ERROR_MSG = "ErrorMessage";

    public final static String CAMP_ID = "CampId";
    public final static String USER_ID = "UserID";
    public final static String PATIENT_ID = "PatientID";
    public final static String BP_DIASTOLIC = "BPDiastolic";
    public final static String BP_SYSTOLIC = "BPSystolic";
    public final static String BP_INTER = "BPInter";
    public final static String BLD_GLUCOSE_RANDOM = "BloodGlucoseRandom";
    public final static String BLD_GLUCOSE_RANDOM_INTER = "BloodGlucoseRandomInter";
    public final static String WEIGHT = "Weight";
    public final static String DATE_OF_EXAMINATION = "DateOfExamination";
    public final static String MED_DET_ID = "MeddetId";
    public final static String HBSAG = "HBsAg";
    public final static String HIV = "HIV";
    public final static String HEMOGLOBIN = "Hemoglobin";
    public final static String HEMOGLOBIN_INTER = "HemoglobinInter";
    public final static String DIAGNOSIS = "Diagnosis";
    public final static String OPD = "Opd";
    public final static String SURGERY = "Surgery";
    public final static String SUB_TAG_NAME = "SubTagName";
    public final static String SUB_TAG_ID = "SubTagId";
    public final static String TAG_ID = "TagId";

    @SerializedName(MED_DET_ID)
    private Long id;

    @SerializedName(ERROR_CODE)
    private Long errorCode;

    @SerializedName(ERROR_MSG)
    private String errorMsg;

    @SerializedName(CAMP_ID)
    private Long campId;

    @SerializedName(USER_ID)
    private Long userId; // Id of the Patient Logged In

    @SerializedName(PATIENT_ID)
    private Long patientId; // Patient's Id

    @SerializedName(BP_DIASTOLIC)
    private String bpDiastolic;

    @SerializedName(BP_SYSTOLIC)
    private String bpSystolic;

    @SerializedName(BP_INTER)
    private String bpInterPretation;

    @SerializedName(BLD_GLUCOSE_RANDOM)
    private String bldGlucoseRandom;

    @SerializedName(BLD_GLUCOSE_RANDOM_INTER)
    private String bldGlucoseRandomInterPretation;

    @SerializedName(WEIGHT)
    private String weight;

    @SerializedName(DATE_OF_EXAMINATION)
    private String dateOfExamination;

    @SerializedName(HBSAG)
    private String hbsag;

    @SerializedName(HIV)
    private String hiv;

    @SerializedName(HEMOGLOBIN)
    private String hemoglobin;

    @SerializedName(HEMOGLOBIN_INTER)
    private String hemoglobinInter;

    @SerializedName(DIAGNOSIS)
    private String diagnosis;

    @SerializedName(OPD)
    private boolean opd;

    @SerializedName(SURGERY)
    private boolean surgery;

    @SerializedName(SUB_TAG_NAME)
    private String subTagName;

    @SerializedName(SUB_TAG_ID)
    private String subTagId;

    @SerializedName(TAG_ID)
    private String tagId;

    private boolean fresh;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Long errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Long getCampId() {
        return campId;
    }

    public void setCampId(Long campId) {
        this.campId = campId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getBpDiastolic() {
        return bpDiastolic;
    }

    public void setBpDiastolic(String bpDiastolic) {
        this.bpDiastolic = bpDiastolic;
    }

    public String getBpSystolic() {
        return bpSystolic;
    }

    public void setBpSystolic(String bpSystolic) {
        this.bpSystolic = bpSystolic;
    }

    public String getBpInterPretation() {
        return bpInterPretation;
    }

    public void setBpInterPretation(String bpInterPretation) {
        this.bpInterPretation = bpInterPretation;
    }

    public String getBldGlucoseRandom() {
        return bldGlucoseRandom;
    }

    public void setBldGlucoseRandom(String bldGlucoseRandom) {
        this.bldGlucoseRandom = bldGlucoseRandom;
    }

    public String getBldGlucoseRandomInterPretation() {
        return bldGlucoseRandomInterPretation;
    }

    public void setBldGlucoseRandomInterPretation(String bldGlucoseRandomInterPretation) {
        this.bldGlucoseRandomInterPretation = bldGlucoseRandomInterPretation;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDateOfExamination() {
        return dateOfExamination;
    }

    public void setDateOfExamination(String dateOfExamination) {
        this.dateOfExamination = dateOfExamination;
    }

    public String getHbsag() {
        return hbsag;
    }

    public void setHbsag(String hbsag) {
        this.hbsag = hbsag;
    }

    public String getHiv() {
        return hiv;
    }

    public void setHiv(String hiv) {
        this.hiv = hiv;
    }

    public String getHemoglobin() {
        return hemoglobin;
    }

    public void setHemoglobin(String hemoglobin) {
        this.hemoglobin = hemoglobin;
    }

    public String getHemoglobinInter() {
        return hemoglobinInter;
    }

    public void setHemoglobinInter(String hemoglobinInter) {
        this.hemoglobinInter = hemoglobinInter;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public boolean isOpd() {
        return opd;
    }

    public void setOpd(boolean opd) {
        this.opd = opd;
    }

    public boolean isSurgery() {
        return surgery;
    }

    public void setSurgery(boolean surgery) {
        this.surgery = surgery;
    }

    public String getSubTagName() {
        return subTagName;
    }

    public void setSubTagName(String subTagName) {
        this.subTagName = subTagName;
    }

    public String getSubTagId() {
        return subTagId;
    }

    public void setSubTagId(String subTagId) {
        this.subTagId = subTagId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public boolean isFresh() {
        return fresh;
    }

    public void setFresh(boolean fresh) {
        this.fresh = fresh;
    }
}
