package com.p3app2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {

    /* Navigation Drawer Objects */
    private String[] m_option_titles = {"Home", "Chat", "Settings"};
    private DrawerLayout m_drawer_layout;
    private ListView m_drawer_list;

    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle = "Navigation Drawer";
    private CharSequence mTitle = "Home Screen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        // TODO: why is this deprecated? jk I fixed
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

        // Highlight the selected item, update the title, and close the drawer
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
