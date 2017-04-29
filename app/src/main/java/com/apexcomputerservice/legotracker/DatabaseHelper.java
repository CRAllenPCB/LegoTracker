package com.apexcomputerservice.legotracker;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.apexcomputerservice.legotracker.model.Brands;
import com.apexcomputerservice.legotracker.model.Chain;
import com.apexcomputerservice.legotracker.model.Displays;
import com.apexcomputerservice.legotracker.model.Inventory;
import com.apexcomputerservice.legotracker.model.LegoTypes;
import com.apexcomputerservice.legotracker.model.Product;
import com.apexcomputerservice.legotracker.model.Skin;
import com.apexcomputerservice.legotracker.model.Store;


import java.util.ArrayList;
import java.util.List;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;
import static java.sql.Types.NULL;

/**
 * Created by chris on 2/3/17.
 */



public class DatabaseHelper extends SQLiteOpenHelper {



    //All Static variables
    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "LegoTracker";

    //Chain Table Name
    private static final String TABLE_CHAIN = "chain";

    //Chain Table Columns names
    private static final String CHAINID = "chainid";
    private static final String CHAIN_NAME = "chainName";

    //Displays Table Name
    private static final String TABLE_DISPLAYS = "displays";
    private static final String PLACEMENTID= "placementId";
    private static final String PLACEMENT_DATE = "placementDate";
    private static final String UP = "placementUp";
    private static final String TYPE = "typeid";
    private static final String INT_QTY = "initalQty";
    private static final String CUR_QTY = "currentQty";
    private static final String RESOLD = "resold";
    private static final String NOTES = "notes";
    private static final String REMOVAL_DATE = "removalDate";
    private static final String WEEKS_UP = "weeksUp";

    //LegoTypes Table Names
    private static final String TABLE_LEGOTYPES = "legotypes";
    private static final String TYPEID = "typeid";
    private static final String TYPE_DESCRIPTION = "typeDescription";

    //Skin Table Names
    private static final String TABLE_SKINTYPES = "skintypes";
    private static final String SKINID = "skinid";
    private static final String SKIN_DESCRIPTION = "skinDescription";

    //Store Table Names
    private static final String TABLE_STORE = "stores";
    private static final String STOREID = "storeid";
    private static final String STORE_NUM = "storeNumber";
    private static final String STORE_ADD1 = "storeAddress1";
    private static final String STORE_ADD2 =  "storeAddres2";
    private static final String STORE_CITY = "storeCity";
    private static final String STORE_STATE ="storeState";
    private static final String STORE_ZIP = "storeZip";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";

    //Inventory Table Names
    private static final String TABLE_INVENTORY = "inventory";
    private static final String INV_ID = "inventoryid";
    private static final String INV_COUNT = "count";
    private static final String INV_DESCRIPTION = "description";

    //Prodcut Table Names
    private static final  String TABLE_PRODUCT = "product";
    private static final String PRODUCT_ID = "productid";
    private static final String PRODUCT_DECRIPTION = "description";

    //Brand Table Names
    private static final String TABLE_BRAND = "brand";
    private static final String BRAND_ID = "brandid";
    private static final String BRAND_DESCRIPTION = "description";



