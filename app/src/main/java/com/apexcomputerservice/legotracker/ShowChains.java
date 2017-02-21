package com.apexcomputerservice.legotracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.apexcomputerservice.legotracker.model.Chain;

import java.util.ArrayList;
import java.util.List;

public class ShowChains extends AppCompatActivity {

    DatabaseHelper helper;
    List<Chain> chainList;
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_chains);

        helper = new DatabaseHelper(this);
        chainList = new ArrayList<Chain>();
        helper.openReadableDB();
        chainList = helper.getChains();
        helper.closeDB();

        mRecyclerView = (RecyclerView)findViewById(R.id.rv);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RVAdapterChain(chainList);
        mRecyclerView.setAdapter(mAdapter);



    }
}
