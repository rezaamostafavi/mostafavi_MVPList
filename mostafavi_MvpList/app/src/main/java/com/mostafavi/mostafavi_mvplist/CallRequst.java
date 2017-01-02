package com.mostafavi.mostafavi_mvplist;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by mostafavi on 10/16/2016.
 */
public class CallRequst {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client;
    private Context context;

    public void callRequest(final Context context, final String url, final ResponseReceiver responseReceiver) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String response = post(url);
                    response((Activity) context, responseReceiver, response);
                } catch (IOException e) {
                    e.printStackTrace();
                    if (responseReceiver != null) {
                        response((Activity) context, responseReceiver, null);
                    }
                }
            }
        }.start();
    }

    private void response(final Activity context, final ResponseReceiver responseReceiver, final String responseData) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (responseData != null)
                    Log.d("@PostResponse", responseData);
                responseReceiver.getResponse(context, responseData);
            }
        });
    }

    private String post(String url) throws IOException {
        client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).readTimeout(40, TimeUnit.SECONDS).writeTimeout(50, TimeUnit.SECONDS).build();
//        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
//                .post(body)
                .build();
//        Log.d("@PostRequest", json);
        Call call = client.newCall(request);
        Response response = call.execute();
        return response.body().string();
    }

    public interface ResponseReceiver {

        void getResponse(Object sender, String result);
    }

}
