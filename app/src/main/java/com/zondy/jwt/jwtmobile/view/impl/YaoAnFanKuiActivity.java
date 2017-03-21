package com.zondy.jwt.jwtmobile.view.impl;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zondy.jwt.jwtmobile.R;
import com.zondy.jwt.jwtmobile.base.BaseActivity;
import com.zondy.jwt.jwtmobile.entity.EntityJingq;
import com.zondy.jwt.jwtmobile.entity.EntityMedia;
import com.zondy.jwt.jwtmobile.entity.EntityUser;
import com.zondy.jwt.jwtmobile.presenter.IJingqHandlePresenter;
import com.zondy.jwt.jwtmobile.presenter.impl.JingqHandlePresenterImpl;
import com.zondy.jwt.jwtmobile.util.CommonUtil;
import com.zondy.jwt.jwtmobile.util.GlideImageLoader;
import com.zondy.jwt.jwtmobile.util.SharedTool;
import com.zondy.jwt.jwtmobile.util.ToastTool;
import com.zondy.jwt.jwtmobile.view.IYaoAnFanKuiView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/3.
 */
public class YaoAnFanKuiActivity extends BaseActivity implements IYaoAnFanKuiView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_feedback_result_content)
    EditText etFeedback;
   @BindView(R.id.rv_media)
    RecyclerView rvFeedbackMedia;
    @BindView(R.id.tv_confirm_feedback)
    TextView tvFeedback;
    @BindView(R.id.tv_add_media)
    TextView tvAddMedia;
