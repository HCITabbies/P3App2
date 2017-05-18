package com.p3app2;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.p3app2.Chat_Window.ChatRecord;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends Fragment {
    View _view;
    ListView list_View;
    ArrayAdapter<ChatRecord> chatRecordAdapter;
    private OnFragmentInteractionListener mListener;
    List<ChatRecord> chatRecords ;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Log.d("HistoryFragment", "reaching history onViewCreated");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _view =  inflater.inflate(R.layout.fragment_history, container, false);
        list_View = (ListView) _view.findViewById(R.id.historyChatsContainer);

         /*
        **********************************
        **********************************
        CODE TO VIEW WRITTEN RECORD
        - by Rohan Arora
        **********************************
        **********************************
         */


        FileInputStream fis = null;
        try {
            fis = getContext().openFileInput("ChatRecord1.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d("Written FileNotFound","");
        }

        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader bufferedReader = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        String json = sb.toString();
        ChatRecord openRecord = gson.fromJson(json, ChatRecord.class);


        Log.d("Opened Chat Record", String.valueOf(openRecord.getNumberOfMessages()));
        chatRecords = new ArrayList<ChatRecord>();

        chatRecords.add(openRecord);



        chatRecordAdapter = new ArrayAdapter<ChatRecord>(getContext(), R.layout.list_item_chat_history_list, chatRecords);
        list_View.setAdapter(chatRecordAdapter);


        return _view;
    }






    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
