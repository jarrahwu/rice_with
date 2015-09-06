package com.stkj.xtools;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MultipartRequest extends Request<String> {

    private MultipartEntityBuilder mMultipartEntityBuilder;
    private List<MultipartParameter> mMultipartParameters;

    private final Response.Listener<String> mListener;
    private HttpEntity mEntity;

    public MultipartRequest(String url, Response.ErrorListener errorListener, Response.Listener<String> listener)
    {
        super(Method.POST, url, errorListener);

        mListener = listener;

        mMultipartEntityBuilder = MultipartEntityBuilder.create();
        mMultipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        mMultipartParameters = new ArrayList<MultipartParameter>();
    }

    private void buildMultipartEntity()
    {

        for (MultipartParameter p : mMultipartParameters) {
            if(p.getValue().getClass() == File.class) {
                mMultipartEntityBuilder.addPart(p.getParamName(), new FileBody((File) p.getValue()));
            }
            if(p.getValue().getClass() == String.class){
                mMultipartEntityBuilder.addTextBody(p.getParamName(), (String) p.getValue());
            }
            if(p.getValue().getClass() == JSONObject.class){
                mMultipartEntityBuilder.addTextBody(p.getParamName(), p.getValue().toString(), ContentType.APPLICATION_JSON);
            }
        }

        mEntity = mMultipartEntityBuilder.build();

//        entity.addPart(FILE_PART_NAME, new FileBody(mFilePart));
//        try
//        {
//            entity.addPart(STRING_PART_NAME, new StringBody(mStringPart));
//        }
//        catch (UnsupportedEncodingException e)
//        {
//            VolleyLog.e("UnsupportedEncodingException");
//        }
    }

    @Override
    public String getBodyContentType()
    {
        return mEntity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try
        {
            mEntity.writeTo(bos);
        }
        catch (IOException e)
        {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response)
    {
        return Response.success("Uploaded", getCacheEntry());
    }

    @Override
    protected void deliverResponse(String response)
    {
        mListener.onResponse(response);
    }

    public void addMultipartParam(List<MultipartParameter> parameters) {
        for(MultipartParameter p : parameters) {
            mMultipartParameters.add(p);
        }
        buildMultipartEntity();
    }
}