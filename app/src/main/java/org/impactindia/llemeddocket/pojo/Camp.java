package org.impactindia.llemeddocket.pojo;

import com.google.gson.annotations.SerializedName;

public class Camp implements WsModel, Model {

    public static final String CAMP_ID = "CampId";
    public static final String CAMP_YEAR = "CampYear";
    public static final String CAMP_NO = "CampNo";
    public static final String CAMP_LOCATION = "CampLocation";
    public static final String FROM_DATE = "FromDate";
    public static final String TO_DATE = "ToDate";

    private Long id;

    @SerializedName(CAMP_ID)
    private Long campId;

    @SerializedName(CAMP_YEAR)
    private String campYear;

    @SerializedName(CAMP_NO)
    private String campNo;

    @SerializedName(CAMP_LOCATION)
    private String campLocation;

    @SerializedName(FROM_DATE)
    private String fromDate;

    @SerializedName(TO_DATE)
    private String toDate;

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

    public String getCampYear() {
        return campYear;
    }

    public void setCampYear(String campYear) {
        this.campYear = campYear;
    }

    public String getCampNo() {
        return campNo;
    }

    public void setCampNo(String campNo) {
        this.campNo = campNo;
    }

    public String getCampLocation() {
        return campLocation;
    }

    public void setCampLocation(String campLocation) {
        this.campLocation = campLocation;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}
