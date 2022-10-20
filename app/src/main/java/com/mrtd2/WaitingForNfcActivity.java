package com.mrtd2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGImageView;
import com.mrtd2.BitiAndroid.AbstractNfcActivity;
import com.mrtd2.BitiAndroid.TagProvider;
import com.omanid.R;

import java.io.Serializable;


public class WaitingForNfcActivity extends AbstractNfcActivity implements Serializable
{

    private String passportNumber;
    private String dateOfBirth;
    private String dateOfExpiration;
    SVGImageView logo, back,NFCLogo;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_for_nfc);


        this.passportNumber = (String)getIntent().getSerializableExtra("passportNumber");
        this.dateOfBirth = (String)getIntent().getSerializableExtra("dateOfBirth");
        this.dateOfExpiration = (String)getIntent().getSerializableExtra("dateOfExpiration");
        logo = findViewById(R.id.logo);
        back = findViewById(R.id.backbutton);
        NFCLogo = findViewById(R.id.nfclogo);
        try {
            SVG svg = SVG.getFromResource(getResources(), R.raw.logo);
            logo.setSVG(svg);
            SVG back1 = SVG.getFromResource(getResources(), R.raw.arowback);
            back.setSVG(back1);
            SVG nfc = SVG.getFromResource(getResources(), R.raw.nfc_logo);
            NFCLogo.setSVG(nfc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WaitingForNfcActivity.this, MainActivityMRZ.class);
                startActivity(i);

            }
        });

        if(TagProvider.isTagReady()) {
            this.readPassport();
        }

    }

    public void onNewIntent(Intent intent)
    {
        ((TextView)findViewById(R.id.placeYourDeviceInstructions)).setText(getResources().getString(R.string.found_nfc_text));
        ((TextView)findViewById(R.id.placeYourDeviceInstructions)).setGravity(Gravity.CENTER_HORIZONTAL);
        super.onNewIntent(intent);

        this.readPassport();
    }

    private void readPassport()
    {
        Intent activityIntent = new Intent("bondi.nfcPassportReader.jan.mrtd2.ReadingPassportActivity");
        activityIntent.putExtra("passportNumber", this.passportNumber);
        activityIntent.putExtra("dateOfBirth", this.dateOfBirth);
        activityIntent.putExtra("dateOfExpiration", this.dateOfExpiration);
        startActivity(activityIntent);

        this.finish();
    }

}
