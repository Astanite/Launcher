package com.astanite.launcher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class RecyclerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.appsList);
        Log.i("MyInfo","Making adapter");
        RAdapter radapter = new RAdapter(this);
        Log.i("MyInfo","Setting adapter");
        recyclerView.setAdapter(radapter);
        Log.i("MyInfo","Making Layout Manager");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
