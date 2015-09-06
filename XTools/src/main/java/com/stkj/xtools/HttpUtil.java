package com.stkj.xtools;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jarrahwu on 15/6/22.
 */
public class HttpUtil {

    public static JSONObject makeJson(KV... kvs) {
        Map<String, Object> map = new HashMap<>();

        for (KV kv : kvs) {
            map.put(kv.key, kv.value);
        }

        JSONObject jo = new JSONObject(map);
        return jo;
    }

    public static class KV {
        public String key;
        public Object value;

        public static KV make(String key, Object value) {
            KV kv = new KV();
            kv.key = key;
            kv.value = value;
            return kv;
        }
    }


    public static void execMultipart(String url, ArrayList<MultipartParameter> mPrams) {
        Response.ErrorListener eLsn = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                com.stkj.xtools.Util.toast("发布失败 " + volleyError.networkResponse.statusCode);
            }
        };
        Response.Listener<String> sLsn = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                android.util.Log.e("blue~~~~~~", s);
                com.stkj.xtools.Util.toast("发布成功 " + s);
            }
        };


        MultipartRequest multipartRequest = new MultipartRequest(url, eLsn, sLsn);
        multipartRequest.addMultipartParam(mPrams);
        XTool.getInstance().getRequestQueue().add(multipartRequest);
    }


    private static void httpWrap(int Method, String url, JSONObject params, Response.Listener<JSONObject> lsn, Response.ErrorListener elsn) {
        JsonObjectRequest jr = new JsonObjectRequest(Method, url, params, lsn, elsn);
        XTool.getInstance().getRequestQueue().add(jr);
    }

    /**
     * @param url
     * @param params 参数
     * @param lsn 成功回调
     * @param elsn 错误回调
     */
    private static void requestDelete(String url, JSONObject params, Response.Listener<JSONObject> lsn, Response.ErrorListener elsn) {
        httpWrap(Request.Method.DELETE, url, params, lsn, elsn);
    }

    private static void requestPut(String url, JSONObject params, Response.Listener<JSONObject> lsn, Response.ErrorListener elsn) {
        httpWrap(Request.Method.PUT, url, params, lsn, elsn);
    }

}
