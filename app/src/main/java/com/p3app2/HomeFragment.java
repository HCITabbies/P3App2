package com.p3app2;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.p3app2.Chat_Window.ChatWindowActivity;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

/**
 * Created by jgraham on 5/15/17.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    ViewGroup _view_grp;
    View _view;

    PulsatorLayout _pulsator;

    /* Shared Preferences for Anonymous indication textviews */
    protected SharedPreferences shared_pref;
    TextView anon_text_on;
    TextView anon_text_off;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _view_grp = container;
        _view = inflater.inflate(R.layout.fragment_home, container, false);

        /* set anon indicator text */
        anon_text_off = (TextView) _view.findViewById(R.id.anon_off_txt);
        anon_text_on = (TextView) _view.findViewById(R.id.anon_on_txt);

        /* Make pulsating start button */
        ImageButton start_button = (ImageButton) _view.findViewById(R.id.start_session_btn);
        start_button.setOnClickListener(this);

        return _view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        _pulsator = (PulsatorLayout) getView().findViewById(R.id.pulsator);
        _pulsator.start();
        Log.d("HomeFragment", "onViewCreated done");
    }

    @Override
    public void onResume() {
        super.onResume();
        shared_pref = getActivity().getSharedPreferences("Dickshouse", Context.MODE_PRIVATE);
        boolean anon_val = shared_pref.getBoolean(SettingsFragment.KEY_ANON, true);
        if (anon_val) {
            getActivity().setTheme(R.style.AppTheme);
            anon_text_off.setVisibility(TextView.VISIBLE);
            anon_text_on.setVisibility(TextView.INVISIBLE);
        } else {
            anon_text_off.setVisibility(TextView.INVISIBLE);
            anon_text_on.setVisibility(TextView.VISIBLE);
            getActivity().setTheme(R.style.anonTheme);
        }

        Log.d("HomeFragment", "onResume returning");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.start_session_btn):
                /* start the session */
                confirmChatDialog();
                return;
            default:
                return;

        }
    }

    public void confirmChatDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder
                .setMessage("About to start a Text-Chat Session - Please confirm")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        /*Chat Window Intent Code goes here*/
                        Intent intent = new Intent(_view.getContext(), ChatWindowActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .show();
    }
}
