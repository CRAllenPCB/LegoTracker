package com.apexcomputerservice.legotracker;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.v4.app.DialogFragment;


import com.apexcomputerservice.legotracker.model.Chain;

import java.util.ArrayList;
import java.util.List;

public class ShowChains extends AppCompatActivity {

    DatabaseHelper helper;
    List<Chain> chainList;
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String chainTable;//Name of the chainTable
    boolean emptyTable;
    TextView emptyTextView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_chains);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {


            }
        });





        //Check that table is populate
        chainTable="chain";
        helper = new DatabaseHelper(this);
        helper.openReadableDB();
        emptyTable = helper.isTableEmpty(chainTable);
        helper.closeDB();
        if(emptyTable){
            emptyTextView = (TextView) findViewById(R.id.emptyTextView);
        }else {


            helper = new DatabaseHelper(this);
            chainList = new ArrayList<Chain>();
            helper.openReadableDB();
            chainList = helper.getChains();
            helper.closeDB();

            mRecyclerView = (RecyclerView) findViewById(R.id.rv);
            mRecyclerView.setHasFixedSize(true);

            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);

            mAdapter = new RVAdapterChain(chainList);
            mRecyclerView.setAdapter(mAdapter);
        }


    }


}