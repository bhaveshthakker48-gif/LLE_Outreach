package org.impactindia.llemeddocket.pojo;

public class PhcUserData {

    int p_id;
    String campID,campNo,phc,phc_userID,subcenter,village,pada;

    public PhcUserData() {
    }

    public PhcUserData(String campID, String campNo, String phc, String phc_userID , String subcenter, String village,String pada) {
        this.campID = campID;
        this.campNo = campNo;
        this.phc = phc;
        this.phc_userID = phc_userID;
        this.subcenter = subcenter;
        this.village = village;
        this.pada = pada;
    }


    public String getSubcenter() {
        return subcenter;
    }

    public void setSubcenter(String subcenter) {
        this.subcenter = subcenter;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getPada() {
        return pada;
    }

    public void setPada(String pada) {
        this.pada = pada;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
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

    public String getPhc() {
        return phc;
    }

    public void setPhc(String phc) {
        this.phc = phc;
    }

    public String getPhc_userID() {
        return phc_userID;
    }

    public void setPhc_userID(String phc_userID) {
        this.phc_userID = phc_userID;
    }
}
