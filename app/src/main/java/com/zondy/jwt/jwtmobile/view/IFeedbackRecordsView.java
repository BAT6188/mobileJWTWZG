package com.zondy.jwt.jwtmobile.view;

import com.zondy.jwt.jwtmobile.entity.EntityFeedbackRecord;
import com.zondy.jwt.jwtmobile.entity.EntityJingq;

import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */
public interface IFeedbackRecordsView {
   public void onGetFeedbackRecordsSuccess(List<EntityFeedbackRecord> feedbackRecords);
    public void onGetFeedbackRecordsFailed(Exception e);
    public void showLoadingProgress(boolean isShow);

}
