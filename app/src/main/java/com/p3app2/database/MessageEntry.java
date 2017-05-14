package com.p3app2.database;

/**
 * Created by prashantanantharaman on 5/14/17.
 */

public class MessageEntry {
    int mId;
    int sender;
    String subject;
    String body;

    MessageEntry(int id, int sender, String sub, String body)
    {
        this.mId = id;
        this.sender = sender;
        this.subject = sub;
        this.body = body;
    }
}
