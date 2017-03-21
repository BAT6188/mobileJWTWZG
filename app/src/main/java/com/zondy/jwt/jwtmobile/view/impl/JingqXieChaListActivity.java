package com.zondy.jwt.jwtmobile.view.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.zondy.jwt.jwtmobile.entity.EntityUser;
import com.zondy.jwt.jwtmobile.presenter.IJingqHandlePresenter;
import com.zondy.jwt.jwtmobile.presenter.impl.JingqHandlePresenterImpl;
import com.zondy.jwt.jwtmobile.util.CommonUtil;
import com.zondy.jwt.jwtmobile.util.SharedTool;
import com.zondy.jwt.jwtmobile.util.ToastTool;
import com.zondy.jwt.jwtmobile.view.IJingqXieChaListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/2/28.
 */
public class JingqXieChaListActivity extends BaseActivity implements IJingqXieChaListView {

    @BindView(R.id.rl_jingqxiechadatas)
    XRecyclerView rlJingqxiechadatas;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    ImageView ivReceiveState;
    public List<ImageView> ivChays=new ArrayList<>();//选中的子项imageView集合

    IJingqHandlePresenter jingqHandlePresenter;
    List<EntityJingq> jingqXieChaDatas;
    CommonAdapter<EntityJingq> adapterJingqXieChaList;
    EntityUser user;
    EntityJingq jingqxiecha;//选中的警情


    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, JingqXieChaListActivity.class);
        return intent;
    }

    @Override
    public int setCustomContentViewResourceId() {
        return R.layout.activity_yaoanxiecha;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParam();
        initView();
        initOperator();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void initParam() {
        jingqHandlePresenter=new JingqHandlePresenterImpl(this);
        user = SharedTool.getInstance().getUserInfo(context);
        jingqXieChaDatas=new ArrayList<EntityJingq>();
        queryXiectsDatas();//查询协查列表
//        Log.d("wuzhengguan","大小"+jingqXieChaDatas.size()+"内容：");

        adapterJingqXieChaList=new CommonAdapter<EntityJingq>(context,R.layout.item_jingq_xiec,jingqXieChaDatas) {
            @Override
            protected void convert(ViewHolder holder, EntityJingq entityJingq, int position) {
//               holder.setImageResource(R.id.iv_receive_state,R.drawable.ic_weichakan);
//                Log.d("wuzhengguan","类别："+entityJingq.getBjlb());
                if(jingqXieChaDatas.size()>0){
                Log.d("wuzhengguan","大小"+jingqXieChaDatas.size()+"内容："+"entityJingq"+entityJingq.getBjlb());}
                holder.setText(R.id.tv_jingqlb,entityJingq.getBjlb());//案件类别
                holder.setText(R.id.tv_dizhi,entityJingq.getBaojdz());//报警地址
                holder.setText(R.id.tv_neirong,entityJingq.getBaojnr());//报警内容
                holder.setText(R.id.tv_jiejingren,entityJingq.getZjg_cjr());//处警（接警）人
                int jingqzt=entityJingq.getJingqzt();//获取当前警情状态（int值)
                String tmpJingqzt=null;
                switch (jingqzt){
                    case 0:
                        tmpJingqzt="未受理";
                        holder.setText(R.id.tv_shijian,entityJingq.getBaojsj());//时间为报警时间
                        holder.setText(R.id.tv_jingqzt,tmpJingqzt);
                        break;
                    case 1:
                        tmpJingqzt="已接警";
                        holder.setText(R.id.tv_shijian,entityJingq.getChujsj());//时间为接警时间
                        holder.setText(R.id.tv_jingqzt,tmpJingqzt);
                        break;
                    case 2:
                        tmpJingqzt="到达现场";
                        holder.setText(R.id.tv_shijian,entityJingq.getDaodsj());//时间为警员到场时间
                        holder.setText(R.id.tv_jingqzt,tmpJingqzt);
                        break;
                    case 3:
                        tmpJingqzt="处警完毕,资料提交成功";
                        holder.setText(R.id.tv_shijian,entityJingq.getFanksj());//时间为反馈时间
                        holder.setText(R.id.tv_jingqzt,tmpJingqzt);
                        break;
                    case 4:
                        tmpJingqzt="处警完毕,资料提交失败";
                        holder.setText(R.id.tv_shijian,entityJingq.getDaodsj());//资料提交失败，时间为警员到场时间
                        holder.setText(R.id.tv_jingqzt,tmpJingqzt);
                        break;
                    default:
                        break;
                }

            }
        };
    }

    public void initView() {

        initActionBar(toolbar,tvTitle,"要案协查");
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        rlJingqxiechadatas.setLayoutManager(linearLayoutManager);
        rlJingqxiechadatas.setRefreshProgressStyle(ProgressStyle.Pacman);
        rlJingqxiechadatas.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        rlJingqxiechadatas.setPullRefreshEnabled(true);
        rlJingqxiechadatas.setLoadingMoreEnabled(true);
        rlJingqxiechadatas.setLoadingListener(new XRecyclerView.LoadingListener(){

            @Override
            public void onRefresh() {
                String jh = user.getUserName();
                String simid = CommonUtil.getDeviceId(context);
                String zzjgdm=user.getZzjgdm();
//                Log.d("wuzhengguan",user.toString());
                if(ivChays!=null&&ivChays.size()>0){
                    for(ImageView ivCY:ivChays){
                        ivCY.setImageDrawable(getDrawable(R.drawable.ic_yichakan));
                    }
                }
                jingqHandlePresenter.queryXiectsDatas(jh,simid,zzjgdm);//查询协查推送列表

            }

            @Override
            public void onLoadMore() {
//                Log.d("wuzhengguan","加载更多");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        rlJingqxiechadatas.loadMoreComplete();

                    }
                },2000);
            }
        });
        rlJingqxiechadatas.setAdapter(adapterJingqXieChaList);
        rlJingqxiechadatas.addItemDecoration(new SimpleDividerDecoration(context));//添加分割线
        adapterJingqXieChaList.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                jingqxiecha = jingqXieChaDatas.get(position-1);
                String jh = user.getUserName();
                String simid = CommonUtil.getDeviceId(context);
                jingqHandlePresenter.confirmReceiveMsg(jingqxiecha.getJingqid(),jh,simid);//推送消息接收确认
