package com.zondy.jwt.jwtmobile.view.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zondy.jwt.jwtmobile.R;
import com.zondy.jwt.jwtmobile.base.BaseActivity;
import com.zondy.jwt.jwtmobile.entity.EntityJingq;
import com.zondy.jwt.jwtmobile.entity.EntityUser;
import com.zondy.jwt.jwtmobile.presenter.IJingqHandlePresenter;
import com.zondy.jwt.jwtmobile.presenter.impl.JingqHandlePresenterImpl;
import com.zondy.jwt.jwtmobile.util.CommonUtil;
import com.zondy.jwt.jwtmobile.util.SharedTool;
import com.zondy.jwt.jwtmobile.util.ToastTool;
import com.zondy.jwt.jwtmobile.view.IJingqListView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class JingqListActivity extends BaseActivity implements IJingqListView {


    @BindView(R.id.rl_jingqdatas)
    XRecyclerView rlJingqdatas;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    IJingqHandlePresenter jingqclPresenter;
    List<EntityJingq> jingqDatas;
    CommonAdapter<EntityJingq> adapterJingqList;
    EntityUser user;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, JingqListActivity.class);
        return intent;
    }

    @Override
    public int setCustomContentViewResourceId() {
        return R.layout.activity_jingqcl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initParam();
        initView();
        initOperator();
    }

    @Override
    protected void onResume() {
        super.onResume();
        queryJingqDatas();//jingqclPresenter.queryJingqDatas(jh, simid);
    }

    public void initParam() {
        jingqclPresenter = new JingqHandlePresenterImpl(this);
        user = SharedTool.getInstance().getUserInfo(context);
        jingqDatas = new ArrayList<EntityJingq>();

        adapterJingqList = new CommonAdapter<EntityJingq>(context, R.layout.item_jingqcl_jingq, jingqDatas) {
            @Override
            protected void convert(ViewHolder holder, EntityJingq entityJingq, int position) {
                holder.setText(R.id.tv_title, entityJingq.getBaojdz());//报警地址
                holder.setText(R.id.tv_message, entityJingq.getBaojnr());//内容
                holder.setText(R.id.tv_time, entityJingq.getBaojsj());//时间
            }


        };
    }

    public void initView() {
        initActionBar(toolbar, tvTitle, "警情处理");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rlJingqdatas.setLayoutManager(linearLayoutManager);

        rlJingqdatas.setRefreshProgressStyle(ProgressStyle.Pacman);
        rlJingqdatas.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        rlJingqdatas.setPullRefreshEnabled(true);
        rlJingqdatas.setLoadingMoreEnabled(false);
        rlJingqdatas.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                String jh = user.getUserName();
                String simid = CommonUtil.getDeviceId(context);
                jingqclPresenter.queryJingqDatas(jh,simid);
            }

            @Override
            public void onLoadMore() {
            }
        });

        rlJingqdatas.setAdapter(adapterJingqList);
        rlJingqdatas.addItemDecoration(new SimpleDividerDecoration(context));
        adapterJingqList.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                EntityJingq jingq = jingqDatas.get(position-1);
//                if (jingq.getState() >= EntityJingq.HADHANDLED) {
//                    startActivity(JingqDetailWithHandledActivity.createIntent(context, jingq));
//                } else {
                    startActivity(JingqDetailWithUnhandleActivity.createIntent(context,jingq ));
//                }

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }


    public void initOperator() {

    }

    public void queryJingqDatas(){
        String jh = user.getUserName();
        String simid = CommonUtil.getDeviceId(context);
        jingqclPresenter.queryJingqDatas(jh, simid);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.jingqcl_main,menu);
        return true;
    }

    /**
     * 重写该方法让菜单项里面的icon显示
     * @param menu
     * @return
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(menu != null){
            if(menu.getClass().getSimpleName().equals("MenuBuilder")){
                try{
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                }
                catch(NoSuchMethodException e){}
                catch(Exception e){}
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

//    @Override
//    public boolean onMenuOpened(int featureId, Menu menu) {
//        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
//            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
//                try {
//                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
//                    m.setAccessible(true);
//                    m.invoke(menu, true);
//                } catch (Exception e) {
//                }
//            }
//        }
//        return super.onMenuOpened(featureId, menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            case R.id.sos:
                ToastTool.getInstance().shortLength(this, "一键报警", true);
                break;
            case R.id.xiecha:   //进入要案协查列表活动
                startActivity(JingqXieChaListActivity.createIntent(context));
                break;
            case R.id.jingqsb:
                startActivity(JingqShangBaoActivity.createIntent(context));//进入警情上报页面
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onGetJingqDatasSuccess(final List<EntityJingq> jingqDatas) {
        recyclerViewLoadFinish();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (jingqDatas != null) {
                    JingqListActivity.this.jingqDatas.clear();
                    JingqListActivity.this.jingqDatas.addAll(jingqDatas);
                    adapterJingqList.notifyDataSetChanged();
                }

            }
        });

    }

    public void recyclerViewLoadFinish(){
        rlJingqdatas.refreshComplete();
        rlJingqdatas.loadMoreComplete();
    }

    @Override
    public void onGetJingqDatasFailed(Exception e) {
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
