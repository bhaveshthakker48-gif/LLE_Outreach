package org.impactindia.llemeddocket.pojo;

public class OutreachPlanData {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;
    String campID,campNo,Outrach_userId,campYear,camp_Location,state,outreach_From,outreach_To,fromDate,toDate,distric,taluka,outreachplan_ID,designation;

    public OutreachPlanData() {
    }

    public OutreachPlanData(String campID, String campNo, String outrach_userId, String campYear, String camp_Location,
                            String state, String outreach_From, String outreach_To, String fromDate,
                            String toDate, String distric, String taluka, String outreachplan_ID, String designation) {
        this.campID = campID;
        this.campNo = campNo;
        this.Outrach_userId = outrach_userId;
        this.campYear = campYear;
        this.camp_Location = camp_Location;
        this.state = state;
        this.outreach_From = outreach_From;
        this.outreach_To = outreach_To;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.distric = distric;
        this.taluka = taluka;
        this.outreachplan_ID = outreachplan_ID;
    }

    public String getCampID() {
        return campID;
    }

    public void setCampID(String campID) {
        this.campID = campID;
    }

    public String getCampNo() {
        return campNo;
    }

    public void setCampNo(String campNo) {
        this.campNo = campNo;
    }

    public String getOutrach_userId() {
        return Outrach_userId;
    }

    public void setOutrach_userId(String outrach_userId) {
        Outrach_userId = outrach_userId;
    }

    public String getCampYear() {
        return campYear;
    }

    public void setCampYear(String campYear) {
        this.campYear = campYear;
    }

    public String getCamp_Location() {
        return camp_Location;
    }

    public void setCamp_Location(String camp_Location) {
        this.camp_Location = camp_Location;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOutreach_From() {
        return outreach_From;
    }

    public void setOutreach_From(String outreach_From) {
        this.outreach_From = outreach_From;
    }

    public String getOutreach_To() {
        return outreach_To;
    }

    public void setOutreach_To(String outreach_To) {
        this.outreach_To = outreach_To;
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

    public String getDistric() {
        return distric;
    }

    public void setDistric(String distric) {
        this.distric = distric;
    }

    public String getTaluka() {
        return taluka;
    }

    public void setTaluka(String taluka) {
        this.taluka = taluka;
    }

    public String getOutreachplan_ID() {
        return outreachplan_ID;
    }

    public void setOutreachplan_ID(String outreachplan_ID) {
        this.outreachplan_ID = outreachplan_ID;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
