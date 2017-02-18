package com.apexcomputerservice.legotracker.model;

/**
 * Created by chris on 2/5/17.
 */

public class Inventory {

    //private variables
    int inventoryid, count;
    String description;

    //empty constructor
    public Inventory(){

    }

    public int getInventoryid() {
        return inventoryid;
    }

    public void setInventoryid(int inventoryid) {
        this.inventoryid = inventoryid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
