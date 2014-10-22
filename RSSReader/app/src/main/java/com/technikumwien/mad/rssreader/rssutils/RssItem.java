/*
 * Copyright (C) 2011 Mats Hofman <http://matshofman.nl/contact/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.technikumwien.mad.rssreader.rssutils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.technikumwien.mad.rssreader.greenDAO.DaoSession;
import com.technikumwien.mad.rssreader.greenDAO.RssFeedDao;
import com.technikumwien.mad.rssreader.greenDAO.RssItemDao;

import de.greenrobot.dao.DaoException;

public class RssItem implements Comparable<RssItem>, Parcelable {


    private long id;
    private String title;
    private String link;
    private Date pubDate;
    private String description;
    private String content;
    private String usid;
    private boolean read;
    private boolean starred;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient RssItemDao myDao;

    private RssFeed rssFeed;
    private Long rssFeed__resolvedKey;

    public RssItem() {

    }

    public RssItem(long id, String title, String link,
                   Date pubDate, String description, String content, String usid,
                   long rssFeed__resolvedKey, boolean read, boolean starred) {

        this.id = id;
        this.title = title;
        this.link = link;
        this.pubDate = pubDate;
        this.description = description;
        this.content = content;
        this.usid = usid;
        this.rssFeed__resolvedKey = rssFeed__resolvedKey;
        this.read = read;
        this.starred = starred;
    }


    public RssItem(Parcel source) {

        Bundle data = source.readBundle();
        title = data.getString("title");
        link = data.getString("link");
        pubDate = (Date) data.getSerializable("pubDate");
        description = data.getString("description");
        content = data.getString("content");
        rssFeed = data.getParcelable("feed");

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        Bundle data = new Bundle();
        data.putString("title", title);
        data.putString("link", link);
        data.putSerializable("pubDate", pubDate);
        data.putString("description", description);
        data.putString("content", content);
        dest.writeBundle(data);
    }

    public static final Parcelable.Creator<RssItem> CREATOR = new Parcelable.Creator<RssItem>() {
        public RssItem createFromParcel(Parcel data) {
            return new RssItem(data);
        }
        public RssItem[] newArray(int size) {
            return new RssItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


   /*
   public RssFeed getFeed() {
        return feed;
    }

    public void setFeed(RssFeed feed) {
        this.feed = feed;
    }
    */
   public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getUsid() {
        return usid;
    }

    public void setUsid(String usid) {
        this.usid = usid;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public void setPubDate(String pubDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
            this.pubDate = dateFormat.parse(pubDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }

    public boolean isRead() {

        return read;
    }

    public boolean isStarred() {
        return starred;
    }

    @Override
    public int compareTo(RssItem another) {
        if(getPubDate() != null && another.getPubDate() != null) {
            return getPubDate().compareTo(another.getPubDate());
        } else {
            return 0;
        }
    }

    public void setRssFeed__resolvedKey(Long rssFeed__resolvedKey) {
        this.rssFeed__resolvedKey = rssFeed__resolvedKey;
    }

    public Long getRssFeed__resolvedKey() {

        return rssFeed__resolvedKey;
    }
    /* ---------------------------- GreenDao Methods ---------------------------- */
    /* To-one relationship, resolved on first access. */
    public RssFeed getFeed() {
//        if(this.feed != null) {

            //TODO: Recursive Call: - fix it!
            long __key = this.getFeed().getId();
            if (rssFeed__resolvedKey == null || !rssFeed__resolvedKey.equals(__key)) {
                if (daoSession == null) {
                    throw new DaoException("Entity is detached from DAO context");
                }
                RssFeedDao targetDao = daoSession.getRssFeedDao();
                RssFeed rssFeedNew = targetDao.load(__key);
                synchronized (this) {
                    rssFeed = rssFeedNew;
                    rssFeed__resolvedKey = __key;
                }
            }
  //      }
        return rssFeed;
    }

    public void setFeed(RssFeed rssFeed) {
        if (rssFeed == null) {
            throw new DaoException("To-one property 'customerId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.rssFeed = rssFeed;
            rssFeed__resolvedKey = rssFeed.getId();
        }
    }

    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRssItemDao() : null;
    }

    /* Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /* Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /* Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

}
