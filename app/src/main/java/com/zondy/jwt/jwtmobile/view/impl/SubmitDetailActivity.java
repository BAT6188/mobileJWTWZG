package com.zondy.jwt.jwtmobile.view.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zondy.jwt.jwtmobile.R;
import com.zondy.jwt.jwtmobile.base.BaseActivity;
import com.zondy.jwt.jwtmobile.entity.EntityTempJingq;
import com.zondy.mapgis.android.mapview.MapView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/3/12.
 */
public class SubmitDetailActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_jqdz)
    TextView tvDizhi;
    @BindView(R.id.tv_ajlb)
    TextView tvLeibie;
    @BindView(R.id.tv_ajlx)
    TextView tvLeixing;
    @BindView(R.id.tv_ajxl)
    TextView tvXilei;
    @BindView(R.id.tv_sbnr)
    TextView tvNeirong;
    @BindView(R.id.recyclerview)
    RecyclerView rv_media;
    ScrollView mScrollView;
    private final int REQ_CODE_EDIT_IMAGE = 1;//点击图片进入展示图片活动的请求码
    private MapView mapView;
    EntityTempJingq entityTempJingq;
   CommonAdapter<String> imageAdapter;
    List<String> imageDatas;//多媒体资料路径集合



    public static Intent createIntent(Context context, EntityTempJingq shangbaoJingq){
        Intent intent=new Intent(context,SubmitDetailActivity.class);
        intent.putExtra("entityShangbao",shangbaoJingq);
        return intent;
    }

    @Override
    public int setCustomContentViewResourceId() {
        return R.layout.activity_submit_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParam();
        initView();
        initOperator();
    }

    public void initParam() {
        entityTempJingq=(EntityTempJingq)getIntent().getSerializableExtra("entityShangbao");
        mapView=(MapView)findViewById(R.id.mapview);
        imageDatas= new ArrayList<String>();
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
                Log.d("wuzhengguan","点击图片");
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

        initActionBar(toolbar,tvTitle,"上报详情");
        updateJingqView(entityTempJingq);
    }

    private void updateJingqView(EntityTempJingq entityTempJingq) {
        tvDizhi.setText(entityTempJingq.getBjdz());
        tvLeibie.setText(entityTempJingq.getBjlb());
        tvLeixing.setText(entityTempJingq.getBjlx());
        tvXilei.setText(entityTempJingq.getBjxl());
        tvNeirong.setText(entityTempJingq.getBaojnr());
        String FilesPath=entityTempJingq.getFilesPath();
        String[] str=FilesPath.split(",");
        if(str.length>0) {
            for (int i = 0; i < str.length; i++) {
                imageDatas.add(str[i]);
            }
        }
        imageDatas.clear();
        imageDatas.add("/storage/emulated/0/DCIM/P70310-234429.jpg");

        imageDatas.add("/storage/emulated/0/Pictures/Screenshots/S70315-175313.jpg");
        imageDatas.add("/storage/emulated/0/Pictures/Screenshots/S70315-175324.jpg");
        imageDatas.add("/storage/emulated/0/Pictures/Screenshots/S70315-175313.jpg");
        imageAdapter.notifyDataSetChanged();
        rv_media.setAdapter(imageAdapter);
        rv_media.setLayoutManager(new GridLayoutManager(this, 3));
//
    }


    public void initOperator() {

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

//    class ImageAdapter extends BaseAdapter {
//        Context mContext;        //上下文对象
//
//        public ImageAdapter(Context context) {
//            this.mContext = context;
//        }
//        @Override
//        public int getCount() {
//            return imageDatas.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return imageDatas.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            View view= LayoutInflater.from(mContext).inflate(R.layout.item_jingq_handled_images,null);
////                ImageView imageView=new ImageView(mContext);
//            ImageView imageView = (ImageView)view.findViewById(R.id.iv_handled_jingq_image);
////            imageView.setImageResource(mImageResourceIds[position]);
////            BitmapFactory.decodeFile(mImageResourceIds[position],null);
//            Glide.with(mContext).load(imageDatas.get(position)).
//                    crossFade().into(imageView);
////            imageView.setLayoutParams(new LinearLayout.LayoutParams(100,100));
//            imageView.setLayoutParams(new Gallery.LayoutParams(200, 200));
////            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//            return imageView;
//        }
//    }

}
