package com.example.mlbastan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Gorevler extends AppCompatActivity {
   static RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gorevler);




        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        GorevAdapter GorevAdapter = new GorevAdapter(GorevModel.getData(), this);
        recyclerView.setAdapter(GorevAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


    }

    public void goProfile(View view){

        Intent intent = new Intent(this, ProfileActivitiy.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_out_reverse, R.anim.right_in_reverse);
        finish();

    }

}
