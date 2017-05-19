package com.freshleafy.freshleafy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.freshleafy.freshleafy.data.MyOrdersContract.myOrdersContractEntry;
import com.freshleafy.freshleafy.loadingActivities.OrderLoadingActivity;

public class MyAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        //Enable the back button on menu
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Delete orders here
        //Delete the table containing orders
        int rows = getContentResolver().delete(myOrdersContractEntry.CONTENT_URI_MY_ORDER,null,null);
        Log.v("MyOrdersActivity","Rows Deleted "+rows);

        // THIS PART OF THE CODE TAKES CARE OF THE INTENTS TO MY ORDERS AND MY PROFILE
        //Create a TextView object for my orders
        TextView myOrderTextView = (TextView) findViewById(R.id.my_order_text_view);
        //Set on click listener on the TextView
        myOrderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create the intent to set the path for crossover
                Intent myOrderIntent = new Intent(MyAccountActivity.this, OrderLoadingActivity.class);
                //Start the intent
                startActivity(myOrderIntent);
            }
        });

        //Create a TextView object for my orders
        TextView myProfileTextView = (TextView) findViewById(R.id.my_profile_text_view);
        //Set on click listener on the TextView
        myProfileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create the intent to set the path for crossover
                Intent myProfileIntent = new Intent(MyAccountActivity.this, ProfileActivty.class);
                //Start the intent
                startActivity(myProfileIntent);
                finish();
            }
        });

        //Override to use back menu button



//        Button button = (Button) findViewById(R.id.testbutton);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Posting posting = new Posting();
//                posting.execute();
//
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                Intent intent = new Intent(MyAccountActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MyAccountActivity.this,MainActivity.class);
        startActivity(intent);
    }
}

//    public class Posting extends AsyncTask<Void,Void,Void>{
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            URL url = null;
//            BufferedReader reader = null; //To read the response
//            String JSONResponse = null; //Write the response to this string
//
//            try {
//
//                url = new URL("http://ec2-34-209-89-98.us-west-2.compute.amazonaws.com/newcust");
//            }catch (MalformedURLException e) {
//
//                Log.v("Bad Url"," : "+e);
//            }
//
//            try{
//
//
//                //is output buffer writer
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setDoOutput(true); //This is set to true since we want a POST method
//
//
//                //set headers and methods
//                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.setRequestProperty("Content-Type","application/json");
//                httpURLConnection.setRequestProperty("Accept","application/json");
//
//                //create the test json object
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("name","a1");
//                jsonObject.put("login","al1");
//                jsonObject.put("app_id","1");
//
//
//                Log.v("String Json ",""+ jsonObject.toString());
//
//                //write the json data
//                Writer writer = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(),"UTF-8"));
//                writer.write(jsonObject.toString());
//                writer.close();
//
//                //Get the input stream from the API
//                InputStream inputStream = httpURLConnection.getInputStream();
//
//
//                //Create a buffered reader to read the above input stream
//                StringBuffer buffer = new StringBuffer();
//                if(inputStream == null){
//                    return null;
//                }else{
//
//                    reader = new BufferedReader(new InputStreamReader(inputStream));
//
//                    String inputLine;
//                    while ((inputLine = reader.readLine())!= null){
//
//                        buffer.append(inputLine);
//                    }
//
//                }
//
//                //check if the buffer returned is null, if yes then do nothing
//                if(buffer.length() == 0 ){
//                    return null;
//                }
//
//                JSONResponse = buffer.toString();
//
//                Log.v("The response from "," API : " +JSONResponse);
//
////
////                DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
////                dataOutputStream.writeBytes(jsonObject.toString());
////                dataOutputStream.flush();
////                dataOutputStream.close();
//
//                }catch (IOException e ) {
//                Log.v("IO ERROR ", ": " + e);
//            }catch (JSONException s){
//                Log.v("Makelaude "," Json parse nahi hua "+s);
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            Log.v("It is done "," posting");
//        }
//    }






