package org.impactindia.llemeddocket.pojo;

import com.google.gson.annotations.SerializedName;

import org.impactindia.llemeddocket.orm.DAO;
import org.impactindia.llemeddocket.orm.DAOException;

public class BloodPressureNRData extends Base implements WsModel, Model {

    private final static String ID = "ID";
    private static final String AGE = "Age";
    private static final String SYSTOLIC_MMHG = "Systolic_mmHg";
    private static final String DIASTOLIC_MMHG = "Diastolic_mmHg";
    private static final String AGE_RANGE_START = "AgeRangeStart";
    private static final String AGE_RANGE_END = "AgeRangeEnd";
    private static final String AGE_RANGE_TYPE = "AgeRangeType";
    private static final String SYSTOLIC_RANGE_START = "SystolicRangeStart";
    private static final String SYSTOLIC_RANGE_END = "SystolicRangeEnd";
    private static final String DIASTOLIC_RANGE_START = "DiastolicRangeStart";
    private static final String DIASTOLIC_RANGE_END = "DiastolicRangeEnd";
    private final static String FLAG = "Flag";
    @SerializedName(ID)
    private Long id;

    @SerializedName(AGE)
    private String age;

    @SerializedName(SYSTOLIC_MMHG)
    private String systolicMMHG;

    @SerializedName(DIASTOLIC_MMHG)
    private String diastolicMMHG;

    @SerializedName(AGE_RANGE_START)
    private String ageRangeStart;

    @SerializedName(AGE_RANGE_END)
    private String ageRangeEnd;

    @SerializedName(AGE_RANGE_TYPE)
    private String ageRangeType;

    @SerializedName(SYSTOLIC_RANGE_START)
    private String systolicRangeStart;

    @SerializedName(SYSTOLIC_RANGE_END)
    private String systolicRangeEnd;

    @SerializedName(DIASTOLIC_RANGE_START)
    private String diastolicRangeStart;

    @SerializedName(DIASTOLIC_RANGE_END)
    private String diastolicRangeEnd;

    @SerializedName(FLAG)
    private String flag;


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSystolicMMHG() {
        return systolicMMHG;
    }

    public void setSystolicMMHG(String systolicMMHG) {
        this.systolicMMHG = systolicMMHG;
    }

    public String getDiastolicMMHG() {
        return diastolicMMHG;
    }

    public void setDiastolicMMHG(String diastolicMMHG) {
        this.diastolicMMHG = diastolicMMHG;
    }

    public String getAgeRangeStart() {
        return ageRangeStart;
    }

    public void setAgeRangeStart(String ageRangeStart) {
        this.ageRangeStart = ageRangeStart;
    }

    public String getAgeRangeEnd() {
        return ageRangeEnd;
    }

    public void setAgeRangeEnd(String ageRangeEnd) {
        this.ageRangeEnd = ageRangeEnd;
    }

    public String getAgeRangeType() {
        return ageRangeType;
    }

    public void setAgeRangeType(String ageRangeType) {
        this.ageRangeType = ageRangeType;
    }

    public String getSystolicRangeStart() {
        return systolicRangeStart;
    }

    public void setSystolicRangeStart(String systolicRangeStart) {
        this.systolicRangeStart = systolicRangeStart;
    }

    public String getSystolicRangeEnd() {
        return systolicRangeEnd;
    }

    public void setSystolicRangeEnd(String systolicRangeEnd) {
        this.systolicRangeEnd = systolicRangeEnd;
    }

    public String getDiastolicRangeStart() {
        return diastolicRangeStart;
    }

    public void setDiastolicRangeStart(String diastolicRangeStart) {
        this.diastolicRangeStart = diastolicRangeStart;
    }

    public String getDiastolicRangeEnd() {
        return diastolicRangeEnd;
    }

    public void setDiastolicRangeEnd(String diastolicRangeEnd) {
        this.diastolicRangeEnd = diastolicRangeEnd;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

}
