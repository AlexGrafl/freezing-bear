package com.technikumwien.mad.rssreader.fragments;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Messenger;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.technikumwien.mad.rssreader.MainActivity;
import com.technikumwien.mad.rssreader.R;
import com.technikumwien.mad.rssreader.adapters.RssFeedLazyListAdapter;
import com.technikumwien.mad.rssreader.rssutils.RssFeed;
import com.technikumwien.mad.rssreader.services.ReadRssService;

import de.greenrobot.dao.query.LazyList;

/**
 * Created by Alex on 05.10.2014.
 */
public class ListFeedsFragment extends ListFragment {
    //TODO: get rid of this
    private static final String TEMP_URL = "http://rss.orf.at/news.xml";

    boolean mDualPane;
    int mCurCheckPosition = 0;
    private RssFeedLazyListAdapter adapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LazyList<RssFeed> list = ((MainActivity) getActivity()).getDaoSession()
                .getRssFeedDao().queryBuilder().listLazy();
        // Populate list with our static array of titles.
        adapter = new RssFeedLazyListAdapter(getActivity(), list);
        setListAdapter(adapter);

        // Check to see if we have a frame in which to embed the details
        // fragment directly in the containing UI.
        View detailsFrame = getActivity().findViewById(R.id.details);
        mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

        if (mDualPane) {
            // In dual-pane mode, the list view highlights the selected item.
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            // Make sure our UI is in the correct state.
            showFeed(mCurCheckPosition);
        }
        getActivity().setTitle(R.string.title_activity_main);
        setHasOptionsMenu(true);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu items for use in the action bar
        super.onCreateOptionsMenu(menu, inflater);
        if(menu.findItem(R.id.add_feed_menu) != null){
            menu.findItem(R.id.add_feed_menu).setVisible(true);
            menu.findItem(R.id.refresh_menu).setVisible(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.refresh_menu:
                //TODO: REFRESH LISTS
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showFeed(position);
    }

    void showFeed(int index) {
        mCurCheckPosition = index;


        // We can display everything in-place with fragments, so update
        // the list to highlight the selected item and show the data.
        getListView().setItemChecked(index, true);
        RssFeed feed = adapter.getItem(mCurCheckPosition);
        // Check what fragment is currently shown, replace if needed.
        ViewFeedFragment viewFeed = (ViewFeedFragment)
                getFragmentManager().findFragmentById(R.id.details);
        if (viewFeed == null || viewFeed.getShownIndex() != index) {
            // Make new fragment to show this selection.
            viewFeed = ViewFeedFragment.newInstance(index, feed);

            // Execute a transaction, replacing any existing fragment
            // with this one inside the frame.
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (mDualPane) {
                    ft.replace(R.id.details, viewFeed);
                } else {
                    ft.replace(R.id.list, viewFeed);
                    ft.addToBackStack(null);
                }
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();

        }
    }



}
