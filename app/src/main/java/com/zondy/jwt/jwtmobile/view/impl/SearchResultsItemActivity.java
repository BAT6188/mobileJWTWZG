package com.zondy.jwt.jwtmobile.view.impl;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zondy.jwt.jwtmobile.R;
import com.zondy.jwt.jwtmobile.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sheep on 2017/1/5.
 */

public class SearchResultsItemActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
    private int allpics;//此服务端返回的查询结果又多少张图片
    String[] dmtljs;//储存url图片地址的string数组
    private List<ImageView> vpLists;
    private LinearLayout ll_dot_group;//用来添加小圆点
    private ViewPager vp;
    private boolean toloop=true;
    private boolean isSwitchPager = false; //默认不切换
    private int previousPosition = 0; //默认为0
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            //更新当前viewpager的 要显示的当前条目
            vp.setCurrentItem(vp.getCurrentItem() + 1);
        }
    };
    @Override
    public int setCustomContentViewResourceId() {
        return R.layout.activity_searchresults_item;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String itemName = intent.getStringExtra("NAME");
        String dz=intent.getStringExtra("DZ");
        String dh=intent.getStringExtra("DH");

        String dmtlj=intent.getStringExtra("dmtlj");
        dmtljs=dmtlj.split(",");
        allpics=dmtljs.length;
//        String topPic=dmtljs[0];
//        String topPicCS=topPic.replace("61.183.129.187:4040","192.168.9.188:8080");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_searchresults_item);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingtoolbarlayout);
//        ImageView ivTop= (ImageView) findViewById(R.id.iv_searchresults_item);
        TextView tvMC= (TextView) findViewById(R.id.tv_searchresults_item_content_text);
        TextView tvDZ= (TextView) findViewById(R.id.tv_searchresults_item_dz);
        TextView tvDH= (TextView) findViewById(R.id.tv_searchresults_item_dh);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(itemName);
//        Glide.with(this).load(topPicCS).into(ivTop);
        if(TextUtils.isEmpty(dh)){
            tvDH.setText("电话：暂无相关信息");
        }
        tvMC.setText(itemName);
        tvDZ.setText("地址："+dz);
        initViewPages();
    }

    private void initViewPages() {
        vp= (ViewPager) findViewById(R.id.vp);
        vp.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        toloop = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        toloop = false;
                        break;
                    case MotionEvent.ACTION_UP:
                        toloop = true;
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        ll_dot_group= (LinearLayout) findViewById(R.id.ll_dot_group);
        initViewPagerData();
        vp.setAdapter(new ViewpagerAdapter());

        //设置当前viewpager要显示第几个条目
        int item = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % vpLists.size());
        vp.setCurrentItem(item);

        //把第一个小圆点设置为白色，显示第一个textview内容
        ll_dot_group.getChildAt(previousPosition).setEnabled(true);
        //设置viewpager滑动的监听事件
        vp.addOnPageChangeListener(this);
        //实现自动切换的功能
        new Thread() {
            public void run() {
                while (!isSwitchPager) {
                    SystemClock.sleep(3500);
                    //拿着我们创建的handler 发消息
                    if(toloop){
                        handler.sendEmptyMessage(0);
                    }
                }
            }
        }.start();
    }

    private void initViewPagerData() {
        vpLists=new ArrayList<>();
        ImageView iv;
        View dotView;
        for(int i=0;i<allpics;i++){
            iv=new ImageView(this);
            String pic=dmtljs[i];
            String picCS=pic.replace("61.183.129.187:4040","192.168.9.188:8080");
            Glide.with(this).load(picCS).into(iv);
            vpLists.add(iv);
            //准备小圆点的数据
            dotView = new View(getApplicationContext());
            dotView.setBackgroundResource(R.drawable.selector_dot);
            //设置小圆点的宽和高
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(22,22);
            //设置每个小圆点之间距离
            if (i != 0) {
                params.leftMargin = 20;
            }
            dotView.setLayoutParams(params);
            //设置小圆点默认状态
            dotView.setEnabled(false);
            //把dotview加入到线性布局中
            ll_dot_group.addView(dotView);
        }
    }

    /**
     * 定义数据适配器
     */
    private class ViewpagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        //是否复用当前view对象
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        //初始化每个条目要显示的内容
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //拿着position位置 % 集合.size
            int newposition = position % vpLists.size();
            //获取到条目要显示的内容imageview
            ImageView iv = vpLists.get(newposition);
            //要把 iv加入到 container 中
            container.addView(iv);
            return iv;
        }

        //销毁条目
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //移除条目
            container.removeView((View) object);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        //当Activity销毁的时候 把是否切换的标记置为true
        isSwitchPager = true;
        super.onDestroy();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //拿着position位置 % 集合.size
        int newposition = position % vpLists.size();
        //取出postion位置的小圆点 设置为true
        ll_dot_group.getChildAt(newposition).setEnabled(true);
        //把一个小圆点设置为false
        ll_dot_group.getChildAt(previousPosition).setEnabled(false);
        previousPosition = newposition;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
