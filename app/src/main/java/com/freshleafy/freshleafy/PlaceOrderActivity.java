package com.freshleafy.freshleafy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.freshleafy.freshleafy.Adapters.SlotTimeAdapter;
import com.freshleafy.freshleafy.queryAttribues.DeliverySlotAttributes;

import java.util.ArrayList;

public class PlaceOrderActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        //Get the view of Progress bar layout
        View emptyView = findViewById(R.id.place_order_loading_indicator);

        Intent intent= getIntent();
        Bundle extra = intent.getExtras();
        final ArrayList<DeliverySlotAttributes> list = (ArrayList<DeliverySlotAttributes>) extra.getSerializable("list");

        //Initialize the SlotTimeAdapter object
        SlotTimeAdapter slotTimeAdapter = new SlotTimeAdapter(this,list);
        //Get the list view that we are going to inflate
        ListView listView = (ListView) findViewById(R.id.place_order_slot_time);

        //Make the listView use the adapter
        listView.setAdapter(slotTimeAdapter);

        //Disappear the view
        emptyView.setVisibility(View.GONE);

        //THIS BLOCK SETS THE DELIVERY TIME
        final Global globalObject = (Global) getApplication();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("Time ",list.get(position).getTime()+"");
                int time = list.get(position).getTime();
                String shift = list.get(position).getSlotDayTime();
                if(shift.equals("PM")){
                    time = time +12;
                    globalObject.setDeliveryTime(Integer.toString(time)+":00:00");
                    Log.v("Time in 24 Hr Format",globalObject.getDeliveryTime());
                }else {
                    if(time<10){
                        globalObject.setDeliveryTime("0"+Integer.toString(time)+":00:00");
                        Log.v("Time in 24 Hr Format",globalObject.getDeliveryTime());
                    }else {
                        globalObject.setDeliveryTime(Integer.toString(time)+":00:00");
                        Log.v("Time in 24 Hr Format",globalObject.getDeliveryTime());
                    }

                }

                Intent confirmOrder = new Intent(PlaceOrderActivity.this,ConfirmOrderActivity.class);
                startActivity(confirmOrder);


            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(PlaceOrderActivity.this,CartActivity.class);
        startActivity(intent);
        finish();
    }
}
