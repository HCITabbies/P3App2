package com.p3app2.database;

/**
 * Created by prashantanantharaman on 5/14/17.
 */

public class MessageEntry {
    public long _id;
    public String mId;
    public int sender;
    public String subject;
    public String body;

    public MessageEntry() {

    }

    public MessageEntry(String id, int sender, String sub, String body) {
        this.mId = id;
        this.sender = sender;
        this.subject = sub;
        this.body = body;
    }
}
