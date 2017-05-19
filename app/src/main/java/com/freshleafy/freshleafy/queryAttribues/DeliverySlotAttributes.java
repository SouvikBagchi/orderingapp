package com.freshleafy.freshleafy.queryAttribues;

import java.io.Serializable;

/**
 * Created by Souvik on 4/27/2017.
 */

public class DeliverySlotAttributes implements Serializable {

    //Define the constructor variables
    private int mTime;
    private String mSlotDayTime;

    public DeliverySlotAttributes(int time, String slotDayTime){

        mTime = time;
        mSlotDayTime = slotDayTime;

    }

    //Create methods to return the attributes
    public int getTime(){return mTime;}

    public String getSlotDayTime(){return mSlotDayTime;}

}
