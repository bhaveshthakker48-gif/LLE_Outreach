package org.impactindia.llemeddocket.pojo;

public class PhcSubcenterData {
    int id;
    String campID,subCenter,phc,campNo,village,pada;

    public PhcSubcenterData() {
    }

    public PhcSubcenterData(String campID,String campNo, String subCenter, String phc,String village,String pada) {
        this.campID = campID;
        this.subCenter = subCenter;
        this.phc = phc;
        this.campNo = campNo;
        this.village = village;
        this.pada = pada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCampID() {
        return campID;
    }

    public void setCampID(String campID) {
        this.campID = campID;
    }

    public String getSubCenter() {
        return subCenter;
    }

    public void setSubCenter(String subCenter) {
        this.subCenter = subCenter;
    }

    public String getPhc() {
        return phc;
    }

    public void setPhc(String phc) {
        this.phc = phc;
    }

    public String getCampNo() {
        return campNo;
    }

    public void setCampNo(String campNo) {
        this.campNo = campNo;
    }


}
