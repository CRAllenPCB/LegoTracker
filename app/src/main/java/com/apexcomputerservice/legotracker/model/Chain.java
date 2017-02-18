package com.apexcomputerservice.legotracker.model;

/**
 * Created by chris on 2/3/17.
 */

public class Chain {
    //private variables
    int chainid;
    String chainName;

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
    }
}
