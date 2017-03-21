package com.zondy.jwt.jwtmobile.view;

import com.zondy.jwt.jwtmobile.entity.EntityJingq;

import java.util.List;

/**
 * Created by Administrator on 2017/2/28.
 */
public interface IJingqXieChaListView {
    public void onGetJingqXieChaDatasSuccess(List<EntityJingq> jingqXieChaDatas);
    public void onGetJingqXieChaDatasFailed(Exception exception);
    public void showLoadingProgress(boolean isShow);
    public void onconfirmReceiveMsgSuccess();//已接收推送
    public void onconfirmReceiveMsgFailed(Exception exception);//未接受推送
}
