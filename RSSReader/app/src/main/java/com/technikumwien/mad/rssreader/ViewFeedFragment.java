package com.technikumwien.mad.rssreader;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;

/**
 * Created by Alex on 05.10.2014.
 */
public class ViewFeedFragment extends ListFragment {

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

        //TODO: Call ws and get feed entries!
        setListAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_activated_1, new String[]{"x", "y", "z"}));


    }
}