//                startActivity(JingqXieChaDetailActivity.createIntent(context,jingqxiecha ));//进入要案详情页面
                View v =holder.itemView.findViewById(R.id.iv_receive_state);//获取点击子项中的接受状态图片
                 ivReceiveState=(ImageView) v;
                ImageView ivChay=(ImageView) v;
                ivChays.add(ivChay);
                ivReceiveState.setImageResource(R.drawable.ic_weichakan);//变为已查看

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

    private void initOperator() {

    }


    public void queryXiectsDatas() {
        String jh = user.getUserName();
        String zzjgdm=user.getZzjgdm();
        String simid = CommonUtil.getDeviceId(context);
        jingqHandlePresenter.queryXiectsDatas(jh,simid,zzjgdm);
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

    /**
     * 重写IJingqXieChaListView方法
     * @param jingqXieChaDatas
     */
    @Override
    public void onGetJingqXieChaDatasSuccess(final List<EntityJingq> jingqXieChaDatas) {
        recyclerViewLoadFinish();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(jingqXieChaDatas!=null){
                    JingqXieChaListActivity.this.jingqXieChaDatas.clear();
                    JingqXieChaListActivity.this.jingqXieChaDatas.addAll(jingqXieChaDatas);
                    Log.d("wuzhengguan","集合大小"+JingqXieChaListActivity.this.jingqXieChaDatas.size());
                    adapterJingqXieChaList.notifyDataSetChanged();
                }
            }
        });

    }

    /**
     *  recyclerView加载完成
     */
    public void recyclerViewLoadFinish() {
        rlJingqxiechadatas.refreshComplete();
        rlJingqxiechadatas.loadMoreComplete();

    }

    @Override
    public void onGetJingqXieChaDatasFailed(Exception exception) {
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
     * 确认接收推送
     * @param exception
     */
    @Override
    public void onconfirmReceiveMsgFailed(Exception exception) {
        ToastTool.getInstance().shortLength(context,exception.getMessage(),true);
    }

    @Override
    public void onconfirmReceiveMsgSuccess() {
//        ToastTool.getInstance().shortLength(context, "确认接收推送成功", true);


//        ivReceiveState=(ImageView)findViewById(R.id.iv_receive_state);


//        ivReceiveState.setImageResource(R.drawable.ic_yichakan); //当成功接收消息推送后，图片变为已查看
//        jingqxiecha.setReadState(1);//设置阅读状态为已读
        ivReceiveState.setImageResource(R.drawable.ic_weichakan);
        startActivity(JingqXieChaDetailActivity.createIntent(context,jingqxiecha ));//进入要案详情页面


    }
}
