package com.apexcomputerservice.legotracker.model;

/**
 * Created by Chris on 4/4/2017.
 */

public class Product {
    int productid;
    String productDescription;

    public Product(){

    }

    public Product(int productid, String productDescription){
        this.productid = productid;
        this.productDescription = productDescription;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    //Override toString() so chainName will show in Spinner
    @Override
    public String toString(){
        return this.productDescription;
    }
}
