package com.example.saurabhgoyal.buyhatkeproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by saurabh goyal on 10/23/2016.
 */
public class SearchItemActivity extends ActionBarActivity {
    RecyclerView recyclerView;

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerView= (RecyclerView) findViewById(R.id.drawerlist);
        VivzAdapter3 vivzAdapter=new VivzAdapter3(getApplicationContext(),HomeActivity.searchMessageList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recyclerView.setAdapter(vivzAdapter);
        toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }



}
