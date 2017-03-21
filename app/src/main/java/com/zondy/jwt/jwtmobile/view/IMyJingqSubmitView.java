package com.zondy.jwt.jwtmobile.view;

import com.zondy.jwt.jwtmobile.entity.EntityJingq;
import com.zondy.jwt.jwtmobile.entity.EntityTempJingq;

import java.util.List;

/**
 * Created by Administrator on 2017/3/10.
 */
public interface IMyJingqSubmitView {
    public void onGetJingqSubmitDatasSuccess(List<EntityTempJingq> jingqSubmitDatas);
    public void onGetJingqSubmitDatasFailed(Exception exception);
    public void showLoadingProgress(boolean isShow);

    /**
     * 再次提交
     */
    public void handleJingqSuccess();
    public void handleJingqFailed(Exception exception);
}
