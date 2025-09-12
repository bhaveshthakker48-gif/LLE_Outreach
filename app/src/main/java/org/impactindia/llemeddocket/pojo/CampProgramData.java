package org.impactindia.llemeddocket.pojo;

public class CampProgramData {
    int p_id;
    String camp_id,camp_no,campprogram,campfrom,campto;

    public CampProgramData(String camp_id, String camp_no, String campprogram, String campfrom, String campto) {
        this.camp_id = camp_id;
        this.camp_no = camp_no;
        this.campprogram = campprogram;
        this.campfrom = campfrom;
        this.campto = campto;
    }

    public CampProgramData() {
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public String getCamp_id() {
        return camp_id;
    }

    public void setCamp_id(String camp_id) {
        this.camp_id = camp_id;
    }

    public String getCamp_no() {
        return camp_no;
    }

    public void setCamp_no(String camp_no) {
        this.camp_no = camp_no;
    }

    public String getCampprogram() {
        return campprogram;
    }

    public void setCampprogram(String campprogram) {
        this.campprogram = campprogram;
    }

    public String getCampfrom() {
        return campfrom;
    }

    public void setCampfrom(String campfrom) {
        this.campfrom = campfrom;
    }

    public String getCampto() {
        return campto;
    }

    public void setCampto(String campto) {
        this.campto = campto;
    }
}
