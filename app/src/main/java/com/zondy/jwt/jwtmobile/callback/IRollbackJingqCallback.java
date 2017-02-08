package com.zondy.jwt.jwtmobile.callback;

/**
 * Created by ywj on 2017/1/12 0012.
 */

public interface IRollbackJingqCallback {
    public void rollbackJingqSuccess();
    public void rollbackJingqFailed(Exception exception);
}
