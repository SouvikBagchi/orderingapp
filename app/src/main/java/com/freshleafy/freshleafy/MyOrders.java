package com.freshleafy.freshleafy;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.freshleafy.freshleafy.data.MyOrdersContract.myOrdersContractEntry;

public class MyOrders extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor>{

    private static final int ORDER_LOADER = 4;

    private MyOrdersCursorAdapter mMyOrdersCursorAdapter;

    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        //This sets up the back button in the menu item
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get the listView which we want to inflate
        ListView listView = (ListView) findViewById(R.id.listview_for_order_list);

        //Initialize the cursor adapter
        mMyOrdersCursorAdapter = new MyOrdersCursorAdapter(this,null);
        //Set it up on the list view
        listView.setAdapter(mMyOrdersCursorAdapter);

        //Kick off the loader manager
        getLoaderManager().initLoader(ORDER_LOADER,null,this);

        //THE NEXT BLOCK HOOKS UP CURSOR TO GET THE ID AND START INTENT
        //Initialize cursor
//        final Cursor cursor = mMyOrdersCursorAdapter.getCursor();
//        Log.v("Orders Cursor",cursor.getCount()+"");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Move the cursor to position of click
                mCursor.moveToPosition(position);

                //Get the index of the BE_ID
                int index = mCursor.getColumnIndex(myOrdersContractEntry.COLUMN_BE_ID);

                //Get the integer value of the BE_ID from the cursor
                int be_id = mCursor.getInt(index);

                //Create intent to go to order details
                Intent intent = new Intent(MyOrders.this,OrderDetailsActivity.class);
                //Put extra id in the intent
                intent.putExtra("id",toString().valueOf(be_id));
                //Start activity and finish this one
                startActivity(intent);
                finish();


            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //For up button
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(MyOrders.this,MyAccountActivity.class);
                startActivity(intent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MyOrders.this,MyAccountActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public android.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {


        //Create the projection
        String [] projection = {
                myOrdersContractEntry._ID,

                myOrdersContractEntry.COLUMN_BE_ID,
                myOrdersContractEntry.COLUMN_ORDER_DELIVERY_TIME,
                myOrdersContractEntry.COLUMN_ORDER_DELIVERY_DATE,
                myOrdersContractEntry.COLUMN_ORDER_PLACED_DATE,
                myOrdersContractEntry.COLUMN_ITEM_QUANTITY,
                myOrdersContractEntry.COLUMN_UNIT_PRICE,
                "sum("+myOrdersContractEntry.COLUMN_UNIT_PRICE+"*"+
                        myOrdersContractEntry.COLUMN_ITEM_QUANTITY+")" +" AS TOTAL"
        };

        String selection = "BE_ID = BE_ID GROUP BY BE_ID ORDER BY BE_ID DESC";
        String [] selectionArgs = {};

        //No Selection is required since no items will be loaded if the quantity is 0
        return new CursorLoader(this,myOrdersContractEntry.CONTENT_URI_MY_ORDER,projection,selection,null,null);
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor data) {

        if(data != null){

            //Get the no data and visibility to null
            View noOrderView = findViewById(R.id.no_order_empty_view);
            noOrderView.setVisibility(View.GONE);

            mMyOrdersCursorAdapter.swapCursor(data);

            //Set the cursor instance variable to private so that we get the data
            //which will be used to get the id for intent pass
            mCursor = data;

        }

    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {

        mMyOrdersCursorAdapter.swapCursor(null);

    }

}
