package com.freshleafy.freshleafy.queryAttribues;

/**
 * Created by Souvik on 4/16/2017.
 */

public class ItemsSoldAttributes {


    //Define constructor variables
    private String mEng_name;
    private String mHin_name;
    private String mImage64;
    private String mMeasure;
    private int mId;
    private int mPopularity;
    private int mItem_category;
    private int mSelling_price;
    private int mIncrement;

    //Constructs a new itemssoldattribute object

    public ItemsSoldAttributes(String eng_name, String hin_name, String image64, String measure, int id, int popularity, int item_category, int selling_price,int increment) {

        mEng_name = eng_name;
        mHin_name = hin_name;
        mImage64 = image64;
        mMeasure = measure;
        mId = id;
        mPopularity = popularity;
        mItem_category = item_category;
        mSelling_price = selling_price;
        mIncrement = increment;

    }

    //Create methods to return the value of the attributes
    public String getEngName() { return mEng_name;}

    public String getHin_name(){ return mHin_name;}

    public String getImage64(){ return mImage64;}

    public String getMeasure(){ return mMeasure;};

    public int getID(){ return mId;}

    public int getPopularity(){ return mPopularity;}

    public int getItemCategory(){ return mItem_category;}

    public int getSellingPrice(){ return mSelling_price;}

    public int getIncrement(){return mIncrement;}



}
