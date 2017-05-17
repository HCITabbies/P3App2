package com.p3app2;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.WindowManager;

/**
 * Kirti will handle this activity
 */

public class SettingsFragment extends PreferenceFragment{
    public static final String KEY_ANON = "setting_anonymous";
    public static final String KEY_UNAME = "setting_username";
    public static final String KEY_MUTE = "setting_mute";
    public static final String KEY_SCREENSHOT= "setting_screenshot";
    public static final String KEY_DELETE = "setting_delete";
    public static final String KEY_APPVERSION = "setting_appversion";
    public static final String KEY_FAQ = "faq_preference";
    public static final String KEY_CONTACT = "contact_preference";
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1234;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Dickshouse", Context.MODE_PRIVATE);
        // Anon cust
        Preference anon = findPreference(KEY_ANON);
        customizeAnon(anon);

        // Uname customization
        Preference uname = findPreference(KEY_UNAME);
        customizeUname(uname);

        // FAQ Customizations
        Preference faq = findPreference(KEY_FAQ);
//        customizeFaq(faq);

        // Contact customization
        Preference contact = findPreference(KEY_CONTACT);
        customizeContact(contact);

        //Notification mute
        Preference mute = findPreference(KEY_MUTE);
        customizeMute(mute);

        //Screenshot custom
        Preference ss = findPreference(KEY_SCREENSHOT);
        customizeScreenshot(ss);
    }

    public void customizeUname(final Preference uname) {
        final SharedPreferences sharedPreferences3 = getActivity().getSharedPreferences("Dickshouse", Context.MODE_PRIVATE);
        uname.setSummary(sharedPreferences3.getString(KEY_UNAME,"anon"));

        uname.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference mute, Object val) {
                String newVal = (String)val;
                uname.setSummary(newVal);
                return true;
            }
        });
    }
//
//    public void customizeFaq(Preference faq) {
//        faq.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//            @Override
//            public boolean onPreferenceClick(Preference preference) {
//                // If FAQ or Contact us ..do what needs to be done
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.cs.dartmouth.edu/~kiwi/"));
//                startActivity(browserIntent);
//                return false;
//            }
//        });
//    }

    public void customizeContact(Preference contact) {
        final Context that = getContext();
        contact.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                new AlertDialog.Builder(that)
                        .setTitle("Confirm")
                        .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // do nothing
                            }
                        })
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // OK has been pressed => force the new value and update the checkbox display
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:123456789"));
                                if ( ContextCompat.checkSelfPermission( that, Manifest.permission.CALL_PHONE ) != PackageManager.PERMISSION_GRANTED ) {
                                    ActivityCompat.requestPermissions(getActivity(), new String[] {  android.Manifest.permission.CALL_PHONE  }, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                                }

                                startActivity(callIntent);
                            }
                        }).create().show();

                return false;
            }
        });
    }

    public void customizeMute(Preference mute) {
        final SharedPreferences sharedPreferences4 = getActivity().getSharedPreferences("Dickshouse", Context.MODE_PRIVATE);
        mute.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference mute, Object val) {
                Boolean disable = (Boolean)val;
                if (disable.booleanValue()) {
                    // Disable notifications
                } else {
                    // Enable notifications
                }
                return true;
            }
        });
    }



    public void customizeScreenshot(Preference ss) {
        final SharedPreferences sharedPreferences5 = getActivity().getSharedPreferences("Dickshouse", Context.MODE_PRIVATE);
        ss.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference ss, Object val) {
                Boolean disable = (Boolean)val;
                if (disable.booleanValue()) {
                    getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
                } else {
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
                }
                return true;
            }
        });
    }

    public void customizeAnon(Preference anon) {
        final SharedPreferences sharedPreferences6 = getActivity().getSharedPreferences("Dickshouse", Context.MODE_PRIVATE);
        anon.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference anon, Object val) {
                Boolean disable = (Boolean)val;
                if (disable.booleanValue()) {
                    // Disable anonymity
                    getActivity().setTheme(R.style.AppTheme_Dark);
                } else {
                    // Enable anonimity
                    getActivity().setTheme(R.style.anonTheme);
                }
                return true;
            }
        });
    }
}