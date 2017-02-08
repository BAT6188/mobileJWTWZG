package com.zondy.jwt.jwtmobile.callback;

import com.zondy.jwt.jwtmobile.entity.EntityZD;

import java.util.List;

/**
 * Created by ywj on 2017/1/12 0012.
 */

public interface IQueryAllJingqKuaisclTypesCallback {
    public void queryJingqKuaisclSuccess(List<EntityZD> jingqKuaisclTypes);
    public void queryJingqKuaisclFailed(Exception exception);
}
