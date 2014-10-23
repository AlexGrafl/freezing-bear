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
        View v = inflater.inflate(R.layout.rss_list_item, parent, false);
        RssFeedViewHolder viewHolder = new RssFeedViewHolder();
        viewHolder.title = (TextView) v.findViewById(R.id.rss_item_title);
        v.setTag(viewHolder);
        return v;
    }

    @Override
    public void bindView(View view, Context context, RssFeed item) {
        RssFeedViewHolder viewHolder = (RssFeedViewHolder) view.getTag();
        viewHolder.title.setText(item.getTitle());
    }

    public class RssFeedViewHolder{
        TextView title;
    }
}
