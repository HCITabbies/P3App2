package com.p3app2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.p3app2.Chat_Window.ChatRecord;

import java.util.ArrayList;

/**
 * Created by rohan on 18-05-2017.
 */

public class HistoryAdapter extends ArrayAdapter<ChatRecord> {
    public HistoryAdapter(@NonNull Context context, ArrayList<ChatRecord> resource) {
        super(context,0, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ChatRecord record = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_chat_history_list, parent, false);
        }
        // Lookup view for data population
        TextView startDate = (TextView) convertView.findViewById(R.id.chatStartInfo);
        TextView endDate = (TextView) convertView.findViewById(R.id.chatEndInfo);
        TextView numberOfMessages=(TextView) convertView.findViewById(R.id.chatMessageCount);
        // Populate the data into the template view using the data object
        startDate.setText("Chat Started on/at: "+record.getDateTimeStarted());
        endDate.setText("Chat Started on/at: "+record.getDateTimeEnded());
        numberOfMessages.setText("Messages exchanged in this conversation: "+record.getNumberOfMessages());
        // Return the completed view to render on screen
        return convertView;
    }
}
