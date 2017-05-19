package com.freshleafy.freshleafy.queryUtils;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.freshleafy.freshleafy.queryAttribues.DeliverySlotAttributes;

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
 * Created by Souvik on 4/27/2017.
 */

public class DeliverySlotQueryUtils {

    public DeliverySlotQueryUtils(){}

    //This block fetches the items
    public static List<DeliverySlotAttributes> fetchDeliverySlots(String requestUrl,String customerName,String customerPassword){

        //Create the url to be passed in http request method
        URL url = createURL(requestUrl);

        //Create json response
        String jsonResponse = null;

        //make http request
        try{

            jsonResponse = makeHTTPRequest(url,customerName,customerPassword);
        }catch (Exception e){

        }

        //Make a delivery slot List and return it
        List<DeliverySlotAttributes> slots = extractDeliverySlotFeatures(jsonResponse);

        return slots;

    }


    //This method creates the url and throws exception in case of a malformed url
    private static URL createURL(String stringURL){

        //Create the URL variable to hold the created url
        URL url = null;

        try{
            url = new URL(stringURL);

        }catch (MalformedURLException e){

            Log.v("Deliver Slot Query Util","Bad URL:"+e);

        }

        return url;
    }

    //This method reads from the input string(bytes) and transforms it into a readable string
    private static String readFromString(InputStream inputStream) throws IOException{

        //Create the variable to hold the string output to be returned
        StringBuilder output = new StringBuilder();
        if(inputStream != null){

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while(line != null){
                output.append(line);
                line = bufferedReader.readLine();
            }

        }
        //Return the built string
        return output.toString();

    }

    //This block makes the HTTP request
    public static String makeHTTPRequest(URL url,String customerName,String customerPassword){

        //Declare the variable to hold the json response
        String jsonResponse = "";

        //Check if url is null, if yes then return early
        if(url == null){

            return jsonResponse;
        }

        //Create a HTTPURLConnection variable
        HttpURLConnection httpURLConnection = null;
        //Create the input stream
        InputStream inputStream = null;

        //Try-Catch block to request the API
        try{
            //Build the user credential since it is a protected route
            String credentials=customerName+":"+customerPassword;
            //Encode the credentials so that the post recognises the stream
            byte[] encodedByte = Base64.encode(credentials.getBytes(),0);
            //Build the String to be passes as a parameter to the request property
            String basicAuth = "Basic "+new String(encodedByte);

            //Start the connection
            httpURLConnection = (HttpURLConnection) url.openConnection();
            //Set read and write timeout
            httpURLConnection.setReadTimeout(150000);
            httpURLConnection.setConnectTimeout(200000);
            httpURLConnection.setRequestMethod("GET");
            //Set the url request property for basic authentication
            httpURLConnection.addRequestProperty("Authorization",basicAuth);

            //Connect the connection
            httpURLConnection.connect();

            //Check for the response code and read input stream if cod = 200
            if(httpURLConnection.getResponseCode()==200){
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromString(inputStream);
            }else {
                Log.v("Delivery Slot Query","Responose code: "+httpURLConnection.getResponseCode());
            }

        }catch (IOException e){
            Log.e("Delivery Slot Query", "Problem retrieving the earthquake JSON results.", e);
        }finally{
            if(httpURLConnection !=null){
                //Disconnect the connection
                httpURLConnection.disconnect();
            }
            if(inputStream!=null){
                //Close the input stream
                try {
                    inputStream.close();
                } catch (IOException e) {

                }
            }
        }

        return jsonResponse;
    }

    //This block extracts the json features we are interested in
    private static List<DeliverySlotAttributes> extractDeliverySlotFeatures(String deliverySlotJSON){

        //Check if deliveryJSON is null
        if(TextUtils.isEmpty(deliverySlotJSON)){
            return null;
        }

        //Create a list to hold the slots
        List<DeliverySlotAttributes> slots = new ArrayList<>();

        //Parse the json response
        try{

            JSONArray jsonArray = new JSONArray(deliverySlotJSON);
            //Get the length of the array
            int length = jsonArray.length();

            //Iterate over the jsonArray
            for(int i =0;i<length;i++){

                //Fetch the current json object
                JSONObject currentJSONObject = jsonArray.getJSONObject(i);
                //Fetch the attributes
                String daySlotTime = currentJSONObject.getString("slot_day_time");
                int time = currentJSONObject.getInt("slot_time");

                //Create a temporary slots attribute object
                DeliverySlotAttributes temporaryAttribute = new DeliverySlotAttributes(time,daySlotTime);
                slots.add(temporaryAttribute);


            }
        }catch (JSONException e){

        }
        return slots;
    }



}
