package com.zondy.jwt.jwtmobile.model;

import com.zondy.jwt.jwtmobile.callback.IAcceptJingqCallback;
import com.zondy.jwt.jwtmobile.callback.IArriveConfirmCallback;
import com.zondy.jwt.jwtmobile.callback.IJingqHandleCallback;
import com.zondy.jwt.jwtmobile.callback.IQueryAllJingqKuaisclTypesCallback;
import com.zondy.jwt.jwtmobile.callback.IQueryAllJingqTypesCallback;
import com.zondy.jwt.jwtmobile.callback.IQueryJinqDatasCallback;
import com.zondy.jwt.jwtmobile.callback.IReloadJingqCallback;
import com.zondy.jwt.jwtmobile.callback.IRollbackJingqCallback;

/**
 * Created by ywj on 2017/1/12 0012.
 */

public interface IJingqHandleModel {
    /**
     * 查询警情列表
     * @param jh
     * @param simid
     * @param queryJingqDatasCallback
     */
    public void queryJingqDatas(String jh, String simid, IQueryJinqDatasCallback queryJingqDatasCallback);

    /**
     * 根据警情id查询警情
     * @param jingqid
     * @param jh
     * @param simid
     * @param reloadJingqCallback
     */
    public void reloadJingq(String jingqid, String jh, String simid, IReloadJingqCallback reloadJingqCallback);

    /**
     * 接收警情
     * @param jingqid
     * @param carid
     * @param jh
     * @param simid
     * @param acceptJingqCallback
     */
    public void acceptJingq(String jingqid, String carid, String jh, String simid, IAcceptJingqCallback acceptJingqCallback);

    /**
     * 回退警情
     * @param jingqid
     * @param jh
     * @param simid
     * @param rollbackJingqCallback
     */
    public void rollbackJingq(String jingqid, String jh, String simid, IRollbackJingqCallback rollbackJingqCallback);

    /**
     * 警情确认到场
     * @param jingyid
     * @param jingqid
     * @param longitude
     * @param latitude
     * @param jh
     * @param simid
     * @param arriveConfirmCallback
     */
    public void arriveConfirm(String jingyid, String jingqid, String longitude, String latitude, String jh, String simid, IArriveConfirmCallback arriveConfirmCallback);

    /**
     *  处理警情
     * @param jingyid 警员警号
     * @param jingqid 警情编号
     * @param chuljg 处理结果
     * @param bjlb 报警类别
     * @param bjlx 报警类型
     * @param bjxl 报警细类
     * @param chuljgms 处理结果描述
     * @param filesPath 多媒体路径全名字串
     * @param jh 警号
     * @param simid 客户端设备串号
     * @param jingqHandleCallback 警情处理回调接口
     */
    public void submitJingq(String jingyid, String jingqid, String chuljg, String bjlb, String bjlx, String bjxl, String chuljgms, String filesPath, String jh, String simid, IJingqHandleCallback jingqHandleCallback);

    /**
     * 获取所有的警情类型
     * @param jh
     * @param simid
     */
    public void queryAllJingqTypes(String jh, String simid, IQueryAllJingqTypesCallback queryAllJingqTypesCallback);
        public void queryAllJingqKuaisclTypes(String jh, String simid, IQueryAllJingqKuaisclTypesCallback queryAllJingqKuaisclTypesCallback);
}
