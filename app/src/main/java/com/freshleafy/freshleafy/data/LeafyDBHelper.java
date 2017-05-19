package com.freshleafy.freshleafy.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.freshleafy.freshleafy.data.ItemsSoldContract.itemsSoldContractEntry;
import com.freshleafy.freshleafy.data.AuthContract.authContractEntry;
import com.freshleafy.freshleafy.data.MyOrdersContract.myOrdersContractEntry;
/**
 * Created by Souvik on 4/7/2017.
 */
public class LeafyDBHelper extends SQLiteOpenHelper {

    //NAME THE DATABASE
    private static final String DATABASE_NAME = "freshleafy.db";

    //Create the database version, if you change the schema then increment the version
    private static final int DATABASE_VERSION = 1;

    //CREATE THE CONSTRUCTOR FOR THE CLASS
    public LeafyDBHelper(Context context) {
        super(context, DATABASE_NAME, null/*CURSOR FACTORY*/, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String SQL_CREATE_ITEMS_SOLD_TABLE = "CREATE TABLE " + itemsSoldContractEntry.TABLE_NAME+"("+
                itemsSoldContractEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                itemsSoldContractEntry.COLUMN_ITEM_ID + " INTEGER NOT NULL, "+
                itemsSoldContractEntry.COLUMN_ITEM_NAME_ENGLISH + " TEXT,"+
                itemsSoldContractEntry.COLUMN_MEASURE +" TEXT NOT NULL,"+
                itemsSoldContractEntry.COLUMN_IMAGE +" TEXT,"+
                itemsSoldContractEntry.COLUMN_CATEGORY+" INTEGER,"+
                itemsSoldContractEntry.COLUMN_POPULARITY +" INTEGER ,"+
                itemsSoldContractEntry.COLUMN_ITEM_NAME_HINDI + " TEXT, "+
                itemsSoldContractEntry.COLUMN_UNIT_PRICE +" INTEGER,"+
                itemsSoldContractEntry.COLUMN_QUANTITY + " INTEGER DEFAULT 0," +
                itemsSoldContractEntry.COLUMN_INCREMENT + " INTEGER, "+
                itemsSoldContractEntry.COLUMN_TOTAL + " INTEGER DEFAULT 0)";

        //Create the auth table in the database
        String SQL_CREATE_CUSTOMER_APP_ID_TABLE = "CREATE TABLE "+
                authContractEntry.TABLE_NAME +"("+
                authContractEntry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                authContractEntry.COLUMN_AUTH_ID +" STRING,"+
                authContractEntry.COLUMN_AUTH_TOKEN +" STRING )";

        //Create the MyOrders table
        String SQL_CREATE_MY_ORDERS_TABLE = "CREATE TABLE "+ myOrdersContractEntry.TABLE_NAME+ "("+
                myOrdersContractEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                myOrdersContractEntry.COLUMN_BE_ID +" INTEGER, "+
                myOrdersContractEntry.COLUMN_ORDER_PLACED_DATE +" TEXT, "+
                myOrdersContractEntry.COLUMN_ORDER_DELIVERY_DATE + " TEXT,"+
                myOrdersContractEntry.COLUMN_ITEM_NAME_ENGLISH + " TEXT ,"+
                myOrdersContractEntry.COLUMN_ITEM_QUANTITY + " INTEGER,"+
                myOrdersContractEntry.COLUMN_ITEM_MEASURE +" TEXT,"+
                myOrdersContractEntry.COLUMN_UNIT_PRICE + " INTEGER ,"+
                myOrdersContractEntry.COLUMN_HINDI_NAME + " TEXT,"+
                myOrdersContractEntry.COLUMN_ORDER_DELIVERY_TIME + " TEXT )";

        //Execute all the tables and create it
        //The items sold table
        db.execSQL(SQL_CREATE_ITEMS_SOLD_TABLE);
        //The auth contract table
        db.execSQL(SQL_CREATE_CUSTOMER_APP_ID_TABLE);
        //The my orders table
        db.execSQL(SQL_CREATE_MY_ORDERS_TABLE);
        //The Current order table -- Tentative

    }

//    @Override
//    public void onOpen(SQLiteDatabase db) {
//        super.onOpen(db);
//        String DROP_TABLE = "DROP TABLE "+ itemsSoldContractEntry.TABLE_NAME;
//
//        //Drop the items sold table before creating
//        db.execSQL(DROP_TABLE);
//    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void close(){


    }




}
