package com.freshleafy.freshleafy.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.freshleafy.freshleafy.R;
import com.freshleafy.freshleafy.queryAttribues.DeliverySlotAttributes;

import java.util.ArrayList;

/**
 * Created by Souvik on 4/28/2017.
 */

public class SlotTimeAdapter extends ArrayAdapter<DeliverySlotAttributes> {


    public SlotTimeAdapter(Context context, ArrayList<DeliverySlotAttributes> slotTimes) {
        super(context,0,slotTimes);
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {

        View listItemView = convertView;

        //Check if there is already inflated view
        if(listItemView == null){

            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_slot_time,parent,false);
        }


        //Get the current slot at this position
        DeliverySlotAttributes currentDeliverySlotAttributes = getItem(position);

        //Define all the text views you want to set
        TextView slotTimeTextView = (TextView) listItemView.findViewById(R.id.place_order_shift_time);
        TextView slotShiftChild = (TextView) listItemView.findViewById(R.id.place_order_shift_child);

        TextView slotShiftMain = (TextView) listItemView.findViewById(R.id.place_order_shift_main);

        //Get the attributes
        int time = currentDeliverySlotAttributes.getTime();
        String shift = currentDeliverySlotAttributes.getSlotDayTime();
//        Log.v("Slot shift",shift+"");
        String dayShift;
        if(shift.equals("PM")){
            dayShift = "Evening";
//            Log.v("Day Shift PM",""+dayShift);
        }else{
            dayShift = "Morning";
        }


        //Set the corresponding text views
        slotTimeTextView.setText(Integer.toString(time));
        slotShiftChild.setText(shift);
        slotShiftMain.setText(dayShift);



        return listItemView;



    }
}

