package com.technikumwien.mad.rssreader.greenDAO;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class DaoGeneratorImpl {
    public DaoGeneratorImpl() throws Exception {
        Schema schema = new Schema(1000, "com.technikumwien.mad.rssreader.greenDAO");
        addCustomerOrder(schema);
        new DaoGenerator().generateAll(schema, "./src-gen");
    }

    private static void addCustomerOrder(Schema schema) {
        Entity rssFeed = schema.addEntity("RssFeed");
        rssFeed.addIdProperty();
        rssFeed.addStringProperty("rsslink").notNull();
        rssFeed.addStringProperty("title").notNull();
        rssFeed.addStringProperty("link");
        rssFeed.addStringProperty("description");
        rssFeed.addStringProperty("language");


        Entity rssItem = schema.addEntity("RssItem");
        rssItem.addIdProperty();
        rssItem.addStringProperty("title");
        rssItem.addStringProperty("link");
        Property pubDate = rssItem.addDateProperty("pubDate").getProperty();
        rssItem.addStringProperty("description");
        rssItem.addStringProperty("content");
        rssItem.addStringProperty("usid");
        Property rssFeedId = rssItem.addLongProperty("customerId").notNull().getProperty();
        rssItem.addBooleanProperty("read");
        rssItem.addBooleanProperty("starred");

        rssItem.addToOne(rssFeed, rssFeedId);

        ToMany rssFeedToRssItems = rssFeed.addToMany(rssItem, rssFeedId);
        rssFeedToRssItems.setName("rssItems");
        rssFeedToRssItems.orderAsc(pubDate);
    }

}