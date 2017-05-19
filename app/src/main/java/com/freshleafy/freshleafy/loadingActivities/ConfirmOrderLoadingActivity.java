package com.freshleafy.freshleafy.loadingActivities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.freshleafy.freshleafy.Global;
import com.freshleafy.freshleafy.MainActivity;
import com.freshleafy.freshleafy.NoInternetActivity;
import com.freshleafy.freshleafy.R;
import com.freshleafy.freshleafy.data.ItemsSoldContract.itemsSoldContractEntry;
import com.freshleafy.freshleafy.queryAttribues.PostOrderAttributes;
import com.freshleafy.freshleafy.queryUtils.PostOrderQueryUtils;

import java.util.ArrayList;
import java.util.List;

public class ConfirmOrderLoadingActivity extends AppCompatActivity {

    private static final String POST_ORDER_URL = "http://ec2-34-209-89-98.us-west-2.compute.amazonaws.com/addordertesting";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order_loading);

        //Set the order status to ORDERED
        Global globalObject = (Global) getApplication();
        globalObject.setOrderStatus("ORDERED");

        //Set the order placed screen to gone
        View orderPlacedView = findViewById(R.id.post_posting_order);
        orderPlacedView.setVisibility(View.GONE);

        PostOrderAsync postOrderAsync = new PostOrderAsync();
        postOrderAsync.execute();

        Button mainMenuNavigationButton = (Button) findViewById(R.id.main_menu_navigation_button);
        mainMenuNavigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMenuIntent = new Intent(ConfirmOrderLoadingActivity.this, MainActivity.class);
                startActivity(mainMenuIntent);

                //UPDATE ALL QUANTITY TO 0
                //Set the content values
                ContentValues contentValues = new ContentValues();
                contentValues.put(itemsSoldContractEntry.COLUMN_QUANTITY,0);
                contentValues.put(itemsSoldContractEntry.COLUMN_TOTAL,0);
                //Set it to 0
                int number = getContentResolver().update(itemsSoldContractEntry.CONTENT_URI_ITEMS_SOLD,contentValues,null,null);
                Log.v("Updated", number+"");

                //Close the activity once we go to the other activity -- Since activity finishes here no need to take care of
                //onPostRequest
                finish();

            }
        });


    }

    //Disable going back
    @Override
    public void onBackPressed() {
        //super.onBackPressed();

    }

    private class PostOrderAsync extends AsyncTask<Void,Void,String>{
        @Override
        protected String doInBackground(Void... params) {


            //Get the projection of the columns we want to query
            String[] projection = {itemsSoldContractEntry.COLUMN_ITEM_ID,
                    itemsSoldContractEntry.COLUMN_QUANTITY};

            String selection = "QUANTITY >?";
            String[] selectionArgs = {"0"};

            //Get the cursor
            Cursor cursor = getContentResolver().query(itemsSoldContractEntry.CONTENT_URI_ITEMS_SOLD,projection,
                    selection,selectionArgs,null);
//            cursor.moveToFirst();

            //Create the the list
            List<PostOrderAttributes> postOrderAttributesList = new ArrayList<>();
//            cursor.moveToNext();
            while(cursor.moveToNext()){
                //Create the temp list object
                int columnQuantity = cursor.getColumnIndex(itemsSoldContractEntry.COLUMN_QUANTITY);
                int columnID = cursor.getColumnIndex(itemsSoldContractEntry.COLUMN_ITEM_ID);
                Log.v("quantity column",columnQuantity+"");
                Log.v("id column",columnID+"");
                int id = cursor.getInt(columnID);
                int quantity = cursor.getInt(columnQuantity);
                PostOrderAttributes temp = new PostOrderAttributes(id,quantity);
                postOrderAttributesList.add(temp);
            }



            //Create the global object and get the remaining fields
            Global globalObject =(Global) getApplication();
            String customerLogin = globalObject.getCustomerName();
            String password = globalObject.getCustomerPassword();
            String orderPlacedDate = globalObject.getOrderPlacedDate();
            String deliveryDate = globalObject.getDeliveryDate();
            String deliveryTime = globalObject.getDeliveryTime();
            int totalPrice = globalObject.getTotalSellingPrice();
            String orderStatus = globalObject.getOrderStatus();

            //Declare string to store the return value
            String returnValue = null;


            //Get variables to check internet settings
            //check for the internet connection
            // Get a reference to the ConnectivityManager to check state of network connectivity
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);

            // Get details on the currently active default data network
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            //Logic to check network settings then continue
            //This part takes care of the scrollview and shows no internet connection message when there is no internet connection
            if(networkInfo != null && networkInfo.isConnected()){
                PostOrderQueryUtils postOrderQueryUtils = new PostOrderQueryUtils();
                returnValue = postOrderQueryUtils.writeOrder(POST_ORDER_URL,postOrderAttributesList,customerLogin,password,orderPlacedDate,deliveryDate,deliveryTime,totalPrice,orderStatus);
                return returnValue;


            }else {

                Intent intent = new Intent(ConfirmOrderLoadingActivity.this, NoInternetActivity.class);
                startActivity(intent);

                //Close the activity once we go to the other activity -- Since activity finishes here no need to take care of
                //onPostRequest
                finish();
            }


            return returnValue;
        }

        protected void onPostExecute(String returnValue){

            View view = findViewById(R.id.posting_order_loading);
            view.setVisibility(View.GONE);

            //Set the order placed view visible
            View orderPlacedView = findViewById(R.id.post_posting_order);
            orderPlacedView.setVisibility(View.VISIBLE);



        }


    }
}
