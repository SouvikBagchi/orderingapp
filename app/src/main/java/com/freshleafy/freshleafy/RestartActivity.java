package com.freshleafy.freshleafy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class RestartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restart);

        System.exit(0);

    }

    public static void doRestart(Activity anyActivity){

        anyActivity.startActivity(new Intent(anyActivity.getApplicationContext(),RestartActivity.class));

    }
}
