package com.omanid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGImageView;

public class NFC_result_Activity extends AppCompatActivity {
    SVGImageView logo,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_result);
        logo = findViewById(R.id.logo);
        back = findViewById(R.id.backbutton);
        try {
            SVG svg = SVG.getFromResource(getResources(), R.raw.logo);
            logo.setSVG(svg);
            SVG back1 = SVG.getFromResource(getResources(), R.raw.arowback);
            back.setSVG(back1);
    }catch (Exception e) {
            e.printStackTrace();
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(NFC_result_Activity.this,ComparisionSuccessful.class);
                startActivity(i);

            }
        });
}}