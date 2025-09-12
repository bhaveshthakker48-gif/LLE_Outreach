package org.impactindia.llemeddocket.pojo;

public class Householddata {

    int id;
    String household_no;
    String total_member;
    String member_type;
    String relationship;
    String f_name;
    String m_name;
    String l_name;
    String gender;
    String dob;
    String age;

    public Householddata() {
    }

    public Householddata(int id, String household_no, String total_member, String member_type, String relationship, String f_name, String m_name, String l_name, String gender, String dob, String age) {
        this.id = id;
        this.household_no = household_no;
        this.total_member = total_member;
        this.member_type = member_type;
        this.relationship = relationship;
        this.f_name = f_name;
        this.m_name = m_name;
        this.l_name = l_name;
        this.gender = gender;
        this.dob = dob;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHousehold_no() {
        return household_no;
    }

    public void setHousehold_no(String household_no) {
        this.household_no = household_no;
    }

    public String getTotal_member() {
        return total_member;
    }

    public void setTotal_member(String total_member) {
        this.total_member = total_member;
    }

    public String getMember_type() {
        return member_type;
    }

    public void setMember_type(String member_type) {
        this.member_type = member_type;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
