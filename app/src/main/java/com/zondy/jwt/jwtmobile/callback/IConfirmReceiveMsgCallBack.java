package com.zondy.jwt.jwtmobile.callback;

/**
 * Created by Administrator on 2017/3/3.
 */
public interface IConfirmReceiveMsgCallBack {
    public void confirmReceiveMsgSuccess();
    public void confirmReceiveMsgFailed(Exception exception);
}
