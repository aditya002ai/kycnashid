package com.omanid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Camera_Result extends AppCompatActivity {
    SVGImageView logo, back;
    TextView DocumnetNumber;
    TextView Dob;
    TextView ExpriDate;

    public int selectedDateField;
    public String dateOfBirth = "000000";
    public int[] dateOfBirthIntArray = {15, 6, 1980};
    public String dateOfExpiration = "000000";
    public int[] dateOfExpirationIntArray = {15, 6, 2020};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_result);
        logo = findViewById(R.id.logo);
        back = findViewById(R.id.backbutton);
        DocumnetNumber = findViewById(R.id.documnetNumber);
        Dob = findViewById(R.id.dob);
        ExpriDate = findViewById(R.id.ExpriDate);
        try {
            SVG svg = SVG.getFromResource(getResources(), R.raw.logo);
            logo.setSVG(svg);
            SVG back1 = SVG.getFromResource(getResources(), R.raw.arowback);
            back.setSVG(back1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Camera_Result.this, ComparisionSuccessful.class);
                startActivity(i);

            }
        });
        DocumnetNumber.setText(Utility.getInstance().getPassportNumber());
        Dob.setText(Utility.getInstance().getDateOfBirth());
        ExpriDate.setText(Utility.getInstance().getExpiryDate());
//        Dob= Utility.getInstance().setDateOfBirth(DocumnetNumber);
//        ExpriDate= Utility.getInstance().setExpiryDate(DocumnetNumber);

//        901217
        Dob.setText(formateDateFromstring("yymmdd", "d MMM yyyy",Utility.getInstance().getDateOfBirth() ));
        ExpriDate.setText(formateDateFromstring("yymmdd", "d MMM yyyy",Utility.getInstance().getExpiryDate() ));


        Dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        ExpriDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    public String formatMonth(String month) {
        String value  = null;
        try {
            SimpleDateFormat monthParse = new SimpleDateFormat("MM");
            SimpleDateFormat monthDisplay = new SimpleDateFormat("MMM");
            value = monthDisplay.format(monthParse.parse(month));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate) {
        Date parsed = null;
        String outputDate = "";
        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());
        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);
        } catch (ParseException e) {
        }
        return outputDate;

    }

}