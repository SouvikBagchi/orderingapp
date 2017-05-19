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


public class FruitsFragment extends Fragment implements android.app.LoaderManager.LoaderCallbacks<Cursor>{


    //For loader callback
    //Unique arbitrary constant
    private static final int DATA_LOADER = 1;
    //create a cursor adapter object
    FruitsFragmentCursorAdapter mFruitsFragmentCursorAdapter;

    public FruitsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_fruit, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstance){
        super.onActivityCreated(savedInstance);

        //Find the listview you want to set it on
        ListView listView = (ListView) getView().findViewById(R.id.listview_fruit_frag);

        //Setup the adapter to create a list view, so that it only shows when the list has 0 items
        //Find the empty view and set it up when there is no data
        View emptyView = getView().findViewById(R.id.empty_view_fragment_fruit);
        listView.setEmptyView(emptyView);

        //There is data yet so pass in null till the cursor is loading
        mFruitsFragmentCursorAdapter = new FruitsFragmentCursorAdapter(this.getActivity(),null);
        listView.setAdapter(mFruitsFragmentCursorAdapter);

        //Kick off the loader
        getActivity().getLoaderManager().initLoader(DATA_LOADER,null,this);

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
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
                itemsSoldContractEntry.COLUMN_ITEM_ID,
                itemsSoldContractEntry.COLUMN_INCREMENT

        };

        String selection = "CATEGORY = ?";
        String[] selectionArgs ={"2"};
        return new CursorLoader(this.getContext(),itemsSoldContractEntry.CONTENT_URI_ITEMS_SOLD,projection,selection,selectionArgs,null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mFruitsFragmentCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {



    }





    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
