package com.freshleafy.freshleafy;

import android.app.Application;

/**
 * Created by Souvik on 4/26/2017.
 */

public class Global extends Application {

    private String customerName; // customer_login
    private String customerPassword;
    private String orderPlacedDate; //order_placed_date
    private String deliveryDate; //delivery_date
    private String deliveryTime; //delivery_time
    private int totalSellingPrice; // total_selling_price
    private String orderStatus; //order_status




    public void setCustomerName(String name){customerName = name;}

    public void setCustomerPassword(String password){customerPassword = password;}

    public void setOrderPlacedDate(String placedDate){orderPlacedDate = placedDate;}

    public void setDeliveryDate(String deliveryDateInput){deliveryDate = deliveryDateInput;}

    public void setDeliveryTime(String deliveryTimeInput){deliveryTime = deliveryTimeInput;}

    public void setTotalSellingPrice(int totalSellingPriceInput){totalSellingPrice = totalSellingPriceInput;}

    public void setOrderStatus(String orderStatusOrdered){orderStatus = orderStatusOrdered;}

    public String getCustomerName(){return customerName;}

    public String getCustomerPassword(){return customerPassword;}

    public String getOrderPlacedDate(){return orderPlacedDate;}

    public String getDeliveryDate(){return deliveryDate;}

    public String getDeliveryTime(){return deliveryTime;}

    public int getTotalSellingPrice(){return totalSellingPrice;}

    public String getOrderStatus(){return orderStatus;}
}
