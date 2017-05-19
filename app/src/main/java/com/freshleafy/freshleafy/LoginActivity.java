package com.freshleafy.freshleafy;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.freshleafy.freshleafy.loadingActivities.ItemsSoldLoadActivity;

public class LoginActivity extends AppCompatActivity {



    //This variable will hold the empty state view and set it when no internet activity
    private TextView mEmptyStateTextView;
    //scrollView
    private LinearLayout mEmptyView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Get the context to delete the database everytime the app starts
        Context context = getApplicationContext();
        //Delete the database
        context.deleteDatabase("freshleafy.db");

        //THIS PART OF THE CODE WILL TAKE CARE OF CONNECTIVITY SETTINGS
        //Initialize mEmptyStateTextView to to text view.
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        //Initialize mScrollView
        mEmptyView = (LinearLayout) findViewById(R.id.activity_login);

        //check for the internet connection
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        //This part takes care of the scrollview and shows no internet connection message when there is no internet connection
        if(networkInfo != null && networkInfo.isConnected()){
            mEmptyStateTextView.setVisibility(View.GONE);

        }
        else{

            mEmptyStateTextView.setText("No Internet Connection");
            mEmptyView.setVisibility(View.INVISIBLE);

        }

        //Instantiate the global class and get the variables
        final Global globalObject = (Global)getApplication();

        //Set up the edit text objects
        //This part of the code accesses the edit text fields and passes it onto ItemsSoldQueryUtils for verification
        final EditText customerName = (EditText) findViewById(R.id.login_name_edit_text);
        final EditText passwordEditText = (EditText) findViewById(R.id.password_edit_text);

        //This is where the login button is assigned and intent to start next activity
        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get the input from the names and the password text field
                String nameField = customerName.getText().toString().trim();
                String passwordField = passwordEditText.getText().toString().trim();


                //Set the global variables
                globalObject.setCustomerName(nameField);
                globalObject.setCustomerPassword(passwordField);

                //Create the intent and the intent paths
                Intent intentMainActivity = new Intent(LoginActivity.this,ItemsSoldLoadActivity.class);
                //start the activity on click
                startActivity(intentMainActivity);
                finish();
            }
        });

    }
}
