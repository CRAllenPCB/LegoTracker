package com.apexcomputerservice.legotracker.model;

/**
 * Created by chris on 3/20/17.
 */

public class Skin {

    int skinid;
    String skinDescription;

    //Empty constructor
    public Skin(){

    }

    //Constructor
    public Skin(int skinid, String skinDescription){
        this.skinid = skinid;
        this.skinDescription = skinDescription;
    }

    public int getSkinid() {
        return skinid;
    }

    public void setSkinid(int skinid) {
        this.skinid = skinid;
    }

    public String getSkinDescription() {
        return skinDescription;
    }

    public void setSkinDescription(String skinDescription) {
        this.skinDescription = skinDescription;
    }
}
