package com.freshleafy.freshleafy.loadingActivities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.freshleafy.freshleafy.Global;
import com.freshleafy.freshleafy.NoInternetActivity;
import com.freshleafy.freshleafy.PlaceOrderActivity;
import com.freshleafy.freshleafy.R;
import com.freshleafy.freshleafy.queryAttribues.DeliverySlotAttributes;
import com.freshleafy.freshleafy.queryUtils.DeliverySlotQueryUtils;

import java.util.ArrayList;
import java.util.List;


public class DeliverySlotLoadActivity extends AppCompatActivity {

    private static String mDeliverySlotURL = "http://ec2-34-209-89-98.us-west-2.compute.amazonaws.com/deliveryslot";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_slot_load);

        DeliverySlotAsyncTask deliverySlotAsyncTask = new DeliverySlotAsyncTask();
        deliverySlotAsyncTask.execute();
    }

    private class DeliverySlotAsyncTask extends AsyncTask<String,Void,List<DeliverySlotAttributes>>{

        @Override
        protected List<DeliverySlotAttributes> doInBackground(String... params) {
            Global globalObject = (Global)getApplication();
            String customerName = globalObject.getCustomerName();
            String customerPassword = globalObject.getCustomerPassword();
            Log.v("customerName : ",customerName);
            Log.v("customerPassword : ",customerPassword);

            //Create the list to hold the list of delivery times
            List<DeliverySlotAttributes> list = null;

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

                //Fetch the slots and store into the list item
                list = DeliverySlotQueryUtils.fetchDeliverySlots(mDeliverySlotURL,customerName,customerPassword);
                //return the list to be passed onto the on post execute
                return list;


            }else {

                //Go to the no internet activty
                Intent intent = new Intent(DeliverySlotLoadActivity.this, NoInternetActivity.class);
                startActivity(intent);
                //Write to logs
                Log.v("Delivery Slot Load","No internet Connection, redirecting");

                //Close the activity once we go to the other activity -- Since activity finishes here no need to take care of
                //onPostRequest
                finish();
            }


            return list;

        }

        protected void onPostExecute(List<DeliverySlotAttributes> list){


            ArrayList<DeliverySlotAttributes> intentList = new ArrayList<>(list);
            //Create the intent to go to the next activity.
            Intent placeOrderIntent= new Intent(DeliverySlotLoadActivity.this, PlaceOrderActivity.class);
            //Create a Bundle to pass the list object to the next activity
            Bundle bundle = new Bundle();
            //Put the list you want to pass
            bundle.putSerializable("list",intentList);
            placeOrderIntent.putExtras(bundle);
            startActivity(placeOrderIntent);

        }
    }
}
