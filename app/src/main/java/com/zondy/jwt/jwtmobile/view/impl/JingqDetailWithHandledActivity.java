package com.zondy.jwt.jwtmobile.view.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
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
import com.zondy.jwt.jwtmobile.view.IJingqDetailWithHandledView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 已处理警情详情界面
 */
public class JingqDetailWithHandledActivity extends BaseActivity implements IJingqDetailWithHandledView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_reload)
    TextView tv_reload;

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
    @BindView(R.id.rv_media)
    RecyclerView rv_media;

    EntityJingq entityJingq;
    IJingqHandlePresenter jingqHandlePresenter;
    EntityUser user;
    CommonAdapter<String> adapterImages;
    List<String> imageDatas;

    public static Intent createIntent(Context context, EntityJingq jingq) {
        Intent intent = new Intent(context, JingqDetailWithHandledActivity.class);
        intent.putExtra("entityJingq", jingq);
        return intent;
    }

    @Override
    public int setCustomContentViewResourceId() {
        return R.layout.activity_jingq_detail_with_handled;
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
        imageDatas = new ArrayList<>();
        adapterImages = new CommonAdapter<String>(context, R.layout.item_jingq_handled_images, imageDatas) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                Glide.with(mContext)
                        .load(s)
                        .placeholder(R.drawable.ic_aixin)
                        .crossFade()
                        .into((ImageView) holder.getView(R.id.iv_handled_jingq_image));
            }
        };
    }

    public void initView() {
        initActionBar(toolbar, tvTitle, "警情详情");
        updateJingqView(entityJingq);
        rv_media.setLayoutManager(new GridLayoutManager(context, 3));
        rv_media.setAdapter(adapterImages);
    }


    public void initOperator() {
    }

    public void updateJingqView(EntityJingq jingq) {
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
        String imgPaths = entityJingq.getFilesPath();
        String[] imgs = imgPaths.split(",");
        imageDatas.clear();
        if (imgs != null && imgs.length > 0) {
            for (String s : imgs) {
                if (!TextUtils.isEmpty(s)) {
                    imageDatas.add(s);
                }
            }

        }
        adapterImages.notifyDataSetChanged();
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
    public void loadJingqSuccess(EntityJingq jingq) {
        dismissLoadingDialog();
        this.entityJingq = jingq;
        updateJingqView(jingq);
    }

    @Override
    public void loadJIngqFalied(Exception e) {
        dismissLoadingDialog();
        ToastTool.getInstance().shortLength(context, e.getMessage(), true);
    }


    @OnClick({R.id.tv_reload})
    public void onClick(View view) {
        String jingqid = entityJingq.getJingqid();
        String jh = user.getUserName();
        String simid = CommonUtil.getDeviceId(context);
        switch (view.getId()) {
            case R.id.tv_reload:
                jingqHandlePresenter.reloadJingqWithJingqHandled(jingqid, jh, simid);
                showLoadingDialog();
                break;
        }
    }

}
