//THE NEXT THREE OVERRIDDEN METHODS FETCHED DATA AND ADDS THEM TO THE SQ BE


//    @Override
//    public Loader<List<ItemsSoldAttributes>> onCreateLoader(int id, Bundle args) {
//        return new ItemSoldLoader(MainActivity.this, URL_ITEMS_SOLD_QUERY);
//    }
//
//
//
//    @Override
//    public void onLoadFinished(Loader<List<ItemsSoldAttributes>> loader, List<ItemsSoldAttributes> data) {
//
//        List<ItemsSoldAttributes> list = data;
//
//        ContentValues contentValues = new ContentValues();
//        int length = data.size();
//        for(int i = 0;i<length;i++){
//
//            //Get the features from the data array
//            String eng_name = list.get(i).getEngName();
//            String hin_name = list.get(i).getHin_name();
//            String image64 = list.get(i).getImage64();
//            String measure = list.get(i).getMeasure();
//            int id = list.get(i).getID();
//            int popularity = list.get(i).getPopularity();
//            int item_category = list.get(i).getItemCategory();
//            int selling_price = list.get(i).getSellingPrice();
//
//            //Put the data into content values
//            contentValues.put(itemsSoldContractEntry.COLUMN_ITEM_NAME_ENGLISH,eng_name);
//            contentValues.put(itemsSoldContractEntry.COLUMN_ITEM_NAME_HINDI,hin_name);
//            contentValues.put(itemsSoldContractEntry.COLUMN_IMAGE,image64);
//            contentValues.put(itemsSoldContractEntry.COLUMN_MEASURE,measure);
//            contentValues.put(itemsSoldContractEntry.COLUMN_ITEM_ID,id);
//            contentValues.put(itemsSoldContractEntry.COLUMN_POPULARITY,popularity);
//            contentValues.put(itemsSoldContractEntry.COLUMN_CATEGORY,item_category);
//            contentValues.put(itemsSoldContractEntry.COLUMN_UNIT_PRICE,selling_price);
//
//            //Insert the data into the database
//            Uri newUri = getContentResolver().insert(itemsSoldContractEntry.CONTENT_URI_ITEMS_SOLD,contentValues);
//            Log.v("I don't know"," Let's find out : "+ newUri);
//
//
//        }
//        View progressBar = findViewById(R.id.loading_indicator);
//        progressBar.setVisibility(View.GONE);
//
//
//
//    }
//
//    @Override
//    public void onLoaderReset(Loader<List<ItemsSoldAttributes>> loader) {
//
//
//
//    }



//        THIS PART OF THE CODE WILL FETCH ALL THE DATA FROM THE BE
//        BEING SIMULATED BY CREATION OF 99 ITEMS IN SQLITE APP BE FOR NOW
//        get the database repository in the writable mode

//final Random randomGenerator = new Random();
////        SQLiteDatabase db = mLeafyDBHelper.getWritableDatabase();
//
//        //create a content values object
//        ContentValues contentValues = new ContentValues();
//
//        //loop through to insert into database
//        long newRowId;
//        int count=0;

//        for(int i = 0;i<99;i++) {
//
//            //item id from BE
//            contentValues.put(itemsSoldContractEntry.COLUMN_ITEM_ID, i);
//            //item name from BE
//            contentValues.put(itemsSoldContractEntry.COLUMN_ITEM_NAME_ENGLISH,"" +i);
//            //images put into corr items alternatively
//            if (count % 2 == 0) {
//                contentValues.put(itemsSoldContractEntry.COLUMN_IMAGE,R.string.image_onion_base64);
//                contentValues.put(itemsSoldContractEntry.COLUMN_CATEGORY,1);
//                contentValues.put(itemsSoldContractEntry.COLUMN_ITEM_NAME_HINDI,"भाजी");
//
//            }else{
//                contentValues.put(itemsSoldContractEntry.COLUMN_IMAGE,R.string.image_peas_base64+"");
//                contentValues.put(itemsSoldContractEntry.COLUMN_CATEGORY,2);
//                contentValues.put(itemsSoldContractEntry.COLUMN_ITEM_NAME_HINDI,"फल");
//            }
//
//            if(i%3==0){
//                contentValues.put(itemsSoldContractEntry.COLUMN_MEASURE,"Kg");
////                contentValues.put(itemsSoldContractEntry.COLUMN_SUBCATEGORY,3);
//
//            }else{
//                contentValues.put(itemsSoldContractEntry.COLUMN_MEASURE,"Boxes");
////                contentValues.put(itemsSoldContractEntry.COLUMN_SUBCATEGORY,i%3);
//            }
//            contentValues.put(itemsSoldContractEntry.COLUMN_UNIT_PRICE,randomGenerator.nextInt(100));
//            count+=1;
//
////            newRowId = db.insert(itemsSoldContractEntry.TABLE_NAME,null,contentValues);
////            Log.v("vegetable activity ","new row id :"+ newRowId);
//
//            //THIS WORKS
//            Uri newUri = getContentResolver().insert(itemsSoldContractEntry.CONTENT_URI_ITEMS_SOLD,contentValues);
//
//            Log.v("I dont know"," Let's find out : "+ newUri);
//
//        }

//URL for items list

