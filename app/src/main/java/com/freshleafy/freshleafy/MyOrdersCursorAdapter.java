package com.freshleafy.freshleafy;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.freshleafy.freshleafy.data.MyOrdersContract.myOrdersContractEntry;

/**
 * Created by Souvik on 5/8/2017.
 */

public class MyOrdersCursorAdapter extends CursorAdapter {


    public MyOrdersCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        //Create the elemental view
        return LayoutInflater.from(context).inflate(R.layout.order_list_elements, parent, false);
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {

        //Find all the view items
        TextView orderId = (TextView) view.findViewById(R.id.order_id_textview);
        TextView orderDeliveryDate = (TextView) view.findViewById(R.id.order_delivery_date);
        TextView orderPlacedDate = (TextView) view.findViewById(R.id.order_placed_date);
        TextView orderDeliveryTime = (TextView) view.findViewById(R.id.order_delivery_time);
        TextView orderTotal = (TextView) view.findViewById(R.id.my_order_total_price);

        //Get all the index of the columns
        int orderIdCol = cursor.getColumnIndex(myOrdersContractEntry.COLUMN_BE_ID);
        int deliveryDateCol = cursor.getColumnIndex(myOrdersContractEntry.COLUMN_ORDER_DELIVERY_DATE);
        int deliveryTimeCol = cursor.getColumnIndex(myOrdersContractEntry.COLUMN_ORDER_DELIVERY_TIME);
        int placedDateCol = cursor.getColumnIndex(myOrdersContractEntry.COLUMN_ORDER_PLACED_DATE);
        int unitPriceCol = cursor.getColumnIndex(myOrdersContractEntry.COLUMN_UNIT_PRICE);
        int quantityCol = cursor.getColumnIndex(myOrdersContractEntry.COLUMN_ITEM_QUANTITY);
        int totalCol = cursor.getColumnIndex("TOTAL");

        //Find out the values from the cols
        String ID = cursor.getString(orderIdCol);
        String deliveryDate = cursor.getString(deliveryDateCol);
        String deliveryTime = cursor.getString(deliveryTimeCol);
        String placedDate = cursor.getString(placedDateCol);
        String price = cursor.getString(unitPriceCol);
        String quantity = cursor.getString(quantityCol);
        String total = cursor.getString(7);

        //Calculate the total for check with totalNew
        int totalNew = Integer.parseInt(price) * Integer.parseInt(quantity);

        //This shows what the cusor is returning
        Log.v("MyORDCURAD", ID + "," + deliveryDate + "," + deliveryTime + "," + placedDate + "," + total + "," + totalNew);

        //Set it to the text views
        orderId.setText(ID);
        orderDeliveryDate.setText("Delivery Date : " + deliveryDate);
        orderDeliveryTime.setText("Delivery Time : " + deliveryTime);
        orderPlacedDate.setText("Date Ordered : " + placedDate);
        orderTotal.setText(total);
    }
}