    //TABLE CREATE STATEMENTS
    private static final String CREATE_TABLE_CHAINS = "CREATE TABLE " +
            TABLE_CHAIN + "(" +
            CHAINID + " INTEGER PRIMARY KEY, " +
            CHAIN_NAME + " TEXT" + ")";
    private static final String CREATE_TABLE_DISPLAYS = "CREATE TABLE " +
            TABLE_DISPLAYS + "(" +
            PLACEMENTID + " INTEGER PRIMARY KEY, " +
            PLACEMENT_DATE + " INTEGER, " +
            UP + " INTEGER, " +
            CHAINID + " INTEGER, " +
            STOREID + " INTEGER, " +
            TYPE + " INTEGER, " +
            INT_QTY + " INTEGER, " +
            CUR_QTY + " INTEGER, " +
            RESOLD + " INTEGER, " +
            SKINID + " INTEGER, " +
            NOTES + " TEXT, " +
            REMOVAL_DATE + " INTEGER, " +
            PRODUCT_ID + " INTEGER, " +
            BRAND_ID + " INTEGER, " +
            WEEKS_UP + " INTEGER " + ")";
    private static final String CREATE_TABLE_LEGOTYPES = "CREATE TABLE " +
            TABLE_LEGOTYPES + "(" +
            TYPEID + " INTEGER PRIMARY KEY, "+
            TYPE_DESCRIPTION + " TEXT, " +
            PRODUCT_ID + " INTEGER" +  ")";
    private static final String CREATE_TABLE_SKINTYPES = "CREATE TABLE " +
            TABLE_SKINTYPES + "(" +
            SKINID + " INTEGER PRIMARY KEY, " +
            SKIN_DESCRIPTION + " TEXT, " +
            PRODUCT_ID + " INTEGER, " +
            BRAND_ID + " INTEGER" + ")";
    private static final String CREATE_TABLE_STORE = "CREATE TABLE " +
            TABLE_STORE + "(" +
            STOREID + " INTEGER PRIMARY KEY, " +
            CHAINID + " INTEGER, " +
            STORE_NUM + " TEXT, " +
            STORE_ADD1 + " TEXT, " +
            STORE_ADD2 + " TEXT, " +
            STORE_CITY + " TEXT, " +
            STORE_STATE + " TEXT, " +
            STORE_ZIP + " TEXT, " +
            LATITUDE + " TEXT, " +
            LONGITUDE + " TEXT" + ")";
    private static final String CREATE_INVENTORY_TABLE = "CREATE TABLE " +
            TABLE_INVENTORY + "(" +
            INV_ID + " INTEGER PRIMARY KEY, " +
            INV_DESCRIPTION + " TEXT, " +
            INV_COUNT + " INTEGER" + ")";

    private static final String CREATE_PRODUCT_TABLE = "CREATE TABLE " +
            TABLE_PRODUCT + "(" +
            PRODUCT_ID + " INTEGER PRIMARY KEY, " +
            PRODUCT_DECRIPTION +" TEXT" + ")";

    private static final String CREATE_BRAND_TABLE = "CREATE TABLE " +
            TABLE_BRAND + "(" +
            BRAND_ID + " INTEGER PRIMARY KEY, " +
            BRAND_DESCRIPTION + " TEXT, " +
            PRODUCT_ID + " INTEGER" + ")";

    private final Context fContext;


    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        fContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create required tables
        db.execSQL(CREATE_TABLE_CHAINS);
        db.execSQL(CREATE_TABLE_DISPLAYS);
        db.execSQL(CREATE_TABLE_LEGOTYPES);
        db.execSQL(CREATE_TABLE_STORE);
        db.execSQL(CREATE_INVENTORY_TABLE);
        db.execSQL(CREATE_TABLE_SKINTYPES);
        db.execSQL(CREATE_BRAND_TABLE);
        db.execSQL(CREATE_PRODUCT_TABLE);


        populateLegoTypes(db);
        populateSkin(db);
        populateProduct(db);
        populateBrands(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISPLAYS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEGOTYPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVENTORY);

