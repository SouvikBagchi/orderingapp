package com.freshleafy.freshleafy.queryUtils;

import android.util.Base64;
import android.util.Log;

import com.freshleafy.freshleafy.queryAttribues.PostOrderAttributes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Souvik on 4/30/2017.
 */

public class PostOrderQueryUtils {
    public PostOrderQueryUtils() {
    }


    //This creates the URL
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {

            Log.v("URL is invalid ", "problem with url", e);
        }
        return url;
    }

    //This method writes it to API String stringUrl,List<PostOrderAttributes> list
    public String writeOrder(String stringUrl,List<PostOrderAttributes> postOrderAttributesList ,
                           String customerLogin, String customerPassword,
                           String orderPlacedDate, String deliveryDate, String deliveryTime,
                           int totalPrice, String orderStatus) {

        //  Create the url
        URL url = createUrl(stringUrl);
        //To read the response
        BufferedReader reader = null;
        //Write the response from API to this string
        String JSONResponse = null;

        //Make a variable to hold the size
        int length = postOrderAttributesList.size();

        try{


            //Create the json object
            JSONObject mainJSONObject = new JSONObject();
            mainJSONObject.put("customer_login",customerLogin);
            mainJSONObject.put("order_placed_date",orderPlacedDate);
            mainJSONObject.put("delivery_date",deliveryDate);
            mainJSONObject.put("delivery_time",deliveryTime);
            mainJSONObject.put("total_selling_price",totalPrice);
            mainJSONObject.put("order_status",orderStatus);

            //Create the JSONArray to hold the items attribute objects
            JSONArray itemsArray = new JSONArray();
            for(int i =0;i<length;i++){
                JSONObject tempObject = new JSONObject();
                PostOrderAttributes currentItem = postOrderAttributesList.get(i);
                int id = currentItem.getmItemSoldID();
                int quantity = currentItem.getmQuantity();
                tempObject.put("items_sold_id",id);
                tempObject.put("quantity",quantity);
                itemsArray.put(tempObject);
            }

            //Add the JSONArray to the main object
            mainJSONObject.put("items",itemsArray);

            //Create the JSONArray which will hold all the items
            Log.v("OBJECT ",mainJSONObject.toString());




            //

            //Create the connection
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(true);//This is set to true since we want a POST method

            //Set the header and the methods
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type","application/json");
            httpURLConnection.setRequestProperty("Accept","application/json");


            //Create the credentials for accessing the ordertable
            String credentials = customerLogin+":"+customerPassword;
            Log.v("Credentials",credentials);
            byte[] encodedByte = Base64.encode(credentials.getBytes(),0);
            String basicAuth = "Basic"+new String(encodedByte);

            //Set request property for authorization
            httpURLConnection.addRequestProperty("Authorization",basicAuth);

            //Write json data
            Writer writer = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(),"UTF-8"));
            writer.write(mainJSONObject.toString());
            writer.close();

            //Get the input stream from the API
            InputStream inputStream = httpURLConnection.getInputStream();

            //Create a buffered reader to read the above input stream
            StringBuffer buffer =  new StringBuffer();
            if(inputStream == null){
                Log.v("input Stream","was null") ;
            }else {
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String inputLine;

                while((inputLine = reader.readLine())!= null){
                    buffer.append(inputLine);
                }
                //check if the buffer returned is null, if yes then do nothing
                if(buffer.length() == 0 ){
                    return null;
                }

                JSONResponse = buffer.toString();

                Log.v("The response from "," API : " +JSONResponse);
            }


        }catch (IOException e){

        }catch (JSONException e){

        }
        return JSONResponse;
    }
}


