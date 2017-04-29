package com.apexcomputerservice.legotracker;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.apexcomputerservice.legotracker.adapters.RVAdapterStore;
import com.apexcomputerservice.legotracker.model.Store;

import java.util.ArrayList;
import java.util.List;

public class ShowStores extends AppCompatActivity {

    DatabaseHelper helper;
    List<Store> storeList;
    RecyclerView mRecyclerView;
    RVAdapterStore mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private final static String TABLE_NAME ="stores";//Name of the storeTable
    boolean emptyTable;
    TextView emptyTextView;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_stores);

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
                //call Add Stores here
                startActivity(new Intent(ShowStores.this, AddStores.class));
            }
        });

        populateView();

    }

    @Override
    public void onRestart(){
        super.onRestart();
        populateView();
    }


    public void populateView() {
        //Check that table is populate

        helper = new DatabaseHelper(this);
        helper.openReadableDB();
        emptyTable = helper.isTableEmpty(TABLE_NAME);
        helper.closeDB();
        if (emptyTable) {
            emptyTextView = (TextView) findViewById(R.id.emptyTextView);
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            emptyTextView = (TextView) findViewById(R.id.emptyTextView);
            emptyTextView.setVisibility(View.GONE);
            helper = new DatabaseHelper(this);
            storeList = new ArrayList<Store>();
            helper.openReadableDB();
            storeList = helper.getStores();
            helper.closeDB();

            mRecyclerView = (RecyclerView) findViewById(R.id.rv);
            mRecyclerView.setHasFixedSize(true);

            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);

            mAdapter = new RVAdapterStore(this, storeList);
            mRecyclerView.setAdapter(mAdapter);

        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        if (item.getTitle()=="Edit"){
            Toast.makeText(this,"Edit selected", Toast.LENGTH_LONG).show();
        }else if (item.getTitle()=="Delete"){
            mAdapter.removeStore();
        }
        return super.onContextItemSelected(item);

    }






}