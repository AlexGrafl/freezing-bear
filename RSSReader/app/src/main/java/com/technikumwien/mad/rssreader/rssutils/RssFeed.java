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

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.technikumwien.mad.rssreader.adapters.DomainObject;
import com.technikumwien.mad.rssreader.greenDAO.DaoSession;
import com.technikumwien.mad.rssreader.greenDAO.RssFeedDao;
import com.technikumwien.mad.rssreader.greenDAO.RssItemDao;

import de.greenrobot.dao.DaoException;

public class RssFeed extends DomainObject implements Parcelable {


    private long id;
    private String rssLink;
    private String title;
    private String link;
    private String description;
    private String language;
    private ArrayList<RssItem> rssItems;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient RssFeedDao myDao;

    public RssFeed() {
        rssItems = new ArrayList<>();
    }

    public RssFeed(String rssLink, String title) {
        this.rssLink = rssLink;
        this.title = title;
    }
    public RssFeed(long id, String rssLink, String title, String link, String description, String language){
        this.id = id;
        this.rssLink = rssLink;
        this.title = title;
        this.link = link;
        this.description = description;
        this.language = language;
    }

    public RssFeed(Parcel source) {

        Bundle data = source.readBundle();
        rssLink = data.getString("rssLink");
        title = data.getString("title");
        link = data.getString("link");
        description = data.getString("description");
        language = data.getString("language");
        rssItems = data.getParcelableArrayList("rssItems");

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        Bundle data = new Bundle();
        data.putString("rssLink", rssLink);
        data.putString("title", title);
        data.putString("link", link);
        data.putString("description", description);
        data.putString("language", language);
        data.putParcelableArrayList("rssItems", rssItems);
        dest.writeBundle(data);
    }

    public static final Parcelable.Creator<RssFeed> CREATOR = new Parcelable.Creator<RssFeed>() {
        public RssFeed createFromParcel(Parcel data) {
            return new RssFeed(data);
        }
        public RssFeed[] newArray(int size) {
            return new RssFeed[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    void addRssItem(RssItem rssItem) {
        rssItems.add(rssItem);
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public ArrayList<RssItem> getRssItems() {
        return rssItems;
    }

    public void setRssItems(ArrayList<RssItem> rssItems) {
        this.rssItems = rssItems;
    }

    public String getRssLink() {
        return rssLink;
    }

    public void setRssLink(String rssLink) {
        this.rssLink = rssLink;
    }


    /*** following Methods are essential for GreenDao: ***/

    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRssFeedDao() : null;
    }
    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public ArrayList<RssItem> getOrders() {
        if (rssItems == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RssItemDao targetDao = daoSession.getRssItemDao();
            ArrayList<RssItem> rssItemsNew = targetDao._queryRssFeed_Orders(id);
            synchronized (this) {
                if(rssItems == null) {
                    rssItems = rssItemsNew;
                }
            }
        }
        return rssItems;
    }

    /* Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetOrders() {
        rssItems = null;
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