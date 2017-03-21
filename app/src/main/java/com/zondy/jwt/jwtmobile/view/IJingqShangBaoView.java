package com.zondy.jwt.jwtmobile.view;

import com.zondy.jwt.jwtmobile.entity.EntityZD;

import java.util.List;

/**
 * Created by Administrator on 2017/3/9.
 */
public interface IJingqShangBaoView {

    public void handleJingqSuccess();
    public void handleJingqFalied(Exception e);
    public void updateJingqTypes(List<EntityZD> jignqtypes);
}
