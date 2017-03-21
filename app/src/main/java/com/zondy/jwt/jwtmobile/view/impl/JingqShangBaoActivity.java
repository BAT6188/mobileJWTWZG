package com.zondy.jwt.jwtmobile.view.impl;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zondy.jwt.jwtmobile.R;
import com.zondy.jwt.jwtmobile.base.BaseActivity;
import com.zondy.jwt.jwtmobile.base.BaseCommonAdapter;
import com.zondy.jwt.jwtmobile.base.BaseViewHolder;
import com.zondy.jwt.jwtmobile.entity.EntityTempJingq;
import com.zondy.jwt.jwtmobile.entity.EntityUser;
import com.zondy.jwt.jwtmobile.entity.EntityZD;
import com.zondy.jwt.jwtmobile.presenter.IJingqHandlePresenter;
import com.zondy.jwt.jwtmobile.presenter.impl.JingqHandlePresenterImpl;
import com.zondy.jwt.jwtmobile.util.CommonUtil;
import com.zondy.jwt.jwtmobile.util.GlideImageLoader;
import com.zondy.jwt.jwtmobile.util.SharedTool;
import com.zondy.jwt.jwtmobile.util.ToastTool;
import com.zondy.jwt.jwtmobile.view.IJingqShangBaoView;
import com.zondy.mapgis.android.mapview.MapView;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/9.
 */
public class JingqShangBaoActivity extends BaseActivity implements IJingqShangBaoView{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private MapView mapView;
    @BindView(R.id.sp_anjian_leibie)
    Spinner spAnjianLb;
    @BindView(R.id.sp_anjian_leixing)
    Spinner spAnjianLx;
    @BindView(R.id.sp_anjian_xilei)
    Spinner spAnjianXl;
    @BindView(R.id.et_sbnr)
    EditText etContent;
    @BindView(R.id.tv_shangbao)
    TextView tvShangbao;
    @BindView(R.id.tv_tianjia)
    TextView tvTianjia;
    @BindView(R.id.et_dz)
    EditText etAdress;
    @BindView(R.id.rv_media)
    RecyclerView rvShangbaoMedia;
    @BindView(R.id.scrollview)
    ScrollView mScrollView;

    IJingqHandlePresenter jingqHandlePresenter;
    EntityUser user;
    EntityTempJingq entityJingq;

    List<EntityZD> ajlbDatas;//案件类别字典类型集合
    BaseCommonAdapter<EntityZD> ajlbAdapter;
    List<EntityZD> ajlxDatas;//案件类型
    BaseCommonAdapter<EntityZD> ajlxAdapter;
    List<EntityZD> ajxlDatas;//案件细类
    BaseCommonAdapter<EntityZD> ajxlAdapter;
    List<EntityZD> allJingqTypes;//所有警情类型

    EntityZD anjlx;
    EntityZD anjlb;
    EntityZD anjxl;

    public static final int REQUEST_CODE_SELECT = 100;//点击“添加图片”进入图片选择的请求码
    private final int REQ_CODE_EDIT_IMAGE = 1;//点击图片进入编辑图片活动的请求码
    public final int imgCountLimit = 9;

