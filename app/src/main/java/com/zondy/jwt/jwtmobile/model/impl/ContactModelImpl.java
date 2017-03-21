package com.zondy.jwt.jwtmobile.model.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zondy.jwt.jwtmobile.callback.IQueryContactsByZZJGCallback;
import com.zondy.jwt.jwtmobile.callback.IQueryZZJGCallback;
import com.zondy.jwt.jwtmobile.entity.EntityContact;
import com.zondy.jwt.jwtmobile.entity.EntityUser;
import com.zondy.jwt.jwtmobile.entity.EntityZD;
import com.zondy.jwt.jwtmobile.manager.UrlManager;
import com.zondy.jwt.jwtmobile.model.IContactModel;
import com.zondy.jwt.jwtmobile.util.CommonUtil;
import com.zondy.jwt.jwtmobile.util.SharedTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;

/**
 * Created by sheep on 2017/1/17.
 */

public class ContactModelImpl implements IContactModel {
//    @Override
//    public void queryZZJG(Context context,String zdlx, final IQueryZZJGCallback queryZZJGCallback) {
//
//        String url= UrlManager.getSERVER()+UrlManager.queryZZJGZD;
//        JSONObject param=new JSONObject();
//        try {
//            param.put("jh", SharedTool.getInstance().getUserInfo(context).getUserName());
//            param.put("simid", CommonUtil.getDeviceId(context));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        try {
//            OkHttpUtils.postString().url(url).content(param.toString()).mediaType(MediaType.parse("application/json; charset=utf-8"))
//                    .build().execute(new Callback() {
//                @Override
//                public Object parseNetworkResponse(Response response, int id) throws Exception {
//                    String string=response.body().string();
//                    JSONObject object=new JSONObject(string);
//                    int resultCode=object.optInt("result");
//                    String msg=object.optString("message");
//                    switch (resultCode){
//                        case 1:
//                            List<EntityZD> allEntitys=new ArrayList<EntityZD>();
//                            JSONArray zdArray=null;
//                            zdArray=object.optJSONArray("list");
//                            if(zdArray!=null){
//                                for(int i=0;i<zdArray.length();i++){
//                                    JSONObject zzjgObj=zdArray.getJSONObject(i);
//                                    String obj=zzjgObj.toString();
//                                    EntityZD zzjg=new Gson().fromJson(obj,EntityZD.class);
//                                    allEntitys.add(zzjg);
//                                }
//                            }
//                            return allEntitys;
//                        default:
//                            return msg;
//                    }
//
//                }
//
//                @Override
//                public void onError(Call call, Exception e, int id) {
//
//                }
//
//                @Override
//                public void onResponse(Object response, int id) {
//                    if(response instanceof String){
//                        String msg= (String) response;
//                        queryZZJGCallback.queryUnSuccessed(msg);
//                    }else {
//                        List<EntityZD> allEntitys= (List<EntityZD>) response;
//                        queryZZJGCallback.querySuccessed(allEntitys);
//                    }
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void queryZZJG(Context context, String zdlx, final IQueryZZJGCallback queryZZJGCallback) {
        String url = UrlManager.getSERVER() + UrlManager.queryZZJGZD;
        JSONObject param = new JSONObject();
        try {
            param.put("jh", SharedTool.getInstance().getUserInfo(context).getUserName());
            param.put("simid", CommonUtil.getDeviceId(context));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            OkGo.post(url)
                    .upString(param.toString())
                    .cacheKey("queryZZJG")
                    .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
                    .execute(new StringCallback() {
                        @Override
                        public void onCacheSuccess(String s, Call call) {
                            super.onCacheSuccess(s, call);
                            Log.i("sheep", "queryZZJG,读取的缓存");
                            Log.i("sheep", "s=" + s);
                            JSONObject object = null;
                            try {
                                object = new JSONObject(s);
                                int resultCode = object.optInt("result");
                                String msg = object.optString("message");
                                switch (resultCode) {
                                    case 1:
                                        List<EntityZD> allEntitys = new ArrayList<EntityZD>();
                                        JSONArray zdArray = null;
                                        zdArray = object.optJSONArray("list");
                                        if (zdArray != null) {
                                            for (int i = 0; i < zdArray.length(); i++) {
                                                JSONObject zzjgObj = zdArray.getJSONObject(i);
                                                String obj = zzjgObj.toString();
                                                EntityZD zzjg = new Gson().fromJson(obj, EntityZD.class);
                                                allEntitys.add(zzjg);
                                            }
                                        }
                                        queryZZJGCallback.querySuccessed(allEntitys);
                                        break;
                                    default:
                                        queryZZJGCallback.queryUnSuccessed(msg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            Log.i("sheep", "response=" + response.toString());
                            Log.i("sheep", "s=" + s);
                            JSONObject object = null;
                            try {
                                object = new JSONObject(s);
                                int resultCode = object.optInt("result");
                                String msg = object.optString("message");
                                switch (resultCode) {
                                    case 1:
                                        List<EntityZD> allEntitys = new ArrayList<EntityZD>();
                                        JSONArray zdArray = null;
                                        zdArray = object.optJSONArray("list");
                                        if (zdArray != null) {
                                            for (int i = 0; i < zdArray.length(); i++) {
                                                JSONObject zzjgObj = zdArray.getJSONObject(i);
                                                String obj = zzjgObj.toString();
                                                EntityZD zzjg = new Gson().fromJson(obj, EntityZD.class);
                                                allEntitys.add(zzjg);
                                            }
                                        }
                                        queryZZJGCallback.querySuccessed(allEntitys);
                                        break;
                                    default:
                                        queryZZJGCallback.queryUnSuccessed(msg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void queryContactsByZZJG(Context context, final String zzjg, final IQueryContactsByZZJGCallback queryContactsByZZJGCallback) {
        String url = UrlManager.getSERVER() + "/ZhongheQuery!queryConnectionByDwdm";
        JSONObject param = new JSONObject();
        try {
            param.put("dwdm", zzjg);
            param.put("jh", SharedTool.getInstance().getUserInfo(context).getUserName());
            param.put("simid", CommonUtil.getDeviceId(context));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            OkGo.post(url)
                    .upString(param.toString())
                    .cacheKey("queryConnectionByDwdm："+zzjg)
                    .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            Log.i("sheep","response="+response.toString());
                            Log.i("sheep","s="+s);
                            JSONObject object = null;
                            try {
                                object = new JSONObject(s);
                                int resultCode = object.optInt("result");
                                String msg = object.optString("message");
                                switch (resultCode) {
                                    case 1:
                                        List<EntityContact> allEntitys = new ArrayList<EntityContact>();
                                        JSONArray zdArray = null;
                                        zdArray = object.optJSONArray("connectionList");
                                        if (zdArray != null) {
                                            for (int i = 0; i < zdArray.length(); i++) {
                                                JSONObject zzjgObj = zdArray.getJSONObject(i);
                                                String obj = zzjgObj.toString();
                                                EntityContact contact = new Gson().fromJson(obj, EntityContact.class);
                                                allEntitys.add(contact);
                                            }
                                        }
                                        queryContactsByZZJGCallback.querySuccessed(allEntitys);
                                        break;
                                    default:
                                        queryContactsByZZJGCallback.queryUnSuccessed(msg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onCacheSuccess(String s, Call call) {
                            super.onCacheSuccess(s, call);
                            Log.i("sheep","queryConnectionByDwdm："+zzjg+",读取了缓存");
                            Log.i("sheep","s="+s);
                            JSONObject object = null;
                            try {
                                object = new JSONObject(s);
                                int resultCode = object.optInt("result");
                                String msg = object.optString("message");
                                switch (resultCode) {
                                    case 1:
                                        List<EntityContact> allEntitys = new ArrayList<EntityContact>();
                                        JSONArray zdArray = null;
                                        zdArray = object.optJSONArray("connectionList");
                                        if (zdArray != null) {
                                            for (int i = 0; i < zdArray.length(); i++) {
                                                JSONObject zzjgObj = zdArray.getJSONObject(i);
                                                String obj = zzjgObj.toString();
                                                EntityContact contact = new Gson().fromJson(obj, EntityContact.class);
                                                allEntitys.add(contact);
                                            }
                                        }
                                        queryContactsByZZJGCallback.querySuccessed(allEntitys);
                                        break;
                                    default:
                                        queryContactsByZZJGCallback.queryUnSuccessed(msg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    @Override
//    public void queryContactsByZZJG(Context context, String zzjg, final IQueryContactsByZZJGCallback queryContactsByZZJGCallback) {
//        String url = UrlManager.getSERVER() + "/ZhongheQuery!queryConnectionByDwdm";
//        JSONObject param = new JSONObject();
//        try {
//            param.put("dwdm", zzjg);
//            param.put("jh", SharedTool.getInstance().getUserInfo(context).getUserName());
//            param.put("simid", CommonUtil.getDeviceId(context));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        try {
//            OkHttpUtils.postString().url(url).content(param.toString()).mediaType(MediaType.parse("application/json; charset=utf-8"))
//                    .build().execute(new Callback() {
//
//                @Override
//                public Object parseNetworkResponse(Response response, int id) throws Exception {
//                    String string = response.body().string();
//                    JSONObject object = new JSONObject(string);
//                    int resultCode = object.optInt("result");
//                    String msg = object.optString("message");
//                    switch (resultCode) {
//                        case 1:
//                            List<EntityContact> allEntitys = new ArrayList<EntityContact>();
//                            JSONArray zdArray = null;
//                            zdArray = object.optJSONArray("connectionList");
//                            if (zdArray != null) {
//                                for (int i = 0; i < zdArray.length(); i++) {
//                                    JSONObject zzjgObj = zdArray.getJSONObject(i);
//                                    String obj = zzjgObj.toString();
//                                    EntityContact contact = new Gson().fromJson(obj, EntityContact.class);
//                                    allEntitys.add(contact);
//                                }
//                            }
//                            return allEntitys;
//                        default:
//                            return msg;
//                    }
//
//                }
//
//                @Override
//                public void onError(Call call, Exception e, int id) {
//
//                }
//
//                @Override
//                public void onResponse(Object response, int id) {
//                    if (response instanceof String) {
//                        String msg = (String) response;
//                        queryContactsByZZJGCallback.queryUnSuccessed(msg);
//                    } else {
//                        List<EntityContact> contacts = (List<EntityContact>) response;
//                        queryContactsByZZJGCallback.querySuccessed(contacts);
//                    }
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


}
