package com.technikumwien.mad.rssreader.greenDAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.technikumwien.mad.rssreader.rssutils.RssFeed;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * DAO for table RssFeed.
 */
public class RssFeedDao extends AbstractDao<RssFeed, Long> {

    public static final String TABLENAME = "RSSFEED";

    /**
     * Properties of entity RssFeed.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {

       //TODO: private ArrayList<RssItem> rssItems; ???

        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property rssLink = new Property(1, String.class, "rsslink", false, "RSSLINK");
        public final static Property title = new Property(2, String.class, "title", false, "TITLE");
        public final static Property link = new Property(3, String.class, "link", false, "LINK");
        public final static Property description = new Property(4, String.class, "description", false, "DESCRIPTION");
        public final static Property language = new Property(5, String.class, "language", false, "LANGUAGE");
    };

    private DaoSession daoSession;


    public RssFeedDao(DaoConfig config) {
        super(config);
    }

    public RssFeedDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    //TODO: use TABLENAME for query
    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'RSSFEED' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'RSSLINK' TEXT NOT NULL ," + // 1: rssLink
                "'TITLE' TEXT NOT NULL ," +
                "'LINK' TEXT ," +
                "'DESCRIPTION' TEXT ," +
                "'LANGUAGE' TEXT ," +
                ");");
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'RSSFEED'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, RssFeed entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        stmt.bindString(2, entity.getRssLink());
        stmt.bindString(3, entity.getTitle());
        stmt.bindString(4, entity.getLink());
        stmt.bindString(5, entity.getDescription());
        stmt.bindString(6, entity.getLanguage());
    }

    @Override
    protected void attachEntity(RssFeed entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /** @inheritdoc */
    @Override
    public RssFeed readEntity(Cursor cursor, int offset) {
        RssFeed entity = new RssFeed( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.getString(offset + 1), //rssLink
                cursor.getString(offset + 2), //title
                cursor.getString(offset + 3), //link
                cursor.getString(offset + 4), //description
                cursor.getString(offset + 5) // Language
                );
        return entity;
    }

    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, RssFeed entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setRssLink(cursor.getString(offset + 1));
        entity.setTitle(cursor.getString(offset + 2));
        entity.setLink(cursor.getString(offset + 3));
        entity.setDescription(cursor.getString(offset + 4));
        entity.setLanguage(cursor.getString(offset + 5));
    }

    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(RssFeed entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /** @inheritdoc */
    @Override
    public Long getKey(RssFeed entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }

}
