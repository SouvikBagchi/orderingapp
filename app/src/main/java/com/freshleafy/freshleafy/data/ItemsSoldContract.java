package com.freshleafy.freshleafy.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Souvik on 4/7/2017.
 */

public class ItemsSoldContract {

    private ItemsSoldContract() {}

    //THIS NEXT BLOCK OF CODE TAKES CARE OF CONTENT AUTHORITY

    //We first create a content authority which has the same string value as in the manifest
    public static final String CONTENT_AUTHORITY = "com.freshleafy.freshleafy";

    //To make this a usable URI, we use the parse method which takes in a URI string and returns a Uri.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //One for the items sold table
    public static final String PATH_ITEMS_SOLD = "items_sold";


    public static final class itemsSoldContractEntry implements BaseColumns {


        //create a full URI for the class as a constant called CONTENT_URI.
        //The Uri.withAppendedPath() method appends the BASE_CONTENT_URI (which contains the scheme
        // and the content authority) to the path segment.
        public static final Uri CONTENT_URI_ITEMS_SOLD = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ITEMS_SOLD);


        //CREATE THE TABLE NAME FOR THE ITEMS SOLD
        public final static String TABLE_NAME = "items_sold";

        //THE FOLLOWING LINES DEFINES THE COLUMNS NAMES FOR THE
        //The first column is the column id which is the primary key
        public final static String _ID = BaseColumns._ID;

        //The second column will contain a unique item id from the backend
        public final static String COLUMN_ITEM_ID = "ITEM_ID";

        //The third column will contain the item name
        public final static String COLUMN_ITEM_NAME_ENGLISH = "ITEM_NAME_ENGLISH";

        //The  fourth column is the measure of the items
        public final static String COLUMN_MEASURE = "MEASURE";

        //The fifth column will contain the base64 string of the image
        public final static String COLUMN_IMAGE = "IMAGE" ;

        //The sixth column will have the category
        public final static String COLUMN_CATEGORY = "CATEGORY";

        //The seventh column will have the sub category
        public final static String COLUMN_SUBCATEGORY = "SUBCATEGORY";

        //The eight column will have the hindi name
        public final static String COLUMN_ITEM_NAME_HINDI = "ITEM_NAME_HINDI";

        //The ninth column will have the unit selling price
        public final static String COLUMN_UNIT_PRICE = "UNIT_PRICE";

        //The tenth column will contain the quantity which will default to zero
        public final static String COLUMN_QUANTITY = "QUANTITY";

        //The eleventh column will contain the popularity according to which the items will be sorted
        public final static String COLUMN_POPULARITY = "POPULARITY";

        //The twelfth column will contain the increment value which will be the unit increment
        public final static String COLUMN_INCREMENT = "INCREMENT";

        //The thirteenth column will hold the product of quantity and unit price
        public final static String COLUMN_TOTAL = "TOTAL";

        //The fourteenth column will hold nothing date of the items
        public final static String COLUMN_DATE = "DATE";



    }
}
