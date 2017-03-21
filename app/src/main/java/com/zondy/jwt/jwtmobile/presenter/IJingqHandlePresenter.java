package com.zondy.jwt.jwtmobile.presenter;

import android.app.Activity;

/**
 * Created by ywj on 2017/1/12 0012.
 */

public interface IJingqHandlePresenter {

    /**
     * 查询警情列表
     * @param jh
     * @param simid
     */
    public void queryJingqDatas(String jh, String simid);

    /**
     * 未处理警情界面,根据id加载警情
     * @param jingqid
     * @param jh
     * @param simid
     */
    public void reloadJingqWithJingqUnhandle(String jingqid, String jh, String simid);

    /**
     * 已处理警情界面,根据id加载警情
     * @param jingqid
     * @param jh
     * @param simid
     */
    public void reloadJingqWithJingqHandled(String jingqid, String jh, String simid);

    /**
     * 接收警情
     * @param jingqid
     * @param carid
     * @param jh
     * @param simid
     */
    public void acceptJingq(String jingqid, String carid, String jh, String simid);

    /**
     * 回退警情
     * @param jingqid
     * @param jh
     * @param simid
     */
    public void rollbackJingq(String jingqid, String jh, String simid);

    /**
     * 警情到场
     * @param jingyid
     * @param jingqid
     * @param longitude
     * @param latitude
     * @param jh
     * @param simid
     */
    public void arriveConfirm(String jingyid, String jingqid, String longitude, String latitude, String jh, String simid);


    /**
     * 处理警情
     * @param jingyid
     * @param jingqid
     * @param chuljg
     * @param bjlb
     * @param bjlx
     * @param bjxl
     * @param chuljgms
     * @param filesPath
     * @param jh
     * @param simid
     */
    public void jingqHandle(String jingyid, String jingqid, String chuljg, String bjlb, String bjlx, String bjxl, String chuljgms, String filesPath, String jh, String simid);

    /**
     * 查询所有的警情类型
     * @param jh
     * @param simid
     */
    public void queryAllJingqTypes(String jh, String simid, Activity act);

    /**
     * 查询所有的处警快速选择
     * @param jh
     * @param simid
     */
    public void queryAllJingqKuaisclTypes(String jh, String simid);

    /**
     * 查询协查推送列表
     * @param jh
     * @param simid
     */
    public void queryXiectsDatas(String jh, String simid,String zzjgdm);

    /**
     * 推送消息接受确认
     * @param jingqid
     * @param jh
     * @param simid
     */
    public void confirmReceiveMsg(String jingqid, String jh, String simid);


    /**
     * 民警反馈警情
     * @param jingyid
     * @param jingqid
     * @param bjlb
     * @param bjlx
     * @param bjxl
     * @param fknr
     * @param fksj
     * @param filesPath
     * @param jh
     * @param simid
     */
    public void feedbackJingq(String jingyid, String jingqid, String bjlb, String bjlx, String bjxl,
                              String fknr, String fksj, String filesPath, String jh, String simid);

    /**
     * 查询民警反馈记录
     * @param jh
     * @param simid

     */
    public void queryFeedbackRecords(String jh, String simid);

    /**
     * 上报警情
     * @param jh
     * @param simid
     * @param bjrxm
     * @param bjrjh
     * @param bjsj
     * @param bjrdh
     * @param bjdz
     * @param baojnr
     * @param bjlb
     * @param bjlx
     * @param bjxl
     * @param longitude
     * @param latitude
     * @param filesPath
     */
    public void shangbaoJingq(String jh, String simid, String bjrxm, String bjrjh, String bjsj, String bjrdh,
                              String bjdz, String baojnr, String bjlb, String bjlx, String bjxl, String longitude,
                              String latitude, String filesPath,Activity act);

    /**
     * 查询我的上报记录
     * @param jh
     * @param simid
     */
    public void queryMySubmitRecords(String jh, String simid);


}
