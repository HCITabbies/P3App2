package com.p3app2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Connection;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.p3app2.database.MessageEntry;
import com.p3app2.database.MySQLiteDbHelper;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import static java.security.AccessController.getContext;

public class WelcomeActivity extends AppCompatActivity {

    /* Navigation Drawer Objects */
    private String[] m_option_titles = {"Home", "Chat", "Settings"};
    private DrawerLayout m_drawer_layout;
    private ListView m_drawer_list;

    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle = "Navigation Drawer";
    private CharSequence mTitle = "Home Screen";

    class DatabaseAsyncTask extends AsyncTask
    {

        @Override
        protected Object doInBackground(Object[] params) {
            MySQLiteDbHelper helper = MySQLiteDbHelper.getInstance(getApplicationContext());
            ArrayList<MessageEntry> values = helper.fetchEntries();
            MessageEntry entry = new MessageEntry(1, 0, "abcd", "adsfsfsd");
            long res = helper.insertEntry(entry);
            ArrayList<MessageEntry> values1 = helper.fetchEntries();
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        new DatabaseAsyncTask();
        XMPPConnections xmppConnections = new XMPPConnections();
        try {
            XMPPConnections.sendMessage("does this work?");
        }
        catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        /* Initializing Navigation Drawer */
        m_drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        m_drawer_list = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        m_drawer_list.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, m_option_titles));
        // Set the list's click listener
        m_drawer_list.setOnItemClickListener(new DrawerItemClickListener());

        /* nav drawer shadow when opened */
        m_drawer_layout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);


        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(this, m_drawer_layout, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        m_drawer_layout.addDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }

    }

    /**
     * react to the user tapping/selecting an options menu item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
//        // Insert the fragment by replacing any existing fragment
//        FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.content_frame, fragment)
//                .commit()

        // Highlight the selected item, update the astitle, and close the drawer
        m_drawer_list.setItemChecked(position, true);
        //setTitle(m_option_titles[position]);
        m_drawer_layout.closeDrawer(m_drawer_list);

        switch(position) {
            case (1):
                m_drawer_list.setItemChecked(0, true);
                Intent chat_intent = new Intent(this, ChatActivity.class);
                startActivity(chat_intent);
                return;
            case (2):
                m_drawer_list.setItemChecked(0, true);
                Intent settings_intent = new Intent(this, SettingsActivity.class);
                startActivity(settings_intent);
                return;
            default:
                return;
        }



    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

}

