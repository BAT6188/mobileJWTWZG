package com.zondy.jwt.jwtmobile.callback;

import java.util.List;

/**
 * Created by ywj on 2017/1/18 0018.
 */

public interface IBaseModelCallback<T> {
    public void onSuccess(List<T> datas);
    public void onFailed(Exception exception);
    public void inProgress(float progress, long total, int id);
}
