//package com.omanid;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.caverock.androidsvg.SVG;
//import com.caverock.androidsvg.SVGImageView;
//import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
//
//import org.jetbrains.annotations.Nullable;
//
//public class bottomSheet extends BottomSheetDialogFragment {
//    SVGImageView logo;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable
//            ViewGroup container, @Nullable Bundle savedInstanceState)
//        {
//            View v = inflater.inflate(R.layout.activity_bottom_sheet,
//                    container, false);
//            logo = logo.findViewById(R.id.loading);
//
//            try {
//                SVG svg = SVG.getFromResource(getResources(), R.raw.nfc_logo);
//                logo.setSVG(svg);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return v;
//    }
//}