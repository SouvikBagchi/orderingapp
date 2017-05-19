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
 * Created by Souvik on 5/13/2017.
 */

public class OrderDetailsCursorAdapter extends CursorAdapter {

    public OrderDetailsCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.order_details_element_list,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        //Find all the views to be updated
//        ImageView imageView = (ImageView) view.findViewById(R.id.order_details_image);
        TextView englishTextView = (TextView) view.findViewById(R.id.order_details_english_name);
        TextView hindiNameTextView = (TextView) view.findViewById(R.id.order_details_hindi_name);
        TextView unitPriceTextView = (TextView) view.findViewById(R.id.order_details_unit_price);
        TextView unitMeasureTextView = (TextView) view.findViewById(R.id.order_detail_unit_measure);
        TextView quantityTextView = (TextView) view.findViewById(R.id.order_detail_total_quantity);
        TextView totalPriceTextView = (TextView) view.findViewById(R.id.order_detail_total_price);

        //Get the column attributes you are interested in
//        int columnImage = cursor.getColumnIndex(myOrdersContractEntry.COLUMN_IMAGE);
        int columnEnglishName  = cursor.getColumnIndex(myOrdersContractEntry.COLUMN_ITEM_NAME_ENGLISH);
        int columnHindiName = cursor.getColumnIndex(myOrdersContractEntry.COLUMN_HINDI_NAME);
        int columnUnitPrice = cursor.getColumnIndex(myOrdersContractEntry.COLUMN_UNIT_PRICE);
        int columnUnitMeasure = cursor.getColumnIndex(myOrdersContractEntry.COLUMN_ITEM_MEASURE);
        int columnQuantity = cursor.getColumnIndex(myOrdersContractEntry.COLUMN_ITEM_QUANTITY);

        //Get the values of the
        String englishName = cursor.getString(columnEnglishName);
        String hindiName = cursor.getString(columnHindiName);
        String unitMeasure = cursor.getString(columnUnitMeasure);
        String unitPrice = cursor.getString(columnUnitPrice);
        String quantity = cursor.getString(columnQuantity);

        //get the string for the cal text view separately
        String calculation = quantity + " x ₹"+unitPrice + " = ₹" + Integer.parseInt(quantity)*Integer.parseInt(unitPrice);

        //Print in log
        Log.v("OrderDetailsCursor",englishName+","+hindiName+","+unitMeasure+","+unitPrice+","+quantity);

        //Set the text
        englishTextView.setText(englishName+"");
        hindiNameTextView.setText(hindiName+"");
        unitPriceTextView.setText("₹" +unitPrice);
        unitMeasureTextView.setText("Per "+unitMeasure);
        quantityTextView.setText(quantity+"");
        totalPriceTextView.setText(calculation+"");




    }
}
