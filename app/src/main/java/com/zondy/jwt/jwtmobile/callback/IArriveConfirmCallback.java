package com.zondy.jwt.jwtmobile.callback;

/**
 * Created by ywj on 2017/1/12 0012.
 */

public interface IArriveConfirmCallback {
    public void arriveSuccess();
    public void arriveFailed(Exception exception);
}
