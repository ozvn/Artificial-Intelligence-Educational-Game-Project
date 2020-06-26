package com.example.mlbastan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mlbastan.Helper.InternetCheck;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.cloud.FirebaseVisionCloudDetectorOptions;
import com.google.firebase.ml.vision.cloud.label.FirebaseVisionCloudLabel;
import com.google.firebase.ml.vision.cloud.label.FirebaseVisionCloudLabelDetector;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetector;
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetectorOptions;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {

    CameraView cameraView;
    Button btnDetect;
    AlertDialog waitingDialog;

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();

    }

    @Override
    protected void onPause()  {
        super.onPause();
        cameraView.stop();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraView = (CameraView)findViewById(R.id.camera_view);

        btnDetect = (Button)findViewById(R.id.btn_detect);

        waitingDialog = new SpotsDialog.Builder().setContext(this).setMessage("Please waiting. Don't move. I am trying to understand").setCancelable(false).build();

        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                waitingDialog.show();
                Bitmap bitmap = cameraKitImage.getBitmap();
                bitmap = Bitmap.createScaledBitmap(bitmap,cameraView.getWidth(),cameraView.getHeight(),false);
                cameraView.stop();

                runDetector(bitmap);
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });

        btnDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.start();
                cameraView.captureImage();
            }
        });
    }

    private void runDetector(Bitmap bitmap) {
       final FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);



        new InternetCheck(new InternetCheck.Consumer() {
            @Override
            public void accept(boolean internet) {
                if(internet){
                    //internet varsa cloud kullanicaz.

                    FirebaseVisionCloudDetectorOptions options =
                            new FirebaseVisionCloudDetectorOptions.Builder()
                                    .setMaxResults(1) // En yuksek ihtimalli 1. sonucu al
                                    .build();
                    FirebaseVisionCloudLabelDetector detector =
                            FirebaseVision.getInstance().getVisionCloudLabelDetector(options);

                    detector.detectInImage(image)
                            .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionCloudLabel>>() {
                                @Override
                                public void onSuccess(List<FirebaseVisionCloudLabel> firebaseVisionCloudLabels) {
                                    processDataResultCloud(firebaseVisionCloudLabels);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("EDMTERROR",e.getMessage());
                                }
                            });

                }

                else if (ProfileActivitiy.intPred ==0) {

                    waitingDialog.setMessage("Please Connect To Internet Or Gain WWI Skill");
                    Toast.makeText(getApplicationContext(),"Please Connect To Internet Or Gain WWI Skill",Toast.LENGTH_LONG).show();

                    new CountDownTimer(5000, 1000) {
                        public void onTick(long millisUntilFinished) {
                        }

                        public void onFinish() {
                            Intent intent = new Intent(MainActivity.this,ProfileActivitiy.class);
                            startActivity(intent);
                            finish();
                        }
                    }.start();


                }

                    else if (ProfileActivitiy.intPred==1) {

                    FirebaseVisionLabelDetectorOptions options =
                            new FirebaseVisionLabelDetectorOptions.Builder()
                                    .setConfidenceThreshold(0.8f) // En yuksek ihtimalli sonucu al
                                    .build();
                    FirebaseVisionLabelDetector detector =
                            FirebaseVision.getInstance().getVisionLabelDetector(options);


                    detector.detectInImage(image)
                            .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionLabel>>() {
                                @Override
                                public void onSuccess(List<FirebaseVisionLabel> firebaseVisionLabels) {
                                    processDataResult(firebaseVisionLabels);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("EDMTERROR", e.getMessage());
                                }
                            });

                }




            }
        });
    }

    private void processDataResultCloud(List<FirebaseVisionCloudLabel> firebaseVisionCloudLabels) {
        for (FirebaseVisionCloudLabel label : firebaseVisionCloudLabels)
        {

            if (ProfileActivitiy.objPred==1){
                Toast.makeText(this, "I Think This Is "+label.getLabel(), Toast.LENGTH_LONG).show();
            }

            ArrayList<String> gorevler = ProfileActivitiy.gorevler;

            for (int x=0; x<gorevler.size(); x++) {

                if (label.getLabel().matches(gorevler.get(x))){

                    Toast.makeText(this, "Thanks I Learned The "+label.getLabel(), Toast.LENGTH_LONG).show();

                    gorevler.remove(x);
                    String yenidizitut = null;
                    System.out.println("diziye sayi: "+gorevler.size());

                    for (int y=0; y<gorevler.size();y++) {
                        if (y==0){
                            yenidizitut = gorevler.get(0);
                        }else{
                            yenidizitut = yenidizitut + "-" + gorevler.get(y);
                        }

                    }

                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("xxx");
                    editor.putString("xxx", yenidizitut);
                    editor.commit();

                    if (yenidizitut==null) {
                        editor.putString("xxx", "All Missions Completed");
                    }

                    int puan = settings.getInt("puanitut",0);
                    puan = puan + 1;
                    editor.putInt("puanitut", puan);
                    editor.commit();

                    break;
                }

            }

        }
        if (waitingDialog.isShowing()){
            waitingDialog.dismiss();
        }
    }

    private void processDataResult(List<FirebaseVisionLabel> firebaseVisionLabels) {
        for (FirebaseVisionLabel label : firebaseVisionLabels)
        {
            Toast.makeText(this, "WWI SKILL: "+label.getLabel(), Toast.LENGTH_LONG).show();
        }
        if (waitingDialog.isShowing()){
            waitingDialog.dismiss();
        }
    }

    public void profileGo(View view){
        Intent intent = new Intent(this, ProfileActivitiy.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        finish();
    }

}
