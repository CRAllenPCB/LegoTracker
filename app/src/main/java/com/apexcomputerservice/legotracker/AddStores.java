package com.apexcomputerservice.legotracker;

import android.support.v7.app.ActionBar;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.apexcomputerservice.legotracker.model.Chain;
import com.apexcomputerservice.legotracker.model.Store;

import java.util.List;

public class AddStores extends AppCompatActivity {

    /*  Build views
        Get data from EditText
        Make sure EditText is not null-else return error to user
        Call Database Helper to add to database
        Upon completion, return to Show Chains
     */


    EditText addStoreNum, addAddress1, addAddress2, addCity, addState, addZip;
    String chainName;
    int chainId;
    Spinner spinner;
    DatabaseHelper helper;
    Store newStore;
    String TAG="SQLInputCheck";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stores);

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
                addStoreToDatabase();
            }
        });

        spinner = (Spinner) findViewById(R.id.spinnerChain);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
                //Grab id
                Chain chainSelected = (Chain)parent.getItemAtPosition(pos);
                chainId = chainSelected.getChainid();
                Log.v(TAG, "Chain ID Selected = " + Integer.toString(chainId));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView){

            }
        });

        loadSpinnerData();

        // Pull Chain ID from spinner
        addStoreNum = (EditText) findViewById(R.id.etStoreNum);
        addAddress1 = (EditText) findViewById(R.id.etAddress1);
        addAddress2 = (EditText) findViewById(R.id.etAddChain);
        addCity = (EditText) findViewById(R.id.etCity);
        addState = (EditText) findViewById(R.id.etState);
        addZip = (EditText) findViewById(R.id.etZip);

    }

    public void addStoreToDatabase(){
        //Pull chain id from above
        String storeNumToAdd, address1ToAdd, address2ToAdd,cityToAdd,stateToAdd,zipToAdd;


        boolean isEmpty = addStoreNum == null || addStoreNum.getText().toString().trim().length() == 0;

        if (isEmpty){
            Toast.makeText(getApplicationContext(), "Store Number cannot be empty", Toast.LENGTH_SHORT).show();
        } else {
            int chainIdToAdd = chainId;
            Log.v(TAG, "Chain ID To add = " + Integer.toString(chainIdToAdd));
            storeNumToAdd = addStoreNum.getText().toString();
            if (addAddress1 == null || addAddress1.getText().toString().trim().length() == 0){
                address1ToAdd = "";
            } else {
                address1ToAdd = addAddress1.getText().toString();
            }
            Log.v(TAG, "Address 1 to add = " + address1ToAdd.toString());
            if (addAddress2 == null || addAddress2.getText().toString().trim().length() == 0){
                address2ToAdd = null;
            } else{
                address2ToAdd = addAddress2.getText().toString();
            }
            Log.v(TAG, "Address 2 to add = " + address2ToAdd);
            if (addCity == null || addCity.getText().toString().trim().length() == 0){
                cityToAdd = "";
            } else {
                cityToAdd = addCity.getText().toString();
            }
            Log.v(TAG, "City to add = " + cityToAdd);
            if (addState == null || addState.getText().toString().trim().length() == 0){
                stateToAdd = "";
            } else{
                stateToAdd = addState.getText().toString();
            }
            Log.v(TAG, "State to add = " + stateToAdd);
            if (addZip == null || addZip.getText().toString().trim().length() == 0){
                zipToAdd = "";
            } else {
                zipToAdd = addZip.getText().toString();
            }
            Log.v(TAG, "Zip to add = " + zipToAdd);

            helper = new DatabaseHelper(this);
            helper.openWriteableDB();

            newStore = new Store();
            //Set Chain id
            newStore.setChainid(chainIdToAdd);
            newStore.setStoreNumber(storeNumToAdd);
            newStore.setStoreAddress1(address1ToAdd);
            newStore.setStoreAddress2(address2ToAdd);
            newStore.setStoreCity(cityToAdd);
            newStore.setStoreState(stateToAdd);
            newStore.setStoreZip(zipToAdd);
            helper.addStore(newStore);
            helper.closeDB();

            Toast.makeText(getApplicationContext(),"Store "+ chainName + " " + storeNumToAdd + "added", Toast.LENGTH_SHORT).show();

        }
    }



    private void loadSpinnerData(){
        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
        List<Chain> chains = helper.getChains();

        //Create adapter for spinner
        ArrayAdapter<Chain> dataAdapter = new ArrayAdapter<Chain>(this, android.R.layout.simple_spinner_item, chains);
        //Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Attach dat adapter to spinner
        spinner.setAdapter(dataAdapter);

    }
}
