package com.technikumwien.mad.rssreader.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.technikumwien.mad.rssreader.R;

/**
 * Created by Alex on 05.10.2014.
 */
public class AddFeedFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.add_feed_fragment, container, false);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if(menu != null){
            menu.findItem(R.id.add_feed_menu).setVisible(false);
            menu.findItem(R.id.refresh_menu).setVisible(false);
        }
    }

    //TODO: Add feed url to DB and navigate back to list + refresh
}
