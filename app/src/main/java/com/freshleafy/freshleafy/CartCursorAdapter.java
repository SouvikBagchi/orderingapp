package com.freshleafy.freshleafy;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.freshleafy.freshleafy.data.ItemsSoldContract.itemsSoldContractEntry;
/**
 * Created by Souvik on 4/24/2017.
 */

public class CartCursorAdapter extends CursorAdapter{

    public CartCursorAdapter(Context context, Cursor c) {
        super(context, c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.cart_list_elements,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        //Find all the views to be updated
        ImageView imageView = (ImageView) view.findViewById(R.id.image_cart_list_view);
        TextView englishNameTextView = (TextView) view.findViewById(R.id.cart_list_english_name);
        TextView hindiNameTextView = (TextView) view.findViewById(R.id.cart_list_hindi_name);
        TextView unitPriceTextView = (TextView) view.findViewById(R.id.cart_list_unit_price);
        TextView unitMeasureTextView = (TextView) view.findViewById(R.id.cart_list_unit_measure);
        TextView quantityTextView = (TextView) view.findViewById(R.id.cart_total_quantity);
        TextView totalPriceTextView = (TextView) view.findViewById(R.id.cart_list_total_price);

        //Get the column attributes you are interested in
        int columnImage = cursor.getColumnIndex(itemsSoldContractEntry.COLUMN_IMAGE);
        int columnEnglishName  = cursor.getColumnIndex(itemsSoldContractEntry.COLUMN_ITEM_NAME_ENGLISH);
        int columnHindiName = cursor.getColumnIndex(itemsSoldContractEntry.COLUMN_ITEM_NAME_HINDI);
        int columnUnitPrice = cursor.getColumnIndex(itemsSoldContractEntry.COLUMN_UNIT_PRICE);
        int columnUnitMeasure = cursor.getColumnIndex(itemsSoldContractEntry.COLUMN_MEASURE);
        int columnQuantity = cursor.getColumnIndex(itemsSoldContractEntry.COLUMN_QUANTITY);

        //Get the values of the
        String image = cursor.getString(columnImage);
        String englishName = cursor.getString(columnEnglishName);
        String hindiName = cursor.getString(columnHindiName);
        String unitMeasure = cursor.getString(columnUnitMeasure);
        String unitPrice = cursor.getString(columnUnitPrice);
        String quantity = cursor.getString(columnQuantity);

        //Log.v("Image ", englishName);

        //Decode the string to create a bitmap
        byte[] decodedString = Base64.decode(image,Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);

        //get the string for the cal text view separately
        String calculation = quantity + " x ₹"+unitPrice + " = ₹" + Integer.parseInt(quantity)*Integer.parseInt(unitPrice);


        //Set the text
        imageView.setImageBitmap(decodedByte);
        englishNameTextView.setText(englishName);
        hindiNameTextView.setText(hindiName);
        unitPriceTextView.setText("₹" +unitPrice);
        unitMeasureTextView.setText("Per "+unitMeasure);
        quantityTextView.setText(quantity);
        totalPriceTextView.setText(calculation);

    }
}
