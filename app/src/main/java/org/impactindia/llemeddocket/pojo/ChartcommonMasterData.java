package org.impactindia.llemeddocket.pojo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.impactindia.llemeddocket.DatabaseHelper;

public class ChartcommonMasterData {

    String chartid,type,isdelted,gender,range,xmin,xmax,ymin,ymax,charttitle,xtitle,ytitle,xinterval,yinterval,datasource,flag;
    //boolean isdelted;

    public ChartcommonMasterData(String chartid, String type, String gender, String range, String xmin, String xmax,
                                 String ymin, String ymax, String charttitle, String xtitle, String ytitle, String xinterval,
                                 String yinterval, String datasource, String flag) {
        this.chartid = chartid;
        this.type = type;
        this.gender = gender;
        this.range = range;
        this.xmin = xmin;
        this.xmax = xmax;
        this.ymin = ymin;
        this.ymax = ymax;
        this.charttitle = charttitle;
        this.xtitle = xtitle;
        this.ytitle = ytitle;
        this.xinterval = xinterval;
        this.yinterval = yinterval;
        this.datasource = datasource;
        this.flag = flag;
        this.isdelted = isdelted;
    }

    public ChartcommonMasterData() {
    }

    public String getChartid() {
        return chartid;
    }

    public void setChartid(String chartid) {
        this.chartid = chartid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getXmin() {
        return xmin;
    }

    public void setXmin(String xmin) {
        this.xmin = xmin;
    }

    public String getXmax() {
        return xmax;
    }

    public void setXmax(String xmax) {
        this.xmax = xmax;
    }

    public String getYmin() {
        return ymin;
    }

    public void setYmin(String ymin) {
        this.ymin = ymin;
    }

    public String getYmax() {
        return ymax;
    }

    public void setYmax(String ymax) {
        this.ymax = ymax;
    }

    public String getCharttitle() {
        return charttitle;
    }

    public void setCharttitle(String charttitle) {
        this.charttitle = charttitle;
    }

    public String getXtitle() {
        return xtitle;
    }

    public void setXtitle(String xtitle) {
        this.xtitle = xtitle;
    }

    public String getYtitle() {
        return ytitle;
    }

    public void setYtitle(String ytitle) {
        this.ytitle = ytitle;
    }

    public String getXinterval() {
        return xinterval;
    }

    public void setXinterval(String xinterval) {
        this.xinterval = xinterval;
    }

    public String getYinterval() {
        return yinterval;
    }

    public void setYinterval(String yinterval) {
        this.yinterval = yinterval;
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String isIsdelted() {
        return isdelted;
    }

    public void setIsdelted(String isdelted) {
        this.isdelted = isdelted;
    }
}
