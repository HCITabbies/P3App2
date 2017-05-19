package com.p3app2;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.p3app2.Chat_Window.ChatWindowActivity;


/**
 * Created by jgraham on 5/15/17.
 */

public class VoiceFragment extends Fragment implements View.OnClickListener {
    View _view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_voice, container, false);

        ImageButton voice_button = (ImageButton) _view.findViewById(R.id.voice_call_btn);
        voice_button.setOnClickListener(this);

        return _view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Log.d("VOiceFragment", "onViewCreated done");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.voice_call_btn):
                Log.d("VoiceFragment", "voice_call_btn pressed");
                confirmVoiceDialog();

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Dickshouse", Context.MODE_PRIVATE);
        boolean enable = sharedPreferences.getBoolean(SettingsFragment.KEY_ANON, true);

        if (enable) {
            getActivity().setTheme(R.style.AppTheme);
        } else {
            getActivity().setTheme(R.style.anonTheme);
        }

    }

    private void confirmVoiceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder
                .setMessage("About to call Dick's Medical House Staff - Please confirm")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Yes-code
                        Log.d("VoiceFragment", "Calling");
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "123456789"));
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
