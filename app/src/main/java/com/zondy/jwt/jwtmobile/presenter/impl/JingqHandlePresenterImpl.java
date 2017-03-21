package com.zondy.jwt.jwtmobile.presenter.impl;

import android.app.Activity;

import com.zondy.jwt.jwtmobile.callback.IAcceptJingqCallback;
import com.zondy.jwt.jwtmobile.callback.IArriveConfirmCallback;
import com.zondy.jwt.jwtmobile.callback.IConfirmReceiveMsgCallBack;
import com.zondy.jwt.jwtmobile.callback.IJingqHandleCallback;
import com.zondy.jwt.jwtmobile.callback.IJingqShangbaoCallback;
import com.zondy.jwt.jwtmobile.callback.IQueryAllJingqKuaisclTypesCallback;
import com.zondy.jwt.jwtmobile.callback.IQueryAllJingqTypesCallback;
import com.zondy.jwt.jwtmobile.callback.IQueryFeedbackRecordCallback;
import com.zondy.jwt.jwtmobile.callback.IQueryJinqDatasCallback;
import com.zondy.jwt.jwtmobile.callback.IQueryMySubmitRecordsCallback;
import com.zondy.jwt.jwtmobile.callback.IQueryXiectsDatasCallback;
import com.zondy.jwt.jwtmobile.callback.IReloadJingqCallback;
import com.zondy.jwt.jwtmobile.callback.IRollbackJingqCallback;
import com.zondy.jwt.jwtmobile.callback.IYaoAnFeedbackCallBack;
import com.zondy.jwt.jwtmobile.entity.EntityFeedbackRecord;
import com.zondy.jwt.jwtmobile.entity.EntityJingq;
import com.zondy.jwt.jwtmobile.entity.EntityTempJingq;
import com.zondy.jwt.jwtmobile.entity.EntityZD;
import com.zondy.jwt.jwtmobile.model.IJingqHandleModel;
import com.zondy.jwt.jwtmobile.model.impl.JingqHandleModelImpl;
import com.zondy.jwt.jwtmobile.presenter.IJingqHandlePresenter;
import com.zondy.jwt.jwtmobile.view.IFeedbackRecordsView;
import com.zondy.jwt.jwtmobile.view.IJingqDetailWithHandledView;
import com.zondy.jwt.jwtmobile.view.IJingqDetailWithUnhandleView;
import com.zondy.jwt.jwtmobile.view.IJingqImageEditView;
import com.zondy.jwt.jwtmobile.view.IJingqListView;
import com.zondy.jwt.jwtmobile.view.IJingqShangBaoView;
import com.zondy.jwt.jwtmobile.view.IJingqXieChaListView;
import com.zondy.jwt.jwtmobile.view.IJingqhandleView;
import com.zondy.jwt.jwtmobile.view.IMyJingqSubmitView;
import com.zondy.jwt.jwtmobile.view.IYaoAnFanKuiView;

import java.util.List;

/**
 * Created by ywj on 2017/1/12 0012.
 */

public class JingqHandlePresenterImpl implements IJingqHandlePresenter {

    IJingqListView jingqclView;//警情列表
    IJingqDetailWithUnhandleView jingqdcView;//警情到场
    IJingqhandleView jingqHandleView;//警情处理
    IJingqDetailWithHandledView jingqDetailWithHandledView;
    IJingqImageEditView jingqImageEditView;

    IJingqXieChaListView jingqXieChaListView;//协查推送列表
    IYaoAnFanKuiView yaoAnFanKuiView;//要案反馈页面
    IFeedbackRecordsView feedbackRecordsView;//反馈记录页面
    IJingqShangBaoView jingqShangBaoView;//警情上报页面
    IMyJingqSubmitView myJingqSubmitView;//我的上报

    IJingqHandleModel jingqHandleModel;

    public JingqHandlePresenterImpl(IJingqListView jingqclView) {
        this.jingqclView = jingqclView;
        jingqHandleModel = new JingqHandleModelImpl();
    }

    public JingqHandlePresenterImpl(IJingqDetailWithUnhandleView jingqdcView) {
        this.jingqdcView = jingqdcView;
        jingqHandleModel = new JingqHandleModelImpl();
    }

