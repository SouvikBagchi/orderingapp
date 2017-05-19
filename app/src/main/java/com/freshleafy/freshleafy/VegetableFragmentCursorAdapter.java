package com.freshleafy.freshleafy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.freshleafy.freshleafy.data.ItemsSoldContract.itemsSoldContractEntry;

/**
 * Created by Souvik on 4/7/2017.
 */

public class VegetableFragmentCursorAdapter extends CursorAdapter {

    public VegetableFragmentCursorAdapter(Context context,Cursor cursor){
        super(context,cursor,0);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.fragment_vegetable,parent,false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {



        //First find all the views that I want to modify individually
        ImageView imageView = (ImageView) view.findViewById(R.id.vegetable_fragment_fruit_image);
        TextView engTextView = (TextView) view.findViewById(R.id.vegetable_fragment_english_name);
        TextView hindiTextView = (TextView) view.findViewById(R.id.vegetable_fragment_hindi_name);
        TextView measureTextView = (TextView) view.findViewById(R.id.vegetable_fragment_unit_measure);
        TextView priceTextView = (TextView) view.findViewById(R.id.vegetable_fragment_unit_price);
        TextView quantityTextView = (TextView) view.findViewById(R.id.vegetable_fragment_quantity_text_view);
        TextView priceCalTextView = (TextView) view.findViewById(R.id.vegetable_fragment_price_calculation);
        TextView quantityIncrementView = (TextView) view.findViewById(R.id.vegetable_fragment_quantity_increment);

        //Find the columns of the attributes we are interested in
        int columnImage = cursor.getColumnIndex(itemsSoldContractEntry.COLUMN_IMAGE);
        int columnEngName = cursor.getColumnIndex(itemsSoldContractEntry.COLUMN_ITEM_NAME_ENGLISH);
        int columnHinName = cursor.getColumnIndex(itemsSoldContractEntry.COLUMN_ITEM_NAME_HINDI);
        int columnMeasure = cursor.getColumnIndex(itemsSoldContractEntry.COLUMN_MEASURE);
        final int columnPrice = cursor.getColumnIndex(itemsSoldContractEntry.COLUMN_UNIT_PRICE);
        final int columnQuantity = cursor.getColumnIndex(itemsSoldContractEntry.COLUMN_QUANTITY);
        final int columnID = cursor.getColumnIndex(itemsSoldContractEntry._ID);
        final int columnIncrement = cursor.getColumnIndex(itemsSoldContractEntry.COLUMN_INCREMENT);

        //Read the attributes from the cursor
        String image = cursor.getString(columnImage);
        String engName = cursor.getString(columnEngName);
        String hinName = cursor.getString(columnHinName);
        String measure = cursor.getString(columnMeasure);
        String price = cursor.getString(columnPrice);
        String quantity = cursor.getString(columnQuantity);
        int quantityIncrement = cursor.getInt(columnIncrement);

        //get the string for the cal text view separately
        String calculation = quantity + " x "+price + " = " + Integer.parseInt(quantity)*Integer.parseInt(price);

        //Decode the string to create a bitmap
        byte[] decodedString = Base64.decode(image,Base64.DEFAULT);
        Log.v("This is the bitmap ",decodedString+"");
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
        Log.v("This is something else ",decodedByte+"");

        //Update the text views with the values
        imageView.setImageBitmap(decodedByte);
        engTextView.setText(engName);
        hindiTextView.setText(hinName);
        measureTextView.setText("per "+measure);
        priceTextView.setText("₹ " + price);
        quantityTextView.setText(quantity);
        priceCalTextView.setText(calculation);
        quantityIncrementView.setText("Quantity + "+quantityIncrement);

        //Define the two buttons (increment and decrement)
        Button incrementButton = (Button) view.findViewById(R.id.vegetable_fragment_increment);
        Button decrementButton = (Button) view.findViewById(R.id.vegetable_fragment_decrement);

        //Get the position of the cursor
        final int position = cursor.getPosition();

        //Set the onclick listener to increment button
        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Set up a content values object
                ContentValues incrementValue = new ContentValues();

                //Make the cursor go to the position of the click
                cursor.moveToPosition(position);

                //Get the increment unit
                int incrementUnit = cursor.getInt(columnIncrement);
                //Get the old quantity
                int oldQuantity = (cursor.getInt(columnQuantity));
                //updatet the quantity
                int newQuantity = oldQuantity +incrementUnit;

                //Put it into the contentvalue object
                incrementValue.put(itemsSoldContractEntry.COLUMN_QUANTITY,newQuantity);


                //Get the unit price
                int unitPrice = cursor.getInt(columnPrice);
                //Calculate the total
                int total = newQuantity*unitPrice;
                //Put the total in increment value
                incrementValue.put(itemsSoldContractEntry.COLUMN_TOTAL,total);

                //THIS BLOCK OF CODE UPDATES THE VALUE INTO THE DATABASE
                //Selection claus which will point to the item_sold_id which will be updated
                String selection = itemsSoldContractEntry._ID + "=?";
                //Get the item id which should be updated
                int item_id = cursor.getInt(columnID);
                String itemIDArgs = Integer.toString(item_id);

                //Selection args claus
                String[] selectionArgs = {itemIDArgs};
                //Update the value
                int rowsUpdated = context.getContentResolver().update(
                        Uri.withAppendedPath(itemsSoldContractEntry.CONTENT_URI_ITEMS_SOLD,Integer.toString(item_id)),
                        incrementValue,
                        selection,selectionArgs);
                Log.v("No of rows ","update : "+rowsUpdated);

            }
        });

        //Set onclick listener on the decrement button
        decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Create a Content Values object
                ContentValues decrementValue = new ContentValues();

                //Move the cursor to the position
                cursor.moveToPosition(position);


                //Get the decrement unit
                int decrementUnit = cursor.getInt(columnIncrement);


                //Get the current quantity value
                int oldQuantity = cursor.getInt(columnQuantity);
                //Define a variable for new Quantity
                int newQuantity = 0;
                //Check if it is zero
                if(oldQuantity ==0){
                    Toast.makeText(context,"Quantity already 0",Toast.LENGTH_SHORT).show();
                }else {
                    newQuantity = oldQuantity - decrementUnit;
                }

                decrementValue.put(itemsSoldContractEntry.COLUMN_QUANTITY,newQuantity);

                //Get the unit price
                int unitPrice = cursor.getInt(columnPrice);
                //Calculate the total
                int total = newQuantity*unitPrice;
                //Put the total in increment value
                decrementValue.put(itemsSoldContractEntry.COLUMN_TOTAL,total);

                //THIS BLOCK OF CODE UPDATES THE VALUE INTO THE DATABASE
                //Selection claus which will point to the item_sold_id which will be updated
                String selection = itemsSoldContractEntry._ID + "=?";
                //Get the item id which should be updated
                int item_id = cursor.getInt(columnID);
                String itemIDArgs = Integer.toString(item_id);

                //Selection args claus
                String[] selectionArgs = {itemIDArgs};
                //Update the value
                int rowsUpdated = context.getContentResolver().update(
                        Uri.withAppendedPath(itemsSoldContractEntry.CONTENT_URI_ITEMS_SOLD,Integer.toString(item_id)),
                        decrementValue,
                        selection,selectionArgs);
                Log.v("No of rows ","update : "+rowsUpdated);

            }
        });
    }

}

