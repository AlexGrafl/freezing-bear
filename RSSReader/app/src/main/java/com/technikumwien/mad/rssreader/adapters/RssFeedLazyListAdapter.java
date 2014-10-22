package com.technikumwien.mad.rssreader.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.technikumwien.mad.rssreader.R;
import com.technikumwien.mad.rssreader.rssutils.RssFeed;

import de.greenrobot.dao.query.LazyList;

/**
 * Created by Alex on 22.10.2014.
 */
public class RssFeedLazyListAdapter extends LazyListAdapter<RssFeed> {

    public RssFeedLazyListAdapter(Context context, LazyList<RssFeed> lazyList) {
        super(context, lazyList);
    }

    @Override
    public View newView(Context context, RssFeed item, ViewGroup parent) {
        if(item == null) return null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.rss_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, RssFeed item) {
        TextView textView = (TextView) view.findViewById(R.id.rss_item_title);
        textView.setText(item.getTitle());
    }
}
