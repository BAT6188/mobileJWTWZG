package com.zondy.jwt.jwtmobile.presenter.impl;

import com.zondy.jwt.jwtmobile.callback.IAcceptJingqCallback;
import com.zondy.jwt.jwtmobile.callback.IArriveConfirmCallback;
import com.zondy.jwt.jwtmobile.callback.IJingqHandleCallback;
import com.zondy.jwt.jwtmobile.callback.IQueryAllJingqKuaisclTypesCallback;
import com.zondy.jwt.jwtmobile.callback.IQueryAllJingqTypesCallback;
import com.zondy.jwt.jwtmobile.callback.IQueryJinqDatasCallback;
import com.zondy.jwt.jwtmobile.callback.IReloadJingqCallback;
import com.zondy.jwt.jwtmobile.callback.IRollbackJingqCallback;
import com.zondy.jwt.jwtmobile.entity.EntityJingq;
import com.zondy.jwt.jwtmobile.entity.EntityZD;
import com.zondy.jwt.jwtmobile.model.IJingqHandleModel;
import com.zondy.jwt.jwtmobile.model.impl.JingqHandleModelImpl;
import com.zondy.jwt.jwtmobile.presenter.IJingqHandlePresenter;
import com.zondy.jwt.jwtmobile.view.IJingqDetailWithHandledView;
import com.zondy.jwt.jwtmobile.view.IJingqDetailWithUnhandleView;
import com.zondy.jwt.jwtmobile.view.IJingqImageEditView;
import com.zondy.jwt.jwtmobile.view.IJingqListView;
import com.zondy.jwt.jwtmobile.view.IJingqhandleView;

import java.util.List;

/**
 * Created by ywj on 2017/1/12 0012.
 */

public class JingqHandlePresenterImpl implements IJingqHandlePresenter {

    IJingqListView jingqclView;
    IJingqDetailWithUnhandleView jingqdcView;
    IJingqhandleView jingqHandleView;
    IJingqDetailWithHandledView jingqDetailWithHandledView;
    IJingqImageEditView jingqImageEditView;
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
    }@Override
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

    @Override
    public void queryAllJingqTypes(String jh, String simid) {
        jingqHandleModel.queryAllJingqTypes(jh, simid, new IQueryAllJingqTypesCallback() {
            @Override
            public void jingqHandleSuccess(List<EntityZD> jingqTypes) {
                jingqHandleView.updateJingqTypes(jingqTypes);
            }

            @Override
            public void jingqHandleFailed(Exception exception) {
                jingqHandleView.updateJingqTypes(null);
            }
        });
    }

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


}
