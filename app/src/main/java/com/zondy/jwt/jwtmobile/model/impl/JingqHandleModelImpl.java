package com.zondy.jwt.jwtmobile.model.impl;

import android.text.TextUtils;
import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zondy.jwt.jwtmobile.base.MyApplication;
import com.zondy.jwt.jwtmobile.callback.IAcceptJingqCallback;
import com.zondy.jwt.jwtmobile.callback.IArriveConfirmCallback;
import com.zondy.jwt.jwtmobile.callback.IJingqHandleCallback;
import com.zondy.jwt.jwtmobile.callback.IQueryAllJingqKuaisclTypesCallback;
import com.zondy.jwt.jwtmobile.callback.IQueryAllJingqTypesCallback;
import com.zondy.jwt.jwtmobile.callback.IQueryJinqDatasCallback;
import com.zondy.jwt.jwtmobile.callback.IReloadJingqCallback;
import com.zondy.jwt.jwtmobile.callback.IRollbackJingqCallback;
import com.zondy.jwt.jwtmobile.entity.EntityJingq;
import com.zondy.jwt.jwtmobile.entity.EntityZD;
import com.zondy.jwt.jwtmobile.manager.UrlManager;
import com.zondy.jwt.jwtmobile.model.IJingqHandleModel;
import com.zondy.jwt.jwtmobile.util.GsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Response;

/**
 * Created by ywj on 2017/1/12 0012.
 */

public class JingqHandleModelImpl implements IJingqHandleModel {

    String msg = "";
    private static final String TAG = "JingqHandleModelImpl";



