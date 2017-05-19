package com.freshleafy.freshleafy;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.freshleafy.freshleafy.data.ItemsSoldContract.itemsSoldContractEntry;


public class VegetableFragment extends Fragment implements android.app.LoaderManager.LoaderCallbacks<Cursor>{

    //For loader callback
    //Unique arbitrary constant
    private static final int DATA_LOADER = 0;
    //create a cursor adapter object
    VegetableFragmentCursorAdapter mVegetableFragmentCursorAdapter;
    public VegetableFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_list_vegetable, container, false);

        //Create the view
        View listView = inflater.inflate(R.layout.fragment_list_vegetable,container,false);
        return listView;

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        //Find the listView you want to set it up on
        ListView listView = (ListView) getView().findViewById(R.id.listview_vegetable_frag);

        //Setup the adapter to create a list view, so that it only shows when the list has 0 items
        //Find the empty view and set it up when there is no data
        View emptyView = getView().findViewById(R.id.vegetable_fragment_empty_view);
        listView.setEmptyView(emptyView);


        //There is data yet so pass in null till the cursor is loading
        mVegetableFragmentCursorAdapter = new VegetableFragmentCursorAdapter(this.getActivity(), null);
        listView.setAdapter(mVegetableFragmentCursorAdapter);

        //Kick off the loader
        getActivity().getLoaderManager().initLoader(DATA_LOADER,null,this);



    }

    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
       super.onDetach();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                itemsSoldContractEntry._ID,
                itemsSoldContractEntry.COLUMN_CATEGORY,
                itemsSoldContractEntry.COLUMN_ITEM_NAME_ENGLISH,
                itemsSoldContractEntry.COLUMN_ITEM_NAME_HINDI,
                itemsSoldContractEntry.COLUMN_IMAGE,
                itemsSoldContractEntry.COLUMN_QUANTITY,
                itemsSoldContractEntry.COLUMN_MEASURE,
                itemsSoldContractEntry.COLUMN_UNIT_PRICE,
                itemsSoldContractEntry.COLUMN_INCREMENT
        };

        String selection = "CATEGORY = ?";
        String[] selectionArgs ={"1"};
        return new CursorLoader(this.getContext(),itemsSoldContractEntry.CONTENT_URI_ITEMS_SOLD,projection,selection,selectionArgs,null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        mVegetableFragmentCursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mVegetableFragmentCursorAdapter.swapCursor(null);

    }
}
