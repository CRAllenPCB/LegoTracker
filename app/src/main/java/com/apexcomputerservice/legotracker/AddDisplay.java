package com.apexcomputerservice.legotracker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.apexcomputerservice.legotracker.model.Brands;
import com.apexcomputerservice.legotracker.model.Chain;
import com.apexcomputerservice.legotracker.model.Displays;
import com.apexcomputerservice.legotracker.model.LegoTypes;
import com.apexcomputerservice.legotracker.model.Product;
import com.apexcomputerservice.legotracker.model.Skin;
import com.apexcomputerservice.legotracker.model.Store;
import com.facebook.stetho.inspector.protocol.module.Database;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.text.InputType.TYPE_NULL;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class AddDisplay extends AppCompatActivity {

    EditText datePlaced, numberPlaced ,notes;
    int chainId , storeId, legoId, skinId, productID, brandId, initalQty;
    long datePlacedUnix;
    String notesText;
    Spinner spinnerChain, spinnerStore, spinnerDisplay, spinnerSkin, spinnerProduct, spinnerBrand;
    DatabaseHelper helper;
    Displays newDisplays;
    Calendar c;
    SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy", Locale.US);

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
        spinnerProduct = (Spinner) findViewById(R.id.spinnerProduct);
        spinnerBrand = (Spinner) findViewById(R.id.spinnerBrand);

        //Handle Date
        c = Calendar.getInstance();
        //Get rid of keyboard
        datePlaced.setInputType(InputType.TYPE_NULL);

        final DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                c.set(YEAR, year);
                c.set(MONTH, month);
                c.set(DAY_OF_MONTH, day);

                datePlaced.setText(sdf.format(c.getTime()));
                datePlacedUnix = c.getTimeInMillis()/1000;
            }
        };

        datePlaced.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new DatePickerDialog(AddDisplay.this,dateListener,c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //Set Listeners
        spinnerChain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
                //Grab id
                Chain chainSelected = (Chain)parent.getItemAtPosition(pos);
                chainId = chainSelected.getChainid();

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
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView){
            }
        });

        spinnerProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
                Product productSelected = (Product)parent.getItemAtPosition(pos);
                productID = productSelected.getProductid();
                loadDisplaySpinnerData(productID);
                loadBrandSpinner(productID);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView){
            }
        });



        loadProductSpinnerData();

        spinnerBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
                Brands brandSelected = (Brands)parent.getItemAtPosition(pos);
                brandId = brandSelected.getBrandid();

                loadSkinSpinnerData(legoId, productID, brandId);
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

                loadSkinSpinnerData(legoId, productID, brandId);
            }
            @Override
                    public void onNothingSelected(AdapterView<?> adapterView){
            }
        });

       loadDisplaySpinnerData(productID);

        spinnerSkin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
                Skin skinSelected = (Skin)parent.getItemAtPosition(pos);
                skinId = skinSelected.getSkinid();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView){
            }
        });
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

    private void loadDisplaySpinnerData(int productID){
        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
        List<LegoTypes> legos = helper.getLegoTypes(productID);

        ArrayAdapter<LegoTypes> dataAdapter = new ArrayAdapter<LegoTypes>(this, android.R.layout.simple_spinner_item, legos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDisplay.setAdapter(dataAdapter);
    }

    private void loadSkinSpinnerData(int legoId, int productID, int brandId){
        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
        List<Skin> skins = helper.getSkins(legoId, productID, brandId);

        ArrayAdapter<Skin> dataAdapter = new ArrayAdapter<Skin>(this, android.R.layout.simple_spinner_item,skins);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSkin.setAdapter(dataAdapter);
    }


    private void loadProductSpinnerData(){
        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
        List<Product> product = helper.getProducts();

        ArrayAdapter<Product> dataAdapter = new ArrayAdapter<Product>(this,android.R.layout.simple_spinner_item,product);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProduct.setAdapter(dataAdapter);
    }

    private void loadBrandSpinner(int productID){
        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
        List<Brands> brand = helper.getBrands(productID);

        ArrayAdapter<Brands> dataAdapter = new ArrayAdapter<Brands>(this,android.R.layout.simple_spinner_item,brand);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBrand.setAdapter(dataAdapter);
    }

    private void addDisplayToDatabase(){

        if (datePlaced == null ){
            Toast.makeText(getApplicationContext(), "Date cannot be empty", Toast.LENGTH_SHORT).show();
        } else {
            if (numberPlaced == null) {
                Toast.makeText(getApplicationContext(), "Number Placed cannot be empty", Toast.LENGTH_SHORT).show();
            } else {
                initalQty = Integer.parseInt(numberPlaced.getText().toString());
                notesText = notes.getText().toString();
                //Add to database
                helper = new DatabaseHelper(this);
                helper.openWriteableDB();

                newDisplays = new Displays();
                newDisplays.setPlacementDate(datePlacedUnix);
                newDisplays.setPlacementUp(1);
                newDisplays.setChainid(chainId);
                newDisplays.setStoreid(storeId);
                newDisplays.setTypeid(legoId);
                newDisplays.setInitalQty(initalQty);
                newDisplays.setCurrentQty(initalQty);
                newDisplays.setResold(0);
                newDisplays.setSkinid(skinId);
                newDisplays.setNotes(notesText);
                newDisplays.setProductId(productID);
                newDisplays.setBrandId(brandId);


                helper.addDisplay(newDisplays);
                helper.close();
                Toast.makeText(getApplicationContext(), "New Display Added", Toast.LENGTH_SHORT).show();

                setResult(Activity.RESULT_OK);
                finish();
            }
        }
    }
}
