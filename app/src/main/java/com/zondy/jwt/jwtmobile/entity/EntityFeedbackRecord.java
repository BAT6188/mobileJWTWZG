package com.zondy.jwt.jwtmobile.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/16.
 */
public class EntityFeedbackRecord implements Serializable {

    String jingqid;//对某一警情
    String feedbackid;//反馈消息ID
    int feedbacktype;//0-警员向中心，1-中心向警员
    String fkfxm;//反馈警员姓名
    String fkfjh;//反馈警员警号
    String zzjgmc;//组织单位名称
    String zzjgdm;//组织单位代码
    String jydh;//警员电话
    String zzjgdh;//组织机构电话
    String fknr;
    String fksj;
    String filespath;

    public static final int TOCENTER = 0;// 0-警员向中心
    public static final int TOPOLICE = 1;//1-中心向警员

    public String getFeedbackid() {
        return feedbackid;
    }

    public void setFeedbackid(String feedbackid) {
        this.feedbackid = feedbackid;
    }

    public int getFeedbacktype() {
        return feedbacktype;
    }

    public void setFeedbacktype(int feedbacktype) {
        this.feedbacktype = feedbacktype;
    }

    public String getFkfjh() {
        return fkfjh;
    }

    public void setFkfjh(String fkfjh) {
        this.fkfjh = fkfjh;
    }

    public String getFilespath() {
        return filespath;
    }

    public void setFilespath(String filespath) {
        this.filespath = filespath;
    }

    public String getFkfxm() {
        return fkfxm;
    }

    public void setFkfxm(String fkfxm) {
        this.fkfxm = fkfxm;
    }

    public String getFknr() {
        return fknr;
    }

    public void setFknr(String fknr) {
        this.fknr = fknr;
    }

    public String getFksj() {
        return fksj;
    }

    public void setFksj(String fksj) {
        this.fksj = fksj;
    }

    public String getJydh() {
        return jydh;
    }

    public void setJydh(String jydh) {
        this.jydh = jydh;
    }

    public String getZzjgdh() {
        return zzjgdh;
    }

    public void setZzjgdh(String zzjgdh) {
        this.zzjgdh = zzjgdh;
    }

    public String getZzjgdm() {
        return zzjgdm;
    }

    public void setZzjgdm(String zzjgdm) {
        this.zzjgdm = zzjgdm;
    }

    public String getZzjgmc() {
        return zzjgmc;
    }

    public void setZzjgmc(String zzjgmc) {
        this.zzjgmc = zzjgmc;
    }

    public String getJingqid() {
        return jingqid;
    }

    public void setJingqid(String jingqid) {
        this.jingqid = jingqid;
    }
}
