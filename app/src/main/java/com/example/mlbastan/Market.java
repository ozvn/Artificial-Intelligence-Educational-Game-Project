package com.example.mlbastan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Market extends AppCompatActivity {
    Button buyobj;
    Button buyint;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int puankontrol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        buyobj = findViewById(R.id.buyobj);
        buyint = findViewById(R.id.buyint);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPreferences.edit();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        puankontrol = settings.getInt("puanitut",0);


        if (ProfileActivitiy.objPred!=0) {
            buyobj.setVisibility(View.INVISIBLE);
        }

        if (ProfileActivitiy.intPred!=0) {
            buyint.setVisibility(View.INVISIBLE);
        }

    }

    public void buypredclick(View view){
        if (ProfileActivitiy.puan>1){

            buyobj.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Congratulations You Got That Skill", Toast.LENGTH_LONG).show();
            int puantut = ProfileActivitiy.puan;
            puantut = puantut-2;
            editor.putInt("puanitut", puantut);
            editor.commit();
            editor.putInt("objPred", 1);
            editor.commit();

        }else {
            Toast.makeText(this, "You Don't Have Enough NP (2NP)", Toast.LENGTH_LONG).show();
        }
    }


    public void internetless(View view){
        if (ProfileActivitiy.puan>9){

            buyint.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Congratulations You Got That Skill", Toast.LENGTH_LONG).show();
            int puantut = ProfileActivitiy.puan;
            puantut = puantut-10;
            editor.putInt("puanitut", puantut);
            editor.commit();
            editor.putInt("intPred", 1);
            editor.commit();

        }else {
            Toast.makeText(this, "You Don't Have Enough NP (10NP)", Toast.LENGTH_LONG).show();
        }
    }

    public void goProfile(View view){

        Intent intent = new Intent(this, ProfileActivitiy.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_out_reverse, R.anim.right_in_reverse);
        finish();

    }


}
