package com.zondy.jwt.jwtmobile.entity;

import com.zondy.jwt.jwtmobile.util.GsonUtil;

import java.io.Serializable;

/**
 * Created by ywj on 2017/1/12 0012.
 */

public class EntityJingq implements Serializable {

    public static final int UNREAD = 0;// 0-未受理
    public static final int HADREAD = 1;//1-已接警
    public static final int HADREACHCONFIRM = 2;//2-已确认到场
    public static final int HADHANDLED = 3;//3-处警完毕

    private static final long serialVersionUID = 1L;
    String jingqid;//警情id
    String baojr;// 报警人
    String baojrdh;// 报警人电话
    String baojdz;// 报警地址
    String baojnr;// 报警内容
    String baojsj;// 报警时间
    int jingqzt;// 处理状态，0-未受理，1-已接警，2-到达现场，3-处警完毕,资料提交成功,4-处警完毕，资料提交失败。
    String jiejh;// 接警号
    String bjlb; //案件类别
    String bjlx; //案件类型
    String bjxl; //案件细类
    String bjlbdm;//案件类别代码
    String bjlxdm;//案件类型代码
    String bjxldm;//案件细类代码
    String fknr;// 反馈内容
    String filesPath;// 文件路径
    String longitude;//经度
    String latitude;//纬度
    String gxdwdm;//管辖单位代码
    String zhipsj;//指派时间
    String chujsj;//处警(接警)时间
    String daodsj;//到达时间
    String fanksj;//反馈时间

    String zjg_cjjg;//null,处警结果
    String zjg_cjlbbm;//null,处警类别编码
    String zjg_cjlbmc;//null,处警类别名称
    String zjg_cjr;//null,处警人
    String zjg_cjsxbm;//null,处警属性编码
    String zjg_cjsxmc;//null,处警属性名称
    String zjg_fsdd;//null,事发地点
    String zjg_sfcsbm;//null,事发场所编码
    String zjg_sfcsmc;//null,事发场所名称
    String zjg_sfsjsx;//null,事发时间上限
    String zjg_sfsjxx;//null,事发时间下限
    String zjg_ssqk;//null,损失情况
    String zjg_tqqkbm;//null,天气情况编码
    String zjg_tqqkmc;//null,天气情况名称

    public String jingyid;//处理警情的警员id
    long id;//存储在本地数据库的id
    int readState;//阅读状态，0-未读，1-已读
    String time;//存储在本地数据库的时间


    public String getJingqid() {
        return jingqid;
    }

    public String getGxdwdm() {
        return gxdwdm;
    }

    public void setGxdwdm(String gxdwdm) {
        this.gxdwdm = gxdwdm;
    }

    public void setJingqid(String jingqid) {
        this.jingqid = jingqid;
    }

    public String getBaojr() {
        return baojr;
    }

    public void setBaojr(String baojr) {
        this.baojr = baojr;
    }

    public String getBaojrdh() {
        return baojrdh;
    }

    public void setBaojrdh(String baojrdh) {
        this.baojrdh = baojrdh;
    }

    public String getBaojdz() {
        return baojdz;
    }

    public void setBaojdz(String baojdz) {
        this.baojdz = baojdz;
    }

    public String getBaojnr() {
        return baojnr;
    }

    public void setBaojnr(String baojnr) {
        this.baojnr = baojnr;
    }

    public String getBaojsj() {
        return baojsj;
    }

    public void setBaojsj(String baojsj) {
        this.baojsj = baojsj;
    }

    public int getState() {
        return jingqzt;
    }

    public void setState(int state) {
        this.jingqzt = state;
    }

    public String getJiejh() {
        return jiejh;
    }

    public void setJiejh(String jiejh) {
        this.jiejh = jiejh;
    }


    public String getFknr() {
        return fknr;
    }

    public void setFknr(String fknr) {
        this.fknr = fknr;
    }

    public String getFilesPath() {
        return filesPath;
    }

    public void setFilesPath(String filesPath) {
        this.filesPath = filesPath;
    }

    public int getReadState() {
        return readState;
    }

