package com.apexcomputerservice.legotracker.model;

/**
 * Created by Chris on 4/4/2017.
 */

public class Brands {

    int brandid, productid;
    String description;

    public Brands(){

    }

    public Brands(int brandid, String description, int productid){
        this.brandid = brandid;
        this.description = description;
        this.productid = productid;
    }

    public int getBrandid() {
        return brandid;
    }

    public void setBrandid(int brandid) {
        this.brandid = brandid;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //Override toString() so chainName will show in Spinner
    @Override
    public String toString(){
        return this.description;
    }
}
