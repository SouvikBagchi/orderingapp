package com.freshleafy.freshleafy.queryAttribues;

/**
 * Created by Souvik on 4/30/2017.
 */

public class PostOrderAttributes {

    //Define the variables
    private int mItemSoldID;
    private int mQuantity;

    public PostOrderAttributes(int itemSoldID, int quantity){

        mItemSoldID = itemSoldID;
        mQuantity = quantity;
    }

    public int getmItemSoldID(){return mItemSoldID;}

    public int getmQuantity(){return mQuantity;}
}
