package com.p3app2;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.p3app2.Chat_Window.ChatWindowActivity;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

/**
 * Created by jgraham on 5/15/17.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    ViewGroup _view_grp;
    View _view;

    PulsatorLayout _pulsator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _view_grp = container;
        _view =  inflater.inflate(R.layout.fragment_home, container, false);

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
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.start_session_btn):
                /* start the session */
                /*Chat Window Intent Code goes here*/
                Intent intent = new Intent(v.getContext(), ChatWindowActivity.class);
                startActivity(intent);


        }
    }
}
