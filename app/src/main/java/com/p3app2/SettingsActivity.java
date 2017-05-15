package com.p3app2;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Kirti will handle this activity
 */

public class SettingsActivity extends PreferenceActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

}