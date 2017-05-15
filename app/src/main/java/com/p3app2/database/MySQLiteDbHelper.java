package com.p3app2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by prashantanantharaman on 5/14/17.
 */


import java.util.ArrayList;
import java.util.List;

    public class MySQLiteDbHelper extends SQLiteOpenHelper {
        // Database name string
        public static final String DATABASE_NAME = "Database_Dickshouse";
        // Table schema, column names
        public static final String KEY_ROWID = "_id";    // RowID that is auto generated from the DB.
        public static final String KEY_ID = "id";       // ID generated from the web server.
        public static final String KEY_SENDER = "sender";   // URL.
        public static final String KEY_SUBJECT = "subject"; // Title of the Link.
        public static final String KEY_BODY = "body"; //public, private entries.
        // Table name string. (Only one table)
        private static final String TABLE_NAME_ENTRIES = "Dickshouse_Database_Table_Messages";
        // Version code
        private static final int DATABASE_VERSION = 3;

        // SQL query to create the table for the first time
        // Data types are defined below
        private static final String CREATE_TABLE_ENTRIES = "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME_ENTRIES
                + " ("
                + KEY_ROWID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_ID
                + " TEXT UNIQUE, "
                + KEY_SENDER
                + " INTEGER, "
                + KEY_SUBJECT
                + " TEXT, "
                + KEY_BODY
                + " TEXT"
                + ");";
        private static final String[] mColumnList = new String[]{KEY_ROWID,
                KEY_ID, KEY_SENDER, KEY_SUBJECT, KEY_BODY};
        private static MySQLiteDbHelper sInstance;

        //Used on one object at any instance for database operations. Used Singleton Design pattern.
        public MySQLiteDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            Log.d("DB created", "Database_Kitabu");
        }

        public static synchronized MySQLiteDbHelper getInstance(Context context) {
            // Using the application context, which will ensure that you
            // don't accidentally leak an Activity's context.
            if (sInstance == null) {
                sInstance = new MySQLiteDbHelper(context.getApplicationContext());
            }
            return sInstance;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // create the table schema.
            db.execSQL(CREATE_TABLE_ENTRIES);
            Log.d("Database", "Table Created");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Upgrade the database if the doesn't exist.
            Log.w(MySQLiteDbHelper.class.getName(),
                    "Upgrading database from version " + oldVersion + " to "
                            + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ENTRIES);
            onCreate(db);
        }

        public long insertEntry(MessageEntry entry)
        {
            ContentValues value = new ContentValues();
            // put values in a ContentValues object
            value.put(KEY_ID, entry.mId);
            value.put(KEY_SENDER, entry.sender);
            value.put(KEY_SUBJECT, entry.subject);
            value.put(KEY_BODY, entry.body);
            // get a database object
            SQLiteDatabase dbObj = getWritableDatabase();
            long id;
            // insert the record
            id = dbObj.insert(TABLE_NAME_ENTRIES, null, value);
            dbObj.close();
            // return the primary key for the new record
            return id;
        }

        public ArrayList<MessageEntry> fetchEntries() {
            SQLiteDatabase dbObj = getReadableDatabase();
            // store all the entries to an ArrayList
            ArrayList<MessageEntry> entryList = new ArrayList<>();
            // do the query without any condition. it retrieves every record from
            // the database
            Cursor cursor = dbObj.query(TABLE_NAME_ENTRIES, mColumnList, null,
                    null, null, null, null);
            // the cursor initially points the record PRIOR to the first record
            // use the while loop to read every record from the cursor
            while (cursor.moveToNext()) {
                MessageEntry entry = cursorToEntry(cursor, false);
                entryList.add(entry);
            }
            cursor.close();
            dbObj.close();
            Log.d("Total", String.valueOf(entryList.size()));
            return entryList;
        }

        private MessageEntry cursorToEntry(Cursor cursor, boolean needGps) {
            MessageEntry entry = new MessageEntry();
            entry._id = cursor.getLong(cursor.getColumnIndex(KEY_ROWID));
            entry.mId = cursor.getString(cursor.getColumnIndex(KEY_ID));
            entry.sender = cursor.getInt(cursor.getColumnIndex(KEY_SENDER));
            entry.subject = cursor.getString(cursor.getColumnIndex(KEY_SUBJECT));
            entry.body = cursor.getString(cursor.getColumnIndex(KEY_BODY));
            return entry;
        }
}
