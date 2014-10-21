package com.technikumwien.mad.rssreader.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.technikumwien.mad.rssreader.R;
import com.technikumwien.mad.rssreader.rssutils.RssItem;

import java.util.List;

/**
 * Created by Alex on 06.10.2014.
 */

public class RssItemArrayAdapter extends ArrayAdapter<RssItem> {
    private static final String TAG = "RssItemArrayAdapter";

    public RssItemArrayAdapter(Context context, int resource, int textViewResourceId,
                               List<RssItem> objects) {
        super(context, resource, textViewResourceId, objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.rss_list_item, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.label = (TextView) convertView.findViewById(R.id.rss_item_title);
            convertView.setTag(holder);
        }
        ViewHolder views = (ViewHolder) convertView.getTag();
        RssItem item = getItem(position);
        views.label.setText(item.getTitle());
        convertView.setSelected(item.isSelected());
        views.label.setTextColor(item.isRead() ? Color.GRAY : Color.BLACK);
        views.label.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0,
                item.isStarred() ? R.drawable.heart32_black : 0, 0);
        return convertView;
    }

    private static final class ViewHolder{
        private TextView label;
        private ImageView image;
    }
}
