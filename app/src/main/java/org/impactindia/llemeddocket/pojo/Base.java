package org.impactindia.llemeddocket.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Base implements WsModel {

    private final static String ERROR_CODE = "ErrorCode";
    private final static String ERROR_MSG = "ErrorMessage";
    private final static String CAMP_DATA = "CampData";
    private final static String TAG_LIST = "TList";
    private final static String SUB_TAG_LIST = "SubTagMasterList";
    private final static String STATE_DATA = "StateData";
    private final static String LOG_IN_DATA = "LoginData";
    private final static String PATIENT_LIST = "PatientLst";
    private final static String PATIENT_DATA_LIST = "PatientdataList";
    private final static String PR_LIST = "PRList";
    private final static String BLOOD_PRESSURE_LIST = "BloodpressList";
    private final static String PATIENT_MEDICAL = "PatientMedical";
    private final static String CREATED_DATE = "CreatedDate";
    private final static String MODIFIED_DATE = "ModifiedDate";
    private final static String PATIENT_MEDICAL_DATA_RESPONSE = "PatientMedidataList";
    protected final static String DATA = "Data";

    @SerializedName(ERROR_CODE)
    private Long errorCode;

    @SerializedName(ERROR_MSG)
    private String errorMsg;

    @SerializedName(LOG_IN_DATA)
    private List<UserDetails> logInData;

    @SerializedName(CAMP_DATA)
    private List<Camp> campList;

    @SerializedName(TAG_LIST)
    private List<Tag> tagList;

    @SerializedName(SUB_TAG_LIST)
    private List<SubTag> subTagList;

    @SerializedName(STATE_DATA)
    private List<State> stateList;

    @SerializedName(PATIENT_LIST)
    private List<Patient> patientList;

    @SerializedName(PATIENT_DATA_LIST)
    private List<Patient> patientDataList;

    @SerializedName(PR_LIST)
    private List<Patient> prList;

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @SerializedName(BLOOD_PRESSURE_LIST)
    private List<BloodPressureNRData> bloodPressureList;

    @SerializedName(PATIENT_MEDICAL)
    private List<MedicalDetails> medicalDetailsList;

    @SerializedName(PATIENT_MEDICAL_DATA_RESPONSE)
    private List<MedicalDetails> medicalDetailsRespList;

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

    public List<UserDetails> getLogInData() {
        return logInData;
    }

    public void setLogInData(List<UserDetails> logInData) {
        this.logInData = logInData;
    }

    public List<Camp> getCampList() {
        return campList;
    }

    public void setCampList(List<Camp> campList) {
        this.campList = campList;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public List<SubTag> getSubTagList() {
        return subTagList;
    }

    public void setSubTagList(List<SubTag> subTagList) {
        this.subTagList = subTagList;
    }

    public List<State> getStateList() {
        return stateList;
    }

    public void setStateList(List<State> stateList) {
        this.stateList = stateList;
    }

    public List<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
    }

    public List<Patient> getPatientDataList() {
        return patientDataList;
    }

    public void setPatientDataList(List<Patient> patientDataList) {
        this.patientDataList = patientDataList;
    }

    @SerializedName(CREATED_DATE)
    private String createdDate;

    @SerializedName(MODIFIED_DATE)
    private String modifiedDate;

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }


    public List<Patient> getPrList() {
        return prList;
    }

    public void setPrList(List<Patient> prList) {
        this.prList = prList;
    }

    public List<BloodPressureNRData> getBloodPressureList() {
        return bloodPressureList;
    }

    public void setBloodPressureList(List<BloodPressureNRData> bloodPressureList) {
        this.bloodPressureList = bloodPressureList;
    }

    public List<MedicalDetails> getMedicalDetailsList() {
        return medicalDetailsList;
    }

    public void setMedicalDetailsList(List<MedicalDetails> medicalDetailsList) {
        this.medicalDetailsList = medicalDetailsList;
    }

    public List<MedicalDetails> getMedicalDetailsRespList() {
        return medicalDetailsRespList;
    }

    public void setMedicalDetailsRespList(List<MedicalDetails> medicalDetailsRespList) {
        this.medicalDetailsRespList = medicalDetailsRespList;
    }
}
