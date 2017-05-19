package com.freshleafy.freshleafy;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.freshleafy.freshleafy.data.ItemsSoldContract.itemsSoldContractEntry;
import com.freshleafy.freshleafy.loadingActivities.DeliverySlotLoadActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CartActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor>{

    private static final int CART_LOADER = 3;

    CartCursorAdapter mCartCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //This sets up the back button in the menu item
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get the listView which we want to inflate
        ListView listView = (ListView) findViewById(R.id.list_view_cart);

        //Setup the adapter to create a list view, so that it only shows when the list has 0 items
        //Find the empty view and set it up when there is no data

//        listView.setEmptyView(emptyView);

        //Get the other text views
        Button placeOrderTextView = (Button) findViewById(R.id.button_cart_order);
        TextView totalTextView = (TextView) findViewById(R.id.cart_list_total_price);
        TextView totalCartStaticView = (TextView) findViewById(R.id.cart_total_price_static);
        TextView emptyViewCart = (TextView) findViewById(R.id.empty_view_cart_list);



        //Get the total of the TOTAL column - That is the total price customer has to pay to order
        String [] projection = { "sum("+itemsSoldContractEntry.COLUMN_TOTAL +") as 'Total'"};
        Cursor cursor = getContentResolver().query(itemsSoldContractEntry.CONTENT_URI_ITEMS_SOLD,projection,null,null,null);
        cursor.moveToFirst();

        //Declare a int variable to hold the total
        int total=0;
        if(cursor.getString(0)!=null){
            total = Integer.parseInt(cursor.getString(0));
        }

        //Set the total to the global class variable
        Global globalObject = (Global) getApplication();
        globalObject.setTotalSellingPrice(total);
        Log.v("Total price",globalObject.getTotalSellingPrice()+"");

        //Check if the total is 0. Implies that no item has been selected and hence set gone as visibility.
        if(total == 0){

            totalTextView.setVisibility(View.GONE);
            placeOrderTextView.setVisibility(View.GONE);
            totalCartStaticView.setVisibility(View.GONE);

        }else{

            //Set empty view to gone
            emptyViewCart.setVisibility(View.GONE);
            //There is data yet so pass in null till the cursor is loading
            mCartCursorAdapter = new CartCursorAdapter(this,null);
            listView.setAdapter(mCartCursorAdapter);

            //Kick off the loader
            getLoaderManager().initLoader(CART_LOADER,null,this);


            //Set the total text view
            totalTextView.setText("₹ " +cursor.getString(0));


        }


        //Set an onclick listener on placeOrderTextView
        placeOrderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CartActivity.this,DeliverySlotLoadActivity.class);
                startActivity(intent);
                finish();

            }
        });

        //Test
        cursor.close();
        String[] projection2 = {itemsSoldContractEntry.COLUMN_ITEM_ID,
                itemsSoldContractEntry.COLUMN_QUANTITY};

        String selection = "QUANTITY >?";
        String[] selectionArgs = {"0"};

        Cursor cursor2 = getContentResolver().query(itemsSoldContractEntry.CONTENT_URI_ITEMS_SOLD,projection2,
                selection,selectionArgs,null);
        // Log.v("Cursor ka count ",cursor2.getCount()+"");
        cursor2.close();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //Go back to main activity when back is pressed
        Intent intent = new Intent(CartActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public android.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = {
                itemsSoldContractEntry._ID,
                itemsSoldContractEntry.COLUMN_CATEGORY,
                itemsSoldContractEntry.COLUMN_ITEM_NAME_ENGLISH,
                itemsSoldContractEntry.COLUMN_ITEM_NAME_HINDI,
                itemsSoldContractEntry.COLUMN_IMAGE,
                itemsSoldContractEntry.COLUMN_QUANTITY,
                itemsSoldContractEntry.COLUMN_MEASURE,
                itemsSoldContractEntry.COLUMN_UNIT_PRICE,
                itemsSoldContractEntry.COLUMN_ITEM_ID,
                itemsSoldContractEntry.COLUMN_INCREMENT

        };

        String selection = "QUANTITY > ?";
        String[] selectionArgs ={"0"};
        return new CursorLoader(this,itemsSoldContractEntry.CONTENT_URI_ITEMS_SOLD,projection,selection,selectionArgs,null);
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor data) {

        if(data != null){
            mCartCursorAdapter.swapCursor(data);
        }

        data.moveToFirst();
        int s = data.getColumnCount();
        Log.v("Data at 0 ",s+"");



        //THIS BLOCK OF THE CODE INITIALIZES THE CURRENT DATE
        //Define the date format
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        //Get the current Date
        Date currentDate = new Date();
        //Set the time zone
        simpleDate.setTimeZone(TimeZone.getTimeZone("IST"));
        //Get the date in the format
        String finalDate = simpleDate.format(currentDate);
        //print it in the log
        Log.v("Current Date ",finalDate);
        //Initialize the global object
        Global globalObject = (Global) getApplication();
        //Set all the dates
        globalObject.setDeliveryDate(finalDate);
        globalObject.setOrderPlacedDate(finalDate);



    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {

        mCartCursorAdapter.swapCursor(null);

    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;

    }
}
