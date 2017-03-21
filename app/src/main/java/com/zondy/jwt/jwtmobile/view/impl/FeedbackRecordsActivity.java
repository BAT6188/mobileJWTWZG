package com.zondy.jwt.jwtmobile.view.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zondy.jwt.jwtmobile.R;
import com.zondy.jwt.jwtmobile.base.BaseActivity;
import com.zondy.jwt.jwtmobile.entity.EntityFeedbackRecord;
import com.zondy.jwt.jwtmobile.entity.EntityJingq;
import com.zondy.jwt.jwtmobile.entity.EntityUser;
import com.zondy.jwt.jwtmobile.presenter.IJingqHandlePresenter;
import com.zondy.jwt.jwtmobile.presenter.impl.JingqHandlePresenterImpl;
import com.zondy.jwt.jwtmobile.util.CommonUtil;
import com.zondy.jwt.jwtmobile.util.SharedTool;
import com.zondy.jwt.jwtmobile.util.ToastTool;
import com.zondy.jwt.jwtmobile.view.IFeedbackRecordsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/3/7.
 */
public class FeedbackRecordsActivity extends BaseActivity implements IFeedbackRecordsView {
    @BindView(R.id.rl_feedbackrecordsdatas)
    XRecyclerView rlFeedbackRecords;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    IJingqHandlePresenter jingqHandlePresenter;
    List<EntityFeedbackRecord> feedbackRecordsDatas;
    CommonAdapter<EntityFeedbackRecord> adapterfeedbackRecordsList;
    EntityUser user;
//    EntityJingq entityJingq;//上一活动传入的警情（复制上一活动的jingqid）
    EntityFeedbackRecord selectedRecord;//选中的记录



    @Override
    public int setCustomContentViewResourceId() {
        return R.layout.activity_feedback_records;
    }

    public static Intent createIntent(Context context, EntityJingq jingqxiecha){
        Intent intent=new Intent(context,FeedbackRecordsActivity.class);
        intent.putExtra("entityXieCha",jingqxiecha);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParam();
        initView();
        initOperator();
    }
    @Override
    protected void onResume() {
        super.onResume();
        queryFeedbackRecordsDatas();//查询反馈记录列表
    }
    public void initParam() {
//        entityJingq = (EntityJingq) getIntent().getSerializableExtra("entityXieCha");
        jingqHandlePresenter=new JingqHandlePresenterImpl(this);
        user = SharedTool.getInstance().getUserInfo(context);
        feedbackRecordsDatas=new ArrayList<EntityFeedbackRecord>();

        adapterfeedbackRecordsList=new CommonAdapter<EntityFeedbackRecord>(context,R.layout.item_feedback_records,feedbackRecordsDatas) {
            @Override
            protected void convert(ViewHolder holder, EntityFeedbackRecord entityJingq, int position) {
                if(entityJingq.getFeedbacktype()==0){
                    holder.setImageResource(R.id.iv_feedback_to,R.drawable.ic_to_center);

                }else{
                    holder.setImageResource(R.id.iv_feedback_to,R.drawable.ic_to_police);
                }

                holder.setText(R.id.tv_fknr,entityJingq.getFknr());
                holder.setText(R.id.tv_fksj,entityJingq.getFksj());

            }
        };
    }

    public void initView() {
        initActionBar(toolbar,tvTitle,"反馈记录");
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        rlFeedbackRecords.setLayoutManager(linearLayoutManager);
        rlFeedbackRecords.setRefreshProgressStyle(ProgressStyle.Pacman);
        rlFeedbackRecords.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        rlFeedbackRecords.setPullRefreshEnabled(true);
        rlFeedbackRecords.setLoadingMoreEnabled(true);
        rlFeedbackRecords.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                String jh = user.getUserName();
                String simid = CommonUtil.getDeviceId(context);
//                String jingqid=entityJingq.getJingqid();
                jingqHandlePresenter.queryFeedbackRecords(jh,simid);//查询反馈记录列表
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        rlFeedbackRecords.loadMoreComplete();

                    }
                },2000);

            }
        });
        rlFeedbackRecords.setAdapter(adapterfeedbackRecordsList);
        rlFeedbackRecords.addItemDecoration(new SimpleDividerDecoration(context));//添加分割线
        adapterfeedbackRecordsList.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                selectedRecord=feedbackRecordsDatas.get(position-1);//从反馈记录集合中得到选中的记录
                startActivity(RecordDetailActivity.createIntent(context,selectedRecord));//进入记录详情页(携带选中的记录)

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

    public void queryFeedbackRecordsDatas(){
        String jh = user.getUserName();
        String simid = CommonUtil.getDeviceId(context);
//        String jingqid=entityJingq.getJingqid();
        jingqHandlePresenter.queryFeedbackRecords(jh,simid);//查询反馈记录列表
    }
    public void initOperator() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onGetFeedbackRecordsSuccess(final List<EntityFeedbackRecord> feedbackRecords) {
        recyclerViewLoadFinish();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(feedbackRecords!=null){
                    FeedbackRecordsActivity.this.feedbackRecordsDatas.clear();
                    FeedbackRecordsActivity.this.feedbackRecordsDatas.addAll(feedbackRecords);
                    adapterfeedbackRecordsList.notifyDataSetChanged();
                }
            }
        });

    }

    public void recyclerViewLoadFinish() {
        rlFeedbackRecords.refreshComplete();
        rlFeedbackRecords.loadMoreComplete();

    }

    @Override
    public void onGetFeedbackRecordsFailed(Exception e) {
        recyclerViewLoadFinish();
        ToastTool.getInstance().shortLength(context, e.getMessage(), true);
    }

    @Override
    public void showLoadingProgress(boolean isShow) {
        if (isShow) {
            showLoadingDialog();
        } else {
            dismissLoadingDialog();
        }
    }
}
