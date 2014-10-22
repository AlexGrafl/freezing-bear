package com.technikumwien.mad.rssreader.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.technikumwien.mad.rssreader.R;
import com.technikumwien.mad.rssreader.rssutils.RssItem;

import de.greenrobot.dao.query.LazyList;

/**
 * Created by Alex on 22.10.2014.
 */
public class RssItemLazyListAdapter extends LazyListAdapter<RssItem> {

    public RssItemLazyListAdapter(Context context, LazyList<RssItem> lazyList) {
        super(context, lazyList);
    }

    @Override
    public View newView(Context context, RssItem item, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.rss_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, RssItem item) {
        TextView label = (TextView) view.findViewById(R.id.rss_item_title);
        label.setText(item.getTitle());
        label.setTextColor(item.isRead() ? Color.GRAY : Color.BLACK);
        label.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0,
                item.isStarred() ? R.drawable.heart32_black : 0, 0);
    }
}
