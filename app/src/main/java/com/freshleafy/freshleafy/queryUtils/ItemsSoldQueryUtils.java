package com.freshleafy.freshleafy.queryUtils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.freshleafy.freshleafy.loadingActivities.FailedLoginCredentialsActivity;
import com.freshleafy.freshleafy.loadingActivities.ServerNotRunningActivity;
import com.freshleafy.freshleafy.queryAttribues.ItemsSoldAttributes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Souvik on 4/16/2017.
 */

public class ItemsSoldQueryUtils{

    private static final String LOG_TAG = "ItemsSoldQueryUtils ";



    /**
     * Create a private constructor because no one should ever create a QueryUtils object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    public ItemsSoldQueryUtils(){}

    //THE FOLLOWING BLOCK OF CODE WILL GET THE DATA FROM THE BE AND FILL THE LIST

    public static List<ItemsSoldAttributes> fetchItemsSoldData(String requestURL,String customerName,String customerPassword,Context context){

        URL url = createUrl(requestURL);

        //Perform a HTTP request and get a url back
        String jsonResponse = null;

        //Create the List<ItemsSoldAttributes> itemsSoldAttributes
        List<ItemsSoldAttributes> itemsSoldAttributes;

        try{

            jsonResponse = makeHTTPRequest(url,customerName,customerPassword,context );
        }catch (IOException e){

            Log.e(LOG_TAG," Exception make HTTP request", e);
        }

        //Extract the relevant features and put it into the ItemsSoldAttributeList
        itemsSoldAttributes = extractItemsSoldFeatures(jsonResponse);

        //Return the list of ItemsSoldAttributes
        return itemsSoldAttributes;


    }

    //This method makes the HTTP request
    public static String makeHTTPRequest(URL url,String customerName,String customerPassword,Context context) throws IOException {

        //Declare a blank a string to append when getting the json response
        String jsonResponse = "";

        //Declare a intent object
        Intent intent = new Intent();

        //Check whether url is null, if yes then return early
        if(url == null){
            return jsonResponse;
        }

        //Get a HttpURLConnection Object
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{

            String credentials = customerName+":"+customerPassword;
            Log.v("Credentials",credentials);

            byte[] encodedByte = Base64.encode(credentials.getBytes(),0);
            String basicAuth = "Basic "+new String(encodedByte);

            //We start the url connection.
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(100000);//in milliseconds
            urlConnection.setConnectTimeout(150000);
            urlConnection.setRequestMethod("GET");

            //Set the request property so that it parses that into the api for authentication
            urlConnection.addRequestProperty("Authorization",basicAuth);

            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else{
                Log.e("GET query ", "Error response code: " + urlConnection.getResponseCode());

                int responseCode = urlConnection.getResponseCode();
                //502 - Server is under maintenance pleasegetItemsSoldContext try after sometime
                //401 - Unauthorized access
                Log.v("response dushri",responseCode+"");
                if(responseCode == 401){
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setClass(context, FailedLoginCredentialsActivity.class);
                    context.startActivity(intent);
                }else if(responseCode == 502){
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setClass(context, ServerNotRunningActivity .class);
                    context.startActivity(intent);

                }


            }
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("URL Building ItemsSold", "Problem building the URL ", e);
        }
        return url;
    }


    private static List<ItemsSoldAttributes> extractItemsSoldFeatures(String itemSoldJSON){

        //Check if the itemSoldJSON is empty or not
        if(TextUtils.isEmpty(itemSoldJSON)){
            return null;
        }

        //Create an empty itemsSoldAttribute list
        List<ItemsSoldAttributes> itemsSoldAttributes = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.

        try{

            //The query returns a array object and we need to initialize it in a JSONarray object
            JSONArray jsonArray = new JSONArray(itemSoldJSON);
            //Next we get the length of the JSON array
            int length = jsonArray.length();

//            //Create a contentValues object to help put values into database
//            ContentValues contentValues = new ContentValues();

            //This section loops through the entire array to get all the details to put into our SoldItemsDB
            for(int i = 0; i <length;i++){

                //Get current objects in the array
                JSONObject currentItem = jsonArray.getJSONObject(i);
                //Get all the attributes in the currentItem and put into database
                String eng_name = currentItem.getString("eng_name");
                String hin_name = currentItem.getString("hin_name");
                Log.v("Hindi name",hin_name);
                String image64 = currentItem.getString("image64");
                String measure = currentItem.getString("measure");
                int id = currentItem.getInt("id");
                int popularity = currentItem.getInt("popularity");
                int item_category = currentItem.getInt("item_category");
                int selling_price = currentItem.getInt("selling_price");
                int increment = currentItem.getInt("increment");

                //Create a new object of the ItemsSoldAttributes class
                ItemsSoldAttributes itemsSoldAttribute = new ItemsSoldAttributes(eng_name,hin_name,image64,measure,id,popularity,item_category,selling_price,increment);

                //Append the arrayList of itemSoldAttributes array
                itemsSoldAttributes.add(itemsSoldAttribute);

            }
        }catch (JSONException e){

            Log.e("queryItemsSold "," Problem parsing the JSON results ",e);

        }

        return itemsSoldAttributes;
    }
}