    @Override
    public void queryJingqDatas(String jh, String simid, final IQueryJinqDatasCallback queryJingqDatasCallback) {

        msg = "";
        String url = UrlManager.getSERVER() + UrlManager.queryJingqList;
        JSONObject param = new JSONObject();
        try {
            param.put("jh", jh);
            param.put("simid", simid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            OkHttpUtils.postString().url(url).content(param.toString()).mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .build().execute(new Callback<List<EntityJingq>>() {

                @Override
                public void inProgress(float progress, long total, int id) {
                    super.inProgress(progress, total, id);
                    queryJingqDatasCallback.inProgress(progress, total, id);
                }

                @Override
                public List<EntityJingq> parseNetworkResponse(Response response, int id) throws Exception {
                    String string = response.body().string();
                    JSONObject object = new JSONObject(string);
                    int resultCode = object.optInt("result");
                    msg = object.optString("message");
                    String dataStr = object.optString("jingqList");
                    switch (resultCode) {
                        case 1:
                            List<EntityJingq> jignqDatas = GsonUtil.json2BeanList(dataStr, EntityJingq.class);
                            return jignqDatas;
                        default:
                            return null;
                    }
                }

                @Override
                public void onError(Call call, Exception e, int id) {
                    queryJingqDatasCallback.onFailed(e);
                }

                @Override
                public void onResponse(List<EntityJingq> response, int id) {
                    if(response != null){
                        queryJingqDatasCallback.onSuccess(response);
                    }else{
                        if(MyApplication.IS_TEST_JINGQLIST){
                            String value = "[{\"baojdz\":\"板湖镇南郭村南郭大桥北侧50米处\",\"baojnr\":\"我的女儿步行至此处，被一辆电动三轮车撞伤，现人受伤我将其先送往医院，请派人处理。\",\"baojr\":\"汤如宝\",\"baojrdh\":\"13770176910\",\"baojsj\":\"2017-01-13 07:31:48\",\"bjlb\":null,\"bjlbdm\":\"210000\",\"bjlx\":null,\"bjlxdm\":\"210100\",\"bjxl\":null,\"bjxldm\":\"210105\",\"chujsj\":null,\"daodsj\":null,\"fanksj\":null,\"filesPath\":\"\",\"fknr\":\"电动三轮车撞到行人，致行人受伤。\",\"gxdwdm\":null,\"jiejh\":\"1046\",\"jingqid\":\"JJ320700_130185367\",\"jingqzt\":\"0\",\"latitude\":\"33.63876\",\"longitude\":\"119.59547\",\"zhipsj\":null,\"zjg_cjjg\":null,\"zjg_cjlbbm\":null,\"zjg_cjlbmc\":null,\"zjg_cjr\":null,\"zjg_cjsxbm\":null,\"zjg_cjsxmc\":null,\"zjg_fsdd\":null,\"zjg_sfcsbm\":null,\"zjg_sfcsmc\":null,\"zjg_sfsjsx\":null,\"zjg_sfsjxx\":null,\"zjg_ssqk\":null,\"zjg_tqqkbm\":null,\"zjg_tqqkmc\":null},{\"baojdz\":\"县城澳门路与上海路交汇处\",\"baojnr\":\"大约三天前的上午，我父亲骑电动车至这里时被一辆拖拉机撞伤了，当时没有报警，现双方在县医院因为治疗事情发生矛盾。请派人处理。\",\"baojr\":\"高德军\",\"baojrdh\":\"13814321112\",\"baojsj\":\"2017-01-13 07:31:48\",\"bjlb\":null,\"bjlbdm\":\"210000\",\"bjlx\":null,\"bjlxdm\":\"210100\",\"bjxl\":null,\"bjxldm\":\"210102\",\"chujsj\":null,\"daodsj\":null,\"fanksj\":null,\"filesPath\":\"\",\"fknr\":\"一辆电动车与一辆拖拉机相撞，电动车驾驶人受伤，伤者已送医院，事故待处理。\",\"gxdwdm\":null,\"jiejh\":\"1001\",\"jingqid\":\"20160809090638000005\",\"jingqzt\":\"0\",\"latitude\":\"33.76077\",\"longitude\":\"119.78141\",\"zhipsj\":null,\"zjg_cjjg\":null,\"zjg_cjlbbm\":null,\"zjg_cjlbmc\":null,\"zjg_cjr\":null,\"zjg_cjsxbm\":null,\"zjg_cjsxmc\":null,\"zjg_fsdd\":null,\"zjg_sfcsbm\":null,\"zjg_sfcsmc\":null,\"zjg_sfsjsx\":null,\"zjg_sfsjxx\":null,\"zjg_ssqk\":null,\"zjg_tqqkbm\":null,\"zjg_tqqkmc\":null}]";
                            response = GsonUtil.json2BeanList(value,EntityJingq.class);
                            queryJingqDatasCallback.onSuccess(response);
                        }else{
                            queryJingqDatasCallback.onFailed(new Exception("暂无数据"));
                        }
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void reloadJingq(String jingqid, String jh, String simid, final IReloadJingqCallback reloadJingqCallback) {
        String url = UrlManager.getSERVER() + UrlManager.queryJingqByJingqid;
        msg = "";
        JSONObject param = new JSONObject();
        try {
            param.put("jingqid", jingqid);
            param.put("jh", jh);
            param.put("simid", simid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpUtils.postString().url(url).content(param.toString()).mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build().execute(new Callback<EntityJingq>(){
            @Override
            public EntityJingq parseNetworkResponse(Response response, int id) throws Exception {
                String string = response.body().string();
                JSONObject object = new JSONObject(string);
                int resultCode = object.optInt("result");
                msg = object.optString("message");
                String jingqStr = object.optString("jingqInfo");
                EntityJingq jingq = null;
                switch (resultCode){
                    case 1:
                        jingq = GsonUtil.json2Bean(jingqStr,EntityJingq.class);
                        break;
                    default:
                        break;
                }
                return jingq;
            }

            @Override
            public void onError(Call call, Exception e, int id) {

                reloadJingqCallback.loadJingqFailed(e);
            }

            @Override
            public void onResponse(EntityJingq response, int id) {
                if(response != null){
                    reloadJingqCallback.loadJingqSuccess(response);
                }else {
                    reloadJingqCallback.loadJingqFailed(new Exception(msg));
                }
            }
        });

    }

    @Override
    public void acceptJingq(String jingqid, String carid, String jh, String simid, final IAcceptJingqCallback acceptJingqCallback) {
        String url = UrlManager.getSERVER() + UrlManager.receiveJingqingConfirm;
        JSONObject param = new JSONObject();
        msg = "";
        try {
            param.put("jingqid", jingqid);
            param.put("carid", carid);
            param.put("jh", jh);
            param.put("simid", simid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpUtils.postString().url(url).content(param.toString()).mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build().execute(new Callback<Boolean>() {
            @Override
            public Boolean parseNetworkResponse(Response response, int id) throws Exception {
                String string = response.body().string();
                JSONObject object = new JSONObject(string);
                int resultCode = object.optInt("result");
                msg = object.optString("message");
                switch (resultCode) {
                    case 1:
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {

                acceptJingqCallback.acceptJingqFailed(e);
            }

            @Override
            public void onResponse(Boolean response, int id) {
                if (response) {
                    acceptJingqCallback.acceptJingqSuccess();
                } else {
                    acceptJingqCallback.acceptJingqFailed(new Exception(msg));

                }
            }
        });


    }

    @Override
    public void rollbackJingq(String jingqid, String jh, String simid, final IRollbackJingqCallback rollbackJingqCallback) {
        String url = UrlManager.getSERVER() + UrlManager.receiveJingqingReBack;
        JSONObject param = new JSONObject();
        msg = "";
        try {
            param.put("jingqid", jingqid);
            param.put("jh", jh);
            param.put("simid", simid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpUtils.postString().url(url).content(param.toString()).mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build().execute(new Callback<Boolean>() {
            @Override
            public Boolean parseNetworkResponse(Response response, int id) throws Exception {
                String string = response.body().string();
                JSONObject object = new JSONObject(string);
                int resultCode = object.optInt("result");
                msg = object.optString("message");
                switch (resultCode) {
                    case 1:
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {

                rollbackJingqCallback.rollbackJingqFailed(e);
            }

            @Override
            public void onResponse(Boolean response, int id) {
                if (response) {
                    rollbackJingqCallback.rollbackJingqSuccess();
                } else {
                    rollbackJingqCallback.rollbackJingqFailed(new Exception(msg));

                }
            }
        });

    }

    @Override
    public void arriveConfirm(String jingyid, String jingqid, String longitude, String latitude, String jh, String simid, final IArriveConfirmCallback arriveConfirmCallback) {
        String url = UrlManager.getSERVER() + UrlManager.reachConfirm;
        JSONObject param = new JSONObject();
        msg = "";
        try {
            param.put("jingyid", jingyid);
            param.put("jingqid", jingqid);
            param.put("longitude", longitude);
            param.put("latitude", latitude);
            param.put("jh", jh);
            param.put("simid", simid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpUtils.postString().url(url).content(param.toString()).mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build().execute(new Callback<Boolean>() {
            @Override
            public Boolean parseNetworkResponse(Response response, int id) throws Exception {
                String string = response.body().string();
                JSONObject object = new JSONObject(string);
                int resultCode = object.optInt("result");
                msg = object.optString("message");
                String dataStr = object.optString("jingqList");
                switch (resultCode) {
                    case 1:
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {

                arriveConfirmCallback.arriveFailed(e);
            }

            @Override
            public void onResponse(Boolean response, int id) {
                if (response) {
                    arriveConfirmCallback.arriveSuccess();
                } else {
                    arriveConfirmCallback.arriveFailed(new Exception(msg));

                }
            }
        });


    }






    @Override
    public void submitJingq(String jingyid, String jingqid, String chuljg, String bjlb, String bjlx, String bjxl, String chuljgms, String filesPath, String jh, String simid, final IJingqHandleCallback jingqHandleCallback) {
        String url = UrlManager.getSERVER() + UrlManager.handleJingqing;
        msg = "";
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("jingyid", jingyid);
            jsonParam.put("jingqid", jingqid);
            jsonParam.put("chuljg", chuljg);
            jsonParam.put("bjlb", bjlb);
            jsonParam.put("bjlx", bjlx);
            jsonParam.put("bjxl", bjxl);
            jsonParam.put("chuljgms", chuljgms);
            jsonParam.put("filesPath", filesPath);
            jsonParam.put("jh", jh);
            jsonParam.put("simid", simid);
        } catch (Exception e) {
            Log.e(TAG, "submitJingq: ");
        }
        Map<String,String> param = new HashMap<>();
        param.put("strBody",jsonParam.toString());

        PostFormBuilder builder = OkHttpUtils.post();
        String[] files = filesPath.split(",");
        Log.i(TAG, "submitJingq: " + files.length);
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                if (!TextUtils.isEmpty(files[i])) {
                    File file = new File(files[i]);
                    String fileName = file.getName();
                    String fileId = "mFile";
                    builder.addFile(fileId, fileName, file);
                }

            }
        }

        builder.url(url).params(param)
                .build().execute(new Callback<Boolean>() {

            @Override
            public Boolean parseNetworkResponse(Response response, int id) throws Exception {
                String string = response.body().string();
                JSONObject object = new JSONObject(string);
                int resultCode = object.optInt("result");
                msg = object.optString("message");
                boolean result = false;
                switch (resultCode) {
                    case 1:
                        result = true;
                        break;
                    default:
                        break;
                }
                return result;
            }

            @Override
            public void onError(Call call, Exception e, int id) {

                jingqHandleCallback.jingqHandleFailed(e);
            }

            @Override
            public void onResponse(Boolean response, int id) {

                if (response) {
                    jingqHandleCallback.jingqHandleSuccess();
                } else {
                    jingqHandleCallback.jingqHandleFailed(new Exception(msg));

                }
            }
        });
    }

    @Override
    public void queryAllJingqTypes(String jh, String simid, final IQueryAllJingqTypesCallback queryAllJingqTypesCallback) {
        String url = UrlManager.getSERVER() + UrlManager.queryJingqTypeZD;
        JSONObject param = new JSONObject();
        msg = "";
        try {
            param.put("jh", jh);
            param.put("simid", simid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpUtils.postString().url(url).content(param.toString()).mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build().execute(new Callback<List<EntityZD>>() {
            @Override
            public List<EntityZD> parseNetworkResponse(Response response, int id) throws Exception {
                String string = response.body().string();
                JSONObject object = new JSONObject(string);
                int resultCode = object.optInt("result");
                msg = object.optString("message");
                String resultStr = object.optString("list");
                List<EntityZD> result = null;
                switch (resultCode) {
                    case 1:
                        result = GsonUtil.json2BeanList(resultStr, EntityZD.class);
                        break;
                    default:
                        break;
                }
                return result;
            }

            @Override
            public void onError(Call call, Exception e, int id) {

                queryAllJingqTypesCallback.jingqHandleFailed(e);
            }

            @Override
            public void onResponse(List<EntityZD> response, int id) {

                if (response != null) {
                    queryAllJingqTypesCallback.jingqHandleSuccess(response);
                } else {
                    queryAllJingqTypesCallback.jingqHandleFailed(new Exception(msg));

                }
            }
        });
    }

    @Override
    public void queryAllJingqKuaisclTypes(String jh, String simid, final IQueryAllJingqKuaisclTypesCallback queryAllJingqKuaisclTypesCallback) {
        String url = UrlManager.getSERVER() + UrlManager.jingqingksxz;
        JSONObject param = new JSONObject();
        msg = "";
        try {
            param.put("jh", jh);
            param.put("simid", simid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpUtils.postString().url(url).content(param.toString()).mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build().execute(new Callback<List<EntityZD>>() {
            @Override
            public List<EntityZD> parseNetworkResponse(Response response, int id) throws Exception {
                String string = response.body().string();
                JSONObject object = new JSONObject(string);
                int resultCode = object.optInt("result");
                msg = object.optString("message");
                String resultStr = object.optString("list");
                List<EntityZD> result = null;
                switch (resultCode) {
                    case 1:
                        result = GsonUtil.json2BeanList(resultStr, EntityZD.class);
                        break;
                    default:
                        break;
                }
                return result;
            }

            @Override
            public void onError(Call call, Exception e, int id) {

                queryAllJingqKuaisclTypesCallback.queryJingqKuaisclFailed(e);
            }

            @Override
            public void onResponse(List<EntityZD> response, int id) {

                if (response != null) {
                    queryAllJingqKuaisclTypesCallback.queryJingqKuaisclSuccess(response);
                } else {
                    queryAllJingqKuaisclTypesCallback.queryJingqKuaisclFailed(new Exception(msg));

                }
            }
        });
    }

}
