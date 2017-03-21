package com.zondy.jwt.jwtmobile.view.impl;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zondy.jwt.jwtmobile.R;
import com.zondy.jwt.jwtmobile.base.BaseActivity;
import com.zondy.jwt.jwtmobile.entity.EntityFeedbackRecord;
import com.zondy.jwt.jwtmobile.entity.EntityJingq;
import com.zondy.jwt.jwtmobile.entity.EntityUser;
import com.zondy.jwt.jwtmobile.ui.DividerItemDecoration;
import com.zondy.jwt.jwtmobile.util.SharedTool;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/8.
 */
public class RecordDetailActivity extends BaseActivity{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_fankuifang)
    TextView tvFanKuiFang;
    @BindView(R.id.tv_suoshudanwei)
    TextView tvDanwei;
    @BindView(R.id.tv_lianxiphone)
    TextView tvPhone;
    @BindView(R.id.tv_fankuinr)
    TextView tvFankuinr;
    @BindView(R.id.tv_fankuisj)
    TextView tvFankuisj;
    @BindView(R.id.recyclerview)
    RecyclerView rv_media;
    @BindView(R.id.iv_takephoto)
    ImageView ivTakephoto;
    @BindView(R.id.iv_takevideo)
    ImageView ivTakevideo;
    boolean isChanged=false;//判断图片是否改变
    private final int REQ_CODE_EDIT_IMAGE = 1;//点击图片进入展示图片活动的请求码


    EntityFeedbackRecord selectedRecord;//从上一活动点击子项传入的
    EntityUser user;
    CommonAdapter<String> imageAdapter;
//    int[] mImageResourceIds;
    List<String> imageDatas;//多媒体资料路径集合


    public static Intent createIntent(Context context, EntityFeedbackRecord feedbackRecord) {
        Intent intent = new Intent(context, RecordDetailActivity.class);
        intent.putExtra("entityRecord",feedbackRecord);
        return intent;
    }
    @Override
    public int setCustomContentViewResourceId() {
        return R.layout.activity_record_detail;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParam();
        initView();
        initOperator();
    }

    public void initParam() {
        selectedRecord=(EntityFeedbackRecord) getIntent().getSerializableExtra("entityRecord");
        user = SharedTool.getInstance().getUserInfo(context);
        imageDatas= new ArrayList<String>();
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
        initActionBar(toolbar,tvTitle,"记录详情");
        updateRecordDetail(selectedRecord);
    }

    public void updateRecordDetail(EntityFeedbackRecord selectedRecord) {
//        if(反馈方是警员)
        if(selectedRecord.getFeedbacktype()==0) {
            tvFanKuiFang.setText(selectedRecord.getFkfxm());//获取警员中文名
            tvDanwei.setText(selectedRecord.getZzjgmc());
            tvPhone.setText(selectedRecord.getJydh());
            tvFankuinr.setText(selectedRecord.getFknr());//根据传来的selectedRecord获取反馈内容
            tvFankuisj.setText(selectedRecord.getFksj());
            String FilesPath = selectedRecord.getFilespath();//获取反馈的资料的文件路径
            String[] str = FilesPath.split(",");
            if (str.length > 0) {
                for (int i = 0; i < str.length; i++) {
                    imageDatas.add(str[i]);
                }
            }
        }else{
            tvFanKuiFang.setText("指挥中心");
            tvDanwei.setText(selectedRecord.getZzjgmc());
            tvPhone.setText(selectedRecord.getZzjgdh());
            tvFankuinr.setText(selectedRecord.getFknr());//根据传来的selectedRecord获取反馈内容
            tvFankuisj.setText(selectedRecord.getFksj());
            String FilesPath = selectedRecord.getFilespath();//获取反馈的资料的文件路径
            String[] str = FilesPath.split(",");
            if (str.length > 0) {
                for (int i = 0; i < str.length; i++) {
                    imageDatas.add(str[i]);
                }
            }
        }
//        imageDatas.add("/sina/weibo/weibo/img-6a5a0a263a298c3808cae4364fa4288e.jpg");
//        imageDatas.add("/sina/weibo/weibo/img-6c35a5bc17f6304547301b5365628d5f.jpg");
//        imageDatas.add("/sina/weibo/weibo/img-8c31ce43c15943f94d02b25e1a7cea94.jpg");
//        imageDatas.add("/sina/weibo/weibo/img-37f9f6f64669008219a7ee365990077c.jpg");
//        imageDatas.add("/sina/weibo/weibo/img-409d663eefaa9a5ea7ed628a5fc855f6.jpg");
        //测试
        imageDatas.clear();
        imageDatas.add("/storage/emulated/0/Pictures/Screenshots/S70315-175324.jpg");
        imageDatas.add("/storage/emulated/0/Pictures/Screenshots/S70315-175313.jpg");
        imageDatas.add("/storage/emulated/0/Pictures/Screenshots/S70315-175324.jpg");
        imageDatas.add("/storage/emulated/0/Pictures/Screenshots/S70315-175313.jpg");
        imageDatas.add("/storage/emulated/0/Pictures/Screenshots/S70315-175324.jpg");
        imageAdapter.notifyDataSetChanged();
        rv_media.setAdapter(imageAdapter);
        rv_media.setLayoutManager(new GridLayoutManager(this, 3));
        Log.d("wuzhengguan","图片个数"+imageAdapter.getItemCount());

//        mGallery.setSelection(imageAdapter.getCount()/2);//设置第二张图片为默认选择的，这样第一章位于开始位置
//        ViewGroup.MarginLayoutParams layoutParams=(ViewGroup.MarginLayoutParams)mGallery.getLayoutParams();
//        WindowManager wm=(WindowManager)(context).getSystemService(Context.WINDOW_SERVICE);
//        int width=wm.getDefaultDisplay().getWidth();
//        Log.d("wuzhengguan","宽度："+width);
//        layoutParams.setMargins(-width*3/4,20,0,0);
//        mGallery.setLayoutParams(layoutParams);


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

//    class ImageAdapter extends BaseAdapter{
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
//           imageView.setLayoutParams(new Gallery.LayoutParams(200, 200));
////            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//            return imageView;
//        }
//    }
//
//        @OnClick({R.id.iv_takephoto,R.id.iv_takevideo})
//        public void  onClick(View v){
//            switch (v.getId()){
//                case R.id.iv_takephoto:
//                    if(isChanged) {
//                        ivTakephoto.setImageDrawable(getResources().getDrawable(R.drawable.ic_takephoto));
//                        mGallery.setVisibility(View.GONE);
//                    }else{
//                        ivTakephoto.setImageDrawable(getResources().getDrawable(R.drawable.ic_takephoto_press));
//                        mGallery.setVisibility(View.VISIBLE);
//                    }
//                    isChanged=!isChanged;
//                    break;
//                case R.id.iv_takevideo:
//                    if(isChanged) {
//                        ivTakevideo.setImageDrawable(getResources().getDrawable(R.drawable.ic_takevideo));
//                        mGallery.setVisibility(View.GONE);
//                    }else{
//                        ivTakevideo.setImageDrawable(getResources().getDrawable(R.drawable.ic_takevideo_press));
//                        mGallery.setVisibility(View.VISIBLE);
//                    }
//                    isChanged=!isChanged;
//                    break;
//                default:
//                    break;
//        }
//    }


}
