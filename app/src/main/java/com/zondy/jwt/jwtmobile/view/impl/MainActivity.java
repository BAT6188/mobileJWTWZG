package com.zondy.jwt.jwtmobile.view.impl;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zondy.jwt.jwtmobile.R;
import com.zondy.jwt.jwtmobile.base.BaseActivity;
import com.zondy.jwt.jwtmobile.entity.EntityJingq;
import com.zondy.jwt.jwtmobile.entity.EntityUser;
import com.zondy.jwt.jwtmobile.util.SharedTool;
import com.zondy.jwt.jwtmobile.util.ToastTool;
import com.zondy.mapgis.android.mapview.MapView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by sheep on 2017/1/5.
 */

public class MainActivity extends BaseActivity {
    @BindView(R.id.tv_ll_name)
    TextView tvName;
    @BindView(R.id.tv_ll_jh)
    TextView tvJh;
    private DrawerLayout drawerLayout;
    private MapView mapView;
    private FloatingActionButton fab;
//    private LinearLayout llZhcx;
//    private LinearLayout llTxl;
//    private LinearLayout llSz;
//    @BindView(R.id.ll_main_jingqcl)
//    LinearLayout llMainJingqcl;//警情处理
    @BindView(R.id.rv_menu)
    RecyclerView rv_menu;//菜单
    List<EntityMenu> menus;
    CommonAdapter<EntityMenu> adapterMenuList;
    @Override
    public int setCustomContentViewResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParams();
        initViews();
    }

    private void initParams() {
        mapView = (MapView) findViewById(R.id.mapview);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        menus = new ArrayList<>();
        menus.add(new EntityMenu("综合查询",R.drawable.ic_zonghss));
        menus.add(new EntityMenu("通讯录",R.drawable.ic_tongxl));
        menus.add(new EntityMenu("警情处理",R.drawable.ic_shezhi));
        menus.add(new EntityMenu("设置",R.drawable.ic_shezhi));
        menus.add(new EntityMenu("盘查比对",R.drawable.ic_shezhi));
        menus.add(new EntityMenu("数据采集",R.drawable.ic_shezhi));
        adapterMenuList = new CommonAdapter<EntityMenu>(context,R.layout.item_main_menu,menus) {
            @Override
            protected void convert(ViewHolder holder, EntityMenu s, int position) {
                TextView tv = holder.getView(R.id.tv_value);
                tv.setText(s.getMenuTitle());
                ImageView iv = holder.getView(R.id.iv_value);
                Glide.with(context).load(s.getMenuResourceId()).into(iv);
//                Drawable drawable= getResources().getDrawable(s.getMenuResourceId());
//                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//                tv.setCompoundDrawables(drawable,null,null,null);

            }



        };
        adapterMenuList.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                drawerLayout.closeDrawers();
                TextView tv = (TextView) view.findViewById(R.id.tv_value);

                String menuTxt = tv.getText().toString().trim();
                    if("综合查询".equals(menuTxt)){
                        ToastTool.getInstance().shortLength(context,menuTxt,true);

                        Intent intent = new Intent(MainActivity.this, ScrollActivity.class);
                        startActivity(intent);
                        return;
                    }
                if("通讯录".equals(menuTxt)){
                    ToastTool.getInstance().shortLength(context,menuTxt,true);

                    Intent intent=new Intent(MainActivity.this,ContactsActivity.class);
                    startActivity(intent);
                    return;
                }
                if("警情处理".equals(menuTxt)){
                    ToastTool.getInstance().shortLength(context,menuTxt,true);
                    startActivity(JingqListActivity.createIntent(context)); return;
                }
                if("设置".equals(menuTxt)){
                    ToastTool.getInstance().shortLength(context,menuTxt,true);
                    Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                    startActivity(intent);  return;
                }
                if("盘查比对".equals(menuTxt)){
                    ToastTool.getInstance().shortLength(context,menuTxt,true);
                    return;
                }
                if("数据采集".equals(menuTxt)){
                    ToastTool.getInstance().shortLength(context,menuTxt,true);
                    return;
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void initViews() {
        EntityUser user = SharedTool.getInstance().getUserInfo(MainActivity.this);
        if (user != null) {
            tvName.setText(user.getCtname());
            tvJh.setText("警号："+user.getUserName());
        }
        mapView.loadFromFile(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "MapGIS/map/wuhan/wuhan.xml");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
//        llZhcx.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, ScrollActivity.class);
//                startActivity(intent);
//                drawerLayout.closeDrawers();
//            }
//        });
//        llTxl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(MainActivity.this,ContactsActivity.class);
//                startActivity(intent);
//                drawerLayout.closeDrawers();
//            }
//        });
//        llSz.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
//                startActivity(intent);
//                drawerLayout.closeDrawers();
//            }
//        });
//        llMainJingqcl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ToastTool.getInstance().shortLength(context,"警情处理",true);
//                startActivity(JingqListActivity.createIntent(context));
//            }
//        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_menu.setLayoutManager(linearLayoutManager);
        rv_menu.setAdapter(adapterMenuList);
    }

    class EntityMenu{
        String menuTitle;
        int menuResourceId;

        public EntityMenu( String menuTitle,int menuResourceId) {
            this.menuResourceId = menuResourceId;
            this.menuTitle = menuTitle;
        }

        public int getMenuResourceId() {
            return menuResourceId;
        }

        public void setMenuResourceId(int menuResourceId) {
            this.menuResourceId = menuResourceId;
        }

        public String getMenuTitle() {
            return menuTitle;
        }

        public void setMenuTitle(String menuTitle) {
            this.menuTitle = menuTitle;
        }
    }
}
