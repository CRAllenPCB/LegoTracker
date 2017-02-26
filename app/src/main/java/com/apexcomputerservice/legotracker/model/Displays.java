package com.apexcomputerservice.legotracker.model;

import java.sql.Date;

/**
 * Created by chris on 2/3/17.
 */


/**
 * placementUp should be 1 or 0 - no boolean
 * Convert placementDate. See http://stackoverflow.com/questions/24523397/how-get-date-from-datetime-field-in-sqlite-android
 */

public class Displays {
    //private variables
    int placementId;
    long placementDate;
    int placementUp;
    int storeid;
    int typeid;
    int initalQty;
    int currentQty;
    int resold;

    //empty constructor
    public Displays(){

    }

    //constructor
    public Displays(int placementId, long placementDate, int placementUp, int storeid, int typeid, int initalQty, int currentQty){
        this.placementId = placementId;
        this.placementDate = placementDate;
        this.placementUp = placementUp;
        this.storeid =  storeid;
        this.typeid = typeid;
        this.initalQty = initalQty;
        this.currentQty = currentQty;
        this.resold = resold;
    }

    public int getPlacementId() {
        return placementId;
    }

    public void setPlacementId(int placementId) {
        this.placementId = placementId;
    }

    public long getPlacementDate() {
        return placementDate;
    }

    public void setPlacementDate(long placementDate) {
        this.placementDate = placementDate;
    }

    public int getPlacementUp() {
        return placementUp;
    }

    public void setPlacementUp(int placementUp) {
        this.placementUp = placementUp;
    }

    public int getStoreid() {
        return storeid;
    }

    public void setStoreid(int storeid) {
        this.storeid = storeid;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public int getInitalQty() {
        return initalQty;
    }

    public void setInitalQty(int initalQty) {
        this.initalQty = initalQty;
    }

    public int getCurrentQty() {
        return currentQty;
    }

    public void setCurrentQty(int currentQty) {
        this.currentQty = currentQty;
    }

    public int getResold() {
        return resold;
    }

    public void setResold(int resold) {
        this.resold = resold;
    }
}