    public void setReadState(int readState) {
        this.readState = readState;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getLongitude() {

        double longitude = 0;
        try {
            Double.valueOf(this.longitude);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        double latitude = 0;
        try {
            Double.valueOf(this.latitude);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAjlb() {
        return bjlb;
    }

    public void setAjlb(String ajlb) {
        this.bjlb = ajlb;
    }

    public String getAjlx() {
        return bjlx;
    }

    public void setAjlx(String ajlx) {
        this.bjlx = ajlx;
    }

    public String getAjxl() {
        return bjxl;
    }

    public void setAjxl(String ajxl) {
        this.bjxl = ajxl;
    }


    public String getAjlbbm() {
        return bjlbdm;
    }

    public void setAjlbbm(String ajlbbm) {
        this.bjlbdm = ajlbbm;
    }

    public String getAjlxbm() {
        return bjlxdm;
    }

    public void setAjlxbm(String ajlxbm) {
        this.bjlxdm = ajlxbm;
    }

    public String getAjxlbm() {
        return bjxldm;
    }

    public void setAjxlbm(String ajxlbm) {
        this.bjxldm = ajxlbm;
    }


    public String getZhipsj() {
        return zhipsj;
    }

    public void setZhipsj(String zhipsj) {
        this.zhipsj = zhipsj;
    }

    public String getChujsj() {
        return chujsj;
    }

    public void setChujsj(String chujsj) {
        this.chujsj = chujsj;
    }

    public String getDaodsj() {
        return daodsj;
    }

    public void setDaodsj(String daodsj) {
        this.daodsj = daodsj;
    }

    public String getFanksj() {
        return fanksj;
    }

    public void setFanksj(String fanksj) {
        this.fanksj = fanksj;
    }


    public String getJingyid() {
        return jingyid;
    }

    public void setJingyid(String jingyid) {
        this.jingyid = jingyid;
    }


    public int getJingqzt() {
        return jingqzt;
    }

    public void setJingqzt(int jingqzt) {
        this.jingqzt = jingqzt;
    }

    public String getBjlb() {
        return bjlb;
    }

    public void setBjlb(String bjlb) {
        this.bjlb = bjlb;
    }

    public String getBjlx() {
        return bjlx;
    }

    public void setBjlx(String bjlx) {
        this.bjlx = bjlx;
    }

    public String getBjxl() {
        return bjxl;
    }

    public void setBjxl(String bjxl) {
        this.bjxl = bjxl;
    }

    public String getBjlbdm() {
        return bjlbdm;
    }

    public void setBjlbdm(String bjlbdm) {
        this.bjlbdm = bjlbdm;
    }

    public String getBjlxdm() {
        return bjlxdm;
    }

    public void setBjlxdm(String bjlxdm) {
        this.bjlxdm = bjlxdm;
    }

    public String getBjxldm() {
        return bjxldm;
    }

    public void setBjxldm(String bjxldm) {
        this.bjxldm = bjxldm;
    }

    public String getZjg_cjjg() {
        return zjg_cjjg;
    }

    public void setZjg_cjjg(String zjg_cjjg) {
        this.zjg_cjjg = zjg_cjjg;
    }

    public String getZjg_cjlbbm() {
        return zjg_cjlbbm;
    }

    public void setZjg_cjlbbm(String zjg_cjlbbm) {
        this.zjg_cjlbbm = zjg_cjlbbm;
    }

    public String getZjg_cjlbmc() {
        return zjg_cjlbmc;
    }

    public void setZjg_cjlbmc(String zjg_cjlbmc) {
        this.zjg_cjlbmc = zjg_cjlbmc;
    }

    public String getZjg_cjr() {
        return zjg_cjr;
    }

    public void setZjg_cjr(String zjg_cjr) {
        this.zjg_cjr = zjg_cjr;
    }

    public String getZjg_cjsxbm() {
        return zjg_cjsxbm;
    }

    public void setZjg_cjsxbm(String zjg_cjsxbm) {
        this.zjg_cjsxbm = zjg_cjsxbm;
    }

    public String getZjg_cjsxmc() {
        return zjg_cjsxmc;
    }

    public void setZjg_cjsxmc(String zjg_cjsxmc) {
        this.zjg_cjsxmc = zjg_cjsxmc;
    }

    public String getZjg_fsdd() {
        return zjg_fsdd;
    }

    public void setZjg_fsdd(String zjg_fsdd) {
        this.zjg_fsdd = zjg_fsdd;
    }

    public String getZjg_sfcsbm() {
        return zjg_sfcsbm;
    }

    public void setZjg_sfcsbm(String zjg_sfcsbm) {
        this.zjg_sfcsbm = zjg_sfcsbm;
    }

    public String getZjg_sfcsmc() {
        return zjg_sfcsmc;
    }

    public void setZjg_sfcsmc(String zjg_sfcsmc) {
        this.zjg_sfcsmc = zjg_sfcsmc;
    }

    public String getZjg_sfsjsx() {
        return zjg_sfsjsx;
    }

    public void setZjg_sfsjsx(String zjg_sfsjsx) {
        this.zjg_sfsjsx = zjg_sfsjsx;
    }

    public String getZjg_sfsjxx() {
        return zjg_sfsjxx;
    }

    public void setZjg_sfsjxx(String zjg_sfsjxx) {
        this.zjg_sfsjxx = zjg_sfsjxx;
    }

    public String getZjg_ssqk() {
        return zjg_ssqk;
    }

    public void setZjg_ssqk(String zjg_ssqk) {
        this.zjg_ssqk = zjg_ssqk;
    }

    public String getZjg_tqqkbm() {
        return zjg_tqqkbm;
    }

    public void setZjg_tqqkbm(String zjg_tqqkbm) {
        this.zjg_tqqkbm = zjg_tqqkbm;
    }

    public String getZjg_tqqkmc() {
        return zjg_tqqkmc;
    }

    public void setZjg_tqqkmc(String zjg_tqqkmc) {
        this.zjg_tqqkmc = zjg_tqqkmc;
    }

    public EntityJingq() {
    }


    @Override
    public String toString() {
        return "EntityJingqing [jingqid=" + jingqid + ", baojr=" + baojr
                + ", baojrdh=" + baojrdh + ", baojdz=" + baojdz + ", baojnr="
                + baojnr + ", baojsj=" + baojsj + ", state=" + jingqzt
                + ", jiejh=" + jiejh + ", ajlb=" + bjlb + ", ajlx=" + bjlx
                + ", ajxl=" + bjxl + ", ajlbbm=" + bjlbdm + ", ajlxbm="
                + bjlxdm + ", ajxlbm=" + bjxldm + ", fknr=" + fknr
                + ", filesPath=" + filesPath + ", longitude=" + longitude
                + ", latitude=" + latitude + ", jingyid=" + jingyid + ", id="
                + id + ", readState=" + readState + ", time=" + time
                + ", zhipsj=" + zhipsj + ", chujsj=" + chujsj + ", daodsj="
                + daodsj + ", fanksj=" + fanksj + "]";
    }


    public String toJsonStr() {
        return GsonUtil.bean2Json(this);

    }

    public static EntityJingq paseStrToEntity(String jsonStr) {
        return GsonUtil.json2Bean(jsonStr, EntityJingq.class);
    }


}
