package com.apexcomputerservice.legotracker;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apexcomputerservice.legotracker.model.Chain;

import static android.support.v7.appcompat.R.styleable.View;

public class AddChains extends AppCompatActivity {

    /*  Build views
        Get data from EditText
        Make sure EditText is not null-else return error to user
        Call Database Helper to add to database
        Upon completion, return to Show Chains
     */

    TextView addDirections;
    EditText addChains;
    DatabaseHelper helper;
    Chain newChain;
    String TAG="SQLInputCheck";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chains);

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
                addChainToDatabase();
            }
        });

        //Place FAB above soft keyboard
        //Add "android:windowSoftInputMode="adjustResize" to activity manifest

        addDirections = (TextView) findViewById(R.id.tvAddDirections);
        addChains = (EditText) findViewById(R.id.etAddChain);


    }

    public void addChainToDatabase(){  //Trigger this method on SAVE click
            Log.v(TAG, "addChains = " + addChains.getText().toString());
        String chainToAdd = addChains.getText().toString();
            Log.v(TAG,"chainToAdd = " + chainToAdd);
        boolean isEmpty = chainToAdd == null || chainToAdd.trim().length() == 0;
        if (isEmpty){
            // Make Toast to display error msg
            Toast.makeText(getApplicationContext(), "Chain cannot be empty", Toast.LENGTH_SHORT).show();
        } else{
            //Add to db
            helper = new DatabaseHelper(this);
            helper.openWriteableDB();

            newChain = new Chain();
            newChain.setChainName(chainToAdd);
                String newTest = newChain.getChainName();
                Log.v(TAG,"newTest = " + newTest);
            helper.addChain(newChain);
            Toast.makeText(getApplicationContext(),"Chain " + chainToAdd +" added", Toast.LENGTH_SHORT).show();
        }
    }


}

