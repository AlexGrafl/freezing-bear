package com.technikumwien.mad.rssreader.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.technikumwien.mad.rssreader.MainActivity;
import com.technikumwien.mad.rssreader.R;
import com.technikumwien.mad.rssreader.rssutils.RssFeed;
import com.technikumwien.mad.rssreader.services.ReadRssService;


/**
 * Created by Alex on 05.10.2014.
 */
public class AddFeedFragment extends Fragment {
    private EditText url;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        View v = inflater.inflate(R.layout.add_feed_fragment, container, false);
        url = (EditText) v.findViewById(R.id.add_feed_url);
        progressDialog = new ProgressDialog(v.getContext());
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        Button button = (Button) v.findViewById(R.id.add_feed_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ReadRssService.class);
                i.putExtra(ReadRssService.RSS_FEED_URL, url.getText().toString());
                i.putExtra(ReadRssService.RSS_READER_HANDLER, new Messenger(new SubscribeFeedHandler()));
                getActivity().startService(i);
                progressDialog.show();
            }
        });
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if(menu != null){
            menu.findItem(R.id.add_feed_menu).setVisible(false);
            menu.findItem(R.id.refresh_menu).setVisible(false);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        return false;
    }

    public class SubscribeFeedHandler extends Handler {
        @Override
        public void handleMessage(Message msg){
            progressDialog.hide();
            if(msg.obj == null){
                url.setError("Invalid feed!");
                return;
            }
            RssFeed rssFeed = (RssFeed) msg.obj;
            ((MainActivity)getActivity())
                    .getDaoSession().getRssFeedDao().insert(rssFeed);
            ((MainActivity)getActivity())
                    .getDaoSession().getRssItemDao().insertInTx(rssFeed.getRssItems());
            getActivity().onBackPressed();

        }
    }
}