        //Create new tables
        onCreate(db);
    }



    public boolean isTableEmpty(String tableName){
        SQLiteDatabase db = this.getReadableDatabase();
        String count = "SELECT count(*) FROM " + tableName;
        Cursor c = db.rawQuery(count,null);
        c.moveToFirst();
        int icount = c.getInt(0);
        c.close();
        if (icount>0){
            return false;}
        else {return true;}
    }

    //TODO resetStore should be removed prior to release

    /**
     *
     * Reset store database
     *
     * THIS SHOULD BE REMOVED
     */

    public void resetStore(){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISPLAYS);
        db.execSQL(CREATE_TABLE_DISPLAYS);



    }

    // ***********************Add to tables**************************

    public void addChain(Chain chain){
        // Add new chain
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CHAIN_NAME, chain.getChainName());

        //Insert rows
        db.insert(TABLE_CHAIN, null, values);

    }

    public void addDisplay(Displays displays){
        //Add new display
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(PLACEMENT_DATE, String.valueOf(displays.getPlacementDate()));
        values.put(UP, displays.getPlacementUp());
        values.put(CHAINID, displays.getChainid());
        values.put(STOREID, displays.getStoreid());
        values.put(TYPE , displays.getTypeid());
        values.put(INT_QTY, displays.getInitalQty());
        values.put(CUR_QTY , displays.getCurrentQty());
        values.put(RESOLD, displays.getResold());
        values.put(SKINID, displays.getSkinid());
        values.put(NOTES, displays.getNotes());
        values.put(PRODUCT_ID, displays.getProductId());
        values.put(BRAND_ID, displays.getBrandId());


        //Insert rows
        db.insert(TABLE_DISPLAYS, null, values);

    }

    public void populateLegoTypes(SQLiteDatabase db){
       // SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        Resources res = fContext.getResources();
        String[] legoDescription = res.getStringArray(R.array.lego_description);
        String[] legoProductId = res.getStringArray(R.array.lego_product_id);
        int length = legoDescription.length;
        for(int i = 0; i < length; i++){
            values.put(TYPE_DESCRIPTION, legoDescription[i]);
            values.put(PRODUCT_ID, legoProductId[i]);
            db.insert(TABLE_LEGOTYPES, null, values);
        }



    }

    public void populateSkin(SQLiteDatabase db){
       // SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        Resources res = fContext.getResources();
        String[] skinDescrition = res.getStringArray(R.array.skin_description);
        String[] skinProductId = res.getStringArray(R.array.skin_product_id);
        String[] skinBrandId = res.getStringArray(R.array.skin_brand_id);
        int length = skinDescrition.length;
        for(int i = 0; i < length; i++){
            values.put(SKIN_DESCRIPTION, skinDescrition[i]);
            values.put(PRODUCT_ID, skinProductId[i]);
            values.put(BRAND_ID,skinBrandId[i]);
            db.insert(TABLE_SKINTYPES, null, values);
        }



    }

    public void populateProduct(SQLiteDatabase db){
      // SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        Resources res = fContext.getResources();
        String[] productDescription = res.getStringArray(R.array.product_description);
        int length = productDescription.length;
        for(int i = 0; i < length; i++){
            values.put(PRODUCT_DECRIPTION, productDescription[i]);
            db.insert(TABLE_PRODUCT, null, values);
        }


    }

    public void populateBrands(SQLiteDatabase db){
       // SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        Resources res = fContext.getResources();
        String[] brandDescription = res.getStringArray(R.array.brand_description);
        String[] brandProductId = res.getStringArray(R.array.brand_product_id);
        int length = brandDescription.length;
        for(int i =0; i < length; i++){
            values.put(BRAND_DESCRIPTION, brandDescription[i]);
            values.put(PRODUCT_ID,brandProductId[i]);
            db.insert(TABLE_BRAND, null, values);
        }


    }

    public void addStore(Store store){
        //Add new store
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CHAINID, store.getChainid());
        values.put(STORE_NUM, store.getStoreNumber());
        values.put(STORE_ADD1, store.getStoreAddress1());
        values.put(STORE_ADD2, store.getStoreAddress2());
        values.put(STORE_CITY, store.getStoreCity());
        values.put(STORE_STATE, store.getStoreState());
        values.put(STORE_ZIP, store.getStoreZip());
        values.put(LATITUDE,store.getLat());
        values.put(LONGITUDE, store.getLng());

        db.insert(TABLE_STORE, null, values);
        db.close();
    }

    public void addInventory(Inventory inv){
        //Add new store
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(INV_DESCRIPTION, inv.getDescription());
        values.put(INV_COUNT, inv.getCount());

        db.insert(TABLE_INVENTORY, null, values);
        db.close();
    }

    //******************************Retrieve data from tables********************

    public Displays getSingleDisplay(long placementid){
        //Get single display
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT *" +
          //          TABLE_CHAIN + "." + CHAIN_NAME + " AS chain_name" +
          //          TABLE_STORE + "." + STORE_NUM + " AS store_number" +
          //          TABLE_LEGOTYPES + "." + TYPE_DESCRIPTION + " AS lego_description" +
          //          TABLE_SKINTYPES + "." + SKIN_DESCRIPTION + " AS skin_description" +
                " FROM " + TABLE_DISPLAYS +
                " WHERE " + PLACEMENTID + " = " + placementid;
        //            " JOIN " + TABLE_CHAIN + " ON " +
        //                TABLE_DISPLAYS + "." + CHAINID + " = " +
        //                TABLE_CHAIN + "." + CHAINID +
        //            " JOIN " + TABLE_STORE + " ON " +
        //                TABLE_DISPLAYS + "." + STOREID + " = " +
        //                TABLE_STORE + "." + STOREID +
        //            " JOIN " + TABLE_LEGOTYPES + " ON " +
        //                TABLE_DISPLAYS + "." + TYPEID + " = " +
        //                TABLE_LEGOTYPES + "." + TYPEID +
        //            " JOIN " + TABLE_SKINTYPES + " ON " +
        //                TABLE_DISPLAYS + "." + SKINID + " = " +
        //                TABLE_SKINTYPES + "." + SKINID;


        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Displays d = new Displays();
        d.setPlacementId(c.getInt(c.getColumnIndex(PLACEMENTID)));
        d.setPlacementDate(c.getLong(c.getColumnIndex(PLACEMENT_DATE)));
        d.setPlacementUp(c.getInt(c.getColumnIndex(UP)));
        d.setTypeid(c.getInt(c.getColumnIndex(TYPE)));
        d.setInitalQty(c.getInt(c.getColumnIndex(INT_QTY)));
        d.setCurrentQty(c.getInt(c.getColumnIndex(CUR_QTY)));
        d.setResold(c.getInt(c.getColumnIndex(RESOLD)));
        d.setChainid(c.getInt(c.getColumnIndex(CHAINID)));
        d.setStoreid(c.getInt(c.getColumnIndex(STOREID)));
        d.setSkinid(c.getInt(c.getColumnIndex(SKINID)));
        d.setNotes(c.getString(c.getColumnIndex(NOTES)));
        d.setRemovalDate(c.getLong(c.getColumnIndex(REMOVAL_DATE)));
        d.setProductId(c.getInt(c.getColumnIndex(PRODUCT_ID)));
        d.setWeeksUp(c.getInt(c.getColumnIndex(WEEKS_UP)));




        if (c !=null && !c.isClosed()) {
            c.close();
        }

        return d;



    }

    public List<Displays> getDisplaysByChainId(int chainid) {
        List<Displays> displays = new ArrayList<Displays>();
        String selectQuery = "SELECT * FROM " + TABLE_DISPLAYS +
                " WHERE " +CHAINID + " = " + chainid;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //Loop through all rows and add to list
        if (c.moveToFirst()) {
            do {
                Displays d = new Displays();
                d.setPlacementId(c.getInt(c.getColumnIndex(PLACEMENTID)));
                d.setPlacementDate(c.getLong(c.getColumnIndex(PLACEMENT_DATE)));
                d.setPlacementUp(c.getInt(c.getColumnIndex(UP)));
                d.setTypeid(c.getInt(c.getColumnIndex(TYPE)));
                d.setInitalQty(c.getInt(c.getColumnIndex(INT_QTY)));
                d.setCurrentQty(c.getInt(c.getColumnIndex(CUR_QTY)));
                d.setResold(c.getInt(c.getColumnIndex(RESOLD)));
                d.setChainid(c.getInt(c.getColumnIndex(CHAINID)));
                d.setStoreid(c.getInt(c.getColumnIndex(STOREID)));
                d.setBrandId(c.getInt(c.getColumnIndex(BRAND_ID)));
                //Add to list
                displays.add(d);
            } while (c.moveToNext());
        }
        if (c !=null && !c.isClosed()) {
            c.close();
        }
        return displays;
    }

    public List<Displays> getDisplaysByStoreId(int storeid) {
        List<Displays> displays = new ArrayList<Displays>();
        String selectQuery = "SELECT * FROM " + TABLE_DISPLAYS +
                " WHERE " +STOREID + " = " + storeid;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //Loop through all rows and add to list
        if (c.moveToFirst()) {
            do {
                Displays d = new Displays();
                d.setPlacementId(c.getInt(c.getColumnIndex(PLACEMENTID)));
                d.setPlacementDate(c.getLong(c.getColumnIndex(PLACEMENT_DATE)));
                d.setPlacementUp(c.getInt(c.getColumnIndex(UP)));
                d.setTypeid(c.getInt(c.getColumnIndex(TYPE)));
                d.setInitalQty(c.getInt(c.getColumnIndex(INT_QTY)));
                d.setCurrentQty(c.getInt(c.getColumnIndex(CUR_QTY)));
                d.setResold(c.getInt(c.getColumnIndex(RESOLD)));
                d.setChainid(c.getInt(c.getColumnIndex(CHAINID)));
                d.setStoreid(c.getInt(c.getColumnIndex(STOREID)));
                d.setBrandId(c.getInt(c.getColumnIndex(BRAND_ID)));
                //Add to list
                displays.add(d);
            } while (c.moveToNext());
        }
        if (c !=null && !c.isClosed()) {
            c.close();
        }
        return displays;
    }

    public List<Displays> getAllDisplays() {
        List<Displays> displays = new ArrayList<Displays>();
        String selectQuery = "SELECT * FROM " + TABLE_DISPLAYS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //Loop through all rows and add to list
        if (c.moveToFirst()) {
            do {
                Displays d = new Displays();
                d.setPlacementId(c.getInt(c.getColumnIndex(PLACEMENTID)));
                d.setPlacementDate(c.getLong(c.getColumnIndex(PLACEMENT_DATE)));
                d.setPlacementUp(c.getInt(c.getColumnIndex(UP)));
                d.setTypeid(c.getInt(c.getColumnIndex(TYPE)));
                d.setInitalQty(c.getInt(c.getColumnIndex(INT_QTY)));
                d.setCurrentQty(c.getInt(c.getColumnIndex(CUR_QTY)));
                d.setResold(c.getInt(c.getColumnIndex(RESOLD)));
                d.setChainid(c.getInt(c.getColumnIndex(CHAINID)));
                d.setStoreid(c.getInt(c.getColumnIndex(STOREID)));
                d.setBrandId(c.getInt(c.getColumnIndex(BRAND_ID)));
                //Add to list
                displays.add(d);
            } while (c.moveToNext());
        }
        if (c !=null && !c.isClosed()) {
            c.close();
        }
        return displays;
    }

    public List<Displays> getActiveDisplays() {
        List<Displays> displays = new ArrayList<Displays>();
        String selectQuery = "SELECT * FROM " + TABLE_DISPLAYS + " WHERE " + UP + "= 1 ";


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //Loop through all rows and add to list
        if (c.moveToFirst()) {
            do {
                Displays d = new Displays();
                d.setPlacementId(c.getInt(c.getColumnIndex(PLACEMENTID)));
                d.setPlacementDate(c.getLong(c.getColumnIndex(PLACEMENT_DATE)));
                d.setPlacementUp(c.getInt(c.getColumnIndex(UP)));
                d.setTypeid(c.getInt(c.getColumnIndex(TYPE)));
                d.setInitalQty(c.getInt(c.getColumnIndex(INT_QTY)));
                d.setCurrentQty(c.getInt(c.getColumnIndex(CUR_QTY)));
                d.setResold(c.getInt(c.getColumnIndex(RESOLD)));

                //Add to list
                displays.add(d);
            } while (c.moveToNext());

        }
        if (c !=null && !c.isClosed()) {
            c.close();
        }

        return displays;
    }

    public List<Chain> getChains() {
        List<Chain> chains = new ArrayList<Chain>();
        String selectQuery = "SELECT * FROM " + TABLE_CHAIN;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //Loop through all rows and add to list
        if (c.moveToFirst()) {
            do {
                Chain ch = new Chain();
                ch.setChainid(c.getInt(c.getColumnIndex(CHAINID)));
                ch.setChainName(c.getString(c.getColumnIndex(CHAIN_NAME)));

                //Add to list
                chains.add(ch);
            } while (c.moveToNext());

        }

        if (c !=null && !c.isClosed()) {
            c.close();
        }

        return chains;
    }

    public List<Product> getProducts(){
        List<Product> products = new ArrayList<Product>();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //Loop through rows
        if (c.moveToFirst()){
            do {
                Product p = new Product();
                p.setProductid(c.getInt(c.getColumnIndex(PRODUCT_ID)));
                p.setProductDescription(c.getString(c.getColumnIndex(PRODUCT_DECRIPTION)));
                products.add(p);
            } while (c.moveToNext());
        }
        if (c != null && !c.isClosed()){
            c.close();
        }
        return products;
    }

    public List<Brands> getBrands(int productID){
        List<Brands> brands = new ArrayList<Brands>();
        String selectQuery = "SELECT * FROM " + TABLE_BRAND +
                " WHERE " +
                TABLE_BRAND + "." + PRODUCT_ID + " = " +
                productID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()){
            do {
                Brands b = new Brands();
                b.setBrandid(c.getInt(c.getColumnIndex(BRAND_ID)));
                b.setDescription(c.getString(c.getColumnIndex(BRAND_DESCRIPTION)));
                b.setProductid(c.getInt(c.getColumnIndex(PRODUCT_ID)));

                brands.add(b);
            } while (c.moveToNext());
        }
        if (c != null && !c.isClosed()){
            c.close();
        }
        return brands;
    }

    public String getSingleBrand(int brandId){
        String brandName = null;
        String selectQuery = "SELECT * FROM " + TABLE_BRAND +
                " WHERE " + TABLE_BRAND + "." + BRAND_ID + " = " + brandId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.getCount()>0) {
            c.moveToFirst();
            brandName = c.getString(c.getColumnIndex(BRAND_DESCRIPTION));
        }
        c.close();
        return brandName;
    }


    public Chain getSingleChain(long chainid){
        //Get single display
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_CHAIN + " WHERE " + CHAINID + " = " + chainid;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Chain chain = new Chain();
        chain.setChainName(c.getString(c.getColumnIndex(CHAIN_NAME)));
        if (c !=null && !c.isClosed()) {
            c.close();
        }

        return chain;

    }


    public List<LegoTypes> getLegoTypes(int productId) {
        List<LegoTypes> legotypes = new ArrayList<LegoTypes>();
       String selectQuery = "SELECT * FROM " + TABLE_LEGOTYPES +
               " WHERE CASE " +
               " WHEN " + productId + " IN (4, 5) THEN " +
                    PRODUCT_ID + " = " + productId +
               " ELSE " +
                    PRODUCT_ID + " = " + productId +
                    " OR " + PRODUCT_ID + " = 0" +
               " END " ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //Loop through all rows and add to list
        if (c.moveToFirst()) {
            do {
                LegoTypes l = new LegoTypes();
                l.setTypeid(c.getInt(c.getColumnIndex(TYPEID)));
                l.setTypeDescription(c.getString(c.getColumnIndex(TYPE_DESCRIPTION)));

                //Add to list
                legotypes.add(l);
            } while (c.moveToNext());

        }
        if (c !=null && !c.isClosed()) {
            c.close();
        }
        return legotypes;
    }

    public String getSingleLegoType(int legoId){
        String legoDescription = null;
        String selectQuery = "SELECT * FROM " + TABLE_LEGOTYPES +
                " WHERE " + TABLE_LEGOTYPES + "." + TYPEID + " = " + legoId;

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.getCount() > 0) {
            c.moveToFirst();
            legoDescription = c.getString(c.getColumnIndex(TYPE_DESCRIPTION));
        }

        c.close();
        return legoDescription;
    }

    public List<Skin> getSkins(int legoId, int productId, int brandId){
        List<Skin> skintypes = new ArrayList<Skin>();

        String selectQuery = "SELECT * FROM " + TABLE_SKINTYPES +
                // Exclude everything not a lego or duplo (1-2)
                " WHERE CASE " +
                    " WHEN " + legoId + " > 2 THEN " +
                        PRODUCT_ID + " = 0 " +
                //Exclude Pure Leaf from Tea
                    " WHEN " + productId + " = 3 AND " + brandId + " != 3 THEN " +
                        PRODUCT_ID + " = 0 " +
                //Dove brand only gets Dove 60th skin
                    " WHEN " + productId + " = 6 AND " + brandId + " = 13 THEN " +
                        PRODUCT_ID + " IN (0, 6)" +
                //Personal care (other than Dove)
                    " WHEN " + productId + " = 6 AND " + brandId + " != 13 THEN " +
                        BRAND_ID + " IN (0, 99) " +
                //Else everything else matches product ID
                "ELSE " + PRODUCT_ID + " IN (0, " + productId + ")" +
                "END";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //Loop through all rows and add to list
        if(c.moveToFirst()){
            do{
                Skin s = new Skin();
                s.setSkinid(c.getInt(c.getColumnIndex(SKINID)));
                s.setSkinDescription(c.getString(c.getColumnIndex(SKIN_DESCRIPTION)));

                //Add to list
                skintypes.add(s);
            } while (c.moveToNext());
        }
        if (c != null && !c.isClosed()){
            c.close();
        }
        return skintypes;
    }



    public List<Store> getStores() {
        List<Store> stores = new ArrayList<Store>();
        String selectQuery = "SELECT * FROM " + TABLE_STORE +
                ", " + TABLE_CHAIN +
                " WHERE " +
                TABLE_STORE +".CHAINID = " +
                TABLE_CHAIN +".CHAINID";


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //Loop through all rows and add to list
        if (c.moveToFirst()) {
            do {
                Store s = new Store();
                s.setChainid(c.getInt(c.getColumnIndex(CHAINID)));
                s.setStoreid(c.getInt(c.getColumnIndex(STOREID)));
                s.setStoreNumber(c.getString(c.getColumnIndex(STORE_NUM)));
                s.setStoreAddress1(c.getString(c.getColumnIndex(STORE_ADD1)));
                s.setStoreAddress2(c.getString(c.getColumnIndex(STORE_ADD2)));
                s.setStoreCity(c.getString(c.getColumnIndex(STORE_CITY)));
                s.setStoreState(c.getString(c.getColumnIndex(STORE_STATE)));
                s.setStoreZip(c.getString(c.getColumnIndex(STORE_ZIP)));


                //Add to list
                stores.add(s);
            } while (c.moveToNext());

        }

        if (c !=null && !c.isClosed()) {
            c.close();
        }

        return stores;
    }

    public List<Store> getChainStores(int chainid) {
        List<Store> stores = new ArrayList<Store>();
        String selectQuery = "SELECT * FROM " + TABLE_STORE +
                " WHERE " +
                TABLE_STORE +".CHAINID = " +
                chainid;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //Loop through all rows and add to list
        if (c.moveToFirst()) {
            do {
                Store s = new Store();
                s.setChainid(c.getInt(c.getColumnIndex(CHAINID)));
                s.setStoreid(c.getInt(c.getColumnIndex(STOREID)));
                s.setStoreNumber(c.getString(c.getColumnIndex(STORE_NUM)));
                s.setStoreAddress1(c.getString(c.getColumnIndex(STORE_ADD1)));
                s.setStoreAddress2(c.getString(c.getColumnIndex(STORE_ADD2)));
                s.setStoreCity(c.getString(c.getColumnIndex(STORE_CITY)));
                s.setStoreState(c.getString(c.getColumnIndex(STORE_STATE)));
                s.setStoreZip(c.getString(c.getColumnIndex(STORE_ZIP)));

                //Add to list
                stores.add(s);
            } while (c.moveToNext());

        }

        if (c !=null && !c.isClosed()) {
            c.close();
        }

        return stores;
    }

    public String getSingleStoreNumber(int storeId){
        String storeNum = null;
        String selectQuery = "SELECT * FROM " + TABLE_STORE +
                " WHERE " + TABLE_STORE + "." + STOREID + " = " + storeId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.getCount() > 0) {
            c.moveToFirst();
            storeNum = c.getString(c.getColumnIndex(STORE_NUM));
        }
        c.close();
        return storeNum;
    }

    public String getSingleStoreLocation (int storeId) {
        String storeCity = null;
        String storeState = null;
        String storeLocation = null;
        String selectQuery = "SELECT * FROM " + TABLE_STORE +
                " WHERE " + TABLE_STORE + "." + STOREID + " = " + storeId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.getCount() > 0) {
            c.moveToFirst();
            storeCity = c.getString(c.getColumnIndex(STORE_CITY));
            storeState = c.getString(c.getColumnIndex(STORE_STATE));
        }
        c.close();
        storeLocation = storeCity + ", " + storeState;
        return storeLocation;
    }


    public List<Inventory> getInventory() {
        List<Inventory> inv = new ArrayList<Inventory>();
        String selectQuery = "SELECT * FROM " + TABLE_INVENTORY;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //Loop through all rows and add to list
        if (c.moveToFirst()) {
            do {
                Inventory i = new Inventory();
                i.setCount(c.getInt(c.getColumnIndex(INV_COUNT)));
                i.setDescription(c.getString(c.getColumnIndex(INV_DESCRIPTION)));

                //Add to list
                inv.add(i);
            } while (c.moveToNext());

        }

        if (c !=null && !c.isClosed()) {
            c.close();
        }

        return inv;
    }

    //********************UPDATE data in tables*******************************

    public int updateChain(Chain chain){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CHAIN_NAME, chain.getChainName());

        //Update rows
        return db.update(TABLE_CHAIN, values, CHAINID + " = ?", new String[] {String.valueOf(chain.getChainid())});

    }

    public int updateDisplay(Displays display) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PLACEMENT_DATE, display.getPlacementDate());
        values.put(UP, display.getPlacementUp());
        values.put(TYPE, display.getTypeid());
        values.put(INT_QTY, display.getInitalQty());
        values.put(CUR_QTY, display.getCurrentQty());
        values.put(RESOLD, display.getResold());

        //update rows
        return db.update(TABLE_DISPLAYS, values, PLACEMENTID + " = ?", new String[] {String.valueOf(display.getPlacementId())});

    }

    public int updateStore(Store store){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(STORE_NUM, store.getStoreNumber());
        values.put(STORE_ADD1, store.getStoreAddress1());
        values.put(STORE_ADD2, store.getStoreAddress2());
        values.put(STORE_CITY, store.getStoreCity());
        values.put(STORE_STATE, store.getStoreState());
        values.put(STORE_ZIP, store.getStoreZip());
        values.put(CHAINID, store.getChainid());

        //update rows
        return db.update(TABLE_STORE, values, STOREID + " = ?", new String[] {String.valueOf(store.getStoreid())});

    }

    public int updateInventory(Inventory inv){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(INV_COUNT, inv.getCount());

        //update rows
        return db.update(TABLE_INVENTORY, values, INV_ID + " = ?", new String[] {String.valueOf(inv.getInventoryid())});
    }

    //********************** Delete Records ************************

    public void deleteChain(int chainid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CHAIN, CHAINID + " = ?", new String[] {String.valueOf(chainid)});
            Log.v("TAG", "Chain " + chainid + " deleted");
    }

    public void deleteDisplay(int displayid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DISPLAYS, PLACEMENTID + " = ?", new String[] {String.valueOf(displayid)});
    }

    public void deleteDisplayByChain(int chainid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DISPLAYS, CHAINID + " = ?", new String[] {String.valueOf(chainid)});
    }

    public void deleteDisplayByStore(int storeid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DISPLAYS, STOREID + " = ?", new String[] {String.valueOf(storeid)});
    }

    public void deleteStore (int storeid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STORE, STOREID + " = ?", new String[] {String.valueOf(storeid)});
    }

    public void deleteStoreByChain (int chainid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete (TABLE_STORE, CHAINID + " = ?", new String[] {String.valueOf(chainid)});

    }

    //*********** Close Database **********************************

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    //************ Open Database *********************************8

    public DatabaseHelper openReadableDB() throws SQLException{
        SQLiteDatabase db = this.getReadableDatabase();
        return this;
    }

    public DatabaseHelper openWriteableDB() throws SQLException{
        SQLiteDatabase db = this.getWritableDatabase();
        return this;
    }

}
