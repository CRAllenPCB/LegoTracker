package com.apexcomputerservice.legotracker.model;

/**
 * Created by chris on 2/3/17.
 */

public class Store {

    //private variables
    int storeid;
    int chainid;
    String storeNumber, storeAddress1, storeAddress2, storeCity, storeState, storeZip;

    //empty constructor
    public Store(){

    }

    //constructor
    public Store(int storeid, int chainid, String storeNumber, String storeAddress1, String storeAddress2, String storeCity, String storeState, String storeZip){
        this.storeid = storeid;
        this.chainid = chainid;
        this.storeNumber = storeNumber;
        this.storeAddress1 = storeAddress1;
        this.storeAddress2 = storeAddress2;
        this.storeCity = storeCity;
        this.storeState = storeState;
        this.storeZip = storeZip;
    }

    public int getStoreid() {
        return storeid;
    }

    public void setStoreid(int storeid) {
        this.storeid = storeid;
    }

    public int getChainid() {
        return chainid;
    }

    public void setChainid(int chainid) {
        this.chainid = chainid;
    }

    public String getStoreNumber() {
        return storeNumber;
    }

    public void setStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }

    public String getStoreAddress1() {
        return storeAddress1;
    }

    public void setStoreAddress1(String storeAddress1) {
        this.storeAddress1 = storeAddress1;
    }

    public String getStoreAddress2() {
        return storeAddress2;
    }

    public void setStoreAddress2(String storeAddress2) {
        this.storeAddress2 = storeAddress2;
    }

    public String getStoreCity() {
        return storeCity;
    }

    public void setStoreCity(String storeCity) {
        this.storeCity = storeCity;
    }

    public String getStoreState() {
        return storeState;
    }

    public void setStoreState(String storeState) {
        this.storeState = storeState;
    }

    public String getStoreZip() {
        return storeZip;
    }

    public void setStoreZip(String storeZip) {
        this.storeZip = storeZip;
    }
}
