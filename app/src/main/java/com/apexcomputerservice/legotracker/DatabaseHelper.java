package com.apexcomputerservice.legotracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.apexcomputerservice.legotracker.model.Chain;
import com.apexcomputerservice.legotracker.model.Displays;
import com.apexcomputerservice.legotracker.model.Inventory;
import com.apexcomputerservice.legotracker.model.LegoTypes;
import com.apexcomputerservice.legotracker.model.Store;


import java.util.ArrayList;
import java.util.List;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;
import static java.sql.Types.NULL;

/**
 * Created by chris on 2/3/17.
 */



public class DatabaseHelper extends SQLiteOpenHelper {

    String TAG = "Tag";

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

    //LegoTypes Table Names
    private static final String TABLE_LEGOTYPES = "legotypes";
    private static final String TYPEID = "typeid";
    private static final String TYPE_DESCRIPTION = "typeDescription";

    //Store Table Names
    private static final String TABLE_STORE = "stores";
    private static final String STOREID = "storeid";
    private static final String STORE_NUM = "storeNumber";
    private static final String STORE_ADD1 = "storeAddress1";
    private static final String STORE_ADD2 =  "storeAddres2";
    private static final String STORE_CITY = "storeCity";
    private static final String STORE_STATE ="storeState";
    private static final String STORE_ZIP = "storeZip";

    //Inventory Table Names
    private static final String TABLE_INVENTORY = "inventory";
    private static final String INV_ID = "inventoryid";
    private static final String INV_COUNT = "count";
    private static final String INV_DESCRIPTION = "description";


    //TABLE CREATE STATEMENTS
    private static final String CREATE_TABLE_CHAINS = "CREATE TABLE " +
            TABLE_CHAIN + "(" +
            CHAINID + " INTEGER PRIMARY KEY, " +
            CHAIN_NAME + " TEXT" + ")";
    private static final String CREATE_TABLE_DISPLAYS = "CREATE TABLE " +
            TABLE_DISPLAYS + "(" +
            PLACEMENTID + " INTERGER PRIMARY KEY, " +
            PLACEMENT_DATE + " TEXT, " +
            UP + " INTEGER, " +
            CHAINID + " INTEGER, " +
            STOREID + " INTEGER, " +
            TYPE + " INTERGER, " +
            INT_QTY + " INTEGER, " +
            CUR_QTY + " INTEGER " +
            RESOLD + " INTEGER" + ")";
    private static final String CREATE_TABLE_LEGOTYPES = "CREATE TABLE " +
            TABLE_LEGOTYPES + "(" +
            TYPEID + " INTEGER PRIMARY KEY, "+
            TYPE_DESCRIPTION + " TEXT" + ")";
    private static final String CREATE_TABLE_STORE = "CREATE TABLE " +
            TABLE_STORE + "(" +
            STOREID + " INTEGER PRIMARY KEY, " +
            STORE_NUM + " TEXT, " +
            STORE_ADD1 + " TEXT, " +
            STORE_ADD2 + " TEXT, " +
            STORE_CITY + " TEXT, " +
            STORE_STATE + " TEXT, " +
            STORE_ZIP + " TEXT" + ")";
    private static final String CREATE_INVENTORY_TABLE = "CREATE TABLE " +
            TABLE_INVENTORY + "(" +
            INV_ID + " INTEGER PRIMARY KEY," +
            INV_DESCRIPTION + " TEXT " +
            INV_COUNT + " INTEGER" + ")";


    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create required tables
        db.execSQL(CREATE_TABLE_CHAINS);
        db.execSQL(CREATE_TABLE_DISPLAYS);
        db.execSQL(CREATE_TABLE_LEGOTYPES);
        db.execSQL(CREATE_TABLE_STORE);
        db.execSQL(CREATE_INVENTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISPLAYS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEGOTYPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORE);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_INVENTORY);

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

    // ***********************Add to tables**************************

    public void addChain(Chain chain){
        // Add new chain
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CHAIN_NAME, chain.getChainName());

        //Insert rows
        db.insert(TABLE_CHAIN, null, values);
        db.close();
    }

    public void addDisplay(Displays displays){
        //Add new dipsplay
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(PLACEMENT_DATE, String.valueOf(displays.getPlacementDate()));
        values.put(UP, displays.getPlacementUp());
        values.put(TYPE , displays.getTypeid());
        values.put(INT_QTY, displays.getInitalQty());
        values.put(CUR_QTY , displays.getCurrentQty());
        values.put(RESOLD, displays.getResold());

        //Insert rows
        db.insert(TABLE_DISPLAYS, null, values);
        db.close();
    }

    public void addLegoTypes(LegoTypes legoTypes){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TYPE_DESCRIPTION, legoTypes.getTypeDescription());

        db.insert(TABLE_LEGOTYPES, null, values);
        db.close();
    }

    public void addStore(Store store){
        //Add new store
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(STORE_NUM, store.getStoreNumber());
        values.put(STORE_ADD1, store.getStoreAddress1());
        values.put(STORE_ADD2, store.getStoreAddress2());
        values.put(STORE_CITY, store.getStoreCity());
        values.put(STORE_STATE, store.getStoreState());
        values.put(STORE_ZIP, store.getStoreZip());

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

        String selectQuery = "SELECT * FROM " + TABLE_DISPLAYS + " WHERE " + PLACEMENTID + " = " + placementid;

        Log.e(TAG, selectQuery);

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

        if (c !=null && !c.isClosed()) {
            c.close();
        }

        return d;



    }

    public List<Displays> getAllDisplays() {
        List<Displays> displays = new ArrayList<Displays>();
        String selectQuery = "SELECT * FROM " + TABLE_DISPLAYS;

        Log.e(TAG, selectQuery);

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

    public List<Displays> getActiveDisplays() {
        List<Displays> displays = new ArrayList<Displays>();
        String selectQuery = "SELECT * FROM " + TABLE_DISPLAYS + " WHERE " + UP + "= 1 ";

        Log.e(TAG, selectQuery);

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

        Log.e(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //Loop through all rows and add to list
        if (c.moveToFirst()) {
            do {
                Chain ch = new Chain();
                ch.setChainid(c.getInt(c.getColumnIndex(CHAINID)));
                ch.setChainName(c.getColumnName(c.getColumnIndex(CHAIN_NAME)));

                //Add to list
                chains.add(ch);
            } while (c.moveToNext());

        }

        if (c !=null && !c.isClosed()) {
            c.close();
        }

        return chains;
    }

    public List<LegoTypes> getLegoTypes() {
        List<LegoTypes> legotypes = new ArrayList<LegoTypes>();
        String selectQuery = "SELECT * FROM " + TABLE_LEGOTYPES;

        Log.e(TAG, selectQuery);

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

    public List<Store> getStores() {
        List<Store> stores = new ArrayList<Store>();
        String selectQuery = "SELECT * FROM " + TABLE_STORE;

        Log.e(TAG, selectQuery);

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

    public List<Inventory> getInventory() {
        List<Inventory> inv = new ArrayList<Inventory>();
        String selectQuery = "SELECT * FROM " + TABLE_INVENTORY;

        Log.e(TAG, selectQuery);

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
    }

    public void deleteDisplay(int displayid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DISPLAYS, PLACEMENTID + " = ?", new String[] {String.valueOf(displayid)});
    }

    public void deleteStore (int storeid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STORE, STOREID + " = ?", new String[] {String.valueOf(storeid)});
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
