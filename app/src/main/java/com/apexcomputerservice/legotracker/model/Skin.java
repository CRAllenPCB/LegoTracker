package com.apexcomputerservice.legotracker.model;

/**
 * Created by chris on 3/20/17.
 */

public class Skin {

    int skinid, brandid, productid;
    String skinDescription;

    //Empty constructor
    public Skin(){

    }

    //Constructor
    public Skin(int skinid, String skinDescription, int brandid, int productid){
        this.skinid = skinid;
        this.skinDescription = skinDescription;
        this.brandid = brandid;
        this.productid = productid;
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
    //Override toString() so chainName will show in Spinner
    @Override
    public String toString(){
        return this.skinDescription;
    }
}
