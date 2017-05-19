package com.freshleafy.freshleafy;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import com.freshleafy.freshleafy.data.MyOrdersContract.myOrdersContractEntry;
public class OrderDetailsActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor> {

    //Create instance variable to hold the id
    private String mId;
    OrderDetailsCursorAdapter mOrderDetailsCursorAdapter;

    private static final int ORDER_DETAILS_LOADER = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        //Set up the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get the order id from the previous intent
        String id = getIntent().getStringExtra("id");

        //Set the instance variable to id
        mId = id;

        //Get the list view where the cursor loader will set the values
        ListView listView = (ListView) findViewById(R.id.listview_for_order_details);

        mOrderDetailsCursorAdapter = new OrderDetailsCursorAdapter(this,null);
        listView.setAdapter(mOrderDetailsCursorAdapter);
        //Kick off the loader
        getLoaderManager().initLoader(ORDER_DETAILS_LOADER,null,this);


    }

    //On back pressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //Intent to go to my orders
        Intent intent = new Intent(OrderDetailsActivity.this,MyOrders.class);
        //Start the activity and finish this one
        startActivity(intent);
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //To go back to my orders
        Intent intent = new Intent(OrderDetailsActivity.this,MyOrders.class);
        startActivity(intent);
        finish();

        return true;
    }

    @Override
    public android.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String [] projection = {
                myOrdersContractEntry._ID,
                myOrdersContractEntry.COLUMN_BE_ID,
                myOrdersContractEntry.COLUMN_ITEM_NAME_ENGLISH,
                myOrdersContractEntry.COLUMN_HINDI_NAME,
                myOrdersContractEntry.COLUMN_UNIT_PRICE,
                myOrdersContractEntry.COLUMN_ITEM_MEASURE,
                myOrdersContractEntry.COLUMN_ITEM_QUANTITY
        };

        String selection = myOrdersContractEntry.COLUMN_BE_ID+"=?";
        String [] selectionArgs ={mId};

        return new android.content.CursorLoader(this,myOrdersContractEntry.CONTENT_URI_MY_ORDER,projection,selection,selectionArgs,null);
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor data) {

        if(data != null){

            mOrderDetailsCursorAdapter.swapCursor(data);
        }

    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {

        mOrderDetailsCursorAdapter.swapCursor(null);

    }


}
