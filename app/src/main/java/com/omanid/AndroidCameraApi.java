package com.omanid;


import android.Manifest;
import android.app.Activity;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import me.aflak.ezcam.EZCam;
import me.aflak.ezcam.EZCamCallback;


public class AndroidCameraApi extends Activity implements EZCamCallback {

    Button buttonBackground;
    ImageView logo;

    private TextureView textureView;

    private EZCam cam;
    private SimpleDateFormat dateFormat;

    private final String TAG = "CAM";
    LinearLayout mainLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_camera_api);
        buttonBackground = (Button) findViewById(R.id.getpicture);
        logo=findViewById(R.id.logo);
        mainLayout=findViewById(R.id.mainLayout);



        textureView = (TextureView) findViewById(R.id.textureview);
        dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault());

        cam = new EZCam(this);
        cam.setCameraCallback(this);

        String id = cam.getCamerasList().get(CameraCharacteristics.LENS_FACING_FRONT);
        cam.selectCamera(id);

        Dexter.withActivity(AndroidCameraApi.this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                cam.open(CameraDevice.TEMPLATE_PREVIEW, textureView);
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                Log.e(TAG, "permission denied");
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();

        buttonBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cam.takePicture();
            }
        });
    }
    @Override
    public void onCameraReady() {
        cam.setCaptureSetting(CaptureRequest.COLOR_CORRECTION_ABERRATION_MODE, CameraMetadata.COLOR_CORRECTION_ABERRATION_MODE_HIGH_QUALITY);
        cam.startPreview();

//        textureView.setOnLongClickListener(this);
    }

    @Override
    public void onPicture(Image image) {
        cam.stopPreview();
        try {
            String filename = "image_"+dateFormat.format(new Date())+".jpg";
            File file = new File(getFilesDir(), filename);
            EZCam.saveImage(image, file);
            FaceData.getInstance().setImageSelfie(file);

//            Intent intent = new Intent(this, DisplayActivity.class);
//            intent.putExtra("filepath", file.getAbsolutePath());
//            startActivity(intent);
            finish();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onCameraDisconnected() {
        Log.e(TAG, "Camera disconnected");
    }

    @Override
    public void onError(String message) {
        Log.e(TAG, message);
    }

    @Override
    protected void onDestroy() {
        cam.close();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



}