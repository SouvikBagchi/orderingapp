package com.freshleafy.freshleafy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.freshleafy.freshleafy.loadingActivities.ConfirmOrderLoadingActivity;

public class ConfirmOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order_activty);

        //Initialize a global variable
        final Global globalObject = new Global();

        //Get the button
        Button confirmButton = (Button) findViewById(R.id.confrim_order_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                globalObject.setOrderStatus("ORDER PLACED");
                Intent intent = new Intent(ConfirmOrderActivity.this, ConfirmOrderLoadingActivity.class);
                startActivity(intent);


            }
        });

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