    public JingqHandlePresenterImpl(IJingqhandleView jingqHandleView) {
        this.jingqHandleView = jingqHandleView;
        jingqHandleModel = new JingqHandleModelImpl();
    }

    public JingqHandlePresenterImpl(IJingqDetailWithHandledView jingqDetailWithHandledView) {
        this.jingqDetailWithHandledView = jingqDetailWithHandledView;
        jingqHandleModel = new JingqHandleModelImpl();
    }

    public JingqHandlePresenterImpl(IJingqImageEditView jingqImageEditView) {
        this.jingqImageEditView = jingqImageEditView;
        jingqHandleModel = new JingqHandleModelImpl();
    }

    public JingqHandlePresenterImpl(IJingqXieChaListView jingqXieChaListView) {
        this.jingqXieChaListView = jingqXieChaListView;
        jingqHandleModel = new JingqHandleModelImpl();
    }


    public JingqHandlePresenterImpl(IYaoAnFanKuiView yaoAnFanKuiView) {
        this.yaoAnFanKuiView = yaoAnFanKuiView;
        jingqHandleModel = new JingqHandleModelImpl();
    }

    public JingqHandlePresenterImpl(IFeedbackRecordsView feedbackRecordsView) {
        this.feedbackRecordsView = feedbackRecordsView;
        jingqHandleModel = new JingqHandleModelImpl();
    }

    public JingqHandlePresenterImpl(IJingqShangBaoView jingqShangBaoView) {
        this.jingqShangBaoView = jingqShangBaoView;
        jingqHandleModel = new JingqHandleModelImpl();
    }

    public JingqHandlePresenterImpl(IMyJingqSubmitView myJingqSubmitView) {
        this.myJingqSubmitView = myJingqSubmitView;
        jingqHandleModel = new JingqHandleModelImpl();
    }

    /**
     * 利用警号和设备id查询大体警情列表数据
     * @param jh
     * @param simid
     */
    @Override
    public void queryJingqDatas(String jh, String simid) {
        jingqHandleModel.queryJingqDatas(jh, simid, new IQueryJinqDatasCallback() {

            @Override
            public void onSuccess(List<EntityJingq> jignqDatas) {
                jingqclView.onGetJingqDatasSuccess(jignqDatas);
            }

            @Override
            public void onFailed(Exception exception) {

                jingqclView.onGetJingqDatasFailed(exception);
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                if (progress < 100) {
                    jingqclView.showLoadingProgress(true);
                } else {
                    jingqclView.showLoadingProgress(false);
                }
            }
        });
    }

    /**
     * 利用警情id重新加载警情（未处理）
     * @param jingqid
     * @param jh
     * @param simid
     */
    @Override
    public void reloadJingqWithJingqUnhandle(String jingqid, String jh, String simid) {
        jingqHandleModel.reloadJingq(jingqid, jh, simid, new IReloadJingqCallback() {
            @Override
            public void loadJingqSuccess(EntityJingq jingq) {
                jingqdcView.reLoadJingqSuccess(jingq);
            }

            @Override
            public void loadJingqFailed(Exception exception) {

                jingqdcView.reLoadJIngqFalied(exception);
            }
        });
    }

    /**
     *利用警情id重新加载警情（已处理）
     * @param jingqid
     * @param jh
     * @param simid
     */
    @Override
    public void reloadJingqWithJingqHandled(String jingqid, String jh, String simid) {
        jingqHandleModel.reloadJingq(jingqid, jh, simid, new IReloadJingqCallback() {
            @Override
            public void loadJingqSuccess(EntityJingq jingq) {
                jingqDetailWithHandledView.loadJingqSuccess(jingq);
            }

            @Override
            public void loadJingqFailed(Exception exception) {

                jingqDetailWithHandledView.loadJIngqFalied(exception);
            }
        });
    }

    /**
     * 接警
     * @param jingqid
     * @param carid
     * @param jh
     * @param simid
     */
    @Override
    public void acceptJingq(String jingqid, String carid, String jh, String simid) {
        jingqHandleModel.acceptJingq(jingqid, carid, jh
                , simid, new IAcceptJingqCallback() {
                    @Override
                    public void acceptJingqSuccess() {
                        jingqdcView.receiveJingqSuccess();
                    }

                    @Override
                    public void acceptJingqFailed(Exception exception) {
                        jingqdcView.receiveJIngqFalied(exception);
                    }
                });
    }

