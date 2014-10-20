package com.technikumwien.mad.rssreader.greenDAO;

import android.database.sqlite.SQLiteDatabase;

import com.technikumwien.mad.rssreader.rssutils.RssFeed;
import com.technikumwien.mad.rssreader.rssutils.RssItem;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;


/**
 * {@inheritDoc}
 *
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig rssFeedDaoConfig;
    private final DaoConfig rssItemDaoConfig;

    private final RssFeedDao rssFeedDao;
    private final RssItemDao rssItemDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);


        rssFeedDaoConfig = daoConfigMap.get(RssFeedDao.class).clone();
        rssFeedDaoConfig.initIdentityScope(type);

        rssItemDaoConfig = daoConfigMap.get(RssItemDao.class).clone();
        rssItemDaoConfig.initIdentityScope(type);

        rssFeedDao = new RssFeedDao(rssFeedDaoConfig, this);
        rssItemDao = new RssItemDao(rssItemDaoConfig, this);

        registerDao(RssFeed.class, rssFeedDao);
        registerDao(RssItem.class, rssItemDao);
    }

    public void clear() {
        rssFeedDaoConfig.getIdentityScope().clear();
        rssItemDaoConfig.getIdentityScope().clear();
    }


    public RssFeedDao getRssFeedDao() {
        return rssFeedDao;
    }

    public RssItemDao getRssItemDao() {
        return rssItemDao;
    }

}