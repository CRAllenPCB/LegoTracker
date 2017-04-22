package com.apexcomputerservice.legotracker;

import android.content.Context;
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
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;


import com.apexcomputerservice.legotracker.model.Chain;

import java.util.ArrayList;
import java.util.List;

public class ShowChains extends AppCompatActivity {

    DatabaseHelper helper;
    List<Chain> chainList;
    RecyclerView mRecyclerView;
    RVAdapterChain mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private final static String TABLE_NAME ="chain";//Name of the chainTable
    boolean emptyTable;
    TextView emptyTextView;
    Toolbar toolbar;
    Context context;


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
                //call Add Chains here
                startActivity(new Intent(ShowChains.this, AddChains.class));
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
       // helper.openReadableDB();
        emptyTable = helper.isTableEmpty(TABLE_NAME);
        //helper.closeDB();
        if (emptyTable) {
            emptyTextView = (TextView) findViewById(R.id.emptyTextView);
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            emptyTextView = (TextView) findViewById(R.id.emptyTextView);
            emptyTextView.setVisibility(View.GONE);
            helper = new DatabaseHelper(this);
            chainList = new ArrayList<Chain>();
           // helper.openReadableDB();
            chainList = helper.getChains();
           // helper.closeDB();

            mRecyclerView = (RecyclerView) findViewById(R.id.rv);
            mRecyclerView.setHasFixedSize(true);

            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);

            mAdapter = new RVAdapterChain(this, chainList);
            mRecyclerView.setAdapter(mAdapter);

        }
    }

        @Override
        public boolean onContextItemSelected(MenuItem item){
            if (item.getTitle()=="Edit"){
                Toast.makeText(this,"Edit selected", Toast.LENGTH_LONG).show();
            }else if (item.getTitle()=="Delete"){
                mAdapter.removeChain();
            }
            return super.onContextItemSelected(item);

        }






}
