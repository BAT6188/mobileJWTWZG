package com.zondy.jwt.jwtmobile.view;

import com.zondy.jwt.jwtmobile.entity.EntityJingq;

/**
 * Created by ywj on 2017/1/12 0012.
 */

public interface IJingqDetailWithUnhandleView {
    public void reLoadJingqSuccess(EntityJingq jingq);
    public void reLoadJIngqFalied(Exception e);
    public void receiveJingqSuccess();
    public void receiveJIngqFalied(Exception e);
    public void arriveConfirmSuccess();
    public void arriveConfirmFailed(Exception e);
    public void rollbackJingqSuccess();
    public void rollbackJingqFailed(Exception e);
}
