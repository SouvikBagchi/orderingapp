package com.freshleafy.freshleafy.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Souvik on 4/7/2017.
 */

public class MyOrdersContract {

    private MyOrdersContract() {
    }

    //THIS NEXT BLOCK OF CODE TAKES CARE OF CONTENT AUTHORITY

    //We first create a content authority which has the same string value as in the manifest
    public static final String CONTENT_AUTHORITY = "com.freshleafy.freshleafy";

    //To make this a usable URI, we use the parse method which takes in a URI string and returns a Uri.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    //One for My Orders table
    public static final String PATH_MY_ORDERS = "orders";

    public static final class myOrdersContractEntry implements BaseColumns {

        //The content URI to access the pet data in the provider
        public static final Uri CONTENT_URI_MY_ORDER = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MY_ORDERS);

        //Name of the table
        public final static String TABLE_NAME = "orders";

        //Start naming the columns
        //The first column is the column id which holds the primary key
        public final static String _ID = BaseColumns._ID;
        //Second Column holds the actual order_id from the backend
        public final static String COLUMN_BE_ID = "BE_ID"; //ORDER_ID
        //Third column holds the orders place date
        public final static String COLUMN_ORDER_PLACED_DATE = "ORDERS_PLACED_DATE";
        //Fourth Column holds the date of delivery
        public final static String COLUMN_ORDER_DELIVERY_DATE = "ORDER_DELIVERY_DATE";
        //Fifth column holds the ITEM_NAME
        public final static String COLUMN_ITEM_NAME_ENGLISH = "ITEM_NAME_ENGLISH";
        //Sixth column hold the QUANTITY OF THE ITEM ORDERED
        public final static String COLUMN_ITEM_QUANTITY = "ITEM_QUANTITY";
        //The seventh column holds the item measure
        public final static String COLUMN_ITEM_MEASURE = "MEASURE";
        //The eight column hold the price
        public final static String COLUMN_UNIT_PRICE = "UNIT_PRICE";
        //The ninth column will hold the hindi name
        public final static String COLUMN_HINDI_NAME = "ITEM_NAME_HINDI";
        //The tenth column holds the time
        public final static String COLUMN_ORDER_DELIVERY_TIME = "DELIVERY_TIME";




    }
}
