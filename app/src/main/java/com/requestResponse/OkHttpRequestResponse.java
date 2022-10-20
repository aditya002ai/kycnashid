package com.requestResponse;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.MyUtils;
import com.interfaceClass.RequestResponse;
import com.omanid.LogService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpRequestResponse {
    private static final OkHttpRequestResponse ourInstance = new OkHttpRequestResponse();

    public OkHttpRequestResponse() {
    }

    public static OkHttpRequestResponse getInstance() {
        return ourInstance;
    }


    public static void showMessage(final Context context, final String mMessage) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {

            @Override
            public void run() {
//                LogService.writeLog(context, mMessage);

            }
        });


    }

    public static void showMessageDialog(final Context context, final String mMessage) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {

            @Override
            public void run() {
                MyUtils.getInstance().messageDialog(context, "Internet Connection Problem, Please try again!");


            }
        });


    }

    public static Boolean uploadFile(final Context context, byte[] f1, byte[] f2, final RequestResponse requestResponse) {
        try {
            String URL = "http://185.64.25.107:5001/face_match";

            HttpUrl.Builder urlBuilder = HttpUrl.parse(URL).newBuilder();
            String url = urlBuilder.build().toString();

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build();

            RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
//                    .addFormDataPart("file1", file1.getName(), RequestBody.create(MediaType.parse("image/jpg"), file1))
//                    .addFormDataPart("file1", file1.getName(), RequestBody.create(MediaType.parse("image/*"), file1))
//                    .addFormDataPart("file1", file1.getName(), RequestBody.create(MediaType.parse("rb"), file1))
//                    .addFormDataPart("file2", file2.getName(), RequestBody.create(MediaType.parse("rb"), file2))
                    .addFormDataPart("file1", "filename1.jpg",
                            RequestBody.create(MediaType.parse("image/*jpg"), f1))
                    .addFormDataPart("file2", "filename2.jpg",
                            RequestBody.create(MediaType.parse("image/*jpg"), f2))
                    .build();


            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(final Call call, final IOException e) {
                    MyUtils.getInstance().dismissDialog();
                    showMessage(context, "Error" + e.getMessage());
                    // Handle the error
                    Log.e("TAG", "Error: ");

                }

                @Override
                public void onResponse(final Call call, final Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String res = response.body().string();
                        MyUtils.getInstance().dismissDialog();
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(res);
                            requestResponse.myResponse(obj);
//                        showMessage(context,"Match : "+ obj.getDouble("match"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            MyUtils.getInstance().dismissDialog();

                        }

                        // Handle the error
                    } else {
                        showMessage(context, "Face Not Found in the Image");
                        MyUtils.getInstance().dismissDialog();

                    }
                    // Upload successful
                }
            });

            return true;
        } catch (Exception ex) {
            MyUtils.getInstance().dismissDialog();
            // Handle the error
        }
        return false;
    }


    public boolean internetConnectionCheck(Context context) {
        if (!MyUtils.getInstance().checkInternetConnection(context)) {
            showToastMessage(context, "No Internet Connection, Please check your connection and try Again.");
            MyUtils.getInstance().dismissDialog();
            return true;

        }
        return false;
    }

    public void errorShowToastMessage(final Context context, final String mMessage) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {

            @Override
            public void run() {
                JSONObject object = null;
                try {
                    object = new JSONObject(mMessage);
                    Toast.makeText(context, object.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                MyUtils.getInstance().dismissDialog();
            }
        });

    }

    public void showToastMessage(final Context context, final String mMessage) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(context, mMessage, Toast.LENGTH_SHORT).show();
            }
        });


    }



}
