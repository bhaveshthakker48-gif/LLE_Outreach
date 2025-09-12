package org.impactindia.llemeddocket.pojo;

public class SyncData {
    String campid,userid,synchdate,synchtime,extra,synchid;

    public SyncData(String campid, String userid, String synchdate, String synchtime, String extra) {
        this.campid = campid;
        this.userid = userid;
        this.synchdate = synchdate;
        this.synchtime = synchtime;
        this.extra = extra;
    }

    public String getCampid() {
        return campid;
    }

    public String getSynchid() {
        return synchid;
    }

    public void setSynchid(String synchid) {
        this.synchid = synchid;
    }

    public void setCampid(String campid) {
        this.campid = campid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSynchdate() {
        return synchdate;
    }

    public void setSynchdate(String synchdate) {
        this.synchdate = synchdate;
    }

    public String getSynchtime() {
        return synchtime;
    }

    public void setSynchtime(String synchtime) {
        this.synchtime = synchtime;
    }

    public String getExtra() {
        return extra;
    }

    public SyncData() {
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }


}
