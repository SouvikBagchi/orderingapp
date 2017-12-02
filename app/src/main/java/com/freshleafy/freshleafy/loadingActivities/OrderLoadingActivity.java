package com.freshleafy.freshleafy.loadingActivities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.freshleafy.freshleafy.Global;
import com.freshleafy.freshleafy.MyOrders;
import com.freshleafy.freshleafy.NoInternetActivity;
import com.freshleafy.freshleafy.R;
import com.freshleafy.freshleafy.data.MyOrdersContract.myOrdersContractEntry;
import com.freshleafy.freshleafy.queryAttribues.OrderItemsAttributes;
import com.freshleafy.freshleafy.queryUtils.OrderItemsQueryUtils;

import java.util.List;


public class OrderLoadingActivity extends AppCompatActivity {

    private static final String URL_ORDER_ITEM_QUERY = "obsfuscated url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_loading);


        //Kick off the async task
        OrderItemsAsync orderItemsAsync = new OrderItemsAsync();
        orderItemsAsync.execute(URL_ORDER_ITEM_QUERY);


    }

    //Create the Async Class

    private class OrderItemsAsync extends AsyncTask<String,Void,List<OrderItemsAttributes>>{

        @Override
        protected List<OrderItemsAttributes> doInBackground(String... params) {

            //Create the global object from Application
            Global globalObject = (Global) getApplication();

            //Get the customer login
            String customerLogin = globalObject.getCustomerName();
            //Get the customer password
            String customerPassword = globalObject.getCustomerPassword();
            Log.v("cred OrdLoad",customerLogin+":"+customerPassword);

            //Create variable which will hold the result of order set
            List<OrderItemsAttributes> orderItemsAttributes = null;

            //check for the internet connection
            // Get a reference to the ConnectivityManager to check state of network connectivity
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);

            // Get details on the currently active default data network
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            //This part takes care of the scrollview and shows no internet connection message when there is no internet connection
            if(networkInfo != null && networkInfo.isConnected()) {

                //Fetch the result
                orderItemsAttributes = OrderItemsQueryUtils.fetchOrderItems(params[0],customerLogin,customerPassword);

                //Check if null then
                return orderItemsAttributes;


            }else {
                Intent intent = new Intent(OrderLoadingActivity.this, NoInternetActivity.class);
                startActivity(intent);
            }

            return orderItemsAttributes;
        }

        protected void onPostExecute(List<OrderItemsAttributes> list){

            if(list != null){
                int length = list.size();

                //get all the features and put into the table
                for(int i = 0;i <length;i ++){

                    //Get the features
                    int id = list.get(i).getmOrder_ID();
                    String orderDeliveryDate = list.get(i).getmOrder_Delivery_Date();
                    String orderPlacedDate = list.get(i).getmOrder_Placed_Date();
                    String englishName = list.get(i).getmItem_Name_English();
                    String hindiName = list.get(i).getmItem_Name_Hindi();
                    String measure = list.get(i).getmItem_Measure();
                    String deliveryTime = list.get(i).getmDeliveryTime();
                    int quantity = list.get(i).getmItem_Quantity();
                    int unitPrice = list.get(i).getmUnit_Price();

//                    //DELETE LATER
//                    Log.v("OrdLoadAct temp",id+","+orderDeliveryDate+","+orderPlacedDate+englishName+","
//                            +hindiName+measure+","+deliveryTime+","+quantity+","+unitPrice);
//                    //WORKS


                    //Put the data into the content value object
                    ContentValues contentValues =  new ContentValues();


                    contentValues.put(myOrdersContractEntry.COLUMN_BE_ID,id);
                    contentValues.put(myOrdersContractEntry.COLUMN_ORDER_PLACED_DATE,orderPlacedDate);
                    contentValues.put(myOrdersContractEntry.COLUMN_ORDER_DELIVERY_DATE,orderDeliveryDate);
                    contentValues.put(myOrdersContractEntry.COLUMN_ITEM_NAME_ENGLISH,englishName);
                    contentValues.put(myOrdersContractEntry.COLUMN_HINDI_NAME,hindiName);
                    contentValues.put(myOrdersContractEntry.COLUMN_ITEM_QUANTITY,quantity);
                    contentValues.put(myOrdersContractEntry.COLUMN_ITEM_MEASURE,measure);
                    contentValues.put(myOrdersContractEntry.COLUMN_UNIT_PRICE,unitPrice);
                    contentValues.put(myOrdersContractEntry.COLUMN_ORDER_DELIVERY_TIME,deliveryTime);

                    //Insert it into the table
                    Uri uri = getContentResolver().insert(myOrdersContractEntry.CONTENT_URI_MY_ORDER,contentValues);
                    //Log.v("URI",uri+"");


                    //Method to go the next activity or update the screen
                    Intent intent = new Intent(OrderLoadingActivity.this, MyOrders.class);
                    startActivity(intent);

                    //Close the activity once we go to the other activity -- Since activity finishes here no need to take care of
                    //onPostRequest
                    finish();
                }
            }


        }
    }
}
