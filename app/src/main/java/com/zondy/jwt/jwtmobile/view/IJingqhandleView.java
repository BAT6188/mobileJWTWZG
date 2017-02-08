package com.zondy.jwt.jwtmobile.view;

import com.zondy.jwt.jwtmobile.entity.EntityZD;

import java.util.List;

/**
 * Created by ywj on 2017/1/12 0012.
 */

public interface IJingqhandleView {
    public void handleJingqSuccess();
    public void handleJingqFalied(Exception e);
    public void updateJingqTypes(List<EntityZD> jignqtypes);
    public void updateJingqKuaisclTypes(List<EntityZD> jignqKuaiscltypes);
}
