package com.freshleafy.freshleafy.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.freshleafy.freshleafy.data.AuthContract.authContractEntry;
import com.freshleafy.freshleafy.data.ItemsSoldContract.itemsSoldContractEntry;
import com.freshleafy.freshleafy.data.MyOrdersContract.myOrdersContractEntry;

/**
 * Created by Souvik on 4/7/2017.
 */
public class LeafyProvider extends ContentProvider {

    /** Tag for the log messages */
    public static final String LOG_TAG = LeafyProvider.class.getSimpleName();

    /**

     * Initialize the provider and the database helper object.

     */
    //Create a DBHelper object
    private LeafyDBHelper mLeafyDBHelper;

    //CREATE THE CODE FOR ACCESSING THE URI'S OF ENTIRE TABLE
    //Code for auth table
    private static final int AUTH = 100;
    //Code for items sold table
    private static final int ITEMS_SOLD_TABLE = 200;
    //Code for my orders table
    private static final int MY_ORDERS_TABLE = 300;

    //CREATE THE CODE FOR ACCESSING THE URI'S OF SINGLE ROW
    //Code for auth table
    private static final int AUTH_1 = 101;
    //Code for items sold table
    private static final int ITEMS_SOLD_TABLE_ID = 201;
    //Code for my orders table
    private static final int MY_ORDERS_TABLE_ID = 301;

    // Create a uri matcher object
    private static final UriMatcher sUrimatcher = new UriMatcher(UriMatcher.NO_MATCH);

    //Create a static method for uri matcher
    static {

        //URI matcher for auth table
        sUrimatcher.addURI(AuthContract.CONTENT_AUTHORITY,AuthContract.PATH_AUTH,AUTH);
        //URI matcher for items sold table
        sUrimatcher.addURI(ItemsSoldContract.CONTENT_AUTHORITY,ItemsSoldContract.PATH_ITEMS_SOLD,ITEMS_SOLD_TABLE);
        //URI matcher for the my orders table
        sUrimatcher.addURI(MyOrdersContract.CONTENT_AUTHORITY,MyOrdersContract.PATH_MY_ORDERS,MY_ORDERS_TABLE);
        //Now we add matcher for accessing only a single line of item
        //URI matcher for auth table
        sUrimatcher.addURI(AuthContract.CONTENT_AUTHORITY,AuthContract.PATH_AUTH + "/#",AUTH_1);
        //URI matcher for items sold table
        sUrimatcher.addURI(ItemsSoldContract.CONTENT_AUTHORITY,ItemsSoldContract.PATH_ITEMS_SOLD+ "/#", ITEMS_SOLD_TABLE_ID);
        //URI matcher for the my orders table
        sUrimatcher.addURI(MyOrdersContract.CONTENT_AUTHORITY,MyOrdersContract.PATH_MY_ORDERS+ "/#", MY_ORDERS_TABLE_ID);


    }