    /**
     * 回退警情
     * @param jingqid
     * @param jh
     * @param simid
     */
    @Override
    public void rollbackJingq(String jingqid, String jh, String simid) {
        jingqHandleModel.rollbackJingq(jingqid, jh, simid, new IRollbackJingqCallback() {
            @Override
            public void rollbackJingqSuccess() {
                jingqdcView.rollbackJingqSuccess();
            }

            @Override
            public void rollbackJingqFailed(Exception exception) {

                jingqdcView.rollbackJingqFailed(exception);
            }
        });
    }

    /**
     * 到场确认
     * @param jingyid
     * @param jingqid
     * @param longitude
     * @param latitude
     * @param jh
     * @param simid
     */
    @Override
    public void arriveConfirm(String jingyid, String jingqid, String longitude, String latitude, String jh, String simid) {

        jingqHandleModel.arriveConfirm(jingyid, jingqid, longitude, latitude, jh
                , simid, new IArriveConfirmCallback() {
                    @Override
                    public void arriveSuccess() {
                        jingqdcView.arriveConfirmSuccess();
                    }

                    @Override
                    public void arriveFailed(Exception exception) {
                        jingqdcView.arriveConfirmFailed(exception);
                    }
                });
    }

    /**
     * 警情到场处理（提交警情）
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
    @Override
    public void jingqHandle(String jingyid, String jingqid, String chuljg, String bjlb, String bjlx, String bjxl, String chuljgms, String filesPath, String jh, String simid) {
        jingqHandleModel.submitJingq(jingyid, jingqid, chuljg, bjlb, bjlx, bjxl, chuljgms, filesPath, jh, simid, new IJingqHandleCallback() {
            @Override
            public void jingqHandleSuccess() {
                jingqHandleView.handleJingqSuccess();
            }

            @Override
            public void jingqHandleFailed(Exception exception) {
                jingqHandleView.handleJingqFalied(exception);
            }
        });
    }

    /**
     * 查询所有警情类型
     * @param jh
     * @param simid
     */
    @Override
    public void queryAllJingqTypes(String jh, String simid, final Activity act) {
        jingqHandleModel.queryAllJingqTypes(jh, simid, new IQueryAllJingqTypesCallback() {
            @Override
            public void jingqHandleSuccess(List<EntityZD> jingqTypes) {
                if(act instanceof IJingqShangBaoView){
                    jingqShangBaoView.updateJingqTypes(jingqTypes);
                }else{
                jingqHandleView.updateJingqTypes(jingqTypes);
                }

            }

            @Override
            public void jingqHandleFailed(Exception exception) {
                if(act instanceof IJingqShangBaoView){
                    jingqShangBaoView.updateJingqTypes(null);
                }else{
                jingqHandleView.updateJingqTypes(null);
                }
            }
        });
    }

    /**
     * 查询所有快速处理类型
     * @param jh
     * @param simid
     */
    @Override
    public void queryAllJingqKuaisclTypes(String jh, String simid) {
        jingqHandleModel.queryAllJingqKuaisclTypes(jh, simid, new IQueryAllJingqKuaisclTypesCallback() {
            @Override
            public void queryJingqKuaisclSuccess(List<EntityZD> jingqKuaisclTypes) {
                jingqHandleView.updateJingqKuaisclTypes(jingqKuaisclTypes);
            }

            @Override
            public void queryJingqKuaisclFailed(Exception exception) {
            }

        });
    }

    /**
     * 查询协查推送列表
     * @param jh
     * @param simid
     */
    @Override
    public void queryXiectsDatas(String jh, String simid,String zzjgdm) {
      jingqHandleModel.queryXiectsDatas(jh, simid, zzjgdm,new IQueryXiectsDatasCallback() {
          @Override
          public void onSuccess(List<EntityJingq> datas) {
              jingqXieChaListView.onGetJingqXieChaDatasSuccess(datas);
          }

          @Override
          public void onFailed(Exception exception) {
              jingqXieChaListView.onGetJingqXieChaDatasFailed(exception);
          }

          @Override
          public void inProgress(float progress, long total, int id) {
              if (progress < 100) {
                  jingqXieChaListView.showLoadingProgress(true);
              } else {
                  jingqXieChaListView.showLoadingProgress(false);
              }
          }
      });
    }

