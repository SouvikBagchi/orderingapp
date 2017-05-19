package com.freshleafy.freshleafy.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Souvik on 4/7/2017.
 */

public class AuthContract {

    private AuthContract() {}

    //THIS NEXT BLOCK OF CODE TAKES CARE OF CONTENT AUTHORITY

    //We first create a content authority which has the same string value as in the manifest
    public static final String CONTENT_AUTHORITY = "com.freshleafy.freshleafy";

    //To make this a usable URI, we use the parse method which takes in a URI string and returns a Uri.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //One for auth table
    public static final String  PATH_AUTH = "auth";

    public static final class authContractEntry implements BaseColumns {

        //create a full URI for the class as a constant called CONTENT_URI.
        //The Uri.withAppendedPath() method appends the BASE_CONTENT_URI (which contains the scheme
        // and the content authority) to the path segment.
        public static final Uri CONTENT_URI_AUTH = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_AUTH);


        //Name of the table
        public final static String TABLE_NAME = "auth";

        //The primary key
        public final static String _ID = BaseColumns._ID;

        //The first column holds the customer_app_id/customer_id
        public final static String COLUMN_AUTH_ID = "AUTH_ID";

        //The second column hold
        public final static String COLUMN_AUTH_TOKEN = "AUTH_TOKEN";
    }

}
