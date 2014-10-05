package com.technikumwien.mad.rssreader;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Alex on 05.10.2014.
 */
public class AddFeedFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_feed_fragment, container, false);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if(menu != null){
            //FIXME: des geht ned
            menu.findItem(R.id.add_feed).setVisible(false);
        }
    }

    //TODO: Add feed url to DB and navigate back to list + refresh
}
