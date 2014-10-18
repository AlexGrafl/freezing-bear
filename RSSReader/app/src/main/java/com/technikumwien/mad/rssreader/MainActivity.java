package com.technikumwien.mad.rssreader;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.technikumwien.mad.rssreader.fragments.AddFeedFragment;
import com.technikumwien.mad.rssreader.fragments.ListFeedsFragment;


/**
 * Created by Alex on 05.10.2014.
 */
public class MainActivity extends Activity {

    private ListFeedsFragment listFeedsFragment;
    private boolean mDualPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        listFeedsFragment = new ListFeedsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.list, listFeedsFragment);
        transaction.commit();

        View detailsFrame = findViewById(R.id.details);
        mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_feed_menu:
                AddFeedFragment addFeedFragment = new AddFeedFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if(mDualPane) {
                    transaction.replace(R.id.details, addFeedFragment);
                } else {
                    transaction.replace(R.id.list, addFeedFragment);
                    transaction.addToBackStack(null);
                }
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.commit();
                return true;
            default:
                return super.onMenuItemSelected(featureId, item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
