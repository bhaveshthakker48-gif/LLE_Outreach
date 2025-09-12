package org.impactindia.llemeddocket.pojo;

import com.google.gson.annotations.SerializedName;

public class Patient implements WsModel, Model {

    public final static String CAMP_ID = "CampId";
    public final static String USER_ID = "UserID";
    public final static String REG_NO = "RegNo";
    public final static String PATIENT_ID = "PatientID";
    public final static String FNAME = "FName";
    public final static String MNAME = "MName";
    public final static String LNAME = "LName";
    public final static String GENDER = "Gender";
    public final static String DOB = "DOB";
    public final static String AGE = "Age";
    public final static String AGE_UNIT = "AgeUnit";
    public static final String HOUSE_NAME_NO = "HouseNameNo";
    public static final String STREET_NAME = "StreetName";
    public static final String LOCALITY_AREA_PADA = "LocalityAreaPada";
    public static final String CITY_TOWN_VILLAGE = "CityTownVillage";
    public static final String TALUKA = "Taluka";
    public static final String DISTRICT = "District";
    public static final String STATE_ID = "StateId";
    public static final String STATE_NAME = "StateName";
    public static final String PIN_CODE = "Pincode";
    public static final String COUNTRY_NAME = "CountryName";
    public final static String MOBILE_NO = "MobileNo";
    public final static String RESIDENCE_NO = "ResidenceNo";
    public final static String AADHAR_NO = "AadharNo";
    public final static String EMAIL_ID = "EmailID";
    public final static String REFERRED_BY = "ReferredBy";
    public final static String TAG_NAME = "TagName";
    public final static String TAG_ID = "TagId";
    public final static String PROFILE_IMG_PATH = "ProfileImagePath";
    public final static String PROFILE_IMG_NAME = "ProfileImageName";
    public final static String HAS_IMG = "Has_Img";
    public final static String IS_ACTIVE = "IsActive";
    public final static String LAST_VISIT_DATE = "LastVisitDate";
    public final static String DATE_CREATED = "DateCreated";
    public final static String DATE_MODIFIED = "ModifiedDate";
    private final static String ERROR_CODE = "ErrorCode";
    private final static String ERROR_MSG = "ErrorMessage";

    private Long id;

    @SerializedName(CAMP_ID)
    private Long campId;

    @SerializedName(USER_ID)
    private Long userId;

    @SerializedName(REG_NO)
    private String regNo;

    @SerializedName(FNAME)
    private String fName;

    @SerializedName(MNAME)
    private String mName;

    @SerializedName(LNAME)
    private String lName;

    @SerializedName(GENDER)
    private String gender;

    @SerializedName(DOB)
    private String dob;

    @SerializedName(AGE)
    private Integer age;

    @SerializedName(AGE_UNIT)
    private String ageUnit;

    @SerializedName(HOUSE_NAME_NO)
    private String houseNameNo;

    @SerializedName(STREET_NAME)
    private String streetName;

    @SerializedName(LOCALITY_AREA_PADA)
    private String localityAreaPada;

    @SerializedName(CITY_TOWN_VILLAGE)
    private String cityTownVillage;

    @SerializedName(TALUKA)
    private String taluka;

    @SerializedName(DISTRICT)
    private String district;

    @SerializedName(COUNTRY_NAME)
    private String countryName;

    @SerializedName(STATE_ID)
    private Long stateId;

    @SerializedName(STATE_NAME)
    private String stateName;

    @SerializedName(PIN_CODE)
    private String pinCode;

    @SerializedName(MOBILE_NO)
    private String mobileNo;

    @SerializedName(RESIDENCE_NO)
    private String residenceNo;

    @SerializedName(AADHAR_NO)
    private String aadharNo;

    @SerializedName(EMAIL_ID)
    private String emailId;

    @SerializedName(REFERRED_BY)
    private String referredBy;

    @SerializedName(TAG_NAME)
    private String tagName;

    @SerializedName(TAG_ID)
    private String tagId;

    @SerializedName(PROFILE_IMG_PATH)
    private String profileImgPath;

    @SerializedName(PROFILE_IMG_NAME)
    private String profileImgName;

    @SerializedName(IS_ACTIVE)
    private boolean active;

    @SerializedName(PATIENT_ID)
    private Long patientId;

    @SerializedName(LAST_VISIT_DATE)
    private String lastVisitDate;

    @SerializedName(DATE_CREATED)
    private String dateCreated;

    @SerializedName(DATE_MODIFIED)
    private String dateModified;

    @SerializedName(ERROR_CODE)
    private Long errorCode;

    @SerializedName(ERROR_MSG)
    private String errorMsg;

    @SerializedName(HAS_IMG)
    private boolean hasImg;

    private boolean fresh;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAgeUnit() {
        return ageUnit;
    }

    public void setAgeUnit(String ageUnit) {
        this.ageUnit = ageUnit;
    }

    public String getHouseNameNo() {
        return houseNameNo;
    }

    public void setHouseNameNo(String houseNameNo) {
        this.houseNameNo = houseNameNo;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getLocalityAreaPada() {
        return localityAreaPada;
    }

    public void setLocalityAreaPada(String localityAreaPada) {
        this.localityAreaPada = localityAreaPada;
    }

    public String getCityTownVillage() {
        return cityTownVillage;
    }

    public void setCityTownVillage(String cityTownVillage) {
        this.cityTownVillage = cityTownVillage;
    }

    public String getTaluka() {
        return taluka;
    }

    public void setTaluka(String taluka) {
        this.taluka = taluka;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getResidenceNo() {
        return residenceNo;
    }

    public void setResidenceNo(String residenceNo) {
        this.residenceNo = residenceNo;
    }

    public String getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(String aadharNo) {
        this.aadharNo = aadharNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getReferredBy() {
        return referredBy;
    }

    public void setReferredBy(String referredBy) {
        this.referredBy = referredBy;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getProfileImgPath() {
        return profileImgPath;
    }

    public void setProfileImgPath(String profileImgPath) {
        this.profileImgPath = profileImgPath;
    }

    public String getProfileImgName() {
        return profileImgName;
    }

    public void setProfileImgName(String profileImgName) {
        this.profileImgName = profileImgName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getLastVisitDate() {
        return lastVisitDate;
    }

    public void setLastVisitDate(String lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
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

    public boolean isHasImg() {
        return hasImg;
    }

    public void setHasImg(boolean hasImg) {
        this.hasImg = hasImg;
    }

    public boolean isFresh() {
        return fresh;
    }

    public void setFresh(boolean fresh) {
        this.fresh = fresh;
    }
}
