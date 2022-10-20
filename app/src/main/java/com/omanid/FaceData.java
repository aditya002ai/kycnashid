package com.omanid;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class FaceData {
    private static final FaceData ourInstance = new FaceData();

    public static FaceData getInstance() {
        return ourInstance;
    }

    private FaceData() {
    }

    public File getImageSelfie() {
        return imageSelfie;
    }

    public void setImageSelfie(File imageSelfie) {
        this.imageSelfie = imageSelfie;
    }

    File imageSelfie;

    public Bitmap getFaceCroped() {
        return faceCroped;
    }

    public void setFaceCroped(Bitmap faceCroped) {
        this.faceCroped = faceCroped;
    }

    private Bitmap faceCroped;

    public Bitmap getManuall() {
        return manuall;
    }

    public void setManuall(Bitmap manuall) {
        this.manuall = manuall;
    }

    private Bitmap manuall;

    public File  convertFile(Context context){
        //create a file to write bitmap data
        File f = new File(context.getCacheDir(), "xyz");
        try {
            f.createNewFile();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        faceCroped.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }
}
