package com.technikumwien.mad.rssreader;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import de.greenrobot.dao.*;
import de.greenrobot.dao.query.QueryBuilder;


import com.technikumwien.mad.rssreader.fragments.AddFeedFragment;
import com.technikumwien.mad.rssreader.fragments.ListFeedsFragment;
import com.technikumwien.mad.rssreader.greenDAO.DaoGeneratorImpl;
import com.technikumwien.mad.rssreader.greenDAO.DaoMaster;
import com.technikumwien.mad.rssreader.greenDAO.DaoSession;
import com.technikumwien.mad.rssreader.greenDAO.RssFeedDao;
import com.technikumwien.mad.rssreader.greenDAO.RssItemDao;
import com.technikumwien.mad.rssreader.rssutils.RssFeed;
import com.technikumwien.mad.rssreader.rssutils.RssItem;

import java.util.Date;
import java.util.List;


/**
 * Created by Alex on 05.10.2014.
 */
public class MainActivity extends Activity {
    private ListFeedsFragment listFeedsFragment;
    private boolean mDualPane;
    private DaoSession daoSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*** Test ***/
        //rssFeedDao is functional
        /*DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();

        daoSession.insert(rssFeed);

        QueryBuilder qb = daoSession.getRssFeedDao().queryBuilder();
        qb.where(RssFeedDao.Properties.Id.gt(0));
        List testList = qb.list();*/


        setupDatabase();

        RssFeedDao rssFeedDao = daoSession.getRssFeedDao();
        RssFeed rssFeed = new RssFeed(1, "www.test.com#rss", "title", "www.normallink.com", "testeditesttest", "EN");
        rssFeed.__setDaoSession(daoSession);

        RssItemDao rssItemDao = daoSession.getRssItemDao();
        RssItem rssItem = new RssItem(2, "testTitle", "www.link.at",new Date(), "testDescription", "this is content","2250518", 1, false, false);
        rssItem.setFeed(rssFeed);
        rssItem.__setDaoSession(daoSession);
        daoSession.insert(rssItem);
        QueryBuilder qb = daoSession.getRssItemDao().queryBuilder();
        qb.where(RssItemDao.Properties.Id.gt(0));
        List testList = qb.list();
        rssItem.update();

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

    private void setupDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "rssdb", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }
    public DaoSession getDaoSession() {
        return daoSession;
    }
}
