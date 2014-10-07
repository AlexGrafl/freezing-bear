package com.technikumwien.mad.rssreader.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.technikumwien.mad.rssreader.rssutils.RssItem;
import com.technikumwien.mad.rssreader.rssutils.RssReader;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class ReadRssService extends Service {
    private static final String TAG = "ReadRssService";

    public static final String RSS_FEED_URL = "RSS_FEED_URL";
    public static final String RSS_READER_HANDLER = "RSS_READER_HANDLER";
    private Messenger messenger;

    public ReadRssService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        //do not bind this!
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();
        String rssFeedUrl = extras.getString(RSS_FEED_URL);
        messenger = extras.getParcelable(RSS_READER_HANDLER);
        readRssFeed(rssFeedUrl);
        return START_NOT_STICKY;
    }

    private void readRssFeed(final String rssFeedUrl){
        Thread t = new Thread(){
            @Override
            public void run(){
                try {
                    Message msg = new Message();
                    msg.obj = RssReader.read(new URL(rssFeedUrl));
                    messenger.send(msg);
                    stopSelf();
                } catch (SAXException | RemoteException |IOException e) {
                    Log.e(TAG, "Error reading RSS feed from " + rssFeedUrl, e);
                }
            }
        };
        t.start();
    }
}
