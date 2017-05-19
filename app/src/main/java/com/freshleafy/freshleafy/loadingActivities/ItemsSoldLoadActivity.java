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

import com.freshleafy.freshleafy.Global;
import com.freshleafy.freshleafy.MainActivity;
import com.freshleafy.freshleafy.NoInternetActivity;
import com.freshleafy.freshleafy.R;
import com.freshleafy.freshleafy.data.ItemsSoldContract.itemsSoldContractEntry;
import com.freshleafy.freshleafy.queryAttribues.ItemsSoldAttributes;
import com.freshleafy.freshleafy.queryUtils.ItemsSoldQueryUtils;

import java.util.List;

public class ItemsSoldLoadActivity extends AppCompatActivity {

    private static final String URL_ITEMS_SOLD_QUERY = "http://ec2-34-209-89-98.us-west-2.compute.amazonaws.com/itemsoldlist";

    //Create a Context variable to be accessed from itemssold query utils
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_sold_load);

        //KICK OFF THE ASYNC TASK
        ItemsSoldAsyncTask itemsSoldAsync = new ItemsSoldAsyncTask();
        itemsSoldAsync.execute(URL_ITEMS_SOLD_QUERY);

        //Initialize the ItemsSodQueryUtils object
        mContext = this.getApplicationContext();

    }

    //CREATE AN ASYNC TASK METHOD TO CALL THE NETWORK IN THE BACKGROUND
    private class ItemsSoldAsyncTask extends AsyncTask<String, Void, List<ItemsSoldAttributes>> {

        @Override
        protected List<ItemsSoldAttributes> doInBackground(String... params) {


            //Create the global object from Application
            Global globalObject = (Global) getApplication();

            //Get the customer login
            String customerName = globalObject.getCustomerName();
            //Get the customer password
            String customerPassword = globalObject.getCustomerPassword();


            //List of items sold attribute
            List<ItemsSoldAttributes> itemsSoldAttributes = null;
            //check for the internet connection
            // Get a reference to the ConnectivityManager to check state of network connectivity
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);

            // Get details on the currently active default data network
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            //This part takes care of the scrollview and shows no internet connection message when there is no internet connection
            if(networkInfo != null && networkInfo.isConnected()) {

                //Write the logic to handle the responses

                itemsSoldAttributes = ItemsSoldQueryUtils.fetchItemsSoldData(params[0],customerName,customerPassword,mContext);
//                Log.v("ItemsSoldLoadActivity","list of items "+itemsSoldAttributes);
                if(itemsSoldAttributes != null){
                    Intent newIn = new Intent(ItemsSoldLoadActivity.this, MainActivity.class);
                    startActivity(newIn);
                }

            }else {

                Intent intent = new Intent(ItemsSoldLoadActivity.this, NoInternetActivity.class);
                startActivity(intent);
                finish();
            }


            return itemsSoldAttributes;
        }
        protected void onPostExecute(List<ItemsSoldAttributes> list) {

            if(list != null){

                ContentValues contentValues = new ContentValues();
                int length = list.size();
                for (int i = 0; i < length; i++) {

                    //Get the features from the data array
                    String eng_name = list.get(i).getEngName();
                    String hin_name = list.get(i).getHin_name();
                    String image64 = list.get(i).getImage64();
                    String measure = list.get(i).getMeasure();
                    int id = list.get(i).getID();
                    int popularity = list.get(i).getPopularity();
                    int item_category = list.get(i).getItemCategory();
                    int selling_price = list.get(i).getSellingPrice();
                    int increment = list.get(i).getIncrement();


                    //import com.freshleafy.freshleafy.data.ItemsSoldContract.itemsSoldContractEntry;
                    //Put the data into content values
                    contentValues.put(itemsSoldContractEntry.COLUMN_ITEM_NAME_ENGLISH, eng_name);
                    contentValues.put(itemsSoldContractEntry.COLUMN_ITEM_NAME_HINDI, hin_name);
                    contentValues.put(itemsSoldContractEntry.COLUMN_IMAGE, image64);
                    contentValues.put(itemsSoldContractEntry.COLUMN_MEASURE, measure);
                    contentValues.put(itemsSoldContractEntry.COLUMN_ITEM_ID, id);
                    contentValues.put(itemsSoldContractEntry.COLUMN_POPULARITY, popularity);
                    contentValues.put(itemsSoldContractEntry.COLUMN_CATEGORY, item_category);
                    contentValues.put(itemsSoldContractEntry.COLUMN_UNIT_PRICE, selling_price);
                    contentValues.put(itemsSoldContractEntry.COLUMN_INCREMENT,increment);

                    //Insert the data into the database
                    Uri newUri = getContentResolver().insert(itemsSoldContractEntry.CONTENT_URI_ITEMS_SOLD, contentValues);
//                    Log.v("Let see ", " now what is this " + newUri);
//
//                    Intent mainActivityIntent = new Intent(ItemsSoldLoadActivity.this, MainActivity.class);
//                    startActivity(mainActivityIntent);



                }

            }
        }
    }
}
