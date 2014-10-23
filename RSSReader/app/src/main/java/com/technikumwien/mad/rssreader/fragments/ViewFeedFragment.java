package com.technikumwien.mad.rssreader.fragments;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.technikumwien.mad.rssreader.MainActivity;
import com.technikumwien.mad.rssreader.R;
import com.technikumwien.mad.rssreader.adapters.RssItemLazyListAdapter;
import com.technikumwien.mad.rssreader.greenDAO.RssItemDao;
import com.technikumwien.mad.rssreader.rssutils.RssFeed;
import com.technikumwien.mad.rssreader.rssutils.RssItem;
import com.technikumwien.mad.rssreader.adapters.RssItemArrayAdapter;
import com.technikumwien.mad.rssreader.services.ReadRssService;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.LazyList;


/**
 * Created by Alex on 05.10.2014.
 */
public class ViewFeedFragment extends ListFragment implements AbsListView.MultiChoiceModeListener{
    private static final String TAG = "ViewFeedFragment";
    private static final String CURRENT_FEED = "CurrentFeed";

    private boolean actionMode = false;

    private RssItemLazyListAdapter adapter;
    private RssFeed currentRssFeed;

    private ProgressDialog progressDialog;

    public static ViewFeedFragment newInstance(int index, RssFeed feed) {
        ViewFeedFragment f = new ViewFeedFragment();

        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);
        f.currentRssFeed = feed;

        return f;
    }

    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (adapter == null){
            adapter = new RssItemLazyListAdapter(getActivity(), getLazyList());
            setListAdapter(adapter);
        }
        if((savedInstanceState != null)
                && (savedInstanceState.getParcelable(CURRENT_FEED) != null) ) {
            currentRssFeed = savedInstanceState.getParcelable(CURRENT_FEED);
        }
        setHasOptionsMenu(true);
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(actionMode) return false;
                actionMode = true;
                getActivity().startActionMode(ViewFeedFragment.this);
                view.setSelected(true);
                return true;
            }
        });
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        getListView().setMultiChoiceModeListener(this);

        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        getActivity().setTitle(currentRssFeed.getTitle());

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    private LazyList<RssItem> getLazyList() {
        return ((MainActivity) getActivity()).getDaoSession()
                .getRssItemDao().queryBuilder()
                .where(RssItemDao.Properties.rssFeedId.eq(currentRssFeed.getId()))
                .orderDesc(RssItemDao.Properties.pubDate)
                .listLazy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(CURRENT_FEED, currentRssFeed);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        RssItem item = adapter.getItem(position);
        item.setRead(true);
        ((MainActivity) getActivity())
                .getDaoSession().getRssItemDao().insertOrReplace(item);
        adapter.notifyDataSetChanged();
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
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            case R.id.refresh_menu:
                Intent i = new Intent(getActivity(), ReadRssService.class);
                i.putExtra(ReadRssService.RSS_FEED_URL, currentRssFeed.getRssLink());
                i.putExtra(ReadRssService.RSS_READER_HANDLER, new Messenger(new RssReadHandler()));
                getActivity().startService(i);
                progressDialog.show();
                Log.i(TAG, "refresh pressed");
            default:
                return true;
        }
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.cab_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()){
            case R.id.mark_read:
            case R.id.mark_unread:
            case R.id.mark_starred:
                doContextAction(item.getItemId());
                mode.finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        actionMode = false;
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

    }


    public class RssReadHandler extends Handler {
        @Override
        public void handleMessage(Message msg){
            if(msg.obj == null) return;
            RssFeed rssFeed = (RssFeed) msg.obj;
            updateFeedEntries(rssFeed);
            adapter.notifyDataSetChanged();
            progressDialog.hide();
            Log.i(TAG, "List refreshed successfully.");
        }
    }

    private void updateFeedEntries(RssFeed rssFeed) {
        for(RssItem item : rssFeed.getRssItems()) {
            try {
                item.setFeed(currentRssFeed);

                ((MainActivity) getActivity())
                        .getDaoSession().getRssItemDao().insert(item);
            }catch(SQLiteConstraintException ignore){
            }
        }
        adapter.updateLazyList(getLazyList());
    }

    private void doContextAction(int id){
        SparseBooleanArray checked = getListView().getCheckedItemPositions();
        for (int i = 0; i < checked.size(); i++) {
            final int index = checked.keyAt(i);
            switch (id){
                case R.id.mark_read:
                    adapter.getItem(index).setRead(true);
                    break;
                case R.id.mark_unread:
                    adapter.getItem(index).setRead(false);
                    break;
                case R.id.mark_starred:
                    adapter.getItem(index).setStarred(true);
                    break;
            }
            ((MainActivity) getActivity())
                    .getDaoSession().getRssItemDao().insertOrReplace(adapter.getItem(index));
        }
        adapter.notifyDataSetChanged();
    }
}