//    @BindView(R.id.iv_takephoto)
//    ImageView ivTakePhoto;
//    @BindView(R.id.iv_takevideo)
//    ImageView ivTakeVideo;


    public static final int REQUEST_CODE_SELECT = 100;//点击“添加图片”进入图片选择的请求码
    private final int REQ_CODE_EDIT_IMAGE = 1;//点击图片进入编辑图片活动的请求码
    public final int imgCountLimit = 9;


    EntityJingq entityJingq;
    IJingqHandlePresenter jingqHandlePresenter;
    EntityUser user;
    CommonAdapter<String> adapterImage;
    List<String> imageDatas;

    @Override
    public int setCustomContentViewResourceId() {
        return R.layout.activity_yaoan_fankui;
    }
    public static Intent createIntent(Context context, EntityJingq jingq){
        Intent intent=new Intent(context,YaoAnFanKuiActivity.class);
        intent.putExtra("entityJingq", jingq);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParam();
        initView();
        initOperator();
    }

    public void initParam() {
        entityJingq=(EntityJingq)getIntent().getSerializableExtra("entityJingq");
        jingqHandlePresenter = new JingqHandlePresenterImpl(this);
        user = SharedTool.getInstance().getUserInfo(context);
        imageDatas= new ArrayList<>();
        String addImgUriStr = getResourceUri(R.drawable.ic_handlejingq_add_img);
        imageDatas.add(addImgUriStr);
        adapterImage=new CommonAdapter<String>(context, R.layout.item_jingq_handled_images, imageDatas){

            @Override
            protected void convert(ViewHolder holder, String s, int position) {

                Glide.with(mContext)
                        .load(s)
                        .crossFade()
                        .into((ImageView) holder.getView(R.id.iv_handled_jingq_image));


            }
        };

        adapterImage.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                String imgPath = adapterImage.getDatas().get(position);//获取子项的图片uri
                if(imgPath.equals( getResourceUri(R.drawable.ic_handlejingq_add_img))) {//如果子项图片是“添加图片”
                    addImage();
                }else{
                    if(imageDatas.contains(getResourceUri(R.drawable.ic_handlejingq_add_img))){//如果图片字符串集合包含“添加图片”的字符串
                        imageDatas.remove(getResourceUri(R.drawable.ic_handlejingq_add_img));//就删除“添加图片”
                    }
                    //进入图片编辑页面
                    startActivityForResult(JingqImageEditActivity.createIntent(context, imageDatas, position, true),REQ_CODE_EDIT_IMAGE);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

//    private void initImagePicker() {
//        ImagePicker imagePicker = ImagePicker.getInstance();
//        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
//        imagePicker.setShowCamera(true);                      //显示拍照按钮
//        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
//        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
//        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
//        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
//        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
//        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
//    }

    public void initView() {
        initActionBar(toolbar, tvTitle, "要案反馈");
        rvFeedbackMedia.setAdapter(adapterImage);
        rvFeedbackMedia.setLayoutManager(new GridLayoutManager(this, 4));//每行4个


//        rvFeedbackMedia.setHasFixedSize(true);

    }

    public void initOperator() {

    }

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

    @OnClick({R.id.tv_confirm_feedback,R.id.tv_add_media})
    public void onClick(View view){
        switch (view.getId()){

            case R.id.tv_confirm_feedback:
                String jh = user.getUserName();
                String simid = CommonUtil.getDeviceId(context);
                String jingyid = user.getUserId();
                String jingqid = entityJingq.getJingqid() + "";
                String filesPath = entityJingq.getFilesPath();
                String ajlb=entityJingq.getAjlb();
                String ajlx=entityJingq.getAjlx();
                String ajxl=entityJingq.getAjxl();
                String fknr=etFeedback.getText().toString().trim();
                String fksj=getTime();
                Log.d("wuzhengguan",fksj);
                jingqHandlePresenter.feedbackJingq(jingyid,jingqid,ajlb,ajlx,ajxl,fknr,fksj,filesPath,jh,simid);
                break;
            case R.id.tv_add_media:
                addImage();
                break;

            default:
                break;

        }


    }

    //获得当前年月日时分秒星期
    public String getTime(){
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
//        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        String mHour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));//时
        String mMinute = String.valueOf(c.get(Calendar.MINUTE));//分
        String mSecond = String.valueOf(c.get(Calendar.SECOND));//秒
        return mYear + "-" + mMonth + "-" + mDay+" "+mHour+":"+mMinute+":"+mSecond;
    }

    public void  addImage(){
        int count  = imgCountLimit -imageDatas.size();
        if(imageDatas.contains(getResourceUri(R.drawable.ic_handlejingq_add_img))){
            imageDatas.remove(getResourceUri(R.drawable.ic_handlejingq_add_img));
            count++;
        }
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setShowCamera(true);
        imagePicker.setSelectLimit(count);
        imagePicker.setCrop(false);
        Intent intent = new Intent(getApplicationContext(), ImageGridActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }





    /**
     * 协查反馈成功或失败
     */
    @Override
    public void feedBackSuccess() {
        ToastTool.getInstance().shortLength(context, "反馈成功", true);
        entityJingq.setJingqzt(EntityJingq.HADHANDLED);//处警完毕,资料提交成功3

    }

    @Override
    public void feedBackFailed(Exception e) {
        ToastTool.getInstance().shortLength(context, "反馈失败," + e.getMessage(), true);
        entityJingq.setJingqzt(4);//处警完毕,资料提交失败4
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        如果请求码是编辑图片
        if(resultCode==Activity.RESULT_OK && requestCode == REQ_CODE_EDIT_IMAGE){
            //从图片编辑页面获得图片路径字符串集合
            imageDatas.clear();
            imageDatas.addAll((List<String>) data.getSerializableExtra("imageDatas"));
//            Log.d("wuzhengguan","长度"+imageDatas.size());
            StringBuffer sb = new StringBuffer();
            boolean isAddSpliteChar = false;
            for (String s : imageDatas) {
                if (isAddSpliteChar) {
                    sb.append(",");
                }
                sb.append(s);
                isAddSpliteChar = true;
            }
            entityJingq.setFilesPath(sb.toString());//警情设置文件路径
            if(imageDatas.size()< imgCountLimit){//如果字符串集合长度小于最大限制就最后添加“添加图片”
                imageDatas.add(getResourceUri(R.drawable.ic_handlejingq_add_img));
            }
            adapterImage.notifyDataSetChanged();
        }
//请求码是选择图片
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS && requestCode ==REQUEST_CODE_SELECT){
            if(data!=null){

                final ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra
                        (ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null && images.size() > 0){
                    StringBuffer sb = new StringBuffer();
                    boolean isAddDivder = false;
                    for(int i = 0;i<images.size();i++){
                        ImageItem item = images.get(i);
                        final String path = item.path;
                        if(isAddDivder){
                            sb.append(",");
                        }
                        sb.append(path);
                        isAddDivder = true;
                        imageDatas.add(path);
                    }
                    entityJingq.setFilesPath(sb.toString());
                }
//如果选择的图片数量小于限制，就添加“添加图片”
                if(imageDatas.size()< imgCountLimit){
                    Resources r =context.getResources();
                    imageDatas.add(getResourceUri(R.drawable.ic_handlejingq_add_img));
                }
                adapterImage.notifyDataSetChanged();
            }else{
                Toast.makeText(getApplicationContext(), "未选择任何图片", Toast.LENGTH_SHORT).show();
            }

        }

    }

public String getResourceUri(int resId){
    Resources r=context.getResources();
    Uri uri=Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+"://"+r.getResourcePackageName(resId)+"/"+
            r.getResourceTypeName(resId)+"/"+r.getResourceEntryName(resId));
    return uri.toString();
}


}
