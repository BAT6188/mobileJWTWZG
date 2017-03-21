package com.zondy.jwt.jwtmobile.view.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zondy.jwt.jwtmobile.R;
import com.zondy.jwt.jwtmobile.base.BaseActivity;
import com.zondy.jwt.jwtmobile.entity.EntityJingq;
import com.zondy.jwt.jwtmobile.entity.EntityUser;
import com.zondy.jwt.jwtmobile.presenter.IJingqHandlePresenter;
import com.zondy.jwt.jwtmobile.util.CommonUtil;
import com.zondy.jwt.jwtmobile.util.SharedTool;
import com.zondy.mapgis.android.mapview.MapView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/2.
 */
public class JingqXieChaDetailActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_fankui)
    TextView tvFanKui;
    @BindView(R.id.tv_fankui_jilu)
    TextView tvFanKuiRecord;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;

    @BindView(R.id.tv_anjian_leixing)
    TextView tvAnJianType;
    @BindView(R.id.tv_baojing_shijian)
    TextView tvBaoJingTime;
    @BindView(R.id.tv_baojingren)
    TextView tvBaoJingRen;
    @BindView(R.id.tv_baojing_dianhua)
    TextView tvBaoJingPhone;
    @BindView(R.id.tv_jiejing_shijian)
    TextView tvJieJingTime;
    @BindView(R.id.tv_jiejingren)
    TextView tvJieJingRen;
    @BindView(R.id.tv_jiejing_dianhua)
    TextView tvJieJingPhone;
    @BindView(R.id.tv_anfa_dizhi)
    TextView tvAddress;
    @BindView(R.id.tv_jingq_neirong)
    TextView tvContent;
    @BindView(R.id.recyclerview)
    RecyclerView rv_media;
//    @BindView(R.id.scroll_layout)
//    ScrollLayout mScrollLayout;
    private MapView mapView;
    private final int REQ_CODE_EDIT_IMAGE = 1;//点击图片进入展示图片活动的请求码

    EntityJingq entityJingq;
    EntityUser user;
    IJingqHandlePresenter jingqHandlePresenter;
    CommonAdapter<String> imageAdapter;
    List<String> imageDatas;//多媒体资料路径集合

    public static Intent createIntent(Context context, EntityJingq jingqxiecha){
        Intent intent=new Intent(context,JingqXieChaDetailActivity.class);
        intent.putExtra("entityXieCha",jingqxiecha);
        return intent;
    }
    @Override
    public int setCustomContentViewResourceId() {
        return R.layout.activity_xiecha_detail;
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParam();
        initView();
        initOperator();
    }

    public void initParam() {
        entityJingq = (EntityJingq) getIntent().getSerializableExtra("entityXieCha");
        user = SharedTool.getInstance().getUserInfo(context);
        mapView=(MapView)findViewById(R.id.mapview);
        imageDatas= new ArrayList<String>();
        String FilesPath = entityJingq.getFilesPath();//获取反馈的资料的文件路径
        String[] str = FilesPath.split(",");
        if (str.length > 0) {
            for (int i = 0; i < str.length; i++) {
                imageDatas.add(str[i]);
            }
        }
//        mImageResourceIds=new int[]{R.drawable.ic_aixin,R.drawable.ic_caidan,R.drawable.ic_dizhi,R.drawable.ic_dl_pwd};
        imageAdapter=new CommonAdapter<String>(context,R.layout.item_record_detail_images, imageDatas) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                Glide.with(mContext)
                        .load(s)
                        .crossFade()
                        .into((ImageView) holder.getView(R.id.iv_record_detail_image));

            }
        };
        imageAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //进入图片展示页面
                startActivityForResult(JingqImageEditActivity.createIntent(context, imageDatas, position, false),REQ_CODE_EDIT_IMAGE);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

    public void initView() {
        mapView.loadFromFile(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "MapGIS/map/wuhan/wuhan.xml");
        mapView.setShowNorthArrow(false);
        initActionBar(toolbar,tvTitle,"要案详情");
        mapView.getChildAt(0).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP){
                    mScrollView.requestDisallowInterceptTouchEvent(false);//允许scrollView截断点击放松事件，scrollView可滑动
                }else{
                    mScrollView.requestDisallowInterceptTouchEvent(true);//不允许scrollView截断点击事件，由子view处理
                }
                return false;
            }
        });

//        mScrollLayout.setMinOffset(0);
//        mScrollLayout.setMaxOffset((int) (ScreenUtil.getScreenHeight(this) * 0.65));
//        mScrollLayout.setIsSupportExit(true);
//        mScrollLayout.setAllowHorizontalScroll(true);
//        mScrollLayout.setToOpen();
//        mScrollLayout.setExitOffset(ScreenUtil.dip2px(this, 150));

        updateJingqView(entityJingq);
    }

    public void updateJingqView(EntityJingq entityJingq) {
        tvAnJianType.setText(entityJingq.getAjlb()+"> "+entityJingq.getAjlx()+"> "+entityJingq.getAjxl());
        tvBaoJingTime.setText(entityJingq.getBaojsj());
        tvBaoJingRen.setText(entityJingq.getBaojr());
        String baoJingPhone=entityJingq.getBaojrdh();
        if (!TextUtils.isEmpty(baoJingPhone)){
            if (baoJingPhone.startsWith("0")) {
                baoJingPhone = baoJingPhone.replaceFirst("0", "");
            }
        }else {
                baoJingPhone = "";
            }

        tvBaoJingPhone.setText(baoJingPhone);
        tvJieJingTime.setText(entityJingq.getChujsj());
        tvJieJingRen.setText(entityJingq.getZjg_cjr());//处警人
        tvJieJingPhone.setText("654321");//接警人联系电话？
        tvAddress.setText(entityJingq.getZjg_fsdd());//案发地址不是报警地址？
        tvContent.setText(entityJingq.getBaojnr());//报警内容

        imageAdapter.notifyDataSetChanged();
        rv_media.setAdapter(imageAdapter);
        rv_media.setLayoutManager(new GridLayoutManager(this, 3));

    }


    private void initOperator() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
            default:
                break;
        }
        return true;
    }


    @OnClick({R.id.tv_fankui,R.id.tv_fankui_jilu})
    public void onClick(View view){
        String jh=user.getUserName();
        String simid= CommonUtil.getDeviceId(context);
        String jingqid=entityJingq.getJingqid();

        switch (view.getId()){
            case R.id.tv_fankui://进入警情上报页面

               startActivity(YaoAnFanKuiActivity.createIntent(context,entityJingq));
                break;
            case R.id.tv_fankui_jilu://查询反馈记录，进入反馈记录列表页面

                startActivity(FeedbackRecordsActivity.createIntent(context,entityJingq));
                break;
            default:
                break;

        }

    }


}
