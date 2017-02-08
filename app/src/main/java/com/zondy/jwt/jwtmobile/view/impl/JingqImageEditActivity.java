package com.zondy.jwt.jwtmobile.view.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zondy.jwt.jwtmobile.R;
import com.zondy.jwt.jwtmobile.base.BaseActivity;
import com.zondy.jwt.jwtmobile.presenter.IJingqHandlePresenter;
import com.zondy.jwt.jwtmobile.presenter.impl.JingqHandlePresenterImpl;
import com.zondy.jwt.jwtmobile.view.IJingqImageEditView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 警情图片编辑页面
 */
public class JingqImageEditActivity extends BaseActivity implements IJingqImageEditView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.vp_image)
    ViewPager vpImage;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    IJingqHandlePresenter jingqHandlePresenter;
    //    BaseCommonAdapter<String> adapterImages;
    PagerAdapter adapterViewPager;
    List<String> imageDatas;
    List<View> imageViews;
    boolean isCanEdit;
    int currentPageviewIndex;

    public static Intent createIntent(Context context, List<String> imageDatas, int index,boolean isCanEdit) {
        Intent intent = new Intent(context, JingqImageEditActivity.class);
        intent.putExtra("imageDatas", (Serializable) imageDatas);
        intent.putExtra("isCanEdit", isCanEdit);
        intent.putExtra("index", index);
        return intent;
    }

    @Override
    public int setCustomContentViewResourceId() {
        return R.layout.activity_jingq_image_edit;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParam();
        initView();
        initOperator();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isCanEdit) {
            getMenuInflater().inflate(R.menu.jingq_image_edit, menu);
        }
        return true;
    }

    public List<View> createViewpagerDatas(List<String> imageDatas){
        List<View> views = new ArrayList<>();

        for (String s : imageDatas) {
            ImageView iv = new ImageView(context);
            Glide.with(context)
                    .load(s)
                    .placeholder(R.drawable.ic_aixin)
                    .crossFade()
                    .into(iv);
            views.add(iv);
        }
        return views;
    }

    public void initParam() {
        jingqHandlePresenter = new JingqHandlePresenterImpl(this);
        Intent intent = getIntent();
        imageDatas = (List<String>) intent.getSerializableExtra("imageDatas");
        isCanEdit = intent.getBooleanExtra("isCanEdit", false);
        currentPageviewIndex = intent.getIntExtra("index",0);
        imageViews = new ArrayList<>();
        imageViews.addAll(createViewpagerDatas(imageDatas));
       adapterViewPager = createViewPagerAdapter(imageViews);

    }

    public PagerAdapter createViewPagerAdapter(final List<View> imageViews){
        PagerAdapter adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return imageViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(imageViews.get(position));
                return imageViews.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                if(position > imageViews.size() - 1)return;
                container.removeView(imageViews.get(position));
            }

            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }
        };
        return  adapter;
    }

    public void initView() {
        initActionBar(toolbar, tvTitle, "警情详情");
        vpImage.setAdapter(adapterViewPager);
        vpImage.setCurrentItem(currentPageviewIndex);
    }


    public void initOperator() {
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(isCanEdit){
                    Intent intent = new Intent();
                    intent.putExtra("imageDatas",(Serializable) imageDatas);
                    setResult(RESULT_OK,intent);
                }
                this.finish();
                break;
            case R.id.delete:
                int i = vpImage.getCurrentItem();
                imageDatas.remove(i);
                if(imageDatas.size() < 1){
                    Intent intent = new Intent();
                    intent.putExtra("imageDatas",(Serializable) imageDatas);
                    setResult(RESULT_OK,intent);
                    this.finish();
                    break;
                }
//                imageViews.remove(i);

                imageViews.clear();
                imageViews.addAll(createViewpagerDatas(imageDatas));
//                currentPageviewIndex = 0;
//                vpImage.setCurrentItem(currentPageviewIndex);
                adapterViewPager.notifyDataSetChanged();
//                imageViews.clear();
//                imageViews.addAll(createViewpagerDatas(imageDatas));
//                adapterViewPager = null;
//                adapterViewPager = createViewPagerAdapter(imageViews);
//                adapterViewPager.notifyDataSetChanged();
//                vpImage.setCurrentItem(currentPageviewIndex);
//                vpImage.setAdapter(adapterViewPager);
                break;
            default:
                break;
        }
        return true;
    }


}
