package com.apexcomputerservice.legotracker.model;

import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by chris on 2/3/17.
 */

public class Chain {
    //private variables
    int chainid;
    String chainName;
    String TAG = "SQLInputCheck";

    //Empty constructor
    public Chain() {

    }
    //constructor
    public Chain(int chainid, String chainName){
        this.chainid = chainid;
        this.chainName = chainName;
    }

    public int getChainid() {
        return chainid;
    }

    public void setChainid(int chainid) {
        this.chainid = chainid;
    }

    public String getChainName() {
        return chainName;
    }

    public void setChainName(String chainName) {
        this.chainName = chainName;
            Log.v(TAG, "Chain obj name is " + getChainName() );
    }
}
