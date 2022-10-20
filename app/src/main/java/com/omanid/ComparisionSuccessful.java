package com.omanid;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGImageView;
import com.liveness.LivenessMainActivity;
import com.mrtd2.MainActivityMRZ;

import org.jmrtd.lds.icao.MRZInfo;

import java.io.IOException;

public class ComparisionSuccessful extends AppCompatActivity {
    SVGImageView logo, dropdown1, dropdown2, dropdown3, certificate;
    Button verification;
    RelativeLayout Camera, NFC, Facing;
    static final public String MRZ_RESULT = "MRZ_RESULT";
    int REQUEST_CODE_ASK_PERMISSIONS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparision_successful);
        logo = findViewById(R.id.logo);
        dropdown1 = findViewById(R.id.next);
        dropdown2 = findViewById(R.id.next1);
        dropdown3 = findViewById(R.id.next2);
//        certificate = findViewById(R.id.certificate);
        verification = findViewById(R.id.complte);
        Camera = findViewById(R.id.camera);
        NFC = findViewById(R.id.nfcresult);
        Facing = findViewById(R.id.Faceresult);
        try {
            SVG svg = SVG.getFromResource(getResources(), R.raw.logo);
            logo.setSVG(svg);
            SVG one = SVG.getFromResource(getResources(), R.raw.next);
            dropdown1.setSVG(one);
            SVG two = SVG.getFromResource(getResources(), R.raw.next);
            dropdown2.setSVG(two);
            SVG three = SVG.getFromResource(getResources(), R.raw.next);
            dropdown3.setSVG(three);
            SVG correct = SVG.getFromResource(getResources(), R.raw.success_logo);
            certificate.setSVG(correct);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(ComparisionSuccessful.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(ComparisionSuccessful.this,
                            new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_ASK_PERMISSIONS);

                } else {
                    Intent i = new Intent(ComparisionSuccessful.this, CameraRectangle.class);
                    startActivityForResult(i, 503);
                }

            }
        });
        NFC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap icon = BitmapFactory.decodeResource(getResources(),
                        R.drawable.arun);
                LivenessData.getInstance().setNfcBitmap(icon);
                Intent i=new Intent(ComparisionSuccessful.this, LivenessActivity.class);
                startActivity(i);

            }
        });
        Facing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bitmap icon = BitmapFactory.decodeResource(ComparisionSuccessful.this.getResources(),
//                        R.drawable.arun);
//                LivenessData.getInstance().setNfcBitmap(icon);
//                Intent i=new Intent(ComparisionSuccessful.this,LivenessActivity.class);
//                startActivity(i);

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 502) {
            if (resultCode == RESULT_OK) {
                boolean keypad = data.getBooleanExtra("keypad", false);
                MRZInfo info = (MRZInfo) data.getSerializableExtra(MRZ_RESULT);
                if (info != null) {

                    String firstName = info.getPrimaryIdentifier().replaceAll("<", "").trim();
                    String lastname = info.getSecondaryIdentifier().replaceAll("<", "").trim();
                    String fullName = lastname + " " + firstName;
                    String nationality = info.getNationality();
                    String gender = info.getGender().name();
                    Utility.getInstance().setFullName(fullName);
                    Utility.getInstance().setNationality(nationality);
                    Utility.getInstance().setGender(gender);
                    if (!TextUtils.isEmpty(info.getDocumentNumber()))
                        Utility.getInstance().setPassportNumber(info.getDocumentNumber());
                    else
                        Utility.getInstance().setPassportNumber("");
                    if (!TextUtils.isEmpty(info.getDateOfBirth()))
                        Utility.getInstance().setDateOfBirth(info.getDateOfBirth());
                    else
                        Utility.getInstance().setDateOfBirth("");
                    if (!TextUtils.isEmpty(info.getDateOfExpiry()))
                        Utility.getInstance().setExpiryDate(info.getDateOfExpiry());
                    else
                        Utility.getInstance().setExpiryDate("");
                    LogService.writeLog(getApplicationContext(), "Passport scan completed :" + info.toString());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            gotoRead();
                        }
                    }, 500);

                } else if (keypad) {
                    Utility.getInstance().setPassportNumber("");
                    Utility.getInstance().setDateOfBirth("");
                    Utility.getInstance().setExpiryDate("");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            gotoRead();
                        }
                    }, 500);
                } else {
                    Toast.makeText(getApplicationContext(), "Cannot read password", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (requestCode == 503) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    gotoRead();
                }
            }, 0);
        }
    }

    private void gotoRead() {
        Intent intent = new Intent(ComparisionSuccessful.this, MainActivityMRZ.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent i = new Intent(ComparisionSuccessful.this, CameraRectangle.class);
                startActivityForResult(i, 503);
            } else {

                if (ActivityCompat.checkSelfPermission(ComparisionSuccessful.this,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {

                    ActivityCompat.requestPermissions(ComparisionSuccessful.this,
                            new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_ASK_PERMISSIONS);
                }
            }

//                }
//                Toas/t.makeText(this, "Camera permission not granted", Toast.LENGTH_SHORT).show();


        }
    }

}