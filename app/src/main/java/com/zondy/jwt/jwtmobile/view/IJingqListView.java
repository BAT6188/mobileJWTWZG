package com.zondy.jwt.jwtmobile.view;

import com.zondy.jwt.jwtmobile.entity.EntityJingq;

import java.util.List;

/**
 * Created by ywj on 2017/1/12 0012.
 */

public interface IJingqListView {
    public void onGetJingqDatasSuccess(List<EntityJingq> jingqDatas);
    public void onGetJingqDatasFailed(Exception exception);
    public void showLoadingProgress(boolean isShow);
}
