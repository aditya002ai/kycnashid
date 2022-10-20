package com.omanid;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class LogService extends IntentService {
    private static final String MESSAGE = "message";

    private static final SimpleDateFormat format_date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    static FirebaseFirestore db  = FirebaseFirestore.getInstance();

    private File logFile;

    public LogService() {
        super("LogService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        FirebaseApp.initializeApp(this);

        String path = Environment.getExternalStorageDirectory().getPath();
        Calendar c = Calendar.getInstance();
        logFile = new File(path + "/passportData");
        try {
            if (!logFile.exists())
                logFile.mkdirs();
            logFile = new File(logFile.getAbsolutePath() +Utility.getInstance().getFilePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static StringBuilder stringBuilder=new StringBuilder();

    public static void writeLog(Context context, String message) {
//        stringBuilder.append(message+"\n");
//        addDataFCM(message);
//
//        if (ContextCompat.checkSelfPermission(context,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                == PackageManager.PERMISSION_GRANTED) {
//            Intent intent = new Intent(context, LogService.class);
//            intent.putExtra(MESSAGE, message);
//            context.startService(intent);
//        }
    }



    private static void addDataFCM(String value){

        Map<String, Object> city = new HashMap<>();
        city.put("data", value);

// Add a new document with a generated ID
        db.collection("Log_"+ Utility.getInstance().documentKey)
                .add(city)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });



    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String message = intent.getStringExtra(MESSAGE);
            try {
                Calendar c = Calendar.getInstance();
                MLog.d(message);
                if (logFile != null) {
                    BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
                    buf.append(format_date.format(c.getTime()) + "   " + message);
                    buf.newLine();
                    buf.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
