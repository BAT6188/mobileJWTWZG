package com.zondy.jwt.jwtmobile.view.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zondy.jwt.jwtmobile.R;
import com.zondy.jwt.jwtmobile.base.BaseActivity;
import com.zondy.jwt.jwtmobile.entity.EntityJingq;
import com.zondy.jwt.jwtmobile.entity.EntityUser;
import com.zondy.jwt.jwtmobile.presenter.IJingqHandlePresenter;
import com.zondy.jwt.jwtmobile.presenter.impl.JingqHandlePresenterImpl;
import com.zondy.jwt.jwtmobile.util.CommonUtil;
import com.zondy.jwt.jwtmobile.util.SharedTool;
import com.zondy.jwt.jwtmobile.util.ToastTool;
import com.zondy.jwt.jwtmobile.view.IJingqDetailWithUnhandleView;

import butterknife.BindView;
import butterknife.OnClick;

public class JingqDetailWithUnhandleActivity extends BaseActivity implements IJingqDetailWithUnhandleView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_reload)
    TextView tv_reload;
    @BindView(R.id.tv_reach_confirm)
    TextView tv_reach_confirm;// 到场确认
    @BindView(R.id.tv_accept)
    TextView tv_accept;// 接警

    @BindView(R.id.tv_reback)
    TextView tv_reback;// 回退
    @BindView(R.id.et_jingqid)
    EditText etJingqid;
    @BindView(R.id.btn_search_by_jingqid)
    Button btn_search_by_jingqid;

    @BindView(R.id.tv_baongjingshijian)
    TextView tv_baongjingshijian;
    @BindView(R.id.tv_jiejinghao)
    TextView tv_jiejinghao;
    @BindView(R.id.tv_anjian_leibie)
    TextView tv_anjian_leibie;
    @BindView(R.id.tv_anjian_leixing)
    TextView tv_anjian_leixing;
    @BindView(R.id.tv_anjian_xilei)
    TextView tv_anjian_xilei;
    @BindView(R.id.tv_baojingren)
    TextView tv_baojingren;
    @BindView(R.id.tv_phone_num)
    TextView tv_phone_num;
    @BindView(R.id.tv_call)
    TextView tv_call;
    @BindView(R.id.tv_didian)
    TextView tv_didian;
    @BindView(R.id.tv_jingqingneirong)
    TextView tv_jingqingneirong;

    EntityJingq entityJingq;
    IJingqHandlePresenter jingqHandlePresenter;
    EntityUser user;

    public static Intent createIntent(Context context, EntityJingq jingq) {
        Intent intent = new Intent(context, JingqDetailWithUnhandleActivity.class);
        intent.putExtra("entityJingq", jingq);
        return intent;
    }

    @Override
    public int setCustomContentViewResourceId() {

        return R.layout.activity_jingqdc;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParam();
        initView();
        initOperator();
    }

    public void initParam() {
        entityJingq = (EntityJingq) getIntent().getSerializableExtra("entityJingq");
        jingqHandlePresenter = new JingqHandlePresenterImpl(this);
        user = SharedTool.getInstance().getUserInfo(context);
    }

    public void initView() {
        initActionBar(toolbar, tvTitle, "警情到场");
        updateJingqView(entityJingq);
    }


    public void initOperator() {
    }

    public void updateJingqView(EntityJingq jingq){
        tv_baongjingshijian.setText(entityJingq.getBaojsj());
        tv_jiejinghao.setText(entityJingq.getJiejh());

        tv_anjian_leibie.setText(entityJingq.getAjlb());
        tv_anjian_leixing.setText(entityJingq.getAjlx());
        tv_anjian_xilei.setText(entityJingq.getAjxl());

        tv_baojingren.setText(entityJingq.getBaojr());
        String phoneNum = entityJingq.getBaojrdh();
        if (!TextUtils.isEmpty(phoneNum)) {
            if (phoneNum.startsWith("0")) {
                phoneNum = phoneNum.replaceFirst("0", "");
            }
        } else {
            phoneNum = "";
        }
        tv_phone_num.setText(phoneNum);
        tv_didian.setText(entityJingq.getBaojdz());
        tv_jingqingneirong.setText(entityJingq.getBaojnr());
//        if (entityJingq.getState() >= EntityJingq.HADREAD) {// 已接收，隐藏接收按钮
//            tv_accept.setVisibility(View.GONE);
//            tv_reback.setVisibility(View.VISIBLE);
//        }
//        if (entityJingq.getState() >= EntityJingq.HADREACHCONFIRM) {
//            tv_reback.setVisibility(View.GONE);
//        }
//
//        if (entityJingq.getState() > EntityJingq.HADREACHCONFIRM) {// 已处理警情，隐藏到场确认按钮
//            tv_reach_confirm.setVisibility(View.GONE);
//        } else {
//            tv_reach_confirm.setVisibility(View.VISIBLE);
//        }
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
    public void reLoadJingqSuccess(EntityJingq jingq) {
        dismissLoadingDialog();
        this.entityJingq = jingq;
        updateJingqView(jingq);
    }

    @Override
    public void reLoadJIngqFalied(Exception e) {
        dismissLoadingDialog();
        ToastTool.getInstance().shortLength(context,e.getMessage(),true);
    }

    @Override
    public void receiveJingqSuccess() {
        dismissLoadingDialog();
        ToastTool.getInstance().shortLength(context, "接收成功", true);

        entityJingq
                .setState(EntityJingq.HADREAD);
        tv_accept.setVisibility(View.GONE);
        tv_reback.setVisibility(View.VISIBLE);
    }

    @Override
    public void receiveJIngqFalied(Exception e) {
        dismissLoadingDialog();
        ToastTool.getInstance().shortLength(context,e.getMessage(),true);
    }

    @Override
    public void arriveConfirmSuccess() {
        dismissLoadingDialog();
        tv_reback
                .setVisibility(View.GONE);
        entityJingq
                .setState(EntityJingq.HADREACHCONFIRM);
        startActivity(JingqHandleActivity.createIntent(context,entityJingq));
    }

    @Override
    public void arriveConfirmFailed(Exception e) {
        dismissLoadingDialog();

        ToastTool.getInstance().shortLength(context,e.getMessage(),true);
    }

    @Override
    public void rollbackJingqSuccess() {
        dismissLoadingDialog();
        entityJingq.setState(EntityJingq.UNREAD);
        tv_accept.setVisibility(View.VISIBLE);
        tv_reback.setVisibility(View.GONE);
    }

    @Override
    public void rollbackJingqFailed(Exception e) {
        dismissLoadingDialog();
        ToastTool.getInstance().shortLength(context,e.getMessage(),true);
    }


    @OnClick({R.id.tv_reload, R.id.tv_reach_confirm, R.id.tv_accept, R.id.tv_reback, R.id.btn_search_by_jingqid})
    public void onClick(View view) {
        String jingqid = entityJingq.getJingqid();
        String jingyid = entityJingq.getJingyid();
        String  carid = user.getCarid();
        String jh = user.getUserName();
        String simid = CommonUtil.getDeviceId(context);
        switch (view.getId()) {
            case R.id.tv_reload:
                    jingqHandlePresenter.reloadJingqWithJingqUnhandle(jingqid,jh,simid);
                    showLoadingDialog();
                break;
            case R.id.tv_reach_confirm:
                String longitude = "120";
                String latitude = "31";
                jingqHandlePresenter.arriveConfirm(jingyid,jingqid,longitude,latitude,jh,simid);
                showLoadingDialog();
                break;
            case R.id.tv_accept:
                jingqHandlePresenter.acceptJingq(jingqid,carid,jh,simid);
                showLoadingDialog();
                break;
            case R.id.tv_reback:
                jingqHandlePresenter.rollbackJingq(jingqid,jh,simid);
                showLoadingDialog();
                break;
            case R.id.btn_search_by_jingqid:
                String inputJingqid = etJingqid.getText().toString().trim();
                if(!TextUtils.isEmpty(inputJingqid)){
                jingqHandlePresenter.reloadJingqWithJingqUnhandle(inputJingqid,jh,simid);
                showLoadingDialog();
                }
            break;
        }
    }
}
