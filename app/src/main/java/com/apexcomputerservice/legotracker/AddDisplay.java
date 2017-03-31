package com.apexcomputerservice.legotracker;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.apexcomputerservice.legotracker.model.Chain;
import com.apexcomputerservice.legotracker.model.LegoTypes;
import com.apexcomputerservice.legotracker.model.Skin;
import com.apexcomputerservice.legotracker.model.Store;

import java.util.List;

public class AddDisplay extends AppCompatActivity {

    EditText datePlaced, numberPlaced ,notes;
    int chainId , storeId, legoId, skinId;
    Spinner spinnerChain, spinnerStore, spinnerDisplay, spinnerSkin;
    DatabaseHelper helper;
    Display newDisplay;
    String TAG="SQLInputCheck";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_display);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Enter method to process EditText input
                addDisplayToDatabase();
            }
        });



        datePlaced = (EditText) findViewById(R.id.etDatePlaced);
        notes = (EditText) findViewById(R.id.etNotes);
        numberPlaced = (EditText) findViewById(R.id.etNumberPlaced);
        spinnerStore = (Spinner) findViewById(R.id.spinnerStore);
        spinnerDisplay = (Spinner) findViewById(R.id.spinnerDisplay);
        spinnerSkin = (Spinner) findViewById(R.id.spinnerSkin);
        spinnerChain = (Spinner) findViewById(R.id.spinnerChain);

        //Set Listeners
        spinnerChain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
                //Grab id
                Chain chainSelected = (Chain)parent.getItemAtPosition(pos);
                chainId = chainSelected.getChainid();
                Log.v(TAG, "Chain ID Selected = " + Integer.toString(chainId));
                //TODO Load Store spinner based on chain id
                loadStoreSpinnerData(chainId);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView){

            }
        });

        loadChainSpinnerData();

        spinnerStore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
                Store storeSelected = (Store)parent.getItemAtPosition(pos);
                storeId = storeSelected.getStoreid();
                Log.v(TAG, "Store ID Selected = " + Integer.toString(storeId));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView){

            }
        });

        spinnerDisplay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
                LegoTypes displaySelected = (LegoTypes)parent.getItemAtPosition(pos);
                legoId = displaySelected.getTypeid();
                Log.v(TAG, "Display ID Selected = " + Integer.toString(legoId));
            }
            @Override
                    public void onNothingSelected(AdapterView<?> adapterView){

            }
        });

        loadDisplaySpinnerData();

        spinnerSkin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
                Skin skinSelected = (Skin)parent.getItemAtPosition(pos);
                skinId = skinSelected.getSkinid();
                Log.v(TAG, "Skin ID Selected = " + Integer.toString(skinId));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView){

            }
        });

        loadSkinSpinnerData();

    }


    private void loadChainSpinnerData(){
        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
        List<Chain> chains = helper.getChains();

        //Create adapter for spinner
        ArrayAdapter<Chain> dataAdapter = new ArrayAdapter<Chain>(this, android.R.layout.simple_spinner_item, chains);
        //Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Attach dat adapter to spinner
        spinnerChain.setAdapter(dataAdapter);

    }

    private void loadStoreSpinnerData(int chainid){
        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
        List<Store> stores = helper.getChainStores(chainid);

        ArrayAdapter<Store> dataAdapter = new ArrayAdapter<Store>(this, android.R.layout.simple_spinner_item, stores);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStore.setAdapter(dataAdapter);
    }

    private void loadDisplaySpinnerData(){
        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
        List<LegoTypes> legos = helper.getLegoTypes();

        ArrayAdapter<LegoTypes> dataAdapter = new ArrayAdapter<LegoTypes>(this, android.R.layout.simple_spinner_item, legos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDisplay.setAdapter(dataAdapter);
    }

    private void loadSkinSpinnerData(){
        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
        List<Skin> skins = helper.getSkins();

        ArrayAdapter<Skin> dataAdapter = new ArrayAdapter<Skin>(this, android.R.layout.simple_spinner_item,skins);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSkin.setAdapter(dataAdapter);
    }

    private void addDisplayToDatabase(){
        //TODO add display to database
    }
}
