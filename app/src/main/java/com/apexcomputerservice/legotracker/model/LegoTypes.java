package com.apexcomputerservice.legotracker.model;

/**
 * Created by chris on 2/3/17.
 */

public class LegoTypes {

    //private variables
    int typeid,productid;
    String typeDescription;

    //empty constructor
    public LegoTypes(){

    }

    //constructor
    public LegoTypes(int typeid, String typeDescription, int productid){
        this.typeid = typeid;
        this.typeDescription = typeDescription;
        this.productid = productid;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    //Override toString() so chainName will show in Spinner
    @Override
    public String toString(){
        return this.typeDescription;
    }
}
