package com.apexcomputerservice.legotracker.model;

/**
 * Created by chris on 2/3/17.
 */

public class LegoTypes {

    //private variables
    int typeid;
    String typeDescription;

    //empty constructor
    public LegoTypes(){

    }

    //constructor
    public LegoTypes(int typeid, String typeDescription){
        this.typeid = typeid;
        this.typeDescription = typeDescription;
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
}
