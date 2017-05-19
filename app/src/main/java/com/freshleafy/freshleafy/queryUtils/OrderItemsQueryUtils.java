package com.freshleafy.freshleafy.queryUtils;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.freshleafy.freshleafy.queryAttribues.OrderItemsAttributes;

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
 * Created by Souvik on 5/7/2017.
 */

public class OrderItemsQueryUtils {

    /**
     * Create a private constructor because no one should ever create a QueryUtils object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */

    public OrderItemsQueryUtils(){}


    //Create the feature extraction method
    public static List<OrderItemsAttributes> fetchOrderItems(String requestURL, String customerLogin,String customerPassword){

        //Create the url
        URL url = createURL(requestURL);


        //Create variable to hold the response string
        String jsonResponse = "";

        try {
            //Get the response
            jsonResponse = makeHTTPRequest(url,customerLogin,customerPassword);

        }catch (IOException e){
            Log.v("OrderItemsQuery ioe",e+"");
        }

        //Extract the features from the response
        List<OrderItemsAttributes> orderItemsAttributes = extractFeatureFromJson(jsonResponse);

        //DELETE LATER
        Log.v("FETCH12",orderItemsAttributes+"");

        //Return the order items attribute
        return orderItemsAttributes;

    }

    //Returns the JSON String
    private static String makeHTTPRequest(URL url,String customerLogin,String customerPass) throws IOException{

        //Create the jsonString which will finally hold the value from the api
        String jsonString = "";

        //Check if the url is null
        if(url == null){
            return jsonString;
        }

        //Create variable for the HTTP url object
        HttpURLConnection httpURLConnection = null;

        //Create variable for the input stream
        InputStream inputStream = null;

        //The try catch block request
        try{


            //Define the credentials
            String credentials = customerLogin+":"+customerPass;

            //Encode the credentials
            byte[] encodedByte = Base64.encode(credentials.getBytes(),0);
            String basicAuth = "Basic "+ new String(encodedByte);

            //Start(Open) the url connection
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(150000);
            httpURLConnection.setConnectTimeout(200000);

            //Add the authorization request property
            httpURLConnection.addRequestProperty("Authorization",basicAuth);

            //Connect
            httpURLConnection.connect();


            //Check if the response code is 200 then proceed
            if(httpURLConnection.getResponseCode()==200){
                //Read the Stream
                inputStream = httpURLConnection.getInputStream();
                //Parse it into the jsonResponse string
                jsonString = readFromString(inputStream);
            }


        }catch (IOException e){
            Log.v("OrderItemsQueryUtils",e+"");
        }finally {
            if(httpURLConnection != null){
                //Disconnect the httpURLConnection
                httpURLConnection.disconnect();
            }
            if(inputStream != null){
                inputStream.close();
            }
        }

        return jsonString;
    }


    //Create the method to create the url from the string provided
    private static URL createURL(String stringUrl){

        URL returnUrl = null;
        try{

            returnUrl = new URL(stringUrl);

        }catch (MalformedURLException malformedUrlException){
            Log.v("Malformed URL",malformedUrlException+"");
        }

        return returnUrl;
    }


    //This method helps us convert the incoming stream to string output
    private static String readFromString(InputStream inputStream) throws  IOException{

        //Create the string builder variable
        StringBuilder output = new StringBuilder();

        if(inputStream != null){
            //Create the input stream reader
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));

            //Create a Buffered Reader to read the inputStream
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            //Create a temporary variable to hold the chars
            String inputLine = bufferedReader.readLine();

            //Append the string to output
            while (inputLine != null){
                output.append(inputLine);
                inputLine = bufferedReader.readLine();
            }


        }


        return output.toString();

    }

    //This method helps us extract the features by converting the jsonString to json object
    private static List<OrderItemsAttributes> extractFeatureFromJson(String orderItemJSON){

        //Check if the input is empty then return early
        if(TextUtils.isEmpty(orderItemJSON)){
            return  null;
        }

        //Create a list of empty orderItems
        List<OrderItemsAttributes> orderItemsAttributes = new ArrayList<>();

        try{

            //Try to parse the json to get the features
            JSONArray jsonArray = new JSONArray(orderItemJSON);

            //DELETE LATER
            Log.v("OrdQueryJSONExtFeat10",jsonArray.toString());


            //Get the length of the array
            int length = jsonArray.length();

            //iterate over the json
            for(int i = 0;i<length;i++){

                //Get the current json object
                JSONObject currentJSONObject = jsonArray.getJSONObject(i);

                //Get all the attributes
                int id = currentJSONObject.getInt("order_id");
                String customerName = currentJSONObject.getString("customer_name");
                String orderPlacedDate = currentJSONObject.getString("order_placed_date");
                String orderDeliveryDate = currentJSONObject.getString("delivery_date");
                String deliveryTime = currentJSONObject.getString("delivery_time");
                String englishName = currentJSONObject.getString("english_name");
                int quantity = currentJSONObject.getInt("quantity");
                String  measure = currentJSONObject.getString("measure");
                int unitPrice = currentJSONObject.getInt("unit_price");
                String hindiName = currentJSONObject.getString("hindi_name");

                //Create a temporary OrderItems object to hold the attributes
                OrderItemsAttributes tempOrderItemsAttributes = new OrderItemsAttributes(id,customerName,orderPlacedDate,orderDeliveryDate,deliveryTime
                        ,englishName,quantity,measure,unitPrice,hindiName);

                //DELETE THIS LATER
                Log.v("OrderItemAtt11",tempOrderItemsAttributes+"");
                //WORKS TILL HERE

                //Append the temporary item to the list
                orderItemsAttributes.add(tempOrderItemsAttributes);

            }

        }catch (JSONException e){

            Log.v("OrderItemsQueryUtils jm",e+"");
        }

        return orderItemsAttributes;

    }
}
