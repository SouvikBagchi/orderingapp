package com.freshleafy.freshleafy.queryAttribues;

/**
 * Created by Souvik on 5/6/2017.
 */

public class OrderItemsAttributes {

    //Define the constructor variables
    private int mOrder_ID;
    private String mCustomerName;
    private String mOrder_Placed_Date;
    private String mOrder_Delivery_Date;
    private String mDeliveryTime;
    private String mItem_Name_English;
    private int mItem_Quantity;
    private String mItem_Measure;
    private int mUnit_Price;
    private String mItem_Name_Hindi;

    public OrderItemsAttributes(int order_ID, String customerName,String order_Placed_Date,String order_Delivery_Date,String deliveryTime, String item_Name_English, int quantity,
                                String item_Measure, int unit_Measure,String item_Hindi_Name){
        mOrder_ID = order_ID;
        mCustomerName = customerName;
        mOrder_Placed_Date = order_Placed_Date;
        mOrder_Delivery_Date = order_Delivery_Date;
        mDeliveryTime =deliveryTime;
        mItem_Name_English = item_Name_English;
        mItem_Quantity = quantity;
        mItem_Measure = item_Measure;
        mUnit_Price = unit_Measure;
        mItem_Name_Hindi = item_Hindi_Name;
    }

    //Write public methods to access the private variables
    public int getmOrder_ID(){return mOrder_ID;}

    public String getmCustomerName(){return mCustomerName;}

    public String getmOrder_Placed_Date(){return mOrder_Placed_Date;}

    public String getmOrder_Delivery_Date(){return mOrder_Delivery_Date;}

    public String getmItem_Name_English(){return mItem_Name_English;}

    public int getmItem_Quantity(){return mItem_Quantity;}

    public String getmItem_Measure(){return mItem_Measure;}

    public int getmUnit_Price(){return mUnit_Price;}

    public String getmItem_Name_Hindi(){return mItem_Name_Hindi;}

    public String getmDeliveryTime(){return mDeliveryTime;}


}
