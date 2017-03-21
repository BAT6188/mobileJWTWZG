package com.zondy.jwt.jwtmobile.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/10.
 */
public class EntityTempJingq implements Serializable {

    String submitid;//上报警情的ID
    String bjrxm;//报警警员姓名
    String bjrjh;//报警警员警号
    String bjsj;//报警时间
    String shenhetgsj;//审核通过时间
    String bjrdh;//报警人电话
    String bjdz;//报警地址
    String baojnr;//报警内容

    String bjlb;//报警类别
    String bjlx;//报警类型
    String bjxl;//报警细类
    String bjlbdm;//案件类别代码
    String bjlxdm;//案件类型代码
    String bjxldm;//案件细类代码

    String longitude;//经度
    String latitude;//纬度
    String filesPath;//文件路径
    int shenhezt;//审核状态 0-未审核，1-审核通过，2-审核未通过
    String nopassReason;//审核未通过原因
    String phoneWhenUncheck;//未审核时供办联系电话


    public static final int UNCHECK = 0;// 0-未审核
    public static final int HADCHECK = 1;//1-审核通过
    public static final int HADCHECKFAILED = 2;//2-审核未通过


    public String getBaojnr() {
        return baojnr;
    }

    public void setBaojnr(String baojnr) {
        this.baojnr = baojnr;
    }

    public String getBjdz() {
        return bjdz;
    }

    public void setBjdz(String bjdz) {
        this.bjdz = bjdz;
    }

    public String getBjlb() {
        return bjlb;
    }

    public void setBjlb(String bjlb) {
        this.bjlb = bjlb;
    }

    public String getBjlbdm() {
        return bjlbdm;
    }

    public void setBjlbdm(String bjlbdm) {
        this.bjlbdm = bjlbdm;
    }

    public String getBjlx() {
        return bjlx;
    }

    public void setBjlx(String bjlx) {
        this.bjlx = bjlx;
    }

    public String getBjlxdm() {
        return bjlxdm;
    }

    public void setBjlxdm(String bjlxdm) {
        this.bjlxdm = bjlxdm;
    }

    public String getBjrdh() {
        return bjrdh;
    }

    public void setBjrdh(String bjrdh) {
        this.bjrdh = bjrdh;
    }

    public String getBjrjh() {
        return bjrjh;
    }

    public void setBjrjh(String bjrjh) {
        this.bjrjh = bjrjh;
    }

    public String getBjrxm() {
        return bjrxm;
    }

    public void setBjrxm(String bjrxm) {
        this.bjrxm = bjrxm;
    }

    public String getBjsj() {
        return bjsj;
    }

    public void setBjsj(String bjsj) {
        this.bjsj = bjsj;
    }

    public String getBjxl() {
        return bjxl;
    }

    public void setBjxl(String bjxl) {
        this.bjxl = bjxl;
    }

    public String getBjxldm() {
        return bjxldm;
    }

    public void setBjxldm(String bjxldm) {
        this.bjxldm = bjxldm;
    }

    public String getFilesPath() {
        return filesPath;
    }

    public void setFilesPath(String filesPath) {
        this.filesPath = filesPath;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getShenhezt() {
        return shenhezt;
    }

    public void setShenhezt(int shenhezt) {
        this.shenhezt = shenhezt;
    }

    public String getSubmitid() {
        return submitid;
    }

    public void setSubmitid(String submitid) {
        this.submitid = submitid;
    }

    public String getShenhetgsj() {
        return shenhetgsj;
    }

    public void setShenhetgsj(String shenhetgsj) {
        this.shenhetgsj = shenhetgsj;
    }

    public String getNopassReason() {
        return nopassReason;
    }

    public void setNopassReason(String nopassReason) {
        this.nopassReason = nopassReason;
    }

    public String getPhoneWhenUncheck() {
        return phoneWhenUncheck;
    }

    public void setPhoneWhenUncheck(String phoneWhenUncheck) {
        this.phoneWhenUncheck = phoneWhenUncheck;
    }
}
