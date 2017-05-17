package com.p3app2.Chat_Window;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.p3app2.MessageReceiverService;
import com.p3app2.R;
import com.p3app2.XMPPConnections;

import org.jivesoftware.smack.SmackException;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatWindowActivity extends AppCompatActivity {


    private EditText messageET;
    private static ListView messagesContainer;
    private Button sendBtn;
    private static ChatAdapter adapter;
    private ArrayList<ChatMessage> chatHistory;
    private Button finishChat;
    private int clientChatCounter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent ir  = new Intent(this, MessageReceiverService.class);
        startService(ir);
        setContentView(R.layout.chat_window);
        try {
            initControls();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

    private void initControls() throws SmackException.NotConnectedException {
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (Button) findViewById(R.id.chatSendButton);
        finishChat = (Button) findViewById(R.id.finishChatButton);
        clientChatCounter=0;
        TextView meLabel = (TextView) findViewById(R.id.meLbl);
        TextView companionLabel = (TextView) findViewById(R.id.friendLabel);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        companionLabel.setText("Counselor");// Hard Coded
        loadDummyHistory();
        ////


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setId(Integer.toString(clientChatCounter));
                clientChatCounter++;
                chatMessage.setMessage(messageText);
                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                chatMessage.setMe(true);
                try {
                    XMPPConnections.sendMessage(chatMessage.getMessage());
                }
                catch (SmackException.NotConnectedException e) {
                    e.printStackTrace();
                }

                messageET.setText("");

                displayMessage(chatMessage);
            }
        });


        finishChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////Save all the conversations to flat file

                MessageReceiverService.unsetNotification();
                finish();

            }
            });
    }

    public static void displayMessage(ChatMessage message)
    {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private static void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

    private void loadDummyHistory() throws SmackException.NotConnectedException {

        chatHistory = new ArrayList<ChatMessage>();

        ChatMessage msg = new ChatMessage();
        msg.setId("1");
        msg.setMe(false);
        msg.setMessage("Hello, Welcome to the Dick's House Online Counseling.");
        msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg);
        ChatMessage msg1 = new ChatMessage();
        msg1.setId("2");
        msg1.setMe(false);
        msg1.setMessage("I am your Counselor Tabby. What's bothering you today?");
        msg1.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg1);

        adapter = new ChatAdapter(ChatWindowActivity.this, new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);

        for(int i=0; i<chatHistory.size(); i++) {
            ChatMessage message = chatHistory.get(i);
            displayMessage(message);
        }
    }
}
