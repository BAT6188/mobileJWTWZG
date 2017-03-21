package com.zondy.jwt.jwtmobile.model.impl;

import android.text.TextUtils;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zondy.jwt.jwtmobile.base.MyApplication;
import com.zondy.jwt.jwtmobile.callback.IAcceptJingqCallback;
import com.zondy.jwt.jwtmobile.callback.IArriveConfirmCallback;
import com.zondy.jwt.jwtmobile.callback.IConfirmReceiveMsgCallBack;
import com.zondy.jwt.jwtmobile.callback.IJingqHandleCallback;
import com.zondy.jwt.jwtmobile.callback.IJingqShangbaoCallback;
import com.zondy.jwt.jwtmobile.callback.IQueryAllJingqKuaisclTypesCallback;
import com.zondy.jwt.jwtmobile.callback.IQueryAllJingqTypesCallback;
import com.zondy.jwt.jwtmobile.callback.IQueryFeedbackRecordCallback;
import com.zondy.jwt.jwtmobile.callback.IQueryJinqDatasCallback;
import com.zondy.jwt.jwtmobile.callback.IQueryMySubmitRecordsCallback;
import com.zondy.jwt.jwtmobile.callback.IQueryXiectsDatasCallback;
import com.zondy.jwt.jwtmobile.callback.IReloadJingqCallback;
import com.zondy.jwt.jwtmobile.callback.IRollbackJingqCallback;
import com.zondy.jwt.jwtmobile.callback.IYaoAnFeedbackCallBack;
import com.zondy.jwt.jwtmobile.entity.EntityFeedbackRecord;
import com.zondy.jwt.jwtmobile.entity.EntityJingq;
import com.zondy.jwt.jwtmobile.entity.EntityTempJingq;
import com.zondy.jwt.jwtmobile.entity.EntityZD;
import com.zondy.jwt.jwtmobile.manager.UrlManager;
import com.zondy.jwt.jwtmobile.model.IJingqHandleModel;
import com.zondy.jwt.jwtmobile.util.GsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
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


    /**
     * 利用警号和设备id查询大体警情列表数据
     * @param jh
     * @param simid
     * @param queryJingqDatasCallback
     */
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

    /**
     * 利用警情id重新加载警情（未处理）
     * @param jingqid
     * @param jh
     * @param simid
     * @param reloadJingqCallback
     */
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

    /**
     * 接收警情
     * @param jingqid
     * @param carid
     * @param jh
     * @param simid
     * @param acceptJingqCallback
     */
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

    /**
     * 回退警情
     * @param jingqid
     * @param jh
     * @param simid
     * @param rollbackJingqCallback
     */
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

    /**
     * 到场确认
     * @param jingyid
     * @param jingqid
     * @param longitude
     * @param latitude
     * @param jh
     * @param simid
     * @param arriveConfirmCallback
     */
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

    /**
     * 提交警情
     * @param jingyid 警员警号
     * @param jingqid 警情编号
     * @param chuljg 处理结果
     * @param bjlb 报警类别
     * @param bjlx 报警类型
     * @param bjxl 报警细类
     * @param chuljgms 处理结果描述
     * @param filesPath 多媒体路径全名字串
     * @param jh 警号
     * @param simid 客户端设备串号
     * @param jingqHandleCallback 警情处理回调接口
     */

    @Override
    public void submitJingq(String jingyid, String jingqid, String chuljg, String bjlb, String bjlx, String bjxl, String chuljgms, String filesPath, String jh, String simid, final IJingqHandleCallback jingqHandleCallback) {
//        String url = UrlManager.getSERVER() + UrlManager.handleJingqing;
//        msg = "";
//        JSONObject jsonParam = new JSONObject();
//        try {
//            jsonParam.put("jingyid", jingyid);
//            jsonParam.put("jingqid", jingqid);
//            jsonParam.put("chuljg", chuljg);
//            jsonParam.put("bjlb", bjlb);
//            jsonParam.put("bjlx", bjlx);
//            jsonParam.put("bjxl", bjxl);
//            jsonParam.put("chuljgms", chuljgms);
//            jsonParam.put("filesPath", filesPath);
//            jsonParam.put("jh", jh);
//            jsonParam.put("simid", simid);
//        } catch (Exception e) {
//            Log.e(TAG, "submitJingq: ");
//        }
//        Map<String,String> param = new HashMap<>();
//        param.put("strBody",jsonParam.toString());
//
//        PostFormBuilder builder = OkHttpUtils.post();
//        String[] files = filesPath.split(",");
//        Log.i(TAG, "submitJingq: " + files.length);
//        if (files != null && files.length > 0) {
//            for (int i = 0; i < files.length; i++) {
//                if (!TextUtils.isEmpty(files[i])) {
//                    File file = new File(files[i]);
//                    String fileName = file.getName();
//                    String fileId = "mFile";
//                    builder.addFile(fileId, fileName, file);
//                }
//
//            }
//        }
//
//        builder.url(url).params(param)
//                .build().execute(new Callback<Boolean>() {
//
//            @Override
//            public Boolean parseNetworkResponse(Response response, int id) throws Exception {
//                String string = response.body().string();
//                JSONObject object = new JSONObject(string);
//                int resultCode = object.optInt("result");
//                msg = object.optString("message");
//                boolean result = false;
//                switch (resultCode) {
//                    case 1:
//                        result = true;
//                        break;
//                    default:
//                        break;
//                }
//                return result;
//            }
//
//            @Override
//            public void onError(Call call, Exception e, int id) {
//
//                jingqHandleCallback.jingqHandleFailed(e);
//            }
//
//            @Override
//            public void onResponse(Boolean response, int id) {
//
//                if (response) {
//                    jingqHandleCallback.jingqHandleSuccess();
//                } else {
//                    jingqHandleCallback.jingqHandleFailed(new Exception(msg));
//
//                }
//            }
//        });
        String url = UrlManager.getSERVER() + UrlManager.handleJingqing;
        List<File> files = new ArrayList<>();
        if(!TextUtils.isEmpty(filesPath)){
            String[] ss = filesPath.split(",");

            for(int i = 0;i< ss.length;i++){

                files.add(new File(ss[i]));
            }
        }

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


        OkGo.post(url).tag(this)//
                .isMultipart(true)       // 强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
                .params("strBody",jsonParam.toString())        // 这里可以上传参数
//                .params("file1", new File("filepath1"))   // 可以添加文件上传
//                .params("file2", new File("filepath2"))     // 支持多文件同时添加上传
                .addFileParams("images", files) // 这里支持一个key传多个文件
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //上传成功

                    }


                    @Override
                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        //这里回调上传进度(该回调在主线程,可以直接更新ui)
                    }


                });
    }

    /**
     * 查询所有警情类型
     * @param jh
     * @param simid
     * @param queryAllJingqTypesCallback
     */
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

    /**
     * 查询所有警情快速处理类型
     * @param jh
     * @param simid
     * @param queryAllJingqKuaisclTypesCallback
     */
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

    /**
     * 查询协查推送列表
     * @param jh
     * @param simid
     * @param zzjgdm
     * @param queryXiectsDatasCallback
     */
    @Override
    public void queryXiectsDatas(String jh, String simid, String zzjgdm,final IQueryXiectsDatasCallback queryXiectsDatasCallback) {
        msg = "";
        String url = UrlManager.getSERVER() + UrlManager.queryXiectsxxList;
        JSONObject param = new JSONObject();
        try {
            param.put("jh", jh);
            param.put("simid", simid);
            param.put("zzjgdm",zzjgdm);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            OkHttpUtils.postString().url(url).content(param.toString()).mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .build().execute(new Callback<List<EntityJingq>>() {
                @Override
                public List<EntityJingq> parseNetworkResponse(Response response, int id) throws Exception {

                    String string = response.body().string();
                    Log.d("wuzhengguan","我的响应："+string);
                    JSONObject object = new JSONObject(string);
                    int resultCode = object.optInt("result");
                    msg = object.optString("message");
                    String dataStr = object.optString("jingqList");
                    switch (resultCode) {
                        case 1:
                            List<EntityJingq> xiectsDatas = GsonUtil.json2BeanList(dataStr, EntityJingq.class);
                            if(xiectsDatas!=null){
                                Log.d("wuzhengguan","实体不为空,集合大小为："+xiectsDatas.size());
                            }
                            return xiectsDatas;
                        default:
                            return null;
                    }
                }

                @Override
                public void inProgress(float progress, long total, int id) {
                    super.inProgress(progress, total, id);
//                    Log.d("wuzhengguan", "inProgress: "+"1111");
                    queryXiectsDatasCallback.inProgress(progress, total, id);
                }

                @Override
                public void onError(Call call, Exception e, int id) {
                    Log.d("wuzhengguan", "inProgress: "+"111111");
                    String value = "[{\"baojdz\":\"板湖镇南郭村南郭大桥北侧50米处\",\"baojnr\":\"我的女儿步行至此处，被一辆电动三轮车撞伤，现人受伤我将其先送往医院，请派人处理。\",\"baojr\":\"汤如宝\",\"baojrdh\":\"13770176910\",\"baojsj\":\"2017-01-13 07:31:48\",\"bjlb\":\"交通警情\",\"bjlbdm\":\"210000\",\"bjlx\":\"道路交通事故\",\"bjlxdm\":\"210100\",\"bjxl\":\"行人与车辆相撞\",\"bjxldm\":\"210105\",\"chujsj\":\"2017-01-13 09:31:48\",\"daodsj\":null,\"fanksj\":null,\"filesPath\":\"\",\"fknr\":\"电动三轮车撞到行人，致行人受伤。\",\"gxdwdm\":null,\"jiejh\":\"1046\",\"jingqid\":\"JJ320700_130185367\",\"jingqzt\":\"1\",\"latitude\":\"33.63876\",\"longitude\":\"119.59547\",\"zhipsj\":null,\"zjg_cjjg\":null,\"zjg_cjlbbm\":null,\"zjg_cjlbmc\":null,\"zjg_cjr\":\"王大\",\"zjg_cjsxbm\":null,\"zjg_cjsxmc\":null,\"zjg_fsdd\":\"板湖镇南郭村\",\"zjg_sfcsbm\":null,\"zjg_sfcsmc\":null,\"zjg_sfsjsx\":null,\"zjg_sfsjxx\":null,\"zjg_ssqk\":null,\"zjg_tqqkbm\":null,\"zjg_tqqkmc\":null}," +
                            "{\"baojdz\":\"县城澳门路与上海路交汇处\",\"baojnr\":\"大约三天前的上午，我父亲骑电动车至这里时被一辆拖拉机撞伤了，当时没有报警，现双方在县医院因为治疗事情发生矛盾。请派人处理。\",\"baojr\":\"高德军\",\"baojrdh\":\"13814321112\",\"baojsj\":\"2017-01-13 07:31:48\",\"bjlb\":\"纠纷\",\"bjlbdm\":\"210000\",\"bjlx\":\"交通纠纷\",\"bjlxdm\":\"210100\",\"bjxl\":\"骑车碰撞\",\"bjxldm\":\"210102\",\"chujsj\":\"2017-01-13 07:40:54\",\"daodsj\":\"2017-01-13 10:40:54\",\"fanksj\":null,\"filesPath\":\"\",\"fknr\":\"一辆电动车与一辆拖拉机相撞，电动车驾驶人受伤，伤者已送医院，事故待处理。\",\"gxdwdm\":null,\"jiejh\":\"1001\",\"jingqid\":\"20160809090638000005\",\"jingqzt\":\"2\",\"latitude\":\"33.76077\",\"longitude\":\"119.78141\",\"zhipsj\":null,\"zjg_cjjg\":null,\"zjg_cjlbbm\":null,\"zjg_cjlbmc\":null,\"zjg_cjr\":\"李四\",\"zjg_cjsxbm\":null,\"zjg_cjsxmc\":null,\"zjg_fsdd\":\"县城澳门路\",\"zjg_sfcsbm\":null,\"zjg_sfcsmc\":null,\"zjg_sfsjsx\":null,\"zjg_sfsjxx\":null,\"zjg_ssqk\":null,\"zjg_tqqkbm\":null,\"zjg_tqqkmc\":null}," +
                            "{\"baojdz\":\"益林镇火车站加美便利店\",\"baojnr\":\"发现便利店现金被偷，请来人处理\",\"baojr\":\"高德\",\"baojrdh\":\"13814254387\",\"baojsj\":\"2017-01-14 07:31:48\",\"bjlb\":\"盗窃\",\"bjlbdm\":\"210000\",\"bjlx\":\"盗窃财物\",\"bjlxdm\":\"210100\",\"bjxl\":\"现金损失\",\"bjxldm\":\"210102\",\"chujsj\":\"2017-01-14 07:50:54\",\"daodsj\":\"2017-01-14 10:40:54\",\"fanksj\":\"2017-01-14 12:40:54\",\"filesPath\":\"\",\"fknr\":\"发现一千元现金被偷。\",\"gxdwdm\":null,\"jiejh\":\"1009\",\"jingqid\":\"20160809090638000006\",\"jingqzt\":\"3\",\"latitude\":\"33.76077\",\"longitude\":\"119.78141\",\"zhipsj\":null,\"zjg_cjjg\":null,\"zjg_cjlbbm\":null,\"zjg_cjlbmc\":null,\"zjg_cjr\":\"赵四\",\"zjg_cjsxbm\":null,\"zjg_cjsxmc\":null,\"zjg_fsdd\":\"加美便利店\",\"zjg_sfcsbm\":null,\"zjg_sfcsmc\":null,\"zjg_sfsjsx\":null,\"zjg_sfsjxx\":null,\"zjg_ssqk\":null,\"zjg_tqqkbm\":null,\"zjg_tqqkmc\":null}," +
                            "{\"baojdz\":\"板湖镇南郭村南郭大桥南侧50米处\",\"baojnr\":\"我的女儿步行至此处，被一辆电动三轮车撞伤，现人受伤我将其先送往医院，请派人处理。\",\"baojr\":\"汤如宝\",\"baojrdh\":\"13770176910\",\"baojsj\":\"2017-01-13 07:31:48\",\"bjlb\":\"交通警情\",\"bjlbdm\":\"210000\",\"bjlx\":\"道路交通事故\",\"bjlxdm\":\"210100\",\"bjxl\":\"行人与车辆相撞\",\"bjxldm\":\"210105\",\"chujsj\":\"2017-01-13 09:31:48\",\"daodsj\":null,\"fanksj\":null,\"filesPath\":\"\",\"fknr\":\"电动三轮车撞到行人，致行人受伤。\",\"gxdwdm\":null,\"jiejh\":\"1046\",\"jingqid\":\"JJ320700_130185367\",\"jingqzt\":\"1\",\"latitude\":\"33.63876\",\"longitude\":\"119.59547\",\"zhipsj\":null,\"zjg_cjjg\":null,\"zjg_cjlbbm\":null,\"zjg_cjlbmc\":null,\"zjg_cjr\":\"王大\",\"zjg_cjsxbm\":null,\"zjg_cjsxmc\":null,\"zjg_fsdd\":\"板湖镇南郭村\",\"zjg_sfcsbm\":null,\"zjg_sfcsmc\":null,\"zjg_sfsjsx\":null,\"zjg_sfsjxx\":null,\"zjg_ssqk\":null,\"zjg_tqqkbm\":null,\"zjg_tqqkmc\":null}," +
                            "{\"baojdz\":\"板湖镇南郭村南郭大桥北侧50米处\",\"baojnr\":\"我的儿子步行至此处，被一辆电动三轮车撞伤，现人受伤我将其先送往医院，请派人处理。\",\"baojr\":\"汤如宝\",\"baojrdh\":\"13770176910\",\"baojsj\":\"2017-01-13 07:31:48\",\"bjlb\":\"交通警情\",\"bjlbdm\":\"210000\",\"bjlx\":\"道路交通事故\",\"bjlxdm\":\"210100\",\"bjxl\":\"行人与车辆相撞\",\"bjxldm\":\"210105\",\"chujsj\":\"2017-01-13 09:31:48\",\"daodsj\":null,\"fanksj\":null,\"filesPath\":\"\",\"fknr\":\"电动三轮车撞到行人，致行人受伤。\",\"gxdwdm\":null,\"jiejh\":\"1046\",\"jingqid\":\"JJ320700_130185367\",\"jingqzt\":\"1\",\"latitude\":\"33.63876\",\"longitude\":\"119.59547\",\"zhipsj\":null,\"zjg_cjjg\":null,\"zjg_cjlbbm\":null,\"zjg_cjlbmc\":null,\"zjg_cjr\":\"王大\",\"zjg_cjsxbm\":null,\"zjg_cjsxmc\":null,\"zjg_fsdd\":\"板湖镇南郭村\",\"zjg_sfcsbm\":null,\"zjg_sfcsmc\":null,\"zjg_sfsjsx\":null,\"zjg_sfsjxx\":null,\"zjg_ssqk\":null,\"zjg_tqqkbm\":null,\"zjg_tqqkmc\":null}]";
                    List<EntityJingq> response = GsonUtil.json2BeanList(value, EntityJingq.class);
                    queryXiectsDatasCallback.onSuccess(response);
                    //queryXiectsDatasCallback.onFailed(e);
                }

                @Override
                public void onResponse(List<EntityJingq> response, int id) {
                    if (response != null) {
//                        Log.d("wuzhengguan","响应结果："+response.get(0).toString());
                        Log.d("wuzhengguan","不空:"+response.size());
                        String value = "[{\"baojdz\":\"板湖镇南郭村南郭大桥北侧50米处\",\"baojnr\":\"我的女儿步行至此处，被一辆电动三轮车撞伤，现人受伤我将其先送往医院，请派人处理。\",\"baojr\":\"汤如宝\",\"baojrdh\":\"13770176910\",\"baojsj\":\"2017-01-13 07:31:48\",\"bjlb\":\"交通警情\",\"bjlbdm\":\"210000\",\"bjlx\":\"道路交通事故\",\"bjlxdm\":\"210100\",\"bjxl\":\"行人与车辆相撞\",\"bjxldm\":\"210105\",\"chujsj\":\"2017-01-13 09:31:48\",\"daodsj\":null,\"fanksj\":null,\"filesPath\":\"\",\"fknr\":\"电动三轮车撞到行人，致行人受伤。\",\"gxdwdm\":null,\"jiejh\":\"1046\",\"jingqid\":\"JJ320700_130185367\",\"jingqzt\":\"1\",\"latitude\":\"33.63876\",\"longitude\":\"119.59547\",\"zhipsj\":null,\"zjg_cjjg\":null,\"zjg_cjlbbm\":null,\"zjg_cjlbmc\":null,\"zjg_cjr\":\"王大\",\"zjg_cjsxbm\":null,\"zjg_cjsxmc\":null,\"zjg_fsdd\":\"板湖镇南郭村\",\"zjg_sfcsbm\":null,\"zjg_sfcsmc\":null,\"zjg_sfsjsx\":null,\"zjg_sfsjxx\":null,\"zjg_ssqk\":null,\"zjg_tqqkbm\":null,\"zjg_tqqkmc\":null}," +
                                "{\"baojdz\":\"县城澳门路与上海路交汇处\",\"baojnr\":\"大约三天前的上午，我父亲骑电动车至这里时被一辆拖拉机撞伤了，当时没有报警，现双方在县医院因为治疗事情发生矛盾。请派人处理。\",\"baojr\":\"高德军\",\"baojrdh\":\"13814321112\",\"baojsj\":\"2017-01-13 07:31:48\",\"bjlb\":\"纠纷\",\"bjlbdm\":\"210000\",\"bjlx\":\"交通纠纷\",\"bjlxdm\":\"210100\",\"bjxl\":\"骑车碰撞\",\"bjxldm\":\"210102\",\"chujsj\":\"2017-01-13 07:40:54\",\"daodsj\":\"2017-01-13 10:40:54\",\"fanksj\":null,\"filesPath\":\"\",\"fknr\":\"一辆电动车与一辆拖拉机相撞，电动车驾驶人受伤，伤者已送医院，事故待处理。\",\"gxdwdm\":null,\"jiejh\":\"1001\",\"jingqid\":\"20160809090638000005\",\"jingqzt\":\"2\",\"latitude\":\"33.76077\",\"longitude\":\"119.78141\",\"zhipsj\":null,\"zjg_cjjg\":null,\"zjg_cjlbbm\":null,\"zjg_cjlbmc\":null,\"zjg_cjr\":\"李四\",\"zjg_cjsxbm\":null,\"zjg_cjsxmc\":null,\"zjg_fsdd\":\"县城澳门路\",\"zjg_sfcsbm\":null,\"zjg_sfcsmc\":null,\"zjg_sfsjsx\":null,\"zjg_sfsjxx\":null,\"zjg_ssqk\":null,\"zjg_tqqkbm\":null,\"zjg_tqqkmc\":null}," +
                                "{\"baojdz\":\"益林镇火车站加美便利店\",\"baojnr\":\"发现便利店现金被偷，请来人处理\",\"baojr\":\"高德\",\"baojrdh\":\"13814254387\",\"baojsj\":\"2017-01-14 07:31:48\",\"bjlb\":\"盗窃\",\"bjlbdm\":\"210000\",\"bjlx\":\"盗窃财物\",\"bjlxdm\":\"210100\",\"bjxl\":\"现金损失\",\"bjxldm\":\"210102\",\"chujsj\":\"2017-01-14 07:50:54\",\"daodsj\":\"2017-01-14 10:40:54\",\"fanksj\":\"2017-01-14 12:40:54\",\"filesPath\":\"\",\"fknr\":\"发现一千元现金被偷。\",\"gxdwdm\":null,\"jiejh\":\"1009\",\"jingqid\":\"20160809090638000006\",\"jingqzt\":\"3\",\"latitude\":\"33.76077\",\"longitude\":\"119.78141\",\"zhipsj\":null,\"zjg_cjjg\":null,\"zjg_cjlbbm\":null,\"zjg_cjlbmc\":null,\"zjg_cjr\":\"赵四\",\"zjg_cjsxbm\":null,\"zjg_cjsxmc\":null,\"zjg_fsdd\":\"加美便利店\",\"zjg_sfcsbm\":null,\"zjg_sfcsmc\":null,\"zjg_sfsjsx\":null,\"zjg_sfsjxx\":null,\"zjg_ssqk\":null,\"zjg_tqqkbm\":null,\"zjg_tqqkmc\":null}," +
                                "{\"baojdz\":\"板湖镇南郭村南郭大桥南侧50米处\",\"baojnr\":\"我的女儿步行至此处，被一辆电动三轮车撞伤，现人受伤我将其先送往医院，请派人处理。\",\"baojr\":\"汤如宝\",\"baojrdh\":\"13770176910\",\"baojsj\":\"2017-01-13 07:31:48\",\"bjlb\":\"交通警情\",\"bjlbdm\":\"210000\",\"bjlx\":\"道路交通事故\",\"bjlxdm\":\"210100\",\"bjxl\":\"行人与车辆相撞\",\"bjxldm\":\"210105\",\"chujsj\":\"2017-01-13 09:31:48\",\"daodsj\":null,\"fanksj\":null,\"filesPath\":\"\",\"fknr\":\"电动三轮车撞到行人，致行人受伤。\",\"gxdwdm\":null,\"jiejh\":\"1046\",\"jingqid\":\"JJ320700_130185367\",\"jingqzt\":\"1\",\"latitude\":\"33.63876\",\"longitude\":\"119.59547\",\"zhipsj\":null,\"zjg_cjjg\":null,\"zjg_cjlbbm\":null,\"zjg_cjlbmc\":null,\"zjg_cjr\":\"王大\",\"zjg_cjsxbm\":null,\"zjg_cjsxmc\":null,\"zjg_fsdd\":\"板湖镇南郭村\",\"zjg_sfcsbm\":null,\"zjg_sfcsmc\":null,\"zjg_sfsjsx\":null,\"zjg_sfsjxx\":null,\"zjg_ssqk\":null,\"zjg_tqqkbm\":null,\"zjg_tqqkmc\":null}," +
                                "{\"baojdz\":\"板湖镇南郭村南郭大桥北侧50米处\",\"baojnr\":\"我的儿子步行至此处，被一辆电动三轮车撞伤，现人受伤我将其先送往医院，请派人处理。\",\"baojr\":\"汤如宝\",\"baojrdh\":\"13770176910\",\"baojsj\":\"2017-01-13 07:31:48\",\"bjlb\":\"交通警情\",\"bjlbdm\":\"210000\",\"bjlx\":\"道路交通事故\",\"bjlxdm\":\"210100\",\"bjxl\":\"行人与车辆相撞\",\"bjxldm\":\"210105\",\"chujsj\":\"2017-01-13 09:31:48\",\"daodsj\":null,\"fanksj\":null,\"filesPath\":\"\",\"fknr\":\"电动三轮车撞到行人，致行人受伤。\",\"gxdwdm\":null,\"jiejh\":\"1046\",\"jingqid\":\"JJ320700_130185367\",\"jingqzt\":\"1\",\"latitude\":\"33.63876\",\"longitude\":\"119.59547\",\"zhipsj\":null,\"zjg_cjjg\":null,\"zjg_cjlbbm\":null,\"zjg_cjlbmc\":null,\"zjg_cjr\":\"王大\",\"zjg_cjsxbm\":null,\"zjg_cjsxmc\":null,\"zjg_fsdd\":\"板湖镇南郭村\",\"zjg_sfcsbm\":null,\"zjg_sfcsmc\":null,\"zjg_sfsjsx\":null,\"zjg_sfsjxx\":null,\"zjg_ssqk\":null,\"zjg_tqqkbm\":null,\"zjg_tqqkmc\":null}]";
                        List<EntityJingq> response1 = GsonUtil.json2BeanList(value, EntityJingq.class);
                        queryXiectsDatasCallback.onSuccess(response1);
                    } else {
                        Log.d("wuzhengguan","111222");
                        if (MyApplication.IS_TEST_JINGQLIST) {
                            String value = "[{\"baojdz\":\"板湖镇南郭村南郭大桥北侧50米处\",\"baojnr\":\"我的女儿步行至此处，被一辆电动三轮车撞伤，现人受伤我将其先送往医院，请派人处理。\",\"baojr\":\"汤如宝\",\"baojrdh\":\"13770176910\",\"baojsj\":\"2017-01-13 07:31:48\",\"bjlb\":null,\"bjlbdm\":\"210000\",\"bjlx\":null,\"bjlxdm\":\"210100\",\"bjxl\":null,\"bjxldm\":\"210105\",\"chujsj\":null,\"daodsj\":null,\"fanksj\":null,\"filesPath\":\"\",\"fknr\":\"电动三轮车撞到行人，致行人受伤。\",\"gxdwdm\":null,\"jiejh\":\"1046\",\"jingqid\":\"JJ320700_130185367\",\"jingqzt\":\"0\",\"latitude\":\"33.63876\",\"longitude\":\"119.59547\",\"zhipsj\":null,\"zjg_cjjg\":null,\"zjg_cjlbbm\":null,\"zjg_cjlbmc\":null,\"zjg_cjr\":null,\"zjg_cjsxbm\":null,\"zjg_cjsxmc\":null,\"zjg_fsdd\":null,\"zjg_sfcsbm\":null,\"zjg_sfcsmc\":null,\"zjg_sfsjsx\":null,\"zjg_sfsjxx\":null,\"zjg_ssqk\":null,\"zjg_tqqkbm\":null,\"zjg_tqqkmc\":null},{\"baojdz\":\"县城澳门路与上海路交汇处\",\"baojnr\":\"大约三天前的上午，我父亲骑电动车至这里时被一辆拖拉机撞伤了，当时没有报警，现双方在县医院因为治疗事情发生矛盾。请派人处理。\",\"baojr\":\"高德军\",\"baojrdh\":\"13814321112\",\"baojsj\":\"2017-01-13 07:31:48\",\"bjlb\":null,\"bjlbdm\":\"210000\",\"bjlx\":null,\"bjlxdm\":\"210100\",\"bjxl\":null,\"bjxldm\":\"210102\",\"chujsj\":null,\"daodsj\":null,\"fanksj\":null,\"filesPath\":\"\",\"fknr\":\"一辆电动车与一辆拖拉机相撞，电动车驾驶人受伤，伤者已送医院，事故待处理。\",\"gxdwdm\":null,\"jiejh\":\"1001\",\"jingqid\":\"20160809090638000005\",\"jingqzt\":\"0\",\"latitude\":\"33.76077\",\"longitude\":\"119.78141\",\"zhipsj\":null,\"zjg_cjjg\":null,\"zjg_cjlbbm\":null,\"zjg_cjlbmc\":null,\"zjg_cjr\":null,\"zjg_cjsxbm\":null,\"zjg_cjsxmc\":null,\"zjg_fsdd\":null,\"zjg_sfcsbm\":null,\"zjg_sfcsmc\":null,\"zjg_sfsjsx\":null,\"zjg_sfsjxx\":null,\"zjg_ssqk\":null,\"zjg_tqqkbm\":null,\"zjg_tqqkmc\":null}]";
                            response = GsonUtil.json2BeanList(value, EntityJingq.class);
                            queryXiectsDatasCallback.onSuccess(response);
                        } else {
                            queryXiectsDatasCallback.onFailed(new Exception("暂无数据"));
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 推送消息接受确认
     * @param jingqid
     * @param jh
     * @param simid
     * @param confirmReceiveMsgCallBack
     */
    @Override
    public void confirmReceiveMsg(String jingqid, String jh, String simid, final IConfirmReceiveMsgCallBack confirmReceiveMsgCallBack) {
        String url=UrlManager.getSERVER() + UrlManager.receiveMsg;// 推送消息接受确认
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
                confirmReceiveMsgCallBack.confirmReceiveMsgFailed(e);//请求失败的描述
            }

            @Override
            public void onResponse(Boolean response, int id) {

                if (response){
                    confirmReceiveMsgCallBack.confirmReceiveMsgSuccess();
                }else{
                    Log.d("wuzhengguan", "inProgress: "+"222222"+msg);
                    confirmReceiveMsgCallBack.confirmReceiveMsgSuccess();//假数据
//                    confirmReceiveMsgCallBack.confirmReceiveMsgFailed(new Exception(msg));//失败的描述信息
                }

            }
        });
    }

    /**
     * 民警向中心要案反馈(接口文档无)
     * @param jingyid
     * @param jingqid
     * @param bjlb
     * @param bjlx
     * @param bjxl
     * @param fknr
     * @param fksj
     * @param filesPath
     * @param jh
     * @param simid
     * @param yaoAnFeedbackCallBack
     */
    @Override
    public void FeedbackJingq(String jingyid, String jingqid, String bjlb, String bjlx, String bjxl, String fknr, String fksj, String filesPath, String jh, String simid, final IYaoAnFeedbackCallBack yaoAnFeedbackCallBack) {

        String url=UrlManager.getSERVER()+UrlManager.xiectsFeedback;
        msg = "";
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("jingyid", jingyid);
            jsonParam.put("jingqid", jingqid);
            jsonParam.put("bjlb", bjlb);
            jsonParam.put("bjlx", bjlx);
            jsonParam.put("bjxl", bjxl);
            jsonParam.put("fknr",fknr);
            jsonParam.put("fksj",fksj);
            jsonParam.put("filesPath", filesPath);
            jsonParam.put("jh", jh);
            jsonParam.put("simid", simid);

        } catch (JSONException e) {
            Log.d("wuzhengguan", "feedbackJingq: ");
            e.printStackTrace();
        }
        Map<String,String> param = new HashMap<>();
        param.put("strBody",jsonParam.toString());
        PostFormBuilder builder=OkHttpUtils.post();
        String[] files = filesPath.split(",");
        Log.d("wuzhengguan", "feedbackJingq图片张数: " + files.length);
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

                yaoAnFeedbackCallBack.yaoAnFeedbackFailed(e);

            }

            @Override
            public void onResponse(Boolean response, int id) {
                if (response) {
                    yaoAnFeedbackCallBack.yaoAnFeedbackSuccess();
                } else {
                    Log.d("wuzhengguan", "inProgress: "+"333333"+msg);
                    yaoAnFeedbackCallBack.yaoAnFeedbackFailed(new Exception(msg));
                }

            }
        });



    }

    /**
     * 查询警员的反馈记录（接口文档无）
     * @param jh
     * @param simid

     * @param queryFeedbackRecordCallback
     */
    @Override
    public void queryFeedbackRecord(String jh, String simid,  final IQueryFeedbackRecordCallback queryFeedbackRecordCallback) {
        msg="";
        String url = UrlManager.getSERVER() + UrlManager.xiectsQueryFeedbackList;
        JSONObject param = new JSONObject();
        try {
            param.put("jh", jh);
            param.put("simid", simid);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpUtils.postString().url(url).content(param.toString()).mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build().execute(new Callback<List<EntityFeedbackRecord>>() {

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                queryFeedbackRecordCallback.inProgress(progress,total,id);
            }

            @Override
            public List<EntityFeedbackRecord> parseNetworkResponse(Response response, int id) throws Exception {
                String string = response.body().string();
                JSONObject object = new JSONObject(string);
                int resultCode = object.optInt("result");
                msg = object.optString("message");
                String dataStr = object.optString("jingqList");
                switch (resultCode){
                    case 1:
                        List<EntityFeedbackRecord> feedbackRecords=GsonUtil.json2BeanList(dataStr,EntityFeedbackRecord.class);
                        return feedbackRecords;
                    default:
                        return null;

                }

            }

            @Override
            public void onError(Call call, Exception e, int id) {
                Log.d("wuzhengguan","inProgress: "+"444444");
                String value = "[{\"jingqid\":\"1111111\",\"feedbackid\":\"222222\",\"feedbacktype\":\"0\",\"fkfxm\":\"李四\",\" fkfjh\":\"123456\",\"zzjgmc\":\"阜林县公安局\",\"zzjgdm\":\"210000\",\"jydh\":\"13223456978\",\"zzjgdh\":\"110\",\"fknr\":\"现金损失了2万块，目前警方正调取监控视频\",\"fksj\":\"2017-03-02 12:30:00\",\"filespath\":\"\"},{\"jingqid\":\"1111111\",\"feedbackid\":\"222223\",\"feedbacktype\":\"1\",\"fkfxm\":\"李四\",\" fkfjh\":\"123456\",\"zzjgmc\":\"阜林县公安局\",\"zzjgdm\":\"210000\",\"jydh\":\"13223456978\",\"zzjgdh\":\"110\",\"fknr\":\"请该警员尽快上传犯案现场的图片，中心会派人调查此事\",\"fksj\":\"2017-03-02 13:30:00\",\"filespath\":\"\"}]";
                List<EntityFeedbackRecord> response = GsonUtil.json2BeanList(value, EntityFeedbackRecord.class);
                Log.d("wuzhengguan","长度："+response.size());
                queryFeedbackRecordCallback.onSuccess(response);
//                queryFeedbackRecordCallback.onFailed(e);
            }

            @Override
            public void onResponse(List<EntityFeedbackRecord> response, int id) {
                if(response!=null){
                    queryFeedbackRecordCallback.onSuccess(response);
                }else{
                    queryFeedbackRecordCallback.onFailed(new Exception(msg));
                }
            }
        });
    }

    /**
     * 民警临时发送上报警情
     * @param jh
     * @param simid
     * @param bjrxm
     * @param bjrjh
     * @param bjsj
     * @param bjrdh
     * @param bjdz
     * @param baojnr
     * @param bjlb
     * @param bjlx
     * @param bjxl
     * @param longitude
     * @param latitude
     * @param filesPath
     * @param jingqShangbaoCallback
     */
    @Override
    public void shangbaoJingq(String jh, String simid, String bjrxm, String bjrjh, String bjsj, String bjrdh,
                              String bjdz, String baojnr, String bjlb, String bjlx, String bjxl, String longitude, String latitude, String filesPath, final IJingqShangbaoCallback jingqShangbaoCallback) {

        String url = UrlManager.getSERVER() + UrlManager.sendJingq;
        msg = "";
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("jh",jh);
            jsonParam.put("simid",simid);
            jsonParam.put("bjrxm",bjrxm);
            jsonParam.put("bjrjh",bjrjh);
            jsonParam.put("bjsj",bjsj);
            jsonParam.put("bjrdh",bjrdh);
            jsonParam.put("bjdz",bjdz);
            jsonParam.put("baojnr",baojnr);
            jsonParam.put("bjlb",bjlb);
            jsonParam.put("bjlx",bjlx);
            jsonParam.put("bjxl",bjxl);
            jsonParam.put("longitude",longitude);
            jsonParam.put("latitude",latitude);
            jsonParam.put("filesPath",filesPath);

        } catch (JSONException e) {
            e.printStackTrace();
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
                jingqShangbaoCallback.jingqShangbaoFailed(e);
            }

            @Override
            public void onResponse(Boolean response, int id) {

                if(response){
                    jingqShangbaoCallback.jingqShangbaoSuccess();
                }else{
                    Log.d("wuzhengguan","inProgress: "+"555555");
                    jingqShangbaoCallback.jingqShangbaoFailed(new Exception(msg));
                }
            }
        });

    }

    /**
     * 查询我的上报记录（接口文档无）
     * @param jh
     * @param simid
     * @param queryMySubmitRecordsCallback
     */
    @Override
    public void queryMySubmitRecords(String jh, String simid, final IQueryMySubmitRecordsCallback queryMySubmitRecordsCallback) {

        msg = "";
        String url = UrlManager.getSERVER() + "queryMySubmitRecords";
        JSONObject param = new JSONObject();

        try {
            param.put("jh",jh);
            param.put("simid",simid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpUtils.postString().url(url).content(param.toString()).mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build().execute(new Callback<List<EntityTempJingq>>() {
            @Override
            public List<EntityTempJingq> parseNetworkResponse(Response response, int id) throws Exception {
                String string = response.body().string();
                JSONObject object = new JSONObject(string);
                int resultCode = object.optInt("result");
                msg = object.optString("message");
                String dataStr = object.optString("tempjingqList");
                switch (resultCode) {
                    case 1:
                        List<EntityTempJingq> mySubmitDatas = GsonUtil.json2BeanList(dataStr, EntityTempJingq.class);
                        return mySubmitDatas;
                    default:
                        return null;
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
//                queryMySubmitRecordsCallback.onFailed(e);
                Log.d("wuzhengguan","inProgress: "+"666666");
                String value = "[{\"bjdz\":\"板湖镇南郭村南郭大桥北侧50米处\",\"baojnr\":\"行人步行至此处，被一辆电动三轮车撞伤，现人受伤我将其先送往医院，请派人处理。\",\"bjrxm\":\"汤如宝\",\"bjrdh\":\"13770176910\",\"bjsj\":\"2017-01-13 07:31:48\",\"bjlb\":\"交通警情\",\"bjlbdm\":\"210000\",\"bjlx\":\"道路交通事故\",\"bjlxdm\":\"210100\",\"bjxl\":\"行人与车辆相撞\",\"bjxldm\":\"210105\",\"shenhetgsj\":\"2017-01-13 09:31:48\",\"filesPath\":\"\",\" submitid\":\"JJ320700_130185367\",\"shenhezt\":\"1\",\"latitude\":\"33.63876\",\"longitude\":\"119.59547\",\"bjrjh\":\"111111\",\"nopassReason\":\"\",\"phoneWhenUncheck\":\"13245678900\"},{\"bjdz\":\"县城澳门路与上海路交汇处\",\"baojnr\":\"我的父亲步行至此处，被一辆拖拉机撞伤，现人受伤我将其先送往医院，请派人处理。\",\"bjrxm\":\"汤如宝\",\"bjrdh\":\"13770176910\",\"bjsj\":\"2017-01-14 07:31:48\",\"bjlb\":\"交通警情\",\"bjlbdm\":\"210000\",\"bjlx\":\"道路交通事故\",\"bjlxdm\":\"210100\",\"bjxl\":\"行人与车辆相撞\",\"bjxldm\":\"210105\",\"shenhetgsj\":\"\",\"filesPath\":\"\",\" submitid\":\"JJ320700_130538673\",\"shenhezt\":\"0\",\"latitude\":\"33.63876\",\"longitude\":\"119.59547\",\"bjrjh\":\"111111\",\"nopassReason\":\"\",\"phoneWhenUncheck\":\"13245678900\"},{\"bjdz\":\"意林镇火车站佳美便利店\",\"baojnr\":\"发现便利店现金被偷，请来人处理。\",\"bjrxm\":\"汤如宝\",\"bjrdh\":\"13770176910\",\"bjsj\":\"2017-01-13 07:31:48\",\"bjlb\":\"交通警情\",\"bjlbdm\":\"210000\",\"bjlx\":\"道路交通事故\",\"bjlxdm\":\"210100\",\"bjxl\":\"行人与车辆相撞\",\"bjxldm\":\"210105\",\"shenhetgsj\":\"\",\"filesPath\":\"\",\" submitid\":\"JJ320700_130185367\",\"shenhezt\":\"2\",\"latitude\":\"33.63876\",\"longitude\":\"119.59547\",\"bjrjh\":\"111111\",\"nopassReason\":\"审核时出现问题\",\"phoneWhenUncheck\":\"13245678900\"}]";
                List<EntityTempJingq> response = GsonUtil.json2BeanList(value, EntityTempJingq.class);
                queryMySubmitRecordsCallback.onSuccess(response);
            }

            @Override
            public void onResponse(List<EntityTempJingq> response, int id) {
                if(response != null){
                    queryMySubmitRecordsCallback.onSuccess(response);
                }else{
                    queryMySubmitRecordsCallback.onFailed(new Exception(msg));
                }
            }
        });
    }








}
