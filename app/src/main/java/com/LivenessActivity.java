package com;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;

import com.omanid.R;
import com.omanid.Utility;

public class LivenessActivity extends AppCompatActivity {

    ImageView image1,image2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liveness);

        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);

        try {
            Matrix matrix = new Matrix();
            matrix.postRotate(270);
            Bitmap bmp = BitmapFactory.decodeByteArray(Utility.getInstance().scannedImage, 0, Utility.getInstance().scannedImage.length);

            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bmp, 2048, 2048, true);
            Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
//            ImageView image = (ImageView) findViewById(R.id.image1);
            image1.setImageBitmap(rotatedBitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}