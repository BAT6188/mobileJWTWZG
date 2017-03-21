package com.zondy.jwt.jwtmobile.view.impl;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zondy.jwt.jwtmobile.R;
import com.zondy.jwt.jwtmobile.base.BaseActivity;
import com.zondy.jwt.jwtmobile.entity.EntityJingq;
import com.zondy.jwt.jwtmobile.entity.EntityTempJingq;
import com.zondy.jwt.jwtmobile.entity.EntityUser;
import com.zondy.jwt.jwtmobile.presenter.IJingqHandlePresenter;
import com.zondy.jwt.jwtmobile.presenter.impl.JingqHandlePresenterImpl;
import com.zondy.jwt.jwtmobile.util.CommonUtil;
import com.zondy.jwt.jwtmobile.util.SharedTool;
import com.zondy.jwt.jwtmobile.util.ToastTool;
import com.zondy.jwt.jwtmobile.view.IMyJingqSubmitView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/10.
 */
public class MyJingqSubmitActivity extends BaseActivity implements IMyJingqSubmitView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_jingqsubmitdatas)
    XRecyclerView rlJingqSubmitDatas;
    ImageView ivReceiveState;

    IJingqHandlePresenter jingqHandlePresenter;
    List<EntityTempJingq> jingqSubmitDatas;
    CommonAdapter<EntityTempJingq> adapterJingqSubmitList;
    EntityUser user;
    EntityTempJingq jingqsubmit;//选中的上报警情

    public List<ImageView> ivChays=new ArrayList<>();//选中的子项imageView集合


    @Override
    public int setCustomContentViewResourceId() {
        return R.layout.activity_mysubmit_records;
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, MyJingqSubmitActivity.class);
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
        queryMySubmitDatas();//查询协查列表
    }

    public void initParam() {
        jingqHandlePresenter=new JingqHandlePresenterImpl(this);
        user = SharedTool.getInstance().getUserInfo(context);
        jingqSubmitDatas=new ArrayList<EntityTempJingq>();

        adapterJingqSubmitList=new CommonAdapter<EntityTempJingq>(context,R.layout.item_jingq_mysubmit,jingqSubmitDatas) {
            @Override
            protected void convert(ViewHolder holder, final EntityTempJingq entityTempJingq, int position) {
                holder.setText(R.id.tv_jingqlb,entityTempJingq.getBjlb());//案件类别
                holder.setText(R.id.tv_dizhi,entityTempJingq.getBjdz());//报警地址
                holder.setText(R.id.tv_neirong,entityTempJingq.getBaojnr());//报警内容
                switch (entityTempJingq.getShenhezt()){
                    case 0:
                        holder.setText(R.id.tv_shijian,entityTempJingq.getBjsj());
                        holder.setText(R.id.tv_shenhezt,"未审核");
                        holder.setTextColor(R.id.tv_shenhezt,0xffE9691E);
                        holder.setText(R.id.tv_phone,entityTempJingq.getPhoneWhenUncheck());
                        //未审核时拨号按钮点击
                        holder.setOnClickListener(R.id.tv_call, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+entityTempJingq.getPhoneWhenUncheck()));
                                startActivity(intent);
                            }
                        });

                        holder.setVisible(R.id.rl_reason,false);
                        holder.setVisible(R.id.rl_reason1,false);
                        holder.setVisible(R.id.rl_again,false);
                        break;
                    case 1:
                        holder.setText(R.id.tv_shijian,entityTempJingq.getShenhetgsj());
                        holder.setText(R.id.tv_shenhezt,"审核通过");
                        holder.setTextColor(R.id.tv_shenhezt,0xff23B539);

                        holder.setVisible(R.id.rl_phone,false);
                        holder.setVisible(R.id.rl_reason,false);
                        holder.setVisible(R.id.rl_reason1,false);
                        holder.setVisible(R.id.rl_again,false);
                        break;
                    case 2:
                        holder.setText(R.id.tv_shijian,entityTempJingq.getBjsj());
                        holder.setText(R.id.tv_shenhezt,"审核未通过");
                        holder.setTextColor(R.id.tv_shenhezt,0xff0166FF);
                        holder.setText(R.id.tv_reason,entityTempJingq.getNopassReason());
                        //审核未通过时的再次提交按钮
                        holder.setOnClickListener(R.id.tv_again, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String jh = user.getUserName();
                                String simid = CommonUtil.getDeviceId(context);
                                String bjrxm=entityTempJingq.getBjrxm();
                                String bjrjh=entityTempJingq.getBjrjh();
                                String bjsj=entityTempJingq.getBjsj();
                                String bjrdh=entityTempJingq.getBjrdh();
                                String bjdz=entityTempJingq.getBjdz();
                                String baojnr=entityTempJingq.getBaojnr();
                                String bjlb=entityTempJingq.getBjlb();
                                String bjlx=entityTempJingq.getBjlx();
                                String bjxl=entityTempJingq.getBjxl();
                                String longitude = entityTempJingq.getLongitude();
                                String latitude = entityTempJingq.getLatitude();
                                String filesPath="";
                                //重新上报
                                jingqHandlePresenter.shangbaoJingq(jh,simid,bjrxm,bjrjh,bjsj,bjrdh,
                                        bjdz,baojnr,bjlb,bjlx,bjxl,longitude,latitude,filesPath,MyJingqSubmitActivity.this);

                            }
                        });

                        holder.setVisible(R.id.rl_phone,false);
                        break;
                    default:
                        break;

                }

            }
        };
    }

    public void initView() {
        initActionBar(toolbar,tvTitle,"我的上报");
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        rlJingqSubmitDatas.setLayoutManager(linearLayoutManager);
        rlJingqSubmitDatas.setRefreshProgressStyle(ProgressStyle.Pacman);
        rlJingqSubmitDatas.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        rlJingqSubmitDatas.setPullRefreshEnabled(true);
        rlJingqSubmitDatas.setLoadingMoreEnabled(true);
        rlJingqSubmitDatas.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                String jh = user.getUserName();
                String simid = CommonUtil.getDeviceId(context);
                if(ivChays!=null&&ivChays.size()>0){
                    for(ImageView ivCY:ivChays){
                        ivCY.setImageDrawable(getDrawable(R.drawable.ic_yichakan));
                    }
                }
                jingqHandlePresenter.queryMySubmitRecords(jh,simid);
            }

            @Override
            public void onLoadMore() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        rlJingqSubmitDatas.loadMoreComplete();

                    }
                },2000);
            }
        });
        rlJingqSubmitDatas.setAdapter(adapterJingqSubmitList);
        rlJingqSubmitDatas.addItemDecoration(new SimpleDividerDecoration(context));//添加分割线
        adapterJingqSubmitList.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                jingqsubmit=jingqSubmitDatas.get(position-1);
                View v=holder.itemView.findViewById(R.id.iv_receive_state);
                ivReceiveState=(ImageView)v;
                ImageView ivChay=(ImageView) v;
                ivChays.add(ivChay);
                ivReceiveState.setImageResource(R.drawable.ic_weichakan);
                startActivity(SubmitDetailActivity.createIntent(context,jingqsubmit));//进入上报详情界面


            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });



    }

    private void queryMySubmitDatas() {
        String jh = user.getUserName();
        String simid = CommonUtil.getDeviceId(context);
        jingqHandlePresenter.queryMySubmitRecords(jh,simid);
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
    public void onGetJingqSubmitDatasSuccess(final List<EntityTempJingq> jingqSubmitDatas) {
        recyclerViewLoadFinish();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(jingqSubmitDatas!=null){
                    MyJingqSubmitActivity.this.jingqSubmitDatas.clear();
                    MyJingqSubmitActivity.this.jingqSubmitDatas.addAll(jingqSubmitDatas);
                    adapterJingqSubmitList.notifyDataSetChanged();
                }
            }
        });

    }

    /**
     *  recyclerView加载完成
     */
    public void recyclerViewLoadFinish() {
        rlJingqSubmitDatas.refreshComplete();
        rlJingqSubmitDatas.loadMoreComplete();

    }
    @Override
    public void onGetJingqSubmitDatasFailed(Exception exception) {
        recyclerViewLoadFinish();
        ToastTool.getInstance().shortLength(context, exception.getMessage(), true);
    }

    @Override
    public void showLoadingProgress(boolean isShow) {
        if (isShow) {
            showLoadingDialog();
        } else {
            dismissLoadingDialog();
        }
    }

    /**
     * 再次提交按钮
     * @param exception
     */
    @Override
    public void handleJingqFailed(Exception exception) {
        ToastTool.getInstance().shortLength(context, "处理失败," + exception.getMessage(), true);
    }

    @Override
    public void handleJingqSuccess() {
        ToastTool.getInstance().shortLength(context, "处理成功", true);
    }



}