    @Override
    public boolean onCreate() {
        mLeafyDBHelper = new LeafyDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        //Get a readable database
        SQLiteDatabase db = mLeafyDBHelper.getReadableDatabase();

        //This cursor will hold the result of the query
        Cursor cursor;

        //Figure out if the URI matcher can match the URI to a specific code
        int match = sUrimatcher.match(uri);
        //Switch allows us the see what the uri is
        switch (match){

            case AUTH :
                cursor = db.query(authContractEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case ITEMS_SOLD_TABLE :
                cursor = db.query(itemsSoldContractEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);

                break;
            case MY_ORDERS_TABLE :
                cursor = db.query(myOrdersContractEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
//                cursor = db.rawQuery("SELECT BE_ID, sum(ITEM_QUANTITY*UNIT_PRICE) AS TOTAL, ORDER_PLACED_DATE," +
//                        "ORDER_DELIVERY_DATE FROM "+ myOrdersContractEntry.TABLE_NAME+ " GROUP_BY BE_ID",null);
                break;
//            case CURRENT_ORDER :
//                cursor = db.query(currentOrderContractEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
//                break;
            case AUTH_1 :
                selection = authContractEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(authContractEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case ITEMS_SOLD_TABLE_ID:
                selection = itemsSoldContractEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(itemsSoldContractEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case MY_ORDERS_TABLE_ID:
                selection = itemsSoldContractEntry._ID +"=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(myOrdersContractEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
//            case CURRENT_ORDER_1 :
//                selection = currentOrderContractEntry._ID + "=?";
//                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
//                cursor = db.query(currentOrderContractEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
//                break;
            default :
                throw new IllegalArgumentException("Cannot query unknown URI "+ uri);


        }

        //set note to cursor loader to see what was changed to loader such that it can update the ui
        cursor.setNotificationUri(getContext().getContentResolver(),uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }



    @Override
    public int update(Uri uri, ContentValues contentValues,String selection,String[] selectionArgs){


        //This cursor will hold the result of the query
        int returnColumn = 0;

        //Figure out if the URI matcher can match the URI to a specific code
        int match = sUrimatcher.match(uri);

        switch (match){

            case ITEMS_SOLD_TABLE:
                updateItemsSoldQuantity(uri,contentValues,selection,selectionArgs);
                break;

            case ITEMS_SOLD_TABLE_ID:
                selection = itemsSoldContractEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                returnColumn  = updateItemsSoldQuantity(uri,contentValues,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Insertion is not supported for : "+uri);

        }



        return returnColumn;
    }

    private int updateItemsSoldQuantity(Uri uri,ContentValues contentValues,String selection,String [] selectionArgs){

        SQLiteDatabase database = mLeafyDBHelper.getWritableDatabase();

        int rowsUpdated = database.update(itemsSoldContractEntry.TABLE_NAME,contentValues,selection,selectionArgs);

        getContext().getContentResolver().notifyChange(uri,null);


        return rowsUpdated;
    }

    @Override
    public int delete(Uri uri,String selection, String[] selectionArgs){

        //Get the writable database
        SQLiteDatabase database = mLeafyDBHelper.getWritableDatabase();
        //Get the uri from the matcher
        final int matchUri = sUrimatcher.match(uri);

        //Return from delete myorders table
        int rowsDeleted = 0;
        switch (matchUri){
            case MY_ORDERS_TABLE :
                //Delete all rows of the my order table
                rowsDeleted = database.delete(myOrdersContractEntry.TABLE_NAME,selection,selectionArgs);

        }

        return rowsDeleted;

    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues){

        final int matchUri = sUrimatcher.match(uri);
        switch (matchUri){

            case AUTH:
                return insertAuth(uri,contentValues);

            case ITEMS_SOLD_TABLE:
                return insertItemsSold(uri,contentValues);

            case MY_ORDERS_TABLE :
                return insertMyOrders(uri,contentValues);

            default:
                throw new IllegalArgumentException("Insertion is not supported for : "+uri);

        }

    }

    private Uri insertAuth(Uri uri,ContentValues contentValues){

        //get a writable database first
        SQLiteDatabase database = mLeafyDBHelper.getWritableDatabase();
        long id = database.insert(authContractEntry.TABLE_NAME,null,contentValues);

        if(id == -1){
            Log.e(LOG_TAG," Failed to insert row for uri : "+uri);
        }

        return ContentUris.withAppendedId(uri,id);
    }

    private Uri insertItemsSold(Uri uri, ContentValues contentValues){

        //get a writable database first
        SQLiteDatabase database = mLeafyDBHelper.getWritableDatabase();
        long id = database.insert(itemsSoldContractEntry.TABLE_NAME,null,contentValues);

        if(id == -1){
            Log.e(LOG_TAG," Failed to insert row for uri : "+uri);
        }
        return ContentUris.withAppendedId(uri,id);
    }

    private Uri insertMyOrders(Uri uri,ContentValues contentValues){

        //get a writable database first
        SQLiteDatabase database = mLeafyDBHelper.getWritableDatabase();
        long id = database.insert(myOrdersContractEntry.TABLE_NAME,null,contentValues);

        if(id == -1){
            Log.e(LOG_TAG," Failed to insert row for uri : "+uri);
        }
        return ContentUris.withAppendedId(uri,id);
    }
}
