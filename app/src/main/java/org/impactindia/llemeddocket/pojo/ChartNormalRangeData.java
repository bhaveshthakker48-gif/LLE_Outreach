package org.impactindia.llemeddocket.pojo;

public class ChartNormalRangeData {

    String chartid;
    String xvalue;
    String y3;
    String y5;
    String y10;
    String y25;
    String ynormal;
    String y75;
    String y90;
    String y97;
    String createddate;
    String rangestart;
    String rangend;
    String y23;
    String y27;
    String modifieddate;

    public String getY95() {
        return y95;
    }

    public void setY95(String y95) {
        this.y95 = y95;
    }

    String y95;
    private Double overWeight;
    private Double obese;

    public Double getOverWeight() {
        return overWeight;
    }

    public void setOverWeight(Double overWeight) {
        this.overWeight = overWeight;
    }

    public Double getObese() {
        return obese;
    }

    public void setObese(Double obese) {
        this.obese = obese;
    }

    public ChartNormalRangeData(String chartid, String xvalue, String y3, String y5, String y10,
                                String y25, String ynormal, String y75, String y90, String y97,
                                String createddate, String rangestart, String rangend, String y23,
                                String y27) {
        this.chartid = chartid;
        this.xvalue = xvalue;
        this.y3 = y3;
        this.y5 = y5;
        this.y10 = y10;
        this.y25 = y25;
        this.ynormal = ynormal;
        this.y75 = y75;
        this.y90 = y90;
        this.y97 = y97;
        this.createddate = createddate;
        this.rangestart = rangestart;
        this.rangend = rangend;
        this.y23 = y23;
        this.y27 = y27;
       // this.modifieddate = modifieddate;
    }

    public ChartNormalRangeData() {
    }

    public String getChartid() {
        return chartid;
    }

    public void setChartid(String chartid) {
        this.chartid = chartid;
    }

    public String getXvalue() {
        return xvalue;
    }

    public void setXvalue(String xvalue) {
        this.xvalue = xvalue;
    }

    public String getY3() {
        return y3;
    }

    public void setY3(String y3) {
        this.y3 = y3;
    }

    public String getY5() {
        return y5;
    }

    public void setY5(String y5) {
        this.y5 = y5;
    }

    public String getY10() {
        return y10;
    }

    public void setY10(String y10) {
        this.y10 = y10;
    }

    public String getY25() {
        return y25;
    }

    public void setY25(String y25) {
        this.y25 = y25;
    }

    public String getYnormal() {
        return ynormal;
    }

    public void setYnormal(String ynormal) {
        this.ynormal = ynormal;
    }

    public String getY75() {
        return y75;
    }

    public void setY75(String y75) {
        this.y75 = y75;
    }

    public String getY90() {
        return y90;
    }

    public void setY90(String y90) {
        this.y90 = y90;
    }

    public String getY97() {
        return y97;
    }

    public void setY97(String y97) {
        this.y97 = y97;
    }

    public String getCreateddate() {
        return createddate;
    }

    public void setCreateddate(String createddate) {
        this.createddate = createddate;
    }

    public String getRangestart() {
        return rangestart;
    }

    public void setRangestart(String rangestart) {
        this.rangestart = rangestart;
    }

    public String getRangend() {
        return rangend;
    }

    public void setRangend(String rangend) {
        this.rangend = rangend;
    }

    public String getY23() {
        return y23;
    }

    public void setY23(String y23) {
        this.y23 = y23;
    }

    public String getY27() {
        return y27;
    }

    public void setY27(String y27) {
        this.y27 = y27;
    }

    public String getModifieddate() {
        return modifieddate;
    }

    public void setModifieddate(String modifieddate) {
        this.modifieddate = modifieddate;
    }
}
