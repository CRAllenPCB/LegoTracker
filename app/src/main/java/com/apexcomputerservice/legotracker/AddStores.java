package com.apexcomputerservice.legotracker;

import android.location.Address;
import android.location.Geocoder;
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
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
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
                finish();
            }
        });

        spinner = (Spinner) findViewById(R.id.spinnerChain);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
                //Grab id
                Chain chainSelected = (Chain)parent.getItemAtPosition(pos);
                chainId = chainSelected.getChainid();
                chainName = chainSelected.getChainName();
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
            storeNumToAdd = addStoreNum.getText().toString();
            if (addAddress1 == null || addAddress1.getText().toString().trim().length() == 0){
                address1ToAdd = "";
            } else {
                address1ToAdd = addAddress1.getText().toString();
            }
            if (addAddress2 == null || addAddress2.getText().toString().trim().length() == 0){
                address2ToAdd = null;
            } else{
                address2ToAdd = addAddress2.getText().toString();
            }
            if (addCity == null || addCity.getText().toString().trim().length() == 0){
                cityToAdd = "";
            } else {
                cityToAdd = addCity.getText().toString();
            }
            if (addState == null || addState.getText().toString().trim().length() == 0){
                stateToAdd = "";
            } else{
                stateToAdd = addState.getText().toString();
            }
            if (addZip == null || addZip.getText().toString().trim().length() == 0){
                zipToAdd = "";
            } else {
                zipToAdd = addZip.getText().toString();
            }

            //TODO Add a way to identfy stores without LatLng set (not found) and allow to set LatLng at current location
            String fullAddress = address1ToAdd + ", " + cityToAdd + ", " + stateToAdd + " " + zipToAdd;
            String fullStoreName = chainName + " " + storeNumToAdd;
            LatLng latLong;
                Log.v("TAG", "Show address: " + fullAddress);
                Log.v("TAG", "Full store name is " + fullStoreName);
            latLong = getLocationFromAddress(fullAddress);
                Log.v("TAG", "Location is " + latLong);
            String latString = null;
            String lngString = null;
            if (latLong == null) {
                latLong = getLocationFromName(fullStoreName);}
            Double latDouble = latLong.latitude;
                    Log.v("TAG", "Double Lat is " + latDouble);
            Double lngDouble = latLong.longitude;
                    Log.v("TAG", "Dobule Long is " + lngDouble);
            latString = latDouble.toString();
            lngString = lngDouble.toString();


                    Log.v("TAG", "Lat String is " + latString);
                    Log.v("TAG", "Lng String is " + lngString);

            helper = new DatabaseHelper(this);
            helper.openWriteableDB();

            newStore = new Store();
            newStore.setChainid(chainIdToAdd);
            newStore.setStoreNumber(storeNumToAdd);
            newStore.setStoreAddress1(address1ToAdd);
            newStore.setStoreAddress2(address2ToAdd);
            newStore.setStoreCity(cityToAdd);
            newStore.setStoreState(stateToAdd);
            newStore.setStoreZip(zipToAdd);
            newStore.setLat(latString);
            newStore.setLng(lngString);
            helper.addStore(newStore);
            helper.closeDB();

            Toast.makeText(getApplicationContext(),"Store "+ chainName + " " + storeNumToAdd + " added", Toast.LENGTH_SHORT).show();

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

    public LatLng getLocationFromAddress(String strAddress){

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng p1 = null;

        try {
            //May throw an IOException
            address = coder.getFromLocationName(strAddress, 1);
                Log.v("TAG", "Size of array is " + address.size());
            if (address == null) {
                return null;
            }
            if (address.size() == 0){
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());


        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return p1;
    }

    public LatLng getLocationFromName(String strName){

        Geocoder coder = new Geocoder(this);
        List<Address> name;
        LatLng p1 = null;

        try {
            //May throw an IOException
            name = coder.getFromLocationName(strName, 1);
            Log.v("TAG", "Size of array is " + name.size());
            if (name == null) {
                return null;
            }
            if (name.size() == 0){
                return null;
            }
            Address location = name.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());


        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return p1;
    }
}
