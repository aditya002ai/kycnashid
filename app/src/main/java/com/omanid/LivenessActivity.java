package com.omanid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;


import com.MyUtils;
import com.interfaceClass.RequestResponse;
import com.liveness.LivenessMainActivity;
import com.requestResponse.OkHttpRequestResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class LivenessActivity extends Activity {
    TextView text, FaceComparison;
    ImageView view_photo, certificate1;
    Button clickLiveness, buttonBackground;
    File imgFile1;
    Instant start;
    int CAMERA_PIC_REQUEST = 1001;
    int CAMERA_AUTOSELFY_REQUEST = 1005;
    LinearLayout mainLayout, btnFaceComparison;
    ImageView logo;
    boolean imageUploadedSelfi = false;
    boolean faceComparision = false;
//    RequestResponse requestSelfiImage = new RequestResponse() {
//        @Override
//        public void myResponse(JSONObject jsonObject) {
//            try {
//                if (jsonObject.getString("statuscode").equals("1")) {
//                    imageUploadedSelfi = true;
////                    if(faceComparision) {
////                    Intent intent = new Intent(LivenessActivity.this, ReferenceScreen.class);
////                    startActivity(intent);
////                    }
//                } else {
//                    MyUtils.getInstance().messageDialog(LivenessActivity.this, "Internet Connection Problem, Please try again!");
////                    Toast.makeText(LivenessActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
//
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void myJsonArray(JSONArray jsonObject) {
//
//        }
//    };
    RequestResponse requestResponseFace = new RequestResponse() {
        @Override
        public void myResponse(final JSONObject jsonObject) {

            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void run() {
                    try {
//                        MyUtils.getInstance().dismissDialog();
                        double value = jsonObject.getDouble("match");
//                        String myText = "Not Match Try Again\n" + value;
                        String myText = "Face Comparison Failed \n Try Again";
                        Instant finish = Instant.now();
                        long timeElapsed = Duration.between(start, finish).toMillis();
                        long time = TimeUnit.SECONDS.toSeconds(TimeUnit.MILLISECONDS.toSeconds(timeElapsed));

//                        if (value < 0.35) {
                        if (value < 0.5) {
                            MyUtils.getInstance().setFacecomparisonStatus("Face Comparison Successful");
                            myText = "Face Comparison Successful";
                            faceComparision = true;

                        } else {
                            MyUtils.getInstance().setFacecomparisonStatus("Face Comparison Failed");
                            faceComparision = false;

                        }



                        text.setText(myText);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


        }

        @Override
        public void myJsonArray(JSONArray jsonObject) {

        }
    };
    private String pictureImagePath = "";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liveness);
        text = findViewById(R.id.text);
        view_photo = findViewById(R.id.view_photo);
        certificate1 = findViewById(R.id.certificate1);
        clickLiveness = findViewById(R.id.clickLiveness);
        buttonBackground = findViewById(R.id.buttonBackground);
        mainLayout = findViewById(R.id.mainLayout);
        logo = findViewById(R.id.logo);


        btnFaceComparison = findViewById(R.id.btnFaceComparison);
        FaceComparison = findViewById(R.id.FaceComparison);

        view_photo.setImageBitmap(LivenessData.getInstance().getNfcImage());

        clickLiveness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                openBackCamera();
                openAutoCamera();
            }
        });

        buttonBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                 && imageUploadedSelfi
                if (!text.getText().toString().equals("") && imageUploadedSelfi) {
                    MyUtils.getInstance().alertDialogNoYes(LivenessActivity.this);
                } else {
                    Toast.makeText(getApplicationContext(), "Please Click For Face Comparison", Toast.LENGTH_SHORT).show();
                }
            }
        });


        openAutoCamera();


    }

    private void openAutoCamera() {
//        Intent cameraIntent = new Intent(LivenessActivity.this, AndroidCameraApi.class);
//        startActivityForResult(cameraIntent, CAMERA_AUTOSELFY_REQUEST);

        Intent i=new Intent(LivenessActivity.this, LivenessMainActivity.class);
        startActivityForResult(i, CAMERA_AUTOSELFY_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PIC_REQUEST) {
            if (resultCode == -1) {
                imgFile1 = new File(pictureImagePath);
                certificate1.setImageURI(Uri.fromFile(imgFile1));
                MyUtils.getInstance().showProgressDialog(LivenessActivity.this);
                new UploadFace().execute();
            }
        }

        if (requestCode == CAMERA_AUTOSELFY_REQUEST) {
            if (resultCode == 0) {
//                imgFile1 = FaceData.getInstance().getImageSelfie();
//                if (imgFile1 != null) {
//                    certificate1.setImageURI(Uri.fromFile(imgFile1));
//                    MyUtils.getInstance().showProgressDialog(LivenessActivity.this);
//                    new UploadFace().execute();
//                }


                try {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(270);
                    Bitmap bmp = BitmapFactory.decodeByteArray(Utility.getInstance().liveImage, 0, Utility.getInstance().liveImage.length);
                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bmp, 2048, 2048, true);
                    Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
                    certificate1.setImageBitmap(rotatedBitmap);
                    MyUtils.getInstance().showProgressDialog(LivenessActivity.this);
                    new UploadFace().execute();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private byte[] byteArray(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageInByte = baos.toByteArray();
        return imageInByte;
    }




    @Override
    public void onBackPressed() {
        MyUtils.getInstance().processend(LivenessActivity.this, "Are you sure, you want to end this process?");
    }



    private class UploadFace extends AsyncTask<String, Void, String> {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(String... strings) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            start = Instant.now();
            faceComparision = false;
            FaceData.getInstance().setImageSelfie(null);
            OkHttpRequestResponse.getInstance().uploadFile(LivenessActivity.this, byteArray(view_photo), byteArray(certificate1), requestResponseFace);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }



}
