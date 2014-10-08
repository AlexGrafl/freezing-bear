package com.technikumwien.mad.rssreader.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.technikumwien.mad.rssreader.R;
import com.technikumwien.mad.rssreader.rssutils.RssFeed;
import com.technikumwien.mad.rssreader.rssutils.RssItem;
import com.technikumwien.mad.rssreader.adapters.RssItemArrayAdapter;
import com.technikumwien.mad.rssreader.services.ReadRssService;

import java.util.ArrayList;


/**
 * Created by Alex on 05.10.2014.
 */
public class ViewFeedFragment extends ListFragment {
    private static final String TAG = "ViewFeedFragment";
    private static final String CURRENT_FEED = "CurrentFeed";

    private RssItemArrayAdapter rssItemArrayAdapter;
    private RssFeed currentRssFeed;

    public static ViewFeedFragment newInstance(int index) {
        ViewFeedFragment f = new ViewFeedFragment();

        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);

        return f;
    }

    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (rssItemArrayAdapter == null){
            rssItemArrayAdapter = new RssItemArrayAdapter(getActivity(), 1,
                    1, new ArrayList<RssItem>());
            setListAdapter(rssItemArrayAdapter);
        }
        if((savedInstanceState != null)
                && (savedInstanceState.getParcelable(CURRENT_FEED) != null) ) {
            currentRssFeed = savedInstanceState.getParcelable(CURRENT_FEED);
            rssItemArrayAdapter.clear();
            rssItemArrayAdapter.addAll(currentRssFeed.getRssItems());
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(CURRENT_FEED, currentRssFeed);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        RssItem item = rssItemArrayAdapter.getItem(position);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getLink()));
        startActivity(intent);
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
        switch(item.getItemId()){
            case R.id.refresh_menu:
                Intent i = new Intent(getActivity(), ReadRssService.class);
                i.putExtra(ReadRssService.RSS_FEED_URL, currentRssFeed.getRssLink());
                i.putExtra(ReadRssService.RSS_READER_HANDLER, new Messenger(new RssReadHandler()));
                getActivity().startService(i);
                Log.i(TAG, "refresh pressed");
            default:
                return true;
        }
    }


    public RssReadHandler getRssReadHandler(){
        return new RssReadHandler();
    }


    public class RssReadHandler extends Handler {
        @Override
        public void handleMessage(Message msg){
            rssItemArrayAdapter.clear();
            currentRssFeed = (RssFeed) msg.obj;
            rssItemArrayAdapter.addAll(currentRssFeed.getRssItems());
            getActivity().setTitle(currentRssFeed.getTitle());
            Log.i(TAG, "List refreshed successfully, got " + currentRssFeed.getRssItems().size()
                    + " items.");
        }
    }
}