    List<String> imageDatas;
    CommonAdapter<String> adapterImage;


    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, JingqShangBaoActivity.class);
        return intent;
    }

    @Override
    public int setCustomContentViewResourceId() {
        return R.layout.activity_jingq_shangbao;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParam();
        initView();
        initOperator();
    }

    public void initParam() {
        entityJingq=new EntityTempJingq();
        jingqHandlePresenter = new JingqHandlePresenterImpl(this);
        user = SharedTool.getInstance().getUserInfo(context);
        mapView=(MapView) findViewById(R.id.mapview);
        allJingqTypes = new ArrayList<EntityZD>();
        ajlbDatas = new ArrayList<EntityZD>();
        ajlxDatas = new ArrayList<EntityZD>();
        ajxlDatas = new ArrayList<EntityZD>();
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

    public void initView() {
        initActionBar(toolbar, tvTitle, "警情上报");
        mapView.loadFromFile(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "MapGIS/map/wuhan/wuhan.xml");
        mapView.setShowNorthArrow(false);
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
        rvShangbaoMedia.setAdapter(adapterImage);
        rvShangbaoMedia.setLayoutManager(new GridLayoutManager(this, 4));//每行4个
        initAnjianTypeSpinner();
    }

    public void initOperator() {
        String jh = user.getUserName();
        String simid = CommonUtil.getDeviceId(context);
        jingqHandlePresenter.queryAllJingqTypes(jh, simid,this);//查询所有警情类型,回调成功就 jingqHandleView.updateJingqTypes(jingqTypes);
    }

    /**
     * 为警情类型spinner设适配器
     */
    public void initAnjianTypeSpinner() {
        spAnjianLb.setAdapter(ajlbAdapter=new BaseCommonAdapter<EntityZD>(context,ajlbDatas,
                R.layout.item_jingqshangbao_sp) {
            @Override
            public void convert(BaseViewHolder holder, EntityZD item) {
                holder.setText(R.id.tv_value, item.getMc());
            }
        });
        spAnjianLx.setAdapter(ajlxAdapter=new BaseCommonAdapter<EntityZD>(context,ajlxDatas,
                R.layout.item_jingqshangbao_sp) {
            @Override
            public void convert(BaseViewHolder holder, EntityZD item) {
                holder.setText(R.id.tv_value, item.getMc());
            }
        });
        spAnjianXl.setAdapter(ajxlAdapter=new BaseCommonAdapter<EntityZD>(context,ajxlDatas,
                R.layout.item_jingqshangbao_sp) {
            @Override
            public void convert(BaseViewHolder holder, EntityZD item) {
                holder.setText(R.id.tv_value, item.getMc());
            }
        });

        spAnjianLb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                anjlb=ajlbAdapter.getItem(position);
                List<EntityZD> lxs=getChildJingqType(allJingqTypes,anjlb);//获取选中的案件类别对应的类型字典集合
                if(lxs!=null){
                    ajlxDatas.clear();
                    ajlxDatas.addAll(lxs);//填充类型数据集合
                    ajlxAdapter.notifyDataSetChanged();
                    spAnjianLx.setSelection(0);//显示第一个案件类型
                    anjlx=ajlxDatas.get(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                ajlxDatas.clear();
                ajlxAdapter.notifyDataSetChanged();
                ajxlDatas.clear();
                ajxlAdapter.notifyDataSetChanged();
            }
        });

      spAnjianLx.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              anjlx=ajlxAdapter.getItem(position);
              List<EntityZD> xls=getChildJingqType(allJingqTypes,anjlx);
              if(xls!=null){
                  ajxlDatas.clear();
                  ajxlDatas.addAll(xls);
                  ajxlAdapter.notifyDataSetChanged();
//                  spAnjianXl.setSelection(0);
//                  anjxl=ajxlDatas.get(0);
              }
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {
              ajxlDatas.clear();
              ajxlAdapter.notifyDataSetChanged();
          }
      });

       spAnjianXl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               anjxl=ajxlAdapter.getItem(position);
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });

    }


    /**
     * 根据selectedZD获取子警情类型
     *
     * @param parentZDDatas
     * @param selectedZD
     * @return
     */
    public List<EntityZD> getChildJingqType(List<EntityZD> parentZDDatas, EntityZD selectedZD) {
        List<EntityZD> chidrenZD = new ArrayList<EntityZD>();
        if (selectedZD != null && parentZDDatas.size() > 0) {
            if (parentZDDatas != null && parentZDDatas.size() > 0) {
                for (EntityZD zd : parentZDDatas) {
                    if (selectedZD.getBm().equals(zd.getPid())) {
                        chidrenZD.add(zd);
                    }
                }
            }
        }
        return chidrenZD;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.jingqsb_main,menu);
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            case R.id.myshangbao:
                startActivity(MyJingqSubmitActivity.createIntent(context));//进入我的上报记录列表页面
                break;
            default:
                break;
        }
        return true;
    }




    @Override
    public void handleJingqSuccess() {
        ToastTool.getInstance().shortLength(context, "处理成功", true);
    }


    @Override
    public void handleJingqFalied(Exception e) {
        ToastTool.getInstance().shortLength(context, "处理失败," + e.getMessage(), true);
    }

    /**
     * IJingqShangBaoView更新警情类型
     * @param jignqtypes
     */
    @Override
    public void updateJingqTypes(List<EntityZD> jignqtypes) {
        if (jignqtypes != null){
            allJingqTypes.clear();
            allJingqTypes.addAll(jignqtypes);
            List<EntityZD> lbs = getChildJingqType(allJingqTypes, new EntityZD("0", "", "0"));//获取案件类别集合
            if (lbs != null && lbs.size() > 0){
                ajlbDatas.clear();
                ajlbDatas.addAll(lbs);
                ajlbAdapter.notifyDataSetChanged();//填充案别类型集合
                spAnjianLb.setSelection(0);
            }

        }else{
            ToastTool.getInstance().shortLength(context, "获取所有警情类型失败", true);
        }
    }


@OnClick({R.id.tv_shangbao,R.id.tv_tianjia,R.id.btn_location})
public void onClick(View view){
    switch (view.getId()){
        case R.id.tv_shangbao:
            String jh = user.getUserName();
            String simid = CommonUtil.getDeviceId(context);
            String bjrxm=user.getCtname();
            String bjrjh=user.getUserName();
            String bjsj=getTime();
            String bjrdh=user.getPhone();
            String bjdz=etAdress.getText().toString().trim();
            String baojnr=etContent.getText().toString().trim();
            String bjlb=anjlb.getMc();
            String bjlx=anjlx.getMc();
            String bjxl=anjxl.getMc();
            String longitude = "120";
            String latitude = "31";
            String filesPath=entityJingq.getFilesPath();
            jingqHandlePresenter.shangbaoJingq(jh,simid,bjrxm,bjrjh,bjsj,bjrdh,bjdz,baojnr,bjlb,bjlx,bjxl,longitude,latitude,filesPath,this);
            break;
        case R.id.tv_tianjia:
//            ToastTool.getInstance().shortLength(context, "添加图片", true);
            addImage();
            break;
        case R.id.btn_location:
            ToastTool.getInstance().shortLength(context, "查找附近标准地址", true);
            break;
        default:
            break;

    }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        如果请求码是编辑图片
        if(resultCode== Activity.RESULT_OK && requestCode == REQ_CODE_EDIT_IMAGE){
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
Log.d("wuzhengguan","000000");
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

    public String getResourceUri(int resId){
        Resources r=context.getResources();
        Uri uri=Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+"://"+r.getResourcePackageName(resId)+"/"+
                r.getResourceTypeName(resId)+"/"+r.getResourceEntryName(resId));
        return uri.toString();
    }

}