    /**
     * 推送消息接受确认
     * @param jingqid
     * @param jh
     * @param simid
     */
    @Override
    public void confirmReceiveMsg(String jingqid, String jh, String simid) {
        jingqHandleModel.confirmReceiveMsg(jingqid, jh, simid, new IConfirmReceiveMsgCallBack() {
            @Override
            public void confirmReceiveMsgSuccess() {
                jingqXieChaListView.onconfirmReceiveMsgSuccess();
            }

            @Override
            public void confirmReceiveMsgFailed(Exception exception) {

                jingqXieChaListView.onconfirmReceiveMsgFailed(exception);
            }
        });
    }

    /**
     * 反馈警情
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
    @Override
    public void feedbackJingq(String jingyid, String jingqid, String bjlb, String bjlx, String bjxl, String fknr, String fksj, String filesPath, String jh, String simid) {
        jingqHandleModel.FeedbackJingq(jingyid, jingqid, bjlb, bjlx, bjxl, fknr, fksj, filesPath, jh, simid, new IYaoAnFeedbackCallBack() {
            @Override
            public void yaoAnFeedbackSuccess() {
                yaoAnFanKuiView.feedBackSuccess();
            }

            @Override
            public void yaoAnFeedbackFailed(Exception e) {
                yaoAnFanKuiView.feedBackFailed(e);
            }
        });


    }

    /**
     * 查询警员的反馈记录
     * @param jh
     * @param simid

     */
    @Override
    public void queryFeedbackRecords(String jh, String simid) {
        jingqHandleModel.queryFeedbackRecord(jh, simid, new IQueryFeedbackRecordCallback() {
            @Override
            public void onSuccess(List<EntityFeedbackRecord> datas) {

                feedbackRecordsView.onGetFeedbackRecordsSuccess(datas);
            }

            @Override
            public void onFailed(Exception exception) {

                feedbackRecordsView.onGetFeedbackRecordsFailed(exception);
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                if(progress<100){
                    feedbackRecordsView.showLoadingProgress(true);
                }else{
                    feedbackRecordsView.showLoadingProgress(false);
                }
            }
        });

    }

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
    @Override
    public void shangbaoJingq(String jh, String simid, String bjrxm, String bjrjh, String bjsj,
                              String bjrdh, String bjdz, String baojnr, String bjlb, String bjlx, String bjxl,
                              String longitude, String latitude, String filesPath,final Activity act) {
        jingqHandleModel.shangbaoJingq(jh, simid, bjrxm, bjrjh, bjsj, bjrdh, bjdz, baojnr, bjlb,
                bjlx, bjxl, longitude, latitude, filesPath, new IJingqShangbaoCallback() {
                    @Override
                    public void jingqShangbaoSuccess() {
                        if(act instanceof IMyJingqSubmitView){
                            myJingqSubmitView.handleJingqSuccess();
                        }
                        else{
                        jingqShangBaoView.handleJingqSuccess();
                        }
                    }

                    @Override
                    public void jingqShangbaoFailed(Exception exception) {

                        if(act instanceof IMyJingqSubmitView){
                            myJingqSubmitView.handleJingqFailed(exception);
                        }else{
                        jingqShangBaoView.handleJingqFalied(exception);
                        }
                    }
                });
    }

    /**
     * 查询上报记录
     * @param jh
     * @param simid
     */
    @Override
    public void queryMySubmitRecords(String jh, String simid) {
        jingqHandleModel.queryMySubmitRecords(jh, simid, new IQueryMySubmitRecordsCallback() {
            @Override
            public void onSuccess(List<EntityTempJingq> datas) {
                myJingqSubmitView.onGetJingqSubmitDatasSuccess(datas);
            }

            @Override
            public void onFailed(Exception exception) {
                myJingqSubmitView.onGetJingqSubmitDatasFailed(exception);
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                if(progress<100){
                    myJingqSubmitView.showLoadingProgress(true);
                }else{
                    myJingqSubmitView.showLoadingProgress(false);
                }
            }
        });
    }





}
