package org.impactindia.llemeddocket.pojo;

import java.util.ArrayList;

public class PadanashaworkerData {
    Integer id,campid,outreachdetailsid,outreachuserid;
    String campno,dateofdataenter,outreachdate,pada,subcenter,village,phc,anmname,anmmobileno,ashaworkername,ashaworkermobile,gpname,contactpersonname,cpmobile;

    public ArrayList<PopMedicalData> getPopulationdetails() {
        return populationdetails;
    }

    public void setPopulationdetails(ArrayList<PopMedicalData> populationdetails) {
        this.populationdetails = populationdetails;
    }

    ArrayList<PopMedicalData> populationdetails;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCampid() {
        return campid;
    }

    public void setCampid(Integer campid) {
        this.campid = campid;
    }

    public String getCampno() {
        return campno;
    }

    public void setCampno(String campno) {
        this.campno = campno;
    }

    public Integer getOutreachdetailsid() {
        return outreachdetailsid;
    }

    public void setOutreachdetailsid(Integer outreachdetailsid) {
        this.outreachdetailsid = outreachdetailsid;
    }

    public Integer getUserid() {
        return outreachuserid;
    }

    public void setUserid(Integer userid) {
        this.outreachuserid = userid;
    }

    public String getDateofdataenter() {
        return dateofdataenter;
    }

    public void setDateofdataenter(String dateofdataenter) {
        this.dateofdataenter = dateofdataenter;
    }

    public String getOutreachdate() {
        return outreachdate;
    }

    public void setOutreachdate(String outreachdate) {
        this.outreachdate = outreachdate;
    }

    public String getPada() {
        return pada;
    }

    public void setPada(String pada) {
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

    public String getPhc() {
        return phc;
    }

    public void setPhc(String phc) {
        this.phc = phc;
    }

    public String getAnmname() {
        return anmname;
    }

    public void setAnmname(String anmname) {
        this.anmname = anmname;
    }

    public String getAnmmobileno() {
        return anmmobileno;
    }

    public void setAnmmobileno(String anmmobileno) {
        this.anmmobileno = anmmobileno;
    }

    public String getAshaworkername() {
        return ashaworkername;
    }

    public void setAshaworkername(String ashaworkername) {
        this.ashaworkername = ashaworkername;
    }

    public String getAshaworkermobile() {
        return ashaworkermobile;
    }

    public void setAshaworkermobile(String ashaworkermobile) {
        this.ashaworkermobile = ashaworkermobile;
    }

    public String getGpname() {
        return gpname;
    }

    public void setGpname(String gpname) {
        this.gpname = gpname;
    }

    public String getContactpersonname() {
        return contactpersonname;
    }

    public void setContactpersonname(String contactpersonname) {
        this.contactpersonname = contactpersonname;
    }

    public String getCpmobile() {
        return cpmobile;
    }

    public void setCpmobile(String cpmobile) {
        this.cpmobile = cpmobile;
    }

    public PadanashaworkerData(Integer campid, Integer outreachuserid, String outreachdate, String dateofdataenter,
                               String pada, String village, String phc, String subcenter,
                               String anmname, String anmmobileno, String ashaworkername, String ashaworkermobile,
                               String gpname, String contactpersonname, String cpmobile, Integer outreachdetailsid) {
        this.campid = campid;
//        this.campno = campno;
        this.outreachuserid = outreachuserid;
        this.outreachdate = outreachdate;
        this.dateofdataenter = dateofdataenter;
        this.pada = pada;
        this.subcenter = subcenter;
        this.village = village;
        this.phc = phc;
        this.anmname = anmname;
        this.anmmobileno = anmmobileno;
        this.ashaworkername = ashaworkername;
        this.ashaworkermobile = ashaworkermobile;
        this.gpname = gpname;
        this.contactpersonname = contactpersonname;
        this.cpmobile = cpmobile;
        this.outreachdetailsid = outreachdetailsid;
    }


    public PadanashaworkerData() {
    }




}
