package com.example.mlbastan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ProfileActivitiy extends AppCompatActivity {

TextView puanText;
String gorevlergecici[];
static ArrayList<String> gorevler=new ArrayList<String>();
static int puan;
static int objPred;
static int intPred;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_activitiy);

        puanText = findViewById(R.id.point);

        //Dizileri SharedPref ile tutamadigimiz icin String olarak tutup parcaliyorum.

        gorevler.clear();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String dizigibi = settings.getString("xxx", "Airline-Furniture-Milk-Mountain-Motorcycle-Laptop-Room-Restaurant-Red-Sky-Star-Tea-Text-Tomato-Water");
        gorevlergecici = dizigibi.split("-");

        for (int x=0; x<gorevlergecici.length; x++) {

            gorevler.add(gorevlergecici[x]);

        }

        puan = settings.getInt("puanitut",0);
        puanText.setText(String.valueOf(puan));

        objPred = settings.getInt("objPred", 0);
        intPred = settings.getInt("intPred", 0);

    }

    public void analiz(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_out_reverse, R.anim.right_in_reverse);
        finish();
    }

    public void gorevlerigor(View view){
        Intent intent = new Intent(this, Gorevler.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        finish();

    }

    public void goMarket(View view){
        Intent intent = new Intent(this, Market.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        finish();

    }

}
