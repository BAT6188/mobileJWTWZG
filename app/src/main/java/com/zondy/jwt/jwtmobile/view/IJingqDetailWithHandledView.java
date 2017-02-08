package com.zondy.jwt.jwtmobile.view;

import com.zondy.jwt.jwtmobile.entity.EntityJingq;

/**
 * Created by ywj on 2017/1/12 0012.
 */

public interface IJingqDetailWithHandledView {
    public void loadJingqSuccess(EntityJingq jingq);
    public void loadJIngqFalied(Exception e);
}
